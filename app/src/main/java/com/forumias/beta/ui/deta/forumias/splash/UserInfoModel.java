package com.forumias.beta.ui.deta.forumias.splash;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class UserInfoModel {
    private String status;
    private String message;

    @SerializedName("detail")
    private UserDetails userDetails;
    @SerializedName("email_setting")
    private EmailSetting emailSetting;

    @Data
    public class  UserDetails{
        private int id;
        private String name;
        private String email;
        private String full_name;
        private String image;
        private String about;
        private int type;
        private int hide_real_name;
        private int profile_view;
        private int total_posts;
        private int followers;
        private int following;
        private int follow_flag;
        private String updated_at;
        private String created_at;
        private int is_verified;

    }

    @Data
    public class EmailSetting{
        private int id;
        private int user_id;
        private int post;
        private int tag;
        private int follow_me;
        private int group_accepted;
        private int channel_accepted;
        private int answer_question;
        private int comment_like;
        private int request_ans;

    }

}
