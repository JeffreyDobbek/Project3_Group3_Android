package com.example.cst438_project03_group03.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = AppDatabase.IMAGE_TABLE)
public class Image {

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
    private String numLikes;

    public Image(int imageId, int postId, String image, String numLikes) {
        this.imageId = imageId;
        this.postId = postId;
        this.image = image;
        this.numLikes = numLikes;
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

    public String getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(String numLikes) {
        this.numLikes = numLikes;
    }
}
