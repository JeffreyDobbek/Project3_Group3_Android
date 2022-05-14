package com.example.cst438_project03_group03.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateAccountResponse {

    @SerializedName("newId")
    @Expose
    private int newId;

    public int getNewId() {
        return newId;
    }

    public void setNewId(int newId) {
        this.newId = newId;
    }
}
