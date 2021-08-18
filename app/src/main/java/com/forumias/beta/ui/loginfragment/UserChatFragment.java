package com.forumias.beta.ui.loginfragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.UserPojo;
import com.forumias.beta.ui.messanger.GroupActivity;
import com.forumias.beta.R;
import com.forumias.beta.ui.messanger.CreateChanelActivity;
import com.forumias.beta.ui.login.LoginActivity;
import com.forumias.beta.ui.deta.forumias.profile.UserProfileActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserChatFragment extends Fragment {
    List<UserPojo> userChatList= new ArrayList<>();
    @BindView(R.id.userChatRecyclerView)
    RecyclerView userChatRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_chat, container, false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        viewUser();
        return view;
    }

    private void viewUser() {

        UserPojo userPojo = new UserPojo("Musafir Prajapti", true);
        userChatList.add(userPojo);
        UserPojo userPojo1 = new UserPojo("Ravi Sharma", true);
        userChatList.add(userPojo1);
        UserPojo userPojo2 = new UserPojo("Manish Yadav", true);
        userChatList.add(userPojo2);

       /* RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        userChatRecyclerView.setLayoutManager(layoutManager);
        ChatUserAdapter chatUserAdapter = new ChatUserAdapter(getContext(),userChatList);
        userChatRecyclerView.setAdapter(chatUserAdapter);*/

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.chat_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        search(searchView);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_group:
                Intent newGroup = new Intent(getContext(), GroupActivity.class);
                startActivity(newGroup);
                break;
            case R.id.menu_chanel:
                Intent newChanel = new Intent(getContext(), CreateChanelActivity.class);
                startActivity(newChanel);
                break;
            case R.id.menu_account:
                Intent profile = new Intent(getContext() , UserProfileActivity.class);
                startActivity(profile);
                break;
            case R.id.menu_logout:
                Log.e("okoko==> " , "==============okokokok============");
                MyPreferenceData myPreferenceData = new MyPreferenceData(getContext());
                if (myPreferenceData.clearData()) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }

                break;
        }

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
