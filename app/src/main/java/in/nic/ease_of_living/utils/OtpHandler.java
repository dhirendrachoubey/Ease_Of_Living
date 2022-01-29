package in.nic.ease_of_living.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONObject;

import in.nic.ease_of_living.supports.MyVolley;

/**
 * Created by Neha on 5/25/2018.
 */

public class OtpHandler {

    /* Function to check if user id (email or mob) exists and send otp to send otp to mobile */
    public static void doGetOTP(final Context context, final String strAlertTitle, final ProgressDialog pd,
                                String strUserId, String strUserLoginType,
                                Response.Listener<JSONObject> volleyResponseListener
                                )
    {
        try {
            JSONObject jsRequest = new JSONObject();

            jsRequest.put("user_id", strUserId);
            jsRequest.put("user_login_type", strUserLoginType);

            MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context,"029", false, false, "otp/v1/getUserMobileOTP/", jsRequest, strAlertTitle,
                    volleyResponseListener);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Function to validate otp sent to mobile */
    public static void doValidateOTP(final Context context, final String strAlertTitle,
                                      final ProgressDialog pd, String strOtp, String strUserId, String strUserLoginType,
                                     Response.Listener<JSONObject> volleyResponseListener
                                     )
    {
        try {
            JSONObject jsRequest = new JSONObject();
            jsRequest.put("otp", strOtp);
            jsRequest.put("user_id", strUserId);
            jsRequest.put("user_login_type", strUserLoginType);

            MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context,"030", false, false, "otp/v1/verifyOTP/", jsRequest, strAlertTitle,
                    volleyResponseListener);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
