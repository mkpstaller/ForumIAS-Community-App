package com.forumias.beta.pojo;

import lombok.Data;

@Data
public class ReportModel {
    private String status;
    private String message;
  //  {"status":"fail","message":"You have already reported this post."}
}
