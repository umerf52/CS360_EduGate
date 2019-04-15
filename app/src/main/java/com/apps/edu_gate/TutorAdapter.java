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
        holder.textViewName.setText(tutor.Name);
        holder.textViewAddress.setText(tutor.Address);
    }

    @Override
    public int getItemCount() {
        return tutorList.size();
    }

    class TutorViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName, textViewAddress;

        public TutorViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.person_fname);
            textViewAddress = itemView.findViewById(R.id.person_address);
        }
    }
}