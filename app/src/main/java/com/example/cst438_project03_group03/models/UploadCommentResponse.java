package com.example.cst438_project03_group03.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class: UploadCommentResponse.java
 * Description: Model for response from "uploadComment" API request.
 */
public class UploadCommentResponse {

    @SerializedName("commentId")
    @Expose
    private int commentId;

    public int getCommentId() {
        return commentId;
    }
}
