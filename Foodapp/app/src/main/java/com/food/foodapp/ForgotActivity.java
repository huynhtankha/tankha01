package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ForgotActivity extends AppCompatActivity {
    ImageView button;
    EditText email;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodApi foodApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        upView();
        conTrol();
    }

    private void conTrol() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stremail = email.getText().toString().trim();
                if(TextUtils.isEmpty(stremail)){
                    Toast.makeText(getApplicationContext(),"Bạn chua nhập email",Toast.LENGTH_SHORT).show();
                }
                else {
                    compositeDisposable.add(foodApi.forgotPassword(stremail)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        if(userModel.isSuccess()){
                                            Toast.makeText(getApplicationContext(),userModel.getMessage(),Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else {
                                            Toast.makeText(getApplicationContext(),userModel.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }

    private void upView() {
        button = findViewById(R.id.forgot);
        email = findViewById(R.id.forgotemail);

        String baseUrl = Utils.BASE_URL;

        // Khởi tạo RetrofitClient với baseUrl
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);
    }
}