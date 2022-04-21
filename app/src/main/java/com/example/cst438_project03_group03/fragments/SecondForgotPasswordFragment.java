package com.example.cst438_project03_group03.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cst438_project03_group03.LoginActivity;
import com.example.cst438_project03_group03.databinding.FragmentSecondForgotPasswordBinding;

public class SecondForgotPasswordFragment extends Fragment {

    private FragmentSecondForgotPasswordBinding mBinding;

    private EditText mNewPasswordField;
    private EditText mConfirmPasswordField;

    private String mNewPassword;
    private String mConfirmPassword;

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

        mNewPasswordField = mBinding.forgotPasswordEnterNewPassword;
        mConfirmPasswordField = mBinding.forgotPasswordConfirmNewPassword;

        mBinding.forgotPasswordConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFields();

                if (fieldsFilled()) {
                    if (isNew()) {
                        if (passwordsMatch()) {
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

    // TODO: need to check in the database
    private boolean isNew() {
        // check that new password is not the same as the old password
        return true;
    }

    private boolean passwordsMatch() {
        return mNewPassword.equals(mConfirmPassword);
    }
}