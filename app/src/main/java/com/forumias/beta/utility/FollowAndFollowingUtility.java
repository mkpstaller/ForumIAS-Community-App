package com.forumias.beta.utility;

import android.content.Context;
import android.view.View;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.forumias.beta.api.APIInterface;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.pojo.FollowUnFollowModel;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowAndFollowingUtility {

    public void followAndFollowing(Context context , String userID , String status, String tagID ,
                                   String actType , LinearLayoutCompat tvFollow , LinearLayoutCompat tvFollowing){
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<FollowUnFollowModel> call = apiInterface.getFollowUnFollowing(userID , tagID ,status , actType);
        call.enqueue(new Callback<FollowUnFollowModel>() {
            @Override
            public void onResponse(@NotNull Call<FollowUnFollowModel> call, @NotNull Response<FollowUnFollowModel> response) {
                assert response.body() != null;
                if(response.body().getStatus().equalsIgnoreCase(BaseUrl.SUCCESS)){
                  if(response.isSuccessful()){
                      if(response.body().getFlag() == 1){
                          tvFollow.setVisibility(View.GONE);
                          tvFollowing.setVisibility(View.VISIBLE);
                      }else{
                          tvFollow.setVisibility(View.VISIBLE);
                          tvFollowing.setVisibility(View.GONE);
                      }
                  }
                }
            }

            @Override
            public void onFailure(@NotNull Call<FollowUnFollowModel> call, @NotNull Throwable t) {

            }
        });


    }
}
