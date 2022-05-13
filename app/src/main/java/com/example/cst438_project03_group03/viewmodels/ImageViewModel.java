package com.example.cst438_project03_group03.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cst438_project03_group03.database.Comment;
import com.example.cst438_project03_group03.database.Image;
import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.models.ImageInfo;
import com.example.cst438_project03_group03.models.ImgurResponse;
import com.example.cst438_project03_group03.models.ImgurUpload;
import com.example.cst438_project03_group03.repositories.CommentRepository;
import com.example.cst438_project03_group03.repositories.ImageRepository;
import com.example.cst438_project03_group03.repositories.UserRepository;

import java.util.List;

public class ImageViewModel extends AndroidViewModel {

    private ImageRepository imageRepository;
    private LiveData<List<ImageInfo>> postImagesLiveData;
    private LiveData<ImgurResponse> imgurResponseLiveData;

    public ImageViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        imageRepository = new ImageRepository();

        postImagesLiveData = imageRepository.getPostImagesLiveData();
        imgurResponseLiveData = imageRepository.getImgurResponseLiveData();
    }

    public void getAllPostPics() {
        imageRepository.getAllPostPics();
    }
    public void imgurUpload(ImgurUpload imgurUpload, String clientId) {
        imageRepository.imgurUpload(imgurUpload, clientId);
    }

    public LiveData<List<ImageInfo>> getPostImagesLiveData() {
        return postImagesLiveData;
    }
    public LiveData<ImgurResponse> getImgurResponseLiveData() {
        return imgurResponseLiveData;
    }
}
