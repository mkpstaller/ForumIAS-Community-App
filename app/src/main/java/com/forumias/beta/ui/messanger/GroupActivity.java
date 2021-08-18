package com.forumias.beta.ui.messanger;

import android.content.Intent;
import android.os.Bundle;

import com.forumias.beta.adapter.GroupSelectedAdapted;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.myinterface.SelectUserInterface;
import com.forumias.beta.pojo.UserPojo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.forumias.beta.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupActivity extends AppCompatActivity implements SelectUserInterface {
    List<UserPojo> userChatList = new ArrayList<>();
    List<UserPojo> groupList;
    @BindView(R.id.mainRecyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.groupSelectedRecyclerView)
    RecyclerView groupSelectedRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        groupList = new ArrayList<>();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                Type type = new TypeToken<List<UserPojo>>() {}.getType();
                String json = gson.toJson(groupList, type);
                Intent intent = new Intent(GroupActivity.this , FinalGroupActivity.class);
                intent.putExtra(BaseUrl.GROUP_LIST ,json);
                startActivity(intent);
            }
        });
        viewUser();
    }


    private void viewUser() {

        UserPojo userPojo = new UserPojo("Musafir Prajapti", true);
        userChatList.add(userPojo);
        UserPojo userPojo1 = new UserPojo("Ravi Sharma", true);
        userChatList.add(userPojo1);
        UserPojo userPojo2 = new UserPojo("Manish Yadav", true);
        userChatList.add(userPojo2);
        UserPojo userPojo3 = new UserPojo("Sumesh Singh", true);
        userChatList.add(userPojo3);
        UserPojo userPojo4 = new UserPojo("Ramesh Yadav", true);
        userChatList.add(userPojo4);
        UserPojo userPojo5 = new UserPojo("Remika Coco", true);
        userChatList.add(userPojo5);
        UserPojo userPojo6 = new UserPojo("Monika Sharma", true);
        userChatList.add(userPojo6);

    }

    @Override
    public void selectedUser(String name , int status) {
        if(status == 1) {
            if (name != null) {
                groupSelectedRecyclerView.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            } else {
                fab.setVisibility(View.GONE);
                groupSelectedRecyclerView.setVisibility(View.GONE);
            }
            groupList.add(new UserPojo(name, true));
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            groupSelectedRecyclerView.setLayoutManager(layoutManager);
            ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
            GroupSelectedAdapted groupSelectedAdapted = new GroupSelectedAdapted(this, groupList, this);
            groupSelectedRecyclerView.setAdapter(groupSelectedAdapted);
        }else{
            if(groupList.size() == 1){
                fab.setVisibility(View.GONE);
                groupSelectedRecyclerView.setVisibility(View.GONE);
            }else{
                groupSelectedRecyclerView.setVisibility(View.VISIBLE);
            }
            userChatList.add(new UserPojo(name, true));
            //setUserAdapter(userChatList);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView(searchView);

        return true;
    }

    private void searchView(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}
