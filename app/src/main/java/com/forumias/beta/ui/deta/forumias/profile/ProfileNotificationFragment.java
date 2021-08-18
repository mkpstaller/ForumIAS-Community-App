package com.forumias.beta.ui.deta.forumias.profile;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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
public class ProfileNotificationFragment extends Fragment {

    @BindView(R.id.tvEmailNotification)
    AppCompatTextView tvEmailNotification;
    @BindView(R.id.checkboxOne)
    AppCompatCheckBox checkboxOne;
    @BindView(R.id.checkboxTwo)
    AppCompatCheckBox checkboxTwo;
    @BindView(R.id.checkboxThree)
    AppCompatCheckBox checkboxThree;
    @BindView(R.id.checkboxFour)
    AppCompatCheckBox checkboxFour;
    @BindView(R.id.checkboxFive)
    AppCompatCheckBox checkboxFive;
    @BindView(R.id.checkboxSix)
    AppCompatCheckBox checkboxSix;
    @BindView(R.id.checkboxSeven)
    AppCompatCheckBox checkboxSeven;
    @BindView(R.id.checkboxEight)
    AppCompatCheckBox checkboxEight;
    @BindView(R.id.btnUpdateEmail)
    MaterialButton btnUpdateEmail;


    private int tagId , actType , loginUserId ,darkModeStatus;
    private String name;
    private int one , two , three , four ,five , six , seven , eight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_notification, container, false);
        ButterKnife.bind(this,view);
        MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(getContext()));
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        name = myPreferenceData.getData(BaseUrl.NAME);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
        viewDarkMode();

        getProfile();

        return view;
    }

    private void getProfile() {

            BetaApiClient apiClient = new BetaApiClient();
            APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
            Call<UserInfoModel> call = apiInterface.verifiedLoginUser(name,loginUserId);
            call.enqueue(new Callback<UserInfoModel>() {
                @Override
                public void onResponse(@NotNull Call<UserInfoModel> call, @NotNull Response<UserInfoModel> response) {
                    if(response.isSuccessful()) {
                        assert response.body() != null;
                        setEmailNotificatonData(response.body().getEmailSetting());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<UserInfoModel> call, @NotNull Throwable t) {

                }
            });
    }

    private void setEmailNotificatonData(UserInfoModel.EmailSetting emailSetting) {
        if(emailSetting.getPost() == 0){
            checkboxOne.setChecked(true);
        }else{
            checkboxOne.setChecked(false);
        }
        if(emailSetting.getTag() == 0){
            checkboxTwo.setChecked(true);
        }else{
            checkboxTwo.setChecked(false);
        }
        if(emailSetting.getFollow_me() == 0){
            checkboxThree.setChecked(true);
        }else{
            checkboxThree.setChecked(false);
        }
        if(emailSetting.getGroup_accepted() == 0){
            checkboxFour.setChecked(true);
        }else{
            checkboxFour.setChecked(false);
        }
        if(emailSetting.getChannel_accepted() == 0){
            checkboxFive.setChecked(true);
        }else{
            checkboxFive.setChecked(false);
        }
        if(emailSetting.getAnswer_question() == 0){
            checkboxSix.setChecked(true);
        }else{
            checkboxSix.setChecked(false);
        }
        if(emailSetting.getComment_like() == 0){
            checkboxSeven.setChecked(true);
        }else{
            checkboxSeven.setChecked(false);
        }
        if(emailSetting.getRequest_ans() == 0){
            checkboxEight.setChecked(true);
        }else{
            checkboxEight.setChecked(false);
        }

    }

    private void viewDarkMode() {
        if(darkModeStatus == 1){
            tvEmailNotification.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.light_white));
            checkboxOne.setTextColor(getResources().getColor(R.color.light_white));
            checkboxTwo.setTextColor(getResources().getColor(R.color.light_white));
            checkboxThree.setTextColor(getResources().getColor(R.color.light_white));
            checkboxFour.setTextColor(getResources().getColor(R.color.light_white));
            checkboxFive.setTextColor(getResources().getColor(R.color.light_white));
            checkboxSeven.setTextColor(getResources().getColor(R.color.light_white));
            checkboxSix.setTextColor(getResources().getColor(R.color.light_white));
            checkboxEight.setTextColor(getResources().getColor(R.color.light_white));
        }else{
            tvEmailNotification.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.black_light));
            checkboxOne.setTextColor(getResources().getColor(R.color.black_light));
            checkboxTwo.setTextColor(getResources().getColor(R.color.black_light));
            checkboxThree.setTextColor(getResources().getColor(R.color.black_light));
            checkboxFour.setTextColor(getResources().getColor(R.color.black_light));
            checkboxFive.setTextColor(getResources().getColor(R.color.black_light));
            checkboxSeven.setTextColor(getResources().getColor(R.color.black_light));
            checkboxSix.setTextColor(getResources().getColor(R.color.black_light));
            checkboxEight.setTextColor(getResources().getColor(R.color.black_light));
        }
    }

    @OnClick(R.id.btnUpdateEmail)
    public void onClickEmailUpdate(){
        if(checkboxOne.isChecked()){
            one = 0;
        }else{
            one = 1;
        }
        if(checkboxTwo.isChecked()){
            two = 0;
        }else{
            two = 1;
        }
        if(checkboxThree.isChecked()){
            three = 0;
        }else{
            three  = 1;
        }
        if(checkboxFour.isChecked()){
            four = 0;
        }else{
            four  = 1;
        }
        if(checkboxFive.isChecked()){
            five = 0;
        }else{
            five  = 1;
        }
        if(checkboxSix.isChecked()){
            six = 0;
        }else{
            six  = 1;
        }
        if(checkboxSeven.isChecked()){
            seven = 0;
        }else{
            seven  = 1;
        }
        if(checkboxEight.isChecked()){
            eight = 0;
        }else{
            eight  = 1;
        }

        postEmailData();

    }

    private void postEmailData() {
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Email Update..!");
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.show();
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<ProfileSettingModel> call = apiInterface.emailNotificationSetting(loginUserId,one , two , three ,four
                , five ,six , seven ,eight);
        call.enqueue(new Callback<ProfileSettingModel>() {
            @Override
            public void onResponse(@NotNull Call<ProfileSettingModel> call, @NotNull Response<ProfileSettingModel> response) {
                if(response.isSuccessful()){
                    pd.dismiss();
                    Toast.makeText(getContext() ,"Success Full Update.!" , Toast.LENGTH_LONG).show();
                    getProfile();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ProfileSettingModel> call, Throwable t) {
                pd.dismiss();
            }
        });

    }
}
