package com.forumias.beta.ui.deta.forumias.deep_link.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadnemati.clickablewebview.ClickableWebView;
import com.bumptech.glide.Glide;
import com.forumias.beta.R;
import com.forumias.beta.ui.deta.forumias.deep_link.DeepLinkActivity;
import com.forumias.beta.ui.deta.forumias.deep_link.model.DeepCommentModel;
import com.forumias.beta.utility.DateFormatUtils;
import com.forumias.beta.utility.Utility;

import java.util.List;

public class CommentReplyAdapter extends RecyclerView.Adapter<CommentReplyAdapter.ViewHolder> {
    Context context;
    List<DeepCommentModel.SubCommentsData> subComments;

    public CommentReplyAdapter(Context context, List<DeepCommentModel.SubCommentsData> subComments) {
        this.context = context;
        this.subComments = subComments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_on_post_layout ,parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(subComments.get(position).user_info.image).into(holder.ivCommentUserImage);
        holder.tvCommentUserName.setText(subComments.get(position).user_info.full_name);
        new Utility().showAllComment(holder.clickable_webview, subComments.get(position).description);
        holder.tvDate.setText("commented " + new DateFormatUtils().showPostDate(subComments.get(position).description));
        holder.llUpVoteSection.setVisibility(View.GONE);
        holder.tvViewCountSecond.setText(new Utility().prettyCount(subComments.get(position).views));
    }

    @Override
    public int getItemCount() {
        return subComments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ClickableWebView clickable_webview;
        ImageView ivCommentUserImage;
        AppCompatTextView tvCommentUserName , tvDate , tvViewCountSecond;
        LinearLayoutCompat llUpVoteSection;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clickable_webview = itemView.findViewById(R.id.clickable_webview);
            ivCommentUserImage = itemView.findViewById(R.id.ivCommentUserImage);
            tvCommentUserName = itemView.findViewById(R.id.tvCommentUserName);
            tvDate = itemView.findViewById(R.id.tvDate);
            llUpVoteSection = itemView.findViewById(R.id.llUpVoteSection);
            tvViewCountSecond = itemView.findViewById(R.id.tvViewCountSecond);
        }
    }
}
