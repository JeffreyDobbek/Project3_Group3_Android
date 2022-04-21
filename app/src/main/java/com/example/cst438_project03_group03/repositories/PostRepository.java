package com.example.cst438_project03_group03.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cst438_project03_group03.api.ApiService;
import com.example.cst438_project03_group03.database.Post;
import com.example.cst438_project03_group03.database.User;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostRepository {

    private static final String API_SERVICE_BASE_URL = "";

    private final ApiService apiService;
    private final MutableLiveData<Post> postLiveData;

    public PostRepository() {
        postLiveData = new MutableLiveData<>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        apiService = new Retrofit.Builder()
                .baseUrl(API_SERVICE_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);
    }

    public void getPosts() {

    }

    public LiveData<Post> getPostLiveData() {
        return postLiveData;
    }
}
