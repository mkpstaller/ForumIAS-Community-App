package com.forumias.beta.ui.deta.forumias.channel.channel_paging;

import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.forumias.beta.pojo.ChannelModel;

public class ChannelListViewModel extends ViewModel {
    private LiveData<PagedList<ChannelModel.ChannelList>> itemDataList;
    private MutableLiveData<PageKeyedDataSource<Integer,ChannelModel.ChannelList>> liveDataSource;

    public LiveData<PagedList<ChannelModel.ChannelList>> getItemData(String channelType , ProgressBar progressBar,int loginUserId){
        if(itemDataList == null){
            itemDataList = new MutableLiveData<>();
            loadChannelList(channelType,progressBar,loginUserId);
        }
        return itemDataList;
    }
    public void invalidateDataSource() {
        liveDataSource.getValue().invalidate();
    }

    public void loadChannelList(String channelType , ProgressBar progressBar , int loginUserId){
        ChannelListDataSourceFactory channelListDataSourceFactory = new ChannelListDataSourceFactory(channelType ,progressBar , loginUserId);
        liveDataSource = channelListDataSourceFactory.getItemLiveDataSource();
        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ChannelListDataSource.PAGE_SIZE)
                        .build();

        itemDataList = (new LivePagedListBuilder(channelListDataSourceFactory, config)).build();
    }
}
