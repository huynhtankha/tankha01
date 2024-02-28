package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.food.foodapp.adapter.PostAdminAdapter;
import com.food.foodapp.adapter.PostsAdpater;
import com.food.foodapp.models.Posts;
import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostAdminMainActivity extends AppCompatActivity {
    RecyclerView post;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodApi foodApi;
    List<Posts> mangPost;
    PostAdminAdapter adpater;
    ImageView add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_admin_main);
        String baseUrl = Utils.BASE_URL;
        // Khởi tạo RetrofitClient với baseUrl
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);
        add = findViewById(R.id.addpost);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddPostActivity.class);
                startActivity(intent);
            }
        });
        post = findViewById(R.id.recyclerViewpost);
        mangPost = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        post.setHasFixedSize(true);
        post.setLayoutManager(layoutManager);
        gatData();

    }

    private void gatData() {
        compositeDisposable.add(foodApi.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        postsModel -> {
                            if(postsModel.isSuccess()){
                                mangPost = postsModel.getResult();
                                adpater = new PostAdminAdapter(getApplicationContext(), mangPost);
                                post.setAdapter(adpater);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Không thấy bài viết" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
    }
}