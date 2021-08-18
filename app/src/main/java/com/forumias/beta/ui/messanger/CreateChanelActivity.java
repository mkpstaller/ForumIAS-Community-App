package com.forumias.beta.ui.messanger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.forumias.beta.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateChanelActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.editChanelName)
    AppCompatEditText editChanelName;
    @BindView(R.id.editChanelDescription)
    AppCompatEditText editChanelDescription;
    @BindView(R.id.fabChanelSubmit)
    FloatingActionButton fabChanelSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chanel);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }

    @OnClick(R.id.fabChanelSubmit)
    public void onClickChanelSubmit(){
        if(TextUtils.isEmpty(editChanelName.getText().toString())){
            editChanelName.setError(getString(R.string.required_field));
            editChanelName.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(editChanelDescription.getText().toString())){
            editChanelDescription.setError(getString(R.string.required_field));
            editChanelDescription.requestFocus();
            return;
        }
    }
}
