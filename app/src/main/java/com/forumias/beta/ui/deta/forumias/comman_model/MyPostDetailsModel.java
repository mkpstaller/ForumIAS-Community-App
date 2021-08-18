package com.forumias.beta.ui.deta.forumias.comman_model;

import com.forumias.beta.ui.deta.forumias.home.model.HomeLatestPostModel;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class MyPostDetailsModel {
    private String status;
    private String message;
    @SerializedName("detail")
    private Details details;
    @SerializedName("added_to_myspace")
    private String addToMySpace;

    @Data
    public class Details{
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
        private String desc_email;
        private int status;
        private int is_private;
        private int featured;
        private int view_count;
        private String shared_with;
        private int is_discussion;
        private int is_announcement;
        private String commented_at;
        private String created_at;
        @SerializedName("like_info")
        private HomeLatestPostModel.MyLatestPost.LikeInfo likeInfo;


    }

    private  int following;
    private int comment_count;
    @SerializedName("user_info")
    private MyUserInfo userInfo;
    @SerializedName("page_info")
    private MyPageInfo myPageInfo;

    @Data
    public class MyUserInfo{
        private int id;
        private String name;
        private String full_name;
        private String image;
        private String about;
        private int hide_real_name;
        private int is_verified;

    }

    @Data
    public class MyPageInfo{
        private int id;
        private String title;
        private String tag_slug;
        private String tag_img;
        private String color_code;
        private int is_verified;
    }

}
