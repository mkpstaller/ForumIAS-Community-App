package com.forumias.beta.ui.deta.forumias.user.user_profile_and_post;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.ui.deta.forumias.profile.MyPostFragment;

public class UserPostTabs extends FragmentStatePagerAdapter {
    private int tabCount , tagId , isVerified;
    private String name  , userName , image;
    UserPostTabs(FragmentManager fm, int tabCount ,String name , int tagId , String image , int isVerified) {
        super(fm);
        this.tabCount= tabCount;
        this.name = name;
        this.tagId = tagId;
        this.image = image;
        this.isVerified = isVerified;

        Log.e("data get in==1=> " , isVerified+"    "+image);

    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle bundle1 = new Bundle();
                Fragment tab1 = new MyPostFragment();
                bundle1.putInt(BaseUrl.TAB_POSITION, position);
                bundle1.putString(BaseUrl.NAME, name);
                bundle1.putInt(BaseUrl.TAG_ID, tagId);
                bundle1.putInt(BaseUrl.IS_VERIFIED, isVerified);
                bundle1.putString(BaseUrl.IMAGE, image);
                tab1.setArguments(bundle1);
                return tab1;
            case 1:
                Bundle bundle2 = new Bundle();
                Fragment tab2 = new MyPostFragment();
                bundle2.putInt(BaseUrl.TAB_POSITION, position);
                bundle2.putString(BaseUrl.NAME, name);
                bundle2.putInt(BaseUrl.TAG_ID, tagId);
                bundle2.putInt(BaseUrl.IS_VERIFIED, isVerified);
                bundle2.putString(BaseUrl.IMAGE, image);
                tab2.setArguments(bundle2);
                return tab2;
            case 2:
                Log.e("data get in===> " , userName+"    "+image);
                Bundle bundle3 = new Bundle();
                Fragment tab3 = new MyPostFragment();
                bundle3.putInt(BaseUrl.TAB_POSITION, position);
                bundle3.putString(BaseUrl.NAME, name);
                bundle3.putInt(BaseUrl.TAG_ID, tagId);
                bundle3.putInt(BaseUrl.IS_VERIFIED, isVerified);
                bundle3.putString(BaseUrl.IMAGE, image);
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
                title = "Post";
                break;
            case 1:
                title = "Discussion";
                break;
            case 2:
                title = "Comments";
                break;

        }
        return title;
    }
}