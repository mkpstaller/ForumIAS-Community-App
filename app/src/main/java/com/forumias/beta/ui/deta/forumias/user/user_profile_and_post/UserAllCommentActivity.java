package com.forumias.beta.ui.deta.forumias.user.user_profile_and_post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.ShareCommentModel;
import com.forumias.beta.ui.deta.forumias.comment.fragment.all_comment_paging.SaveCommentInterfacae;
import com.forumias.beta.ui.deta.forumias.comment.fragment.model.AllCommentModel;
import com.forumias.beta.ui.deta.forumias.save_comment.RemoveCommentModel;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAllCommentActivity extends AppCompatActivity implements SaveCommentInterfacae {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvTitle)
    AppCompatTextView tvTitle;
    @BindView(R.id.llUserCommentSection)
    LinearLayoutCompat llUserCommentSection;
    @BindView(R.id.ivBack)
    AppCompatImageView ivBack;
    @BindView(R.id.ivAppLogo)
    AppCompatImageView ivAppLogo;
    @BindView(R.id.llToolbarSection)
    LinearLayoutCompat llToolbarSection;


    int darkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_all_comment);

        ButterKnife.bind(this);

        MyPreferenceData myPreferenceData = new MyPreferenceData(this);
        int loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
         darkMode = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);


        int postId = Objects.requireNonNull(getIntent().getExtras()).getInt(BaseUrl.POST_ID);
        int tagID = Objects.requireNonNull(getIntent().getExtras()).getInt(BaseUrl.TAG_ID);
        int isverified = Objects.requireNonNull(getIntent().getExtras()).getInt(BaseUrl.IS_VERIFIED);

        Log.e("data= tagId===> " , String.valueOf(tagID));
        getUserAllCommentList(tagID , postId , isverified);
        setDarkMode();
    }

    public  void setDarkMode(){
        if(darkMode == 1){
            llUserCommentSection.setBackgroundResource(R.color.darkmode_back_color);
            llToolbarSection.setBackgroundResource(R.color.black);
            tvTitle.setTextColor(ContextCompat.getColor(this , R.color.light_white));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back_key_white));
            ivAppLogo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.forumias_logo_white));
        }else{
            llToolbarSection.setBackgroundResource(R.color.white);
            llUserCommentSection.setBackgroundResource(R.color.back_color);
            tvTitle.setTextColor(ContextCompat.getColor(this , R.color.black_light));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back_key));
            ivAppLogo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo_black));
        }
    }

    @OnClick(R.id.ivBack)
    public void onClickBack(){
        finish();
    }
    private void getUserAllCommentList(int loginUserId, int postId , int isVerified) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<UserCommentModel> call = apiInterface.getAllComment(postId , loginUserId);
        call.enqueue(new Callback<UserCommentModel>() {
            @Override
            public void onResponse(Call<UserCommentModel> call, Response<UserCommentModel> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getUserCommentInfo().size() > 0){
                        tvTitle.setText(response.body().getPostInfo().getTitle());
                        setCommentAdapter(response.body().getUserCommentInfo() , isVerified);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserCommentModel> call, Throwable t) {

            }
        });
    }

    private void setCommentAdapter(List<UserCommentModel.UserCommentInfo> userCommentInfo , int isVerified) {
        UserAllCommentAdapter allCommentAdapter = new UserAllCommentAdapter(userCommentInfo , getApplicationContext() , isVerified , this);
        recyclerView.setAdapter(allCommentAdapter);
    }

    @Override
    public void saveComment(int loginUserId, int saveCommentId, int postId, int commentId,
                            int isDelete, AppCompatImageView ivSaveComment, boolean status) {

        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<RemoveCommentModel> call = apiInterface.saveDeleteComment(isDelete,loginUserId,postId
                ,commentId ,saveCommentId);
        call.enqueue(new Callback<RemoveCommentModel>() {
            @Override
            public void onResponse(@NotNull Call<RemoveCommentModel> call, @NotNull Response<RemoveCommentModel> response) {

                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        AllCommentModel.Result.CommentList model = new AllCommentModel.Result.CommentList();
                        model.setSaveCommentStatus(true);
                        if(status){
                            ivSaveComment.setImageDrawable(Objects.requireNonNull(getApplicationContext()).getResources().getDrawable(R.drawable.ic_yellow_star));

                        }else{
                            ivSaveComment.setImageDrawable(Objects.requireNonNull(getApplicationContext()).getResources().getDrawable(R.drawable.ic_star));

                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<RemoveCommentModel> call, @NotNull Throwable t) {

            }
        });

    }

    @Override
    public void shareComment(int commentId) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<ShareCommentModel> call = apiInterface.getShareUrl(commentId);
        call.enqueue(new Callback<ShareCommentModel>() {
            @Override
            public void onResponse(Call<ShareCommentModel> call, Response<ShareCommentModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        shareData(response.body().getComment_url());
                    }
                }
            }

            @Override
            public void onFailure(Call<ShareCommentModel> call, Throwable t) {

            }
        });
    }

    private void shareData(String comment_url) {
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_TEXT,comment_url);
        share.setType("text/plain");
        startActivity(Intent.createChooser(share,"Share More"));
    }
}
