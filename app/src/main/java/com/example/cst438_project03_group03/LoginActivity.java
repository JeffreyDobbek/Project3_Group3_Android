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
import com.example.cst438_project03_group03.models.UserInfo;
import com.example.cst438_project03_group03.util.Constants;
import com.example.cst438_project03_group03.viewmodels.UserViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;

import java.util.List;

import com.google.android.gms.tasks.Task;

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
    private List<UserInfo> mUsers;

    private UserViewModel mViewModel;

    private static final int RC_SIGN_IN = 0;
    private static final String TAG = "google sign in";

    private String adminUsername = "admin";
    private String adminPassword = "admin";

    private String mUsername;
    private String mPassword;

    private int mUserId;

    GoogleSignInClient mGoogleSignInClient;

    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSharedPrefs = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);

        automaticLogin();
        wireUpDisplay();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        /*
        AppDatabase db = AppDatabase.getInstance(LoginActivity.this);
        userDao = db.userDao();

        userDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
                .userDao();

        mUsers = userDao.getAllUsers();
         */

        mViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mViewModel.init();
        
        mViewModel.getUserLiveData().observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo user) {
                mUserId = user.getUserId();
            }
        });
        
        mViewModel.getUserListLiveData().observe(this, new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> users) {
                mUsers = users;
            }
        });

        mViewModel.getAllUsers();

        /**
         * Attempts to log in the user.
         */
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fieldsField()) {
                    if (validCredentials()) {
                        login();
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

                switch (v.getId()) {
                    case R.id.google_sign_in_button:
                        googleSignIn();
                        break;
                }
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
        if (mSharedPrefs.getInt(Constants.USER_ID_KEY, -1) != -1) {
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
            if (user.getUsername().equals(mUsername) && user.getPassword().equals(mPassword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Logs in the user and saves them to sharedPreferences.
     */
    private void login() {
        /*
        if (mUserText.getText().toString().equals(adminUsername) && mPasswordText.getText().toString().equals(adminPassword)) {
            Toast.makeText(LoginActivity.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra(EXTRA_IS_ADMIN, true);
            intent.putExtra(EXTRA_USER_ID, -1);
            startActivity(intent);
            return;
        }
        for (UserInfo u : mUsers) {
            if (mUserText.getText().toString().equals(u.getUsername()) && mPasswordText.getText().toString().equals(u.getPassword())) {
                Toast.makeText(LoginActivity.this, "Welcome back " + u.getUsername(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(EXTRA_USER_ID, u.getUserId());
                startActivity(intent);
                return;
            }
            else {
                Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        }
         */
        
        mViewModel.getUserByUsername(mUsername);
        
        SharedPreferences.Editor editor = mSharedPrefs.edit();
        editor.putInt(Constants.USER_ID_KEY, mUserId);
        editor.apply();
        
        Toast.makeText(LoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // TODO: Finish implementing Google sign in

    /**
     * used to check if user already signed in
     *
     @Override
     protected void onStart() {
     super.onStart();

     GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

     updateUI(account);
     }
     */

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            // updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCode class reference for more information.
            Log.v("Error", "signInResult:failed code=" + e.getStatusCode());
            // updateUI(null);
        }
    }
}