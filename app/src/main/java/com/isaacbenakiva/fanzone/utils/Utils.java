/**
 * FILE: Utils.java
 * AUTHOR: Dr. Isaac Ben-Akiva <isaac.ben-akiva@ubimobitech.com>
 * <p/>
 * CREATED ON: 27/06/15
 */

package com.isaacbenakiva.fanzone.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.isaacbenakiva.fanzone.R;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by benakiva on 27/06/15.
 */
public class Utils {
    public static final String calculateEllapsedTime(Context context, Date lastUpdate) {
        Date nowDate = new Date();
        String durationString = "";

        long before = lastUpdate.getTime();
        long now = nowDate.getTime();

        long seconds = (now - before) / 1000;
        long days = (int) TimeUnit.SECONDS.toDays(seconds);
        long weeks = days / 7;

        if(weeks > 0) {
            durationString = context.getResources().getString(R.string.weeks, weeks);

            return durationString;
        }

        if(days > 0) {
            durationString = context.getResources().getString(R.string.day, days);

            return durationString;
        }

        long hours = TimeUnit.SECONDS.toHours(seconds) - (days * 24);

        if(hours > 0) {
            durationString = context.getResources().getString(R.string.hours, hours);

            return durationString;
        }

        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);

        durationString = context.getResources().getString(R.string.minutes, minute);

        return durationString;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting()){
            return true;
        } else {
            Toast.makeText(context, "No network connection", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
