package com.forumias.beta.ui.deta.forumias.group;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.pojo.DefaultPojo;
import com.forumias.beta.ui.deta.forumias.group.group_adapter.GroupMemberAdapter;
import com.forumias.beta.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupMemberFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_member, container, false);
        ButterKnife.bind(this , view);
        initView();

        return view;
    }

    private void initView() {

        List<DefaultPojo> list = new ArrayList<>();
        DefaultPojo defaultPojo1 = new DefaultPojo("Musafir prajapati");
        list.add(defaultPojo1);
        DefaultPojo defaultPojo2 = new DefaultPojo("Musafir prajapati");
        list.add(defaultPojo2);
        DefaultPojo defaultPojo3 = new DefaultPojo("Musafir prajapati");
        list.add(defaultPojo3);
        DefaultPojo defaultPojo4 = new DefaultPojo("Musafir prajapati");
        list.add(defaultPojo4);
        DefaultPojo defaultPojo5 = new DefaultPojo("Musafir prajapati");
        list.add(defaultPojo5);
        DefaultPojo defaultPojo6 = new DefaultPojo("Musafir prajapati");
        list.add(defaultPojo6);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            position = bundle.getInt(BaseUrl.TAB_POSITION,0);
        }

        GroupMemberAdapter mySpaceAdapter = new GroupMemberAdapter(list,getContext() , position);
        recyclerView.setAdapter(mySpaceAdapter);
    }

}
