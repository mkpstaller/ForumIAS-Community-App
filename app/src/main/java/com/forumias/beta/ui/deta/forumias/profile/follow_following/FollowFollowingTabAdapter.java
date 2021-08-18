package com.forumias.beta.ui.deta.forumias.profile.follow_following;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.ui.loginfragment.FollowerFragment;
import com.forumias.beta.ui.loginfragment.FollowingFragment;

public class FollowFollowingTabAdapter extends FragmentStatePagerAdapter {
    int tabCount , followers , following;
    public FollowFollowingTabAdapter(@NonNull FragmentManager fm, int tabCount , int followers , int following) {
        super(fm);
        this.tabCount = tabCount;
        this.followers = followers;
        this.following  = following;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment tab1 = new FollowerFragment();
                callProfileFragment(tab1 , position);
                return tab1;
            case 1:
                Fragment fm2 = new FollowingFragment();
                callProfileFragment(fm2 , position);
                return fm2;
            default:
                return null;
        }

    }

    private void callProfileFragment(Fragment fm, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(BaseUrl.TAB_POSITION, position);
        fm.setArguments(bundle);
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
                title = "Followers ( "+followers+" )";
                break;
            case 1:
                title = "Following ( "+following+" )";
                break;

        }
        return title;
    }
}
