package com.example.cst438_project03_group03.models;

import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class: CommentInfo.java
 * Description: Model for posting and retrieving comments from the database via API requests.
 */
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

    @Expose(serialize = false)
    private String profilePic;

    @Expose(serialize = false)
    private String username;

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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
