package com.forumias.beta.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class SearchModel {
    private String status;
    private String message;
    private int total_posts;

    @SerializedName("posts")
    private List<SearchPost> posts;

    @Data
    public class SearchPost{
        private int id;
        private String title;
        private String slug;
        private String name;
        private String image;
    }
}
