package com.apps.edu_gate;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RateDetailActivity extends BaseActivity {

    private List<Double> ratings = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_detail);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        Tutorinfo x = (Tutorinfo) getIntent().getSerializableExtra("result");
        setTitle((x.firstName.substring(0, 1).toUpperCase() + x.firstName.substring(1)) + " " +
                x.lastName.substring(0, 1).toUpperCase() + x.firstName.substring(1));
        ratings = x.rating;

        mAdapter = new RateDetailAdapter(this, ratings);
        recyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
    }


}
