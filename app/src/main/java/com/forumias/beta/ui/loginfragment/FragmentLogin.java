package com.forumias.beta.ui.loginfragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
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
import android.widget.Toast;

import com.forumias.beta.api.APIClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.LoginWithPasswordModel;
import com.forumias.beta.pojo.OtpVerificationResponse;
import com.forumias.beta.pojo.RegisterBaseResponse;
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.ui.deta.forumias.splash.BetaSplashActivity;
import com.forumias.beta.R;
import com.forumias.beta.ui.deta.forumias.working_us.WebViewActivity;
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
public class FragmentLogin extends Fragment implements View.OnClickListener {


    @BindView(R.id.tvNewSignUp)
    AppCompatTextView tvNewSignUp;
    @BindView(R.id.tvLoginWithOtp)
    AppCompatTextView tvLoginWithOtp;

    @BindView(R.id.tvForgotPassword)
    AppCompatTextView tvForgotPassword;
    @BindView(R.id.editEmailOrMobile)
    AppCompatEditText editEmailOrMobile;
    @BindView(R.id.editPassword)
    AppCompatEditText editPassword;

    @BindView(R.id.btnLogin)
    AppCompatButton btnLogin;

    @BindView(R.id.tilPassword)
    TextInputLayout tilPassword;
    @BindView(R.id.tilEmailPhone)
    TextInputLayout tilEmailPhone;
    @BindView(R.id.tvWelcome)
    AppCompatTextView tvWelcome;
    @BindView(R.id.tvToContinue)
    AppCompatTextView tvToContinue;
    @BindView(R.id.tvByLogin)
    AppCompatTextView tvByLogin;
    @BindView(R.id.tvNewUser)
    AppCompatTextView tvNewUser;
    @BindView(R.id.llLayout)
    RelativeLayout llLayout;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, view);

        tvNewSignUp.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        tvLoginWithOtp.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        setDarkMode();

        return view;
    }

    private void setDarkMode() {
            MyPreferenceData sp = new MyPreferenceData(getContext());
            int darkModeStatua = sp.getIntegerData(BaseUrl.DARK_MODE);
            if(darkModeStatua == 1){
                editEmailOrMobile.setTextColor(ContextCompat.getColor(getContext(),R.color.light_white));
                editPassword.setTextColor(ContextCompat.getColor(getContext(),R.color.light_white));
                tvWelcome.setTextColor(ContextCompat.getColor(getContext(),R.color.light_white));
                tvToContinue.setTextColor(ContextCompat.getColor(getContext(),R.color.light_white));
                tvLoginWithOtp.setTextColor(ContextCompat.getColor(getContext(),R.color.light_white));
                tvForgotPassword.setTextColor(ContextCompat.getColor(getContext(),R.color.light_white));
                tvNewUser.setTextColor(ContextCompat.getColor(getContext(),R.color.light_white));
                tvByLogin.setTextColor(ContextCompat.getColor(getContext(),R.color.light_white));
                tilPassword.setBoxStrokeColor(ContextCompat.getColor(getContext(),R.color.text_input_color));
                tilEmailPhone.setBoxStrokeColor(ContextCompat.getColor(getContext(),R.color.text_input_color));
                llLayout.setBackgroundResource(R.color.darkmode_back_color);
            }else{
                editEmailOrMobile.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                editPassword.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                tvWelcome.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                tvToContinue.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                tvLoginWithOtp.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                tvForgotPassword.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                tvNewUser.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                tvByLogin.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
                tilPassword.setBoxStrokeColor(ContextCompat.getColor(getContext(),R.color.text_input_color));
                tilEmailPhone.setBoxStrokeColor(ContextCompat.getColor(getContext(),R.color.text_input_color));
                llLayout.setBackgroundResource(R.color.white);
            }
    }

    @OnClick(R.id.llTermsCondition)
    public void onClickTermsCondition(){
        Intent intent = new Intent(getContext() , WebViewActivity.class);
        intent.putExtra(BaseUrl.WEB_LINK , BaseUrl.TERMS_CONDITION);
        startActivity(intent);
    }
    @OnClick(R.id.ivBack)
    public void onClickBack(){
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStackImmediate();

    }

    @Override
    public void onClick(View view) {
        Fragment fragment;
        switch (view.getId()) {
            case R.id.tvNewSignUp:
               fragment = new FragmentSignUp();
                replaceFragment(fragment);
                break;
            case R.id.tvForgotPassword:
                fragment = new FragmentForgot();
                replaceFragment(fragment);
                break;
            case R.id.tvLoginWithOtp:
                fragment = new OptFragment();
                replaceFragment(fragment);
                break;
            case R.id.btnLogin:

                if (TextUtils.isEmpty(Objects.requireNonNull(editEmailOrMobile.getText()).toString().trim())) {
                    editEmailOrMobile.setError(getString(R.string.required_field));
                    editEmailOrMobile.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(Objects.requireNonNull(editPassword.getText()).toString().trim())) {
                    editPassword.setError(getString(R.string.required_field));
                    editPassword.requestFocus();
                    return;
                }
                emailOrNumberValidation(editEmailOrMobile.getText().toString() , editPassword.getText().toString());

                break;
        }
    }

    private void loginWithPassword(String email , String studentId , String password) {
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage(getString(R.string.login_verification));
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("student_id", studentId);
        params.put("user_pass", password);

        APIClient apiClient = new APIClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<LoginWithPasswordModel> call = apiInterface.LoginWithPassword(params);
        call.enqueue(new Callback<LoginWithPasswordModel>() {
            @Override
            public void onResponse(Call<LoginWithPasswordModel> call, Response<LoginWithPasswordModel> response) {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                assert response.body() != null;
                if (response.body().getStatus() == 1) {
                    //Toast.makeText(getActivity(), "Successfully logged in.", Toast.LENGTH_LONG).show();
                    MyUtility myUtility = new MyUtility();
                    myUtility.addCustomToast("Successfully logged in." , Objects.requireNonNull(getActivity()));
                    MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(getContext()));
                    myPreferenceData.saveData(BaseUrl.STATUS, String.valueOf(response.body().getStatus()));
                    myPreferenceData.saveIntegerData(BaseUrl.AUTH_ID, Integer.parseInt(studentId));
                    Intent intent = new Intent(getActivity(), BetaSplashActivity.class);
                    Objects.requireNonNull(getActivity()).startActivity(intent);
                    getActivity().finish();
                } else if (response.body().getStatus() == 2) {
                    MyUtility myUtility = new MyUtility();
                    myUtility.addCustomToast("Invalid Password." , Objects.requireNonNull(getActivity()));
                   // Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginWithPasswordModel> call, @NotNull Throwable t) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }

                Toast.makeText(getActivity(), "Invalid Number Or Password", Toast.LENGTH_LONG).show();
            }
        });
    }


   /* private void loginWithMagicLink(String mobile , String studentId) {
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage(getString(R.string.send_link));
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        Map<String, String> params = new HashMap<>();
        params.put("phone", mobile);
        params.put("student_id", studentId);


        APIClient apiClient = new APIClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<RegisterBaseResponse> call = apiInterface.LoginWithMagicLink(params);
        call.enqueue(new Callback<RegisterBaseResponse>() {
            @Override
            public void onResponse(@NotNull Call<RegisterBaseResponse> call, @NotNull Response<RegisterBaseResponse> response) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                assert response.body() != null;
                if (response.body().getStatus() == 1) {
                    MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(getContext()));
                    myPreferenceData.saveIntegerData(BaseUrl.STATUS, response.body().getStatus());

                } else if (response.body().getStatus() == 2) {
                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<RegisterBaseResponse> call, @NotNull Throwable t) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });
    }*/

    private void emailOrNumberValidation(String emailOrNumber , String password) {
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
                if (response.body().getStatus() == 1) {

                    loginWithPassword(response.body().getData().getEmail(),
                            String.valueOf(response.body().getData().getUserId()) , password);

                } else{
                   // Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OtpVerificationResponse> call, Throwable t) {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                MyUtility myUtility = new MyUtility();
                myUtility.addCustomToast("Register first...!" , Objects.requireNonNull(getActivity()));
              //  Toast.makeText(getActivity(), "Register first...!", Toast.LENGTH_LONG).show();
            }
        });
    }

   private void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            ft.add(R.id.login_framelayout, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

}
