package com.forumias.beta.ui.deta.forumias.save_comment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.utility.DateFormatUtils;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveCommentDetailsActivity extends AppCompatActivity {


    @BindView(R.id.ivCommentUserImage)
    ImageView ivCommentUserImage;
    @BindView(R.id.tvDate)
    AppCompatTextView tvDate;
    @BindView(R.id.tvCommentUserName)
    AppCompatTextView tvCommentUserName;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.ivShare)
    AppCompatImageView ivShare;
    @BindView(R.id.tvUserVerified)
    AppCompatImageView ivUserverified;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.ivBack)
    AppCompatImageView ivBack;
    @BindView(R.id.llToolbarSection)
    LinearLayoutCompat llToolbarSection;
    @BindView(R.id.ivAppLogo)
    AppCompatImageView ivAppLogo;

    private int loginUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_comment_details);
        ButterKnife.bind(this);


        MyPreferenceData myPreferenceData = new MyPreferenceData(this);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);


        getSaveCommentDetails();
    }

    private void getSaveCommentDetails() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String slug = bundle.getString(BaseUrl.SLUG);
        int commentId = bundle.getInt(BaseUrl.ID);
        progressBar.setVisibility(View.VISIBLE);
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<SaveCommentDetailsModel> call = apiInterface.getSaveCommentDetails(slug , commentId);
        call.enqueue(new Callback<SaveCommentDetailsModel>() {
            @Override
            public void onResponse(@NotNull Call<SaveCommentDetailsModel> call, @NotNull Response<SaveCommentDetailsModel> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        viewDetails(response.body());
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<SaveCommentDetailsModel> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void viewDetails(SaveCommentDetailsModel model){
        if(Utility.isNotNullOrEmpty(model.getSaveCommentInfo().getUserInfo().getImage())){
            Glide.with(this).load(model.getSaveCommentInfo().getUserInfo().getImage()).placeholder(R.drawable.user_placeholder).dontAnimate().into(ivCommentUserImage);
        }
        tvDate.setText( new DateFormatUtils().showPostDate(model.getSaveCommentInfo().getCreated_at()));
        if(model.getSaveCommentInfo().getUserInfo().getHide_real_name() == 1){
            tvCommentUserName.setText(model.getSaveCommentInfo().getUserInfo().getName());
        }else{
            tvCommentUserName.setText(model.getSaveCommentInfo().getUserInfo().getFull_name());
        }
        new Utility().showDescription(webView, model.getSaveCommentInfo().getDescription());
        new Utility().showUserVerified(model.getSaveCommentInfo().getUserInfo().getIs_verified(), ivUserverified);
    }
}
