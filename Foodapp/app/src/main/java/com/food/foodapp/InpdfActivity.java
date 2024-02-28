//package com.food.foodapp;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import com.food.foodapp.activity.MainActivity;
//import com.food.foodapp.models.Item;
//import com.food.foodapp.models.Order;
//import com.itextpdf.io.IOException;
//import com.itextpdf.kernel.geom.PageSize;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Paragraph;
//import com.karumi.dexter.Dexter;
//import com.karumi.dexter.PermissionToken;
//import com.karumi.dexter.listener.PermissionDeniedResponse;
//import com.karumi.dexter.listener.PermissionGrantedResponse;
//import com.karumi.dexter.listener.PermissionRequest;
//import com.karumi.dexter.listener.single.PermissionListener;
//
//import android.Manifest;
//import android.widget.TextView;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//
//public class InpdfActivity extends AppCompatActivity {
//    TextView nameTxt,mobile, drchi, phuong, quan, tensp;
//    Button pdf;
//    Order order;
//    Item item;
//    int pageWidth =1200;
//    Bitmap bitmap,scalebmp;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_inpdf);
//        nameTxt = findViewById(R.id.namee);
//        mobile = findViewById(R.id.mobileorder);
//        pdf = findViewById(R.id.pdff);
//        drchi = findViewById(R.id.dc);
//        phuong = findViewById(R.id.phuong1);
//        quan = findViewById(R.id.quan1);
//        tensp = findViewById(R.id.tensporder);
//
//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            String ordername  = extras.getString("name");
//            String ordermobile = extras.getString("mobile");
//            String kv = extras.getString("dichi");
//            String phuongCT = extras.getString("phuong");
//            String quanCT = extras.getString("quan");
//            nameTxt.setText(ordername);
//            mobile.setText(ordermobile);
//            drchi.setText(kv);
//            phuong.setText(phuongCT);
//            quan.setText(quanCT);
//
//        }
//
//        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pizzahead);
//        scalebmp = Bitmap.createScaledBitmap(bitmap, pageWidth, 518, false);
//        pdf.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                generatePdf();
//            }
//        });
//    }
//}
