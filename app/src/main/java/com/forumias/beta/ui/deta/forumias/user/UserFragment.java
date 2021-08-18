package com.forumias.beta.ui.deta.forumias.user;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.FollowUnFollowModel;
import com.forumias.beta.pojo.PostCountModel;
import com.forumias.beta.ui.deta.forumias.user.user_paging.UserAdapter;
import com.forumias.beta.ui.deta.forumias.user.user_paging.UserModel;
import com.forumias.beta.ui.deta.forumias.user.user_paging.UserViewModel;
import com.forumias.beta.utility.InternetConnection;
import com.forumias.beta.utility.MyUtility;
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
public class UserFragment extends Fragment implements UserInterface {

    @BindView(R.id.rvUser)
    RecyclerView rvUser;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rlUserMainPage)
    RelativeLayout rlUserMainPage;
    String userTag="";
    boolean onlineStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);

        InternetConnection internetConnection = new InternetConnection();
        onlineStatus  = internetConnection.checkConnection(Objects.requireNonNull(getContext()));

        MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(getContext()));
        int loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        int darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);

        assert getArguments() != null;
        int position = getArguments().getInt(BaseUrl.POSITION);

        if(darkModeStatus == 1){
            rlUserMainPage.setBackgroundResource(R.color.darkmode_back_color);
        }else{
            rlUserMainPage.setBackgroundResource(R.color.white);

        }

        setTabPosition(position, loginUserId);

        return view;
    }

    private void setTabPosition(int position, int loginUserId) {
        switch (position){
            case 0:
                if(onlineStatus){
                    userTag = "";
                    addUserModelView(userTag,loginUserId);
                }else{
                    MyUtility myUtility = new MyUtility();
                    myUtility.checkConnection(getContext());
                }
                break;
            case 1:
                if(onlineStatus){
                    userTag = "latest";
                    addUserModelView(userTag,loginUserId);
                }else{
                    MyUtility myUtility = new MyUtility();
                    myUtility.checkConnection(getContext());
                }
                break;
        }
    }

    private void addUserModelView(String userTag, int loginUserId) {
        UserAdapter adapter = new UserAdapter(getContext() , this);
        UserViewModel userViewModel = ViewModelProviders.of(Objects.requireNonNull(this)).get(UserViewModel.class);
        userViewModel.itemPagedList(loginUserId , progressBar,userTag).observe(this, userListings ->  {
            adapter.submitList(userListings);
            rvUser.setVisibility(View.VISIBLE);

        });

        rvUser.setAdapter(adapter);
    }


    @Override
    public void UserInformation(String name, String about, String image, String date) {

        Dialog dialog = new Dialog(Objects.requireNonNull(getContext()));
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.user_info_dialog);
        AppCompatTextView tvName = dialog.findViewById(R.id.tvName);
        AppCompatTextView tvAbout = dialog.findViewById(R.id.tvAbout);
        ImageView ivUserImage = dialog.findViewById(R.id.ivUserImage);
        AppCompatTextView tvDate = dialog.findViewById(R.id.tvDate);
        AppCompatTextView tvFollowers = dialog.findViewById(R.id.tvFollowers);
        AppCompatTextView tvPostCount = dialog.findViewById(R.id.tvPost);
        AppCompatTextView tvComments = dialog.findViewById(R.id.tvComments);
        AppCompatImageView ivClose = dialog.findViewById(R.id.ivClose);
        tvName.setText(name);

        ivClose.setOnClickListener(view -> dialog.dismiss());
        getPost(name , tvComments , tvPostCount , tvFollowers , tvDate);
        if (about != null) {
            tvAbout.setVisibility(View.VISIBLE);
            tvAbout.setText(Html.fromHtml(about));
        } else {
            tvAbout.setVisibility(View.GONE);
        }
        Glide.with(getContext()).load(image).into(ivUserImage);
        dialog.show();
    }



    private void getPost(String name, AppCompatTextView tvComments, AppCompatTextView tvPostCount,
                         AppCompatTextView tvFollowers ,AppCompatTextView tvDate) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<PostCountModel> call = apiInterface.getPopupDetails(name);
        call.enqueue(new Callback<PostCountModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<PostCountModel> call, @NotNull Response<PostCountModel> response) {
                assert response.body() != null;
                tvComments.setText(response.body().getCommentCount()+"  Comments");
                tvFollowers.setText(response.body().getFollowCount()+" Followers");
                tvPostCount.setText(response.body().getPostCount()+" Post");
                tvDate.setText("Joined "+response.body().getMemberSince() +" ago");
            }

            @Override
            public void onFailure(@NotNull Call<PostCountModel> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void userFollowUnfollow(UserModel.UserListing userListing, String followingData, String userId, String tagId, String status, String actType ,
                                   LinearLayoutCompat tvFollow , LinearLayoutCompat  tvFollowing , int position) {

        Log.e("data follow===> " , userId+"     "+tagId+"   "+status+"    "+actType);
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
                            userListing.setFollowStatus(1);

                        } else {
                            tvFollow.setVisibility(View.VISIBLE);
                            tvFollowing.setVisibility(View.GONE);
                            userListing.setFollowStatus(0);
                        }

                }
            }

            @Override
            public void onFailure(@NotNull Call<FollowUnFollowModel> call, @NotNull Throwable t) {

            }
        });

    }

}
