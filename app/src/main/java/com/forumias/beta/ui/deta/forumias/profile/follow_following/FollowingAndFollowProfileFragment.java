package com.forumias.beta.ui.deta.forumias.profile.follow_following;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class FollowingAndFollowProfileFragment extends Fragment implements TabLayout.OnTabSelectedListener{
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private int loginUserId , darkModeStatus , followersCount ,followingCount;
    private String userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following_and_follow_profile, container, false);
        ButterKnife.bind(this , view);

        MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(getContext()));
        userName = myPreferenceData.getData(BaseUrl.NAME);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
        followersCount = myPreferenceData.getIntegerData(BaseUrl.FOLLOWER_DATA);
        followingCount = myPreferenceData.getIntegerData(BaseUrl.FOLLOWING_DATA);

        if(darkModeStatus == 1){
            tabLayout.setBackgroundResource(R.color.darkmode_back_color);
            tabLayout.setTabTextColors(ContextCompat.getColorStateList(getContext(), R.color.bnv_tab_item_foreground_white));
        }else{
            tabLayout.setBackgroundResource(R.color.white);
            tabLayout.setTabTextColors(ContextCompat.getColorStateList(getContext(), R.color.bnv_tab_item_foreground_black));
        }

        setTabSetting();
        return view;
    }


    private void setTabSetting() {
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        FollowFollowingTabAdapter adapter = new FollowFollowingTabAdapter(getChildFragmentManager(),
                tabLayout.getTabCount() , followersCount , followingCount);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
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
