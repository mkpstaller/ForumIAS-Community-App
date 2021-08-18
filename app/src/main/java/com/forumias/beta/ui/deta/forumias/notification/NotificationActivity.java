package com.forumias.beta.ui.deta.forumias.notification;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.ReportModel;
import com.forumias.beta.ui.deta.forumias.notification.notification_pagin.NotificationPagingAdapter;
import com.forumias.beta.ui.deta.forumias.notification.notification_pagin.NotificationViewModel;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.ui.deta.forumias.channel.MyChannelPostActivity;
import com.forumias.beta.ui.deta.forumias.comment.ui.CommentOnPostDetailsActivity;
import com.forumias.beta.ui.deta.forumias.page.MyPagesPostActivity;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.UserProfileAndPostActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends Fragment implements NotificationReadingInteface , SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvLabel)
    AppCompatTextView tvLabel;
    @BindView(R.id.llNotificationSection)
    LinearLayoutCompat llNotificationSection;
    @BindView(R.id.rlLoginInfo)
    RelativeLayout rlLoginInfo;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private int loginUserId , darkModeStatus;
    NotificationViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_notification, container, false);
        ButterKnife.bind(this ,view);

        MyPreferenceData myPreferenceData = new MyPreferenceData(getContext());
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setRefreshing(false);
        if(darkModeStatus == 1){
            llNotificationSection.setBackgroundResource(R.color.darkmode_back_color);
            tvLabel.setTextColor(ContextCompat.getColor(getContext() ,R.color.light_white));
        }else{
            llNotificationSection.setBackgroundResource(R.color.back_color);
            tvLabel.setTextColor(ContextCompat.getColor(getContext() ,R.color.black_light));
        }

        if(loginUserId != 0) {
            initView();
            rlLoginInfo.setVisibility(View.GONE);
        }else{
            rlLoginInfo.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private void initView() {
        NotificationPagingAdapter notificationAdapter = new NotificationPagingAdapter(getContext() , this);
        viewModel = ViewModelProviders.of(this).get(NotificationViewModel.class);
        viewModel.getItemList(loginUserId).observe(this, notificationAdapter::submitList);
        recyclerView.setAdapter(notificationAdapter);
       // viewModel.invalidateDataSource();

    }


    @Override
    public void notificationReading(int loginUserId, int notificationId, int action, int module ,
                                    NotificationModel.MyNotification.NotificationData notificationData) {
        BetaApiClient apiClient = new BetaApiClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<ReportModel> call = apiInterface.getNotificationReading(loginUserId , notificationId);
        call.enqueue(new Callback<ReportModel>() {
            @Override
            public void onResponse(Call<ReportModel> call, Response<ReportModel> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        notificationData.setClickStatus(true);
                        callIntent(notificationData);
                    }
                }
            }

            @Override
            public void onFailure(Call<ReportModel> call, Throwable t) {

            }
        });
    }


    private void callIntent(NotificationModel.MyNotification.NotificationData notificationData) {
        switch (notificationData.getAction()){
            case 0:
                switch (notificationData.getModule()){
                    case 1:
                        if(notificationData.getNotifi_channelInfo() != null){
                            Intent intent = new Intent(getContext(), MyChannelPostActivity.class);
                            intent.putExtra(BaseUrl.SLUG,notificationData.getNotifi_channelInfo().getChannel_slug());
                            if(notificationData.getNotif_fromUserInfo() != null) {
                                intent.putExtra(BaseUrl.USER_ID, notificationData.getNotif_fromUserInfo().getId());
                            }
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(getActivity(), CommentOnPostDetailsActivity.class);
                            MyPreferenceData myPref = new MyPreferenceData(getContext());
                            if(notificationData.getNotif_postInfo() != null){
                                myPref.saveData(BaseUrl.PREF_SLUG , notificationData.getNotif_postInfo().getSlug());
                            }
                            startActivity(intent);
                        }
                        break;
                    case 8:
                        Intent intent = new Intent(getActivity(), CommentOnPostDetailsActivity.class);
                        MyPreferenceData myPref = new MyPreferenceData(getContext());
                        if(notificationData.getNotif_postInfo() != null){
                            myPref.saveData(BaseUrl.PREF_SLUG , notificationData.getNotif_postInfo().getSlug());
                        }
                        startActivity(intent);
                        break;
                }
                break;
            case 1:
                switch (notificationData.getModule()){
                    case  1:
                        Intent intent = new Intent(getActivity(), CommentOnPostDetailsActivity.class);
                        MyPreferenceData myPref = new MyPreferenceData(getContext());
                        if(notificationData.getNotif_postInfo() != null){
                            myPref.saveData(BaseUrl.PREF_SLUG , notificationData.getNotif_postInfo().getSlug());
                        }
                        startActivity(intent);
                        break;
                }
            case 2:
                switch (notificationData.getModule()){
                    case 1:
                        if(notificationData.getNotifi_channelInfo() != null){
                            Intent intent = new Intent(getContext(), MyChannelPostActivity.class);
                            intent.putExtra(BaseUrl.SLUG,notificationData.getNotifi_channelInfo().getChannel_slug());
                            if(notificationData.getNotif_fromUserInfo() != null) {
                                intent.putExtra(BaseUrl.USER_ID, notificationData.getNotif_fromUserInfo().getId());
                            }
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(getActivity(), CommentOnPostDetailsActivity.class);
                            MyPreferenceData myPref = new MyPreferenceData(getContext());
                            if(notificationData.getNotif_postInfo() != null){
                                myPref.saveData(BaseUrl.PREF_SLUG , notificationData.getNotif_postInfo().getSlug());
                            }
                            startActivity(intent);
                        }
                        /*if(notificationData.getNotif_postInfo() != null)
                            callCommentPostDetailsPostInfo(notificationData.getNotif_postInfo().getSlug());
                        break;*/
                }
                break;

            case 4:
                    switch (notificationData.getModule()){
                        case 5:
                            Intent friend = new Intent(getContext() , UserProfileAndPostActivity.class);
                            if(notificationData.getNotif_fromUserInfo() != null){
                                friend.putExtra(BaseUrl.NAME , notificationData.getNotif_fromUserInfo().getName());
                            }
                            startActivity(friend);
                            break;

                        case 4:
                            if(notificationData.getNotif_groupInfo_one() != null){
                                Intent intent = new Intent(getContext(), MyPagesPostActivity.class);
                                MyPreferenceData myPref = new MyPreferenceData(getContext());
                                myPref.saveData(BaseUrl.PREF_SLUG , notificationData.getNotif_groupInfo_one().getTag_slug());
                                startActivity(intent);
                            }
                            break;
                        case 3:
                            if(notificationData.getNotif_channelInfo_one() != null){
                                Intent intent = new Intent(getContext(), MyChannelPostActivity.class);
                                intent.putExtra(BaseUrl.SLUG,notificationData.getNotif_channelInfo_one().getChannel_slug());
                                if(notificationData.getNotif_userInfo() != null) {
                                    intent.putExtra(BaseUrl.USER_ID, notificationData.getNotif_fromUserInfo().getId());
                                }
                                startActivity(intent);
                            }
                            break;

                    }
                    break;
                case 6:
                    switch (notificationData.getModule()){
                        case 2:
                            Intent intent = new Intent(getActivity(), CommentOnPostDetailsActivity.class);
                            MyPreferenceData myPref = new MyPreferenceData(getContext());
                            if(notificationData.getNotif_postInfo() != null){
                                myPref.saveData(BaseUrl.PREF_SLUG , notificationData.getNotif_postInfo().getSlug());
                            }
                            startActivity(intent);
                            break;
                    }
                break;
                case 5:
                case 8:
                case 10:
                case 7:
                    switch (notificationData.getModule()){
                        case 1:
                        case 2:
                            if(notificationData.getNotif_postInfo() != null)
                            callCommentPostDetailsPostInfo(notificationData.getNotif_postInfo().getSlug());
                            break;

                    }
                    break;
            case 13:
                switch (notificationData.getModule()){
                    case  1:
                        Intent intent = new Intent(getActivity(), CommentOnPostDetailsActivity.class);
                        MyPreferenceData myPref = new MyPreferenceData(getContext());
                        if(notificationData.getNotif_postInfo() != null){
                            myPref.saveData(BaseUrl.PREF_SLUG , notificationData.getNotif_postInfo().getSlug());
                        }
                        startActivity(intent);
                        break;
                }
                break;
            }

    }

    private void callCommentPostDetailsPostInfo(String slug) {
        Intent intent = new Intent(getContext(), CommentOnPostDetailsActivity.class);
        MyPreferenceData myPref = new MyPreferenceData(getContext());
        myPref.saveData(BaseUrl.PREF_SLUG , slug);
        startActivity(intent);
    }


    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(false);
       // initView();
        viewModel.invalidateDataSource();

    }

   /* @Override
    public void onResume() {

        initView();
        //viewModel.invalidateDataSource();
        Log.e("resume===not====> " , "okkokokok");
        super.onResume();

    }*/
}
