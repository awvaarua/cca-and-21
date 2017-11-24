package com.ccf.cryptocurrency;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

import com.ccf.cryptocurrency.Infrastructure.ApiRestClient;
import com.ccf.cryptocurrency.tasks.LoginTask;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

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
        //LoginTask loginTask = new LoginTask(getApplicationContext(), email, password);
        //loginTask.execute();
        login(email, password);
    }

    private void login(String email, String password) {
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", password);
        ApiRestClient.post("/customers/login", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String sessionId = response.getString("id");
                    int userId = response.getInt("userId");
                    SharedPreferences sharedPref = LoadDataActivity.this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("user_session", sessionId);
                    editor.putInt("user_id", userId);
                    editor.commit();

                    // Start the new activity
                    Intent i = new Intent(LoadDataActivity.this, UserActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    LoadDataActivity.this.startActivity(i);

                } catch (JSONException e){
                    System.out.println(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println(errorResponse);
            }
        });
    }
}
