package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.food.foodapp.activity.HomeActivity;
import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginAdminActivity extends AppCompatActivity {
    EditText email,password;
    ImageView login;
    FoodApi foodApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        String baseUrl = Utils.BASE_URL;
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);
        email = findViewById(R.id.loginemailadmin);
        password = findViewById(R.id.loginpasswordadmin);
        login = findViewById(R.id.loginadmin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_email = email.getText().toString().trim();
                String str_password = password.getText().toString().trim();
                if(TextUtils.isEmpty(str_email)){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(str_password)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập Password", Toast.LENGTH_SHORT).show();
                } else {
                    compositeDisposable.add(foodApi.getLoginAdmin(str_email,str_password)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    adminModel -> {
                                        if(adminModel.isSuccess()){
                                            Utils.admin_food = adminModel.getResult().get(0);
                                            Intent intent = new Intent(LoginAdminActivity.this, HomeAdminActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else
                                            Toast.makeText(getApplicationContext(), "Vui lòng nhập lại tài khoản mật khẩu", Toast.LENGTH_SHORT).show();
                                    },
                                    throwable ->{
                                        Toast.makeText(getApplicationContext(),throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }
}