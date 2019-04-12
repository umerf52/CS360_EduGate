package com.apps.edu_gate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class SignupEducationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_education);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.finishButton) {
            mAuth.signOut();
            Intent myIntent = new Intent(SignupEducationActivity.this, StartupActivity.class);
            SignupEducationActivity.this.startActivity(myIntent);
        }
    }
}
