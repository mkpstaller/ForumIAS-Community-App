package com.forumias.beta.ui.deta.forumias.page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.ui.deta.forumias.comment.CommentTabPager;
import com.forumias.beta.ui.deta.forumias.comment.ui.model.LikeUserModel;
import com.forumias.beta.ui.loginfragment.LoginAlertFragment;
import com.forumias.beta.utility.CircularTextView;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.ui.deta.forumias.CreateCommentOnPostActivity;
import com.forumias.beta.ui.deta.forumias.ReportDailogFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagePostCommentActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    MaterialToolbar toolbar;
    @BindView(R.id.tvPageImage)
    ImageView tvPageImage;
    @BindView(R.id.tvChannelFirstText)
    CircularTextView tvChannelFirstText;
    @BindView(R.id.tvGroupName)
    AppCompatTextView tvGroupName;
    @BindView(R.id.webView)
    WebView webView;
    /*@BindView(R.id.tvCommentCont)
    AppCompatTextView tvCommentCont;*/
    @BindView(R.id.tvViewCountSecond)
    AppCompatTextView tvViewCount;
    @BindView(R.id.tvLikeCount)
    AppCompatTextView tvLikeCount;
    @BindView(R.id.ivLikeImage)
    AppCompatImageView ivLikeImage;

    @BindView(R.id.llLikeInfoSection)
    LinearLayoutCompat llLikeInfoSection;
    @BindView(R.id.tvLikeCountNumber)
    AppCompatTextView tvLikeCountNumber;
    @BindView(R.id.ivOneLikeImage)
    ImageView ivOneLikeImage;
    @BindView(R.id.ivTwoLikeImage)
    ImageView ivTwoLikeImage;
    @BindView(R.id.ivThreeLikeImage)
    ImageView ivThreeLikeImage;
    @BindView(R.id.tvLikeUserName)
    AppCompatTextView tvLikeUserName;
  /* @BindView(R.id.ivMore)
   AppCompatImageView ivMore;*/
   @BindView(R.id.ivUserVerified)
   AppCompatImageView ivUserVerified;
   @BindView(R.id.tvTitle)
   AppCompatTextView tvTitle;
   @BindView(R.id.llLikeSection)
   LinearLayoutCompat llLikeSection;
   @BindView(R.id.llPageTitle)
   LinearLayoutCompat llPageTitle;
   @BindView(R.id.codlPageDetails)
    CoordinatorLayout codlPageDetails;
   @BindView(R.id.llPostDetailsWebView)
   LinearLayoutCompat llPostDetailsWebView;
   @BindView(R.id.llCommentSection)
   LinearLayoutCompat llCommentSection;
   @BindView(R.id.view)
   View view;
   @BindView(R.id.llPostTitleBack)
   LinearLayoutCompat llPostTitleBack;
   @BindView(R.id.ivReport)
   AppCompatImageView ivReport;
   @BindView(R.id.ivAddToMySpace)
   AppCompatImageView ivAddToMySpace;
   @BindView(R.id.ivPageNotification)
   AppCompatImageView ivPageNotification;
   @BindView(R.id.tvCommentPost)
   AppCompatTextView tvCommentPost;
   @BindView(R.id.tvTotalCommentCount)
   AppCompatTextView tvTotalCommentCount;
    @BindView(R.id.llToolbarSection)
    LinearLayoutCompat llToolbarSection;
    @BindView(R.id.ivBack)
    AppCompatImageView ivBack;
    @BindView(R.id.ivAppLogo)
    AppCompatImageView ivAppLogo;

    private String slug;

    private int postId,commentCount , likeCount , viewCount , loginUserId , darkModeStatus;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_post_comment);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        setInitView();
        initView();
        initTabView();

    }

    @SuppressLint("SetTextI18n")
    private void setInitView() {

        MyPreferenceData myPref = new MyPreferenceData(this);
        String pageName = myPref.getData(BaseUrl.PREF_COMM_GROUP_NAME);
         darkModeStatus= myPref.getIntegerData(BaseUrl.DARK_MODE);
        String title = myPref.getData(BaseUrl.PREF_COMM_TITLE);
        String colorCode = myPref.getData(BaseUrl.PREF_COMM_COLORS);
       String tagImage = myPref.getData(BaseUrl.PREF_COMM_TAG_IMAGE);
       String description = myPref.getData(BaseUrl.PREF_COMM_DESCRIPTION);
        postId=myPref.getIntegerData(BaseUrl.PREF_COMM_POST_ID);
        loginUserId=myPref.getIntegerData(BaseUrl.USER_ID);
        commentCount = myPref.getIntegerData(BaseUrl.PREF_COMM_COMMENT_COUNT);
        viewCount = myPref.getIntegerData(BaseUrl.PREF_COMM_VIEW_COUNT);
        likeCount = myPref.getIntegerData(BaseUrl.PREF_COMM_LIKE_COUNT);
        int myLikeCheck = myPref.getIntegerData(BaseUrl.PREF_COMM_MY_LIKE_CHECK);
        int isVerified = myPref.getIntegerData(BaseUrl.PREF_COMM_IS_VERIFIED);

        setDarkModeView();

        new Utility().showUserVerified(isVerified, ivUserVerified);

        if(myLikeCheck ==1){
            ivLikeImage.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_favorite_red));
        }else{
            ivLikeImage.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.ic_favorite_border));
        }
       /* if(commentCount != 0){
            tvCommentCont.setText(commentCount+" Comments");
        }*/
        tvTitle.setText(title);
        tvViewCount.setText(new Utility().prettyCount(viewCount));
        tvLikeCount.setText(String.valueOf(likeCount));
        tvGroupName.setText(pageName);
        tvTotalCommentCount.setText(String.valueOf(commentCount));
        if(!Utility.isNullOrEmpty(tagImage)){
            tvPageImage.setVisibility(View.VISIBLE);
            Glide.with(this).load(BaseUrl.PAGE_IMAGE_URL+tagImage).into(tvPageImage);
        }else{
            tvPageImage.setVisibility(View.GONE);
            tvChannelFirstText.setVisibility(View.VISIBLE);
            tvChannelFirstText.setStrokeWidth(1);
            tvChannelFirstText.setSolidColor(colorCode);
            assert pageName != null;
            char firstText = pageName.charAt(0);
            tvChannelFirstText.setText(String.valueOf(firstText));
        }

       if(darkModeStatus == 1) {
           new Utility().showDarkMoeDescription(webView, description);
       }else{
           new Utility().showDescription(webView, description);
       }

       if(loginUserId != 0){
           ivReport.setVisibility(View.VISIBLE);
       }else{
           ivReport.setVisibility(View.GONE);
       }

        getLikeUserList();

    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        //this.arEditor = findViewById(R.id.postEditor);
        MyPreferenceData myPref = new MyPreferenceData(this);
        slug= myPref.getData(BaseUrl.PREF_SLUG);
        getPostDetails();


    }

    private void setDarkModeView() {
        if(darkModeStatus == 1){
            codlPageDetails.setBackgroundResource(R.color.darkmode_back_color);
            llPageTitle.setBackgroundResource(R.color.darkmode_back_color);
            llCommentSection.setBackgroundResource(R.drawable.dark_post_border);
            tabLayout.setBackgroundResource(R.color.low_black);
            llPostDetailsWebView.setBackgroundResource(R.color.darkmode_back_color);
            llPostTitleBack.setBackgroundResource(R.color.gray_dark);
            tvTitle.setTextColor(ContextCompat.getColor(this , R.color.light_white));
            tvGroupName.setTextColor(ContextCompat.getColor(this , R.color.light_white));
            tvLikeUserName.setTextColor(ContextCompat.getColor(this , R.color.low_gray));
            tvLikeCountNumber.setTextColor(ContextCompat.getColor(this , R.color.low_gray));
            view.setBackgroundResource(R.color.border_color);
            llToolbarSection.setBackgroundColor(ContextCompat.getColor(this,R.color.darkmode_back_color));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back_key_white));
            ivAppLogo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo_white));
        }else{
            codlPageDetails.setBackgroundResource(R.color.back_color);
            llPostDetailsWebView.setBackgroundResource(R.color.white);
            llCommentSection.setBackgroundResource(R.color.back_color);
            llPostTitleBack.setBackgroundResource(R.color.white);
            tabLayout.setBackgroundResource(R.color.back_color);
            llPageTitle.setBackgroundResource(R.color.back_color);
            tvTitle.setTextColor(ContextCompat.getColor(this , R.color.black_light));
            tvGroupName.setTextColor(ContextCompat.getColor(this , R.color.black_light));
            tvLikeUserName.setTextColor(ContextCompat.getColor(this , R.color.gray_dark));
            tvLikeCountNumber.setTextColor(ContextCompat.getColor(this , R.color.black_light));
            view.setBackgroundResource(R.color.low_gray);
            llToolbarSection.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back_key));
            ivAppLogo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo_black));
        }
    }

    @OnClick(R.id.ivBack)
    public void OnClickBack(){
        finish();
    }

    @OnClick(R.id.ivPageNotification)
    public void onClickPageNotification(){
        Toast.makeText(this , "Under Development..!" , Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.ivAddToMySpace)
    public void onClickPageAddToMySpace(){
        Toast.makeText(this , "Under Development..!" , Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.ivReport)
     public void onClickMore() {
         PopupMenu popup = new PopupMenu(this, ivReport);
         popup.getMenuInflater().inflate(R.menu.more_menu, popup.getMenu());
         popup.setOnMenuItemClickListener(item -> {
             if (item.getItemId() == R.id.menu_report) {
                 Log.e("Report: ", "Click Ok report");
                 ReportDailogFragment fragment = new ReportDailogFragment(postId ,loginUserId);
                 fragment.show(getSupportFragmentManager(), "MyCustomDialog");
             }
             return true;
         });
         popup.show();
     }

     @OnClick(R.id.tvCommentPost)
     public void OnClickCommentPost(){
         if(loginUserId != 0) {
             Intent intent = new Intent(this, CreateCommentOnPostActivity.class);
             intent.putExtra(BaseUrl.POST_ID, postId);
             intent.putExtra(BaseUrl.CALL_CLASS, 2);
             startActivity(intent);
             finish();
         }else{
             //  new MyUtility().showLoginAlert(this);
             LoginAlertFragment alert  = new LoginAlertFragment();
             alert.show(getSupportFragmentManager() ,"MyAlertLogin");
         }
     }

   /* @OnClick(R.id.fabCommentButton)
    public void onClickFabCloseOpen() {

        if(loginUserId != 0) {
            Intent intent = new Intent(this, CreateCommentOnPostActivity.class);
            intent.putExtra(BaseUrl.POST_ID, postId);
            intent.putExtra(BaseUrl.CALL_CLASS, 2);
            startActivity(intent);
            finish();
        }else{
          //  new MyUtility().showLoginAlert(this);
            LoginAlertFragment alert  = new LoginAlertFragment();
            alert.show(getSupportFragmentManager() ,"MyAlertLogin");
        }

    }*/

    @OnClick(R.id.llLikeSection)
    public void onClickLikeUnLike(){
        if(loginUserId != 0){

        }else{
            LoginAlertFragment alert  = new LoginAlertFragment();
            alert.show(getSupportFragmentManager() ,"MyAlertLogin");
               // new MyUtility().showLoginAlert(this);
        }
    }


    private void initTabView(){

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
         CommentTabPager commentTabPager = new CommentTabPager(getSupportFragmentManager()
                 ,tabLayout.getTabCount(),postId ,0 , commentCount);
        viewPager.setAdapter(commentTabPager);
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


    private void getPostDetails() {

    }

    private void getLikeUserList() {
        Log.e("Ok like post==> ", String.valueOf(postId));
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<LikeUserModel> call = apiInterface.getLikeUser(String.valueOf(postId));
        call.enqueue(new Callback<LikeUserModel>() {
            @Override
            public void onResponse(@NotNull Call<LikeUserModel> call, @NotNull Response<LikeUserModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getLikeUserLists().size() > 0) {
                        showLikeUser(response.body().getLikeUserLists());
                       // likeUser.addAll(response.body().getLikeUserLists());
                    }else{
                        llLikeInfoSection.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<LikeUserModel> call, @NotNull Throwable t) {

            }
        });
    }


    @SuppressLint("SetTextI18n")
    private void showLikeUser(List<LikeUserModel.LikeUserList> likeUserLists) {

        if (likeUserLists.size() >= 3) {
            Glide.with(this).load(likeUserLists.get(0).getImg()).into(ivOneLikeImage);
            Glide.with(this).load(likeUserLists.get(1).getImg()).into(ivTwoLikeImage);
            Glide.with(this).load(likeUserLists.get(2).getImg()).into(ivThreeLikeImage);
            tvLikeUserName.setText(likeUserLists.get(0).getName() + ", " +
                    likeUserLists.get(1).getName() + ", " + likeUserLists.get(2).getName());
        }
        if (likeUserLists.size() == 2) {
            Glide.with(this).load(likeUserLists.get(0).getImg()).into(ivOneLikeImage);
            Glide.with(this).load(likeUserLists.get(1).getImg()).into(ivTwoLikeImage);
            ivThreeLikeImage.setVisibility(View.GONE);
            tvLikeUserName.setText(likeUserLists.get(0).getName() + ", " +
                    likeUserLists.get(1).getName());
        }
        if (likeUserLists.size() == 1) {
            Glide.with(this).load(likeUserLists.get(0).getImg()).into(ivOneLikeImage);
            ivTwoLikeImage.setVisibility(View.GONE);
            ivThreeLikeImage.setVisibility(View.GONE);
            tvLikeUserName.setText(likeUserLists.get(0).getName());
        }


        int otherLikeCount;
        String totalCount;

        llLikeInfoSection.setVisibility(View.VISIBLE);
        if (likeUserLists.size() > 2) {
            otherLikeCount = likeUserLists.size() - 2;
            totalCount = "<b> <font color='blue'>" + otherLikeCount + " Other like</font></b>";
        } else {
            totalCount = String.valueOf(likeUserLists.size());
        }
        tvLikeCountNumber.setText(Html.fromHtml(totalCount));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
