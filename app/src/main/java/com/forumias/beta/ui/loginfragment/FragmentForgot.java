package com.forumias.beta.ui.loginfragment;


import android.app.ProgressDialog;
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


import com.forumias.beta.api.APIClient;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.DefaultPojo;
import com.forumias.beta.pojo.OtpVerificationResponse;
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.R;
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
public class FragmentForgot extends Fragment implements View.OnClickListener{ // for use  implements View.OnClickListener


    @BindView(R.id.editEmailOrMobile)
    AppCompatEditText editEmailOrMobile;
    @BindView(R.id.btnForgotPassword)
    MaterialButton btnForgetPassword;
    @BindView(R.id.tvNewSignUp)
    AppCompatTextView tvNewSignUp;
    @BindView(R.id.tvForgetYourPass)
    AppCompatTextView tvForgetYourPass;
    @BindView(R.id.tvUserInfo)
    AppCompatTextView tvUserInfo;
    @BindView(R.id.tiLEmailOrNumberSection)
    TextInputLayout tiLEmailOrNumberSection;
    @BindView(R.id.tvNewUser)
    AppCompatTextView tvNewUser;
    @BindView(R.id.llLayout)
    RelativeLayout llLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot, container, false);

        ButterKnife.bind(this, view);

        tvNewSignUp.setOnClickListener(this);
        btnForgetPassword.setOnClickListener(this);

        setDarkMode();

        return view;
    }

    private void setDarkMode() {
        MyPreferenceData sp = new MyPreferenceData(getContext());
        int darkModeStatus = sp.getIntegerData(BaseUrl.DARK_MODE);
        if(darkModeStatus == 1){
            llLayout.setBackgroundResource(R.color.darkmode_back_color);
            tvForgetYourPass.setTextColor(ContextCompat.getColor(getContext(),R.color.light_white));
            tvUserInfo.setTextColor(ContextCompat.getColor(getContext(),R.color.light_white));
            tvNewUser.setTextColor(ContextCompat.getColor(getContext(),R.color.light_white));
            editEmailOrMobile.setTextColor(ContextCompat.getColor(getContext() , R.color.light_white));
        }else{
            llLayout.setBackgroundResource(R.color.white);
            tvForgetYourPass.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
            tvUserInfo.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
            tvNewUser.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
            editEmailOrMobile.setTextColor(ContextCompat.getColor(getContext() , R.color.black));
        }

        tiLEmailOrNumberSection.setBoxStrokeColor(ContextCompat.getColor(getContext(),R.color.text_input_color));
    }

    @OnClick(R.id.ivBack)
    public void onClikBack(){
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStackImmediate();
    }

  @Override
    public void onClick(View view) {
        Fragment fragment;
        switch (view.getId()) {
            case R.id.btnLogin:
               fragment = new FragmentSignUp();
                replaceFragment(fragment);
                break;

            case R.id.btnForgotPassword:
                String emailOrMobile = Objects.requireNonNull(editEmailOrMobile.getText()).toString().trim();
                if(TextUtils.isEmpty(emailOrMobile)){
                    editEmailOrMobile.setError(getString(R.string.required_field));
                    editEmailOrMobile.requestFocus();
                    return;
                }
                UserVerifyingService(emailOrMobile);

                break;
        }
    }


   private void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.login_framelayout, fragment);
            ft.commit();
        }
    }

    private void UserVerifyingService(String emailOrNumber) {
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Verification...!");
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
            public void onResponse(@NotNull Call<OtpVerificationResponse> call, Response<OtpVerificationResponse> response) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                assert response.body() != null;
                if (response.body().getStatus() == 1) {
                    requestForForgotPassword(response.body().getData().getEmail() , response.body().getData().getUserId());
                } else if (response.body().getStatus() == 2) {
                    MyUtility myUtility = new MyUtility();
                    myUtility.addCustomToast(response.body().getMessage() , Objects.requireNonNull(getActivity()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<OtpVerificationResponse> call, @NotNull Throwable t) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });
    }


    private void requestForForgotPassword(String email, int userId) {

        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Sending password on mail...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("student_id", String.valueOf(userId));
        APIClient apiClient = new APIClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<DefaultPojo> call = apiInterface.requestForgotPass(params);
        call.enqueue(new Callback<DefaultPojo>() {
            @Override
            public void onResponse(Call<DefaultPojo> call, Response<DefaultPojo> response) {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                assert response.body() != null;
                if (response.body().getStatus() == 1) {
                    MyUtility myUtility = new MyUtility();
                    myUtility.addCustomToast(response.body().getMessage() , Objects.requireNonNull(getActivity()));
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
                } else if (response.body().getStatus() == 2) {
                    MyUtility myUtility = new MyUtility();
                    myUtility.addCustomToast(response.body().getMessage() , Objects.requireNonNull(getActivity()));
                }
            }

            @Override
            public void onFailure(@NotNull Call<DefaultPojo> call, @NotNull Throwable t) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });

    }


   /* private void showMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(llLayout, message, Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(llLayout, message, Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });

        snackbar.show();
    }*/

}
