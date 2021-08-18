package com.forumias.beta.ui.deta.forumias.page.page_paging_adapter;

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

public class PagePostDataSource extends PageKeyedDataSource<Integer, PagePostModel.PageData> {
    static final int PAGE_SIZE = 10;
    private static final int PAGE_NUM = 1;
    private String slug;
    private ProgressBar progressBar;

    public PagePostDataSource(String slug , ProgressBar progressBar) {
        this.slug = slug;
        this.progressBar = progressBar;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, PagePostModel.PageData> callback) {
      //  progressBar.setVisibility(View.VISIBLE);
        BetaApiClient apiClient = new BetaApiClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<PagePostModel> call = apiInterface.fetchChannelPostDetails(slug,PAGE_NUM);
        call.enqueue(new Callback<PagePostModel>() {
            @Override
            public void onResponse(@NotNull Call<PagePostModel> call, @NotNull Response<PagePostModel> response) {
                progressBar.setVisibility(View.GONE);
                assert response.body() != null;
                if(response.isSuccessful()){
                    List<PagePostModel.PageData> pageDataList = new ArrayList<>();
                    if(response.body().getAnnouncementsLists().size() > 0){
                        pageDataList.addAll(response.body().getAnnouncementsLists());
                    }
                    if(response.body().getPagePost().getPagePostList().size() > 0){
                        pageDataList.addAll(response.body().getPagePost().getPagePostList());
                    }
                    callback.onResult(pageDataList,null,PAGE_NUM+1);
                }
            }

            @Override
            public void onFailure(@NotNull Call<PagePostModel> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, PagePostModel.PageData> callback) {
        BetaApiClient apiClient = new BetaApiClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<PagePostModel> call = apiInterface.fetchChannelPostDetails(slug,params.key);
        call.enqueue(new Callback<PagePostModel>() {
            @Override
            public void onResponse(@NotNull Call<PagePostModel> call, @NotNull Response<PagePostModel> response) {

                Integer key = (params.key > 1) ? params.key - 1 : null;
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getPagePost().getPagePostList().size() > 0) {
                        callback.onResult(response.body().getPagePost().getPagePostList(), key);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<PagePostModel> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, PagePostModel.PageData> callback) {
        BetaApiClient apiClient = new BetaApiClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<PagePostModel> call = apiInterface.fetchChannelPostDetails(slug,params.key);
        call.enqueue(new Callback<PagePostModel>() {
            @Override
            public void onResponse(@NotNull Call<PagePostModel> call, @NotNull Response<PagePostModel> response) {
                Integer key = params.key + 1;
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getPagePost().getPagePostList().size() > 0) {
                        callback.onResult(response.body().getPagePost().getPagePostList(), key);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<PagePostModel> call, @NotNull Throwable t) {

            }
        });
    }
}
