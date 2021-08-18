package com.forumias.beta.ui.deta.forumias.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.forumias.beta.api.BaseUrl;


public class UserTabPager extends FragmentStatePagerAdapter {
    private int tabCount;
    UserTabPager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle bundle1 = new Bundle();
                Fragment tab1 = new UserFragment();
                bundle1.putInt(BaseUrl.TAB_POSITION, position);
                tab1.setArguments(bundle1);
               return tab1;
            case 1:
                Bundle bundle2 = new Bundle();
                Fragment tab2 = new UserFragment();
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
                title = "Top Followers";
                break;
            case 1:
                title = "Latest Users";
                break;

        }
        return title;
    }
}