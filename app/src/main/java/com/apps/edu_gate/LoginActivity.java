package com.apps.edu_gate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;




public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "login user";
    private FirebaseAuth mAuth;
    private EditText mEmailField;
    private EditText mPasswordField;
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private int found1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("SignIn");
        mEmailField = findViewById(R.id.email);
        mPasswordField = findViewById(R.id.password);
        emailLayout = (TextInputLayout) findViewById(R.id.emailLayout);
        passwordLayout = (TextInputLayout) findViewById(R.id.passwordLayout);

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

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
//                            if (user.isEmailVerified()) {
//                                Log.d(TAG, "This was a verified user");
//                            } else {
//                                Log.d(TAG, "UNVERIFIED USER");
//                                new AlertDialog.Builder(LoginActivity.this)
//                                        .setTitle("Email not verified")
//                                        .setMessage("Your account has not been verified yet.\nCheck the email sent to you to verify your account.")
//                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int which) {
//                                            }
//                                        })
//                                        .show();
//                                mAuth.signOut();
//                                updateUI(null);
//                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "Login Failed:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        hideProgressDialog();
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailLayout.setErrorEnabled(true);
            emailLayout.setError("Required");
            valid = false;
        } else {
            emailLayout.setErrorEnabled(false);
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordLayout.setErrorEnabled(true);
            passwordLayout.setError("Required");
            valid = false;
        } else {
            passwordLayout.setErrorEnabled(false);
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            Toast.makeText(LoginActivity.this,
                    "User signed in: " + user.getEmail(), Toast.LENGTH_SHORT).show();
            if(found1==1){
                Intent myIntent = new Intent(LoginActivity.this, ViewYourProfileActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }else if(found1==0){
                Intent myIntent = new Intent(LoginActivity.this, AdminMainPageActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }
        } else {

        }
    }
    ValueEventListener valueEventListener2 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                found1 = 1;
                loginuser(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
            else{
                found1 = -1;
                Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(LoginActivity.this, StartupActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                found1 = 0;
                loginuser(mEmailField.getText().toString(), mPasswordField.getText().toString());
            }
            else{
                Query q1 = FirebaseDatabase.getInstance().getReference("Tutors").orderByChild("emailAddress").equalTo(mEmailField.getText().toString());
                q1.addListenerForSingleValueEvent(valueEventListener2);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    @Override
    public void onClick(View v) {
        if (!validateForm()) {
            return;
        }
        int i = v.getId();
        if (i == R.id.loginButton) {
            showProgressDialog();
            Query q = FirebaseDatabase.getInstance().getReference("Admin").orderByChild("mEmailAddress").equalTo(mEmailField.getText().toString());
            q.addListenerForSingleValueEvent(valueEventListener);

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
