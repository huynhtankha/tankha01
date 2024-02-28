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
import com.food.foodapp.models.Rating;
import com.food.foodapp.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.MyViewHolder> {
    Context context;
    List<Rating> ratingList;

    public RatingAdapter(Context context, List<Rating> ratingList) {
        this.context = context;
        this.ratingList = ratingList;
    }

    @NonNull
    @Override
    public RatingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rating, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingAdapter.MyViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Rating rating = ratingList.get(position);
        myViewHolder.comment.setText(rating.getComment());
        myViewHolder.username.setText(rating.getUsername());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());
        myViewHolder.rating.setText(currentDate);
        if(rating.getHinhanh().contains("http")){
            Glide.with(context).load(rating.getHinhanh()).into(myViewHolder.imga);
        }
        else {
            String hinh= Utils.BASE_URL + "imagesuser/"+rating.getHinhanh();
            Glide.with(context).load(hinh).into(myViewHolder.imga);
        }
    }


    @Override
    public int getItemCount() {
        return ratingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView comment, username;
        TextView rating;
        ImageView imga;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.comment);
            username = itemView.findViewById(R.id.usernamerating);
            imga = itemView.findViewById(R.id.imuser);
            rating = itemView.findViewById(R.id.daterating);
        }
    }
}
