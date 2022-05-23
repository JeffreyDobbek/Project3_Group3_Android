package com.example.cst438_project03_group03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cst438_project03_group03.adapters.CommentsAdapter;
import com.example.cst438_project03_group03.databinding.ActivityChangeEmailBinding;
import com.example.cst438_project03_group03.databinding.ActivityViewCommentsBinding;
import com.example.cst438_project03_group03.models.CommentInfo;
import com.example.cst438_project03_group03.models.UploadCommentResponse;
import com.example.cst438_project03_group03.models.UserInfo;
import com.example.cst438_project03_group03.util.Constants;
import com.example.cst438_project03_group03.viewmodels.CommentViewModel;
import com.example.cst438_project03_group03.viewmodels.UserViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Class: ViewCommentsActivity.java
 * Description: Holds a post's comments and ability to post a comment.
 */
public class ViewCommentsActivity extends AppCompatActivity {

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

    private int mPostId;
    private int mUserId;

    private String mComment;

    private CommentInfo mCommentInfo;

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
        setCommentViewModel();
        setUserViewModel();

        mSharedPrefs = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);
        String profilePic = mSharedPrefs.getString(Constants.USER_PROFILE_PIC_KEY, null);
        setProfilePic(profilePic);

        mSwipeRefreshLayout = findViewById(R.id.comments_refresh);
        mAdapter = new CommentsAdapter(this);


        mPostId = getIntent().getIntExtra("postId", -1);
        mUserId = mSharedPrefs.getInt(Constants.USER_ID_KEY, -1);

        RecyclerView recyclerView = findViewById(R.id.comments_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setRefreshing(true);
        mCommentViewModel.getPostComments(mPostId);

        /**
         * Swipe to refresh comment Recycler View.
         */
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCommentViewModel.getPostComments(mPostId);
            }
        });
    }

    /**
     * Wires up display elements.
     */
    private void wireUpDisplay() {
        mCloseIv = mBinding.commentsCloseIv;
        mUploadIv = mBinding.commentsUploadIv;
        mProfilePicCiv = mBinding.commentMyProfilePicCiv;
        mCommentEt = mBinding.commentEt;
    }

    /**
     * Sets button listeners.
     */
    private void setOnClickListeners() {
        /**
         * Closes the current activity.
         */
        mCloseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * Attempts to upload a comment.
         */
        mUploadIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mComment = mCommentEt.getText().toString();
                /**
                 * Comment is valid if it is not empty and is less than or equal to 255 characters.
                 */
                if (!mComment.isEmpty()) {
                    if (mComment.length() <= 255) {
                        mCommentInfo = new CommentInfo();

                        mCommentInfo.setPostId(mPostId);
                        mCommentInfo.setUserId(mUserId);
                        mCommentInfo.setComment(mComment);

                        /**
                         * Retrieve comments again.
                         */
                        mSwipeRefreshLayout.setRefreshing(true);
                        mCommentViewModel.uploadComment(mCommentInfo);
                        mCommentEt.setText("");
                    } else {
                        Toast.makeText(getApplicationContext(), "Comment can't be more than 255 characters.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter a comment.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Initializes the comment view model and sets up live data observers.
     */
    private void setCommentViewModel() {
        mCommentViewModel = new ViewModelProvider(this).get(CommentViewModel.class);
        mCommentViewModel.init();

        /**
         * Listens for request for all comments of selected post.
         */
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

        /**
         * Listens for upload comment response.
         */
        mCommentViewModel.getUploadCommentResponseLiveData().observe(this, new Observer<UploadCommentResponse>() {
            @Override
            public void onChanged(UploadCommentResponse response) {
                if (response != null) {
                    Log.i("comment uploaded", response.getCommentId() + "");
                    mCommentViewModel.getPostComments(mPostId);
                } else {
                    Snackbar.make(mBinding.view, "An error occurred", Snackbar.LENGTH_SHORT).show();
                    Log.i("ERROR", "comment failed to upload");
                }
            }
        });
    }

    /**
     * Initializes the user view model and sets up live data observers.
     */
    private void setUserViewModel() {
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mUserViewModel.init(getApplication());

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

    /**
     * Sets profile picture of the logged in user.
     * @param profilePic Image URL of the user's profile picture.
     */
    private void setProfilePic(String profilePic) {
        if (profilePic != null) {
            Glide.with(mBinding.getRoot().getRootView())
                    .load(profilePic)
                    .into(mBinding.commentMyProfilePicCiv);
        }
    }
}