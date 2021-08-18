package com.forumias.beta.ui.deta.forumias.splash;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class PostSettingModel {
    private String status;
    private String message;

    @SerializedName("result")
    private List<SettingList> settingLists;

    @Data
    public class SettingList{
        private int id;
        private int type;
        private String title;
        private String description;
        private int visibility;

    }
}
