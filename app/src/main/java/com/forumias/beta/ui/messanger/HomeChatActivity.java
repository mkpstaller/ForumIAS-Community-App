package com.forumias.beta.ui.messanger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.api.BetaApiClient;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.pojo.FollowingORFollowerResponse;
import com.forumias.beta.ui.loginfragment.FollowerFragment;
import com.forumias.beta.ui.loginfragment.FollowingFragment;
import com.forumias.beta.ui.loginfragment.GalleryFragment;
import com.forumias.beta.ui.loginfragment.UserChatFragment;
import com.forumias.beta.utility.InternetConnection;
import com.forumias.beta.api.APIInterface;
import com.forumias.beta.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeChatActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    MaterialToolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.fabSelectChatUser)
    FloatingActionButton fabSelectChatUser;
    boolean doubleBackToExitPressedOnce = false;
    FollowerFragment followerFragment;
    FollowingFragment followingFragment;
    GalleryFragment galleryFragment;
    UserChatFragment userChatFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_chat);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        MyPreferenceData myPreferenceData = new MyPreferenceData(this);
        int id = myPreferenceData.getIntegerData(BaseUrl.USER_ID);
        Log.e("user id :- ", ""+id);

        followerFragment = new FollowerFragment();
        followingFragment = new FollowingFragment();
        galleryFragment = new GalleryFragment();
        userChatFragment = new UserChatFragment();


        boolean internetStatus = new InternetConnection().checkConnection(this);
        if (internetStatus) {
            // loadFollowingAndFollowing();
        } else {
            checkConection();
        }

        loadFollowingAndFollowing();
    }

    private void loadFollowingAndFollowing() {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading Data...!");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        APIInterface apiInterface = new BetaApiClient().getClient().create(APIInterface.class);
        Call<FollowingORFollowerResponse> call = apiInterface.getFollOrFollowingData("6");
        call.enqueue(new Callback<FollowingORFollowerResponse>() {
            @Override
            public void onResponse(Call<FollowingORFollowerResponse> call, Response<FollowingORFollowerResponse> response) {
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                if (response.body().getStatus().equals(BaseUrl.SUCCESS)) {
                    setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<FollowingORFollowerResponse> call, Throwable t) {
                t.printStackTrace();
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
            }
        });

    }

    private void setData(FollowingORFollowerResponse response) {
        Bundle bundle = new Bundle();
        Bundle bundle1 = new Bundle();
        Gson gson = new Gson();
        Type follower = new TypeToken<List<FollowingORFollowerResponse.Follower>>() {
        }.getType();
        Type following = new TypeToken<List<FollowingORFollowerResponse.Following>>() {
        }.getType();
        String followerData = gson.toJson(response.getGetFollowerList(), follower);
        String followingData = gson.toJson(response.getGetFollowingList(), following);
        bundle.putString(BaseUrl.FOLLOWER_DATA, followerData);
        bundle1.putString(BaseUrl.FOLLOWING_DATA, followingData);
        followerFragment.setArguments(bundle);
        followingFragment.setArguments(bundle1);
        setupViewPager(viewPager, followerFragment, followingFragment, galleryFragment, userChatFragment);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void checkConection() {
        Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.connection_layout);
        AppCompatImageView showImage = dialog.findViewById(R.id.showImage);
        AppCompatTextView tvTryAgain = dialog.findViewById(R.id.tvTryAgain);
        tvTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                startActivity(intent);
            }
        });
        Glide.with(this).load(R.drawable.nointernet).into(showImage);

        dialog.show();

    }

    @OnClick(R.id.fabSelectChatUser)
    public void onClickFab() {
        Intent intent = new Intent(HomeChatActivity.this, AllFriendListActivity.class);
        startActivity(intent);
    }

    private void setupViewPager(ViewPager viewPager, FollowerFragment followerFragment,
                                FollowingFragment followingFragment, GalleryFragment galleryFragment,
                                UserChatFragment userChatFragment) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(userChatFragment, getString(R.string.chat));
        viewPagerAdapter.addFragment(followerFragment, getString(R.string.follower));
        viewPagerAdapter.addFragment(followingFragment, getString(R.string.following));
        //viewPagerAdapter.addFragment(galleryFragment , getString(R.string.gallery));
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}

