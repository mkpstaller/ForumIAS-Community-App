package com.forumias.beta.ui.deta.forumias.user.user_paging;

import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

public class UserViewModel extends ViewModel {
    private LiveData<PagedList<UserModel.UserListing>> itemList;
    private MutableLiveData<PageKeyedDataSource<Integer, UserModel.UserListing>> liveDataSource;


    public LiveData<PagedList<UserModel.UserListing>> itemPagedList(int loginUserId, ProgressBar progressBar , String userTag){

        if (itemList == null) {
            itemList = new MutableLiveData<>();
            //we will load it asynchronously from server in this method
            loadSearchNews(loginUserId , progressBar , userTag);
        }

        return itemList;
    }



    private void loadSearchNews(int loginUserId, ProgressBar progressBar , String userTag) {
        UserDataSourceFactory dataSourceFactory = new UserDataSourceFactory(loginUserId , progressBar , userTag);
        liveDataSource = dataSourceFactory.getItemLiveDataSource();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(UserItemDataSource.PAGE_SIZE)
                        .build();

        itemList = (new LivePagedListBuilder(dataSourceFactory, config)).build();

    }
}
