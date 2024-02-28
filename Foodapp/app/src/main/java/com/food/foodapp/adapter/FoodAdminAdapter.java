package com.food.foodapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.food.foodapp.R;
import com.food.foodapp.models.CategoryFood;

import java.util.List;

public class FoodAdminAdapter extends RecyclerView.Adapter<FoodAdminAdapter.MyViewHolder>{
    Context context;
    List<CategoryFood> array;

    public FoodAdminAdapter(Context context, List<CategoryFood> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public FoodAdminAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoryadmin,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdminAdapter.MyViewHolder holder, int position) {
        CategoryFood categoryFood = array.get(position);
        holder.tvten.setText(categoryFood.getTensp());
//        holder.tvgia.setText(categoryFood.getGia());
        Glide.with(context).load(categoryFood.getHinhanh()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvgia, tvten;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvten = itemView.findViewById(R.id.tensp);
            image = itemView.findViewById(R.id.imgsp);
        }
    }
}
