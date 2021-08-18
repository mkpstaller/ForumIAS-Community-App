package com.forumias.beta.utility;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormatUtils {
    String  showPostDate;
    public  String postDateFormat(String getDate){
        Date newDate;
        String outDate = null;
        SimpleDateFormat input = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss a"); // 2019-09-06 12:14:16
        SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy");
        try {
            newDate = input.parse(getDate);                 // parse input
            outDate = output.format(newDate);    // format output
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outDate;
    }


    public String showPostDate(String date2){
        try {
          //  String format = "yyyy-MM-dd hh:mm:ss";
           SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
           // sdf1.setTimeZone(TimeZone.getTimeZone("UTC"));
            String date1 = sdf1.format(new Date());


            Date dateObj2 = sdf1.parse(date1);
            Date dateObj1 = sdf1.parse(date2);

            System.out.println(dateObj1);
            System.out.println(dateObj2 + "\n");

            //DecimalFormat crunchifyFormatter = new DecimalFormat("###,###");
            long diff = dateObj2.getTime() - dateObj1.getTime();

            int diffDays = (int) (diff / (24 * 60 * 60 * 1000));

            int diffHours = (int) (diff / (60 * 60 * 1000));

            int diffMin = (int) (diff / (60 * 1000));

            int diffSec = (int) (diff / (1000));

            if(diffSec < 60){
               // showPostDate = diffSec+" Second";
                showPostDate = "Few second ago";
            }else if(diffMin<60){
                showPostDate = diffMin+" Minute(s) ago";
            }else if(diffHours < 24){
                showPostDate = diffHours+" Hour(s) ago";
            }else if(diffHours > 24){
                showPostDate = diffDays+" day(s) ago";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return showPostDate;
    }

}
