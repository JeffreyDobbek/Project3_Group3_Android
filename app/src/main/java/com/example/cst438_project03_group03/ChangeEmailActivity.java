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

import com.example.cst438_project03_group03.databinding.ActivityChangeEmailBinding;
import com.example.cst438_project03_group03.models.UserInfo;
import com.example.cst438_project03_group03.viewmodels.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ChangeEmailActivity extends AppCompatActivity {

    private ActivityChangeEmailBinding mBinding;

    private String mEmail;
    private String mNewEmail;

    private ImageView mCloseIv;

    private EditText mNewEmailEt;

    private Button mConfirmBtn;

    private UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        getSupportActionBar().hide();

        mBinding = ActivityChangeEmailBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mEmail = getIntent().getStringExtra("email");

        wireUpDisplay();
        setOnClickListeners();
        setUserViewModel();
    }

    private void wireUpDisplay() {
        mCloseIv = mBinding.changeEmailCloseIv;
        mNewEmailEt = mBinding.changeEmailNewEmailEt;
        mConfirmBtn = mBinding.changeEmailConfirmBtn;

        mNewEmailEt.setText(mEmail);
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
                if (fieldsFilled(mNewEmailEt)) {
                    if (mNewEmail.contains("@")) {
                        mUserViewModel.getAllUsers();
                    } else {
                        Toast.makeText(getApplicationContext(), "Not a valid email.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     *
     */
    private void setUserViewModel() {
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mUserViewModel.init();

        mUserViewModel.getUserListLiveData().observe(this, new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> users) {
                if (users != null) {
                    for (UserInfo user : users) {
                        if (user.getEmail().equals(mNewEmail)) {
                            Toast.makeText(getApplicationContext(), "This email is taken.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    Toast.makeText(getApplicationContext(), "Email Successfully Changed.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                    startActivity(intent);
                    // else update the email
                } else {
                    Snackbar.make(mBinding.getRoot().getRootView(), "An error occurred", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Checks if the new email field is filled.
     * @param newEmailEt Content of the new email edit text.
     * @return true or false.
     */
    private boolean fieldsFilled(EditText newEmailEt) {
        mNewEmail = newEmailEt.getText().toString();
        return !mNewEmail.isEmpty();
    }
}