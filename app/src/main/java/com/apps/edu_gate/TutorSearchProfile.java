package com.apps.edu_gate;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TutorSearchProfile extends AppCompatActivity {

    Button forcall;
    TextView fname;
    TextView gender;
    TextView lastname;
    TextView instituion;
    TextView location;
    TextView grades;
    TextView subjects;
    TextView rating;
    ImageView imageTutor;

    View itemView;

    private RecyclerView recyclerView;
    private List<Admininfo> adminList = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Tutorinfo x = (Tutorinfo) getIntent().getSerializableExtra("result");
        setContentView(R.layout.activity_tutor_search_profile);
        fname = (TextView) findViewById(R.id.fname);
        fname.setText(x.firstName);
        gender = (TextView) findViewById(R.id.gender);
        gender.setText(x.gender);
        lastname = (TextView) findViewById(R.id.lname);
        lastname.setText(x.lastName);
        instituion = (TextView) findViewById(R.id.institution);
        instituion.setText(x.recentInstitution);
        location = (TextView) findViewById(R.id.location);
        location.setText(x.tutionLocation);
        forcall = findViewById(R.id.callbutton);
        Query q = FirebaseDatabase.getInstance().getReference("Admin")
                .orderByChild("Number");
        q.addListenerForSingleValueEvent(valueEventListener);


        forcall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Dialog dialog = new Dialog(TutorSearchProfile.this);
                dialog.setContentView(R.layout.dialogbox_call);
                recyclerView = dialog.findViewById(R.id.rv);
                recyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                adminList = new ArrayList<>();
                mAdapter = new CallerAdapter(getApplicationContext(), adminList);
                recyclerView.setAdapter(mAdapter);
                dialog.show();

            }
        });
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            adminList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Admininfo admin = snapshot.getValue(Admininfo.class);
                    adminList.add(admin);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
