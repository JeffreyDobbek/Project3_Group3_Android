package com.example.cst438_project03_group03.models;

import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageInfo {

    @SerializedName("imageId")
    @Expose
    @PrimaryKey
    private int imageId;

    @SerializedName("postId")
    @Expose
    private int postId;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("numLikes")
    @Expose
    private int numLikes;

    @SerializedName("orders")
    @Expose
    private int orders;

    public ImageInfo() {
    }

    public ImageInfo(int postId, String image, int numLikes, int orders) {
        this.postId = postId;
        this.image = image;
        this.numLikes = numLikes;
        this.orders = orders;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }
}
