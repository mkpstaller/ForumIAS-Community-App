package com.forumias.beta.ui.deta.forumias.comment.ui.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class LikeUserModel {
    private String status;
    private String message;
    @SerializedName("result")
    private List<LikeUserList> likeUserLists;

    @Data
    public class LikeUserList{
        private String name;
        private String img;
    }
}
