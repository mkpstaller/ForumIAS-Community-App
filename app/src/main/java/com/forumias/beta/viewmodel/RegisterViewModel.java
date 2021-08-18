package com.forumias.beta.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.forumias.beta.api.APIClient;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.pojo.RegisterBaseResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<RegisterBaseResponse> userRegister;

    public LiveData<RegisterBaseResponse> getRegisterUser(String displayName,
                                                                String fullName,
                                                                String email,
                                                                String mobile){
        if(userRegister == null){
            userRegister = new MutableLiveData<>();
            PostRegisterData(displayName , fullName , email , mobile);
        }
        return userRegister;
    }

    public void PostRegisterData(String displayName, String fullName, String email, String mobile){
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
            public void onResponse(Call<RegisterBaseResponse> call, Response<RegisterBaseResponse> response) {

                //finally we are setting the list to our MutableLiveData
                Log.e("data==> ", String.valueOf(response.body()));
                userRegister.setValue(response.body());

            }

            @Override
            public void onFailure(Call<RegisterBaseResponse> call, Throwable t) {

            }
        });

    }
}
