package com.forumias.beta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.forumias.beta.R;
import com.forumias.beta.pojo.FollowingORFollowerResponse;


import java.util.ArrayList;
import java.util.List;

public class SelectUserAdapter extends RecyclerView.Adapter<SelectUserAdapter.ViewHolder> {

    private List<FollowingORFollowerResponse.Follower> followerList;
    private Context context;
    FollowingORFollowerResponse.Follower followerModel;

    public SelectUserAdapter(Context context, List<FollowingORFollowerResponse.Follower> followerList) {
        this.context = context;
        this.followerList = followerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        followerModel = followerList.get(position);
        holder.tvChatUserName.setText(followerList.get(position).getName());
        Glide.with(context).load(followerList.get(position).getImage()).into(holder.ivUserImage);
        holder.itemView.setTag(followerList);
    }

    @Override
    public int getItemCount() {
        return followerList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView tvChatUserName;
        public LinearLayoutCompat llUserSection;
        public ImageView ivUserImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChatUserName = itemView.findViewById(R.id.tvChatUserName);
            llUserSection = itemView.findViewById(R.id.llUserSection);
            ivUserImage = itemView.findViewById(R.id.ivUserImage);

            llUserSection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 /*   UserPojo userPojo = (UserPojo) itemView.getTag();
                    selectUserInterface.selectedUser(userPojo.getName() , 1);
                    if(chatUserList.size() >= 0){
                        chatUserList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    }*/
                }
            });
        }
    }

    public void filterList(List<FollowingORFollowerResponse.Follower> filterList){
        followerList  = new ArrayList<>();
        followerList.addAll(filterList);
        notifyDataSetChanged();
    }
}
