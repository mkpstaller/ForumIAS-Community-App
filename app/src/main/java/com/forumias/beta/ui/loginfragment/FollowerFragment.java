package com.forumias.beta.ui.loginfragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.forumias.beta.adapter.FollowingUserAdapter;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.FollowUnFollowModel;
import com.forumias.beta.pojo.FollowerAndFollowingModel;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.R;
import com.forumias.beta.ui.deta.forumias.profile.model.FollowersModel;
import com.forumias.beta.ui.deta.forumias.user.UserFollowUnfollowInterface;

import org.jetbrains.annotations.NotNull;

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
public class FollowerFragment extends Fragment implements UserFollowUnfollowInterface {

   // List<FollowingORFollowerResponse.Follower> followerList;
    @BindView(R.id.mainRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rlNoDataFound)
    RelativeLayout rlNoDataFound;
    FollowingUserAdapter followingUserAdapter;
    List<FollowerAndFollowingModel> list;
    private int loginUserId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follower, container, false);
        ButterKnife.bind(this , view);

        MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(getContext()));
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);

       /* setHasOptionsMenu(true);
        followerList = new ArrayList<>();

        Gson gson = new Gson();
        String friendJson = getArguments().getString(BaseUrl.FOLLOWER_DATA);
        if(friendJson != null) {
            Type type = new TypeToken<List<FollowingORFollowerResponse.Follower>>() {}.getType();
            followerList = gson.fromJson(friendJson, type);
            if(followerList.size() > 0) {
                rlNoDataFound.setVisibility(View.GONE);
                setFollowerData(followerList);
            }else{
                rlNoDataFound.setVisibility(View.VISIBLE);
            }
        }
        else{
            Log.d("Location Count","failed");
        }

*/

       getFollowersData();

     //  setFollowAdapter();

        return view;
    }

    private void getFollowersData() {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<FollowersModel> call = apiInterface.getfollowersLis(loginUserId);
        call.enqueue(new Callback<FollowersModel>() {
            @Override
            public void onResponse(@NotNull Call<FollowersModel> call, @NotNull Response<FollowersModel> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getFollowerLists().size() > 0){
                        setFollowerAdapter(response.body().getFollowerLists());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<FollowersModel> call, Throwable t) {

            }
        });

    }

    /*private void setFollowingAdapter(List<FollowersModel.FollowerList> followingLists) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        followingUserAdapter = new FollowingUserAdapter(getContext(),followingLists,1);
        recyclerView.setAdapter(followingUserAdapter);
    }*/

    private void setFollowerAdapter(List<FollowersModel.FollowerList> followerLists) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        followingUserAdapter = new FollowingUserAdapter(getContext(),followerLists,0 , this);
        recyclerView.setAdapter(followingUserAdapter);
    }

    @Override
    public void followUnfollowing(int loginUserId, int id, int status, List<FollowersModel.FollowerList> followingUserList, int position) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<FollowUnFollowModel> call = apiInterface.getFollowUnFollowing(String.valueOf(loginUserId) ,
                String.valueOf(id) , String.valueOf(status) , "2");

        call.enqueue(new Callback<FollowUnFollowModel>() {
            @Override
            public void onResponse(@NotNull Call<FollowUnFollowModel> call, @NotNull Response<FollowUnFollowModel> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        if(response.body().getFlag() == 1){
                            followingUserList.remove(position);
                            followingUserAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<FollowUnFollowModel> call, @NotNull Throwable t) {

            }
        });
    }

  /*  private void setFollowAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        followingUserAdapter = new FollowingUserAdapter(getContext(),list,0);
        recyclerView.setAdapter(followingUserAdapter);
    }*/

   /* private void setFollowerData(List<FollowingORFollowerResponse.Follower> getFollowerList) {
        list = new ArrayList<>();

        for(int i = 0; i < getFollowerList.size(); i++){
            FollowerAndFollowingModel model = new FollowerAndFollowingModel();
            model.setId(getFollowerList.get(i).getId());
            model.setName(getFollowerList.get(i).getName());
            model.setImage(getFollowerList.get(i).getImage());
            list.add(model);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        followingUserAdapter = new FollowingUserAdapter(getContext(),list);
        recyclerView.setAdapter(followingUserAdapter);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.chat_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        search(searchView);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_group:
                Intent newGroup = new Intent(getContext(), GroupActivity.class);
                startActivity(newGroup);
                break;
            case R.id.menu_chanel:
                Intent newChanel = new Intent(getContext(), CreateChanelActivity.class);
                startActivity(newChanel);
                break;
            case R.id.menu_logout:
                MyPreferenceData myPreferenceData = new MyPreferenceData(getContext());
                if (myPreferenceData.clearData()) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }

                break;
        }

        return true;
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String filterText = newText.toLowerCase();
                List<FollowerAndFollowingModel> filterList = new ArrayList<>();
                for(FollowerAndFollowingModel follower : list){
                    if(follower.getName().toLowerCase().contains(filterText)){
                        filterList.add(follower);
                    }
                }
                followingUserAdapter.filterList(filterList);
                followingUserAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

*/
}
