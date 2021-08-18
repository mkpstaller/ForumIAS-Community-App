package com.chinalwb.are.api;



import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {
    @Multipart
    @POST("jack_up.php")
    Call<ResponseBody> postRegisterData(@Part MultipartBody.Part file);
    //fun imageUrlGet(@Part file : MultipartBody.Part):Call<UploadImageBaseResponse>

    @Multipart
    @POST("jack_up.php?act_type=post")
    Call<String> postImageFile(@Part MultipartBody.Part file);
}
