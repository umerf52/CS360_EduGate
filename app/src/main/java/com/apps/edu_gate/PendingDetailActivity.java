package com.apps.edu_gate;

import android.os.Bundle;
import android.util.Log;
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

    TextView fname;
    TextView lname;
    TextView cnic;
    TextView address;
    TextView location;
    TextView contact;
    TextView institution;
    Button yes;
    Button no;
    ImageView prof;
    ImageView trans;
    ListView listView = null;
    private List<String> lastList = new ArrayList<>();
    private TutorInfo x;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = new ListView(this);
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
            Log.e("grad",split2[i]);
            Log.e("sub",splited[i]);
        }
        fname = (TextView) findViewById(R.id.fname);
        fname.setText(x.firstName.substring(0,1).toUpperCase()+x.firstName.substring(1));
        lname = (TextView) findViewById(R.id.lname);
        lname.setText(x.lastName.substring(0,1).toUpperCase()+x.lastName.substring(1));
        cnic = (TextView) findViewById(R.id.cnicNo);
        cnic.setText(x.cnicNo);
        contact = (TextView) findViewById(R.id.contactNo);
        contact.setText(x.contactNo);
        address = (TextView) findViewById(R.id.address);
        address.setText(WordUtils.capitalizeFully(x.address));
        location = (TextView) findViewById(R.id.location);
        location.setText(WordUtils.capitalizeFully(x.tuitionLocation));
        institution = (TextView) findViewById(R.id.institution);
        institution.setText(x.recentInstitution);
        prof = (ImageView) findViewById(R.id.imageProfile);
        trans = (ImageView) findViewById(R.id.imageTranscript);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.list_item_3, R.id.txtitem, lastList);
        listView = (ListView) findViewById(R.id.sub_list);
        listView.setAdapter(adapter);
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
        yes = (Button) findViewById(R.id.approve);
        no = (Button) findViewById(R.id.disapprove);

    }
    public void acceptProfile(View view) {
        DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference("Tutors");
        dbNode.child(x.key).child("profileStatus").setValue(1);
        Toast.makeText(getBaseContext(), "Profile Verified", Toast.LENGTH_SHORT).show();
        finish();
    }
    public void rejectProfile(View view) {
        DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference("Tutors");
        dbNode.child(x.key).child("profileStatus").setValue(0);
        Toast.makeText(getBaseContext(), "Profile Declined", Toast.LENGTH_SHORT).show();
        finish();
    }
}
