package com.forumias.beta.ui.deta.forumias.user.user_paging;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.api.APIInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserItemDataSource extends PageKeyedDataSource<Integer  , UserModel.UserListing> {

    static final int  PAGE_SIZE = 20;
    private static final int  FIRST_PAGE = 1;
    private int loginUserId;
    private ProgressBar progressBar;
    private String userTag;
    static String followingData;


    UserItemDataSource(int loginUserId, ProgressBar progressBar , String userTag) {
        this.loginUserId=loginUserId;
        this.progressBar = progressBar;
        this.userTag = userTag;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, UserModel.UserListing> callback) {

        progressBar.setVisibility(View.VISIBLE);
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<UserModel> call = apiInterface.fetchUserListData(loginUserId, FIRST_PAGE,userTag);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NotNull Call<UserModel> call, @NotNull Response<UserModel> response) {
                progressBar.setVisibility(View.GONE);
              if(response.body() != null){
                  followingData = response.body().getFollow_info();
                  Log.e("update new ==> " , response.body().getFollow_info());
                  if(response.body().getUserListing().size() > 0) {
                      callback.onResult(response.body().getUserListing(), null, FIRST_PAGE + 1);
                  }
              }
            }

            @Override
            public void onFailure(@NotNull Call<UserModel> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, UserModel.UserListing> callback) {

        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<UserModel> call = apiInterface.fetchUserListData(loginUserId, params.key,userTag);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NotNull Call<UserModel> call, @NotNull Response<UserModel> response) {
                Integer key = (params.key > 1) ? params.key - 1 : null;
                if(response.body() != null){
                    callback.onResult(response.body().getUserListing(), key);
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserModel> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, UserModel.UserListing> callback) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<UserModel> call = apiInterface.fetchUserListData(loginUserId, params.key,userTag);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NotNull Call<UserModel> call, @NotNull Response<UserModel> response) {

                if(response.body() != null){
                    Integer key = params.key + 1;
                    callback.onResult(response.body().getUserListing(), key);
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserModel> call, @NotNull Throwable t) {

            }
        });
    }
}
