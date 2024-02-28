package com.food.foodapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.LayoutInflaterCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.food.foodapp.R;
import com.food.foodapp.models.CategoryFood;

import java.util.List;
import android.view.LayoutInflater;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {
    Context context;
    List<CategoryFood> array;

    public FoodAdapter(Context context, List<CategoryFood> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);

        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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
//            tvgia = itemView.findViewById(R.id.giasp);
            tvten = itemView.findViewById(R.id.tensp);
            image = itemView.findViewById(R.id.imgsp);
        }
    }
}
