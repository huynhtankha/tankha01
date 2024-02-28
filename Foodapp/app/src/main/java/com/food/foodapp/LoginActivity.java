package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.food.foodapp.activity.HomeActivity;
import com.food.foodapp.activity.MainActivity;
import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    TextView dk, admin;
    ImageView login;
    EditText loginemail, loginpasword;
    FoodApi foodApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        admin = findViewById(R.id.quantri);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginAdminActivity.class);
                startActivity(intent);
            }
        });
        Paper.init(this);
        setUpView();
        TextView forgot = findViewById(R.id.forgot);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotActivity.class);
                startActivity(intent);
            }
        });
        dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_email = loginemail.getText().toString().trim();
                String str_password = loginpasword.getText().toString().trim();
                if(TextUtils.isEmpty(str_email)){
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(str_password)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập Password", Toast.LENGTH_SHORT).show();
                } else {

                    Paper.book().write("email",str_email);
                    Paper.book().write("password", str_password);
                    compositeDisposable.add(foodApi.getLogin(str_email,str_password)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        if (userModel.isSuccess()) {
                                            Utils.user_food = userModel.getResult().get(0);
                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else
                                            Toast.makeText(getApplicationContext(), "Vui lòng nhập lại tài khoản mật khẩu", Toast.LENGTH_SHORT).show();
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(),throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }

    private void setUpView() {
        String baseUrl = Utils.BASE_URL;
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);
        dk = findViewById(R.id.tvdk);
        login = findViewById(R.id.login);
        loginemail = findViewById(R.id.loginemail);
        loginpasword = findViewById(R.id.loginpassword);
        if(Paper.book().read("email")!=null && Paper.book().read("password")!=null){
            loginemail.setText(Paper.book().read("email"));
            loginpasword.setText(Paper.book().read("password"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.user_food.getEmail() !=null && Utils.user_food.getPassword() !=null ){
            loginemail.setText(Utils.user_food.getEmail());
            loginpasword.setText(Utils.user_food.getPassword());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}