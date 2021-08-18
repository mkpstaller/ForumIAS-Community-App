package com.forumias.beta.ui.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.ui.loginfragment.FragmentLogin;
import com.forumias.beta.ui.loginfragment.FragmentSignUp;

import org.jetbrains.annotations.NotNull;

public class LoginTabs extends FragmentStatePagerAdapter {
    private int tabCount;
    LoginTabs(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle bundle1 = new Bundle();
                Fragment tab1 = new FragmentLogin();
                bundle1.putInt(BaseUrl.TAB_POSITION, position);
                tab1.setArguments(bundle1);
                return tab1;
            case 1:
                Bundle bundle2 = new Bundle();
                Fragment tab2 = new FragmentSignUp();
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
                title = "Login";
                break;
            case 1:
                title = "Sign Up";
                break;

        }
        return title;
    }
}