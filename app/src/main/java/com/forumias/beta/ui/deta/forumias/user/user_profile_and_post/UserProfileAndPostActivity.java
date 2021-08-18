package com.forumias.beta.ui.deta.forumias.user.user_profile_and_post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.ui.deta.forumias.splash.UserInfoModel;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.user_post_paging.UserPostModel;
import com.forumias.beta.ui.loginfragment.LoginAlertFragment;
import com.forumias.beta.utility.FollowAndFollowingUtility;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.ui.deta.forumias.home.CallLikeUnLike;
import com.forumias.beta.ui.deta.forumias.home.model.HomeLatestPostModel;
import com.forumias.beta.ui.deta.forumias.home.model.LikeResponseModel;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileAndPostActivity extends AppCompatActivity implements CallLikeUnLike ,TabLayout.OnTabSelectedListener{

    @BindView(R.id.ivFriendImage)
    ImageView ivFriendImage;
    @BindView(R.id.tvName)
    AppCompatTextView tvName;
    @BindView(R.id.tvAbout)
    AppCompatTextView tvAbout;
    @BindView(R.id.tvFollowersCount)
    AppCompatTextView tvFollowersCount;
    @BindView(R.id.tvFollowingCount)
    AppCompatTextView tvFollowingCount;
    @BindView(R.id.tvPostsCount)
    AppCompatTextView tvPostsCount;
/*    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tvNothingShow)
    AppCompatTextView tvNothingShow;*/
    @BindView(R.id.llFollowing)
    LinearLayoutCompat tvFollowing;
    @BindView(R.id.llFollow)
    LinearLayoutCompat tvFollow;
    @BindView(R.id.tvDisplayName)
    AppCompatTextView tvDisplayName;
    @BindView(R.id.ivUserVerified)
    AppCompatImageView ivUserVerified;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.codUserDetailsSection)
    CoordinatorLayout codUserDetailsSection;
    @BindView(R.id.llUserInfo)
    LinearLayoutCompat llUserInfo;

    @BindView(R.id.llToolbarSection)
    LinearLayoutCompat llToolbarSection;
    @BindView(R.id.ivBack)
    AppCompatImageView ivBack;
    @BindView(R.id.ivAppLogo)
    AppCompatImageView ivAppLogo;


    private int tagId , actType , loginUserId , darkModeStatus;
    private String name;
    private static final String FOLLOW_TYPE = "1";
    private static final String FOLLOWING_TYPE = "0";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_post);
        ButterKnife.bind(this);
        context = this;


        initView();
    }

    private void initView(){

        MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(this));
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
       // name = myPreferenceData.getData(BaseUrl.NAME);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
      //  Log.e("userName=1111=> " , name);

        Bundle bundle;
        bundle = getIntent().getExtras();
        assert bundle != null;
         name = bundle.getString(BaseUrl.NAME);

        Log.e("userName==> " , name);
        userDetailsInfo(name);

        setDarkModeView();
      //  userPostInfo(name);

    }

    private void setDarkModeView() {
        if(darkModeStatus == 1){
            codUserDetailsSection.setBackgroundResource(R.color.black_light);
            llUserInfo.setBackgroundResource(R.color.black_light);
            tabLayout.setBackgroundResource(R.color.darkmode_back_color);
            tvName.setTextColor(ContextCompat.getColor(context,R.color.light_white));
            tvDisplayName.setTextColor(ContextCompat.getColor(context,R.color.light_white));
            tvAbout.setTextColor(ContextCompat.getColor(context,R.color.light_white));
            tvFollowersCount.setTextColor(ContextCompat.getColor(context,R.color.light_white));
            tvPostsCount.setTextColor(ContextCompat.getColor(context,R.color.light_white));
            tvFollowingCount.setTextColor(ContextCompat.getColor(context,R.color.light_white));
            llToolbarSection.setBackgroundColor(ContextCompat.getColor(context,R.color.darkmode_back_color));
            ivBack.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_back_key_white));
            ivAppLogo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.forumias_logo_white));
        }else{
            codUserDetailsSection.setBackgroundResource(R.color.back_color);
            llUserInfo.setBackgroundResource(R.color.white);
            tabLayout.setBackgroundResource(R.color.back_color);
            tvName.setTextColor(ContextCompat.getColor(context,R.color.black_light));
            tvDisplayName.setTextColor(ContextCompat.getColor(context,R.color.black_light));
            tvAbout.setTextColor(ContextCompat.getColor(context,R.color.black_light));
            tvFollowersCount.setTextColor(ContextCompat.getColor(context,R.color.black_light));
            tvPostsCount.setTextColor(ContextCompat.getColor(context,R.color.black_light));
            tvFollowingCount.setTextColor(ContextCompat.getColor(context,R.color.black_light));
            llToolbarSection.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
            ivBack.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_back_key));
            ivAppLogo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.logo_black));
        }
    }

    private void initTabView(String name, String image, String names, int is_verified) {

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        UserPostTabs adapter = new UserPostTabs(getSupportFragmentManager(), tabLayout.getTabCount() ,
                names ,tagId , image , is_verified);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.addOnTabSelectedListener(this);
        //  Glide.with(getContext()).load(R.drawable.red_logo).into(ivRedBlink);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewpager.setCurrentItem(tab.getPosition()); }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) { }
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    private void userDetailsInfo(String name) {
        BetaApiClient apiClient = new BetaApiClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<UserInfoModel> call = apiInterface.verifiedLoginUser(name,loginUserId);
        call.enqueue(new Callback<UserInfoModel>() {
            @Override
            public void onResponse(@NotNull Call<UserInfoModel> call, @NotNull Response<UserInfoModel> response) {

                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)) {
                        setUserInfoDetails(response.body().getUserDetails());
                        tagId = response.body().getUserDetails().getId();
                        Log.e("data Image=== >" , response.body().getUserDetails().getName());
                        initTabView(name , response.body().getUserDetails().getImage() ,
                                response.body().getUserDetails().getName() , response.body().getUserDetails().getIs_verified());
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<UserInfoModel> call, @NotNull Throwable t) {

            }
        });

    }

 /*   private void userPostInfo(String name) {

        HomeLatestAdapter homeLatestAdapter = new HomeLatestAdapter(this, 1 , "124",this);
        UserPostViewModel viewModel = ViewModelProviders.of(this).get(UserPostViewModel.class);
        viewModel.itemPagedList(name , progressBar).observe(this,homeLatestAdapter::submitList);
        recyclerView.setAdapter(homeLatestAdapter);
    }*/


    @OnClick(R.id.llFollow)
    public void onClickFollow(){
        if(loginUserId != 0) {
            new FollowAndFollowingUtility().followAndFollowing(this, String.valueOf(loginUserId)
                    , FOLLOW_TYPE, String.valueOf(tagId), "2", tvFollow, tvFollowing);
        }else{
            //new MyUtility().showLoginAlert(context);
            LoginAlertFragment alert  = new LoginAlertFragment();
            alert.show(getSupportFragmentManager() ,"MyAlertLogin");
        }

    }

    @OnClick(R.id.llFollowing)
    public void onClickFollowing(){
        if(loginUserId != 0) {
            new FollowAndFollowingUtility().followAndFollowing(this, String.valueOf(loginUserId)
                    , FOLLOWING_TYPE, String.valueOf(tagId), "2", tvFollow, tvFollowing);
        }else{
            LoginAlertFragment alert  = new LoginAlertFragment();
            alert.show(getSupportFragmentManager() ,"MyAlertLogin");
           // new MyUtility().showLoginAlert(context);
        }
    }

    @OnClick(R.id.ivBack)
    public void onClickBack(){
        finish();
    }

    @SuppressLint("SetTextI18n")
    private void setUserInfoDetails(UserInfoModel.UserDetails userDetails) {
        tvName.setText(userDetails.getName());
        tvDisplayName.setText("@"+userDetails.getName());
        tvAbout.setText(userDetails.getAbout());

        tvFollowersCount.setText(String.valueOf(userDetails.getFollowers()));
        tvFollowingCount.setText(String.valueOf(userDetails.getFollowing()));
        tvPostsCount.setText(String.valueOf(userDetails.getTotal_posts()));

        Glide.with(this).load(userDetails.getImage()).into(ivFriendImage);

      /*  if(userDetails.getHide_real_name() ==1){
            tvFriendName.setText(userDetails.getName());
        }else{
            tvFriendName.setText(userDetails.getFull_name());
        }*/

        new Utility().showUserVerified(userDetails.getIs_verified(), ivUserVerified);
        if(userDetails.getFollow_flag() == 1){
            tvFollowing.setVisibility(View.VISIBLE);
            tvFollow.setVisibility(View.GONE);
            Log.e("userDetails==22=> " , String.valueOf(userDetails.getFollow_flag()));
        }else{
            tvFollowing.setVisibility(View.GONE);
            tvFollow.setVisibility(View.VISIBLE);
            Log.e("userDetails==11=> " , String.valueOf(userDetails.getFollow_flag()));
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void callMyLikeUnlike(ProgressBar progressBar, HomeLatestPostModel.MyLatestPost latestPost,
                                 int position, int likeCount, int postId, int userId,
                                 AppCompatImageView ivLikeImageView, AppCompatTextView tvLikeCount
            ,AppCompatImageView ivLiveHeart) {


        Log.e("Data==> " , String.valueOf(postId));
        progressBar.setVisibility(View.VISIBLE);
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<LikeResponseModel> call = apiInterface.checkLikeUnLike(postId, userId);
        call.enqueue(new Callback<LikeResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<LikeResponseModel> call, @NotNull Response<LikeResponseModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus().equals(BaseUrl.SUCCESS)) {
                        if (response.body().getAction() == BaseUrl.LIKE) {
                            if (latestPost.getLikeInfo() != null) {
                                int totalCount = likeCount + 1;
                                latestPost.getLikeInfo().setLikeUserId(String.valueOf(userId));
                                latestPost.getLikeInfo().setLikeCount(totalCount);
                                tvLikeCount.setText(String.valueOf(totalCount));
                                ivLikeImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_blue));
                            } else {
                                HomeLatestPostModel.MyLatestPost.LikeInfo newData = new HomeLatestPostModel.MyLatestPost.LikeInfo();
                                newData.setPostId(postId);
                                newData.setLikeCount(1);
                                newData.setLikeUserId(String.valueOf(userId));

                                latestPost.setLikeInfo(newData);
                                tvLikeCount.setText(String.valueOf(1));
                                ivLikeImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_blue));

                            }
                            // homeLatestAdapter.notifyItemChanged(position);
                        } else {
                            int totalCountLess = likeCount - 1;
                            latestPost.getLikeInfo().setLikeUserId("001");
                            latestPost.getLikeInfo().setLikeCount(totalCountLess);
                            tvLikeCount.setText(String.valueOf(totalCountLess));
                            ivLikeImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_like));
                        }
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<LikeResponseModel> call, @NotNull Throwable t) {

            }
        });


    }

    @Override
    public void userMyLikeUnlike(ProgressBar progressBar, UserPostModel.MyStoriesList latestPost, int position, int likeCount, int postId, int userId, AppCompatImageView ivLikeImageView, AppCompatTextView tvLikeCount, AppCompatImageView ivLiveHeart) {

    }

    @Override
    public void testCallMethod() {

    }

    @Override
    public void addToMySpace(int userId, int postId, AppCompatImageView ivAddToMySpace) {

    }

    @Override
    public void reportDailog(int id, int loginUserId, Context context) {

    }
}
