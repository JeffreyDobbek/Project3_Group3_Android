package com.example.cst438_project03_group03.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = RoomDatabase.IMAGE_TABLE)
public class Image {

    @PrimaryKey
    private int imageId;

    private int postId;

    private String image;

    private int numLikes;
    private int orders;

    public Image(int imageId, int postId, String image, int numLikes, int orders) {
        this.imageId = imageId;
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
