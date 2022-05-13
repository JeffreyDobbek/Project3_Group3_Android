package com.example.cst438_project03_group03.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cst438_project03_group03.api.ApiService;
import com.example.cst438_project03_group03.database.Post;
import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.models.PostInfo;
import com.example.cst438_project03_group03.util.Constants;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostRepository {

    private final ApiService apiService;
    private final MutableLiveData<List<PostInfo>> allLivePostsLiveData;

    /**
     * Constructor that initializes the LiveData variables and API service with Retrofit.
     */
    public PostRepository() {
        allLivePostsLiveData = new MutableLiveData<>();

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
     * Gets all live posts in the database.
     */
    public void getAllLivePosts() {
        apiService.getAllLivePosts()
                .enqueue(new Callback<List<PostInfo>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<PostInfo>> call, @NonNull Response<List<PostInfo>> response) {
                        if (response.body() != null) {
                            allLivePostsLiveData.postValue(response.body());
                            Log.i("success", "success");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<PostInfo>> call, @NonNull Throwable t) {
                        allLivePostsLiveData.postValue(null);
                        Log.i("fail", "fail");
                    }
                });
    }


    /**
     * @return LiveData of all live posts response.
     */
    public LiveData<List<PostInfo>> getAllLivePostsLiveData() {
        return allLivePostsLiveData;
    }
}
