package com.forumias.beta.ui.deta.forumias.message.model;

import java.util.List;

import lombok.Data;

@Data
public class ChatListModel {
    public String status;
    public String message;
    public int chat_id;

    public ToUserDetails to_user_detail;
    public List<chatHistory> chat_histoty;

    @Data
    public class  ToUserDetails{
        public int id;
        public String  name;
        public String full_name;
        public int hide_real_name;
        public String image;
        public   int  type;
        public   String  about;
        public   int  is_verified;
        public   int  status;
    }


    @Data
    public  class chatHistory{
        public int id;
        public int chat_id;
        public int to_user;
        public int from_user;
        public int msg_type;
        public String  message;
        public String  file;
        public String  file_extension;
        public int  status;
        public int  read_flag;
        public int  notification_flag;
        public String  created_at;

    }

}


