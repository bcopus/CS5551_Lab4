package com.example.copus.quotemood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void handleRegisterButton(View v) {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    public void handleLoginButton(View v) {
        Intent loginIntent = new Intent(LoginActivity.this, MoodActivity.class);
        startActivity(loginIntent);
    }


}//LoginActivity
