package com.forumias.beta.ui.deta.forumias.save_comment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.ui.deta.forumias.save_comment.SaveCommentPaging.SaveCommentAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveCommentListActivity extends AppCompatActivity implements SaveDeleteCommentInterface{


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.llSaveComment)
    LinearLayoutCompat llSaveComment;
    @BindView(R.id.ivBack)
    AppCompatImageView ivBack;
    @BindView(R.id.llToolbarSection)
    LinearLayoutCompat llToolbarSection;
    @BindView(R.id.ivAppLogo)
    AppCompatImageView ivAppLogo;
    @BindView(R.id.tvTitle)
    AppCompatTextView tvTitle;
    @BindView(R.id.rlToolBar)
    RelativeLayout rlToolBar;

    private int loginUserId , darkModeStatus;
    SaveCommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_comment_list);
        ButterKnife.bind(this);


        MyPreferenceData myPreferenceData = new MyPreferenceData(this);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);

        if(darkModeStatus == 1){
            llSaveComment.setBackgroundResource(R.color.black);
            rlToolBar.setBackgroundResource(R.color.black);
            ivBack.setImageDrawable(ContextCompat.getDrawable(this , R.drawable.ic_back_key_white));
            tvTitle.setTextColor(ContextCompat.getColor(this , R.color.light_white));
            llToolbarSection.setBackgroundColor(ContextCompat.getColor(this,R.color.darkmode_back_color));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back_key_white));
            ivAppLogo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo_white));
        }else{
            rlToolBar.setBackgroundResource(R.color.white);
            llSaveComment.setBackgroundResource(R.color.back_color);
            ivBack.setImageDrawable(ContextCompat.getDrawable(this , R.drawable.ic_back_key));
            tvTitle.setTextColor(ContextCompat.getColor(this , R.color.black_light));
            llToolbarSection.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back_key));
            ivAppLogo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo_black));
        }

        getSaveCommentData();
    }

    @OnClick(R.id.ivBack)
    public void onClickBack(){
        finish();
    }

    private void getSaveCommentData() {
        progressBar.setVisibility(View.VISIBLE);
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<SaveCommentModel> call = apiInterface.getSaveComment(loginUserId);
        call.enqueue(new Callback<SaveCommentModel>() {
            @Override
            public void onResponse(@NotNull Call<SaveCommentModel> call, @NotNull Response<SaveCommentModel> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        if(response.body().getInfo().getSaveCommentLists().size() > 0){
                           adapter = new SaveCommentAdapter(response.body().getInfo().getSaveCommentLists() ,
                                    getApplicationContext() , SaveCommentListActivity.this);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<SaveCommentModel> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void saveDeleteComment(int loginUserId, int saveCommentId, int postId, int commentId, int isDelete,
                                  int position , List<SaveCommentModel.InfoData.SaveCommentList> saveCommentLists , Context context) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Remove Comment..!");
        progressDialog.show();
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<RemoveCommentModel> call = apiInterface.saveDeleteComment(isDelete ,loginUserId ,postId ,commentId , saveCommentId);
        call.enqueue(new Callback<RemoveCommentModel>() {
            @Override
            public void onResponse(@NotNull Call<RemoveCommentModel> call, @NotNull Response<RemoveCommentModel> response) {
                saveCommentLists.remove(position);
                adapter.notifyItemRemoved(position);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NotNull Call<RemoveCommentModel> call, @NotNull Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
