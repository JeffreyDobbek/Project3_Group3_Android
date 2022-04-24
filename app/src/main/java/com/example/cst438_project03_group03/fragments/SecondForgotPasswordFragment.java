package com.example.cst438_project03_group03.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cst438_project03_group03.LoginActivity;
import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.databinding.FragmentSecondForgotPasswordBinding;
import com.example.cst438_project03_group03.viewmodels.UserViewModel;

import java.util.List;

/**
 * Class: SecondForgotPasswordFragment.java
 * Description: Part 2 of Forgot Password feature
 *              User can create their new password.
 */
public class SecondForgotPasswordFragment extends Fragment {

    private FragmentSecondForgotPasswordBinding mBinding;

    private EditText mNewPasswordField;
    private EditText mConfirmPasswordField;

    private String mNewPassword;
    private String mConfirmPassword;
    private String mUsername;

    private User mUser;

    private UserViewModel mViewModel;

    public static SecondForgotPasswordFragment newInstance() {
        return new SecondForgotPasswordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentSecondForgotPasswordBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        assert bundle != null;
        mUsername = bundle.getString("username");

        mNewPasswordField = mBinding.forgotPasswordEnterNewPassword;
        mConfirmPasswordField = mBinding.forgotPasswordConfirmNewPassword;

        mViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mViewModel.init();
        mViewModel.getUserLiveData().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    mUser = user;
                }
            }
        });

        mViewModel.getUserByUsername(mUsername);

        mBinding.forgotPasswordConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFields();

                if (fieldsFilled()) {
                    if (isNew()) {
                        if (passwordsMatch()) {
                            updatePassword();
                            Toast.makeText(getContext().getApplicationContext(), "Password Successfully Reset.", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(view.getContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext().getApplicationContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext().getApplicationContext(), "New password cannot be the same as old password.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getFields() {
        mNewPassword = mNewPasswordField.getText().toString();
        mConfirmPassword = mConfirmPasswordField.getText().toString();
    }

    private boolean fieldsFilled() {
        return !mNewPassword.isEmpty() && !mConfirmPassword.isEmpty();
    }

    private boolean isNew() {
        mViewModel.getUserByUsername(mUsername);

        return !mUser.getPassword().equals(mNewPassword);
    }

    private boolean passwordsMatch() {
        return mNewPassword.equals(mConfirmPassword);
    }

    // TODO: update user's information in the database
    private void updatePassword() {

    }
}