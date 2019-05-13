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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
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

    private ArrayList<Spinner> grade_spinners = new ArrayList<>();
    private ArrayList<Spinner> subject_spinners = new ArrayList<>();
    private ArrayList<ImageView> buttons_list = new ArrayList<>();
    private ArrayList<LinearLayout> layout_list = new ArrayList<>();

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri profileImageUri;
    private Uri transcriptImageUri;

    private String FirstName;
    private String LastName;
    private String CnicNo;
    private String Address;
    private String Gender;
    private String ContactNo;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    private String email;
    private EditText mInstitution;
    private EditText mTuitionLocation;
    private FirebaseAuth mAuth;
    private String grade_values;
    private String subject_values;
    private TextView imageName;
    private Spinner myEducationDegree;
    private String degreeString;
    private TextInputLayout mInstituteLayout;
    private TextInputLayout mLocationLayout;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_education);
        setTitle("Educational Details");
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

        mInstitution = findViewById(R.id.institution);
        mTuitionLocation = findViewById(R.id.tuition_location);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        Button imageButton = findViewById(R.id.image_button);
        imageName = findViewById(R.id.image_name);
        myEducationDegree = findViewById(R.id.my_education_dropdown);
        mInstituteLayout = findViewById(R.id.institution_layout);
        mLocationLayout = findViewById(R.id.location_layout);
        Button mSubmitButton = findViewById(R.id.submit_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSpinners();
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Tutors");
    }

    public void submitForm() {
        if (!validateForm()) {
            return;
        }

        degreeString = String.valueOf(myEducationDegree.getSelectedItem());
        if (!getSpinnerValues()) return;
        mAuth = FirebaseAuth.getInstance();
        addTutor();
    }


    private void addTutor() {
        showProgressDialog();
        String Institution = mInstitution.getText().toString();
        String Location = mTuitionLocation.getText().toString().toLowerCase();

        Tutor tutor = new Tutor(FirstName, LastName, email, CnicNo, Address, ContactNo,
                Gender, Institution, Location, grade_values, subject_values, degreeString);

        uploadFileProfile(profileImageUri, tutor);
    }


    private boolean validateForm() {
        boolean valid = true;

        if (transcriptImageUri == null) {
            Toast.makeText(getApplicationContext(), "Transcript image not added", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        String Institute = mInstitution.getText().toString();
        if (TextUtils.isEmpty(Institute)) {
            mInstituteLayout.setErrorEnabled(true);
            mInstituteLayout.setError("Required.");
            valid = false;
        } else {
            mInstituteLayout.setErrorEnabled(false);
            mInstitution.setError(null);
        }

        String Location = mTuitionLocation.getText().toString();
        if (TextUtils.isEmpty(Location)) {
            mLocationLayout.setErrorEnabled(true);
            mLocationLayout.setError("Required.");
            valid = false;
        } else {
            mLocationLayout.setErrorEnabled(false);
            mTuitionLocation.setError(null);
        }

        return valid;
    }

    private void addSpinners() {
        final LinearLayout dropdown_layout = findViewById(R.id.subjects_grades_layout);

        Spinner newSpinner = new Spinner(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.subjectOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        newSpinner.setAdapter(adapter);

        Spinner newSpinner1 = new Spinner(this);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this, R.array.gradeOptions, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        newSpinner1.setAdapter(adapter1);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        ImageView button = new ImageView(this);
        button.setBackground(getResources().getDrawable(R.drawable.baseline_cancel));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LinearLayout inv = findViewById(R.id.subjects_grades_layout);
                int index = buttons_list.indexOf(view);
                inv.removeView(layout_list.get(index));
                layout_list.remove(index);
                subject_spinners.remove(index);
                grade_spinners.remove(index);
                buttons_list.remove(index);
            }
        });

        LinearLayout temp_layout = new LinearLayout(this);
        layout_list.add(temp_layout);
        temp_layout.setOrientation(LinearLayout.HORIZONTAL);
        temp_layout.setBackground(getDrawable(R.drawable.background));
        temp_layout.setPadding(0, 8, 0, 8);
        params.setMargins(0, 8, 0, 8);
        temp_layout.setLayoutParams(params);

        temp_layout.addView(button);
        temp_layout.addView(newSpinner1);
        temp_layout.addView(newSpinner);
        grade_spinners.add(newSpinner1);
        subject_spinners.add(newSpinner);
        buttons_list.add(button);

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
            if (temp_filename != null) {
                temp_filename = temp_filename.substring(temp_filename.lastIndexOf("/") + 1);
                imageName.setText(temp_filename);
            }
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
