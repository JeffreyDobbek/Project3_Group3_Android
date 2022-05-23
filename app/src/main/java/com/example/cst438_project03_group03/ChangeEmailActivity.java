package com.example.cst438_project03_group03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cst438_project03_group03.databinding.ActivityChangeEmailBinding;
import com.example.cst438_project03_group03.models.UpdateUserResponse;
import com.example.cst438_project03_group03.models.UserInfo;
import com.example.cst438_project03_group03.util.Constants;
import com.example.cst438_project03_group03.viewmodels.UserViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class ChangeEmailActivity extends AppCompatActivity {

    private ActivityChangeEmailBinding mBinding;

    private String mEmail;
    private String mNewEmail;

    private int mUserId;

    private String mName;
    private String mUsername;
    private String mImage;

    private ImageView mCloseIv;

    private EditText mNewEmailEt;

    private Button mConfirmBtn;

    private UserViewModel mUserViewModel;

    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email);

        getSupportActionBar().hide();

        mSharedPrefs = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);
        mUserId = mSharedPrefs.getInt(Constants.USER_ID_KEY, -1);
        mName = mSharedPrefs.getString(Constants.USER_NAME_KEY, null);
        mUsername = mSharedPrefs.getString(Constants.USER_USERNAME_KEY, null);
        mImage = mSharedPrefs.getString(Constants.USER_PROFILE_PIC_KEY, null);

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

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(ChangeEmailActivity.this);

        if (account != null) {
            mNewEmailEt.setEnabled(false);
            mConfirmBtn.setClickable(false);
            Snackbar.make(mBinding.getRoot().getRootView(), "Can't edit email if signed in with Google account.", Snackbar.LENGTH_LONG).show();
        }
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
        mUserViewModel.init(getApplication());

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

                    UserInfo user = new UserInfo();
                    user.setUserId(mUserId);
                    user.setName(mName);
                    user.setEmail(mNewEmail);
                    user.setUsername(mUsername);
                    user.setImage(mImage);

                    mUserViewModel.updateUser(user);
                } else {
                    Snackbar.make(mBinding.getRoot().getRootView(), "An error occurred", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        mUserViewModel.getUpdateUserLiveData().observe(this, new Observer<UpdateUserResponse>() {
            @Override
            public void onChanged(UpdateUserResponse response) {
                if (response != null) {
                    Toast.makeText(getApplicationContext(), "Email Successfully Changed.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                    startActivity(intent);
                } else {
                    Snackbar.make(mBinding.getRoot().getRootView(), "An error occurred", Snackbar.LENGTH_SHORT).show();
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