package com.forumias.beta.ui.deta.forumias.channel.channel_post;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.utility.DateFormatUtils;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;

public class ChannelPostAdapter extends PagedListAdapter<ChannelPostModel.ChannelAnnouncementsList , ChannelPostAdapter.ViewHolder> {
    private Context context;
    private String title;

    private boolean clickStatus = true;
    private int darkModeStatus;

    public ChannelPostAdapter(Context context , String title) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.title = title;
        MyPreferenceData myPreferenceData = new MyPreferenceData(context);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_post_layout,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChannelPostModel.ChannelAnnouncementsList channelPostList = getItem(position);

        if(darkModeStatus == 1){
            holder.llChannelPost.setBackgroundResource(R.drawable.dark_post_border);
            holder.tvTitle.setTextColor(ContextCompat.getColor(context,R.color.light_white));
            holder.myView.setBackgroundResource(R.color.border_color);
        }else{
            holder.llChannelPost.setBackgroundResource(R.color.white);
            holder.tvTitle.setTextColor(ContextCompat.getColor(context,R.color.black_light));
            holder.myView.setBackgroundResource(R.color.low_gray);
        }

        assert channelPostList != null;
        if(channelPostList.getIs_announcement() == 1){
            holder.ivPin.setVisibility(View.VISIBLE);
        }else{
            holder.ivPin.setVisibility(View.GONE);
        }

        holder.tvViewCount.setText(new Utility().prettyCount(channelPostList.getView_count())+" Views");
        holder.tvTitle.setText(channelPostList.getTitle());
        String data = new DateFormatUtils().showPostDate(channelPostList.getCreated_at());
        holder.tvDate.setText(data);
    /*  String description = channelPostList.getDescription();
        if(description.length() > 100){
            description = description.substring(0,100)+" <font color=\"blue\">...Read More";
            holder.tvDescription.setText(Html.fromHtml(description));
        }
        holder.tvDescription.setText(Html.fromHtml(description));*/

       // Log.e("image===> " , channelPostList.getColor_code());
        holder.llChannelPostSection.setOnClickListener(v -> {
            Intent intent = new Intent(context , ChannelPostDetailsActivity.class);
            intent.putExtra(BaseUrl.TITLE , channelPostList.getTitle());
            intent.putExtra(BaseUrl.DESCRIPTION , channelPostList.getDescription());
            intent.putExtra(BaseUrl.VIEW_COUNT , channelPostList.getView_count());
            intent.putExtra(BaseUrl.SLUG , title);
            intent.putExtra(BaseUrl.IMAGE , channelPostList.getImg());
            intent.putExtra(BaseUrl.COLOR_CODE , channelPostList.getColor_code());
            intent.putExtra(BaseUrl.CHYTEXT , channelPostList.getCatchy_text());
            context.startActivity(intent);
        });



    }


   private static DiffUtil.ItemCallback<ChannelPostModel.ChannelAnnouncementsList> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ChannelPostModel.ChannelAnnouncementsList>() {
                @Override
                public boolean areItemsTheSame(@NonNull ChannelPostModel.ChannelAnnouncementsList oldItem, @NonNull ChannelPostModel.ChannelAnnouncementsList newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull ChannelPostModel.ChannelAnnouncementsList oldItem, @NonNull ChannelPostModel.ChannelAnnouncementsList newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public class ViewHolder extends RecyclerView.ViewHolder {
        private  AppCompatTextView tvTitle , tvDescription  , tvViewCount , tvDate;
        private  AppCompatImageView ivPin,tvShare;
        private LinearLayoutCompat llChannelPostSection , llChannelPost;
        private View myView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivPin = itemView.findViewById(R.id.ivPin);
            tvViewCount = itemView.findViewById(R.id.tvViewCount);
            llChannelPostSection = itemView.findViewById(R.id.llChannelPostSection);
            tvDate = itemView.findViewById(R.id.tvDate);
            llChannelPost = itemView.findViewById(R.id.llChannelPost);
            myView = itemView.findViewById(R.id.myView);

        }
    }
}
