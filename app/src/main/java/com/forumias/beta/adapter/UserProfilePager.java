package com.forumias.beta.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.ui.deta.forumias.profile.MyPostFragment;
import com.forumias.beta.ui.deta.forumias.profile.ProfileNotificationFragment;
import com.forumias.beta.ui.deta.forumias.profile.ProfileSettingFragment;
import com.forumias.beta.ui.deta.forumias.profile.follow_following.FollowingAndFollowProfileFragment;

public class UserProfilePager extends FragmentStatePagerAdapter {
    private int tabCount;
    private String name;
    public UserProfilePager(@NonNull FragmentManager fm, int tabCount , String name) {
        super(fm);
        this.tabCount = tabCount;
        this.name = name;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                ProfileSettingFragment tab1 = new ProfileSettingFragment();
                bundle.putInt(BaseUrl.TAB_POSITION, position);

                tab1.setArguments(bundle);
                return tab1;
            case 1:
                Fragment fm2 = new MyPostFragment();
              //  callProfileFragment(fm2 , 0);
                Bundle bundle1 = new Bundle();
                bundle1.putInt(BaseUrl.TAB_POSITION, 0);
                bundle1.putString(BaseUrl.NAME, name);
                bundle1.putInt(BaseUrl.TAG_ID, 0);
                bundle1.putInt(BaseUrl.IS_VERIFIED, 0);
                bundle1.putString(BaseUrl.IMAGE, "");
                fm2.setArguments(bundle1);

                return fm2;
/*
           case 2:
               Fragment fm3 = new MyPostFragment();
                callProfileFragment(fm3 , position);
                return fm3; */
            case 2:
                Fragment fm4 = new ProfileNotificationFragment();
                callProfileFragment(fm4 , position);
                return fm4;
            case 3:
                Fragment fm5 = new FollowingAndFollowProfileFragment();
                callProfileFragment(fm5 , position);
                return fm5;
            default:
                return null;
        }

    }

    private void callProfileFragment(Fragment fm, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(BaseUrl.TAB_POSITION, position);
        bundle.putString(BaseUrl.NAME, name);
        fm.setArguments(bundle);
    }


    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
     /*   String title = null;
        switch (position){
            case 0:
                title = "Profile";
                break;
            case 1:
                title = "Email Notification";
                break;

        }*/
        return null;
    }
}
