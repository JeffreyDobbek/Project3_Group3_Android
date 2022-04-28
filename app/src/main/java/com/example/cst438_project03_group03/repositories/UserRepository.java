package com.example.cst438_project03_group03.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cst438_project03_group03.api.ApiService;
import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.models.CreateAccountResult;
import com.example.cst438_project03_group03.models.UserInfo;
import com.example.cst438_project03_group03.util.Constants;


import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

/**
 * Class: UserRepository.java
 * Description: Handles api requests related to user information.
 */
public class UserRepository {

    private final ApiService apiService;
    private final MutableLiveData<List<UserInfo>> userListLiveData;
    private final MutableLiveData<UserInfo> userLiveData;
    private final MutableLiveData<CreateAccountResult> createUserLiveData;

    /**
     * Constructor that initializes the LiveData variables and API service with Retrofit.
     */
    public UserRepository() {
        userListLiveData = new MutableLiveData<>();
        userLiveData = new MutableLiveData<>();
        createUserLiveData = new MutableLiveData<>();

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
     * Gets every user in the database.
     */
    public void getAllUsers() {
        apiService.getAllUsers()
                .enqueue(new Callback<List<UserInfo>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<UserInfo>> call, @NonNull Response<List<UserInfo>> response) {
                        if (response.body() != null) {
                            userListLiveData.postValue(response.body());
                            Log.i("success", "success");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<UserInfo>> call, @NonNull Throwable t) {
                        userListLiveData.postValue(null);
                        Log.i("fail", "fail");
                    }
                });
    }

    /**
     * Gets one user from the database by their unique id.
     * @param userId The user's id.
     */
    public void getUserByUserId(int userId) {
        apiService.getUserByUserId(userId)
                .enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        if (response.body() != null) {
                            userLiveData.postValue(response.body());
                            Log.i("success", "success");
                        }
                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {
                        userLiveData.postValue(null);
                        Log.i("fail", "fail");
                    }
                });
    }


    /*
    public void getUserByUserId(int userId) {
        try {
            Response<UserInfo> response = apiService.getUserByUserId(userId).execute();
            if (response.body() != null) {
                userLiveData.postValue(response.body());
                Log.i("success", "success");
            } else {
                userLiveData.postValue(null);
                Log.i("fail", "fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    /**
     * Gets one user from the database by their unique username.
     * @param username The user's username.
     */
    public void getUserByUsername(String username) {
        apiService.getUserByUsername(username)
                .enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                        if (response.body() != null) {
                            userLiveData.postValue(response.body());
                            Log.i("success", "success");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                        userLiveData.postValue(null);
                        Log.i("fail", "fail");
                    }


        });
    }

    public void createUser(UserInfo userInfo) {
        apiService.createUser(userInfo)
                .enqueue(new Callback<CreateAccountResult>() {
                    @Override
                    public void onResponse(@NonNull Call<CreateAccountResult> call, @NonNull Response<CreateAccountResult> response) {
                        if (response.body() != null) {
                            createUserLiveData.postValue(response.body());
                            Log.i("success", "success");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CreateAccountResult> call, @NonNull Throwable t) {
                        createUserLiveData.postValue(null);
                        Log.i("fail", "fail");
                    }
                });
    }

    /**
     * @return LiveData of a list of users.
     */
    public LiveData<List<UserInfo>> getUserListLiveData() {
        return userListLiveData;
    }

    /**
     * @return LiveData of one user.
     */
    public LiveData<UserInfo> getUserLiveData() {
        return userLiveData;
    }

    /**
     * @return LiveData of create user response.
     */
    public LiveData<CreateAccountResult> getCreateUserLiveData() {
        return createUserLiveData;
    }
}
