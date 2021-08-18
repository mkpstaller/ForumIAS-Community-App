package com.forumias.beta.ui.deta.forumias.reply_quote;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chinalwb.are.AREditor;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.ui.deta.forumias.create_story.CreateStoryModel;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.ui.deta.forumias.comment.ui.CommentOnPostDetailsActivity;
import com.forumias.beta.ui.deta.forumias.page.PagePostCommentActivity;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReplyQuoteOnCommentActivity extends AppCompatActivity {

    @BindView(R.id.tvTitle)
    AppCompatTextView tvTitle;
    @BindView(R.id.ivUserImage)
    ImageView ivUserImage;
    @BindView(R.id.tvName)
    AppCompatTextView tvName;
    @BindView(R.id.rlReplySection)
    RelativeLayout rlReplySection;
    @BindView(R.id.llReplyEditSection)
    LinearLayoutCompat llReplyEditSection;
    @BindView(R.id.ivBack)
    AppCompatImageView ivBack;

    private int loginUserId , postId , type ,callClass , darkModeStatus;
    private AREditor arEditor;
    private String description ,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_qoute_on_comment);
        ButterKnife.bind(this);

        initBundleData();
        initSharePrefData();
    }

    private void initSharePrefData() {
        this.arEditor = findViewById(R.id.postEditor);
        MyPreferenceData myPreferenceData = new MyPreferenceData(this);
        String userImage = myPreferenceData.getData(BaseUrl.TAG_IMAGE);
        String userName = myPreferenceData.getData(BaseUrl.FULL_NAME);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);

        if (!Utility.isNullOrEmpty(userImage)) {
            Glide.with(this).load(userImage)
                    .into(ivUserImage);
        }
        tvName.setText(userName);

        darkModeView();
    }

    private void darkModeView() {
        if(darkModeStatus == 1){
            rlReplySection.setBackgroundResource(R.color.darkmode_back_color);
            llReplyEditSection.setBackgroundResource(R.drawable.dark_post_border);
            tvName.setTextColor(ContextCompat.getColor(this , R.color.light_white));
            tvTitle.setTextColor(ContextCompat.getColor(this , R.color.light_white));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this , R.drawable.ic_back_key_white));
        }else{
            rlReplySection.setBackgroundResource(R.color.back_color);
            llReplyEditSection.setBackgroundResource(R.color.white);
            tvName.setTextColor(ContextCompat.getColor(this , R.color.black_light));
            tvTitle.setTextColor(ContextCompat.getColor(this , R.color.black_light));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this , R.drawable.ic_back_key));

        }
    }

    @SuppressLint("SetTextI18n")
    private void initBundleData() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        name = bundle.getString(BaseUrl.NAME);
        description = bundle.getString(BaseUrl.DESCRIPTION);
        postId = bundle.getInt(BaseUrl.POST_ID);
        type = bundle.getInt(BaseUrl.TYPE);
        callClass = bundle.getInt(BaseUrl.CALL_CLASS);
        switch (type){
            case 1:
                tvTitle.setText("Reply on ( " + name + " ) Comment");
                break;
            case 2:
                tvTitle.setText("Quote on ( " + name + " ) Comment");
                break;
        }

    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        this.arEditor.onActivityResult(requestCode, resultCode, data,"image");
    }


    @OnClick(R.id.tvPost)
    public void onClickReply(){
        String areEditorDescription = this.arEditor.getARE().getHtml();
        if (Utility.isNullOrEmpty(description)) {
            Toast.makeText(this, "Description field is required..!", Toast.LENGTH_LONG).show();
            return;
        }


        switch (type){
            case 1:
                String finalReply = "<html> "+"<b style='color:blue'>\n@"+name+"</b>  "+areEditorDescription+" </html>";
                Log.e("finalReply===> " , finalReply);
                createCommentOnPost(finalReply);
                break;
            case 2:

                String finalQuote = "<html>" +
                        "<head>" +
                        "<style>" +
                        "blockquote {" +
                        "  display: block;" +
                        "  margin-top: 1em;" +
                        "  margin-bottom: 1em;" +
                        "  margin-left: 4px;" +
                        "  margin-right: 4px;" +
                        "padding-left: 25px;" +
                        "padding-right: 7px;" +
                        "border-left: 8px solid #999;" +
                        "margin-left: -0.5rem;" +
                        "  border:1px" +
                        "}" +
                        "</style>" +
                        "</head>" +
                        "<body>"+
                        "<blockquote>"+
                        "<p><b style='color:blue'>"+ name+"</style></b>"+" said <br><br>" +description+
                        "</blockquote>" +
                        "</body>" +
                        "</html>"+
                        areEditorDescription +
                        "\n";
                Log.e("quote===> " ,finalQuote);
                createCommentOnPost(finalQuote);
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
                Intent one = new Intent(this, CommentOnPostDetailsActivity.class);
                one.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(one);
                finish();
                break;
            case 0:
                Intent two = new Intent(this, PagePostCommentActivity.class);
                two.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(two);
                finish();
                break;
        }

    }

}
