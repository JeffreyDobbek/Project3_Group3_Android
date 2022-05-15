package com.example.cst438_project03_group03.models;

import com.example.cst438_project03_group03.database.ImageDao;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class: ImgurResponse.java
 * Description: Model for response from Imgur upload API request.
 */
public class ImgurResponse {

    @SerializedName("data")
    @Expose
    private ImgurData data;

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("status")
    @Expose
    private int status;

    public ImgurData getData() {
        return data;
    }

    public void setData(ImgurData data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
