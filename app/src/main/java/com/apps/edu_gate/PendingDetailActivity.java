package com.apps.edu_gate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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
    private Tutorinfo x;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = new ListView(this);
        x = (Tutorinfo) getIntent().getSerializableExtra("result");
        setContentView(R.layout.activity_pending_detail);
        String sub = x.subject;
        String grad = x.grade;
        String[] splited = sub.split("-");
        String[] split2 = grad.split("-");
        for(int i=0; i<splited.length; i++){
            String f = split2[i].substring(0,1).toUpperCase()+split2[i].substring(1) + ": " + splited[i].substring(0,1).toUpperCase()+splited[i].substring(1);
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
        address.setText(x.address);
        location = (TextView) findViewById(R.id.location);
        location.setText(x.tuitionLocation);
        institution = (TextView) findViewById(R.id.institution);
        institution.setText(x.recentInstitution);
        prof = (ImageView) findViewById(R.id.imageProfile);
        trans = (ImageView) findViewById(R.id.imageTranscript);
        Picasso.get()
                .load(x.getProfileImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .fit()
                .centerCrop()
                .into(prof);
        Picasso.get()
                .load(x.gettranscriptImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .fit()
                .centerCrop()
                .into(trans);
        yes = (Button) findViewById(R.id.approve);
        no = (Button) findViewById(R.id.disapprove);
    }
    public void acceptProfile(View view) {
        DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference("Tutors");
        dbNode.child(x.key).child("profileStatus").setValue(1);
        Toast.makeText(getBaseContext(), "Profile Verified!", Toast.LENGTH_SHORT).show();
    }
    public void rejectProfile(View view) {
        DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference("Tutors");
        dbNode.child(x.key).child("profileStatus").setValue(0);
        Toast.makeText(getBaseContext(), "Profile Declined!", Toast.LENGTH_SHORT).show();
    }
}
