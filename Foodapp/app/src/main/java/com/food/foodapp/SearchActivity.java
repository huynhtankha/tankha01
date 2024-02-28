package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.food.foodapp.activity.HomeActivity;
import com.food.foodapp.adapter.BargerAdapter;
import com.food.foodapp.adapter.ProductAdapter;
import com.food.foodapp.models.ProductFood;
import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText editText;

    BargerAdapter adapterBg;
    List<ProductFood> productFoodList;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodApi foodApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ImageView imageView = findViewById(R.id.homet);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
        // Lấy BASE_URL từ Utils
        String baseUrl = Utils.BASE_URL;

        // Khởi tạo RetrofitClient với baseUrl
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);
        upView();
    }

    private void upView() {
        productFoodList = new ArrayList<>();
        recyclerView = findViewById(R.id.reviewsearch);
        editText = findViewById(R.id.searchf);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                dataSearch();
            }
        });

    }

    private void dataSearch() {
        productFoodList.clear();
        String searchfood = editText.getText().toString().trim();
        compositeDisposable.add(foodApi.search(searchfood)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    productFoodModel -> {
                        if(productFoodModel.isSuccess()){
                            productFoodList = productFoodModel.getResult();
                            adapterBg = new BargerAdapter(getApplicationContext(), productFoodList);
                            recyclerView.setAdapter(adapterBg);
                        }
                    },
                    throwable -> {
                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                ));
    }
}