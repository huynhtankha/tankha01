package com.food.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;
import com.google.android.material.navigation.NavigationView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChanAdminActivity extends AppCompatActivity{
    EditText passnew, xnpass;
    ImageView change;
    FoodApi foodApi;
    Toolbar toolbar;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chan_admin);
        toolbar = findViewById(R.id.toobbardmk);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Đổi mật khẩu");



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        passnew = findViewById(R.id.mknew);
        xnpass = findViewById(R.id.xnmk);
        change = findViewById(R.id.chanepassword);
        String baseUrl = Utils.BASE_URL;
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = passnew.getText().toString().trim();
                String confirmPassword = xnpass.getText().toString().trim();

                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(ChanAdminActivity.this, "Vui lòng nhập mật khẩu và xác nhận mật khẩu mới", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(ChanAdminActivity.this, "Mật khẩu và xác nhận mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }


                compositeDisposable.add(foodApi.getUpdateadmin(Utils.admin_food.getEmail(), newPassword)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    if (response.isSuccess()) {
                                        Toast.makeText(ChanAdminActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ChanAdminActivity.this, LoginAdminActivity.class);
                                        startActivity(intent);

                                        finish();
                                    } else {
                                        Toast.makeText(ChanAdminActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> {
                                    Toast.makeText(ChanAdminActivity.this, "Lỗi: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        ));
            }
        });
    }
}