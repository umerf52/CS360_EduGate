package com.apps.edu_gate;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.apache.commons.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

public class TutorSearchProfileActivity extends AppCompatActivity {

    private ArrayList<String> adminNumbers = new ArrayList<>();
    private  List<String> subjectsTur = new ArrayList<>();
    private  List<String> gradesTur = new ArrayList<>();
    private  List<String> lastList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView subList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TutorInfo x = (TutorInfo) getIntent().getSerializableExtra("result");
        adminNumbers = getIntent().getStringArrayListExtra("num");
        setContentView(R.layout.activity_tutor_search_profile);
        setTitle("Profile");
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        String sub = x.subject;
        String grad = x.grade;
        String[] split = sub.split("-");
        String[] split2 = grad.split("-");
        for (int i = 0; i < split.length; i++) {
            subjectsTur.add(split[i].substring(0, 1).toUpperCase() + split[i].substring(1));
            gradesTur.add(split2[i].substring(0,1).toUpperCase()+split2[i].substring(1));
            String f = WordUtils.capitalizeFully(split2[i]) + ": " + WordUtils.capitalizeFully(split[i]);
            lastList.add(f);
        }
        ImageView imageTutor = findViewById(R.id.imageTutor);
        Picasso.get()
                .load(x.getProfileImage())
                .placeholder(R.drawable.placeholder_profile_picture)
                .fit()
                .centerCrop()
                .into(imageTutor);
        TextView firstName = findViewById(R.id.fname);
        firstName.setText(WordUtils.capitalizeFully(x.firstName));
        TextView gender = findViewById(R.id.gender);
        gender.setText(x.gender);
        TextView lastName = findViewById(R.id.lname);
        lastName.setText(WordUtils.capitalizeFully(x.lastName));
        TextView institution = findViewById(R.id.institution);
        institution.setText(WordUtils.capitalizeFully(x.recentInstitution));
        TextView location = findViewById(R.id.location);
        location.setText(WordUtils.capitalizeFully(x.tuitionLocation));
        ratingBar.setRating((float)x.tempr);
        ArrayAdapter adapter1 = new ArrayAdapter<>(this,
                R.layout.list_item, R.id.txtitem, lastList);

        subList = findViewById(R.id.sub_list);
        subList.setAdapter(adapter1);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogListView();
            }
        });
    }

    public void showDialogListView() {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(TutorSearchProfileActivity.this);
        builderSingle.setTitle("Call Administrator");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(TutorSearchProfileActivity.this, android.R.layout.select_dialog_item);
        for (String adminNumber : adminNumbers) {
            arrayAdapter.add(adminNumber);
        }

        builderSingle.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                Uri uri = Uri.parse("tel:" + strName);
                Intent myIntent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(myIntent);
            }
        });
        builderSingle.show();
    }
}
