package com.apps.edu_gate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
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
    private String email;
    private EditText mInstitution;
    private EditText mTuitionLocation;
    private Spinner gradeDropdown;
    private Spinner subjectDropdown;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_education);

        Intent prevIntent = getIntent();

        FirstName = prevIntent.getStringExtra("Fname");
        LastName = prevIntent.getStringExtra("Lname");
        email = prevIntent.getStringExtra("email");
        CnicNo = prevIntent.getStringExtra("Cnic");
        Address = prevIntent.getStringExtra("Address");
        Gender = prevIntent.getStringExtra("Gender");
        ContactNo = prevIntent.getStringExtra("Contact");

        mInstitution = (EditText) findViewById(R.id.institution);
        mTuitionLocation = (EditText) findViewById(R.id.tuition_location);
        gradeDropdown = findViewById(R.id.tuition_grade);
        subjectDropdown = findViewById(R.id.tuition_subject);

        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Tutors");
    }

    public void onClick(View v) {
        if(!validateForm()){
            return;
        }

        int i = v.getId();
        if (i == R.id.submit_button) {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            addTutor();
            mAuth.signOut();
            Intent myIntent = new Intent(SignupEducationActivity.this, StartupActivity.class);
            SignupEducationActivity.this.startActivity(myIntent);
        }
    }


    private void addTutor(){
        String Institution = mInstitution.getText().toString();
        String Location = mTuitionLocation.getText().toString();
        String grade = gradeDropdown.getSelectedItem().toString();
        String subject = subjectDropdown.getSelectedItem().toString();

        String key = databaseReference.push().getKey();

        Tutor tutor = new Tutor(FirstName, LastName, email, CnicNo, Address, ContactNo,
                Gender, Institution, Location, grade, subject);

        tutor.setKey(key);

        databaseReference.child(key).setValue(tutor);
        Toast.makeText(this, "Profile data stored", Toast.LENGTH_LONG).show();
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
