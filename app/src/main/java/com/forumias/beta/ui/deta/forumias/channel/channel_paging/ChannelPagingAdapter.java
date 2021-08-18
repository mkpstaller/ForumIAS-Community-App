package com.forumias.beta.ui.deta.forumias.channel.channel_paging;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.ChannelModel;
import com.forumias.beta.ui.deta.forumias.channel.channelInterface.ChannelSubscribeInterface;
import com.forumias.beta.utility.CircularTextView;
import com.forumias.beta.R;
import com.forumias.beta.ui.deta.forumias.channel.MyChannelPostActivity;

import java.util.Arrays;
import java.util.List;

public class ChannelPagingAdapter extends PagedListAdapter<ChannelModel.ChannelList,ChannelPagingAdapter.ViewHolder> {
    private Context context;
    private ChannelSubscribeInterface channelSubscribeInterface;
    private int loginUserId;
    private int tabPosition;
    private int itemCount , darkModeStatus;
    public ChannelPagingAdapter(Context context , ChannelSubscribeInterface channelSubscribeInterface , int tabPosition) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.channelSubscribeInterface = channelSubscribeInterface;
        this.tabPosition = tabPosition;
        MyPreferenceData myPreferenceData = new MyPreferenceData(context);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.channels_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChannelModel.ChannelList channelModel = getItem(position);

        Log.e("channel===11111==> " ,channelModel.getTitle());

        if(darkModeStatus == 1){
            holder.llChannelAdapter.setBackgroundResource(R.drawable.dark_post_border);
            holder.tvName.setTextColor(ContextCompat.getColor(context , R.color.light_white));
            holder.tvFollowerCount.setTextColor(ContextCompat.getColor(context , R.color.light_white));
            holder.tvPostCount.setTextColor(ContextCompat.getColor(context , R.color.light_white));
            holder.tvNameMy.setTextColor(ContextCompat.getColor(context , R.color.light_white));
            holder.tvPostCountMy.setTextColor(ContextCompat.getColor(context , R.color.light_white));
            holder.tvFollowerCountMy.setTextColor(ContextCompat.getColor(context , R.color.light_white));
        }else{
            holder.llChannelAdapter.setBackgroundResource(R.color.white);
            holder.tvFollowerCount.setTextColor(ContextCompat.getColor(context , R.color.black_light));
            holder.tvPostCount.setTextColor(ContextCompat.getColor(context , R.color.black_light));
            holder.tvName.setTextColor(ContextCompat.getColor(context , R.color.black_light));
            holder.tvNameMy.setTextColor(ContextCompat.getColor(context , R.color.black_light));
            holder.tvPostCountMy.setTextColor(ContextCompat.getColor(context , R.color.black_light));
            holder.tvFollowerCountMy.setTextColor(ContextCompat.getColor(context , R.color.black_light));
        }

        holder.tvName.setText(channelModel.getTitle());
        holder.tvPostCount.setText(String.valueOf(channelModel.getPostCount()));
        holder.tvChannelDescreption.setText(Html.fromHtml(channelModel.getDescription()));

        holder.tvNameMy.setText(channelModel.getTitle());
        holder.tvPostCountMy.setText(String.valueOf(channelModel.getPostCount()));
      //  holder.tvFollowerCountMy.setText(Html.fromHtml(channelModel.getDescription()));



      /*  if(channelModel.getFollowRequest() != null){
            holder.tvSubscribed.setVisibility(View.GONE);
            holder.tvUnSubscribed.setVisibility(View.VISIBLE);
            holder.tvUnSubscribed.setText("Requested");
        }else {
            String subscribeBy = channelModel.getFollowInfo().getFollowBy();
            String[] followUserCount = subscribeBy.split(",");
            int itemCount = followUserCount.length;
            List<String> followList = Arrays.asList(followUserCount);*/

            switch (tabPosition){
                case 2:
                case 4:
                    holder.llMyChannelSection.setVisibility(View.VISIBLE);
                    holder.tvSubscribed.setVisibility(View.GONE);
                    holder.llUnSubscribed.setVisibility(View.GONE);

                    if(channelModel.getFollowInfo() != null) {
                        String subscribeBy = channelModel.getFollowInfo().getFollowBy();
                        String[] followUserCount = subscribeBy.split(",");
                        itemCount = followUserCount.length;
                        List<String> followList = Arrays.asList(followUserCount);
                        if (followList.contains(String.valueOf(loginUserId))) {
                            holder.tvSubscribed.setVisibility(View.GONE);
                            holder.llUnSubscribed.setVisibility(View.VISIBLE);
                        } else {
                            if (channelModel.getSubscribeStatus() == 1) {
                                holder.tvSubscribed.setVisibility(View.GONE);
                                holder.llUnSubscribed.setVisibility(View.VISIBLE);
                            } else {
                                holder.tvSubscribed.setVisibility(View.VISIBLE);
                                holder.llUnSubscribed.setVisibility(View.GONE);
                            }
                        }

                    }
                    break;
                case 0:
                    holder.llMyChannelSection.setVisibility(View.GONE);
                    holder.llChannelSection.setVisibility(View.VISIBLE);
                        Log.e("data ==> " , "Subcribed");
                        if(channelModel.getFollowInfo() != null) {
                            String subscribeBy = channelModel.getFollowInfo().getFollowBy();
                            String[] followUserCount = subscribeBy.split(",");
                            itemCount = followUserCount.length;
                            List<String> followList = Arrays.asList(followUserCount);
                            if (followList.contains(String.valueOf(loginUserId))) {
                                holder.tvSubscribed.setVisibility(View.GONE);
                                holder.llUnSubscribed.setVisibility(View.VISIBLE);
                            } else {
                                if (channelModel.getSubscribeStatus() == 1) {
                                    holder.tvSubscribed.setVisibility(View.GONE);
                                    holder.llUnSubscribed.setVisibility(View.VISIBLE);
                                } else {
                                    holder.tvSubscribed.setVisibility(View.VISIBLE);
                                    holder.llUnSubscribed.setVisibility(View.GONE);
                                }
                            }

                        }
                    break;
                case 1:
                case 3:
                    holder.llMyChannelSection.setVisibility(View.GONE);
                    holder.llChannelSection.setVisibility(View.VISIBLE);
                    if(channelModel.getFollowRequest() != null){
                        holder.tvSubscribed.setVisibility(View.GONE);
                        holder.llUnSubscribed.setVisibility(View.VISIBLE);
                        holder.tvUnSubscribed.setVisibility(View.VISIBLE);
                        holder.tvUnSubscribed.setText("Requested");
                        Log.e("data ==> " , "Request");
                    }else {
                        Log.e("data ==> " , "Subcribed");
                        String subscribeBy = channelModel.getFollowInfo().getFollowBy();
                        String[] followUserCount = subscribeBy.split(",");
                        itemCount = followUserCount.length;
                        List<String> followList = Arrays.asList(followUserCount);
                        if (followList.contains(String.valueOf(loginUserId))) {
                            holder.tvSubscribed.setVisibility(View.GONE);
                            holder.llUnSubscribed.setVisibility(View.VISIBLE);
                        } else {
                            if (channelModel.getSubscribeStatus() == 1) {
                                holder.tvSubscribed.setVisibility(View.GONE);
                                holder.llUnSubscribed.setVisibility(View.VISIBLE);
                            } else {
                                holder.tvSubscribed.setVisibility(View.VISIBLE);
                                holder.llUnSubscribed.setVisibility(View.GONE);
                            }
                        }
                    }
                break;
            }

         /*   if((tabPosition == 2)) { // Tagpos 2 means my channel
                holder.tvSubscribed.setVisibility(View.GONE);
                holder.tvUnSubscribed.setVisibility(View.GONE);

            } else{

                if (followList.contains(String.valueOf(loginUserId))) {
                    holder.tvSubscribed.setVisibility(View.GONE);
                    holder.tvUnSubscribed.setVisibility(View.VISIBLE);
                } else {
                    if (channelModel.getSubscribeStatus() == 1) {
                        holder.tvSubscribed.setVisibility(View.GONE);
                        holder.tvUnSubscribed.setVisibility(View.VISIBLE);
                    } else {
                        holder.tvSubscribed.setVisibility(View.VISIBLE);
                        holder.tvUnSubscribed.setVisibility(View.GONE);
                    }
                }

            }*/

            holder.tvFollowerCount.setText(String.valueOf(itemCount));
            holder.tvFollowerCountMy.setText(String.valueOf(itemCount));
       // }

  /*      if (!Utility.isNullOrEmpty(channelModel.getChannelImg())) {
            holder.tvChannelFirstText.setVisibility(View.GONE);
            holder.ivChannelImage.setVisibility(View.VISIBLE);
            Glide.with(context).load(BaseUrl.PAGE_IMAGE_URL + channelModel.getChannelImg()).into(holder.ivChannelImage);
        } else {
            holder.tvChannelFirstText.setVisibility(View.VISIBLE);
            holder.tvChannelFirstText.setStrokeWidth(1);
            holder.tvChannelFirstText.setSolidColor(channelModel.getColorCode());
            char firstText = channelModel.getTitle().charAt(0);
            holder.tvChannelFirstText.setText(String.valueOf(firstText));
        }*/


        holder.llChannelSection.setOnClickListener(view -> {

            switch (tabPosition){
                case 2:
                    String subscribeBy = channelModel.getFollowInfo().getFollowBy();
                    String[] followUserCount = subscribeBy.split(",");
                    List<String> followList = Arrays.asList(followUserCount);
                    if(followList.contains(String.valueOf(loginUserId))){
                        Intent intent = new Intent(context, MyChannelPostActivity.class);
                        intent.putExtra(BaseUrl.SLUG,channelModel.getChannelSlug());
                        intent.putExtra(BaseUrl.USER_ID,channelModel.getUserId());
                        context.startActivity(intent);
                    }else{
                        Toast.makeText(context,"You do not have permission to access this channel.",Toast.LENGTH_LONG).show();
                    }
                    break;
                case 0:
                case 1:
                case 3:
                case 4:
                    Intent intent = new Intent(context, MyChannelPostActivity.class);
                    intent.putExtra(BaseUrl.SLUG,channelModel.getChannelSlug());
                    intent.putExtra(BaseUrl.USER_ID,channelModel.getUserId());
                    context.startActivity(intent);
                    break;
            }

        });


        holder.llMyChannelSection.setOnClickListener(view -> {

            switch (tabPosition){
                case 2:
                    String subscribeBy = channelModel.getFollowInfo().getFollowBy();
                    String[] followUserCount = subscribeBy.split(",");
                    List<String> followList = Arrays.asList(followUserCount);
                    if(followList.contains(String.valueOf(loginUserId))){
                        Intent intent = new Intent(context, MyChannelPostActivity.class);
                        intent.putExtra(BaseUrl.SLUG,channelModel.getChannelSlug());
                        intent.putExtra(BaseUrl.USER_ID,channelModel.getUserId());
                        context.startActivity(intent);
                    }else{
                        Toast.makeText(context,"You do not have permission to access this channel.",Toast.LENGTH_LONG).show();
                    }
                    break;

                case 4:
                    Intent intent = new Intent(context, MyChannelPostActivity.class);
                    intent.putExtra(BaseUrl.SLUG,channelModel.getChannelSlug());
                    intent.putExtra(BaseUrl.USER_ID,channelModel.getUserId());
                    context.startActivity(intent);
                    break;
            }

        });


        holder.tvSubscribed.setOnClickListener(view ->{

            switch (tabPosition){
                case 1:
                case 3:
                    Log.e("data==", "Click  1");
                    if(channelModel.getFollowRequest() != null) {
                        Log.e("data==", "Click  3");
                        channelSubscribeInterface.privateChannelSubscribe(channelModel, loginUserId, channelModel.getId()
                                ,2,
                                holder.tvSubscribed, holder.llUnSubscribed,holder.tvUnSubscribed, position);
                    }else{
                        Log.e("data==", "Click  4");
                        channelSubscribeInterface.privateChannelSubscribe(channelModel, loginUserId, channelModel.getId()
                                ,1,
                                holder.tvSubscribed, holder.llUnSubscribed ,holder.tvUnSubscribed, position);
                    }
                    break;

                case 0:
                    channelSubscribeInterface.channelSubscribe(channelModel ,String.valueOf(loginUserId), String.valueOf(channelModel.getId())
                            , String.valueOf(BaseUrl.SUBSCRIBED_STATUS), String.valueOf(BaseUrl.SUBSCRIBED_ACT_TYPE),
                            holder.tvSubscribed, holder.llUnSubscribed ,holder.tvUnSubscribed, position);
                    break;
            }

        });
        holder.llUnSubscribed.setOnClickListener(view ->{

                switch (tabPosition){
                    case 1:
                    case 3:
                        if(channelModel.getFollowRequest() != null) {
                            Log.e("data==", "Click  3");
                            channelSubscribeInterface.privateChannelSubscribe(channelModel, loginUserId, channelModel.getId()
                                    ,2,
                                    holder.tvSubscribed, holder.llUnSubscribed ,holder.tvUnSubscribed, position);
                        }else{
                            Log.e("data==", "Click  4");
                            channelSubscribeInterface.privateChannelSubscribe(channelModel, loginUserId, channelModel.getId()
                                    ,1,
                                    holder.tvSubscribed, holder.llUnSubscribed , holder.tvUnSubscribed, position);
                        }
                        break;
                    case 0:
                        channelSubscribeInterface.channelSubscribe(channelModel,String.valueOf(loginUserId), String.valueOf(channelModel.getId())
                                , String.valueOf(BaseUrl.UNSUBSCRIBED_STATUS), String.valueOf(BaseUrl.SUBSCRIBED_ACT_TYPE),
                                holder.tvSubscribed, holder.llUnSubscribed, holder.tvUnSubscribed, position);
                        break;
                }

                });

    }


    private static DiffUtil.ItemCallback<ChannelModel.ChannelList>  DIFF_CALLBACK =
            new DiffUtil.ItemCallback<ChannelModel.ChannelList>() {
                @Override
                public boolean areItemsTheSame(@NonNull ChannelModel.ChannelList oldItem, @NonNull ChannelModel.ChannelList newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull ChannelModel.ChannelList oldItem, @NonNull ChannelModel.ChannelList newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvName, tvChannelDescreption, tvFollowerCount,
                 tvPostCount , tvUnSubscribed , tvNameMy , tvPostCountMy , tvFollowerCountMy;
        private CircularTextView tvChannelFirstText;
        private LinearLayoutCompat llChannelSection , llUnSubscribed ,tvSubscribed ,
                llChannelAdapter , llMyChannelSection;
        private ImageView ivChannelImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvChannelFirstText = itemView.findViewById(R.id.tvChannelFirstText);
            tvChannelDescreption = itemView.findViewById(R.id.tvChannelDescreption);
            ivChannelImage = itemView.findViewById(R.id.ivChannelImage);
            tvFollowerCount = itemView.findViewById(R.id.tvFollowerCount);
            llUnSubscribed = itemView.findViewById(R.id.llUnSubscribed);
            tvSubscribed = itemView.findViewById(R.id.llSubscribed);
            tvUnSubscribed = itemView.findViewById(R.id.tvUnSubscribed);
            tvPostCount = itemView.findViewById(R.id.tvPostCount);
            llChannelSection = itemView.findViewById(R.id.llChannelSection);
            llChannelAdapter = itemView.findViewById(R.id.llChannelAdapter);
            llMyChannelSection = itemView.findViewById(R.id.llMyChannelSection);
            tvNameMy = itemView.findViewById(R.id.tvNameMy);
            tvPostCountMy = itemView.findViewById(R.id.tvPostCountMy);
            tvFollowerCountMy = itemView.findViewById(R.id.tvFollowerCountMy);
        }
    }

}
