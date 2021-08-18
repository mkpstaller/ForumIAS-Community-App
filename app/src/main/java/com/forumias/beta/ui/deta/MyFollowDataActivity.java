package com.forumias.beta.ui.deta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.forumias.beta.adapter.FollowingAdapter;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.PagesFollowingModel;
import com.forumias.beta.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFollowDataActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvGroupName)
    AppCompatTextView tvGroupName;
    private int loginUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_follow_data);
        ButterKnife.bind(this);

        MyPreferenceData myPreferenceData = new MyPreferenceData(this);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);

        initView();
    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        int position = bundle.getInt(BaseUrl.POSITION);
        String slug = bundle.getString(BaseUrl.SLUG);

        switch (position){
            case 0:
                tvGroupName.setText(getString(R.string.page_following));
                break;
            case 1:
                tvGroupName.setText(getString(R.string.subscribed_channel));
                break;
            /*case 2:
                tvGroupName.setText(getString(R.string.subscribed_channel));
                break;*/
        }
        getFollowingDataList(slug, position);
    }

    private void getFollowingDataList(String slug, int position){
        Log.e("slug===>",slug);
        BetaApiClient apiClient = new BetaApiClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<PagesFollowingModel> call = apiInterface.fetchPagesFollowing(String.valueOf(loginUserId)  , slug);
        call.enqueue(new Callback<PagesFollowingModel>() {
            @Override
            public void onResponse(@NotNull Call<PagesFollowingModel> call, @NotNull Response<PagesFollowingModel> response) {
                assert response.body() != null;
                if(response.body().getStatus().equalsIgnoreCase(BaseUrl.SUCCESS)){
                    if(response.body().getFollowing().size() > 0){
                        setAdapterInData(response.body().getFollowing() , position);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<PagesFollowingModel> call, @NotNull Throwable t) {

            }
        });
    }

    private void setAdapterInData(List<PagesFollowingModel.Following> following, int position) {
        FollowingAdapter adapter = new FollowingAdapter(following , this , BaseUrl.SHOW_DATA , position);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
