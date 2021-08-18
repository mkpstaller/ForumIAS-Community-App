package com.forumias.beta.ui.deta.forumias.user.user_profile_and_post.user_post_paging;

import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;


public class UserPostViewModel extends ViewModel  {
    private LiveData<PagedList<UserPostModel.MyStoriesList>> itemList;
    private MutableLiveData<PageKeyedDataSource<Integer, UserPostModel.MyStoriesList>> liveDataSource;

   public LiveData<PagedList<UserPostModel.MyStoriesList>> itemPagedList(String name, ProgressBar progressBar
           , String tab , AppCompatTextView tvNoDataFound){

        if (itemList == null) {
            itemList = new MutableLiveData<>();
            //we will load it asynchronously from server in this method
            loadHomePost(name,progressBar , tab , tvNoDataFound);
        }

        return itemList;
    }



    private void loadHomePost(String name,ProgressBar progressBar , String tab , AppCompatTextView tvNoDataFound) {
        UserPostDataSourceFactory userPostDataSourceFactory = new UserPostDataSourceFactory(name,progressBar , tab , tvNoDataFound);
        liveDataSource = userPostDataSourceFactory.getItemLiveDataSource();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(UserPostDataSource.PAGE_SIZE)
                        .build();

        itemList = (new LivePagedListBuilder(userPostDataSourceFactory, config)).build();

    }


}
