package com.food.foodapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.food.foodapp.CartActivity;
import com.food.foodapp.R;
import com.food.foodapp.models.CartFood;
import com.food.foodapp.models.EventBus.PlusEvent;
import com.food.foodapp.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

import io.paperdb.Paper;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<CartFood> cartFoodList;

    public CartAdapter(Context context, List<CartFood> cartFoodList) {
        this.context = context;
        this.cartFoodList = cartFoodList;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewcart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        CartFood cartFood = cartFoodList.get(position);
        holder.tenspcart.setText(cartFood.getTensp());
        holder.slcart.setText(String.valueOf(cartFood.getSoluong()));
        Glide.with(context).load(cartFood.getHinhanh()).into(holder.imgcart);
        if(cartFood.getHinhanh().contains("http")){
            Glide.with(context).load(cartFood.getHinhanh()).into(holder.imgcart);
        }
        else {
            String hinh= Utils.BASE_URL + "images/"+cartFood.getHinhanh();
            Glide.with(context).load(hinh).into(holder.imgcart);
        }
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Utils.mangcart.get(holder.getAdapterPosition()).setCheck(true);
                    if(!Utils.mangmua.contains(cartFood)){
                        Utils.mangmua.add(cartFood);
                    }


                    EventBus.getDefault().postSticky(new PlusEvent());
                }else {
                    Utils.mangcart.get(holder.getAdapterPosition()).setCheck(false);
                    for(int i=0 ; i<Utils.mangmua.size(); i++){
                        if(Utils.mangmua.get(i).getIdsp() == cartFood.getIdsp()){
                            Utils.mangmua.remove(i);
                            EventBus.getDefault().postSticky(new PlusEvent());
                        }
                    }
                }
            }
        });
        holder.checkBox.setChecked(cartFood.isCheck());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.giaspcart.setText(decimalFormat.format(cartFood.getGiasp()) + "đ");

        long gia = cartFood.getSoluong() * cartFood.getGiasp();
        holder.tongcart.setText(decimalFormat.format(gia) + "đ");
        EventBus.getDefault().postSticky(new PlusEvent());


        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = cartFood.getSoluong();
                if (currentQuantity < 10) { // Kiểm tra số lượng không vượt quá 10
                    currentQuantity++; // Tăng số lượng lên 1
                    cartFood.setSoluong(currentQuantity); // Cập nhật đối tượng CartFood

                    // Cập nhật giao diện người dùng để hiển thị số lượng đã cập nhật
                    holder.slcart.setText(String.valueOf(currentQuantity));

                    // Cập nhật tổng giá mới
                    long newTotalPrice = currentQuantity * cartFood.getGiasp();
                    holder.tongcart.setText(decimalFormat.format(newTotalPrice) + "đ");

                    // Gửi sự kiện thông báo thay đổi
                    EventBus.getDefault().postSticky(new PlusEvent());
                }
            }
        });

// Minus button click listener
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = cartFood.getSoluong();
                if (currentQuantity > 1) {
                    currentQuantity--;
                    cartFood.setSoluong(currentQuantity);

                    holder.slcart.setText(String.valueOf(currentQuantity));

                    long newTotalPrice = currentQuantity * cartFood.getGiasp();
                    holder.tongcart.setText(decimalFormat.format(newTotalPrice) + "đ");

                    // Gửi sự kiện thông báo thay đổi
                    EventBus.getDefault().postSticky(new PlusEvent());

                } else {
                    // Nếu số lượng nhỏ hơn 1, xóa sản phẩm khỏi giỏ hàng
                    Utils.mangmua.remove(cartFood);
                    cartFoodList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, cartFoodList.size());
                    Paper.book().write("cartfood", Utils.mangcart);

                    EventBus.getDefault().postSticky(new PlusEvent());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartFoodList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgcart, minus, plus;
        TextView tenspcart, giaspcart, tongcart, slcart;
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgcart = itemView.findViewById(R.id.imgcart);
            tenspcart = itemView.findViewById(R.id.tenspcart);
            giaspcart = itemView.findViewById(R.id.giasp);
            tongcart = itemView.findViewById(R.id.tvtong);
            slcart = itemView.findViewById(R.id.slcart);
            minus = itemView.findViewById(R.id.minus);
            plus = itemView.findViewById(R.id.plus);
            checkBox = itemView.findViewById(R.id.checkmuahang);


        }
    }
}
