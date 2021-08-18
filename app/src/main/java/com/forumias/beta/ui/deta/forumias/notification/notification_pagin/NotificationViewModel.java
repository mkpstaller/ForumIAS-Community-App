package com.forumias.beta.ui.deta.forumias.notification.notification_pagin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.forumias.beta.ui.deta.forumias.notification.NotificationModel;


public class NotificationViewModel extends ViewModel {
    private LiveData<PagedList<NotificationModel.MyNotification.NotificationData>> itemList;
    private MutableLiveData<PageKeyedDataSource<Integer, NotificationModel.MyNotification.NotificationData>> liveDataSource;


    public LiveData<PagedList<NotificationModel.MyNotification.NotificationData>> getItemList(int loginUserId){

        if(itemList == null){
            itemList = new  MutableLiveData<>();
            loadChannelPost(loginUserId);
        }
        return itemList;
    }

    public void invalidateDataSource(){
        liveDataSource.getValue().invalidate();
    }
    private void loadChannelPost(int loginUserId){
        NotificationDataSourceFactory notificationDataSourceFactory = new NotificationDataSourceFactory(loginUserId);
        liveDataSource = notificationDataSourceFactory.getItemLiveDataSource();
        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(NotificationDataSource.PAGE_SIZE)
                        .build();

        itemList = (new LivePagedListBuilder(notificationDataSourceFactory, config)).build();

    }
}
