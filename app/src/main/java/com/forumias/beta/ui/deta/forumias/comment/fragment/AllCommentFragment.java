package com.forumias.beta.ui.deta.forumias.comment.fragment;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.chinalwb.are.AREditor;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.myinterface.ReplyInterface;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.ShareCommentModel;
import com.forumias.beta.ui.deta.forumias.ImageViewActivity;
import com.forumias.beta.ui.deta.forumias.comment.adapter.UseFullCommentPostAdapter;
import com.forumias.beta.ui.deta.forumias.comment.fragment.all_comment_paging.AllCommentViewModel;
import com.forumias.beta.ui.deta.forumias.comment.fragment.all_comment_paging.CommentAdapter;
import com.forumias.beta.ui.deta.forumias.comment.fragment.all_comment_paging.SaveCommentInterfacae;
import com.forumias.beta.ui.deta.forumias.comment.fragment.model.AllCommentModel;
import com.forumias.beta.ui.deta.forumias.comment.fragment.model.UseFullCommentModel;
import com.forumias.beta.ui.deta.forumias.comment.ui.model.UpVoteModel;
import com.forumias.beta.ui.deta.forumias.create_story.CreateStoryModel;
import com.forumias.beta.ui.deta.forumias.database.CommentListDatabase;
import com.forumias.beta.ui.deta.forumias.database.PostTable;
import com.forumias.beta.ui.deta.forumias.reply_quote.ReplyQuoteOnCommentActivity;
import com.forumias.beta.ui.deta.forumias.save_comment.RemoveCommentModel;
import com.forumias.beta.ui.login.LoginActivity;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;
import com.forumias.beta.api.APIInterface;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllCommentFragment extends Fragment implements ReplyInterface, SaveCommentInterfacae {
    @BindView(R.id.rvCommentRecyclerView)
    RecyclerView rvCommentRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rlLoginInfo)
    RelativeLayout rlLoginInfo;
    @BindView(R.id.tvInfo)
    AppCompatTextView tvLoginMessage;
    @BindView(R.id.bottom_sheet)
    LinearLayoutCompat bottom_sheet;
    @BindView(R.id.fabCommentButton)
    FloatingActionButton fabCommentButton;
    @BindView(R.id.fabCommentSend)
    FloatingActionButton fabCommentSend;
    @BindView(R.id.tvNameOnComment)
    AppCompatTextView tvNameOnComment;
    @BindView(R.id.tvNothingShow)
    AppCompatTextView tvNothingShow;
    @BindView(R.id.codlCommentSection)
    CoordinatorLayout codlCommentSection;

    AppCompatImageView fabCommentMore;

    private BottomSheetBehavior sheetBehavior;
    private UseFullCommentPostAdapter useFullCommentPostAdapter;
    private CommentAdapter commentAdapter;
    private LinearLayoutManager layoutManager;
    AppCompatTextView tvTotalCommentCount;
    private MyPreferenceData myPreferenceData;

    private int postId, position, loginUserId , callClassStatus , darkModeStatus , totalCommentCount;
    private AREditor arEditor;
    private static int firstVisibleInListview = 1;
    private int commentPos = 0;
    private CommentListDatabase commentListDatabase;
    PostTable postTable1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_comment, container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            postId = bundle.getInt(BaseUrl.POST_ID);
            position = bundle.getInt(BaseUrl.POSITION);
            callClassStatus = bundle.getInt(BaseUrl.CALL_CLASS);
        }
        myPreferenceData = new MyPreferenceData(Objects.requireNonNull(getContext()));
        loginUserId = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        darkModeStatus = myPreferenceData.getIntegerData(BaseUrl.DARK_MODE);

        myPreferenceData.saveIntegerData(BaseUrl.COMMENT_POST_ID , postId);

        fabCommentMore = Objects.requireNonNull(getActivity()).findViewById(R.id.fabCommentMore);
        tvTotalCommentCount = Objects.requireNonNull(getActivity()).findViewById(R.id.tvTotalCommentCount);
       // Log.e("total Comment ===> " , tvTotalCommentCount.getText().toString());
        totalCommentCount = Integer.parseInt(tvTotalCommentCount.getText().toString());
        layoutManager = new LinearLayoutManager(getContext());
        rvCommentRecyclerView.setLayoutManager(layoutManager);


       // Log.e("comment count ==> " , String.valueOf(myPreferenceData.getIntegerData(BaseUrl.COMMENT_POS)));
        commentPos = myPreferenceData.getIntegerData(BaseUrl.COMMENT_POS);

        commentListDatabase = Room.databaseBuilder(getContext(), CommentListDatabase.class,
                CommentListDatabase.DB_NAME).fallbackToDestructiveMigration().build();

        setDarkModeView();

        showTabPosition(position);
        this.arEditor = view.findViewById(R.id.postEditor);

        return view;
    }

    private void getCommentPosition(int postId) {
        new AsyncTask<Integer , Void , PostTable>(){

            @Override
            protected PostTable doInBackground(Integer... params) {
                return commentListDatabase.doaCommment().getPostPosition(params[0]);
            }

            @Override
            protected void onPostExecute(PostTable postTable) {
                super.onPostExecute(postTable);
               if(postTable != null){
                   getCommentPostData(postTable);
               }

            }
        }.execute(postId);
    }

    private void getCommentPostData(PostTable postTable) {
       // Log.e("database Data =====> " , postTable.commentPos +"       "+ postTable.postId);

        if(postTable.commentPos != 0) {
            int commentCount = postTable.commentPos;

            if (String.valueOf(postTable.postId).contains(String.valueOf(postId))) {
                new Handler().postDelayed(() -> {

                    if (commentCount != 0) {
                       // Log.e("data=============> " , "dfsdfsdfsdfsd"+commentCount);
                        layoutManager.computeScrollVectorForPosition(commentCount);
                        rvCommentRecyclerView.findViewHolderForAdapterPosition(commentCount);
                       // layoutManager.scrollToPositionWithOffset(commentCount , 100);
                        rvCommentRecyclerView.scrollToPosition(commentCount);
                        rvCommentRecyclerView.smoothScrollToPosition(commentCount);
                        layoutManager.onRestoreInstanceState(layoutManager.onSaveInstanceState());

                    }
                }, 1000);
            }
        }

    }

    private void setDarkModeView() {
        if(darkModeStatus == 1){
            codlCommentSection.setBackgroundResource(R.color.darkmode_back_color);
        }else{
            codlCommentSection.setBackgroundResource(R.color.back_color);
        }
    }

    private void showTabPosition(int position) {
      switch (position) {
            case 0:
                addComment(0);
                break;
            case 1:
                addUseFullComment();
                break;
        }
    }


    @OnClick(R.id.fabCommentSend)
    public void onClickCommentSend(){
        String description = this.arEditor.getARE().getHtml();
        if (Utility.isNullOrEmpty(description)) {
            Toast.makeText(getContext(), "Description field is required..!", Toast.LENGTH_LONG).show();
            return;
        }
        //onMessageReadListener.onMessageRead(true);
        createCommentOnPost(description);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        this.arEditor.onActivityResult(requestCode, resultCode, data,"image");
    }


    private void createCommentOnPost(String description) {

        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Please Wait..!");
        dialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("user_id", String.valueOf(loginUserId));
        params.put("post_id", String.valueOf(postId));
        params.put("description", description);
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<CreateStoryModel> call = apiInterface.createCommentOnPost(params);
        call.enqueue(new Callback<CreateStoryModel>() {
            @Override
            public void onResponse(@NotNull Call<CreateStoryModel> call, @NotNull Response<CreateStoryModel> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getStatus().equals(BaseUrl.SUCCESS)) {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                        fabCommentButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_comment_white));
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<CreateStoryModel> call, @NotNull Throwable t) {
                dialog.dismiss();
            }
        });
    }


    private void addUseFullComment() {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        retrofit2.Call<UseFullCommentModel> call = apiInterface.getUseFullCommentHome(postId, loginUserId, 0, 1);
        call.enqueue(new Callback<UseFullCommentModel>() {
            @Override
            public void onResponse(@NotNull Call<UseFullCommentModel> call, @NotNull Response<UseFullCommentModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResult().size() > 0) {
                        addUseFullAdapter(response.body().getResult());
                        rlLoginInfo.setVisibility(View.GONE);
                    } else {
                        rlLoginInfo.setVisibility(View.VISIBLE);
                        tvNothingShow.setVisibility(View.GONE);

                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<UseFullCommentModel> call, @NotNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                rlLoginInfo.setVisibility(View.VISIBLE);
            }
        });

    }

    private void addUseFullAdapter(List<AllCommentModel.Result.CommentList> commentLists) {
        rlLoginInfo.setVisibility(View.GONE);
        useFullCommentPostAdapter = new UseFullCommentPostAdapter(commentLists
                , getContext(), this ,this , callClassStatus);
        rvCommentRecyclerView.setAdapter(useFullCommentPostAdapter);
    }

    private void addComment(int position) {
        rlLoginInfo.setVisibility(View.GONE);
        commentAdapter = new CommentAdapter(getContext(), this ,
                this , callClassStatus , totalCommentCount , tvTotalCommentCount);
        AllCommentViewModel allCommentViewModel = ViewModelProviders.of(this).get(AllCommentViewModel.class);
        allCommentViewModel.itemAllCommentPageList(loginUserId, postId, progressBar, position, rlLoginInfo, getContext()).observe(this, commentAdapter::submitList); // use function Interface  actual code ( commentAdapter.submitList(commentLists);)
        rvCommentRecyclerView.setAdapter(commentAdapter);
        firstVisibleInListview = layoutManager.findFirstVisibleItemPosition();

        getCommentPosition(postId);



        fabCommentMore.setOnClickListener(view1 -> {
           layoutManager.scrollToPosition(layoutManager.findFirstCompletelyVisibleItemPosition() + totalCommentCount);
                  rvCommentRecyclerView.smoothScrollToPosition(totalCommentCount);
            Log.e("move pos===> " , String.valueOf(totalCommentCount));
            layoutManager.computeScrollVectorForPosition(totalCommentCount);
            rvCommentRecyclerView.findViewHolderForAdapterPosition(totalCommentCount);
            layoutManager.onSaveInstanceState();

          //  layoutManager.setStackFromEnd(true);
           // layoutManager.setReverseLayout(true);
           // rvCommentRecyclerView.setLayoutManager(layoutManager);
           // rvCommentRecyclerView.scrollToPosition(totalCommentCount);
          //  rvCommentRecyclerView.smoothScrollToPosition(totalCommentCount);

        });
    }


    private void callLoginIntent(){
        Intent intent = new Intent(getActivity() , LoginActivity.class);
        Objects.requireNonNull(getActivity()).startActivity(intent);
        getActivity().finish();

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void replyOnComment(String userName  ,String description , int callClassStatus) {

        Intent intent = new Intent(getContext() , ReplyQuoteOnCommentActivity.class);
        intent.putExtra(BaseUrl.NAME , userName);
        intent.putExtra(BaseUrl.DESCRIPTION , description);
        intent.putExtra(BaseUrl.POST_ID , postId);
        intent.putExtra(BaseUrl.TYPE , 1);
        intent.putExtra(BaseUrl.CALL_CLASS , callClassStatus);
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void quoteOnComment(String userName, String description , int callClassStatus) {

        Intent intent = new Intent(getContext() , ReplyQuoteOnCommentActivity.class);
        intent.putExtra(BaseUrl.NAME , userName);
        intent.putExtra(BaseUrl.DESCRIPTION , description);
        intent.putExtra(BaseUrl.POST_ID , postId);
        intent.putExtra(BaseUrl.TYPE , 2);
        intent.putExtra(BaseUrl.CALL_CLASS , callClassStatus);
        startActivity(intent);
    }

    @Override
    public void upVoteOnComment(int postId, int userId, int commentId, AppCompatImageView ivUpVote,
                                AppCompatTextView tvUpVote , LinearLayoutCompat llUpVoteSection) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<UpVoteModel> call = apiInterface.upVoteOnComment(postId , loginUserId , commentId);

        call.enqueue(new Callback<UpVoteModel>() {
            @Override
            public void onResponse(@NotNull Call<UpVoteModel> call, @NotNull Response<UpVoteModel> response) {
                int count = 0;
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        if(response.body().getAction() == 1){
                            ivUpVote.setImageDrawable(Objects.requireNonNull(getContext()).getResources()
                                    .getDrawable(R.drawable.id_upvote_blue));
                          //  llUpVoteSection.setBackgroundResource(R.drawable.qoute_shape_blue);
                            int upVoteCount = Integer.parseInt(tvUpVote.getText().toString().trim());
                            count = upVoteCount+1;
                            tvUpVote.setText(String.valueOf(count));
                            AllCommentModel.Result.CommentList.UpVotes upVotesModel = new AllCommentModel.Result.CommentList.UpVotes();
                            upVotesModel.setUser_ids(String.valueOf(loginUserId));
                            upVotesModel.setUpvote_count(count);
                        }else{
                            ivUpVote.setImageDrawable(Objects.requireNonNull(getContext()).getResources()
                                    .getDrawable(R.drawable.ic_upvote_dark));
                          //  llUpVoteSection.setBackgroundResource(R.drawable.reply_shape);
                            int upVoteCount = Integer.parseInt(tvUpVote.getText().toString().trim());
                            count = upVoteCount-1;
                            tvUpVote.setText(String.valueOf(count));
                            AllCommentModel.Result.CommentList.UpVotes upVotesModel = new AllCommentModel.Result.CommentList.UpVotes();
                            upVotesModel.setUser_ids("001");
                            upVotesModel.setUpvote_count(count);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<UpVoteModel> call, @NotNull Throwable t) {

            }
        });
    }

    @Override
    public void shareComment(int commentId) {
        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<ShareCommentModel> call = apiInterface.getShareUrl(commentId);
        call.enqueue(new Callback<ShareCommentModel>() {
            @Override
            public void onResponse(Call<ShareCommentModel> call, Response<ShareCommentModel> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                       shareData(response.body().getComment_url());
                    }
                }
            }

            @Override
            public void onFailure(Call<ShareCommentModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void showImageView(String url) {
     /*   Bundle bundle = new Bundle();
        bundle.putString(BaseUrl.IMAGE , url);
        ImagePostDialogFragment alert  = new ImagePostDialogFragment();
        alert.show(getChildFragmentManager() ,"image");
        alert.setArguments(bundle);*/

     Log.e("image=11111==> " , url);
     Intent intent = new Intent(getContext() , ImageViewActivity.class);
     intent.putExtra(BaseUrl.IMAGE , url);
     startActivity(intent);
    }

    private void shareData(String comment_url) {
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_TEXT,comment_url);
        share.setType("text/plain");

        getContext().startActivity(Intent.createChooser(share,"Share More"));
    }


    @Override
    public void saveComment(int loginUserId, int saveCommentId, int postId, int commentId
            , int isDelete , AppCompatImageView ivSaveComment ,boolean status) {

        BetaApiClient betaApiClient = new BetaApiClient();
        APIInterface apiInterface = betaApiClient.getClient().create(APIInterface.class);
        Call<RemoveCommentModel> call = apiInterface.saveDeleteComment(isDelete,loginUserId,postId
                ,commentId ,saveCommentId);
        call.enqueue(new Callback<RemoveCommentModel>() {
            @Override
            public void onResponse(@NotNull Call<RemoveCommentModel> call, @NotNull Response<RemoveCommentModel> response) {

                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getStatus().equals(BaseUrl.SUCCESS)){
                        AllCommentModel.Result.CommentList model = new AllCommentModel.Result.CommentList();
                        model.setSaveCommentStatus(true);
                        if(status){
                            ivSaveComment.setImageDrawable(Objects.requireNonNull(getContext()).getResources().getDrawable(R.drawable.ic_yellow_star));

                        }else{
                            ivSaveComment.setImageDrawable(Objects.requireNonNull(getContext()).getResources().getDrawable(R.drawable.ic_star));

                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<RemoveCommentModel> call, @NotNull Throwable t) {

            }
        });

    }

}
