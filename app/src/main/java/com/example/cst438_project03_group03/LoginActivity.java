package com.example.cst438_project03_group03;


import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cst438_project03_group03.database.AppDatabase;
import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.database.UserDao;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;

import java.util.List;

import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_IS_ADMIN = "com.example.cst438_project03_group03.EXTRA_IS_ADMIN";
    public static final String EXTRA_USER_ID = "com.example.cst438_project03_group03.EXTRA_USER_ID";

    private Button mLoginButton, mCreateAccountButton;
    private SignInButton mGoogleSignInButton;
    private EditText mUserText, mPasswordText;

    private UserDao userDao;
    private List<User> users;

    private static final int RC_SIGN_IN = 0;
    private static final String TAG = "google sign in";

    String adminUsername = "admin";
    String adminPassword = "admin";

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserText = findViewById(R.id.enterUsername);
        mPasswordText = findViewById(R.id.enterPassword);

        mLoginButton = findViewById(R.id.buttonLogin);
        mGoogleSignInButton = findViewById(R.id.google_sign_in_button);
        mCreateAccountButton = findViewById(R.id.createAccountButton);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        AppDatabase db = AppDatabase.getInstance(LoginActivity.this);
        userDao = db.userDao();
        /*
        userDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
                .userDao();
        */
        users = userDao.getAllUsers();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

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

        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    //TODO: implement later
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

    private void login() {
        for (User u : users) {
            if (mUserText.getText().toString().equals(adminUsername) && mPasswordText.getText().toString().equals(adminPassword)) {
                Toast.makeText(LoginActivity.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(EXTRA_IS_ADMIN, true);
                intent.putExtra(EXTRA_USER_ID, -1);
                startActivity(intent);
                return;
            }
            else if (mUserText.getText().toString().equals(u.getUsername()) && mPasswordText.getText().toString().equals(u.getPassword())) {
                Toast.makeText(LoginActivity.this, "Welcome back " + u.getUsername(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra(EXTRA_USER_ID, u.getUserId());
                startActivity(intent);
                return;
            }
        }
        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
    }

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