package com.forumias.beta.ui.deta.forumias.home.home_paging;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.forumias.beta.ui.deta.forumias.home.model.HomeLatestPostModel;

import org.jetbrains.annotations.NotNull;

public class MySpacePostDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, HomeLatestPostModel.MyLatestPost>> itemLiveDataSource
            = new MutableLiveData<>();
    private int userId;
    private int articleVisible;
    private ProgressBar progressBar;
    private AppCompatTextView tvNotDataFound;
    RelativeLayout rlProgress;
    public MySpacePostDataSourceFactory(int articleVisible, int userId, ProgressBar progressBar
            ,AppCompatTextView tvNotDataFound , RelativeLayout rlProgress) {
        this.userId = userId;
        this.progressBar = progressBar;
        this.articleVisible = articleVisible;
        this.tvNotDataFound = tvNotDataFound;
        this.rlProgress = rlProgress;

    }


    @NotNull
    @Override
    public DataSource create() {
        MySpacePostDataSource itemDataSource = new MySpacePostDataSource(articleVisible,userId , progressBar
                ,tvNotDataFound ,rlProgress);
        itemLiveDataSource.postValue(itemDataSource);
        return itemDataSource;
    }

    MutableLiveData<PageKeyedDataSource<Integer,HomeLatestPostModel.MyLatestPost>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
