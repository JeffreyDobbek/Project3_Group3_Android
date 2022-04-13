package com.example.cst438_project03_group03.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    private int image;

    @Expose(serialize = false)
    private int cachedOrder;

    public User() {
    }

    public User(int userId, String username, String email, String name, String password, int image) {
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getCachedOrder() {
        return cachedOrder;
    }

    public void setCachedOrder(int cachedOrder) {
        this.cachedOrder = cachedOrder;
    }
}
