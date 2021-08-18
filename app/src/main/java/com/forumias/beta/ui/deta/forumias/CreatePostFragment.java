package com.forumias.beta.ui.deta.forumias;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.forumias.beta.R;
import com.google.android.material.checkbox.MaterialCheckBox;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePostFragment extends DialogFragment {
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

        return view;
    }


}
