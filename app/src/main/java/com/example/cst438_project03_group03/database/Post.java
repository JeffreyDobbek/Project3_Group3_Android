package com.example.cst438_project03_group03.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = RoomDatabase.POST_TABLE)
public class Post {

    @PrimaryKey
    private int postId;

    private int userId;

    private String caption;

    private int numLikes;
    private int numComments;

    public Post(int postId, int userId, String caption, int numLikes, int numComments) {
        this.postId = postId;
        this.userId = userId;
        this.caption = caption;
        this.numLikes = numLikes;
        this.numComments = numComments;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }
}