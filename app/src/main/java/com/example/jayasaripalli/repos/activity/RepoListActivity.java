package com.example.jayasaripalli.repos.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.jayasaripalli.repos.R;
import com.example.jayasaripalli.repos.RepoListFragment;

public class RepoListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_list);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString("username",
                    getIntent().getStringExtra("username"));
            RepoListFragment fragment = new RepoListFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }
}
