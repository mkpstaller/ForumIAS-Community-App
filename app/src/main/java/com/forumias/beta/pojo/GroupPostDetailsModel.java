package com.forumias.beta.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class GroupPostDetailsModel {
    private String status;
    private String message;
    @SerializedName("posts")
    private List<GroupPostList> groupPostList;
    @SerializedName("followers")
    private List<Followers> followersList;

    @Data
    public class GroupPostList {
        private int id;
        @SerializedName("user_id")
        private int userId;
        @SerializedName("posted_by")
        private int postedBy;
        @SerializedName("category_id")
        private int categoryId;
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
        private int shared_with;
        @SerializedName("is_discussion")
        private int isDiscussion;
        @SerializedName("is_announcement")
        private int isAnnouncement;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;
      /*  @SerializedName("user_info")
        public HomeLatestPostModel.MyLatestPost.UserInfoModel userInfo;
        @SerializedName("comment_info")
        private List<CommentInfo> commentInfoList;
        @SerializedName("like_info")
        public HomeLatestPostModel.MyLatestPost.LikeInfo likeInfo;

        @Data
        public class CommentInfo {
            private int id;
            @SerializedName("post_id")
            private int postId;
            @SerializedName("user_id")
            private int userId;
            private int status;

        }*/

    }

    @Data
    public class Followers{
        private String name;
        private String image;
        private int id;
        private int type;

    }

}
