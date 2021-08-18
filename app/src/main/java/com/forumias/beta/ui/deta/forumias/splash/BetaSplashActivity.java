package com.forumias.beta.ui.deta.forumias.splash;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.forumias.beta.api.APIInterface;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.BookMarkResponse;
import com.forumias.beta.utility.InternetConnection;
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.BuildConfig;
import com.forumias.beta.R;
import com.forumias.beta.ui.deta.WelcomeHomeActivity;
import com.google.android.material.snackbar.Snackbar;
import com.wang.avi.AVLoadingIndicatorView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/*import eu.dkaratzas.android.inapp.update.Constants;
import eu.dkaratzas.android.inapp.update.InAppUpdateManager;
import eu.dkaratzas.android.inapp.update.InAppUpdateStatus;*/
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BetaSplashActivity extends AppCompatActivity{

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;
    String versionName;
    int authId;
    private int REQUEST_CODE = 11;
    private static final int REQ_CODE_VERSION_UPDATE = 530;
    private static final String TAG = "Sample";
   // private InAppUpdateManager inAppUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beta_splash);
        ButterKnife.bind(this);
        authId = new MyPreferenceData(BetaSplashActivity.this).getIntegerData(BaseUrl.AUTH_ID);

        InternetConnection internetConnection = new InternetConnection();
        boolean onlineStatus  = internetConnection.checkConnection(this);
        if(onlineStatus){
          // checkAppVersion();
            getPostSetting(authId);
        }else{
            MyUtility myUtility = new MyUtility();
            myUtility.checkConnection(this);
        }


        avi.show();

    }

    private void checkLoginStatus(int authId){
        if(authId == 0){
           // Log.e("auth id===1=> " , String.valueOf(authId));
            Intent intent = new Intent(BetaSplashActivity.this , WelcomeHomeActivity.class);
            startActivity(intent);
            finish();
        }else {
          //  Log.e("auth id==2==> " , String.valueOf(authId));
            loginVerification(authId);
        }
    }

    private void loginVerification(int authId) {
        progressBar.setVisibility(View.GONE);
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<BookMarkResponse> call = apiInterface.getLoginVerification(authId);
        call.enqueue(new Callback<BookMarkResponse>() {
            @Override
            public void onResponse(@NotNull Call<BookMarkResponse> call, @NotNull Response<BookMarkResponse> response) {
                assert response.body() != null;
                progressBar.setVisibility(View.GONE);
                if(response.body().getStatus().contentEquals(BaseUrl.SUCCESS)){
                    getUserDetails(response.body().getUserName() , response.body().getId());
                }
            }
            @Override
            public void onFailure(@NotNull Call<BookMarkResponse> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

  private void getUserDetails(String userName  , int loginUserId) {
        progressBar.setVisibility(View.GONE);
        BetaApiClient apiClient = new BetaApiClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<UserInfoModel> call = apiInterface.verifiedLoginUser(userName,loginUserId);
        call.enqueue(new Callback<UserInfoModel>() {
            @Override
            public void onResponse(@NotNull Call<UserInfoModel> call, @NotNull Response<UserInfoModel> response) {
                progressBar.setVisibility(View.GONE);

                assert response.body() != null;
                int userId = response.body().getUserDetails().getId();
                MyPreferenceData preferenceData = new MyPreferenceData(BetaSplashActivity.this);
                preferenceData.saveIntegerData(BaseUrl.USER_ID,userId);
                preferenceData.saveData(BaseUrl.SUCCESS,"success");
                preferenceData.saveData(BaseUrl.NAME,response.body().getUserDetails().getName());
                preferenceData.saveData(BaseUrl.EMAIL,response.body().getUserDetails().getEmail());
                preferenceData.saveData(BaseUrl.FULLNAME,response.body().getUserDetails().getFull_name());
                preferenceData.saveData(BaseUrl.TAG_IMAGE,response.body().getUserDetails().getImage());
                preferenceData.saveData(BaseUrl.ABOUT,response.body().getUserDetails().getAbout());
                preferenceData.saveIntegerData(BaseUrl.FOLLOWER_DATA,response.body().getUserDetails().getFollowers());
                preferenceData.saveIntegerData(BaseUrl.FOLLOWING_DATA,response.body().getUserDetails().getFollowing());
                preferenceData.saveIntegerData(BaseUrl.USER_TYPE,response.body().getUserDetails().getType());
                preferenceData.saveIntegerData(BaseUrl.POST_COUNT,response.body().getUserDetails().getTotal_posts());
                preferenceData.saveIntegerData(BaseUrl.IS_VERIFIED,response.body().getUserDetails().getIs_verified());
                Intent intent = new Intent(BetaSplashActivity.this , WelcomeHomeActivity.class);
                startActivity(intent);
                finish();
                avi.hide();
            }

            @Override
            public void onFailure(@NotNull Call<UserInfoModel> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void getPostSetting(int authId){
        progressBar.setVisibility(View.GONE);
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<PostSettingModel> call = apiInterface.getPostSettingData("1");

        call.enqueue(new Callback<PostSettingModel>() {
            @Override
            public void onResponse(@NotNull Call<PostSettingModel> call, @NotNull Response<PostSettingModel> response) {
                progressBar.setVisibility(View.GONE);
                assert response.body() != null;

                if(response.isSuccessful()){
                    if(response.body().getSettingLists().size() > 0){
                        saveSettingData(response.body().getSettingLists());
                    }
                    checkLoginStatus(authId);
                }

            }

            @Override
            public void onFailure(@NotNull Call<PostSettingModel> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void saveSettingData(List<PostSettingModel.SettingList> settingLists) {
        MyPreferenceData preferenceData = new MyPreferenceData(BetaSplashActivity.this);

        for(int i = 0; i < settingLists.size(); i++){
            if(settingLists.get(i).getType() == 4){
                preferenceData.saveIntegerData(BaseUrl.ARTICLE_VISIBLE,settingLists.get(i).getVisibility());
            }
            if(settingLists.get(i).getType() == 5){
                preferenceData.saveIntegerData(BaseUrl.SHORT_DES_VISIBLE,settingLists.get(i).getVisibility());
            }
        }
    }

}
