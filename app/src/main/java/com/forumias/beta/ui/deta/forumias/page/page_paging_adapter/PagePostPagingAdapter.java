package com.forumias.beta.ui.deta.forumias.page.page_paging_adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;
import com.forumias.beta.ui.deta.forumias.page.PagePostCommentActivity;
import com.forumias.beta.ui.deta.forumias.page.page_interface.PagePostLikeUnlike;

import java.util.Arrays;
import java.util.List;

public class PagePostPagingAdapter extends PagedListAdapter<PagePostModel.PageData, PagePostPagingAdapter.ViewHolder> {

    private Context context;
    private PagePostLikeUnlike pagePostLikeUnlike;
    private int loginUserId,isVerified,darkModeStatus;
    private String pageName , tagImage , colorCode;
    public PagePostPagingAdapter(Context context,PagePostLikeUnlike pagePostLikeUnlike , String pageName ,
                                 String tagImage , String colorCode ,int isVerified) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.pagePostLikeUnlike = pagePostLikeUnlike;
        this.pageName = pageName;
        this.tagImage = tagImage;
        this.colorCode = colorCode;
        this.isVerified = isVerified;
        MyPreferenceData preferenceData = new MyPreferenceData(context);
        loginUserId = preferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = preferenceData.getIntegerData(BaseUrl.DARK_MODE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_post_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PagePostModel.PageData pageData = getItem(position);

        if(darkModeStatus == 1){
            holder.llPageListPost.setBackgroundResource(R.drawable.dark_post_border);
            holder.tvPostTitle.setTextColor(ContextCompat.getColor(context , R.color.light_white));
            holder.myView.setBackgroundResource(R.color.border_color);
        }else{
            holder.llPageListPost.setBackgroundResource(R.color.white);
            holder.tvPostTitle.setTextColor(ContextCompat.getColor(context , R.color.black_light));
            holder.myView.setBackgroundResource(R.color.low_gray);

        }

        Log.e("Page Slug=====> ",pageData.getSlug());

        assert pageData != null;
        MyUtility myUtility = new MyUtility();


        if(loginUserId != 0){
            holder.ivBookMark.setVisibility(View.VISIBLE);
            holder.ivReport.setVisibility(View.VISIBLE);
        }else{
            holder.ivBookMark.setVisibility(View.GONE);
            holder.ivReport.setVisibility(View.GONE);
        }


        myUtility.likeUnLikeMethod(context,holder.ivLikeImage,holder.tvLikeCount,pageData,loginUserId);

        holder.tvCommentCount.setText(String.valueOf(pageData.getComment_info_count()));

        if(pageData.getIs_announcement() == 1){
            holder.tvAnnouncement.setVisibility(View.VISIBLE);
        }else{
            holder.tvAnnouncement.setVisibility(View.GONE);
        }

        if (pageData.getType() == 0) {
            holder.llPostImageSection.setVisibility(View.GONE);
            holder.tvQuestion.setVisibility(View.VISIBLE);
           // holder.tvGroup.setVisibility(View.GONE);
        } else {
            holder.llPostImageSection.setVisibility(View.VISIBLE);
            if((Utility.isNullOrEmpty(pageData.getImg()))&&
                    (Utility.isNullOrEmpty(pageData.getCatchy_text()))){
                holder.llPostImageSection.setVisibility(View.GONE);
                holder.rlCathySection.setVisibility(View.GONE);
            }else {
                if (Utility.isNullOrEmpty(pageData.getImg())) {
                    holder.rlCathySection.setVisibility(View.VISIBLE);
                    holder.ivPostImage.setVisibility(View.GONE);
                    if (pageData.getColor_code().equalsIgnoreCase("#f1c40f")) {
                        holder.rlCathySection.setBackgroundColor(Color.parseColor(pageData.getColor_code()));
                        holder.tvCatchyName.setTextColor(ContextCompat.getColor(context, R.color.red));
                        holder.tvCatchyName.setText(pageData.getCatchy_text());
                    } else {
                        holder.rlCathySection.setBackgroundColor(Color.parseColor(pageData.getColor_code()));
                        holder.tvCatchyName.setTextColor(ContextCompat.getColor(context, R.color.white));
                        holder.tvCatchyName.setText(pageData.getCatchy_text());
                    }
                } else {
                    holder.rlCathySection.setVisibility(View.GONE);
                    holder.ivPostImage.setVisibility(View.VISIBLE);
                    Glide.with(context).load(BaseUrl.POST_IMAGE_URL + pageData.getImg()).into(holder.ivPostImage);
                }
            }
        }

        holder.tvViewCountSecond.setText(new Utility().prettyCount(pageData.getView_count())); // show view count
        holder.tvPostTitle.setText(pageData.getTitle());

        new Utility().showDescription(holder.webview , pageData.getDescription());

        if(pageData.getBookmarked_by() != null) {
            String bookMarkBy = pageData.getBookmarked_by();
            String[] myBookmark = bookMarkBy.split(",");
            List<String> bookmarkList = Arrays.asList(myBookmark);
            if (bookmarkList.contains(String.valueOf(loginUserId))) {
                holder.ivBookMark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bookmark));
            } else {
                holder.ivBookMark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_unbookmark));
            }
        }

        holder.llLikeSection.setOnClickListener(v -> {
            if(loginUserId != 0) {
                if (pageData.getLikeInfo() != null) {
                    pagePostLikeUnlike.callMyLikeUnlike(holder.likeProgressBar, pageData, position, pageData.getLikeInfo().getLike_count(),
                            pageData.getId(), loginUserId, holder.ivLikeImage, holder.tvLikeCount);
                } else {
                    pagePostLikeUnlike.callMyLikeUnlike(holder.likeProgressBar, pageData, position, 0,
                            pageData.getId(), loginUserId, holder.ivLikeImage, holder.tvLikeCount);
                }
            }else{
                new MyUtility().showLoginAlert(context);
            }
        });

        holder.ivAddToMySpace.setOnClickListener(view -> {
            Toast.makeText(context , "Under Development..!" ,Toast.LENGTH_LONG).show();
          //  pagePostLikeUnlike.addToMySpace(loginUserId,pageData.getId() , holder.ivAddToMySpace);
        });

        holder.ivPageNotification.setOnClickListener(view -> {
            Toast.makeText(context , "Under Development..!" , Toast.LENGTH_LONG).show();
        });

        holder.ivReport.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(context, holder.ivReport);
            popup.getMenuInflater().inflate(R.menu.more_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_report) {
                    Log.e("Report: ", "Click Ok report");
                    pagePostLikeUnlike.repostVoidData(loginUserId , pageData.getId());
                }
                return true;
            });
            popup.show();
        });



        holder.tvCommentSection.setOnClickListener(v -> {
            Intent intent = new Intent(context, CommentOnPostDetailsActivity.class);
            MyPreferenceData myPref = new MyPreferenceData(context);
            myPref.saveData(BaseUrl.PREF_SLUG, pageData.getSlug());
            context.startActivity(intent);
            //Intent intent = new Intent(context, PagePostCommentActivity.class);
            //MyPreferenceData myPref = new MyPreferenceData(context);
            //myPref.saveData(BaseUrl.PREF_COMM_GROUP_NAME , pageName);
           /* myPref.saveData(BaseUrl.PREF_COMM_COLORS , colorCode);
            myPref.saveData(BaseUrl.PREF_COMM_TAG_IMAGE , tagImage);
            myPref.saveData(BaseUrl.PREF_COMM_DESCRIPTION , pageData.getDescription());
            myPref.saveIntegerData(BaseUrl.PREF_COMM_POST_ID , pageData.getId());
            myPref.saveIntegerData(BaseUrl.PREF_COMM_COMMENT_COUNT , pageData.getComment_info_count());
            myPref.saveIntegerData(BaseUrl.PREF_COMM_VIEW_COUNT , pageData.getView_count());
            myPref.saveIntegerData(BaseUrl.PREF_COMM_IS_VERIFIED , isVerified);
            if(pageData.getLikeInfo() != null){
                String likeUser = pageData.getLikeInfo().getUser_ids();
                String[] likeUserId = likeUser.split(",");
                List<String> likeUserIdList = Arrays.asList(likeUserId);
                if(likeUserIdList.contains(String.valueOf(loginUserId))){
                   myPref.saveIntegerData(BaseUrl.PREF_COMM_MY_LIKE_CHECK , 1);
                }else{
                    myPref.saveIntegerData(BaseUrl.PREF_COMM_MY_LIKE_CHECK , 0);
                }
                myPref.saveIntegerData(BaseUrl.PREF_COMM_LIKE_COUNT,pageData.getLikeInfo().getLike_count());
            }*/
           // context.startActivity(intent);
        });

        holder.tvPostTitle.setOnClickListener(v -> {
            Intent intent = new Intent(context, CommentOnPostDetailsActivity.class);
            MyPreferenceData myPref = new MyPreferenceData(context);
            myPref.saveData(BaseUrl.PREF_SLUG, pageData.getSlug());
            context.startActivity(intent);
         /*   Intent intent = new Intent(context, PagePostCommentActivity.class);
          MyPreferenceData myPref = new MyPreferenceData(context);
          myPref.saveData(BaseUrl.PREF_COMM_GROUP_NAME , pageName);
          myPref.saveData(BaseUrl.PREF_COMM_TITLE , pageData.getTitle());
          myPref.saveData(BaseUrl.PREF_COMM_COLORS , colorCode);
          myPref.saveData(BaseUrl.PREF_COMM_TAG_IMAGE , tagImage);
          myPref.saveData(BaseUrl.PREF_COMM_DESCRIPTION , pageData.getDescription());
          myPref.saveIntegerData(BaseUrl.PREF_COMM_POST_ID , pageData.getId());
          myPref.saveIntegerData(BaseUrl.PREF_COMM_COMMENT_COUNT , pageData.getComment_info_count());
          myPref.saveIntegerData(BaseUrl.PREF_COMM_VIEW_COUNT , pageData.getView_count());
          myPref.saveIntegerData(BaseUrl.PREF_COMM_IS_VERIFIED , isVerified);
            if(pageData.getLikeInfo() != null){
                String likeUser = pageData.getLikeInfo().getUser_ids();
                String[] likeUserId = likeUser.split(",");
                List<String> likeUserIdList = Arrays.asList(likeUserId);
                if(likeUserIdList.contains(String.valueOf(loginUserId))){
                    myPref.saveIntegerData(BaseUrl.PREF_COMM_MY_LIKE_CHECK , 1);
                }else{
                    myPref.saveIntegerData(BaseUrl.PREF_COMM_MY_LIKE_CHECK , 0);
                }
                myPref.saveIntegerData(BaseUrl.PREF_COMM_LIKE_COUNT,pageData.getLikeInfo().getLike_count());
            }else{
                myPref.saveIntegerData(BaseUrl.PREF_COMM_MY_LIKE_CHECK , 0);
                myPref.saveIntegerData(BaseUrl.PREF_COMM_LIKE_COUNT,0);
            }

            context.startActivity(intent);*/
        });

        holder.ivShare.setOnClickListener(v -> {
            String shareData= BaseUrl.SHARE_BASE_URL+pageData.getSlug();
            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_TEXT,shareData);
            share.setType("text/plain");
            context.startActivity(Intent.createChooser(share,"Share More"));
        });

        holder.ivBookMark.setOnClickListener(view -> {


            if(pageData.getBookmarked_by() != null){
                String bookMarkBy1 = pageData.getBookmarked_by();
                String[] myBookmark1 = bookMarkBy1.split(",");
                List<String> bookmarkList1 = Arrays.asList(myBookmark1);
                if (bookmarkList1.contains(String.valueOf(loginUserId))){
                    new MyUtility().BookMarkAPI(String.valueOf(pageData.getId()) ,
                            String.valueOf(loginUserId),0,holder.ivBookMark,context);
                }else{
                    new MyUtility().BookMarkAPI(String.valueOf(pageData.getId()) ,
                            String.valueOf(loginUserId),1,holder.ivBookMark,context);
                }
            }else{
                new MyUtility().BookMarkAPI(String.valueOf(pageData.getId()) ,
                        String.valueOf(loginUserId),1,holder.ivBookMark,context);
            }


        });

    }


    private static DiffUtil.ItemCallback<PagePostModel.PageData> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<PagePostModel.PageData>() {
                @Override
                public boolean areItemsTheSame(@NonNull PagePostModel.PageData oldItem, @NonNull PagePostModel.PageData newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull PagePostModel.PageData oldItem, @NonNull PagePostModel.PageData newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class ViewHolder extends RecyclerView.ViewHolder{
        private AppCompatTextView tvName, tvPostTitle, tvCatchyName, tvDescription, tvQuestion, tvGroup,
                 tvLikeCount, tvCommentCount, tvViewCount, tvMore, tvChannel, tvViewCountSecond, tvPostInfo,
                tvPostUserInfo, tvAnnouncement;
        private LinearLayoutCompat llMainPostSection, llPostImageSection, llLikeSection,
                llPostUserSection,tvCommentSection , llPageListPost;
        private AppCompatImageView ivPostImage, ivBookMark, tvUserVerifiedInfo, tvUserVerified
                ,ivLikeImage, ivShare,ivReport , ivAddToMySpace , ivPageNotification;
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
            tvLikeCount = itemView.findViewById(R.id.tvLikeCount);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            ivBookMark = itemView.findViewById(R.id.tvBoorMark);
            ivShare = itemView.findViewById(R.id.ivShare);
            tvMore = itemView.findViewById(R.id.tvMore);
            tvChannel = itemView.findViewById(R.id.tvChannel);
            tvViewCount = itemView.findViewById(R.id.tvViewCount);
            tvViewCountSecond = itemView.findViewById(R.id.tvViewCountSecond);
            llMainPostSection = itemView.findViewById(R.id.llMainPostSection);
            rlLikeCommentSection = itemView.findViewById(R.id.rlLikeCommentSection);
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
            ivReport = itemView.findViewById(R.id.ivReport);
            llPageListPost = itemView.findViewById(R.id.llPageListPost);
            myView = itemView.findViewById(R.id.myView);
            ivAddToMySpace = itemView.findViewById(R.id.ivAddToMySpace);
            ivPageNotification = itemView.findViewById(R.id.ivPageNotification);
        }
    }
}
