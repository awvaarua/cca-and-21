package com.ccf.cryptocurrency;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateAccountActivity extends AppCompatActivity {

    Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        createAccountButton = (Button) findViewById(R.id.button_login);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        Intent createAccountIntent = new Intent(this, CreateAccountLoadActivity.class);
        createAccountIntent.putExtra("email","sanso.barcelo94@gmail.com");
        createAccountIntent.putExtra("password","fura4468AB");
        //loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(createAccountIntent);
    }
}
