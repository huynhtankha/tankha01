package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.food.foodapp.models.CartFood;
import com.food.foodapp.models.ProductFood;
import com.food.foodapp.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;
import java.util.ArrayList;

import io.paperdb.Paper;

public class DetailProductActivity extends AppCompatActivity {
    private TextView ten, gia, nd, cart, sl, tong, trangthai;
    private ImageView img, plusbtn, minusbtn, cart1;
    private ProductFood productFood;
    LinearLayout binhluan;
    private NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        binhluan = findViewById(R.id.binhluan);
        initViews();
        getProductData();
        binhluan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RatingActivity.class);
                intent.putExtra("product_id", productFood.getI());
                intent.putExtra("product_image", productFood.getHinhanh());
                intent.putExtra("pro_ten",productFood.getTensp());
                startActivity(intent);
            }
        });

        plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increaseQuantity();
            }
        });

        minusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decreaseQuantity();
            }
        });

        initCart();
    }

    private void initViews() {
        ten = findViewById(R.id.detailten);
        gia = findViewById(R.id.detailgia);
        nd = findViewById(R.id.detailnd);
        cart = findViewById(R.id.detaicart);
        sl = findViewById(R.id.detaisl);
        img = findViewById(R.id.detailimg);
        plusbtn = findViewById(R.id.plus);
        minusbtn = findViewById(R.id.minus);
        tong = findViewById(R.id.detailtong);
        badge = findViewById(R.id.sl);
        trangthai = findViewById(R.id.trangthai);

    }

    private void getProductData() {
        productFood = (ProductFood) getIntent().getSerializableExtra("chitiet");
        ten.setText(productFood.getTensp());
        trangthai.setText(productFood.getTrangthai());
        nd.setText(productFood.getNoidung());
        Glide.with(getApplicationContext()).load(productFood.getHinhanh()).into(img);
        if(productFood.getHinhanh().contains("http")){
            Glide.with(getApplicationContext()).load(productFood.getHinhanh()).into(img);
        }
        else {
            String hinh= Utils.BASE_URL + "images/"+productFood.getHinhanh();
            Glide.with(getApplicationContext()).load(hinh).into(img);
        }
        displayInitialPrice();
    }

    private void displayInitialPrice() {
        double pricePerItem = Double.parseDouble(productFood.getGia());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        gia.setText(decimalFormat.format(pricePerItem) + "đ");
        updateTotalPrice();

    }

    private void decreaseQuantity() {
        int minus = Integer.parseInt(sl.getText().toString());
        if (minus > 1) {
            minus--;
            sl.setText(String.valueOf(minus));
            updateTotalPrice();
        } else {
            Toast.makeText(this, "Số lượng không thể nhỏ hơn 1", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTotalPrice() {
        if (productFood != null) {
            int currentQuantity = Integer.parseInt(sl.getText().toString());
            double pricePerItem = Double.parseDouble(productFood.getGia());
            double totalPrice = currentQuantity * pricePerItem;
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            tong.setText(decimalFormat.format(totalPrice) + "đ");
        }
    }

    private void increaseQuantity() {
        int plus = Integer.parseInt(sl.getText().toString());
        plus = Math.min(10, plus + 1);
        sl.setText(String.valueOf(plus));
        updateTotalPrice();
    }

    private void initCart() {
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productFood.getTrangthai().equalsIgnoreCase("Hết sản phẩm")) {
                    Toast.makeText(DetailProductActivity.this, "Sản phẩm đã hết hàng", Toast.LENGTH_SHORT).show();
                } else {
                    addCart();
                    Paper.book().write("cartfood", Utils.mangcart);
                }
            }
        });
    }

    private void addCart() {
        int soluong = Integer.parseInt(sl.getText().toString());
        long gia = Long.parseLong(productFood.getGia());

        if (Utils.mangcart != null) {
            boolean itemExists = false;

            for (CartFood cartItem : Utils.mangcart) {
                if (cartItem.getIdsp() == productFood.getI()) {
                    itemExists = true;
                    break;
                }
            }

            if (!itemExists) {
                CartFood cartFood = new CartFood();
                cartFood.setGiasp(gia);
                cartFood.setSoluong(soluong);
                cartFood.setIdsp(productFood.getI());
                cartFood.setTensp(productFood.getTensp());
                cartFood.setHinhanh(productFood.getHinhanh());
                Utils.mangcart.add(cartFood);

                Toast.makeText(this, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Sản phẩm đã có trong giỏ hàng", Toast.LENGTH_SHORT).show();
            }


        } else {
            CartFood cartFood = new CartFood();
            cartFood.setGiasp(gia);
            cartFood.setSoluong(soluong);
            cartFood.setIdsp(productFood.getI());
            cartFood.setTensp(productFood.getTensp());
            cartFood.setHinhanh(productFood.getHinhanh());
            Utils.mangcart = new ArrayList<>();
            Utils.mangcart.add(cartFood);
            Toast.makeText(this, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        }
    }

}

