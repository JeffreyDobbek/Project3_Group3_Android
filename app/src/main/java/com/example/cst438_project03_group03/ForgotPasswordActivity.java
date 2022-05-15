package com.example.cst438_project03_group03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.example.cst438_project03_group03.fragments.FirstForgotPasswordFragment;
import com.example.cst438_project03_group03.fragments.SecondForgotPasswordFragment;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().hide();

        FirstForgotPasswordFragment firstForgotPasswordFragment = FirstForgotPasswordFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.forgot_password_fragment_fl, firstForgotPasswordFragment)
                .commit();
    }
}