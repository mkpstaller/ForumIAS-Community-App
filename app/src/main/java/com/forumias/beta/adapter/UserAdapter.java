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
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.ui.deta.forumias.user.UserInterface;
import com.forumias.beta.ui.deta.forumias.user.user_paging.UserModel;
import com.forumias.beta.R;

import java.util.List;
import java.util.Objects;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<UserModel.UserListing> userList;
    private Context context;
    private UserInterface userInterface;
    private int loginUserId;
    public  UserAdapter(List<UserModel.UserListing> userList, Context context , UserInterface userInterface){
        this.context = context;
        this.userList = userList;
        this.userInterface = userInterface;
        MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(context));
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(userList.get(position).getName());
        holder.tvDate.setText(userList.get(position).getCreatedAt());
        if(userList.get(position).getAbout() != null) {
            holder.tvAbout.setVisibility(View.VISIBLE);
            holder.tvAbout.setText(Html.fromHtml(userList.get(position).getAbout()));
        }else{
            holder.tvAbout.setVisibility(View.GONE);
        }
        if(loginUserId != 0) {
            if (userList.get(position).getFollowInfo() != null) {
                holder.tvFollowing.setVisibility(View.VISIBLE);
                holder.tvFollow.setVisibility(View.GONE);
            } else {
                holder.tvFollowing.setVisibility(View.GONE);
                holder.tvFollow.setVisibility(View.VISIBLE);
            }
        }else{
            holder.tvFollowing.setVisibility(View.GONE);
            holder.tvFollow.setVisibility(View.VISIBLE);
        }
        Glide.with(context).load(userList.get(position).getImage()).into(holder.ivUserImage);

        holder.ivUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInterface.UserInformation(userList.get(position).getName() , userList.get(position).getAbout()
                        , userList.get(position).getImage() , userList.get(position).getCreatedAt());
            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvName ,tvAbout , tvFollow , tvFollowing , tvDate;
        private ImageView ivUserImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAbout = itemView.findViewById(R.id.tvAbout);
            ivUserImage = itemView.findViewById(R.id.ivUserImage);
            tvFollowing = itemView.findViewById(R.id.tvFollowing);
            tvFollow = itemView.findViewById(R.id.tvFollow);
            tvDate = itemView.findViewById(R.id.tvDate);

        }
    }

}
