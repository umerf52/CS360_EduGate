package com.apps.edu_gate;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

public class TutorAdapter extends RecyclerView.Adapter<TutorAdapter.TutorViewHolder> {

    private Context mCtx;
    private List<Tutorinfo> tutorList;
    private static MyClickListener myClickListener;
    private StorageReference storageReference;
    private DatabaseReference mDatabase;

    public static class TutorViewHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView fname;
        TextView lname;
        TextView institution;
        TextView rating;
        TextView location;
        ImageView profileImage;

        public TutorViewHolder(View itemView) {
            super(itemView);
            fname = (TextView) itemView.findViewById(R.id.fname);
            lname = (TextView) itemView.findViewById(R.id.lname);
            institution = (TextView) itemView.findViewById(R.id.institution);
            rating = (TextView) itemView.findViewById(R.id.rating);
            location = (TextView) itemView.findViewById(R.id.location);
            profileImage = (ImageView)itemView.findViewById(R.id.person_photo);
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
        ArrayList<Double> temp = tutor.rating;
        double x = 0;
        double count = 0;
        Iterator it = temp.iterator();
        while (it.hasNext()) {
            count++;
            x = x + (long)it.next();
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
//        storageReference = FirebaseStorage.getInstance().getReference();
//        mDatabase = FirebaseDatabase.getInstance().getReference(tutor.profileImage);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mCtx.getContentResolver(), android.net.Uri.parse((tutor.profileImage).toString()));
            holder.profileImage.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        holder.profileImage.setImageResource(tutor.profileImage);
//        holder.instituttion.setText(tutor.urlImage);
    }

    @Override
    public int getItemCount() {
        return tutorList.size();
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}