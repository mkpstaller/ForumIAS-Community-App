package com.forumias.beta.ui.deta.forumias.home;

import android.content.Context;
import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.forumias.beta.ui.deta.forumias.home.model.HomeLatestPostModel;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.user_post_paging.UserPostModel;

public interface CallLikeUnLike {
    void callMyLikeUnlike(ProgressBar progressBar, HomeLatestPostModel.MyLatestPost latestPost, int position, int likeCount, int postId , int userId , AppCompatImageView ivLikeImageView ,
                          AppCompatTextView tvLikeCount , AppCompatImageView ivLiveHeart);

    void userMyLikeUnlike(ProgressBar progressBar, UserPostModel.MyStoriesList latestPost, int position, int likeCount, int postId , int userId , AppCompatImageView ivLikeImageView ,
                          AppCompatTextView tvLikeCount , AppCompatImageView ivLiveHeart);

    void testCallMethod();
    void addToMySpace(int userId , int postId , AppCompatImageView ivAddToMySpace);
    void reportDailog(int id, int loginUserId, Context context);

}
