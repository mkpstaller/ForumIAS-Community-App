package com.forumias.beta.pojo;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class BookMarkResponse {
    private String status;
    private String message;
    private String userName;
    private int id;
    @SerializedName("account_status")
    private int accountStatus;
}
