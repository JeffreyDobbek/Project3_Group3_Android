package com.example.cst438_project03_group03.models;

import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentInfo {

    @SerializedName("commentId")
    @Expose
    @PrimaryKey
    private int commentId;

    @SerializedName("postId")
    @Expose
    private int postId;

    @SerializedName("userId")
    @Expose
    private int userId;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("numLikes")
    @Expose
    private int numLikes;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }
}
