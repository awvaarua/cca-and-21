package com.ccf.cryptocurrency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    TextView createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        createAccount = (TextView) findViewById(R.id.createAccount);

        createAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void login() {
        Intent loginIntent = new Intent(this, LoadDataActivity.class);
        loginIntent.putExtra("email","sanso.barcelo94@gmail.com");
        loginIntent.putExtra("password","fura4468AB");
        //loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
        //finish();
    }

    private void createAccount() {
        Intent createAccountIntent = new Intent(this, CreateAccountActivity.class);
        //loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(createAccountIntent);
        //finish();
    }
}
