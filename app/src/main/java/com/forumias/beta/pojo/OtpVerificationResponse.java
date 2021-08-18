package com.forumias.beta.pojo;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class OtpVerificationResponse {
    private int status;
    private String message;
    private int totalCount;
    @SerializedName("data")
    public data data;

    @Data
    public class data{
        private int userId;
        private String name;
        private String email;
        private String fullName;
        private long mobile;
        private long rollNumber;
    }
}
