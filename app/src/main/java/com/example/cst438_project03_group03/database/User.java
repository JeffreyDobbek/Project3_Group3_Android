package com.example.cst438_project03_group03.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class: User.java
 * Description: Model and entity for a User object.
 */
@Entity(tableName = AppDatabase.USER_TABLE)
public class User {

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

    @Expose(serialize = false)
    private int cachedOrder;

    public User() {
    }

    /**
     * Parameterized constructor to initialize a User object.
     * @param userId The user's unique id.
     * @param username The user's unique username.
     * @param email The user's unique email.
     * @param name The user's real name.
     * @param password The user's password.
     * @param image The user's profile picture (an Imgur link).
     */
    public User(int userId, String username, String email, String name, String password, String image) {
        this.userId = userId;
        this.email = email;
        this.username = username;
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

    public int getCachedOrder() {
        return cachedOrder;
    }

    public void setCachedOrder(int cachedOrder) {
        this.cachedOrder = cachedOrder;
    }
}
