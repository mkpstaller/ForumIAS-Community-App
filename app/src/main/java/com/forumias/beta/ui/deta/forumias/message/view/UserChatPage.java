package com.forumias.beta.ui.deta.forumias.message.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.ui.deta.forumias.message.adapter.ChatListAdapter;
import com.forumias.beta.ui.deta.forumias.message.model.ChatListModel;
import com.forumias.beta.ui.deta.forumias.message.model.ChatModel;
import com.forumias.beta.utility.Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserChatPage extends AppCompatActivity {
    @BindView(R.id.recyclerViewChat)
    RecyclerView recyclerView;
    @BindView(R.id.rlChatMessage)
    RelativeLayout rlChatMessage;
    @BindView(R.id.editMessage)
    AppCompatEditText editMessage;
    @BindView(R.id.ivChatUserImage)
    ImageView ivChatUserImage;
    @BindView(R.id.ivChatUserVerify)
    AppCompatImageView ivChatUserVerify;
    @BindView(R.id.tvChatUserName)
    AppCompatTextView tvChatUserName;
    @BindView(R.id.ivBack)
    AppCompatImageView ivBack;

    @BindView(R.id.ivRefresh)
    AppCompatImageView ivRefresh;


    private int userId , chatId , toUser , chatUserId;
    Thread th;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat_page);
        ButterKnife.bind(this);


        MyPreferenceData sp = new MyPreferenceData(this);
        userId = sp.getIntegerData(BaseUrl.USER_ID);

        Bundle bundle = getIntent().getExtras();
        chatId = bundle.getInt(BaseUrl.CHAT_ID , 0);
        chatUserId = bundle.getInt(BaseUrl.CHAT_USER_ID , 0);

        Log.e("chatUserId======> ",chatUserId+"");

        getChatList();

        ivBack.setOnClickListener(view -> {
            finish();
        });
        rlChatMessage.setOnClickListener(view -> {

            if(editMessage.getText().toString().isEmpty()){
                editMessage.setError("Type a message...!");
                editMessage.requestFocus();
            }else{
                chatPostMethod();
            }
        });

        ivRefresh.setOnClickListener(view -> {
            getChatList();
        });

     /*   th = new Thread(){
            public void run(){
                while (!isInterrupted()){
                    try {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("refresh in ====> ","5 second");
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        th.start();*/

    }


    private void chatPostMethod() {
        Map<String , String> data = new HashMap<>();
        data.put("from_user" , String.valueOf(userId));
        data.put("to_user" , String.valueOf(toUser));
        data.put("chat_id" , String.valueOf(chatId));
        data.put("my_msg" , editMessage.getText().toString());
        BetaApiClient betaApiClient = new  BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<ChatModel> call = apiInterface.postMessage(data);
        call.enqueue(new Callback<ChatModel>() {
            @Override
            public void onResponse(Call<ChatModel> call, Response<ChatModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        editMessage.getText().clear();
                        getChatList();
                    }
                }
            }

            @Override
            public void onFailure(Call<ChatModel> call, Throwable t) {

            }
        });
    }


    private void getChatList() {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<ChatListModel> call = apiInterface.getUserChat(userId , chatUserId);
        call.enqueue(new Callback<ChatListModel>() {
            @Override
            public void onResponse(Call<ChatListModel> call, Response<ChatListModel> response) {
                if(response.isSuccessful()){
                    Log.e("data ====> ",response.body().getMessage());
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        Log.e("data ====> ",response.body().getMessage());
                        toUser = response.body().to_user_detail.id;
                        setChatUserData(response.body().to_user_detail);
                        if(response.body().getChat_histoty().size() > 0){
                            List<ChatListModel.chatHistory> chatList = new ArrayList<>(response.body().getChat_histoty());
                            Collections.reverse(chatList);
                            getChatData(chatList , response.body().to_user_detail.name , response.body().to_user_detail.image);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<ChatListModel> call, Throwable t) {

            }
        });

    }

    private void setChatUserData(ChatListModel.ToUserDetails to_user_detail) {
        tvChatUserName.setText(to_user_detail.name);
        Glide.with(this).load(to_user_detail.image).into(ivChatUserImage);
        new Utility().showUserVerified(to_user_detail.is_verified , ivChatUserVerify);
    }

    private void getChatData(List<ChatListModel.chatHistory> chatHistoty , String name , String image) {

        ChatListAdapter chatListAdapter = new ChatListAdapter(this , chatHistoty , name , image);
        recyclerView.setAdapter(chatListAdapter);
    }
}