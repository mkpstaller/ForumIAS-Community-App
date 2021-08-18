package com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.user_post_paging;

import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.paging.PageKeyedDataSource;

import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.api.APIInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPostDataSource extends PageKeyedDataSource<Integer,UserPostModel.MyStoriesList> {

    static final int PAGE_SIZE = 10;
    private static final int PAGE_NUM = 1;
    private  String name;
    private ProgressBar progressBar;
    private String tab;
    private AppCompatTextView tvNoDataFound;
    UserPostDataSource(String name, ProgressBar progressBar , String tab , AppCompatTextView tvNoDataFound){
        this.progressBar = progressBar;
        this.name = name;
        this.tab = tab;
        this.tvNoDataFound= tvNoDataFound;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, UserPostModel.MyStoriesList> callback) {
        progressBar.setVisibility(View.VISIBLE);
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<UserPostModel> call = apiInterface.getUserPostList(tab,name, PAGE_NUM);
        call.enqueue(new Callback<UserPostModel>() {
            @Override
            public void onResponse(@NotNull Call<UserPostModel> call, @NotNull Response<UserPostModel> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getMyStories().getMyStoriesLists().size() > 0){
                        tvNoDataFound.setVisibility(View.GONE);
                        callback.onResult(response.body().getMyStories().getMyStoriesLists(),null,PAGE_NUM+1);
                    }else{
                        tvNoDataFound.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<UserPostModel> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, UserPostModel.MyStoriesList> callback) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<UserPostModel> call = apiInterface.getUserPostList(tab,name, params.key);
        call.enqueue(new Callback<UserPostModel>() {
            @Override
            public void onResponse(@NotNull Call<UserPostModel> call, @NotNull Response<UserPostModel> response) {
                Integer key = (params.key > 1) ? params.key - 1 : null;
                if(response.isSuccessful()) {
                    assert response.body() != null;
                        callback.onResult(response.body().getMyStories().getMyStoriesLists(), key);
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserPostModel> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, UserPostModel.MyStoriesList> callback) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<UserPostModel> call = apiInterface.getUserPostList(tab,name, params.key);
        call.enqueue(new Callback<UserPostModel>() {
            @Override
            public void onResponse(@NotNull Call<UserPostModel> call, @NotNull Response<UserPostModel> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    Integer key = params.key + 1;
                        callback.onResult(response.body().getMyStories().getMyStoriesLists(), key);
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserPostModel> call, @NotNull Throwable t) {

            }
        });
    }
}
