package com.apps.edu_gate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddNewAdminActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private EditText mfirst_name;
    private EditText mlast_name;
    private EditText memail_address;
    private EditText mpassword;
    private EditText mphone_number;

    Button add_admin;


    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_admin);
        setTitle("New Admin");

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
//                Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_LONG).show();
                add_new_admin();
            }
        });

        // [START initialize_auth]
        // Initialize Firebase Auth
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
    }


    private void add_new_admin() {
//        showProgressDialog();

//        if (isValidPassword(mpassword) && isEmailValid(memail_address))
//        {
//            Admin admin = new Admin(mfirst_name, mlast_name, memail_address, mphone_number);
//
////            uploadFileProfile(profileImageUri, tutor);
//        }
//        else if (!isEmailValid(memail_address)  || !isValidPassword(mpassword))
//        {
//            Toast.makeText(getApplicationContext(), "Email or Password not valid", Toast.LENGTH_LONG).show();
//        }
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

}
