package com.example.cst438_project03_group03.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cst438_project03_group03.database.Post;
import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.repositories.PostRepository;
import com.example.cst438_project03_group03.repositories.UserRepository;

public class PostViewModel extends AndroidViewModel {

    private PostRepository postRepository;
    private LiveData<Post> postLiveData;

    public PostViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        postRepository = new PostRepository();
        postLiveData = postRepository.getPostLiveData();
    }

    public void getPosts() {
        postRepository.getPosts();
    }

    public LiveData<Post> getPostLiveData() {
        return postLiveData;
    }
}
