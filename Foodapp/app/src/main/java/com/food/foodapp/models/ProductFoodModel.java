package com.food.foodapp.models;

import java.util.List;

public class ProductFoodModel {
    boolean success;
    String message;
    List<ProductFood> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ProductFood> getResult() {
        return result;
    }

    public void setResult(List<ProductFood> result) {
        this.result = result;
    }
}
