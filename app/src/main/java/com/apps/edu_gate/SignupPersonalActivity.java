package com.apps.edu_gate;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Dictionary;
import java.util.Hashtable;

public class SignupPersonalActivity extends BaseActivity {

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mCnicNumber;
    private EditText mAddress;
    private Spinner mGender;
    private EditText mContact;
    private String email;

    private Button mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_personal);

        Intent prevIntent = getIntent();

        email = prevIntent.getStringExtra("email");
        Toast.makeText(this, email, Toast.LENGTH_LONG).show();

        mFirstName = (EditText) findViewById(R.id.first_name);
        mLastName = (EditText) findViewById(R.id.last_name);
        mCnicNumber = (EditText) findViewById(R.id.cnic_number);
        mAddress = (EditText) findViewById(R.id.house_address);
        mGender = (Spinner) findViewById(R.id.my_education_dropdown);
        mContact = (EditText) findViewById(R.id.contact_number);

        mNextButton = findViewById(R.id.submit_button);

        mContact.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    public void onClick(View v) {
        if (!validateForm()) {
            return;
        }

        String FirstName = mFirstName.getText().toString().trim();
        String LastName = mLastName.getText().toString().trim();
        String CnicNo = mCnicNumber.getText().toString();
        String Address = mAddress.getText().toString().trim();
        String Gender = mGender.getSelectedItem().toString();
        String Contact = mContact.getText().toString();

        int i = v.getId();
        if (i == R.id.submit_button) {
//            Dictionary<String, String> data = AddPresonalInfo();
            Intent myIntent = new Intent(SignupPersonalActivity.this, SignupEducationActivity.class);
            myIntent.putExtra("Fname", FirstName);
            myIntent.putExtra("Lname", LastName);
            myIntent.putExtra("email", email);
            myIntent.putExtra("Cnic", CnicNo);
            myIntent.putExtra("Address", Address);
            myIntent.putExtra("Gender", Gender);
            myIntent.putExtra("Contact", Contact);
            SignupPersonalActivity.this.startActivity(myIntent);
        }
    }

    private Dictionary<String, String> AddPresonalInfo() {
        String FirstName = mFirstName.getText().toString().trim();
        String LastName = mLastName.getText().toString().trim();
        String CnicNo = mCnicNumber.getText().toString();
        String Address = mAddress.getText().toString().trim();
        String Gender = mGender.getSelectedItem().toString();
        String Contact = mContact.getText().toString();


        Dictionary<String, String> data = new Hashtable<String, String>();
        data.put("kFirstName", FirstName);
        data.put("kLastName", LastName);
        data.put("kCnicNo", CnicNo);
        data.put("kAddress", Address);
        data.put("kGender", Gender);
        data.put("kContact", Contact);

        return data;
    }

    private boolean validateForm() {
        boolean valid = true;

        String FName = mFirstName.getText().toString();
        if (TextUtils.isEmpty(FName)) {
            mFirstName.setError("Required.");
            valid = false;
        } else {
            mFirstName.setError(null);
        }

        String LName = mLastName.getText().toString();
        if (TextUtils.isEmpty(LName)) {
            mLastName.setError("Required.");
            valid = false;
        } else {
            mLastName.setError(null);
        }

        String Cnic = mCnicNumber.getText().toString();
        if (TextUtils.isEmpty(Cnic)) {
            mCnicNumber.setError("Required.");
            valid = false;
        } else {
            mCnicNumber.setError(null);
        }

        String Address = mAddress.getText().toString();
        if (TextUtils.isEmpty(Address)) {
            mAddress.setError("Required.");
            valid = false;
        } else {
            mAddress.setError(null);
        }

        String Gender = mGender.getSelectedItem().toString();
        if (TextUtils.isEmpty(Gender)) {
            //mGender.setError("Required.");
            valid = false;
        }

        String Contact = mContact.getText().toString();
        if (TextUtils.isEmpty(Contact)) {
            mContact.setError("Required.");
            valid = false;
        } else {
            mContact.setError(null);
        }

        return valid;
    }
}
