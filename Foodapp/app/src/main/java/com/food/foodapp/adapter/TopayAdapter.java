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
import com.food.foodapp.models.CartFood;
import com.food.foodapp.utils.Utils;

import java.text.DecimalFormat;
import java.util.List;

public class TopayAdapter extends RecyclerView.Adapter<TopayAdapter.MyViewHolder> {
    Context context;
    List<CartFood> cartFoodList;

    public TopayAdapter(Context context, List<CartFood> cartFoodList) {
        this.context = context;
        this.cartFoodList = cartFoodList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topay, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CartFood cartFood = cartFoodList.get(position);

        holder.tensp.setText(cartFood.getTensp());
        holder.soluong.setText("x" + String.valueOf(cartFood.getSoluong()));
        Glide.with(context).load(cartFood.getHinhanh()).into(holder.imagetopay);
        if(cartFood.getHinhanh().contains("http")){
            Glide.with(context).load(cartFood.getHinhanh()).into(holder.imagetopay);
        }
        else {
            String hinh= Utils.BASE_URL + "images/"+cartFood.getHinhanh();
            Glide.with(context).load(hinh).into(holder.imagetopay);
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.giasp.setText(decimalFormat.format(cartFood.getGiasp()) + "đ");
        long gia = cartFood.getSoluong() * cartFood.getGiasp();
        holder.tong.setText(decimalFormat.format(gia) + "đ");

    }

    @Override
    public int getItemCount() {
        return cartFoodList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imagetopay;
        TextView tensp, soluong, giasp, tong;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagetopay = itemView.findViewById(R.id.imagetopay);
            soluong = itemView.findViewById(R.id.quantitytopay);
            tensp = itemView.findViewById(R.id.tensptopay);
            giasp = itemView.findViewById(R.id.giatopay);
            tong = itemView.findViewById(R.id.tongtoppay);
        }
    }
}
