package com.forumias.beta.ui.deta.forumias.home.model;

import java.util.PrimitiveIterator;

import lombok.Data;

@Data
public class AdsController {
    String status;
    String message;

    private ResultData  result;

    @Data
   public class ResultData{
        String description;
        int status;
    }
}
