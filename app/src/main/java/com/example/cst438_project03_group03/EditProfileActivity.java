package com.example.cst438_project03_group03;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cst438_project03_group03.util.Constants;

/**
 * Class: EditProfileActivity.java
 * Description: Allows the user to edit their profile information.
 */
public class EditProfileActivity extends AppCompatActivity {

    private SharedPreferences mSharedPrefs;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mSharedPrefs = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.exit_item:
                intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}