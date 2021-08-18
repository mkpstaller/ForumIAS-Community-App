package com.forumias.beta.ui.deta.forumias.user.user_profile_and_post;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadnemati.clickablewebview.ClickableWebView;
import com.bumptech.glide.Glide;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.ui.deta.forumias.comment.fragment.all_comment_paging.SaveCommentInterfacae;
import com.forumias.beta.utility.DateFormatUtils;
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;

import java.util.List;
import java.util.Objects;

public class UserAllCommentAdapter extends RecyclerView.Adapter<UserAllCommentAdapter.ViewHolder> {
    private Context context;
    private List<UserCommentModel.UserCommentInfo> userCommentList;
    private int isVerified , darkModeStatus,loginUserId;
    private SaveCommentInterfacae saveCommentInterfacae;
    public UserAllCommentAdapter(List<UserCommentModel.UserCommentInfo> userCommentInfo,
                                 Context context ,int isVerified , SaveCommentInterfacae saveCommentInterfacae) {
        this.context = context;
        this.userCommentList = userCommentInfo;
        this.isVerified = isVerified;
        this.saveCommentInterfacae = saveCommentInterfacae;
        MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(context));
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_on_post_layout , parent , false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.llCommentBottom.setVisibility(View.VISIBLE);
        holder.commentView.setVisibility(View.GONE);
        holder.ivUpVote.setVisibility(View.GONE);
        holder.ivQuoteOnComment.setVisibility(View.GONE);
        holder.ivReplyOnComment.setVisibility(View.GONE);
        holder.llUpVoteSection.setVisibility(View.GONE);

        if(darkModeStatus == 1) {
            new Utility().showAllCommentDarkMode(holder.clickable_webview, userCommentList.get(position).getDescription());
            holder.llCommentSection.setBackground(ContextCompat.getDrawable(context,R.drawable.dark_post_border));
            //  holder.ivUpVote.setBackground(ContextCompat.getDrawable(context,R.drawable.id_upvote));
            holder.ivReplyOnComment.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_reply_dark));
            holder.ivQuoteOnComment.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_qoute_dark));
            holder.tvCommentUserName.setTextColor(ContextCompat.getColor(context,R.color.white));
            holder.commentView.setBackgroundColor(ContextCompat.getColor(context,R.color.black_light));

        }else{
            new Utility().showAllComment(holder.clickable_webview, userCommentList.get(position).getDescription());
            holder.llCommentSection.setBackground(ContextCompat.getDrawable(context,R.drawable.post_border));
            holder.tvCommentUserName.setTextColor(ContextCompat.getColor(context,R.color.black));
            // holder.ivUpVote.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_upvote_dark));
            holder.ivReplyOnComment.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_reply));
            holder.ivQuoteOnComment.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_quote));
        }

        holder.tvDate.setText("commented " + new DateFormatUtils().showPostDate(userCommentList.get(position).getCreated_at()));
        holder.tvViewCountSecond.setText(new Utility().prettyCount(userCommentList.get(position).getViews()));

        if (userCommentList.get(position).getUserInfo() != null) {
            if (userCommentList.get(position).getUserInfo().getHide_real_name() == 1) {
                holder.tvCommentUserName.setText(userCommentList.get(position).getUserInfo().getName());
            }
            Glide.with(context).load(userCommentList.get(position).getUserInfo().getImage()).placeholder(R.drawable.user_placeholder).dontAnimate().into(holder.ivCommentUserImage);
        }

        if(userCommentList.get(position).getSoft_delete() == 0){
            holder.tvDeletedPost.setVisibility(View.GONE);
        }else{
            holder.tvDeletedPost.setVisibility(View.VISIBLE);
        }


        holder.ivShare.setOnClickListener(v -> {
            saveCommentInterfacae.shareComment(userCommentList.get(position).getId());
        });


        holder.ivSaveComment.setOnClickListener(view -> {
          if (loginUserId != 0) {
                if (userCommentList.get(position).getSaveComment().size() > 0) {
                    saveCommentInterfacae.saveComment(loginUserId, userCommentList.get(position).getSaveComment().get(0).getId(), 0
                            , 0, 1, holder.ivSaveComment, false);
                } else {
                    saveCommentInterfacae.saveComment(loginUserId, 0, userCommentList.get(position).getPost_id()
                            , userCommentList.get(position).getId(), 0, holder.ivSaveComment, true);
                }
            }else{
                new MyUtility().showLoginAlert(context);
            }

        });
    }

    @Override
    public int getItemCount() {
        return userCommentList.size();
    }

    protected  class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvCommentUserName, tvReplyCount, tvUpVoters,
                tvDate , tvViewCountSecond , tvDeletedPost;
        private ImageView ivCommentUserImage;
        private RecyclerView recyclerView;
        private AppCompatImageView ivReplyOnComment, ivQuoteOnComment, ivShare , ivSaveComment,ivUpVote;
        private LinearLayoutCompat llViewReplySection , llUpVoteSection , llCommentSection , llCommentBottom;
        private WebView webView;
        private View commentView;
        private ClickableWebView clickable_webview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCommentUserName = itemView.findViewById(R.id.tvCommentUserName);
            webView = itemView.findViewById(R.id.webView);
            ivCommentUserImage = itemView.findViewById(R.id.ivCommentUserImage);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            llViewReplySection = itemView.findViewById(R.id.llViewReplySection);
            tvReplyCount = itemView.findViewById(R.id.tvReplyCount);
            ivReplyOnComment = itemView.findViewById(R.id.ivReplyOnComment);
            ivReplyOnComment = itemView.findViewById(R.id.ivReplyOnComment);
            ivQuoteOnComment = itemView.findViewById(R.id.ivQuoteOnComment);
            tvUpVoters = itemView.findViewById(R.id.tvUpVoters);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivShare = itemView.findViewById(R.id.ivShare);
            ivSaveComment = itemView.findViewById(R.id.ivSaveComment);
            ivUpVote = itemView.findViewById(R.id.ivUpVote);
            llUpVoteSection = itemView.findViewById(R.id.llUpVoteSection);
            llCommentSection = itemView.findViewById(R.id.llCommentSection);
            commentView = itemView.findViewById(R.id.commentView);
            llCommentBottom = itemView.findViewById(R.id.llCommentBottom);
            tvViewCountSecond = itemView.findViewById(R.id.tvViewCountSecond);
            clickable_webview = itemView.findViewById(R.id.clickable_webview);
            tvDeletedPost = itemView.findViewById(R.id.tvDeletedPost);
        }
    }
}
