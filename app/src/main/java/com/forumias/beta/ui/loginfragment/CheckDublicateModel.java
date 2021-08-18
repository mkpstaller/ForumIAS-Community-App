package com.forumias.beta.ui.loginfragment;

import lombok.Data;

@Data
public class CheckDublicateModel {
    public String status;
    public int flag;
    public String message;

    public CheckData data;

    @Data
    public class CheckData{
        public String UserName;
        public String Email;
        public String Phone;

    }

}

/*{"status":"success","flag":1,"message":"User available","data"
        :{"UserName":"musa","Email":"musaphir@flaviant.com","Phone":"9871751739"}}*/
