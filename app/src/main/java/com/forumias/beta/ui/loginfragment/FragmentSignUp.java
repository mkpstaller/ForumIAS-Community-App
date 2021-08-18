package com.forumias.beta.ui.loginfragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.forumias.beta.api.APIClient;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.RegisterBaseResponse;
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.utility.Utility;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSignUp extends Fragment implements View.OnClickListener {

    @BindView(R.id.btnRegister)
    MaterialButton btnRegister;
    @BindView(R.id.tvLoginPage)
    AppCompatTextView tvLoginPage;
    @BindView(R.id.editDisplayName)
    AppCompatEditText editDisplayName;
    @BindView(R.id.editFullName)
    AppCompatEditText editFullName;
    @BindView(R.id.editEmail)
    AppCompatEditText editEmail;
    @BindView(R.id.editMobile)
    AppCompatEditText editMobile;
    @BindView(R.id.llLayout)
    RelativeLayout llLayout;
    @BindView(R.id.tilMobileNumber)
    TextInputLayout tilMobileNumber;
    @BindView(R.id.tilEmail)
    TextInputLayout tilEmail;
    @BindView(R.id.tilFullName)
    TextInputLayout tilFullName;
    @BindView(R.id.tilDisplayName)
    TextInputLayout tilDisplayName;
    @BindView(R.id.tvCreateAccount)
    AppCompatTextView tvCreatedAccount;
    @BindView(R.id.togetStarted)
    AppCompatTextView togetStarted;
    @BindView(R.id.tvNewUser)
    AppCompatTextView tvNewUser;
    @BindView(R.id.tvByCreated)
    AppCompatTextView tvByCreated;
    @BindView(R.id.tvMessageForDublicate)
    AppCompatTextView tvMessageForDublicate;
    @BindView(R.id.tvMessageForDublicateEmail)
    AppCompatTextView tvMessageForDublicateEmail;
    @BindView(R.id.tvMessageForDublicateName)
    AppCompatTextView tvMessageForDublicateName;


    String mobile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.bind(this, view);
        btnRegister.setOnClickListener(this);
        tvLoginPage.setOnClickListener(this);
        setDarkModeView();
        return view;
    }

    private void setDarkModeView() {
        MyPreferenceData sp = new MyPreferenceData(getContext());
        int darkModelStatus = sp.getIntegerData(BaseUrl.DARK_MODE);

        if(darkModelStatus == 1){
            llLayout.setBackgroundResource(R.color.darkmode_back_color);
            editMobile.setTextColor(ContextCompat.getColor(getContext() , R.color.light_white));
            editEmail.setTextColor(ContextCompat.getColor(getContext() , R.color.light_white));
            editFullName.setTextColor(ContextCompat.getColor(getContext() , R.color.light_white));
            editDisplayName.setTextColor(ContextCompat.getColor(getContext() , R.color.light_white));
            tvCreatedAccount.setTextColor(ContextCompat.getColor(getContext() , R.color.light_white));
            tvByCreated.setTextColor(ContextCompat.getColor(getContext() , R.color.light_white));
            togetStarted.setTextColor(ContextCompat.getColor(getContext() , R.color.light_white));
            tvNewUser.setTextColor(ContextCompat.getColor(getContext() , R.color.light_white));

        }else{
           llLayout.setBackgroundResource(R.color.white);
            editMobile.setTextColor(ContextCompat.getColor(getContext() , R.color.black));
            editEmail.setTextColor(ContextCompat.getColor(getContext() , R.color.black));
            editFullName.setTextColor(ContextCompat.getColor(getContext() , R.color.black));
            editDisplayName.setTextColor(ContextCompat.getColor(getContext() , R.color.black));
            tvCreatedAccount.setTextColor(ContextCompat.getColor(getContext() , R.color.black));
            tvByCreated.setTextColor(ContextCompat.getColor(getContext() , R.color.black));
            togetStarted.setTextColor(ContextCompat.getColor(getContext() , R.color.black));
            tvNewUser.setTextColor(ContextCompat.getColor(getContext() , R.color.black));
        }

        tilMobileNumber.setBoxStrokeColor(ContextCompat.getColor(getContext() , R.color.text_input_color));
        tilFullName.setBoxStrokeColor(ContextCompat.getColor(getContext() , R.color.text_input_color));
        tilEmail.setBoxStrokeColor(ContextCompat.getColor(getContext() , R.color.text_input_color));
        tilDisplayName.setBoxStrokeColor(ContextCompat.getColor(getContext() , R.color.text_input_color));


        checkDublicate();
    }

    private void checkDublicate() {
        editMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvMessageForDublicate.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String mobileNumber = charSequence.toString();
                Log.e("toatl lenght=====> " ,String.valueOf( mobileNumber.length()));
                if(mobileNumber.length() > 9){
                    dublicateCheck(mobileNumber ,1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editDisplayName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvMessageForDublicateName.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name = charSequence.toString();
                Log.e("toatl lenght=====> " ,String.valueOf( name.length()));
                if(name.length() > 5){
                    dublicateCheck(name , 2);
                }else{
                    tvMessageForDublicateName.setVisibility(View.VISIBLE);
                    tvMessageForDublicateName.setText("Enter at least 6 characters, special characters are not allowed");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvMessageForDublicateEmail.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String email = charSequence.toString();
                Log.e("toatl lenght=====> " ,String.valueOf( email.length()));
                Utility utility = new Utility();
                if(utility.isValidEmail(email)){
                    dublicateCheck(email ,3);
                }



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void dublicateCheck(String mobileNumber , int viewStatus) {
            APIClient apiClient = new APIClient();
            APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
            Call<CheckDublicateModel> call = apiInterface.checkDublicate(mobileNumber);

            call.enqueue(new Callback<CheckDublicateModel>() {
                @Override
                public void onResponse(Call<CheckDublicateModel> call, Response<CheckDublicateModel> response) {
                    Log.e("Doblicate Data =====> " ,"okokokok");
                    if(response.isSuccessful()){
                        if(response.body().flag == 1){
                            Log.e("Doblicate Data =====> " ,"okokokok");
                            switch (viewStatus){
                                case 1:{
                                    tvMessageForDublicate.setVisibility(View.VISIBLE);
                                    break;
                                }
                                case 2:{
                                    tvMessageForDublicateName.setText("Already Taken, try other display name.!");
                                    tvMessageForDublicateName.setVisibility(View.VISIBLE);
                                    break;
                                }
                                case 3:{
                                    tvMessageForDublicateEmail.setVisibility(View.VISIBLE);
                                    break;
                                }
                            }

                        }else{

                        }
                    }
                }

                @Override
                public void onFailure(Call<CheckDublicateModel> call, Throwable t) {

                }
            });
    }

    @OnClick(R.id.ivBack)
    public void onClickBack(){
        Log.e("click====> " , "okokokokokko");
        //Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStackImmediate();
       getActivity().finish();
    }

    @Override
    public void onClick(View view) {
        Fragment fragment;
        switch (view.getId()) {
            case R.id.tvLoginPage:
                fragment = new FragmentLogin();
                loginFragment(fragment);
                break;
            case R.id.btnRegister:

                if(TextUtils.isEmpty(Objects.requireNonNull(editMobile.getText()).toString().trim())){
                    editMobile.setError(getString(R.string.required_field));
                    editMobile.requestFocus();
                    return;
                }
                if(editMobile.getText().toString().length() != 10){
                    editMobile.setError(getString(R.string.mobile_input));
                    editMobile.requestFocus();
                    return;
                }

                Utility utility = new Utility();
                if(!utility.isValidEmail(Objects.requireNonNull(editEmail.getText()).toString())){
                    editEmail.setError(getString(R.string.required_fieldemail));
                    editEmail.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Objects.requireNonNull(editFullName.getText()).toString().trim())){
                    editFullName.setError(getString(R.string.required_field));
                    editFullName.requestFocus();
                    return;
                }


                if(TextUtils.isEmpty(Objects.requireNonNull(editDisplayName.getText()).toString().trim())){
                    editDisplayName.setError(getString(R.string.required_field));
                    editDisplayName.requestFocus();
                    return;
                }


                postRegisterValue(editDisplayName.getText().toString() , editFullName.getText().toString()
                        ,editEmail.getText().toString(),
                        editMobile.getText().toString());

                break;

        }
    }

    private void postRegisterValue(String displayName, String fullName, String email, String mobile) {

        Log.e("check value first=> " , mobile);
        this.mobile = mobile;

        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Please Wait...!");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        Map<String  , String> params = new HashMap<>();
        params.put("displayName" , displayName);
        params.put("fullName", fullName);
        params.put("email" , email);
        params.put("fromSource" , "accept");
        params.put("phone",mobile);

        APIClient apiClient = new APIClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<RegisterBaseResponse> call = apiInterface.postRegisterData(params);
        call.enqueue(new Callback<RegisterBaseResponse>() {
            @Override
            public void onResponse(@NotNull Call<RegisterBaseResponse> call, @NotNull Response<RegisterBaseResponse> response) {
                if(pd.isShowing()){
                    pd.dismiss();
                }
                assert response.body() != null;
                if(response.body().getStatus() == 1){
                   int userId = response.body().getData().getId();
                    getOtpNumber(userId , mobile , email , displayName);
                }else if(response.body().getStatus() == 2){
                    MyUtility myUtility = new MyUtility();
                    myUtility.addCustomToast(response.body().getMessage() , Objects.requireNonNull(getActivity()));
                    //Toast.makeText(getActivity() , ""+response.body().getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(@NotNull Call<RegisterBaseResponse> call, @NotNull Throwable t) {
                if(pd.isShowing()){
                    pd.dismiss();
                }
            }
        });


    }

    private void getOtpNumber(int userId, String mobile, String email, String displayName) {
        ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Please Wait..!");
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.show();
        Map<String , String> params = new HashMap<>();
        params.put("phone", mobile);
        params.put("student_id",String.valueOf(userId));
        params.put("email",email);
        params.put("displayName",displayName);
        APIClient apiClient = new APIClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<RegisterBaseResponse> call = apiInterface.getOtpNumber(params);
        call.enqueue(new Callback<RegisterBaseResponse>() {
            @Override
            public void onResponse(@NotNull Call<RegisterBaseResponse> call, Response<RegisterBaseResponse> response) {
                if(pd.isShowing()){
                    pd.dismiss();
                }
                assert response.body() != null;
                if(response.body().getStatus() == 1){
                    Log.e("id====> " , String.valueOf(response.body().getData().getId()));
                    int userId = response.body().getData().getId();
                    Fragment fragment = new OptFragment();
                    replaceFragment(fragment ,userId);
                }else if(response.body().getStatus() == 2){
                    MyUtility myUtility = new MyUtility();
                    myUtility.addCustomToast(response.body().getMessage() , Objects.requireNonNull(getActivity()));
                    //Toast.makeText(getActivity() , ""+response.body().getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(@NotNull Call<RegisterBaseResponse> call, @NotNull Throwable t) {
                if(pd.isShowing()){
                    pd.dismiss();
                }
            }
        });
    }

   private void replaceFragment(Fragment fragment, int userId) {
        if (fragment != null) {
            Bundle bundle = new Bundle();
            bundle.putInt(BaseUrl.USER_ID,userId);
            bundle.putString(BaseUrl.MOBILE,mobile);
            fragment.setArguments(bundle);
            FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.login_framelayout, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

    private void loginFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            ft.add(R.id.login_framelayout, fragment);
            ft.addToBackStack(null);
            ft.commit();

        }
    }

}
