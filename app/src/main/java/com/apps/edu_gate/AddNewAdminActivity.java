package com.apps.edu_gate;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddNewAdminActivity extends BaseActivity {

    private static final String TAG = "AddNewAdminActivity";

    private EditText mfirst_name;
    private EditText mlast_name;
    private EditText memail_address;
    private EditText mpassword;
    private EditText mphone_number;

    Button add_admin;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseAdmin;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_admin);
        setTitle("Add New Administrator");

        // Views
        mfirst_name = findViewById(R.id.first_name);
        mlast_name = findViewById(R.id.last_name);
        memail_address = findViewById(R.id.email_address);
        mpassword = findViewById(R.id.password);
        mphone_number = findViewById(R.id.phone_number);

        // Buttons
        add_admin = findViewById(R.id.add_admin);;

        add_admin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!validateForm()){
                    return;
                }
                createAccount(memail_address.getText().toString(), mpassword.getText().toString());
                add_new_admin();
            }
        });

        // [START initialize_auth]
        // Initialize Firebase Auth
        FirebaseApp.initializeApp(this);
        databaseAdmin = FirebaseDatabase.getInstance().getReference().child("Admin");
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
    }


    private void createAccount(final String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        mAuth.signOut();

        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(AddNewAdminActivity.this, "Error making account.",
                                    Toast.LENGTH_LONG).show();
                            updateUI(null);
                        } else {
                            Toast.makeText(AddNewAdminActivity.this, "Account created ",
                                    Toast.LENGTH_LONG).show();
                            sendEmailVerification();
                            updateUI(null);
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, e.getMessage());
                        hideProgressDialog();
                        new AlertDialog.Builder(AddNewAdminActivity.this)
                                .setTitle("Error")
                                .setMessage(e.getMessage())
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                    }
                });
    }



    private void add_new_admin() {
        //showProgressDialog();
        String firstname = mfirst_name.getText().toString();
        String lastname = mlast_name.getText().toString();
        String email = memail_address.getText().toString();
        String contactNo = mphone_number.getText().toString();

        String id = databaseAdmin.push().getKey();
        Admin newadmin = new Admin(firstname, lastname, email, contactNo);

        databaseAdmin.child(id).setValue(newadmin);
    }


    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            Toast.makeText(AddNewAdminActivity.this,
                    "New Admin added", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(AddNewAdminActivity.this, AdminMainPageActivity.class);
            AddNewAdminActivity.this.startActivity(myIntent);
            //SignupEmailPasswordActivity.this.startActivity(myIntent);
        } else {
            mAuth.signOut();
            Toast.makeText(AddNewAdminActivity.this, "Signed Out",
                    Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(AddNewAdminActivity.this, StartupActivity.class);
            AddNewAdminActivity.this.startActivity(myIntent);
        }
    }


    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    private boolean validateForm() {
        boolean valid = true;

        String fname = mfirst_name.getText().toString();
        if (TextUtils.isEmpty(fname)) {
            mfirst_name.setError("Required.");
            valid = false;
        } else {
            mfirst_name.setError(null);
        }

        String lname = mlast_name.getText().toString();
        if (TextUtils.isEmpty(lname)) {
            mlast_name.setError("Required.");
            valid = false;
        } else {
            mlast_name.setError(null);
        }

        String email = memail_address.getText().toString();
        if (TextUtils.isEmpty(email)){
            memail_address.setError("Required");
            valid = false;
        }
        else{
            memail_address.setError(null);
        }

        if (!isEmailValid(email)) {
            memail_address.setError("Invalid email address.");
            valid = false;
        }

        String password = mpassword.getText().toString();
        if (TextUtils.isEmpty(password)){
            mpassword.setError("Required");
            valid = false;
        }
        else{
            mpassword.setError(null);
        }

        if (password.length() < 8) {
            mpassword.setError("Password should be 8 characters long.");
            valid = false;
        }

        String contact = mphone_number.getText().toString();
        if (TextUtils.isEmpty(contact)){
            mphone_number.setError("Required");
            valid = false;
        }
        else{
            mphone_number.setError(null);
        }

        return valid;
    }

    private void sendEmailVerification() {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button

                        if (task.isSuccessful()) {
//                            Toast.makeText(SignupEmailPasswordActivity.this,
//                                    "Verification email sent to " + user.getEmail(),
//                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(AddNewAdminActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
