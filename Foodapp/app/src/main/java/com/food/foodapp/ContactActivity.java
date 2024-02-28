package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.widget.Toolbar;

import com.food.foodapp.activity.MainActivity;

public class ContactActivity extends AppCompatActivity {
    LinearLayout facebook, youtube, gmail, mobile, tiktok;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        youtube = findViewById(R.id.zalo);
        gmail= findViewById(R.id.gmail);
        mobile= findViewById(R.id.mobile);
        facebook = findViewById(R.id.facebook);
        tiktok = findViewById(R.id.tiktok);
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFacebook();
            }
        });
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openYoutube();
            }
        });
        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGmail();
            }
        });
        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callMobile();
            }
        });
        tiktok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tiktokOpen();
            }
        });
        toolbar = findViewById(R.id.tsb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Liên hệ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void tiktokOpen() {
        String tiktokUrl = "https://www.tiktok.com/@khab1906318";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tiktokUrl));
        startActivity(intent);
    }

    private void callMobile() {
        String phoneNumber = "0354446943";
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    private void openGmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:kyha3210@gmail.com"));
        startActivity(intent);
    }

    private void openYoutube() {
        String youtubeUrl = "https://www.youtube.com/channel/UCuLzOGRECG1j0sL2WIOlb8g";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl));
        startActivity(intent);
    }

    private void openFacebook() {
        String facebookUrl = "https://www.facebook.com/profile.php?id=100093605877003";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
        startActivity(intent);
    }
}