package com.forumias.beta.ui.deta.forumias.channel.channel_post;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class ChannelPostModel {
    private String status;
    private String message;
    private int total_posts;
    @SerializedName("channel_detail")
    private ChannelDetails channelDetails;
    @SerializedName("announcements")
    private List<ChannelAnnouncementsList> channelAnnouncementsLists;
    @SerializedName("posts")
    private ChannelPost channelPost;

    @Data
    public class ChannelDetails{
        private int id;
        private String title;
        private String channel_slug;
        private String channel_img;
        private String color_code;
        private String description;
        private int status;
        private int created_by;
        private int user_id;
        private String channel_admins;
        private int academy_channel;
        private int channel_type;
        private String created_at;
        private String updated_at;

        @SerializedName("follow_info")
        private FollowInfo followInfo;

        @Data
        public class FollowInfo {
            private int id;
            private int type;
            private int tag_id;
            private String  user_id;
            private String  follow_by;
            private String  created_at;
            private String  updated_at;

        }
    }

    @Data
    public class ChannelAnnouncementsList{
        private int id;
        private int tid;
        private int user_id;
        private int posted_by;
        private String category_id;
        private int tag_id;
        private int channel_id;
        private int  event_id;
        private String bookmarked_by;
        private String tag_name;
        private String catchy_text;
        private int type;
        private String title;
        private String slug;
        private String img;
        private String color_code;
        private String description;
        private int desc_email;
        private int status;
        private int is_private;
        private int featured;
        private int view_count;
        private String shared_with;
        private int is_discussion;
        private int is_announcement;
        private int last_user_commented;
        private String  commented_at;
        private String  created_at;
        private String  updated_at;
       @SerializedName("channel_info")
        private ChannelInfo channelInfo;

       @Data
        public class ChannelInfo{
           private int id;
           private String title;
           private String channel_slug;
       }
    }


    @Data
    public class ChannelPost{
        @SerializedName("data")
        private List<ChannelAnnouncementsList> channelPostList;
    }
}
