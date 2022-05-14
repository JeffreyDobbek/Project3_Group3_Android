package com.example.cst438_project03_group03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cst438_project03_group03.adapters.PostResultsAdapter;
import com.example.cst438_project03_group03.database.User;
import com.example.cst438_project03_group03.models.ImageInfo;
import com.example.cst438_project03_group03.models.PostInfo;
import com.example.cst438_project03_group03.models.UserInfo;
import com.example.cst438_project03_group03.viewmodels.ImageViewModel;
import com.example.cst438_project03_group03.viewmodels.PostViewModel;
import com.example.cst438_project03_group03.viewmodels.UserViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LiveFeedActivity extends AppCompatActivity {

    private UserViewModel mUserViewModel;
    private PostViewModel mPostViewModel;
    private ImageViewModel mImageViewModel;

    private PostResultsAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private HashMap<Integer, UserInfo> mUserMap = new HashMap<>();
    private HashMap<Integer, List<ImageInfo>> mImageMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_feed);

        mSwipeRefreshLayout = findViewById(R.id.live_posts_refresh);
        mAdapter = new PostResultsAdapter(this);

        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mUserViewModel.init();

        mPostViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        mPostViewModel.init();

        mImageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);
        mImageViewModel.init();

        /**
         * Listens for request for all live posts in the database.
         */
        mPostViewModel.getAllLivePostsLiveData().observe(this, new Observer<List<PostInfo>>() {
            @Override
            public void onChanged(List<PostInfo> posts) {
                if (posts != null) {
                    mAdapter.setPosts(posts);
                    mUserViewModel.getAllUsers();
                } else {
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * Listens for request for all users in the database.
         */
        mUserViewModel.getUserListLiveData().observe(this, new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> users) {
                mUserMap.clear();
                if (users != null) {
                    for (UserInfo user : users) {
                        mUserMap.put(user.getUserId(), user);
                    }
                    mAdapter.setUsers(mUserMap);
                    mImageViewModel.getAllPostPics();
                }
            }
        });

        /**
         * Listens for request for all images in the database.
         */
        mImageViewModel.getPostImagesLiveData().observe(this, new Observer<List<ImageInfo>>() {
            @Override
            public void onChanged(List<ImageInfo> images) {
                if (images != null) {
                    mImageMap.clear();
                    for (ImageInfo image : images) {
                        if (!mImageMap.containsKey(image.getPostId())) {
                            mImageMap.put(image.getPostId(), new ArrayList<>());
                        }
                        mImageMap.get(image.getPostId()).add(image);
                    }
                    mAdapter.setImages(mImageMap);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        /**
         * Setting up post recycler view
         */
        RecyclerView recyclerView = findViewById(R.id.live_feed_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setRefreshing(true);
        mPostViewModel.getAllLivePosts();

        /**
         * Swipe down to refresh the live post feed.
         */
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPostViewModel.getAllLivePosts();
            }
        });
    }
}