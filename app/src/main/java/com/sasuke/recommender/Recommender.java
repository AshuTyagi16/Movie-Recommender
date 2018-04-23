package com.sasuke.recommender;

import android.app.Application;
import android.content.Context;

/**
 * Created by abc on 4/23/2018.
 */

public class Recommender extends Application {

    private static Recommender instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getAppContext() {
        return instance;
    }
}
