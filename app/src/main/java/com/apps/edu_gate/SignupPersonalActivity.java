package com.apps.edu_gate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignupPersonalActivity extends BaseActivity {

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mCnicNumber;
    private EditText mAddress;
    private Spinner mGender;
    private EditText mContact;
    private String email;

    private Button mNextButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button imageButton;
    private TextView imageName;
    private Uri mImageUri;

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
        imageButton = findViewById(R.id.image_button);
        imageName = findViewById(R.id.image_name);

        mNextButton = findViewById(R.id.submit_button);


        mContact.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
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
            myIntent.putExtra("myImageUriString", mImageUri.toString());
            SignupPersonalActivity.this.startActivity(myIntent);
        }
    }


    private boolean validateForm() {
        boolean valid = true;

        if (mImageUri == null) {
            valid = false;
        }

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
            temp_filename = temp_filename.substring(temp_filename.lastIndexOf("/") + 1);
            imageName.setText(temp_filename);
        }
    }

}
