package com.food.foodapp.adapter;

import android.content.Context;
import android.print.PrintManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.food.foodapp.Interface.ClickListener;
import com.food.foodapp.OrderDeletedEvent;
import com.food.foodapp.R;

import com.food.foodapp.models.Order;
import com.food.foodapp.models.ProductFood;
import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;

import android.view.LayoutInflater;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemorder,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.txtorder.setText("Đơn hàng: "+ order.getId());
        holder.username.setText("Khách hàng: "+ order.getUsername());
        holder.mobile.setText(order.getMobile());
        holder.sonha.setText("Địa chỉ: " + order.getStreet()+", ");
        holder.phuong.setText(order.getWard()+", ");
        holder.quan.setText(order.getAddress() + ", TP.Cần Thơ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date orderDate = order.getOrder_date();
        String formattedDate = dateFormat.format(orderDate);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        String status = trangthai(order.getXacnhan());
        holder.xacnhan.setText(status);
        switch (status) {
            case "Chờ xác nhận":
                holder.statusImageView.setImageResource(R.drawable.cho);
                holder.statusImageView.setVisibility(View.VISIBLE);
                break;
            case "Chờ lấy hàng":
                holder.statusImageView.setImageResource(R.drawable.hang);
                holder.statusImageView.setVisibility(View.VISIBLE);
                break;
            case "Chờ giao hàng":
                holder.statusImageView.setImageResource(R.drawable.sping);
                holder.statusImageView.setVisibility(View.VISIBLE);
                break;
            case "Đơn hàng hủy":
                holder.statusImageView.setImageResource(R.drawable.huydonhang);
                holder.statusImageView.setVisibility(View.VISIBLE);
                break;
            default:
                holder.statusImageView.setVisibility(View.GONE);
                break;
        }

        holder.ngaygio.setText("Ngày đặt hàng: " + formattedDate);
        holder.tongtien.setText("Tổng tiền: " + decimalFormat.format(Double.parseDouble(order.getTotal())) + "đ");
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recyclerView.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );

        layoutManager.setInitialPrefetchItemCount(order.getItem().size());
        OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(context, order.getItem());
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setAdapter(orderDetailAdapter);
        holder.recyclerView.setRecycledViewPool(viewPool);
    }

    private String trangthai(String status){
        String result = "";
        switch (status){
            case "":
                result = "Chờ xác nhận";
                break;
            case "Chờ lấy hàng":
                result = "Chờ lấy hàng";
                break;
            case "Chờ giao hàng":
                result = "Chờ giao hàng";
                break;
            case "Đơn hàng hủy":
                result = "Đơn hàng hủy";
                break;
        }
        return result;
    }
    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtorder, quan, phuong, sonha, username, mobile, ngaygio, tongtien, xacnhan;
        RecyclerView recyclerView;

        ImageView statusImageView, deleteorder;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tongtien = itemView.findViewById(R.id.tongtotal);
            txtorder = itemView.findViewById(R.id.orderhang);
            recyclerView = itemView.findViewById(R.id.revdetails);
            quan = itemView.findViewById(R.id.textdiachi);
            phuong = itemView.findViewById(R.id.idquan);
            sonha = itemView.findViewById(R.id.textstreet);
            username = itemView.findViewById(R.id.nameuser);
            mobile = itemView.findViewById(R.id.textmobile);
            ngaygio = itemView.findViewById(R.id.orderday);
            xacnhan = itemView.findViewById(R.id.xacnhan);
            statusImageView = itemView.findViewById(R.id.statusImage);
            deleteorder = itemView.findViewById(R.id.delete);
            deleteorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteItem(orderList.get(getAdapterPosition()));
                    }
            });
        }
    }


    private void deleteItem(Order order) {
        String baseUrl = Utils.BASE_URL;
        FoodApi foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);
        int orderId = order.getId();
        if ("Chờ giao hàng".equals(trangthai(order.getXacnhan()))) {
            Toast.makeText(context.getApplicationContext(), "Không thể xóa đơn hàng đang giao hàng", Toast.LENGTH_LONG).show();
            return;
        }
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(
                foodApi.deleteorder(orderId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                addProductModel -> {
                                    orderList.remove(order);
                                    notifyDataSetChanged();
                                    Toast.makeText(context.getApplicationContext(), "Thành công", Toast.LENGTH_LONG).show();
                                },
                                throwable -> {
                                    Toast.makeText(context.getApplicationContext(), "Lỗi", Toast.LENGTH_LONG).show();
                                }
                        ));
    }

}
