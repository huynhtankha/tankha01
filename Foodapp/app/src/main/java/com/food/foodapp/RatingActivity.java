package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.food.foodapp.activity.HomeActivity;
import com.food.foodapp.adapter.BargerAdapter;
import com.food.foodapp.adapter.RatingAdapter;
import com.food.foodapp.models.CartFood;
import com.food.foodapp.models.ProductFood;
import com.food.foodapp.models.Rating;
import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RatingActivity extends AppCompatActivity {
    EditText  cmt;
    Spinner spinnerRating;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodApi foodApi;

    LinearLayoutManager linearLayoutManager;
    RatingAdapter adapter;
    List<Rating> ratings;
    RecyclerView recyclerView;
    int page = 1;
    int idsp;
    ImageView imageView, anh, result ;
    TextView tesp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        String baseUrl = Utils.BASE_URL;
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);
//        spinnerRating = findViewById(R.id.editrating);

//        String[] ratingsArray = {"1", "2", "3", "4", "5"};
//        ArrayAdapter<String> ratingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ratingsArray);
//        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//
//        spinnerRating.setAdapter(ratingAdapter);
        anh = findViewById(R.id.anhdaidien);
        if (Utils.user_food.getHinhanh().contains("http")) {
            Glide.with(getApplicationContext())
                    .load(Utils.user_food.getHinhanh() + "?t=" + System.currentTimeMillis())
                    .into(anh);
        } else {
            String hinh = Utils.BASE_URL + "imagesuser/" + Utils.user_food.getHinhanh();
            Glide.with(getApplicationContext())
                    .load(hinh + "?t=" + System.currentTimeMillis())
                    .into(anh);
        }
        imageView =findViewById(R.id.imasp);
        tesp = findViewById(R.id.tensprating);
        Intent intent = getIntent();
        String productName = intent.getStringExtra("pro_ten");
        tesp.setText(productName);

        if (intent != null) {
            String productImage = intent.getStringExtra("product_image");
            if (productImage != null) {
                Glide.with(this).load(productImage).into(imageView);
            }
        }
        idsp = getIntent().getIntExtra("product_id", 1);
        upView();
        getData(page);

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_cmt = cmt.getText().toString().trim();
                if (TextUtils.isEmpty(str_cmt)) {
                    Toast.makeText(RatingActivity.this, "Bạn chưa nhập thông tin", Toast.LENGTH_SHORT).show();
                }
                else {

                    int str_id = Utils.user_food.getId();
                    String str_name = Utils.user_food.getUsername();
                    Date dateting = new Date();
                    String str_hinhanh = Utils.user_food.getHinhanh();
                    int productId = getIntent().getIntExtra("product_id", -1);
                    compositeDisposable.add(foodApi.getcmt(str_id,str_hinhanh,str_name,productId,str_cmt,dateting)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    ratingModel -> {
                                        cmt.setText("");
                                        getData(page);
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(),throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }

    private void upView() {
        cmt = findViewById(R.id.cmt);
        result = findViewById(R.id.resultdy);
        recyclerView = findViewById(R.id.reviewrating);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        ratings = new ArrayList<>();
    }

    private void getData(int page) {
        compositeDisposable.add(foodApi.getRating(page, idsp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ratingModel -> {
                            if (ratingModel.isSuccess()) {
                                if (adapter == null) {
                                    ratings = ratingModel.getResult();
                                    adapter = new RatingAdapter(getApplicationContext(), ratings);
                                    recyclerView.setAdapter(adapter);
                                } else {
                                    if (page == 1) {
                                        ratings.clear();
                                    }
                                    ratings.addAll(ratingModel.getResult());
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối", Toast.LENGTH_LONG).show();
                        }
                ));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

}