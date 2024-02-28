package com.food.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng mapIndia = new LatLng(10.0333, 105.7667);
        this.googleMap.addMarker(new MarkerOptions().position(mapIndia).title("Order Food Cái Răng"));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(mapIndia));

        LatLng NK = new LatLng(10.0359, 105.7693);
        this.googleMap.addMarker(new MarkerOptions().position(NK).title("Order Food Ninh Kiều"));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(NK));

    }
}