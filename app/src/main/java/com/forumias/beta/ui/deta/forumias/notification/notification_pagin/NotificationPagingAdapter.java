package com.forumias.beta.ui.deta.forumias.notification.notification_pagin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.utility.DateFormatUtils;
import com.forumias.beta.R;
import com.forumias.beta.ui.deta.forumias.notification.NotificationModel;
import com.forumias.beta.ui.deta.forumias.notification.NotificationReadingInteface;

import java.util.Arrays;
import java.util.List;

public class NotificationPagingAdapter extends PagedListAdapter<NotificationModel.MyNotification.NotificationData, NotificationPagingAdapter.ViewHolder> {

    private Context context;
    private int loginUserId , darkModeStatus;
    private  NotificationReadingInteface readingInterface;
    public NotificationPagingAdapter(Context context , NotificationReadingInteface readingInterface) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.readingInterface = readingInterface;
        MyPreferenceData preferenceData = new MyPreferenceData(context);
        loginUserId = preferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = preferenceData.getIntegerData(BaseUrl.DARK_MODE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationModel.MyNotification.NotificationData notificationData = getItem(position);

        if(darkModeStatus == 1){
          //  holder.llNotificationSection.setBackgroundResource(R.drawable.dark_post_border);
            holder.tvName.setTextColor(ContextCompat.getColor(context , R.color.light_white));
            holder.tvTitle.setTextColor(ContextCompat.getColor(context , R.color.light_white));

            if(notificationData.getRead_by() != null){
                String readBy = notificationData.getRead_by();
                String[] notiReadBy = readBy.split(",");

                List<String> readUserId = Arrays.asList(notiReadBy);

                if(readUserId.contains(String.valueOf(loginUserId))){
                    holder.llNotificationSection.setBackgroundResource(R.drawable.dark_post_border);
                }else{
                    if(notificationData.isClickStatus()){
                        holder.llNotificationSection.setBackgroundResource(R.drawable.dark_post_border);
                    }else{
                        holder.llNotificationSection.setBackgroundResource(R.drawable.notification_shape_blue);
                    }
                }
            }

        }else{
           // holder.llNotificationSection.setBackgroundResource(R.drawable.notification_shape_blue);
            holder.tvName.setTextColor(ContextCompat.getColor(context , R.color.black_light));
            holder.tvTitle.setTextColor(ContextCompat.getColor(context , R.color.black_light));


            if(notificationData.getRead_by() != null){
                String readBy = notificationData.getRead_by();
                String[] notiReadBy = readBy.split(",");

                List<String> readUserId = Arrays.asList(notiReadBy);

                if(readUserId.contains(String.valueOf(loginUserId))){
                    holder.llNotificationSection.setBackgroundResource(R.drawable.notification_shape_white);
                }else{
                    if(notificationData.isClickStatus()){
                        holder.llNotificationSection.setBackgroundResource(R.drawable.notification_shape_white);
                    }else{
                        holder.llNotificationSection.setBackgroundResource(R.drawable.notification_shape_blue);
                    }
                }
            }

        }

        assert notificationData != null;
        holder.tvTime.setText(new DateFormatUtils().showPostDate(notificationData.getCreated_at()));
        switch (notificationData.getAction()){
            case 0:
                switch (notificationData.getModule()){
                    case 1:
                        holder.tvNameInfo.setText(" Publish a new post");
                        break;
                    case 8:
                        holder.tvNameInfo.setText(" stated a poll");
                        break;

                }
                break;
            case 1:
                switch (notificationData.getModule()){
                    case 1:
                        if(notificationData.getChannel_id() != 0){
                            holder.tvNameInfo.setText(" Update post");
                        }else {
                            holder.tvNameInfo.setText(" page");
                        }
                        break;

                }
                break;
            case 2:
                holder.tvNameInfo.setText("commented");
                break;
            case 4:
                switch (notificationData.getModule()){
                    case 5:
                        holder.tvNameInfo.setText("Started following you");
                        break;
                    case 4:
                        holder.tvNameInfo.setText("Started following");
                        break;
                }
                break;
            case 5:
                holder.tvNameInfo.setText("answered question");
                break;
            case 6:
                switch (notificationData.getModule()){
                    case 2:
                        holder.tvNameInfo.setText("replied on a answer");
                        break;
                }
                break;
            case 7:
                switch (notificationData.getModule()){
                    case 1:
                    case 2:
                        holder.tvNameInfo.setText("Link your post");
                        break;

                }
                break;

            case 8:
                switch (notificationData.getModule()){
                    case 2:
                        holder.tvNameInfo.setText("Asked you a question");
                        break;
                }
                break;
            case 13:
                switch (notificationData.getModule()){
                    case 1:
                        holder.tvNameInfo.setText("has a quoted a comment");
                        break;
                }
                break;

        }



        holder.tvTitle.setVisibility(View.VISIBLE);
        if(notificationData.getNotif_postInfo() != null){
            holder.tvTitle.setText(notificationData.getNotif_postInfo().getTitle());
            holder.tvTitle.setVisibility(View.VISIBLE);
        }else if(notificationData.getNotif_channelInfo_one() != null){
            holder.tvTitle.setText(notificationData.getNotif_channelInfo_one().getTitle());
            holder.tvTitle.setVisibility(View.VISIBLE);
        }else if(notificationData.getNotif_groupInfo_one() != null){
            holder.tvTitle.setVisibility(View.VISIBLE);
            holder.tvTitle.setText(notificationData.getNotif_groupInfo_one().getTitle());
        }else{
            holder.tvTitle.setVisibility(View.GONE);
        }

        if(notificationData.getNotifi_channelInfo() != null){
            holder.tvName.setText(notificationData.getNotifi_channelInfo().getTitle());
            Glide.with(context).load(R.drawable.ic_channels).into(holder.ivUserImage);
        }else {

            if(notificationData.getNotif_groupInfo() != null){
                if(notificationData.getGroup_id() == 0) {
                    holder.tvName.setText(notificationData.getNotif_fromUserInfo().getName());
                    Glide.with(context).load(BaseUrl.PAGE_IMAGE_URL + notificationData.getNotif_groupInfo().getTag_img()).into(holder.ivUserImage);
                }else{
                    holder.tvName.setText(notificationData.getNotif_groupInfo().getTitle());
                    Glide.with(context).load(notificationData.getNotif_fromUserInfo().getImage()).into(holder.ivUserImage);
                }
                }else {

                if (notificationData.getNotif_userInfo() != null) {
                    holder.tvName.setText(notificationData.getNotif_userInfo().getName());
                    Glide.with(context).load(notificationData.getNotif_userInfo().getImage()).into(holder.ivUserImage);
                } else {
                    if(notificationData.getNotif_fromUserInfo() != null) {
                        holder.tvName.setText(notificationData.getNotif_fromUserInfo().getName());
                        Glide.with(context).load(notificationData.getNotif_fromUserInfo().getImage()).into(holder.ivUserImage);
                    }
                }
            }
        }


        holder.llNotificationSection.setOnClickListener(view -> {
            if(darkModeStatus == 1){
                holder.llNotificationSection.setBackgroundResource(R.drawable.dark_post_border);
            }else{
                holder.llNotificationSection.setBackgroundResource(R.drawable.notification_shape_white);
            }

            notificationData.setClickStatus(true);
            readingInterface.notificationReading(loginUserId ,notificationData.getId() , notificationData.getAction()
                                , notificationData.getModule() , notificationData);
        });


    }



    private static DiffUtil.ItemCallback<NotificationModel.MyNotification.NotificationData> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<NotificationModel.MyNotification.NotificationData>() {
                @Override
                public boolean areItemsTheSame(@NonNull NotificationModel.MyNotification.NotificationData oldItem, @NonNull NotificationModel.MyNotification.NotificationData newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull NotificationModel.MyNotification.NotificationData oldItem, @NonNull NotificationModel.MyNotification.NotificationData newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class ViewHolder extends RecyclerView.ViewHolder{
        public AppCompatTextView tvTitle , tvName ,tvNameInfo , tvTime;
        public ImageView ivUserImage;
        public LinearLayoutCompat llNotificationSection;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvName = itemView.findViewById(R.id.tvName);
            tvNameInfo = itemView.findViewById(R.id.tvNameInfo);
            tvTime = itemView.findViewById(R.id.tvTime);
            ivUserImage = itemView.findViewById(R.id.ivUserImage);
            llNotificationSection = itemView.findViewById(R.id.llNotificationSection);
        }
    }
}
