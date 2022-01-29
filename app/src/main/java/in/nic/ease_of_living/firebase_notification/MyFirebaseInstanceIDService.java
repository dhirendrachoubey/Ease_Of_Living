package in.nic.ease_of_living.firebase_notification;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import in.nic.ease_of_living.interfaces.Constants;
import in.nic.ease_of_living.supports.MySharedPref;


/**
 * Created by Chinki on 7/22/2019.
 */

public class MyFirebaseInstanceIDService  extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        MySharedPref.saveFirebaseTokenId(getApplicationContext(),refreshedToken);

        // Saving reg id to shared preferences  //cgTsrWYsxNE:APA91bHx-fY-Jdhd6w4wldy9DGeCxvFpxXVK2RKBW4wf7VcDBz1Q67GnNkBTcCjjHZItOT-Uynjv79tnYbcakjdGitoWACBejptyyOif_XOjrnnsKTsmbJAB9wm0al6NYjeQwnqZnkgz

        // storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Constants.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server

    }

  /*  private void storeRegIdInPref(String token) {
      *//*  SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.commit();*//*
    }*/


}
