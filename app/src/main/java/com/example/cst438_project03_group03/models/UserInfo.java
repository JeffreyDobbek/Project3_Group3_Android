package com.example.cst438_project03_group03.models;

import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class: UserInfo.java
 * Description: Model for posting and retrieving users from the database via API requests.
 */
public class UserInfo {

    @SerializedName("userId")
    @Expose
    @PrimaryKey
    private int userId;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("pic")
    @Expose
    private String pic;

    public UserInfo() {
    }

    /**
     * Parameterized constructor to initialize a User object (without a user id).
     * @param username The user's unique username.
     * @param email The user's unique email.
     * @param name The user's real name.
     * @param password The user's password.
     * @param image The user's profile picture (an Imgur link).
     */
    public UserInfo(String username, String email, String name, String password, String image) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;
        this.image = image;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
