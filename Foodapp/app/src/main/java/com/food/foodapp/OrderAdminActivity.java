package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.food.foodapp.adapter.OrAdminAdapter;
import com.food.foodapp.adapter.OrderAdapter;
import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderAdminActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodApi foodApi;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_admin);
        String baseUrl = Utils.BASE_URL;
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);
        upView();
        getData();
    }

    private void getData() {
        compositeDisposable.add(foodApi.gethistoryadmin(Utils.user_food.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        orderModel -> {
                            OrAdminAdapter orAdminAdapter = new OrAdminAdapter(getApplicationContext(),orderModel.getResult());
                            recyclerView.setAdapter(orAdminAdapter);
                        } ,
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void upView() {
        recyclerView = findViewById(R.id.revdonhang1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }
}