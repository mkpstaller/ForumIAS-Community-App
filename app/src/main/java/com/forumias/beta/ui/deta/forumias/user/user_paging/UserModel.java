package com.forumias.beta.ui.deta.forumias.user.user_paging;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
public class UserModel {

    private String status;
    private String message;
    private String follow_info;
    @SerializedName("users_listing")
    private List<UserListing> userListing;

    @Data
    public static class UserListing{

        private String isFollow;
        private int id;
        private int auth_id;
        private String token;
        private int type;
        private String name;
        private String image;
        @SerializedName("full_name")
        private String fullName;
        private String email;
        private int status;
        @SerializedName("hide_real_name")
        private int  hideRealName;
        private int onboarding;
        private String about;
        @SerializedName("mobile_number")
        private String mobileNumber;
        @SerializedName("roll_number")
        private String rollNumber;
        @SerializedName("email_varified_at")
        private String emailVerifiedAt;
        @SerializedName("notify_view_date")
        private String notifyViewDate;
        @SerializedName("profile_view")
        private String profileView;
        private String featured;
        private int is_verified;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("follow_info_count")
        private String followInfoCount;
        private int followStatus = 0;
        private String followId = "0";
        @SerializedName("follow_info")
        private FollowUserInfo followInfo;

        public class FollowUserInfo{

        }


    }
}