package com.food.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;
import com.food.foodapp.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.http.Field;

public class SignupActivity extends AppCompatActivity {
    private static final int FILE_PICKER_REQUEST_CODE = 1;
    EditText email, pass, xnpass, mobile, name;
    ImageView signup;
    TextView tvdn;
    FirebaseAuth firebaseAuth;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodApi foodApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
//        Spinner spinner = findViewById(R.id.spinner);
//        Spinner spin = findViewById(R.id.spi);
//        List<String> stringList = new ArrayList<>();
//        stringList.add("Vui lòng chọn"); // Mục mặc định
//        stringList.add("Quận Cái Răng");
//        stringList.add("Quận Ninh Kiều");
//        stringList.add("Quận Bình Thủy");
//        stringList.add("Quận Ô Môn");
//        stringList.add("Quận Thốt Nốt");
//        stringList.add("Huyện Cờ Đỏ");
//        stringList.add("Huyện Phong Điền");
//        stringList.add("Huyện Thới Lai");
//        stringList.add("Huyện Vĩnh Thạnh");
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, stringList);
////        spinner.setAdapter(adapter);
//        List<String> phuongList = new ArrayList<>();
//        phuongList.add("Vui lòng chọn"); // Mục mặc định
//        ArrayAdapter<String> phuongAdapter = new ArrayAdapter<>(SignupActivity.this, android.R.layout.simple_spinner_dropdown_item, phuongList);
////        spin.setAdapter(phuongAdapter);
////
////        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                String selectedQuan = stringList.get(position);
//
//                if (selectedQuan.equals("Quận Cái Răng")) {
//                    // Nếu chọn "Quận Cái Răng", cập nhật các tùy chọn trong spinner "spin"
//                    phuongList.clear();
//                    phuongList.add("Vui lòng chọn"); // Mục mặc định
//                    phuongList.add("Phường Tân Phú");
//                    phuongList.add("Phường Hưng Phú");
//                    phuongList.add("Phường Hưng Thạnh");
//                    phuongList.add("Phường Lê Bình");
//                    phuongList.add("Phường Phú Thứ");
//                    phuongList.add("Phường Thường Thạnh");
//                    phuongAdapter.notifyDataSetChanged();
//                } else if (selectedQuan.equals("Quận Ninh Kiều")) {
//                    // Nếu chọn "Quận Ninh Kiều", cập nhật các tùy chọn trong spinner "spin"
//                    phuongList.clear();
//                    phuongList.add("Vui lòng chọn"); // Mục mặc định
//                    phuongList.add("Phường An Bình");
//                    phuongList.add("Phường An Cư");
//                    phuongList.add("Phường An Hoà");
//                    phuongList.add("Phường An Khánh");
//                    phuongList.add("Phường An Nghiệp");
//                    phuongList.add("Phường An Phú");
//                    phuongList.add("Phường Cái Khế");
//                    phuongList.add("Phường Hưng Lợi");
//                    phuongList.add("Phường Tân An");
//                    phuongList.add("Phường Thới Bình");
//                    phuongList.add("Phường Xuân Khánh");
//                    phuongAdapter.notifyDataSetChanged();
//                } else if (selectedQuan.equals("Quận Bình Thủy")) {
//                    // Nếu chọn "Quận Ninh Kiều", cập nhật các tùy chọn trong spinner "spin"
//                    phuongList.clear();
//                    phuongList.add("Vui lòng chọn"); // Mục mặc định
//                    phuongList.add("Phường An Thới");
//                    phuongList.add("Phường Bình Tuỷ");
//                    phuongList.add("Phường Bùi Hữu Nghĩa");
//                    phuongList.add("Phường Long Hoà");
//                    phuongList.add("Phường Long Tuyền");
//                    phuongList.add("Phường Thới An Đông");
//                    phuongList.add("Phường Trà An");
//                    phuongList.add("Phường Trà Nóc");
//                    phuongAdapter.notifyDataSetChanged();
//                }
//                else if (selectedQuan.equals("Quận Ô Môn")) {
//                    // Nếu chọn "Quận Ninh Kiều", cập nhật các tùy chọn trong spinner "spin"
//                    phuongList.clear();
//                    phuongList.add("Vui lòng chọn"); // Mục mặc định
//                    phuongList.add("Phường Châu Văn Liêm");
//                    phuongList.add("Phường Long Hưng");
//                    phuongList.add("Phường Phước Thới");
//                    phuongList.add("Phường Thới An");
//                    phuongList.add("Phường Thới Hoà");
//                    phuongList.add("Phường Thới Long");
//                    phuongList.add("Phường Trường Lạc");
//                    phuongAdapter.notifyDataSetChanged();
//                }
//                else if (selectedQuan.equals("Quận Thốt Nốt")) {
//                    // Nếu chọn "Quận Ninh Kiều", cập nhật các tùy chọn trong spinner "spin"
//                    phuongList.clear();
//                    phuongList.add("Vui lòng chọn"); // Mục mặc định
//                    phuongList.add("Phường Tân Hưng");
//                    phuongList.add("Phường Tân Lộc");
//                    phuongList.add("Phường Thạnh Hoà");
//                    phuongList.add("Phường Thốt Nốt");
//                    phuongList.add("Phường Thới Thuận");
//                    phuongList.add("Phường Thuận An");
//                    phuongList.add("Phường Thuận Hưng");
//                    phuongList.add("Phường Trung Kiên");
//                    phuongList.add("Phường Trung Nhứt");
//                    phuongAdapter.notifyDataSetChanged();
//                }
//                else if (selectedQuan.equals("Huyện Cờ Đỏ")) {
//                    // Nếu chọn "Quận Ninh Kiều", cập nhật các tùy chọn trong spinner "spin"
//                    phuongList.clear();
//                    phuongList.add("Vui lòng chọn"); // Mục mặc định
//                    phuongList.add("Thị trấn Cờ Đỏ");
//                    phuongList.add("Xã Đông Hiệp");
//                    phuongList.add("Xã Đông Thắng");
//                    phuongList.add("Xã Thạnh Phú");
//                    phuongList.add("Xã Thới Đồng");
//                    phuongList.add("Xã Thới Hưng");
//                    phuongList.add("Xã Thới Xuân");
//                    phuongList.add("Xã Trung An");
//                    phuongList.add("Xã Trung Hưng");
//                    phuongList.add("Xã Trung Thạnh");
//                    phuongAdapter.notifyDataSetChanged();
//                }
//                else if (selectedQuan.equals("Huyện Phong Điền")) {
//                    // Nếu chọn "Quận Ninh Kiều", cập nhật các tùy chọn trong spinner "spin"
//                    phuongList.clear();
//                    phuongList.add("Vui lòng chọn"); // Mục mặc định
//                    phuongList.add("Thị trấn Phong Điền");
//                    phuongList.add("Xã Giai Xuân");
//                    phuongList.add("Xã Mỹ Khánh");
//                    phuongList.add("Xã Nhơn Ái");
//                    phuongList.add("Xã Nhơn Nghĩa");
//                    phuongList.add("Xã Tân Thới");
//                    phuongList.add("Xã Tân Thới");
//                    phuongAdapter.notifyDataSetChanged();
//                }
//                else if (selectedQuan.equals("Huyện Thới Lai")) {
//                    // Nếu chọn "Quận Ninh Kiều", cập nhật các tùy chọn trong spinner "spin"
//                    phuongList.clear();
//                    phuongList.add("Vui lòng chọn"); // Mục mặc định
//                    phuongList.add("Thị trấn Thới Lai");
//                    phuongList.add("Phường Tân Lộc");
//                    phuongList.add("Xã Định Môn");
//                    phuongList.add("Xã Đông Bình");
//                    phuongList.add("Xã Đông Thuận");
//                    phuongList.add("Xã Thân Thạnh");
//                    phuongList.add("Xã Thới Tân");
//                    phuongList.add("Xã Thới Thạnh");
//                    phuongList.add("Xã Trường Thắng");
//                    phuongList.add("Xã Trường Thành");
//                    phuongList.add("Xã Trường Xuân");
//                    phuongList.add("Xã Trường Xuân A");
//                    phuongList.add("Xã Trường Xuân B");
//                    phuongList.add("Xã Xuân Thắng");
//                    phuongAdapter.notifyDataSetChanged();
//                }
//                else if (selectedQuan.equals("Huyện Vĩnh Thạnh")) {
//                    // Nếu chọn "Quận Ninh Kiều", cập nhật các tùy chọn trong spinner "spin"
//                    phuongList.clear();
//                    phuongList.add("Vui lòng chọn"); // Mục mặc định
//                    phuongList.add("Thị trấn Vĩnh Thạnh");
//                    phuongList.add("Thị trấn Thạnh An");
//                    phuongList.add("Xã Thạnh An");
//                    phuongList.add("Xã Thạnh Lộc");
//                    phuongList.add("Xã Thạnh Lợi");
//                    phuongList.add("Xã Thạnh Mỹ");
//                    phuongList.add("Xã Thạnh Quới");
//                    phuongList.add("Xã Thạnh Thắng");
//                    phuongList.add("Xã Thạnh Tiến");
//                    phuongList.add("Xã Vĩnh Bình");
//                    phuongList.add("Xã Vĩnh Trinh");
//                    phuongAdapter.notifyDataSetChanged();
//                }
//                else {
//                    // Xử lý các lựa chọn Quận khác theo cách cần thiết
//                    // Bạn có thể đặt các tùy chọn khác cho spinner "spin" ở đây
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parentView) {
//                // Xử lý trường hợp không có gì được chọn (nếu cần)
//            }
//        });

        upView();
        initControl();
    }

    private void initControl() {
        tvdn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    private void signUp() {
        String stringname = name.getText().toString().trim();
        String stringemail = email.getText().toString().trim();
        String stringpassword = pass.getText().toString().trim();
        String stringxnpassword = xnpass.getText().toString().trim();
        String stringmobile = mobile.getText().toString().trim();

       // String stringaddress = ((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString();
        //String stringphuong = ((Spinner) findViewById(R.id.spi)).getSelectedItem().toString();

//        if (stringaddress.equals("Vui lòng chọn")) {
//            Toast.makeText(getApplicationContext(), "Vui lòng chọn Quận/Huyện", Toast.LENGTH_SHORT).show();
//        } else if (stringphuong.equals("Vui lòng chọn")) {
//            Toast.makeText(getApplicationContext(), "Vui lòng chọn Phường/Thị trấn/Xã", Toast.LENGTH_SHORT).show();
//        } else if
       if(TextUtils.isEmpty(stringemail)) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(stringpassword)) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập Password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(stringxnpassword)) {
            Toast.makeText(getApplicationContext(), "Vui lòng xác nhận lại Password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(stringmobile)) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập Mobile", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(stringname)) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập Name", Toast.LENGTH_SHORT).show();
        } else {
            if (stringpassword.equals(stringxnpassword)) {
                compositeDisposable.add(foodApi.getSign(stringname, stringemail, stringpassword, stringmobile)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    if (userModel.isSuccess()) {
                                        Utils.user_food.setEmail(stringemail);
                                        Utils.user_food.setPassword(stringpassword);
                                        Toast.makeText(getApplicationContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> {
                                    Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        ));
            } else {
                Toast.makeText(getApplicationContext(), "Password không khớp", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void upView() {
        String baseUrl = Utils.BASE_URL;
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);
        email = findViewById(R.id.editemail);
        pass = findViewById(R.id.editpass);
        xnpass = findViewById(R.id.editxnpass);
        signup = findViewById(R.id.tvsign);
        mobile = findViewById(R.id.editmobile);
        name = findViewById(R.id.editname);
        tvdn = findViewById(R.id.dn);
    }
}
