package com.example.cst438_project03_group03.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cst438_project03_group03.BuildConfig;
import com.example.cst438_project03_group03.LoginActivity;
import com.example.cst438_project03_group03.databinding.FragmentSecondCreateAccountBinding;
import com.example.cst438_project03_group03.models.CreateAccountResponse;
import com.example.cst438_project03_group03.models.ImgurResponse;
import com.example.cst438_project03_group03.models.ImgurUpload;
import com.example.cst438_project03_group03.models.UserInfo;
import com.example.cst438_project03_group03.viewmodels.ImageViewModel;
import com.example.cst438_project03_group03.viewmodels.UserViewModel;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Class: SecondCreateAccountFragment.java
 * Description: Part 2 of Create Account feature
 *              User can choose their profile picture and provide a username and their real name.
 */
public class SecondCreateAccountFragment extends Fragment {

    private static final int RESULT_LOAD_IMAGE = 1;

    private CircleImageView mProfilePic;

    private EditText mUsernameField;
    private EditText mNameField;

    private String mEmail;
    private String mPassword;
    private String mConfirmPassword;
    private String mUsername;
    private String mName;
    private String mPic;

    private UserInfo mUser;
    private List<UserInfo> mUsers;
    private ImgurUpload mImgurUpload = new ImgurUpload();

    private Button mRegisterButton;
    private Button mUploadPictureButton;

    private FragmentSecondCreateAccountBinding mBinding;

    private UserViewModel mUserViewModel;
    private ImageViewModel mImageViewModel;

    public static SecondCreateAccountFragment newInstance() {
        return new SecondCreateAccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();

        assert bundle != null;
        mEmail = bundle.getString("email");
        mPassword = bundle.getString("password");
        mConfirmPassword = bundle.getString("confirmPassword");

        mBinding = FragmentSecondCreateAccountBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wireUpDisplay();

        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        mUserViewModel.init();

        mImageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);
        mImageViewModel.init();

        /**
         * Get request for all users from the database.
         */
        mUserViewModel.getUserListLiveData().observe(getViewLifecycleOwner(), new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> users) {
                if (users != null) {
                    mUsers = users;
                }
            }
        });

        mUserViewModel.getAllUsers();

        /**
         * Post request to save a user to the database.
         */
        mUserViewModel.getCreateUserLiveData().observe(getViewLifecycleOwner(), new Observer<CreateAccountResponse>() {
            @Override
            public void onChanged(CreateAccountResponse response) {
                if (response != null) {
                    Toast.makeText(getContext().getApplicationContext(), "Account Successfully Created.", Toast.LENGTH_SHORT).show();
                    Log.i("newId", response.getNewId() + "");

                    Intent intent = new Intent(view.getContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * Post request to upload an image to Imgur.
         * Used to retrieve an imgur link.
         */
        mImageViewModel.getImgurResponseLiveData().observe(getViewLifecycleOwner(), new Observer<ImgurResponse>() {
            @Override
            public void onChanged(ImgurResponse imgurResponse) {
                if (imgurResponse != null) {
                    mPic = imgurResponse.getData().getLink();
                    mUser = new UserInfo();

                    mUser.setUsername(mUsername);
                    mUser.setEmail(mEmail);
                    mUser.setName(mName);
                    mUser.setPic(mPic);
                    mUser.setPassword(mPassword);

                    mUserViewModel.createUser(mUser);
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * Starts gallery intent to choose an image from phone storage.
         */
        mUploadPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        /**
         * Creates user account.
         */
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fieldsFilled()) {
                    if (validUsername()) {
                        mImageViewModel.imgurUpload(mImgurUpload, "Client-ID " + BuildConfig.IMGUR_CLIENT_ID);
                    }
                }
            }
        });
    }

    /**
     * Wire up display components.
     * mProfilePic
     * mUsernameField
     * mNameField
     * mRegisterButton
     * mUploadPictureButton
     */
    private void wireUpDisplay() {
        mProfilePic = mBinding.createAccountCircleImage;

        mUsernameField = mBinding.createAccountUsernameEditText;
        mNameField = mBinding.createAccountNameEditText;

        mRegisterButton = mBinding.createAccountRegisterButton;
        mUploadPictureButton = mBinding.createAccountUploadPictureButton;
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
            mProfilePic.setImageURI(selectedImage);

            BitmapDrawable drawable = (BitmapDrawable) mProfilePic.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            String image = Base64.getEncoder().encodeToString(byteArray);

            mImgurUpload.setImage(image);
        }
    }

    /**
     * Checks that all text fields have been filled.
     * @return true if all are filled, false otherwise.
     */
    private boolean fieldsFilled() {
        mUsername = mUsernameField.getText().toString();
        mName = mNameField.getText().toString();

        return !mUsername.isEmpty() && !mName.isEmpty();
    }

    /**
     * Checks if the username hasn't been taken yet.
     * @return true if username is not taken, false otherwise.
     */
    private boolean validUsername() {
        mUserViewModel.getAllUsers();

        for (UserInfo user : mUsers) {
            if (user.getUsername().equals(mUsername)) {
                return false;
            }
        }
        return true;
    }
}