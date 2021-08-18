package com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.user_post_paging;

import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import org.jetbrains.annotations.NotNull;

public class UserPostDataSourceFactory extends DataSource.Factory {


    private MutableLiveData<PageKeyedDataSource<Integer, UserPostModel.MyStoriesList>> itemLiveDataSource
            = new MutableLiveData<>();
    private String  name;
    private ProgressBar progressBar;
    private String tab;
    private AppCompatTextView tvNoDataFound;
    public UserPostDataSourceFactory(String name, ProgressBar progressBar , String tab , AppCompatTextView tvNoDataFound) {
        this.name = name;
        this.progressBar = progressBar;
        this.tab = tab;
        this.tvNoDataFound = tvNoDataFound;

    }

    @NotNull
    @Override
    public DataSource create() {
        UserPostDataSource itemDataSource = new UserPostDataSource(name , progressBar , tab , tvNoDataFound);
        itemLiveDataSource.postValue(itemDataSource);
        return itemDataSource;
    }

    MutableLiveData<PageKeyedDataSource<Integer,UserPostModel.MyStoriesList>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
