package com.forumias.beta.ui.deta.forumias.group.group_adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.pojo.GroupPostDetailsModel;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.UserProfileAndPostActivity;
import com.forumias.beta.R;

import java.util.List;

public class GroupJoinMemberAdapter extends RecyclerView.Adapter<GroupJoinMemberAdapter.ViewHolder> {
    private List<GroupPostDetailsModel.Followers> followersList;
    private Context context;

    public GroupJoinMemberAdapter(List<GroupPostDetailsModel.Followers> followersList, Context context) {
        this.followersList = followersList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_join_member_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(followersList.get(position).getName());
        Glide.with(context).load(followersList.get(position).getImage()).into(holder.ivImage);

        holder.llGroupUserSection.setOnClickListener(view -> {
            Intent friend = new Intent(context , UserProfileAndPostActivity.class);
            friend.putExtra(BaseUrl.NAME , followersList.get(position).getName());
            friend.putExtra(BaseUrl.TAG_ID , followersList.get(position).getId());
            friend.putExtra(BaseUrl.ACT_TYPE , followersList.get(position).getType());
            context.startActivity(friend);
        });
    }

    @Override
    public int getItemCount() {
        return followersList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvName;
        private ImageView ivImage;
        private LinearLayoutCompat llGroupUserSection;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivImage = itemView.findViewById(R.id.ivImage);
            llGroupUserSection = itemView.findViewById(R.id.llGroupUserSection);
        }
    }
}
