package com.example.cst438_project03_group03.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class: LikePicResponse.java
 * Description: Model for response from "likePic" and "unlikePic" API requests
 */
public class LikePicResponse {

    @SerializedName("output")
    @Expose
    private String output;

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
