package com.forumias.beta.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class ChannelModel {

    private String status;
    private String message;
    @SerializedName("channels_listing")
    private ChannelListing channelListing;

    @Data
    public class  ChannelListing{
        private int current_page;
        @SerializedName("data")
        private List<ChannelList> channelListList;
    }


    @Data
    public static class ChannelList{
        private int id;
        private int subscribeStatus = 0;
        private String title;
        @SerializedName("channel_slug")
        private String channelSlug;
        @SerializedName("channel_img")
        private String channelImg;
        @SerializedName("color_code")
        private String colorCode;
        private String description;
        private int status;
        @SerializedName("created_by")
        private int createdBy;
        @SerializedName("user_id")
        private int userId;
        @SerializedName("channel_admins")
        private String channelAdmin;
        @SerializedName("channel_type")
        private int channelType;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("channel_posts_count")
        private int postCount;

        @SerializedName("follow_info")
        private FollowInfo followInfo;

        @SerializedName("follow_request")
        private FollowRequest followRequest;

        @Data
        public class FollowInfo{
            private int id;
            private int type;
            @SerializedName("tag_id")
            private int tagId;
            @SerializedName("user_id")
            private int userId;
            @SerializedName("follow_by")
            public String followBy;
            @SerializedName("created_at")
            private String createdAt;
            @SerializedName("updated_at")
            private String updatedAt;

        }

        @Data
        public class FollowRequest{
            public int id;
            private int group_id;
            private int user_id;
            private int status;
            private int request_type;
            private String created_at;
        }

    }

}
