package com.example.cst438_project03_group03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cst438_project03_group03.adapters.CommentsAdapter;
import com.example.cst438_project03_group03.databinding.ActivityChangeEmailBinding;
import com.example.cst438_project03_group03.databinding.ActivityViewCommentsBinding;
import com.example.cst438_project03_group03.models.CommentInfo;
import com.example.cst438_project03_group03.models.UserInfo;
import com.example.cst438_project03_group03.util.Constants;
import com.example.cst438_project03_group03.viewmodels.CommentViewModel;
import com.example.cst438_project03_group03.viewmodels.UserViewModel;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Class: ViewCommentsActivity.java
 * Description:
 */
public class ViewCommentsActivity extends AppCompatActivity {

    private int mPostId;

    private ActivityViewCommentsBinding mBinding;

    private SharedPreferences mSharedPrefs;

    private ImageView mCloseIv;
    private ImageView mUploadIv;

    private CircleImageView mProfilePicCiv;

    private EditText mCommentEt;

    private UserViewModel mUserViewModel;
    private CommentViewModel mCommentViewModel;

    private CommentsAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private HashMap<Integer, UserInfo> mUserMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments);

        getSupportActionBar().hide();

        mBinding = ActivityViewCommentsBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        wireUpDisplay();
        setOnClickListeners();

        mSharedPrefs = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);
        String profilePic = mSharedPrefs.getString(Constants.USER_PROFILE_PIC_KEY, null);
        setProfilePic(profilePic);

        mSwipeRefreshLayout = findViewById(R.id.comments_refresh);
        mAdapter = new CommentsAdapter(this);

        setCommentViewModel();
        setUserViewModel();

        mPostId = getIntent().getIntExtra("postId", -1);

        mCommentViewModel.getPostComments(mPostId);

        RecyclerView recyclerView = findViewById(R.id.comments_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCommentViewModel.getPostComments(mPostId);
            }
        });
    }

    private void wireUpDisplay() {
        mCloseIv = mBinding.commentsCloseIv;
        mUploadIv = mBinding.commentsUploadIv;
        mProfilePicCiv = mBinding.commentMyProfilePicCiv;
        mCommentEt = mBinding.commentEt;
    }

    private void setOnClickListeners() {
        mCloseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mUploadIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setCommentViewModel() {
        mCommentViewModel = new ViewModelProvider(this).get(CommentViewModel.class);
        mCommentViewModel.init();

        mCommentViewModel.getCommentListLiveData().observe(this, new Observer<List<CommentInfo>>() {
            @Override
            public void onChanged(List<CommentInfo> comments) {
                if (comments != null) {
                    mAdapter.setComments(comments);
                    mUserViewModel.getAllUsers();
                } else {
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUserViewModel() {
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mUserViewModel.init();

        mUserViewModel.getUserListLiveData().observe(this, new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> users) {
                mUserMap.clear();
                if (users != null) {
                    for (UserInfo user : users) {
                        mUserMap.put(user.getUserId(), user);
                    }
                    mAdapter.setUsers(mUserMap);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void setProfilePic(String profilePic) {
        if (profilePic != null) {
            Glide.with(mBinding.getRoot().getRootView())
                    .load(profilePic)
                    .into(mBinding.commentMyProfilePicCiv);
        }
    }
}