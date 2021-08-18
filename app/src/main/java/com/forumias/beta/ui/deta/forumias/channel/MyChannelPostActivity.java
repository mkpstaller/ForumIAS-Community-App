package com.forumias.beta.ui.deta.forumias.channel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.forumias.beta.adapter.MyChannelPager;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.FollowUnFollowModel;
import com.forumias.beta.ui.deta.forumias.channel.channel_paging.Private_channel_Subscribe;
import com.forumias.beta.ui.deta.forumias.channel.channel_post.ChannelPostAdapter;
import com.forumias.beta.ui.deta.forumias.channel.channel_post.ChannelPostViewModel;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyChannelPostActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
   /* @BindView(R.id.tvChannelFirstText)
    CircularTextView tvChannelFirstText;*/
    @BindView(R.id.webView)
    WebView webView;
    /*@BindView(R.id.ivChannelImage)
    ImageView ivChannelImage;*/
    /*@BindView(R.id.tvUnSubscribed)
    AppCompatTextView tvSubscribed;*/
    @BindView(R.id.llSubscribed)
    LinearLayoutCompat llSubscribe;
    @BindView(R.id.llUnSubscribed)
    LinearLayoutCompat llUnSubscribed;
    @BindView(R.id.tvUnSubscribed)
    AppCompatTextView tvUnSubscribed;
    @BindView(R.id.tvSubscriberCount)
    AppCompatTextView tvSubscriberCount;
    @BindView(R.id.tvPostCount)
    AppCompatTextView tvPostCount;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvChannelName)
    AppCompatTextView tvChannelName;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tvCreatePost)
    AppCompatTextView tvCreatePost;
    /*@BindView(R.id.createChannelPost_sheet)
    MaterialCardView createChannelPost_sheet;*/
    @BindView(R.id.codlChannelDetails)
    CoordinatorLayout codlChannelDetails;
    @BindView(R.id.collapsToollayout)
    CollapsingToolbarLayout collapsToollayout;
    @BindView(R.id.llToolbarSection)
    LinearLayoutCompat llToolbarSection;
    @BindView(R.id.ivBack)
    AppCompatImageView ivBack;
    @BindView(R.id.ivAppLogo)
    AppCompatImageView ivAppLogo;


    private int loginUserId, userId, tabPosition, tagId, status , darkModeStatus , subscribeCount;
   // BottomSheetBehavior sheetBehavior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_channel);
        ButterKnife.bind(this);
        MyPreferenceData myPreferenceData = new MyPreferenceData(this);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);

        setDarkModeView();

        setChannelData();
       // initBottomSheet();


    }

    private void setDarkModeView() {
        if(darkModeStatus == 1){
            codlChannelDetails.setBackgroundResource(R.color.darkmode_back_color);
            collapsToollayout.setBackgroundResource(R.color.darkmode_back_color);
            tvChannelName.setTextColor(ContextCompat.getColor(this,R.color.light_white));
            tvPostCount.setTextColor(ContextCompat.getColor(this,R.color.light_white));
            tvSubscriberCount.setTextColor(ContextCompat.getColor(this,R.color.light_white));
            llToolbarSection.setBackgroundColor(ContextCompat.getColor(this,R.color.darkmode_back_color));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back_key_white));
            ivAppLogo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo_white));
        }else{
            codlChannelDetails.setBackgroundResource(R.color.back_color);
            collapsToollayout.setBackgroundResource(R.color.back_color);
            tvChannelName.setTextColor(ContextCompat.getColor(this,R.color.black_light));
            tvPostCount.setTextColor(ContextCompat.getColor(this,R.color.black_light));
            tvSubscriberCount.setTextColor(ContextCompat.getColor(this,R.color.black_light));
            llToolbarSection.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back_key));
            ivAppLogo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo_black));
        }
    }

    private void setChannelData() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        // String title = bundle.getString(BaseUrl.TITLE);
        String slug = bundle.getString(BaseUrl.SLUG);
        userId = bundle.getInt(BaseUrl.USER_ID);
        channelDetails(slug);


    }

    @OnClick(R.id.ivBack)
    public void onClickBack(){
        finish();
    }

   /* @OnClick(R.id.ivClose)
    public void onClickCreatePostBack() {
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    @OnClick(R.id.tvCreatePost)
    public void onClickCreatePost() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

    }*/

    @OnClick(R.id.llSubscribed) // this click method use for send invite and subscribe
    public void onClickSubscribe() {
        switch (tabPosition){
            case 0:
                channelSubscribe();
                break;
            case 1:
            case 3:
                privateChannelSubscribe();
                break;
        }

    }


    @OnClick(R.id.llUnSubscribed)
    public void onClickUnSuscribed(){
       switch (tabPosition){
           case 0:
               channelSubscribe();
               break;
           case 1:
           case 3:
               privateChannelSubscribe();
               break;
       }
    }

    private void channelDetails(String slug) {

        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<ChannelDetailsInfo> call = apiInterface.getChannelDetailsInfo(slug, loginUserId);

        call.enqueue(new Callback<ChannelDetailsInfo>() {
            @Override
            public void onResponse(@NotNull Call<ChannelDetailsInfo> call, @NotNull Response<ChannelDetailsInfo> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus().equals(BaseUrl.SUCCESS)) {
                        setChannelDetails(response.body().getChannelDetailsInfo());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ChannelDetailsInfo> call, @NotNull Throwable t) {

            }
        });
    }

    private void setChannelDetails(ChannelDetailsInfo.ChannelInfo channelDetailsInfo) {

        channelPostView(channelDetailsInfo.getChannel_slug(), userId, channelDetailsInfo.getTitle());

        Log.e("user Data ====> " , userId +"         "+loginUserId);

        if (userId == loginUserId) {
            tabLayout.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            // initView();
        }

        tagId = channelDetailsInfo.getId();
        tabPosition = channelDetailsInfo.getChannel_type();

        tvChannelName.setText(channelDetailsInfo.getTitle());
        if(darkModeStatus == 1) {
            new Utility().showDarkMoeDescription(webView, channelDetailsInfo.getDescription());
        }else{
            new Utility().showChannelDescription(webView, channelDetailsInfo.getDescription());
        }
       /*\ if (!Utility.isNullOrEmpty(channelDetailsInfo.getChannel_img())) {
            tvChannelFirstText.setVisibility(View.GONE);
            ivChannelImage.setVisibility(View.VISIBLE);
            Glide.with(this).load(BaseUrl.PAGE_IMAGE_URL + channelDetailsInfo.getChannel_img()).into(ivChannelImage);
        } else {
            tvChannelFirstText.setVisibility(View.VISIBLE);
            ivChannelImage.setVisibility(View.GONE);
            tvChannelFirstText.setSolidColor(channelDetailsInfo.getColor_code());
            assert channelDetailsInfo.getTitle() != null;
            char firstChar = channelDetailsInfo.getTitle().charAt(0);
            tvChannelFirstText.setText(String.valueOf(firstChar));
        }*/
        tvPostCount.setText(String.valueOf(channelDetailsInfo.getChannel_posts_count()));


        Log.e("type====> " , String.valueOf(channelDetailsInfo.getChannel_type()));
        if(channelDetailsInfo.getChannelFollowInfo() != null) {
            String subscribeBy = channelDetailsInfo.getChannelFollowInfo().getFollow_by();
            String[] followUserCount = subscribeBy.split(",");
            subscribeCount = followUserCount.length;
            tvSubscriberCount.setText(String.valueOf(subscribeCount));
        }

        switch (channelDetailsInfo.getChannel_type()){
            case 2:
            case 4:
                llSubscribe.setVisibility(View.GONE);
                llUnSubscribed.setVisibility(View.GONE);
                break;
            case 0:
                Log.e("data ==> " , "Subcribed");
                if(channelDetailsInfo.getChannelFollowInfo() != null) {
                    String subscribeBy = channelDetailsInfo.getChannelFollowInfo().getFollow_by();
                    String[] followUserCount = subscribeBy.split(",");
                    subscribeCount = followUserCount.length;
                    List<String> followList = Arrays.asList(followUserCount);
                    if (followList.contains(String.valueOf(loginUserId))) {
                        llSubscribe.setVisibility(View.GONE);
                        llUnSubscribed.setVisibility(View.VISIBLE);
                        status = 0;
                    } else {
                        llSubscribe.setVisibility(View.VISIBLE);
                        llUnSubscribed.setVisibility(View.GONE);
                        status = 1;
                    }

                }
                break;
            case 1:
            case 3:

                if(channelDetailsInfo.getFollowRequest() != null){
                    llSubscribe.setVisibility(View.GONE);
                    llUnSubscribed.setVisibility(View.VISIBLE);
                    tvUnSubscribed.setVisibility(View.VISIBLE);
                    tvUnSubscribed.setText("Requested");
                    Log.e("data ==> " , "Request");
                    status = 2;
                }else {
                    llSubscribe.setVisibility(View.VISIBLE);
                    llUnSubscribed.setVisibility(View.GONE);
                    status  = 1;
                }
                break;
        }
    }


 /*   private void initBottomSheet() {
        sheetBehavior = BottomSheetBehavior.from(createChannelPost_sheet);

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }*/

    private void channelPostView(String slug, int userId, String title) {
        ChannelPostAdapter channelPostAdapter = new ChannelPostAdapter(this, title);
        ChannelPostViewModel viewModel = ViewModelProviders.of(this).get(ChannelPostViewModel.class);
        viewModel.getItemData(slug, progressBar, userId).observe(this, channelPostAdapter::submitList);
        recyclerView.setAdapter(channelPostAdapter);

    }

    private void initView() {
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        MyChannelPager adapter = new MyChannelPager(getSupportFragmentManager(), tabLayout.getTabCount());
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


    public void channelSubscribe() {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<FollowUnFollowModel> call = apiInterface.getFollowUnFollowing(String.valueOf(loginUserId),
                String.valueOf(tagId), String.valueOf(status), "3");
        call.enqueue(new Callback<FollowUnFollowModel>() {
            @Override
            public void onResponse(@NotNull Call<FollowUnFollowModel> call, @NotNull Response<FollowUnFollowModel> response) {
                assert response.body() != null;
                if (response.body().getStatus().equalsIgnoreCase(BaseUrl.SUCCESS)) {
                    if (response.body().getFlag() == 1) {
                        llSubscribe.setVisibility(View.GONE);
                        llUnSubscribed.setVisibility(View.VISIBLE);
                    } else {
                        llSubscribe.setVisibility(View.VISIBLE);
                        llUnSubscribed.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<FollowUnFollowModel> call, @NotNull Throwable t) {

            }
        });
    }



    public void privateChannelSubscribe() {

        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<Private_channel_Subscribe> call = apiInterface.getPrivaetChannelSubscribe(status , tagId , loginUserId);
        call.enqueue(new Callback<Private_channel_Subscribe>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<Private_channel_Subscribe> call, @NotNull Response<Private_channel_Subscribe> response) {

                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        if(response.body().getAct_type() == 1){
                            llSubscribe.setVisibility(View.GONE);
                            llUnSubscribed.setVisibility(View.VISIBLE);
                            tvUnSubscribed.setText("Requested");
                        }else{
                            llSubscribe.setVisibility(View.VISIBLE);
                            llUnSubscribed.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<Private_channel_Subscribe> call, @NotNull Throwable t) {

            }
        });
    }
}