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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class RateAdapter extends RecyclerView.Adapter<RateAdapter.rateViewHolder> {
    private Context mCtx;
    private static RateAdapter.MyClickListener myClickListener;
    private List<TutorInfo> tutorList;

    public RateAdapter(Context mCtx, List<TutorInfo> tutorList) {
        this.mCtx = mCtx;
        this.tutorList = tutorList;
    }

    public void setOnItemClickListener(RateAdapter.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @NonNull
    @Override
    public RateAdapter.rateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recycler_rate, parent, false);
        return new RateAdapter.rateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RateAdapter.rateViewHolder holder, int position) {
        TutorInfo tutor = tutorList.get(position);
        ArrayList<Double> temp = tutor.rating;
        double x = 0;
        double count = 0;
        Iterator it = temp.iterator();
        while (it.hasNext()) {
            count++;
            x = x + (long)it.next();
        }
        double averageRate = x / count;
        averageRate = BigDecimal.valueOf(averageRate)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
        tutor.tempr = averageRate;
        String rate = String.valueOf(averageRate);
        Picasso.get()
                .load(tutor.getProfileImage())
                .placeholder(R.drawable.placeholder_profile_picture)
                .fit()
                .centerCrop()
                .into(holder.profileImage);
        holder.firstName.setText((WordUtils.capitalizeFully(tutor.firstName)));
        holder.lastName.setText((WordUtils.capitalizeFully(tutor.lastName)));
        holder.rating.setText(rate);
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

    @Override
    public int getItemCount() {
        return tutorList.size();
    }

    public static class rateViewHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView firstName;
        TextView lastName;
        TextView rating;
        ImageView profileImage;

        public rateViewHolder(View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.fname);
            lastName = itemView.findViewById(R.id.lname);
            rating = itemView.findViewById(R.id.rating);
            profileImage = itemView.findViewById(R.id.person_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

}
