package com.forumias.beta.ui.deta.forumias.comment.fragment.all_comment_paging;


import androidx.appcompat.widget.AppCompatImageView;


public interface SaveCommentInterfacae {
    void saveComment(int loginUserId , int saveCommentId , int postId , int commentId
            , int isDelete , AppCompatImageView ivSaveComment , boolean status);
    void shareComment(int commentId);

}
