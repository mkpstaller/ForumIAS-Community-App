package com.forumias.beta.ui.deta.forumias.user.user_profile_and_post;

import com.forumias.beta.ui.deta.forumias.comment.fragment.model.AllCommentModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class UserCommentModel {
    private String status;
    @SerializedName("post_info")
    private PostInfo postInfo;
    @SerializedName("comment_info")
    private List<UserCommentInfo> userCommentInfo;


    @Data
    public class PostInfo{
        private int id;
        private String title;
    }

    @Data
   public class UserCommentInfo{
        private int id;
        private int post_id;
        private int user_id;
        private int page_id;
        private int group_id;
        private String  description;
        private int status;
        private int views;
        private int soft_delete;
        private int is_scheduled;
        private String  created_at;
        private String  updated_at;
        @SerializedName("user_info")
        private AllCommentModel.Result.CommentList.UserInfo userInfo;
        @SerializedName("saved_comments")
        private List<AllCommentModel.Result.CommentList.SaveComment> saveComment;

    }


}
