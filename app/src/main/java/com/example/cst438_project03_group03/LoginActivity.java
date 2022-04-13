package com.example.cst438_project03_group03;


import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cst438_project03_group03.database.AppDatabase;
import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.database.UserDao;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_IS_ADMIN = "com.example.cst438_project03_group03.EXTRA_IS_ADMIN";
    public static final String EXTRA_USER_ID = "com.example.cst438_project03_group03.EXTRA_USER_ID";

    private Button mLoginButton, mCreateAccountButton;
    private EditText mUserText, mPasswordText;

    private UserDao userDao;
    private List<User> users;

    String adminUsername = "admin";
    String adminPassword = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserText = findViewById(R.id.enterUsername);
        mPasswordText = findViewById(R.id.enterPassword);

        mLoginButton = findViewById(R.id.buttonLogin);
        mCreateAccountButton = findViewById(R.id.createAccountButton);

        userDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
                .getDao();

        users = userDao.getAllUsers();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
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
}