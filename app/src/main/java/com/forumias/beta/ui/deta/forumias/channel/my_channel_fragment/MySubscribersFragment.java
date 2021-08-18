package com.forumias.beta.ui.deta.forumias.channel.my_channel_fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forumias.beta.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MySubscribersFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_subscribers, container, false);
        return view;
    }

}
