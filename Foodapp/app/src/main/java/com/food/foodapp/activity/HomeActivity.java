package com.food.foodapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.food.foodapp.BargerActivity;
import com.food.foodapp.CameraActivity;
import com.food.foodapp.CartActivity;
import com.food.foodapp.MessengerActivity;
import com.food.foodapp.ProfileActivity;
import com.food.foodapp.R;
import com.food.foodapp.SearchActivity;
import com.food.foodapp.SettingActivity;
import com.food.foodapp.adapter.FoodAdapter;
import com.food.foodapp.adapter.ProductAdapter;
import com.food.foodapp.models.CategoryFood;
import com.food.foodapp.models.ProductFood;
import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodApi foodApi;
    List<CategoryFood> mangCategory;
    FoodAdapter foodAdapter;
    List<ProductFood> mangProduct;
    ProductAdapter productAdapter;
    ImageView came;
    TextView search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(6000);
                }catch (Exception exception){

                }
            }
        };
        thread.start();
        Paper.init(this);
        came = findViewById(R.id.camenhan);
        search = findViewById(R.id.searchfood);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        came.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });
//        if (Utils.user_food != null) {
//            String imgpro = Utils.user_food.getHinhanh();
//            if (imgpro != null && !imgpro.isEmpty()) {
//                // Sử dụng Glide để tải và hiển thị hình ảnh từ URL lên ImageView
//                Glide.with(this)
//                        .load(imgpro) // imgpro là URL hình ảnh
//                        .into(img); // img là ImageView bạn muốn hiển thị hình ảnh
//            }
//        }
        TextView tvname = findViewById(R.id.tvname);
        if(Utils.user_food !=null ){
            String userName = Utils.user_food.getUsername();
            if (userName != null && !userName.isEmpty()) {
                tvname.setText(userName);

            }
        }
        LinearLayout chat = findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MessengerActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout set = findViewById(R.id.setting);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout homecartButton = findViewById(R.id.homecart);

        homecartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển đến một Activity mới (ví dụ: CartActivity)
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("user", Utils.user_food);
                startActivity(intent);
            }
        });
        LinearLayout layout = findViewById(R.id.homeBtn);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        Setupview();
        getCategory();
        getProduct();
        getEventClick();

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
                            Intent barger = new Intent(getApplicationContext(), BargerActivity.class);
                            barger.putExtra("loai",1);
                            startActivity(barger);
                            break;
                        case 1:
                            Intent donut = new Intent(getApplicationContext(), BargerActivity.class);
                            donut.putExtra("loai",4);
                            startActivity(donut);
                            break;
                        case 2:
                            Intent drink = new Intent(getApplicationContext(), BargerActivity.class);
                            drink.putExtra("loai",3);
                            startActivity(drink);
                            break;
                        case 3:
                            Intent pizza = new Intent(getApplicationContext(), BargerActivity.class);
                            pizza.putExtra("loai",2);
                            startActivity(pizza);
                            break;
                        case 0:
                            Intent hotdog = new Intent(getApplicationContext(), BargerActivity.class);
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


    private void getProduct() {
        compositeDisposable.add(foodApi.getProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                     productFoodModel -> {
                         if(productFoodModel.isSuccess()) {
                             mangProduct = productFoodModel.getResult();
                             productAdapter = new ProductAdapter(getApplicationContext(), mangProduct);
                             recyclerView2.setAdapter(productAdapter);


                         }
                     },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối được" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void getCategory() {
        compositeDisposable.add(foodApi.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categoryFoodModel -> {
                            if (categoryFoodModel.isSuccess()) {
                                mangCategory = categoryFoodModel.getResult();
                                foodAdapter = new FoodAdapter(getApplicationContext(), mangCategory);
                                recyclerView.setAdapter(foodAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
    }

    private void Setupview() {

        recyclerView = findViewById(R.id.productRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView2 = findViewById(R.id.recyclerView2);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this ,LinearLayoutManager.HORIZONTAL, false );
        recyclerView2.setLayoutManager(layoutManager1);
        recyclerView2.setHasFixedSize(true);

        mangCategory = new ArrayList<>();
        mangProduct = new ArrayList<>();
        if(Paper.book().read("cartfood")!=null){
            Utils.mangcart = Paper.book().read("cartfood");
        }
        if(Utils.mangcart == null){
            Utils.mangcart = new ArrayList<>();
        }

        // Lấy BASE_URL từ Utils
        String baseUrl = Utils.BASE_URL;

        // Khởi tạo RetrofitClient với baseUrl
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);
    }

}
