package com.apps.edu_gate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class StartupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.hide();
        }
        setContentView(R.layout.activity_startup);

        Button student_button = findViewById(R.id.student_button);
        Button tutor_button = findViewById(R.id.tutor_button);

        tutor_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(StartupActivity.this, LoginActivity.class);
                StartupActivity.this.startActivity(myIntent);
            }
        });

        student_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(StartupActivity.this, SearchWithFragments.class);
                myIntent.putExtra("who","startup");
                StartupActivity.this.startActivity(myIntent);
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
