package com.forumias.beta.adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.ui.deta.forumias.group.GroupMemberFragment;
import com.forumias.beta.ui.deta.forumias.home.MySpaceFragment;


public class MyGroupPager extends FragmentStatePagerAdapter {
    private int tabCount;
    public MyGroupPager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                MySpaceFragment tab1 = new MySpaceFragment();
                bundle.putInt(BaseUrl.TAB_POSITION, position);
                tab1.setArguments(bundle);
                return tab1;
            case 1:
               Bundle bundle2 = new Bundle();
                GroupMemberFragment tab2 = new GroupMemberFragment();
                bundle2.putInt(BaseUrl.TAB_POSITION, position);
                tab2.setArguments(bundle2);
                return tab2;

            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position){
            case 0:
                title = "Feed";
                break;
            case 1:
                title = "Page Subscribers";
                break;

        }
        return title;
    }
}