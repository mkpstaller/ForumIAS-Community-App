package com.forumias.beta.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class FollowingORFollowerResponse {

    private String status;
    private String message;
    @SerializedName("total_followers")
    private String totalFollowers;
    @SerializedName("total_following")
    private String totalFollowing;
    @SerializedName("follower")
    private List<Follower> getFollowerList;
    @SerializedName("following")
    private List<Following> getFollowingList;

    @Data
    public class Follower{
        private int id;
        private String name;
        private String image;
    }

    @Data
    public class Following {
        private int id;
        private String name;
        private String image;
    }
}
