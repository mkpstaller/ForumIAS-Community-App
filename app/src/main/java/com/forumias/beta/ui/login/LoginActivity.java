package com.forumias.beta.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.mypreference.MyPreferenceData;
import com.forumias.beta.ui.loginfragment.FragmentLogin;
import com.forumias.beta.ui.loginfragment.FragmentSignUp;
import com.forumias.beta.R;

import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity{
    boolean doubleBackToExitPressedOnce = false;
    FragmentManager fragmentManager;
    int status = 0 , statusLogin = 1;

    /*@BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        MyPreferenceData myPreferenceData = new MyPreferenceData(this);
       // Log.e("My Login Status==> ", String.valueOf(myPreferenceData.getIntegerData(BaseUrl.STATUS)));
        status = myPreferenceData.getIntegerData(BaseUrl.STATUS);

       fragmentManager = getSupportFragmentManager();
            if (findViewById(R.id.login_framelayout) != null) {
                if (savedInstanceState != null) {
                    return;
                }

                Bundle bundle = getIntent().getExtras();
                assert bundle != null;
                statusLogin = bundle.getInt(BaseUrl.STATUS);
               // Log.e("stata====> ", String.valueOf(statusLogin));
                if(statusLogin == 1){
                    fragmentManager.beginTransaction().add(R.id.login_framelayout, new FragmentLogin()).addToBackStack(null).commit();
                }else{
                    fragmentManager.beginTransaction().add(R.id.login_framelayout, new FragmentSignUp()).addToBackStack(null).commit();
                }

            }
    }

  @Override public void onBackPressed() {
      Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.login_framelayout);
      if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
          super.onBackPressed();
      }
  }


}
