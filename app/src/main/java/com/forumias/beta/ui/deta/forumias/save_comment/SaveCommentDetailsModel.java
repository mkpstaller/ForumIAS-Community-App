package com.forumias.beta.ui.deta.forumias.save_comment;

import com.forumias.beta.ui.deta.forumias.comment.fragment.model.AllCommentModel;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SaveCommentDetailsModel {
    private String status;
    @SerializedName("post_info")
    private SaveCommentModel.InfoData.SaveCommentList.PostInfo postInfo;
    @SerializedName("comment_info")
    private SaveCommentInfo saveCommentInfo;


    @Data
    public class SaveCommentInfo{
        private int id;
        private String description;
        private int status;
        private String created_at;
        private int post_id;
        private int user_id;
        private int page_id;
        private int pid;
        @SerializedName("user_info")
        private AllCommentModel.Result.CommentList.UserInfo userInfo;
    }
}
