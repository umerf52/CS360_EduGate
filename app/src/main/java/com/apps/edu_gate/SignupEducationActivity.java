package com.apps.edu_gate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;

public class SignupEducationActivity extends AppCompatActivity {

    ArrayList<Spinner> grade_spinners = new ArrayList<Spinner>();
    ArrayList<Spinner> subject_spinners = new ArrayList<Spinner>();
    ArrayList<String> grade_values = new ArrayList<String>();
    ArrayList<String> subject_values = new ArrayList<String>();

    String FirstName;
    String LastName;
    String CnicNo;
    String Address;
    String Gender;
    String ContactNo;
    private String email;
    private EditText mInstitution;
    private EditText mTuitionLocation;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_education);

        addSpinners();

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
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Tutors");
    }

    public void onClick(View v) {
        if(!validateForm()){
            return;
        }

        int i = v.getId();
        if (i == R.id.submit_button) {
            if (!getSpinnerValues()) return;
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            addTutor();
            mAuth.signOut();
            Intent myIntent = new Intent(SignupEducationActivity.this, StartupActivity.class);
            SignupEducationActivity.this.startActivity(myIntent);
        } else if (i == R.id.fab) {
            addSpinners();
        }
    }


    private void addTutor(){
        String Institution = mInstitution.getText().toString();
        String Location = mTuitionLocation.getText().toString();

        String key = databaseReference.push().getKey();

        Tutor tutor = new Tutor(FirstName, LastName, email, CnicNo, Address, ContactNo,
                Gender, Institution, Location, grade_values, subject_values);

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

    private void addSpinners() {
        LinearLayout dropdown_layout = (LinearLayout) findViewById(R.id.subjects_grades_layout);

        Spinner newSpinner = new Spinner(SignupEducationActivity.this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                SignupEducationActivity.this, R.array.subjectOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        newSpinner.setAdapter(adapter);

        Spinner newSpinner1 = new Spinner(SignupEducationActivity.this);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                SignupEducationActivity.this, R.array.gradeOptions, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        newSpinner1.setAdapter(adapter1);

        LinearLayout temp_layout = new LinearLayout(this);

        temp_layout.setOrientation(LinearLayout.HORIZONTAL);
        temp_layout.addView(newSpinner1);
        temp_layout.addView(newSpinner);
        grade_spinners.add(newSpinner1);
        subject_spinners.add(newSpinner);

        dropdown_layout.addView(temp_layout);
    }

    private boolean getSpinnerValues() {
        subject_values.clear();
        grade_values.clear();
        Iterator i = grade_spinners.iterator();
        while (i.hasNext()) {
            Spinner tmp = (Spinner) i.next();
            String val = String.valueOf(tmp.getSelectedItem());
            if (val.equals("Grade")) {
                Toast.makeText(this, "Invalid value for Grade", Toast.LENGTH_SHORT).show();
                grade_values.clear();
                return false;
            }
            grade_values.add(String.valueOf(tmp.getSelectedItem()));
        }

        Iterator j = subject_spinners.iterator();
        while (j.hasNext()) {
            Spinner tmp = (Spinner) j.next();
            String val = String.valueOf(tmp.getSelectedItem());
            if (val.equals("Subject")) {
                Toast.makeText(this, "Invalid value for Subject", Toast.LENGTH_SHORT).show();
                subject_values.clear();
                return false;
            }
            subject_values.add(String.valueOf(tmp.getSelectedItem()));
        }

        return true;
    }

}
