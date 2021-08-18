package com.forumias.beta.pojo;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class VerificationOtp {
    private int status;
    private String message;
    private int totalCount;
    @SerializedName("data")
    public RegisterBaseResponse.data data;

    @Data
    public class data{
        private int id;
        private int userId;
        private String name;
        private String email;
        private String fullName;
        private int mobile;
        private int rollNumber;
    }
}
