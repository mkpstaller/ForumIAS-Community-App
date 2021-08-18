package com.forumias.beta.ui.deta.forumias.profile.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class FollowersModel {
    private String status;
    private  int total_followers;
    private  int total_following;

    @SerializedName("follower")
    private List<FollowerList> followerLists;
    @SerializedName("following")
    private List<FollowerList> followingLists;

    @Data
    public class FollowerList{
        private int id;
        private String name;
        private String image;
        private int is_verified;
    }

   /* @Data
    public class FollowingList{
        private int id;
        private String name;
        private String image;
        private int is_verified;
    }*/

}
