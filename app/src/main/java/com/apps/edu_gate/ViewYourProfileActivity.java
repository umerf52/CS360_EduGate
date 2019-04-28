package com.apps.edu_gate;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.apache.commons.text.WordUtils;

import java.util.ArrayList;

public class ViewYourProfileActivity extends BaseActivity {

    EditText fname;
    EditText lname;
    TextView gender;
    ImageView profileImageView;
    EditText contactNumber;
    EditText tuitionLocation;
    String id;
    Tutorinfo tutor;
    FirebaseUser currentFirebaseUser;
    FirebaseAuth AuthUI = FirebaseAuth.getInstance();

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int status = snapshot.child("profileStatus").getValue(int.class);
                        Tutorinfo tutor = new Tutorinfo();
                        tutor.grade = snapshot.child("grade").getValue(String.class);
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
                }
            }
            else{
                Toast.makeText(getBaseContext(),"No such results!", Toast.LENGTH_LONG).show();
            }
            hideProgressDialog();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_your_profile);
        showProgressDialog();
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        gender = (TextView) findViewById(R.id.gender);
            profileImageView = findViewById(R.id.profile_picture);
            contactNumber = findViewById(R.id.contact_number);
            tuitionLocation = findViewById(R.id.tuition_location);
        currentFirebaseUser = AuthUI.getCurrentUser();
        String s = currentFirebaseUser.getEmail();
        Log.e("ssss",s );
        Query q = FirebaseDatabase.getInstance().getReference("Tutors").orderByChild("emailAddress")
                .equalTo(s);
        q.addListenerForSingleValueEvent(valueEventListener);
        }

}
