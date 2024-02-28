package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChangeActivity extends AppCompatActivity {
    EditText newPasswordEditText, confirmPasswordEditText;
    ImageView changePasswordButton;
    FoodApi foodApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        TextView forgot = findViewById(R.id.qmk);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ForgotActivity.class);
                startActivity(intent);
            }
        });
        newPasswordEditText = findViewById(R.id.editTextNewPassword);
        confirmPasswordEditText = findViewById(R.id.editTextConfirmPassword);
        changePasswordButton = findViewById(R.id.buttonChangePassword);

        String baseUrl = Utils.BASE_URL;
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = newPasswordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(ChangeActivity.this, "Vui lòng nhập mật khẩu và xác nhận mật khẩu mới", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(ChangeActivity.this, "Mật khẩu và xác nhận mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }


                compositeDisposable.add(foodApi.getUpdate(Utils.user_food.getEmail(), newPassword)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                response -> {
                                    if (response.isSuccess()) {
                                        Toast.makeText(ChangeActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ChangeActivity.this, LoginActivity.class);
                                        startActivity(intent);

                                        finish();
                                    } else {
                                        Toast.makeText(ChangeActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> {
                                    Toast.makeText(ChangeActivity.this, "Lỗi: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        ));
            }
        });
    }
}