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
import com.example.cst438_project03_group03.models.IsPicLikedResponse;
import com.example.cst438_project03_group03.models.LikePicResponse;
import com.example.cst438_project03_group03.repositories.CommentRepository;
import com.example.cst438_project03_group03.repositories.ImageRepository;
import com.example.cst438_project03_group03.repositories.UserRepository;

import java.util.List;

/**
 * Class: ImageViewModel.java
 * Description:
 */
public class ImageViewModel extends AndroidViewModel {

    private ImageRepository imageRepository;
    private LiveData<List<ImageInfo>> postImagesLiveData;
    private LiveData<ImgurResponse> imgurResponseLiveData;
    private LiveData<IsPicLikedResponse> isPicLikedResponseLiveData;
    private LiveData<LikePicResponse> likePicResponseLiveData;

    public ImageViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        imageRepository = new ImageRepository();

        postImagesLiveData = imageRepository.getPostImagesLiveData();
        imgurResponseLiveData = imageRepository.getImgurResponseLiveData();
        isPicLikedResponseLiveData = imageRepository.getIsPicLikedResponseLiveData();
        likePicResponseLiveData = imageRepository.getLikePicResponseLiveData();
    }

    public void getAllPostPics() {
        imageRepository.getAllPostPics();
    }

    public void isPicLiked(int userId, int imageId) {
        imageRepository.isPicLiked(userId, imageId);
    }

    public void imgurUpload(ImgurUpload imgurUpload, String clientId) {
        imageRepository.imgurUpload(imgurUpload, clientId);
    }

    public void likePic(int userId, int imageId) {
        imageRepository.likePic(userId, imageId);
    }

    public void unlikePic(int userId, int imageId) {
        imageRepository.unlikePic(userId, imageId);
    }

    public LiveData<List<ImageInfo>> getPostImagesLiveData() {
        return postImagesLiveData;
    }

    public LiveData<ImgurResponse> getImgurResponseLiveData() {
        return imgurResponseLiveData;
    }

    public LiveData<IsPicLikedResponse> getIsPicLikedResponseLiveData() {
        return isPicLikedResponseLiveData;
    }

    public LiveData<LikePicResponse> getLikePicResponseLiveData() {
        return likePicResponseLiveData;
    }
}
