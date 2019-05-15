package com.apps.edu_gate;

import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
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

public class FragmentName extends Fragment implements SearchWithFragments.DataUpdateListener {

    View v;
    private RecyclerView recyclerView;
    private List<TutorInfo> tutorList;
    RecyclerView.Adapter mAdapter;
    private ArrayList<String> adminNumbers = new ArrayList<>();

    String s;
    String check;
    String c;


    public FragmentName(){

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        v = inflater.inflate(R.layout.fragment_layout,container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.rv);
        mAdapter = new TutorAdapter(getContext(), tutorList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        return v;
    }

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


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            tutorList.clear();
            int count= 0;
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int status = snapshot.child("profileStatus").getValue(int.class);
                    if(status==1){
                        count++;
                        TutorInfo tutor = new TutorInfo();
                        tutor.grade = snapshot.child("grade").getValue(String.class);
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
                    }
                }
                mAdapter.notifyDataSetChanged();
                hideProgressDialog();
            }
            else{
                hideProgressDialog();
                Toast.makeText(getActivity().getBaseContext(),"No such results!", Toast.LENGTH_LONG).show();
            }
            if(count==0){
                hideProgressDialog();
                Toast.makeText(getActivity().getBaseContext(),"No such results!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };



    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        tutorList = new ArrayList<>();
        s = "";
        check = "";
    }
    @Override
    public void onDataUpdate(String xyz, String ident, String checker) {
        s = xyz;
        check = ident;
        c = checker;
        if(ident.equals("Name")){
            showProgressDialog();
//            Toast.makeText(getActivity().getBaseContext(), xyz, Toast.LENGTH_LONG).show();
            Log.e("Name", xyz);
            Query q7 = FirebaseDatabase.getInstance().getReference("Admin").orderByChild("mContactNo");
            q7.addListenerForSingleValueEvent(valueEventListener5);
            Query q = FirebaseDatabase.getInstance().getReference("Tutors").orderByChild("firstName").equalTo(xyz);
            q.addListenerForSingleValueEvent(valueEventListener);
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
            ((TutorAdapter) mAdapter).setOnItemClickListener(new TutorAdapter.MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Log.e("Name","clinersss");
                    TutorInfo x = tutorList.get(position);
                    if(c.equals("startup")){
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



    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Looking for Tutors...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }
}
