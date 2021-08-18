package com.forumias.beta.ui.deta.forumias.channel;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.forumias.beta.api.BaseUrl;


public class ChannelsTabPager extends FragmentStatePagerAdapter {
    private int tabCount;
    public ChannelsTabPager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
        Log.e("position channe;===> " , String.valueOf(tabCount));
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                Fragment tab1 = new PublicChannelFragment();
                bundle.putInt(BaseUrl.TAB_POSITION, position);
                tab1.setArguments(bundle);
                return tab1;
           case 1:
                Bundle bundle2 = new Bundle();
                Fragment tab2 = new PublicChannelFragment();
                bundle2.putInt(BaseUrl.TAB_POSITION, position);
                tab2.setArguments(bundle2);
                return tab2;
            case 2:
                Bundle bundle3 = new Bundle();
                Fragment tab3 = new PublicChannelFragment();
                bundle3.putInt(BaseUrl.TAB_POSITION, position);
                tab3.setArguments(bundle3);
                return tab3;
            case 3:
                Bundle bundle4 = new Bundle();
                Fragment tab4 = new PublicChannelFragment();
                bundle4.putInt(BaseUrl.TAB_POSITION, position);
                tab4.setArguments(bundle4);
                return tab4;
            case 4:
                Bundle bundle5 = new Bundle();
                Fragment tab5 = new PublicChannelFragment();
                bundle5.putInt(BaseUrl.TAB_POSITION, position);
                tab5.setArguments(bundle5);
                return tab5;
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
                title = "Secret";
                break;
            case 3:
                title = "Academy";
                break;
            case 4:
                title = "My Channels";
                break;
        }
        return title;
    }
}