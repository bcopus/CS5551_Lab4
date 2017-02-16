package com.example.copus.quotemood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    //return to the Login Activity.
    public void handleCancelButton(View v) {
        Intent cancelIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(cancelIntent);
    }

    //user has created and account, send to Login Activity
    public void handleRegisterButton(View v) {
        Intent loginIntent = new Intent(RegisterActivity.this, MoodActivity.class);
        startActivity(loginIntent);
    }

} //RegisterActivity
