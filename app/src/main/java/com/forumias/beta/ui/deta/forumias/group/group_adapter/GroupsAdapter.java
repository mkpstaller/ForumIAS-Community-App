package com.forumias.beta.ui.deta.forumias.group.group_adapter;

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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.GroupModel;
import com.forumias.beta.utility.CircularTextView;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.ui.deta.forumias.user.UserInterface;
import com.forumias.beta.ui.deta.forumias.group.GroupsDetailsActivity;
import com.forumias.beta.ui.deta.forumias.page.MyPagesPostActivity;
import com.forumias.beta.R;

import java.util.Arrays;
import java.util.List;


public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> {
    private List<GroupModel.GroupListing> groupList;
    private  Context context;
   // private boolean showMore = true; // Todo this code uncommented if you uncomment Description code
    private UserInterface userInterface;
    private int loginUserId;

    public GroupsAdapter(List<GroupModel.GroupListing> groupList , Context context , UserInterface userInterface){
        this.context = context;
        this.groupList = groupList;
        this.userInterface = userInterface;

        MyPreferenceData myPreferenceData = new MyPreferenceData(context);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.groups_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(groupList.get(position).getTitle());
        holder.tvPostCount.setText(groupList.get(position).getPostInfoCount());
        new Utility().showUserVerified(groupList.get(position).getIs_verified(), holder.ivUserVerified);
        String followersInfo = String.valueOf(groupList.get(position).getFollowInfo().getFollowBy());
        String[] followInfoCount = followersInfo.split(",");
        int followCount = followInfoCount.length;
        holder.tvFollowers.setText(String.valueOf(followCount));
        List<String> followUserId = Arrays.asList(followInfoCount);

        if(followUserId.contains(String.valueOf(loginUserId))){
            holder.tvLeaveGroup.setVisibility(View.VISIBLE);
            holder.tvJoinGroup.setVisibility(View.GONE);
        }else{
            holder.tvLeaveGroup.setVisibility(View.GONE);
            holder.tvJoinGroup.setVisibility(View.VISIBLE);
        }

        if (!Utility.isNullOrEmpty(groupList.get(position).getTagImage())) {
            holder.tvChannelFirstText.setVisibility(View.GONE);
            holder.ivChannelImage.setVisibility(View.VISIBLE);
            Glide.with(context).load(BaseUrl.PAGE_IMAGE_URL+groupList.get(position).getTagImage()).into(holder.ivChannelImage);
        } else {
                holder.ivChannelImage.setVisibility(View.GONE);
                holder.tvChannelFirstText.setVisibility(View.VISIBLE);
                holder.tvChannelFirstText.setStrokeWidth(1);
                holder.tvChannelFirstText.setSolidColor(groupList.get(position).getColorCode());
                char firstText = groupList.get(position).getTitle().charAt(0);
                holder.tvChannelFirstText.setText(String.valueOf(firstText));
                holder.ivChannelImage.setVisibility(View.GONE);
        }

       holder.llGroupSection.setOnClickListener(view -> {
           Intent intent = new Intent(context , MyPagesPostActivity.class);
           intent.putExtra(BaseUrl.GROUP_NAME,groupList.get(position).getTitle());
           intent.putExtra(BaseUrl.DESCRIPTION,groupList.get(position).getDescription());
           intent.putExtra(BaseUrl.POST_COUNT,groupList.get(position).getPostInfoCount());
           intent.putExtra(BaseUrl.COLORS,groupList.get(position).getColorCode());
           intent.putExtra(BaseUrl.SLUG,groupList.get(position).getTagSlug());
           intent.putExtra(BaseUrl.FOLLOW_COUNT,String.valueOf(followCount));
           intent.putExtra(BaseUrl.ACT_TYPE,String.valueOf(followCount));
           intent.putExtra(BaseUrl.TAG_IMAGE,groupList.get(position).getTagImage());
           intent.putExtra(BaseUrl.PAGE,groupList.get(position).getIsPage());
           intent.putExtra(BaseUrl.GROUP_TYPE,groupList.get(position).getGroupType());
           intent.putExtra(BaseUrl.USER_ID,groupList.get(position).getUserId());
           context.startActivity(intent);




       });

       /*holder.tvLeaveGroup.setOnClickListener(view ->
               userInterface.userFollowUnfollow( String.valueOf(loginUserId), String.valueOf(groupList.get(position).getId())
                       , "0" , String.valueOf(groupList.get(position).getStatus()) , holder.tvJoinGroup , holder.tvLeaveGroup , position));


      holder.tvJoinGroup.setOnClickListener(view ->
              userInterface.userFollowUnfollow( String.valueOf(loginUserId), String.valueOf(groupList.get(position).getId())
                      , "1" , String.valueOf(groupList.get(position).getStatus()) , holder.tvJoinGroup , holder.tvLeaveGroup , position));
*/
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvName , tvPostCount ,tvFollowers , tvJoinGroup
                , tvLeaveGroup;
        private LinearLayoutCompat llGroupSection;
        private CircularTextView tvChannelFirstText;
        private ImageView ivChannelImage;
        AppCompatImageView ivUserVerified;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
          //  tvDescriprion = itemView.findViewById(R.id.tvDescriprion); // Todo tvDescription textView
            llGroupSection = itemView.findViewById(R.id.llGroupSection);
            tvChannelFirstText = itemView.findViewById(R.id.tvChannelFirstText);
            ivChannelImage = itemView.findViewById(R.id.ivChannelImage);
            tvPostCount = itemView.findViewById(R.id.tvPostCount);
            tvFollowers = itemView.findViewById(R.id.tvFollowers);
            tvJoinGroup = itemView.findViewById(R.id.tvJoinGroup);
            tvLeaveGroup = itemView.findViewById(R.id.tvLeaveGroup);
            ivUserVerified = itemView.findViewById(R.id.ivUserVerified);
           // tvShowMore = itemView.findViewById(R.id.tvShowMore); // Todo show and hide tvDescription
            llGroupSection.setOnClickListener(view -> {
                Intent intent = new Intent(context , GroupsDetailsActivity.class);
                context.startActivity(intent);
            });
        }
    }
}
