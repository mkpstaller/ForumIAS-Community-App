package com.forumias.messenger.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.forumias.messenger.fragment.AllFragment;
import com.forumias.messenger.fragment.RequestFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    public TabPagerAdapter(FragmentManager fm , int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                AllFragment tabOne = new AllFragment();
                return tabOne;
            case 1:
                RequestFragment tabTwo = new RequestFragment();
                return  tabTwo;
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
                title = "All";
                break;
            case 1:
                title = "Request";
                break;
        }
        return title;
    }
}
