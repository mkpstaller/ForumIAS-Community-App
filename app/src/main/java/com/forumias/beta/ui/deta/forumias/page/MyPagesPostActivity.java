package com.forumias.beta.ui.deta.forumias.page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.forumias.beta.adapter.MyGroupPager;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.FollowUnFollowModel;
import com.forumias.beta.pojo.GroupPostDetailsModel;
import com.forumias.beta.ui.deta.forumias.academy.PageDetailsModel;
import com.forumias.beta.ui.deta.forumias.create_story.CreateStoryOrQuestionActivity;
import com.forumias.beta.ui.deta.forumias.page.page_interface.PagePostLikeUnlike;
import com.forumias.beta.ui.deta.forumias.page.page_paging_adapter.PagePostModel;
import com.forumias.beta.ui.deta.forumias.page.page_paging_adapter.PagePostPagingAdapter;
import com.forumias.beta.ui.deta.forumias.page.page_paging_adapter.PagePostViewModel;
import com.forumias.beta.ui.loginfragment.LoginAlertFragment;
import com.forumias.beta.utility.CircularTextView;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.ui.deta.forumias.ReportDailogFragment;
import com.forumias.beta.ui.deta.forumias.home.model.AddMySpaceModel;
import com.forumias.beta.ui.deta.forumias.home.model.LikeResponseModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPagesPostActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener,
        PagePostLikeUnlike {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tvFullDescription)
    AppCompatTextView tvFullDescription;
    @BindView(R.id.tvFollowersCount)
    AppCompatTextView tvFollowersCount;
    @BindView(R.id.tvPostsCount)
    AppCompatTextView tvPostsCount;
    @BindView(R.id.postRecyclerView)
    RecyclerView postRecyclerView;
    @BindView(R.id.llFollow)
    LinearLayoutCompat tvFollow;
    @BindView(R.id.llFollowing)
    LinearLayoutCompat tvFollowing;
    @BindView(R.id.tvCreatePost)
    LinearLayoutCompat tvCreatePost;
    @BindView(R.id.ivChannelImage)
    ImageView ivPageImage;
    @BindView(R.id.tvChannelFirstText)
    CircularTextView tvChannelFirstText;
    @BindView(R.id.tvGroupName)
    AppCompatTextView tvGroupName;
    @BindView(R.id.toolbar)
    MaterialToolbar toolbar;
    @BindView(R.id.createPost_sheet)
    MaterialCardView createPost_sheet;
    @BindView(R.id.cbAddBackCover)
    MaterialCheckBox cbAddBackCover;
    @BindView(R.id.llBackCover)
    LinearLayoutCompat llBackCover;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ivUserVerified)
    AppCompatImageView ivUserVerified;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.codlPageDetails)
    CoordinatorLayout codlPageDetails;
    @BindView(R.id.collapsToollayout)
    CollapsingToolbarLayout collapsToollayout;
    @BindView(R.id.llToolbarSection)
    LinearLayoutCompat llToolbarSection;
    @BindView(R.id.ivBack)
    AppCompatImageView ivBack;
    @BindView(R.id.ivAppLogo)
    AppCompatImageView ivAppLogo;

    private Context context;

    private List<GroupPostDetailsModel.Followers> followersList;
    private String description;
    private int loginUserId, tagId, isVerified , darkModeStatus;
    private static final int ACCOUNT_TYPE = 1;
    private static final int FOLLOW_TYPE = 1;
    private static final int FOLLOWING_TYPE = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_group_post);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        context = this;
        followersList = new ArrayList<>();
        MyPreferenceData myPreferenceData = new MyPreferenceData(this);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);

        setViewDarkMOde();


        setBundle();

        cbAddBackCover.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                llBackCover.setVisibility(View.VISIBLE);
            } else {
                llBackCover.setVisibility(View.GONE);
            }
        });

    }

    private void setViewDarkMOde() {
        if(darkModeStatus == 1) {
            codlPageDetails.setBackgroundResource(R.color.darkmode_back_color);
            collapsToollayout.setBackgroundResource(R.color.darkmode_back_color);
            tvGroupName.setTextColor(ContextCompat.getColor(this ,R.color.light_white));
            tvFullDescription.setTextColor(ContextCompat.getColor(this ,R.color.light_white));
            tvFollowersCount.setTextColor(ContextCompat.getColor(this ,R.color.light_white));
            tvPostsCount.setTextColor(ContextCompat.getColor(this ,R.color.light_white));
            llToolbarSection.setBackgroundColor(ContextCompat.getColor(this,R.color.darkmode_back_color));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back_key_white));
            ivAppLogo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo_white));
        }else{
            codlPageDetails.setBackgroundResource(R.color.back_color);
            collapsToollayout.setBackgroundResource(R.color.back_color);
            tvGroupName.setTextColor(ContextCompat.getColor(this ,R.color.black_light));
            tvFullDescription.setTextColor(ContextCompat.getColor(this ,R.color.black_light));
            tvFollowersCount.setTextColor(ContextCompat.getColor(this ,R.color.black_light));
            tvPostsCount.setTextColor(ContextCompat.getColor(this ,R.color.black_light));
            llToolbarSection.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back_key));
            ivAppLogo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo_black));
        }
    }


    @OnClick(R.id.ivBack)
    public void onClickBack(){
        finish();
    }
    @OnClick(R.id.tvCreatePost)
    public void onClickCreatePost() {

        Intent intent = new Intent(MyPagesPostActivity.this, CreateStoryOrQuestionActivity.class);
        intent.putExtra(BaseUrl.ASK_STATUS, 3);
        intent.putExtra(BaseUrl.TAG_ID, String.valueOf(tagId));
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.tvFollow)
    public void onClickFollow() {
        if (loginUserId != 0) {
            followUnFollowingMethod(FOLLOW_TYPE);
        } else {
           // new MyUtility().showLoginAlert(context);
            LoginAlertFragment alert  = new LoginAlertFragment();
            alert.show(getSupportFragmentManager() ,"MyAlertLogin");
        }
    }

    @OnClick(R.id.tvFollowing)
    public void onClickFollowing() {
        if (loginUserId != 0) {
            followUnFollowingMethod(FOLLOWING_TYPE);
        } else {
           // new MyUtility().showLoginAlert(context);
            LoginAlertFragment alert  = new LoginAlertFragment();
            alert.show(getSupportFragmentManager() ,"MyAlertLogin");
        }
    }





    private void setBundle() {

        MyPreferenceData myPref = new MyPreferenceData(context);
        String tagSlug = myPref.getData(BaseUrl.PREF_SLUG);
        Log.e("page slug====> " , tagSlug);
        getPageDetails(tagSlug);

    }

    private void callPagePostViewModel(String slug, String pageName, String tagImage, String colorCode) {

        PagePostPagingAdapter pagePostPagingAdapter = new PagePostPagingAdapter(this, this,
                pageName, tagImage, colorCode, isVerified);
        PagePostViewModel viewModel = ViewModelProviders.of(this).get(PagePostViewModel.class);
        viewModel.getItemList(slug, progressBar).observe(this, pagePostPagingAdapter::submitList);
        postRecyclerView.setAdapter(pagePostPagingAdapter);

    }


    private void getPageDetails(String slug){
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<PageDetailsModel> call = apiInterface.getPageDetails(slug);

        call.enqueue(new Callback<PageDetailsModel>() {
            @Override
            public void onResponse(@NotNull Call<PageDetailsModel> call, @NotNull Response<PageDetailsModel> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        setPageData(response.body().getPageInfo());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<PageDetailsModel> call, @NotNull Throwable t) {

            }
        });
    }

    private void setPageData(PageDetailsModel.PageInfo pageInfo) {

        callPagePostViewModel(pageInfo.getTag_slug(), pageInfo.getTitle(), pageInfo.getTag_img(), pageInfo.getColor_code());

        tagId = pageInfo.getId();
        new Utility().showUserVerified(pageInfo.getIs_verified(), ivUserVerified);
        String followersInfo1 = String.valueOf(pageInfo.getFollowInfo().getFollow_by());
        String[] followInfoCount1 = followersInfo1.split(",");
        List<String> followUserId1 = Arrays.asList(followInfoCount1);
        if(followUserId1.contains(String.valueOf(loginUserId))){
            tvFollowing.setVisibility(View.VISIBLE);
            tvFollow.setVisibility(View.GONE);
            tvCreatePost.setVisibility(View.VISIBLE);

        }else{
            tvFollowing.setVisibility(View.GONE);
            tvFollow.setVisibility(View.VISIBLE);
            tvCreatePost.setVisibility(View.GONE);
        }
        tvFollowersCount.setText(String.valueOf(followUserId1.size()));
        tvGroupName.setText(pageInfo.getTitle());
        tvFullDescription.setText(Html.fromHtml(pageInfo.getDescription()));
        tvPostsCount.setText(String.valueOf(pageInfo.getPost_info_count()));

       if(!Utility.isNullOrEmpty(pageInfo.getTag_img())) {
            ivPageImage.setVisibility(View.VISIBLE);
            Glide.with(this).load(BaseUrl.PAGE_IMAGE_URL + pageInfo.getTag_img()).into(ivPageImage);
        } else {
            ivPageImage.setVisibility(View.GONE);
            tvChannelFirstText.setVisibility(View.VISIBLE);
            tvChannelFirstText.setStrokeWidth(1);
            tvChannelFirstText.setSolidColor(pageInfo.getColor_code());
            assert pageInfo.getTitle() != null;
            char firstText = pageInfo.getTitle().charAt(0);
            tvChannelFirstText.setText(String.valueOf(firstText));

        }

    }


    @Override
    public void callMyLikeUnlike(ProgressBar progressBar, PagePostModel.PageData pagePosts, int position,
                                 int likeCount, int postId, int userId, AppCompatImageView ivLikeImageView,
                                 AppCompatTextView tvLikeCount) {

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
                            if (pagePosts.getLikeInfo() != null) {
                                int totalCount = likeCount + 1;
                                pagePosts.getLikeInfo().setUser_ids(String.valueOf(userId));
                                pagePosts.getLikeInfo().setLike_count(totalCount);
                                tvLikeCount.setText(String.valueOf(totalCount));
                                ivLikeImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_blue));
                            } else {
                                PagePostModel.PageData.LikeInfo newData = new PagePostModel.PageData.LikeInfo();
                                newData.setPost_id(postId);
                                newData.setLike_count(1);
                                newData.setUser_ids(String.valueOf(userId));

                                pagePosts.setLikeInfo(newData);
                                tvLikeCount.setText(String.valueOf(1));
                                ivLikeImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_like_blue));

                            }
                        } else {
                            int totalCountLess = likeCount - 1;
                            pagePosts.getLikeInfo().setUser_ids("001");
                            pagePosts.getLikeInfo().setLike_count(totalCountLess);
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
    public void addToMySpace(int userId, int postId, AppCompatImageView ivAddToMySpace) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<AddMySpaceModel> call = apiInterface.addToMySpace(userId , postId);
        call.enqueue(new Callback<AddMySpaceModel>() {
            @Override
            public void onResponse(@NotNull Call<AddMySpaceModel> call, @NotNull Response<AddMySpaceModel> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getResult() == 1){
                        ivAddToMySpace.setImageDrawable(ContextCompat.getDrawable(getApplicationContext() , R.drawable.ic_check_blue));
                    }else {
                        ivAddToMySpace.setImageDrawable(ContextCompat.getDrawable(getApplicationContext() , R.drawable.ic_add_black));
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<AddMySpaceModel> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void repostVoidData(int loginUserId, int postId) {
        ReportDailogFragment fragment = new ReportDailogFragment(postId ,loginUserId);
        fragment.show(getSupportFragmentManager(), "MyCustomDialog");
    }


    private void followUnFollowingMethod(int status) {

        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<FollowUnFollowModel> call = apiInterface.getFollowUnFollowing(String.valueOf(loginUserId)
                , String.valueOf(tagId), String.valueOf(status), String.valueOf(ACCOUNT_TYPE));
        call.enqueue(new Callback<FollowUnFollowModel>() {
            @Override
            public void onResponse(@NotNull Call<FollowUnFollowModel> call, @NotNull Response<FollowUnFollowModel> response) {
                assert response.body() != null;
                if (response.body().getStatus().equalsIgnoreCase(BaseUrl.SUCCESS)) {
                    if (response.body().getFlag() == 1) {
                        tvFollow.setVisibility(View.GONE);
                        tvFollowing.setVisibility(View.VISIBLE);
                    } else {
                        tvFollow.setVisibility(View.VISIBLE);
                        tvFollowing.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(@NotNull Call<FollowUnFollowModel> call, @NotNull Throwable t) {

            }
        });
    }


    private void initView() {

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        MyGroupPager adapter = new MyGroupPager(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onRestart() {
        setBundle();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        setBundle();
        super.onResume();

    }
}
