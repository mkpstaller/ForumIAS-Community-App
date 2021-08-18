package com.forumias.beta.ui.deta.forumias.page.page_paging_adapter;

import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;


public class PagePostViewModel extends ViewModel {
    private LiveData<PagedList<PagePostModel.PageData>> itemList;
    private MutableLiveData<PageKeyedDataSource<Integer, PagePostModel.PageData>> liveDataSource;


    public LiveData<PagedList<PagePostModel.PageData>> getItemList(String slug , ProgressBar progressBar){

        if(itemList == null){
            itemList = new  MutableLiveData<>();
            loadChannelPost(slug , progressBar);
        }
        return itemList;
    }

    private void loadChannelPost(String slug , ProgressBar progressBar){
        PagePostDataSourceFactory channelPostDataSourceFactory = new PagePostDataSourceFactory(slug , progressBar);
        liveDataSource = channelPostDataSourceFactory.getItemLiveDataSource();
        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(PagePostDataSource.PAGE_SIZE)
                        .build();

        itemList = (new LivePagedListBuilder(channelPostDataSourceFactory, config)).build();

    }
}
