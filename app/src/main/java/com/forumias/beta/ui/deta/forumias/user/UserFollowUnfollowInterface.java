package com.forumias.beta.ui.deta.forumias.user;

import com.forumias.beta.ui.deta.forumias.profile.model.FollowersModel;

import java.util.List;

public interface UserFollowUnfollowInterface {
    void followUnfollowing(int loginUserId, int id, int status, List<FollowersModel.FollowerList> followingUserList, int position);
}
