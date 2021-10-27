package com.example.ferreroimagerecognition;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Commons {

    public static final int DATE_TIME = 5;
    public static final int DATE = 6;

    public static void print( String message) {
        if (BuildConfig.DEBUG){

        }

    }

    public static void printException( String message, Throwable e) {
        Log.e("TAG", message, e);
    }

    public static void printException(Throwable e) {
        Log.e("TAG", "~", e);
    }

    public static void printException(String message) {
        Log.e("TAG", message);
    }

    public static void printInformation(String message) {
        if (BuildConfig.DEBUG)
            Log.i("TAG", message);
    }

    /**
     * @deprecated
     * @see {{@link com.ivy.utils.DateTimeUtils#now(int)}}
     * @param dateFormat - to be converted
     * @return formatted date
     */
    public static String now(int dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf;
        if (DATE_TIME == dateFormat)
            sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH);
        else if (DATE == dateFormat)
            sdf = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
        else
            sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.ENGLISH);

        return sdf.format(cal.getTime());
    }


}
