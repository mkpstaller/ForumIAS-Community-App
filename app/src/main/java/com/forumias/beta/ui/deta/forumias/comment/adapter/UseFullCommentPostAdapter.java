package com.forumias.beta.ui.deta.forumias.comment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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
import com.ahmadnemati.clickablewebview.listener.OnWebViewClicked;
import com.bumptech.glide.Glide;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.myinterface.ReplyInterface;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.ui.deta.forumias.comment.fragment.all_comment_paging.SaveCommentInterfacae;
import com.forumias.beta.ui.deta.forumias.comment.fragment.model.AllCommentModel;
import com.forumias.beta.utility.DateFormatUtils;
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UseFullCommentPostAdapter extends RecyclerView.Adapter<UseFullCommentPostAdapter.ViewHolder> {
    private List<AllCommentModel.Result.CommentList> commentLists;
    private Context context;
    private boolean viewReplyStatus = true;
    private ReplyInterface replyInterface;
    private int loginUserId , callClass , darkModeStatus;
    private SaveCommentInterfacae saveCommentInterfacae;

    public UseFullCommentPostAdapter(List<AllCommentModel.Result.CommentList> commentLists, Context context,
                                     ReplyInterface replyInterface , SaveCommentInterfacae saveCommentInterfacae ,int callClass) {
        this.context = context;
        this.commentLists = commentLists;
        this.replyInterface = replyInterface;
        this.saveCommentInterfacae = saveCommentInterfacae;
        this.callClass = callClass;
        try {
            MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(context));
            loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
            darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);

        }catch (Exception e){e.printStackTrace();}

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_on_post_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AllCommentModel.Result.CommentList commentList = commentLists.get(position);


        assert commentList != null;

        if(loginUserId != 0){
            holder.ivQuoteOnComment.setVisibility(View.VISIBLE);
            holder.ivReplyOnComment.setVisibility(View.VISIBLE);

        }else{
            holder.ivQuoteOnComment.setVisibility(View.GONE);
            holder.ivReplyOnComment.setVisibility(View.GONE);
        }

        holder.webView.setVerticalScrollBarEnabled(true);

        if(darkModeStatus == 1) {
            new Utility().showAllCommentDarkMode(holder.webView, commentList.getDescription());
            new Utility().showAllCommentDarkMode(holder.clickable_webview, commentList.getDescription());
            holder.llCommentSection.setBackground(ContextCompat.getDrawable(context,R.drawable.dark_post_border));
            //  holder.ivUpVote.setBackground(ContextCompat.getDrawable(context,R.drawable.id_upvote));
            holder.ivReplyOnComment.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_reply_dark));
            holder.ivQuoteOnComment.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_qoute_dark));
            holder.tvCommentUserName.setTextColor(ContextCompat.getColor(context,R.color.white));
            holder.commentView.setBackgroundColor(ContextCompat.getColor(context,R.color.black_light));

        }else{
            new Utility().showAllComment(holder.webView, commentList.getDescription());
            new Utility().showAllComment(holder.clickable_webview, commentList.getDescription());
            holder.llCommentSection.setBackground(ContextCompat.getDrawable(context,R.drawable.post_border));
            holder.tvCommentUserName.setTextColor(ContextCompat.getColor(context,R.color.black));
            // holder.ivUpVote.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_upvote_dark));
            holder.ivReplyOnComment.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_reply));
            holder.ivQuoteOnComment.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_quote));
        }

       // new Utility().showAllComment(holder.webView, commentList.getDescription());
        holder.tvDate.setText("commented " + new DateFormatUtils().showPostDate(commentList.getCreated_at()));

        if(commentList.getSaveComments().size() > 0){
            holder.ivSaveComment.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_yellow_star));
        }else{
            holder.ivSaveComment.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star));
        }

        if (commentList.getTagInfo() != null) {
            holder.tvCommentUserName.setText(commentList.getTagInfo().getTitle());
            Glide.with(context).load(BaseUrl.PAGE_IMAGE_URL + commentList.getTagInfo().getTag_img()).placeholder(R.drawable.user_placeholder).dontAnimate().into(holder.ivCommentUserImage);
        } else {
            if (commentList.getUserInfo() != null) {
                if (commentList.getUserInfo().getHide_real_name() == 1) {
                    holder.tvCommentUserName.setText(commentList.getUserInfo().getName());
                }
                Glide.with(context).load(commentList.getUserInfo().getImage()).placeholder(R.drawable.user_placeholder).dontAnimate().into(holder.ivCommentUserImage);
            }
        }
        if (commentList.getUpVotes() != null) {
            String upVoteUser = commentList.getUpVotes().getUser_ids();
            Log.e("upVote id == > " , upVoteUser);
            String[] userIds = upVoteUser.split(",");
            List<String> userIdList = Arrays.asList(userIds);
            if(userIdList.contains(String.valueOf(loginUserId))){
                holder.ivUpVote.setImageDrawable(context.getResources().getDrawable(R.drawable.id_upvote_blue));
            }else{
                holder.ivUpVote.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_upvote_dark));
            }
            holder.tvUpVoters.setText(String.valueOf(commentList.getUpVotes().getUpvote_count()));
        }else{
            holder.ivUpVote.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_upvote_dark));
                holder.tvUpVoters.setText("0");

        }


        holder.llViewReplySection.setVisibility(View.GONE);


        holder.ivReplyOnComment.setOnClickListener(v -> {
            if (loginUserId != 0) {
                if (commentList.getUserInfo() != null) {
                    replyInterface.replyOnComment(commentList.getUserInfo().getName(),
                            commentList.getDescription() , callClass);
                } else {
                    if (commentList.getTagInfo() != null) {
                        replyInterface.replyOnComment(commentList.getTagInfo().getTitle(),
                                commentList.getDescription(),callClass);
                    }
                }
            } else {
                new MyUtility().showLoginAlert(context);
            }
        });
        holder.ivQuoteOnComment.setOnClickListener(v -> {

            if (loginUserId != 0) {
                if (commentList.getUserInfo() != null) {
                    replyInterface.quoteOnComment(commentList.getUserInfo().getName(),
                            commentList.getDescription(),callClass);
                } else {
                    if (commentList.getTagInfo() != null) {
                        replyInterface.quoteOnComment(commentList.getUserInfo().getName(),
                                commentList.getDescription(),callClass);
                    }
                }
            } else {
                new MyUtility().showLoginAlert(context);
            }
        });

        holder.llUpVoteSection.setOnClickListener(view -> {
            if(loginUserId != 0) {
                replyInterface.upVoteOnComment(commentList.getPost_id(), loginUserId, commentList.getId()
                        , holder.ivUpVote, holder.tvUpVoters, holder.llUpVoteSection);
            }else{
                new MyUtility().showLoginAlert(context);
            }

        });

        holder.ivShare.setOnClickListener(v -> {
            replyInterface.shareComment(commentList.getId());
        });

        holder.ivSaveComment.setOnClickListener(view -> {
            if(commentList.getSaveComments().size() > 0){
                Log.e("click delete" , "okokokok");
                saveCommentInterfacae.saveComment(loginUserId , commentList.getSaveComments().get(0).getId() ,0
                        ,0 , 1 , holder.ivSaveComment , true);
            }else{
                Log.e("click save" , "okokokok");
                saveCommentInterfacae.saveComment(loginUserId , 0 ,commentList.getPost_id()
                        ,commentList.getId() , 0 , holder.ivSaveComment , false);
            }
        });

        holder.clickable_webview.setOnWebViewClickListener(new OnWebViewClicked() {
            @Override
            public void onClick(String url) {
                replyInterface.showImageView(url);
            }
        });


    }

    @Override
    public int getItemCount() {
        return commentLists.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvCommentUserName, tvReplyCount, tvUpVoters, tvDate , tvViewCountSecond;
        private ImageView ivCommentUserImage;
        private RecyclerView recyclerView;
        private AppCompatImageView ivReplyOnComment, ivQuoteOnComment, ivShare , ivSaveComment,ivUpVote;
        private LinearLayoutCompat llViewReplySection , llUpVoteSection , llCommentSection;
        private WebView webView;
        private View commentView;
        ClickableWebView clickable_webview;

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
            tvViewCountSecond = itemView.findViewById(R.id.tvViewCountSecond);
            clickable_webview = itemView.findViewById(R.id.clickable_webview);
        }
    }
}
