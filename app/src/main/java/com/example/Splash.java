package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    ImageView logo;
    Animation animation;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.app_splash_music);
        mediaPlayer.start();

        logo = findViewById(R.id.Logo);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
        logo.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);
                finish();
                mediaPlayer.stop();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}