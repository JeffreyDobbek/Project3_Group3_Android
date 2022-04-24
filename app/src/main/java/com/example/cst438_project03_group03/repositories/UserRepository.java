package com.example.cst438_project03_group03.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cst438_project03_group03.api.ApiService;
import com.example.cst438_project03_group03.database.User;


import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class: UserRepository.java
 * Description: Handles api requests related to user information.
 */
public class UserRepository {

    private static final String API_SERVICE_BASE_URL = "https://choicesproj3.youngphil5.repl.co/";

    private final ApiService apiService;
    private final MutableLiveData<List<User>> userListLiveData;
    private final MutableLiveData<User> userLiveData;

    /**
     * Constructor that initializes the LiveData variables and API service.
     */
    public UserRepository() {
        userListLiveData = new MutableLiveData<>();
        userLiveData = new MutableLiveData<>();

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

    /**
     * Gets every user in the database.
     */
    public void getAllUsers() {
        apiService.getAllUsers()
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                        if (response.body() != null) {
                            userListLiveData.postValue(response.body());
                            Log.i("success", "success");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                        userListLiveData.postValue(null);
                        Log.i("fail", "fail");
                    }
                });
    }

    /**
     * Gets one user from the username by their unique username.
     * @param username The user's username.
     */
    public void getUserByUsername(String username) {
        apiService.getUserByUsername(username)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        if (response.body() != null) {
                            userLiveData.postValue(response.body());
                            Log.i("success", "success");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        userLiveData.postValue(null);
                        Log.i("fail", "fail");
                    }


        });
    }

    /**
     * @return LiveData of a list of users.
     */
    public LiveData<List<User>> getUserListLiveData() {
        return userListLiveData;
    }

    /**
     * @return LiveData of one user.
     */
    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }
}
