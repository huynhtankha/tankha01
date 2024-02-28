package com.food.foodapp.models;

import java.util.List;

public class CategoryFoodModel {
    boolean success;
    String message;
    List<CategoryFood> result;

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

    public List<CategoryFood> getResult() {
        return result;
    }

    public void setResult(List<CategoryFood> result) {
        this.result = result;
    }
}
