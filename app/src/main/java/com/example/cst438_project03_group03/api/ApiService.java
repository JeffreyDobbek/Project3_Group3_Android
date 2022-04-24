package com.example.cst438_project03_group03.api;

import com.example.cst438_project03_group03.database.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
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
    Call<List<User>> getAllUsers();

    /**
     * API request to retrieve a user from the database given their unique username.
     * @param username The user's username.
     * @return one user.
     */
    @GET("api/getUser")
    Call<User> getUserByUsername(
            @Query("username") String username
    );
}
