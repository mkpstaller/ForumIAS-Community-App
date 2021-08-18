package com.forumias.beta.ui.deta.forumias.comment.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.forumias.beta.pojo.PostDetailsModel;
import com.forumias.beta.R;

import java.util.List;

public class CommentOnReplyAdapter extends RecyclerView.Adapter<CommentOnReplyAdapter.ViewHolder> {
    List<PostDetailsModel.CommentListing.SubCommentInfo> commentOnReplyList;
    Context context;
    public CommentOnReplyAdapter(List<PostDetailsModel.CommentListing.SubCommentInfo> commentOnReplyList , Context context){
        this.context = context;
        this.commentOnReplyList = commentOnReplyList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_on_reply_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvReplyText.setText(Html.fromHtml(commentOnReplyList.get(position).getDescription()));
    }

    @Override
    public int getItemCount() {
        return commentOnReplyList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvReplyUserName , tvReplyText;
        private ImageView ivReplyUserImage;
        private RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReplyUserName = itemView.findViewById(R.id.tvReplyUserName);
            ivReplyUserImage = itemView.findViewById(R.id.ivReplyUserImage);
            tvReplyText = itemView.findViewById(R.id.tvReplyText);

        }
    }
}
