package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.food.foodapp.activity.MainActivity;
import com.food.foodapp.adapter.PostsAdpater;
import com.food.foodapp.models.Posts;
import com.food.foodapp.models.ProductFood;
import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodApi foodApi;
    List<Posts> mangPost;
    PostsAdpater adpater;
    RecyclerView reviewbaiviet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
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
        boolean isPlaying = true;
        VideoView videoView = findViewById(R.id.video);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.khuma)); // Thay thế bằng tài nguyên video của bạn
        videoView.start(); // Bắt đầu phat video
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (videoView.isPlaying()) {
                        videoView.pause(); // Dừng video nếu nó đang phát
                    } else {
                        videoView.start(); // Tiếp tục phát video nếu nó đã tạm dừng
                    }
                    return true; // Trả về true để xử lý sự kiện và không truyền sự kiện cho các trình nghe khác
                }
                return false; // Trả về false nếu không phải sự kiện chạm vào
            }
        });

        String baseUrl = Utils.BASE_URL;
        // Khởi tạo RetrofitClient với baseUrl
        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);

        reviewbaiviet = findViewById(R.id.reviewbaiviet);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviewbaiviet.setLayoutManager(layoutManager1);
        reviewbaiviet.setHasFixedSize(true);
        mangPost = new ArrayList<>();
        Toolbar baiviet = findViewById(R.id.toolbar6);
        setSupportActionBar(baiviet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        baiviet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        ViewFlipper viewFlipper = findViewById(R.id.viewFlipper);
//        List<String> manghinhanh = new ArrayList<>();
//        manghinhanh.add("https://inan2h.vn/wp-content/uploads/2022/12/in-banner-quang-cao-do-an-7-1.jpg");
//        manghinhanh.add("https://img.pikbest.com/templates/20220325/bg/b012764c410ce.png!sw800");
//        manghinhanh.add("https://inan2h.vn/wp-content/uploads/2022/12/in-banner-quang-cao-do-an-14-1.jpg");
//        for(int i=0; i<manghinhanh.size();i++){
//            ImageView imageView = new ImageView(getApplicationContext());
//            Glide.with(getApplicationContext()).load(manghinhanh.get(i)).into(imageView);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            viewFlipper.addView(imageView);
//
//        }
//        viewFlipper.setFlipInterval(2000);
//        viewFlipper.setAutoStart(true);
//        Animation slidein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.right_item);
//        Animation slideout = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.left);
//        viewFlipper.setInAnimation(slidein);
//        viewFlipper.setInAnimation(slideout);
        getData();
    }

    private void getData() {
        compositeDisposable.add(foodApi.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        postsModel -> {
                            if(postsModel.isSuccess()){
                                mangPost = postsModel.getResult();
                                adpater = new PostsAdpater(getApplicationContext(), mangPost);
                                reviewbaiviet.setAdapter(adpater);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Không thấy bài viết" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
    }
}