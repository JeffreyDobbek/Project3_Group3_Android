package com.example.cst438_project03_group03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cst438_project03_group03.databinding.ActivityImageBinding;
import com.example.cst438_project03_group03.models.IsPicLikedResponse;
import com.example.cst438_project03_group03.util.Constants;
import com.example.cst438_project03_group03.viewmodels.ImageViewModel;

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

    private void wireUpDisplay() {
        mImageIv = mBinding.imageIv;
        mCloseIv = mBinding.imageCloseIv;
        mLikeIv = mBinding.imageLikeIv;
        mLikeIv.setTag("not Liked");
        mNumLikesTv = mBinding.imageNumLikesTv;

        mNumLikesTv.setText(mNumLikes + "");
    }

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

            }
        });
    }

    private void setImageViewModel() {
        mImageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);
        mImageViewModel.init();

        mImageViewModel.getIsPicLikedResponseLiveData().observe(this, new Observer<IsPicLikedResponse>() {
            @Override
            public void onChanged(IsPicLikedResponse response) {
                if (response != null) {
                    mResult = response.getResult();
                    Toast.makeText(getApplicationContext(), mResult, Toast.LENGTH_SHORT).show();
                    setLikedStatus(mResult);
                }
            }
        });
    }

    private void setImage(String image) {
        if (image != null) {
            Glide.with(mBinding.getRoot().getRootView())
                    .load(image)
                    .into(mBinding.imageIv);
        }
    }

    private void setLikedStatus(String result) {
        if (result.equals("L")) {
            mLikeIv.setImageResource(R.drawable.ic_like);
            mLikeIv.setTag("liked");
        } else if (result.equals("NL")) {
            mLikeIv.setImageResource(R.drawable.ic_like_border);
            mLikeIv.setTag("not Liked");
        }
    }

    private void updateLikedStatus() {

    }
}