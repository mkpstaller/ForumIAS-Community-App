package com.forumias.beta.ui.deta.forumias.save_comment;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class SaveCommentModel {
    private String status;
    private String message;

    private InfoData info;

    @Data
    public class InfoData{
        @SerializedName("data")
        private List<SaveCommentList> saveCommentLists;

        @Data
        public class SaveCommentList{
            private int id;
            private int post_id;
            private int comment_id;
            private int user_id;
            private String created_at;

            @SerializedName("post_info")
            private PostInfo postInfo;
            @SerializedName("comment_info")
            private CommentInfo CommentInfo;

            @Data
            public class PostInfo{
                private int id;
                private String title;
                private String slug;
            }

            @Data
            public class CommentInfo{
                private int id;
                private String description;
                private String status;
            }
        }
    }
}
