package com.forumias.beta.ui.deta.forumias.user.user_paging;

import android.widget.ProgressBar;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import org.jetbrains.annotations.NotNull;

public class UserDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, UserModel.UserListing>> itemLiveDataSource = new MutableLiveData<>();
    int loginUserId;
    ProgressBar progressBar;
    String userTag;
    public UserDataSourceFactory(int loginUserId, ProgressBar progressBar , String userTag) {
        this.loginUserId = loginUserId;
        this.progressBar = progressBar;
        this.userTag = userTag;
    }

    @NotNull
    @Override
    public DataSource create() {
        UserItemDataSource itemDataSource = new UserItemDataSource(loginUserId , progressBar,userTag);
        itemLiveDataSource.postValue(itemDataSource);
        return itemDataSource;
    }

    MutableLiveData<PageKeyedDataSource<Integer,UserModel.UserListing>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
