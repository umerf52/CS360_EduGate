package com.apps.edu_gate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TutorAdapter extends RecyclerView.Adapter<TutorAdapter.TutorViewHolder> {

    private Context mCtx;
    private List<Tutorinfo> tutorList;
    private static MyClickListener myClickListener;

    public static class TutorViewHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView fname;
        TextView address;

        public TutorViewHolder(View itemView) {
            super(itemView);
            fname = (TextView) itemView.findViewById(R.id.person_fname);
            address = (TextView) itemView.findViewById(R.id.person_address);
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

    public TutorAdapter(Context mCtx, List<Tutorinfo> tutorList) {
        this.mCtx = mCtx;
        this.tutorList = tutorList;
    }

    @NonNull
    @Override
    public TutorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclertutor, parent, false);
        return new TutorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TutorViewHolder holder, int position) {
        Tutorinfo tutor = tutorList.get(position);
        holder.fname.setText(tutor.Name);
        holder.address.setText(tutor.Address);
    }

    @Override
    public int getItemCount() {
        return tutorList.size();
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

//    class TutorViewHolder extends RecyclerView.ViewHolder {
//
//        TextView textViewName, textViewAddress;
//
//        public TutorViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            textViewName = itemView.findViewById(R.id.person_fname);
//            textViewAddress = itemView.findViewById(R.id.person_address);
//        }
//    }
}