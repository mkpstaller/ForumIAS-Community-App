package com.forumias.beta.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.R;
import com.forumias.beta.pojo.BookMarkResponse;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.ui.deta.forumias.comment.ui.model.CheckFollowModel;
import com.forumias.beta.ui.deta.forumias.home.model.HomeLatestPostModel;
import com.forumias.beta.ui.deta.forumias.home.model.LikeResponseModel;
import com.forumias.beta.ui.deta.forumias.page.page_paging_adapter.PagePostModel;
import com.forumias.beta.ui.deta.forumias.splash.BetaSplashActivity;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.user_post_paging.UserPostModel;
import com.forumias.beta.ui.login.LoginActivity;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyUtility {

    private int status;
    public int postBookMark(String bookMarkBy , int loginUserId){
        if (!Utility.isNullOrEmpty(bookMarkBy)) {
           // String bookmarkBy = bookMarkBy;
            String[] myBookmark = bookMarkBy.split(",");
            List<String> bookmarkList = Arrays.asList(myBookmark);
            if (bookmarkList.contains(String.valueOf(loginUserId))) {
                status = 1;
            } else {
                status = 0;
            }
        } else {
            status = 0;
        }
        return status;
    }

    public void BookMarkAPI(String postId , String userId , int bookMarksStatus , AppCompatImageView ivBookMark , Context context){
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<BookMarkResponse> call = apiInterface.fetchBookMarkData(postId,userId , String.valueOf(bookMarksStatus));
        call.enqueue(new Callback<BookMarkResponse>() {
            @Override
            public void onResponse(@NotNull Call<BookMarkResponse> call, @NotNull Response<BookMarkResponse> response) {
                assert response.body() != null;
                if(response.body().getStatus().equalsIgnoreCase(BaseUrl.SUCCESS)){
                    if(bookMarksStatus == 1){
                        ivBookMark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bookmark));
                    }else{
                        MyPreferenceData myPreferenceData = new MyPreferenceData(context);
                        int darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
                        if(darkModeStatus == 1) {
                            ivBookMark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_unbookmark_white));
                        }else{
                            ivBookMark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_unbookmark));
                        }
                    }
                }
            }
            @Override
            public void onFailure(@NotNull Call<BookMarkResponse> call, @NotNull Throwable t) {

            }
        });
    }


    public int  checkFollowUnFollow(int id , int type) {
        final int[] followType = new int[1];
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<CheckFollowModel> call = apiInterface.checkFollow(type, id, 164);
        call.enqueue(new Callback<CheckFollowModel>() {
            @Override
            public void onResponse(@NotNull Call<CheckFollowModel> call, @NotNull Response<CheckFollowModel> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus().equals(BaseUrl.SUCCESS)) {
                       followType[0] = response.body().getFollow();
                    }

                }
            }

            @Override
            public void onFailure(@NotNull Call<CheckFollowModel> call, @NotNull Throwable t) {

            }
        });
        return followType[0];
    }

    public void clickLineUnLike(Context context,int postId , int loginUserId,int likeCount,
                                AppCompatImageView ivLikeImage , AppCompatTextView tvLikeCount , int likeOnDetailsPage){
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<LikeResponseModel> call = apiInterface.checkLikeUnLike(postId , loginUserId);
        call.enqueue(new Callback<LikeResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<LikeResponseModel> call, @NotNull Response<LikeResponseModel> response) {
                if(response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus().equals(BaseUrl.SUCCESS)) {
                        if(response.body().getAction() == BaseUrl.LIKE){
                            ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_red));
                            if(likeOnDetailsPage != 1) {
                                int totalLikeCount = likeCount + 1;
                                tvLikeCount.setText(String.valueOf(totalLikeCount));
                            }
                        }else{
                            if(likeOnDetailsPage != 1) {
                                int totalLikeCount = likeCount - 1;
                                tvLikeCount.setText(String.valueOf(totalLikeCount));
                            }
                            MyPreferenceData myPreferenceData = new MyPreferenceData(context);
                            int darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
                            if(darkModeStatus == 1){
                                ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border));
                            }else{
                               ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border));
                            }

                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<LikeResponseModel> call, @NotNull Throwable t) {

            }
        });
    }


    public void likeUnLikeMethod(Context context, AppCompatImageView ivLikeImage, AppCompatTextView tvLikeCount,
                                 PagePostModel.PageData pageData, int loginUserId){
        MyPreferenceData myPreferenceData = new MyPreferenceData(context);
        int darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);

        if(pageData.getLikeInfo() != null) {
            tvLikeCount.setText(String.valueOf(pageData.getLikeInfo().getLike_count()));
            String likeUser = pageData.getLikeInfo().getUser_ids();
            String[] likeUserId = likeUser.split(",");
            List<String> likeUserIdList = Arrays.asList(likeUserId);
            if(likeUserIdList.contains(String.valueOf(loginUserId))){
                ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_red));
            }else{
                if(darkModeStatus == 1) {
                    ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border));
                }else{
                    ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border));
                }
            }
        }else{
            tvLikeCount.setText("0");
            if(darkModeStatus == 1) {
                ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border));
            }else{
                ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border));
            }
        }
    }

    public void homePageLikeUnLike(Context context, AppCompatImageView ivLikeImage, AppCompatTextView tvLikeCount,
                                   HomeLatestPostModel.MyLatestPost latestPost, int loginUserId){
        MyPreferenceData myPreferenceData = new MyPreferenceData(context);
        int darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
        if (latestPost.getLikeInfo() != null) {
            tvLikeCount.setText(String.valueOf(latestPost.getLikeInfo().getLikeCount()));
            String likeUser = String.valueOf(latestPost.getLikeInfo().getLikeUserId());
            String[] likeId = likeUser.split(",");
            List<String> likeUserId = Arrays.asList(likeId);
            if(likeUserId.contains(String.valueOf(loginUserId))){
                ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_red));
            }else{
                if(darkModeStatus == 1) {
                    ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border));
                }else{
                    ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border));
                }
            }
        }else{
            tvLikeCount.setText("");
            if(darkModeStatus == 1) {
                ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border));
            }else{
                ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border));
            }
        }

    }


    public void userPageLikeUnLike(Context context, AppCompatImageView ivLikeImage, AppCompatTextView tvLikeCount,
                                   UserPostModel.MyStoriesList latestPost, int loginUserId){
        MyPreferenceData myPreferenceData = new MyPreferenceData(context);
        int darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);
        if (latestPost.getLikeInfo() != null) {
            tvLikeCount.setText(String.valueOf(latestPost.getLikeInfo().getLikeCount()));
            String likeUser = String.valueOf(latestPost.getLikeInfo().getLikeUserId());
            String[] likeId = likeUser.split(",");
            List<String> likeUserId = Arrays.asList(likeId);
            if(likeUserId.contains(String.valueOf(loginUserId))){
                ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_red));
            }else{
                if(darkModeStatus == 1) {
                    ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border));
                }else{
                    ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border));
                }
            }
        }else{
            tvLikeCount.setText("");
            if(darkModeStatus == 1) {
                ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border));
            }else{
                ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border));
            }
        }

    }



 /*   public void homePageLikeUnLike(Context context, AppCompatImageView ivLikeImage, AppCompatTextView tvLikeCount,
                                   HomeLatestPostModel.MyLatestPost latestPost, int loginUserId){
        if (latestPost.getLikeInfo() != null) {
            tvLikeCount.setText(String.valueOf(latestPost.getLikeInfo().getLikeCount()));
            String likeUser = String.valueOf(latestPost.getLikeInfo().getLikeUserId());
            String[] likeId = likeUser.split(",");
            List<String> likeUserId = Arrays.asList(likeId);
            if(likeUserId.contains(String.valueOf(loginUserId))){
                ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_red));
            }else{
                ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border));
            }
        }else{
            tvLikeCount.setText("");
            ivLikeImage.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_border));
        }

    }
*/
    /*public void showLoginAlert(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Please login to continue")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        Intent intent = new Intent(context , LoginActivity.class);
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Alert Message");
        alert.show();
    }*/

    public void showLoginAlert(Context context){

       Dialog dialog = new Dialog(context);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.login_alert_layout);
        AppCompatTextView tvLogin = dialog.findViewById(R.id.tvLogin);
        AppCompatTextView tvSignup = dialog.findViewById(R.id.tvSignup);
        AppCompatImageView ivClose = dialog.findViewById(R.id.ivClose);


        tvLogin.setOnClickListener(view -> {
            Intent intent = new Intent(context , LoginActivity.class);
            intent.putExtra(BaseUrl.STATUS,1);
            context.startActivity(intent);
            ((Activity)context).finish();
            dialog.dismiss();
        });

        tvSignup.setOnClickListener(view -> {
            Intent intent = new Intent(context , LoginActivity.class);
            intent.putExtra(BaseUrl.STATUS,2);
            context.startActivity(intent);
            ((Activity)context).finish();
            dialog.dismiss();
        });


        ivClose.setOnClickListener(view -> dialog.dismiss());
        dialog.show();



    /*    AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Please login to continue")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Alert Message");
        alert.show();*/
    }

    public void checkConnection(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.connection_layout);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        AppCompatImageView showImage = dialog.findViewById(R.id.showImage);
        AppCompatTextView tvTryAgain = dialog.findViewById(R.id.tvTryAgain);
        Glide.with(context).load(R.drawable.check_net).placeholder(R.drawable.check_net).into(showImage);
        dialog.show();

        tvTryAgain.setOnClickListener(view -> {
            dialog.dismiss();
            Intent intent = new Intent(context , BetaSplashActivity.class);
            context.startActivity(intent);

        });


    }

    public void addMySpaceView(String followThread , String postId , AppCompatImageView ivAddMySpace , Context context , int showannouncement){

        String[] addSpace = followThread.split(",");
        List<String> addMySpace = Arrays.asList(addSpace);
        if(showannouncement == 0) {
            if (addMySpace.contains(String.valueOf(postId))) {
                ivAddMySpace.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check_blue));
            } else {
                ivAddMySpace.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add_black));
            }
        }else{
            if (addMySpace.contains(String.valueOf(postId))) {
                ivAddMySpace.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check_blue));
               // tvFollow.setText(context.getString(R.string.following));
            } else {
                ivAddMySpace.setVisibility(View.GONE);
            }
        }
    }


    @SuppressLint("WrongThread")
    public static File saveBitmapToFile(File file){
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE=60;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 60 , outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }


    public static void shareAppWithSocial(Context context, String application, String title,
                                          String description) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setPackage(application);

        intent.putExtra(android.content.Intent.EXTRA_TITLE, title);
        intent.putExtra(Intent.EXTRA_TEXT, description);
        intent.setType("text/plain");

        try {
            // Start the specific social application
            context.startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            // The application does not exist
            Toast.makeText(context, "app have not been installed.", Toast.LENGTH_SHORT).show();
        }


    }

    public void addCustomToast(String message , Activity context){
        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.login_custome_toast,  Objects.requireNonNull(context).findViewById(R.id.custom_toast_layout));
        AppCompatTextView tvMessage = layout.findViewById(R.id.tvToast);
        tvMessage.setText(message);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP|Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public  void addLikeMusic(Context context){
        MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.like);
        mPlayer.start();
    }


    public  void addBookMarkMusic(Context context){
        MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.bookmark);
        mPlayer.start();
    }


    public  void addAddSpaceMusic(Context context){
        MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.addspace);
        mPlayer.start();
    }

}


