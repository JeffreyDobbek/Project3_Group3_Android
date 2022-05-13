package com.example.cst438_project03_group03;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cst438_project03_group03.databinding.FragmentSecondCreateAccountBinding;
import com.example.cst438_project03_group03.fragments.SecondCreateAccountFragment;
import com.example.cst438_project03_group03.models.CreateAccountResult;
import com.example.cst438_project03_group03.models.ImgurResponse;
import com.example.cst438_project03_group03.models.ImgurUpload;
import com.example.cst438_project03_group03.models.UserInfo;
import com.example.cst438_project03_group03.viewmodels.ImageViewModel;
import com.example.cst438_project03_group03.viewmodels.UserViewModel;

import java.util.List;


public class PostActivity extends AppCompatActivity {

    private String mEmail;
    private String mPassword;
    private String mConfirmPassword;
    private String mUsername;
    private String mName;
    private String mPic;

    private UserInfo mUser;
    private List<UserInfo> mUsers;

    private UserViewModel mUserViewModel;
    private ImageViewModel mImageViewModel;
    private ImgurUpload mImgurUpload = new ImgurUpload();


    public static PostActivity newInstance() {
        return new PostActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Button upload = findViewById(R.id.uploadPhotoButton);


        mImageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);
        mImageViewModel.init();



        mImageViewModel.getImgurResponseLiveData().observe(this, new Observer<ImgurResponse>() {
            @Override
            public void onChanged(ImgurResponse imgurResponse) {
                if (imgurResponse != null) {
                    mPic = imgurResponse.getData().getLink();

                } else {
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            ImageView imageView = findViewById(R.id.postImageView);
            imageView.setImageURI(selectedImage);
        }
    }
}