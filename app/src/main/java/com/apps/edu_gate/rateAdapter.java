package com.apps.edu_gate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class rateAdapter extends RecyclerView.Adapter<rateAdapter.rateViewHolder> {
    private Context mCtx;
    private List<Tutorinfo> tutorList;
    private static rateAdapter.MyClickListener myClickListener;
    private StorageReference storageReference;
    private DatabaseReference mDatabase;

    public static class rateViewHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView fname;
        TextView lname;
        TextView rating;
        ImageView profileImage;

        public rateViewHolder(View itemView) {
            super(itemView);
            fname = (TextView) itemView.findViewById(R.id.fname);
            lname = (TextView) itemView.findViewById(R.id.lname);
            rating = (TextView) itemView.findViewById(R.id.rating);
            profileImage = (ImageView)itemView.findViewById(R.id.person_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(rateAdapter.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public rateAdapter(Context mCtx, List<Tutorinfo> tutorList) {
        this.mCtx = mCtx;
        this.tutorList = tutorList;
    }
    @NonNull
    @Override
    public rateAdapter.rateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerateone, parent, false);
        return new rateAdapter.rateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rateAdapter.rateViewHolder holder, int position) {
        Tutorinfo tutor = tutorList.get(position);
        ArrayList<Double> temp = tutor.rating;
        double x = 0;
        double count = 0;
        Iterator it = temp.iterator();
        while (it.hasNext()) {
            count++;
            x = x + (long)it.next();
//            it.remove(); // avoids a ConcurrentModificationException
        }
        double avgrate = x/count;
        tutor.tempr = avgrate;
        String rate = String.valueOf(avgrate);
        holder.fname.setText(tutor.firstName.substring(0,1).toUpperCase()+tutor.firstName.substring(1));
        holder.lname.setText(tutor.lastName.substring(0,1).toUpperCase()+tutor.lastName.substring(1));
        holder.rating.setText(rate);
        Picasso.get()
                .load(tutor.getProfileImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .fit()
                .centerCrop()
                .into(holder.profileImage);
    }

    @Override
    public int getItemCount() {
        return tutorList.size();
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }




}
