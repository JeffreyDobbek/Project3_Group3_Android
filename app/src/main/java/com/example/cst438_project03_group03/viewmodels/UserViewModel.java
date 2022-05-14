package com.example.cst438_project03_group03.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cst438_project03_group03.models.CreateAccountResponse;
import com.example.cst438_project03_group03.models.UserInfo;
import com.example.cst438_project03_group03.repositories.UserRepository;

import java.util.List;

/**
 * Class: UserViewModel.java
 * Description:
 */
public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private LiveData<List<UserInfo>> userListLiveData;
    private LiveData<UserInfo> userLiveData;
    private LiveData<CreateAccountResponse> createUserLiveData;

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        userRepository = new UserRepository();

        userListLiveData = userRepository.getUserListLiveData();
        userLiveData = userRepository.getUserLiveData();
        createUserLiveData = userRepository.getCreateUserLiveData();
    }

    public void getAllUsers() {
        userRepository.getAllUsers();
    }
    public void getUserByUserId(int userId) {
        userRepository.getUserByUserId(userId);
    }
    public void getUserByUsername(String username) {
        userRepository.getUserByUsername(username);
    }
    public void createUser(UserInfo userInfo) {
        userRepository.createUser(userInfo);
    }

    public LiveData<List<UserInfo>> getUserListLiveData() {
        return userListLiveData;
    }
    public LiveData<UserInfo> getUserLiveData() {
        return userLiveData;
    }
    public LiveData<CreateAccountResponse> getCreateUserLiveData() {
        return createUserLiveData;
    }
}
