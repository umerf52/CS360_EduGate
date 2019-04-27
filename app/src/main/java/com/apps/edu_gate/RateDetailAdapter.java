package com.apps.edu_gate;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class RateDetailAdapter extends RecyclerView.Adapter<RateDetailAdapter.RateDetailViewHolder> {
    private Context mCtx;
    private ArrayList<Double> ratings;
    private static RateDetailAdapter.MyClickListener myClickListener;

    public static class RateDetailViewHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView rate;
        Button button;

        public RateDetailViewHolder(View itemView) {
            super(itemView);
            rate = (TextView) itemView.findViewById(R.id.rating_number);
            button = (Button) itemView.findViewById(R.id.edit_button);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(RateDetailAdapter.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public RateDetailAdapter(Context mCtx, ArrayList<Double> ratings) {
        this.mCtx = mCtx;
        this.ratings = ratings;
    }
    @NonNull
    @Override
    public RateDetailAdapter.RateDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerrating, parent, false);
        return new RateDetailAdapter.RateDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RateDetailAdapter.RateDetailViewHolder holder, int position) {
        double one = ratings.get(position);
        Log.e("wow1111", String.valueOf(one));

        holder.rate.setText(String.valueOf(one));
        holder.button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.e("wow", "onClick:button ");
            }
        });
    }

    @Override
    public int getItemCount() {
        return ratings.size();
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }




}
