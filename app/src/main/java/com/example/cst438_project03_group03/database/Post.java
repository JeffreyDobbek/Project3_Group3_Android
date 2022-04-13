package com.example.cst438_project03_group03.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = AppDatabase.POST_TABLE)
public class Post {

    @SerializedName("postId")
    @Expose
    @PrimaryKey
    private int postId;

    @SerializedName("userId")
    @Expose
    private int userId;

    @SerializedName("caption")
    @Expose
    private String caption;

    @SerializedName("numLikes")
    @Expose
    private int numLikes;

    @SerializedName("numComments")
    @Expose
    private int numComments;

    @Expose(serialize = false)
    private int recentPosts;

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

    public int getRecentPosts() {
        return recentPosts;
    }

    public void setRecentPosts(int recentPosts) {
        this.recentPosts = recentPosts;
    }
}