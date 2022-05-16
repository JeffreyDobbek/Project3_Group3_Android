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
import com.example.cst438_project03_group03.databinding.ActivityChangePasswordBinding;
import com.example.cst438_project03_group03.models.UserInfo;
import com.example.cst438_project03_group03.viewmodels.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ChangePasswordActivity extends AppCompatActivity {

    private ActivityChangePasswordBinding mBinding;

    private String mPassword;
    private String mNewPassword;

    private ImageView mCloseIv;

    private EditText mNewPasswordEt;

    private Button mConfirmBtn;

    private UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getSupportActionBar().hide();

        mBinding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mPassword = getIntent().getStringExtra("password");

        wireUpDisplay();
        setOnClickListeners();
        setUserViewModel();
    }

    private void wireUpDisplay() {
        mCloseIv = mBinding.changePasswordCloseIv;
        mNewPasswordEt = mBinding.changePasswordNewPasswordEt;
        mConfirmBtn = mBinding.changePasswordConfirmBtn;
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
                if (fieldsFilled(mNewPasswordEt)) {
                    if (!mNewPassword.equals(mPassword)) {
                        // update password
                    } else {
                        Toast.makeText(getApplicationContext(), "New password can't be the same as old password.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUserViewModel() {
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mUserViewModel.init(getApplication());

        // update password
    }

    private boolean fieldsFilled(EditText newPasswordEt) {
        mNewPassword = newPasswordEt.getText().toString();
        return !mNewPassword.isEmpty();
    }
}