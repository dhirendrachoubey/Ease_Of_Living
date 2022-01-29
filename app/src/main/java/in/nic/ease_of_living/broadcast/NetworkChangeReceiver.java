package in.nic.ease_of_living.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import in.nic.ease_of_living.interfaces.Communicator;


public class NetworkChangeReceiver extends BroadcastReceiver
{
    Boolean isInternet_available=false;
    private Communicator comm;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        comm = (Communicator)context;
        try
        {
            if (isOnline(context)) {
                isInternet_available=true;
                comm.event(isInternet_available);
            } else {
                isInternet_available=false;
                comm.event(isInternet_available);
            }
        } catch (NullPointerException e) {

        }
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            return false;
        }
    }


}