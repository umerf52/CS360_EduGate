package com.apps.edu_gate;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class delAdapter extends RecyclerView.Adapter<delAdapter.delViewHolder> {
    private Context mCtx;
    private List<Tutorinfo> tutorList;
    private static delAdapter.MyClickListener myClickListener;
    private StorageReference storageReference;
    private DatabaseReference mDatabase;

    public static class delViewHolder extends RecyclerView.ViewHolder
            implements View
            .OnLongClickListener {
        TextView fname;
        TextView lname;
        ImageView profileImage;

        public delViewHolder(View itemView) {
            super(itemView);
            fname = (TextView) itemView.findViewById(R.id.fname);
            lname = (TextView) itemView.findViewById(R.id.lname);
            profileImage = (ImageView)itemView.findViewById(R.id.person_photo);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
            return true;
        }
    }

    public void setOnItemClickListener(delAdapter.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public delAdapter(Context mCtx, List<Tutorinfo> tutorList) {
        this.mCtx = mCtx;
        this.tutorList = tutorList;
    }
    @NonNull
    @Override
    public delAdapter.delViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerdel, parent, false);
        return new delAdapter.delViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull delAdapter.delViewHolder holder, int position) {
        Tutorinfo tutor = tutorList.get(position);

        holder.fname.setText(tutor.firstName.substring(0,1).toUpperCase()+tutor.firstName.substring(1));
        holder.lname.setText(tutor.lastName.substring(0,1).toUpperCase()+tutor.lastName.substring(1));
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
