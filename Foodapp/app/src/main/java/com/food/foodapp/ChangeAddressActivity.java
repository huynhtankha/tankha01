package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.food.foodapp.activity.HomeActivity;
import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChangeAddressActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodApi foodApi;
    EditText editName, editMobile, editStress;
    Spinner spquanhuyen, spxa;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_address);
        toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thay đổi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        editStress = findViewById(R.id.stress);
        editStress.setText(Utils.user_food.getStreet());
        editName = findViewById(R.id.updatename1);
        editMobile = findViewById(R.id.updatemobile1);
        editName.setText(Utils.user_food.getUsername());
        editMobile.setText(Utils.user_food.getMobile());
        spquanhuyen = (Spinner) findViewById(R.id.spquan1);
        spxa = (Spinner) findViewById(R.id.spxa1);

        List<String> stringList = new ArrayList<>();
        stringList.add("Vui lòng chọn"); // Mục mặc định
        stringList.add("Quận Cái Răng");
        stringList.add("Quận Ninh Kiều");
        stringList.add("Quận Bình Thủy");
        stringList.add("Quận Ô Môn");
        stringList.add("Quận Thốt Nốt");
        stringList.add("Huyện Cờ Đỏ");
        stringList.add("Huyện Phong Điền");
        stringList.add("Huyện Thới Lai");
        stringList.add("Huyện Vĩnh Thạnh");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, stringList);
        spquanhuyen.setAdapter(adapter);
        List<String> phuongList = new ArrayList<>();
        phuongList.add("Vui lòng chọn"); // Mục mặc định
        ArrayAdapter<String> phuongAdapter = new ArrayAdapter<>(ChangeAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, phuongList);
        spxa.setAdapter(phuongAdapter);

        spquanhuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedQuan = stringList.get(position);

                if (selectedQuan.equals("Quận Cái Răng")) {
                    // Nếu chọn "Quận Cái Răng", cập nhật các tùy chọn trong spinner "spin"
                    phuongList.clear();
                    phuongList.add("Vui lòng chọn"); // Mục mặc định
                    phuongList.add("Phường Tân Phú");
                    phuongList.add("Phường Hưng Phú");
                    phuongList.add("Phường Hưng Thạnh");
                    phuongList.add("Phường Lê Bình");
                    phuongList.add("Phường Phú Thứ");
                    phuongList.add("Phường Thường Thạnh");
                    phuongAdapter.notifyDataSetChanged();
                } else if (selectedQuan.equals("Quận Ninh Kiều")) {
                    // Nếu chọn "Quận Ninh Kiều", cập nhật các tùy chọn trong spinner "spin"
                    phuongList.clear();
                    phuongList.add("Vui lòng chọn"); // Mục mặc định
                    phuongList.add("Phường An Bình");
                    phuongList.add("Phường An Cư");
                    phuongList.add("Phường An Hoà");
                    phuongList.add("Phường An Khánh");
                    phuongList.add("Phường An Nghiệp");
                    phuongList.add("Phường An Phú");
                    phuongList.add("Phường Cái Khế");
                    phuongList.add("Phường Hưng Lợi");
                    phuongList.add("Phường Tân An");
                    phuongList.add("Phường Thới Bình");
                    phuongList.add("Phường Xuân Khánh");
                    phuongAdapter.notifyDataSetChanged();
                } else if (selectedQuan.equals("Quận Bình Thủy")) {
                    // Nếu chọn "Quận Ninh Kiều", cập nhật các tùy chọn trong spinner "spin"
                    phuongList.clear();
                    phuongList.add("Vui lòng chọn"); // Mục mặc định
                    phuongList.add("Phường An Thới");
                    phuongList.add("Phường Bình Tuỷ");
                    phuongList.add("Phường Bùi Hữu Nghĩa");
                    phuongList.add("Phường Long Hoà");
                    phuongList.add("Phường Long Tuyền");
                    phuongList.add("Phường Thới An Đông");
                    phuongList.add("Phường Trà An");
                    phuongList.add("Phường Trà Nóc");
                    phuongAdapter.notifyDataSetChanged();
                }
                else if (selectedQuan.equals("Quận Ô Môn")) {
                    // Nếu chọn "Quận Ninh Kiều", cập nhật các tùy chọn trong spinner "spin"
                    phuongList.clear();
                    phuongList.add("Vui lòng chọn"); // Mục mặc định
                    phuongList.add("Phường Châu Văn Liêm");
                    phuongList.add("Phường Long Hưng");
                    phuongList.add("Phường Phước Thới");
                    phuongList.add("Phường Thới An");
                    phuongList.add("Phường Thới Hoà");
                    phuongList.add("Phường Thới Long");
                    phuongList.add("Phường Trường Lạc");
                    phuongAdapter.notifyDataSetChanged();
                }
                else if (selectedQuan.equals("Quận Thốt Nốt")) {
                    // Nếu chọn "Quận Ninh Kiều", cập nhật các tùy chọn trong spinner "spin"
                    phuongList.clear();
                    phuongList.add("Vui lòng chọn"); // Mục mặc định
                    phuongList.add("Phường Tân Hưng");
                    phuongList.add("Phường Tân Lộc");
                    phuongList.add("Phường Thạnh Hoà");
                    phuongList.add("Phường Thốt Nốt");
                    phuongList.add("Phường Thới Thuận");
                    phuongList.add("Phường Thuận An");
                    phuongList.add("Phường Thuận Hưng");
                    phuongList.add("Phường Trung Kiên");
                    phuongList.add("Phường Trung Nhứt");
                    phuongAdapter.notifyDataSetChanged();
                }
                else if (selectedQuan.equals("Huyện Cờ Đỏ")) {
                    // Nếu chọn "Quận Ninh Kiều", cập nhật các tùy chọn trong spinner "spin"
                    phuongList.clear();
                    phuongList.add("Vui lòng chọn"); // Mục mặc định
                    phuongList.add("Thị trấn Cờ Đỏ");
                    phuongList.add("Xã Đông Hiệp");
                    phuongList.add("Xã Đông Thắng");
                    phuongList.add("Xã Thạnh Phú");
                    phuongList.add("Xã Thới Đồng");
                    phuongList.add("Xã Thới Hưng");
                    phuongList.add("Xã Thới Xuân");
                    phuongList.add("Xã Trung An");
                    phuongList.add("Xã Trung Hưng");
                    phuongList.add("Xã Trung Thạnh");
                    phuongAdapter.notifyDataSetChanged();
                }
                else if (selectedQuan.equals("Huyện Phong Điền")) {
                    // Nếu chọn "Quận Ninh Kiều", cập nhật các tùy chọn trong spinner "spin"
                    phuongList.clear();
                    phuongList.add("Vui lòng chọn"); // Mục mặc định
                    phuongList.add("Thị trấn Phong Điền");
                    phuongList.add("Xã Giai Xuân");
                    phuongList.add("Xã Mỹ Khánh");
                    phuongList.add("Xã Nhơn Ái");
                    phuongList.add("Xã Nhơn Nghĩa");
                    phuongList.add("Xã Tân Thới");
                    phuongList.add("Xã Tân Thới");
                    phuongAdapter.notifyDataSetChanged();
                }
                else if (selectedQuan.equals("Huyện Thới Lai")) {
                    // Nếu chọn "Quận Ninh Kiều", cập nhật các tùy chọn trong spinner "spin"
                    phuongList.clear();
                    phuongList.add("Vui lòng chọn"); // Mục mặc định
                    phuongList.add("Thị trấn Thới Lai");
                    phuongList.add("Phường Tân Lộc");
                    phuongList.add("Xã Định Môn");
                    phuongList.add("Xã Đông Bình");
                    phuongList.add("Xã Đông Thuận");
                    phuongList.add("Xã Thân Thạnh");
                    phuongList.add("Xã Thới Tân");
                    phuongList.add("Xã Thới Thạnh");
                    phuongList.add("Xã Trường Thắng");
                    phuongList.add("Xã Trường Thành");
                    phuongList.add("Xã Trường Xuân");
                    phuongList.add("Xã Trường Xuân A");
                    phuongList.add("Xã Trường Xuân B");
                    phuongList.add("Xã Xuân Thắng");
                    phuongAdapter.notifyDataSetChanged();
                }
                else if (selectedQuan.equals("Huyện Vĩnh Thạnh")) {
                    // Nếu chọn "Quận Ninh Kiều", cập nhật các tùy chọn trong spinner "spin"
                    phuongList.clear();
                    phuongList.add("Vui lòng chọn"); // Mục mặc định
                    phuongList.add("Thị trấn Vĩnh Thạnh");
                    phuongList.add("Thị trấn Thạnh An");
                    phuongList.add("Xã Thạnh An");
                    phuongList.add("Xã Thạnh Lộc");
                    phuongList.add("Xã Thạnh Lợi");
                    phuongList.add("Xã Thạnh Mỹ");
                    phuongList.add("Xã Thạnh Quới");
                    phuongList.add("Xã Thạnh Thắng");
                    phuongList.add("Xã Thạnh Tiến");
                    phuongList.add("Xã Vĩnh Bình");
                    phuongList.add("Xã Vĩnh Trinh");
                    phuongAdapter.notifyDataSetChanged();
                }
                else {
                    // Xử lý các lựa chọn Quận khác theo cách cần thiết
                    // Bạn có thể đặt các tùy chọn khác cho spinner "spin" ở đây
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý trường hợp không có gì được chọn (nếu cần)
            }
        });
        String baseUrl = Utils.BASE_URL;
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);

        Button dongYButton = findViewById(R.id.dongy1);
        dongYButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy giá trị họ tên và số điện thoại từ các trường nhập liệu

                String newName = editName.getText().toString().trim();
                String newMobile = editMobile.getText().toString().trim();
                String newStress = editStress.getText().toString().trim();
                String stringaddress = ((Spinner) findViewById(R.id.spquan1)).getSelectedItem().toString();
                String stringphuong = ((Spinner) findViewById(R.id.spxa1)).getSelectedItem().toString();

                // Kiểm tra nếu các trường không được điền
                if (newName.isEmpty() || newMobile.isEmpty() || stringaddress.equals("Vui lòng chọn") || stringphuong.equals("Vui lòng chọn")) {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu tất cả thông tin đã được nhập đầy đủ, thực hiện các thao tác cập nhật
                    compositeDisposable.add(foodApi.getUpdatet(Utils.user_food.getId(), newName, newMobile, stringaddress, stringphuong, newStress)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    response -> {
                                        if (response.isSuccess()) {
                                            // Cập nhật thông tin người dùng sau khi cập nhật thành công
                                            Utils.user_food.setUsername(newName);
                                            Utils.user_food.setMobile(newMobile);
                                            Utils.user_food.setAddress(stringaddress);
                                            Utils.user_food.setWard(stringphuong);
                                            Utils.user_food.setStreet(newStress);

                                            Toast.makeText(ChangeAddressActivity.this, "Đổi thông tin thành công", Toast.LENGTH_SHORT).show();

                                            // Chuyển đến trang HomeActivity
                                            Intent intent = new Intent(ChangeAddressActivity.this, ToPayActivity.class);
                                            intent.putExtra("tongtien", getIntent().getLongExtra("tongtien", 0));
                                            startActivity(intent);
                                            finish(); // Kết thúc Activity hiện tại để ngăn quay lại.
                                        } else {
                                            Toast.makeText(ChangeAddressActivity.this, "Đổi thông tin thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(ChangeAddressActivity.this, "Lỗi: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }
}
