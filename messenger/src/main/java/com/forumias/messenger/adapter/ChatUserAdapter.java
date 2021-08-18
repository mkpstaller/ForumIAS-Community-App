package com.forumias.messenger.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.forumias.messenger.R;
import com.forumias.messenger.ui.ChatActivity;

import java.util.List;

public class ChatUserAdapter extends RecyclerView.Adapter<ChatUserAdapter.ViewHolder> {

    private List<UserPojo> chatUserList;
    private Context context;

    public ChatUserAdapter(Context context , List<UserPojo> chatUserList){
        this.context = context;
        this.chatUserList = chatUserList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_chat_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvChatUserName.setText(chatUserList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return chatUserList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView tvChatUserName;
        public LinearLayoutCompat llUserSection;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChatUserName = itemView.findViewById(R.id.tvChatUserName);
            llUserSection = itemView.findViewById(R.id.llUserSection);

            llUserSection.setOnClickListener(v -> {
                 Intent intent = new Intent(context, ChatActivity.class);
                 context.startActivity(intent);

            });
        }
    }
}
