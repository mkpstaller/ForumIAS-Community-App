package com.forumias.beta.ui.deta.forumias.page;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.GroupModel;
import com.forumias.beta.utility.CircularTextView;
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;
import com.forumias.beta.ui.deta.forumias.page.page_interface.PageFollowInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PagesAdapter extends RecyclerView.Adapter<PagesAdapter.ViewHolder> {
    private List<GroupModel.GroupListing> groupList;
    private  Context context;
    private boolean showMore = true;
    private PageFollowInterface pageFollowInterface;
    private int loginUserId ,darkModeStatus;
    PagesAdapter(List<GroupModel.GroupListing> groupList , int loginUserId, Context context, PageFollowInterface pageFollowInterface){
        this.context = context;
        this.groupList = groupList;
        this.pageFollowInterface = pageFollowInterface;
        this.loginUserId = loginUserId;
        MyPreferenceData myPreferenceData = new MyPreferenceData(context);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pages_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GroupModel.GroupListing groupModel = groupList.get(position);

        if(darkModeStatus == 1){
            holder.llPageListSection.setBackgroundResource(R.drawable.dark_post_border);
            holder.tvName.setTextColor(ContextCompat.getColor(context, R.color.light_white));
            holder.tvPostCount.setTextColor(ContextCompat.getColor(context, R.color.light_white));
            holder.tvFollowers.setTextColor(ContextCompat.getColor(context, R.color.light_white));
        }else{
            holder.llPageListSection.setBackgroundResource(R.color.white);
            holder.tvName.setTextColor(ContextCompat.getColor(context, R.color.black_light));
            holder.tvPostCount.setTextColor(ContextCompat.getColor(context, R.color.black_light));
            holder.tvFollowers.setTextColor(ContextCompat.getColor(context, R.color.black_light));
        }

        holder.tvName.setText(groupModel.getTitle());
        holder.tvPostCount.setText(String.valueOf(groupModel.getPostInfoCount()));
        new Utility().showUserVerified(groupModel.getIs_verified(), holder.ivUserVerified);
        String followersInfo = String.valueOf(groupModel.getFollowInfo().getFollowBy());
        String[] followInfoCount = followersInfo.split(",");
        int followCount = followInfoCount.length;
        holder.tvFollowers.setText(String.valueOf(followCount));
        List<String> followUserId = Arrays.asList(followInfoCount);

        if(followUserId.contains(String.valueOf(loginUserId))){
            holder.tvFollowing.setVisibility(View.VISIBLE);
            holder.tvFollow.setVisibility(View.GONE);
        }else{
            if(groupModel.getFollowStatus() == 1){
                holder.tvFollowing.setVisibility(View.VISIBLE);
                holder.tvFollow.setVisibility(View.GONE);
            }else {
                holder.tvFollowing.setVisibility(View.GONE);
                holder.tvFollow.setVisibility(View.VISIBLE);
            }
        }

        if (!Utility.isNullOrEmpty(groupModel.getTagImage())) {
            holder.tvChannelFirstText.setVisibility(View.GONE);
            holder.ivChannelImage.setVisibility(View.VISIBLE);
            Glide.with(context).load(BaseUrl.PAGE_IMAGE_URL+groupModel.getTagImage()).into(holder.ivChannelImage);
        } else {
                holder.ivChannelImage.setVisibility(View.GONE);
                holder.tvChannelFirstText.setVisibility(View.VISIBLE);
                holder.tvChannelFirstText.setStrokeWidth(1);
                holder.tvChannelFirstText.setSolidColor(groupModel.getColorCode());
                char firstText = groupModel.getTitle().charAt(0);
                holder.tvChannelFirstText.setText(String.valueOf(firstText));
                holder.ivChannelImage.setVisibility(View.GONE);
        }


       holder.llGroupSection.setOnClickListener(view -> {
           Intent intent = new Intent(context , MyPagesPostActivity.class);
           MyPreferenceData myPref = new MyPreferenceData(context);
           myPref.saveData(BaseUrl.PREF_SLUG,groupModel.getTagSlug());
           context.startActivity(intent);
         //  groupList.remove(position);
         //  notifyDataSetChanged();
       });


    holder.tvFollowing.setOnClickListener(view -> {
        if(loginUserId != 0) {
            pageFollowInterface.pageFollowing(groupModel, String.valueOf(loginUserId), String.valueOf(groupModel.getId())
                    , String.valueOf(BaseUrl.UNSUBSCRIBED_STATUS), String.valueOf(BaseUrl.PAGE_ACT_TYPE), holder.tvFollow, holder.tvFollowing, position);
        }else{
            new MyUtility().showLoginAlert(context);
        }

    });


      holder.tvFollow.setOnClickListener(view -> {
          if(loginUserId != 0) {
              pageFollowInterface.pageFollowing(groupModel, String.valueOf(loginUserId), String.valueOf(groupList.get(position).getId())
                      , String.valueOf(BaseUrl.SUBSCRIBED_STATUS), String.valueOf(BaseUrl.PAGE_ACT_TYPE), holder.tvFollow, holder.tvFollowing, position);
          }else{
              new MyUtility().showLoginAlert(context);
          }
      });

    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvName , tvDescription , tvPostCount ,tvFollowers
             ,tvShowMore;
        private LinearLayoutCompat llGroupSection ,tvFollow ,tvFollowing , llPageListSection;
        private CircularTextView tvChannelFirstText;
        private ImageView ivChannelImage;
        private AppCompatImageView ivUserVerified;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            llGroupSection = itemView.findViewById(R.id.llGroupSection);
            tvChannelFirstText = itemView.findViewById(R.id.tvChannelFirstText);
            ivChannelImage = itemView.findViewById(R.id.ivChannelImage);
            tvPostCount = itemView.findViewById(R.id.tvPostCount);
            tvFollowers = itemView.findViewById(R.id.tvFollowers);
            tvFollow = itemView.findViewById(R.id.llFollow);
            tvFollowing = itemView.findViewById(R.id.llFollowing);
            tvShowMore = itemView.findViewById(R.id.tvShowMore);
            ivUserVerified = itemView.findViewById(R.id.ivUserVerified);
            llPageListSection = itemView.findViewById(R.id.llPageListSection);
            /*llGroupSection.setOnClickListener(view -> {
                Intent intent = new Intent(context , GroupsDetailsActivity.class);
                context.startActivity(intent);
            });*/
        }
    }

    public void updateListData(List<GroupModel.GroupListing> pageList){

       groupList = new ArrayList<>();
        groupList.addAll(pageList);
        notifyDataSetChanged();
    }
}
