package com.forumias.messenger.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.forumias.messenger.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomePage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tvMessenger)
    public void onClickMessenger(){
        Intent intent = new Intent(WelcomePage.this , HomeMessengerActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
    }

    @OnClick(R.id.tvGoToBeta)
    public void onClickGoToBeta(){
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.forumias.chat");
        startActivity(intent);
        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);

    }
}
