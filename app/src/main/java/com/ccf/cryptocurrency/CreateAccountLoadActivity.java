package com.ccf.cryptocurrency;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ccf.cryptocurrency.Infrastructure.ApiRestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CreateAccountLoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Intent myIntent = getIntent();
        String email = myIntent.getStringExtra("email");
        String password = myIntent.getStringExtra("password");
        createAccount(email, password);
    }

    private void createAccount(String email, String password) {
        createAccountRequest(email, password);
    }

    private void createAccountRequest(String email, final String password) {
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", password);
        ApiRestClient.postAsync("/customers", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String emailResponse = response.getString("email");
                    int userId = response.getInt("id");
                    login(emailResponse, password);

                } catch (Exception e/*JSONException e*/){
                    finish();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                finish();
            }
        });
    }

    private void login(String email, String password) {
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", password);
        ApiRestClient.postAsync("/customers/login", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String sessionId = response.getString("id");
                    int userId = response.getInt("userId");
                    SharedPreferences sharedPref = CreateAccountLoadActivity.this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("user_session", sessionId);
                    editor.putInt("user_id", userId);
                    editor.commit();
                    ApiRestClient.setSession(sessionId);

                    // Start the new activity
                    Intent i = new Intent(CreateAccountLoadActivity.this, UserActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    CreateAccountLoadActivity.this.startActivity(i);

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
