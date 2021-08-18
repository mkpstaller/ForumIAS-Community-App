package com.forumias.beta.ui.deta.forumias.channel;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.ui.login.LoginActivity;
import com.forumias.beta.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelsFragment extends Fragment implements TabLayout.OnTabSelectedListener{
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.llChannelSection)
    LinearLayoutCompat llChannelSection;
    @BindView(R.id.rlLoginInfo)
    RelativeLayout rvLoginInfo;
    @BindView(R.id.rlChannelSection)
    RelativeLayout rlChannelSection;


    int darkModeStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channels, container, false);
        ButterKnife.bind(this , view);
        MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(getContext()));
        int loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);

        setDarkModeView();

        if(loginUserId != 0) {
            llChannelSection.setVisibility(View.VISIBLE);
            rvLoginInfo.setVisibility(View.GONE);
            initView();
        }else{
            llChannelSection.setVisibility(View.GONE);
            rvLoginInfo.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @OnClick(R.id.tvLogin)
    public void onClickLogin(){
        Intent intent = new Intent(getContext() , LoginActivity.class);
        intent.putExtra(BaseUrl.STATUS,1);
        startActivity(intent);
      //  getActivity().finish();
    }

    @OnClick(R.id.tvSignUp)
    public void onClickSignUp(){
        Intent intent = new Intent(getContext() , LoginActivity.class);
        intent.putExtra(BaseUrl.STATUS,2);
        startActivity(intent);
    }

    private void setDarkModeView() {
        if(darkModeStatus == 1){
            rlChannelSection.setBackgroundResource(R.color.gray_dark);
            tabLayout.setBackgroundResource(R.color.black_light);
        }else{
            rlChannelSection.setBackgroundResource(R.color.white);
            tabLayout.setBackgroundResource(R.color.white);
        }
    }

    private void initView() {
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        ChannelsTabPager adapter = new ChannelsTabPager(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) { }
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


}
