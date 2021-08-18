package com.forumias.beta.myinterface;

import com.forumias.beta.pojo.ReadingModel;

import java.util.List;

public interface TagInterface {
    void addMyTag(int position);
    void deleteReading(int loginUserId , int postId , int actionType  , int position , List<ReadingModel.ReadingList> readingLists);
}
