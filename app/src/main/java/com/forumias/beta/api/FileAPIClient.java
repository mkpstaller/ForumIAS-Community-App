package com.forumias.beta.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FileAPIClient {

    public Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

  /*    List<Interceptor> data = client().interceptors();
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i) instanceof LoggingInterceptor) {
                    LoggingInterceptor intercept = (LoggingInterceptor) data.get(i);
                    String url = intercept.getRequestUrl();
                    // todo : do what's ever you want with url
                    break;
                } else {
                    continue;
                }
            }
        }
            */


        return new Retrofit.Builder()
                .baseUrl("https://forumias.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

}
