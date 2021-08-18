package com.forumias.beta.myinterface;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

public interface ReplyInterface {
    void replyOnComment(String userName , String description , int callClass);
    void quoteOnComment(String userName , String post , int callClass);
    void upVoteOnComment(int postId , int userId , int commentId , AppCompatImageView ivUpVote ,
                         AppCompatTextView tvUpVote , LinearLayoutCompat llUpVoteSection);
    void shareComment(int commentId);
    void showImageView(String url);
}
