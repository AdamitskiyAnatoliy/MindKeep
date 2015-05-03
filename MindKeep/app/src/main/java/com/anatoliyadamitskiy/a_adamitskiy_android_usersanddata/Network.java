package com.anatoliyadamitskiy.a_adamitskiy_android_usersanddata;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Anatoliy on 4/24/15.
 */
public class Network {

    Context mContext;

    public Network (Context con) {
        mContext = con;
    }

    public Boolean checkNetwork() {
        ConnectivityManager manager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

}
