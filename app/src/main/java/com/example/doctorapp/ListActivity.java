package com.example.doctorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements itemClickCallBack{


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    RecyclerView recyclerView;
    List<Doctors> doctorsList;
    DoctorListAdapter doctorListAdapter;

    TextView logout;


   ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        sharedPreferences = getSharedPreferences("SP",MODE_PRIVATE);
        editor = sharedPreferences.edit();


        recyclerView = findViewById(R.id.rv_list);
        logout = findViewById(R.id.logout);

        doctorsList = new ArrayList<>();


        db.collection("Doctors")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                Doctors doctorsFromDb = document.toObject(Doctors.class);
                                setAdapter(doctorsFromDb);


                            }
                        } else {
                            Toast.makeText(ListActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });






//        doctorsList.add(new Doctors("James","Ortho",""));
//        doctorsList.add(new Doctors("James","Ortho",""));
//        doctorsList.add(new Doctors("James","Ortho",""));
//        doctorsList.add(new Doctors("James","Ortho",""));
//        doctorsList.add(new Doctors("James","Ortho",""));
//        doctorsList.add(new Doctors("James","Ortho",""));
//        doctorsList.add(new Doctors("James","Ortho",""));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putBoolean("loginStatus",false);
                editor.apply();
                Intent i = new Intent(ListActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }


    void setAdapter(Doctors doctorsFromDb){
        Doctors doctor=new Doctors();
        doctor.setName(doctorsFromDb.getName());
        doctor.setSpecialization(doctorsFromDb.getSpecialization());
        doctor.setProfile_image(doctorsFromDb.getProfile_image());

        doctorsList.add(doctor);


        doctorListAdapter = new  DoctorListAdapter(doctorsList,this);
        recyclerView.setAdapter(doctorListAdapter);
    }

    @Override
    public void itemClick(Doctors doctors) {

    }

}