package com.forumias.beta.ui.deta.forumias.group;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.forumias.beta.pojo.DefaultPojo;
import com.forumias.beta.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupsDetailsActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_details);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
      //  initView();
    }

    @OnClick(R.id.ivBackClose)
    public void onClickBack(){
        finish();
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


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
       // MyLatestAnnouncementPostAdapter mySpaceAdapter = new MyLatestAnnouncementPostAdapter(list,this);
        //recyclerView.setAdapter(mySpaceAdapter);
    }
}
