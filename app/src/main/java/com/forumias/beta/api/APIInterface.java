package com.forumias.beta.api;


import com.forumias.beta.pojo.BookMarkResponse;
import com.forumias.beta.pojo.ChannelModel;
import com.forumias.beta.pojo.DefaultPojo;
import com.forumias.beta.pojo.FollowUnFollowModel;
import com.forumias.beta.pojo.FollowingORFollowerResponse;
import com.forumias.beta.pojo.GroupModel;
import com.forumias.beta.pojo.GroupPostDetailsModel;
import com.forumias.beta.pojo.LoginOtpVerification;
import com.forumias.beta.pojo.LoginWithPasswordModel;
import com.forumias.beta.pojo.OtpVerificationResponse;
import com.forumias.beta.pojo.PagesFollowingModel;
import com.forumias.beta.pojo.PostCountModel;
import com.forumias.beta.pojo.PostDetailsModel;
import com.forumias.beta.pojo.ProfileImageModel;
import com.forumias.beta.pojo.ReadingModel;
import com.forumias.beta.pojo.RegisterBaseResponse;
import com.forumias.beta.pojo.ReportModel;
import com.forumias.beta.pojo.SearchModel;
import com.forumias.beta.pojo.ShareCommentModel;
import com.forumias.beta.pojo.UserInfoAndPostModel;
import com.forumias.beta.ui.deta.forumias.academy.PageDetailsModel;
import com.forumias.beta.ui.deta.forumias.channel.ChannelDetailsInfo;
import com.forumias.beta.ui.deta.forumias.channel.channel_paging.Private_channel_Subscribe;
import com.forumias.beta.ui.deta.forumias.channel.channel_post.ChannelPostModel;
import com.forumias.beta.ui.deta.forumias.comman_model.MyPostDetailsModel;
import com.forumias.beta.ui.deta.forumias.comment.fragment.model.AllCommentModel;
import com.forumias.beta.ui.deta.forumias.comment.fragment.model.UseFullCommentModel;
import com.forumias.beta.ui.deta.forumias.comment.ui.model.CheckFollowModel;
import com.forumias.beta.ui.deta.forumias.comment.ui.model.LikeUserModel;
import com.forumias.beta.ui.deta.forumias.comment.ui.model.PollModel;
import com.forumias.beta.ui.deta.forumias.comment.ui.model.UpVoteModel;
import com.forumias.beta.ui.deta.forumias.create_story.CreateStoryModel;
import com.forumias.beta.ui.deta.forumias.deep_link.model.DeepCommentModel;
import com.forumias.beta.ui.deta.forumias.home.model.AddMySpaceModel;
import com.forumias.beta.ui.deta.forumias.home.model.AdsController;
import com.forumias.beta.ui.deta.forumias.home.model.AdsControllerImage;
import com.forumias.beta.ui.deta.forumias.home.model.HomeLatestPostModel;
import com.forumias.beta.ui.deta.forumias.home.model.LikeResponseModel;

import com.forumias.beta.ui.deta.forumias.message.model.ChatListModel;
import com.forumias.beta.ui.deta.forumias.message.model.ChatModel;
import com.forumias.beta.ui.deta.forumias.message.model.IndexMessageModel;
import com.forumias.beta.ui.deta.forumias.notification.NotificationModel;
import com.forumias.beta.ui.deta.forumias.page.page_paging_adapter.PagePostModel;
import com.forumias.beta.ui.deta.forumias.profile.model.FollowersModel;
import com.forumias.beta.ui.deta.forumias.profile.model.ProfileSettingModel;
import com.forumias.beta.ui.deta.forumias.save_comment.RemoveCommentModel;
import com.forumias.beta.ui.deta.forumias.save_comment.SaveCommentDetailsModel;
import com.forumias.beta.ui.deta.forumias.save_comment.SaveCommentModel;
import com.forumias.beta.ui.deta.forumias.splash.PostSettingModel;
import com.forumias.beta.ui.deta.forumias.splash.UserInfoModel;
import com.forumias.beta.ui.deta.forumias.splash.VersionModel;
import com.forumias.beta.ui.deta.forumias.user.user_paging.UserModel;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.UserCommentModel;
import com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.user_post_paging.UserPostModel;
import com.forumias.beta.ui.loginfragment.CheckDublicateModel;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    @POST("v2/studentregister")
    Call<RegisterBaseResponse> postRegisterData(@Body Map<String, String> register);
    @POST("v2/verifymobileandsendotp")
    Call<RegisterBaseResponse> getOtpNumber(@Body Map<String, String> register);
    @POST("v2/verifyotp")
    Call<OtpVerificationResponse> sendOtpVerification(@Body Map<String, String> register);
    @POST("v2/studentlogin")
    Call<OtpVerificationResponse> loginStudent(@Body Map<String, String> register);
    @POST("v2/VerifymobileAndSendOTPAfterLoginSucccess")
    Call<RegisterBaseResponse> getOtp(@Body Map<String, String> register);
    @POST("v2/verifyotpAfterLoginSuccess")
    Call<LoginOtpVerification> verificationOtpLogin(@Body Map<String, String> register);
    @POST("v2/loginusingpassword")
    Call<LoginWithPasswordModel> LoginWithPassword(@Body Map<String, String> register);
    @POST("v2/SendMagicCode")
    Call<RegisterBaseResponse> LoginWithMagicLink(@Body Map<String, String> register);
    @POST("v2/studentresetpasswordrequest")
    Call<DefaultPojo> requestForgotPass(@Body Map<String, String> register);
    @GET("followers")
    Call<FollowingORFollowerResponse> getFollOrFollowingData(@Query("user_id") String userId);
    @GET("search_post")
    Call<SearchModel> fetchSearchPost(@Query("search") String search);
   // @GET("search.php")
   // Call<SearchModel> searchModel(@Query("search_title") String data);
    @GET("post_detail")
    Call<PostDetailsModel> fetchPostDetails(@Query("slug") String slug, @Query("user_id") int userId);
    @GET("channels")
    Call<ChannelModel> fetchChannelListData(@Query("user_id") int userId , @Query("type") String channelType,
                                            @Query("page") int pageNumber);
    @GET("channel/dashboard")
    Call<ChannelPostModel> fetchChannelPost(@Query("slug") String slug, @Query("page") int pageNumber,
                                            @Query("user_id") int userLoginId);
    @GET("dashboard")
    Call<HomeLatestPostModel> fetchHomeMySpacePost(@Query("user_id") String userId);
    @GET("featured")
    Call<HomeLatestPostModel> fetchHomeFeaturedPost(@Query("user_id") String userId);
  /*  @GET("latest")
    Call<HomeLatestPostModel> fetchHomeLatestPost(@Query("user_id") String userId);  */
    @GET("latest")
    Call<HomeLatestPostModel> fetchHomeLatestPost(@Query("user_id") String userId , @Query("page") int pageNumber);
    @GET("myspace")
    Call<HomeLatestPostModel> fetchHomeMySpancePost(@Query("user_id") String userId , @Query("page") int pageNumber);
    @GET("users")
    Call<UserModel> fetchUserListData(@Query("user_id") int userId, @Query("page") int page, @Query("tab") String tag);
    @GET("user_pop_detail")
    Call<PostCountModel> getPopupDetails(@Query("user_name") String userName);
    @GET("groups")
    Call<GroupModel> getGroupList(@Query("user_id") int userId);

    @GET("groups")
    Call<GroupModel> getMyPageList(@Query("user_id") int userId , @Query("tab") String tabName);

    @GET("user_detail")
    Call<UserInfoAndPostModel> getUserInfoPost(@Query("username") String userName);
    @GET("user_info")
    Call<UserInfoModel> verifiedLoginUser(@Query("username") String userName , @Query("user_id") int loginUserId);
    @GET("follow_unfollow_tag")
    Call<FollowUnFollowModel> getFollowUnFollowing(@Query("user_id") String userId, @Query("tg_id") String tagId,
                                                   @Query("status") String status, @Query("act_type") String actType);
    @POST("request_private_channel")
    Call<Private_channel_Subscribe> getPrivaetChannelSubscribe(@Query("act_type") int actType ,
                                                               @Query("channel_id") int channelId , @Query("user_id") int userId);
    @GET("group")
    Call<GroupPostDetailsModel> fetchGroupPostDetails(@Query("slug") String tagSlug);
    @GET("group")
    Call<PagePostModel> fetchChannelPostDetails(@Query("slug") String tagSlug, @Query("page") int pageNumber);
    // this three api change in future
   // @GET("subscribed")
   // Call<GroupFollowingModel> fetchGroupFollowing(@Query("user_id") String userId , @Query("following") String slugName);
  //  @GET("subscribed")
   // Call<ChannelFollowingModel> fetchChannelFollowing(@Query("user_id") String userId , @Query("following") String slugName);
    @GET("subscribed")
    Call<PagesFollowingModel> fetchPagesFollowing(@Query("user_id") String userId, @Query("following") String slugName);
    /*@GET("reading_list")
    Call<ReadingModel> fetchReadingList(@Query("auth_id") String authId);*/

    @GET("reading_list")
    Call<ReadingModel> fetchReadingList(@Query("user_id") int  userLoginId , @Query("page") int pageNumber);

    @GET("bookmark_action")
    Call<BookMarkResponse> fetchBookMarkData(@Query("post_id") String postId, @Query("user_id") String userId,
                                             @Query("status") String status);
    @GET("mobile_login_signup")
    Call<BookMarkResponse> getLoginVerification(@Query("auth_id") int authId);
    @GET("general_web_setting")
    Call<PostSettingModel> getPostSettingData(@Query("user_id") String userId);
    @GET("v0/post_likes")
    Call<LikeUserModel> getLikeUser(@Query("post_id") String postId);
    @GET("v0/check_follow")
    Call<CheckFollowModel> checkFollow(@Query("type") int type , @Query("follow_to") int followTo,
                                       @Query("follow_from") int followFrom);
    @GET("v0/get_comments")
    Call<AllCommentModel> getAllCommentHome(@Query("post_id") int post_id , @Query("user_id") int user_id,
                                            @Query("page") int pageNumber);

    @GET("v0/get_comments")
    Call<UseFullCommentModel> getUseFullCommentHome(@Query("post_id") int post_id , @Query("user_id") int user_id,
                                                    @Query("page") int pageNumber , @Query("useful") int position);
    @POST("v0/like_unlike")
    Call<LikeResponseModel> checkLikeUnLike(@Query("post_id") int  postId , @Query("user_id") int  userId);

  /*  @GET("user_detail")
    Call<UserPostModel> getUserPostList(@Query("username") String userName , @Query("page") int pageNumber);*/
    @GET("user_detail")
    Call<UserPostModel> getUserPostList(@Query("tab")String tab , @Query("username") String userName , @Query("page") int pageNumber);
    @GET("page_info")
    Call<PageDetailsModel> getPageDetails(@Query("slug") String pageName);

  /*  @GET("create_story")
    Call<DefaultPojo> createStory(@Query("user_id") String userId , @Query("title") String title ,
                                  @Query("description") String description,@Query("tag_id") String tagId,
                                  @Query("file_upload") String fileUpload);*/
  @POST("create_story")
    Call<CreateStoryModel> createStory(@Body Map<String  , String > createStory);
  @POST("post_comment")
  Call<CreateStoryModel> createCommentOnPost(@Body Map<String , String> createPostComment);

    @Multipart
    @POST("jack_up.php?act_type=post")
    Call<String> postImageFile(@Part MultipartBody.Part file);

    @Multipart
    @POST("/api/v2/updateProfilePicture")
    Call<ProfileImageModel> postUserImageImageFile(@Part MultipartBody.Part file , @Part("auth_id") int authID);


    @GET("saved_comments")
    Call<SaveCommentModel> getSaveComment(@Query("user_id") int userId);
    @POST("save_delete_comment")
    Call<RemoveCommentModel> saveDeleteComment(@Query("is_delete") int isDelete , @Query("user_id") int userId ,
                                               @Query("post_id") int postId , @Query("comment_id") int commentId,
                                               @Query("saved_comment_id") int saveCommentId);

   @GET("comment_detail")
    Call<SaveCommentDetailsModel> getSaveCommentDetails(@Query("post_slug") String slug , @Query("comment_id") int commentId);
   @POST("upvote_downvote")
    Call<UpVoteModel> upVoteOnComment(@Query("post_id") int postId, @Query("user_id") int userId
                            , @Query("comment_id") int commentId);

   @GET("my_post_detail")
   Call<MyPostDetailsModel> getPostDetails(@Query("slug") String slug);

    @GET("channel_info")
    Call<ChannelDetailsInfo> getChannelDetailsInfo(@Query("slug") String slug, @Query("user_id") int userId);

    @GET("notifications")
    Call<NotificationModel> getNotificationData(@Query("user_id") int userId, @Query("page") int page);

    @POST("v0/add_to_myspace")
    Call<AddMySpaceModel> addToMySpace(@Query("user_id") int userId , @Query("post_id") int postId);
    @POST("v0/settings")
    Call<ProfileSettingModel> postProfileSettingData(@Query("user_id") int userId , @Query("hide_real_name") int hideRealName ,
                                                     @Query("follow_back") int followBack , @Query("about") String aboutUs);
    @POST("v0/email_settings")
    Call<ProfileSettingModel> emailNotificationSetting(@Query("user_id")int userId,@Query("post") int post ,@Query("tag")int tag, @Query("follow_me") int followMe
            , @Query("group_accepted") int groupAccepted , @Query("channel_accepted") int chennelActed ,
              @Query("answer_question") int ansQuestion , @Query("comment_like") int commentLike , @Query("request_ans") int requestAns);

    /*post,tag,follow_me,group_accepted,channel_accepted,answer_question,comment_like,request_ans*/

    @GET("followers")
    Call<FollowersModel> getfollowersLis(@Query("user_id") int userId);



    @POST("v0/report_post")
    Call<ReportModel> postReport(@Query("post_id") int postId , @Query("user_id") int userId ,
                                 @Query("report_option")  int reportOption , @Query("other_description") String description);

    @GET("v0/user_comment")
    Call<UserCommentModel> getAllComment(@Query("post_id") int postId , @Query("user_id") int userId);

    @POST("v0/reading_list_action")
    Call<ResponseBody> readingListDelete(@Query("user_id") int loginUserId , @Query("action_type") int actionType,
                                         @Query("saved_id") int postId);

    @GET("v0/get_share_cmnt_url")
    Call<ShareCommentModel> getShareUrl(@Query("comment_id") int commentId);

    @GET("v0/read_notification")
    Call<ReportModel> getNotificationReading(@Query("user_id") int loginUserId , @Query("notify_id") int notificationId);

    @GET("get_version")
    Call<VersionModel> getNewVerison();

    @GET("homeNotice")
    Call<AdsController> getAdsController();

    @GET("load_ads")
    Call<AdsControllerImage> getAdsImageController(@Query("acreen") int screen);


    @GET("v0/inbox")
    Call<IndexMessageModel> getMessageIndex(@Query("user_id") int userId);

    @GET("v0/message")
    Call<ChatListModel> getUserChat(@Query("inbox_user") int indexUserId , @Query("to_user") int toUserId);

    /*@FormUrlEncoded
    @POST("send_message")
    Call<ChatModel> postMessage(@Field("from_user") int fromUser,
                                @Field("to_user") int toUser , @Field("chat_id") int chatId ,
                                @Field("my_msg") String message);*/

    @POST("v0/send_message")
    Call<ChatModel> postMessage(@Body Map<String  , String > message);

    @GET("v0/load_poll")
    Call<PollModel> getPoll(@Query("post_id") int postId , @Query("user_id") int  userId);

    @GET("v2/chekUserDetail/{data}")
    Call<CheckDublicateModel> checkDublicate(@Path("data") String data);

    @GET("v0/get_comment_info")
    Call<DeepCommentModel> getCommentShare(@Query("comment_id") String commentId , @Query("user_id") int userId);

}
