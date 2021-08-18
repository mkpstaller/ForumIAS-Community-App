package com.forumias.beta.adapter;

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
import com.forumias.beta.R;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.ui.deta.forumias.comment.ui.model.LikeUserModel;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.UserProfileAndPostActivity;

import java.util.List;

public class LikeUserAdapter extends RecyclerView.Adapter<LikeUserAdapter.ViewHolder> {
    private List<LikeUserModel.LikeUserList> likeUserList;
    Context context;
    public LikeUserAdapter(List<LikeUserModel.LikeUserList> likeUserList , Context context){
        this.likeUserList = likeUserList;
        this.context = context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.like_user_show_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvLikeUserName.setText(likeUserList.get(position).getName());
        Glide.with(context).load(likeUserList.get(position).getImg()).into(holder.ivLikeuserImage);

        holder.llLikeUserSection.setOnClickListener(view -> {
            Intent friend = new Intent(context , UserProfileAndPostActivity.class);
            friend.putExtra(BaseUrl.NAME , likeUserList.get(position).getName());
            context.startActivity(friend);
        });

    }

    @Override
    public int getItemCount() {
        return likeUserList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivLikeuserImage;
        private AppCompatTextView tvLikeUserName;
        private LinearLayoutCompat llLikeUserSection;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivLikeuserImage = itemView.findViewById(R.id.ivLikeUserImage);
            tvLikeUserName = itemView.findViewById(R.id.tvLikeUserName);
            llLikeUserSection = itemView.findViewById(R.id.llLikeUserSection);
        }
    }
}
