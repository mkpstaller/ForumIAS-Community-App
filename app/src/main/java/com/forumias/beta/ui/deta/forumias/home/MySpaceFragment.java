package com.forumias.beta.ui.deta.forumias.home;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ahmadnemati.clickablewebview.ClickableWebView;
import com.forumias.beta.api.AdsApiClient;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.ui.deta.forumias.home.home_paging.HomeLatestAdapter;
import com.forumias.beta.ui.deta.forumias.home.home_paging.LatestPostViewModel;
import com.forumias.beta.ui.deta.forumias.home.home_paging.MySpancePostViewModel;
import com.forumias.beta.ui.deta.forumias.home.model.AddMySpaceModel;
import com.forumias.beta.ui.deta.forumias.home.model.AdsController;
import com.forumias.beta.ui.deta.forumias.home.model.AdsControllerImage;
import com.forumias.beta.ui.deta.forumias.home.model.HomeLatestPostModel;
import com.forumias.beta.ui.deta.forumias.home.model.LikeResponseModel;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.user_post_paging.UserPostModel;
import com.forumias.beta.ui.login.LoginActivity;
import com.forumias.beta.utility.InternetConnection;
import com.forumias.beta.utility.MyBounceInterpolator;
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.ui.deta.forumias.ReportDailogFragment;
import com.forumias.beta.utility.Utility;
import com.google.android.material.card.MaterialCardView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MySpaceFragment extends Fragment implements CallLikeUnLike , SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.mySpaceRecyclerView)
    RecyclerView rvMySpace;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.sectionComment)
    RelativeLayout sectionComment;
    @BindView(R.id.commentEdit)
    AppCompatEditText commentEdit;
    @BindView(R.id.loginInfoSection)
    MaterialCardView loginInfoSection;
    @BindView(R.id.tvNotDataFound)
    AppCompatTextView tvNotDataFound;
    @BindView(R.id.rlMySpaceMain)
    RelativeLayout rlMySpaceMain;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.rlProgress)
    RelativeLayout rlProgress;
    @BindView(R.id.searchView)
    SearchView searchView;

    @BindView(R.id.adsView)
    ClickableWebView adsView;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.webViewImage)
    WebView webViewImage;
    @BindView(R.id.ivAdsShowHide)
    AppCompatImageView ivAdsShowHide;

    boolean imageAdsShowStatus = true;

    private int loginUserId, articleVisible , position;
    HomeLatestAdapter homeLatestAdapter;
    private boolean onlineStatus;
    MySpancePostViewModel mySpancePostViewModel;
    LatestPostViewModel latestPostViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_space, container, false);
        ButterKnife.bind(this, view);

        initView(view);
        getMyAdsController();
        getMyAdsImageController();

        return view;
    }

    private void initView(View view) {
        swipeRefresh.setOnRefreshListener(this);
        MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(getContext()));
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        articleVisible = myPreferenceData.getIntegerData(BaseUrl.ARTICLE_VISIBLE);
        int darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
        int searchStatus = myPreferenceData.getIntegerData(BaseUrl.SEARCH_STATUS);
        InternetConnection internetConnection = new InternetConnection();
        onlineStatus  = internetConnection.checkConnection(Objects.requireNonNull(getContext()));
        if(searchStatus == 10){
            searchView.setVisibility(View.GONE);
            searchView.setIconifiedByDefault(true);
            searchView.setFocusable(true);
            searchView.setIconified(false);
            searchView.clearFocus();
            searchView.requestFocusFromTouch();
        }else{
            searchView.setVisibility(View.GONE);
        }

        if(darkModeStatus == 1){
            rlMySpaceMain.setBackgroundResource(R.color.darkmode_back_color);
        }else{
            rlMySpaceMain.setBackgroundResource(R.color.back_color);
        }

        assert getArguments() != null;
        position = getArguments().getInt(BaseUrl.TAB_POSITION, 0);
        getAddMySpaceData(position);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                postSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getMyAdsController() {

        AdsApiClient adsApiClient = new AdsApiClient();
        APIInterface apiInterface =  adsApiClient.getClient().create(APIInterface.class);
         Call<AdsController> call = apiInterface.getAdsController();
         call.enqueue(new Callback<AdsController>() {
             @Override
             public void onResponse(Call<AdsController> call, Response<AdsController> response) {
                 if(response.isSuccessful()){
                     assert response.body() != null;
                     if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        viewAdsController(response.body().getResult());
                     }
                 }
             }

             @Override
             public void onFailure(Call<AdsController> call, Throwable t) {

             }
         });

    }

    private void getMyAdsImageController() {

        AdsApiClient adsApiClient = new AdsApiClient();
        APIInterface apiInterface =  adsApiClient.getClient().create(APIInterface.class);
        Call<AdsControllerImage> call = apiInterface.getAdsImageController(1);
        call.enqueue(new Callback<AdsControllerImage>() {
            @Override
            public void onResponse(Call<AdsControllerImage> call, Response<AdsControllerImage> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        viewAdsControllerImage(response.body().getDescription());
                    }
                }
            }

            @Override
            public void onFailure(Call<AdsControllerImage> call, Throwable t) {

            }
        });

    }

    private void viewAdsControllerImage(String description) {
        webViewImage.setVisibility(View.VISIBLE);
        if(description.isEmpty()) {
            webViewImage.setVisibility(View.GONE);
            ivAdsShowHide.setVisibility(View.GONE);
        }else{
            webViewImage.setVisibility(View.VISIBLE);
            ivAdsShowHide.setVisibility(View.VISIBLE);
            new Utility().showCommentDescription(webViewImage, description);
            webViewImage.setWebChromeClient(new WebChromeClient()
            {
                private View mCustomView;
                private WebChromeClient.CustomViewCallback mCustomViewCallback;
                protected FrameLayout mFullscreenContainer;
                private int mOriginalOrientation;
                private int mOriginalSystemUiVisibility;

                public Bitmap getDefaultVideoPoster()
                {
                    if (getActivity() == null) {
                        return null;
                    }
                    return BitmapFactory.decodeResource(getActivity().getApplicationContext().getResources(), 2130837573);
                }

                public void onHideCustomView()
                {
                    ((FrameLayout)getActivity().getWindow().getDecorView()).removeView(this.mCustomView);
                    this.mCustomView = null;
                    getActivity().getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
                    getActivity().setRequestedOrientation(this.mOriginalOrientation);
                    this.mCustomViewCallback.onCustomViewHidden();
                    this.mCustomViewCallback = null;
                }

                public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
                {
                    if (this.mCustomView != null)
                    {
                        onHideCustomView();
                        return;
                    }
                    this.mCustomView = paramView;
                    this.mOriginalSystemUiVisibility = getActivity().getWindow().getDecorView().getSystemUiVisibility();
                    this.mOriginalOrientation = getActivity().getRequestedOrientation();
                    this.mCustomViewCallback = paramCustomViewCallback;
                    ((FrameLayout)getActivity().getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
                    getActivity().getWindow().getDecorView().setSystemUiVisibility(3846);
                }
            });

        }


        ivAdsShowHide.setOnClickListener(view -> {
            if(imageAdsShowStatus){
                webViewImage.setVisibility(View.GONE);
                imageAdsShowStatus = false;
                ivAdsShowHide.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_white));
            }else{
                webViewImage.setVisibility(View.VISIBLE);
                imageAdsShowStatus = true;
                ivAdsShowHide.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_keyboard_arrow_up));
            }
        });
    }

    private void viewAdsController(AdsController.ResultData result) {
        if(result.getStatus() == 1){
            webView.setVisibility(View.VISIBLE);
           new Utility().showCommentDescription(webView, result.getDescription());




        }else{
            webView.setVisibility(View.GONE);
        }
    }


    private void postSearch(String queryData) {
        String searchData = queryData.toLowerCase();
        List<HomeLatestPostModel> listData = new ArrayList<>();

       /* for(HomeLatestPostModel model : listData){
            if(model.getTitle().toLowerCase().contains(userInput)){
                list.add(model);
            }
        }
        pageAdapter.updateListData(list);*/

    }

    private void getAddMySpaceData(int position) {

        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<HomeLatestPostModel> call = apiInterface.fetchHomeLatestPost(String.valueOf(loginUserId),1);
        call.enqueue(new Callback<HomeLatestPostModel>() {
            @Override
            public void onResponse(@NotNull Call<HomeLatestPostModel> call, @NotNull Response<HomeLatestPostModel> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    showTab(position , response.body().getFollow_thread());
                }
            }
            @Override
            public void onFailure(@NotNull Call<HomeLatestPostModel> call, @NotNull Throwable t) {
            }
        });
    }

    @OnClick(R.id.tvLoginNow)
    public void onClickLoginNow() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();
    }

    private void showTab(int position, String followThread) {
        switch (position) {
            case 0:
                if(onlineStatus){
                    loadlatestData(0 , followThread);
                }else{
                    MyUtility myUtility = new MyUtility();
                    myUtility.checkConnection(getContext());
                }
                break;
            case 1:

                if(onlineStatus){
                    loadData(1 ,followThread);
                }else{
                    MyUtility myUtility = new MyUtility();
                    myUtility.checkConnection(getContext());
                }
                break;
        }
    }

    private void loadData(int annoucement, String followThread) {
        try{

        homeLatestAdapter = new HomeLatestAdapter(getContext(), annoucement, followThread,this);
        mySpancePostViewModel = ViewModelProviders.of(this).get(MySpancePostViewModel.class);
        mySpancePostViewModel.itemPagedList(articleVisible, loginUserId, progressBar ,tvNotDataFound ,rlProgress).observe(this, homeLatestAdapter::submitList);
        rvMySpace.setAdapter(homeLatestAdapter);

        }catch (Exception e){e.printStackTrace();}
    }

    private void loadlatestData(int annoucement, String followThread) {
        try{

        homeLatestAdapter = new HomeLatestAdapter(getContext(), annoucement, followThread,this);
        latestPostViewModel = ViewModelProviders.of(this).get(LatestPostViewModel.class);
        latestPostViewModel.itemPagedList(articleVisible, loginUserId, progressBar ,tvNotDataFound).observe(this, homeLatestAdapter::submitList);
        rvMySpace.setAdapter(homeLatestAdapter);

        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void callMyLikeUnlike(ProgressBar progressBar, HomeLatestPostModel.MyLatestPost latestPost, int position, int likeCount, int postId,
                                 int userId, AppCompatImageView ivLikeImageView,
                                 AppCompatTextView tvLikeCount , AppCompatImageView ivLiveHeart) {
        progressBar.setVisibility(View.GONE);
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
                                ivLikeImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red));
                                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.5, 20);
                                Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
                                animation.setInterpolator(interpolator);
                                ivLiveHeart.setAnimation(animation);
                                addCustomToast("Like Successfully.!");
                            } else {
                                HomeLatestPostModel.MyLatestPost.LikeInfo newData = new HomeLatestPostModel.MyLatestPost.LikeInfo();
                                newData.setPostId(postId);
                                newData.setLikeCount(1);
                                newData.setLikeUserId(String.valueOf(userId));

                                latestPost.setLikeInfo(newData);
                                tvLikeCount.setText(String.valueOf(1));
                                ivLikeImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red));
                                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                                Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
                                animation.setInterpolator(interpolator);
                                ivLiveHeart.setAnimation(animation);
                                addCustomToast("Like Successfully.!");

                            }
                        } else {
                            int totalCountLess = likeCount - 1;
                            latestPost.getLikeInfo().setLikeUserId("001");
                            latestPost.getLikeInfo().setLikeCount(totalCountLess);
                            tvLikeCount.setText(String.valueOf(totalCountLess));
                            ivLikeImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border));
                            addCustomToast("Unlike Successfully.!");
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
        sectionComment.setVisibility(View.VISIBLE);
        rvMySpace.setLayoutFrozen(true);
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
                    if(response.body().getResult() == 1){
                        ivAddToMySpace.setImageDrawable(ContextCompat.getDrawable(getContext() , R.drawable.ic_check_blue));
                        addCustomToast(response.body().getMessage());
                    }else {
                        ivAddToMySpace.setImageDrawable(ContextCompat.getDrawable(getContext() , R.drawable.ic_add_black));
                        addCustomToast(response.body().getMessage());
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<AddMySpaceModel> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void reportDailog(int postId, int loginUserId, Context context) {
        ReportDailogFragment fragment = new ReportDailogFragment(postId ,loginUserId);
        fragment.show(getChildFragmentManager(), "MyCustomDialog");
    }

    @Override
    public void onRefresh() {
       switch (position){
            case 0:
                latestPostViewModel.invalidateDataSource();
                swipeRefresh.setRefreshing(false);
                getAddMySpaceData(position);
                break;
            case 1:
                mySpancePostViewModel.invalidateDataSource();
                swipeRefresh.setRefreshing(false);
                getAddMySpaceData(position);
                break;

        }
    }
    public void addCustomToast(String message){
        MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(getContext()));
        int darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,  Objects.requireNonNull(getActivity()).findViewById(R.id.custom_toast_layout));
        AppCompatTextView tvMessage = layout.findViewById(R.id.tvToast);
        LinearLayoutCompat custom_toast_layout = layout.findViewById(R.id.custom_toast_layout);
        if(darkModeStatus == 1){
            custom_toast_layout.setBackgroundResource(R.color.white);
            tvMessage.setTextColor(ContextCompat.getColor(getContext() ,R.color.black));
        }else{
            custom_toast_layout.setBackgroundResource(R.color.black);
            tvMessage.setTextColor(ContextCompat.getColor(getContext() ,R.color.white));
        }
        tvMessage.setText(message);
        Toast toast = new Toast(getContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
