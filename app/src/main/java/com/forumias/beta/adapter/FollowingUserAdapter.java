package com.forumias.beta.adapter;

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
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.R;
import com.forumias.beta.ui.deta.forumias.profile.model.FollowersModel;
import com.forumias.beta.ui.deta.forumias.user.UserFollowUnfollowInterface;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.UserProfileAndPostActivity;

import java.util.List;

public class FollowingUserAdapter extends RecyclerView.Adapter<FollowingUserAdapter.ViewHolder> {

    private List<FollowersModel.FollowerList> followingUserList;
    private Context context;
    int pos , darkModeStatus , loginUserId;
    UserFollowUnfollowInterface userFollowUnfollowInterface;

    public FollowingUserAdapter(Context context, List<FollowersModel.FollowerList> followingUserList , int pos ,
                                UserFollowUnfollowInterface userFollowUnfollowInterface) {
        this.context = context;
        this.followingUserList = followingUserList;
        this.pos = pos;
        this.userFollowUnfollowInterface = userFollowUnfollowInterface;
        MyPreferenceData myPreferenceData = new MyPreferenceData(context);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       if(darkModeStatus == 1){
           holder.tvUserName.setTextColor(ContextCompat.getColor(context , R.color.light_white));
       }else{
           holder.tvUserName.setTextColor(ContextCompat.getColor(context , R.color.black_light));
       }
       if(pos == 1){
           holder.llFollowing.setVisibility(View.VISIBLE);
           holder.tvFollowers.setVisibility(View.GONE);
       }else{
           holder.llFollowing.setVisibility(View.GONE);
           holder.tvFollowers.setVisibility(View.VISIBLE);
       }

        new Utility().showUserVerified(followingUserList.get(position).getIs_verified(), holder.ivUserVerified);
       holder.tvUserName.setText(followingUserList.get(position).getName());
       Glide.with(context).load(followingUserList.get(position).getImage()).into(holder.ivUserImage);

       holder.tvUserName.setOnClickListener(view -> {
           Intent friend = new Intent(context , UserProfileAndPostActivity.class);
           friend.putExtra(BaseUrl.NAME , followingUserList.get(position).getName());
           context.startActivity(friend);
       });

       holder.ivUserImage.setOnClickListener(view -> {
           Intent friend = new Intent(context , UserProfileAndPostActivity.class);
           friend.putExtra(BaseUrl.NAME , followingUserList.get(position).getName());
           context.startActivity(friend);
       });

       holder.llFollowing.setOnClickListener(view -> {
           userFollowUnfollowInterface.followUnfollowing(loginUserId , followingUserList.get(position).getId() , 0 , followingUserList ,position);
       });

      /*  holder.llFollowing.setOnClickListener(view -> {
            userFollowUnfollowInterface.followUnfollowing(loginUserId , followingUserList.get(position).getId() , 0 , followingUserList ,position);
        });*/
    }

    @Override
    public int getItemCount() {
        return followingUserList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView tvUserName , tvFollowers;
        public LinearLayoutCompat llUserSection , llFollowing;
        public ImageView ivUserImage;
        public AppCompatImageView ivUserVerified;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            llUserSection = itemView.findViewById(R.id.llUserSection);
            ivUserImage = itemView.findViewById(R.id.ivUserImage);
            llFollowing = itemView.findViewById(R.id.llFollowing);
            tvFollowers = itemView.findViewById(R.id.tvFollowers);
            ivUserVerified = itemView.findViewById(R.id.ivUserVerified);

        }
    }
}
