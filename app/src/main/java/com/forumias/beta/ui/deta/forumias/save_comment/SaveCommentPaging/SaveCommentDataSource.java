package com.forumias.beta.ui.deta.forumias.save_comment.SaveCommentPaging;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.ui.deta.forumias.save_comment.SaveCommentModel;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveCommentDataSource extends PageKeyedDataSource<Integer , SaveCommentModel.InfoData.SaveCommentList> {
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, SaveCommentModel.InfoData.SaveCommentList> callback) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<SaveCommentModel> call = apiInterface.getSaveComment(2);
        call.enqueue(new Callback<SaveCommentModel>() {
            @Override
            public void onResponse(@NotNull Call<SaveCommentModel> call, @NotNull Response<SaveCommentModel> response) {

            }

            @Override
            public void onFailure(@NotNull Call<SaveCommentModel> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, SaveCommentModel.InfoData.SaveCommentList> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, SaveCommentModel.InfoData.SaveCommentList> callback) {

    }
}
