package com.forumias.beta.ui.deta.forumias.comment.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.chinalwb.are.AREditor;
import com.forumias.beta.adapter.LikeUserAdapter;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.myinterface.pollClickEvent;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.FollowUnFollowModel;
import com.forumias.beta.ui.deta.forumias.comman_model.MyPostDetailsModel;
import com.forumias.beta.ui.deta.forumias.comment.adapter.PollAdapter;
import com.forumias.beta.ui.deta.forumias.comment.ui.model.LikeUserModel;
import com.forumias.beta.ui.deta.forumias.comment.ui.model.PollModel;
import com.forumias.beta.ui.loginfragment.LoginAlertFragment;
import com.forumias.beta.utility.DateFormatUtils;
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.ui.deta.forumias.CreateCommentOnPostActivity;
import com.forumias.beta.ui.deta.forumias.ReportDailogFragment;
import com.forumias.beta.ui.deta.forumias.comment.CommentTabPager;
import com.forumias.beta.ui.deta.forumias.comment.ui.model.CheckFollowModel;
import com.forumias.beta.ui.deta.forumias.home.model.AddMySpaceModel;
import com.forumias.beta.ui.deta.forumias.page.MyPagesPostActivity;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.UserProfileAndPostActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommentOnPostDetailsActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener , pollClickEvent {

    @BindView(R.id.tvName)
    AppCompatTextView tvName;
    @BindView(R.id.ivPostImage)
    AppCompatImageView ivPostImage;
    @BindView(R.id.rlCathySection)
    RelativeLayout rlCathySection;
    @BindView(R.id.tvCatchyName)
    AppCompatTextView tvCatchyName;
    @BindView(R.id.ivPostUserImage)
    ImageView ivPostUserImage;
    @BindView(R.id.tvLikeCount)
    AppCompatTextView tvLikeCount;
    @BindView(R.id.ivOneLikeImage)
    ImageView ivOneLikeImage;
    @BindView(R.id.ivTwoLikeImage)
    ImageView ivTwoLikeImage;
    @BindView(R.id.ivThreeLikeImage)
    ImageView ivThreeLikeImage;
    @BindView(R.id.tvLikeUserName)
    AppCompatTextView tvLikeUserName;
    @BindView(R.id.tvLike)
    AppCompatTextView tvLike;
    @BindView(R.id.tvPostTitle)
    AppCompatTextView tvPostTitle;
    @BindView(R.id.llLikeInfoSection)
    LinearLayoutCompat llLikeInfoSection;
    @BindView(R.id.toolbar)
    MaterialToolbar toolbar;
    @BindView(R.id.llFollow)
    LinearLayoutCompat llFollow;
    @BindView(R.id.llFollowing)
    LinearLayoutCompat llFollowing;
    @BindView(R.id.tvDateTime)
    AppCompatTextView tvDateTime;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tvTotalCommentCount)
    AppCompatTextView tvCommentCont;
    @BindView(R.id.llPostImageSection)
    LinearLayoutCompat llPostImageSection;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.webViewMore)
    WebView webViewMore;
    @BindView(R.id.tvMore)
    AppCompatImageView tvMore;
    @BindView(R.id.tvUserVerifiedInfo)
    AppCompatImageView tvUserVerifiedInfo;
    @BindView(R.id.tvViewSeeCount)
    AppCompatTextView tvViewSeeCount;
    @BindView(R.id.bottom_sheet)
    LinearLayoutCompat bottom_sheet;
    @BindView(R.id.fabCommentButton)
    FloatingActionButton fabCommentButton;
    @BindView(R.id.fabCommentSend)
    FloatingActionButton fabCommentSend;
    @BindView(R.id.ivLikeImage)
    AppCompatImageView ivLikeImage;
    @BindView(R.id.llLikeSection)
    LinearLayoutCompat llLikeSection;
    @BindView(R.id.tvNameOnComment)
    AppCompatTextView tvNameOnComment;
    @BindView(R.id.llUserDetailsSection)
    RelativeLayout llUserDetailsSection;
    @BindView(R.id.llPageDetailsSection)
    LinearLayoutCompat llPageDetailsSection;
    @BindView(R.id.tvPageImage)
    ImageView ivPageImage;
    @BindView(R.id.tvGroupName)
    AppCompatTextView tvGroupName;
    @BindView(R.id.llPostBackColor)
    LinearLayoutCompat llPostBackColor;
    @BindView(R.id.homeCommentPostSection)
    CoordinatorLayout homeCommentPostSection;
    @BindView(R.id.llPostSection)
    LinearLayoutCompat llPostSection;
    @BindView(R.id.llCommentSectionTab)
    LinearLayoutCompat llCommentSectionTab;
    @BindView(R.id.tvFollow)
    AppCompatTextView tvFollow;
    @BindView(R.id.tvShare)
    AppCompatTextView tvShare;
    @BindView(R.id.tvCommentPost)
    AppCompatTextView tvCommentPost;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.rlMoreTitle)
    RelativeLayout rlMoreTitle;
    @BindView(R.id.ivMoreArrow)
    AppCompatImageView ivMoreArrow;
    @BindView(R.id.rlMorePost)
    RelativeLayout rlMorePost;
    @BindView(R.id.ivPostMoreArrow)
    AppCompatImageView ivMoreArrowMore;
    @BindView(R.id.ivFollowIcon)
    AppCompatImageView ivFollowIcon;
    @BindView(R.id.ivPostMoreArrowHide)
    AppCompatImageView ivPostMoreArrowHide;
    @BindView(R.id.llMoreData)
    LinearLayoutCompat llMoreData;
    @BindView(R.id.llAddToMySpace)
    LinearLayoutCompat llAddToMySpace;
    @BindView(R.id.llToolbarSection)
    LinearLayoutCompat llToolbarSection;
    @BindView(R.id.ivBack)
    AppCompatImageView ivBack;
    @BindView(R.id.ivAppLogo)
    AppCompatImageView ivAppLogo;
    @BindView(R.id.pollRecyclerView)
    RecyclerView pollRecyclerView;
    @BindView(R.id.tvUnderDev)
    AppCompatTextView tvUnderDev;
   /* @BindView(R.id.llPollLayout)
    LinearLayoutCompat llPollLayout;
    @BindView(R.id.tvOne)
    AppCompatTextView tvOne;
    @BindView(R.id.tvTwo)
    AppCompatTextView tvTwo;
    @BindView(R.id.tvThree)
    AppCompatTextView tvThree;
    @BindView(R.id.tvFour)
    AppCompatTextView tvFour;
    @BindView(R.id.tvPerOne)
    AppCompatTextView tvPerOne;
    @BindView(R.id.tvPerTwo)
    AppCompatTextView tvPerTwo;
    @BindView(R.id.tvPerThree)
    AppCompatTextView tvPerThree;
    @BindView(R.id.tvPerFour)
    AppCompatTextView tvPerFour;
    @BindView(R.id.oneSeekBar)
    AppCompatSeekBar oneSeekBar;
    @BindView(R.id.twoSeekBar)
    AppCompatSeekBar twoSeekBar;
    @BindView(R.id.threeSeekBar)
    AppCompatSeekBar threeSeekBar;
    @BindView(R.id.fourSeekBar)
    AppCompatSeekBar fourSeekBar;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.radioButton1)
    AppCompatRadioButton radioButton1;
    @BindView(R.id.radioButton2)
    AppCompatRadioButton radioButton2;
    @BindView(R.id.radioButton3)
    AppCompatRadioButton radioButton3;
    @BindView(R.id.radioButton4)
    AppCompatRadioButton radioButton4;
    @BindView(R.id.pollSectionOne)
    RelativeLayout pollSectionOne;
    @BindView(R.id.pollSectionTwo)
    RelativeLayout pollSectionTwo;
    @BindView(R.id.pollSectionThree)
    RelativeLayout pollSectionThree;
    @BindView(R.id.pollSectionFour)
    RelativeLayout pollSectionFour;*/



    private boolean titleClickStatus= true;
    private boolean postClickStatus= true;
    private int likeCount;
    String showUserName;
    PollAdapter pollAdapter;
    List<PollModel.PollInfo> pollInfoList;


    BottomSheetBehavior sheetBehavior;
    boolean readMoreStatus = true;

    List<LikeUserModel.LikeUserList> likeUser;
    private String slug;
    private String postId;
    private AREditor arEditor;
    private int loginUserId,id , callIntent , darkModeStatus , commentCount;
    private String name;

    private int[] tabIcons = {
            R.drawable.ic_access_time,
            R.drawable.ic_most_useful,
    };
    Context context;
    private  int likeOnDetailsPage=1;
    private int showannouncement = 0;

    double count1=1,count2=1,count3=1,count4 = 1;
    boolean flag1 = true, flag2 = true, flag3= true, flag4 = true;
    boolean userMatch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_on_post);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        context = this;
        MyPreferenceData myPreferenceData = new MyPreferenceData(this);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        name = myPreferenceData.getData(BaseUrl.NAME);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);

   if(darkModeStatus == 1) {
            homeCommentPostSection.setBackgroundResource(R.color.darkmode_back_color);
            llCommentSectionTab.setBackgroundResource(R.color.darkmode_back_color);
            llPostBackColor.setBackgroundResource(R.drawable.post_details_shape);
            tvCommentPost.setBackgroundResource(R.drawable.comment_edit_shape_dark);
            tvCommentPost.setTextColor(ContextCompat.getColor(context,R.color.low_gray));
            llPostSection.setBackgroundResource(R.color.darkmode_back_color);
            webView.setBackgroundResource(R.color.darkmode_back_color);
            tvPostTitle.setTextColor(ContextCompat.getColor(context, R.color.light_white));
            tvLikeCount.setTextColor(ContextCompat.getColor(context, R.color.low_gray));
            tvName.setTextColor(ContextCompat.getColor(context, R.color.light_white));
            tvLikeUserName.setTextColor(ContextCompat.getColor(context, R.color.light_white));
            tabLayout.setBackgroundResource(R.color.darkmode_back_color);
            tvLike.setTextColor(ContextCompat.getColor(context,R.color.low_gray));
            tvShare.setTextColor(ContextCompat.getColor(context,R.color.low_gray));
            tvFollow.setTextColor(ContextCompat.getColor(context,R.color.low_gray));
            tvViewSeeCount.setTextColor(ContextCompat.getColor(context,R.color.low_gray));
            view.setBackgroundColor(ContextCompat.getColor(context,R.color.gray_dark));
            llToolbarSection.setBackgroundColor(ContextCompat.getColor(context,R.color.darkmode_back_color));
            ivBack.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_back_key_white));
            ivAppLogo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.forumias_logo_white));
        }else{
            homeCommentPostSection.setBackgroundResource(R.color.back_color);
            llCommentSectionTab.setBackgroundResource(R.color.back_color);
            llPostBackColor.setBackgroundResource(R.color.white);
            llPostSection.setBackgroundResource(R.color.back_color);
            tvCommentPost.setBackgroundResource(R.drawable.commnet_edit_shape);
            tvCommentPost.setTextColor(ContextCompat.getColor(context,R.color.blue));
            webView.setBackgroundResource(R.color.white);
            tvPostTitle.setTextColor(ContextCompat.getColor(context, R.color.black));
            tvLikeCount.setTextColor(ContextCompat.getColor(context, R.color.black));
            tvName.setTextColor(ContextCompat.getColor(context, R.color.black));
            tvLikeUserName.setTextColor(ContextCompat.getColor(context, R.color.black));
            tabLayout.setBackgroundResource(R.color.back_color);
            llToolbarSection.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
            ivBack.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_back_key));
            ivAppLogo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.logo_black));
        }



        likeUser = new ArrayList<>();
        pollInfoList = new ArrayList<>();



        initCommentDialogBottomSheet();
        initView();


    }

    private void getPollData(int type) {
        BetaApiClient apiClient = new BetaApiClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<PollModel> call = apiInterface.getPoll(Integer.parseInt(postId),loginUserId);
        call.enqueue(new Callback<PollModel>() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onResponse(Call<PollModel> call, Response<PollModel> response) {
                if(response.isSuccessful()){
                    if(response.body().status.contains(BaseUrl.SUCCESS)){
                        if(response.body().poll_info.size() > 0){
                            pollRecyclerView.setVisibility(View.VISIBLE);
                            pollInfoList.addAll(response.body().poll_info);
                            pollInfoList = getModel(false);

                            pollAdapter = new PollAdapter(CommentOnPostDetailsActivity.this , pollInfoList , response.body().total_vote , CommentOnPostDetailsActivity.this);
                            pollRecyclerView.setAdapter(pollAdapter);
                            tvUnderDev.setVisibility(View.GONE);

                        }
                    }
                    /*if(response.body().status.equals(BaseUrl.SUCCESS)){
                        Log.e("poll Data ===========> ",response.body().toString());
                        if(response.body().poll_info.size()==2){
                            tvOne.setText(response.body().poll_info.get(0).name);
                            tvTwo.setText(response.body().poll_info.get(1).name);
                            pollSectionThree.setVisibility(View.GONE);
                            pollSectionFour.setVisibility(View.GONE);
                        }else{
                            tvOne.setText(response.body().poll_info.get(0).name);
                            tvTwo.setText(response.body().poll_info.get(1).name);
                            tvThree.setText(response.body().poll_info.get(2).name);
                            tvFour.setText(response.body().poll_info.get(3).name);
                        }

                        if(response.body().selected_opt != 0){
                            Log.e("Select Opt=11===> " ,response.body().selected_opt+"");
                            getPercent(response.body().poll_info , response.body().total_vote);
                        }else{
                            oneSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_border));
                            oneSeekBar.setProgress(100);
                            twoSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_border));
                            twoSeekBar.setProgress(100);
                            threeSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_border));
                            threeSeekBar.setProgress(100);
                            fourSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_border));
                            fourSeekBar.setProgress(100);
                           Log.e("Select Opt====> " ,response.body().selected_opt+"");
                        }

                        tvOne.setOnClickListener(view1 -> {
                            if(flag1){
                                count1++;
                                count2 = 1;
                                count3 = 1;
                                count4 = 1;
                                flag1 = false;
                                flag2 = true;
                                flag3 = true;
                                flag4 = true;

                                List<String> items = Arrays.asList(response.body().poll_info.get(0).voted_by.split("\\s*,\\s*"));
                                int totalPerOne  = (items.size()*100)/response.body().total_vote;
                                tvPerOne.setText(totalPerOne+"%");
                                oneSeekBar.setProgress(totalPerOne);
                            }
                        });
                        tvTwo.setOnClickListener(view1 -> {
                            if(flag2){
                                count1 =1;
                                count2 ++;
                                count3 = 1;
                                count4 = 1;
                                flag1 = true;
                                flag2 = false;
                                flag3 = true;
                                flag4 = true;

                                List<String> items1 = Arrays.asList(response.body().poll_info.get(1).voted_by.split("\\s*,\\s*"));
                                int totalPerOne  = (items1.size()*100)/response.body().total_vote;
                                tvPerTwo.setText(totalPerOne+"%");
                                twoSeekBar.setProgress(totalPerOne);
                            }
                        });
                        tvThree.setOnClickListener(view1 -> {
                            if(flag3){
                                count1 = 1;
                                count2 = 1;
                                count3++;
                                count4 = 1;
                                flag1 = true;
                                flag2 = true;
                                flag3 = false;
                                flag4 = true;

                                List<String> items3 = Arrays.asList(response.body().poll_info.get(2).voted_by.split("\\s*,\\s*"));
                                int totalPerOne  = (items3.size()*100)/response.body().total_vote;
                                tvPerThree.setText(totalPerOne+"%");
                                threeSeekBar.setProgress(totalPerOne);
                            }
                        });
                        tvFour.setOnClickListener(view1 -> {
                            if(flag4){
                                count1 = 1;
                                count2 = 1;
                                count3 = 1;
                                count4++;
                                flag1 = true;
                                flag2 = true;
                                flag3 = true;
                                flag4 = false;

                                List<String> items4 = Arrays.asList(response.body().poll_info.get(3).voted_by.split("\\s*,\\s*"));
                                int totalPerOne  = (items4.size()+1*100)/response.body().total_vote+1;
                                tvPerFour.setText(totalPerOne+"%");
                                fourSeekBar.setProgress(totalPerOne);
                            }
                        });

                    }*/
                }
            }

            @Override
            public void onFailure(Call<PollModel> call, Throwable t) {

            }
        });
    }



    private ArrayList<PollModel.PollInfo> getModel(boolean isSelect){
        ArrayList<PollModel.PollInfo> list = new ArrayList<>();
        for(int i = 0; i < pollInfoList.size(); i++){

            PollModel.PollInfo model = new PollModel.PollInfo();
            model.isSelectAt = isSelect;
            //model.setAnimal(animallist[i]);
            list.add(model);
        }
        return list;
    }

   /* @SuppressLint("SetTextI18n")
    private void getPercent(List<PollModel.PollInfo> poll_info, int total_vote) {
        List<String> items = Arrays.asList(poll_info.get(0).voted_by.split("\\s*,\\s*"));
        Log.e("two size ==1=> ", String.valueOf(items.size()));

        int totalPerOne  = (items.size()*100)/total_vote;
        tvPerOne.setText(totalPerOne+"%");

        if(poll_info.size() == 2) {
            if (items.contains(String.valueOf(loginUserId))) {
                if (poll_info.get(0).is_correct == 1) {
                    oneSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_success));
                    oneSeekBar.setProgress(totalPerOne);
                    radioButton1.setChecked(true);
                    radioButton1.setHighlightColor(Color.GREEN);
                } else {
                    oneSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_fail));
                    oneSeekBar.setProgress(totalPerOne);
                    radioButton1.setChecked(true);
                    radioButton1.setHighlightColor(Color.RED);
                }
            } else {
                if (poll_info.get(0).is_correct == 1) {
                    oneSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_success));
                    oneSeekBar.setProgress(totalPerOne);
                    radioButton1.setChecked(true);
                    radioButton1.setHighlightColor(Color.GREEN);
                } else {
                    oneSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb));
                    oneSeekBar.setProgress(totalPerOne);
                }

            }
            List<String> items2 = Arrays.asList(poll_info.get(1).voted_by.split("\\s*,\\s*"));
            int totalPerTwo = (items2.size() * 100) / total_vote;
            tvPerTwo.setText(totalPerTwo + "%");

            if (items2.contains(String.valueOf(loginUserId))) {
                if (poll_info.get(1).is_correct == 1) {
                    twoSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_success));
                    twoSeekBar.setProgress(totalPerTwo);
                } else {
                    twoSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_fail));
                    twoSeekBar.setProgress(totalPerTwo);
                }
            } else {
                if (poll_info.get(1).is_correct == 1) {
                    twoSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_success));
                    twoSeekBar.setProgress(totalPerTwo);
                } else {
                    twoSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb));
                    twoSeekBar.setProgress(totalPerTwo);
                }
            }


        }else{

            if (items.contains(String.valueOf(loginUserId))) {
                if (poll_info.get(0).is_correct == 1) {
                    oneSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_success));
                    oneSeekBar.setProgress(totalPerOne);
                    radioButton1.setChecked(true);
                    radioButton1.setHighlightColor(Color.GREEN);
                } else {
                    oneSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_fail));
                    oneSeekBar.setProgress(totalPerOne);
                    radioButton1.setChecked(true);
                    radioButton1.setHighlightColor(Color.RED);
                }
            } else {
                if (poll_info.get(0).is_correct == 1) {
                    oneSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_success));
                    oneSeekBar.setProgress(totalPerOne);
                    radioButton1.setChecked(true);
                    radioButton1.setHighlightColor(Color.GREEN);
                } else {
                    oneSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb));
                    oneSeekBar.setProgress(totalPerOne);
                }

            }
            List<String> items2 = Arrays.asList(poll_info.get(1).voted_by.split("\\s*,\\s*"));
            int totalPerTwo = (items2.size() * 100) / total_vote;
            tvPerTwo.setText(totalPerTwo + "%");

            if (items2.contains(String.valueOf(loginUserId))) {
                if (poll_info.get(1).is_correct == 1) {
                    twoSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_success));
                    twoSeekBar.setProgress(totalPerTwo);
                } else {
                    twoSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_fail));
                    twoSeekBar.setProgress(totalPerTwo);
                }
            } else {
                if (poll_info.get(1).is_correct == 1) {
                    twoSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_success));
                    twoSeekBar.setProgress(totalPerTwo);
                } else {
                    twoSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb));
                    twoSeekBar.setProgress(totalPerTwo);
                }
            }
        List<String> items3 = Arrays.asList(poll_info.get(2).voted_by.split("\\s*,\\s*"));
        int totalPerThree  = (items3.size()*100)/total_vote;
        tvPerThree.setText(totalPerThree+"%");
            if(items3.contains(String.valueOf(loginUserId))){
                if(poll_info.get(2).is_correct == 1){
                    threeSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_success));
                    threeSeekBar.setProgress(totalPerThree);
                }else{
                    threeSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_fail));
                    threeSeekBar.setProgress(totalPerThree);
                }
            }else{
                if(poll_info.get(2).is_correct == 1){
                    threeSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_success));
                    threeSeekBar.setProgress(totalPerThree);
                }else{
                    threeSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb));
                    threeSeekBar.setProgress(totalPerThree);
                }
            }



      //  new  Utility().setProgess(items3 , poll_info.get(3).is_correct , this , loginUserId , threeSeekBar);

        List<String> items4 = Arrays.asList(poll_info.get(3).voted_by.split("\\s*,\\s*"));
        int totalPerFour  = (items4.size()*100)/total_vote;
        tvPerFour.setText(totalPerFour+"%");

          if(items4.contains(String.valueOf(loginUserId))){
                // userMatch = true;
                if(poll_info.get(3).is_correct == 1){
                    fourSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_success));
                    fourSeekBar.setProgress(totalPerFour);
                }else{
                    fourSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_fail));
                    fourSeekBar.setProgress(totalPerFour);
                }
            }else{
              if(poll_info.get(3).is_correct == 1){
                  fourSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb_success));
                  fourSeekBar.setProgress(totalPerFour);
              }else{
                  fourSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_thumb));
                  fourSeekBar.setProgress(totalPerFour);
              }
            }

}
    }*/

    @OnClick(R.id.ivBack)
    public void onClickBack(){
        finish();
    }

    private void initCommentDialogBottomSheet() {
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        if(loginUserId != 0) {
                            fabCommentButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_close_white));
                            fabCommentSend.setVisibility(View.GONE);
                        }else{
                           // new MyUtility().showLoginAlert(context);
                            LoginAlertFragment alert  = new LoginAlertFragment();
                            alert.show(getSupportFragmentManager() ,"MyAlertLogin");
                        }
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        fabCommentButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_comment_white));
                        fabCommentSend.setVisibility(View.GONE);
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

    }

    @OnClick(R.id.llAddToMySpace)
    public void onClickAddToMySpace(){
        if(loginUserId != 0) {
            MyUtility myUtility = new MyUtility();
            myUtility.addAddSpaceMusic(this);
            addToMySpace();
        }else{
            new MyUtility().showLoginAlert(context);
        }
    }

    @OnClick(R.id.llShareSection)
    public void onClickShareLink(){
        String sendData = BaseUrl.SHARE_BASE_URL+slug;
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_TEXT, sendData);
        share.setType("text/plain");
        context.startActivity(Intent.createChooser(share,"Share With."));
    }

    @OnClick(R.id.llFollow)
    public void onClickFollow(){
        followAndUnFollowMethod(1);
    }

    @OnClick(R.id.llFollowing)
    public void onClickFollowing(){
        followAndUnFollowMethod(0);
    }

    @OnClick(R.id.tvCommentPost)
    public void onClickFabComment() {
        if(loginUserId != 0){

            Intent intent = new Intent(this , CreateCommentOnPostActivity.class);
            try {
                intent.putExtra(BaseUrl.POST_ID , Integer.parseInt(postId));
                intent.putExtra(BaseUrl.COMMENT_ID , "0");
                intent.putExtra(BaseUrl.CALL_CLASS , 1);
            }catch (Exception e){e.printStackTrace();}
            startActivity(intent);
           finish();
        }else{
            LoginAlertFragment alert  = new LoginAlertFragment();
            alert.show(getSupportFragmentManager() ,"MyAlertLogin");
        }
    }

    @OnClick(R.id.rlMoreTitle)
    public void onClickMoreTitle(){
        if(titleClickStatus) {
            int lineCount = tvPostTitle.getLineCount();
            tvPostTitle.setMaxLines(lineCount);
            titleClickStatus = false;
            ivMoreArrow.setRotation(180);
            setMoreDayNightArrow();

        }else{
            tvPostTitle.setMaxLines(2);
            titleClickStatus = true;
            ivMoreArrow.setRotation(0);
            setMoreDayNight();
        }
    }

    @OnClick(R.id.llPostMoreArrowHide)
    public void onClickPostMore(){

        webView.setVisibility(View.VISIBLE);
        rlMorePost.setVisibility(View.VISIBLE);
        llMoreData.setVisibility(View.GONE);

    }

    @OnClick(R.id.llPostMoreArrow)
    public void onClickPostMoreHide(){
        Log.e("data click==>  " , "okokokokok");

        webView.setVisibility(View.GONE);
        rlMorePost.setVisibility(View.GONE);
        llMoreData.setVisibility(View.VISIBLE);
    }


    private void setMoreDayNight() {
        if(darkModeStatus == 1) {
            rlMoreTitle.setBackgroundResource(R.drawable.shadow_black);
            ivMoreArrow.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.down_arrow_white));
        }else{
            rlMoreTitle.setBackgroundResource(R.drawable.shadow_white);
            ivMoreArrow.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.down_arrow_black));
        }
    }
    private void setMoreDayNightArrow() {
        if(darkModeStatus == 1) {
            rlMoreTitle.setBackgroundResource(R.drawable.shadow_blank);
            ivMoreArrow.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.down_arrow_white));
        }else{
            rlMoreTitle.setBackgroundResource(R.drawable.shadow_blank);
            ivMoreArrow.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.down_arrow_black));
        }
    }

    private void setMoreDayNightPost(WebView webView, String description) {
        if(darkModeStatus == 1) {
            new Utility().showDarkMoeDescription(webView,description);
            rlMorePost.setBackgroundResource(R.drawable.shadow_black);
            ivMoreArrowMore.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.down_arrow_white));
        }else{
            new Utility().showDescription(webView, description);
            rlMorePost.setBackgroundResource(R.drawable.shadow_white);
            ivMoreArrowMore.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.down_arrow_black));
        }
    }

    @OnClick(R.id.tvMore)
    public void onClickMore() {
        PopupMenu popup = new PopupMenu(this, tvMore);
        popup.getMenuInflater().inflate(R.menu.more_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_report) {
                Log.e("Report: ", "Click Ok report");
                ReportDailogFragment fragment = new ReportDailogFragment(Integer.parseInt(postId) ,loginUserId);
                fragment.show(getSupportFragmentManager(), "MyCustomDialog");
            }
            return true;
        });
        popup.show();
    }

    @OnClick(R.id.llLikeSection)
    public void onClickLike() {
        if(loginUserId != 0) {
            MyUtility myUtility = new MyUtility();
            myUtility.addLikeMusic(this);
            myUtility.clickLineUnLike(this, Integer.parseInt(postId), loginUserId, likeCount, ivLikeImage, tvLike , likeOnDetailsPage);
        }else{
          //  new MyUtility().showLoginAlert(context);
            LoginAlertFragment alert  = new LoginAlertFragment();
            alert.show(getSupportFragmentManager() ,"MyAlertLogin");
        }
    }

    @OnClick(R.id.fabCommentSend)
    public void onClickCommentSend(){
        String commentData = this.arEditor.getARE().getHtml();

        if(Utility.isNullOrEmpty(commentData)){
            Toast.makeText(this, "Comment field is required..!", Toast.LENGTH_LONG).show();
        }

        Log.e("data====> " , commentData);
    }


    @OnClick(R.id.ivPostUserImage)
    public void onClickUserImage(){
        if(callIntent == 1){
            Intent friend = new Intent(this , UserProfileAndPostActivity.class);
            friend.putExtra(BaseUrl.NAME , showUserName);
            startActivity(friend);
        }else{
            MyPreferenceData myPref = new MyPreferenceData(context);
            myPref.saveData(BaseUrl.PREF_SLUG,showUserName);
            Intent page = new Intent(this , MyPagesPostActivity.class);
            page.putExtra(BaseUrl.NAME , showUserName);
            startActivity(page);
        }

    }

    @OnClick(R.id.tvName)
    public void onClickUserName(){
        if(callIntent == 1){
            Intent friend = new Intent(this , UserProfileAndPostActivity.class);
            friend.putExtra(BaseUrl.NAME , showUserName);
            startActivity(friend);
        }else{
            MyPreferenceData myPref = new MyPreferenceData(context);
            myPref.saveData(BaseUrl.PREF_SLUG,showUserName);
            Intent page = new Intent(this , MyPagesPostActivity.class);
            page.putExtra(BaseUrl.NAME , showUserName);
            startActivity(page);
        }

    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        this.arEditor = findViewById(R.id.postEditor);
        MyPreferenceData myPref = new MyPreferenceData(this);
        slug= myPref.getData(BaseUrl.PREF_SLUG);
        getPostDetails();


    }


    private void getPostDetails(){
        BetaApiClient apiClient = new BetaApiClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<MyPostDetailsModel> call = apiInterface.getPostDetails(slug);
        call.enqueue(new Callback<MyPostDetailsModel>() {
            @Override
            public void onResponse(@NotNull Call<MyPostDetailsModel> call, @NotNull Response<MyPostDetailsModel> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if (response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        try {
                            showPostDetails(response.body() , response.body().getDetails().getType());
                        }catch (Exception e){e.printStackTrace();}

                        postId = String.valueOf(response.body().getDetails().getId());
                        id = response.body().getDetails().getUser_id();
                        tabInitView(response.body().getComment_count());
                        //setupTabIcons();
                        getLikeUserList();
                        getPollData(response.body().getDetails().getType());

                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<MyPostDetailsModel> call, @NotNull Throwable t) {

            }
        });
    }

   /* private void setupTabIcons() {
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(tabIcons[1]);
    }
*/

    @SuppressLint("SetTextI18n")
    private void tabInitView(int commentCount) {

        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        CommentTabPager adapter = new CommentTabPager(getSupportFragmentManager(),
                tabLayout.getTabCount(), Integer.parseInt(postId) , 1 , commentCount);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    @SuppressLint("SetTextI18n")
    @OnClick(R.id.tvLikeCount)
    public void onClickLikeCount() {
        Dialog dialog = new Dialog(this);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.like_user_dialog);
        RecyclerView rvLikeUserView = dialog.findViewById(R.id.recyclerView);
        AppCompatTextView tvLikeCount = dialog.findViewById(R.id.tvLikeCount);
        ImageView ivClose = dialog.findViewById(R.id.ivClose);
        tvLikeCount.setText(likeUser.size() + "  Likes");
        LikeUserAdapter likeUserAdapter = new LikeUserAdapter(likeUser, this);
        rvLikeUserView.setAdapter(likeUserAdapter);
        ivClose.setOnClickListener(v ->
                dialog.dismiss()
        );
        dialog.show();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        this.arEditor.onActivityResult(requestCode, resultCode, data,"image");
    }


    private void followAndUnFollowMethod(int status) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<FollowUnFollowModel> call = apiInterface.getFollowUnFollowing(String.valueOf(loginUserId) ,
                String.valueOf(id) , String.valueOf(status) , "2");

        call.enqueue(new Callback<FollowUnFollowModel>() {
            @Override
            public void onResponse(@NotNull Call<FollowUnFollowModel> call, @NotNull Response<FollowUnFollowModel> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        if(response.body().getFlag() == 1){
                            llFollow.setVisibility(View.GONE);
                            llFollowing.setVisibility(View.VISIBLE);
                        }else{
                            llFollow.setVisibility(View.VISIBLE);
                            llFollowing.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<FollowUnFollowModel> call, @NotNull Throwable t) {

            }
        });
    }


    private void checkFollowUnFollow(int id, int type) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<CheckFollowModel> call = apiInterface.checkFollow(type, id, loginUserId);
        call.enqueue(new Callback<CheckFollowModel>() {
            @Override
            public void onResponse(@NotNull Call<CheckFollowModel> call, @NotNull Response<CheckFollowModel> response) {
                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        if (response.body().getStatus().equals(BaseUrl.SUCCESS)) {
                            setFollowAndFollowingView(response.body().getFollow());
                        }
                    }catch (Exception e){e.printStackTrace();}

                }
            }

            @Override
            public void onFailure(@NotNull Call<CheckFollowModel> call, @NotNull Throwable t) {

            }
        });

    }

    private void getLikeUserList() {
      //  Log.e("Ok like post==> ", postId);
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<LikeUserModel> call = apiInterface.getLikeUser(postId);
        call.enqueue(new Callback<LikeUserModel>() {
            @Override
            public void onResponse(@NotNull Call<LikeUserModel> call, @NotNull Response<LikeUserModel> response) {
                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        if (response.body().getLikeUserLists().size() > 0) {
                            showLikeUser(response.body().getLikeUserLists());
                            likeUser.addAll(response.body().getLikeUserLists());
                        }else{
                            llLikeInfoSection.setVisibility(View.GONE);
                        }

                    }catch (Exception e){e.printStackTrace();}

                }
            }

            @Override
            public void onFailure(@NotNull Call<LikeUserModel> call, @NotNull Throwable t) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showLikeUser(List<LikeUserModel.LikeUserList> likeUserLists) {

        for (int i = 0; i < likeUserLists.size(); i++) {
            if (likeUserLists.get(i).getName().equals(name)) {
                ivLikeImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red));
                break;
            }else{
                if(darkModeStatus == 1){
                    ivLikeImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border));
                }else{
                    ivLikeImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border));
                }
            }
        }

        try {


        if (likeUserLists.size() >= 3) {
            Glide.with(this).load(likeUserLists.get(0).getImg()).into(ivOneLikeImage);
            Glide.with(this).load(likeUserLists.get(1).getImg()).into(ivTwoLikeImage);
            Glide.with(this).load(likeUserLists.get(2).getImg()).into(ivThreeLikeImage);
            tvLikeUserName.setText(likeUserLists.get(0).getName() + ", " +
                    likeUserLists.get(1).getName());
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
        }catch (Exception e){e.printStackTrace();}

        int otherLikeCount;
        String totalCount;


        if (likeUserLists.size() > 2) {
            otherLikeCount = likeUserLists.size() - 2;
            if(darkModeStatus ==1){
                totalCount = "and <b> <font color=#FFFFFF>" + otherLikeCount + " Others </b>like this</font></b>";
            }else{
                totalCount = "and <b> <font color=#1A1A1A>" + otherLikeCount + " Others </b>like this</font></b>";
            }

        } else {
            totalCount = String.valueOf(likeUserLists.size());
        }
        tvLikeCount.setText(Html.fromHtml(totalCount));
        likeCount = likeUserLists.size();
       // tvLike.setText(String.valueOf(likeUserLists.size()));


    }

    private void setFollowAndFollowingView(int followStatus) {
        if (followStatus == 1) {
            llFollow.setVisibility(View.GONE);
            llFollowing.setVisibility(View.VISIBLE);
        } else {
            llFollow.setVisibility(View.VISIBLE);
            llFollowing.setVisibility(View.GONE);
        }
    }


    @SuppressLint("SetTextI18n")
    private void showPostDetails(MyPostDetailsModel postDetails , int type) {

        if (postDetails.getComment_count() != 0) {
            commentCount = postDetails.getComment_count();
            tvCommentCont.setText(String.valueOf(postDetails.getComment_count()));
        }
        if(postDetails.getAddToMySpace() != null) {
            String[] addSpace = postDetails.getAddToMySpace().split(",");
            List<String> addMySpace = Arrays.asList(addSpace);
                if (addMySpace.contains(String.valueOf(loginUserId))) {
                    ivFollowIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check_blue));
                    tvFollow.setText(getString(R.string.following));
                } else {
                    ivFollowIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add_black));
                    tvFollow.setText(getString(R.string.follow));
                }
        }
        tvViewSeeCount.setText(new Utility().prettyCount(postDetails.getDetails().getView_count()));
        if(postDetails.getUserInfo() != null) {
            llUserDetailsSection.setVisibility(View.VISIBLE);
            llPageDetailsSection.setVisibility(View.GONE);
            callIntent = 1;
            showUserName = postDetails.getUserInfo().getName();
            checkFollowUnFollow(postDetails.getUserInfo().getId(), 2);// check follow unfollow info
            if (postDetails.getUserInfo().getHide_real_name() == 1) {
                tvName.setText(postDetails.getUserInfo().getName());
            } else {
                tvName.setText(postDetails.getUserInfo().getFull_name());
            }

            try {
                new Utility().showUserVerified(postDetails.getUserInfo().getIs_verified(), tvUserVerifiedInfo);
                Glide.with(this).load(postDetails.getUserInfo().getImage()).dontAnimate().placeholder(R.drawable.user_placeholder).into(ivPostUserImage);
            }catch (Exception e){e.printStackTrace();}

        }else if(postDetails.getMyPageInfo() != null){
            llUserDetailsSection.setVisibility(View.GONE);
            llPageDetailsSection.setVisibility(View.VISIBLE);

            checkFollowUnFollow(postDetails.getMyPageInfo().getId(), 1);// check follow unfollow info
            callIntent = 2;
            showUserName = postDetails.getMyPageInfo().getTag_slug();
                tvGroupName.setText(postDetails.getMyPageInfo().getTitle());
                Glide.with(this).load(BaseUrl.PAGE_IMAGE_URL+postDetails.getMyPageInfo().getTag_img()).dontAnimate().placeholder(R.drawable.user_placeholder).into(ivPageImage);
        }

        tvPostTitle.setText(postDetails.getDetails().getTitle());
        tvDateTime.setText(new DateFormatUtils().showPostDate(postDetails.getDetails().getCreated_at()));
        Log.e("line Count==> " , tvPostTitle.getLineCount()+"");
       if(tvPostTitle.getLineCount() > 2){
           tvPostTitle.setMaxLines(2);
            rlMoreTitle.setVisibility(View.VISIBLE);
           setMoreDayNight();
        }else{
           rlMoreTitle.setVisibility(View.GONE);
        }

       if(type == 2){ // type == 2 mns show poll
           pollRecyclerView.setVisibility(View.VISIBLE);
           webView.setVisibility(View.GONE);
           webViewMore.setVisibility(View.GONE);
           llPostImageSection.setVisibility(View.GONE);
           ivMoreArrowMore.setVisibility(View.GONE);
           rlMorePost.setVisibility(View.GONE);
       }else{
           pollRecyclerView.setVisibility(View.GONE);
           webView.setVisibility(View.VISIBLE);
           webViewMore.setVisibility(View.VISIBLE);
           llPostImageSection.setVisibility(View.VISIBLE);
           ivMoreArrowMore.setVisibility(View.VISIBLE);
           rlMorePost.setVisibility(View.VISIBLE);
           setMoreDayNightPost(webView,postDetails.getDetails().getDescription());
           setMoreDayNightPost(webViewMore,postDetails.getDetails().getDescription());
       }


        if (Utility.isNotNullOrEmpty(postDetails.getDetails().getImg())){
            llPostImageSection.setVisibility(View.VISIBLE);
            rlCathySection.setVisibility(View.GONE);
            ivPostImage.setVisibility(View.VISIBLE);
            Glide.with(context).load(BaseUrl.POST_IMAGE_URL + postDetails.getDetails().getImg()).into(ivPostImage);
        }else{
            if(Utility.isNotNullOrEmpty(postDetails.getDetails().getCatchy_text())) {
                llPostImageSection.setVisibility(View.VISIBLE);
                rlCathySection.setVisibility(View.VISIBLE);
                ivPostImage.setVisibility(View.GONE);
                if (postDetails.getDetails().getColor_code().equalsIgnoreCase("#f1c40f")) {
                    rlCathySection.setBackgroundColor(Color.parseColor(postDetails.getDetails().getColor_code()));
                    tvCatchyName.setTextColor(ContextCompat.getColor(context, R.color.red));
                    tvCatchyName.setText(postDetails.getDetails().getCatchy_text());
                } else {
                    rlCathySection.setBackgroundColor(Color.parseColor(postDetails.getDetails().getColor_code()));
                    tvCatchyName.setTextColor(ContextCompat.getColor(context, R.color.white));
                    tvCatchyName.setText(postDetails.getDetails().getCatchy_text());
                }
            }else{
                llPostImageSection.setVisibility(View.GONE);
            }
        }

    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    private  void addToMySpace(){
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<AddMySpaceModel> call = apiInterface.addToMySpace(loginUserId , Integer.parseInt(postId));
        call.enqueue(new Callback<AddMySpaceModel>() {
            @Override
            public void onResponse(@NotNull Call<AddMySpaceModel> call, @NotNull Response<AddMySpaceModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getResult() == 1){
                        ivFollowIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext() , R.drawable.ic_check_blue));
                        addCustomToast(response.body().getMessage());
                        tvFollow.setText(getString(R.string.following));
                    }else {
                        ivFollowIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext() , R.drawable.ic_add_black));
                        addCustomToast(response.body().getMessage());
                        tvFollow.setText(getString(R.string.follow));
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<AddMySpaceModel> call, @NotNull Throwable t) {

            }
        });
    }

    private void addCustomToast(String message){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,  findViewById(R.id.custom_toast_layout));
        AppCompatTextView tvMessage = layout.findViewById(R.id.tvToast);
        tvMessage.setText(message);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.with(getApplicationContext()).pauseRequests();
    }

    @Override
    public void onClickPoll() {

    }
}
