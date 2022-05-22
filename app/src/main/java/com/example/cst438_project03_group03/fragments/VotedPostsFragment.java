package com.example.cst438_project03_group03.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cst438_project03_group03.R;
import com.example.cst438_project03_group03.adapters.PostResultsAdapter;
import com.example.cst438_project03_group03.database.Image;
import com.example.cst438_project03_group03.databinding.FragmentVotedPostsBinding;
import com.example.cst438_project03_group03.models.ImageInfo;
import com.example.cst438_project03_group03.models.PostInfo;
import com.example.cst438_project03_group03.models.UserInfo;
import com.example.cst438_project03_group03.util.Constants;
import com.example.cst438_project03_group03.viewmodels.ImageViewModel;
import com.example.cst438_project03_group03.viewmodels.PostViewModel;
import com.example.cst438_project03_group03.viewmodels.UserViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class: VotedPostsFragment.java
 * Description: Holds feed of posts that the user has voted on.
 */
public class VotedPostsFragment extends Fragment {

    private FragmentVotedPostsBinding mBinding;

    private SharedPreferences mSharedPrefs;

    private PostViewModel mPostViewModel;
    private UserViewModel mUserViewModel;
    private ImageViewModel mImageViewModel;

    private PostResultsAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private int mUserId;

    private List<PostInfo> mPosts = new ArrayList<>();
    private HashMap<Integer, UserInfo> mUserMap = new HashMap<>();
    private HashMap<Integer, List<ImageInfo>> mImageMap = new HashMap<>();

    public static VotedPostsFragment newInstance() {
        return new VotedPostsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentVotedPostsBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the user's id from shared preferences
        mSharedPrefs = getActivity().getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        mUserId = mSharedPrefs.getInt(Constants.USER_ID_KEY, -1);

        mSwipeRefreshLayout = mBinding.votedRefresh;
        mAdapter = new PostResultsAdapter(getContext());

        setPostViewModel();
        setUserViewModel();
        setImageViewModel();

        // Initializing the recycler view
        RecyclerView recyclerView = mBinding.votedRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setRefreshing(true);
        mPostViewModel.getLikedPosts(mUserId);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPostViewModel.getLikedPosts(mUserId);
            }
        });
    }

    /**
     * Initializes the post view model and sets up live data observers.
     */
    private void setPostViewModel() {
        mPostViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        mPostViewModel.init();

        // Listens for request for all live posts in the database.
        mPostViewModel.getPostListLiveData().observe(getViewLifecycleOwner(), new Observer<List<PostInfo>>() {
            @Override
            public void onChanged(List<PostInfo> posts) {
                if (posts != null) {
                    if (mPosts.size() != posts.size()) {
                        mPosts = posts;
                    }
                    mAdapter.setPosts(mPosts);
                    mUserViewModel.getAllUsers();
                }
            }
        });
    }

    /**
     * Initializes the user view model and sets up live data observers.
     */
    private void setUserViewModel() {
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mUserViewModel.init(getActivity().getApplication());

        // Listens for request for all users in the database.
        mUserViewModel.getUserListLiveData().observe(getViewLifecycleOwner(), new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> users) {
                if (users != null) {
                    for (UserInfo user : users) {
                        mUserMap.put(user.getUserId(), user);
                    }
                    mAdapter.setUsers(mUserMap);
                    mImageViewModel.getAllPostPics();
                }
            }
        });
    }

    /**
     * Initializes the image view model and sets up live data observers.
     */
    private void setImageViewModel() {
        mImageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);
        mImageViewModel.init();

        // Listens for request for all images in the database.
        mImageViewModel.getPostImagesLiveData().observe(getViewLifecycleOwner(), new Observer<List<ImageInfo>>() {
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
    }

    @Override
    public void onResume() {
        super.onResume();
        mPostViewModel.getLikedPosts(mUserId);
    }
}