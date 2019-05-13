package com.apps.edu_gate;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.apache.commons.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

public class ViewYourProfileActivity extends BaseActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private static final String TAG = "ViewYourProfileActivity";
    private long mBackPressed;
    private TextView first_name;
    private TextView last_name;
    private TextView gender;
    private ImageView profileImageView;
    private TextView profileStatus;
    private EditText contactNumber;
    private EditText tuitionLocation;
    private Spinner myEducationDropdown;
    private List<String> subjectsTur = new ArrayList<>();
    private List<String> gradesTur = new ArrayList<>();
    private String new_subject_values = "";
    private String new_grade_values = "";
    private String tutorKey = "";
    private FirebaseAuth AuthUI = FirebaseAuth.getInstance();
    private boolean hasProfilePictureChanged = false;
    private Uri mImageUri;
    private TutorInfo tutor;
    private ArrayList<Spinner> grade_spinners = new ArrayList<>();
    private ArrayList<Spinner> subject_spinners = new ArrayList<>();
    private ArrayList<ImageView> buttons_list = new ArrayList<>();
    private ArrayList<LinearLayout> layout_list = new ArrayList<>();
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    tutor = new TutorInfo();
                    tutor.grade = snapshot.child("grade").getValue(String.class);
                    tutor.degree = snapshot.child("degree").getValue(String.class);
                    tutor.subject = snapshot.child("subject").getValue(String.class);
                    tutor.address = snapshot.child("address").getValue(String.class);
                    tutor.profileStatus = snapshot.child("profileStatus").getValue(int.class);
                    tutor.cnicNo = snapshot.child("cnicNo").getValue(String.class);
                    tutor.contactNo = snapshot.child("contactNo").getValue(String.class);
                    tutor.emailAddress = snapshot.child("emailAddress").getValue(String.class);
                    tutor.firstName = snapshot.child("firstName").getValue(String.class);
                    tutor.gender = snapshot.child("gender").getValue(String.class);
                    tutor.lastName = snapshot.child("lastName").getValue(String.class);
                    tutor.recentInstitution = snapshot.child("recentInstitution").getValue(String.class);
                    tutor.tuitionLocation = snapshot.child("tuitionLocation").getValue(String.class);
                    tutor.rating = (ArrayList<Double>) snapshot.child("rating").getValue();
                    tutor.profileImage = snapshot.child("profileImage").getValue(String.class);
                    tutorKey = snapshot.child("key").getValue(String.class);
                    Picasso.get()
                            .load(tutor.getProfileImage())
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .networkPolicy(NetworkPolicy.NO_CACHE)
                            .placeholder(R.drawable.placeholder_profile_picture)
                            .fit()
                            .centerCrop()
                            .into(profileImageView);
                    first_name.setText(WordUtils.capitalizeFully(tutor.firstName));
                    last_name.setText(WordUtils.capitalizeFully(tutor.lastName));
                    gender.setText(WordUtils.capitalizeFully(tutor.gender));
                    if (tutor.profileStatus == 0) {
                        profileStatus.setText("Rejected");
                        profileStatus.setTextColor(Color.parseColor("#ff0000"));
                    } else if (tutor.profileStatus == 1) {
                        profileStatus.setText("Verified");
                        profileStatus.setTextColor(Color.parseColor("#00ff00"));
                    } else if (tutor.profileStatus == -1) {
                        profileStatus.setText("Pending");
                        profileStatus.setTextColor(Color.parseColor("#8b4513"));
                    }
                    contactNumber.setText(tutor.getContactNo());
                    tuitionLocation.setText(WordUtils.capitalizeFully(tutor.getTuitionLocation()));
                    myEducationDropdown.setSelection(((ArrayAdapter) myEducationDropdown.getAdapter()).getPosition(tutor.getDegree()));

                    String sub = tutor.subject;
                    String grad = tutor.grade;
                    String[] splited = sub.split("-");
                    String[] split2 = grad.split("-");
                    for (int i = 0; i < splited.length; i++) {
                        subjectsTur.add(WordUtils.capitalizeFully(splited[i]));
                        gradesTur.add(WordUtils.capitalizeFully(split2[i]));
                        addSpinners();
                        grade_spinners.get(i).setSelection(((ArrayAdapter) grade_spinners.get(i).getAdapter()).getPosition(gradesTur.get(i)));
                        subject_spinners.get(i).setSelection(((ArrayAdapter) subject_spinners.get(i).getAdapter()).getPosition(subjectsTur.get(i)));
                    }

                }
            } else {
                Toast.makeText(getBaseContext(), "No such results!", Toast.LENGTH_LONG).show();
            }
            hideProgressDialog();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(getBaseContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_your_profile_menu, menu);
        return true;
    }

    private FirebaseStorage mStorage;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_password: {
                Intent myIntent = new Intent(ViewYourProfileActivity.this, ChangePasswordActivity.class);
                ViewYourProfileActivity.this.startActivity(myIntent);
                break;
            }
            case R.id.action_sign_out: {
                signOut();
                break;
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_your_profile);
        setTitle("Your Profile");
        showProgressDialog();
        first_name = findViewById(R.id.fname);
        profileStatus = findViewById(R.id.status);
        last_name = findViewById(R.id.lname);
        gender = findViewById(R.id.gender);
        profileImageView = findViewById(R.id.profile_picture);
        contactNumber = findViewById(R.id.contact_number);
        tuitionLocation = findViewById(R.id.tuition_location);
        myEducationDropdown = findViewById(R.id.my_education_dropdown);
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateTutor();
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addSpinners();
            }
        });
        Button changeImageButton = findViewById(R.id.change_profile_picture);
        changeImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openFileChooser();
            }
        });
        FirebaseUser currentFirebaseUser = AuthUI.getCurrentUser();
        if (currentFirebaseUser != null) {
            String s = currentFirebaseUser.getEmail();
            Query q = FirebaseDatabase.getInstance().getReference("Tutors").orderByChild("emailAddress")
                    .equalTo(s);
            q.addListenerForSingleValueEvent(valueEventListener);
        } else {
            Toast.makeText(getApplicationContext(), "Firebase user is null", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Firebase user is null");
        }

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

    private void updateTutor() {
        showProgressDialog();
        if (hasProfilePictureChanged) {
            StorageReference mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
            final DatabaseReference tempDbNode = FirebaseDatabase.getInstance().getReference("Tutors");
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            StorageTask mUploadTask = fileReference.putFile(mImageUri);
            ;
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
                        final Uri downloadUri = task.getResult();
                        mStorage = FirebaseStorage.getInstance();
                        StorageReference imageRef = mStorage.getReferenceFromUrl(tutor.getProfileImage());
                        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ViewYourProfileActivity.this, "Old Profile picture deleted",
                                        Toast.LENGTH_SHORT).show();
                                tempDbNode.child(tutorKey).child("profileImage").setValue(downloadUri.toString());
                                Toast.makeText(ViewYourProfileActivity.this, "Profile Picture Updated", Toast.LENGTH_SHORT);
                                updateDatabase();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ViewYourProfileActivity.this, "Failed to delete old profile picture",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        hideProgressDialog();
                        Toast.makeText(ViewYourProfileActivity.this, "Error uploading Profile Image",
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            updateDatabase();
        }
    }

    private void getSpinnerValues() {
        new_subject_values = "";
        new_grade_values = "";
        for (Spinner tmp : grade_spinners) {
            String val = String.valueOf(tmp.getSelectedItem());
            if (val.equals("Grade")) {
                continue;
            }
            new_grade_values += ((String.valueOf(tmp.getSelectedItem()).toLowerCase()) + "-");
        }

        for (Spinner tmp : subject_spinners) {
            String val = String.valueOf(tmp.getSelectedItem());
            if (val.equals("Subject")) {
                continue;
            }
            new_subject_values += ((String.valueOf(tmp.getSelectedItem()).toLowerCase()) + "-");
        }

    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else
            Toast.makeText(getBaseContext(), "Press back again in order to exit", Toast.LENGTH_SHORT).show();

        mBackPressed = System.currentTimeMillis();
    }

    private void signOut() {
        AlertDialog alertDialog = new AlertDialog.Builder(ViewYourProfileActivity.this).create();
        alertDialog.setTitle("Sign Out?");
        alertDialog.setMessage("Are you sure you want to sign out?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        alertDialog.show();

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
            Picasso.get()
                    .load(mImageUri)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.placeholder_profile_picture)
                    .fit()
                    .centerCrop()
                    .into(profileImageView);
            hasProfilePictureChanged = true;
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void updateDatabase() {
        DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference("Tutors");
        String contactNew = contactNumber.getText().toString();
        String locationNew = tuitionLocation.getText().toString();
        String degreeNew = String.valueOf(myEducationDropdown.getSelectedItem());
        getSpinnerValues();
        dbNode.child(tutorKey).child("contactNo").setValue(contactNew);
        dbNode.child(tutorKey).child("degree").setValue(degreeNew);
        dbNode.child(tutorKey).child("tuitionLocation").setValue(locationNew);
        dbNode.child(tutorKey).child("subject").setValue(new_subject_values);
        dbNode.child(tutorKey).child("grade").setValue(new_grade_values);
        dbNode.child(tutorKey).child("profileStatus").setValue(-1);
        hideProgressDialog();
        Toast.makeText(this, "Database updated", Toast.LENGTH_SHORT).show();
        recreate();
    }

}
