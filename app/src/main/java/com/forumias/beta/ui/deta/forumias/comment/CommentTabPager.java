package com.forumias.beta.ui.deta.forumias.comment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.pojo.PostDetailsModel;
import com.forumias.beta.ui.deta.forumias.comment.fragment.AllCommentFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.List;


public class CommentTabPager extends FragmentStatePagerAdapter {
    private int tabCount;
    private int postId;
    private int callIntentStatus;
    private int commentCount;
    private List<PostDetailsModel.CommentListing> commentListings;
    public CommentTabPager(FragmentManager fm, int tabCount , int postId , int callIntentStatus ,int commentCount) {
        super(fm);
        this.tabCount= tabCount;
        this.postId = postId;
        this.callIntentStatus = callIntentStatus;
        this.commentCount = commentCount;
    }
    @NotNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment tab1 = new AllCommentFragment();
                passPostId(tab1,position , callIntentStatus , commentCount);
                return tab1;
         case 1:
                Fragment tab2 = new AllCommentFragment();
                //Fragment tab2 = new MostUseFullCommetFragment();
                passPostId(tab2,position , callIntentStatus , commentCount);
                return tab2;
            default:
                return null;
        }
    }

    private void passPostId(Fragment tab , int position , int callIntentStatus , int commentCount){
        Bundle bundle = new Bundle();
        bundle.putInt(BaseUrl.POST_ID,postId);
        bundle.putInt(BaseUrl.POSITION,position);
        bundle.putInt(BaseUrl.CALL_CLASS,callIntentStatus);
        tab.setArguments(bundle);
    }

    private void passCommentList(Fragment tab) {
        Gson gson = new Gson();
        Bundle bundle = new Bundle();
        Type type = new TypeToken<List<PostDetailsModel.CommentListing>>(){}.getType();
        String json = gson.toJson(commentListings,type);
        bundle.putString(BaseUrl.PASS_COMMENT_LIST,json);
        tab.setArguments(bundle);
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
                title = "All Comments "+"("+commentCount+")";
                break;
            case 1:
                title = "Most Useful";
                break;

        }
        return title;
    }
}