package com.example.cst438_project03_group03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cst438_project03_group03.databinding.ActivityImageBinding;

public class ImageActivity extends AppCompatActivity {

    private ActivityImageBinding mBinding;

    private TextView mNumLikesTv;

    private ImageView mImageIv;
    private ImageView mCloseIv;
    private ImageView mLikeIv;

    private String mImage;
    private int mNumLikes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        getSupportActionBar().hide();

        mBinding = ActivityImageBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mImage = getIntent().getStringExtra("image");
        mNumLikes = getIntent().getIntExtra("numLikes", -1);

        wireUpDisplay();
        setOnClickListeners();
        setImage(mImage);
    }

    private void wireUpDisplay() {
        mImageIv = mBinding.imageIv;
        mCloseIv = mBinding.imageCloseIv;
        mLikeIv = mBinding.imageLikeIv;
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

    private void setImage(String image) {
        if (image != null) {
            Glide.with(mBinding.getRoot().getRootView())
                    .load(image)
                    .into(mBinding.imageIv);
        }
    }
}