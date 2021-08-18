package com.forumias.beta.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class ReadingModel {
    private String status;
    private String message;
    @SerializedName("reading_list")
    private ReadingListData readingListData;

    @Data
    public class ReadingListData{
        @SerializedName("data")
        private List<ReadingList> readingLists;
    }

    @Data
    public static class ReadingList {
        private int id;
        @SerializedName("post_id")
        private int postId;
        @SerializedName("auth_id")
        private int authId;
        @SerializedName("saved_type")
        private int savedType;
        @SerializedName("starred")
        private int starred;
        @SerializedName("mark_read")
        private int markRead;
        private int status;
        private String title;
        private String slug;
        private String tag;
        private boolean isSelected;

    }


}
