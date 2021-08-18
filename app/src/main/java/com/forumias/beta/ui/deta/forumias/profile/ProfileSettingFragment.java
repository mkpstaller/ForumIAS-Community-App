package com.forumias.beta.ui.deta.forumias.profile;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.ui.deta.forumias.profile.model.ProfileSettingModel;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.ui.deta.forumias.splash.UserInfoModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */

public class ProfileSettingFragment extends Fragment {
    @BindView(R.id.tvProfileTitle)
    AppCompatTextView tvProfileTitle;
    @BindView(R.id.tvEmailAddress)
    AppCompatTextView tvEmailAddaress;
    @BindView(R.id.tvRealName)
    AppCompatTextView tvRealName;
    @BindView(R.id.tvSignature)
    AppCompatTextView tvSignature;
    @BindView(R.id.tvUserName)
    AppCompatTextView tvUserName;
    @BindView(R.id.checkboxFollowMe)
    MaterialCheckBox checkboxFollowMe;
    @BindView(R.id.checkboxHideRealName)
    MaterialCheckBox checkboxHideRealName;
/*  @BindView(R.id.tilUserName)
    TextInputLayout tilUserName;
    @BindView(R.id.editUserName)
    TextInputEditText editUserName;*/
    @BindView(R.id.tvShowUserName)
    AppCompatTextView tvShowUserName;
    @BindView(R.id.editSignature)
    TextInputEditText editSignature;
    //@BindView(R.id.editEmail)
    //TextInputEditText editEmail;
    @BindView(R.id.editRealName)
    TextInputEditText editRealName;
    @BindView(R.id.tvUpdateProfile)
    MaterialButton tvUpdateProfile;


    private int loginUserId , darkModeStatus , folloBackStatus , hideRealNameStatus;
    private String userName , email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_setting, container, false);
        ButterKnife.bind(this , view);
        MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(getContext()));
        userName = myPreferenceData.getData(BaseUrl.NAME);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
        email = myPreferenceData.getData(BaseUrl.EMAIL);


        viewDayDarkMode();
        setUserInfo();

        return view;
    }

    @SuppressLint("ResourceAsColor")
    private void viewDayDarkMode() {
        if(darkModeStatus == 1){

            tvProfileTitle.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.light_white));
            tvRealName.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.light_white));
            tvSignature.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.light_white));
          //  tvEmailAddaress.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.light_white));
            tvUserName.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.light_white));
            checkboxFollowMe.setTextColor(getResources().getColor(R.color.light_white));
            checkboxHideRealName.setTextColor(getResources().getColor(R.color.light_white));
            tvShowUserName.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));
        }else{
            tvProfileTitle.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.black_light));
            tvRealName.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.black_light));
            tvSignature.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.black_light));
           // tvEmailAddaress.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.black_light));
            tvUserName.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.black_light));
            checkboxFollowMe.setTextColor(getResources().getColor(R.color.black_light));
            checkboxHideRealName.setTextColor(getResources().getColor(R.color.black_light));
            tvShowUserName.setTextColor(ContextCompat.getColor(getContext(), R.color.black_light));

        }
    }


    @OnClick(R.id.tvUpdateProfile)
    public void onClickUpdateProfile(){
        String signature = editSignature.getText().toString();
        checkboxHideRealName.isChecked();
        checkboxFollowMe.isChecked(); // v0/settings

       // Log.e("chcekc Test==> " , String.valueOf(checkboxHideRealName.isChecked()));

        if(checkboxFollowMe.isChecked()){
            folloBackStatus =1;
        }else{
            folloBackStatus = 0;
        }
        if(checkboxHideRealName.isChecked()){
            hideRealNameStatus = 1;
        }else{
            hideRealNameStatus = 0;
        }

        profileSetting(signature , folloBackStatus , hideRealNameStatus);
    }

    private void profileSetting(String signature, int folloBackStatus, int hideRealNameStatus) {
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Profile Update..!");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
            BetaApiClient betaApiClient = new BetaApiClient();
            APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
            Call<ProfileSettingModel> call = apiInterface.postProfileSettingData(loginUserId , hideRealNameStatus
                    , folloBackStatus, signature);

            call.enqueue(new Callback<ProfileSettingModel>() {
                @Override
                public void onResponse(@NotNull Call<ProfileSettingModel> call, @NotNull Response<ProfileSettingModel> response) {
                    if(response.isSuccessful()){
                        pd.dismiss();
                        assert response.body() != null;
                        if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                            Toast.makeText(getContext() , response.body().getMessage() , Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ProfileSettingModel> call, Throwable t) {

                }
            });
    }

    private void setUserInfo() {

        BetaApiClient apiClient = new BetaApiClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<UserInfoModel> call = apiInterface.verifiedLoginUser(userName,loginUserId);
        call.enqueue(new Callback<UserInfoModel>() {
            @Override
            public void onResponse(@NotNull Call<UserInfoModel> call, @NotNull Response<UserInfoModel> response) {

                if(response.isSuccessful()){
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        setUserProfileView(response.body().getUserDetails());
                    }
                }
                //Log.e("User Responce=== > " , response.body().getUserDetails().getFull_name());
            }

            @Override
            public void onFailure(@NotNull Call<UserInfoModel> call, @NotNull Throwable t) {
            }
        });


    }

    private void setUserProfileView(UserInfoModel.UserDetails userDetails) {
        try {
            tvShowUserName.setText(userDetails.getName());
            editSignature.setHint(userDetails.getAbout());
            tvEmailAddaress.setText(userDetails.getEmail());
            editRealName.setHint(userDetails.getFull_name());
        }catch (Exception e){e.printStackTrace();}


          //  Log.e("dasdassad Email====> " , userDetails.getAbout());
    }


}
