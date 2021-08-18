package com.forumias.beta.ui.deta.forumias.deep_link;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadnemati.clickablewebview.ClickableWebView;
import com.bumptech.glide.Glide;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;

import com.forumias.beta.ui.deta.forumias.CreateCommentOnPostActivity;
import com.forumias.beta.ui.deta.forumias.comment.fragment.all_comment_paging.SaveCommentInterfacae;
import com.forumias.beta.ui.deta.forumias.comment.fragment.model.AllCommentModel;
import com.forumias.beta.ui.deta.forumias.comment.ui.CommentOnPostDetailsActivity;
import com.forumias.beta.ui.deta.forumias.deep_link.adapter.CommentReplyAdapter;
import com.forumias.beta.ui.deta.forumias.deep_link.model.DeepCommentModel;
import com.forumias.beta.ui.deta.forumias.save_comment.RemoveCommentModel;
import com.forumias.beta.ui.deta.forumias.splash.BetaSplashActivity;
import com.forumias.beta.ui.loginfragment.LoginAlertFragment;
import com.forumias.beta.utility.DateFormatUtils;
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.utility.Utility;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeepLinkActivity extends AppCompatActivity{

    @BindView(R.id.clickable_webview)
    WebView clickable_webview;
    @BindView(R.id.tvTitleShare)
    AppCompatTextView tvTitle;
    @BindView(R.id.ivCommentUserImage)
    ImageView ivCommentUserImage;
    @BindView(R.id.tvCommentUserName)
    AppCompatTextView tvCommentUserName;
    @BindView(R.id.tvDate)
    AppCompatTextView tvDate;
    @BindView(R.id.ivSaveComment)
    AppCompatImageView ivSaveComment;
    @BindView(R.id.llUpVoteSection)
    LinearLayoutCompat llUpVoteSection;
    @BindView(R.id.replyRecyclerView)
    RecyclerView replyRecyclerView;
    @BindView(R.id.tvCommentPost)
    AppCompatTextView tvCommentPost;
    @BindView(R.id.ivBackDeepLink)
    AppCompatImageView ivBackDeepLink;
    @BindView(R.id.tvViewCountSecond)
    AppCompatTextView tvViewCountSecond;
    @BindView(R.id.tvClickFullPost)
    AppCompatTextView tvClickFullPost;
    @BindView(R.id.webViewDeepLink)
    ClickableWebView webViewDeepLink;
    @BindView(R.id.llDeepLinkMainSection)
    LinearLayoutCompat llDeepLinkMainSection;

    int comment_Id;
    int  postId , loginUserId;
    private String slug;
    String commentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_link);
        ButterKnife.bind(this);

        MyPreferenceData myPref = new MyPreferenceData(this);
        loginUserId = myPref.getIntegerData(BaseUrl.USER_ID);
        Log.e("loginid ====> " , loginUserId+"");

        getUrlData();

        setClickEvent();

        ivBackDeepLink.setOnClickListener(view -> {
            Intent intent = new Intent(this , BetaSplashActivity.class);
            startActivity(intent);
            DeepLinkActivity.this.finish();
        });

        tvClickFullPost.setOnClickListener(view -> {
            Intent intent = new Intent(this, CommentOnPostDetailsActivity.class);
            MyPreferenceData pref = new MyPreferenceData(this);
            pref.saveData(BaseUrl.PREF_SLUG, slug);
            startActivity(intent);
        });

    }

    private void setClickEvent() {
            tvCommentPost.setOnClickListener(view -> {
                if(loginUserId != 0){

                    Intent intent = new Intent(this , CreateCommentOnPostActivity.class);
                    try {
                        intent.putExtra(BaseUrl.POST_ID , postId);
                        intent.putExtra(BaseUrl.COMMENT_ID , comment_Id);
                        intent.putExtra(BaseUrl.CALL_CLASS , 3);
                    }catch (Exception e){e.printStackTrace();}
                    startActivity(intent);
                    finish();
                }else{
                    LoginAlertFragment alert  = new LoginAlertFragment();
                    alert.show(getSupportFragmentManager() ,"MyAlertLogin");
                }
            });
    }

    private void getUrlData() {
        Uri uri = getIntent().getData();
        if(uri != null){
            String data = uri.toString();
            Log.e("data url =====> " , ""+data);
            String[] separated = data.split("\\?");
          //  Log.e("data  =====> " , ""+ separated[1]);


            try {
                if(separated[1].isEmpty()){
                    Log.e("data====> " ,""+data);
                }else{
                    llDeepLinkMainSection.setVisibility(View.VISIBLE);
                    webViewDeepLink.setVisibility(View.GONE);
                    tvClickFullPost.setVisibility(View.VISIBLE);
                    tvCommentPost.setVisibility(View.VISIBLE);
                    commentId=separated[1];
                    getCommentData(separated[1]);
                }
            }catch (ArrayIndexOutOfBoundsException e){
                setWebView(data);
                Log.e("data==11==> " ,""+data);
            }


        }
    }

    private void setWebView(String url) {
        llDeepLinkMainSection.setVisibility(View.GONE);
        webViewDeepLink.setVisibility(View.VISIBLE);
        tvClickFullPost.setVisibility(View.GONE);
        tvCommentPost.setVisibility(View.GONE);
        webViewDeepLink.getSettings().setJavaScriptEnabled(true);
        webViewDeepLink.loadUrl(url);
    }

    private void getCommentData(String commentId) {
        BetaApiClient apiClient = new BetaApiClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<DeepCommentModel> call = apiInterface.getCommentShare(commentId , loginUserId);
        call.enqueue(new Callback<DeepCommentModel>() {
            @Override
            public void onResponse(Call<DeepCommentModel> call, Response<DeepCommentModel> response) {
                if(response.isSuccessful()){
                    if(response.body().status.equals(BaseUrl.SUCCESS)){
                        postId = response.body().data.post_id;
                        comment_Id = response.body().data.id;
                        setData(response.body().data);
                        setReplyComment(response.body().subComments);
                    }
                }

            }

            @Override
            public void onFailure(Call<DeepCommentModel> call, Throwable t) {

            }
        });
    }

    private void setReplyComment(List<DeepCommentModel.SubCommentsData> subComments) {

        CommentReplyAdapter adapter = new CommentReplyAdapter(this,subComments);
        replyRecyclerView.setAdapter(adapter);
    }

    private void setData(DeepCommentModel.CommentData data) {
        new Utility().showAllComment(clickable_webview, data.description);
        tvTitle.setText(data.get_post.title);
        llUpVoteSection.setVisibility(View.GONE);
        Glide.with(this).load(data.user_info.image).into(ivCommentUserImage);
        tvCommentUserName.setText(data.user_info.full_name);
        tvDate.setText("commented " + new DateFormatUtils().showPostDate(data.created_at));
        tvViewCountSecond.setText(new Utility().prettyCount(data.views));

        slug = data.get_post.slug;

        ivSaveComment.setOnClickListener(view -> {
            if (loginUserId != 0) {
                //if (data.) {
                //saveCommentInterfacae.saveComment(loginUserId, data.id, 0, 0, 1, ivSaveComment, false);
               // } else {
                    saveComment(2, 0, data.post_id, data.id, 0, ivSaveComment, true);
               // }
            }else{
                new MyUtility().showLoginAlert(this);
            }

        });

    }

    @Override
    protected void onResume() {
        getCommentData(commentId);
        super.onResume();
    }



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


}