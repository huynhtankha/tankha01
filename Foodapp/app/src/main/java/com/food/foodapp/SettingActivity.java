package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.food.foodapp.R;
import com.food.foodapp.utils.Utils;

import io.paperdb.Paper;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        LinearLayout lienhe = findViewById(R.id.lh);
        lienhe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout diendan = findViewById(R.id.diendan);
        diendan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout qr = findViewById(R.id.qr);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MaQRActivity.class);
                startActivity(intent);
            }
        });
        TextView dangxuat = findViewById(R.id.dangxuat);
        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xóa dữ liệu trong Paper
                Paper.book().delete("cartfood");

                // Xóa dữ liệu trong danh sách mangcart và mangmua
                Utils.mangcart.clear();
                Utils.mangmua.clear();

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout lsorder = findViewById(R.id.order);
        lsorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OrderHistoryActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout map = findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MaplocationActivity.class);
                startActivity(intent);
            }
        });


        Toolbar setting = findViewById(R.id.sett);
        setSupportActionBar(setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        LinearLayout mk = findViewById(R.id.mk);
        mk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, ChangeActivity.class);
                startActivity(intent);
            }
        });
    }
}