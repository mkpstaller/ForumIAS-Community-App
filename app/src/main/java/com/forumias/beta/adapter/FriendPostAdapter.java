package com.forumias.beta.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import com.forumias.beta.utility.Utility;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.pojo.UserInfoAndPostModel;
import com.forumias.beta.ui.deta.forumias.comment.ui.CommentOnPostDetailsActivity;
import com.forumias.beta.R;

import java.util.List;

public class FriendPostAdapter extends RecyclerView.Adapter<FriendPostAdapter.ViewHolder> {

    private List<UserInfoAndPostModel.Stories> userStoryList;
    Context context;
    public FriendPostAdapter(List<UserInfoAndPostModel.Stories> userStoryList, Context context) {
        this.userStoryList = userStoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myspace_list_layout,parent ,false);
        return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (userStoryList.get(position).getType() == 0) {
            holder.llPostImageSection.setVisibility(View.GONE);
            holder.tvQuestion.setVisibility(View.VISIBLE);
            holder.tvGroup.setVisibility(View.GONE);
        } else {
            holder.llPostImageSection.setVisibility(View.VISIBLE);
            if((Utility.isNullOrEmpty(userStoryList.get(position).getImg()) &&
                    (Utility.isNullOrEmpty(userStoryList.get(position).getColorCode())))){
                holder.llPostImageSection.setVisibility(View.GONE);
                holder.rlCathySection.setVisibility(View.GONE);
            }else {
                if (Utility.isNullOrEmpty(userStoryList.get(position).getImg())) {
                    holder.rlCathySection.setVisibility(View.VISIBLE);
                    holder.ivPostImage.setVisibility(View.GONE);
                    if (userStoryList.get(position).getColorCode().equalsIgnoreCase("#f1c40f")) {
                        holder.rlCathySection.setBackgroundColor(Color.parseColor(userStoryList.get(position).getColorCode()));
                        holder.tvCatchyName.setTextColor(ContextCompat.getColor(context, R.color.red));
                        holder.tvCatchyName.setText(userStoryList.get(position).getCatchyText());
                    } else {
                        holder.rlCathySection.setBackgroundColor(Color.parseColor(userStoryList.get(position).getColorCode()));
                        holder.tvCatchyName.setTextColor(ContextCompat.getColor(context, R.color.white));
                        holder.tvCatchyName.setText(userStoryList.get(position).getCatchyText());
                    }
                } else {
                    holder.rlCathySection.setVisibility(View.GONE);
                    holder.ivPostImage.setVisibility(View.VISIBLE);
                    Glide.with(context).load(BaseUrl.POST_IMAGE_URL + userStoryList.get(position).getImg()).into(holder.ivPostImage);
                }
            }
        }

      if (userStoryList.get(position).getStoryUserInfo() != null) {
            holder.tvName.setVisibility(View.GONE);
            Glide.with(context).load(userStoryList.get(position).getStoryUserInfo().getImage()).into(holder.ivUserImage);
                /*String mySignature = "@"+userStoryList.get(position).getStoryUserInfo().getName()+
                        " Created Post "+new DateFormatUtils().showPostDate(userStoryList.get(position).getCreatedAt());
                holder.tvDate.setText(mySignature);*/
        }

        if(userStoryList.get(position).getTagId() == 0 && userStoryList.get(position).getChannelId() == 0){
            holder.llChannelSection.setVisibility(View.GONE);
            holder.llGroupSection.setVisibility(View.GONE);
            holder.tvViewCountSecond.setVisibility(View.VISIBLE);
            holder.rlLikeCommentSection.setVisibility(View.VISIBLE);

        }else {
            if(userStoryList.get(position).getTagId() != 0){
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
        holder.tvViewCountSecond.setText(new Utility().prettyCount(userStoryList.get(position).getViewCount()));
        holder.tvPostTitle.setText(userStoryList.get(position).getTitle());

        new Utility().showDescription(holder.webView , userStoryList.get(position).getDescription());

        holder.llMainPostSection.setOnClickListener(v -> {
                Intent intent = new Intent(context, CommentOnPostDetailsActivity.class);
                intent.putExtra(BaseUrl.SLUG ,userStoryList.get(position).getSlug());
                context.startActivity(intent);
        });

        holder.webView.setOnTouchListener((v, event) -> {
            if (event.getAction()== MotionEvent.ACTION_MOVE){
                return false;
            }

            if (event.getAction()==MotionEvent.ACTION_UP){
                Intent intent = new Intent(context, CommentOnPostDetailsActivity.class);
                intent.putExtra(BaseUrl.SLUG ,userStoryList.get(position).getSlug());
                context.startActivity(intent);
            }

            return false;
        });

    }

    @Override
    public int getItemCount() {
        return userStoryList.size();
    }

    protected  class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvName, tvPostTitle, tvCatchyName, tvDescriprion, tvQuestion, tvGroup ,
                tvLikeCount,tvCommentCount  ,  tvMore,tvChannel , tvViewCount ,tvViewCountSecond , tvDate;
        private LinearLayoutCompat llMainPostSection, llPostImageSection , llGroupSection ,
                llChannelSection , llViewSection;
        private AppCompatImageView ivPostImage,tvBoorMark , ivShare;
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
            tvDescriprion = itemView.findViewById(R.id.tvDescriprion);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvGroup = itemView.findViewById(R.id.tvGroup);
            tvLikeCount = itemView.findViewById(R.id.tvLikeCount);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            tvBoorMark = itemView.findViewById(R.id.tvBoorMark);
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
            tvDate = itemView.findViewById(R.id.tvDate);
            webView = itemView.findViewById(R.id.webView);

        }
    }
}
