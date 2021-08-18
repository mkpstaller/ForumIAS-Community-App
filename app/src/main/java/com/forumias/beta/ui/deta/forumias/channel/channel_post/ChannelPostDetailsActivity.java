package com.forumias.beta.ui.deta.forumias.channel.channel_post;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.ui.deta.forumias.ImageViewActivity;
import com.forumias.beta.utility.Utility;
import com.forumias.beta.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChannelPostDetailsActivity extends AppCompatActivity {

    @BindView(R.id.tvTitle)
    AppCompatTextView tvTitle;
    @BindView(R.id.tvDescription)
    WebView tvDescription;
    @BindView(R.id.tvViewCount)
    AppCompatTextView tvViewCount;
    @BindView(R.id.tvSlug)
    AppCompatTextView tvSlug;
    @BindView(R.id.llPostImageSection)
    LinearLayoutCompat llPostImageSection;
    @BindView(R.id.ivPostImage)
    AppCompatImageView ivPostImage;
    @BindView(R.id.rlCathySection)
    RelativeLayout rlCathySection;
    @BindView(R.id.tvCatchyName)
    AppCompatTextView tvCatchyName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_post_details);
        ButterKnife.bind(this);

        intView();

    }

    @OnClick(R.id.ivBack)
    public void onClickBack(){
        finish();
    }

    private void intView(){

        Bundle bundle = getIntent().getExtras();

        assert bundle != null;
        String title = bundle.getString(BaseUrl.TITLE);
        String slug = bundle.getString(BaseUrl.SLUG);
        String description = bundle.getString(BaseUrl.DESCRIPTION);
        int viewCount = bundle.getInt(BaseUrl.VIEW_COUNT);
        String  image = bundle.getString(BaseUrl.IMAGE);
        String  colorCode = bundle.getString(BaseUrl.COLOR_CODE);
        String  chyText = bundle.getString(BaseUrl.CHYTEXT);

        tvTitle.setText(title);
        tvSlug.setText(slug);
        new Utility().showDescription(tvDescription , description);
        tvViewCount.setText(new Utility().prettyCount(viewCount) + " Views");

       // Log.e("come====> " , colorCode);

        if ((!Utility.isNullOrEmpty(image))) {
            llPostImageSection.setVisibility(View.VISIBLE);
            rlCathySection.setVisibility(View.GONE);
            ivPostImage.setVisibility(View.VISIBLE);
            Glide.with(this).load(BaseUrl.POST_IMAGE_URL + image).into(ivPostImage);

            ivPostImage.setOnClickListener(view -> {
                Intent intent = new Intent(this , ImageViewActivity.class);
                intent.putExtra(BaseUrl.IMAGE , BaseUrl.POST_IMAGE_URL + image);
                startActivity(intent);
            });
            //Log.e("come===11=> " , colorCode);
        } else if(!Utility.isNullOrEmpty(colorCode)) {
            if (!Utility.isNullOrEmpty(chyText)) {
                rlCathySection.setVisibility(View.VISIBLE);
                ivPostImage.setVisibility(View.GONE);
                llPostImageSection.setVisibility(View.VISIBLE);
                assert colorCode != null;
                //  Log.e("come==22==> " , colorCode);
                if (colorCode.equalsIgnoreCase("#f1c40f")) {
                    rlCathySection.setBackgroundColor(Color.parseColor(colorCode));
                    tvCatchyName.setTextColor(ContextCompat.getColor(this, R.color.red));
                    tvCatchyName.setText(chyText);
                } else {
                    rlCathySection.setBackgroundColor(Color.parseColor(colorCode));
                    tvCatchyName.setTextColor(ContextCompat.getColor(this, R.color.white));
                    tvCatchyName.setText(chyText);
                }
            }else {
                llPostImageSection.setVisibility(View.GONE);
            }
        }else{
            llPostImageSection.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
