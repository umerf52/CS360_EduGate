package com.apps.edu_gate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AdminMainPageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    Button search;
    Button verify_profile;
    Button rate_tutor;
    Button change_password;
    Button add_new_administrator;
    Button delete_tutor_profile;
    Button signoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);

        search = findViewById(R.id.search);
        verify_profile = findViewById(R.id.verify_profile);
        rate_tutor = findViewById(R.id.rate_tutor);
        change_password = findViewById(R.id.change_password);
        add_new_administrator = findViewById(R.id.add_new_administrator);
        delete_tutor_profile = findViewById(R.id.delete_tutor_profile);
        signoutButton = findViewById(R.id.signoutButton);


        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(AdminMainPageActivity.this, SearchPageActivity.class);
                AdminMainPageActivity.this.startActivity(myIntent);
            }
        });
        verify_profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(AdminMainPageActivity.this, LoginActivity.class);
                AdminMainPageActivity.this.startActivity(myIntent);
            }
        });
        rate_tutor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(AdminMainPageActivity.this, RateMainActivity.class);
                AdminMainPageActivity.this.startActivity(myIntent);
            }
        });
        change_password.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(AdminMainPageActivity.this, ChangePasswordActivity.class);
                AdminMainPageActivity.this.startActivity(myIntent);
            }
        });
        add_new_administrator.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(AdminMainPageActivity.this, LoginActivity.class);
                AdminMainPageActivity.this.startActivity(myIntent);
            }
        });
        delete_tutor_profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(AdminMainPageActivity.this, ProfileDeleteActivity.class);
                AdminMainPageActivity.this.startActivity(myIntent);
            }
        });
        signoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               signOut();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void updateUI(FirebaseUser user) {
        if (user == null) {
            Toast.makeText(AdminMainPageActivity.this, "Signed Out",
                    Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(AdminMainPageActivity.this, StartupActivity.class);
            AdminMainPageActivity.this.startActivity(myIntent);
        }
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }
}

