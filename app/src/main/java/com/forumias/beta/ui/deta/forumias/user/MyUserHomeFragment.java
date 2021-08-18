package com.forumias.beta.ui.deta.forumias.user;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.R;
import com.google.android.material.tabs.TabLayout;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyUserHomeFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.flUserListSection)
    FrameLayout flUserListSection;
    @BindView(R.id.tvFollowText)
    AppCompatTextView tvFollowText;
    @BindView(R.id.tvFollowHint)
    AppCompatTextView tvFollowHint;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_user_home, container, false);
        ButterKnife.bind(this , view);

        MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(getContext()));
        int darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);

        if(darkModeStatus == 1){
            flUserListSection.setBackgroundResource(R.color.darkmode_back_color);
            tvFollowText.setTextColor(ContextCompat.getColor(getContext(),R.color.light_white));
            tvFollowHint.setTextColor(ContextCompat.getColor(getContext(),R.color.light_white));
        }else{
            flUserListSection.setBackgroundResource(R.color.white);
            tvFollowText.setTextColor(ContextCompat.getColor(getContext(),R.color.gray_dark));
            tvFollowHint.setTextColor(ContextCompat.getColor(getContext(),R.color.gray_dark));
        }

        initTab();
        return view;
    }

    private void initTab(){
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_access_time));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_most_useful));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        UserTabPager adapter = new UserTabPager(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
       // tabLayout.setOnTabSelectedListener(this);
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
}
