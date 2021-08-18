package com.forumias.beta.myinterface;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public interface MyFollowingInterface {
    void followingTag(int position, String slug, RecyclerView recyclerView, AppCompatTextView tvSeeAll, boolean clickStatus);
}
