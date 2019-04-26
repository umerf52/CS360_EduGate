package com.apps.edu_gate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Spinner;
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


public class SearchPageActivity extends BaseActivity {

    private static final String TAG = "SearchPageActivity";

    SearchView searchView;
    private Spinner spinner1;
    String searching;
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
                    Tutorinfo tutor = snapshot.getValue(Tutorinfo.class);
                    tutorList.add(tutor);
                }
                mAdapter.notifyDataSetChanged();
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

        setContentView(R.layout.activity_search_page);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        tutorList = new ArrayList<>();
        mAdapter = new TutorAdapter(this, tutorList);
        recyclerView.setAdapter(mAdapter);

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        searchView = findViewById(R.id.searchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searching = query;
                if(searching != null && !searching.isEmpty()){
                    showProgressDialog();
                    String s = String.valueOf(spinner1.getSelectedItem());
                    s = s.toLowerCase();
                    if(s.equals("name")){
                        s = "firstName";
                    }
                    if(s.equals("address")){
                        s = "tuitionLocation";
                    }
                    if(s.equals("class")){
                        s = "grade";
                    }
                    Toast.makeText(getBaseContext(), s+ query, Toast.LENGTH_LONG).show();
                    Query q = FirebaseDatabase.getInstance().getReference("Tutors")
                            .orderByChild(s)
                            .equalTo(searching);
                    q.addListenerForSingleValueEvent(valueEventListener);
                }
                else{
                    Toast.makeText(getBaseContext(),"Please add item to search!", Toast.LENGTH_LONG).show();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searching = newText;
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((TutorAdapter) mAdapter).setOnItemClickListener(new TutorAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Tutorinfo x = tutorList.get(position);
                Intent myIntent = new Intent(SearchPageActivity.this, TutorSearchProfile.class);
                myIntent.putExtra("result",x);
                Log.d(TAG, "I'm here");
                SearchPageActivity.this.startActivity(myIntent);
            }
        });
    }
}
