package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.food.foodapp.activity.MainActivity;
import com.food.foodapp.adapter.CartAdapter;

import com.food.foodapp.models.CartFood;
import com.food.foodapp.models.EventBus.PlusEvent;
import com.food.foodapp.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;


public class CartActivity extends AppCompatActivity {
    TextView carttrong,tong, itemTotal, services, tvtong;
    RecyclerView recyclerView;

    Toolbar tobar;
    CartAdapter cartAdapter;
    LinearLayout layout;
    ImageView rs;
    ConstraintLayout dathang;
    private long totalQuantity = 0;
    long tongtien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        dathang= findViewById(R.id.checck);
        dathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean atLeastOneChecked = false;

                for (CartFood cartFood : Utils.mangcart) {
                    if (cartFood.isCheck()) {
                        atLeastOneChecked = true;
                        break;
                    }
                }

                if (atLeastOneChecked) {

                    Intent intent = new Intent(getApplicationContext(), ToPayActivity.class);
                    intent.putExtra("tongtien", tongtien);
                    startActivity(intent);
                } else {
                    Toast.makeText(CartActivity.this, "Vui lòng chọn sản phẩm để đặt hàng", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (savedInstanceState != null) {
            totalQuantity = savedInstanceState.getLong("totalQuantity", 0);
        }
        upView();
        initControl();

        actionBar();
        if(Utils.mangmua !=null){
            Utils.mangmua.clear();
        }
        total();

        rs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(cartIntent);
            }
        });
    }



    private void actionBar() {
        setSupportActionBar(tobar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tobar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void total() {
        tongtien = 0;
        long soluong = 0;
        for (int i = 0; i < Utils.mangmua.size(); i++) {
            CartFood cartFood = Utils.mangmua.get(i);
            tongtien = tongtien + (cartFood.getGiasp() * cartFood.getSoluong());
            soluong = soluong + cartFood.getSoluong();
        }

        // Không trừ đi phí vận chuyển
        long tongtienBanDau = tongtien;

        // Trừ đi phí vận chuyển
        tongtien += 10000;

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        tong.setText(decimalFormat.format(tongtien)+ "đ");
        tvtong.setText(decimalFormat.format(tongtienBanDau)+ "đ");
        itemTotal.setText(decimalFormat.format(soluong));
        services.setText("10.000"+ "đ");
    }


    private void initControl() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (Utils.mangcart.size() == 0) {
            carttrong.setVisibility(View.VISIBLE); // Hiển thị TextView khi danh sách rỗng
            recyclerView.setVisibility(View.GONE); // Ẩn RecyclerView khi không có sản phẩm
            layout.setVisibility(View.GONE);
            dathang.setVisibility(View.GONE);

        } else {
            cartAdapter = new CartAdapter(getApplicationContext(), Utils.mangcart);
            recyclerView.setAdapter(cartAdapter);
        }
    }

    private void upView() {
        tong = findViewById(R.id.totalcart);
        recyclerView = findViewById(R.id.rview);
        carttrong = findViewById(R.id.trong);
        tobar = findViewById(R.id.tobar);
        itemTotal = findViewById(R.id.tvsoluong);
        rs = findViewById(R.id.rs);
        layout = findViewById(R.id.listtong);
        services = findViewById(R.id.services);
        tvtong = findViewById(R.id.tong);

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();

    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void even(PlusEvent event) {
        if (event != null) {
            totalQuantity++;
            total();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Lưu lại totalQuantity khi activity bị hủy
        outState.putLong("totalQuantity", totalQuantity);
    }
}