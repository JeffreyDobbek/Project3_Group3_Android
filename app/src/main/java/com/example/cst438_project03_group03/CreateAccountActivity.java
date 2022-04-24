package com.example.cst438_project03_group03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cst438_project03_group03.fragments.FirstCreateAccountFragment;
import com.example.cst438_project03_group03.fragments.SecondCreateAccountFragment;

/**
 * Class: CreateAccountActivity.java
 * Description: Allows users to create an account
 */
public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        FirstCreateAccountFragment firstCreateAccountFragment = FirstCreateAccountFragment.newInstance();
        SecondCreateAccountFragment secondCreateAccountFragment = SecondCreateAccountFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.create_account_fragment_fl, firstCreateAccountFragment)
                .commit();
    }
}