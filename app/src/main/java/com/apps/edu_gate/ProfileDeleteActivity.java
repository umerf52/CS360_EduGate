package com.apps.edu_gate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class ProfileDeleteActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private List<Tutorinfo> tutorList;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            tutorList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Tutorinfo tutor = new Tutorinfo();
                    tutor.firstName = snapshot.child("firstName").getValue(String.class);
                    tutor.lastName = snapshot.child("lastName").getValue(String.class);
                    tutor.profileImage = snapshot.child("profileImage").getValue(String.class);
                    tutor.key = snapshot.child("key").getValue(String.class);
                    tutorList.add(tutor);
                }
                mAdapter.notifyDataSetChanged();
            }
            else{
                Toast.makeText(getBaseContext(),"Database is empty!", Toast.LENGTH_LONG).show();
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
        setContentView(R.layout.activity_profile_delete);
        setTitle("Delete Tutor Profile");
        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        tutorList = new ArrayList<>();
        mAdapter = new delAdapter(this, tutorList);
        recyclerView.setAdapter(mAdapter);

        Query q = getInstance().getReference("Tutors");
        q.addListenerForSingleValueEvent(valueEventListener);
    }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    Toast.makeText(getBaseContext(),"Yes", Toast.LENGTH_LONG).show();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    Toast.makeText(getBaseContext(),"No", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        ((delAdapter) mAdapter).setOnItemClickListener(new delAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                final Tutorinfo x = tutorList.get(position);
                Toast.makeText(getBaseContext(),"Long Pressed!", Toast.LENGTH_LONG).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Do you want to delete this profile?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(getBaseContext(),x.key, Toast.LENGTH_LONG).show();
                        DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference("Tutors");
                        dbNode.child(x.key).removeValue();
                    }
                }).setNegativeButton("No", dialogClickListener).show();
            }
        });
    }
}
