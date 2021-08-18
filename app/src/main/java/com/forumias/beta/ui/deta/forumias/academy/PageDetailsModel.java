package com.forumias.beta.ui.deta.forumias.academy;

import android.graphics.pdf.PdfDocument;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class PageDetailsModel {

private String status;
private String message;
@SerializedName("info")
private PageInfo pageInfo;


    @Data
    public class PageInfo{

        private int id;
        private String title;
        private String tag_slug;
        private String tag_img;
        private String color_code;
        private String description;
        private int is_verified;
        private String post_info_count;

        @SerializedName("follow_info")
        private FollowInfo followInfo;

        @Data
        public class FollowInfo{
            private int id;
            private int type;
            private String user_id;
            private String follow_by;
            private String created_at;
            private String updated_at;

        }
   }

}
