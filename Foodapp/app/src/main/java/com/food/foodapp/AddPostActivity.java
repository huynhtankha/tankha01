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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.food.foodapp.models.AddProductModel;
import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostActivity extends AppCompatActivity {
    EditText tenpost, hinhanhpost, noidungpost;
    Button dongypost;
    ImageView postima;
    String mediaPath;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodApi foodApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        String baseUrl = Utils.BASE_URL;
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);
        tenpost = findViewById(R.id.namepost);
        hinhanhpost = findViewById(R.id.impost);
        noidungpost = findViewById(R.id.noidungpost1);
        postima = findViewById(R.id.filepost);
        dongypost = findViewById(R.id.dongypost);
        postima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(AddPostActivity.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });
        dongypost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringtsp = tenpost.getText().toString().trim();
                String stringhinhanh = hinhanhpost.getText().toString().trim();
                String stringnoidung = noidungpost.getText().toString().trim();
                if(TextUtils.isEmpty(stringtsp)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(stringhinhanh)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập Password", Toast.LENGTH_SHORT).show();
                }  else if (TextUtils.isEmpty(stringnoidung)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập Mobile", Toast.LENGTH_SHORT).show();
                } else {
                    compositeDisposable.add(foodApi.getaddpost(stringtsp,stringhinhanh,stringnoidung)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    addProductModel -> {
                                        if(addProductModel.isSuccess()){
                                            Toast.makeText(getApplicationContext(), addProductModel.getMessage(), Toast.LENGTH_SHORT).show();
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

        Call<AddProductModel> call = foodApi.uploadFilepost(fileToUpload);
        call.enqueue(new Callback<AddProductModel>() {
            @Override
            public void onResponse(Call<AddProductModel> call, Response<AddProductModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AddProductModel serverResponse = response.body();
                    if (serverResponse.isSuccess()) {
                        // Update the hinhanh EditText with the image name
                        hinhanhpost.setText(serverResponse.getName());
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