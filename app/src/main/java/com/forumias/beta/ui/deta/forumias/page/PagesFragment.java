package com.forumias.beta.ui.deta.forumias.page;


import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
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
 */
public class PagesFragment extends Fragment implements TabLayout.OnTabSelectedListener{

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.llPageHomeSection)
    LinearLayoutCompat llPageHomeSection;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pages, container, false);
        ButterKnife.bind(this , view);

        MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(getContext()));
        int darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);

        if(darkModeStatus == 1){
            llPageHomeSection.setBackgroundResource(R.color.darkmode_back_color);
            tabLayout.setBackgroundResource(R.color.darkmode_back_color);
        }else{
            llPageHomeSection.setBackgroundResource(R.color.back_color);
            tabLayout.setBackgroundResource(R.color.back_color);
        }

        initView();
        return view;
    }

    private void initView() {
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        PagesTabPager adapter = new PagesTabPager(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), tabLayout.getTabCount());
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
