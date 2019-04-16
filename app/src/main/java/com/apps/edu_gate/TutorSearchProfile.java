package com.apps.edu_gate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TutorSearchProfile extends AppCompatActivity {


    Button forcall;
    TextView fname;
    TextView address;
    TextView ins;
    View itemView;

    private RecyclerView recyclerView;
    private List<Admininfo> adminList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Tutorinfo x = (Tutorinfo) getIntent().getSerializableExtra("result");
        setContentView(R.layout.activity_tutor_search_profile);
        fname = (TextView) itemView.findViewById(R.id.person_fname);
        fname.setText(x.Name);
        address = (TextView) itemView.findViewById(R.id.person_address);
        address.setText(x.Address);
        ins = (TextView) itemView.findViewById(R.id.person_ins);
        ins.setText(x.Institution);
        forcall = findViewById(R.id.callbutton);
        Query q = FirebaseDatabase.getInstance().getReference("Admin")
                .orderByChild("Number");
        q.addListenerForSingleValueEvent(valueEventListener);


        forcall.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Dialog dialog = new Dialog(TutorSearchProfile.this);
                dialog.setContentView(R.layout.dialogbox_call);
                recyclerView = findViewById(R.id.rv);
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
