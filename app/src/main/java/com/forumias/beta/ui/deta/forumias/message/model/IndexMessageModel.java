package com.forumias.beta.ui.deta.forumias.message.model;

import java.util.List;

import lombok.Data;

@Data
public class IndexMessageModel {

    public String status;
    public String message;
    public List<Result> result;

    @Data
    public class Result{
        public int id;
        public int chat_id;
        public int from_user;
        public int to_user;
        public int msg_type;
        public int status;
        public int read_flag;
        public int notification_flag;
        public int frm_verified;
        public int from_isolated;
        public int to_verified;
        public int to_isolated;
        public String  message;
        public String  file;
        public String  file_extension;
        public String created_at;
        public String  frm_name;
        public String  frm_u_img;
        public String  to_name;
        public String  to_u_img;

    }

}


     /*   "id": 23302,
        "chat_id": 3482,
        "from_user": 2570,
        "to_user": 2,
        "msg_type": 0,
        "message": "Hey, do we have a tentative date for SFG Level 2?",
        "file": null,
        "file_extension": null,
        "status": 1,
        "read_flag": 1,
        "notification_flag": 1,
        "ip_address": "110.235.216.221",
        "created_at": "2021-01-16 23:58:47",
        "updated_at": "2021-02-11 16:21:42",
        "frm_name": "tuckerbudzyn",
        "frm_u_img": "https://vanillicon.com/0afca33fc313a609ea88cb385251dc63_100.png",
        "frm_verified": 0,
        "from_isolated": 0,
        "to_name": "curious_kid",
        "to_u_img": "https://one.forumias.com/images/tmp/avatar_1589890934_46009.jpeg",
        "to_verified": 0,
        "to_isolated": 0
        },*/
