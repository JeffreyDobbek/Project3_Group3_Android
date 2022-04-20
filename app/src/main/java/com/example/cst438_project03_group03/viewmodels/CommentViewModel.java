package com.example.cst438_project03_group03.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cst438_project03_group03.database.Comment;
import com.example.cst438_project03_group03.repositories.CommentRepository;

public class CommentViewModel extends AndroidViewModel {

    private CommentRepository commentRepository;
    private LiveData<Comment> commentLiveData;

    public CommentViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        commentRepository = new CommentRepository();
        commentLiveData = commentRepository.getCommentLiveData();
    }

    public void getComments() {
        commentRepository.getComments();
    }

    public LiveData<Comment> getCommentLiveData() {
        return commentLiveData;
    }
}
