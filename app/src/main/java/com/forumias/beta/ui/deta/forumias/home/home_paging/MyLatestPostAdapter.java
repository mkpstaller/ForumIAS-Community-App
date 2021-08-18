package com.forumias.beta.ui.deta.forumias.home.home_paging;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.myinterface.BookMarkInterface;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.ui.deta.forumias.comment.ui.CommentOnPostDetailsActivity;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.UserProfileAndPostActivity;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.ui.deta.forumias.home.model.HomeLatestPostModel;
import com.forumias.beta.R;

import java.util.List;

public class MyLatestPostAdapter extends RecyclerView.Adapter<MyLatestPostAdapter.ViewHolder> {
    private List<HomeLatestPostModel.MyLatestPost> mySpaceList;
    private Context context;
    private BookMarkInterface bookMarkInterface;
    private int loginUserId;

    public MyLatestPostAdapter(List<HomeLatestPostModel.MyLatestPost> mySpaceList, Context context) {
        this.mySpaceList = mySpaceList;
        this.context = context;
       // this.bookMarkInterface = bookMarkInterface;
        MyPreferenceData myPreferenceData = new MyPreferenceData(context);
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myspace_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mySpaceList.get(position).getType() == 0) {
            holder.llPostImageSection.setVisibility(View.GONE);
            holder.tvQuestion.setVisibility(View.VISIBLE);
            holder.tvGroup.setVisibility(View.GONE);
        } else {
            holder.llPostImageSection.setVisibility(View.VISIBLE);
            if((Utility.isNullOrEmpty(mySpaceList.get(position).getImg()) &&
                    (Utility.isNullOrEmpty(mySpaceList.get(position).getCatchyText())))){
                holder.rlCathySection.setVisibility(View.GONE);
                holder.llPostImageSection.setVisibility(View.GONE);
            }else {
                if (Utility.isNullOrEmpty(mySpaceList.get(position).getImg())) {
                    holder.rlCathySection.setVisibility(View.VISIBLE);
                    holder.ivPostImage.setVisibility(View.GONE);
                    if (mySpaceList.get(position).getColorCode().equalsIgnoreCase("#f1c40f")) {
                        holder.rlCathySection.setBackgroundColor(Color.parseColor(mySpaceList.get(position).getColorCode()));
                        holder.tvCatchyName.setTextColor(ContextCompat.getColor(context, R.color.red));
                        holder.tvCatchyName.setText(mySpaceList.get(position).getCatchyText());
                    } else {
                        holder.rlCathySection.setBackgroundColor(Color.parseColor(mySpaceList.get(position).getColorCode()));
                        holder.tvCatchyName.setTextColor(ContextCompat.getColor(context, R.color.white));
                        holder.tvCatchyName.setText(mySpaceList.get(position).getCatchyText());
                    }
                } else {
                    holder.rlCathySection.setVisibility(View.GONE);
                    holder.ivPostImage.setVisibility(View.VISIBLE);
                    Glide.with(context).load(BaseUrl.POST_IMAGE_URL + mySpaceList.get(position).getImg()).into(holder.ivPostImage);
                }
            }
        }

        if(mySpaceList.get(position).getTagId() == 0 && mySpaceList.get(position).getChannelId() == 0){
            holder.llChannelSection.setVisibility(View.GONE);
            holder.llGroupSection.setVisibility(View.GONE);
            holder.tvViewCountSecond.setVisibility(View.VISIBLE);
            holder.rlLikeCommentSection.setVisibility(View.VISIBLE);

        }else {
            if(mySpaceList.get(position).getTagId() != 0){
                holder.llChannelSection.setVisibility(View.GONE);
                holder.llViewSection.setVisibility(View.GONE);
                holder.llGroupSection.setVisibility(View.VISIBLE);
                holder.rlLikeCommentSection.setVisibility(View.VISIBLE);
            }else{
                holder.llChannelSection.setVisibility(View.VISIBLE);
                holder.llGroupSection.setVisibility(View.GONE);
                holder.rlLikeCommentSection.setVisibility(View.GONE);
                holder.llViewSection.setVisibility(View.VISIBLE);
            }
        }

        holder.tvViewCountSecond.setText(new Utility().prettyCount(mySpaceList.get(position).getViewCount()));
        holder.tvPostTitle.setText(mySpaceList.get(position).getTitle());
        new Utility().showDescription(holder.webview , mySpaceList.get(position).getDescription());
       /* int setBookMark = new MyUtility().postBookMark(mySpaceList.get(position).getBookmarkedBy());
        if(setBookMark == 1){
            holder.ivBookMark.setBackgroundResource(R.drawable.ic_bookmark);
        }else{
            holder.ivBookMark.setBackgroundResource(R.drawable.ic_unbookmark);
        }*/

        if(loginUserId != 0){
            holder.ivBookMark.setVisibility(View.VISIBLE);
        }
        /*
         *This Click use for mark as read post*/
       /* holder.ivBookMark.setOnClickListener(view ->{
            int getPos = new MyPreferenceData(context).getIntegerData(BaseUrl.POSITION);
            if(getPos != position){
                new MyPreferenceData(context).clearData();
                new MyPreferenceData(context).saveIntegerData(BaseUrl.POSITION,position);
                int status = new MyUtility().postBookMark(mySpaceList.get(position).getBookmarkedBy());
                    bookMarkInterface.myBookMark(position, String.valueOf(mySpaceList.get(position).getId()), String.valueOf(loginUserId), holder.ivBookMark, status);
            }else {
                int status = new MyUtility().postBookMark(mySpaceList.get(position).getBookmarkedBy());
                int markStatus = new MyPreferenceData(context).getIntegerData(BaseUrl.BOOK_MARK_STATUS);
                if (markStatus != 100) {
                    if (markStatus == 1) {
                        status = 0;
                        bookMarkInterface.myBookMark(position, String.valueOf(mySpaceList.get(position).getId()), String.valueOf(loginUserId), holder.ivBookMark, status);
                    } else {
                        status = 1;
                        bookMarkInterface.myBookMark(position, String.valueOf(mySpaceList.get(position).getId()), String.valueOf(loginUserId), holder.ivBookMark, status);
                    }
                } else {
                    bookMarkInterface.myBookMark(position, String.valueOf(mySpaceList.get(position).getId()), String.valueOf(loginUserId), holder.ivBookMark, status);
                }
            }

        });
*/
        holder.llMainPostSection.setOnClickListener(view ->{
                Intent intent = new Intent(context, CommentOnPostDetailsActivity.class);
                intent.putExtra(BaseUrl.SLUG ,mySpaceList.get(position).getSlug());
                context.startActivity(intent);

        });


        holder.webview.setOnTouchListener((v, event) -> {
            if (event.getAction()==MotionEvent.ACTION_MOVE){
                return false;
            }

            if (event.getAction()==MotionEvent.ACTION_UP){
                Intent intent = new Intent(context, CommentOnPostDetailsActivity.class);
                intent.putExtra(BaseUrl.SLUG ,mySpaceList.get(position).getSlug());
                context.startActivity(intent);
            }

            return false;
        });

        /*
         * This click method use for more menu*/
        holder.tvMore.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(context, holder.tvMore);
            popup.getMenuInflater().inflate(R.menu.more_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_report) {
                    Log.e("Report: ", "Click Ok report");
                }
                return true;
            });
            popup.show();
        });

        /*
        *
         * This click section use for share data*/
        holder.ivShare.setOnClickListener(view -> {
          //  String sendData = "https://forumias.com/post/detail/"+latestPost.getSlug();
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_TEXT,"SHARE DATA WORKING FINE");
                share.setType("text/plain");
                context.startActivity(share);
        });

        holder.llPostUserSection.setOnClickListener(view -> {
            Intent friend = new Intent(context , UserProfileAndPostActivity.class);
          //  friend.putExtra(BaseUrl.NAME , mySpaceList.get(position).getUserInfo().getName());
            friend.putExtra(BaseUrl.TAG_ID , mySpaceList.get(position).getTagId());
            friend.putExtra(BaseUrl.ACT_TYPE , mySpaceList.get(position).getType());
            context.startActivity(friend);
        });

    }

    @Override
    public int getItemCount() {
        return mySpaceList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvName, tvPostTitle, tvCatchyName, tvDescription, tvQuestion, tvGroup ,
                tvMore,tvChannel , tvViewCountSecond,tvDate;
        private LinearLayoutCompat llMainPostSection, llPostImageSection , llGroupSection ,
                llChannelSection , llViewSection ,llPostUserSection;
        private AppCompatImageView ivPostImage , ivBookMark , ivShare;
        private RelativeLayout rlCathySection , rlLikeCommentSection;
        private ImageView ivUserImage;
        private WebView webview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            ivUserImage = itemView.findViewById(R.id.ivUserImage);
            tvPostTitle = itemView.findViewById(R.id.tvPostTitle);
            llPostImageSection = itemView.findViewById(R.id.llPostImageSection);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            rlCathySection = itemView.findViewById(R.id.rlCathySection);
            tvCatchyName = itemView.findViewById(R.id.tvCatchyName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvGroup = itemView.findViewById(R.id.tvGroup);
          //  tvLikeCount = itemView.findViewById(R.id.tvLikeCount);
           // tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            ivBookMark = itemView.findViewById(R.id.tvBoorMark);
            ivShare = itemView.findViewById(R.id.ivShare);
            tvMore = itemView.findViewById(R.id.tvMore);
            tvChannel = itemView.findViewById(R.id.tvChannel);
          //  tvViewCount = itemView.findViewById(R.id.tvViewCount);
            tvViewCountSecond = itemView.findViewById(R.id.tvViewCountSecond);
            llMainPostSection = itemView.findViewById(R.id.llMainPostSection);
            rlLikeCommentSection = itemView.findViewById(R.id.rlLikeCommentSection);
            llGroupSection = itemView.findViewById(R.id.llGroupSection);
            llChannelSection = itemView.findViewById(R.id.llChannelSection);
            llViewSection = itemView.findViewById(R.id.llViewSection);
            tvDate = itemView.findViewById(R.id.tvDate);
            llPostUserSection = itemView.findViewById(R.id.llPostUserSection);
            webview = itemView.findViewById(R.id.webView);

        }
    }
}
