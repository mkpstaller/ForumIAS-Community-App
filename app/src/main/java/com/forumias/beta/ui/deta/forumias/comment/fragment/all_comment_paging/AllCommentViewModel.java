package com.forumias.beta.ui.deta.forumias.comment.fragment.all_comment_paging;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.forumias.beta.ui.deta.forumias.comment.fragment.model.AllCommentModel;


public class AllCommentViewModel extends ViewModel {
    private LiveData<PagedList<AllCommentModel.Result.CommentList>> itemLiveData;
    private MutableLiveData<PageKeyedDataSource<Integer , AllCommentModel.Result.CommentList>> itemLiveDataSource;


    public LiveData<PagedList<AllCommentModel.Result.CommentList>> itemAllCommentPageList(int loginUserId ,
         int postId , ProgressBar progressBar , int position , RelativeLayout tvNothingShow , Context context){
        if(itemLiveData == null){
            itemLiveData = new  MutableLiveData<>();
            loadAllCommentList(loginUserId , postId ,progressBar , position , tvNothingShow , context);
        }
        return itemLiveData;
    }

    private void loadAllCommentList(int loginUserId , int postId , ProgressBar progressBar , int position,
                                    RelativeLayout tvNothingShow , Context context) {
            AllCommentDataSourceFactory allCommentDataSourceFactory = new AllCommentDataSourceFactory(loginUserId ,
                    postId,progressBar , position,tvNothingShow , context);
            itemLiveDataSource = allCommentDataSourceFactory.getAllCommentDataSource();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(AllCommentDataSource.PAGE_SIZE)
                        .build();

        itemLiveData = (new LivePagedListBuilder(allCommentDataSourceFactory, config)).build();
    }
}
