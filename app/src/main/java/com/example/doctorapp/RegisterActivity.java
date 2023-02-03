package com.example.doctorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText registerName,registerMobile,registerEmail,registerPassword,registerConfirm;
    Button registerButton;
    TextView loginNow;

    String name,mobile,email,password,confirm,blood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerName = findViewById(R.id.registerName);
        registerMobile = findViewById(R.id.registerMobile);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerConfirm = findViewById(R.id.registerConfirm);
        registerButton = findViewById(R.id.registerButton);
        loginNow = findViewById(R.id.loginNow);


        String[] arraySpinner = new String[] {
                "A", "A+", "B", "B+", "AB+", "AB-", "O+","O-"
        };
        Spinner s = (Spinner) findViewById(R.id.registerBlood);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);


        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = registerName.getText().toString();
                mobile = registerMobile.getText().toString();
                email = registerEmail.getText().toString();
                password = registerPassword.getText().toString();
                confirm = registerConfirm.getText().toString();
                blood = s.getSelectedItem().toString();

                DocumentReference docRef = db.collection("users").document(mobile);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                //Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                                Toast.makeText(RegisterActivity.this, "Mobile Number Already Registered", Toast.LENGTH_SHORT).show();
                                

                            } else {
                                //Log.d(TAG, "No such document");


                                if(password.equals(confirm)) {

                                    Map<String, Object> user = new HashMap<>();
                                    user.put("name", name);
                                    user.put("loginMobile", mobile);
                                    user.put("email", email);
                                    user.put("loginPassword", password);
                                    user.put("blood_group", blood);

                                    db.collection("users").document(mobile)
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    //Log.d(TAG, "DocumentSnapshot successfully written!");
                                                    Toast.makeText(RegisterActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                                    Intent i = new Intent(RegisterActivity.this, ListActivity.class);
                                                    startActivity(i);
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Log.w(TAG, "Error writing document", e);
                                                    Toast.makeText(RegisterActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                                                }
                                            });



                                }
                                else {
                                    Toast.makeText(RegisterActivity.this, "Check password", Toast.LENGTH_SHORT).show();
                                }
                                
                                
                                
                            }
                        } else {
                            //Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });




                
            }
        });
    }
}