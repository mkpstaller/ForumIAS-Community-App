package com.forumias.beta.ui.deta.forumias.group.group_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.forumias.beta.pojo.DefaultPojo;
import com.forumias.beta.R;

import java.util.List;

public class GroupMemberAdapter extends RecyclerView.Adapter<GroupMemberAdapter.ViewHolder> {
    private List<DefaultPojo> list;
    private Context context;
    private int tabPos;
    public GroupMemberAdapter(List<DefaultPojo> list , Context context , int tabPos){
        this.list = list;
        this.context = context;
        this.tabPos = tabPos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_member_list , parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tvName.setText(list.get(position).getMessage());
            if(tabPos ==1){
                holder.tvAdmin.setText(context.getString(R.string.accept));
                holder.tvRemove.setText(context.getString(R.string.reject));
            }else{
                holder.tvAdmin.setText(context.getString(R.string.admin));
                holder.tvRemove.setText(context.getString(R.string.remove));
            }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private  AppCompatTextView tvName , tvAdmin , tvRemove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAdmin = itemView.findViewById(R.id.tvAdmin);
            tvRemove = itemView.findViewById(R.id.tvRemove);
        }
    }
}
