package com.food.foodapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.food.foodapp.models.AddProductModel;
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

public class AddActivity extends AppCompatActivity {
    Spinner spinner, trangthai;
    EditText tsp, hinhanh, gia, noidung;
    ImageView file;
    Button chapnhan;
    int loai = 0;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodApi foodApi;
    String mediaPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        String baseUrl = Utils.BASE_URL;
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);
        file= findViewById(R.id.file);
        tsp = findViewById(R.id.namesp);
        spinner = findViewById(R.id.spinnner);
        chapnhan = findViewById(R.id.chapnhan);
        hinhanh = findViewById(R.id.imasp);
        gia = findViewById(R.id.moneysp);
        noidung = findViewById(R.id.noidungsp);
        trangthai = findViewById(R.id.spinnner21);
        List<String> stringListtrang = new ArrayList<>();
        stringListtrang.add("Hết sản phẩm");
        stringListtrang.add("Còn sản phẩm");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, stringListtrang);
        trangthai.setAdapter(adapter1);
        trangthai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        List<String> stringList = new ArrayList<>();
        stringList.add("Vui lòng chọn");
        stringList.add("Barger");
        stringList.add("Pizza");
        stringList.add("Drink");
        stringList.add("Donut");
        stringList.add("Hotdog");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, stringList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loai = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(AddActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });
        chapnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedValue = trangthai.getSelectedItem().toString().trim();
                String stringtsp = tsp.getText().toString().trim();
                String stringhinhanh = hinhanh.getText().toString().trim();
                String stringgia = gia.getText().toString().trim();
                String stringnoidung = noidung.getText().toString().trim();
                if(TextUtils.isEmpty(stringtsp)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(stringhinhanh)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập Password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(stringgia)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng xác nhận lại Password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(stringnoidung)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập Mobile", Toast.LENGTH_SHORT).show();
                } else if (loai ==0) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập Name", Toast.LENGTH_SHORT).show();
                } else {
                    compositeDisposable.add(foodApi.getaddProduct(stringtsp,stringhinhanh,stringgia,stringnoidung,(loai),selectedValue)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    addProductModel -> {
                                        if(addProductModel.isSuccess()){
                                            Toast.makeText(getApplicationContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), ProductManagementActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else {
                                            Toast.makeText(getApplicationContext(), addProductModel.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    },throwable -> {
                                        Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_LONG).show();
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

        Call<AddProductModel> call = foodApi.uploadFile(fileToUpload);
        call.enqueue(new Callback<AddProductModel>() {
            @Override
            public void onResponse(Call<AddProductModel> call, Response<AddProductModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AddProductModel serverResponse = response.body();
                    if (serverResponse.isSuccess()) {
                        // Update the hinhanh EditText with the image name
                        hinhanh.setText(serverResponse.getName());
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