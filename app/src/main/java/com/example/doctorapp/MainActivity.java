package com.example.doctorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;


    private static final int SPLASH_SCREEN_TIME_OUT = 2000; // After completion of 2000 ms, the next activity will get started.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        sharedPreferences = getSharedPreferences("SP",MODE_PRIVATE);//initializing private shared preference


        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();//hide app bar

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                boolean login = sharedPreferences.getBoolean("loginStatus",false);

                if(login)
                {
                    Intent i = new Intent(MainActivity.this,ListActivity.class);
                    startActivity(i);
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                // Intent is used to switch from one activity to another.

                finish(); // the current activity will get finished.
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}