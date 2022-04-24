package com.example.cst438_project03_group03.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cst438_project03_group03.databinding.FragmentSecondCreateAccountBinding;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Class: SecondCreateAccountFragment.java
 * Description: Part 2 of Create Account feature
 *              User can choose their profile picture and provide a username and their real name.
 */
public class SecondCreateAccountFragment extends Fragment {
    private static final int RESULT_LOAD_IMAGE = 1;
    // private static final int RESULT_OK = 1;

    private CircleImageView mProfilePic;

    private EditText mUsernameField;
    private EditText mNameField;

    private String mEmail;
    private String mPassword;
    private String mConfirmPassword;
    private String mUsername;
    private String mName;

    private Button mRegisterButton;
    private Button mUploadPictureButton;

    private FragmentSecondCreateAccountBinding mBinding;

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
        Bundle bundle = new Bundle();

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

        mUploadPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fieldsFilled()) {

                }
            }
        });
    }

    private void wireUpDisplay() {
        mProfilePic = mBinding.createAccountCircleImage;

        mUsernameField = mBinding.createAccountUsernameEditText;
        mNameField = mBinding.createAccountNameEditText;

        mRegisterButton = mBinding.createAccountRegisterButton;
        mUploadPictureButton = mBinding.createAccountUploadPictureButton;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && data != null) {
            Uri selectedImage = data.getData();
            mProfilePic.setImageURI(selectedImage);
           //  mProfilePic.setLayoutParams();
        }
    }

    private boolean fieldsFilled() {
        mUsername = mUsernameField.getText().toString();
        mName = mNameField.getText().toString();

        return !mUsername.isEmpty() && !mName.isEmpty();
    }
}