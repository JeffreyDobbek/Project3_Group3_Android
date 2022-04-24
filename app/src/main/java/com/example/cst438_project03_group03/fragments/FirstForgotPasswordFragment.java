package com.example.cst438_project03_group03.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cst438_project03_group03.R;
import com.example.cst438_project03_group03.databinding.FragmentFirstForgotPasswordBinding;

public class FirstForgotPasswordFragment extends Fragment {

    private FragmentFirstForgotPasswordBinding mBinding;

    private EditText mEmailField;
    private EditText mUsernameField;

    private String mEmail;
    private String mUsername;

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

        mEmailField = mBinding.forgotPasswordEnterEmail;
        mUsernameField = mBinding.forgotPasswordEnterUsername;

        mBinding.forgotPasswordEmailConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFields();

                if (fieldsFilled()) {
                    if (validCredentials()) {
                        Fragment fragment = SecondForgotPasswordFragment.newInstance();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.forgot_password_fragment_fl, fragment).commit();
                    } else {
                        Toast.makeText(getContext().getApplicationContext(), "This email and username are not associated with an account.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
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

    // TODO
    private boolean validCredentials() {
        // check that email and username are match in the database
        return true;
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