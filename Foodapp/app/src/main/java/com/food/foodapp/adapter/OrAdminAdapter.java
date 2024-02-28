package com.food.foodapp.adapter;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Magnifier;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.food.foodapp.PdfActivity;
import com.food.foodapp.R;
import com.food.foodapp.models.Item;
import com.food.foodapp.models.Order;
import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrAdminAdapter extends RecyclerView.Adapter<OrAdminAdapter.MyViewHolder> {
    Context context;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodApi foodApi;

    List<Order> orderList;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    public OrAdminAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrAdminAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemorderadmin,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull OrAdminAdapter.MyViewHolder holder, int position) {
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
        List<String> stringList = new ArrayList<>();
        stringList.add("Chờ xác nhận");
        stringList.add("Chờ lấy hàng");
        stringList.add("Chờ giao hàng");
        stringList.add("Đơn hàng hủy");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, stringList);
        holder.spinner.setAdapter(adapter);
        int selectedIndex = getIndexForStatus(order.getXacnhan(), stringList);
        holder.spinner.setSelection(selectedIndex);
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = stringList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        holder.btnGeneratePdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the createPDF method when the "Generate PDF" button is clicked
                createPDF(context,order);
            }
        });
        holder.tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedItemId = stringList.get(holder.spinner.getSelectedItemPosition());
                String orderId = String.valueOf(order.getId()); // Convert to String
                compositeDisposable.add(foodApi.getUpdatexn(orderId, selectedItemId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                orderModel -> {
                                    Toast.makeText(context, orderModel.getMessage(), Toast.LENGTH_SHORT).show();
                                },
                                throwable -> {
                                    Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        ));
            }
        });

    }

    private void createPDF(Context context, Order order) {
        Bitmap bitmap, scalebmp;
        Date date;
        date = new Date();
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pizzahead);
        scalebmp = Bitmap.createScaledBitmap(bitmap, 1200, 518, false);
        PdfDocument pdfDocument = new PdfDocument();
        Paint myPaint = new Paint();
        Paint titlePaint = new Paint();


        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        canvas.drawBitmap(scalebmp, 0, 0, myPaint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titlePaint.setTextSize(70);
        canvas.drawText("Food Order", 1200 / 2, 270, titlePaint);

        myPaint.setColor(Color.rgb(0,113,188));
        myPaint.setTextSize(30f);
        myPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Điện thoại: 0354446943", 1160, 40, myPaint);
        canvas.drawText("0354446943-0869238167", 1160, 80, myPaint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
        titlePaint.setTextSize(70);
        canvas.drawText("Hóa Đơn", 1200 / 2, 500, titlePaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(35f);
        myPaint.setColor(Color.BLACK);


        canvas.drawText("Khách hàng: " + order.getUsername(), 20, 640, myPaint);
        canvas.drawText("Số điện thoại: " + order.getMobile(), 20, 690, myPaint);
        canvas.drawText("Địa chỉ: " + order.getStreet() + ", " + order.getWard() + ", " + order.getAddress() + " TP.Cần Thơ", 20, 740, myPaint);

        SimpleDateFormat pdfDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedOrderDate = pdfDateFormat.format(order.getOrder_date());
        myPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Mã hóa đơn: " + order.getId(), 1200 - 20, 640, myPaint);
        canvas.drawText("Ngày bán: " + formattedOrderDate, 1200 - 20, 690, myPaint);


        myPaint.setStyle(Paint.Style.STROKE);
        myPaint.setStrokeWidth(2);
        canvas.drawRect(20, 780, 1200 - 20, 860, myPaint);
        myPaint.setTextAlign(Paint.Align.LEFT);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));

        canvas.drawText("STT", 40, 830, myPaint);
        canvas.drawText("Sản phẩm", 150, 830, myPaint);
        canvas.drawText("Giá", 570, 830, myPaint);
        canvas.drawText("Số lượng", 800, 830, myPaint);
        canvas.drawText("Tổng", 970, 830, myPaint);

        canvas.drawLine(140, 790, 140, 840, myPaint);
        canvas.drawLine(560, 790, 560, 840, myPaint);
        canvas.drawLine(790, 790, 790, 840, myPaint);
        canvas.drawLine(960, 790, 960, 840, myPaint);




        int yOffset = 900;
        int stt = 1;
        double grandTotal = 0; // Variable to store the grand total

        for (Item item : order.getItem()) {
            String serialNumber = String.valueOf(stt);
            canvas.drawText(serialNumber, 40, yOffset, myPaint);
            canvas.drawText(item.getTensp(), 140, yOffset, myPaint);


            double gia = Double.parseDouble(String.valueOf(item.getGia()));
            int quantity = Integer.parseInt(String.valueOf(item.getQuantity()));

            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            canvas.drawText(decimalFormat.format(gia)+ "đ", 560, yOffset, myPaint);
            canvas.drawText(String.valueOf(quantity), 790, yOffset, myPaint);
            double total = gia * quantity;
            grandTotal += total;

            canvas.drawText(decimalFormat.format(total)+ "đ", 960, yOffset, myPaint);


            yOffset += 50;
            stt++;
            String orderId = "Đơn hàng: " + order.getId() + ", " + order.getUsername() + ", " + order.getMobile() + "\nĐịa chỉ: " +
                    order.getStreet() + ", " + order.getWard() + ", " + order.getAddress() + " TP.Cần Thơ ";
            for (Item items : order.getItem()) {
                orderId += "\nSản phẩm: " + items.getTensp() + ", Số lượng: " + items.getQuantity() + ", Giá: " + items.getGia() + "đ";
            }

            byte[] orderIdBytes = orderId.getBytes(StandardCharsets.UTF_8);

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(new String(orderIdBytes, StandardCharsets.ISO_8859_1), BarcodeFormat.QR_CODE, 500, 500);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap qrBitmap = barcodeEncoder.createBitmap(bitMatrix);

                canvas.drawBitmap(qrBitmap, 580, 1480, myPaint);

            } catch (WriterException e) {
                e.printStackTrace();
                Toast.makeText(context, "Lỗi mã QR", Toast.LENGTH_SHORT).show();
            }




        }
        double shippingFee = 10000;
        canvas.drawLine(580,1200,1200-20,1200,myPaint);
        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(35f);
        myPaint.setColor(Color.BLACK);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        canvas.drawText("Tổng: ",580, 1250, myPaint);
        canvas.drawText(" " + decimalFormat.format(grandTotal) + "đ", 960,1250,myPaint);
        canvas.drawText("Phí vẩn chuyển: ",580, 1300, myPaint);
        canvas.drawText("" + decimalFormat.format(shippingFee) + "đ", 960, 1300, myPaint);

        myPaint.setColor(Color.rgb(255, 255, 0));
        canvas.drawRect(580, 1350, 1200 - 20, 1450, myPaint);
        myPaint.setColor(Color.BLACK);
        myPaint.setTextSize(40f);
        myPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Tổng Tiền",580, 1420, myPaint);
        canvas.drawText("" + decimalFormat.format(grandTotal+shippingFee) + "đ", 960, 1420, myPaint);


        pdfDocument.finishPage(page);


        String fileName = "orderPdf_" + System.currentTimeMillis() + ".pdf";

        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, fileName);

        try {
            pdfDocument.writeTo(new FileOutputStream(file));
            pdfDocument.close();

            Toast.makeText(context, "PDF đang được tải xuống", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error saving PDF", Toast.LENGTH_SHORT).show();
        }
    }



    private int getIndexForStatus(String status, List<String> statusList) {
        for (int i = 0; i < statusList.size(); i++) {
            if (statusList.get(i).equals(status)) {
                return i;
            }
        }
        return 0;
    }




    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtorder, quan, phuong, sonha, username, mobile, ngaygio, tongtien;
        RecyclerView recyclerView;

        Spinner spinner;
        ImageView tick;
        Button btnGeneratePdf;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            String baseUrl = Utils.BASE_URL;
            foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);
            tongtien = itemView.findViewById(R.id.tongtotal);
            txtorder = itemView.findViewById(R.id.orderhang);
            recyclerView = itemView.findViewById(R.id.revdetails);
            quan = itemView.findViewById(R.id.textdiachi);
            phuong = itemView.findViewById(R.id.idquan);
            sonha = itemView.findViewById(R.id.textstreet);
            username = itemView.findViewById(R.id.nameuser);
            mobile = itemView.findViewById(R.id.textmobile);
            ngaygio = itemView.findViewById(R.id.orderday);
            spinner = itemView.findViewById(R.id.spinnerorder);
            tick = itemView.findViewById(R.id.tich);
            compositeDisposable = new CompositeDisposable();
            btnGeneratePdf = itemView.findViewById(R.id.btnGeneratePdf);

        }
    }



    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        // Clear the CompositeDisposable
        compositeDisposable.clear();
    }
}
