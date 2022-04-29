package com.example.cst438_project03_group03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cst438_project03_group03.R;
import com.example.cst438_project03_group03.database.Post;
import com.example.cst438_project03_group03.adapters.PostResultsAdapter;
import com.example.cst438_project03_group03.models.ImageInfo;
import com.example.cst438_project03_group03.models.PostInfo;
import com.example.cst438_project03_group03.util.Constants;
import com.example.cst438_project03_group03.viewmodels.ImageViewModel;
import com.example.cst438_project03_group03.viewmodels.PostViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int mUserId;

    private Button logoutButton;
    private Button makePostButton;
    private Button viewPostsButton;

    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApplicationInfo ai = null;
        try {
            ai = getApplicationContext().getPackageManager().getApplicationInfo(getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        assert ai != null;
        String key = ai.metaData.getString("com.google.android.geo.CLIENT_ID");

        Toast.makeText(getApplicationContext(), key + "", Toast.LENGTH_SHORT).show();

        mSharedPrefs = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);
        mUserId = mSharedPrefs.getInt(Constants.USER_ID_KEY, -1);

        logoutButton = findViewById(R.id.logOutButton);
        makePostButton = findViewById(R.id.makePostButton);
        viewPostsButton = findViewById(R.id.viewPostsButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSharedPrefs.getInt(Constants.USER_ID_KEY, -1) != -1) {
                    SharedPreferences.Editor editor = mSharedPrefs.edit();
                    editor.putInt(Constants.USER_ID_KEY, -1);
                    editor.apply();
                }
                Toast.makeText(getApplicationContext(), "Logout Successful.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        makePostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                startActivity(intent);
            }
        });
        viewPostsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LiveFeedActivity.class);
                startActivity(intent);
            }
        });
    }
}