package com.forumias.beta.pojo;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PostCountModel {
    private  String status;
    @SerializedName("displayName")
    private String displayName;
    @SerializedName("post_count")
    private String postCount;
    @SerializedName("follow_count")
    private String followCount;
    @SerializedName("comment_count")
    private String CommentCount;
    @SerializedName("member_since")
    private String memberSince;

}
