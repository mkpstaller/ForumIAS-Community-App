package com.forumias.beta.ui.deta.forumias.page;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.FollowUnFollowModel;
import com.forumias.beta.pojo.GroupModel;
import com.forumias.beta.utility.InternetConnection;
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.ui.deta.forumias.page.page_interface.PageFollowInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPagesFragment extends Fragment implements PageFollowInterface , SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvNothingShow)
    AppCompatTextView tvNothingShow;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rlPageHomeOne)
    RelativeLayout rlPageHomeOne;
    @BindView(R.id.searchView)
    SearchView searchView;
  /*  @BindView(R.id.editSearchData)
    AppCompatEditText editSearchData;*/
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    PagesAdapter pageAdapter;
    List<GroupModel.GroupListing> pageList;

    private int loginUserId , darkModeStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypages, container, false);
        ButterKnife.bind(this, view);

        swipeRefresh.setOnRefreshListener(this);
        MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(getContext()));
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
        pageList = new ArrayList<>();

        if(darkModeStatus == 1){
            rlPageHomeOne.setBackgroundResource(R.color.darkmode_back_color);
        }else{
            rlPageHomeOne.setBackgroundResource(R.color.back_color);

        }


        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterPageList(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterPageList(newText);
                return false;
            }
        });

        fetchGroupList();
        return  view;
    }

    private void filterPageList(String data) {
        String userInput = data.toLowerCase();
        Log.e("search Datat==> " , userInput);
        List<GroupModel.GroupListing> list = new ArrayList<>();

        for(GroupModel.GroupListing model : pageList){
            if(model.getTitle().toLowerCase().contains(userInput)){
                list.add(model);
            }
        }
        try {
            if(list.size() > 0){
            pageAdapter.updateListData(list);
            }
        }catch (Exception e){ e.printStackTrace();}

    }


    private void fetchGroupList() {
        assert getArguments() != null;
        int position = getArguments().getInt(BaseUrl.TAB_POSITION);
        viewTabSection(position);

    }

    private void viewTabSection(int position) {
            switch (position){
                case 0:

                    InternetConnection internetConnection = new InternetConnection();
                    boolean onlineStatus  = internetConnection.checkConnection(Objects.requireNonNull(getContext()));
                    if(onlineStatus){
                        getAllPage();
                    }else{
                        MyUtility myUtility = new MyUtility();
                        myUtility.checkConnection(getContext());
                    }

                    break;
                case 1:
                    getMyCreatePage();
                    break;
            }
    }

    private void getAllPage() {
        progressBar.setVisibility(View.VISIBLE);
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<GroupModel> call = apiInterface.getGroupList(loginUserId);
        call.enqueue(new Callback<GroupModel>() {
            @Override
            public void onResponse(@NotNull Call<GroupModel> call, @NotNull Response<GroupModel> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)) {
                        if (response.body().getGroupListings().size() > 0) {
                            tvNothingShow.setVisibility(View.GONE);
                            searchView.setVisibility(View.VISIBLE);
                            setDataInAdapter(response.body().getGroupListings());
                        } else {
                            tvNothingShow.setVisibility(View.VISIBLE);
                            searchView.setVisibility(View.GONE);
                        }
                    }else{
                        tvNothingShow.setVisibility(View.VISIBLE);
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NotNull Call<GroupModel> call, @NotNull Throwable t) {
                tvNothingShow.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    private void getMyCreatePage() {
        progressBar.setVisibility(View.VISIBLE);
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<GroupModel> call = apiInterface.getMyPageList(loginUserId , "created_by_me");
        call.enqueue(new Callback<GroupModel>() {
            @Override
            public void onResponse(@NotNull Call<GroupModel> call, @NotNull Response<GroupModel> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)) {
                        if (response.body().getGroupListings().size() > 0) {
                            searchView.setVisibility(View.VISIBLE);
                            tvNothingShow.setVisibility(View.GONE);
                            setDataInAdapter(response.body().getGroupListings());
                        } else {
                            tvNothingShow.setVisibility(View.VISIBLE);
                            searchView.setVisibility(View.GONE);
                        }
                    }else{
                        tvNothingShow.setVisibility(View.VISIBLE);
                    }
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NotNull Call<GroupModel> call, @NotNull Throwable t) {
                tvNothingShow.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setDataInAdapter(List<GroupModel.GroupListing> groupListingList) {
        pageList.addAll(groupListingList);
        pageAdapter = new PagesAdapter(pageList, loginUserId,getContext()  , this);
        recyclerView.setAdapter(pageAdapter);

    }


    @Override
    public void pageFollowing(GroupModel.GroupListing pageList, String userId, String tagId, String status,
                              String actType, LinearLayoutCompat tvFollow, LinearLayoutCompat tvFollowing, int position) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<FollowUnFollowModel> call = apiInterface.getFollowUnFollowing(userId , tagId , status , actType);
        call.enqueue(new Callback<FollowUnFollowModel>() {
            @Override
            public void onResponse(@NotNull Call<FollowUnFollowModel> call, @NotNull Response<FollowUnFollowModel> response) {
                assert response.body() != null;
                if(response.body().getStatus().equalsIgnoreCase(BaseUrl.SUCCESS)){
                    if(response.body().getFlag() == 1){
                        tvFollow.setVisibility(View.GONE);
                        tvFollowing.setVisibility(View.VISIBLE);
                        pageList.setFollowStatus(1);
                        Log.e("das====> " , "dfadsf");

                    } else {
                        Log.e("das==11==> " , "dfadsf");
                        tvFollow.setVisibility(View.VISIBLE);
                        tvFollowing.setVisibility(View.GONE);
                        pageList.setFollowStatus(0);
                    }

                }
            }

            @Override
            public void onFailure(@NotNull Call<FollowUnFollowModel> call, @NotNull Throwable t) {

            }
        });

    }

    @Override
    public void onRefresh() {
        getAllPage();
        swipeRefresh.setRefreshing(false);
    }
}
