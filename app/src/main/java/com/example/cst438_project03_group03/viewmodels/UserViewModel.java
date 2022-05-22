package com.example.cst438_project03_group03.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.models.CreateAccountResponse;
import com.example.cst438_project03_group03.models.UpdateUserResponse;
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
    private LiveData<UpdateUserResponse> updateUserLiveData;

    public UserViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(@NonNull Application application) {
        userRepository = new UserRepository(application);

        userListLiveData = userRepository.getUserListLiveData();
        userLiveData = userRepository.getUserLiveData();
        createUserLiveData = userRepository.getCreateUserLiveData();
        updateUserLiveData = userRepository.getUpdateUserLiveData();
    }

    // Room
    public void insert(User user) {
        userRepository.insert(user);
    }
    public void update(User user) {
        userRepository.update(user);
    }
    public void delete(User user) {
        userRepository.update(user);
    }
    public void getUserById(int userId) {
        userRepository.getUserById(userId);
    }

    // API
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
    public void updateUser(UserInfo userInfo) {
        userRepository.updateUser(userInfo);
    }
    public void updatePassword(UserInfo userInfo) {
        userRepository.updatePassword(userInfo);
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
    public LiveData<UpdateUserResponse> getUpdateUserLiveData() {
        return updateUserLiveData;
    }
}
