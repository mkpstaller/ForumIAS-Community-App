package com.forumias.beta.ui.deta.forumias.comment.fragment.all_comment_paging;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadnemati.clickablewebview.ClickableWebView;
import com.ahmadnemati.clickablewebview.listener.OnWebViewClicked;
import com.bumptech.glide.Glide;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.myinterface.ReplyInterface;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.ui.deta.forumias.comment.fragment.model.AllCommentModel;
import com.forumias.beta.ui.login.LoginActivity;
import com.forumias.beta.utility.DateFormatUtils;
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.UserProfileAndPostActivity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CommentAdapter extends PagedListAdapter<AllCommentModel.Result.CommentList, CommentAdapter.ViewHolder> {
    private Context context;
    private ReplyInterface replyInterface;
    private int loginUserId , callClass , darkModeStatus , commentCount;
    private SaveCommentInterfacae saveCommentInterfacae;
    int totalComment = 0;
    int lastItemPosition = 0;
    AppCompatTextView tvTotalComment;
    MyPreferenceData myPreferenceData;

    public CommentAdapter(Context context, ReplyInterface replyInterface
            , SaveCommentInterfacae saveCommentInterfacae , int callClass , int totalComment ,AppCompatTextView tvTotalComment) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.replyInterface = replyInterface;
        this.saveCommentInterfacae = saveCommentInterfacae;
        this.callClass = callClass;
        this.totalComment = totalComment;
        this.commentCount = totalComment;
        this.tvTotalComment = tvTotalComment;
        myPreferenceData = new MyPreferenceData(Objects.requireNonNull(context));
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_on_post_layout, parent, false);
        return new CommentAdapter.ViewHolder(view);
    }


    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllCommentModel.Result.CommentList commentList = getItem(position);

        Log.e("pos number==> " , totalComment+"       "+position+"         "+commentCount);

        myPreferenceData.saveIntegerData(BaseUrl.COMMENT_POS , position);

        if (commentCount > position) {
            totalComment--;
            if(totalComment < 0) {
                tvTotalComment.setText(String.valueOf(0));
            }else{
                tvTotalComment.setText(String.valueOf(totalComment));
            }
        }
        else {
            totalComment++;
            tvTotalComment.setText(String.valueOf(totalComment));
        }
        lastItemPosition = position;



        assert commentList != null;
        if(darkModeStatus == 1) {
            new Utility().showAllCommentDarkMode(holder.clickable_webview, commentList.getDescription());
            holder.llCommentSection.setBackground(ContextCompat.getDrawable(context,R.drawable.dark_post_border));
            holder.ivReplyOnComment.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_reply_dark));
            holder.ivQuoteOnComment.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_qoute_dark));
            holder.tvCommentUserName.setTextColor(ContextCompat.getColor(context,R.color.white));
            holder.commentView.setBackgroundColor(ContextCompat.getColor(context,R.color.black_light));

        }else{
            new Utility().showAllComment(holder.clickable_webview, commentList.getDescription());
            holder.llCommentSection.setBackground(ContextCompat.getDrawable(context,R.drawable.post_border));
            holder.tvCommentUserName.setTextColor(ContextCompat.getColor(context,R.color.black));
            holder.ivReplyOnComment.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_reply));
            holder.ivQuoteOnComment.setBackground(ContextCompat.getDrawable(context,R.drawable.ic_quote));
        }
        holder.tvDate.setText("commented " + new DateFormatUtils().showPostDate(commentList.getCreated_at()));
        holder.tvViewCountSecond.setText(new Utility().prettyCount(commentList.getViews()));


        if(loginUserId != 0){
            holder.ivQuoteOnComment.setVisibility(View.VISIBLE);
            holder.ivReplyOnComment.setVisibility(View.VISIBLE);

        }else{
            holder.ivQuoteOnComment.setVisibility(View.GONE);
            holder.ivReplyOnComment.setVisibility(View.GONE);
        }

        if(commentList.getSoft_delete() == 0){
            holder.tvDeletedPost.setVisibility(View.GONE);
            holder.ivSaveComment.setVisibility(View.VISIBLE);
        }else{
            holder.tvDeletedPost.setVisibility(View.VISIBLE);
            holder.ivSaveComment.setVisibility(View.GONE);
        }
        if(commentList.getSaveComments().size() > 0){
            holder.ivSaveComment.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star_blue));
        }else{
            holder.ivSaveComment.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_star));
        }

        if (commentList.getTagInfo() != null) {
            if(commentList.getSoft_delete() == 0){
                holder.tvCommentUserName.setText(commentList.getTagInfo().getTitle());
                Glide.with(context).load(BaseUrl.PAGE_IMAGE_URL + commentList.getTagInfo().getTag_img()).placeholder(R.drawable.user_placeholder).dontAnimate().into(holder.ivCommentUserImage);
            }else{
                // if soft delete 1 the user image hide
                holder.tvCommentUserName.setText("user_"+commentList.getId());
            }


        } else {
            if (commentList.getUserInfo() != null) {

                if(commentList.getSoft_delete() == 0){
                    if (commentList.getUserInfo().getHide_real_name() == 1) {
                        holder.tvCommentUserName.setText(commentList.getUserInfo().getName());
                    }
                    Glide.with(context).load(commentList.getUserInfo().getImage()).placeholder(R.drawable.user_placeholder).dontAnimate().into(holder.ivCommentUserImage);
                }else{
                    // if soft delete 1 the user image hide
                    holder.tvCommentUserName.setText("user_"+commentList.getId());
                    //Glide.with(context).load(commentList.getUserInfo().getImage()).placeholder(R.drawable.user_placeholder).dontAnimate().into(holder.ivCommentUserImage);
                }

            }
        }
        if (commentList.getUpVotes() != null) {
            String upVoteUser = commentList.getUpVotes().getUser_ids();
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

        holder.ivCommentUserImage.setOnClickListener(view -> {
            Intent friend = new Intent(context , UserProfileAndPostActivity.class);
            if (commentList.getUserInfo() != null) {
            friend.putExtra(BaseUrl.NAME , commentList.getUserInfo().getName());
            }
            context.startActivity(friend);
        });

        holder.tvCommentUserName.setOnClickListener(view -> {
            Intent friend = new Intent(context , UserProfileAndPostActivity.class);
            if (commentList.getUserInfo() != null) {
                friend.putExtra(BaseUrl.NAME , commentList.getUserInfo().getName());
            }
            context.startActivity(friend);
        });


        holder.ivReplyOnComment.setOnClickListener(v -> {
            if (loginUserId != 0) {
                if (commentList.getUserInfo() != null) {
                    replyInterface.replyOnComment(commentList.getUserInfo().getName()
                            , commentList.getDescription() , callClass);
                } else {
                    if (commentList.getTagInfo() != null) {
                        replyInterface.replyOnComment(commentList.getTagInfo().getTitle()
                                , commentList.getDescription() , callClass);
                    }
                }
            } else {
                new MyUtility().showLoginAlert(context);
              //  LoginAlertFragment alert  = new LoginAlertFragment();
                //alert.show(context.getSupportFragmentManager() ,"MyAlertLogin");
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
            if (loginUserId != 0) {
                if (commentList.getSaveComments().size() > 0) {
                    saveCommentInterfacae.saveComment(loginUserId, commentList.getSaveComments().get(0).getId(), 0
                            , 0, 1, holder.ivSaveComment, false);
                } else {
                    saveCommentInterfacae.saveComment(loginUserId, 0, commentList.getPost_id()
                            , commentList.getId(), 0, holder.ivSaveComment, true);
                }
            }else{
                new MyUtility().showLoginAlert(context);
            }

        });

        holder.clickable_webview.setOnWebViewClickListener(new OnWebViewClicked() {
            @Override
            public void onClick(String url) {
                replyInterface.showImageView(url);
            }
        });

    }




    private void callLoginIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    private static DiffUtil.ItemCallback<AllCommentModel.Result.CommentList> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<AllCommentModel.Result.CommentList>() {

                @Override
                public boolean areItemsTheSame(@NonNull AllCommentModel.Result.CommentList oldItem, @NonNull AllCommentModel.Result.CommentList newItem) {
                    return false;
                }

                @Override
                public boolean areContentsTheSame(@NonNull AllCommentModel.Result.CommentList oldItem, @NonNull AllCommentModel.Result.CommentList newItem) {
                    return false;
                }
            };

    class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvCommentUserName, tvReplyCount, tvUpVoters, tvDate , tvViewCountSecond , tvDeletedPost;
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
            tvDeletedPost = itemView.findViewById(R.id.tvDeletedPost);
        }
    }
}
