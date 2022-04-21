package com.example.cst438_project03_group03.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.repositories.UserRepository;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private LiveData<User> userLiveData;

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        userRepository = new UserRepository();
        userLiveData = userRepository.getUserLiveData();
    }

    public void getUsers() {
        userRepository.getUsers();
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }
}
