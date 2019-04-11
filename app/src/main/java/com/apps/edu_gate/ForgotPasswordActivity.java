package com.apps.edu_gate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {


    private EditText userEmail;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        findViewById(R.id.resetPasswordButton).setOnClickListener(this);
        userEmail = findViewById(R.id.userEmail);

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void resetPassword(String email){
        if (!validateInput()) {
            return;
        }
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ForgotPasswordActivity.this,
                                    "Password reset link sent to your email", Toast.LENGTH_LONG).show();
                            Intent myIntent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                            ForgotPasswordActivity.this.startActivity(myIntent);
                        }
                        else{
                            Toast.makeText(ForgotPasswordActivity.this,
                                    task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private boolean validateInput(){
        boolean valid = true;

        String email = userEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            userEmail.setError("Required.");
            valid = false;
        } else if (!isEmailValid(email)) {
            userEmail.setError("Invalid email address.");
            valid = false;
        } else {
            userEmail.setError(null);
        }
        return valid;
    }

    @Override
    public void onClick(View v) {
        resetPassword(userEmail.getText().toString());
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
