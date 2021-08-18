package com.forumias.beta.ui.deta.forumias.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.utility.MyBounceInterpolator;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.ui.deta.forumias.ReportDailogFragment;
import com.forumias.beta.ui.deta.forumias.home.CallLikeUnLike;
import com.forumias.beta.ui.deta.forumias.home.model.AddMySpaceModel;
import com.forumias.beta.ui.deta.forumias.home.model.HomeLatestPostModel;
import com.forumias.beta.ui.deta.forumias.home.model.LikeResponseModel;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.user_post_fragment.UserDataAdapter;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.user_post_paging.UserPostModel;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.user_post_paging.UserPostViewModel;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPostFragment extends Fragment implements CallLikeUnLike {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.llPostSection)
    LinearLayoutCompat llPostSection;
    @BindView(R.id.tvNoDataFound)
    AppCompatTextView tvNoDataFound;

    private int tagId , actType , loginUserId ,darkModeStatus , isVerified;
    private String name , userName , image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_post, container, false);
        ButterKnife.bind(this , view);


        initView();
        setTabData();
        return view;
    }



    private void initView(){

        MyPreferenceData myPreferenceData = new MyPreferenceData(getContext());
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
      //  name = myPreferenceData.getData(BaseUrl.NAME);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);

        viewDayDarkMode();
    }

    private void setTabData() {
        assert getArguments() != null;
        int position = getArguments().getInt(BaseUrl.TAB_POSITION, 0);
        name = getArguments().getString(BaseUrl.NAME, "");
        tagId = getArguments().getInt(BaseUrl.TAG_ID, 0);
       isVerified = getArguments().getInt(BaseUrl.IS_VERIFIED,0);
        image = getArguments().getString(BaseUrl.IMAGE,"");

        Log.e("Tag id==454354==> " , tagId + "       "+image);
        switch (position){
            case 0:
                userPostInfo(name,"" ,position);
                break;
            case 1:
                userPostInfo(name,"discussions" , position);
                break;
            case 2:
                userPostInfo(name,"comments" , position);
                break;

        }


    }

    private void viewDayDarkMode() {
        if(darkModeStatus == 1){
            llPostSection.setBackgroundResource(R.color.darkmode_back_color);
        }else{
            llPostSection.setBackgroundResource(R.color.back_color);
        }
    }

    private void userPostInfo(String name, String tab ,int pos) {
      //  HomeLatestAdapter homeLatestAdapter = new HomeLatestAdapter(getContext(), 1 ,"124", this);
        UserDataAdapter userDataAdapter = new UserDataAdapter(getContext() , 0 , "124" ,
                this , pos ,tagId , name , image , isVerified);
        UserPostViewModel viewModel = ViewModelProviders.of(this).get(UserPostViewModel.class);
        viewModel.itemPagedList(name , progressBar , tab , tvNoDataFound).observe(this,userDataAdapter::submitList);
        recyclerView.setAdapter(userDataAdapter);
    }
    @Override
    public void callMyLikeUnlike(ProgressBar progressBar, HomeLatestPostModel.MyLatestPost latestPost, int position, int likeCount, int postId, int userId, AppCompatImageView ivLikeImageView, AppCompatTextView tvLikeCount, AppCompatImageView ivLiveHeart) {

    }

    @Override
    public void userMyLikeUnlike(ProgressBar progressBar, UserPostModel.MyStoriesList latestPost, int position, int likeCount, int postId, int userId, AppCompatImageView ivLikeImageView, AppCompatTextView tvLikeCount, AppCompatImageView ivLiveHeart) {

        progressBar.setVisibility(View.GONE);
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<LikeResponseModel> call = apiInterface.checkLikeUnLike(postId, userId);
        call.enqueue(new Callback<LikeResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<LikeResponseModel> call, @NotNull Response<LikeResponseModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus().equals(BaseUrl.SUCCESS)) {
                        if (response.body().getAction() == BaseUrl.LIKE) {
                            if (latestPost.getLikeInfo() != null) {
                                int totalCount = likeCount + 1;
                                latestPost.getLikeInfo().setLikeUserId(String.valueOf(userId));
                                latestPost.getLikeInfo().setLikeCount(totalCount);
                                tvLikeCount.setText(String.valueOf(totalCount));
                                ivLikeImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red));
                                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                                Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
                                animation.setInterpolator(interpolator);
                                ivLiveHeart.setAnimation(animation);
                            } else {
                               /* UserPostModel.MyStoriesList newData = new UserPostModel.MyStoriesList();
                                newData.getLikeInfo().setPostId(postId);
                                newData.getLikeInfo().setLikeCount(1);
                                newData.getLikeInfo().setLikeUserId(String.valueOf(userId));

                                latestPost.setLikeInfo(newData);*/
                                tvLikeCount.setText(String.valueOf(1));
                                ivLikeImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red));
                                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                                Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
                                animation.setInterpolator(interpolator);
                                ivLiveHeart.setAnimation(animation);

                            }
                        } else {
                            int totalCountLess = likeCount - 1;
                            latestPost.getLikeInfo().setLikeUserId("001");
                            latestPost.getLikeInfo().setLikeCount(totalCountLess);
                            tvLikeCount.setText(String.valueOf(totalCountLess));
                            ivLikeImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border));
                        }
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<LikeResponseModel> call, @NotNull Throwable t) {

            }
        });

    }

    @Override
    public void testCallMethod() {

    }

    @Override
    public void addToMySpace(int userId, int postId, AppCompatImageView ivAddToMySpace) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<AddMySpaceModel> call = apiInterface.addToMySpace(userId , postId);
        call.enqueue(new Callback<AddMySpaceModel>() {
            @Override
            public void onResponse(@NotNull Call<AddMySpaceModel> call, @NotNull Response<AddMySpaceModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getResult() == 1){
                        ivAddToMySpace.setImageDrawable(ContextCompat.getDrawable(getContext() , R.drawable.ic_check_blue));
                    }else {
                        ivAddToMySpace.setImageDrawable(ContextCompat.getDrawable(getContext() , R.drawable.ic_add_black));
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<AddMySpaceModel> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void reportDailog(int id, int loginUserId, Context context) {
        ReportDailogFragment fragment = new ReportDailogFragment(id ,loginUserId);
        fragment.show(getChildFragmentManager(), "MyCustomDialog");
    }
}
