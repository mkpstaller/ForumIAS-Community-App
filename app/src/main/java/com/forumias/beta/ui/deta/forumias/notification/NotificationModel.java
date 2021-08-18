package com.forumias.beta.ui.deta.forumias.notification;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class NotificationModel {

    private String status;
    @SerializedName("notifications")
    private MyNotification notification;


    @Data
    public class MyNotification {
        @SerializedName("data")
        private List<NotificationData> notificationDataList;

        @Data
        public class NotificationData{
            private boolean clickStatus;
            private String myName;
            private int id;
            private int module;
            private int action;
            private int post_id;
            private int user_id;
            private int from_user;
            private int group_id;
            private int group_id_1;
            private int channel_id;
            private int channel_id_1;
            private int to_user;
            private String multi_to_user;
            private String message;
            private String read_by;
            private String notification_type;
            private int  status;
            private int  mail_sent;
            private String  created_at;
            @SerializedName("post_info")
            private Notif_PostInfo notif_postInfo;
            @SerializedName("user_info")
            private Notif_UserInfo notif_userInfo;
            @SerializedName("group_info")
            private Notif_GroupInfo notif_groupInfo;
            @SerializedName("group_info1")
            private Notif_GroupInfo notif_groupInfo_one;
            @SerializedName("channel_info")
            private Notifi_ChannelInfo notifi_channelInfo;
            @SerializedName("channel_info1")
            private Notifi_ChannelInfo notif_channelInfo_one;
            @SerializedName("from_user_info")
            private Notif_UserInfo notif_fromUserInfo;



            @Data
            public class Notif_PostInfo{
                private int id;
                private String title;
                private String slug;
                private int channel_id;
            }

            @Data
            public class Notif_UserInfo {
                private int id;
                private String name;
                private String full_name;
                private int hide_real_name;
                private String image;

            }

            @Data
            public class Notifi_ChannelInfo{
                private int id;
                private String title;
                private String channel_slug;
            }

            @Data
            public class Notif_GroupInfo{
                private int id;
                private String title;
                private String tag_slug;
                private String tag_img;
                private int is_page;
            }
        }
    }

}
