package com.apps.edu_gate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText old_password;
    private EditText new_password;
    private EditText re_new_password;
    private FirebaseAuth firebaseAuth;
    Button change_password_button;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        //findViewById(R.id.change_password_button).setOnClickListener(this);
        old_password = findViewById(R.id.old_password);
        new_password = findViewById(R.id.new_password);
        re_new_password = findViewById(R.id.re_new_password);
        change_password_button = findViewById(R.id.change_password_button);
        dialog = new ProgressDialog(this);

        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void changePassword() {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        if (user != null) {
            String email = user.getEmail();
            String oldpass = old_password.getText().toString();

            AuthCredential credential = EmailAuthProvider.getCredential(email, oldpass);

            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        String newpass = new_password.getText().toString();
                        String confirmpass = re_new_password.getText().toString();

                        if(newpass != confirmpass){
                            Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_LONG);
                            return;
                        }

                        user.updatePassword(newpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Your password has been changed", Toast.LENGTH_LONG);
                                }
                            }

                        });

                    } else {
                        Toast.makeText(getApplicationContext(), "Authentication Failed", Toast.LENGTH_LONG);
                    }
                }
            });

        }
    }

    //@Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.change_password_button){
            changePassword();
        }

    }

}