package com.forumias.beta.ui.deta.forumias.channel;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.ChannelModel;
import com.forumias.beta.pojo.FollowUnFollowModel;
import com.forumias.beta.ui.deta.forumias.channel.channelInterface.ChannelSubscribeInterface;
import com.forumias.beta.ui.deta.forumias.channel.channel_paging.ChannelListViewModel;
import com.forumias.beta.ui.deta.forumias.channel.channel_paging.ChannelPagingAdapter;
import com.forumias.beta.ui.deta.forumias.channel.channel_paging.Private_channel_Subscribe;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PublicChannelFragment extends Fragment implements ChannelSubscribeInterface, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rvChannels)
    RecyclerView rvChannels;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.flChannelSection)
    FrameLayout flChannelSection;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private int position , loginUserId ,darkModeStatus;
    ChannelListViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_public_channel, container, false);
        ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(this);
        MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(getContext()));
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);

        setDakModeView();

        assert getArguments() != null;
        position = getArguments().getInt(BaseUrl.TAB_POSITION);
        showChannelView(position);
        return view;
    }

    private void setDakModeView() {
        if(darkModeStatus == 1) {
            flChannelSection.setBackgroundResource(R.color.darkmode_back_color);
        }else{
            flChannelSection.setBackgroundResource(R.color.back_color);
        }
    }


    private void showChannelView(int position) {
        String PRIVATE = "private";
        String MY_CHANNEL = "my_channels";
        String PUBLIC = "public";
        String SECRET = "secret";
        String ACADEMY = "academy";
        switch (position) {
            case 0:
                setChannelAdapter(position, PUBLIC,progressBar,loginUserId);
                break;
           case 1:
                setChannelAdapter(position, PRIVATE,progressBar,loginUserId);
                break;
            case 2:
                setChannelAdapter(position, SECRET,progressBar,loginUserId);
                break;
            case 3:
                setChannelAdapter(position, ACADEMY,progressBar,loginUserId);
                break;
            case 4:
                setChannelAdapter(position, MY_CHANNEL,progressBar,loginUserId);
                break;
        }
    }

    private void setChannelAdapter(int tabPosition , String channelType , ProgressBar progressBar , int loginUserId) {
        ChannelPagingAdapter channelPagingAdapter = new ChannelPagingAdapter(getContext(),this , tabPosition);
        viewModel = ViewModelProviders.of(this).get(ChannelListViewModel.class);
        viewModel.getItemData(channelType , progressBar , loginUserId).observe(this,channelPagingAdapter::submitList);
        rvChannels.setAdapter(channelPagingAdapter);

    }

    @Override
    public void channelSubscribe(ChannelModel.ChannelList channelList, String userId, String tagId, String status, String actType,
                                 LinearLayoutCompat tvSubscribed, LinearLayoutCompat llUnSubscribed, AppCompatTextView tvUnSubscribed, int position) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<FollowUnFollowModel> call = apiInterface.getFollowUnFollowing(userId , tagId , status , actType);
        call.enqueue(new Callback<FollowUnFollowModel>() {
            @Override
            public void onResponse(@NotNull Call<FollowUnFollowModel> call, @NotNull Response<FollowUnFollowModel> response) {
                assert response.body() != null;
                if(response.body().getStatus().equalsIgnoreCase(BaseUrl.SUCCESS)){
                    if(response.body().getFlag() == 1){
                        tvSubscribed.setVisibility(View.GONE);
                        llUnSubscribed.setVisibility(View.VISIBLE);
                        channelList.setSubscribeStatus(1);
                    }else{
                        tvSubscribed.setVisibility(View.VISIBLE);
                        llUnSubscribed.setVisibility(View.GONE);
                        channelList.setSubscribeStatus(0);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<FollowUnFollowModel> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void privateChannelSubscribe(ChannelModel.ChannelList channelModel, int loginUserId,
                                        int id, int actType, LinearLayoutCompat tvSubscribed,
                                        LinearLayoutCompat llUnSubscribed,AppCompatTextView tvUnSubscribed, int position) {

        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<Private_channel_Subscribe> call = apiInterface.getPrivaetChannelSubscribe(actType , id , loginUserId);
        call.enqueue(new Callback<Private_channel_Subscribe>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<Private_channel_Subscribe> call, @NotNull Response<Private_channel_Subscribe> response) {

                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        if(response.body().getAct_type() == 1){
                            tvSubscribed.setVisibility(View.GONE);
                            llUnSubscribed.setVisibility(View.VISIBLE);
                            tvUnSubscribed.setText("Requested");
                        }else{
                            tvSubscribed.setVisibility(View.VISIBLE);
                            llUnSubscribed.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<Private_channel_Subscribe> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void onRefresh() {
        viewModel.invalidateDataSource();
        swipeRefreshLayout.setRefreshing(false);
    }
}
