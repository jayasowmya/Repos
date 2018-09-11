package com.example.jayasaripalli.repos.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jayasaripalli.repos.R;

import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main activity";
    private RecyclerView repoList;
    private EditText username;
    private String uName;
    private Button submitBtn;
    private static Retrofit retrofit = null;
    private ImageView profileImg;
    private TextView repoNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.ed_username);
        submitBtn = findViewById(R.id.btn_submit);

        submitBtn.setOnClickListener(view -> {
            hideSoftKeyboard();
            uName = username.getText().toString();
            Intent intent = new Intent(this, RepoDetailActivity.class);
            intent.putExtra("username", uName);
            startActivity(intent);
        });
    }

    private  void hideSoftKeyboard() {
        final InputMethodManager inputMethodManager
                = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive() && this.getCurrentFocus() != null) {
            inputMethodManager
                    .hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
