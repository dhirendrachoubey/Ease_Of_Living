package in.nic.ease_of_living.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.supports.MyVolley;

/**
 * Created by Neha on 6/27/2018.
 */

public class Authorization {
    public  static String TAG = "Authorization";
    public static void getToken(final ProgressDialog pdLogin, final String strUserId, final String strPassword,
                                final Context context, Response.Listener<String> volleyResponseListener)
    {
        try {
            Map<String, String> headers = new HashMap<String, String>();

   //   String credentials = "ma2-client:$2a$10$0.d73Dt0.O/40OrIysiTvuqoaJSpcjAniV4BzdyhCWIszXglkDoT2";
    String credentials = "eol-client:$2a$10$0.d73Dt0.O/40OrIysiTvuqoaJSpcjAniV4BzdyhCWIszXglkDoT2";
            String auth = "Basic "
                    + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            headers.put("Authorization", auth);

            Log.d(TAG, "getToken:headers "+headers.toString());

            Map<String, String> params = new HashMap<String, String>();

            params.put("username", strUserId);
            params.put("password", strPassword);
            params.put("grant_type", "password");

            Log.d(TAG, "getToken:params "+params.toString());


            MyVolley.callWebServiceUsingVolleyforAuth(Request.Method.POST, pdLogin, context,"039",
                    headers, params, "token", context.getString(R.string.app_error),
                    volleyResponseListener);
        }catch(Exception e)
        {

        }
    }

}
