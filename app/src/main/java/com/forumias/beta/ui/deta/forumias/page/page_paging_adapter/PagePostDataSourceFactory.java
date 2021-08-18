package com.forumias.beta.ui.deta.forumias.page.page_paging_adapter;

import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class PagePostDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, PagePostModel.PageData>>  itemLiveDataSource
            = new MutableLiveData<>();
    private String slug;
    private ProgressBar progressBar;
    public PagePostDataSourceFactory(String slug , ProgressBar progressBar) {
        this.slug = slug;
        this.progressBar = progressBar;
    }


    @NonNull
    @Override
    public DataSource create() {
        PagePostDataSource channelPostDataSource = new PagePostDataSource(slug , progressBar);
        itemLiveDataSource.postValue(channelPostDataSource);
        return channelPostDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, PagePostModel.PageData>> getItemLiveDataSource(){
        return itemLiveDataSource;
    }
}
