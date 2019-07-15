package com.sabkayar.praveen.popularmovies.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class Utils {
    private static final String LOG_TAG = Utils.class.getSimpleName();

    public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidth dp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5); // +0.5 for correct rounding to int.
        return noOfColumns;
    }

    public static int getScreenHeight(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenHeightDp = displayMetrics.heightPixels / displayMetrics.density;
        return (int)(screenHeightDp+0.5);
    }
    public static int getScreenWidth(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        return (int)(screenWidthDp+0.5);
    }

    public static String getFormattedDate(String dateString,String formatString){
        String dateFormat="yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        String outputDate="";
        try {
            Date date=simpleDateFormat.parse(dateString);
            simpleDateFormat=new SimpleDateFormat(formatString,Locale.ENGLISH);
            outputDate=simpleDateFormat.format(date);
        } catch (ParseException e) {
            Log.e(LOG_TAG,e.getMessage());
        }
        return outputDate;
    }

}
