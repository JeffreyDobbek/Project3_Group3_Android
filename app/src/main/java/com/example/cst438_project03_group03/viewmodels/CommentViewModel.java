package com.example.cst438_project03_group03.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cst438_project03_group03.database.Comment;
import com.example.cst438_project03_group03.models.CommentInfo;
import com.example.cst438_project03_group03.models.UploadCommentResponse;
import com.example.cst438_project03_group03.repositories.CommentRepository;

import java.util.List;

public class CommentViewModel extends AndroidViewModel {

    private CommentRepository commentRepository;
    private LiveData<UploadCommentResponse> uploadCommentResponseLiveData;
    private LiveData<List<CommentInfo>> commentListLiveData;

    public CommentViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        commentRepository = new CommentRepository();
        uploadCommentResponseLiveData = commentRepository.getUploadCommentResponseLiveData();
        commentListLiveData = commentRepository.getCommentListLiveData();
    }

    public void uploadComment(CommentInfo commentInfo) {
        commentRepository.uploadComment(commentInfo);
    }
    public void getPostComments(int postId) {
        commentRepository.getPostComments(postId);
    }

    public LiveData<UploadCommentResponse> getUploadCommentResponseLiveData() {
        return uploadCommentResponseLiveData;
    }
    public LiveData<List<CommentInfo>> getCommentListLiveData() {
        return commentListLiveData;
    }
}
