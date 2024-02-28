package com.food.foodapp.models;

import java.util.List;

public class KeModel {
    boolean success;
    String message;
    List<Ke> result;

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

    public List<Ke> getResult() {
        return result;
    }

    public void setResult(List<Ke> result) {
        this.result = result;
    }
}
