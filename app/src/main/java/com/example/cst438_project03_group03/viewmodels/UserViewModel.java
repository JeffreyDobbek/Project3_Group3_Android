package com.example.cst438_project03_group03.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.repositories.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private LiveData<List<User>> userListLiveData;
    private LiveData<User> userLiveData;

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        userRepository = new UserRepository();
        userListLiveData = userRepository.getUserListLiveData();
        userLiveData = userRepository.getUserLiveData();
    }

    public void getAllUsers() {
        userRepository.getAllUsers();
    }
    public void getUserByUsername(String username) {
        userRepository.getUserByUsername(username);
    }

    public LiveData<List<User>> getUserListLiveData() {
        return userListLiveData;
    }
    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }
}
