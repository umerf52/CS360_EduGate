package com.apps.edu_gate;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SearchPageActivity extends BaseActivity {

    SearchView searchView;
    private Spinner spinner1;
    String searching;
    private RecyclerView recyclerView;
//    private TutorAdapter adapter;
    private List<Tutorinfo> tutorList;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
//    DatabaseReference dbTutors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_page);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tutorList = new ArrayList<>();
        mAdapter = new TutorAdapter(this, tutorList);
        recyclerView.setAdapter(mAdapter);

//        dbTutors = FirebaseDatabase.getInstance().getReference("Tutors");
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        searchView = findViewById(R.id.searchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searching = query;
                if(searching != null && !searching.isEmpty()){
                    String s = String.valueOf(spinner1.getSelectedItem());
                    Toast.makeText(getBaseContext(),String.valueOf(spinner1.getSelectedItem())+ query, Toast.LENGTH_LONG).show();
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
                //Toast.makeText(getBaseContext(), String.valueOf(spinner1.getSelectedItem())+ newText, Toast.LENGTH_LONG).show();
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
                Toast.makeText(getBaseContext(),"CLicked"+x.Address, Toast.LENGTH_LONG).show();
//                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
    }

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
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
