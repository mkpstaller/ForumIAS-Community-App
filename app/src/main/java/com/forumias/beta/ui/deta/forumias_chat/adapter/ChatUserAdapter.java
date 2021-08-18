package com.forumias.beta.ui.deta.forumias_chat.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.UserPojo;
import com.forumias.beta.R;
import com.forumias.beta.ui.deta.forumias_chat.ui.UserChatActivity;

import java.util.List;

public class ChatUserAdapter extends RecyclerView.Adapter<ChatUserAdapter.ViewHolder> {

    private List<UserPojo> chatUserList;
    private Context context;
    private int darkModeStatus;

    public ChatUserAdapter(Context context , List<UserPojo> chatUserList){
        this.context = context;
        this.chatUserList = chatUserList;

        MyPreferenceData myPreferenceData = new MyPreferenceData(context);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_chat_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(darkModeStatus == 1){
            holder.tvChatUserName.setTextColor(ContextCompat.getColor(context,R.color.light_white));
            holder.tvShowCurrentChat.setTextColor(ContextCompat.getColor(context,R.color.light_white));
            holder.tvChatDateTime.setTextColor(ContextCompat.getColor(context,R.color.light_white));
            holder.view.setBackgroundColor(ContextCompat.getColor(context,R.color.gray_dark));
        }else{
            holder.tvChatUserName.setTextColor(ContextCompat.getColor(context,R.color.black));
            holder.tvShowCurrentChat.setTextColor(ContextCompat.getColor(context,R.color.black));
            holder.tvChatDateTime.setTextColor(ContextCompat.getColor(context,R.color.black));
            holder.view.setBackgroundColor(ContextCompat.getColor(context,R.color.gray));
        }


        holder.tvChatUserName.setText(chatUserList.get(position).getName());
        holder.llUserSection.setOnClickListener(view -> {
            Intent intent = new Intent(context, UserChatActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return chatUserList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView tvChatUserName , tvShowCurrentChat ,tvChatDateTime;
        public LinearLayoutCompat llUserSection;
        private View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChatUserName = itemView.findViewById(R.id.tvChatUserName);
            tvShowCurrentChat = itemView.findViewById(R.id.tvShowCurrentChat);
            llUserSection = itemView.findViewById(R.id.llUserSection);
            tvChatDateTime = itemView.findViewById(R.id.tvChatDateTime);
            view = itemView.findViewById(R.id.view);

        }
    }
}
