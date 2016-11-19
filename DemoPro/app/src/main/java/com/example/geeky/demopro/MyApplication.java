package com.example.geeky.demopro;

import android.app.Application;

/**
 * Created by Sudarshan on 10/22/2016.
 */
public class MyApplication extends Application {

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }

    private static boolean activityVisible;
}