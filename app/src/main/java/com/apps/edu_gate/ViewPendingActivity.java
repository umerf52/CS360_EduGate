package com.apps.edu_gate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewPendingActivity extends BaseActivity {

    private List<TutorInfo> tutorList;
    private RecyclerView.Adapter mAdapter;

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            tutorList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int status = snapshot.child("profileStatus").getValue(int.class);
                    if (status==-1){
                        TutorInfo tutor = new TutorInfo();
                        tutor.firstName = snapshot.child("firstName").getValue(String.class);
                        tutor.lastName = snapshot.child("lastName").getValue(String.class);
                        tutor.rating = (ArrayList<Double>) snapshot.child("rating").getValue();
                        tutor.profileImage = snapshot.child("profileImage").getValue(String.class);
                        tutor.key = snapshot.child("key").getValue(String.class);
                        tutor.profileStatus = status;
                        tutor.cnicNo = snapshot.child("cnicNo").getValue(String.class);
                        tutor.contactNo = snapshot.child("contactNo").getValue(String.class);
                        tutor.subject = snapshot.child("subject").getValue(String.class);
                        tutor.grade = snapshot.child("grade").getValue(String.class);
                        tutor.recentInstitution = snapshot.child("recentInstitution").getValue(String.class);
                        tutor.tuitionLocation = snapshot.child("tuitionLocation").getValue(String.class);
                        tutor.address = snapshot.child("address").getValue(String.class);
                        tutor.transcriptImage = snapshot.child("transcriptImage").getValue(String.class);
                        tutorList.add(tutor);
                    }
                }
                mAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getBaseContext(), "No profiles pending!", Toast.LENGTH_LONG).show();
            }
            hideProgressDialog();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            hideProgressDialog();
            Toast.makeText(getBaseContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pending);
        setTitle("Pending Profiles");
        showProgressDialog();
        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        tutorList = new ArrayList<>();
        mAdapter = new PendingAdapter(this, tutorList);
        recyclerView.setAdapter(mAdapter);
        Query q = FirebaseDatabase.getInstance().getReference("Tutors");
        q.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((PendingAdapter) mAdapter).setOnItemClickListener(new PendingAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                TutorInfo x = tutorList.get(position);
                Intent myIntent = new Intent(ViewPendingActivity.this, PendingDetailActivity.class);
                myIntent.putExtra("result",x);
                ViewPendingActivity.this.startActivity(myIntent);
            }
        });
    }


}
