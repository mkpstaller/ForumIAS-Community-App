package com.forumias.beta.ui.deta.forumias.postGalleryImage;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.ui.deta.forumias.create_story.CreatePostFragment;
import com.forumias.beta.ui.deta.forumias.home.MySpaceFragment;


public class ImagePostTab extends FragmentStatePagerAdapter {
    private int tab;
    private Context context;
    private String imageUrl;
    public ImagePostTab(@NonNull FragmentManager fm, int tab , String imageUrl,  Context context) {
        super(fm, tab);
        this.tab = tab;
        this.context = context;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Bundle bundle = new Bundle();
                Fragment tab1= new ChatUserFragment();
                bundle.putInt(BaseUrl.TAB_POSITION , position);
                tab1.setArguments(bundle);
                return tab1;

            case 1:
                MyPreferenceData sp = new MyPreferenceData(context);
                sp.saveIntegerData(BaseUrl.SEARCH_STATUS , 10);
                sp.saveData(BaseUrl.GALLERY_IMAGE , imageUrl);
                Bundle bundle1 = new Bundle();
                Fragment tab2= new MySpaceFragment();
                bundle1.putInt(BaseUrl.TAB_POSITION , position);
                tab2.setArguments(bundle1);
                return tab2;
            case 2:
                Bundle bundle2 = new Bundle();
                Fragment tab3= new CreatePostFragment(imageUrl);
                bundle2.putInt(BaseUrl.TAB_POSITION , position);
                tab3.setArguments(bundle2);
                return tab3;

        }
        return null;
    }

    @Override
    public int getCount() {
        return tab;
    }

    @Override
    public CharSequence getPageTitle(int position){
        String title = null;
        switch (position){
            case 0:
                title = "Chats";
                break;
            case 1:
                title = "Posts";
                break;
            case 2:
                title = "New";
                break;

        }

        return title;
    }

}
