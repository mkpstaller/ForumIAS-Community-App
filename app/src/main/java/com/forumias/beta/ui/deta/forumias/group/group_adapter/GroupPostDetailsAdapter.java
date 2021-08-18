package com.forumias.beta.ui.deta.forumias.group.group_adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.pojo.GroupPostDetailsModel;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;

import java.util.List;

public class GroupPostDetailsAdapter extends RecyclerView.Adapter<GroupPostDetailsAdapter.ViewHolder> {

    private List<GroupPostDetailsModel.GroupPostList> groupPostList;
    private Context context;

    public GroupPostDetailsAdapter(List<GroupPostDetailsModel.GroupPostList> groupPostList, Context context) {
        this.groupPostList = groupPostList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myspace_list_layout , parent , false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (groupPostList.get(position).getType() == 0) {
            holder.llPostImageSection.setVisibility(View.GONE);
            holder.tvQuestion.setVisibility(View.VISIBLE);
            holder.tvGroup.setVisibility(View.GONE);
        } else {
            holder.llPostImageSection.setVisibility(View.VISIBLE);
            if((new Utility().isNullOrEmpty(groupPostList.get(position).getImg()))&&
                    (new Utility().isNullOrEmpty(groupPostList.get(position).getCatchyText()))){
                holder.llPostImageSection.setVisibility(View.GONE);
                holder.rlCathySection.setVisibility(View.GONE);
            }else {
                if (new Utility().isNullOrEmpty(groupPostList.get(position).getImg())) {
                    holder.rlCathySection.setVisibility(View.VISIBLE);
                    holder.ivPostImage.setVisibility(View.GONE);
                    if (groupPostList.get(position).getColorCode().equalsIgnoreCase("#f1c40f")) {
                        holder.rlCathySection.setBackgroundColor(Color.parseColor(groupPostList.get(position).getColorCode()));
                        holder.tvCatchyName.setTextColor(ContextCompat.getColor(context, R.color.red));
                        holder.tvCatchyName.setText(groupPostList.get(position).getCatchyText());
                    } else {
                        holder.rlCathySection.setBackgroundColor(Color.parseColor(groupPostList.get(position).getColorCode()));
                        holder.tvCatchyName.setTextColor(ContextCompat.getColor(context, R.color.white));
                        holder.tvCatchyName.setText(groupPostList.get(position).getCatchyText());
                    }
                } else {
                    holder.rlCathySection.setVisibility(View.GONE);
                    holder.ivPostImage.setVisibility(View.VISIBLE);
                    Glide.with(context).load(BaseUrl.POST_IMAGE_URL + groupPostList.get(position).getImg()).into(holder.ivPostImage);
                }
            }
        }
/*
        if (groupPostList.get(position).getUserInfo() != null) {
            holder.tvName.setText(groupPostList.get(position).getUserInfo().getName());
            Glide.with(context).load(groupPostList.get(position).getUserInfo().getImage()).into(holder.ivUserImage);
        }else{
            String mySignature = "Created Post "+new DateFormatUtils().showPostDate(groupPostList.get(position).getCreatedAt());
            holder.tvDateTime.setText(mySignature);
        }*/

        if(groupPostList.get(position).getTagId() == 0 && groupPostList.get(position).getChannelId() == 0){
            holder.llChannelSection.setVisibility(View.GONE);
            holder.llGroupSection.setVisibility(View.GONE);
            holder.tvViewCountSecond.setVisibility(View.VISIBLE);
            holder.rlLikeCommentSection.setVisibility(View.VISIBLE);

        }else {
            if(groupPostList.get(position).getTagId() != 0){
                holder.llChannelSection.setVisibility(View.GONE);
                holder.llViewSection.setVisibility(View.GONE);
                holder.llGroupSection.setVisibility(View.VISIBLE);
                holder.rlLikeCommentSection.setVisibility(View.VISIBLE);
            }else{
                holder.llChannelSection.setVisibility(View.VISIBLE);
                holder.llGroupSection.setVisibility(View.GONE);
                holder.rlLikeCommentSection.setVisibility(View.GONE);
                holder.llViewSection.setVisibility(View.VISIBLE);

            }
        }

        holder.tvViewCountSecond.setText(new Utility().prettyCount(groupPostList.get(position).getViewCount()));
        holder.tvPostTitle.setText(groupPostList.get(position).getTitle());

        new Utility().showDescription(holder.webView , groupPostList.get(position).getDescription());

    }



    @Override
    public int getItemCount() {
        return groupPostList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvName, tvPostTitle, tvCatchyName, tvDescription, tvQuestion, tvGroup ,tvDateTime,
                tvLikeCount,tvCommentCount ,  tvMore,tvChannel , tvViewCount ,tvViewCountSecond;
        private LinearLayoutCompat llMainPostSection, llPostImageSection , llGroupSection ,
                llChannelSection , llViewSection;
        private AppCompatImageView ivPostImage , ivShare;
        private RelativeLayout rlCathySection , rlLikeCommentSection;
        private ImageView ivUserImage;
        private WebView webView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            ivUserImage = itemView.findViewById(R.id.ivUserImage);
            tvPostTitle = itemView.findViewById(R.id.tvPostTitle);
            llPostImageSection = itemView.findViewById(R.id.llPostImageSection);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            rlCathySection = itemView.findViewById(R.id.rlCathySection);
            tvCatchyName = itemView.findViewById(R.id.tvCatchyName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvGroup = itemView.findViewById(R.id.tvGroup);
            tvLikeCount = itemView.findViewById(R.id.tvLikeCount);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
           // tvBoorMark = itemView.findViewById(R.id.tvBoorMark);
            ivShare = itemView.findViewById(R.id.ivShare);
            tvMore = itemView.findViewById(R.id.tvMore);
            tvChannel = itemView.findViewById(R.id.tvChannel);
            tvViewCount = itemView.findViewById(R.id.tvViewCount);
            tvViewCountSecond = itemView.findViewById(R.id.tvViewCountSecond);
            llMainPostSection = itemView.findViewById(R.id.llMainPostSection);
            rlLikeCommentSection = itemView.findViewById(R.id.rlLikeCommentSection);
            llGroupSection = itemView.findViewById(R.id.llGroupSection);
            llChannelSection = itemView.findViewById(R.id.llChannelSection);
            llViewSection = itemView.findViewById(R.id.llViewSection);
            tvDateTime = itemView.findViewById(R.id.tvDate);
            webView = itemView.findViewById(R.id.webView);
        }
    }


}
