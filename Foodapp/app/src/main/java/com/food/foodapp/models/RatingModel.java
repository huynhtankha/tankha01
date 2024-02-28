package com.food.foodapp.models;

import java.util.List;

public class RatingModel {
    boolean success;
    String message;
    List<Rating> result;

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

    public List<Rating> getResult() {
        return result;
    }

    public void setResult(List<Rating> result) {
        this.result = result;
    }
}
