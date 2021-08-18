package com.forumias.beta.utility;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;

import com.forumias.beta.R;
import com.forumias.beta.ui.deta.forumias.comment.ui.CommentOnPostDetailsActivity;
import com.google.android.material.card.MaterialCardView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    /**
     * This method use for change number format example : 1000 to 1K*/
    public String prettyCount(Number number) {
        char[] suffix = {' ', 'k', 'M', 'B', 'T', 'P', 'E'};
        long numValue = number.longValue();
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            return new DecimalFormat("#0.0").format(numValue / Math.pow(10, base * 3)) + suffix[base];
        } else {
            return new DecimalFormat("#,##0").format(numValue);
        }
    }

    /**
     * This method use for word count
    * */
    public int countWord(String word) {
        if (word == null) {
            return 0;
        }
        String input = word.trim();
        int count = input.isEmpty() ? 0 : input.split("\\s+").length;
        return count;
    }

    public String getSafeSubstring(String s, int maxLength){
        if(!TextUtils.isEmpty(s)){
            if(s.length() >= maxLength){
                return s.substring(0, maxLength);
            }
        }
        return s;
    }

/**
 * This method use for email validation*/
    public boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public boolean checkValidation(AppCompatEditText editEmailOrMobile , Context context){
        String emailOrMobile  = editEmailOrMobile.getText().toString();

        char c = emailOrMobile.charAt(0);
        boolean  isDigit = (c >= '0' && c <= '9');
        Log.e("check validation ==> " , String.valueOf(isDigit));
        if(isDigit){
            if(emailOrMobile.length() != 10){
                editEmailOrMobile.setError(context.getString(R.string.mobile_input));
                editEmailOrMobile.requestFocus();
                return true;
            }
        }else{
            Utility utility = new Utility();
            if(!utility.isValidEmail(emailOrMobile)){
                editEmailOrMobile.setError(context.getString(R.string.required_fieldemail));
                editEmailOrMobile.requestFocus();
                return true;
            }
        }

        return true;
    }

    public void doCircularReveal(MaterialCardView view) {
        int centerX = (view.getLeft() + view.getRight());
        int centerY = (view.getTop() + view.getTop());
        int startRadius = 0;
        int endRadius = Math.max(view.getWidth(), view.getHeight());
        Animator anim =
                ViewAnimationUtils.createCircularReveal(view,
                        centerX,  centerY, startRadius, endRadius);
        anim.setDuration(800);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });

        view.setVisibility(View.VISIBLE);
        anim.start();
    }

  public   void doExitReveal(MaterialCardView view) {
       int centerX = (view.getLeft() + view.getRight());
        int centerY = (view.getTop() + view.getTop());
        int initialRadius = view.getWidth();
        Animator anim =
                ViewAnimationUtils.createCircularReveal(view,
                        centerX, centerY, initialRadius, 0);
        anim.setDuration(800);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
            }
        });
        anim.start();
    }


    /**
     * This method use for check null and empty value*/
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }


    public static boolean isNotNullOrEmpty(String str){
        return str != null && !str.isEmpty() && !str.equals("null");
    }

    public String saveToInternalStorage(Bitmap bitmapImage, Context context){
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("forumImage", Context.MODE_PRIVATE);
        File mypath=new File(directory,"imageOne.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    @SuppressLint("SetJavaScriptEnabled")//proxima-nova
    public void showChannelDescription(WebView webView , String data){
        webView.loadDataWithBaseURL(null, "<style type=\"text/css\">" +
                "@font-face {font-family: segoe_regular;src: url(\"file:///android_asset/fonts/segoe_regular.ttf\")}" +
                "body,* {font-family: segoe_regular;font-size: 38px!important;color:#1A1A1A)}" +
                "img{display: inline;height: auto;max-width: 100%;}" +
                ".card a.cross_remove_on_detail {\n" +
                "    display: none!important;\n" +
                "}</style> "+
                "<body> "+data+"</body>", "text/html", "UTF-8", null);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setBackgroundColor(Color.parseColor("#EBF2F5"));
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
    }

    @SuppressLint("SetJavaScriptEnabled")//proxima-nova
    public void showDescription(WebView webView , String data){
        webView.loadDataWithBaseURL(null, "<style type=\"text/css\">" +
                "@font-face {font-family: segoe_regular;src: url(\"file:///android_asset/fonts/segoe_regular.ttf\")}" +
                "body,* {font-family: segoe_regular;font-size: 38px!important;color:#1A1A1A)}" +
                "img{display: inline;height: auto;max-width: 100%;}" +
                ".card a.cross_remove_on_detail {\n" +
                "    display: none!important;\n" +
                "}</style> "+
                "<body> "+data+"</body>", "text/html", "UTF-8", null);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
    }

    @SuppressLint("SetJavaScriptEnabled")//proxima-nova
    public void showDarkMoeDescription(WebView webView , String data){
        webView.loadDataWithBaseURL(null, "<style type=\"text/css\">" +
                "@font-face {font-family: segoe_regular;src: url(\"file:///android_asset/fonts/segoe_regular.ttf\")}" +
                "body,* {font-family: segoe_regular;font-size: 38px!important;color:#CECCCC}" +
                "img{display: inline;height: auto;max-width: 100%;}" +
                ".card a.cross_remove_on_detail {\n" +
                "    display: none!important;\n" +
                "}</style> "+
                "<body> "+data+"</body>", "text/html", "UTF-8", null);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setBackgroundColor(Color.parseColor("#1F1E1E"));
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
    }

    @SuppressLint("SetJavaScriptEnabled")//proxima-nova
    public void showDescriptionWhite(WebView webView , String data){
        webView.loadDataWithBaseURL(null, "<style type=\"text/css\">" +
                "@font-face {font-family: proximanova_regular;src: url(\"file:///android_asset/fonts/proximanova_regular.ttf\")}" +
                "body,* {font-family: proximanova_regular;font-size: 38px!important;}" +
                "img{display: inline;height: auto;max-width: 100%;}" +
                ".card a.cross_remove_on_detail {\n" +
                "    display: none!important;\n" +
                "}</style> "+
                "<body> "+data+"</body>", "text/html", "UTF-8", null);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void showCommentDescription(WebView webView , String data){

        Pattern regex = Pattern.compile("(?:http(?:s)?:\\/\\/)?(?:www\\.)?(?:youtu\\.be\\/|youtube\\.com\\/(?:(?:watch)?\\?(?:.*&)?v(?:i)?=|(?:embed|v|vi|user)\\/))([^\\?&\\\"'<> #]+)");
        Matcher regexMatcher = regex.matcher(data);
        if (regexMatcher.find()) {
            data = regexMatcher.group();
            data = data.replace("https://youtu.be/","https://www.youtube.com/embed/");
            data =  "<iframe width=\"100%\" height=\"515\" src="+data+" frameborder=\"0\" allowfullscreen></iframe>";

        }
        webView.loadDataWithBaseURL(null, "<style type=\"text/css\">" +
                "@font-face {font-family: proxima-nova;src: url(\"file:///android_asset/font/proximanova_regular.ttf\")}" +
                "body,* {font-family: proxima-nova;font-size: 30px!important;}" +
                "img{display: inline;height: auto;max-width: 100%;}" +
                ".card a.cross_remove_on_detail {\n" +
                "    display: none!important;\n" +
                "}</style> "+
                "<body> "+data+"</body>", "text/html", "UTF-8", null);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);


    }


   /* @SuppressLint("SetJavaScriptEnabled")
    public void showAllComment(WebView webView , String data){
        webView.loadDataWithBaseURL(null, "<style type=\"text/css\">" +
                "@font-face {font-family: proxima-nova;src: url(\"file:///android_asset/fonts/proximanova_regular.ttf\")}" +
                "body,* {font-family: proxima-nova;font-size: 35px!important;}" +
                "img{display: inline;height: auto;max-width: 100%;}" +
                ".card a.cross_remove_on_detail {" +
                "    display: none!important;" +
                "}" +
                "blockquote{" +
                "padding-left: 2rem;" +
                "padding-right: 2rem;" +
                "border-left: 3px solid #999;" +
                "margin-left: -0.5rem;" +
                "}" +
                "</style> "+
                "<body> " +
                "<blockquote cite=\"http://www.worldwildlife.org/who/index.html\">"+
               data+
                "</body>", "text/html", "UTF-8", null);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);

        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);

    }
*/

    @SuppressLint("SetJavaScriptEnabled")
    public void showAllComment(WebView webView , String data){
       String youtubeUrl = "";
       Pattern regex = Pattern.compile("(?:http(?:s)?:\\/\\/)?(?:www\\.)?(?:youtu\\.be\\/|youtube\\.com\\/(?:(?:watch)?\\?(?:.*&)?v(?:i)?=|(?:embed|v|vi|user)\\/))([^\\?&\\\"'<> #]+)");
       Matcher regexMatcher = regex.matcher(data);
      if (regexMatcher.find()) {
          youtubeUrl = regexMatcher.group();
          youtubeUrl = youtubeUrl.replace("https://youtu.be/","https://www.youtube.com/embed/");
          youtubeUrl =  "<iframe width=\"100%\" height=\"515\" src="+youtubeUrl+" frameborder=\"0\" allowfullscreen></iframe>";

      }
        webView.loadDataWithBaseURL(null, "<style type=\"text/css\">" +
                "@font-face {font-family: segoe_regular;src: url(\"file:///android_asset/fonts/segoe_regular.ttf\")}" +
                "body,* {font-family: segoe_regular;font-size: 35px!important;color:#1A1A1A}" +
                "img{display: inline;height: auto;max-width: 100%;}" +
                ".card a.cross_remove_on_detail {" +
                "    display: none!important;" +
                "}" +
                "blockquote{" +
                "padding-left: 2rem;" +
                "padding-right: 2rem;" +
                "border-left: 3px solid #999;" +
                "margin-left: -0.5rem;" +
                "}" +
                "</style> "+
                "<body> " +
                "<blockquote cite=\"http://www.worldwildlife.org/who/index.html\">"+
                youtubeUrl+
                data+
                "</body>", "text/html", "UTF-8", null);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);

    }


    @SuppressLint("SetJavaScriptEnabled")
    public void showAllCommentDarkMode(WebView webView , String data){
        String youtubeUrl = "";
        Pattern regex = Pattern.compile("(?:http(?:s)?:\\/\\/)?(?:www\\.)?(?:youtu\\.be\\/|youtube\\.com\\/(?:(?:watch)?\\?(?:.*&)?v(?:i)?=|(?:embed|v|vi|user)\\/))([^\\?&\\\"'<> #]+)");
        Matcher regexMatcher = regex.matcher(data);
        if (regexMatcher.find()) {
            youtubeUrl = regexMatcher.group();
            youtubeUrl = youtubeUrl.replace("https://youtu.be/","https://www.youtube.com/embed/");
            youtubeUrl =  "<iframe width=\"100%\" height=\"515\" src="+youtubeUrl+" frameborder=\"0\" allowfullscreen></iframe>";

        }
        webView.loadDataWithBaseURL(null, "<style type=\"text/css\">" +
                "@font-face {font-family: segoe_regular;src: url(\"file:///android_asset/fonts/segoe_regular.ttf\")}" +
                "body,* {font-family: segoe_regular;font-size: 35px!important;color:#CECCCC;}" +
                "img{display: inline;height: auto;max-width: 100%;}" +
                ".card a.cross_remove_on_detail {" +
                "    display: none!important;" +
                "}" +
                "blockquote{" +
                "padding-left: 2rem;" +
                "padding-right: 2rem;" +
                "border-left: 3px solid #999;" +
                "margin-left: -0.5rem;" +
                "}" +
                "</style> "+
                "<body> " +
                "<blockquote cite=\"http://www.worldwildlife.org/who/index.html\">"+
                youtubeUrl+
                data+
                "</body>", "text/html", "UTF-8", null);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.setBackgroundColor(Color.parseColor("#1F1E1E"));
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);


    }


    public void showUserVerified(int verifiedStatus , AppCompatImageView ivVerified){
        switch (verifiedStatus){
            case 0:
                ivVerified.setVisibility(View.GONE);
               // ivVerified.setBackgroundResource(R.drawable.red_circle_shape);
                break;
            case 1:
                ivVerified.setVisibility(View.VISIBLE);
              //  ivVerified.setBackgroundResource(R.drawable.blue_circle_shape);
                ivVerified.setBackgroundResource(R.drawable.ic_blue);
                break;
            case 2:
                ivVerified.setVisibility(View.VISIBLE);
               // ivVerified.setBackgroundResource(R.drawable.green_circle_shape);
                ivVerified.setBackgroundResource(R.drawable.ic_green);
                break;
            case 3:
                ivVerified.setVisibility(View.VISIBLE);
               // ivVerified.setBackgroundResource(R.drawable.red_circle_shape);
                ivVerified.setBackgroundResource(R.drawable.ic_red);
                break;
        }
    }


   public void setProgess(List<String> items, int is_correct, Context context, int loginUserId, AppCompatSeekBar oneSeekBar){
       for(int i = 0 ; i < items.size(); i++){
           if(loginUserId == Integer.parseInt(items.get(i))){
              // userMatch = true;
               if(is_correct == 1){
                   oneSeekBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.custom_thumb_success));
               }else{
                   oneSeekBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.custom_thumb_fail));
               }
           }else{
               oneSeekBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.custom_thumb));
           }
       }

   }

}
