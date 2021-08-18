package com.forumias.beta.pojo;

import lombok.Data;

@Data
public class DefaultPojo {
    private  int status;
    private String message;
    private int totalCount;
    private boolean isSelected;
    private int  colorCode;

    public DefaultPojo(String message) {
        this.message = message;
    }

    public DefaultPojo(){}

    public DefaultPojo(int colorCode) {
        this.colorCode = colorCode;
    }
}
