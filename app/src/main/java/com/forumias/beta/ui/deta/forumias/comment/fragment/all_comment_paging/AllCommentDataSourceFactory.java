package com.forumias.beta.ui.deta.forumias.comment.fragment.all_comment_paging;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.forumias.beta.ui.deta.forumias.comment.fragment.model.AllCommentModel;

public class AllCommentDataSourceFactory extends DataSource.Factory {
    private int loginUserId , postId , position;
    private ProgressBar progressBar;
    private RelativeLayout tvNothingShow;
    Context context;
    public AllCommentDataSourceFactory(int loginUserId , int postId , ProgressBar progressBar , int position,
                                       RelativeLayout tvNothingShow , Context context){
        this.loginUserId = loginUserId;
        this.postId = postId;
        this.progressBar = progressBar;
        this.position = position;
        this.tvNothingShow = tvNothingShow;
        this.context = context;
    }

    private MutableLiveData<PageKeyedDataSource<Integer , AllCommentModel.Result.CommentList>> itemLiveDataSource =
            new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource create() {
        AllCommentDataSource allCommentDataSource = new AllCommentDataSource(loginUserId , postId,progressBar, position,
                tvNothingShow , context);
        itemLiveDataSource.postValue(allCommentDataSource);
        return allCommentDataSource;
    }

   public MutableLiveData<PageKeyedDataSource<Integer , AllCommentModel.Result.CommentList>> getAllCommentDataSource(){
        return itemLiveDataSource;
    }

}
