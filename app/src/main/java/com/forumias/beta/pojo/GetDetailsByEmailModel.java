package com.forumias.beta.pojo;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class GetDetailsByEmailModel {

    private String status;
    private String message;
    @SerializedName("detail")
    private DetailsUser details;

    @Data
    private class DetailsUser{
        private int id;
        private String name;
    }
}
