package com.forumias.beta.ui.deta.forumias.save_comment;

import android.content.Context;

import java.util.List;

public interface SaveDeleteCommentInterface {
    void saveDeleteComment(int loginUserId , int saveCommentId , int postId , int commentId , int isDelete  ,
                           int position , List<SaveCommentModel.InfoData.SaveCommentList> saveCommentLists , Context context);
}
