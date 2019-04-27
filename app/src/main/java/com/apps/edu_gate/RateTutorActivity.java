package com.apps.edu_gate;

import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RateTutorActivity extends BaseActivity {

    private ArrayList<Tutorinfo> tutorList = new ArrayList<Tutorinfo>();
    private RecyclerView.Adapter mAdapter;
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            tutorList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
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
                    tutorList.add(tutor);
                }
                Toast.makeText(getBaseContext(), "Loaded Data", Toast.LENGTH_LONG).show();
                mAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getBaseContext(), "No Results Found", Toast.LENGTH_LONG).show();
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
        setContentView(R.layout.activity_rate_tutor);
        showProgressDialog();

        Query q = FirebaseDatabase.getInstance().getReference("Tutors");
        q.addListenerForSingleValueEvent(valueEventListener);
    }
}
