package com.apps.edu_gate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.apache.commons.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

public class TutorSearchProfile extends AppCompatActivity {

    TextView fname;
    TextView gender;
    TextView lastname;
    TextView instituion;
    TextView location;
    ImageView imageTutor;

    private List<String> adminNumbers = new ArrayList<>();
    private  List<String> subjectsTur = new ArrayList<>();
    private  List<String> gradesTur = new ArrayList<>();
    private  List<String> lastList = new ArrayList<>();

    ListView listView = null;
    ListView subList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = new ListView(this);


        Tutorinfo x = (Tutorinfo) getIntent().getSerializableExtra("result");
        setContentView(R.layout.activity_tutor_search_profile);
        setTitle("Profile");
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        String sub = x.subject;
        String grad = x.grade;
        String[] splited = sub.split("-");
        String[] split2 = grad.split("-");
        for(int i=0; i<splited.length; i++){
            subjectsTur.add(splited[i].substring(0,1).toUpperCase()+splited[i].substring(1));
            gradesTur.add(split2[i].substring(0,1).toUpperCase()+split2[i].substring(1));
            String f = WordUtils.capitalizeFully(split2[i]) + ": " + WordUtils.capitalizeFully(splited[i]);
            lastList.add(f);
        }
        fname = (TextView) findViewById(R.id.fname);
        fname.setText(WordUtils.capitalizeFully(x.firstName));
        gender = (TextView) findViewById(R.id.gender);
        gender.setText(x.gender);
        lastname = (TextView) findViewById(R.id.lname);
        lastname.setText(WordUtils.capitalizeFully(x.lastName));
        instituion = (TextView) findViewById(R.id.institution);
        instituion.setText(WordUtils.capitalizeFully(x.recentInstitution));
        location = (TextView) findViewById(R.id.location);
        location.setText(WordUtils.capitalizeFully(x.tuitionLocation));
        imageTutor = findViewById(R.id.imageTutor);
        Picasso.get()
                .load(x.getProfileImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .fit()
                .centerCrop()
                .into(imageTutor);
        ratingBar.setRating((float)x.tempr);
        ArrayAdapter adapter1 = new ArrayAdapter<String>(this,
                R.layout.listitem,R.id.txtitem, lastList);

        subList = (ListView) findViewById(R.id.sub_list);
        subList.setAdapter(adapter1);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        Query q = FirebaseDatabase.getInstance().getReference("Admin")
                .orderByChild("number");
        q.addListenerForSingleValueEvent(valueEventListener);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,
                R.layout.listitem, R.id.txtitem,adminNumbers);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long id) {
                ViewGroup vg=(ViewGroup)view;
                TextView txt=(TextView)vg.findViewById(R.id.txtitem);
                Uri u = Uri.parse("tel:" + txt.getText().toString());
                Intent i = new Intent(Intent.ACTION_DIAL, u);
                startActivity(i);
            }
        });
    }
    public void showDialogListView(View view) {

        AlertDialog.Builder builder = new
                AlertDialog.Builder(TutorSearchProfile.this);

        builder.setCancelable(true);

        builder.setPositiveButton("OK", null);

        builder.setView(listView);

        AlertDialog dialog = builder.create();

        dialog.show();
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            adminNumbers.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Admininfo admin = snapshot.getValue(Admininfo.class);
                    String number = snapshot.child("number").getValue(String.class);
                    adminNumbers.add(number);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
