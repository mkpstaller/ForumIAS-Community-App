package com.forumias.beta.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.pojo.SearchModel;
import com.forumias.beta.R;
import com.forumias.beta.ui.deta.forumias.comment.ui.CommentOnPostDetailsActivity;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.UserProfileAndPostActivity;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class SearchPostAdapter extends RecyclerView.Adapter<SearchPostAdapter.ViewHolder> {

    private List<SearchModel.SearchPost> searchList;
    private Context context;
    private String searchString;
    public SearchPostAdapter(List<SearchModel.SearchPost> searchList, Context context, String searchString){
        this.context = context;
        this.searchList = searchList;
        this.searchString = searchString;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_search_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(context));
        int darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);

        if(darkModeStatus==1){
            holder.tvSearch.setTextColor(ContextCompat.getColor(context , R.color.light_white));
            holder.llSearchList.setBackgroundResource(R.drawable.dark_post_border);
        }else{
            holder.tvSearch.setTextColor(ContextCompat.getColor(context , R.color.black));
            holder.llSearchList.setBackgroundResource(R.color.white);
        }

        if(searchList.get(position).getName().equals("")) {
            holder.tvSearch.setVisibility(View.VISIBLE);
            holder.llUserSection.setVisibility(View.GONE);
            holder.tvSearch.setText(searchList.get(position).getTitle());
            Log.e("data=11==> " , searchList.get(position).getName());
        }else{
            Log.e("data===> " , searchList.get(position).getName());
            holder.tvSearch.setVisibility(View.GONE);
            holder.llUserSection.setVisibility(View.VISIBLE);
            Glide.with(context).load(searchList.get(position).getImage()).into(holder.ivUserImage);
            holder.tvUserName.setText(searchList.get(position).getName());
        }


            if(searchList.get(position).getName().equals("")) {
                String searchText = searchList.get(position).getTitle().toLowerCase(Locale.getDefault());
                if (searchText.contains(searchString)) {
                int startPos = searchText.indexOf(searchString);
                int endPos = startPos + searchString.length();
                Spannable spanString = Spannable.Factory.getInstance().newSpannable(holder.tvSearch.getText());
                spanString.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.tvSearch.setText(spanString);
            }
        }else{
                String searchText = searchList.get(position).getName().toLowerCase(Locale.getDefault());
                if (searchText.contains(searchString)) {
                    int startPos = searchText.indexOf(searchString);
                    int endPos = startPos + searchString.length();
                    Spannable spanString = Spannable.Factory.getInstance().newSpannable(holder.tvUserName.getText());
                    spanString.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    holder.tvUserName.setText(spanString);
                }
            }


        holder.tvSearch.setOnClickListener(view -> {
                Intent intent = new Intent(context, CommentOnPostDetailsActivity.class);
                MyPreferenceData myPref = new MyPreferenceData(context);
                myPref.saveData(BaseUrl.PREF_SLUG, searchList.get(position).getSlug());
                context.startActivity(intent);
        });

        holder.llUserSection.setOnClickListener(view -> {
            Intent friend = new Intent(context , UserProfileAndPostActivity.class);
            friend.putExtra(BaseUrl.NAME , searchList.get(position).getName());
            context.startActivity(friend);
        });

    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvSearch ,tvUserName;
        private LinearLayoutCompat llSearchList , llUserSection , llPostSection;
        private ImageView ivUserImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSearch = itemView.findViewById(R.id.tvSearchPost);
            llSearchList = itemView.findViewById(R.id.llSearchList);
            llUserSection = itemView.findViewById(R.id.llUserSection);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            ivUserImage = itemView.findViewById(R.id.ivUserImage);
            llPostSection = itemView.findViewById(R.id.llPostSection);

        }
    }
}
