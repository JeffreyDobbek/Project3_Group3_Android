package com.example.cst438_project03_group03.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cst438_project03_group03.api.ApiService;
import com.example.cst438_project03_group03.database.Comment;
import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.models.CommentInfo;
import com.example.cst438_project03_group03.util.Constants;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class: CommentRepository.java
 * Description:
 */
public class CommentRepository {

    private final ApiService apiService;
    private final MutableLiveData<List<CommentInfo>> commentListLiveData;

    /**
     * Constructor that initializes the LiveData variables and API service with Retrofit.
     */
    public CommentRepository() {
        commentListLiveData = new MutableLiveData<>();

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
     * Gets a list of comments from one post.
     * @param postId The post's id.
     */
    public void getPostComments(int postId) {
        apiService.getPostComments(postId)
                .enqueue(new Callback<List<CommentInfo>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<CommentInfo>> call, @NonNull Response<List<CommentInfo>> response) {
                        if (response.body() != null) {
                            commentListLiveData.postValue(response.body());
                            Log.i("success", "success");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<CommentInfo>> call, @NonNull Throwable t) {
                        commentListLiveData.postValue(null);
                        Log.i("fail", "fail");
                    }
                });
    }

    /**
     * @return LiveData of all post comments response.
     */
    public LiveData<List<CommentInfo>> getCommentListLiveData() {
        return commentListLiveData;
    }
}
