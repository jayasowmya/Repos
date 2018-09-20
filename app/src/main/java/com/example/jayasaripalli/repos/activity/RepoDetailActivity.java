package com.example.jayasaripalli.repos.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jayasaripalli.repos.GlideApp;
import com.example.jayasaripalli.repos.R;
import com.example.jayasaripalli.repos.RepoListFragment;
import com.example.jayasaripalli.repos.model.User;
import com.example.jayasaripalli.repos.service.GithubApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepoDetailActivity extends AppCompatActivity {

    private boolean mTwoPane;
    private String uName;
    private Retrofit retrofit = null;
    private ImageView profileImg;
    private TextView repoNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_detail);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            uName = bundle.getString("username");
        }
        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        profileImg = findViewById(R.id.profile_img);
        repoNum = findViewById(R.id.numberRepos);
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
                if (response.code() == 200) {
                    loadImage(response);
                } else {
                    showAlert(response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showAlert(t.getMessage());

            }
        });
    }

    private void loadImage(Response<User> response) {
        if (response != null && response.body() != null) {
            GlideApp.with(this)
                    .load(response.body().getAvatar_url())
                    .override(300, 300)
                    .centerCrop()
                    .error(R.drawable.ic_launcher_background)
                    .into(profileImg);
            repoNum.setText("the number of public repositories are:"+response.body().getPublic_repos());
            profileImg.setOnClickListener(view -> {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString("username", uName);
                    RepoListFragment fragment = new RepoListFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, RepoListActivity.class);
                    intent.putExtra("username", uName);

                    context.startActivity(intent);
                }
            });
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
