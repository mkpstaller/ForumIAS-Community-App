package com.forumias.beta.ui.deta.forumias.home.home_paging;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.paging.PageKeyedDataSource;

import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.ui.deta.forumias.home.model.HomeLatestPostModel;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MySpacePostDataSource extends PageKeyedDataSource<Integer, HomeLatestPostModel.MyLatestPost> {

    static final int PAGE_SIZE = 10;
    private static final int PAGE_NUM = 1;
    private int userId;
    private int articleVisible;
    private ProgressBar progressBar;
    private AppCompatTextView tvNotDataFound;
    RelativeLayout rlProgress;
    public MySpacePostDataSource(int articleVisible, int userId, ProgressBar progressBar
            , AppCompatTextView tvNotDataFound ,RelativeLayout avi) {
        this.articleVisible = articleVisible;
        this.userId = userId;
        this.progressBar = progressBar;
        this.tvNotDataFound = tvNotDataFound;
        this.rlProgress = avi;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, HomeLatestPostModel.MyLatestPost> callback) {
       // progressBar.setVisibility(View.VISIBLE);
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<HomeLatestPostModel> call = apiInterface.fetchHomeMySpancePost(String.valueOf(userId),PAGE_NUM);
        call.enqueue(new Callback<HomeLatestPostModel>() {
            @Override
            public void onResponse(@NotNull Call<HomeLatestPostModel> call, @NotNull Response<HomeLatestPostModel> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getPosts().getMyLatestPosts().size() > 0) {
                        progressBar.setVisibility(View.GONE);
                        tvNotDataFound.setVisibility(View.GONE);
                        rlProgress.setVisibility(View.GONE);

                        callback.onResult(response.body().getPosts().getMyLatestPosts(), null, PAGE_NUM + 1);

                    }else{
                        progressBar.setVisibility(View.GONE);
                        rlProgress.setVisibility(View.GONE);
                        tvNotDataFound.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onFailure(@NotNull Call<HomeLatestPostModel> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                rlProgress.setVisibility(View.GONE);

            }
        });
    }


    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, HomeLatestPostModel.MyLatestPost> callback) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<HomeLatestPostModel> call = apiInterface.fetchHomeMySpancePost(String.valueOf(userId), params.key);
        call.enqueue(new Callback<HomeLatestPostModel>() {
            @Override
            public void onResponse(@NotNull Call<HomeLatestPostModel> call, @NotNull Response<HomeLatestPostModel> response) {
                Integer key = (params.key > 1) ? params.key - 1 : null;
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getPosts().getMyLatestPosts().size() > 0) {
                        callback.onResult(response.body().getPosts().getMyLatestPosts(), key);
                    }
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
        Call<HomeLatestPostModel> call = apiInterface.fetchHomeMySpancePost(String.valueOf(userId),params.key);
        call.enqueue(new Callback<HomeLatestPostModel>() {
            @Override
            public void onResponse(@NotNull Call<HomeLatestPostModel> call, @NotNull Response<HomeLatestPostModel> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getPosts().getMyLatestPosts().size() > 0) {
                        // Integer key = true ? params.key + 1 : null;
                        Integer key = params.key + 1;
                        callback.onResult(response.body().getPosts().getMyLatestPosts(), key);
                    }
                }
            }
            @Override
            public void onFailure(@NotNull Call<HomeLatestPostModel> call, @NotNull Throwable t) {

            }
        });
    }


}
