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

class DeleteAdapter extends RecyclerView.Adapter<DeleteAdapter.delViewHolder> {
    private Context mCtx;
    private static DeleteAdapter.MyClickListener myClickListener;
    private List<TutorInfo> tutorList;

    DeleteAdapter(Context mCtx, List<TutorInfo> tutorList) {
        this.mCtx = mCtx;
        this.tutorList = tutorList;
    }

    public void setOnItemClickListener(DeleteAdapter.MyClickListener myClickListener) {
        DeleteAdapter.myClickListener = myClickListener;
    }

    @NonNull
    @Override
    public DeleteAdapter.delViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recycler_delete, parent, false);
        return new DeleteAdapter.delViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteAdapter.delViewHolder holder, int position) {
        TutorInfo tutor = tutorList.get(position);

        Picasso.get()
                .load(tutor.getProfileImage())
                .placeholder(R.drawable.placeholder_profile_picture)
                .fit()
                .centerCrop()
                .into(holder.profileImage);
        holder.firstName.setText((WordUtils.capitalizeFully(tutor.firstName)));
        holder.lastName.setText((WordUtils.capitalizeFully(tutor.lastName)));
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

    @Override
    public int getItemCount() {
        return tutorList.size();
    }

    public static class delViewHolder extends RecyclerView.ViewHolder
            implements View
            .OnLongClickListener {
        TextView firstName;
        TextView lastName;
        ImageView profileImage;

        delViewHolder(View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.fname);
            lastName = itemView.findViewById(R.id.lname);
            profileImage = itemView.findViewById(R.id.person_photo);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
            return true;
        }
    }




}
