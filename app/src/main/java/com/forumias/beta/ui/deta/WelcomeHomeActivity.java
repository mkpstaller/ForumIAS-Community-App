package com.forumias.beta.ui.deta;

import android.content.Intent;
import android.content.IntentSender;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.forumias.beta.BuildConfig;
import com.forumias.beta.R;
import com.forumias.beta.adapter.SideNavAdapter;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.myinterface.MyFollowingInterface;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.DefaultPojo;
import com.forumias.beta.ui.deta.forumias.create_story.CreatePostFragment;
import com.forumias.beta.ui.deta.forumias.create_story.CreateStoryOrQuestionActivity;
import com.forumias.beta.ui.deta.forumias.database.CommentListDatabase;
import com.forumias.beta.ui.deta.forumias.database.PostTable;
import com.forumias.beta.ui.deta.forumias.home.HomeFragment;
import com.forumias.beta.ui.deta.forumias.message.view.UserMessageHomePage;
import com.forumias.beta.ui.deta.forumias.notification.NotificationActivity;
import com.forumias.beta.ui.deta.forumias.page.PagesFragment;
import com.forumias.beta.ui.deta.forumias.profile.UserProfileActivity;
import com.forumias.beta.ui.deta.forumias.splash.VersionModel;
import com.forumias.beta.utility.InternetConnection;
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.utility.Utility;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WelcomeHomeActivity extends AppCompatActivity implements  BottomNavigationView.OnNavigationItemSelectedListener,
        MyFollowingInterface, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.flBottom)
    FrameLayout flBottom;
    @BindView(R.id.bnvBottom)
    BottomNavigationView bnvBottom;
    @BindView(R.id.ivMessage)
    AppCompatImageView ivMessage;
    @BindView(R.id.toolbar)
    MaterialToolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navRecyclerView)
    RecyclerView navRecyclerView;
    @BindView(R.id.ivProfileImage)
    ImageView ivProfileImage;
    @BindView(R.id.tvName)
    AppCompatTextView tvName;
    @BindView(R.id.tvAbout)
    AppCompatTextView tvAbout;
    @BindView(R.id.rlHomeLayout)
    RelativeLayout rlHomeLayout;
    @BindView(R.id.appBar)
    AppBarLayout appBarLayout;
    @BindView(R.id.swDarkMode)
    SwitchCompat swDarkMode;
    @BindView(R.id.ivLogo)
    AppCompatImageView ivLogo;
    @BindView(R.id.rlHeaderSection)
    RelativeLayout rlHeaderSection;
    @BindView(R.id.rlHeaderList)
    RelativeLayout rlHeaderList;
    @BindView(R.id.nav_view)
    NavigationView nav_view;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.llInAppUpdateSection)
    RelativeLayout llInAppUpdateSection;
    @BindView(R.id.tvClickUpdate)
    AppCompatTextView tvCheckUpdate;
    @BindView(R.id.tvNewUpdateTest)
    AppCompatTextView tvNewUpdateText;
    CommentListDatabase commentListDatabase;

    private int darkModeStatus;

    private boolean hidden = true;
    private int userId , imageCount = 0;
    MyPreferenceData myPreferenceData;
    String imageUrlPost;

    private String versionName;

    private static final int REQ_CODE_VERSION_UPDATE = 530;

    AppUpdateManager appUpdateManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_home);

        ButterKnife.bind(this);
       // callInAppUpdate();

        versionName = BuildConfig.VERSION_NAME;
        myPreferenceData = new MyPreferenceData(this);
        userId=myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
        shareImageData();
        // this code use for dark mode for testing
        if(darkModeStatus == 1) {
            darkMode();
            rlHeaderSection.setBackgroundResource(R.color.black);
            rlHeaderList.setBackgroundResource(R.color.black);
            tvName.setTextColor(ContextCompat.getColor(this , R.color.light_white));
            view.setBackgroundColor(ContextCompat.getColor(this,R.color.black_light));
        }else{
            normalMode();
            rlHeaderSection.setBackgroundResource(R.color.light_white);
            rlHeaderList.setBackgroundResource(R.color.light_white);
            tvName.setTextColor(ContextCompat.getColor(this , R.color.black_light));
            view.setBackgroundColor(ContextCompat.getColor(this,R.color.low_gray));
        }

        initDrawerView();
        loginUser();
        bnvBottom.setOnNavigationItemSelectedListener(this);
        nav_view.setNavigationItemSelectedListener(this);

        addBadgeView();


        if(imageCount == 0){
            loadFragment(new HomeFragment());
        }
       checkAppVersion();

        appUpdateManager = AppUpdateManagerFactory.create(this);
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {

                if(result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && result.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)){
                    try {
                        appUpdateManager.startUpdateFlowForResult(result , AppUpdateType.FLEXIBLE , WelcomeHomeActivity.this,REQ_CODE_VERSION_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        appUpdateManager.registerListener(installStateUpdatedListener);
    }

    private InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
        @Override
        public void onStateUpdate(InstallState state) {
                if(state.installStatus() == InstallStatus.DOWNLOADED){
                    showCompletedUpdated();
                }
        }
    };

    @Override
    protected void onStop() {
        if(appUpdateManager != null ) appUpdateManager.unregisterListener(installStateUpdatedListener);
        super.onStop();
    }

    private void showCompletedUpdated() {
        Snackbar snackbar  =  Snackbar.make(findViewById(android.R.id.content), "New App is  ready!",
                Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Install", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUpdateManager.completeUpdate();

            }
        });
        snackbar.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQ_CODE_VERSION_UPDATE && requestCode != RESULT_OK){
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void checkAppVersion() {

        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<VersionModel> call = apiInterface.getNewVerison();
        call.enqueue(new Callback<VersionModel>() {
            @Override
            public void onResponse(@NotNull Call<VersionModel> call, @NotNull Response<VersionModel> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        if(versionName.equals(response.body().getVersion())){
                            llInAppUpdateSection.setVisibility(View.GONE);
                        }else{
                            llInAppUpdateSection.setVisibility(View.VISIBLE);
                            tvCheckUpdate.setOnClickListener(view1 -> {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.forumias.beta&hl=en_IN")));
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<VersionModel> call, @NotNull Throwable t) {

            }
        });
    }


    private void shareImageData() {
        myPreferenceData.deleteSingleData(BaseUrl.SEARCH_STATUS);
        myPreferenceData.deleteSingleData(BaseUrl.GALLERY_IMAGE);
        Intent intent = getIntent();
        String type = intent.getType();
        String action = intent.getAction();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendImage(intent);
            }
        }
      /*  if (Intent.ACTION_SEND_MULTIPLE.equals(action) && getIntent().hasExtra(Intent.EXTRA_STREAM)) {
            ArrayList<Parcelable> list = getIntent().getParcelableArrayListExtra(Intent.EXTRA_STREAM);
           Intent i = new Intent(this, CreateStoryOrQuestionActivity.class);
            i.putExtra(BaseUrl.IMAGE, list);
            startActivity(i);
            finish();
            Log.e("multiplae image==> " , list.toString());
        }*/
    }
    private void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            Log.e("image url====> " , imageUri.toString());
         /*  Intent i = new Intent(this, CreateStoryOrQuestionActivity.class);
            i.putExtra(BaseUrl.IMAGE, imageUri.toString());
            startActivity(i);
            finish();*/
            loadFragment(new CreatePostFragment(imageUri.toString()));
            bnvBottom.setSelectedItemId(R.id.menu_create);
         imageCount = 1;


        } else {
            Toast.makeText(this, "Error occured, URI is invalid", Toast.LENGTH_LONG).show();
        }

    }


    @OnClick(R.id.ivMessage)
    public void OnClickMessage(){
        Intent intent = new Intent(this , UserMessageHomePage.class);
        startActivity(intent);
    }

    private void addBadgeView() {
        try {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) bnvBottom.getChildAt(0);
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(3); //set this to 0, 1, 2, or 3.. accordingly which menu item of the bottom bar you want to show badge
            View notificationBadge = LayoutInflater.from(WelcomeHomeActivity.this).inflate(R.layout.notification_text, menuView, false);
            itemView.addView(notificationBadge);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


  private void loginUser() {
        String success = new MyPreferenceData(WelcomeHomeActivity.this).getData(BaseUrl.SUCCESS);
        if(!Utility.isNullOrEmpty(success)) {
            if (success.equals(BaseUrl.SUCCESS)) {
                try {
                    Glide.with(this).load(new MyPreferenceData(WelcomeHomeActivity.this).getData(BaseUrl.TAG_IMAGE))
                            .into(ivProfileImage);
                }catch (IllegalStateException e){}

                tvName.setText(new MyPreferenceData(WelcomeHomeActivity.this).getData(BaseUrl.NAME));
                ivProfileImage.setOnClickListener(view -> {
                    Intent intent = new Intent(this , UserProfileActivity.class);
                    startActivity(intent);
                });
            }
        }
    }

    private void initDrawerView() {
        ActionBarDrawerToggle toggle = null;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        if(darkModeStatus ==1) {
            toggle.setHomeAsUpIndicator(R.drawable.menu_white);
        }else{
            toggle.setHomeAsUpIndicator(R.drawable.menu_black);
        }


        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });


        if(darkModeStatus == 1){
            swDarkMode.setChecked(true);
        }else{
            swDarkMode.setChecked(false);
        }

        swDarkMode.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if(isChecked){
                myPreferenceData.saveIntegerData(BaseUrl.DARK_MODE , 1);
                Intent intent = new Intent(this , WelcomeHomeActivity.class);
                startActivity(intent);
                finish();
            }else {
                myPreferenceData.saveIntegerData(BaseUrl.DARK_MODE , 0);
                Intent intent = new Intent(this , WelcomeHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


        toggle.syncState();
        setNavRecyclerView();


    }

    private void setNavRecyclerView() {
        List<DefaultPojo> list = new ArrayList<>();
        if (userId != 0) {
            list.add(new DefaultPojo("Profile"));
        }else{
            list.add(new DefaultPojo("Sign up/Login"));
        }
        //list.add(new DefaultPojo("Sign up"));
        list.add(new DefaultPojo("Dark Mode"));
        list.add(new DefaultPojo("Saved Comment"));
        list.add(new DefaultPojo("Reading List"));
        list.add(new DefaultPojo("Notification"));
        list.add(new DefaultPojo("Blog"));
        list.add(new DefaultPojo("Academy"));
        list.add(new DefaultPojo("Settings"));
        list.add(new DefaultPojo("About Us"));


        SideNavAdapter sideNavAdapter = new SideNavAdapter(list , this , this);
        navRecyclerView.setAdapter(sideNavAdapter);
    }



    private void callStoryIntent(int status) {
        Intent intent = new Intent(WelcomeHomeActivity.this , CreateStoryOrQuestionActivity.class);
        intent.putExtra(BaseUrl.ASK_STATUS ,status);
        intent.putExtra(BaseUrl.TAG_ID,"");
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.menu_home:
                fragment=  new HomeFragment();
                break;
            case R.id.menu_user:
                fragment=  new SearchPostActivity();

                break;
            case R.id.menu_create:
                fragment=  new CreatePostFragment(imageUrlPost);
                break;
            case R.id.menu_pages:
                fragment=  new NotificationActivity();
                break;
            case R.id.menu_note:
                fragment=  new PagesFragment();
                break;
        }
        return loadFragment(fragment);
    }
    private boolean loadFragment(Fragment fragment) {
        InternetConnection internetConnection = new InternetConnection();
        boolean onlineStatus  = internetConnection.checkConnection(this);
        if(onlineStatus){
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flBottom, fragment)
                        .commit();
                return true;
            }
        }else{
            MyUtility myUtility = new MyUtility();
            myUtility.checkConnection(this);
        }

        return false;
    }


    @Override
    public void followingTag(int position , String slug , RecyclerView recyclerView , AppCompatTextView tvSeeAll , boolean clickStatus) {

        switch (position){
            case 0:
            case 1:
                callFollowDataActivity(position,slug);
                break;
        }
    }



    private void callFollowDataActivity(int position, String slug){
        Intent intent = new Intent(WelcomeHomeActivity.this , MyFollowDataActivity.class);
        intent.putExtra(BaseUrl.POSITION , position);
        intent.putExtra(BaseUrl.SLUG , slug);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_right,R.anim.anim_slide_out_right);
    }



    private void darkMode() {
        ivLogo.setImageDrawable(this.getResources().getDrawable(R.drawable.forumias_logo_white));
        bnvBottom.setBackgroundResource(R.drawable.dark_bottom_menu_shape);
        toolbar.setBackgroundResource(R.drawable.dark_toolbar_shape);
        rlHomeLayout.setBackgroundResource(R.color.darkmode_back_color);
        tvNewUpdateText.setTextColor(ContextCompat.getColor(this , R.color.black));
        llInAppUpdateSection.setBackgroundResource(R.drawable.update_app_shape_white);
        ivMessage.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_send_white));
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled}, // enabled
                new int[]{-android.R.attr.state_enabled}, // disabled
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_pressed}  // pressed
        };

        int[] colors = new int[]{
                Color.WHITE,
                Color.WHITE,
                Color.WHITE,
                Color.WHITE
        };

        ColorStateList myList = new ColorStateList(states, colors);
        bnvBottom.setItemIconTintList(myList);
    }


    private void normalMode() {
        ivLogo.setImageDrawable(this.getResources().getDrawable(R.drawable.logo_black));
        toolbar.setBackgroundResource(R.drawable.toolbar_shape);
        bnvBottom.setBackgroundResource(R.drawable.bottom_menu_shape);
        rlHomeLayout.setBackgroundResource(R.color.back_color);
        tvNewUpdateText.setTextColor(ContextCompat.getColor(this , R.color.light_white));
        llInAppUpdateSection.setBackgroundResource(R.drawable.update_app_shape);
        ivMessage.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_send));
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled}, // enabled
                new int[]{-android.R.attr.state_enabled}, // disabled
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_pressed}  // pressed
        };

        int[] colors = new int[]{
                Color.BLACK,
                Color.BLACK,
                Color.BLACK,
                Color.BLACK
        };

        ColorStateList myList = new ColorStateList(states, colors);
        bnvBottom.setItemIconTintList(myList);
    }



    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            ActivityCompat.finishAffinity(WelcomeHomeActivity.this);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    @Override
    protected void onResume() {
        commentListDatabase = Room.databaseBuilder(this, CommentListDatabase.class,
                CommentListDatabase.DB_NAME).fallbackToDestructiveMigration().build();
        loginUser();
        SaveCommentPosition();
        super.onResume();
       // callInAppUpdate();
    }

    private void SaveCommentPosition() {
        MyPreferenceData sp = new MyPreferenceData(this);
        int postId  = sp.getIntegerData(BaseUrl.COMMENT_POST_ID);
        int commentPos = sp.getIntegerData(BaseUrl.COMMENT_POS);

        PostTable postTable1;
        if(commentPos != 0) {
            postTable1 = new PostTable();
            postTable1.commentPos = commentPos;
            postTable1.postId = postId;
           // addCommentPosition(postTable1);
            getCommentPosition(postId , postTable1);
        }else{
            postTable1 = new PostTable();
            postTable1.commentPos = commentPos;
            postTable1.postId = postId;
           /// Log.e("data=addd===> " ,"ookokokokko");
            addCommentPosition(postTable1);
        }
    }

    private void addCommentPosition(PostTable postTable) {
        new AsyncTask<PostTable , Void , Void>(){
            @Override
            protected Void doInBackground(PostTable... postTables) {
                commentListDatabase.doaCommment().addCommentPosition(postTable);
                return null;
            }
        }.execute(postTable);
    }
    private void getCommentPosition(int postId , PostTable postTable1) {
        new AsyncTask<Integer , Void , PostTable>(){

            @Override
            protected PostTable doInBackground(Integer... params) {
                return commentListDatabase.doaCommment().getPostPosition(params[0]);
            }

            @Override
            protected void onPostExecute(PostTable postTable) {
                super.onPostExecute(postTable);
               // Log.e("dataBase et===> " , String.valueOf(postTable.postId));
                if(postTable != null) {
                    if (String.valueOf(postTable.postId).contains(String.valueOf(postId))) {
                        //Log.e("update=====> " , "okokook Update");
                       // positionUpdate(postTable1);
                    }else{
                        //Log.e("new Add============> ","okkokokkok ADD");
                        addCommentPosition(postTable1);
                    }
                }else{
                    //Log.e("new Add=========> " , "okokkokok AD");
                    addCommentPosition(postTable1);
                }
            }
        }.execute(postId);
    }


    /*private  void callInAppUpdate(){
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
      Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                try {
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.IMMEDIATE,
                            WelcomeHomeActivity.this,REQ_CODE_VERSION_UPDATE);
                }catch (IntentSender.SendIntentException e){

                }

            }
        });
    }
*/
/*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data == null) return;
        if(requestCode == REQ_CODE_VERSION_UPDATE){
            Toast.makeText(this, "App download starts...", Toast.LENGTH_LONG).show();
            if(resultCode != RESULT_OK){
                Toast.makeText(this, "App download failed.", Toast.LENGTH_LONG).show();
            }
        }
    }*/

}


