package com.example.cst438_project03_group03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Class: CreateAccountActivity.java
 * Description: Allows users to create an account
 */
public class CreateAccountActivity extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mUsernameField;
    private EditText mNameField;
    private EditText mPasswordField;
    private EditText mConfirmPasswordField;

    private String mEmail;
    private String mUsername;
    private String mName;
    private String mPassword;
    private String mConfirmPassword;

    private Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        wireUpDisplay();

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getFieldValues()) {
                    if (checkFields()) {
                        // add user to database
                        Toast.makeText(CreateAccountActivity.this, "Account successfully created.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(CreateAccountActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void wireUpDisplay() {
        mEmailField = findViewById(R.id.create_account_email_edit_text);
        mUsernameField = findViewById(R.id.create_account_username_edit_text);
        mNameField = findViewById(R.id.create_account_name_edit_text);
        mPasswordField = findViewById(R.id.create_account_password_edit_text);
        mConfirmPasswordField = findViewById(R.id.create_account_confirm_password_edit_text);

        mRegisterButton = findViewById(R.id.create_account_button);
    }

    private boolean getFieldValues() {
        mEmail = mEmailField.getText().toString();
        mUsername = mUsernameField.getText().toString();
        mName = mNameField.getText().toString();
        mPassword = mPasswordField.getText().toString();
        mConfirmPassword = mConfirmPasswordField.getText().toString();

        return mEmail != null && mUsername != null && mName != null && mPassword != null;
    }

    private boolean checkFields() {
        // check if email is already in database
        if (emailInDatabase()) {
            Toast.makeText(CreateAccountActivity.this, "This email is already registered with an existing account", Toast.LENGTH_SHORT).show();
            return false;
        }
        // check that email has an @
        if (!mEmail.contains("@") && !mEmail.contains(".com")) {
            Toast.makeText(CreateAccountActivity.this, "Not a valid email.", Toast.LENGTH_SHORT).show();
            return false;
        }
        // check if username is already in database
        if (usernameTaken()) {
            Toast.makeText(CreateAccountActivity.this, "This username is taken.", Toast.LENGTH_SHORT).show();
            return false;
        }
        // check that both passwords match
        if (!mPassword.equals(mConfirmPassword)) {
            Toast.makeText(CreateAccountActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // check if email is in database
    private boolean emailInDatabase() {
        return true;
    }

    // check if username is in database
    private boolean usernameTaken() {
        return true;
    }
}