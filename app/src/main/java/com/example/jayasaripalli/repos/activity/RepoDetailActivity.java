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

import com.example.jayasaripalli.repos.DetailFragment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_detail);
    }
}
