package com.example.doctorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    EditText loginMobile, loginPassword;
    Button login;
    TextView registerNow;

    String mobile, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("SP",MODE_PRIVATE);
        editor = sharedPreferences.edit();


        loginMobile = findViewById(R.id.loginMobile);
        loginPassword = findViewById(R.id.loginPassword);
        login = findViewById(R.id.loginButton);
        registerNow = findViewById(R.id.registerNow);

        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mobile = loginMobile.getText().toString();
                password = loginPassword.getText().toString();


                DocumentReference docRef = db.collection("users").document(mobile);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                                DocumentReference docRef = db.collection("users").document(mobile);
                                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        User user = documentSnapshot.toObject(User.class);

                                        if(user.loginPassword.equals(password)){
                                            Toast.makeText(LoginActivity.this, "Username found", Toast.LENGTH_SHORT).show();

                                            editor.putBoolean("loginStatus",true);
                                            editor.apply();//or commit

                                            Intent i = new Intent(LoginActivity.this,ListActivity.class);
                                            startActivity(i);
                                            finish();

                                        }
                                        else {
                                            Toast.makeText(LoginActivity.this, "Please,check password", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else {
                                //Log.d(TAG, "No such document");
                                Toast.makeText(LoginActivity.this, "Account not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });

                
            }
        });
    }
}