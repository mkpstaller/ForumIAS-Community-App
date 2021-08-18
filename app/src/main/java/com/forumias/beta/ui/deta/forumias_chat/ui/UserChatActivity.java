package com.forumias.beta.ui.deta.forumias_chat.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.R;
import com.google.android.material.appbar.MaterialToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserChatActivity extends AppCompatActivity {

    @BindView(R.id.ivBack)
    AppCompatImageView ivBack;
    @BindView(R.id.tvName)
    AppCompatTextView tvName;
    @BindView(R.id.tvActiveStatus)
    AppCompatTextView tvActiveStatus;
    @BindView(R.id.toolbar)
    MaterialToolbar toolbar;
    @BindView(R.id.llChatHome)
    RelativeLayout llChatHome;
    @BindView(R.id.llChatSection)
    LinearLayoutCompat llChatSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        setDarkMode();
    }

    private void setDarkMode() {
        MyPreferenceData myPreferenceData = new MyPreferenceData(this);
        int  loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        int  darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);

        if(darkModeStatus == 1){
            llChatHome.setBackgroundResource(R.color.darkmode_back_adapter_color);
            toolbar.setBackgroundResource(R.color.black);
            llChatSection.setBackgroundResource(R.drawable.chat_edit_shape_dark);
           // ivAppLogo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.forumias_logo_white));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back_key_white));
            tvName.setTextColor(ContextCompat.getColor(this , R.color.white));
            tvActiveStatus.setTextColor(ContextCompat.getColor(this , R.color.gray));
        }else{
           // ivAppLogo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo_black));
            llChatSection.setBackgroundResource(R.drawable.chat_edit_shape);
            ivBack.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back_key));
            tvName.setTextColor(ContextCompat.getColor(this , R.color.black_light));
            tvActiveStatus.setTextColor(ContextCompat.getColor(this , R.color.gray_dark));
            llChatHome.setBackgroundResource(R.color.back_color);
            toolbar.setBackgroundResource(R.color.white);
        }
    }
}
