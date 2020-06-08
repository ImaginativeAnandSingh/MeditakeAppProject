package com.example.anandsingh.meditakenew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    ProgressBar progressBar;

    Handler handler;

    Runnable runnable;

    Timer timer;

    int i = 0, p = 200;
    private final int delay = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
        progressBar.setMax(100);

        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                if(i<=100)
                {
                    progressBar.setProgress(i);
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    timer.cancel();
                }
            }
        };

        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                i++;
                handler.post(runnable);
            }
        },1000,99);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(in);
                SplashScreen.this.finish();
            }
        },delay);

    }

}