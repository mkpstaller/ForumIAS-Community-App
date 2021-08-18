package com.forumias.beta.ui.deta.forumias.channel.channel_paging;

import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.pojo.ChannelModel;
import com.forumias.beta.api.APIInterface;

import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChannelListDataSource extends PageKeyedDataSource<Integer , ChannelModel.ChannelList> {
    static final int PAGE_SIZE = 10;
    private static final int PAGE_NUM = 1;
    private String channelType;
    private ProgressBar progressBar;
    private int loginUserId;

    public ChannelListDataSource(String channelType , ProgressBar progressBar , int loginUserId){
        this.channelType = channelType;
        this.progressBar = progressBar;
        this.loginUserId = loginUserId;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, ChannelModel.ChannelList> callback) {
       // progressBar.setVisibility(View.VISIBLE);
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<ChannelModel> call = apiInterface.fetchChannelListData(loginUserId,channelType,PAGE_NUM);
        call.enqueue(new Callback<ChannelModel>() {
            @Override
            public void onResponse(@NotNull Call<ChannelModel> call, @NotNull Response<ChannelModel> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getChannelListing().getChannelListList().size() > 0) {
                        callback.onResult(response.body().getChannelListing().getChannelListList(),null,PAGE_NUM+1);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ChannelModel> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ChannelModel.ChannelList> callback) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<ChannelModel> call = apiInterface.fetchChannelListData(loginUserId,channelType,params.key);
        call.enqueue(new Callback<ChannelModel>() {
            @Override
            public void onResponse(@NotNull Call<ChannelModel> call, @NotNull Response<ChannelModel> response) {
                if(response.isSuccessful()) {
                    Integer key = (params.key > 1) ? params.key - 1 : null;
                    assert response.body() != null;
                    if (response.body().getChannelListing().getChannelListList().size() > 0) {
                        callback.onResult(response.body().getChannelListing().getChannelListList(),key);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ChannelModel> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ChannelModel.ChannelList> callback) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<ChannelModel> call = apiInterface.fetchChannelListData(loginUserId,channelType,params.key);
        call.enqueue(new Callback<ChannelModel>() {
            @Override
            public void onResponse(@NotNull Call<ChannelModel> call, @NotNull Response<ChannelModel> response) {
                if(response.isSuccessful()) {
                    Integer key = params.key + 1;
                    assert response.body() != null;
                    if (response.body().getChannelListing().getChannelListList().size() > 0) {
                        callback.onResult(response.body().getChannelListing().getChannelListList(),key);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ChannelModel> call, @NotNull Throwable t) {

            }
        });
    }
}
