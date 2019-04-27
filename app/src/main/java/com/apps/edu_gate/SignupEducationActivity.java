package com.apps.edu_gate;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Iterator;

public class SignupEducationActivity extends BaseActivity {

    ArrayList<Spinner> grade_spinners = new ArrayList<Spinner>();
    ArrayList<Spinner> subject_spinners = new ArrayList<Spinner>();
//    ArrayList<String> grade_values = new ArrayList<String>();
//    ArrayList<String> subject_values = new ArrayList<String>();

    private static final int PICK_IMAGE_REQUEST = 1;
    Uri profileImageUri;
    Uri transcriptImageUri;

    String FirstName;
    String LastName;
    String CnicNo;
    String Address;
    String Gender;
    String ContactNo;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    private String email;
    private EditText mInstitution;
    private EditText mTuitionLocation;
    private FirebaseAuth mAuth;
    private String grade_values;
    private String subject_values;
    private TextView imageName;

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
        String profileImageString = prevIntent.getStringExtra("myImageUriString");
        profileImageUri = Uri.parse(profileImageString);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        mInstitution = (EditText) findViewById(R.id.institution);
        mTuitionLocation = (EditText) findViewById(R.id.tuition_location);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        Button imageButton = findViewById(R.id.image_button);
        imageName = findViewById(R.id.image_name);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Tutors");
    }

    public void onClick(View v) {
        if (!validateForm()) {
            return;
        }

        int i = v.getId();
        if (i == R.id.submit_button) {
            if (!getSpinnerValues()) return;
            mAuth = FirebaseAuth.getInstance();
            addTutor();
        } else if (i == R.id.fab) {
            addSpinners();
        }
    }


    private void addTutor() {
        showProgressDialog();
        String Institution = mInstitution.getText().toString();
        String Location = mTuitionLocation.getText().toString().toLowerCase();

        Tutor tutor = new Tutor(FirstName, LastName, email, CnicNo, Address, ContactNo,
                Gender, Institution, Location, grade_values, subject_values);

        uploadFileProfile(profileImageUri, tutor);
    }


    private boolean validateForm() {
        boolean valid = true;

        if (transcriptImageUri == null) {
            valid = false;
        }

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
        subject_values = "";
        grade_values = "";
        Iterator i = grade_spinners.iterator();
        while (i.hasNext()) {
            Spinner tmp = (Spinner) i.next();
            String val = String.valueOf(tmp.getSelectedItem());
            if (val.equals("Grade")) {
                Toast.makeText(this, "Invalid value for Grade", Toast.LENGTH_SHORT).show();
                grade_values = "";
                return false;
            }
            grade_values += ((String.valueOf(tmp.getSelectedItem()).toLowerCase()) + "-");
        }

        Iterator j = subject_spinners.iterator();
        while (j.hasNext()) {
            Spinner tmp = (Spinner) j.next();
            String val = String.valueOf(tmp.getSelectedItem());
            if (val.equals("Subject")) {
                Toast.makeText(this, "Invalid value for Subject", Toast.LENGTH_SHORT).show();
                subject_values = "";
                return false;
            }
            subject_values += ((String.valueOf(tmp.getSelectedItem()).toLowerCase()) + "-");
        }

        return true;
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
            transcriptImageUri = data.getData();

            String temp_filename = data.getData().getPath();
            temp_filename = temp_filename.substring(temp_filename.lastIndexOf("/") + 1);
            imageName.setText(temp_filename);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFileProfile(final Uri fileUri, final Tutor tutor) {
        if (fileUri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(fileUri));

            mUploadTask = fileReference.putFile(fileUri);

            Task<Uri> urlTask = mUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        tutor.setProfileImage(downloadUri.toString());

                        uploadFileTranscript(transcriptImageUri, tutor);
                    } else {
                        hideProgressDialog();
                        Toast.makeText(SignupEducationActivity.this, "Error uploading Profile Image",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void uploadFileTranscript(final Uri fileUri, final Tutor tutor) {
        if (fileUri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(fileUri));

            mUploadTask = fileReference.putFile(fileUri);

            Task<Uri> urlTask = mUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        tutor.setTranscriptImage(downloadUri.toString());

                        String key = databaseReference.push().getKey();
                        tutor.setKey(key);
                        databaseReference.child(key).setValue(tutor);

                        hideProgressDialog();
                        Toast.makeText(SignupEducationActivity.this, "Successfully added to database",
                                Toast.LENGTH_LONG).show();
                        mAuth.signOut();
                        Intent myIntent = new Intent(SignupEducationActivity.this, LoginActivity.class);
                        SignupEducationActivity.this.startActivity(myIntent);
                    } else {
                        hideProgressDialog();
                        Toast.makeText(SignupEducationActivity.this, "Error uploading Transcript Image",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
