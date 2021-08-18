package com.forumias.beta.adapter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.ui.deta.forumias.channel.my_channel_fragment.AddUserFragment;
import com.forumias.beta.ui.deta.forumias.channel.my_channel_fragment.ChannelFeedFragment;
import com.forumias.beta.ui.deta.forumias.channel.my_channel_fragment.MySubscribersFragment;

import org.jetbrains.annotations.NotNull;


public class MyChannelPager extends FragmentStatePagerAdapter {
    int tabCount;
    public MyChannelPager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }
    @NotNull
    @Override
    public Fragment getItem(int position) {
        Fragment tab;
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                tab = new ChannelFeedFragment();
                bundle.putInt(BaseUrl.TAB_POSITION, position);
                tab.setArguments(bundle);
                return tab;
            case 1:
               Bundle bundle2 = new Bundle();
                tab = new MySubscribersFragment();
                bundle2.putInt(BaseUrl.TAB_POSITION, position);
                tab.setArguments(bundle2);
                return tab;

            case 2:
                Bundle bundle3 = new Bundle();
                tab = new AddUserFragment();
                bundle3.putInt(BaseUrl.TAB_POSITION, position);
                tab.setArguments(bundle3);
                return tab;
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
                title = "Subscribers";
                break;
            case 2:
                title = "Add User";
                break;
        }
        return title;
    }
}