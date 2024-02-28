package com.food.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.food.foodapp.utils.Utils;
import com.google.android.material.navigation.NavigationView;




public class HomeAdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView nameTextView = headerView.findViewById(R.id.nameadmin);
        TextView emailTextView = headerView.findViewById(R.id.emailadmin);
        ImageView imageView = headerView.findViewById(R.id.hinhanhadmin);
        nameTextView.setText(Utils.admin_food.getUsername());
        emailTextView.setText(Utils.admin_food.getEmail());
//        if(Utils.admin_food.getHinhanh().contains("http")){
//            Glide.with(getApplicationContext()).load(Utils.admin_food.getHinhanh()).into(imageView);
//        }
//        else {
//            String hinh= Utils.BASE_URL + "images/"+Utils.admin_food.getHinhanh();
//            Glide.with(getApplicationContext()).load(hinh).into(imageView);
//        }
        String imageUrl = Utils.admin_food.getHinhanh();

        if (imageUrl != null) {
            if (imageUrl.contains("http")) {
                Glide.with(getApplicationContext()).load(imageUrl).into(imageView);
            } else {
                String hinh = Utils.BASE_URL + "images/" + imageUrl;
                Glide.with(getApplicationContext()).load(hinh).into(imageView);
            }
        } else {
            // Handle the case where imageUrl is null
            // You might want to set a default image or show an error message
            Toast.makeText(this, "Image URL is null", Toast.LENGTH_SHORT).show();
        }

        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent = new Intent(this, HomeAdminActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_settings:
                Intent setting = new Intent(this, ChanAdminActivity.class);
                startActivity(setting);
                break;
            case R.id.nav_share:
                Intent prroductmanagement = new Intent(this, ProductManagementActivity.class);
                startActivity(prroductmanagement);
                break;
            case R.id.nav_about:
                Intent order = new Intent(this, OrderAdminActivity.class);
                startActivity(order);
                break;
            case R.id.nav_post:
                Intent post = new Intent(this, PostAdminMainActivity.class);
                startActivity(post);
                break;
            case R.id.nav_tt:
                Intent user = new Intent(this, QlUserActivity.class);
                startActivity(user);
                break;
            case R.id.nav_tk:
                Intent tk = new Intent(this, TKActivity.class);
                startActivity(tk);
                break;
            case R.id.nav_logout:
                Intent lo = new Intent(this, LoginActivity.class);
                startActivity(lo);
                break;
            case R.id.nav_bl:
                Intent bl = new Intent(this, QlRatingMainActivity.class);
                startActivity(bl);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}