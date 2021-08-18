package com.forumias.beta.ui.deta.forumias.page;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.forumias.beta.api.BaseUrl;


public class PagesTabPager extends FragmentStatePagerAdapter {
    int tabCount;
    public PagesTabPager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                MyPagesFragment tab1 = new MyPagesFragment();
                bundle.putInt(BaseUrl.TAB_POSITION, position);
                tab1.setArguments(bundle);
                return tab1;
            case 1:
               Bundle bundle2 = new Bundle();
                MyPagesFragment tab2 = new MyPagesFragment();
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
                title = "All Page";
                break;
            case 1:
                title = "Pages create by me";
                break;

        }
        return title;
    }
}