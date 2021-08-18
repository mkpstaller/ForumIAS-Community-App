package com.forumias.beta.ui.deta.forumias.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.ui.deta.forumias.working_us.WebViewActivity;
import com.forumias.beta.BuildConfig;
import com.forumias.beta.R;
import com.forumias.beta.ui.deta.forumias.splash.BetaSplashActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivityActivity extends AppCompatActivity {

    @BindView(R.id.llSettingSection)
    LinearLayoutCompat llSettingSection;
    @BindView(R.id.rlToolBar)
    RelativeLayout rlToolBar;
    @BindView(R.id.tvTitle)
    AppCompatTextView tvTitle;
    @BindView(R.id.tvSendfeedBakc)
    AppCompatTextView tvSendfeedBakc;
    @BindView(R.id.tvInviteFriend)
    AppCompatTextView tvInviteFriend;
    @BindView(R.id.tvSecurity)
    AppCompatTextView tvSecurity;
    @BindView(R.id.tvVersion)
    AppCompatTextView tvVersion;
    @BindView(R.id.tvtermCondition)
    AppCompatTextView tvtermCondition;
    @BindView(R.id.tvLogout)
    AppCompatTextView tvLogout;
    @BindView(R.id.ivIconOne)
    AppCompatImageView ivIconOne;
    @BindView(R.id.ivIconTwo)
    AppCompatImageView ivIconTwo;
    @BindView(R.id.ivIconThree)
    AppCompatImageView ivIconThree;
    @BindView(R.id.ivIconFour)
    AppCompatImageView ivIconFour;
    @BindView(R.id.ivFive)
    AppCompatImageView ivIconFive;
    @BindView(R.id.ivSix)
    AppCompatImageView ivIconSix;
    @BindView(R.id.ivBack)
    AppCompatImageView ivBack;
    @BindView(R.id.tvVersionCode)
    AppCompatTextView tvVersionCode;


    @BindView(R.id.viewOne)
    View viewOne;
    @BindView(R.id.viewTwo)
    View viewTwo;
    @BindView(R.id.viewThree)
    View viewThree;
    @BindView(R.id.viewFour)
    View viewFour;
    @BindView(R.id.viewFive)
    View viewFive;





    private int darkModelStatus , loginUserId;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_activity);
        ButterKnife.bind(this);

        MyPreferenceData preferenceData = new MyPreferenceData(this);
        darkModelStatus = preferenceData.getIntegerData(BaseUrl.DARK_MODE);
        loginUserId = preferenceData.getIntegerData(BaseUrl.USER_ID);

        if(loginUserId != 0){
            tvLogout.setText(getString(R.string.logout));
        }else{
            tvLogout.setText(getString(R.string.login));
        }

        String version = BuildConfig.VERSION_NAME;
        tvVersionCode.setText("V : "+version);

        setDarkModeView();
    }

    private void setDarkModeView() {
        if(darkModelStatus == 1){
            llSettingSection.setBackgroundResource(R.color.black_light);
            rlToolBar.setBackgroundResource(R.color.darkmode_back_color);
            tvInviteFriend.setTextColor(ContextCompat.getColor(this , R.color.light_white));
            tvTitle.setTextColor(ContextCompat.getColor(this , R.color.light_white));
            tvLogout.setTextColor(ContextCompat.getColor(this , R.color.light_white));
            tvSecurity.setTextColor(ContextCompat.getColor(this , R.color.light_white));
            tvSendfeedBakc.setTextColor(ContextCompat.getColor(this , R.color.light_white));
            tvtermCondition.setTextColor(ContextCompat.getColor(this , R.color.light_white));
            tvVersion.setTextColor(ContextCompat.getColor(this , R.color.light_white));
            tvVersionCode.setTextColor(ContextCompat.getColor(this , R.color.light_white));
            ivIconOne.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_send_white));
            ivIconTwo.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_post_follow));
            ivIconThree.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_lock_rhite));
            ivIconFour.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_about_white));
            ivIconFive.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_page_white));
            ivIconSix.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_exit_white));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back_key_white));
            viewOne.setBackgroundResource(R.color.border_color);
            viewFive.setBackgroundResource(R.color.border_color);
            viewFour.setBackgroundResource(R.color.border_color);
            viewThree.setBackgroundResource(R.color.border_color);
            viewTwo.setBackgroundResource(R.color.border_color);

        }else{
            llSettingSection.setBackgroundResource(R.color.back_color);
            rlToolBar.setBackgroundResource(R.color.white);
            tvInviteFriend.setTextColor(ContextCompat.getColor(this , R.color.black_light));
            tvTitle.setTextColor(ContextCompat.getColor(this , R.color.black_light));
            tvLogout.setTextColor(ContextCompat.getColor(this , R.color.black_light));
            tvSecurity.setTextColor(ContextCompat.getColor(this , R.color.black_light));
            tvSendfeedBakc.setTextColor(ContextCompat.getColor(this , R.color.black_light));
            tvtermCondition.setTextColor(ContextCompat.getColor(this , R.color.black_light));
            tvVersion.setTextColor(ContextCompat.getColor(this , R.color.black_light));
            tvVersionCode.setTextColor(ContextCompat.getColor(this , R.color.black_light));
            ivIconOne.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_send_feadback));
            ivIconTwo.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_invite_friend));
            ivIconThree.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_lock));
            ivIconFour.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_about));
            ivIconFive.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_pages));
            ivIconSix.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_exit));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back_key));

            viewTwo.setBackgroundResource(R.color.low_gray);
            viewOne.setBackgroundResource(R.color.low_gray);
            viewThree.setBackgroundResource(R.color.low_gray);
            viewFour.setBackgroundResource(R.color.low_gray);
            viewFive.setBackgroundResource(R.color.low_gray);
        }
    }

    @OnClick(R.id.rlInviteFriend)
    public void onClickInviteFriend(){
        Intent intent = new Intent(this , InviteFriendActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.rlSendFeedBack)
    public void onClickSendfeedBack(){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
    }

    @OnClick(R.id.rlSecurity)
    public void onClickSecurity(){
        Intent intent = new Intent(this , WebViewActivity.class);
        intent.putExtra(BaseUrl.WEB_LINK , BaseUrl.SECURITY);
        startActivity(intent);
    }

    @OnClick(R.id.llTermsCondition)
    public void onClickTermsCondition(){
        Intent intent = new Intent(this , WebViewActivity.class);
        intent.putExtra(BaseUrl.WEB_LINK , BaseUrl.TERMS_CONDITION);
        startActivity(intent);
    }

    @OnClick(R.id.llLogout)
    public void onClickLogout(){
        MyPreferenceData myPreferenceData = new MyPreferenceData(this);
        myPreferenceData.clearData();
        Intent splash = new Intent(this , BetaSplashActivity.class);
        startActivity(splash);
    }

    @OnClick(R.id.ivBack)
    public void OnClickBack(){
        finish();
    }
}
