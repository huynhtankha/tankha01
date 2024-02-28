package com.food.foodapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.food.foodapp.R;
import com.food.foodapp.models.Posts;

import java.util.List;
import android.view.LayoutInflater;
import android.widget.VideoView;

public class PostsAdpater extends RecyclerView.Adapter<PostsAdpater.MyViewHolder> {
    Context context;
    List<Posts> array;

    public PostsAdpater(Context context, List<Posts> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public PostsAdpater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_posts,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdpater.MyViewHolder holder, int position) {
        Posts posts = array.get(position);
        holder.tenpost.setText(posts.getTen_baiviet());
        Glide.with(context).load(posts.getHinhanh()).into(holder.hinhanh);
        holder.ndposts.setText(posts.getNoidung());

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView hinhanh;
        TextView tenpost, ndposts;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hinhanh = itemView.findViewById(R.id.hinhpost);
            tenpost = itemView.findViewById(R.id.tenposts);
            ndposts = itemView.findViewById(R.id.ndpost);

        }
    }
}
