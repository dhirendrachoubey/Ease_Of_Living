package in.nic.ease_of_living.utils;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;

import in.nic.ease_of_living.broadcast.NetworkChangeReceiver;


/**
 * Created by PC_126 on 8/30/2017.
 */
public class NetworkRegistered {
    public static void registerNetworkBroadcastForNougat(Context context, NetworkChangeReceiver mNetworkReceiver) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    public static void unregisterNetworkChanges(Context context, NetworkChangeReceiver mNetworkReceiver) {
        try {
            context.unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {

        }
    }
}
