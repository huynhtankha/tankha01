package com.food.foodapp.models;

import java.io.Serializable;
import java.util.Date;

public class Rating implements Serializable {
    int idcmt;
    String hinhanh;
    int idusername;
    String username;
    int idsp;
    int rating;
    String comment;
    Date daterating;
    String tensp;
    String images;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public Date getDaterating() {
        return daterating;
    }

    public void setDaterating(Date daterating) {
        this.daterating = daterating;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdcmt() {
        return idcmt;
    }

    public void setIdcmt(int idcmt) {
        this.idcmt = idcmt;
    }

    public int getIdusername() {
        return idusername;
    }

    public void setIdusername(int idusername) {
        this.idusername = idusername;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
