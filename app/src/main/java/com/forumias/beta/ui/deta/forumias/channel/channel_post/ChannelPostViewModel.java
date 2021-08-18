package com.forumias.beta.ui.deta.forumias.channel.channel_post;

import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

public class ChannelPostViewModel extends ViewModel {

    private LiveData<PagedList<ChannelPostModel.ChannelAnnouncementsList>> itemLiveData;
    private MutableLiveData<PageKeyedDataSource<Integer,ChannelPostModel.ChannelAnnouncementsList>> liveDataSource;

    public LiveData<PagedList<ChannelPostModel.ChannelAnnouncementsList>> getItemData(String slug ,
                                            ProgressBar progressBar,int userId){
        if(itemLiveData == null){
            itemLiveData = new MutableLiveData<>();
            loadChannelPostList(slug,progressBar,userId);
        }
        return itemLiveData;
    }

    public void loadChannelPostList(String slug , ProgressBar progressBar,int userId){
        ChannelPostDataSourceFactory channelPostDataSourceFactory = new ChannelPostDataSourceFactory(slug,progressBar,userId);
        liveDataSource = channelPostDataSourceFactory.getItemLiveDataSource();
        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ChannelPostDataSource.PAGE_SIZE)
                        .build();

        itemLiveData = (new LivePagedListBuilder(channelPostDataSourceFactory, config)).build();
    }
}
