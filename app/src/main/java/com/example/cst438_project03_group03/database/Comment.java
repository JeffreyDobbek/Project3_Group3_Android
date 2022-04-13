package com.example.cst438_project03_group03.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = AppDatabase.COMMENT_TABLE)
public class Comment {

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
    private String numLikes;

    public Comment(int commentId, int postId, int userId, String comment, String numLikes) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.comment = comment;
        this.numLikes = numLikes;
    }

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

    public String getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(String numLikes) {
        this.numLikes = numLikes;
    }
}
