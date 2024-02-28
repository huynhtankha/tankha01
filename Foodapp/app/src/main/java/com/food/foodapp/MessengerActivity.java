package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.food.foodapp.activity.MainActivity;

public class MessengerActivity extends AppCompatActivity {
    LottieAnimationView animationView;
    ImageView zalo;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        toolbar = findViewById(R.id.tsm);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hổ trợ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        animationView = findViewById(R.id.animation_view);

        animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMessenger();
            }
        });
        animationView.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // Animation đã hoàn thành, gọi phương thức để mở Messenger ở đây
                openMessenger();
            }
        });
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(6000);
                }catch (Exception exception){

                }
            }
        };
        thread.start();
    }

    private void openMessenger() {
        String facebookUrl = "https://www.messenger.com/t/111831168621268";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
        startActivity(intent);
    }
}