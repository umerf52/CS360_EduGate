package com.apps.edu_gate;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AdminMainPageActivity extends BaseActivity {

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);
        setTitle("Administrator Home");

        FirebaseApp.initializeApp(this);

        ImageButton search = findViewById(R.id.search);
        Button verify_profile = findViewById(R.id.verify_profile);
        Button rate_tutor = findViewById(R.id.rate_tutor);
        Button change_password = findViewById(R.id.change_password);
        Button add_new_administrator = findViewById(R.id.add_new_administrator);
        Button delete_tutor_profile = findViewById(R.id.delete_tutor_profile);
        Button signout_button = findViewById(R.id.signoutButton);


        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(AdminMainPageActivity.this, SearchWithFragments.class);
                AdminMainPageActivity.this.startActivity(myIntent);
            }
        });
        verify_profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(AdminMainPageActivity.this, ViewPendingActivity.class);
                AdminMainPageActivity.this.startActivity(myIntent);
            }
        });
        rate_tutor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(AdminMainPageActivity.this, RateTutorActivity.class);
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
                Intent myIntent = new Intent(AdminMainPageActivity.this, AddNewAdminActivity.class);
                AdminMainPageActivity.this.startActivity(myIntent);
            }
        });
        delete_tutor_profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(AdminMainPageActivity.this, ProfileDeleteActivity.class);
                AdminMainPageActivity.this.startActivity(myIntent);
            }
        });
        signout_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               signOut();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else
            Toast.makeText(getBaseContext(), "Press back again in order to exit", Toast.LENGTH_SHORT).show();

        mBackPressed = System.currentTimeMillis();
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            Toast.makeText(AdminMainPageActivity.this, "Signed Out",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void signOut() {
        AlertDialog alertDialog = new AlertDialog.Builder(AdminMainPageActivity.this).create();
        alertDialog.setTitle("Sign Out?");
        alertDialog.setMessage("Are you sure you want to sign out?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        alertDialog.show();
    }
}

