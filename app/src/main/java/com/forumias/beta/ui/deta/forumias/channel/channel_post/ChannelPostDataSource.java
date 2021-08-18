package com.forumias.beta.ui.deta.forumias.channel.channel_post;

import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.api.APIInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChannelPostDataSource extends PageKeyedDataSource<Integer,ChannelPostModel.ChannelAnnouncementsList> {

    static final int PAGE_SIZE = 10;
    private static final int PAGE_NUM = 1;
    private String slug ;
    private ProgressBar progressBar;
    int userId;
    ChannelPostDataSource(String slug , ProgressBar progressBar,int userId){
        this.slug = slug;
        this.progressBar = progressBar;
        this.userId = userId;
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, ChannelPostModel.ChannelAnnouncementsList> callback) {
       // progressBar.setVisibility(View.VISIBLE);
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<ChannelPostModel> call  = apiInterface.fetchChannelPost(slug,PAGE_NUM,userId);
        call.enqueue(new Callback<ChannelPostModel>() {
            @Override
            public void onResponse(@NotNull Call<ChannelPostModel> call, @NotNull Response<ChannelPostModel> response) {
                progressBar.setVisibility(View.GONE);
                try {
                    if(response.isSuccessful()){
                        assert response.body() != null;
                        List<ChannelPostModel.ChannelAnnouncementsList> channelPostData = new ArrayList<>();
                        if(response.body().getChannelAnnouncementsLists().size() > 0){
                            channelPostData.addAll(response.body().getChannelAnnouncementsLists());
                        }
                        if(response.body().getChannelPost().getChannelPostList().size() > 0){
                            channelPostData.addAll(response.body().getChannelPost().getChannelPostList());
                        }
                        callback.onResult(channelPostData,null,PAGE_NUM+1);
                    }
                }catch (Exception e){e.printStackTrace();}

            }

            @Override
            public void onFailure(@NotNull Call<ChannelPostModel> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ChannelPostModel.ChannelAnnouncementsList> callback) {

        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<ChannelPostModel> call  = apiInterface.fetchChannelPost(slug,params.key,userId);
        call.enqueue(new Callback<ChannelPostModel>() {
            @Override
            public void onResponse(@NotNull Call<ChannelPostModel> call, @NotNull Response<ChannelPostModel> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    Integer key = (params.key > 1) ? params.key - 1 : null;
                    if(response.body().getChannelPost().getChannelPostList().size() > 0){
                        callback.onResult(response.body().getChannelPost().getChannelPostList(),key);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ChannelPostModel> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, ChannelPostModel.ChannelAnnouncementsList> callback) {

        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<ChannelPostModel> call = apiInterface.fetchChannelPost(slug, params.key,userId);
        call.enqueue(new Callback<ChannelPostModel>() {
            @Override
            public void onResponse(@NotNull Call<ChannelPostModel> call, @NotNull Response<ChannelPostModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    Integer key = params.key + 1;
                    if (response.body().getChannelPost().getChannelPostList().size() > 0) {
                        callback.onResult(response.body().getChannelPost().getChannelPostList(), key);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<ChannelPostModel> call, @NotNull Throwable t) {

            }
        });
    }
}
