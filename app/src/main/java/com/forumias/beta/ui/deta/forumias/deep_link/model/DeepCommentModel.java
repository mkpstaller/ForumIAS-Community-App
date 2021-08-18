package com.forumias.beta.ui.deta.forumias.deep_link.model;

import java.util.List;

import lombok.Data;

@Data
public class DeepCommentModel {
    public String status;
    public String slug;
    public  CommentData data;
    public List<SubCommentsData> subComments;

    @Data
    public class CommentData {
        public int id;
        public int pid;
        public int post_id;
        public int views;
        public String  description;
        public String  created_at;
        public  GetPost get_post;
        public  UserInfoShare user_info;


        @Data
        public class  GetPost{
            public int id;
            public String  slug;
            public String  title;
        }

        @Data
        public class UserInfoShare{
            public int id;
            public String name;
            public String full_name;
            public String image;
            public String hide_real_name;
            public String is_verified;
        }
    }

    @Data
    public class SubCommentsData{
        public int id;
        public String description;
        public int views;
        public CommentData.UserInfoShare user_info;
    }
}


   /*   "user_info": {
            "id": 2,
            "name": "curious_kid",
            "full_name": "curious kid",
            "image": "https://one.forumias.com/images/tmp/avatar_1589890934_46009.jpeg",
            "hide_real_name": 1,
            "is_verified": 0
        },*/
