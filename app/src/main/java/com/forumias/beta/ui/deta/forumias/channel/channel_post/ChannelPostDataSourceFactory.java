package com.forumias.beta.ui.deta.forumias.channel.channel_post;

import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class ChannelPostDataSourceFactory extends DataSource.Factory<Integer, ChannelPostModel.ChannelAnnouncementsList> {
    private String slug ;
    private ProgressBar progressBar;
    private  int userId;
    ChannelPostDataSourceFactory(String slug , ProgressBar progressBar,int userId){
        this.slug = slug;
        this.progressBar = progressBar;
        this.userId = userId;
    }

    private MutableLiveData<PageKeyedDataSource<Integer,ChannelPostModel.ChannelAnnouncementsList>> itemLiveDataSource =
            new MutableLiveData<>();


    @NonNull
    @Override
    public DataSource<Integer, ChannelPostModel.ChannelAnnouncementsList> create() {
        ChannelPostDataSource channelPostDataSource = new ChannelPostDataSource(slug,progressBar,userId);
        itemLiveDataSource.postValue(channelPostDataSource);
        return channelPostDataSource;
    }

    MutableLiveData<PageKeyedDataSource<Integer,ChannelPostModel.ChannelAnnouncementsList>> getItemLiveDataSource(){
        return itemLiveDataSource;
    }
}
