package com.apps.edu_gate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminMainPageActivity extends AppCompatActivity {

    Button search;
    Button verify_profile;
    Button rate_tutor;
    Button change_password;
    Button add_new_administrator;
    Button delete_tutor_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);

        search = findViewById(R.id.search);
        verify_profile = findViewById(R.id.verify_profile);
        rate_tutor = findViewById(R.id.rate_tutor);
        change_password = findViewById(R.id.change_password);
        add_new_administrator = findViewById(R.id.add_new_administrator);
        delete_tutor_profile = findViewById(R.id.delete_tutor_profile);

        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(AdminMainPageActivity.this, LoginActivity.class);
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
                Intent myIntent = new Intent(AdminMainPageActivity.this, LoginActivity.class);
                AdminMainPageActivity.this.startActivity(myIntent);
            }
        });
        change_password.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(AdminMainPageActivity.this, LoginActivity.class);
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
                Intent myIntent = new Intent(AdminMainPageActivity.this, LoginActivity.class);
                AdminMainPageActivity.this.startActivity(myIntent);
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


}