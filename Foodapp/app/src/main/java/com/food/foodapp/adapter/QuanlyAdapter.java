package com.food.foodapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.food.foodapp.DetailProductActivity;
import com.food.foodapp.Interface.ClickListener;
import com.food.foodapp.R;
import com.food.foodapp.SuaActivity;
import com.food.foodapp.models.AddProductModel;
import com.food.foodapp.models.ProductFood;
import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;

import java.text.DecimalFormat;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuanlyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private Context context;
    private List<ProductFood> array;

    public QuanlyAdapter(Context context, List<ProductFood> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_DATA) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_productql, parent, false);
            return new MyViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            ProductFood productFood = array.get(position);
            myViewHolder.tvtensp.setText(productFood.getTensp());

            // Thay đổi cách xử lý giá sản phẩm ở đây
            try {
                double gia = Double.parseDouble(productFood.getGia());
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                myViewHolder.tvgiasp.setText("Giá " + decimalFormat.format(gia) + "đ");
            } catch (NumberFormatException e) {
                // Xử lý ngoại lệ số không hợp lệ ở đây (có thể thông báo lỗi hoặc thay thế giá trị mặc định)
                myViewHolder.tvgiasp.setText("Giá không hợp lệ");
            }
            if(productFood.getHinhanh().contains("http")){
                Glide.with(context).load(productFood.getHinhanh()).into(myViewHolder.imghinhanhsp);
            }else {
                String hinh= Utils.BASE_URL + "images/"+productFood.getHinhanh();
                Glide.with(context).load(hinh).into(myViewHolder.imghinhanhsp);
            }
            myViewHolder.sua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SuaActivity.class);
                    intent.putExtra("chitiet", productFood);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        } else {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return array.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvtensp, tvgiasp;
        ImageView imghinhanhsp, sua, xoa;
        private ClickListener clickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            String baseUrl = Utils.BASE_URL;
            // Khởi tạo RetrofitClient với baseUrl

            sua = itemView.findViewById(R.id.sua);
            xoa = itemView.findViewById(R.id.xoa);
            tvtensp = itemView.findViewById(R.id.protsp);
            tvgiasp = itemView.findViewById(R.id.progiasp);
            imghinhanhsp = itemView.findViewById(R.id.proimg);
            xoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemPosition = getAdapterPosition();
                    if (itemPosition != RecyclerView.NO_POSITION) {
                        ProductFood productFood = array.get(itemPosition);
                        int itemIdToDelete = productFood.getI();
                        deleteItem(itemIdToDelete);
                    }
                }
            });
        }
        public void setClickListener(ClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition(), false);
        }
    }

    private void deleteItem(int itemIdToDelete) {
        String baseUrl = Utils.BASE_URL;
        FoodApi foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(
                foodApi.getDel(itemIdToDelete)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                addProductModel -> {
                                    int position = findItemPosition(itemIdToDelete);
                                    if (position != -1) {
                                        array.remove(position);
                                        notifyItemRemoved(position);
                                        Toast.makeText(context.getApplicationContext(), "Xóa thành công", Toast.LENGTH_LONG).show();
                                    }

                                },
                                throwable -> {
                                    Toast.makeText(context.getApplicationContext(),"Lỗi", Toast.LENGTH_LONG).show();
                                }
                        ));
    }

    private int findItemPosition(int itemIdToDelete) {
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) != null && array.get(i).getI() == itemIdToDelete) {
                return i;
            }
        }
        return -1;
    }

}
