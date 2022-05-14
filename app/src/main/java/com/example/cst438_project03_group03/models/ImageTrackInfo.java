package com.example.cst438_project03_group03.models;

import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class: ImageTrackInfo.java
 * Description:
 */
public class ImageTrackInfo {

    @SerializedName("mTTid")
    @Expose
    @PrimaryKey
    private int mTTId;

    @SerializedName("userId")
    @Expose
    private int userId;

    @SerializedName("imageId")
    @Expose
    private int imageId;

    @SerializedName("isLiked")
    @Expose
    private char isLiked;

    public int getmTTId() {
        return mTTId;
    }

    public void setmTTId(int mTTId) {
        this.mTTId = mTTId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public char getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(char isLiked) {
        this.isLiked = isLiked;
    }
}
