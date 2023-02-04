package com.example.doctorapp;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.MyViewHolder>{

    List<Doctors> doctorsList;
    itemClickCallBack callBack;
    Context context;

    public DoctorListAdapter(List<Doctors> doctorsList, Context context,itemClickCallBack callBack) {

        this.doctorsList = doctorsList;
        this.callBack = callBack;
        this.context = context;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()). inflate(R.layout.doctor_list,parent,false);
        return new  MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    Doctors doctorsData = doctorsList.get(position);
    holder.name.setText(doctorsData.getName());
    holder.specialization.setText(doctorsData.getSpecialization());
    holder.mobile.setText(doctorsData.mobile);

    String drImage = doctorsData.getProfile_image();
        Glide.with(context)
                .load(drImage)
                .into(holder.drImage);

        holder.bookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.itemClick(doctorsData);
            }
        });

    }



    @Override
    public int getItemCount() {
        return doctorsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{


    TextView name, specialization,mobile;
    ImageView drImage;
    Button bookbtn;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        mobile = itemView.findViewById(R.id.mobile);
        name = itemView.findViewById(R.id.name);
        specialization = itemView.findViewById(R.id.specialization);
        bookbtn = itemView.findViewById(R.id.bookButton);
        drImage = itemView.findViewById(R.id.profile_image);
    }
}
}
