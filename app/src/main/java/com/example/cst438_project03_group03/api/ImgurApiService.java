package com.example.cst438_project03_group03.api;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.android.volley.BuildConfig;
import com.example.cst438_project03_group03.models.ImgurResponse;
import com.example.cst438_project03_group03.models.ImgurUpload;

import java.util.Properties;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Class: ImgurApiService.java
 * Description: Holds all API requests for the Imgur API.
 */
public interface ImgurApiService {

    /**
     *
     * @param imgurUpload
     * @param clientId
     * @return
     */
    @POST("3/image")
    Call<ImgurResponse> imgurUpload(
            @Body ImgurUpload imgurUpload,
            @Header("Authorization") String clientId
    );
}
