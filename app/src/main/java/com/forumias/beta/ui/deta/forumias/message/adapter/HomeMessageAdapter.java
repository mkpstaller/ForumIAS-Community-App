package com.forumias.beta.ui.deta.forumias.message.adapter;

import android.content.Context;
import android.content.Intent;
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

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.ui.deta.forumias.message.model.IndexMessageModel;
import com.forumias.beta.ui.deta.forumias.message.view.UserChatPage;
import com.forumias.beta.utility.DateFormatUtils;
import com.forumias.beta.utility.Utility;

import java.util.List;

public class HomeMessageAdapter extends RecyclerView.Adapter<HomeMessageAdapter.ViewHolder> {
    private Context context;
    private List<IndexMessageModel.Result> resultList;
    private int userId;
    private int toUser;
    public HomeMessageAdapter(Context context , List<IndexMessageModel.Result> resultList , int userId) {
        this.context = context;
        this.userId = userId;
        this.resultList = resultList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_message_list , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvMessage.setText(resultList.get(position).getMessage());
        String strDateTime = new DateFormatUtils().showPostDate(resultList.get(position).getCreated_at());
        holder.tvTime.setText(strDateTime);



        if(resultList.get(position).getTo_user() !=userId){
            holder.tvName.setText(resultList.get(position).getTo_name());
            Glide.with(context).load(resultList.get(position).getTo_u_img()).into(holder.ivImageUser);
            new Utility().showUserVerified(resultList.get(position).to_verified , holder.ivUserVerified);

        }else if(resultList.get(position).getFrom_user() !=userId){
            holder.tvName.setText(resultList.get(position).getFrm_name());
            Glide.with(context).load(resultList.get(position).getFrm_u_img()).into(holder.ivImageUser);
            new Utility().showUserVerified(resultList.get(position).frm_verified , holder.ivUserVerified);
        }

        holder.llUserChat.setOnClickListener(view -> {
            Intent intent = new Intent(context , UserChatPage.class);
            intent.putExtra(BaseUrl.CHAT_ID , resultList.get(position).chat_id);
            if(resultList.get(position).getTo_user() != userId){
                intent.putExtra(BaseUrl.CHAT_USER_ID , resultList.get(position).to_user);
                Log.e("to User ===> ",resultList.get(position).to_user+"");
                context.startActivity(intent);
            }else if(resultList.get(position).getFrom_user() != userId){

                intent.putExtra(BaseUrl.CHAT_USER_ID , resultList.get(position).from_user);
                Log.e("from User ===> ",resultList.get(position).from_user+"");
                context.startActivity(intent);
            }

                });

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayoutCompat llUserChat;
        AppCompatTextView tvMessage , tvName , tvTime;
        ImageView ivImageUser;
        AppCompatImageView ivUserVerified;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            llUserChat = itemView.findViewById(R.id.llUserChat);
            tvMessage = itemView.findViewById(R.id.tvMessage);
            tvName = itemView.findViewById(R.id.tvName);
            tvTime = itemView.findViewById(R.id.tvTime);
            ivImageUser = itemView.findViewById(R.id.ivImageUser);
            ivUserVerified = itemView.findViewById(R.id.ivUserVerified);

        }
    }
}
