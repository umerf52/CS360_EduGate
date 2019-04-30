package com.apps.edu_gate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends BaseActivity {

    private EditText old_password;
    private EditText new_password;
    private EditText re_new_password;
    private String sendingActivity;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (sendingActivity.equals("AdminMainPageActivity")) {
                Intent myIntent = new Intent(ChangePasswordActivity.this, AdminMainPageActivity.class);
                ChangePasswordActivity.this.startActivity(myIntent);
            } else if (sendingActivity.equals("ViewYourProfileActivity")) {
                Intent myIntent = new Intent(ChangePasswordActivity.this, ViewYourProfileActivity.class);
                ChangePasswordActivity.this.startActivity(myIntent);
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Intent prevIntent = getIntent();
        sendingActivity = prevIntent.getStringExtra("Sender");
        setTitle("Change Password");
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);


        old_password = findViewById(R.id.old_password);
        new_password = findViewById(R.id.new_password);
        re_new_password = findViewById(R.id.re_new_password);
        Button change_password_button = findViewById(R.id.change_password_button);

        FirebaseApp.initializeApp(this);

        change_password_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!validateForm()){
                    return;
                }
                changePassword();
            }
        });
    }


    public void changePassword() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        showProgressDialog();

        if (user != null) {
            String email = user.getEmail();
            String oldpass = old_password.getText().toString();

            AuthCredential credential = EmailAuthProvider.getCredential(email, oldpass);

            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //hideProgressDialog();
                    if (task.isSuccessful()) {
                        old_password.setError(null);
                        String newpass = new_password.getText().toString();
                        user.updatePassword(newpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {
                                    hideProgressDialog();
                                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                                } else {
                                    hideProgressDialog();
                                    Toast.makeText(getApplicationContext(), "Your password has been changed", Toast.LENGTH_LONG).show();
                                    if (sendingActivity.equals("AdminMainPageActivity")) {
                                        Intent myIntent = new Intent(ChangePasswordActivity.this, AdminMainPageActivity.class);
                                        ChangePasswordActivity.this.startActivity(myIntent);
                                    } else if (sendingActivity.equals("ViewYourProfileActivity")) {
                                        Intent myIntent = new Intent(ChangePasswordActivity.this, ViewYourProfileActivity.class);
                                        ChangePasswordActivity.this.startActivity(myIntent);
                                    }
                                }
                            }

                        });

                    } else {
                        hideProgressDialog();
                        old_password.setError("Incorrect Old Password");
                        Toast.makeText(getApplicationContext(), "Account Authentication Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }


    private boolean validateForm(){
        boolean valid = true;

        String oldpass = old_password.getText().toString();
        if (TextUtils.isEmpty(oldpass)){
            old_password.setError("Required");
            valid = false;
        }
        else{
            old_password.setError(null);
        }

        if (old_password.length() < 8) {
            old_password.setError("Incorrect Old Password");
            valid = false;
        }

        String newpass = new_password.getText().toString();
        if (TextUtils.isEmpty(newpass)){
            new_password.setError("Required");
            valid = false;
        }
        else{
            new_password.setError(null);
        }

        boolean temp = true;
        if (new_password.length() < 8) {
            new_password.setError("Password should be 8 characters long.");
            valid = false;
            temp = false;
        }

        String renewpass = re_new_password.getText().toString();
        if (TextUtils.isEmpty(renewpass)){
            re_new_password.setError("Required");
            valid = false;
        }
        else{
            re_new_password.setError(null);
        }

        if (re_new_password.length() < 8) {
            re_new_password.setError("Password should be 8 characters long.");
            valid = false;
            temp = false;
        }

        if(temp && (!newpass.equals(renewpass))){
            re_new_password.setError("New Password and confirmation passwords Don't match");
            valid = false;
        }

        return valid;
    }


}
