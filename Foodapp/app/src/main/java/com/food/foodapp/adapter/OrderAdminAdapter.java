//package com.food.foodapp.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.food.foodapp.PdfActivity;
//import com.food.foodapp.R;
//import com.food.foodapp.models.Item;
//import com.food.foodapp.utils.Utils;
//
//import java.text.DecimalFormat;
//import java.util.List;
//
//public class OrderAdminAdapter extends RecyclerView.Adapter<OrderAdminAdapter.MyViewHolder> {
//    Context context;
//    List<Item> itemList;
//
//    public OrderAdminAdapter(Context context, List<Item> itemList) {
//        this.context = context;
//        this.itemList = itemList;
//    }
//
//    @NonNull
//    @Override
//    public OrderAdminAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemorder_detail,parent,false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull OrderAdminAdapter.MyViewHolder holder, int position) {
//        Item item = itemList.get(position);
//
//        holder.tensp.setText(item.getTensp() + "");
//
//        holder.slluong.setText("x" + item.getQuantity()+"");
//        //Glide.with(context).load(item.getHinhanh()).into(holder.imageView);
//        if(item.getHinhanh().contains("http")){
//            Glide.with(context).load(item.getHinhanh()).into(holder.imageView);
//        }
//        else {
//            String hinh= Utils.BASE_URL + "images/"+item.getHinhanh();
//            Glide.with(context).load(hinh).into(holder.imageView);
//        }
//
//        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        holder.gia.setText(decimalFormat.format(Double.parseDouble(item.getGia())) + "đ");
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // When the item is clicked, create an intent to start another activity
//                Intent intent = new Intent(context, PdfActivity.class);
//
//                // Pass the information to the intent
//                intent.putExtra("productName", item.getTensp());
//                intent.putExtra("productQuantity", item.getQuantity());
//                intent.putExtra("productPrice", item.getGia());
//
//                // Start the activity
//                context.startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return itemList.size();
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder{
//        ImageView imageView;
//        TextView tensp,slluong, gia;
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            imageView = itemView.findViewById(R.id.imagesp);
//            tensp = itemView.findViewById(R.id.ten);
//            slluong = itemView.findViewById(R.id.quantity);
//            gia = itemView.findViewById(R.id.giaa);
//        }
//    }
//}
