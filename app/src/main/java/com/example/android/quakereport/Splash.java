package com.example.android.quakereport;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Aditya on 03-08-2017.
 */

public class Splash extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.splashscreen);

        Toast.makeText(this, "Stilll.. in Devlopment ", Toast.LENGTH_LONG).show();

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Splash.this, EarthquakeActivity.class));
                finish();
            }
        }, secondsDelayed * 1800);
    }

}