package com.example.cst438_project03_group03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cst438_project03_group03.databinding.ActivityChangeNameAndUsernameBinding;
import com.example.cst438_project03_group03.models.UserInfo;
import com.example.cst438_project03_group03.viewmodels.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ChangeNameAndUsernameActivity extends AppCompatActivity {

    private ActivityChangeNameAndUsernameBinding mBinding;

    private String mName;
    private String mNewName;
    private String mUsername;
    private String mNewUsername;

    private ImageView mCloseIv;

    private EditText mNewNameEt;
    private EditText mNewUsernameEt;

    private Button mConfirmBtn;

    private UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name_and_username);

        getSupportActionBar().hide();

        mBinding = ActivityChangeNameAndUsernameBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mName = getIntent().getStringExtra("name");
        mUsername = getIntent().getStringExtra("username");

        wireUpDisplay();
        setOnClickListeners();
        setUserViewModel();
    }

    private void wireUpDisplay() {
        mCloseIv = mBinding.changeNameCloseIv;
        mNewNameEt = mBinding.changeNameNameEt;
        mNewUsernameEt = mBinding.changeNameUsernameEt;
        mConfirmBtn = mBinding.changeNameConfirmBtn;

        mNewNameEt.setText(mName);
        mNewUsernameEt.setText(mUsername);
    }

    private void setOnClickListeners() {
        mCloseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fieldsFilled(mNewNameEt, mNewUsernameEt)) {
                    mUserViewModel.getAllUsers();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUserViewModel() {
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mUserViewModel.init(getApplication());

        mUserViewModel.getUserListLiveData().observe(this, new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> users) {
                if (users != null) {
                    for (UserInfo user : users) {
                        if (user.getUsername().equals(mNewUsername)) {
                            Toast.makeText(getApplicationContext(), "This username is taken.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    Toast.makeText(getApplicationContext(), "Username Successfully Changed.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                    startActivity(intent);
                    // else update the username
                } else {
                    Snackbar.make(mBinding.getRoot().getRootView(), "An error occurred", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean fieldsFilled(EditText newNameEt, EditText newUsernameEt) {
        mNewName = newNameEt.getText().toString();
        mNewUsername = newUsernameEt.getText().toString();
        return !mNewName.isEmpty() && !mNewUsername.isEmpty();
    }
}