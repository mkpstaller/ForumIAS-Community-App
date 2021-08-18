package com.forumias.beta.ui.deta.forumias.notification.notification_pagin;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.forumias.beta.ui.deta.forumias.notification.NotificationModel;

public class NotificationDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, NotificationModel.MyNotification.NotificationData>>  itemLiveDataSource
            = new MutableLiveData<>();

    int loginUserId;
    public NotificationDataSourceFactory(int loginUserId) {
        this.loginUserId = loginUserId;
    }


    @NonNull
    @Override
    public DataSource create() {
        NotificationDataSource notificationDataSource = new NotificationDataSource(loginUserId);
        itemLiveDataSource.postValue(notificationDataSource);
        return notificationDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, NotificationModel.MyNotification.NotificationData>> getItemLiveDataSource(){
        return itemLiveDataSource;
    }
}
