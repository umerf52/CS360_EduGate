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

import org.apache.commons.text.WordUtils;

import java.util.List;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.pendingViewHolder> {

    private Context mCtx;
    private List<TutorInfo> tutorList;
    private static MyClickListener myClickListener;

    PendingAdapter(Context mCtx, List<TutorInfo> tutorList) {
        this.mCtx = mCtx;
        this.tutorList = tutorList;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        PendingAdapter.myClickListener = myClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull pendingViewHolder holder, int position) {
        TutorInfo tutor = tutorList.get(position);
        Picasso.get()
                .load(tutor.getProfileImage())
                .placeholder(R.drawable.placeholder_profile_picture)
                .fit()
                .centerCrop()
                .into(holder.profileImage);
        holder.fname.setText(WordUtils.capitalizeFully(tutor.firstName));
        holder.lname.setText(WordUtils.capitalizeFully(tutor.lastName));
    }

    @NonNull
    @Override
    public pendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recycler_pending, parent, false);
        return new pendingViewHolder(view);
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

    @Override
    public int getItemCount() {
        return tutorList.size();
    }

    public static class pendingViewHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView fname;
        TextView lname;
        ImageView profileImage;

        pendingViewHolder(View itemView) {
            super(itemView);
            fname = itemView.findViewById(R.id.fname);
            lname = itemView.findViewById(R.id.lname);
            profileImage = itemView.findViewById(R.id.person_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}