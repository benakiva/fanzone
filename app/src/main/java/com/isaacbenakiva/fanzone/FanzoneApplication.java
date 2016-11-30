package com.isaacbenakiva.fanzone;

import android.app.Application;
import android.widget.Toast;

import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import io.fabric.sdk.android.Fabric;

/**
 * Created by benakiva on 29/11/2016.
 */

public class FanzoneApplication extends Application {
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "ENTER YOUR KEY HERE";
    private static final String TWITTER_SECRET = "ENTER YOUR SECRET HERE";

    private List<String> mProfanities;

    @Override
    public void onCreate() {
        super.onCreate();

        if (TWITTER_KEY.equals("ENTER YOUR KEY HERE")) {
            Toast.makeText(this, "YOUR NEED TO ENTER YOUR TIWTTER KEY AND SECRET",
                    Toast.LENGTH_SHORT).show();
           // return;
        }

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig));

        createProfanityList();
    }

    private void createProfanityList() {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("swearWords.csv")));

            String strLine;
            StringTokenizer strToken = null;
            mProfanities = new ArrayList<String>();

            while ((strLine = reader.readLine()) != null) {
                strToken = new StringTokenizer(strLine, ",");

                while (strToken.hasMoreTokens()) {
                    mProfanities.add(strToken.nextToken(","));
                }
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
    }
}
