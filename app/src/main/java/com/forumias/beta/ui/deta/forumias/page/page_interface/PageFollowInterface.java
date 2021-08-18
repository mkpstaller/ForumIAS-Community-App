package com.forumias.beta.ui.deta.forumias.page.page_interface;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.forumias.beta.pojo.GroupModel;

public interface PageFollowInterface {
    void pageFollowing(GroupModel.GroupListing pageList, String userId,
                       String tagId, String status, String actType, LinearLayoutCompat tvSubscribed,
                       LinearLayoutCompat tvUnSubscribed, int position);
}
