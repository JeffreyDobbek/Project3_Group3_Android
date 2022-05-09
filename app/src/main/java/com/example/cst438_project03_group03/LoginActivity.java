package com.example.cst438_project03_group03;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cst438_project03_group03.database.AppDatabase;
import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.database.UserDao;
import com.example.cst438_project03_group03.models.CreateAccountResult;
import com.example.cst438_project03_group03.models.UserInfo;
import com.example.cst438_project03_group03.util.Constants;
import com.example.cst438_project03_group03.viewmodels.UserViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.tasks.Task;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Class: LoginActivity.java
 * Description: First screen when opening the app.
 *              Prompts the user to log into their account.
 */
public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_IS_ADMIN = "com.example.cst438_project03_group03.EXTRA_IS_ADMIN";
    public static final String EXTRA_USER_ID = "com.example.cst438_project03_group03.EXTRA_USER_ID";

    private Button mLoginButton, mCreateAccountButton, mForgotPasswordButton;
    private SignInButton mGoogleSignInButton;
    private EditText mUserText, mPasswordText;

    private UserDao userDao;
    private List<UserInfo> mUsers = new ArrayList<>();

    private UserViewModel mViewModel;

    private static final int RC_SIGN_IN = 0;
    private static final String TAG = "google sign in";

    private String adminUsername = "admin";
    private String adminPassword = "admin";

    private String mUsername;
    private String mPassword;

    private int mUserId;

    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount mGoogleSignInAccount;

    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSharedPrefs = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);

        automaticLogin();
        wireUpDisplay();
        setOnClickListeners();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mViewModel.init();
        
        mViewModel.getUserLiveData().observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo user) {
                SharedPreferences.Editor editor = mSharedPrefs.edit();
                editor.putInt(Constants.USER_ID_KEY, user.getUserId());
                editor.apply();

                Toast.makeText(getApplicationContext(), "Login Successful.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        
        mViewModel.getUserListLiveData().observe(this, new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> users) {
                mUsers = users;
            }
        });

        mViewModel.getCreateUserLiveData().observe(this, new Observer<CreateAccountResult>() {
            @Override
            public void onChanged(CreateAccountResult createAccountResult) {
                if (createAccountResult != null) {
                    SharedPreferences.Editor editor = mSharedPrefs.edit();
                    editor.putInt(Constants.USER_ID_KEY, createAccountResult.getNewId());
                    editor.apply();

                    Toast.makeText(getApplicationContext(), "Login Successful.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        mViewModel.getAllUsers();
    }

    private void setOnClickListeners() {
        /**
         * Attempts to log in the user.
         */
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fieldsField()) {
                    if (validCredentials()) {
                        mViewModel.getUserByUsername(mUsername);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid username and/or password.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * OAuth2 Google Sign in
         */
        mGoogleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });

        mForgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Automatically takes user to home page if already logged in.
     */
    private void automaticLogin() {
        if (mSharedPrefs.getInt(Constants.USER_ID_KEY, -1) != -1 || GoogleSignIn.getLastSignedInAccount(LoginActivity.this) != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Wires up display components.
     */
    private void wireUpDisplay() {
        mUserText = findViewById(R.id.enterUsername);
        mPasswordText = findViewById(R.id.enterPassword);

        mLoginButton = findViewById(R.id.buttonLogin);
        mGoogleSignInButton = findViewById(R.id.google_sign_in_button);
        mCreateAccountButton = findViewById(R.id.createAccountButton);
        mForgotPasswordButton = findViewById(R.id.forgot_password_button);
    }

    /**
     * Checks that all text fields have been filled.
     * @return true if all are filled, false otherwise.
     */
    private boolean fieldsField() {
        mUsername = mUserText.getText().toString();
        mPassword = mPasswordText.getText().toString();

        return !mUsername.isEmpty() && !mPassword.isEmpty();
    }

    /**
     * Checks that all field values are valid.
     * @return true if all are valid, false otherwise.
     */
    private boolean validCredentials() {
        mViewModel.getAllUsers();

        for (UserInfo user : mUsers) {
            BCrypt.Result result = BCrypt.verifyer().verify(mPassword.toCharArray(), user.getPassword());

            if (user.getUsername().equals(mUsername) && result.verified) {
                return true;
            }
        }
        return false;
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            if (checkIfRegistered(account)) {
                SharedPreferences.Editor editor = mSharedPrefs.edit();
                editor.putInt(Constants.USER_ID_KEY, mUserId);
                editor.apply();

                Toast.makeText(getApplicationContext(), "Login Successful.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } else {
                UserInfo newUser = new UserInfo();
                newUser.setUsername(account.getDisplayName());
                newUser.setEmail(account.getEmail());
                newUser.setName(account.getDisplayName());
                newUser.setPassword(account.getId());

                if (account.getPhotoUrl() != null) {
                    newUser.setPic(account.getPhotoUrl().toString());
                }
                mViewModel.createUser(newUser);
            }

        } catch (ApiException e) {
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private boolean checkIfRegistered(GoogleSignInAccount account) {
        for (UserInfo user : mUsers) {
            if (user.getEmail().equals(account.getEmail())) {
                mUserId = user.getUserId();
                return true;
            }
        }
        return false;
    }
}