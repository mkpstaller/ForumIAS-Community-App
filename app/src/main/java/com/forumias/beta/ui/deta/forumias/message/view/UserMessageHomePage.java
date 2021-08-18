package com.forumias.beta.ui.deta.forumias.message.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.ui.deta.forumias.message.adapter.HomeMessageAdapter;
import com.forumias.beta.ui.deta.forumias.message.model.ChatListModel;
import com.forumias.beta.ui.deta.forumias.message.model.IndexMessageModel;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserMessageHomePage extends AppCompatActivity {

    @BindView(R.id.rvHomeMessage)
    RecyclerView rvHomeMessage;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_message_home_page);
        ButterKnife.bind(this);
        MyPreferenceData sp = new MyPreferenceData(this);
        userId = sp.getIntegerData(BaseUrl.USER_ID);
        getMessageIndex();
    }

    private void getMessageIndex(){
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<IndexMessageModel> call = apiInterface.getMessageIndex(userId);
        call.enqueue(new Callback<IndexMessageModel>() {
            @Override
            public void onResponse(Call<IndexMessageModel> call, Response<IndexMessageModel> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        if(response.body().getResult().size() > 0){
                            rvHomeMessage.setVisibility(View.VISIBLE);
                            getIndexMessage(response.body().getResult());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<IndexMessageModel> call, Throwable t) {

            }
        });
    }

    private void getIndexMessage(List<IndexMessageModel.Result> result) {
        HomeMessageAdapter  adapter = new HomeMessageAdapter(UserMessageHomePage.this , result , userId);
        rvHomeMessage.setAdapter(adapter);
    }




}