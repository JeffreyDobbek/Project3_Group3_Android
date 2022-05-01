package com.example.cst438_project03_group03.models;

import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PostInfo {

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

    @SerializedName("isLive")
    @Expose
    private char isLive;

    @SerializedName("type")
    @Expose
    private String type;

    @Expose(serialize = false)
    private List<ImageInfo> images = new ArrayList<>();

    @Expose(serialize = false)
    private String profilePic;

    @Expose(serialize = false)
    private String username;

    public PostInfo() {
    }

    public PostInfo(int userId, String caption, int numLikes, int numComments, char isLive, String type) {
        this.postId = postId;
        this.userId = userId;
        this.caption = caption;
        this.numLikes = numLikes;
        this.numComments = numComments;
        this.isLive = isLive;
        this.type = type;
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

    public char getIsLive() {
        return isLive;
    }

    public void setIsLive(char isLive) {
        this.isLive = isLive;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ImageInfo> getImages() {
        return images;
    }

    public void setImages(List<ImageInfo> images) {
        this.images = images;
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
