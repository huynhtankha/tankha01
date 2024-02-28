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
import com.food.foodapp.models.User;
import com.food.foodapp.utils.Utils;

import java.util.List;

public class TtUserAdapter extends RecyclerView.Adapter<TtUserAdapter.MyViewHolder>{
    Context context;
    List<User> usersList;

    public TtUserAdapter(Context context, List<User> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public TtUserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TtUserAdapter.MyViewHolder holder, int position) {
        User user = usersList.get(position);
        holder.ten.setText(user.getUsername());
        holder.email.setText(user.getEmail());
        holder.sdt.setText(user.getMobile());
        holder.dichi.setText(user.getStreet() +", "+ user.getWard()+", "+ user.getAddress() + ", Thành Phố Cần Thơ");
        if(user.getHinhanh().contains("http")){
            Glide.with(context).load(user.getHinhanh()).into(holder.imageView);
        }
        else {
            String hinh= Utils.BASE_URL + "imagesuser/"+user.getHinhanh();
            Glide.with(context).load(hinh).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView ten, email, sdt, dichi;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ten = itemView.findViewById(R.id.ten);
            email = itemView.findViewById(R.id.email);
            sdt = itemView.findViewById(R.id.sdtuser);
            dichi = itemView.findViewById(R.id.diachi);
            imageView = itemView.findViewById(R.id.imageuser);
        }
    }
}
