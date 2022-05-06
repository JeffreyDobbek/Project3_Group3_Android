package com.example.cst438_project03_group03;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cst438_project03_group03.models.UserInfo;
import com.example.cst438_project03_group03.util.Constants;
import com.example.cst438_project03_group03.viewmodels.UserViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cst438_project03_group03.adapters.SectionsPagerAdapter;
import com.example.cst438_project03_group03.databinding.ActivityProfileBinding;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Class: ProfileActivity.java
 * Description: Shows the user their posts and the posts they've interacted them.
 *              Holds logout and edit profile buttons.
 */
public class ProfileActivity extends AppCompatActivity {

private ActivityProfileBinding binding;

    private SharedPreferences mSharedPrefs;

    private UserViewModel mUserViewModel;
    private UserInfo mUserInfo;

    private TextView mNameTv;
    private TextView mUsernameTv;

    private CircleImageView mProfilePicCv;

    private GoogleSignInClient mGoogleSignInClient;

    private int mUserId;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        mSharedPrefs = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);
        mUserId = mSharedPrefs.getInt(Constants.USER_ID_KEY, -1);

        wireUpDisplay();
        setOnClickListeners();
        setUserViewModel();

        mUserViewModel.getUserByUserId(mUserId);
    }

    /**
     * Binds the UI elements.
     */
    private void wireUpDisplay() {
        mNameTv = binding.profileNameTv;
        mUsernameTv = binding.profileUsernameTv;
        mProfilePicCv = binding.profileProfilePicCv;
    }

    private void setOnClickListeners() {
        /**
         * Closes the profile activity.
         */
        binding.profileExitIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * Logs out the user.
         */
        binding.logoutIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSharedPrefs.getInt(Constants.USER_ID_KEY, -1) != -1) {
                    SharedPreferences.Editor editor = mSharedPrefs.edit();
                    editor.putInt(Constants.USER_ID_KEY, -1);
                    editor.apply();
                }

                // sign out of google
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(ProfileActivity.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(), "Logout Successful.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                        });
            }
        });

        /**
         * Takes users to screen where they can update their profile information.
         */
        binding.editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Initializes user view model and sets up live data observers.
     */
    private void setUserViewModel() {
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mUserViewModel.init();

        mUserViewModel.getUserLiveData().observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                if (userInfo != null) {
                    setUserInfo(userInfo.getName(), userInfo.getUsername(), userInfo.getImage());
                } else {
                    Toast.makeText(getApplicationContext(), mUserId + "", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Sets the profile UI with the user's information.
     * @param name The user's name.
     * @param username The user's username.
     * @param image The user's profile picture.
     */
    private void setUserInfo(String name, String username, String image) {
        mNameTv.setText(name);
        mUsernameTv.setText(username);

        if (image != null) {
            Glide.with(binding.viewPager.getRootView())
                    .load(image)
                    .into(binding.profileProfilePicCv);
        }
    }
}