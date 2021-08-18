package com.forumias.beta.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.forumias.beta.utility.CircularTextView;
import com.forumias.beta.pojo.ChannelModel;
import com.forumias.beta.R;

import java.util.List;

public class MyChannelsAdapter extends RecyclerView.Adapter<MyChannelsAdapter.ViewHolder> {
    List<ChannelModel.ChannelList> channelList;
    Context context;
    public MyChannelsAdapter(List<ChannelModel.ChannelList> channelList , Context context){
        this.context = context;
        this.channelList = channelList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.channels_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(channelList.get(position).getChannelSlug());
        holder.tvChannelDescreption.setText(Html.fromHtml(channelList.get(position).getDescription()));
        if(channelList.get(position).getChannelImg() != null){
            holder.tvChannelFirstText.setVisibility(View.GONE);
            Glide.with(context).load(channelList.get(position)).into(holder.ivChannelImage);
        }else{
            holder.tvChannelFirstText.setVisibility(View.VISIBLE);
            holder.tvChannelFirstText.setStrokeWidth(1);
            holder.tvChannelFirstText.setSolidColor(channelList.get(position).getColorCode());
            holder.tvChannelFirstText.setText("T");
        }


    }

    @Override
    public int getItemCount() {
        return channelList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvName , tvChannelDescreption;
        private CircularTextView tvChannelFirstText;
        private ImageView ivChannelImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvChannelFirstText = itemView.findViewById(R.id.tvChannelFirstText);
            tvChannelDescreption = itemView.findViewById(R.id.tvChannelDescreption);
            ivChannelImage = itemView.findViewById(R.id.ivChannelImage);
        }
    }
}
