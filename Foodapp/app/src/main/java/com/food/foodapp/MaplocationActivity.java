package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.food.foodapp.activity.MainActivity;

public class MaplocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maplocation);
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(5500);
                }catch (Exception exception){

                }finally {
                    Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();
    }
}