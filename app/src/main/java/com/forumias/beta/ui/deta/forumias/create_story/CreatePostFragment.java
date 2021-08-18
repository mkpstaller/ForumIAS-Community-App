package com.forumias.beta.ui.deta.forumias.create_story;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.ui.loginfragment.LoginAlertFragment;
import com.forumias.beta.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePostFragment extends Fragment {

    @BindView(R.id.llCreateAskSomeThing)
    LinearLayoutCompat llCreateAskSomeThing;
    @BindView(R.id.llCreateGyan)
    LinearLayoutCompat llCreateGyan;
    @BindView(R.id.llShareLink)
    LinearLayoutCompat llShareLink;
    @BindView(R.id.tvShareGyan)
    AppCompatTextView tvShareGyan;
    @BindView(R.id.tvAsk)
    AppCompatTextView tvAsk;
    @BindView(R.id.tvLink)
    AppCompatTextView tvLink;
    @BindView(R.id.llCreatePostSection)
    FrameLayout llCreatePostSection;
    @BindView(R.id.ivGyanImage)
    AppCompatImageView ivGyanImage;
    @BindView(R.id.ivAskImage)
    AppCompatImageView ivAskImage;
    @BindView(R.id.ivLinkImage)
    AppCompatImageView ivLinkImage;


    int darkModeStatus , loginUserId;

    String imageUrlPost;
    public CreatePostFragment(String imageUrlPost) {
        this.imageUrlPost = imageUrlPost;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_create_post, container, false);
        ButterKnife.bind(this , view);

       MyPreferenceData myPreferenceData = new MyPreferenceData(getContext());
       darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
       loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);

        initViewDarkMode();

        return view;
    }

    private void initViewDarkMode() {
        if(darkModeStatus == 1){
            llCreateGyan.setBackgroundResource(R.drawable.dark_post_border);
            llCreateAskSomeThing.setBackgroundResource(R.drawable.dark_post_border);
            llShareLink.setBackgroundResource(R.drawable.dark_post_border);
            tvShareGyan.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.light_white));
            tvAsk.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.light_white));
            tvLink.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.light_white));
            llCreatePostSection.setBackgroundResource(R.color.darkmode_back_color);
            ivAskImage.setImageDrawable(ContextCompat.getDrawable(getContext() , R.drawable.ic_ask_white));
            ivGyanImage.setImageDrawable(ContextCompat.getDrawable(getContext() , R.drawable.ic_gyan_white));
            ivLinkImage.setImageDrawable(ContextCompat.getDrawable(getContext() , R.drawable.ic_link_white));
        }else{
            llCreateGyan.setBackgroundResource(R.color.white);
            llCreateAskSomeThing.setBackgroundResource(R.color.white);
            llShareLink.setBackgroundResource(R.color.white);
            tvShareGyan.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.black_light));
            tvAsk.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.black_light));
            tvLink.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.black_light));
            llCreatePostSection.setBackgroundResource(R.color.back_color);
            ivAskImage.setImageDrawable(ContextCompat.getDrawable(getContext() , R.drawable.ask_black));
            ivGyanImage.setImageDrawable(ContextCompat.getDrawable(getContext() , R.drawable.gyan_black));
            ivLinkImage.setImageDrawable(ContextCompat.getDrawable(getContext() , R.drawable.link_black));
        }
    }

    @OnClick(R.id.llCreateGyan)
    public void onClickCreateGyan(){
        if(loginUserId != 0) {
            callStoryIntent(1);
        }else{
            LoginAlertFragment alert  = new LoginAlertFragment();
            alert.show(getChildFragmentManager() ,"MyAlertLogin");
        }
   }

    @OnClick(R.id.llCreateAskSomeThing)
    public void onClickCreateAskSometing(){
        if(loginUserId != 0) {
            callStoryIntent(0);
        }else{
            LoginAlertFragment alert  = new LoginAlertFragment();
            alert.show(getChildFragmentManager() ,"MyAlertLogin");
        }
    }

    @OnClick(R.id.llShareLink)
    public void onCreateLink(){
        if(loginUserId != 0) {
            callStoryIntent(2);
        }else{
            LoginAlertFragment alert  = new LoginAlertFragment();
            alert.show(getChildFragmentManager() ,"MyAlertLogin");
        }
    }

    private void callStoryIntent(int status) {
        Intent intent = new Intent(getContext() , CreateStoryOrQuestionActivity.class);
        intent.putExtra(BaseUrl.ASK_STATUS ,status);
        intent.putExtra(BaseUrl.TAG_ID,"");
        intent.putExtra(BaseUrl.IMAGE,imageUrlPost);
        startActivity(intent);
    }
}
