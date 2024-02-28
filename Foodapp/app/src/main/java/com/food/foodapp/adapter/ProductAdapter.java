package com.food.foodapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
//import com.food.foodapp.DetailActivity;
import com.food.foodapp.DetailProductActivity;
import com.food.foodapp.Interface.ClickListener;
import com.food.foodapp.R;
import com.food.foodapp.activity.MainActivity;
import com.food.foodapp.models.ProductFood;
import com.food.foodapp.utils.Utils;

import java.text.DecimalFormat;
import java.util.List;
import android.view.LayoutInflater;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    Context context;
    List<ProductFood> array;

    public ProductAdapter(Context context, List<ProductFood> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.product,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.MyViewHolder holder, int position) {
        ProductFood productFood = array.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvgia.setText(decimalFormat.format(Double.parseDouble(productFood.getGia()))+"đ");
        holder.txtten.setText(productFood.getTensp());
        Glide.with(context).load(productFood.getHinhanh()).into(holder.img);
        if(productFood.getHinhanh().contains("http")){
            Glide.with(context).load(productFood.getHinhanh()).into(holder.img);
        }
        else {
            String hinh= Utils.BASE_URL + "images/"+productFood.getHinhanh();
            Glide.with(context).load(hinh).into(holder.img);
        }
        holder.setClickListener(new ClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isClick) {
                if(!isClick){
                    Intent intent = new Intent(context, DetailProductActivity.class);
                    intent.putExtra("chitiet", productFood);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvgia, txtten;
        ImageView img;
        private ClickListener clickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtten = itemView.findViewById(R.id.tvproduct);
            tvgia = itemView.findViewById(R.id.tvproduct2);

            img = itemView.findViewById(R.id.imgproduct);
            itemView.setOnClickListener(this);

        }

        public void setClickListener(ClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition(), false);
        }
    }
}
