package com.forumias.beta.ui.deta.forumias.comment.fragment.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class UseFullCommentModel {
    private String status;
    private String message;
    @SerializedName("result")
    protected List<AllCommentModel.Result.CommentList> result;
}
