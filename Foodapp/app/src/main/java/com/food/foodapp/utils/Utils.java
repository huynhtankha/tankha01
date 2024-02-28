package com.food.foodapp.utils;

import com.food.foodapp.models.Admin;
import com.food.foodapp.models.CartFood;
import com.food.foodapp.models.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String BASE_URL = "http://192.168.169.186/food/";
    public static List<CartFood> mangcart = new ArrayList<>();

    public static User user_food = new User();
    public static Admin admin_food = new Admin();
    public static List<CartFood> mangmua = new ArrayList<>();

}