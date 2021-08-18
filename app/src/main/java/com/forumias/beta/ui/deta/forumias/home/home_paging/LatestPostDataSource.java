package com.forumias.beta.ui.deta.forumias.home.home_paging;

import android.view.View;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.paging.PageKeyedDataSource;

import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.ui.deta.forumias.home.model.HomeLatestPostModel;
import com.forumias.beta.api.APIInterface;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LatestPostDataSource extends PageKeyedDataSource<Integer, HomeLatestPostModel.MyLatestPost> {

    static final int PAGE_SIZE = 10;
    private static final int PAGE_NUM = 1;
    private int userId;
    private int articleVisible;
    private ProgressBar progressBar;
    private AppCompatTextView tvNotDataFound;
    public LatestPostDataSource(int articleVisible,int userId, ProgressBar progressBar , AppCompatTextView tvNotDataFound) {
        this.articleVisible = articleVisible;
        this.userId = userId;
        this.progressBar = progressBar;
        this.tvNotDataFound = tvNotDataFound;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, HomeLatestPostModel.MyLatestPost> callback) {
       // progressBar.setVisibility(View.VISIBLE);
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<HomeLatestPostModel> call = apiInterface.fetchHomeLatestPost(String.valueOf(userId),PAGE_NUM);
        call.enqueue(new Callback<HomeLatestPostModel>() {
            @Override
            public void onResponse(@NotNull Call<HomeLatestPostModel> call, @NotNull Response<HomeLatestPostModel> response) {
                if(response.body() != null){
                    progressBar.setVisibility(View.GONE);
                    List<HomeLatestPostModel.MyLatestPost> announcementList = new ArrayList<>();


                        if (response.body().getMyLatestAnnouncementPosts().size() > 0) {
                            announcementList.addAll(response.body().getMyLatestAnnouncementPosts());
                        }
                        if(response.body().getPosts().getMyLatestPosts().size() > 0) {
                            announcementList.addAll(response.body().getPosts().getMyLatestPosts());
                        }
                    callback.onResult(announcementList, null, PAGE_NUM + 1);
                }
            }
            @Override
            public void onFailure(@NotNull Call<HomeLatestPostModel> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, HomeLatestPostModel.MyLatestPost> callback) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<HomeLatestPostModel> call = apiInterface.fetchHomeLatestPost(String.valueOf(userId), params.key);
        call.enqueue(new Callback<HomeLatestPostModel>() {
            @Override
            public void onResponse(@NotNull Call<HomeLatestPostModel> call, @NotNull Response<HomeLatestPostModel> response) {
                Integer key = (params.key > 1) ? params.key - 1 : null;
                if(response.body() != null){

                    callback.onResult(response.body().getPosts().getMyLatestPosts(), key);
                }
            }
            @Override
            public void onFailure(@NotNull Call<HomeLatestPostModel> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, HomeLatestPostModel.MyLatestPost> callback) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<HomeLatestPostModel> call = apiInterface.fetchHomeLatestPost(String.valueOf(userId),params.key);
        call.enqueue(new Callback<HomeLatestPostModel>() {
            @Override
            public void onResponse(@NotNull Call<HomeLatestPostModel> call, @NotNull Response<HomeLatestPostModel> response) {
                if(response.body() != null){
                   // Integer key = true ? params.key + 1 : null;

                    Integer key = params.key + 1;
                    callback.onResult(response.body().getPosts().getMyLatestPosts(), key);
                }
            }
            @Override
            public void onFailure(@NotNull Call<HomeLatestPostModel> call, @NotNull Throwable t) {

            }
        });
    }


}
