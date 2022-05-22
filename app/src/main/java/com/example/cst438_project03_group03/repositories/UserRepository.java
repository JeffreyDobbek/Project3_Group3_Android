package com.example.cst438_project03_group03.repositories;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.cst438_project03_group03.api.ApiService;
import com.example.cst438_project03_group03.database.RoomDatabase;
import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.database.UserDao;
import com.example.cst438_project03_group03.models.CreateAccountResponse;
import com.example.cst438_project03_group03.models.UpdateUserResponse;
import com.example.cst438_project03_group03.models.UserInfo;
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
 * Class: UserRepository.java
 * Description: Handles api requests related to user information.
 */
public class UserRepository {

    private UserDao userDao;

    private final ApiService apiService;
    private final MutableLiveData<List<UserInfo>> userListLiveData;
    private final MutableLiveData<UserInfo> userLiveData;
    private final MutableLiveData<CreateAccountResponse> createUserLiveData;
    private final MutableLiveData<UpdateUserResponse> updateUserLiveData;

    /**
     * Constructor that initializes the LiveData variables and API service with Retrofit.
     */
    public UserRepository(@NonNull Application application) {
        RoomDatabase database = RoomDatabase.getInstance(application);
        userDao = database.userDao();

        userListLiveData = new MutableLiveData<>();
        userLiveData = new MutableLiveData<>();
        createUserLiveData = new MutableLiveData<>();
        updateUserLiveData = new MutableLiveData<>();

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

    public void insert(User user) {
        userDao.insert(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(User user) {
        userDao.delete(user);
    }

    public User getUserById(int userId) {
        return userDao.getUserById(userId);
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
                    public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                        if (response.body() != null) {
                            userLiveData.postValue(response.body());
                            Log.i("success", "success");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UserInfo> call, Throwable t) {
                        userLiveData.postValue(null);
                        Log.i("fail", "fail");
                    }
                });
    }


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
                .enqueue(new Callback<CreateAccountResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CreateAccountResponse> call, @NonNull Response<CreateAccountResponse> response) {
                        if (response.body() != null) {
                            createUserLiveData.postValue(response.body());
                            Log.i("success", "success");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CreateAccountResponse> call, @NonNull Throwable t) {
                        createUserLiveData.postValue(null);
                        Log.i("fail", "fail");
                    }
                });
    }

    public void updateUser(UserInfo userInfo) {
        apiService.updateUser(userInfo)
                .enqueue(new Callback<UpdateUserResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<UpdateUserResponse> call, @NonNull Response<UpdateUserResponse> response) {
                        if (response.body() != null) {
                            updateUserLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UpdateUserResponse> call, @NonNull Throwable t) {
                        updateUserLiveData.postValue(null);
                    }
                });
    }

    public void updatePassword(UserInfo userInfo) {
        apiService.updatePassword(userInfo)
                .enqueue(new Callback<UpdateUserResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<UpdateUserResponse> call, @NonNull Response<UpdateUserResponse> response) {
                        if (response.body() != null) {
                            updateUserLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<UpdateUserResponse> call, @NonNull Throwable t) {
                        updateUserLiveData.postValue(null);
                    }
                });
    }

    public LiveData<List<UserInfo>> getUserListLiveData() {
        return userListLiveData;
    }

    public LiveData<UserInfo> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<CreateAccountResponse> getCreateUserLiveData() {
        return createUserLiveData;
    }

    public LiveData<UpdateUserResponse> getUpdateUserLiveData() {
        return updateUserLiveData;
    }
}
