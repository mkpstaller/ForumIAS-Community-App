package com.forumias.beta.ui.deta.forumias;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.forumias.beta.R;
import com.forumias.beta.api.BaseUrl;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ImageViewActivity extends AppCompatActivity {

    @BindView(R.id.imageView)
    PhotoView imageViewZoom;
    @BindView(R.id.ivBack)
    AppCompatImageView ivCloseBack;
    PhotoViewAttacher photoViewAttacher ;
    String url;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        ButterKnife.bind(this);

        initView();

        ivCloseBack.setOnClickListener(view -> {
            Log.e("Click back===========> ","okokokok");
            finish();
        });
    }

   /* @OnClick(R.id.ivBackClose)
    public void OnClickBack(){
        Log.e("Click back===========> ","okokokok");
        finish();
    }*/

    @OnClick(R.id.ivShare)
    public void onClickShare(){
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_TEXT,url);
        share.setType("text/plain");
        startActivity(Intent.createChooser(share,"Share More"));
    }

    @OnClick(R.id.ivDownload)
    public void onClickDownload(){
        new DownloadImage().execute(url);
    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        url = bundle.getString(BaseUrl.IMAGE);

       Glide.with(this).load(url).dontAnimate().placeholder(R.drawable.placeholder_back).into(imageViewZoom);
        }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private class DownloadImage extends AsyncTask<String ,Void  , Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
             mProgressDialog = new ProgressDialog(ImageViewActivity.this);
            mProgressDialog.setTitle("Downloading Image");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }
        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap result) {

             MediaStore.Images.Media.insertImage(
                    getContentResolver(),
                    result,
                    "ForumIAS",
                    "Image of FrumIAS"
            );
            mProgressDialog.dismiss();

        }
    }


}

