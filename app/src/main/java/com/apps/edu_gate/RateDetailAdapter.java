package com.apps.edu_gate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class RateDetailAdapter extends RecyclerView.Adapter<RateDetailAdapter.RateDetailViewHolder> {
    private static RateDetailAdapter.MyClickListener myClickListener;
    private Context mCtx;
    private List<Double> ratingList;

    public RateDetailAdapter(Context mCtx, List<Double> ratingList) {
        this.mCtx = mCtx;
        this.ratingList = ratingList;
    }

    public void setOnItemClickListener(RateDetailAdapter.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @NonNull
    @Override
    public RateDetailAdapter.RateDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.list3, parent, false);
        return new RateDetailAdapter.RateDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RateDetailAdapter.RateDetailViewHolder holder, int position) {
        double rating = ratingList.get(position);
        holder.rating_view.setText((char) rating);
    }

    @Override
    public int getItemCount() {
        return ratingList.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public static class RateDetailViewHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        TextView rating_view;
        Button edit_button;

        public RateDetailViewHolder(View itemView) {
            super(itemView);
            rating_view = (TextView) itemView.findViewById(R.id.rating_number);
            edit_button = itemView.findViewById(R.id.edit_button);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }


}
