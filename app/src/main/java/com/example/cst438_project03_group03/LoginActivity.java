package com.example.cst438_project03_group03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.cst438_project03_group03.models.CreateAccountResponse;
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

    private Button mLoginButton, mCreateAccountButton, mForgotPasswordButton;
    private SignInButton mGoogleSignInButton;
    private EditText mUserText, mPasswordText;

    private UserInfo mNewUser;
    private List<UserInfo> mUsers = new ArrayList<>();

    private UserViewModel mViewModel;

    private static final int RC_SIGN_IN = 0;

    private String mName;
    private String mUsername;
    private String mPassword;
    private String mProfilePic;

    private int mUserId;

    private GoogleSignInClient mGoogleSignInClient;

    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSharedPrefs = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);

        automaticLogin();
        wireUpDisplay();
        setOnClickListeners();

        /**
         * Code needed for Google sign in.
         */
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        setUserViewModel();
        mViewModel.getAllUsers();
    }

    /**
     * Sets button listeners.
     */
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

        /**
         * Allows user to reset their password.
         */
        mForgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        /**
         * Takes user to create account feature.
         */
        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Initializes the User view model and sets up live data observers.
     */
    private void setUserViewModel() {
        mViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mViewModel.init();

        /**
         * Waits for response from getting a user from the database.
         */
        mViewModel.getUserLiveData().observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo user) {
                /**
                 * If user is found, log them in.
                 */
                if (user != null) {
                    SharedPreferences.Editor editor = mSharedPrefs.edit();
                    editor.putInt(Constants.USER_ID_KEY, user.getUserId());
                    editor.putString(Constants.USER_NAME_KEY, user.getName());
                    editor.putString(Constants.USER_USERNAME_KEY, user.getUsername());
                    editor.putString(Constants.USER_PROFILE_PIC_KEY, user.getImage());
                    editor.apply();

                    Toast.makeText(getApplicationContext(), "Login Successful.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        /**
         * Waits for response from getting all users in the database.
         */
        mViewModel.getUserListLiveData().observe(this, new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> users) {
                if (users != null) {
                    mUsers = users;
                }
            }
        });

        /**
         * Waits for response from registering a user.
         */
        mViewModel.getCreateUserLiveData().observe(this, new Observer<CreateAccountResponse>() {
            @Override
            public void onChanged(CreateAccountResponse createAccountResponse) {
                /**
                 * If account is created successfully, log in the user.
                 */
                if (createAccountResponse != null) {
                    SharedPreferences.Editor editor = mSharedPrefs.edit();
                    editor.putInt(Constants.USER_ID_KEY, createAccountResponse.getNewId());
                    editor.putString(Constants.USER_NAME_KEY, mNewUser.getName());
                    editor.putString(Constants.USER_USERNAME_KEY, mNewUser.getUsername());
                    editor.putString(Constants.USER_PROFILE_PIC_KEY, mNewUser.getPic());
                    editor.apply();

                    Toast.makeText(getApplicationContext(), "Login Successful.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
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

    /**
     * Google sign in.
     */
    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * Attempt to sign in with Google account.
     * @param requestCode Sign in request code.
     * @param resultCode Sign in result code.
     * @param data Sign in data.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    /**
     * Handles Google sign in cases.
     * @param completedTask Google sign in task.
     */
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            /**
             * If user is already registered, log them in with their current credentials.
             * Else, use Google credentials to create a new account for user.
             */
            if (checkIfRegistered(account)) {
                SharedPreferences.Editor editor = mSharedPrefs.edit();
                editor.putInt(Constants.USER_ID_KEY, mUserId);
                editor.putString(Constants.USER_NAME_KEY, mName);
                editor.putString(Constants.USER_USERNAME_KEY, mUsername);
                editor.putString(Constants.USER_PROFILE_PIC_KEY, mProfilePic);
                editor.apply();

                Toast.makeText(getApplicationContext(), "Login Successful.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } else {
                mNewUser = new UserInfo();
                mNewUser.setUsername(account.getDisplayName());
                mNewUser.setEmail(account.getEmail());
                mNewUser.setName(account.getDisplayName());
                mNewUser.setPassword(account.getId());

                if (account.getPhotoUrl() != null) {
                    mNewUser.setPic(account.getPhotoUrl().toString());
                }
                mViewModel.createUser(mNewUser);
            }

        } catch (ApiException e) {
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    /**
     * Check if the user is already registered if signing in with a Google account.
     * @param account A Google account.
     * @return true or false.
     */
    private boolean checkIfRegistered(GoogleSignInAccount account) {
        for (UserInfo user : mUsers) {
            if (user.getEmail().equals(account.getEmail())) {
                mUserId = user.getUserId();
                mName = user.getName();
                mUsername = user.getUsername();
                mProfilePic = user.getImage();
                return true;
            }
        }
        return false;
    }
}