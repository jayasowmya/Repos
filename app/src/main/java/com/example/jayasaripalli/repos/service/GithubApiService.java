package com.example.jayasaripalli.repos.service;

import com.example.jayasaripalli.repos.model.Repository;
import com.example.jayasaripalli.repos.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApiService {

    @GET("repositories")
    Call<List<Repository>> getRepositories();

    @GET("users/{user}/repos")
    Call<List<Repository>> listRepos(@Path("user") String user);

    @GET("users/{username}")
    Call<User> getUser(@Path("username") String user);

  /*
    class Factory {

        public static GithubApiService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.github.com/")
                    .build();

            return retrofit.create(GithubApiService.class);
        }
    }*/
}