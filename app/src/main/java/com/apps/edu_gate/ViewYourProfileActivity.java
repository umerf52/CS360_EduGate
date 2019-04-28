package com.apps.edu_gate;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.apache.commons.text.WordUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ViewYourProfileActivity extends BaseActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_your_profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_password: {
                Intent myIntent = new Intent(ViewYourProfileActivity.this, ChangePasswordActivity.class);
                ViewYourProfileActivity.this.startActivity(myIntent);
                break;
            }
            case R.id.action_sign_out: {
                FirebaseAuth.getInstance().signOut();
                Intent myIntent = new Intent(ViewYourProfileActivity.this, LoginActivity.class);
                ViewYourProfileActivity.this.startActivity(myIntent);
                break;
            }
        }
        return true;
    }

    EditText fname;
    EditText lname;
    TextView gender;
    private ImageView profileImageView;
    private EditText contactNumber;
    private EditText tuitionLocation;
    private Spinner myEducationDropdown;
    private List<String> subjectsTur = new ArrayList<>();
    private List<String> gradesTur = new ArrayList<>();
    private String new_subject_values = "";
    private String new_grade_values = "";
    private String tutorKey = "";
    String id;
    Tutorinfo tutor;
    FirebaseUser currentFirebaseUser;
    FirebaseAuth AuthUI = FirebaseAuth.getInstance();

    private ArrayList<Spinner> grade_spinners = new ArrayList<Spinner>();
    private ArrayList<Spinner> subject_spinners = new ArrayList<Spinner>();

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int status = snapshot.child("profileStatus").getValue(int.class);
                    Tutorinfo tutor = new Tutorinfo();
                    tutor.grade = snapshot.child("grade").getValue(String.class);
                    tutor.degree = snapshot.child("degree").getValue(String.class);
                    tutor.subject = snapshot.child("subject").getValue(String.class);
                    tutor.address = snapshot.child("address").getValue(String.class);
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
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .fit()
                            .centerCrop()
                            .into(profileImageView);
                    fname.setText(WordUtils.capitalizeFully(tutor.firstName));
                    lname.setText(WordUtils.capitalizeFully(tutor.lastName));
                    gender.setText(WordUtils.capitalizeFully(tutor.gender));
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
            }
            else{
                Toast.makeText(getBaseContext(),"No such results!", Toast.LENGTH_LONG).show();
            }
            hideProgressDialog();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(getBaseContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_your_profile);
        setTitle("Your Profile");
        showProgressDialog();
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        gender = (TextView) findViewById(R.id.gender);
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
        currentFirebaseUser = AuthUI.getCurrentUser();
        String s = currentFirebaseUser.getEmail();
        Query q = FirebaseDatabase.getInstance().getReference("Tutors").orderByChild("emailAddress")
                .equalTo(s);
        q.addListenerForSingleValueEvent(valueEventListener);

        }

    private void addSpinners() {
        LinearLayout dropdown_layout = (LinearLayout) findViewById(R.id.subjects_grades_layout);

        Spinner newSpinner = new Spinner(ViewYourProfileActivity.this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                ViewYourProfileActivity.this, R.array.subjectOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        newSpinner.setAdapter(adapter);

        Spinner newSpinner1 = new Spinner(ViewYourProfileActivity.this);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                ViewYourProfileActivity.this, R.array.gradeOptions, android.R.layout.simple_spinner_item);
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

    private void updateTutor() {
        showProgressDialog();
        String contactNew = contactNumber.getText().toString();
        String locationNew = tuitionLocation.getText().toString();
        String degreeNew = String.valueOf(myEducationDropdown.getSelectedItem());
        getSpinnerValues();
        DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference("Tutors");
        dbNode.child(tutorKey).child("contactNo").setValue(contactNew);
        dbNode.child(tutorKey).child("degree").setValue(degreeNew);
        dbNode.child(tutorKey).child("tuitionLocation").setValue(locationNew);
        dbNode.child(tutorKey).child("subject").setValue(new_subject_values);
        dbNode.child(tutorKey).child("grade").setValue(new_grade_values);
        hideProgressDialog();
    }

    private boolean getSpinnerValues() {
        new_subject_values = "";
        new_grade_values = "";
        Iterator i = grade_spinners.iterator();
        while (i.hasNext()) {
            Spinner tmp = (Spinner) i.next();
            String val = String.valueOf(tmp.getSelectedItem());
            if (val.equals("Grade")) {
                continue;
            }
            new_grade_values += ((String.valueOf(tmp.getSelectedItem()).toLowerCase()) + "-");
        }

        Iterator j = subject_spinners.iterator();
        while (j.hasNext()) {
            Spinner tmp = (Spinner) j.next();
            String val = String.valueOf(tmp.getSelectedItem());
            if (val.equals("Subject")) {
                continue;
            }
            new_subject_values += ((String.valueOf(tmp.getSelectedItem()).toLowerCase()) + "-");
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
