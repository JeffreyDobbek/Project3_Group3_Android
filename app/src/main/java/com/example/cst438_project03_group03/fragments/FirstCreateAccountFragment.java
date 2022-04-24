package com.example.cst438_project03_group03.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cst438_project03_group03.CreateAccountActivity;
import com.example.cst438_project03_group03.LoginActivity;
import com.example.cst438_project03_group03.R;
import com.example.cst438_project03_group03.databinding.FragmentFirstCreateAccountBinding;

public class FirstCreateAccountFragment extends Fragment {

    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mConfirmPasswordField;

    private String mEmail;
    private String mPassword;
    private String mConfirmPassword;

    private Button mRegisterButton;

    private FragmentFirstCreateAccountBinding mBinding;

    public static FirstCreateAccountFragment newInstance() {
        return new FirstCreateAccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentFirstCreateAccountBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wireUpDisplay();

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fieldsFilled()) {
                    if (checkFields()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("email", mEmail);
                        bundle.putString("password", mPassword);
                        bundle.putString("confirmPassword", mConfirmPassword);

                        Fragment fragment = SecondCreateAccountFragment.newInstance();
                        fragment.setArguments(bundle);

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.create_account_fragment_fl, fragment).commit();
                    }
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void wireUpDisplay() {
        mEmailField = mBinding.createAccountEmailEditText;
        mPasswordField = mBinding.createAccountPasswordEditText;
        mConfirmPasswordField = mBinding.createAccountConfirmPasswordEditText;

        mRegisterButton = mBinding.createAccountButton;
    }

    private boolean fieldsFilled() {
        mEmail = mEmailField.getText().toString();
        mPassword = mPasswordField.getText().toString();
        mConfirmPassword = mConfirmPasswordField.getText().toString();

        return !mEmail.isEmpty() && !mPassword.isEmpty() && !mConfirmPassword.isEmpty();
    }

    private boolean checkFields() {
        // check if email is already in database
        if (emailInDatabase()) {
            Toast.makeText(getContext().getApplicationContext(), "This email is already registered with an existing account", Toast.LENGTH_SHORT).show();
            return false;
        }
        // check that email has an @
        if (!mEmail.contains("@") || !mEmail.contains(".com")) {
            Toast.makeText(getContext().getApplicationContext(), "Not a valid email.", Toast.LENGTH_SHORT).show();
            return false;
        }
        // check if username is already in database
        if (usernameTaken()) {
            Toast.makeText(getContext().getApplicationContext(), "This username is taken.", Toast.LENGTH_SHORT).show();
            return false;
        }
        // check that both passwords match
        if (!mPassword.equals(mConfirmPassword)) {
            Toast.makeText(getContext().getApplicationContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // check if email is in database
    private boolean emailInDatabase() {
        return false;
    }

    // check if username is in database
    private boolean usernameTaken() {
        return false;
    }
}