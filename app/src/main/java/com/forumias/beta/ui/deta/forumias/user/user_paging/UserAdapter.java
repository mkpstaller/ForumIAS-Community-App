package com.forumias.beta.ui.deta.forumias.user.user_paging;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.ui.deta.forumias.user.UserInterface;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.UserProfileAndPostActivity;
import com.forumias.beta.R;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UserAdapter extends PagedListAdapter<UserModel.UserListing, UserAdapter.ViewHolder> {

    private Context context;
    private UserInterface userInterface;
    private int loginUserId,darkModeStatus;
    private String followingUserData;
    public UserAdapter(Context context , UserInterface userInterface) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.userInterface = userInterface;

        MyPreferenceData myPreferenceData = new MyPreferenceData(Objects.requireNonNull(context));
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel.UserListing userList =getItem(position);
        assert userList != null;

        if(darkModeStatus == 1) {
           // new Utility().showAllCommentDarkMode(holder.webView, commentList.getDescription());
            holder.llFriendProfileSection.setBackground(ContextCompat.getDrawable(context,R.drawable.dark_post_border));
           // holder.tvCommentUserName.setTextColor(ContextCompat.getColor(context,R.color.white));
        }else{
           // new Utility().showAllComment(holder.webView, commentList.getDescription());
            holder.llFriendProfileSection.setBackground(ContextCompat.getDrawable(context,R.drawable.post_border));
           // holder.tvCommentUserName.setTextColor(ContextCompat.getColor(context,R.color.black));
        }

        followingUserData = UserItemDataSource.followingData; // this data get User follow id.. // 691810110009005
        String[] followUser = followingUserData.split(",");
        List<String> followList = Arrays.asList(followUser);
        if(followList.contains(String.valueOf(userList.getId()))){
            holder.llFollowing.setVisibility(View.VISIBLE);
            holder.llFollow.setVisibility(View.GONE);
        }else{
            if(userList.getFollowStatus() == 1){
                holder.llFollowing.setVisibility(View.VISIBLE);
                holder.llFollow.setVisibility(View.GONE);
            }else {
                holder.llFollowing.setVisibility(View.GONE);
                holder.llFollow.setVisibility(View.VISIBLE);
            }
        }

        holder.tvName.setText(userList.getName());
        holder.tvDate.setText(userList.getCreatedAt());
        new Utility().showUserVerified(userList.getIs_verified(), holder.tvUserVerified);
        if(userList.getAbout() != null) {
            holder.tvAbout.setVisibility(View.GONE);
            holder.tvAbout.setText(Html.fromHtml(userList.getAbout()));
        }else{
            holder.tvAbout.setVisibility(View.GONE);
        }

        Glide.with(context).load(userList.getImage()).into(holder.ivUserImage);

        holder.ivUserImage.setOnClickListener(v -> {
               userInterface.UserInformation(userList.getName() , userList.getAbout()
                        , userList.getImage() , userList.getCreatedAt());
        });
        holder.llFriendProfileSection.setOnClickListener( v -> {
                Intent friend = new Intent(context , UserProfileAndPostActivity.class);
                friend.putExtra(BaseUrl.NAME , userList.getName());
                context.startActivity(friend);
        });
       holder.llFollow.setOnClickListener(view -> {
           if(loginUserId != 0) {
               userInterface.userFollowUnfollow(userList, followingUserData, String.valueOf(loginUserId), String.valueOf(userList.getId()),
                       String.valueOf(BaseUrl.SUBSCRIBED_STATUS), String.valueOf(BaseUrl.USER_FOLLOW_UNFOLLOW), holder.llFollow, holder.llFollowing, position);
           }else{
              new MyUtility().showLoginAlert(context);
           }
       });


        holder.llFollowing.setOnClickListener(view -> {
            if(loginUserId != 0) {
                userInterface.userFollowUnfollow(userList, followingUserData, String.valueOf(loginUserId), String.valueOf(userList.getId()),
                        String.valueOf(BaseUrl.UNSUBSCRIBED_STATUS), String.valueOf(BaseUrl.USER_FOLLOW_UNFOLLOW), holder.llFollow, holder.llFollowing, position);
            }else{
                new MyUtility().showLoginAlert(context);
            }
        });
    }



    /**
        * This method use for get and set data in adapter */
    private static DiffUtil.ItemCallback<UserModel.UserListing> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<UserModel.UserListing>() {
                @Override
                public boolean areItemsTheSame(@NonNull UserModel.UserListing oldItem, @NonNull UserModel.UserListing newItem) {
                    return oldItem.getId()  == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull UserModel.UserListing oldItem, @NonNull UserModel.UserListing newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvName ,tvAbout , tvFollow , tvFollowing , tvDate;
        private ImageView ivUserImage;
        private LinearLayoutCompat llFriendProfileSection , llFollow , llFollowing;
        private AppCompatImageView tvUserVerified;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvUserVerified = itemView.findViewById(R.id.tvUserVerified);
            tvAbout = itemView.findViewById(R.id.tvAbout);
            ivUserImage = itemView.findViewById(R.id.ivUserImage);
            tvFollowing = itemView.findViewById(R.id.tvFollowing);
            tvFollow = itemView.findViewById(R.id.tvFollow);
            tvDate = itemView.findViewById(R.id.tvDate);
            llFriendProfileSection = itemView.findViewById(R.id.llFriendProfileSection);
            llFollow = itemView.findViewById(R.id.llFollow);
            llFollowing = itemView.findViewById(R.id.llFollowing);
        }
    }
}
