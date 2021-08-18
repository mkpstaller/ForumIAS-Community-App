package com.forumias.beta.ui.deta.forumias.page.page_paging_adapter;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PagePostModel {
    private String status;
    private String message;
    @SerializedName("announcements")
    private List<PageData> announcementsLists;
    @SerializedName("posts")
    private PagePosts pagePost;


    @Data
    public class PagePosts {
        private int current_page;
        @SerializedName("data")
        private List<PageData> pagePostList;
    }

    @Data
    public static class PageData {
        private int id;
        private int tid;
        private int user_id;
        private int posted_by;
        private String category_id;
        private int tag_id;
        private int channel_id;
        private int event_id;
        private String bookmarked_by;
        private String tag_name;
        private String catchy_text;
        private int type;
        private String title;
        private String slug;
        private String img;
        private String color_code;
        private String description;
        private int  desc_email;
        private int  status;
        private int  is_private;
        private int  featured;
        private int  view_count;
        private int  shared_with;
        private int  is_discussion;
        private int  is_announcement;
        private int  last_user_commented;
        private String  commented_at;
        private String  created_at;
        private String  updated_at;
        private int comment_info_count;
        @SerializedName("like_info")
        private LikeInfo likeInfo;

        @Data
        public static class LikeInfo{
            private int post_id;
            private String user_ids;
            private int like_count;

        }


    }

/*
    @Data
    public static class PageData {
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
        private String postInfoCount;

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
*/



}
