package in.nic.ease_of_living.supports;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import in.nic.ease_of_living.gp.R;

/**
 * Created by Neha Jain on 6/22/2017.
 */
/*020-001*/
public class IsConnected
{

    public static Boolean isInternet_connected(Context context, Boolean isShowMessage)
    {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else {
            connected = false;
            if(isShowMessage)
                MyAlert.showAlert(context, R.mipmap.icon_warning, context.getString(R.string.app_warning),  context.getString(R.string.no_internet),"020-001");
        }
        return connected;
    }
    static boolean exists = false;




}
