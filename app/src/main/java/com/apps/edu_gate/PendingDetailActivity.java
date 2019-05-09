package com.apps.edu_gate;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.apache.commons.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

public class PendingDetailActivity extends AppCompatActivity {

    private List<String> lastList = new ArrayList<>();
    private TutorInfo x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x = (TutorInfo) getIntent().getSerializableExtra("result");
        setContentView(R.layout.activity_pending_detail);
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
        Button yes = findViewById(R.id.approve);
        Button no = findViewById(R.id.disapprove);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptProfile();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectProfile();
            }
        });

    }

    public void acceptProfile() {
        DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference("Tutors");
        dbNode.child(x.key).child("profileStatus").setValue(1);
        Toast.makeText(getBaseContext(), "Profile Verified", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void rejectProfile() {
        DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference("Tutors");
        dbNode.child(x.key).child("profileStatus").setValue(0);
        Toast.makeText(getBaseContext(), "Profile Declined", Toast.LENGTH_SHORT).show();
        finish();
    }
}
