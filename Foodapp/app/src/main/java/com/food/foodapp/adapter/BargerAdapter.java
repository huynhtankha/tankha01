package com.food.foodapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class BargerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<ProductFood> array;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public BargerAdapter(Context context, List<ProductFood> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_DATA){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barger,parent,false);
            return new MyViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading,parent,false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyViewHolder){
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            ProductFood product = array.get(position);
            myViewHolder.tvtensp.setText(product.getTensp());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            myViewHolder.tvgiasp.setText("Giá: " + decimalFormat.format(Double.parseDouble(product.getGia()))+"đ");
            if(product.getHinhanh().contains("http")){
                Glide.with(context).load(product.getHinhanh()).into(myViewHolder.imghinhanhsp);
            }
            else {
                String hinh= Utils.BASE_URL + "images/"+product.getHinhanh();
                Glide.with(context).load(hinh).into(myViewHolder.imghinhanhsp);
            }

            myViewHolder.setClickListener(new ClickListener() {
                @Override
                public void onClick(View view, int pos, boolean isClick) {
                    if(!isClick){
                        Intent intent = new Intent(context, DetailProductActivity.class);
                        intent.putExtra("chitiet", product);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }else {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);

        }
    }

    @Override
    public int getItemViewType(int position) {

        return array.get(position)==null ? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);

        }
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvtensp, tvgiasp, tvnoidung, idsp;
        ImageView imghinhanhsp, qr;
        private ClickListener clickListener;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            tvtensp=itemView.findViewById(R.id.protsp);
            tvgiasp=itemView.findViewById(R.id.progiasp);
//            qr = itemView.findViewById(R.id.qrsp);
//            idsp = itemView.findViewById(R.id.idsp);
//            tvnoidung=itemView.findViewById(R.id.prond);
            imghinhanhsp=itemView.findViewById(R.id.proimg);
            itemView.setOnClickListener(this);
        }

        public void setClickListener(ClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition(),false);
        }
    }

}
