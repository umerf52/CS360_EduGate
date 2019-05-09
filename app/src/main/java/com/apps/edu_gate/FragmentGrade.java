package com.apps.edu_gate;

import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentGrade extends Fragment implements SearchWithFragments.DataUpdateListener{

    View v;
    private RecyclerView recyclerView;
    private List<TutorInfo> tutorList;
    RecyclerView.Adapter mAdapter;
    private ArrayList<String> adminNumbers = new ArrayList<>();
    String s;
    String check;
    String c;


    ValueEventListener valueEventListener5 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            adminNumbers.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String number = snapshot.child("mContactNo").getValue(String.class);
                    adminNumbers.add(number);
                }
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    ValueEventListener valueEventListener3 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            tutorList.clear();
            int count=0;
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int status = snapshot.child("profileStatus").getValue(int.class);
                    if(status==1){
                        String subjects = snapshot.child("grade").getValue(String.class);
                        if(subjects.length()>0){
                            String[] splited = subjects.split("-");
                            for(int i=0; i<splited.length; i++){
                                if(s.equals(splited[i])){
                                    TutorInfo tutor = new TutorInfo();
                                    tutor.grade = subjects;
                                    count++;
                                    tutor.subject = snapshot.child("subject").getValue(String.class);
                                    tutor.address = snapshot.child("address").getValue(String.class);
                                    tutor.cnicNo = snapshot.child("cnicNo").getValue(String.class);
                                    tutor.contactNo = snapshot.child("contactNo").getValue(String.class);
                                    tutor.emailAddress = snapshot.child("emailAddress").getValue(String.class);
                                    tutor.firstName = snapshot.child("firstName").getValue(String.class);
                                    tutor.gender = snapshot.child("gender").getValue(String.class);
                                    tutor.lastName = snapshot.child("lastName").getValue(String.class);
                                    tutor.recentInstitution = snapshot.child("recentInstitution").getValue(String.class);
                                    tutor.tuitionLocation = snapshot.child("tuitionLocation").getValue(String.class);
                                    tutor.rating = (ArrayList<Double>) snapshot.child("rating").getValue();
                                    tutor.profileImage = snapshot.child("profileImage").getValue(String.class);
                                    tutorList.add(tutor);
                                    break;
                                }
                            }
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
            else{
                Toast.makeText(getActivity().getBaseContext(),"No such results!", Toast.LENGTH_LONG).show();
            }
            if(count==0){
                Toast.makeText(getActivity().getBaseContext(),"No such results!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };



    public FragmentGrade(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        v = inflater.inflate(R.layout.fragment_layout,container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.rv);
        mAdapter = new TutorAdapter4(getContext(), tutorList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        return v;
    }

    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        tutorList = new ArrayList<>();
        check = "";
        s = "";
        //need from main activity
    }

    @Override
    public void onDataUpdate(String xyz, String ident, String checker) {
        s = xyz;
        check = ident;
        c = checker;
        if(ident.equals("Grade")){
            Query q7 = FirebaseDatabase.getInstance().getReference("Admin").orderByChild("mContactNo");
            q7.addListenerForSingleValueEvent(valueEventListener5);
            Toast.makeText(getActivity().getBaseContext(), s, Toast.LENGTH_LONG).show();
            Log.e("Grade", s);
            Query q = FirebaseDatabase.getInstance().getReference("Tutors");
            q.addListenerForSingleValueEvent(valueEventListener3);
        }
        // put your UI update logic here
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((SearchWithFragments) activity).registerDataUpdateListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((SearchWithFragments) getActivity()).unregisterDataUpdateListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
            ((TutorAdapter4) mAdapter).setOnItemClickListener(new TutorAdapter4.MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    TutorInfo x = tutorList.get(position);
                    Log.e("Grade","clinersss");
                    if (c.equals("startup")){
                        Intent myIntent = new Intent(getActivity().getBaseContext(), TutorSearchProfileActivity.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        myIntent.putExtra("result",x);
                        myIntent.putStringArrayListExtra("num",adminNumbers);
                        getActivity().getBaseContext().startActivity(myIntent);
                    }else{
                        Intent myIntent = new Intent(getActivity().getBaseContext(), SearchTutorAdmin.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        myIntent.putExtra("result",x);
                        myIntent.putStringArrayListExtra("num",adminNumbers);
                        getActivity().getBaseContext().startActivity(myIntent);
                    }
                }
            });
    }
}