package com.forumias.beta.ui.deta.forumias.group;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.FollowUnFollowModel;
import com.forumias.beta.pojo.GroupModel;
import com.forumias.beta.ui.deta.forumias.group.group_adapter.GroupsAdapter;
import com.forumias.beta.R;
import com.forumias.beta.ui.deta.forumias.user.user_paging.UserModel;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.ui.deta.forumias.user.UserInterface;


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
public class PublicGroupFragment extends Fragment implements UserInterface {

    @BindView(R.id.rvGroup)
    RecyclerView rvGroup;
    @BindView(R.id.rlLoginInfo)
    RelativeLayout rlLoginInfo;
    @BindView(R.id.tvInfo)
    AppCompatTextView tvInfo;
    private int loginUserId;
    private String msg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_public_group, container, false);
        ButterKnife.bind(this , view);
        MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(getContext()));
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        fetchGroupList();
        return view;
    }



    private void fetchGroupList() {

          int  position = getArguments() != null ? getArguments().getInt(BaseUrl.TAB_POSITION) : 0;

        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<GroupModel> call = apiInterface.getGroupList(loginUserId);
        call.enqueue(new Callback<GroupModel>() {
            @Override
            public void onResponse(Call<GroupModel> call, Response<GroupModel> response) {
                if(response.body().getStatus().equals(BaseUrl.SUCCESS)) {
                    showChannelView(position, response.body());
                }else{
                   msg = response.body().getMessage();
                   showUserInfo(msg);
                }
            }

            @Override
            public void onFailure(Call<GroupModel> call, Throwable t) {

            }
        });
    }

    private void showChannelView(int position, GroupModel groupModel) {
        switch (position) {
            case 0:
                rlLoginInfo.setVisibility(View.GONE);
                setPublicGroupAdapter(groupModel.getGroupListings());
                break;
            case 1:
                rlLoginInfo.setVisibility(View.GONE);
                setPrivateGroupAdapter(groupModel.getGroupListings());
                break;
            case 2:
                if(loginUserId != 0) {
                    rlLoginInfo.setVisibility(View.GONE);
                    setMyGroupAdapter(groupModel.getGroupListings());
                }else{
                   // Glide.with(this).load(R.drawable.login_second_info).into(ivLoginInfo);
                    rlLoginInfo.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void setPrivateGroupAdapter(List<GroupModel.GroupListing> groupListings) {
        List<GroupModel.GroupListing> groupListingList = new ArrayList<>();
        for(int i = 0 ; i < groupListings.size(); i++){
            if(groupListings.get(i).getGroupType() == 1) {
                GroupModel.GroupListing groupModel = new GroupModel.GroupListing();
                groupModel.setId(groupListings.get(i).getId());
                groupModel.setTitle(groupListings.get(i).getTitle());
                groupModel.setTagSlug(groupListings.get(i).getTagSlug());
                groupModel.setTagImage(groupListings.get(i).getTagImage());
                groupModel.setColorCode(groupListings.get(i).getColorCode());
                groupModel.setDescription(groupListings.get(i).getDescription());
                groupModel.setStatus(groupListings.get(i).getStatus());
                groupModel.setCreatedBy(groupListings.get(i).getCreatedBy());
                groupModel.setUserId(groupListings.get(i).getUserId());
                groupModel.setGroupType(groupListings.get(i).getGroupType());
                groupModel.setAllowCreatePost(groupListings.get(i).getAllowCreatePost());
                groupModel.setPostInfoCount(groupListings.get(i).getPostInfoCount());
                groupModel.setFollowInfo(groupListings.get(i).getFollowInfo());
                groupListingList.add(groupModel);
            }
        }
       setDataInAdapter(groupListingList,1);
    }


    private void setPublicGroupAdapter(List<GroupModel.GroupListing> groupListings) {
        List<GroupModel.GroupListing> groupListingList = new ArrayList<>();
        for(int i = 0 ; i < groupListings.size(); i++){
                if (groupListings.get(i).getIsPage() == 0) {
                    if(groupListings.get(i).getGroupType() == 0) {
                        GroupModel.GroupListing groupModel = new GroupModel.GroupListing();
                        groupModel.setId(groupListings.get(i).getId());
                        groupModel.setTitle(groupListings.get(i).getTitle());
                        groupModel.setTagSlug(groupListings.get(i).getTagSlug());
                        groupModel.setTagImage(groupListings.get(i).getTagImage());
                        groupModel.setColorCode(groupListings.get(i).getColorCode());
                        groupModel.setDescription(groupListings.get(i).getDescription());
                        groupModel.setStatus(groupListings.get(i).getStatus());
                        groupModel.setCreatedBy(groupListings.get(i).getCreatedBy());
                        groupModel.setUserId(groupListings.get(i).getUserId());
                        groupModel.setGroupType(groupListings.get(i).getGroupType());
                        groupModel.setAllowCreatePost(groupListings.get(i).getAllowCreatePost());
                        groupModel.setPostInfoCount(groupListings.get(i).getPostInfoCount());
                        groupModel.setFollowInfo(groupListings.get(i).getFollowInfo());
                        groupListingList.add(groupModel);
                    }
                }
        }
        setDataInAdapter(groupListingList,0);
    }

    private void setMyGroupAdapter(List<GroupModel.GroupListing> groupListings) {
        List<GroupModel.GroupListing> groupListingList = new ArrayList<>();
        for(int i = 0 ; i < groupListings.size(); i++){
                if(groupListings.get(i).getUserId() == loginUserId) {
                    GroupModel.GroupListing groupModel = new GroupModel.GroupListing();
                    groupModel.setId(groupListings.get(i).getId());
                    groupModel.setTitle(groupListings.get(i).getTitle());
                    groupModel.setTagSlug(groupListings.get(i).getTagSlug());
                    groupModel.setTagImage(groupListings.get(i).getTagImage());
                    groupModel.setColorCode(groupListings.get(i).getColorCode());
                    groupModel.setDescription(groupListings.get(i).getDescription());
                    groupModel.setStatus(groupListings.get(i).getStatus());
                    groupModel.setCreatedBy(groupListings.get(i).getCreatedBy());
                    groupModel.setUserId(groupListings.get(i).getUserId());
                    groupModel.setGroupType(groupListings.get(i).getGroupType());
                    groupModel.setAllowCreatePost(groupListings.get(i).getAllowCreatePost());
                    groupModel.setPostInfoCount(groupListings.get(i).getPostInfoCount());
                    groupModel.setFollowInfo(groupListings.get(i).getFollowInfo());
                    groupListingList.add(groupModel);
                }
        }
        setDataInAdapter(groupListingList,2);
    }

    private void setDataInAdapter(List<GroupModel.GroupListing> groupListingList , int tabPosition) {
        if(groupListingList.size() > 0) {
            rlLoginInfo.setVisibility(View.GONE);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            rvGroup.setLayoutManager(layoutManager);
            GroupsAdapter mySpaceAdapter = new GroupsAdapter(groupListingList, getContext(), this);
            rvGroup.setAdapter(mySpaceAdapter);
        }else{
            switch (tabPosition){
                case 0:
                    msg = "Don't have any group in public group";
                    showUserInfo(msg);
                    break;
                case 1:
                    msg = "Don't have any group in private group";
                    showUserInfo(msg);
                    break;
                case 2:
                    msg = "Don't have any group in my group";
                    showUserInfo(msg);
                    break;
            }
        }
    }

    private void showUserInfo(String msg) {
        rlLoginInfo.setVisibility(View.VISIBLE);
        tvInfo.setText(msg);
    }

    /**
     * This Method not Use in this class*/
    @Override
    public void UserInformation(String name, String about, String image, String date) {

    }

    /**
     * This method use for Join Group and Leave Group*/
    @Override
    public void userFollowUnfollow(UserModel.UserListing userModel, String follow, String userId, String tagId, String status, String actType,
                                   LinearLayoutCompat tvJoinGroup, LinearLayoutCompat tvLeaveGroup, int position) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<FollowUnFollowModel> call = apiInterface.getFollowUnFollowing(userId , tagId , status , actType);
        call.enqueue(new Callback<FollowUnFollowModel>() {
            @Override
            public void onResponse(Call<FollowUnFollowModel> call, Response<FollowUnFollowModel> response) {
                if(response.body().getStatus().equalsIgnoreCase(BaseUrl.SUCCESS)){
                    if(Integer.parseInt(status) == 1){
                        tvJoinGroup.setVisibility(View.GONE);
                        tvLeaveGroup.setVisibility(View.VISIBLE);
                    }else{
                        tvJoinGroup.setVisibility(View.VISIBLE);
                        tvLeaveGroup.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<FollowUnFollowModel> call, Throwable t) {

            }
        });
    }
}
