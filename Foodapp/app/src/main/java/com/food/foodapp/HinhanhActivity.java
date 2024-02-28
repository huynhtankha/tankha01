package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class HinhanhActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button btnChooseImage, btnUploadImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hinhanh);
        imageView = findViewById(R.id.imageView);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnUploadImage = findViewById(R.id.btnUploadImage);

    }
}