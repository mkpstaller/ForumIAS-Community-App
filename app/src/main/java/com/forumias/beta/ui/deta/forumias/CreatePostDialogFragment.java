package com.forumias.beta.ui.deta.forumias;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forumias.beta.adapter.ColorAdapter;
import com.forumias.beta.pojo.DefaultPojo;
import com.forumias.beta.R;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePostDialogFragment extends DialogFragment {
    public static String TAG = "FullScreenDialog";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.createPostDialog);
    }

    @BindView(R.id.cbAddBackCover)
    MaterialCheckBox cbAddBackCover;
    @BindView(R.id.llBackCover)
    LinearLayoutCompat llBackCover;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ivClose)
    AppCompatImageView ivClose;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_post_dialog, container, false);
        ButterKnife.bind(this , view);


        cbAddBackCover.setOnCheckedChangeListener((compoundButton, b) -> {
            if(b){
                llBackCover.setVisibility(View.VISIBLE);
            }else{
                llBackCover.setVisibility(View.GONE);
            }
        });

        colorBox();
        return view;
    }

    @OnClick(R.id.ivClose)
    public void onClickClose(){
        dismiss();
    }
    private void colorBox(){
        List<DefaultPojo> colorList = new ArrayList<>();
        DefaultPojo model1 = new DefaultPojo(R.color.c1_green);
        DefaultPojo model2 = new DefaultPojo(R.color.c2_blue);
        DefaultPojo model3 = new DefaultPojo(R.color.c3_yellow);
        DefaultPojo model4 = new DefaultPojo(R.color.c4_red);
        DefaultPojo model5 = new DefaultPojo(R.color.c5_dark_yellow);
        DefaultPojo model6 = new DefaultPojo(R.color.c6_parpal);
        DefaultPojo model7 = new DefaultPojo(R.color.c7_normal_black);
        colorList.add(model1);
        colorList.add(model2);
        colorList.add(model3);
        colorList.add(model4);
        colorList.add(model5);
        colorList.add(model6);
        colorList.add(model7);

        ColorAdapter colorAdapter = new ColorAdapter(colorList , getContext());
        recyclerView.setAdapter(colorAdapter);

    }
}
