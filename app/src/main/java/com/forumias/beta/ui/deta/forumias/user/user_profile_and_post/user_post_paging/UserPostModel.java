package com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.user_post_paging;

import com.forumias.beta.ui.deta.forumias.home.model.HomeLatestPostModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class UserPostModel {
    @SerializedName("stories")
    private MyStories myStories;

    @Data
    public class MyStories{
        private int current_page;
        @SerializedName("data")
        private List<MyStoriesList> myStoriesLists;
    }

    @Data
    public static class MyStoriesList{

        private int id;
        @SerializedName("user_id")
        private int userId;
         @SerializedName("posted_by")
          private int postedBy;
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
          @SerializedName("is_discussion")
          private int isDiscussion;
          @SerializedName("is_announcement")
          private int isAnnouncement;
         /* @SerializedName("last_user_commented")
           private int lastUserCommented;*/

        @SerializedName("comment_info_count")
        private int commentInfoCount;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("commented_at")
        private String commented_at;


        @SerializedName("user_info")
        private HomeLatestPostModel.MyLatestPost.UserInfo userInfo;
        @SerializedName("user_info_by")
        private HomeLatestPostModel.MyLatestPost.UserInfoBy userInfoBy;
        @SerializedName("like_info")
        private HomeLatestPostModel.MyLatestPost.LikeInfo likeInfo;
        @SerializedName("last_user_commented")
        private HomeLatestPostModel.MyLatestPost.LastUserCommented lastUserCommented;
     /*    @SerializedName("group_info")
        private List<HomeLatestPostModel.MyLatestPost.GroupInfo> groupInfo;

        public boolean isCheckedLike() {
            return isCheckedLike;
        }

        public boolean setCheckedLike(boolean checkedLike) {
            isCheckedLike = checkedLike;
            return checkedLike;
        }

        private boolean isCheckedLike;

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
            @SerializedName("is_verified")
            private int isVerified;
        }

        @Data
        public class UserInfoBy{
            private int id;
            private String name;
            @SerializedName("full_name")
            private String fullName;
            private String image;
            private String about;
            @SerializedName("hide_real_name")
            private int hideRealName;
            @SerializedName("is_verified")
            private int isVerified;
        }

        @Data
        public class  LikeInfo{
            @SerializedName("post_id")
            private int postId;
            @SerializedName("user_ids")
            private String likeUserId;
            @SerializedName("like_count")
            private int likeCount;
        }


        @Data
        public class GroupInfo{
            private int id;
            private String title;
            private String tag_slug;
            private int is_page;
            private String tag_img;
            private int is_verified;
        }*/

    }



}
