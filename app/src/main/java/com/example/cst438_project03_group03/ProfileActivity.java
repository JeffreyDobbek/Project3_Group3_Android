package com.example.cst438_project03_group03;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cst438_project03_group03.util.Constants;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cst438_project03_group03.adapters.SectionsPagerAdapter;
import com.example.cst438_project03_group03.databinding.ActivityProfileBinding;

/**
 * Class: ProfileActivity.java
 * Description: Shows the user their posts and the posts they've interacted them.
 *              Holds logout and edit profile buttons.
 */
public class ProfileActivity extends AppCompatActivity {

private ActivityProfileBinding binding;

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

     binding = ActivityProfileBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

    }

    /**
     * Holds logout menu item.
     * @param item The selected menu itme.
     * @return a boolean.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.logout_item:
                if (mSharedPrefs.getInt(Constants.USER_ID_KEY, -1) != -1) {
                    SharedPreferences.Editor editor = mSharedPrefs.edit();
                    editor.putInt(Constants.USER_ID_KEY, -1);
                    editor.apply();
                }

                Toast.makeText(getApplicationContext(), "Logout Successful.", Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}