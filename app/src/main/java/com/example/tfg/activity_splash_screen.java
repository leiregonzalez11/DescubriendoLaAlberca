package com.example.tfg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class activity_splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // HERE WE ARE TAKING THE REFERENCE OF OUR IMAGE
        // SO THAT WE CAN PERFORM ANIMATION USING THAT IMAGE

        ImageView backgroundImage = findViewById(R.id.SplashScreenImage);
        Animation slideAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        backgroundImage.startAnimation(slideAnimation);

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.

        new Handler().postDelayed(() -> {

                /* INTERFAZ PRINCIPAL*/
                //Intent intent = new Intent(activity_splash_screen.this, MainActivity.class);
                Intent intent = new Intent(activity_splash_screen.this, MainActivity.class);
                startActivity(intent);
                finish();

            },3500); // 3000 is the delayed time in milliseconds.

    }

    @Override
    public void onBackPressed(){}

}