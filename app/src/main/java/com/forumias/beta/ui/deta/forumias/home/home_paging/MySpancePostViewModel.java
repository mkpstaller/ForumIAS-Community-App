package com.forumias.beta.ui.deta.forumias.home.home_paging;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.forumias.beta.ui.deta.forumias.home.model.HomeLatestPostModel;


public class MySpancePostViewModel extends ViewModel  {
    private LiveData<PagedList<HomeLatestPostModel.MyLatestPost>> itemList;
    private MutableLiveData<PageKeyedDataSource<Integer, HomeLatestPostModel.MyLatestPost>> liveDataSource;

   public LiveData<PagedList<HomeLatestPostModel.MyLatestPost>> itemPagedList(int aricleVisible,
                                                                              int loginUserId, ProgressBar progressBar
           , AppCompatTextView tvNotDataFound , RelativeLayout rlProgress){

        if (itemList == null) {
            itemList = new MutableLiveData<>();
            //we will load it asynchronously from server in this method
            loadHomePost(aricleVisible,loginUserId,progressBar , tvNotDataFound ,rlProgress);
        }

        return itemList;
    }

    public void invalidateDataSource() {
        liveDataSource.getValue().invalidate();
    }

    private void loadHomePost( int articleVisible, int loginUserId, ProgressBar progressBar
            , AppCompatTextView tvNotDataFound  , RelativeLayout rlProgress) {
        MySpacePostDataSourceFactory latestPostDataSourceFactory = new MySpacePostDataSourceFactory( articleVisible,
                loginUserId,progressBar , tvNotDataFound , rlProgress);
        liveDataSource = latestPostDataSourceFactory.getItemLiveDataSource();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(LatestPostDataSource.PAGE_SIZE)
                        .build();

        itemList = (new LivePagedListBuilder(latestPostDataSourceFactory, config)).build();

    }


}
