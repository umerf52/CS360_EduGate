package com.apps.edu_gate;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class TutorAdapter extends RecyclerView.Adapter<TutorAdapter.TutorViewHolder> {

    private Context mCtx;
    private List<Tutorinfo> tutorList;
    private static MyClickListener myClickListener;

    public static class TutorViewHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView fname;
        TextView lname;
        TextView institution;
        TextView rating;
        TextView location;
//        TextView myimage;

        public TutorViewHolder(View itemView) {
            super(itemView);
            fname = (TextView) itemView.findViewById(R.id.fname);
            lname = (TextView) itemView.findViewById(R.id.lname);
            institution = (TextView) itemView.findViewById(R.id.institution);
            rating = (TextView) itemView.findViewById(R.id.rating);
            location = (TextView) itemView.findViewById(R.id.location);
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
        Iterator it = tutor.rating.entrySet().iterator();
        double x = 0;
        double count = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            count++;
            x = x + (double) pair.getValue();
//            Log.d("TAG","grade: "+pair.getKey() +  " = "  + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
        double avgrate = x/count;
        tutor.tempr = avgrate;
        String rate = String.valueOf(avgrate);
        holder.fname.setText(tutor.firstName);
        holder.location.setText(tutor.tuitionLocation);
        holder.lname.setText(tutor.lastName);
        holder.institution.setText(tutor.recentInstitution);
        holder.rating.setText(rate);
//        holder.instituttion.setText(tutor.urlImage);
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