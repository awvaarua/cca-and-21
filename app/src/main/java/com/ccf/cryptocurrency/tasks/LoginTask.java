package com.ccf.cryptocurrency.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.content.Intent;

import com.ccf.cryptocurrency.UserActivity;

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
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
        }
        System.out.println(email);
        System.out.println(password);
        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {
        Intent i = new Intent(context, UserActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onProgressUpdate(Void... values) {}
}
