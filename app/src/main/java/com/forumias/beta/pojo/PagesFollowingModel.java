package com.forumias.beta.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PagesFollowingModel {
    private String status;
    private String message;
    @SerializedName("data")
    private List<Following> following;

    @Data
    public static class Following{
        private int id;
        private String title;
        @SerializedName("tag_slug")
        private String tagSlug;
        @SerializedName("channel_slug")
        private String channel_slug;
        @SerializedName("tag_img")
        private String tagImage;
        @SerializedName("channel_img")
        private String channelImage;
        @SerializedName("color_code")
        private String colorCode;
        @SerializedName("is_page")
        private String isPage;
        @SerializedName("group_type")
        private String groupType;
        @SerializedName("description")
        private String description;

    }
}
