package com.sasuke.recommender.manager;

/**
 * Created by abc on 4/23/2018.
 */

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.sasuke.recommender.model.User;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sasuke on 1/24/2018.
 */

public class PreferenceManager {

    private static final String MY_PREFS = "perferences";
    private static final boolean DEFAULT_LOGIN_STATUS = false;
    private static final String EXTRA_USER_LOGIN_STATUS = "user_login_status";
    private static final String EXTRA_USER = "user";

    public static void setUser(Context context, User user) {
        updateUserLoginStatus(context, true);
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit();
        editor.putString(EXTRA_USER, new Gson().toJson(user));
        editor.apply();
    }

    private static void updateUserLoginStatus(Context context, boolean status) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit();
        editor.putBoolean(EXTRA_USER_LOGIN_STATUS, status);
        editor.apply();
    }

    public static boolean isUserLoggedIn(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        return prefs.getBoolean(EXTRA_USER_LOGIN_STATUS, DEFAULT_LOGIN_STATUS);
    }

    public static User getUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        String user = prefs.getString(EXTRA_USER, "");
        return new Gson().fromJson(user, User.class);
    }

    public static void clearAll(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

}

