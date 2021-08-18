package com.forumias.beta.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.forumias.beta.myinterface.MyFollowingInterface;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.utility.MyUtility;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.pojo.DefaultPojo;
import com.forumias.beta.R;
import com.forumias.beta.ui.deta.WelcomeHomeActivity;
import com.forumias.beta.ui.deta.forumias.AboutActivity;
import com.forumias.beta.ui.deta.forumias.profile.UserProfileActivity;
import com.forumias.beta.ui.deta.forumias.save_comment.SaveCommentListActivity;
import com.forumias.beta.ui.deta.forumias.setting.SettingActivityActivity;
import com.forumias.beta.ui.deta.ReadingListActivity;
import com.forumias.beta.ui.deta.forumias.working_us.WebViewActivity;
import com.forumias.beta.ui.login.LoginActivity;

import java.util.List;

public class SideNavAdapter extends RecyclerView.Adapter<SideNavAdapter.ViewHolder> {
    private List<DefaultPojo> list;
    private Context context;
    private MyFollowingInterface myFollowingInterface;
    private String slugName;
    private boolean clickGroupStatus = true;
    boolean clickPageStatus = true;
    boolean clickChannelStatus = true;
    private int userId ,darkModeStatus;
    MyPreferenceData myPreferenceData;

    public SideNavAdapter(List<DefaultPojo> list, Context context, MyFollowingInterface myFollowingInterface) {
        this.list = list;
        this.context = context;
        this.myFollowingInterface = myFollowingInterface;
        myPreferenceData = new MyPreferenceData(context);
        userId=myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus=myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_list_layout, parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvSideNavName.setText(list.get(position).getMessage());


        if(darkModeStatus == 1){
            holder.tvSideNavName.setTextColor(ContextCompat.getColor(context,R.color.light_white));

            switch (position){
                case 0:
                    holder.ivIcon.setBackgroundResource(R.drawable.ic_user_white);
                   // holder.rlFollowingSection.setBackgroundResource(R.drawable.notification_shape_blue);
                    break;
                case 1:
                    holder.ivIcon.setBackgroundResource(R.drawable.ic_darkmode_white);
                    holder.ivIcon.setRotation(180);
                    holder.swDarkMode.setVisibility(View.VISIBLE);
                    if(darkModeStatus == 1){
                        holder.swDarkMode.setChecked(true);
                    }else{
                        holder.swDarkMode.setChecked(false);
                    }
                    break;
                case 2:
                    holder.ivIcon.setBackgroundResource(R.drawable.ic_star_white);
                    break;
                case 3:
                    holder.ivIcon.setBackgroundResource(R.drawable.ic_bookmark_white);
                    break;
                case 4:
                    holder.ivIcon.setBackgroundResource(R.drawable.ic_side_notifiction_white);
                    holder.swDarkMode.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    holder.ivIcon.setBackgroundResource(R.drawable.ic_blog_white);
                    break;
                case 6:
                    holder.ivIcon.setBackgroundResource(R.drawable.ic_academy_white);
                    break;
                case 7:
                    holder.ivIcon.setBackgroundResource(R.drawable.ic_setting_white);
                    break;
                case 8:
                    holder.ivIcon.setBackgroundResource(R.drawable.ic_about_white);
                    break;
            }

        }else{
            holder.tvSideNavName.setTextColor(ContextCompat.getColor(context,R.color.black_light));
            switch (position){
                case 0:
                    holder.ivIcon.setBackgroundResource(R.drawable.ic_user);
                    break;
                case 1:
                    holder.ivIcon.setBackgroundResource(R.drawable.ic_sun_darkmode);
                    holder.swDarkMode.setVisibility(View.VISIBLE);
                    if(darkModeStatus == 1){
                        holder.swDarkMode.setChecked(true);
                    }else{
                        holder.swDarkMode.setChecked(false);
                    }
                    break;
                case 2:
                    holder.ivIcon.setBackgroundResource(R.drawable.ic_star_black);
                    break;
                case 3:
                    holder.ivIcon.setBackgroundResource(R.drawable.ic_bookmark_gray);
                    break;
                case 4:
                    holder.ivIcon.setBackgroundResource(R.drawable.ic_side_notification);
                    holder.swDarkMode.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    holder.ivIcon.setBackgroundResource(R.drawable.ic_blog);
                    break;
                case 6:
                    holder.ivIcon.setBackgroundResource(R.drawable.ic_academy);
                    break;
                case 7:
                    holder.ivIcon.setBackgroundResource(R.drawable.ic_setting);
                    break;
                case 8:
                    holder.ivIcon.setBackgroundResource(R.drawable.ic_about);
                    break;
            }
        }

        holder.swDarkMode.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            switch (position){
                case 1:
                    if(isChecked){
                        myPreferenceData.saveIntegerData(BaseUrl.DARK_MODE , 1);
                        Intent intent = new Intent(context , WelcomeHomeActivity.class);
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    }else {
                        myPreferenceData.saveIntegerData(BaseUrl.DARK_MODE , 0);
                        Intent intent = new Intent(context , WelcomeHomeActivity.class);
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    }
                    break;

                case 4:
                    Toast.makeText(context , "Under Development..!",Toast.LENGTH_LONG).show();
                    break;
            }

        });


   holder.rlFollowingSection.setOnClickListener(view -> {
            switch (position){
                case 0:
                    if(userId != 0) {
                        Intent profile = new Intent(context, UserProfileActivity.class);
                      //  signup.putExtra(BaseUrl.STATUS, 2);
                        context.startActivity(profile);
                    }else{
                        Intent signup = new Intent(context, LoginActivity.class);
                        signup.putExtra(BaseUrl.STATUS, 2);
                        context.startActivity(signup);
                    }
                   // slugName = "page";
                    break;
                case 1:
                    //slugName = "channel";
                    break;
                case 2:
                    if(userId != 0){
                        Intent save = new Intent(context , SaveCommentListActivity.class);
                        context.startActivity(save);
                    }else{
                        new MyUtility().showLoginAlert(context);
                    }
                    break;

                case 3:
                    if(userId != 0){
                        Intent save = new Intent(context , ReadingListActivity.class);
                        context.startActivity(save);
                    }else{
                        new MyUtility().showLoginAlert(context);
                    }
                    break;
                case 4:

                    Toast.makeText(context ,"Under Development..!" , Toast.LENGTH_LONG).show();
                  /* if(userId != 0){
                    Intent save = new Intent(context , ReadingListActivity.class);
                    context.startActivity(save);
                    }else{
                        new MyUtility().showLoginAlert(context);
                    }*/
                    break;
                case 5:
                    Intent link = new Intent(context , WebViewActivity.class);
                    link.putExtra(BaseUrl.WEB_LINK , BaseUrl.BLOG_URL);
                    context.startActivity(link);
                    break;

                case 6:
                    Intent acad = new Intent(context , WebViewActivity.class);
                    acad.putExtra(BaseUrl.WEB_LINK , BaseUrl.ACADEMY_URL);
                    context.startActivity(acad);
                    break;
                case 7:
                    Intent setting = new Intent(context , SettingActivityActivity.class);
                    context.startActivity(setting);
                    break;
                case 8:

                    Intent about = new Intent(context , AboutActivity.class);
                    context.startActivity(about);
                  /*  if (userId != 0) {
                        MyPreferenceData myPreferenceData = new MyPreferenceData(context);
                        myPreferenceData.clearData();
                        Intent splash = new Intent(context , BetaSplashActivity.class);
                        context.startActivity(splash);

                    }else{
                        Intent splash = new Intent(context , LoginActivity.class);
                        splash.putExtra(BaseUrl.STATUS , 1);
                        context.startActivity(splash);
                        ((Activity)context).finish();
                    }*/
                    break;
            }

             //   myFollowingInterface.followingTag(position, slugName, holder.recyclerView , holder.tvSeeAll , clickChannelStatus);

        });


    }

   /* private boolean showHide(boolean status, RecyclerView recyclerView , AppCompatTextView tvSeeAll) {
        if(status){
            recyclerView.setVisibility(View.VISIBLE);
            tvSeeAll.setVisibility(View.VISIBLE);
            status = false;
        }else{
            recyclerView.setVisibility(View.GONE);
            tvSeeAll.setVisibility(View.GONE);
            status = true;
        }
        return status;
    }*/

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvSideNavName , tvSeeAll;
        private LinearLayoutCompat rlFollowingSection;
        private RecyclerView recyclerView;
        private SwitchCompat swDarkMode;
        private AppCompatImageView ivIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSideNavName = itemView.findViewById(R.id.tvSideNavName);
            swDarkMode = itemView.findViewById(R.id.swDarkMode);
            rlFollowingSection = itemView.findViewById(R.id.rlFollowingSection);
           // recyclerView = itemView.findViewById(R.id.recyclerView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
        }
    }
}
