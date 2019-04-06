package com.apps.edu_gate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TutorProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_profile);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            Toast.makeText(TutorProfileActivity.this, "Signed Out",
                    Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(TutorProfileActivity.this, StartupActivity.class);
            TutorProfileActivity.this.startActivity(myIntent);
        }
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signoutButton) {
            signOut();
        }
    }
}
