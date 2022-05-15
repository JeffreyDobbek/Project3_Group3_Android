package com.example.cst438_project03_group03.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cst438_project03_group03.models.PostInfo;
import com.example.cst438_project03_group03.repositories.PostRepository;

import java.util.List;

public class PostViewModel extends AndroidViewModel {

    private PostRepository postRepository;
    private LiveData<List<PostInfo>> postListLiveData;

    public PostViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        postRepository = new PostRepository();
        postListLiveData = postRepository.getPostListLiveData();
    }

    public void getAllLivePosts() {
        postRepository.getAllLivePosts();
    }
    public void getUserPosts(int userId) {
        postRepository.getUserPosts(userId);
    }
    public void getLikedPosts(int userId) {
        postRepository.getLikedPosts(userId);
    }

    public LiveData<List<PostInfo>> getPostListLiveData() {
        return postListLiveData;
    }
}
