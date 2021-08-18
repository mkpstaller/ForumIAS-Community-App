package com.forumias.beta.ui.deta.forumias.home.home_paging;

import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.forumias.beta.ui.deta.forumias.home.model.HomeLatestPostModel;

import org.jetbrains.annotations.NotNull;

public class LatestPostDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, HomeLatestPostModel.MyLatestPost>> itemLiveDataSource
            = new MutableLiveData<>();
    private int userId;
    private int articleVisible;
    private ProgressBar progressBar;
    private AppCompatTextView tvNotDataFound;
    public LatestPostDataSourceFactory(int articleVisible,int userId, ProgressBar progressBar , AppCompatTextView tvNotDataFound) {
        this.userId = userId;
        this.progressBar = progressBar;
        this.articleVisible = articleVisible;
        this.tvNotDataFound = tvNotDataFound;

    }


    @NotNull
    @Override
    public DataSource create() {
        LatestPostDataSource itemDataSource = new LatestPostDataSource(articleVisible,userId , progressBar , tvNotDataFound);
        itemLiveDataSource.postValue(itemDataSource);
        return itemDataSource;
    }

    MutableLiveData<PageKeyedDataSource<Integer,HomeLatestPostModel.MyLatestPost>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
