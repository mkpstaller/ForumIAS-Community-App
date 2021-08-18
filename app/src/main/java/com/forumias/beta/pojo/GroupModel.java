package com.forumias.beta.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class GroupModel {
    private String status;
    private String message;
    @SerializedName("groups_listing")
    private List<GroupListing> groupListings;

    @Data
    public static class GroupListing {
        private int id;
        private String title;
        private int followStatus = 0;
        @SerializedName("tag_slug")
        private String tagSlug;
        @SerializedName("tag_img")
        private String tagImage;
        @SerializedName("color_code")
        private String colorCode;
        @SerializedName("description")
        private String description;
        private int is_verified;
        private int status;
        @SerializedName("created_by")
        private String createdBy;
        @SerializedName("user_id")
        private int  userId;
        @SerializedName("group_type")
        private int groupType;
        @SerializedName("is_page")
        private int isPage;
        @SerializedName("allow_create_post")
        private String allowCreatePost;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("post_info_count")
        private int postInfoCount;

        @SerializedName("follow_info")
        private FollowInfo followInfo;

        @Data
        public class FollowInfo{
            private int id;
            private int type;
            @SerializedName("user_id")
            private int userId;
            @SerializedName("follow_by")
            private String followBy;


        }
    }



}
