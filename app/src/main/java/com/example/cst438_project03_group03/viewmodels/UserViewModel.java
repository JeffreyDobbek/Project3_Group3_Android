package com.example.cst438_project03_group03.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.models.CreateAccountResult;
import com.example.cst438_project03_group03.models.UserInfo;
import com.example.cst438_project03_group03.repositories.UserRepository;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;

/**
 * Class: UserViewModel.java
 * Description:
 */
public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private LiveData<List<UserInfo>> userListLiveData;
    private LiveData<UserInfo> userLiveData;
    private LiveData<CreateAccountResult> createUserLiveData;

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
    public LiveData<CreateAccountResult> getCreateUserLiveData() {
        return createUserLiveData;
    }
}
