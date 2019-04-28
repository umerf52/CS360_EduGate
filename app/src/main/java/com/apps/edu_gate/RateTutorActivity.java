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

public class RateTutorActivity extends BaseActivity {


    private List<Tutorinfo> tutorList;
    private RecyclerView.Adapter mAdapter;

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            tutorList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Tutorinfo tutor = new Tutorinfo();
                    tutor.firstName = snapshot.child("firstName").getValue(String.class);
                    tutor.lastName = snapshot.child("lastName").getValue(String.class);
                    tutor.rating = (ArrayList<Double>) snapshot.child("rating").getValue();
                    tutor.profileImage = snapshot.child("profileImage").getValue(String.class);
                    tutor.key = snapshot.child("key").getValue(String.class);
                    tutorList.add(tutor);
                }
                mAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getBaseContext(), "No such results!", Toast.LENGTH_LONG).show();
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
        setContentView(R.layout.activity_rate_main);
        setTitle("Rate Tutors");
        showProgressDialog();
        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        tutorList = new ArrayList<>();
        mAdapter = new rateAdapter(this, tutorList);
        recyclerView.setAdapter(mAdapter);

        Query q = FirebaseDatabase.getInstance().getReference("Tutors");
        q.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((rateAdapter) mAdapter).setOnItemClickListener(new rateAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Tutorinfo x = tutorList.get(position);
                Intent myIntent = new Intent(RateTutorActivity.this, RateDetailActivity.class);
                myIntent.putExtra("result", x);

                RateTutorActivity.this.startActivity(myIntent);
            }
        });
    }
}
