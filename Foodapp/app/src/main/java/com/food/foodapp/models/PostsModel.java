package com.food.foodapp.models;

import java.util.List;

public class PostsModel {
    boolean success;
    String message;
    List<Posts> result;

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

    public List<Posts> getResult() {
        return result;
    }

    public void setResult(List<Posts> result) {
        this.result = result;
    }
}
