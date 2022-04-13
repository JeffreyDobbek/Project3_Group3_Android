package com.example.cst438_project03_group03.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = AppDatabase.OPTION_TABLE)
public class Option {

    @SerializedName("optionId")
    @Expose
    @PrimaryKey
    private int optionId;

    @SerializedName("postId")
    @Expose
    private int postId;

    @SerializedName("option")
    @Expose
    private String option;

    @SerializedName("numLikes")
    @Expose
    private int numLikes;

    public Option(int optionId, int postId, String option, int numLikes) {
        this.optionId = optionId;
        this.postId = postId;
        this.option = option;
        this.numLikes = numLikes;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }
}
