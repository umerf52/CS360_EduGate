package com.apps.edu_gate;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupPersonalActivity extends BaseActivity {

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mCnicNumber;
    private EditText mAddress;
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
        mContact = (EditText) findViewById(R.id.phoneNumber);

        mNextButton = findViewById(R.id.finishButton);

        mContact.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.finishButton) {
            Intent myIntent = new Intent(SignupPersonalActivity.this, SignupEducationActivity.class);
            SignupPersonalActivity.this.startActivity(myIntent);
        }
    }

}
