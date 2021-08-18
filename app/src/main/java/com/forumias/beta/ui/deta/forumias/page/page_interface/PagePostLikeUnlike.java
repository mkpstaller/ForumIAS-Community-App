package com.forumias.beta.ui.deta.forumias.page.page_interface;

import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.forumias.beta.ui.deta.forumias.page.page_paging_adapter.PagePostModel;

public interface PagePostLikeUnlike {
    void callMyLikeUnlike(ProgressBar progressBar, PagePostModel.PageData pagePosts, int position, int likeCount, int postId , int userId , AppCompatImageView ivLikeImageView ,
                          AppCompatTextView tvLikeCount);
    void addToMySpace(int userId , int postId , AppCompatImageView ivAddToMySpace);

    void repostVoidData(int loginUserId, int postId);
}
