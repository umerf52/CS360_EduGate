package com.apps.edu_gate;

import android.content.Intent;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class SignupEducationActivity extends AppCompatActivity {


    String FirstName;
    String LastName;
    String CnicNo;
    String Address;
    String Gender;
    String ContactNo;
    private EditText mInstitution;
    private EditText mTuitionLocation;
    private FirebaseAuth mAuth;

    DatabaseReference databaseTutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_education);

        Intent prevIntent = getIntent();

        FirstName = prevIntent.getStringExtra("Fname");
        LastName = prevIntent.getStringExtra("Lname");
        CnicNo = prevIntent.getStringExtra("Cnic");
        Address = prevIntent.getStringExtra("Address");
        Gender = prevIntent.getStringExtra("Gender");
        ContactNo = prevIntent.getStringExtra("Contact");

        mInstitution = (EditText) findViewById(R.id.institution);
        mTuitionLocation = (EditText) findViewById(R.id.location);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        databaseTutor = FirebaseDatabase.getInstance().getReference("Tutors");
    }

    public void onClick(View v) {
        if(!validateForm()){
            return;
        }

        int i = v.getId();
        if (i == R.id.finishButton) {
            //addTutor();
            //mAuth.signOut();
            Intent myIntent = new Intent(SignupEducationActivity.this, StartupActivity.class);
            SignupEducationActivity.this.startActivity(myIntent);
        }
    }


    private void addTutor(){
        Log.d("msg", "addtutor() trigggered");
        String Institution = mInstitution.getText().toString();
        String Location = mTuitionLocation.getText().toString();

        FirebaseUser user = mAuth.getCurrentUser();
        //String id = databaseTutor.push().getKey();

        Tutor tutor = new Tutor(FirstName, LastName, CnicNo, Address, ContactNo,
                Gender, Institution, Location);

        databaseTutor.setValue(tutor);
        Toast.makeText(this, "Profile data stored", Toast.LENGTH_LONG).show();
        Log.d("msg", "left addtutor()");
    }


    private boolean validateForm() {
        boolean valid = true;

        String Institute = mInstitution.getText().toString();
        if (TextUtils.isEmpty(Institute)) {
            mInstitution.setError("Required.");
            valid = false;
        } else {
            mInstitution.setError(null);
        }

        String Location = mTuitionLocation.getText().toString();
        if (TextUtils.isEmpty(Location)) {
            mTuitionLocation.setError("Required.");
            valid = false;
        } else {
            mTuitionLocation.setError(null);
        }

        return valid;
    }



}
