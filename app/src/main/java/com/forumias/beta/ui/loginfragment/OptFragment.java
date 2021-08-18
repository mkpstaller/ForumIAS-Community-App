package com.forumias.beta.ui.loginfragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chaos.view.PinView;
import com.forumias.beta.api.APIClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.LoginOtpVerification;
import com.forumias.beta.pojo.OtpVerificationResponse;
import com.forumias.beta.pojo.RegisterBaseResponse;
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.R;
import com.forumias.beta.ui.deta.forumias.splash.BetaSplashActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
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
public class OptFragment extends Fragment {

    @BindView(R.id.tvUserInfo)
    AppCompatTextView tvUserInfo;
    @BindView(R.id.editEmailOrMobile)
    AppCompatEditText editEmailOrMobile;
    @BindView(R.id.editOtp)
    AppCompatEditText editOtp;
    @BindView(R.id.btnLoginWithOtp)
    MaterialButton btnLoginWithOtp;
    @BindView(R.id.tiLOtpSection)
    TextInputLayout tiLOtpSection;
    @BindView(R.id.tiLEmailOrNumberSection)
    TextInputLayout tiLEmailOrNumberSection;
    @BindView(R.id.tvReSendOtp)
    AppCompatTextView tvReSendOtp;
    @BindView(R.id.tvNewSignUp)
    AppCompatTextView tvNewSignUp;
    @BindView(R.id.tvNewUser)
    AppCompatTextView tvNewUser;
    @BindView(R.id.llLayout)
    RelativeLayout llLayout;
    @BindView(R.id.otpText)
    PinView otpText;

    private int checkStatus = 1;
    private String studentId , mobileNumber;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opt, container, false);
        ButterKnife.bind(this,view);


        Bundle bundle = getArguments();
        if(bundle != null) {
            studentId = String.valueOf(bundle.getInt(BaseUrl.USER_ID));
            mobileNumber = bundle.getString(BaseUrl.MOBILE);
            checkStatus = 3;
            btnLoginWithOtp.setText(R.string.verifid_otp);
            tiLOtpSection.setVisibility(View.GONE);
            otpText.setVisibility(View.VISIBLE);
            tiLEmailOrNumberSection.setVisibility(View.GONE);
            tvUserInfo.setText("Please enter the OTP");
            tvReSendOtp.setVisibility(View.VISIBLE);
        }

        setDarkMode();

        return view;
    }

    private void setDarkMode() {
        MyPreferenceData sp = new MyPreferenceData(getContext());
        int darkModeStatus = sp.getIntegerData(BaseUrl.DARK_MODE);

        if(darkModeStatus == 1){
            tvUserInfo.setTextColor(ContextCompat.getColor(getContext(),R.color.light_white));
            tvNewUser.setTextColor(ContextCompat.getColor(getContext(),R.color.light_white));
            editEmailOrMobile.setTextColor(ContextCompat.getColor(getContext(),R.color.light_white));
            otpText.setTextColor(ContextCompat.getColor(getContext(),R.color.light_white));
            llLayout.setBackgroundResource(R.color.darkmode_back_color);

        }else{
            tvUserInfo.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
            tvNewUser.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
            editEmailOrMobile.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
            otpText.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
            llLayout.setBackgroundResource(R.color.white);
        }

        tiLEmailOrNumberSection.setBoxStrokeColor(ContextCompat.getColor(getContext() , R.color.text_input_color));
        tiLOtpSection.setBoxStrokeColor(ContextCompat.getColor(getContext() , R.color.text_input_color));
    }

    @OnClick(R.id.ivBack)
    public void onClickBack(){
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStackImmediate();
    }

    @OnClick(R.id.btnLoginWithOtp)
    public void onClickLoginWithOtp(){
        if(checkStatus == 1){
            if (TextUtils.isEmpty(Objects.requireNonNull(editEmailOrMobile.getText()).toString().trim())) {
                editEmailOrMobile.setError(getString(R.string.required_field));
                editEmailOrMobile.requestFocus();
                return;
            }
            emailOrNumberValidation(editEmailOrMobile.getText().toString());
        }

        if(checkStatus == 2){


          /*  if(TextUtils.isEmpty(Objects.requireNonNull(editOtp.getText()).toString().trim())){
                editOtp.setError(getString(R.string.required_field));
                editOtp.requestFocus();
                return;
            }

            if(editOtp.getText().toString().length() != 6){
                editOtp.setError(getString(R.string.input_all_number));
                editOtp.requestFocus();
                return;
            }*/


            if(TextUtils.isEmpty(Objects.requireNonNull(otpText.getText()).toString().trim())){
               // otpText.setError(getString(R.string.required_field));
               // otpText.requestFocus();
                otpText.setLineColor(Color.RED);
                return;
            }

            if(otpText.getText().toString().length() != 6){
                otpText.setError(getString(R.string.input_all_number));
                otpText.requestFocus();
                return;
            }

            otpSubmited(otpText.getText().toString());
        }

        if(checkStatus == 3){

          /*  if(TextUtils.isEmpty(Objects.requireNonNull(editOtp.getText()).toString().trim())){
                editOtp.setError(getString(R.string.required_field));
                editOtp.requestFocus();
                return;
            }

            if(editOtp.getText().toString().length() != 6){
                editOtp.setError(getString(R.string.input_all_number));
                editOtp.requestFocus();
                return;
            }
*/
            if(TextUtils.isEmpty(Objects.requireNonNull(otpText.getText()).toString().trim())){
                // otpText.setError(getString(R.string.required_field));
                // otpText.requestFocus();
                otpText.setLineColor(Color.RED);
                return;
            }

            if(otpText.getText().toString().length() != 6){
                otpText.setError(getString(R.string.input_all_number));
                otpText.requestFocus();
                return;
            }

            otpSignUpSubmited(otpText.getText().toString());
        }
    }


    @OnClick(R.id.tvReSendOtp)
    public void onClickReSendOtp(){
        emailOrNumberValidation(Objects.requireNonNull(editEmailOrMobile.getText()).toString());
    }

    @OnClick(R.id.tvNewSignUp)
    public void onClickSignUp(){
        Fragment fragment = new FragmentSignUp();
        replaceFragment(fragment);
    }

    private void emailOrNumberValidation(String emailOrNumber) {
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage(getString(R.string.verification));
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        Map<String, String> params = new HashMap<>();
        params.put("emailorphone", emailOrNumber);

        APIClient apiClient = new APIClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<OtpVerificationResponse> call = apiInterface.loginStudent(params);
        call.enqueue(new Callback<OtpVerificationResponse>() {
            @Override
            public void onResponse(@NotNull Call<OtpVerificationResponse> call, @NotNull Response<OtpVerificationResponse> response) {
                pd.dismiss();
                assert response.body() != null;
                try {
                    if (response.body().getStatus() == 1) {
                        sendOtpEmailOrNumber(response.body().getData().getMobile(),
                                response.body().getData().getUserId());
                        MyUtility myUtility = new MyUtility();
                        myUtility.addCustomToast(response.body().getMessage(), Objects.requireNonNull(getActivity()));
                    } else {
                        MyUtility myUtility = new MyUtility();
                        myUtility.addCustomToast(response.body().getMessage(), Objects.requireNonNull(getActivity()));
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<OtpVerificationResponse> call, @NotNull Throwable t) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                MyUtility myUtility = new MyUtility();
                myUtility.addCustomToast("Register first...!" , Objects.requireNonNull(getActivity()));
            }
        });
    }

    private void sendOtpEmailOrNumber(long mobile, int userId) {

        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Send OTP on register number...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        Map<String, String> params = new HashMap<>();
        params.put("phone", String.valueOf(mobile));
        params.put("student_id", String.valueOf(userId));
        studentId = String.valueOf(userId);
        mobileNumber = String.valueOf(mobile);
        APIClient apiClient = new APIClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<RegisterBaseResponse> call = apiInterface.getOtp(params);
        call.enqueue(new Callback<RegisterBaseResponse>() {
            @Override
            public void onResponse(@NotNull Call<RegisterBaseResponse> call, @NotNull Response<RegisterBaseResponse> response) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                assert response.body() != null;
                try{
                if (response.body().getStatus() == 1) {
                    checkStatus = 2;
                    btnLoginWithOtp.setText(R.string.verifid_otp);
                    tiLOtpSection.setVisibility(View.GONE);
                    otpText.setVisibility(View.VISIBLE);
                    tiLEmailOrNumberSection.setVisibility(View.GONE);
                    tvUserInfo.setText("Please enter the OTP");
                    tvReSendOtp.setVisibility(View.VISIBLE);
                    MyUtility myUtility = new MyUtility();
                    myUtility.addCustomToast(response.body().getMessage() , Objects.requireNonNull(getActivity()));
                    MyPreferenceData preferenceData = new MyPreferenceData(Objects.requireNonNull(getContext()));
                    preferenceData.saveIntegerData(BaseUrl.AUTH_ID,response.body().getData().getId());
                } else if (response.body().getStatus() == 2) {
                    MyUtility myUtility = new MyUtility();
                    myUtility.addCustomToast(response.body().getMessage() , Objects.requireNonNull(getActivity()));
                }
                }catch (Exception e){e.printStackTrace();}
            }

            @Override
            public void onFailure(@NotNull Call<RegisterBaseResponse> call, @NotNull Throwable t) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });
    }



    private void otpSignUpSubmited(String otp) {
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Verifying OTP..!");
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.show();
        Map<String,String> params = new HashMap<>();
        params.put("student_id",String.valueOf(studentId));
        params.put("OTP",otp);
        params.put("phone",mobileNumber);

        APIClient apiClient = new APIClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<OtpVerificationResponse> call = apiInterface.sendOtpVerification(params);
        call.enqueue(new Callback<OtpVerificationResponse>() {
            @Override
            public void onResponse(@NotNull Call<OtpVerificationResponse> call, @NotNull Response<OtpVerificationResponse> response) {
                if(pd.isShowing()){
                    pd.dismiss();
                }
                assert response.body() != null;
                try{
                if(response.body().getStatus() == 1){
                    MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(getContext()));
                    myPreferenceData.saveIntegerData(BaseUrl.AUTH_ID,Integer.parseInt(studentId));//Old code
                    myPreferenceData.saveIntegerData(BaseUrl.AUTH_ID,response.body().data.getUserId());
                    Intent intent = new Intent(getActivity() , BetaSplashActivity.class);
                    Objects.requireNonNull(getActivity()).startActivity(intent);
                    getActivity().finish();

                }else if(response.body().getStatus() == 2){
                    MyUtility myUtility = new MyUtility();
                    myUtility.addCustomToast(response.body().getMessage() , Objects.requireNonNull(getActivity()));
                }
                }catch (Exception e){e.printStackTrace();}
            }

            @Override
            public void onFailure(@NotNull Call<OtpVerificationResponse> call, @NotNull Throwable t) {
                if(pd.isShowing()){
                    pd.dismiss();
                }
                MyUtility myUtility = new MyUtility();
                myUtility.addCustomToast("OTP is not valid" , Objects.requireNonNull(getActivity()));
              //  Toast.makeText(getActivity(), "OTP is not valid", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void otpSubmited(String otp) {
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Verifying OTP..!");
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.show();
        Map<String,String> params = new HashMap<>();
        params.put("student_id",String.valueOf(studentId));
        params.put("OTP",otp);
        params.put("phone",mobileNumber);

        APIClient apiClient = new APIClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<LoginOtpVerification> call = apiInterface.verificationOtpLogin(params);
        call.enqueue(new Callback<LoginOtpVerification>() {
            @Override
            public void onResponse(@NotNull Call<LoginOtpVerification> call, @NotNull Response<LoginOtpVerification> response) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                assert response.body() != null;
                try {
                if (response.body().getStatus() == 1) {
                    MyUtility myUtility = new MyUtility();
                    myUtility.addCustomToast("Successfully logged in." , Objects.requireNonNull(getActivity()));
                    MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(getContext()));
                    myPreferenceData.saveIntegerData(BaseUrl.STATUS, response.body().getStatus());
                    Intent intent = new Intent(getActivity(), BetaSplashActivity.class);
                    Objects.requireNonNull(getActivity()).startActivity(intent);
                    getActivity().finish();
                } else if (response.body().getStatus() == 2) {
                    MyUtility myUtility = new MyUtility();
                    myUtility.addCustomToast(response.body().getMessage() , Objects.requireNonNull(getActivity()));
                }
                }catch (Exception e){e.printStackTrace();}
            }

            @Override
            public void onFailure(@NotNull Call<LoginOtpVerification> call, @NotNull Throwable t) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                MyUtility myUtility = new MyUtility();
                myUtility.addCustomToast("OTP INVALID" , Objects.requireNonNull(getActivity()));
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.login_framelayout, fragment);
            ft.commit();
        }
    }

}
