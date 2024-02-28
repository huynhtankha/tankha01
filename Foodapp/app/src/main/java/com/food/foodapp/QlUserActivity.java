package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.food.foodapp.adapter.ProductAdapter;
import com.food.foodapp.adapter.TtUserAdapter;
import com.food.foodapp.models.User;
import com.food.foodapp.models.UserModel;
import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QlUserActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<User> mangUser;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodApi foodApi;
    TtUserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ql_user);
        String baseUrl = Utils.BASE_URL;
        // Khởi tạo RetrofitClient với baseUrl
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);
        recyclerView = findViewById(R.id.recyclerViewuser);
        mangUser = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        gatData();

    }

    private void gatData() {
        compositeDisposable.add(foodApi.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()) {
                                mangUser = userModel.getResult();
                                adapter = new TtUserAdapter(getApplicationContext(), mangUser);
                                recyclerView.setAdapter(adapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối được" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
    }
}