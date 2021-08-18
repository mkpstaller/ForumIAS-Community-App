package com.forumias.beta.ui.deta.forumias.user;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.forumias.beta.ui.deta.forumias.user.user_paging.UserModel;

public interface UserInterface {
    void UserInformation(String name, String about, String image, String date);
    void userFollowUnfollow(UserModel.UserListing userListing, String followingData, String userId, String tagId, String status, String actType
            , LinearLayoutCompat tvFollow, LinearLayoutCompat tvFollowing, int position);
}
