package com.forumias.beta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.forumias.beta.myinterface.SelectUserInterface;
import com.forumias.beta.pojo.UserPojo;
import com.forumias.beta.R;

import java.util.List;

public class GroupSelectedAdapted extends RecyclerView.Adapter<GroupSelectedAdapted.ViewHolder> {
    public Context context;
    public List<UserPojo> groupList;
    public UserPojo userPojo;
    SelectUserInterface selectUserInterface;

    public GroupSelectedAdapted(Context context, List<UserPojo> groupList , SelectUserInterface selectUserInterface) {
        this.context = context;
        this.groupList = groupList;
        this.selectUserInterface = selectUserInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_selected_list, parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        userPojo = groupList.get(position);
        holder.tvSelectedUserName.setText(groupList.get(position).getName());
        holder.itemView.setTag(userPojo);
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatTextView tvSelectedUserName;
        public AppCompatImageView ivRemoveSelected;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSelectedUserName = itemView.findViewById(R.id.tvSelectedUserName);
            ivRemoveSelected = itemView.findViewById(R.id.ivRemoveSelected);

            ivRemoveSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserPojo userPojo = (UserPojo) itemView.getTag();
                    selectUserInterface.selectedUser(userPojo.getName() , 0);
                    if(groupList.size() >= 0){
                        groupList.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    }
                }
            });
        }
    }
}
