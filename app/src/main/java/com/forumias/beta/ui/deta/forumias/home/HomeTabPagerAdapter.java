package com.forumias.beta.ui.deta.forumias.home;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.ui.deta.forumias.channel.ChannelsFragment;

import org.jetbrains.annotations.NotNull;

public class HomeTabPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    HomeTabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle bundle1 = new Bundle();
                Fragment tab1 = new MySpaceFragment();
                bundle1.putInt(BaseUrl.TAB_POSITION, position);
                tab1.setArguments(bundle1);
                return tab1;
            case 1:
                Bundle bundle2 = new Bundle();
                Fragment tab2 = new MySpaceFragment();
                bundle2.putInt(BaseUrl.TAB_POSITION, position);
                tab2.setArguments(bundle2);
                return tab2;

            case 2:
                Bundle bundle3 = new Bundle();
                Fragment tab3 = new ChannelsFragment();
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
                title = "Latest";
                break;
            case 1:
                title = "My Space";
                break;
            case 2:
                title = "Channels";
                break;

        }
        return title;
    }
}