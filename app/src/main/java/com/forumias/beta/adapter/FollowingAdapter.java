package com.forumias.beta.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.forumias.beta.R;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.utility.CircularTextView;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.pojo.PagesFollowingModel;
import com.forumias.beta.ui.deta.forumias.channel.MyChannelPostActivity;
import com.forumias.beta.ui.deta.forumias.page.MyPagesPostActivity;

import java.util.List;


public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.ViewHolder> {
    private List<PagesFollowingModel.Following> followingList;
    private Context context;
    private  int listSize=0;
    private int showData;
    private int pos;
    private String image;

    public FollowingAdapter(List<PagesFollowingModel.Following> followingList, Context context , int showData , int position) {
        this.followingList = followingList;
        this.context = context;
        this.showData = showData;
        this.pos = position;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.following_list , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(showData == 1){
            holder.llPostImageSection.setVisibility(View.VISIBLE);
        }else{
            holder.llPostImageSection.setVisibility(View.GONE);
        }

      Log.e("pos=====> " , pos+"       "+followingList.get(position).getChannelImage());

        if(pos == 1){
           image = followingList.get(position).getChannelImage();
        }else{
           image = followingList.get(position).getTagImage();
        }
            if (Utility.isNotNullOrEmpty(image)) {
                holder.ivChannelImage.setVisibility(View.GONE);
                holder.tvChannelFirstText.setVisibility(View.GONE);
                holder.ivChannelImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(BaseUrl.PAGE_IMAGE_URL + image).into(holder.ivChannelImage);
            } else {
                holder.ivChannelImage.setVisibility(View.GONE);
                holder.tvChannelFirstText.setVisibility(View.VISIBLE);
                holder.tvChannelFirstText.setStrokeWidth(1);
                holder.tvChannelFirstText.setSolidColor(followingList.get(position).getColorCode());
                char firstText = followingList.get(position).getTitle().charAt(0);
                holder.tvChannelFirstText.setText(String.valueOf(firstText));
            }

        holder.tvTitle.setText(followingList.get(position).getTitle());
        holder.rlFollowingSection.setOnClickListener(view -> {

            if(pos == 1){
                Intent intent = new Intent(context , MyChannelPostActivity.class);
                intent.putExtra(BaseUrl.SLUG,followingList.get(position).getChannel_slug());
                intent.putExtra(BaseUrl.USER_ID,followingList.get(position).getId());
                context.startActivity(intent);
            }else{
                MyPreferenceData myPreferenceData = new MyPreferenceData(context);
                myPreferenceData.saveData(BaseUrl.PREF_SLUG , followingList.get(position).getTagSlug());
                Intent intent = new Intent(context , MyPagesPostActivity.class);
                context.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return followingList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvTitle;
        private RelativeLayout rlFollowingSection;
        private LinearLayoutCompat llPostImageSection;
        private ImageView ivChannelImage;
        private CircularTextView tvChannelFirstText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            llPostImageSection = itemView.findViewById(R.id.llPostImageSection);
            rlFollowingSection = itemView.findViewById(R.id.rlFollowingSection);
            ivChannelImage = itemView.findViewById(R.id.ivChannelImage);
            tvChannelFirstText = itemView.findViewById(R.id.tvChannelFirstText);
        }
    }
}
