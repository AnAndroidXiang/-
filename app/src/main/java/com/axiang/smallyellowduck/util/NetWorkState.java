package com.axiang.smallyellowduck.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by a2389 on 2017/6/28.
 */

public class NetWorkState {

    public static boolean isNetWorkConnected(Context context) {
        if(context != null) {
            ConnectivityManager manager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if(info != null) {
                return info.isAvailable();
            }
        }
        return false;
    }

    public static boolean isWifiConnected(Context context) {
        if(context != null) {
            ConnectivityManager manager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if(info != null && info.getType() == ConnectivityManager.TYPE_WIFI) {
                return info.isAvailable();
            }
        }
        return false;
    }

    public static boolean isMobileDataConnected(Context context) {
        if(context != null) {
            ConnectivityManager manager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if(info != null && info.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }
}
