package com.forumias.beta.ui.deta.forumias;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.forumias.beta.api.BaseUrl;
import com.forumias.beta.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.view.MotionEvent;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImagePostDialogFragment extends DialogFragment {
    public static String TAG = "FullScreenDialog";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.createPostDialog);
    }


    @BindView(R.id.ivClose)
    AppCompatImageView ivClose;
    @BindView(R.id.imageView)
    AppCompatImageView imageView;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_post_dialog, container, false);
        ButterKnife.bind(this , view);


        assert getArguments() != null;
        String url = getArguments().getString(BaseUrl.IMAGE);

        if(!url.isEmpty()) {
            Glide.with(Objects.requireNonNull(getContext())).load(url).dontAnimate().placeholder(R.drawable.placeholder_back).into(imageView);
        }
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleListener());

        return view;
    }

    @OnClick(R.id.ivClose)
    public void onClickClose(){
        dismiss();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }


    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);
            return true;
        }
    }

}
