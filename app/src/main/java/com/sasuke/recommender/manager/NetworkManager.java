package com.sasuke.recommender.manager;

/**
 * Created by abc on 4/23/2018.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkManager {

    private static volatile NetworkManager instance;
    private static Context mContext;

    public static NetworkManager getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }
        }
        return instance;
    }

    public ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public boolean isConnected() {
        NetworkInfo networkInfo = getConnectivityManager().getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected()
                && !networkInfo.isRoaming();
    }

    private NetworkManager() {

    }

}
