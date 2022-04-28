package com.example.cst438_project03_group03.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cst438_project03_group03.api.ApiService;
import com.example.cst438_project03_group03.database.Image;
import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.models.ImageInfo;
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
    private final MutableLiveData<List<ImageInfo>> postImagesLiveData;

    /**
     * Constructor that initializes the LiveData variables and API service with Retrofit.
     */
    public ImageRepository() {
        postImagesLiveData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        apiService = new Retrofit.Builder()
                .baseUrl(Constants.API_SERVICE_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);
    }

    /**
     * Gets all images of a post by the post's id.
     * @param postId The post's unique id.
     */
    public void getPostImages(int postId) {
        apiService.getPicsByPostId(postId)
                .enqueue(new Callback<List<ImageInfo>>() {
                    @Override
                    public void onResponse(Call<List<ImageInfo>> call, Response<List<ImageInfo>> response) {
                        if (response.body() != null) {
                            postImagesLiveData.postValue(response.body());
                            Log.i("success", "success");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ImageInfo>> call, Throwable t) {
                        postImagesLiveData.postValue(null);
                        Log.i("fail", "fail");
                    }
                });
    }


    /*
    public void getPostImages(int postId) {
        try {
            Response<List<ImageInfo>> response = apiService.getPicsByPostId(postId).execute();

            if (response.body() != null) {
                postImagesLiveData.postValue(response.body());
            } else {
                postImagesLiveData.postValue(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     */

    /**
     * @return LiveData of post images response.
     */
    public LiveData<List<ImageInfo>> getPostImagesLiveData() {
        return postImagesLiveData;
    }
}
