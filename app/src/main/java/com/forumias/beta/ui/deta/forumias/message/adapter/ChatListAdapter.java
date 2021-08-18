package com.forumias.beta.ui.deta.forumias.message.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.forumias.beta.R;
import com.forumias.beta.ui.deta.forumias.message.model.ChatListModel;
import com.forumias.beta.ui.deta.forumias.message.view.UserChatPage;
import com.forumias.beta.utility.DateFormatUtils;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private Context  context;
    private List<ChatListModel.chatHistory> chatHistoty;
    private String name ;
    private String image;
    public ChatListAdapter(Context  context,
                           List<ChatListModel.chatHistory> chatHistoty , String name , String image) {
        this.chatHistoty = chatHistoty;
        this.context = context;
        this.image = image;
        this.name = name;
    }

    @NonNull
    @Override
    public ChatListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_items ,parent ,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.ViewHolder holder, int position) {
       Log.e("message====> ",chatHistoty.get(position).getMessage());
            if(chatHistoty.get(position).getFrom_user() != 164){
                holder.tvFromMessage.setText(chatHistoty.get(position).getMessage());
                String strDateTime = new DateFormatUtils().showPostDate(chatHistoty.get(position).getCreated_at());
                holder.tvFromDate.setText(strDateTime);
                holder.toSection.setVisibility(View.GONE);
                holder.fromSection.setVisibility(View.VISIBLE);
                Glide.with(context).load(image).into(holder.ivUserImage);
            }else {

                holder.toSection.setVisibility(View.VISIBLE);
                holder.fromSection.setVisibility(View.GONE);
                String strDateTime = new DateFormatUtils().showPostDate(chatHistoty.get(position).getCreated_at());
                holder.tvToDate.setText(strDateTime);
                holder.tvToMessage.setText(chatHistoty.get(position).getMessage());
            }
    }

    @Override
    public int getItemCount() {
        return chatHistoty.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{
        private AppCompatTextView tvToMessage , tvFromMessage,  tvToDate , tvFromDate;
        private LinearLayoutCompat toSection, fromSection;
        private ImageView  ivUserImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFromMessage = itemView.findViewById(R.id.tvFromMessage);
            tvToMessage = itemView.findViewById(R.id.tvToMessage);
            toSection = itemView.findViewById(R.id.toSection);
            fromSection = itemView.findViewById(R.id.fromSection);
            ivUserImage = itemView.findViewById(R.id.ivUserImage);
            tvToDate = itemView.findViewById(R.id.tvToDate);
            tvFromDate = itemView.findViewById(R.id.tvFromDate);
        }
    }
}
