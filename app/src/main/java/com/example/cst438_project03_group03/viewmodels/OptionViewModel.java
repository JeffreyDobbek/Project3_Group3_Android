package com.example.cst438_project03_group03.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cst438_project03_group03.database.Option;
import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.repositories.OptionRepository;
import com.example.cst438_project03_group03.repositories.UserRepository;

public class OptionViewModel extends AndroidViewModel {

    private OptionRepository optionRepository;
    private LiveData<Option> optionLiveData;

    public OptionViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        optionRepository = new OptionRepository();
        optionLiveData = optionRepository.getOptionLiveData();
    }

    public void getOptions() {
        optionRepository.getOptions();
    }

    public LiveData<Option> getOptionLiveData() {
        return optionLiveData;
    }
}
