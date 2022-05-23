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
import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.databinding.FragmentFirstCreateAccountBinding;
import com.example.cst438_project03_group03.models.UserInfo;
import com.example.cst438_project03_group03.viewmodels.UserViewModel;

import java.util.List;

/**
 * Class: FirstCreateAccountFragment.java
 * Description: Part 1 of Create Account feature
 *              Asks user to enter an email and a password for their account.
 */
public class FirstCreateAccountFragment extends Fragment {

    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mConfirmPasswordField;

    private ImageView mBackIv;

    private String mEmail;
    private String mPassword;
    private String mConfirmPassword;

    private List<UserInfo> mUsers;

    private Button mRegisterButton;

    private UserViewModel mViewModel;

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
        setOnClickListeners();

        mViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mViewModel.init(getActivity().getApplication());
        mViewModel.getUserListLiveData().observe(getViewLifecycleOwner(), new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> users) {
                if (users != null) {
                    mUsers = users;
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mViewModel.getAllUsers();
    }

    /**
     * Wires up display components.
     */
    private void wireUpDisplay() {
        mEmailField = mBinding.createAccountEmailEditText;
        mPasswordField = mBinding.createAccountPasswordEditText;
        mConfirmPasswordField = mBinding.createAccountConfirmPasswordEditText;

        mRegisterButton = mBinding.createAccountButton;
        mBackIv = mBinding.createAccountBackIv;
    }

    private void setOnClickListeners() {
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fieldsFilled()) {
                    if (validCredentials()) {
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

        mBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Checks that all text fields have been filled.
     * @return true if all are filled, false otherwise.
     */
    private boolean fieldsFilled() {
        mEmail = mEmailField.getText().toString();
        mPassword = mPasswordField.getText().toString();
        mConfirmPassword = mConfirmPasswordField.getText().toString();

        return !mEmail.isEmpty() && !mPassword.isEmpty() && !mConfirmPassword.isEmpty();
    }

    /**
     * Checks that all field values are valid.
     * @return true if all are valid, false otherwise.
     */
    private boolean validCredentials() {
        // check if email is already in database
        if (emailInDatabase()) {
            Toast.makeText(getContext().getApplicationContext(), "This email is already registered with an existing account.", Toast.LENGTH_LONG).show();
            return false;
        }
        // check that email has an @
        if (!mEmail.contains("@") || !mEmail.contains(".com")) {
            Toast.makeText(getContext().getApplicationContext(), "Not a valid email.", Toast.LENGTH_SHORT).show();
            return false;
        }
        // check that both passwords match
        if (!mPassword.equals(mConfirmPassword)) {
            Toast.makeText(getContext().getApplicationContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Checks the database to see if the email has already been used.
     * @return true if email in use, false otherwise.
     */
    private boolean emailInDatabase() {
        mViewModel.getAllUsers();

        for (UserInfo user : mUsers) {
            if (user.getEmail().equals(mEmail)) {
                return true;
            }
        }
        return false;
    }
}