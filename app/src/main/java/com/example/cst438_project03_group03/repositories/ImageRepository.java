package com.example.cst438_project03_group03.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cst438_project03_group03.api.ApiService;
import com.example.cst438_project03_group03.api.ImgurApiService;
import com.example.cst438_project03_group03.database.Image;
import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.models.ImageInfo;
import com.example.cst438_project03_group03.models.ImgurResponse;
import com.example.cst438_project03_group03.models.ImgurUpload;
import com.example.cst438_project03_group03.models.IsPicLikedResponse;
import com.example.cst438_project03_group03.util.Constants;

import java.security.spec.ECField;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageRepository {

    private final ApiService apiService;
    private final ImgurApiService imgurApiService;
    private final MutableLiveData<List<ImageInfo>> postImagesLiveData;
    private final MutableLiveData<ImgurResponse> imgurResponseLiveData;
    private final MutableLiveData<IsPicLikedResponse> isPicLikedResponseLiveData;

    /**
     * Constructor that initializes the LiveData variables and API service with Retrofit.
     */
    public ImageRepository() {
        postImagesLiveData = new MutableLiveData<>();
        imgurResponseLiveData = new MutableLiveData<>();
        isPicLikedResponseLiveData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        apiService = new Retrofit.Builder()
                .baseUrl(Constants.API_SERVICE_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);

        imgurApiService = new Retrofit.Builder()
                .baseUrl(Constants.IMGUR_API_SERVICE_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ImgurApiService.class);
    }

    public void getAllPostPics() {
        apiService.getAllPostPics()
                .enqueue(new Callback<List<ImageInfo>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ImageInfo>> call, @NonNull Response<List<ImageInfo>> response) {
                        if (response.body() != null) {
                            postImagesLiveData.postValue(response.body());
                            Log.i("success", "success");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ImageInfo>> call, @NonNull Throwable t) {
                        postImagesLiveData.postValue(null);
                        Log.i("fail", "fail");
                    }
                });
    }

    public void isPicLiked(int userId, int imageId) {
        apiService.isPicLiked(userId, imageId)
                .enqueue(new Callback<IsPicLikedResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<IsPicLikedResponse> call, @NonNull Response<IsPicLikedResponse> response) {
                        if (response.body() != null) {
                            isPicLikedResponseLiveData.postValue(response.body());
                            Log.i("success", "success");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<IsPicLikedResponse> call, @NonNull Throwable t) {
                        isPicLikedResponseLiveData.postValue(null);
                        Log.i("fail", "fail");
                    }
                });
    }

    public void imgurUpload(ImgurUpload imgurUpload, String clientId) {
        imgurApiService.imgurUpload(imgurUpload, clientId)
                .enqueue(new Callback<ImgurResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ImgurResponse> call, @NonNull Response<ImgurResponse> response) {
                        if (response.body() != null) {
                            imgurResponseLiveData.postValue(response.body());
                            Log.i("success", "success");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ImgurResponse> call, @NonNull Throwable t) {
                        imgurResponseLiveData.postValue(null);
                        Log.i("fail", "fail");
                    }
                });
    }

    public LiveData<List<ImageInfo>> getPostImagesLiveData() {
        return postImagesLiveData;
    }
    public LiveData<ImgurResponse> getImgurResponseLiveData() {
        return imgurResponseLiveData;
    }
    public LiveData<IsPicLikedResponse> getIsPicLikedResponseLiveData() {
        return isPicLikedResponseLiveData;
    }
}
