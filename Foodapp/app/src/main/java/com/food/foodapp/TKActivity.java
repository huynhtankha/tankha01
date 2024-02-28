package com.food.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.food.foodapp.retrofit.FoodApi;
import com.food.foodapp.retrofit.RetrofitClient;
import com.food.foodapp.utils.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TKActivity extends AppCompatActivity {
    BarChart barChart;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    FoodApi foodApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tkactivity);
        String baseUrl = Utils.BASE_URL;

        foodApi = RetrofitClient.getInstance(baseUrl).create(FoodApi.class);

        barChart = findViewById(R.id.barchart);



        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setAxisMinimum(1);
        xAxis.setAxisMaximum(12);
        YAxis yAxisR = barChart.getAxisRight();
        yAxisR.setAxisMinimum(0);
        YAxis yAxisL = barChart.getAxisLeft();
        yAxisL.setAxisMinimum(0);
        compositeDisposable.add(foodApi.getThongke()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        keModel -> {
                            if (keModel.isSuccess()) {
                                List<BarEntry> barEntriesList = new ArrayList<>();

                                for (int i = 0; i < keModel.getResult().size(); i++) {
                                    String tong = keModel.getResult().get(i).getTongtienthang();
                                    String thang = keModel.getResult().get(i).getThang();
                                    barEntriesList.add(new BarEntry(Integer.parseInt(thang),Float.parseFloat(tong)));
                                }
                                BarDataSet barDataSet = new BarDataSet(barEntriesList, "Thống kê");
                                barDataSet.setValueTextSize(12f);


                                BarData barData = new BarData(barDataSet);

                                barChart.setData(barData);
                                barChart.invalidate();
                            }
                        }, throwable -> {
                            Log.d("log", throwable.getMessage());
                        }
                ));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
