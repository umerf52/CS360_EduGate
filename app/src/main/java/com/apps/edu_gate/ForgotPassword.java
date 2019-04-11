package com.apps.edu_gate;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends BaseActivity implements View.OnClickListener {


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
//        if (!validateInput()){
//            return;
//        }
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ForgotPassword.this,
                                    "Password reset link sent to your email", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(ForgotPassword.this,
                                    task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private boolean validateInput(){
        boolean temp = true;

        String email = userEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            userEmail.setError("Required.");
            temp = false;
        }
        else {
            userEmail.setError(null);
        }
        return temp;
    }

    @Override
    public void onClick(View v) {
        resetPassword(userEmail.getText().toString());
        Intent myIntent = new Intent(ForgotPassword.this, LoginActivity.class);
        ForgotPassword.this.startActivity(myIntent);
    }
}
