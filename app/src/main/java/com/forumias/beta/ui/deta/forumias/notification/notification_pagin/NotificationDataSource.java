package com.forumias.beta.ui.deta.forumias.notification.notification_pagin;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.ui.deta.forumias.notification.NotificationModel;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationDataSource extends PageKeyedDataSource<Integer, NotificationModel.MyNotification.NotificationData> {
    static final int PAGE_SIZE = 10;
    private static final int PAGE_NUM = 1;
    private int loginUserId;

    public NotificationDataSource(int loginUserId) {
        this.loginUserId = loginUserId;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, NotificationModel.MyNotification.NotificationData> callback) {

        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<NotificationModel> call = apiInterface.getNotificationData(loginUserId,PAGE_NUM);
        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(@NotNull Call<NotificationModel> call, @NotNull Response<NotificationModel> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getNotification().getNotificationDataList().size() > 0){
                        callback.onResult(response.body().getNotification().getNotificationDataList(),null,PAGE_NUM+1);
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<NotificationModel> call, @NotNull Throwable t) {

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, NotificationModel.MyNotification.NotificationData> callback) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<NotificationModel> call = apiInterface.getNotificationData(loginUserId,params.key);
        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(@NotNull Call<NotificationModel> call, @NotNull Response<NotificationModel> response) {
                Integer key = (params.key > 1) ? params.key - 1 : null;
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getNotification().getNotificationDataList().size() > 0){
                        callback.onResult(response.body().getNotification().getNotificationDataList(),key);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<NotificationModel> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, NotificationModel.MyNotification.NotificationData> callback) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<NotificationModel> call = apiInterface.getNotificationData(loginUserId,params.key);
        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(@NotNull Call<NotificationModel> call, @NotNull Response<NotificationModel> response) {
                Integer key = params.key + 1;
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getNotification().getNotificationDataList().size() > 0){
                        callback.onResult(response.body().getNotification().getNotificationDataList(),key);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<NotificationModel> call, @NotNull Throwable t) {

            }
        });
    }
}
