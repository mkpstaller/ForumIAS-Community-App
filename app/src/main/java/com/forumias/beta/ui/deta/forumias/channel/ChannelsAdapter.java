package com.forumias.beta.ui.deta.forumias.channel;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
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
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.ChannelModel;
import com.forumias.beta.ui.deta.forumias.channel.channelInterface.ChannelSubscribeInterface;
import com.forumias.beta.utility.CircularTextView;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;

import java.util.Arrays;
import java.util.List;

public class ChannelsAdapter extends RecyclerView.Adapter<ChannelsAdapter.ViewHolder> {
    private List<ChannelModel.ChannelList> channelList;
    private Context context;
    private ChannelSubscribeInterface channelSubscribeInterface;
    private int loginUserId, Tagpos;

    ChannelsAdapter(List<ChannelModel.ChannelList> channelList, Context context, ChannelSubscribeInterface channelSubscribeInterface, int Tagpos) {
        this.context = context;
        this.channelList = channelList;
        this.channelSubscribeInterface = channelSubscribeInterface;
        this.Tagpos = Tagpos;
        MyPreferenceData myPreferenceData = new MyPreferenceData(context);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.channels_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ChannelModel.ChannelList channelModel = channelList.get(position);

        holder.tvName.setText(channelModel.getTitle());
        holder.tvPostCount.setText(String.valueOf(channelModel.getPostCount()));
        holder.tvChannelDescreption.setText(Html.fromHtml(channelModel.getDescription()));
        String subscribeBy = channelModel.getFollowInfo().getFollowBy();
        String[] followUserCount = subscribeBy.split(",");
        int itemCount = followUserCount.length;
        Log.e("loginUserId==> ", String.valueOf(loginUserId));
        List<String> followList = Arrays.asList(followUserCount);
        if (Tagpos != 2) { // Tagpos 2 means my channel
            if (followList.contains(String.valueOf(loginUserId))) {
                holder.tvSubscribed.setVisibility(View.GONE);
                holder.tvUnSubscribed.setVisibility(View.VISIBLE);

            } else {
                if(channelModel.getSubscribeStatus() == 1){
                    holder.tvSubscribed.setVisibility(View.GONE);
                    holder.tvUnSubscribed.setVisibility(View.VISIBLE);
                }else {
                    holder.tvSubscribed.setVisibility(View.VISIBLE);
                    holder.tvUnSubscribed.setVisibility(View.GONE);
                }
            }
        }else{
            holder.tvSubscribed.setVisibility(View.GONE);
            holder.tvUnSubscribed.setVisibility(View.GONE);

        }
        holder.tvFollowerCount.setText(String.valueOf(itemCount));
        if (!Utility.isNullOrEmpty(channelModel.getChannelImg())) {
            holder.tvChannelFirstText.setVisibility(View.GONE);
            holder.ivChannelImage.setVisibility(View.VISIBLE);
            Glide.with(context).load(BaseUrl.PAGE_IMAGE_URL + channelModel.getChannelImg()).into(holder.ivChannelImage);
        } else {
            holder.tvChannelFirstText.setVisibility(View.VISIBLE);
            holder.tvChannelFirstText.setStrokeWidth(1);
            holder.tvChannelFirstText.setSolidColor(channelModel.getColorCode());
            char firstText = channelModel.getTitle().charAt(0);
            holder.tvChannelFirstText.setText(String.valueOf(firstText));
        }


        holder.llChannelSection.setOnClickListener(view -> {
            Intent intent = new Intent(context, MyChannelPostActivity.class);
            intent.putExtra(BaseUrl.USER_ID,channelModel.getUserId());
            intent.putExtra(BaseUrl.TITLE,channelModel.getTitle());
            intent.putExtra(BaseUrl.DESCRIPTION,channelModel.getDescription());
            intent.putExtra(BaseUrl.SUBSCRIBED,channelModel.getFollowInfo().getFollowBy());
            intent.putExtra(BaseUrl.POST_COUNT,channelModel.getPostCount());
            if (!Utility.isNullOrEmpty(channelModel.getChannelImg())) {
                intent.putExtra(BaseUrl.IMAGE,channelModel.getChannelImg());
            } else {
                intent.putExtra(BaseUrl.COLOR_CODE,channelModel.getColorCode());
            }


            context.startActivity(intent);
        });
    /*   holder.tvSubscribed.setOnClickListener(view ->
                channelSubscribeInterface.channelSubscribe(channelModel ,String.valueOf(loginUserId), String.valueOf(channelModel.getId())
                        , String.valueOf(BaseUrl.SUBSCRIBED_STATUS), String.valueOf(BaseUrl.SUBSCRIBED_ACT_TYPE), holder.tvSubscribed, holder.llUnSubscribed, position));
        holder.tvUnSubscribed.setOnClickListener(view ->
                channelSubscribeInterface.channelSubscribe(channelModel,String.valueOf(loginUserId), String.valueOf(channelModel.getId())
                        , String.valueOf(BaseUrl.UNSUBSCRIBED_STATUS), String.valueOf(BaseUrl.SUBSCRIBED_ACT_TYPE), holder.tvSubscribed, holder.llUnSubscribed, position));
*/
    }

    @Override
    public int getItemCount() {
        return channelList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvName, tvChannelDescreption, tvFollowerCount, tvUnSubscribed,
                tvSubscribed, tvPostCount;
        private CircularTextView tvChannelFirstText;
        private LinearLayoutCompat llChannelSection;
        private ImageView ivChannelImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvChannelFirstText = itemView.findViewById(R.id.tvChannelFirstText);
            tvChannelDescreption = itemView.findViewById(R.id.tvChannelDescreption);
            ivChannelImage = itemView.findViewById(R.id.ivChannelImage);
            tvFollowerCount = itemView.findViewById(R.id.tvFollowerCount);
            tvUnSubscribed = itemView.findViewById(R.id.tvUnSubscribed);
            tvSubscribed = itemView.findViewById(R.id.tvSubscribed);
            tvPostCount = itemView.findViewById(R.id.tvPostCount);
            llChannelSection = itemView.findViewById(R.id.llChannelSection);
        }
    }
}
