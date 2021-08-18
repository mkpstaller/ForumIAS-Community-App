package com.forumias.beta.ui.loginfragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.R;
import com.forumias.beta.ui.login.LoginActivity;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginAlertFragment extends DialogFragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_AppCompat_Dialog_MinWidth);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_alert, container, false);
        ButterKnife.bind(this , view);

        return view;
    }


    @OnClick(R.id.tvLogin)
    public void onClickLogin(){
        Intent intent = new Intent(getContext() , LoginActivity.class);
        intent.putExtra(BaseUrl.STATUS,1);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();
        dismiss();
    }

    @OnClick(R.id.tvSignup)
    public void onClickSignUp(){
        Intent intent = new Intent(getContext() , LoginActivity.class);
        intent.putExtra(BaseUrl.STATUS,2);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();
        dismiss();
    }

    @OnClick(R.id.ivClose)
    public  void  onClickClose(){
        dismiss();
    }
}
