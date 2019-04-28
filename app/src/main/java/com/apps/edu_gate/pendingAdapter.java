package com.apps.edu_gate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class pendingAdapter extends RecyclerView.Adapter<pendingAdapter.pendingViewHolder> {

    private Context mCtx;
    private List<Tutorinfo> tutorList;
    private static MyClickListener myClickListener;

    public static class pendingViewHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView fname;
        TextView lname;
        ImageView profileImage;

        public pendingViewHolder(View itemView) {
            super(itemView);
            fname = (TextView) itemView.findViewById(R.id.fname);
            lname = (TextView) itemView.findViewById(R.id.lname);
            profileImage = (ImageView)itemView.findViewById(R.id.person_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public pendingAdapter(Context mCtx, List<Tutorinfo> tutorList) {
        this.mCtx = mCtx;
        this.tutorList = tutorList;
    }

    @NonNull
    @Override
    public pendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerpending, parent, false);
        return new pendingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull pendingViewHolder holder, int position) {
        Tutorinfo tutor = tutorList.get(position);
        Picasso.get()
                .load(tutor.getProfileImage())
                .placeholder(R.drawable.placeholder_profile_picture)
                .fit()
                .centerCrop()
                .into(holder.profileImage);
        holder.fname.setText(tutor.firstName.substring(0,1).toUpperCase()+tutor.firstName.substring(1));
        holder.lname.setText(tutor.lastName.substring(0,1).toUpperCase()+tutor.lastName.substring(1));
    }

    @Override
    public int getItemCount() {
        return tutorList.size();
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}