package com.apps.edu_gate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CallerAdapter extends RecyclerView.Adapter<CallerAdapter.CallerViewHolder> {

    private Context mCtx;
    private List<Admininfo> adminList;
    private static CallerAdapter.MyClickListener myClickListener;

    public static class CallerViewHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView number;

        public CallerViewHolder(View itemView) {
            super(itemView);
            number = (TextView) itemView.findViewById(R.id.admin_num);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(CallerAdapter.MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public CallerAdapter(Context mCtx, List<Admininfo> adminList) {
        this.mCtx = mCtx;
        this.adminList = adminList;
    }

    @NonNull
    @Override
    public CallerAdapter.CallerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclercaller, parent, false);
        return new CallerAdapter.CallerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CallerAdapter.CallerViewHolder holder, int position) {
        Admininfo admin = adminList.get(position);
        holder.number.setText(admin.Number);
    }

    @Override
    public int getItemCount() {
        return adminList.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}