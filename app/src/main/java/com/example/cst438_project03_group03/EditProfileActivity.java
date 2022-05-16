package com.example.cst438_project03_group03;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cst438_project03_group03.databinding.ActivityEditProfileBinding;
import com.example.cst438_project03_group03.models.ImgurResponse;
import com.example.cst438_project03_group03.models.ImgurUpload;
import com.example.cst438_project03_group03.models.UserInfo;
import com.example.cst438_project03_group03.util.Constants;
import com.example.cst438_project03_group03.viewmodels.ImageViewModel;
import com.example.cst438_project03_group03.viewmodels.UserViewModel;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Class: EditProfileActivity.java
 * Description: Allows the user to edit their profile information.
 */
public class EditProfileActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;

    private ActivityEditProfileBinding mBinding;

    private ImageView mExitIv;

    private CircleImageView mProfilePicCiv;

    private Button mEditProfilePicBtn;

    private TextView mChangeEmailTv;
    private TextView mChangeNameAndUsernameTv;
    private TextView mChangePasswordTv;

    private UserViewModel mUserViewModel;
    private ImageViewModel mImageViewModel;

    private UserInfo mUserInfo;

    private ImgurUpload mImgurUpload = new ImgurUpload();

    private SharedPreferences mSharedPrefs;

    private String mProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().hide();

        mBinding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        wireUpDisplay();
        setOnClickListeners();
        setUserViewModel();

        mSharedPrefs = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE);
        mProfilePic = mSharedPrefs.getString(Constants.USER_PROFILE_PIC_KEY, null);
        setProfilePic(mProfilePic);

        mUserViewModel.getUserByUserId(mSharedPrefs.getInt(Constants.USER_ID_KEY, -1));
    }

    private void wireUpDisplay() {
        mExitIv = findViewById(R.id.edit_profile_exit_iv);
        mProfilePicCiv = findViewById(R.id.edit_profile_profile_pic_civ);
        mEditProfilePicBtn = findViewById(R.id.edit_profile_edit_picture_button);
        mChangeEmailTv = findViewById(R.id.edit_profile_change_email_tv);
        mChangeNameAndUsernameTv = findViewById(R.id.edit_profile_change_name_and_username_tv);
        mChangePasswordTv = findViewById(R.id.edit_profile_change_password_tv);
    }

    private void setOnClickListeners() {
        mExitIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEditProfilePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        mChangeEmailTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangeEmailActivity.class);
                intent.putExtra("email", mUserInfo.getEmail());
                startActivity(intent);
            }
        });

        mChangeNameAndUsernameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangeNameAndUsernameActivity.class);
                intent.putExtra("name", mUserInfo.getName());
                intent.putExtra("username", mUserInfo.getUsername());
                startActivity(intent);
            }
        });

        mChangePasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                intent.putExtra("password", mUserInfo.getPassword());
                startActivity(intent);
            }
        });
    }

    /**
     * Allows user to choose an image from their phone gallery.
     * @param requestCode Request for retrieving the image.
     * @param resultCode Result of retrieving the image.
     * @param data The image data.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && data != null) {
            Uri selectedImage = data.getData();
            mProfilePicCiv.setImageURI(selectedImage);

            BitmapDrawable drawable = (BitmapDrawable) mProfilePicCiv.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            String image = Base64.getEncoder().encodeToString(byteArray);

            // mImgurUpload.setImage(image);
        }
    }

    private void setUserViewModel() {
        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mUserViewModel.init(getApplication());

        mUserViewModel.getUserLiveData().observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(UserInfo userInfo) {
                if (userInfo != null) {
                    mUserInfo = userInfo;
                }
            }
        });
    }

    private void setImageViewModel() {
        mImageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);
        mImageViewModel.init();

        /**
         * Post request to upload an image to Imgur.
         * Used to retrieve an imgur link.
         */
        mImageViewModel.getImgurResponseLiveData().observe(this, new Observer<ImgurResponse>() {
            @Override
            public void onChanged(ImgurResponse imgurResponse) {
                if (imgurResponse != null) {
                    // update user's profile picture

                    /**
                    Glide.with(mBinding.getRoot().getRootView())
                            .load(imgurResponse.getData().getLink())
                            .into(mBinding.editProfileProfilePicCiv);
                     **/
                } else {
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setProfilePic(String profilePic) {
        if (profilePic != null) {
            Glide.with(mBinding.getRoot().getRootView())
                    .load(profilePic)
                    .into(mBinding.editProfileProfilePicCiv);
        }
    }
}