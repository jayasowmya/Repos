package com.example.jayasaripalli.repos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jayasaripalli.repos.model.User;
import com.example.jayasaripalli.repos.service.GithubApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DetailFragment extends Fragment {
    private String uName;
    private Retrofit retrofit = null;
    private ImageView profileImg;
    private TextView repoNum;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = requireActivity().getIntent().getExtras();
        if (bundle != null) {
            uName = bundle.getString("username");
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_detail, container, false);
        profileImg = view.findViewById(R.id.profile_img);
        repoNum = view.findViewById(R.id.numberRepos);
        if (uName!=null){
            connectAndGetApiData();
        }
        return view;
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
            }
        }

        private void showAlert(String message){
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(requireActivity());
            builder.setTitle("Error")
                    .setMessage(message)
                    .setPositiveButton("try again", (dialog, which) -> requireActivity().onBackPressed())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
}
