package com.forumias.beta.ui.deta.forumias.channel.channelInterface;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.forumias.beta.pojo.ChannelModel;

public interface ChannelSubscribeInterface {
    void channelSubscribe(ChannelModel.ChannelList channelModel, String userId,
                          String tagId, String status, String actType, LinearLayoutCompat tvSubscribed,
                          LinearLayoutCompat llUnSubscribed, AppCompatTextView tvUnSubscribed, int position);
 /*   void privateChannelSubscribe(ChannelModel.ChannelList channelModel, String userId,
                          String tagId, String status,String actType, AppCompatTextView tvSubscribed,
                          AppCompatTextView tvUnSubscribed, int position);*/

    void privateChannelSubscribe(ChannelModel.ChannelList channelModel, int loginUserId, int id,
                                 int actType, LinearLayoutCompat tvSubscribed,
                                 LinearLayoutCompat llUnSubscribed ,AppCompatTextView tvUnSubscribed, int position);
}
