package com.example.cst438_project03_group03.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cst438_project03_group03.database.Comment;
import com.example.cst438_project03_group03.database.Image;
import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.models.ImageInfo;
import com.example.cst438_project03_group03.repositories.CommentRepository;
import com.example.cst438_project03_group03.repositories.ImageRepository;
import com.example.cst438_project03_group03.repositories.UserRepository;

import java.util.List;

public class ImageViewModel extends AndroidViewModel {

    private ImageRepository imageRepository;
    private LiveData<List<ImageInfo>> postImagesLiveData;

    public ImageViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        imageRepository = new ImageRepository();
        postImagesLiveData = imageRepository.getPostImagesLiveData();
    }

    public void getImages(int postId) {
        imageRepository.getPostImages(postId);
    }

    public LiveData<List<ImageInfo>> getPostImagesLiveData() {
        return postImagesLiveData;
    }
}
