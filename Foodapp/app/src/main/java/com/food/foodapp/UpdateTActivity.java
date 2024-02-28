package com.food.foodapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.food.foodapp.activity.HomeActivity;
import com.food.foodapp.activity.MainActivity;
import com.food.foodapp.models.AddProductModel;
import com.food.foodapp.models.User;
import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateTActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodApi foodApi;
    ImageView camera;
    EditText editName, editMobile, editDiachi, edithinhanh;
    Spinner spquanhuyen, spxa;
    Toolbar toolbar;
    String mediaPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tactivity);
        String baseUrl = Utils.BASE_URL;
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cập Nhật");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        editDiachi = findViewById(R.id.editdiachi);
        editName = findViewById(R.id.updatename);
        edithinhanh = findViewById(R.id.edithinhanh);
//        editName.setText(Utils.user_food.getUsername());
//        editMobile.setText(Utils.user_food.getMobile());
        editMobile = findViewById(R.id.updatemobile);

        spquanhuyen = (Spinner) findViewById(R.id.spquan);
        spxa = (Spinner) findViewById(R.id.spxa);

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
        ArrayAdapter<String> phuongAdapter = new ArrayAdapter<>(UpdateTActivity.this, android.R.layout.simple_spinner_dropdown_item, phuongList);
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
        camera = findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(UpdateTActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });
        Button dongYButton = findViewById(R.id.dongy);
        dongYButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newhinhanh = edithinhanh.getText().toString().trim();
                String newDiachi = editDiachi.getText().toString().trim();
                String newName = editName.getText().toString().trim();
                String newMobile = editMobile.getText().toString().trim();
                String stringaddress = ((Spinner) findViewById(R.id.spquan)).getSelectedItem().toString();
                String stringphuong = ((Spinner) findViewById(R.id.spxa)).getSelectedItem().toString();

                // Kiểm tra nếu các trường không được điền
                if (newName.isEmpty() || newMobile.isEmpty() || stringaddress.equals("Vui lòng chọn") || stringphuong.equals("Vui lòng chọn")) {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Nếu tất cả thông tin đã được nhập đầy đủ, thực hiện các thao tác cập nhật
                    compositeDisposable.add(foodApi.getUpdatett(newhinhanh, newName, newMobile, stringaddress, stringphuong,newDiachi,Utils.user_food.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    response -> {
                                        if (response.isSuccess()) {
                                            Utils.user_food.setUsername(newName);
                                            Utils.user_food.setHinhanh(newhinhanh);
                                            Utils.user_food.setMobile(newMobile);
                                            Utils.user_food.setAddress(stringaddress);
                                            Utils.user_food.setWard(stringphuong);
                                            Toast.makeText(UpdateTActivity.this, "Đổi thông tin thành công", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(UpdateTActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(UpdateTActivity.this, "Đổi thông tin thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(UpdateTActivity.this, "Lỗi: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }
    private String getPath(Uri uri){
        String result;
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if(cursor==null){
            result = uri.getPath();
        }else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mediaPath = data.getDataString();
        uploadMultipleFiles();
        Log.d("log", "onActivityResult"+ mediaPath);
    }

    private void uploadMultipleFiles() {
        Uri uri = Uri.parse(mediaPath);
        File file = new File(getPath(uri));
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        Call<AddProductModel> call = foodApi.uploadFileuser(fileToUpload);
        call.enqueue(new Callback<AddProductModel>() {
            @Override
            public void onResponse(Call<AddProductModel> call, Response<AddProductModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AddProductModel serverResponse = response.body();
                    if (serverResponse.isSuccess()) {
                        // Update the hinhanh EditText with the image name
                        edithinhanh.setText(serverResponse.getName());
                        Glide.get(getApplicationContext()).clearMemory();
                        new Thread(() -> Glide.get(getApplicationContext()).clearDiskCache()).start();
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("Response", "Server response not successful");
                }
            }

            @Override
            public void onFailure(Call<AddProductModel> call, Throwable t) {
                Log.d("Log", "Upload failed: " + t.getMessage());
            }
        });
    }
}