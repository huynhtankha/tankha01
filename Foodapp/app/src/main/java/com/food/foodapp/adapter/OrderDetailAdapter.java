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
import com.food.foodapp.models.Item;
import com.food.foodapp.models.Order;
import com.food.foodapp.utils.Utils;

import android.view.LayoutInflater;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder> {
    Context context;
    List<Item> itemList;

    public OrderDetailAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdetail,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.tensp.setText(item.getTensp() + "");
        holder.slluong.setText("x" + item.getQuantity()+"");

        //Glide.with(context).load(item.getHinhanh()).into(holder.imageView);
        if(item.getHinhanh().contains("http")){
            Glide.with(context).load(item.getHinhanh()).into(holder.imageView);
        }
        else {
            String hinh= Utils.BASE_URL + "images/"+item.getHinhanh();
            Glide.with(context).load(hinh).into(holder.imageView);
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.gia.setText(decimalFormat.format(Double.parseDouble(item.getGia())) + "đ");
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tensp,slluong, gia;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagesp);
            tensp = itemView.findViewById(R.id.ten);
            slluong = itemView.findViewById(R.id.quantity);
            gia = itemView.findViewById(R.id.giaa);


        }
    }
}
