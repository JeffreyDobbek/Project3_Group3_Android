package com.example.cst438_project03_group03.api;

import com.example.cst438_project03_group03.models.CommentInfo;
import com.example.cst438_project03_group03.models.CreateAccountResponse;
import com.example.cst438_project03_group03.models.ImageInfo;
import com.example.cst438_project03_group03.models.PostInfo;
import com.example.cst438_project03_group03.models.UploadCommentResponse;
import com.example.cst438_project03_group03.models.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
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
     * API request to retrieve a user from the database given their unique id.
     * @param userId The user's id.
     * @return one user.
     */
    @GET("api/getUserById")
    Call<UserInfo> getUserByUserId(
            @Query("userId") int userId
    );

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
    Call<CreateAccountResponse> createUser(@Body UserInfo userInfo);

    /**
     * API request to get all live posts.
     * @return a list of posts.
     */
    @GET("api/allLivePosts")
    Call<List<PostInfo>> getAllLivePosts();

    /**
     * API request to get all pictures from the database.
     * @return a list of images.
     */
    @GET("api/getAllPostPics")
    Call<List<ImageInfo>> getAllPostPics();

    /**
     * API request to post a comment on a post.
     * @param commentInfo A CommentInfo object.
     * @return the new comment's id.
     */
    @POST("api/uploadComment")
    Call<UploadCommentResponse> uploadComment(@Body CommentInfo commentInfo);

    /**
     * API request to get all of a post's comments.
     * @param postId The post's id.
     * @return a list of comments.
     */
    @GET("api/getComments")
    Call<List<CommentInfo>> getPostComments(
            @Query("postId") int postId
    );
}
