package com.forumias.beta.ui.deta.forumias.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class InviteFriendActivity extends AppCompatActivity {
    public static final String FACEBOOK_PACKAGE_NAME = "com.facebook.katana";
    public static final String TWITTER_PACKAGE_NAME = "com.twitter.android";
    public static final String INSTAGRAM_PACKAGE_NAME = "com.instagram.android";
    public static final String PINTEREST_PACKAGE_NAME = "com.pinterest";
    public static final String WHATS_PACKAGE_NAME =  "com.whatsapp";
    public static final String TELEGRAM =  "com.telegram";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friend);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.llWhatAppShare)
    public void onClickShareWhatApp(){
        MyUtility.shareAppWithSocial(this , WHATS_PACKAGE_NAME ,"ForumIAS" , BaseUrl.PLAY_STORE_LINK);
    }

    @OnClick(R.id.llShareFacebook)
    public void onClickShareFacebook(){
        MyUtility.shareAppWithSocial(this , FACEBOOK_PACKAGE_NAME ,"ForumIAS" , BaseUrl.PLAY_STORE_LINK);
    }

    @OnClick(R.id.llTelegram)
    public void onClikcTelegram(){
        MyUtility.shareAppWithSocial(this , TELEGRAM ,"ForumIAS" , BaseUrl.PLAY_STORE_LINK);
    }

    @OnClick(R.id.llTwiter)
    public void onClikcTwiter(){
        MyUtility.shareAppWithSocial(this , TWITTER_PACKAGE_NAME ,"ForumIAS" , BaseUrl.PLAY_STORE_LINK);
    }

    @OnClick(R.id.ivBack)
    public void onClickBack(){
        finish();
    }
}
