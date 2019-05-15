package com.apps.edu_gate;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

class RateDetailAdapter extends RecyclerView.Adapter<RateDetailAdapter.RateDetailViewHolder> {
    private Context mCtx;
    private ArrayList<Double> ratings;
    private String keys;
    private static RateDetailAdapter.MyClickListener myClickListener;

    @Override
    public void onBindViewHolder(@NonNull RateDetailAdapter.RateDetailViewHolder holder, final int position) {
        holder.rate.setText(String.valueOf(ratings.get(position)));
        holder.rate.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length()>0){
                    String t = String.valueOf(s);
                    double p = Double.parseDouble(t);
                    int x = (int) p;
                    p = (double) x;
                    if (x <= 5 && x>=0) {
                        ratings.set(position, p);
                    }else{
                        Toast.makeText(mCtx, "Invalid Entry, between 0 and 5 only!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatabaseReference dbNode = FirebaseDatabase.getInstance().getReference("Tutors");
                dbNode.child(keys).child("rating").setValue(ratings).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mCtx, "Rating updated!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void setOnItemClickListener(RateDetailAdapter.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public RateDetailAdapter(Context mCtx, ArrayList<Double> ratings, String itskey) {
        this.mCtx = mCtx;
        this.ratings = ratings;
        this.keys = itskey;
    }

    @NonNull
    @Override
    public RateDetailAdapter.RateDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recycler_rate_detail, parent, false);
        return new RateDetailAdapter.RateDetailViewHolder(view);
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

    @Override
    public int getItemCount() {
        return ratings.size();
    }

    public static class RateDetailViewHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        EditText rate;
        Button button;

        public RateDetailViewHolder(View itemView) {
            super(itemView);
            rate = itemView.findViewById(R.id.rating_number);
            button = itemView.findViewById(R.id.edit_button);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }




}
