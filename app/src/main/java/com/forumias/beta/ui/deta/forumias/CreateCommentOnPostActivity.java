package com.forumias.beta.ui.deta.forumias;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.chinalwb.are.AREditor;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.ui.deta.forumias.comment.ui.CommentOnPostDetailsActivity;
import com.forumias.beta.ui.deta.forumias.create_story.CreateStoryModel;
import com.forumias.beta.ui.deta.forumias.deep_link.DeepLinkActivity;
import com.forumias.beta.ui.deta.forumias.page.PagePostCommentActivity;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCommentOnPostActivity extends AppCompatActivity {

    @BindView(R.id.tvTitle)
    AppCompatTextView tvTitle;
    @BindView(R.id.ivUserImage)
    ImageView ivUserImage;
    @BindView(R.id.tvName)
    AppCompatTextView tvName;
    @BindView(R.id.rlCommentSection)
    RelativeLayout rlCommentSection;
    @BindView(R.id.llCommentEditSection)
    LinearLayoutCompat llCommentEditSection;
    @BindView(R.id.ivBack)
    AppCompatImageView ivBack;

    private int loginUserId   , postId , callClass,darkModeStatus;
    private AREditor arEditor;
    private String imageUrl ;
    private MyPreferenceData myPreferenceData;
    private int  commentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment_on_post);

        ButterKnife.bind(this);
        initBundleData();
        initSharePrefData();
    }

    private void initSharePrefData() {
        this.arEditor = findViewById(R.id.postEditor);
        myPreferenceData = new MyPreferenceData(this);
        String userImage = myPreferenceData.getData(BaseUrl.TAG_IMAGE);
        String userName = myPreferenceData.getData(BaseUrl.FULL_NAME);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
        imageUrl = myPreferenceData.getData(BaseUrl.GALLERY_IMAGE);

        Log.e("login user id====> ",loginUserId+"");

        if(imageUrl != null){
            Intent intent = new Intent();
            intent.putExtra("IMAGE", imageUrl);
            this.arEditor.onActivityResult(100, -1, intent, "image");
        }

        darkModeView();

        if (!Utility.isNullOrEmpty(userImage)) {
            Glide.with(this).load(userImage)
                    .into(ivUserImage);
        }
        tvName.setText(userName);
    }

    private void darkModeView() {
        if(darkModeStatus == 1){
            rlCommentSection.setBackgroundResource(R.color.black_light);
            llCommentEditSection.setBackgroundResource(R.drawable.dark_post_border);
            tvName.setTextColor(ContextCompat.getColor(this , R.color.light_white));
            tvTitle.setTextColor(ContextCompat.getColor(this , R.color.light_white));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this , R.drawable.ic_back_key_white));
        }else{
            rlCommentSection.setBackgroundResource(R.color.back_color);
            llCommentEditSection.setBackgroundResource(R.color.white);
            tvName.setTextColor(ContextCompat.getColor(this , R.color.black_light));
            tvTitle.setTextColor(ContextCompat.getColor(this , R.color.black_light));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this , R.drawable.ic_back_key));
        }
    }

    @SuppressLint("SetTextI18n")
    private void initBundleData() {
        try {
            Bundle bundle = getIntent().getExtras();
            assert bundle != null;
            callClass = bundle.getInt(BaseUrl.CALL_CLASS);
            postId = bundle.getInt(BaseUrl.POST_ID);
            commentId = bundle.getInt(BaseUrl.COMMENT_ID);
            Log.e("commentId=====> " , commentId+"");
        }catch (Exception e){e.printStackTrace();}


      //  tvTitle.setText("Reply on ( " + name + " ) Comment");
    }

    @OnClick(R.id.tvPost)
    public void onClickPostComment(){
        String description = this.arEditor.getARE().getHtml();
        if (Utility.isNullOrEmpty(description)) {
            Toast.makeText(this, "Description field is required..!", Toast.LENGTH_LONG).show();
            return;
        }
        createCommentOnPost(description);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        this.arEditor.onActivityResult(requestCode, resultCode, data,imageUrl);
    }



    @OnClick(R.id.ivBack)
    public void onClickBack(){
        refreshActivity();
    }

    @Override
    public void onBackPressed() {
        refreshActivity();
        super.onBackPressed();
    }


    public void refreshActivity() {
        switch (callClass){
            case 1:
                myPreferenceData.deleteSingleData(BaseUrl.GALLERY_IMAGE);
                Intent one = new Intent(this, CommentOnPostDetailsActivity.class);
                one.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(one);
                finish();
                break;
            case 2:
                myPreferenceData.deleteSingleData(BaseUrl.GALLERY_IMAGE);
                Intent two = new Intent(this, PagePostCommentActivity.class);
                two.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(two);
                finish();
                break;
            case 3:
                myPreferenceData.deleteSingleData(BaseUrl.GALLERY_IMAGE);
                Intent three = new Intent(this, DeepLinkActivity.class);
                three.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(three);
                finish();
                break;
        }


    }


    private void createCommentOnPost(String description) {

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait..!");
        dialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("user_id", String.valueOf(loginUserId));
        params.put("post_id", String.valueOf(postId));
        params.put("comment_id", String.valueOf(commentId));
        params.put("description", description);
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<CreateStoryModel> call = apiInterface.createCommentOnPost(params);
        call.enqueue(new Callback<CreateStoryModel>() {
            @Override
            public void onResponse(@NotNull Call<CreateStoryModel> call, @NotNull Response<CreateStoryModel> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus().equals(BaseUrl.SUCCESS)) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        refreshActivity();


                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CreateStoryModel> call, @NotNull Throwable t) {
                dialog.dismiss();
            }
        });
    }


}
