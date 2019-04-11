package com.apps.edu_gate;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "login user";
    private FirebaseAuth mAuth;
    private EditText mEmailField;
    private EditText mPasswordField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);

        findViewById(R.id.loginButton).setOnClickListener(this);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void loginuser(String email, String password){
        Log.d(TAG, "Login:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "Login:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user.isEmailVerified()) {
                                Log.d(TAG, "This was a verified user");
                                updateUI(user);
                            } else {
                                Log.d(TAG, "UNVERIFIED USER");
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setTitle("Email not verified")
                                        .setMessage("Your account has not been verified yet.\nCheck the email sent to you to verify your account.")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .show();
                                mAuth.signOut();
                                updateUI(null);
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "Login Failed:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            Toast.makeText(LoginActivity.this,
                    "User signed in: " + user.getEmail(), Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(LoginActivity.this, TutorProfileActivity.class);
            LoginActivity.this.startActivity(myIntent);
        } else {

        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.loginButton) {
            loginuser(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.signupButton) {
            Intent myIntent = new Intent(LoginActivity.this, SignupEmailPasswordActivity.class);
            LoginActivity.this.startActivity(myIntent);
        }
        else if (i == R.id.forgotPassword){
            Intent myintent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            LoginActivity.this.startActivity(myintent);
        }
    }
}
