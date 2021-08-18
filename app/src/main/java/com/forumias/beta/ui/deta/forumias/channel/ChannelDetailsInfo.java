package com.forumias.beta.ui.deta.forumias.channel;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ChannelDetailsInfo {

    private String status;

    @SerializedName("info")
    private ChannelInfo channelDetailsInfo;

    @Data
    public class ChannelInfo{
        private int id;
        private String title;
        private String channel_slug;
        private String channel_img;
        private String color_code;
        private String description;
        private int channel_type;
        private int academy_channel;
        private int channel_posts_count;

        @SerializedName("follow_info")
        private ChannelFollowInfo channelFollowInfo;
        @SerializedName("follow_request")
        private MyFollowRequest followRequest;

        @Data
        public class ChannelFollowInfo{
           private int id;
           private int type;
           private int tag_id;
           private String user_id;
           private String follow_by;
           private String created_at;

       }
        @Data
        public class MyFollowRequest{
            public int id;
            private int group_id;
            private int user_id;
            private int status;
            private int request_type;
            private String created_at;
        }

    }

}
