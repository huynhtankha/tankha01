package com.food.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.food.foodapp.adapter.FoodAdapter;
import com.food.foodapp.adapter.FoodAdminAdapter;
import com.food.foodapp.adapter.ProductAdapter;
import com.food.foodapp.models.CategoryFood;
import com.food.foodapp.models.ProductFood;
import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class    ProductManagementActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodApi foodApi;
    List<ProductFood> mangProduct;
    ProductAdapter productAdapter;
    List<CategoryFood> mangCategory;
    FoodAdminAdapter foodAdapter;
    ImageView addsanpham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_management);

        recyclerView = findViewById(R.id.reviewsanpham);
        addsanpham = findViewById(R.id.addsanpham);
        addsanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);
            }
        });
        String baseUrl = Utils.BASE_URL;
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);
 //       getProduct();
        getCategory();
        getEventClick();
        // Khởi tạo RetrofitClient với baseUrl
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

    }
    private void getEventClick() {
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(),e.getY());
                if(child != null && e.getAction() == MotionEvent.ACTION_UP){
                    int position = rv.getChildAdapterPosition(child);
                    switch (position) {
                        case 4:
                            Intent barger = new Intent(getApplicationContext(), QlProductActivity.class);
                            barger.putExtra("loai",1);
                            startActivity(barger);
                            break;
                        case 1:
                            Intent donut = new Intent(getApplicationContext(), QlProductActivity.class);
                            donut.putExtra("loai",4);
                            startActivity(donut);
                            break;
                        case 2:
                            Intent drink = new Intent(getApplicationContext(), QlProductActivity.class);
                            drink.putExtra("loai",3);
                            startActivity(drink);
                            break;
                        case 3:
                            Intent pizza = new Intent(getApplicationContext(), QlProductActivity.class);
                            pizza.putExtra("loai",2);
                            startActivity(pizza);
                            break;
                        case 0:
                            Intent hotdog = new Intent(getApplicationContext(), QlProductActivity.class);
                            hotdog.putExtra("loai",5);
                            startActivity(hotdog);
                            break;

                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }
    private void getCategory() {
        compositeDisposable.add(foodApi.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categoryFoodModel -> {
                            if (categoryFoodModel.isSuccess()) {
                                mangCategory = categoryFoodModel.getResult();
                                foodAdapter = new FoodAdminAdapter(getApplicationContext(), mangCategory);
                                recyclerView.setAdapter(foodAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
    }
}