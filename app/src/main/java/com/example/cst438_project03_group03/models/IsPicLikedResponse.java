package com.example.cst438_project03_group03.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IsPicLikedResponse {

    @SerializedName("result")
    @Expose
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
