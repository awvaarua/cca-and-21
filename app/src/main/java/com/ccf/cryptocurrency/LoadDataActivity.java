package com.ccf.cryptocurrency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import com.ccf.cryptocurrency.tasks.LoginTask;

public class LoadDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Intent myIntent = getIntent();
        String email = myIntent.getStringExtra("email");
        String password = myIntent.getStringExtra("password");
        loginRequest(email, password);
    }

    private void loginRequest(String email, String password) {
        LoginTask loginTask = new LoginTask(getApplicationContext(), email, password);
        loginTask.execute();
    }
}
