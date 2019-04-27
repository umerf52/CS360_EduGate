package com.apps.edu_gate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RateDetailActivity extends BaseActivity {

    private List<Double> ratings = new ArrayList<>();
    ListView listView = null;
    CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tutorinfo x = (Tutorinfo) getIntent().getSerializableExtra("result");
        ratings = x.rating;
        setContentView(R.layout.activity_rate_detail);

        listView = (ListView) findViewById(R.id.listView1);
        adapter = new CustomAdapter(this);
        listView.setAdapter(adapter);
    }

    class CustomAdapter extends BaseAdapter {
        public CustomAdapter(Context convertView) {

        }
        @Override
        public int getCount() {

            return 0;
        }

        @Override
        public Object getItem(int position) {

            return null;
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater
                    .from(getApplicationContext());
            View view = inflater.inflate(R.layout.list3, null);
            final Button btn1 = (Button) view.findViewById(R.id.button1);
            final EditText edt = (EditText) view.findViewById(R.id.txt_item);
            TextView btnEdit = (TextView) view.findViewById(R.id.editrate);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    btn1.setVisibility(View.VISIBLE);
                    edt.setEnabled(true);
                }
            });
            btnEdit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(RateDetailActivity.this,"clicked!",Toast.LENGTH_LONG).show();
                }
            });

            return view;
        }

    }


}
