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
import com.food.foodapp.models.Rating;
import com.food.foodapp.models.User;
import com.food.foodapp.utils.Utils;

import java.util.List;

public class QlRatingAdapter extends RecyclerView.Adapter<QlRatingAdapter.MyViewHolder> {
    Context context;
    List<Rating> ratings;

    public QlRatingAdapter(Context context, List<Rating> ratings) {
        this.context = context;
        this.ratings = ratings;
    }

    @NonNull
    @Override
    public QlRatingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qlrating, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QlRatingAdapter.MyViewHolder holder, int position) {
        Rating rating = ratings.get(position);
        holder.hoten.setText(rating.getUsername());
        holder.noidung.setText(rating.getComment());
        holder.tensp.setText(rating.getTensp());
        if(rating.getHinhanh().contains("http")){
            Glide.with(context).load(rating.getHinhanh()).into(holder.sp);
        }
        else {
            String hinh= Utils.BASE_URL + "images/"+rating.getHinhanh();
            Glide.with(context).load(hinh).into(holder.sp);
        }
    }

    @Override
    public int getItemCount() {
        return ratings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView hoten, noidung, tensp;
        ImageView sp;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hoten = itemView.findViewById(R.id.tenrating);
            noidung = itemView.findViewById(R.id.noidungrating);
            tensp = itemView.findViewById(R.id.tsp);
            sp = itemView.findViewById(R.id.imagess);
        }
    }
}
