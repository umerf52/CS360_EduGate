package com.apps.edu_gate;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Dictionary;
import java.util.Hashtable;

public class SignupPersonalActivity extends BaseActivity {

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mCnicNumber;
    private EditText mAddress;
    private Spinner mGender;
    private EditText mContact;

    private Button mNextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_personal);

        mFirstName = (EditText) findViewById(R.id.location);
        mLastName = (EditText) findViewById(R.id.institution);
        mCnicNumber = (EditText) findViewById(R.id.cnicNumber);
        mAddress = (EditText) findViewById(R.id.houseAddress);
        mGender = (Spinner) findViewById(R.id.educationDropdown);
        mContact = (EditText) findViewById(R.id.phoneNumber);

        mNextButton = findViewById(R.id.finishButton);

        mContact.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    public void onClick(View v) {
        if (!validateForm()){
            return;
        }

        String FirstName = mFirstName.getText().toString();
        String LastName = mLastName.getText().toString();
        String CnicNo = mCnicNumber.getText().toString();
        String Address = mAddress.getText().toString();
        String Gender = mGender.getSelectedItem().toString();
        String Contact = mContact.getText().toString();

        int i = v.getId();
        if (i == R.id.finishButton) {
            Dictionary<String, String> data = AddPresonalInfo();
            Intent myIntent = new Intent(SignupPersonalActivity.this, SignupEducationActivity.class);
            myIntent.putExtra("Fname", FirstName);
            myIntent.putExtra("Lname", LastName);
            myIntent.putExtra("Cnic", CnicNo);
            myIntent.putExtra("Address", Address);
            myIntent.putExtra("Gender", Gender);
            myIntent.putExtra("Contact", Contact);
            SignupPersonalActivity.this.startActivity(myIntent);
        }
    }

    private Dictionary<String, String> AddPresonalInfo(){
        String FirstName = mFirstName.getText().toString();
        String LastName = mLastName.getText().toString();
        String CnicNo = mCnicNumber.getText().toString();
        String Address = mAddress.getText().toString();
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

//        if (password.length() < 8) {
//            mPasswordField.setError("Password should be 8 characters long.");
//            valid = false;
//        }

//        if(!isValidPassword(password)) {
//            mPasswordField.setError("Password requirements not met.");
//            valid = false;
//        }

        return valid;
    }

//    boolean isNameValid(CharSequence email) {
//        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
//    }
}
