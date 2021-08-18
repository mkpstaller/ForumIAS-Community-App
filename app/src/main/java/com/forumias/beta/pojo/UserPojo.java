package com.forumias.beta.pojo;

import lombok.Data;

@Data
public class UserPojo {
    private String name;
    private boolean currentStatus;

    public UserPojo(String name , boolean currentStatus){
        this.name = name;
        this.currentStatus = currentStatus;
    }

}
