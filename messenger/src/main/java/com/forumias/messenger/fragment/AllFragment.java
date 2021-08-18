package com.forumias.messenger.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forumias.messenger.R;
import com.forumias.messenger.adapter.ChatUserAdapter;
import com.forumias.messenger.adapter.UserPojo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);
        ButterKnife.bind(this , view);
        viewUser();
        return view;
    }
    private void viewUser() {

        List<UserPojo> userChatList = new ArrayList<>();
        UserPojo userPojo = new UserPojo("Musafir Prajapti", true);
        userChatList.add(userPojo);
        UserPojo userPojo1 = new UserPojo("Ravi Sharma", true);
        userChatList.add(userPojo1);
        UserPojo userPojo2 = new UserPojo("Manish Yadav", true);
        userChatList.add(userPojo2);

        ChatUserAdapter chatUserAdapter = new ChatUserAdapter(getContext(),userChatList);
        recyclerView.setAdapter(chatUserAdapter);

    }
}
