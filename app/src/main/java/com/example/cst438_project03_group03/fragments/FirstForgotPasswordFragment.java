package com.example.cst438_project03_group03.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cst438_project03_group03.LoginActivity;
import com.example.cst438_project03_group03.R;
import com.example.cst438_project03_group03.databinding.FragmentFirstForgotPasswordBinding;
import com.example.cst438_project03_group03.models.UserInfo;
import com.example.cst438_project03_group03.viewmodels.UserViewModel;

import java.util.List;

/**
 * Class: FirstForgotPasswordFragment.java
 * Description: Part 1 of Forgot Password feature
 *              Asks for the user's username and email for authentication before resetting their password.
 */
public class FirstForgotPasswordFragment extends Fragment {

    private FragmentFirstForgotPasswordBinding mBinding;

    private ImageView mBackIv;

    private Button mConfirmBtn;

    private EditText mEmailField;
    private EditText mUsernameField;

    private String mEmail;
    private String mUsername;

    private List<UserInfo> mUsers;

    private UserViewModel mUserViewModel;

    public static FirstForgotPasswordFragment newInstance() {
        return new FirstForgotPasswordFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentFirstForgotPasswordBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wireUpDisplay();
        setOnClickListeners();
        setUserViewModel();

        mUserViewModel.getAllUsers();
    }

    private void wireUpDisplay() {
        mBackIv = mBinding.forgotPasswordBackIv;
        mConfirmBtn = mBinding.forgotPasswordEmailConfirmButton;
        mEmailField = mBinding.forgotPasswordEnterEmail;
        mUsernameField = mBinding.forgotPasswordEnterUsername;
    }

    private void setOnClickListeners() {
        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFields();

                if (fieldsFilled()) {
                    if (validCredentials()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("username", mUsername);

                        Fragment fragment = SecondForgotPasswordFragment.newInstance();
                        fragment.setArguments(bundle);

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.forgot_password_fragment_fl, fragment).commit();

                        Toast.makeText(getContext().getApplicationContext(), "Credentials Confirmed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext().getApplicationContext(), "This email and/or username are not associated with an account.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUserViewModel() {
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mUserViewModel.init(getActivity().getApplication());

        mUserViewModel.getUserListLiveData().observe(getViewLifecycleOwner(), new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> users) {
                if (users != null) {
                    mUsers = users;
                }
            }
        });
    }

    private void getFields() {
        mEmail = mEmailField.getText().toString();
        mUsername = mUsernameField.getText().toString();
    }

    private boolean fieldsFilled() {
        return !mEmail.isEmpty() && !mUsername.isEmpty();
    }

    private boolean validCredentials() {
        mUserViewModel.getAllUsers();

        for (UserInfo user : mUsers) {
            if (user.getEmail().equals(mEmail) && user.getUsername().equals(mUsername)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}