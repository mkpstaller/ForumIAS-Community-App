package com.forumias.beta.ui.deta.forumias.academy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.FollowUnFollowModel;
import com.forumias.beta.ui.deta.forumias.create_story.CreateStoryOrQuestionActivity;
import com.forumias.beta.ui.loginfragment.LoginAlertFragment;
import com.forumias.beta.utility.CircularTextView;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.ui.deta.forumias.home.model.LikeResponseModel;
import com.forumias.beta.ui.deta.forumias.page.page_interface.PagePostLikeUnlike;
import com.forumias.beta.ui.deta.forumias.page.page_paging_adapter.PagePostModel;
import com.forumias.beta.ui.deta.forumias.page.page_paging_adapter.PagePostPagingAdapter;
import com.forumias.beta.ui.deta.forumias.page.page_paging_adapter.PagePostViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AcademyActivity extends AppCompatActivity implements PagePostLikeUnlike {

    @BindView(R.id.tvFullDescription)
    AppCompatTextView tvFullDescription;
    @BindView(R.id.tvFollowersCount)
    AppCompatTextView tvFollowersCount;
    @BindView(R.id.ivUserVerified)
    AppCompatImageView ivUserVerified;
    @BindView(R.id.tvGroupName)
    AppCompatTextView tvGroupName;
    @BindView(R.id.tvPageImage)
    ImageView ivPageImage;
    @BindView(R.id.tvChannelFirstText)
    CircularTextView tvChannelFirstText;
    @BindView(R.id.tvPostsCount)
    AppCompatTextView tvPostsCount;
    @BindView(R.id.tvFollow)
    AppCompatTextView tvFollow;
    @BindView(R.id.tvFollowing)
    AppCompatTextView tvFollowing;
    @BindView(R.id.toolbar)
    MaterialToolbar toolbar;
    @BindView(R.id.postRecyclerView)
    RecyclerView postRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.createPost_sheet)
    MaterialCardView createPost_sheet;
    @BindView(R.id.tvCreatePost)
    AppCompatTextView tvCreatePost;

    private int loginUserId, tagId;
    private static final int ACCOUNT_TYPE = 1;
    private Context context;
    private static final int FOLLOW_TYPE = 1;
    private static final int FOLLOWING_TYPE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academy);
        ButterKnife.bind(this);
        getPageDetails();

        setSupportActionBar(toolbar);

        MyPreferenceData myPreferenceData = new MyPreferenceData(this);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);

        context = this;
       /* final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back_key);
        upArrow.setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/


        toolbar.setNavigationOnClickListener(v -> finish());
    }


    @OnClick(R.id.tvFollow)
    public void onClickFollow() {
        if (loginUserId != 0) {
            followUnFollowingMethod(FOLLOW_TYPE);
        } else {
            //new MyUtility().showLoginAlert(context);
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

    @OnClick(R.id.tvCreatePost)
    public void onClickCreatePost() {
        Intent intent = new Intent(this , CreateStoryOrQuestionActivity.class);
        intent.putExtra(BaseUrl.ASK_STATUS ,4);
        intent.putExtra(BaseUrl.TAG_ID,"");
        startActivity(intent);
    }


    private void getPageDetails() {

        BetaApiClient apiClient = new BetaApiClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<PageDetailsModel> call = apiInterface.getPageDetails("ForumIAS-Academy");

        call.enqueue(new Callback<PageDetailsModel>() {
            @Override
            public void onResponse(@NotNull Call<PageDetailsModel> call, @NotNull Response<PageDetailsModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus().equals(BaseUrl.SUCCESS)) {
                        setPageView(response.body().getPageInfo());
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<PageDetailsModel> call, @NotNull Throwable t) {

            }
        });
    }

    private void setPageView(PageDetailsModel.PageInfo pageInfo) {

        callPagePostViewModel(pageInfo.getTag_slug(), pageInfo.getTitle(), pageInfo.getTag_img(),
                pageInfo.getColor_code(), pageInfo.getIs_verified());

        tagId = pageInfo.getId();

        tvFullDescription.setText(Html.fromHtml(pageInfo.getDescription()));

        tvGroupName.setText(pageInfo.getTitle());
        new Utility().showUserVerified(pageInfo.getIs_verified(), ivUserVerified);
        tvPostsCount.setText(String.valueOf(pageInfo.getPost_info_count()));
        String followBy = pageInfo.getFollowInfo().getFollow_by();
        String[] follow = followBy.split(",");

        List<String> followList = Arrays.asList(follow);
        if (followList.contains(String.valueOf(loginUserId))) {
            tvFollowing.setVisibility(View.VISIBLE);
            tvFollow.setVisibility(View.GONE);
            tvCreatePost.setVisibility(View.VISIBLE);
            int followCount = followList.size() - 1;
            tvFollowersCount.setText(String.valueOf(followCount));
        } else {
            tvFollowing.setVisibility(View.GONE);
            tvFollow.setVisibility(View.VISIBLE);
            tvCreatePost.setVisibility(View.GONE);
            int followCount = followList.size();
            //String strFollowers = "Followers  <br><b>" + followCount + "</b>";
            tvFollowersCount.setText(String.valueOf(followCount));
        }

        if (!Utility.isNullOrEmpty(pageInfo.getTag_img())) {
            ivPageImage.setVisibility(View.VISIBLE);
            Glide.with(this).load(BaseUrl.PAGE_IMAGE_URL + pageInfo.getTag_img()).into(ivPageImage);
        } else {
            ivPageImage.setVisibility(View.GONE);
            tvChannelFirstText.setVisibility(View.VISIBLE);
            tvChannelFirstText.setStrokeWidth(1);
            tvChannelFirstText.setSolidColor(pageInfo.getColor_code());
            char firstText = pageInfo.getTitle().charAt(0);
            tvChannelFirstText.setText(String.valueOf(firstText));

        }


    }


    private void callPagePostViewModel(String slug, String pageName, String tagImage, String colorCode,
                                       int isVerified) {
        PagePostPagingAdapter pagePostPagingAdapter = new PagePostPagingAdapter(this, this,
                pageName, tagImage, colorCode, isVerified);
        PagePostViewModel viewModel = ViewModelProviders.of(this).get(PagePostViewModel.class);
        viewModel.getItemList(slug, progressBar).observe(this, pagePostPagingAdapter::submitList);
        postRecyclerView.setAdapter(pagePostPagingAdapter);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void callMyLikeUnlike(ProgressBar progressBar, PagePostModel.PageData pagePosts, int position, int likeCount, int postId, int userId, AppCompatImageView ivLikeImageView, AppCompatTextView tvLikeCount) {
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

    }

    @Override
    public void repostVoidData(int loginUserId, int postId) {

    }
}
