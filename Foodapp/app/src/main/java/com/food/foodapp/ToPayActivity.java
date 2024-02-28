package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.food.foodapp.activity.HomeActivity;
import com.food.foodapp.adapter.CartAdapter;
import com.food.foodapp.adapter.TopayAdapter;
import com.food.foodapp.models.CartFood;
import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import vn.momo.momo_partner.AppMoMoLib;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPaySDK;

public class ToPayActivity extends AppCompatActivity {
    TextView name, mobile, quan, phuong, thaydoi;
    TextView duong;
    TextView thanhtoan;
    RecyclerView recyclerView;
    int id_order;
    TextView txttongtien;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodApi foodApi;
    Toolbar toolbar1;
    ConstraintLayout momo;
    int soluong;

    private String amount ;
    private String fee = "0";
    int environment = 0;
    private String merchantName = "HoangNgoc";
    private String merchantCode = "MOMOC2IC20220510";
    private String merchantNameLabel = "HoangNgoc";
    private String description = "mua hàng online";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_pay);
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT);
        recyclerView = findViewById(R.id.reviewproduct);
        TopayAdapter topayAdapter = new TopayAdapter(this, Utils.mangmua);
        CartAdapter cartAdapter = new CartAdapter(this, Utils.mangmua);
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setAdapter(topayAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        slItem();


        LinearLayout dulieu = findViewById(R.id.dulieu);
        LinearLayout linearLayout3 = findViewById(R.id.linearLayout3);
        toolbar1 = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setTitle("Thanh toán");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        thaydoi = findViewById(R.id.thaydoi);
        String baseUrl = Utils.BASE_URL;
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);
        name = findViewById(R.id.hten);
        mobile = findViewById(R.id.sdt);
        quan = findViewById(R.id.quan);
        phuong = findViewById(R.id.xa);
        duong = findViewById(R.id.snha);
        thanhtoan = findViewById(R.id.checcck);
        txttongtien = findViewById(R.id.tongtien);
        momo = findViewById(R.id.thanhtoanmomo);

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        long tongtien = getIntent().getLongExtra("tongtien", 0);
        amount = String.valueOf(tongtien);
        txttongtien.setText(decimalFormat.format(tongtien) + "đ");
        name.setText(Utils.user_food.getUsername());
        mobile.setText(Utils.user_food.getMobile());
        duong.setText(Utils.user_food.getStreet());

        if (TextUtils.isEmpty(Utils.user_food.getAddress()) || TextUtils.isEmpty(Utils.user_food.getWard())) {
            dulieu.setVisibility(View.GONE);
            linearLayout3.setVisibility(View.VISIBLE);
            thaydoi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ChangeAddressActivity.class);
                    intent.putExtra("tongtien", tongtien);
                    startActivity(intent);
                }
            });
            TextView capnhat= findViewById(R.id.capnhat);
            capnhat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),ChangeAddressActivity.class);
                    intent.putExtra("tongtien", tongtien);
                    startActivity(intent);
                }
            });

            thanhtoan.setOnClickListener(null);
        } else {
            dulieu.setVisibility(View.VISIBLE);
            linearLayout3.setVisibility(View.GONE);
            quan.setText(Utils.user_food.getAddress());
            phuong.setText(Utils.user_food.getWard());
            thaydoi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ChangeAddressActivity.class);
                    intent.putExtra("tongtien", tongtien);
                    startActivity(intent);
                }
            });
            thanhtoan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String str_address = duong.getText().toString().trim();
                    if (TextUtils.isEmpty(str_address)) {
                        Toast.makeText(ToPayActivity.this, "Bạn chưa nhập địa chỉ cụ thể", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String strname = Utils.user_food.getUsername();
                        String stremail = Utils.user_food.getEmail();
                        String strmobile = Utils.user_food.getMobile();
                        int id = Utils.user_food.getId();
                        String straddress = Utils.user_food.getAddress();
                        String strward = Utils.user_food.getWard();
                        String stress = Utils.user_food.getStreet();
                        long tongtien = getIntent().getLongExtra("tongtien", 0);
                        Date orderDate = new Date();
                        Log.d("test", new Gson().toJson(Utils.mangmua));
                        compositeDisposable.add(foodApi.getOrder(strname,stremail,straddress,strward,stress,strmobile,id,String.valueOf(tongtien),soluong,new Gson().toJson(Utils.mangmua), orderDate)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        addProductModel -> {
                                            Toast.makeText(getApplicationContext(),"Thành công", Toast.LENGTH_SHORT).show();
                                            for(int i = 0; i<Utils.mangmua.size();i++){
                                                CartFood cartFood = Utils.mangmua.get(i);
                                                if(Utils.mangcart.contains(cartFood)){
                                                    Utils.mangcart.remove(cartFood);
                                                }
                                            }
                                            Utils.mangmua.clear();
                                            Paper.book().write("cartfood", Utils.mangcart);
                                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        },
                                        throwable -> {
                                            Toast.makeText(getApplicationContext(),throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                ));
                    }
                }
            });
        }
        momo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_address = duong.getText().toString().trim();
                if (TextUtils.isEmpty(str_address)) {
                    Toast.makeText(ToPayActivity.this, "Bạn chưa nhập địa chỉ cụ thể", Toast.LENGTH_SHORT).show();
                } else {
                    String strname = Utils.user_food.getUsername();
                    String stremail = Utils.user_food.getEmail();
                    String strmobile = Utils.user_food.getMobile();
                    int id = Utils.user_food.getId();
                    String straddress = Utils.user_food.getAddress();
                    String strward = Utils.user_food.getWard();
                    String stress = Utils.user_food.getStreet();
                    long tongtien = getIntent().getLongExtra("tongtien", 0);
                    Date orderDate = new Date();
                    Log.d("test", new Gson().toJson(Utils.mangmua));
                    compositeDisposable.add(foodApi.getOrder(strname, stremail, straddress, strward, stress, strmobile, id, String.valueOf(tongtien), soluong, new Gson().toJson(Utils.mangmua), orderDate)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    addProductModel -> {
                                        Toast.makeText(getApplicationContext(), "Thành công", Toast.LENGTH_SHORT).show();
                                        for (int i = 0; i < Utils.mangmua.size(); i++) {
                                            CartFood cartFood = Utils.mangmua.get(i);
                                            if (Utils.mangcart.contains(cartFood)) {
                                                Utils.mangcart.remove(cartFood);
                                            }
                                        }
                                        Utils.mangmua.clear();
                                        Paper.book().write("cartfood", Utils.mangcart);
                                        id_order = Integer.parseInt(addProductModel.getId_order());
                                        requestPayment(addProductModel.getId_order());
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }
    private void requestPayment(String id_order) {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);


        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", amount);
        eventValue.put("orderId", id_order); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", id_order); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", "0"); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());

        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);


    }
    //Get token callback from MoMo app an submit to server side
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    Log.d("thanhcong",data.getStringExtra("message"));

                    String token = data.getStringExtra("data"); //Token response
                    compositeDisposable.add(foodApi.getMomo(id_order,token)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                addProductModel -> {
                                    if(addProductModel.isSuccess()){
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                },
                                    throwable -> {
                                        Log.d("Lỗi", throwable.getMessage());
                                    }
                            ));

                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }

                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                    } else {
                        Log.d("thanhcong","Không thành công");
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    Log.d("thanhcong","Không thành công");
                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                    Log.d("thanhcong","Không thành công");
                } else {
                    //TOKEN FAIL
                    Log.d("thanhcong","Không thành công");;
                }
            } else {
                Log.d("thanhcong","Không thành công");
            }
        } else {
            Log.d("thanhcong","Không thành công");
        }
    }

    private void slItem() {

        soluong = 0;
        for (int i = 0; i < Utils.mangmua.size(); i++) {
            CartFood cartFood = Utils.mangmua.get(i);
            soluong = soluong + cartFood.getSoluong();
        }
    }
}
