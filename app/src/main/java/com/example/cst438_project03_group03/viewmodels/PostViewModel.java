package com.example.cst438_project03_group03.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cst438_project03_group03.database.Post;
import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.models.PostInfo;
import com.example.cst438_project03_group03.repositories.PostRepository;
import com.example.cst438_project03_group03.repositories.UserRepository;

import java.util.List;

public class PostViewModel extends AndroidViewModel {

    private PostRepository postRepository;
    private LiveData<List<PostInfo>> allLivePostsLiveData;

    public PostViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        postRepository = new PostRepository();
        allLivePostsLiveData = postRepository.getAllLivePostsLiveData();
    }

    public void getAllLivePosts() {
        postRepository.getAllLivePosts();
    }

    public LiveData<List<PostInfo>> getAllLivePostsLiveData() {
        return allLivePostsLiveData;
    }
}
