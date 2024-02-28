package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.food.foodapp.adapter.QlRatingAdapter;
import com.food.foodapp.adapter.TtUserAdapter;
import com.food.foodapp.models.Rating;
import com.food.foodapp.models.User;
import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QlRatingMainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Rating> ratingList;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodApi foodApi;
    QlRatingAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ql_rating_main);
        String baseUrl = Utils.BASE_URL;
        // Khởi tạo RetrofitClient với baseUrl
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);
        recyclerView = findViewById(R.id.binhl);
        ratingList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        gatData();
    }

    private void gatData() {
        compositeDisposable.add(foodApi.getBl()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()) {
                                ratingList = userModel.getResult();
                                adapter = new QlRatingAdapter(getApplicationContext(), ratingList);
                                recyclerView.setAdapter(adapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối được" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
    }
}