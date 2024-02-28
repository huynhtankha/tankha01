package com.food.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.food.foodapp.adapter.BargerAdapter;
import com.food.foodapp.adapter.QuanlyAdapter;
import com.food.foodapp.models.ProductFood;
import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QlProductActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodApi foodApi;
    int page = 1;
    int loai;
    QuanlyAdapter adapterBg;
    List<ProductFood> productFoodList;
    Toolbar toolbar;
    LinearLayoutManager linearLayoutManager;
    Handler handler= new Handler();
    boolean isLoading = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ql_product);


        loai = getIntent().getIntExtra("loai",1);
        UpView();
        getData(page);
        Loading();
    }

    private void Loading() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(isLoading == false){
                    if(linearLayoutManager.findLastCompletelyVisibleItemPosition()==productFoodList.size()-1){
                        isLoading =true;
                        loadingMore();
                    }
                }
            }
        });
    }

    private void loadingMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                productFoodList.add(null);
                adapterBg.notifyItemInserted(productFoodList.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                productFoodList.remove(productFoodList.size()-1);
                adapterBg.notifyItemRemoved(productFoodList.size());
                page = page+1;
                getData(page);
                adapterBg.notifyDataSetChanged();
                isLoading = false;
            }
        },2000);
    }

    private void getData(int page) {
        compositeDisposable.add(foodApi.getFood(page,loai)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productFoodModel -> {
                            if(productFoodModel.isSuccess()){
                                if(adapterBg == null ){
                                    productFoodList = productFoodModel.getResult();
                                    adapterBg = new QuanlyAdapter(getApplicationContext(), productFoodList);
                                    recyclerView.setAdapter(adapterBg);
                                }else {
                                    int vitri = productFoodList.size()-1;
                                    int soluong = productFoodModel.getResult().size();
                                    for(int i=0 ; i<soluong;i++){
                                        productFoodList.add(productFoodModel.getResult().get(i));
                                    }
                                    adapterBg.notifyItemRangeInserted(vitri,soluong);
                                }
                            }
                            else {
//                                Toast.makeText(getApplicationContext(),"Sản phẩm đã hết", Toast.LENGTH_LONG).show();
                                isLoading = true;
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Khong ket noi", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void UpView() {
        recyclerView = findViewById(R.id.recyclerViewproduct);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        productFoodList = new ArrayList<>();

        // Lấy BASE_URL từ Utils
        String baseUrl = Utils.BASE_URL;

        // Khởi tạo RetrofitClient với baseUrl
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);
    }
}