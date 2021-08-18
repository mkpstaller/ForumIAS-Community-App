package com.forumias.beta.pojo;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ProfileImageModel {
    private int status;
    private String message;
    @SerializedName("data")
    private ProfileData profileData;

    @Data
    public class ProfileData{
        private String image;
    }

    // {"status":1,"message":"Profile Updated Successfully","data":{"image":"https:\/\/one.forumias.com\/\/images\/tmp\/avatar_1591697900_52066.jpg","auth_id":52066},"totalCount":0}

}
