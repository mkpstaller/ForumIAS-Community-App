package com.forumias.beta.ui.deta.forumias_chat.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.UserPojo;
import com.forumias.beta.ui.deta.forumias_chat.adapter.ChatUserAdapter;
import com.forumias.beta.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatMainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ivBack)
    AppCompatImageView ivBack;
    @BindView(R.id.ivAppLogo)
    AppCompatImageView ivAppLogo;
    @BindView(R.id.llChatHome)
    RelativeLayout llChatHome;
    @BindView(R.id.llToolbarSection)
    LinearLayoutCompat llToolbarSection;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);
        ButterKnife.bind(this);
        setDarkMode();

        initChatUserList();
    }



    @OnClick(R.id.ivBack)
    public void onClickBack(){
        finish();
    }

    private void setDarkMode() {
        MyPreferenceData myPreferenceData = new MyPreferenceData(this);
       int  loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
       int  darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);

       if(darkModeStatus == 1){
           llChatHome.setBackgroundResource(R.color.darkmode_back_adapter_color);
           llToolbarSection.setBackgroundResource(R.color.black);
           ivAppLogo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.forumias_logo_white));
           ivBack.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back_key_white));
       }else{
           ivAppLogo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo_black));
           ivBack.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back_key));
           llChatHome.setBackgroundResource(R.color.back_color);
           llToolbarSection.setBackgroundResource(R.color.white);
       }
    }

    private void initChatUserList(){
        List<UserPojo> userChatList= new ArrayList<>();
        UserPojo userPojo = new UserPojo("Musafir Prajapti", true);
        userChatList.add(userPojo);
        UserPojo userPojo1 = new UserPojo("Ravi Sharma", true);
        userChatList.add(userPojo1);
        UserPojo userPojo2 = new UserPojo("Manish Yadav", true);
        userChatList.add(userPojo2);

        ChatUserAdapter chatUserAdapter = new ChatUserAdapter(this,userChatList);
        recyclerView.setAdapter(chatUserAdapter);

    }
}
