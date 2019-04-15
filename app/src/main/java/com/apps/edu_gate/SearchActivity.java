package com.apps.edu_gate;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class SearchActivity extends BaseActivity {

    SearchView searchView;
    private Spinner spinner1;
    private Button searchButton;
    String searching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.searchBar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searching = query;
                if(searching != null && !searching.isEmpty()){
                    Toast.makeText(getBaseContext(),String.valueOf(spinner1.getSelectedItem())+ query, Toast.LENGTH_LONG).show();
                    Query q = FirebaseDatabase.getInstance().getReference("Tutors")
                            .orderByChild(String.valueOf(spinner1.getSelectedItem()))
                            .equalTo(searching);
                }
                else{
//                    Toast.makeText(getBaseContext(),"Please add item to search!", Toast.LENGTH_LONG).show();
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
        addListenerOnButton();

    }

    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searching != null && !searching.isEmpty()){
                    Toast.makeText(SearchActivity.this,String.valueOf(spinner1.getSelectedItem())+ searching, Toast.LENGTH_SHORT).show();
                    Query q = FirebaseDatabase.getInstance().getReference("Tutors")
                            .orderByChild(String.valueOf(spinner1.getSelectedItem()))
                            .equalTo(searching);
                }
                else{
                    Toast.makeText(SearchActivity.this,"Please add item to search!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
