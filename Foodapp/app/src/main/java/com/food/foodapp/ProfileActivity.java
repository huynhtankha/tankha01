package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.food.foodapp.models.User;
import com.food.foodapp.models.UserModel;
import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;

import java.io.File;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ProfileActivity extends AppCompatActivity {


    ImageView eyeImageView, editproimg ;
    TextView nameTextView, emailTextView, mobileTextView, pasWord, quan, phuong;
    Button facebookButton, tiktokButton, update;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        update = findViewById(R.id.sua);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UpdateTActivity.class);
                User user = new User();
                user.setUsername(nameTextView.getText().toString());
                user.setEmail(emailTextView.getText().toString());
                user.setMobile(mobileTextView.getText().toString());
                user.setAddress(quan.getText().toString());
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hồ sơ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        quan = findViewById(R.id.qhuyen);
        phuong = findViewById(R.id.xa);
        nameTextView = findViewById(R.id.proten);
        emailTextView = findViewById(R.id.proemail);
        mobileTextView = findViewById(R.id.promobile);
        pasWord = findViewById(R.id.password);
        facebookButton = findViewById(R.id.facebook);
        tiktokButton = findViewById(R.id.tiktok);
        eyeImageView = findViewById(R.id.eye);
        editproimg = findViewById(R.id.editproimg);
        final boolean[] isPasswordVisible = {false};

        eyeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isPasswordVisible[0] = !isPasswordVisible[0];

                if (isPasswordVisible[0]) {

                    pasWord.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    // Ẩn mật khẩu
                    pasWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        // Set click listeners for social media buttons
        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFacebook();
            }
        });

        tiktokButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTiktok();
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            User user = (User) intent.getSerializableExtra("user");
            if (user != null) {
                nameTextView.setText(user.getUsername());
                emailTextView.setText(user.getEmail());
                mobileTextView.setText(user.getMobile());
                pasWord.setText(user.getPassword());
                quan.setText(user.getAddress());
                phuong.setText(user.getWard() + " ");
                // ... (cập nhật các phần giao diện người dùng khác)
                if (Utils.user_food.getHinhanh().contains("http")) {
                    Glide.with(getApplicationContext())
                            .load(Utils.user_food.getHinhanh() + "?t=" + System.currentTimeMillis())
                            .into(editproimg);
                } else {
                    String hinh = Utils.BASE_URL + "imagesuser/" + Utils.user_food.getHinhanh();
                    Glide.with(getApplicationContext())
                            .load(hinh + "?t=" + System.currentTimeMillis())
                            .into(editproimg);
                }
            }
        }
    }




    private void openTiktok() {
        String tikTokUrl = "https://www.tiktok.com/";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tikTokUrl));
        startActivity(intent);
    }

    private void openFacebook() {
        String facebookUrl = "https://www.facebook.com/";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
        startActivity(intent);
    }
}