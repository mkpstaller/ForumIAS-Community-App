package com.forumias.beta.ui.deta.forumias.comment.fragment.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class AllCommentModel {
    private String status;
    private String message;
    @SerializedName("result")
    private Result result;

    @Data
    public static class Result{
        private int current_page;
        @SerializedName("data")
        private List<CommentList>commentLists;

        @Data
        public static class CommentList{
            private int id;
            private int pid;
            private int post_id;
            private int user_id;
            private int page_id;
            private int group_id;
            private int soft_delete;
            private String description;
            private int status;
            private int views;
            private String created_at;
            private String updated_at;
            private boolean saveCommentStatus;

            @SerializedName("user_info")
            private UserInfo userInfo;
            @SerializedName("get_upvotes")
            private UpVotes upVotes;
            @SerializedName("tag_info")
            private TagInfo tagInfo;
            @SerializedName("saved_comments")
            private List<SaveComment> saveComments;

            @Data
            public class UserInfo{
                private int id;
                private String name;
                private String full_name;
                private String image;
                private int hide_real_name;
                private int is_verified;

            }

            @Data
            public static class UpVotes{
                private int id;
                private int post_id;
                private int comment_id;
                private String user_ids;
                private int upvote_count;
            }

            @Data
            public class TagInfo{
                private int id;
                private String title;
                private String tag_slug;
                private String tag_img;
            }

            @Data
            public class SaveComment{
                private int id;
                private int user_id;
            }
        }
    }


}
