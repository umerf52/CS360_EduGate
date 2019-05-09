package com.apps.edu_gate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class SignupPersonalActivity extends BaseActivity {

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mCnicNumber;
    private EditText mAddress;
    private Spinner mGender;
    private EditText mContact;
    private String email;
    private TextInputLayout mFirstNameLayout;
    private TextInputLayout mLastNameLayout;
    private TextInputLayout mCnicLayout;
    private TextInputLayout mAddressLayout;
    private TextInputLayout mContactNoLayout;

    private static final int PICK_IMAGE_REQUEST = 1;
    private TextView imageName;
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_personal);
        setTitle("Personal Details");

        Intent prevIntent = getIntent();

        email = prevIntent.getStringExtra("email");
        Toast.makeText(this, email, Toast.LENGTH_LONG).show();

        mFirstName = findViewById(R.id.first_name);
        mLastName = findViewById(R.id.last_name);
        mCnicNumber = findViewById(R.id.cnic_number);
        mAddress = findViewById(R.id.house_address);
        mGender = findViewById(R.id.my_education_dropdown);
        mContact = findViewById(R.id.contact_number);
        Button imageButton = findViewById(R.id.image_button);
        imageName = findViewById(R.id.image_name);
        Button mNextButton = findViewById(R.id.submit_button);
        mFirstNameLayout = findViewById(R.id.first_nameLayout);
        mLastNameLayout = findViewById(R.id.last_nameLayout);
        mCnicLayout = findViewById(R.id.cnic_numberLayout);
        mAddressLayout = findViewById(R.id.house_addressLayout);
        mContactNoLayout = findViewById(R.id.contact_numberLayout);

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });


    }

    public void submitForm() {
        if (!validateForm()) {
            return;
        }

        String FirstName = mFirstName.getText().toString().trim();
        String LastName = mLastName.getText().toString().trim();
        String CnicNo = mCnicNumber.getText().toString();
        String Address = mAddress.getText().toString().trim();
        String Gender = mGender.getSelectedItem().toString();
        String Contact = mContact.getText().toString();

        Intent myIntent = new Intent(SignupPersonalActivity.this, SignupEducationActivity.class);
        myIntent.putExtra("Fname", FirstName);
        myIntent.putExtra("Lname", LastName);
        myIntent.putExtra("email", email);
        myIntent.putExtra("Cnic", CnicNo);
        myIntent.putExtra("Address", Address);
        myIntent.putExtra("Gender", Gender);
        myIntent.putExtra("Contact", Contact);
        myIntent.putExtra("myImageUriString", mImageUri.toString());
        SignupPersonalActivity.this.startActivity(myIntent);
    }


    private boolean validateForm() {
        boolean valid = true;

        if (mImageUri == null) {
            valid = false;
            Toast.makeText(getApplicationContext(), "No picture added", Toast.LENGTH_SHORT).show();
        }

        String FName = mFirstName.getText().toString();
        if (TextUtils.isEmpty(FName)) {
            mFirstNameLayout.setErrorEnabled(true);
            mFirstNameLayout.setError("Required.");
            valid = false;
        } else {
            mFirstNameLayout.setErrorEnabled(false);
            mFirstName.setError(null);
        }

        String LName = mLastName.getText().toString();
        if (TextUtils.isEmpty(LName)) {
            mLastNameLayout.setErrorEnabled(true);
            mLastNameLayout.setError("Required.");
            valid = false;
        } else {
            mLastNameLayout.setErrorEnabled(false);
            mLastName.setError(null);
        }

        String Cnic = mCnicNumber.getText().toString();
        if (TextUtils.isEmpty(Cnic)) {
            mCnicLayout.setErrorEnabled(true);
            mCnicLayout.setError("Required.");
            valid = false;
        } else {
            mCnicLayout.setErrorEnabled(false);
            mCnicNumber.setError(null);
        }

        String Address = mAddress.getText().toString();
        if (TextUtils.isEmpty(Address)) {
            mAddressLayout.setErrorEnabled(true);
            mAddressLayout.setError("Required.");
            valid = false;
        } else {
            mAddressLayout.setErrorEnabled(false);
            mAddress.setError(null);
        }

        String Gender = mGender.getSelectedItem().toString();
        if (TextUtils.isEmpty(Gender)) {
            valid = false;
        }

        String Contact = mContact.getText().toString();
        if (TextUtils.isEmpty(Contact)) {
            mContactNoLayout.setErrorEnabled(true);
            mContactNoLayout.setError("Required.");
            valid = false;
        } else {
            mContactNoLayout.setErrorEnabled(false);
            mContact.setError(null);
        }

        return valid;
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            String temp_filename = data.getData().getPath();
            if (temp_filename != null) {
                temp_filename = temp_filename.substring(temp_filename.lastIndexOf("/") + 1);
                imageName.setText(temp_filename);
            }
        }
    }

}
