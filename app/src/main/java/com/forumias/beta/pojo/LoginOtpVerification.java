package com.forumias.beta.pojo;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class LoginOtpVerification {
    private int status;
    private String message;
    @SerializedName("data")
    private String email;
    private int totalCount;


}
