package com.example.cst438_project03_group03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cst438_project03_group03.databinding.ActivityImageBinding;
import com.example.cst438_project03_group03.models.IsPicLikedResponse;
import com.example.cst438_project03_group03.models.LikePicResponse;
import com.example.cst438_project03_group03.util.Constants;
import com.example.cst438_project03_group03.viewmodels.ImageViewModel;

/**
 * Class: ImageActivity.java
 * Description: Better view of a post's image, let's user's like or unlike an image.
 */
public class ImageActivity extends AppCompatActivity {

    private ActivityImageBinding mBinding;

    private ImageViewModel mImageViewModel;

    private SharedPreferences mSharedPrefs;

    private TextView mNumLikesTv;

    private ImageView mImageIv;
    private ImageView mCloseIv;
    private ImageView mLikeIv;

    private String mImage;
    private String mResult;

    private int mUserId;
    private int mImageId;
    private int mNumLikes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        getSupportActionBar().hide();

        mSharedPrefs = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);
        mUserId = mSharedPrefs.getInt(Constants.USER_ID_KEY, -1);

        mBinding = ActivityImageBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mImageId = getIntent().getIntExtra("imageId", -1);
        mImage = getIntent().getStringExtra("image");
        mNumLikes = getIntent().getIntExtra("numLikes", -1);

        wireUpDisplay();
        setOnClickListeners();
        setImage(mImage);

        setImageViewModel();

        mImageViewModel.isPicLiked(mUserId, mImageId);
    }

    /**
     * Binds the UI elements.
     */
    private void wireUpDisplay() {
        mImageIv = mBinding.imageIv;
        mCloseIv = mBinding.imageCloseIv;
        mLikeIv = mBinding.imageLikeIv;
        mNumLikesTv = mBinding.imageNumLikesTv;

        mLikeIv.setClickable(false);

        mNumLikesTv.setText(mNumLikes + "");
    }

    /**
     * Sets up the button on click listeners.
     */
    private void setOnClickListeners() {
        mCloseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLikeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLikeIv.getTag().equals("not liked")) {
                    mImageViewModel.likePic(mUserId, mImageId);
                } else if (mLikeIv.getTag().equals("liked")) {
                    mImageViewModel.unlikePic(mUserId, mImageId);
                }
                updateLikedStatus();
            }
        });
    }

    /**
     * Initializes image view model and sets up live data observers.
     */
    private void setImageViewModel() {
        mImageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);
        mImageViewModel.init();

        /**
         * Checks if the image has already been liked by the user.
         */
        mImageViewModel.getIsPicLikedResponseLiveData().observe(this, new Observer<IsPicLikedResponse>() {
            @Override
            public void onChanged(IsPicLikedResponse response) {
                if (response != null) {
                    mResult = response.getResult();
                    setLikedStatus(mResult);
                    mLikeIv.setClickable(true);
                }
            }
        });
    }

    /**
     * Binds the image to the UI.
     * @param image The image url.
     */
    private void setImage(String image) {
        if (image != null) {
            Glide.with(mBinding.getRoot().getRootView())
                    .load(image)
                    .into(mBinding.imageIv);
        }
    }

    /**
     * Set initial like status icon.
     * @param result The result of the API request.
     */
    private void setLikedStatus(String result) {
        if (result.equals("L")) {
            mLikeIv.setImageResource(R.drawable.ic_like);
            mLikeIv.setTag("liked");
        } else if (result.equals("NL")) {
            mLikeIv.setImageResource(R.drawable.ic_like_border);
            mLikeIv.setTag("not liked");
        }
    }

    /**
     * Update the like status icon when clicked.
     */
    private void updateLikedStatus() {
        if (mLikeIv.getTag().equals("not liked")) {
            mLikeIv.setImageResource(R.drawable.ic_like);
            mLikeIv.setTag("liked");
            mNumLikesTv.setText((mNumLikes + 1) + "");
        } else if (mLikeIv.getTag().equals("liked")) {
            mLikeIv.setImageResource(R.drawable.ic_like_border);
            mLikeIv.setTag("not liked");
            mNumLikesTv.setText((mNumLikes - 1) + "");
        }
    }
}