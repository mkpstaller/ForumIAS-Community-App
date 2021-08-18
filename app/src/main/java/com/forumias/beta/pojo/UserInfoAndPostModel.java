package com.forumias.beta.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class UserInfoAndPostModel {
   @SerializedName("following_count")
    private int followingCount;
    @SerializedName("post_count")
    private int postCount;
    @SerializedName("user_info")
    private UserInfo userInfo;
    @SerializedName("user_posts")
    private List<UserPosts> userPosts;
    @SerializedName("stories")
    private List<Stories> storiesList;

    @Data
    public class UserInfo{
        private int id;
        private String name;
        @SerializedName("full_name")
        private String fullName;
        private String image;
        private String about;
        @SerializedName("hide_real_name")
        private int hideRealName;
        @SerializedName("profile_view")
        private int profileView;
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("follow_info")
        private FollowInfo followInfo;

        @Data
        public class FollowInfo{
            @SerializedName("user_id")
            private int userId;
            @SerializedName("follow_by")
            private String followBy;
        }
    }

    @Data
    public class UserPosts{

    }


    @Data
    public class Stories{

        private int id;
        @SerializedName("user_id")
        private int userId;
        @SerializedName("posted_by")
        private int postedBy;
        @SerializedName("category_id")
        private String categoryId;
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
        @SerializedName("type")
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

        @SerializedName("user_info")
        private StoryUserInfo storyUserInfo;
        @SerializedName("like_info")
        private LikeInfo likeInfo;


        @Data
        public class StoryUserInfo{
            private int id;
            private String name;
            @SerializedName("full_name")
            private String fullName;
            private String image;
            @SerializedName("hide_real_name")
            private int hideRealName;

        }
        @Data
        public class  LikeInfo{
            @SerializedName("post_id")
            private int postId;
            @SerializedName("user_ids")
            private String userId;
            @SerializedName("like_count")
            private String likeCount;
        }
    }


}
