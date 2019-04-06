package com.apps.edu_gate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SignupEducationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_education);
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.nextButton) {
            Intent myIntent = new Intent(SignupEducationActivity.this, StartupActivity.class);
            SignupEducationActivity.this.startActivity(myIntent);
        }
    }
}
