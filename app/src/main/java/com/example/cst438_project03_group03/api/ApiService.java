package com.example.cst438_project03_group03.api;

import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.models.CreateAccountResult;
import com.example.cst438_project03_group03.models.UserInfo;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Class: ApiService.java
 * Description: Holds all API requests to access data from the database.
 */
public interface ApiService {

    /**
     * API request to retrieve all users from the database.
     * @return all users.
     */
    @GET("api/getAllUser")
    Call<List<UserInfo>> getAllUsers();

    /**
     * API request to retrieve a user from the database given their unique username.
     * @param username The user's username.
     * @return one user.
     */
    @GET("api/getUser")
    Call<UserInfo> getUserByUsername(
            @Query("username") String username
    );

    /**
     * API request to save a user to the database.
     * @return
     */
    @POST("api/signUp")
    Call<CreateAccountResult> createUser(@Body UserInfo userInfo);
}
