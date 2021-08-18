package com.forumias.beta.ui.deta.forumias.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.chinalwb.are.FileUtil;
import com.forumias.beta.adapter.UserProfilePager;
import com.forumias.beta.api.APIClient;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.ProfileImageModel;
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.utility.PermissionUtility;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.ui.deta.WelcomeHomeActivity;
import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.rlProfileBack)
    RelativeLayout rlProfileBack;
    @BindView(R.id.tvPostCount)
    AppCompatTextView tvPostCount;
    @BindView(R.id.tvFollowingCount)
    AppCompatTextView tvFollowingCount;
    @BindView(R.id.tvFollowersCount)
    AppCompatTextView tvFollowersCount;
    @BindView(R.id.rlProfileBackSection)
    RelativeLayout rlProfileBackSection;
    @BindView(R.id.ivUserImage)
    ImageView ivUserImage;
    @BindView(R.id.tvName)
    AppCompatTextView tvName;
    @BindView(R.id.ivAddImage)
    AppCompatImageView ivAddImage;
    @BindView(R.id.tvEmail)
    AppCompatTextView tvEmail;

    private static final int CAMERA_REQUEST = 100;
    private static final int GALLERY_REQUEST = 101;

    public final String APP_TAG = "MyCustomApp";
    public String photoFileName = "photo.jpg";
    File photoFile;


    private int loginUserId , darkModeStatus , followersCount , followingCount , postCount , authId;
    private String userName , userImage , email;

    private int[] tabIcons = {
            R.drawable.ic_profile_user,
            R.drawable.ic_pages,
            //R.drawable.ic_sms_black,
            R.drawable.ic_notifications_none,
            R.drawable.ic_post_follow,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        MyPreferenceData myPreferenceData = new MyPreferenceData(this);
        userName = myPreferenceData.getData(BaseUrl.NAME);
        email = myPreferenceData.getData(BaseUrl.EMAIL);
        authId = myPreferenceData.getIntegerData(BaseUrl.AUTH_ID);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
        followersCount = myPreferenceData.getIntegerData(BaseUrl.FOLLOWER_DATA);
        followingCount = myPreferenceData.getIntegerData(BaseUrl.FOLLOWING_DATA);
        postCount = myPreferenceData.getIntegerData(BaseUrl.POST_COUNT);
        userImage = myPreferenceData.getData(BaseUrl.TAG_IMAGE);

        setWhiteDarkMode();
        setTabSetting();
        setupTabIcons();
       // setUserInfo();
    }


    @OnClick(R.id.ivBack)
    public void onClickBack(){
        Intent intent = new Intent(this , WelcomeHomeActivity.class);
        startActivity(intent);
        finish();
    }
    @OnClick(R.id.ivAddImage)
    public void onClickUpdateImage(){
        PopupMenu popup = new PopupMenu(this, ivAddImage);
        popup.getMenuInflater().inflate(R.menu.media_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.camera) {
               // Toast.makeText(this , "Under Development...!",Toast.LENGTH_LONG).show();
                boolean galleryStatus = new PermissionUtility().checkCameraPermissions(this, UserProfileActivity.this);
                if (galleryStatus) {
                    startCamera();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Granted Camera Permission..!", Toast.LENGTH_SHORT).show();
                }
            }
            if (item.getItemId() == R.id.gallery) {
                boolean cameraStatus = new PermissionUtility().checkGalleryPermissions(this, UserProfileActivity.this);
                if (cameraStatus) {
                    galleryCamera();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Granted Gallery Permission..!", Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        });
        popup.show();

    }

    private void galleryCamera() {
        Intent gallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, GALLERY_REQUEST);
    }

    private void startCamera() {
  /*      Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri(photoFileName);

        Uri fileProvider = FileProvider.getUriForFile(UserProfileActivity.this, "com.forumias.beta.provider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
      //  if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_REQUEST);
      //  }*/

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    public File getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
          //  Log.d(APP_TAG, "failed to create directory");
        }
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }


    private void setWhiteDarkMode() {

        tvName.setText(userName);
        tvEmail.setText(email);
        tvFollowersCount.setText(String.valueOf(followersCount));
        tvFollowingCount.setText(String.valueOf(followingCount));
        tvPostCount.setText(String.valueOf(postCount));
        Glide.with(this).load(userImage)
                .into(ivUserImage);

        if(darkModeStatus == 1){
            rlProfileBack.setBackgroundResource(R.color.darkmode_back_color);
            tvPostCount.setTextColor(ContextCompat.getColor(this, R.color.light_white));
            tvFollowersCount.setTextColor(ContextCompat.getColor(this, R.color.light_white));
            tvFollowingCount.setTextColor(ContextCompat.getColor(this, R.color.light_white));
            tabLayout.setBackgroundResource(R.color.black_light);

        }else{
            rlProfileBack.setBackgroundResource(R.color.white);
            tvPostCount.setTextColor(ContextCompat.getColor(this, R.color.black_light));
            tvFollowersCount.setTextColor(ContextCompat.getColor(this, R.color.black_light));
            tvFollowingCount.setTextColor(ContextCompat.getColor(this, R.color.black_light));
            tabLayout.setBackgroundResource(R.color.back_color);
            rlProfileBackSection.setBackgroundResource(R.drawable.blue_background_profile);
        }
    }

    private void setupTabIcons() {
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(tabIcons[1]);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(tabIcons[2]);
        Objects.requireNonNull(tabLayout.getTabAt(3)).setIcon(tabIcons[3]);
       // Objects.requireNonNull(tabLayout.getTabAt(4)).setIcon(tabIcons[4]);
    }


    private void setTabSetting() {
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        UserProfilePager adapter = new UserProfilePager(getSupportFragmentManager(), tabLayout.getTabCount() , userName);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) { }
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
          /*  if (requestCode == GALLERY_REQUEST) {
                onSelectFromGalleryResult(data);
            }*/
             if (requestCode == CAMERA_REQUEST) {
                Bitmap photo = null;
                if (data != null) {
                    photo = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                    Log.e("camera Image====> " , String.valueOf(photo));
                }
                Uri uri =converUri(photo);
                uriToFileResult(uri);
            }
        }
    }

    private Uri converUri(Bitmap photo) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), photo, "Title", null);
        return Uri.parse(path);
    }


    private void onSelectFromGalleryResult(Intent data) {
        Uri uri = data.getData();
        ivUserImage.setImageURI(uri);
        File file;
        assert uri != null;
        try {
            file = FileUtil.from(this,uri);
            if(20000 < file.length()){
                file = MyUtility.saveBitmapToFile(file);
            }
            createImageFileUrl(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void uriToFileResult(Uri uri) {

        ivUserImage.setImageURI(uri);
        File file;
        assert uri != null;
        try {
            file = FileUtil.from(this,uri);
            if(20000 < file.length()){
                file = MyUtility.saveBitmapToFile(file);
            }
            createImageFileUrl(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void createImageFileUrl(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        APIClient apiClient = new APIClient();
        APIInterface api = apiClient.getClient().create(APIInterface.class);
        Call<ProfileImageModel> call = api.postUserImageImageFile(part , authId);
        call.enqueue(new Callback<ProfileImageModel>() {
            @Override
            public void onResponse(Call<ProfileImageModel> call, Response<ProfileImageModel> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getStatus() == 1){
                        MyPreferenceData myPreferenceData = new MyPreferenceData(UserProfileActivity.this);
                        myPreferenceData.saveData(BaseUrl.TAG_IMAGE , response.body().getProfileData().getImage());
                        Toast.makeText(getApplicationContext() , response.body().getMessage() , Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ProfileImageModel> call, Throwable t) {

            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
