package com.forumias.beta.ui.deta.forumias.comment.fragment.all_comment_paging;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.ui.deta.forumias.comment.fragment.model.AllCommentModel;
import com.forumias.beta.api.APIInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllCommentDataSource extends PageKeyedDataSource<Integer, AllCommentModel.Result.CommentList> {

    static final int PAGE_SIZE = 20;
    private static int PAGE_NUM = 1;
    private int loginUserId , postId ,position;
    private ProgressBar progressBar;
    private RelativeLayout tvNothingShow;
    Context context;
    public static int MYPAGENUM = 0;
    private int pageNumber;
    MyPreferenceData sp ;

    public AllCommentDataSource(int loginUserId , int postId, ProgressBar progressBar , int position,
                                RelativeLayout tvNothingShow , Context context){
        this.loginUserId = loginUserId;
        this.postId = postId;
        this.progressBar = progressBar;
        this.position = position;
        this.tvNothingShow = tvNothingShow;
        this.context = context;
        sp = new MyPreferenceData(context);
       pageNumber =  sp.getIntegerData(BaseUrl.COMMENT_POS);

    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer , AllCommentModel.Result.CommentList> callback) {
      try {
          progressBar.setVisibility(View.VISIBLE);
      }catch (Exception e){e.printStackTrace();}

        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);

        //Log.e("PAGE_NUM test=1===> ", String.valueOf(PAGE_NUM));
        retrofit2.Call<AllCommentModel> call = apiInterface.getAllCommentHome(postId,loginUserId,PAGE_NUM);
        call.enqueue(new Callback<AllCommentModel>() {
            @Override
            public void onResponse(@NotNull Call<AllCommentModel> call, @NotNull Response<AllCommentModel> response) {
                progressBar.setVisibility(View.GONE);
                    if(response.isSuccessful()){
                        assert response.body() != null;
                        if(response.body().getResult().getCommentLists().size() > 0){
                            sp.deleteSingleData(BaseUrl.COMMENT_POS);
                            tvNothingShow.setVisibility(View.GONE);
                            callback.onResult(response.body().getResult().getCommentLists(),null,PAGE_NUM+1);
                        }else{
                            tvNothingShow.setVisibility(View.VISIBLE);
                        }
                    }
            }

            @Override
            public void onFailure(@NotNull Call<AllCommentModel> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                tvNothingShow.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer,AllCommentModel.Result.CommentList> callback) {

        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
       // Log.e("PAGE_NUM test=2===> ", String.valueOf(PAGE_NUM));
        retrofit2.Call<AllCommentModel> call = apiInterface.getAllCommentHome(postId,loginUserId,params.key);
        call.enqueue(new Callback<AllCommentModel>() {
            @Override
            public void onResponse(@NotNull Call<AllCommentModel> call, @NotNull Response<AllCommentModel> response) {
                Integer key = (params.key > 1) ? params.key - 1 : null;

                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getResult().getCommentLists().size() > 0){
                        callback.onResult(response.body().getResult().getCommentLists(),key);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<AllCommentModel> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer,AllCommentModel.Result.CommentList> callback) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        retrofit2.Call<AllCommentModel> call = apiInterface.getAllCommentHome(postId,loginUserId,params.key);
        call.enqueue(new Callback<AllCommentModel>() {
            @Override
            public void onResponse(@NotNull Call<AllCommentModel> call, @NotNull Response<AllCommentModel> response) {

                Integer key = params.key + 1;
               // Log.e("PAGE_NUM test=3===> ", String.valueOf(params.key));
               // sp.saveIntegerData(BaseUrl.COMMENT_POS , params.key);
               // MYPAGENUM = params.key;
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getResult().getCommentLists().size() > 0){
                        callback.onResult(response.body().getResult().getCommentLists(),key);

                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<AllCommentModel> call, @NotNull Throwable t) {

            }
        });
    }
}
