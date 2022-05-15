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
import com.example.cst438_project03_group03.databinding.FragmentMyPostsBinding;
import com.example.cst438_project03_group03.models.ImageInfo;
import com.example.cst438_project03_group03.models.PostInfo;
import com.example.cst438_project03_group03.util.Constants;
import com.example.cst438_project03_group03.viewmodels.ImageViewModel;
import com.example.cst438_project03_group03.viewmodels.PostViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyPostsFragment extends Fragment {

    private FragmentMyPostsBinding mBinding;

    private SharedPreferences mSharedPrefs;

    private PostViewModel mPostViewModel;
    private ImageViewModel mImageViewModel;

    private PostResultsAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private int mUserId;

    private String mUsername;
    private String mProfilePic;

    private HashMap<Integer, List<ImageInfo>> mImageMap = new HashMap<>();

    public static MyPostsFragment newInstance() {
        return new MyPostsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentMyPostsBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**
         * Retrieve values from shared preferences.
         */
        mSharedPrefs = getActivity().getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        mUserId = mSharedPrefs.getInt(Constants.USER_ID_KEY, -1);
        mUsername = mSharedPrefs.getString(Constants.USER_USERNAME_KEY, null);
        mProfilePic = mSharedPrefs.getString(Constants.USER_PROFILE_PIC_KEY, null);

        mSwipeRefreshLayout = mBinding.myPostsRefresh;
        mAdapter = new PostResultsAdapter(getContext());

        setPostViewModel();
        setImageViewModel();

        RecyclerView recyclerView = mBinding.myPostsRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setRefreshing(true);
        mPostViewModel.getUserPosts(mUserId);

        /**
         * Swipe down to refresh the user's post feed.
         */
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPostViewModel.getUserPosts(mUserId);
            }
        });
    }

    /**
     * Initializes the post view model and sets up live data observers.
     */
    private void setPostViewModel() {
        mPostViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        mPostViewModel.init();

        /**
         * Listens for request for all of a user's posts.
         */
        mPostViewModel.getPostListLiveData().observe(getViewLifecycleOwner(), new Observer<List<PostInfo>>() {
            @Override
            public void onChanged(List<PostInfo> posts) {
                if (posts != null) {
                    for (PostInfo post : posts) {
                        post.setUsername(mUsername);
                        post.setProfilePic(mProfilePic);
                    }
                    mAdapter.setPosts(posts);
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

        /**
         * Listens for request for all images in the database.
         */
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
                } else {
                    mImageViewModel.getAllPostPics();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPostViewModel.getUserPosts(mUserId);
    }
}