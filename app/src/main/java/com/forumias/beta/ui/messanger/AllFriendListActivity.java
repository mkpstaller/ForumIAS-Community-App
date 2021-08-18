package com.forumias.beta.ui.messanger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.forumias.beta.myinterface.SelectUserInterface;
import com.forumias.beta.pojo.UserPojo;
import com.forumias.beta.utility.PermissionUtility;
import com.forumias.beta.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AllFriendListActivity extends AppCompatActivity implements SelectUserInterface {
    List<UserPojo> userChatList = new ArrayList<>();
    @BindView(R.id.userChatRecyclerView)
    RecyclerView userChatRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ivAttachment)
    AppCompatImageView ivAttachment;
    @BindView(R.id.tvGroup)
    AppCompatTextView tvGroup;
    @BindView(R.id.tvChanel)
    AppCompatTextView tvChanel;
    @BindView(R.id.ivSearch)
    SearchView ivSearch;
    
    List<UserPojo> groupList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_friend);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ivAttachment.setVisibility(View.GONE);
        groupList = new ArrayList<>();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });


        viewUser();
    }

    
    @OnClick(R.id.tvGroup)
    public void onClickGroup(){
        Intent intent = new Intent(this , GroupActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tvChanel)
    public void onClickChanel(View view){
        Intent intent = new Intent(this , CreateChanelActivity.class);
        startActivity(intent);
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


     // setUserAdapter(userChatList);

    }

   /* private void setUserAdapter(List<UserPojo> userChatList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        userChatRecyclerView.setLayoutManager(layoutManager);
        SelectUserAdapter chatUserAdapter = new SelectUserAdapter(this, userChatList, this);
        userChatRecyclerView.setAdapter(chatUserAdapter);
    }
*/


    void showPopupWindow(View view) {
        PopupMenu popup = new PopupMenu(AllFriendListActivity.this, view);
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.getMenuInflater().inflate(R.menu.chanel_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                PermissionUtility permissionUtility = new PermissionUtility();
                switch (item.getItemId()){
                    case R.id.menu_public:
                        break;
                    case R.id.menu_private:
                        break;
                    case R.id.menu_subscribed:
                        break;
                    case R.id.menu_myChanel:
                        break;
                }
                return true;
            }
        });
        popup.show();
    }




    @Override
    public void selectedUser(String name , int status) {
       /* if(status == 1) {
            if (name != null) {
                groupSelectedRecyclerView.setVisibility(View.VISIBLE);
            } else {
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
                groupSelectedRecyclerView.setVisibility(View.GONE);
            }else{
                groupSelectedRecyclerView.setVisibility(View.VISIBLE);
            }
            userChatList.add(new UserPojo(name, true));
            setUserAdapter(userChatList);
        }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        search(searchView);
        return true;
    }

    private void search(SearchView searchView) {
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

