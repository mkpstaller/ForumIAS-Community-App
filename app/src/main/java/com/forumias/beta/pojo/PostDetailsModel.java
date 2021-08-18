package com.forumias.beta.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PostDetailsModel {
    private String status;
    private String message;
    @SerializedName("detail")
    private PostDetails postDetails;
    @SerializedName("Posted By")
    private PostedBy postedBy;
    private int following;
    @SerializedName("like_user_info_name")
    private List<LikeUserName> likeUserName;
    @SerializedName("like_user_info_image")
    private List<LikeUserImage> likeUserImage;
    @SerializedName("comment_listing")
    private List<CommentListing> commentListings;

    @Data
    public class PostDetails{
        private  int id;
        @SerializedName("user_id")
        private int userId;
        @SerializedName("posted_by")
        private int postedBy;
        @SerializedName("category_id")
        private int categoryBy;
        @SerializedName("tag_id")
        private int tagId;
        @SerializedName("channel_id")
        private int channelId;
        @SerializedName("bookmarked_by")
        private String bookmarkedBy;
        @SerializedName("tag_name")
        private String tagName;
        @SerializedName("catchy_text")
        private String catchyText;
        private int type;
        private String title;
        private String slug;
        private String img;
        @SerializedName("color_code")
        private String colorCode;
        private String description;
        private int status;
        @SerializedName("is_private")
        private int isPrivate;
        private int featured;
        @SerializedName("view_count")
        private int viewCount;
        @SerializedName("shared_with")
        private String sharedWith;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("like_info")
        public  LikeInfo likeInfo;

        @Data
        public class LikeInfo{
            @SerializedName("like_count")
            private int likeCount;
        }


    }
    @Data
    public class  PostedBy{
        private int id;
        private String name;
        @SerializedName("full_name")
        private String fullName;
        private String image;
        private String about;
        @SerializedName("hide_real_name")
        private int hideRealName;
    }

    @Data
    public class  LikeUserName{
        private String name;
    }

    @Data
    public class  LikeUserImage{
        private String url;
    }


    @Data
    public class CommentListing{
        private int id;
        @SerializedName("post_id")
        private int postId;
        @SerializedName("user_id")
        private int userId;
        private String description;
        private int status;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;

        @SerializedName("user_info")
        private UserInfo userInfo;
        @SerializedName("sub_comment_info")
        private List<SubCommentInfo> subCommentInfo; // reply on comment data model

        @Data
        public class UserInfo{
            private int id;
            private String name;
            @SerializedName("full_name")
            private String fullName;
            private String image;
            @SerializedName("hide_real_name")
            private int hideRealName;

        }


        @Data
        public class SubCommentInfo{
            private int id;
            @SerializedName("post_id")
            private int postId;
            @SerializedName("comment_id")
            private int commentId;
            private String description;
            private int status;
            @SerializedName("created_at")
            private String createdAt;
            @SerializedName("updated_at")
            private String updatedAt;

        }
    }
}
