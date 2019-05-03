package com.apps.edu_gate;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RateDetailActivity extends BaseActivity {

    private ArrayList<Double> ratings = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    String mykey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_detail);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        TutorInfo x = (TutorInfo) getIntent().getSerializableExtra("result");
        setTitle((x.firstName.substring(0, 1).toUpperCase() + x.firstName.substring(1)) + " " +
                x.lastName.substring(0, 1).toUpperCase() + x.lastName.substring(1));
        ratings = x.rating;
        mykey = x.key;


        mAdapter = new RateDetailAdapter(this, ratings, x.key);
        recyclerView.setAdapter(mAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText edittext = new EditText(getBaseContext());
                edittext.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
                final AlertDialog.Builder alert = new AlertDialog.Builder(RateDetailActivity.this);
                alert.setMessage("Add a number between 1 and 5 inclusive");
                alert.setTitle("Add New Rating");
                alert.setView(edittext);
                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String YouEditTextValue = edittext.getText().toString();
                        if(YouEditTextValue.length()>0){
                            double p = Double.parseDouble(YouEditTextValue);
                            if(p<=5){
                                ratings.add(p);
                                DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference("Tutors");
                                dbNode.child(mykey).child("rating").setValue(ratings);
                                Toast.makeText(getBaseContext(), "Rating Added", Toast.LENGTH_SHORT).show();
                                updateUI();
                            }
                            else{
                                Toast.makeText(getBaseContext(), "Invalid Entry!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getBaseContext(), "Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alert.show();
            }
        });
    }

    private void updateUI() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((RateDetailAdapter) mAdapter).setOnItemClickListener(new RateDetailAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
//                Toast.makeText(getBaseContext(),"Clicked!", Toast.LENGTH_LONG).show();
            }
        });
    }

}
