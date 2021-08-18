package com.forumias.beta.ui.messanger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.forumias.beta.adapter.GroupSelectedAdapted;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.myinterface.SelectUserInterface;
import com.forumias.beta.pojo.UserPojo;
import com.forumias.beta.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinalGroupActivity extends AppCompatActivity implements SelectUserInterface {

    List<UserPojo> selectList;
    @BindView(R.id.mainRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_group);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        selectList = new ArrayList<>();
        Intent intent = getIntent();
        Gson gson = new Gson();
        String friendJson = intent.getStringExtra(BaseUrl.GROUP_LIST);
        if(friendJson != null) {
            Type type = new TypeToken<List<UserPojo>>() {}.getType();
            selectList = gson.fromJson(friendJson, type);
            Log.d("Location Count", Integer.toString(selectList.size()));
        }
        else{
            Log.d("Location Count","failed");
        }

        setUserAdapter(selectList);
    }

    private void setUserAdapter(List<UserPojo> userChatList) {
        if(userChatList.size() > 0) {

            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,4);
            recyclerView.setLayoutManager(layoutManager);
            GroupSelectedAdapted chatUserAdapter = new GroupSelectedAdapted(this, userChatList , this);
            recyclerView.setAdapter(chatUserAdapter);
        }
    }


    @Override
    public void selectedUser(String name, int status) {

    }

}

