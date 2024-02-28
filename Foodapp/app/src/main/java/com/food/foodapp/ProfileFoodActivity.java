package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.food.foodapp.utils.Utils;

public class ProfileFoodActivity extends AppCompatActivity {
    ImageView imageView;
    TextView name, email, mobile;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_food);
        imageView = findViewById(R.id.images);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobilenumber);
        update = findViewById(R.id.update);

        Intent intent = getIntent();
        String userImage = intent.getStringExtra("user_image");
        String userName = intent.getStringExtra("user_name");
        String userEmail = intent.getStringExtra("user_email");
        String userMobile = intent.getStringExtra("user_mobile");

        if(Utils.user_food.getHinhanh().contains("http")){
            Glide.with(getApplicationContext()).load(Utils.user_food.getHinhanh()).into(imageView);
        } else {
            String hinh= Utils.BASE_URL + "images/"+Utils.user_food.getHinhanh();
            Glide.with(getApplicationContext()).load(hinh).into(imageView);
        }
        name.setText(userName);
        email.setText(userEmail);
        mobile.setText(userMobile);

    }
}