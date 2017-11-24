package com.ccf.cryptocurrency.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.content.Intent;

import com.ccf.cryptocurrency.Infrastructure.ApiRestClient;
import com.loopj.android.http.*;

import com.ccf.cryptocurrency.UserActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginTask extends AsyncTask<String, Void, String> {

    private String email;
    private String password;
    private Context context;

    public LoginTask(Context context, String email, String password) {
        this.email = email;
        this.password = password;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        SharedPreferences sharedPref = context.getSharedPreferences("com.ccf.cryptocurrency", Context.MODE_PRIVATE);
        sharedPref.edit().putString("user_session", "");
        login();
        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {
        Intent i = new Intent(context, UserActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
    }

    @Override
    protected void onProgressUpdate(Void... values) {}

    private void login() {
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", password);
        ApiRestClient.post("customers/login", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                System.out.println(errorResponse);
            }
        });
    }
}
