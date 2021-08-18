package com.forumias.beta.ui.deta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import com.forumias.beta.adapter.ReadingListAdapter;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.myinterface.TagInterface;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.ReadingModel;
import com.forumias.beta.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadingListActivity extends AppCompatActivity implements TagInterface {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvReadMenu)
    AppCompatImageView tvReadMenu;
    @BindView(R.id.chBoxSelectAll)
    MaterialCheckBox chBoxSelectAll;
    @BindView(R.id.llReadingListSection)
    LinearLayoutCompat llReadingListSection;
    @BindView(R.id.tvTitle)
    AppCompatTextView tvTitle;
    @BindView(R.id.rlTrashSection)
    RelativeLayout rlTrashSection;
    @BindView(R.id.tvTrash)
    AppCompatTextView tvTrash;
    @BindView(R.id.ivBack)
    AppCompatImageView ivBack;
    @BindView(R.id.llToolbarSection)
    LinearLayoutCompat llToolbarSection;
    @BindView(R.id.ivAppLogo)
    AppCompatImageView ivAppLogo;

    private List<ReadingModel.ReadingList> readingMarkLists;
    private boolean checkStatus = true;
    private int loginUserId, darkModeStatus;
    ReadingListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_list); // auth id 52066
        ButterKnife.bind(this);
        readingMarkLists = new ArrayList<>();

        MyPreferenceData myPreferenceData = new MyPreferenceData(this);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);

        setDarkModeView();
        getReadingList();
    }

    private void setDarkModeView() {
        if(darkModeStatus != 0){
            llReadingListSection.setBackgroundResource(R.color.darkmode_back_color);
            rlTrashSection.setBackgroundResource(R.drawable.dark_post_border);
            tvTitle.setTextColor(ContextCompat.getColor(this,R.color.light_white));
            tvTrash.setTextColor(ContextCompat.getColor(this,R.color.light_white));
            chBoxSelectAll.setTextColor(ContextCompat.getColor(this,R.color.light_white));
            llToolbarSection.setBackgroundColor(ContextCompat.getColor(this,R.color.darkmode_back_color));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back_key_white));
            ivAppLogo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo_white));
        }else{
            llReadingListSection.setBackgroundResource(R.color.back_color);
            rlTrashSection.setBackgroundResource(R.color.white);
            tvTitle.setTextColor(ContextCompat.getColor(this,R.color.black_light));
            tvTrash.setTextColor(ContextCompat.getColor(this,R.color.black_light));
            chBoxSelectAll.setTextColor(ContextCompat.getColor(this,R.color.black_light));
            llToolbarSection.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
            ivBack.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_back_key));
            ivAppLogo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.logo_black));
        }
    }

    @OnClick(R.id.ivBack)
    public void OnClickBack(){
        finish();
    }

    @OnClick(R.id.tvTrash)
    public void onClickTrash(){
        readingMarkLists.clear();
        setReadingList(readingMarkLists , false,false);
    }

    @OnClick(R.id.chBoxSelectAll)
    public void onClickCheckSelectAll(){
        if(checkStatus) {
            setReadingList(readingMarkLists, true,false);
            checkStatus = false;
        }else{
            setReadingList(readingMarkLists, false,false);
            checkStatus = true;
        }
    }
    @OnClick(R.id.tvReadMenu)
    public void onClickReadMenu(){
        PopupMenu popupMenu = new PopupMenu(this , tvReadMenu);
        popupMenu.getMenuInflater().inflate(R.menu.reading_menu ,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.menu_read:
                    setReadingList(readingMarkLists ,false , true);
                    break;
                case R.id.menu_unread:
                    setReadingList(readingMarkLists ,false , false);
                    break;
            }
            return true;

        });
        popupMenu.show();
    }

    private void getReadingList(){
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<ReadingModel> call = apiInterface.fetchReadingList(loginUserId,1);
        call.enqueue(new Callback<ReadingModel>() {
            @Override
            public void onResponse(@NotNull Call<ReadingModel> call, @NotNull Response<ReadingModel> response) {
                assert response.body() != null;
                if(response.body().getStatus().equalsIgnoreCase(BaseUrl.SUCCESS)){
                    if(response.body().getReadingListData().getReadingLists().size() > 0){
                        readingMarkLists.addAll(response.body().getReadingListData().getReadingLists());
                        setReadingList(readingMarkLists , false,false);
                    }
                }
            }
            @Override
            public void onFailure(@NotNull Call<ReadingModel> call, @NotNull Throwable t) {

            }
        });
    }

    private void setReadingList(List<ReadingModel.ReadingList> readingLists ,boolean isSelected , boolean readStatus) {
        adapter = new ReadingListAdapter(readingLists , this,isSelected ,readStatus , this);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public void addMyTag(int position) {
        Dialog dialog = new Dialog(this);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.add_tag_layout);
        AppCompatImageView ivClose = dialog.findViewById(R.id.ivClose);
        TextInputEditText editTag = dialog.findViewById(R.id.editTag);
        MaterialButton btnAddTag = dialog.findViewById(R.id.btnAddTag);
        btnAddTag.setOnClickListener(view ->{
            if(TextUtils.isEmpty(editTag.getText().toString().trim())){
                editTag.setError(getString(R.string.required_field));
                editTag.requestFocus();
                return;
            }
        });
        ivClose.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    @Override
    public void deleteReading(int loginUserId, int postId, int actionType, int position , List<ReadingModel.ReadingList> readingLists) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<ResponseBody> call = apiInterface.readingListDelete(loginUserId , actionType ,postId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                   // getReadingList();
                    readingLists.remove(position);
                    adapter.notifyDataSetChanged();
                   // readingLists.notify();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
