package com.forumias.beta.ui.deta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.forumias.beta.adapter.SearchPostAdapter;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.SearchModel;
import com.forumias.beta.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPostActivity extends Fragment {

    @BindView(R.id.searchIconAnim)
    AppCompatImageView searchIconAnim;
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.rvPostSearch)
    RecyclerView rvPostSearch;
    @BindView(R.id.llSearchLayout)
    LinearLayoutCompat llSearchLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_post , container ,false);
        ButterKnife.bind(this , view);


        MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(getContext()));
        int darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
        if(darkModeStatus == 1){
            searchView.setBackgroundResource(R.drawable.new_search_edit_shape);
           // searchView.setHintTextColor(ContextCompat.getColor(getContext(),R.color.light_white));
            //searchView.setTextColor(ContextCompat.getColor(getContext(),R.color.light_white));
            llSearchLayout.setBackgroundResource(R.color.darkmode_back_color);
        }else{
            searchView.setBackgroundResource(R.drawable.new_search_edit_shape);
            llSearchLayout.setBackgroundResource(R.color.back_color);
          //  editSearch.setHintTextColor(ContextCompat.getColor(getContext(),R.color.gray_dark));
           // editSearch.setTextColor(ContextCompat.getColor(getContext(),R.color.gray_dark));
        }

        initView();
        return view;
    }

    private void initView() {
        Glide.with(this).load(R.drawable.search).into(searchIconAnim);

        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.length() > 1){
                    fetchSearchData(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() > 1){
                    fetchSearchData(newText);
                }
                return false;
            }
        });

    }

    private void fetchSearchData(CharSequence charSequence) {

        BetaApiClient apiClient = new BetaApiClient();
        APIInterface apiInterface = apiClient.getClient().create(APIInterface.class);
        Call<SearchModel> call = apiInterface.fetchSearchPost(charSequence.toString());
        call.enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(@NotNull Call<SearchModel> call, @NotNull Response<SearchModel> response) {
                assert response.body() != null;
                if(response.body().getStatus().equalsIgnoreCase(BaseUrl.SUCCESS)){
                    if(response.body().getPosts() != null) {
                        if (response.body().getPosts().size() > 0) {
                            addView(response.body().getPosts() , charSequence.toString());
                            rvPostSearch.setVisibility(View.VISIBLE);
                            searchIconAnim.setVisibility(View.GONE);
                        } else {
                            rvPostSearch.setVisibility(View.GONE);
                            searchIconAnim.setVisibility(View.VISIBLE);
                        }
                    }
                }else{
                    Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(@NotNull Call<SearchModel> call, @NotNull Throwable t) {

            }
        });

    }

    private void addView(List<SearchModel.SearchPost> posts, String searchString) {
        SearchPostAdapter adapter = new SearchPostAdapter(posts , getContext() , searchString);
        rvPostSearch.setAdapter(adapter);
    }
}
