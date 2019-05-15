package com.apps.edu_gate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.squareup.picasso.Picasso;

import org.apache.commons.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchTutorAdmin extends BaseActivity {

    private TutorInfo x;
    private List<String> lastList = new ArrayList<>();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        super.onCreate(savedInstanceState);
        x = (TutorInfo) getIntent().getSerializableExtra("result");
        setContentView(R.layout.activity_search_tutor_admin);
        setTitle("Profile");
        String sub = x.subject;
        String grad = x.grade;
        String[] splited = sub.split("-");
        String[] split2 = grad.split("-");
        for(int i=0; i<splited.length; i++){
            String f = WordUtils.capitalizeFully(split2[i]) + ": " + WordUtils.capitalizeFully(splited[i]);
            lastList.add(f);
        }
        ImageView prof = findViewById(R.id.imageProfile);
        ImageView trans = findViewById(R.id.imageTranscript);
        Picasso.get()
                .load(x.getProfileImage())
                .placeholder(R.drawable.placeholder_profile_picture)
                .fit()
                .centerCrop()
                .into(prof);
        Picasso.get()
                .load(x.getTranscriptImage())
                .placeholder(R.drawable.placeholder_profile_picture)
                .fit()
                .centerCrop()
                .into(trans);
        TextView fname = findViewById(R.id.fname);
        fname.setText(WordUtils.capitalizeFully(x.firstName));
        TextView lname = findViewById(R.id.lname);
        lname.setText(WordUtils.capitalizeFully(x.lastName));
        TextView cnic = findViewById(R.id.cnicNo);
        cnic.setText(x.cnicNo);
        TextView contact = findViewById(R.id.contactNo);
        contact.setText(x.contactNo);
        TextView address = findViewById(R.id.address);
        address.setText(WordUtils.capitalizeFully(x.address));
        TextView location = findViewById(R.id.location);
        location.setText(WordUtils.capitalizeFully(x.tuitionLocation));
        TextView institution = findViewById(R.id.institution);
        institution.setText(x.recentInstitution);
        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.list_item_3, R.id.txtitem, lastList);
        ListView listView = findViewById(R.id.sub_list);
        listView.setAdapter(adapter);

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("tel:" + x.contactNo);
                Intent myIntent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(myIntent);
            }
        });
    }
}
