package com.forumias.beta.ui.deta.forumias.comment.ui.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PollModel {
    public String status;
    public String message;
    public int total_vote;
    public int quiz_correct;
    public int selected_opt;
    public List<PollInfo> poll_info;

    @Data
    public static class PollInfo{
        public int id;
        public int post_id;
        public int user_id;
        public String name;
        public int is_correct;
        public String voted_by;
        public String created_at;
        public String updated_at;
        public boolean isSelectAt = false;


    }

}
