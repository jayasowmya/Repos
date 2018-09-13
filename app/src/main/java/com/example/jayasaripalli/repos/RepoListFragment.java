package com.example.jayasaripalli.repos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jayasaripalli.repos.adapter.RepoAdapter;
import com.example.jayasaripalli.repos.model.Repository;
import com.example.jayasaripalli.repos.service.GithubApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class RepoListFragment extends Fragment {
    private RecyclerView repoList;
    private String uName;
    private Retrofit retrofit = null;

    public RepoListFragment() {
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
        View view= inflater.inflate(R.layout.fragment_repo_list, container, false);
        repoList = view.findViewById(R.id.list_repo);


        repoList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        repoList.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(repoList.getContext(),
                layoutManager.getOrientation());
        repoList.addItemDecoration(dividerItemDecoration);

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
        repoList.setAdapter(new RepoAdapter(repositoryList, R.layout.list_item, requireActivity().getApplicationContext()));
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
