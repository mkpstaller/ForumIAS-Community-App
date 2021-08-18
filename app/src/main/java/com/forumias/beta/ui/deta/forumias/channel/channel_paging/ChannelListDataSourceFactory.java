package com.forumias.beta.ui.deta.forumias.channel.channel_paging;

import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.forumias.beta.pojo.ChannelModel;

public class ChannelListDataSourceFactory extends DataSource.Factory<Integer, ChannelModel.ChannelList> {

    private String channelType;
    private ProgressBar progressBar;
    private int loginUserId;

    public ChannelListDataSourceFactory(String channelType , ProgressBar progressBar , int loginUserId){
        this.channelType = channelType;
        this.progressBar = progressBar;
        this.loginUserId = loginUserId;
    }

    private MutableLiveData<PageKeyedDataSource<Integer,ChannelModel.ChannelList>> itemLiveDataSource =
            new MutableLiveData<>();


    @NonNull
    @Override
    public DataSource<Integer, ChannelModel.ChannelList> create() {
        ChannelListDataSource channelListDataSource = new ChannelListDataSource(channelType , progressBar , loginUserId);
        itemLiveDataSource.postValue(channelListDataSource);
        return channelListDataSource;
    }

    MutableLiveData<PageKeyedDataSource<Integer,ChannelModel.ChannelList>> getItemLiveDataSource(){
        return itemLiveDataSource;
    }
}
