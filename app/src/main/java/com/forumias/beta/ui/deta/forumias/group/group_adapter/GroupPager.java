package com.forumias.beta.ui.deta.forumias.group.group_adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.ui.deta.forumias.group.PublicGroupFragment;


public class GroupPager extends FragmentStatePagerAdapter {
    int tabCount;
    public GroupPager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                PublicGroupFragment tab1 = new PublicGroupFragment();
                bundle.putInt(BaseUrl.TAB_POSITION, position);
                tab1.setArguments(bundle);
                return tab1;
            case 1:
                Bundle bundle2 = new Bundle();
                PublicGroupFragment tab2 = new PublicGroupFragment();
                bundle2.putInt(BaseUrl.TAB_POSITION, position);
                tab2.setArguments(bundle2);
                return tab2;
            case 2:
                Bundle bundle3 = new Bundle();
                PublicGroupFragment tab3 = new PublicGroupFragment();
                bundle3.putInt(BaseUrl.TAB_POSITION, position);
                tab3.setArguments(bundle3);

                return tab3;
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
                title = "Public";
                break;
            case 1:
                title = "Private";
                break;
            case 2:
                title = "My Group";
                break;
        }
        return title;
    }
}