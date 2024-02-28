package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.food.foodapp.adapter.OrderDetailAdapter;
import com.food.foodapp.models.Item;
import com.food.foodapp.models.Order;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PdfActivity extends AppCompatActivity {
    TextView nameTxt, mobile, drchi, phuong, quan, namesp;
    Button pdf;
    int pageWidth = 1200;
    Bitmap bitmap, scalebmp;
    OrderDetailAdapter orderDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        nameTxt = findViewById(R.id.namee);
        mobile = findViewById(R.id.mobileorder);
        pdf = findViewById(R.id.pdff);
        drchi = findViewById(R.id.dc);
        phuong = findViewById(R.id.phuong1);
        quan = findViewById(R.id.quan1);
        namesp = findViewById(R.id.tensporder);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String ordername = extras.getString("name");
            String ordermobile = extras.getString("mobile");
            String kv = extras.getString("dichi");
            String phuongCT = extras.getString("phuong");
            String quanCT = extras.getString("quan");
            nameTxt.setText(ordername);
            mobile.setText(ordermobile);
            drchi.setText(kv);
            phuong.setText(phuongCT);
            quan.setText(quanCT);
        }

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pizzahead);
        scalebmp = Bitmap.createScaledBitmap(bitmap, pageWidth, 518, false);

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePdf();
            }
        });
    }

    private void generatePdf() {
        PdfDocument myPdfDocument = new PdfDocument();
        Paint myPaint = new Paint();
        Paint titlePaint = new Paint();

        if (isExternalStorageWritable()) {
            PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(pageWidth, 2010, 1).create();

            PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);

            Canvas canvas = myPage.getCanvas();
            canvas.drawBitmap(scalebmp, 0, 0, myPaint);

            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            titlePaint.setTextSize(70);
            canvas.drawText("Food Order", pageWidth / 2, 270, titlePaint);

            myPaint.setColor(Color.rgb(0,113,188));
            myPaint.setTextSize(30f);
            myPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText("Thành Phố Cần Thơ", 1160, 40, myPaint);
            canvas.drawText("Điện thoại: 0354446943", 1160, 80, myPaint);

            titlePaint.setTextAlign(Paint.Align.CENTER);
            titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
            titlePaint.setTextSize(70);
            canvas.drawText("Hóa Đơn", pageWidth / 2, 300, titlePaint);

            myPaint.setTextAlign(Paint.Align.LEFT);
            myPaint.setTextSize(35f);
            myPaint.setColor(Color.BLACK);
            canvas.drawText("Khách hàng: " + nameTxt.getText(), 20, 590, myPaint);
            canvas.drawText("Số điện thoại: " + mobile.getText(), 20, 640, myPaint);
            canvas.drawText("Địa chỉ: " + drchi.getText() + ", " + phuong.getText() + ", " + quan.getText() + " Thành Phố Cần Thơ", 20, 690, myPaint);

            myPaint.setStyle(Paint.Style.STROKE);
            myPaint.setStrokeWidth(2);
            canvas.drawRect(20, 780, pageWidth - 20, 860, myPaint);
            myPaint.setTextAlign(Paint.Align.LEFT);


            canvas.drawText("STT", 40, 830, myPaint);
            canvas.drawText("Sản phẩm", 200, 830, myPaint);
            canvas.drawText("Giá", 700, 830, myPaint);
            canvas.drawText("Số lượng", 900, 830, myPaint);
            canvas.drawText("Tổng", 1050, 830, myPaint);

            canvas.drawLine(180, 790, 180, 840, myPaint);
            canvas.drawLine(680, 790, 680, 840, myPaint);
            canvas.drawLine(880, 790, 880, 840, myPaint);
            canvas.drawLine(1030, 790, 1030, 840, myPaint);


            myPdfDocument.finishPage(myPage);

            String fileName = "order_" + System.currentTimeMillis() + ".pdf";

            File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);


            try {
                myPdfDocument.writeTo(new FileOutputStream(file));
                Toast.makeText(PdfActivity.this, "PDF saved to Downloads folder as " + fileName, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(PdfActivity.this, "Error saving PDF", Toast.LENGTH_SHORT).show();
            }
            myPdfDocument.close();
        } else {
            Toast.makeText(this, "External storage not available for writing", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}
