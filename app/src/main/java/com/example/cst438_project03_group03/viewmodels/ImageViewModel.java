package com.example.cst438_project03_group03.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cst438_project03_group03.database.Comment;
import com.example.cst438_project03_group03.database.Image;
import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.repositories.CommentRepository;
import com.example.cst438_project03_group03.repositories.ImageRepository;
import com.example.cst438_project03_group03.repositories.UserRepository;

public class ImageViewModel extends AndroidViewModel {

    private ImageRepository imageRepository;
    private LiveData<Image> imageLiveData;

    public ImageViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        imageRepository = new ImageRepository();
        imageLiveData = imageRepository.getImageLiveData();
    }

    public void getImages() {
        imageRepository.getImages();
    }

    public LiveData<Image> getImageLiveData() {
        return imageLiveData;
    }
}
