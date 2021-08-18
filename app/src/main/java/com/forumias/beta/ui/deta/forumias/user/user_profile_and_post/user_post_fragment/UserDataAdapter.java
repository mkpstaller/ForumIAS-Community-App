package com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.user_post_fragment;

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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.ui.deta.forumias.comment.ui.CommentOnPostDetailsActivity;
import com.forumias.beta.utility.DateFormatUtils;
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;
import com.forumias.beta.ui.deta.forumias.home.CallLikeUnLike;
import com.forumias.beta.ui.deta.forumias.home.model.HomeLatestPostModel;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.UserAllCommentActivity;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.UserProfileAndPostActivity;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.user_post_paging.UserPostModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDataAdapter extends PagedListAdapter<UserPostModel.MyStoriesList, UserDataAdapter.ViewHolder> {
    private Context context;
    private int articleVisible;
    private int shortDesVisible;
    private int showAnnoucement;
    private CallLikeUnLike callLikeUnLike;
    private int loginUserId ,darkModelStatus , tabPosition , tagId , isVerified;
    private String followThread , userName , image;
    MyUtility myUtility = new MyUtility();
    List<HomeLatestPostModel.MyLatestPost> listData;


    public UserDataAdapter(Context context , int showAnnoucement , String followThread, CallLikeUnLike callLikeUnLike ,
                           int tabPosition  , int tagId , String userName , String image  , int isVerified) {
        super(DEFF_CALLBACK);
        this.context = context;
        this.showAnnoucement = showAnnoucement;
        this.callLikeUnLike = callLikeUnLike;
        this.followThread = followThread;
        this.tabPosition = tabPosition;
        this.tagId = tagId;
        this.image = image;
        this.userName = userName;
        this.isVerified = isVerified;
        MyPreferenceData preferenceData = new MyPreferenceData(context);
        loginUserId = preferenceData.getIntegerData(BaseUrl.USER_ID);
        articleVisible = preferenceData.getIntegerData(BaseUrl.ARTICLE_VISIBLE);
        shortDesVisible = preferenceData.getIntegerData(BaseUrl.SHORT_DES_VISIBLE);
        darkModelStatus = preferenceData.getIntegerData(BaseUrl.DARK_MODE);
        listData = new ArrayList<>();

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myspace_list_layout, parent, false);
        return new UserDataAdapter.ViewHolder(view);
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserPostModel.MyStoriesList latestPost = getItem(position);
        assert latestPost != null;
       if(darkModelStatus == 1){
           holder.tvName.setTextColor(ContextCompat.getColor(context,R.color.low_gray));
           holder.llMainPostSection.setBackground(ContextCompat.getDrawable(context,R.drawable.dark_post_border));
           holder.tvPostTitle.setTextColor(ContextCompat.getColor(context,R.color.low_gray));
           holder.myView.setBackgroundResource(R.color.border_color);
           holder.tvReply.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_home_reply_dark));
       }else{
           holder.tvName.setTextColor(ContextCompat.getColor(context,R.color.black));
           holder.llMainPostSection.setBackground(ContextCompat.getDrawable(context,R.drawable.post_border));
           holder.tvPostTitle.setTextColor(ContextCompat.getColor(context,R.color.black));
           holder.myView.setBackgroundResource(R.color.low_gray);
           holder.tvReply.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_home_reply));
       }

       holder.tvPostTitle.setText(latestPost.getTitle());


    holder.tvGroup.setVisibility(View.GONE);
        holder.tvQuestion.setVisibility(View.GONE);
        holder.tvAnnouncement.setVisibility(View.GONE);

        if(loginUserId != 0){
            holder.ivBookMark.setVisibility(View.VISIBLE);
            holder.ivReport.setVisibility(View.VISIBLE);
        }else{
            holder.ivBookMark.setVisibility(View.GONE);
            holder.ivReport.setVisibility(View.VISIBLE);
        }


      if(showAnnoucement == 0) { // ths condition use for show and hide addMySpaceView and Announcement
          if(latestPost.getIsAnnouncement()==1){
              holder.ivPin.setVisibility(View.VISIBLE);
          }else{
              holder.ivPin.setVisibility(View.GONE);
          }
          myUtility.addMySpaceView(followThread, String.valueOf(latestPost.getId()), holder.ivAddToMySpace, context , showAnnoucement);
      }else{
          holder.ivPin.setVisibility(View.GONE);
          myUtility.addMySpaceView(followThread, String.valueOf(latestPost.getId()), holder.ivAddToMySpace, context ,showAnnoucement);
      }

      if(tabPosition != 0) { // tab position 0 not showing Q and D
          if (latestPost.getIsDiscussion() == 1) {
              holder.tvQuestion.setText(R.string.discussion);
              holder.tvQuestion.setVisibility(View.VISIBLE);
          } else {
              holder.tvQuestion.setText(R.string.question);
              holder.tvQuestion.setVisibility(View.VISIBLE);
          }
      }else{
          holder.tvQuestion.setVisibility(View.GONE);
      }
            // this is under discussion by chandan sir===================

/*   if(articleVisible != 1) {
            if (latestPost.getIsAnnouncement() == BaseUrl.IS_ANNOUNCEMENT) {
               *//* if(showAnnoucement == 0) {
                    showAnnouncement(holder.tvAnnouncement);
                }*//*
                holder.tvAnnouncement.setVisibility(View.GONE);
            }else {
                if (latestPost.getType() == 0) {
                    holder.tvQuestion.setVisibility(View.VISIBLE);
                    holder.tvAnnouncement.setVisibility(View.GONE);
                    if (latestPost.getIsDiscussion() == BaseUrl.IS_DISCUSSION) {
                        holder.tvQuestion.setText(R.string.discussion);
                    }else{
                        holder.tvQuestion.setText(R.string.question);
                    }
                } else {
                    holder.tvQuestion.setVisibility(View.GONE);
                    holder.tvAnnouncement.setVisibility(View.GONE);
                }
            }

        }else{
            holder.tvGroup.setVisibility(View.GONE);
            holder.tvQuestion.setVisibility(View.GONE);
            holder.tvAnnouncement.setVisibility(View.GONE);
        }*/

            holder.llPostImageSection.setVisibility(View.VISIBLE);
            if ((Utility.isNullOrEmpty(latestPost.getImg()) &&
                    (Utility.isNullOrEmpty(latestPost.getCatchyText())))) {
                holder.rlCathySection.setVisibility(View.GONE);
                holder.llPostImageSection.setVisibility(View.GONE);
            } else {
                if (Utility.isNullOrEmpty(latestPost.getImg())) {
                    holder.rlCathySection.setVisibility(View.VISIBLE);
                    holder.ivPostImage.setVisibility(View.GONE);
                    if (latestPost.getColorCode().equalsIgnoreCase("#f1c40f")) {
                        holder.rlCathySection.setBackgroundColor(Color.parseColor(latestPost.getColorCode()));
                        holder.tvCatchyName.setTextColor(ContextCompat.getColor(context, R.color.red));
                        holder.tvCatchyName.setText(latestPost.getCatchyText());
                    } else {
                        holder.rlCathySection.setBackgroundColor(Color.parseColor(latestPost.getColorCode()));
                        holder.tvCatchyName.setTextColor(ContextCompat.getColor(context, R.color.white));
                        holder.tvCatchyName.setText(latestPost.getCatchyText());
                    }
                } else {
                    holder.rlCathySection.setVisibility(View.GONE);
                    holder.ivPostImage.setVisibility(View.VISIBLE);
                    Glide.with(context).load(BaseUrl.POST_IMAGE_URL + latestPost.getImg()).into(holder.ivPostImage);
                }
            }

if(tabPosition != 2) {
    if (latestPost.getLastUserCommented() != null) {
        // Log.e("data====> " , "okokokoko"+latestPost.getCommented_at());
        holder.llPostUserSection.setVisibility(View.VISIBLE);
        holder.tvName.setVisibility(View.VISIBLE);
        if (latestPost.getLastUserCommented().getHide_real_name() == 1) {
            holder.tvName.setText(latestPost.getLastUserCommented().getName());
        } else {
            holder.tvName.setText(latestPost.getLastUserCommented().getFull_name());
        }
        Glide.with(context).load(latestPost.getLastUserCommented().getImage()).into(holder.ivUserImage);
        holder.tvUserVerifiedInfo.setVisibility(View.GONE);
        new Utility().showUserVerified(latestPost.getLastUserCommented().getIs_verified(), holder.tvUserVerified);
        String mySignature = "commented about " + new DateFormatUtils().showPostDate(latestPost.getCommented_at());
        holder.tvPostInfo.setText(mySignature);

    } else {
        holder.llPostUserSection.setVisibility(View.GONE);

    }
}else{

    holder.llPostUserSection.setVisibility(View.VISIBLE);
    holder.tvName.setVisibility(View.VISIBLE);
    holder.tvName.setText(userName);
    Glide.with(context).load(image).into(holder.ivUserImage);
    String mySignature = "commented about " + new DateFormatUtils().showPostDate(latestPost.getCommented_at());
    holder.tvPostInfo.setText(mySignature);
    new Utility().showUserVerified(isVerified, holder.tvUserVerified);

}

           if(latestPost.getLikeInfo() != null) {
               myUtility.userPageLikeUnLike(context, holder.ivLikeImage, holder.tvLikeCount, latestPost, loginUserId);
           }


        //Basic Information of latest post
        holder.tvViewCountSecond.setText(new Utility().prettyCount(latestPost.getViewCount()));
        holder.tvPostTitle.setText(latestPost.getTitle());
       // int totalComment = latestPost.getCommentInfoCount() + latestPost.getReplyInfoCount();
        holder.tvCommentCount.setText(String.valueOf(latestPost.getCommentInfoCount()));
        if(shortDesVisible != 1) {
            holder.webview.setVisibility(View.GONE);// hide description
            holder.webview.setVisibility(View.VISIBLE);
            new Utility().showDescription(holder.webview, latestPost.getDescription());
        }else{
            holder.webview.setVisibility(View.GONE);
        }

        if(latestPost.getBookmarkedBy() != null) {
            String bookMarkBy = latestPost.getBookmarkedBy();
            String[] myBookmark = bookMarkBy.split(",");
            List<String> bookmarkList = Arrays.asList(myBookmark);
            if (bookmarkList.contains(String.valueOf(loginUserId))) {
                holder.ivBookMark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bookmark));
            } else {
                if(darkModelStatus == 1){
                    holder.ivBookMark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_unbookmark_white));
                }else{
                    holder.ivBookMark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_unbookmark));
                }

            }
        }


        holder.ivAddToMySpace.setOnClickListener(view -> {
            Toast.makeText(context , "Under Development..!" , Toast.LENGTH_LONG).show();
         //callLikeUnLike.addToMySpace(loginUserId , latestPost.getId() , holder.ivAddToMySpace);

        });

        holder.ivPostNotification.setOnClickListener(view -> {
            Toast.makeText(context , "Under Development..!" , Toast.LENGTH_LONG).show();
        });

        holder.ivBookMark.setOnClickListener(view -> {
            if(latestPost.getBookmarkedBy() != null){
                String bookMarkBy1 = latestPost.getBookmarkedBy();
                String[] myBookmark1 = bookMarkBy1.split(",");
                List<String> bookmarkList1 = Arrays.asList(myBookmark1);
                if (bookmarkList1.contains(String.valueOf(loginUserId))){
                    new MyUtility().BookMarkAPI(String.valueOf(latestPost.getId()) ,
                            String.valueOf(loginUserId),0,holder.ivBookMark,context);
                }else{
                    new MyUtility().BookMarkAPI(String.valueOf(latestPost.getId()) ,
                            String.valueOf(loginUserId),1,holder.ivBookMark,context);
                }
            }else{
                new MyUtility().BookMarkAPI(String.valueOf(latestPost.getId()) ,
                        String.valueOf(loginUserId),1,holder.ivBookMark,context);
            }
        });

        holder.tvCommentSection.setOnClickListener(v -> {

            switch (tabPosition){
                case 0:
                case 1:
                Intent intent = new Intent(context, CommentOnPostDetailsActivity.class);
                MyPreferenceData myPref = new MyPreferenceData(context);
                myPref.saveData(BaseUrl.PREF_SLUG , latestPost.getSlug());
                context.startActivity(intent);

                break;
                case 2:
                    Intent intent1 = new Intent(context , UserAllCommentActivity.class);
                    intent1.putExtra(BaseUrl.POST_ID ,latestPost.getId());
                    intent1.putExtra(BaseUrl.TAG_ID ,tagId);
                    intent1.putExtra(BaseUrl.IS_VERIFIED, isVerified);
                    context.startActivity(intent1);
                    break;
            }
        });


        holder.llMainPostSection.setOnClickListener(view -> {
            switch (tabPosition){
                case 0:
                case 1:

                    Intent intent = new Intent(context, CommentOnPostDetailsActivity.class);
                    MyPreferenceData myPref = new MyPreferenceData(context);
                    myPref.saveData(BaseUrl.PREF_SLUG , latestPost.getSlug());
                    context.startActivity(intent);

                break;
                case 2:
                    Intent intent1 = new Intent(context , UserAllCommentActivity.class);
                    intent1.putExtra(BaseUrl.POST_ID ,latestPost.getId());
                    intent1.putExtra(BaseUrl.TAG_ID ,tagId);
                    intent1.putExtra(BaseUrl.IS_VERIFIED, isVerified);
                    context.startActivity(intent1);
                    break;
            }

        });


        holder.webview.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                return false;
            }

            if (event.getAction() == MotionEvent.ACTION_UP) {
                Intent intent = new Intent(context, CommentOnPostDetailsActivity.class);
                intent.putExtra(BaseUrl.SLUG, latestPost.getSlug());
                intent.putExtra(BaseUrl.POST_ID, latestPost.getId());
                context.startActivity(intent);
            }

            return false;
        });

        /*
         * This click method use for more menu*/
        holder.ivReport.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(context, holder.ivReport);
            popup.getMenuInflater().inflate(R.menu.more_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_report) {
                    Log.e("Report: ", "Click Ok report");
                   // Toast.makeText(context , "Under Development...!",Toast.LENGTH_LONG).show();
                    callLikeUnLike.reportDailog(latestPost.getId() , loginUserId , context);
                }
                return true;
            });
            popup.show();
        });

        /*
         * This click section use for share data*/
       holder.ivShare.setOnClickListener(view -> {
            String sendData = BaseUrl.SHARE_BASE_URL+latestPost.getSlug();
            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_TEXT, sendData);
            share.setType("text/plain");
            context.startActivity(Intent.createChooser(share,"Share With."));
        });

      holder.llPostUserSection.setOnClickListener(view -> {
          Intent friend = new Intent(context , UserProfileAndPostActivity.class);
          if(latestPost.getLastUserCommented() != null) {
              friend.putExtra(BaseUrl.NAME, latestPost.getLastUserCommented().getName());
          }else{
              friend.putExtra(BaseUrl.NAME, latestPost.getUserInfo().getName());
          }
         /*   Intent friend = new Intent(context, UserProfileAndPostActivity.class);
            friend.putExtra(BaseUrl.NAME, latestPost.getUserInfo().getName());
            friend.putExtra(BaseUrl.TAG_ID, latestPost.getTagId());
            friend.putExtra(BaseUrl.ACT_TYPE, latestPost.getType());*/
            context.startActivity(friend);
        });

       holder.llLikeSection.setOnClickListener(v -> {
            Log.e("Click ","Click me.===> " );

            if(loginUserId == 0){
             new MyUtility().showLoginAlert(context);
            }else {
                if (latestPost.getLikeInfo() != null) {
                    Log.e("Click ","Click me.=1==> " +position+" "+latestPost.getLikeInfo().getLikeCount()+" "+latestPost.getId()+" "
                    +loginUserId+" ");
                    callLikeUnLike.userMyLikeUnlike(holder.likeProgressBar, latestPost, position, latestPost.getLikeInfo().getLikeCount(), latestPost.getId(),
                            loginUserId, holder.ivLikeImage, holder.tvLikeCount ,holder.ivLiveHeart);
                } else {
                    Log.e("Click ","Click me.==2=> " );
                    callLikeUnLike.userMyLikeUnlike(holder.likeProgressBar, latestPost, position, 0, latestPost.getId(),
                            loginUserId, holder.ivLikeImage, holder.tvLikeCount , holder.ivLiveHeart);
                }
            }
        });

    }




    @SuppressLint("SetTextI18n")
    private void showUserInfo(HomeLatestPostModel.MyLatestPost latestPost, AppCompatTextView tvName,
                              AppCompatImageView tvUserVerifiedInfo, AppCompatImageView tvUserVerified,
                              ImageView ivUserImage, AppCompatTextView tvPostInfo, AppCompatTextView tvPostUserInfo) {

        if (latestPost.getUserInfo().getHideRealName() != BaseUrl.HIDE_REAL_NAME) {
            tvName.setVisibility(View.VISIBLE);
            tvName.setText(latestPost.getUserInfo().getName());
            new Utility().showUserVerified(latestPost.getUserInfo().getIsVerified(), tvUserVerifiedInfo);
        } else {
            tvUserVerified.setVisibility(View.GONE);
            tvName.setVisibility(View.GONE);
        }

        Glide.with(context).load(latestPost.getUserInfo().getImage()).into(ivUserImage);
        if (latestPost.getUserInfo().getAbout() != null) {
            String mySignature =
                    " (" + latestPost.getUserInfo().getAbout() + ") " +
                            " Created Post " + new DateFormatUtils().showPostDate(latestPost.getCreatedAt());
            tvPostInfo.setText(mySignature);
        } else {
            String mySignature = " Created Post " + new DateFormatUtils().showPostDate(latestPost.getCreatedAt());
            tvPostInfo.setText(mySignature);
        }
        tvPostUserInfo.setVisibility(View.VISIBLE);
        tvPostUserInfo.setText("@" + latestPost.getUserInfo().getName());
        new Utility().showUserVerified(latestPost.getUserInfo().getIsVerified(), tvUserVerifiedInfo);

    }

    private void showAnnouncement(AppCompatTextView tvAnnouncement) {
        tvAnnouncement.setVisibility(View.VISIBLE);
        tvAnnouncement.setText(R.string.announcement);
    }



    private static DiffUtil.ItemCallback<UserPostModel.MyStoriesList> DEFF_CALLBACK =
            new DiffUtil.ItemCallback<UserPostModel.MyStoriesList>() {
                @Override
                public boolean areItemsTheSame(@NonNull UserPostModel.MyStoriesList oldItem, @NonNull UserPostModel.MyStoriesList newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull UserPostModel.MyStoriesList oldItem, @NonNull UserPostModel.MyStoriesList newItem) {
                    return oldItem.equals(newItem);
                }
            };

  /*  public int getSize(){
        return listData.size();
    }
*/
    class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvName, tvPostTitle, tvCatchyName, tvDescription, tvQuestion, tvGroup,
                tvLikeCount, tvCommentCount, tvViewCount, tvChannel, tvViewCountSecond, tvPostInfo,
                tvPostUserInfo, tvAnnouncement;
        private LinearLayoutCompat llMainPostSection, llPostImageSection, llLikeSection,
               llPostUserSection,tvCommentSection;
        private AppCompatImageView ivPostImage, ivBookMark, tvUserVerifiedInfo, tvUserVerified ,ivLikeImage ,
                ivShare , ivReport,tvReply,ivLiveHeart , ivAddToMySpace , ivPin ,ivPostNotification;

        private RelativeLayout rlCathySection, rlLikeCommentSection;
        private ImageView ivUserImage;
        private WebView webview;
        private ProgressBar likeProgressBar;
        private View myView;

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
            tvLikeCount = itemView.findViewById(R.id.tvLikeCount);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            ivBookMark = itemView.findViewById(R.id.tvBoorMark);
            ivShare = itemView.findViewById(R.id.ivShare);
            ivReport = itemView.findViewById(R.id.ivReport);
            tvChannel = itemView.findViewById(R.id.tvChannel);
            tvViewCount = itemView.findViewById(R.id.tvViewCount);
            tvViewCountSecond = itemView.findViewById(R.id.tvViewCountSecond);
            llMainPostSection = itemView.findViewById(R.id.llMainPostSection);
            rlLikeCommentSection = itemView.findViewById(R.id.rlLikeCommentSection);
           // llGroupSection = itemView.findViewById(R.id.llGroupSection);
           // llChannelSection = itemView.findViewById(R.id.llChannelSection);
           // llViewSection = itemView.findViewById(R.id.llViewSection);
            tvPostInfo = itemView.findViewById(R.id.tvPostInfo);
            tvPostUserInfo = itemView.findViewById(R.id.tvPostUserInfo);
            tvUserVerifiedInfo = itemView.findViewById(R.id.tvUserVerifiedInfo);
            tvUserVerified = itemView.findViewById(R.id.tvUserVerified);
            llPostUserSection = itemView.findViewById(R.id.llPostUserSection);
            webview = itemView.findViewById(R.id.webView);
            tvAnnouncement = itemView.findViewById(R.id.tvAnnouncement);
            llLikeSection = itemView.findViewById(R.id.llLikeSection);
            ivLikeImage = itemView.findViewById(R.id.ivLikeImage);
            likeProgressBar = itemView.findViewById(R.id.likeProgressBar);
            tvCommentSection = itemView.findViewById(R.id.tvCommentSection);
            myView = itemView.findViewById(R.id.myView);
            tvReply = itemView.findViewById(R.id.tvReply);
            ivLiveHeart = itemView.findViewById(R.id.ivLiveHeart);
            ivAddToMySpace = itemView.findViewById(R.id.ivAddToMySpace);
            ivPin = itemView.findViewById(R.id.ivPin);
            ivPostNotification = itemView.findViewById(R.id.ivPostNotification);
        }
    }
}
