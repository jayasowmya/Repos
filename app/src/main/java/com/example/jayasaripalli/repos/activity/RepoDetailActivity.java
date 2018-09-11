package com.example.jayasaripalli.repos.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jayasaripalli.repos.GlideApp;
import com.example.jayasaripalli.repos.R;
import com.example.jayasaripalli.repos.adapter.RepoAdapter;
import com.example.jayasaripalli.repos.model.Repository;
import com.example.jayasaripalli.repos.model.User;
import com.example.jayasaripalli.repos.service.GithubApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepoDetailActivity extends AppCompatActivity {

    private static final String TAG = "RepoDetailActivity";
    private RecyclerView repoList;
    private String uName;
    private static Retrofit retrofit = null;
    private ImageView profileImg;
    private TextView repoNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            uName=bundle.getString("username");
        } else {
            Log.e("Bundle not passed",TAG);
        }
        setContentView(R.layout.activity_repo_detail);
        repoList = findViewById(R.id.list_repo);
        profileImg = findViewById(R.id.profile_img);
        repoNum = findViewById(R.id.numberRepos);

        repoList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        repoList.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(repoList.getContext(),
                layoutManager.getOrientation());
        repoList.addItemDecoration(dividerItemDecoration);

        if (uName!=null){
            connectAndGetApiData();
        }
    }

    private void connectAndGetApiData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        GithubApiService githubApiService = retrofit.create(GithubApiService.class);
        Call<User> userCall = githubApiService.getUser(uName);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code()==200) {
                    loadImage(response);
                }else{
                    showAlert(response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showAlert(t.getMessage());

            }
        });
        Call<List<Repository>> listRepocall = githubApiService.listRepos(uName);
        listRepocall.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                if (response.code()==200) {
                    handleResult(response);
                }else{
                    showAlert(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable throwable) {
               showAlert(throwable.getMessage());
            }
        });
    }

    private void handleResult(Response<List<Repository>> response) {
        List<Repository> repositoryList = new ArrayList<>();
        assert response.body() != null;
        for (int i = 0; i < Objects.requireNonNull(response.body()).size(); i++) {
            repositoryList.add(response.body().get(i));
        }
        repoList.setAdapter(new RepoAdapter(repositoryList, R.layout.list_item, getApplicationContext()));
    }

    private void loadImage(Response<User> response) {
        if (response != null && response.body() != null) {
            GlideApp.with(this)
                    .load(response.body().getAvatar_url())
                    .override(300, 300)
                    .centerCrop()
                    .error(R.drawable.ic_launcher_background)
                    .into(profileImg);
            repoNum.setText("the number of public repositories are"+response.body().getPublic_repos());
        }
    }

    private void showAlert(String message){
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Error")
                .setMessage(message)
                .setPositiveButton("try again", (dialog, which) -> onBackPressed())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
