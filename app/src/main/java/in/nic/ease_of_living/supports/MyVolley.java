package in.nic.ease_of_living.supports;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLSocketFactory;

import in.nic.ease_of_living.gp.LoginActivity;
import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.installation.Installation;
import in.nic.ease_of_living.interfaces.Constants;
import in.nic.ease_of_living.interfaces.VolleyCallback;
import in.nic.ease_of_living.ssl.SSLCertification;

/**
 * Created by Neha Jain on 9/6/2017.
 */
/*,"019-042"*/
public class MyVolley {

public  static  String TAG = "MyVolley";

    public static ProgressDialog dialog;

    public static void getJsonResponse(final String strActivityName, final Context context,  int iRequestMethod,final String strServiceCode,
                                       final boolean bShowInternetMessage,
                                       final boolean bAddHeaderExtraDetails, final Boolean bAddToken,
                                       final boolean bPassNetworkReponse,
                                       final boolean bPassNetworkReponseHeaders,
                                       final boolean bReturnErrorResponse,
                                       String strUrl, JSONObject jsRequest,
                                       final VolleyCallback callback) {

        try
        {
            if (IsConnected.isInternet_connected(context , bShowInternetMessage))
            {
                if ((dialog == null) || ((dialog != null) && (!dialog.isShowing())))
                    dialog = new ProgressDialog(context);
                if (!dialog.isShowing()) {
                    dialog.setMessage(context.getString(R.string.please_wait));
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }

                String strMac = GetAddress.getWifiMacAddress();

                if (MySharedPref.getMacAddress(context) != null) {
                    strMac = MySharedPref.getMacAddress(context);
                } else if (strMac != null) {
                    MySharedPref.saveMacAddress(context, GetAddress.getWifiMacAddress());
                }

                final String strMacFinal = strMac;

                if ((bAddHeaderExtraDetails && (strMacFinal == null))) {
                    MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName, context.getString(R.string.allow_wifi_imei), "025-001-" + strServiceCode);
                    dialog.dismiss();
                } else {
                    RequestQueue queue = getRequestQueue(context);
                    String strFinalUrl = getHostUrl(context) + "api/" + strUrl;


                    JsonObjectRequest jsonObjRequest = new JsonObjectRequest(iRequestMethod, strFinalUrl, jsRequest,
                            new Response.Listener<JSONObject> () {

                                @Override
                                public void onResponse(JSONObject Response) {

                                    callback.onVolleySuccessResponse(Response);
                                }

                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    if(bReturnErrorResponse)
                                        callback.onVolleyErrorResponse(error);
                                    else {
                                        if (dialog != null && dialog.isShowing()) {
                                            dialog.dismiss();
                                        }


                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                            MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName, context.getString(R.string.error_no_connection),"019-009-" + strServiceCode);
                                        } else if (error instanceof AuthFailureError) {
                                            MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_server_default),"019-010-" + strServiceCode);
                                        } else if (error instanceof ServerError) {
                                            showVolley_error(context,  strActivityName, error, strServiceCode);
                                        } else if (error instanceof NetworkError) {
                                            MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_server_default),"019-011-" + strServiceCode);
                                        } else if (error instanceof ParseError) {
                                            MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_server_default),"019-012-" + strServiceCode);
                                        } else if (error.toString().contains("NullPointerException")) {
                                            MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_null_pointer),"019-013-" + strServiceCode);
                                        }

                                    }
                                }
                            }) {
                        @Override
                        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                            try {
                                if(bPassNetworkReponse)
                                    return callback.onVolleyNetworkResponse(response);
                                else {
                                    String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                                    JSONObject jsonResponse = new JSONObject(json);
                                    if (bPassNetworkReponseHeaders)
                                        jsonResponse.put("headers", new JSONObject(response.headers));
                                    return Response.success(jsonResponse, HttpHeaderParser.parseCacheHeaders(response));
                                }
                            } catch (UnsupportedEncodingException e) {
                                return Response.error(new ParseError(e));
                            } catch (JsonSyntaxException e) {
                                return Response.error(new ParseError(e));
                            } catch (JSONException e) {
                                return Response.success(new JSONObject(),
                                        HttpHeaderParser.parseCacheHeaders(response));
                            }

                        }

                        // set headers
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");

                            if (bAddHeaderExtraDetails && (strMacFinal != null)) {
                                try {
                                    headers.put("app_id", Installation.id(context));
                                    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                                    headers.put("app_version", packageInfo.versionName);

                                    if (MySharedPref.getImei(context) != null) {
                                        headers.put("client_imei_no", MySharedPref.getImei(context));
                                    } else if (GetAddress.getIMEI(context) != null) {
                                        MySharedPref.saveImei(context, GetAddress.getIMEI(context));
                                        headers.put("client_imei_no", GetAddress.getIMEI(context));
                                    } else
                                        headers.put("client_imei_no", "XXXXXXXXXXXXXXX");

                                    if (GetAddress.getIPAddress(true) != null)
                                        headers.put("client_ip_address", GetAddress.getIPAddress(true));
                                    else
                                        headers.put("client_ip_address", "XXX.XXX.XXX.XXX");

                                    headers.put("client_mac_address", strMacFinal);
                                    headers.put("device_id", MySharedPref.getDeviceId(context));
                                }catch(Exception e)
                                {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"019-023-" + strServiceCode);
                                }
                            }
                            if (bAddToken) {
                                String strFinalToken = MySharedPref.getAuthorizationToken(context).getToken_type() + " " + MySharedPref.getAuthorizationToken(context).getAccess_token();
                                headers.put("Authorization", strFinalToken);

                            }
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            Log.d(TAG, "getHeaders:getJsonResponse "+headers.toString());
                            return headers;
                        }
                    };
                    jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                            1000 * 20, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    queue.cancelAll(queue);
                    queue.add(jsonObjRequest);
                    queue.getCache().clear();
                }
            }
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName + context.getString(R.string.error), e.getMessage(), "025-007-" + strServiceCode);
        }
    }

    public static void callWebServiceUsingVolley(final ProgressDialog pd, final Context context, final String strServiceCode, String strUrl, final Map<String,String> params,
                                                 final String strActivityName, Response.Listener<String> volleyResponseListener) {
        try {
            if(!pd.isShowing())
            {
                pd.setMessage(context.getString(R.string.please_wait));
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);
                pd.show();
            }

            RequestQueue queue = Volley.newRequestQueue(context);
            String strFinalUrl = getHostUrl(context) + "api/" + strUrl;

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, strFinalUrl,
                    volleyResponseListener,
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pd.dismiss();
                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName, context.getString(R.string.error_no_connection),"019-001-" + strServiceCode);
                            } else if (error instanceof AuthFailureError) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_server_default),"019-002-" + strServiceCode);
                            } else if (error instanceof ServerError) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_no_server_available),"019-006-" + strServiceCode);
                            } else if (error instanceof NetworkError) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_server_default),"019-003-" + strServiceCode);
                            } else if (error instanceof ParseError) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_server_default),"019-004-" + strServiceCode);
                            } else if (error.toString().contains("NullPointerException")) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_null_pointer),"019-005-" + strServiceCode);
                            }
                        }
                    })
            {
                @Override
                protected Map<String,String> getParams(){
                    return params;
                }
            };
            // Add the request to the RequestQueue.
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    1000 * 20, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.cancelAll(queue);
            queue.add(stringRequest);
            queue.getCache().clear();

        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"019-007-" + strServiceCode);

        }
    }


    public static void callWebServiceUsingVolley(final int iRequestMethod, final ProgressDialog pd, final Context context, final String strServiceCode,
                                                 final Boolean bAddExtraDetails, final Boolean bAddToken,
                                                 String strUrl, JSONObject jsRequest,
                                                 final String strActivityName, Response.Listener<JSONObject> volleyResponseListener) {
        try
        {
            if(!pd.isShowing())
            {
                pd.setMessage(context.getString(R.string.please_wait));
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);
                pd.show();
            }

            String strMac = GetAddress.getWifiMacAddress();

            if(MySharedPref.getMacAddress(context) != null)
            {
                strMac = MySharedPref.getMacAddress(context);
            }
            else if (strMac != null)
            {
                MySharedPref.saveMacAddress(context, GetAddress.getWifiMacAddress());
            }


            if((bAddExtraDetails && (strMac == null)) )
            {
                MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName, context.getString(R.string.allow_wifi_imei),"019-008-" + strServiceCode);
                pd.dismiss();
            }
            else {
                if (bAddExtraDetails && (strMac != null)) {
                    jsRequest.put("app_id", Installation.id(context));
                    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                    jsRequest.put("app_version", packageInfo.versionName);

                    if (MySharedPref.getImei(context) != null) {
                        jsRequest.put("client_imei_no", MySharedPref.getImei(context));
                    } else if (GetAddress.getIMEI(context) != null) {
                        MySharedPref.saveImei(context, GetAddress.getIMEI(context));
                        jsRequest.put("client_imei_no", GetAddress.getIMEI(context));
                    } else
                        jsRequest.put("client_imei_no", "XXXXXXXXXXXXXXX");

                    if (GetAddress.getIPAddress(true) != null)
                        jsRequest.put("client_ip_address", GetAddress.getIPAddress(true));
                    else
                        jsRequest.put("client_ip_address", "XXX.XXX.XXX.XXX");

                    jsRequest.put("client_mac_address", strMac);
                    jsRequest.put("device_id", MySharedPref.getDeviceId(context));

                }
                Log.d(TAG, "callWebServiceUsingVolley: jsRequest"+jsRequest.toString());
                RequestQueue queue = getRequestQueue(context);

                String strFinalUrl = getHostUrl(context) + "api/" + strUrl;
                Log.d(TAG, "callWebServiceUsingVolley: strFinalUrl"+strFinalUrl);
                // Request a string response from the provided URL.
                JsonObjectRequest jsonObjRequest = new JsonObjectRequest(iRequestMethod, strFinalUrl, jsRequest,
                        volleyResponseListener,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pd.dismiss();
                                Log.d(TAG, "onErrorResponse: error"+error.toString());
                                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName, context.getString(R.string.error_no_connection),"019-009-" + strServiceCode);
                                } else if (error instanceof AuthFailureError) {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_server_default),"019-010-" + strServiceCode);
                                } else if (error instanceof ServerError) {
                                    showVolley_error(context,  strActivityName, error, strServiceCode);
                                } else if (error instanceof NetworkError) {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_server_default),"019-011-" + strServiceCode);
                                } else if (error instanceof ParseError) {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_server_default),"019-012-" + strServiceCode);
                                } else if (error.toString().contains("NullPointerException")) {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_null_pointer),"019-013-" + strServiceCode);
                                }
                            }
                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");

                        if(bAddToken) {
                            String strFinalToken = MySharedPref.getAuthorizationToken(context).getToken_type() + " " + MySharedPref.getAuthorizationToken(context).getAccess_token();
                            headers.put("Authorization", strFinalToken);
                        }
                        Log.d(TAG, "callWebServiceUsingVolley: headers"+headers.toString());

                        return headers;
                    }

                };
                // Add the request to the RequestQueue.
                jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                        1000 * 20, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.cancelAll(queue);
                queue.add(jsonObjRequest);
                queue.getCache().clear();
            }

        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error),  e.getMessage(),"019-014-" + strServiceCode);
        }
    }

    public static void callWebServiceUsingVolley(final int iRequestMethod, final ProgressDialog pd, final Context context,
                                                 final String strServiceCode,
                                                 Boolean bAddExtraDetails, final Boolean bAddToken,
                                                 String strUrl, JSONObject jsRequest,
                                                 final String strActivityName, Response.Listener<JSONObject> volleyResponseListener,
                                                 Response.ErrorListener volleyErrorResponseListener) {
        try {
            if (IsConnected.isInternet_connected(context , true))
            {
                if (!pd.isShowing()) {
                    pd.setMessage(context.getString(R.string.please_wait));
                    pd.setCancelable(false);
                    pd.setCanceledOnTouchOutside(false);
                    pd.show();
                }


                String strMac = GetAddress.getWifiMacAddress();

                if (MySharedPref.getMacAddress(context) != null) {
                    strMac = MySharedPref.getMacAddress(context);
                } else if (strMac != null) {
                    MySharedPref.saveMacAddress(context, GetAddress.getWifiMacAddress());
                }


                if ((bAddExtraDetails && (strMac == null))) {
                    MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName, context.getString(R.string.allow_wifi_imei),"019-015-" + strServiceCode);
                    pd.dismiss();
                } else {
                    if (bAddExtraDetails && (strMac != null)) {
                        jsRequest.put("app_id", Installation.id(context));
                        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                        jsRequest.put("app_version", packageInfo.versionName);

                        if (MySharedPref.getImei(context) != null) {
                            jsRequest.put("client_imei_no", MySharedPref.getImei(context));
                        } else if (GetAddress.getIMEI(context) != null) {
                            MySharedPref.saveImei(context, GetAddress.getIMEI(context));
                            jsRequest.put("client_imei_no", GetAddress.getIMEI(context));
                        } else
                            jsRequest.put("client_imei_no", "XXXXXXXXXXXXXXX");

                        if (GetAddress.getIPAddress(true) != null)
                            jsRequest.put("client_ip_address", GetAddress.getIPAddress(true));
                        else
                            jsRequest.put("client_ip_address", "XXX.XXX.XXX.XXX");

                        jsRequest.put("client_mac_address", strMac);
                        jsRequest.put("device_id", MySharedPref.getDeviceId(context));
                    }

                    Log.d(TAG, "callWebServiceUsingVolley: jsRequest"+jsRequest.toString());
                    RequestQueue queue = getRequestQueue(context);

                    String strFinalUrl = getHostUrl(context) + "api/" + strUrl;

                    // Request a string response from the provided URL.
                    JsonObjectRequest jsonObjRequest = new JsonObjectRequest(iRequestMethod, strFinalUrl, jsRequest,
                            volleyResponseListener,
                            volleyErrorResponseListener) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");
                            if (bAddToken) {
                                String strFinalToken = MySharedPref.getAuthorizationToken(context).getToken_type() + " " + MySharedPref.getAuthorizationToken(context).getAccess_token();
                              headers.put("Authorization", strFinalToken);
                            }

                            Log.d(TAG, "callWebServiceUsingVolley: headers"+headers.toString());
                            return headers;


                        }

                    };


                    // Add the request to the RequestQueue.
                    jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                            1000 * 20, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    queue.cancelAll(queue);
                    queue.add(jsonObjRequest);
                    queue.getCache().clear();
                }
            }


        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"019-016-" + strServiceCode);
        }
    }

    public static void callWebServiceUsingVolleyWithHeaders(final int iRequestMethod, final ProgressDialog pd, final Context context,
                                                     final String strServiceCode,
                                                     final Boolean bAddExtraDetails, final Boolean bAddToken,
                                                            String strUrl, JSONObject jsRequest,
                                                     final String strActivityName, Response.Listener<JSONObject> volleyResponseListener) {
            try
            {
                if(!pd.isShowing())
                {
                    pd.setMessage(context.getString(R.string.please_wait));
                    pd.setCancelable(false);
                    pd.setCanceledOnTouchOutside(false);
                    pd.show();
                }

                String strMac = GetAddress.getWifiMacAddress();

                if(MySharedPref.getMacAddress(context) != null)
                {
                    strMac = MySharedPref.getMacAddress(context);
                }
                else if (strMac != null)
                {
                    MySharedPref.saveMacAddress(context, GetAddress.getWifiMacAddress());
                }
                final String strMacFinal = strMac;


                if((bAddExtraDetails && (strMac == null)) )
                {
                    MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName, context.getString(R.string.allow_wifi_imei),"019-017-" + strServiceCode);
                    pd.dismiss();
                }
                else {

                    RequestQueue queue = getRequestQueue(context);

                    String strFinalUrl = getHostUrl(context) + "api/" + strUrl;

                    // Request a string response from the provided URL.
                    JsonObjectRequest jsonObjRequest = new JsonObjectRequest(iRequestMethod, strFinalUrl, jsRequest,
                            volleyResponseListener,
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    pd.dismiss();
                                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                        MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName, context.getString(R.string.error_no_connection),"019-018-" + strServiceCode);
                                    } else if (error instanceof AuthFailureError) {
                                        MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_server_default),"019-019-" + strServiceCode);
                                    } else if (error instanceof ServerError) {
                                        showVolley_error(context, strActivityName, error, strServiceCode);
                                    } else if (error instanceof NetworkError) {
                                        MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_server_default),"019-020-" + strServiceCode);
                                    } else if (error instanceof ParseError) {
                                        MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_server_default),"019-021-" + strServiceCode);
                                    } else if (error.toString().contains("NullPointerException")) {
                                        MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_null_pointer),"019-022-" + strServiceCode);
                                    }
                                }
                            }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json; charset=utf-8");

                            if(bAddExtraDetails)
                            {
                                try {
                                    headers.put("app_id", Installation.id(context));
                                    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                                    headers.put("app_version", packageInfo.versionName);

                                    if (MySharedPref.getImei(context) != null) {
                                        headers.put("client_imei_no", MySharedPref.getImei(context));
                                    } else if (GetAddress.getIMEI(context) != null) {
                                        MySharedPref.saveImei(context, GetAddress.getIMEI(context));
                                        headers.put("client_imei_no", GetAddress.getIMEI(context));
                                    } else
                                        headers.put("client_imei_no", "XXXXXXXXXXXXXXX");

                                    if (GetAddress.getIPAddress(true) != null)
                                        headers.put("client_ip_address", GetAddress.getIPAddress(true));
                                    else
                                        headers.put("client_ip_address", "XXX.XXX.XXX.XXX");

                                    headers.put("client_mac_address", strMacFinal);
                                    headers.put("device_id", MySharedPref.getDeviceId(context));
                                }catch(Exception e)
                                {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"019-023-" + strServiceCode);
                                }
                            }
                            if(bAddToken) {
                                String strFinalToken = MySharedPref.getAuthorizationToken(context).getToken_type() + " " + MySharedPref.getAuthorizationToken(context).getAccess_token();
                                headers.put("Authorization", strFinalToken);
                            }
                            return headers;
                        }
                    };
                    // Add the request to the RequestQueue.
                    jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                            1000 * 20, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    queue.cancelAll(queue);
                    queue.add(jsonObjRequest);
                    queue.getCache().clear();
                }

            } catch (Exception e) {
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error),  e.getMessage(),"019-024-" + strServiceCode);
            }
        }

    public static void callWebServiceUsingVolleyWithNetworkResponse(final int iRequestMethod, final ProgressDialog pd,
                                                                    final Context context, final String strServiceCode, final Map<String,String> headers,
                                                final Boolean bAddExtraDetails, final Boolean bAddNetworkResponse,
                                                 String strUrl, JSONObject jsRequest,
                                                final String strActivityName, Response.Listener<JSONObject> volleyResponseListener) {
        try
        {
            if(!pd.isShowing())
            {
                pd.setMessage(context.getString(R.string.please_wait));
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);
                pd.show();
            }

            String strMac = GetAddress.getWifiMacAddress();

            if(MySharedPref.getMacAddress(context) != null)
            {
                strMac = MySharedPref.getMacAddress(context);
            }
            else if (strMac != null)
            {
                MySharedPref.saveMacAddress(context, GetAddress.getWifiMacAddress());
            }
            final String strMacFinal = strMac;


            if((bAddExtraDetails && (strMac == null)) )
            {
                MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName, context.getString(R.string.allow_wifi_imei),"019-030-" + strServiceCode);
                pd.dismiss();
            }
            else {

                RequestQueue queue = getRequestQueue(context);

                String strFinalUrl = getHostUrl(context) + "api/" + strUrl;

                // Request a string response from the provided URL.
                JsonObjectRequest jsonObjRequest = new JsonObjectRequest(iRequestMethod, strFinalUrl, jsRequest,
                        volleyResponseListener,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pd.dismiss();
                                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName, context.getString(R.string.error_no_connection),"019-025-" + strServiceCode);
                                } else if (error instanceof AuthFailureError) {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_server_default),"019-026-" + strServiceCode);
                                } else if (error instanceof ServerError) {
                                    showVolley_error(context,  strActivityName, error, strServiceCode);
                                } else if (error instanceof NetworkError) {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_server_default),"019-027-" + strServiceCode);
                                } else if (error instanceof ParseError) {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_server_default),"019-028-" + strServiceCode);
                                } else if (error.toString().contains("NullPointerException")) {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName,context.getString(R.string.error_null_pointer),"019-029-" + strServiceCode);
                                }
                            }
                        }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> finalHeaders = new HashMap<String, String>();
                        finalHeaders = headers;
                        if(bAddExtraDetails)
                        {
                            try {
                                finalHeaders.put("app_id", Installation.id(context));
                                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                                finalHeaders.put("app_version", packageInfo.versionName);

                                if (MySharedPref.getImei(context) != null) {
                                    finalHeaders.put("client_imei_no", MySharedPref.getImei(context));
                                } else if (GetAddress.getIMEI(context) != null) {
                                    MySharedPref.saveImei(context, GetAddress.getIMEI(context));
                                    finalHeaders.put("client_imei_no", GetAddress.getIMEI(context));
                                } else
                                    finalHeaders.put("client_imei_no", "XXXXXXXXXXXXXXX");

                                if (GetAddress.getIPAddress(true) != null)
                                    finalHeaders.put("client_ip_address", GetAddress.getIPAddress(true));
                                else
                                    finalHeaders.put("client_ip_address", "XXX.XXX.XXX.XXX");

                                finalHeaders.put("client_mac_address", strMacFinal);
                                finalHeaders.put("device_id", MySharedPref.getDeviceId(context));
                            }catch(Exception e)
                            {
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error),  e.getMessage(),"019-031-" + strServiceCode);
                            }
                        }
                        return finalHeaders;
                    }

                    @Override
                    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                        try {
                            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

                            JSONObject jsonResponse = new JSONObject(json);
                            jsonResponse.put("headers", new JSONObject(response.headers));

                            return Response.success(jsonResponse,
                                    HttpHeaderParser.parseCacheHeaders(response));
                        } catch (UnsupportedEncodingException e) {
                            pd.dismiss();
                            return Response.error(new ParseError(e));
                        } catch (JsonSyntaxException e) {
                            pd.dismiss();
                            return Response.error(new ParseError(e));
                        } catch (JSONException e) {
                            pd.dismiss();
                            return Response.error(new ParseError(e));
                        }
                    }
                };
                // Add the request to the RequestQueue.
                jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                        1000 * 20, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.cancelAll(queue);
                queue.add(jsonObjRequest);
                queue.getCache().clear();
            }

        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error),  e.getMessage(),"019-031");
        }
    }

    public static void callWebServiceUsingVolleyforAuth(final int iRequestMethod, final ProgressDialog pd, final Context context,
                                                           final String strServiceCode,
                                                           final Map<String,String> headers,
                                                            final Map<String, String> params, String strUrl,
                                                            final String strActivityName,
                                                        Response.Listener<String> volleyResponseListener) {
        try {
            if (!pd.isShowing()) {
                pd.setMessage(context.getString(R.string.please_wait));
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);
                pd.show();
            }

            String strFinalUrl = getHostUrl(context) + "oauth/" + strUrl;
            RequestQueue queue = getRequestQueue(context);

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(iRequestMethod, strFinalUrl,
                    volleyResponseListener,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pd.dismiss();
                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName, context.getString(R.string.error_no_connection),"019-032-" + strServiceCode);
                            } else if (error instanceof AuthFailureError) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName, context.getString(R.string.error_server_default),"019-033-" + strServiceCode);
                            } else if (error instanceof ServerError) {
                                showVolley_error(context, strActivityName, error, strServiceCode);
                            } else if (error instanceof NetworkError) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName, context.getString(R.string.error_server_default),"019-034-" + strServiceCode);
                            } else if (error instanceof ParseError) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName, context.getString(R.string.error_server_default),"019-035-" + strServiceCode);
                            } else if (error.toString().contains("NullPointerException")) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, strActivityName, context.getString(R.string.error_null_pointer),"019-036-" + strServiceCode);
                            }
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            return params;
                        }


                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            return headers;
                        }
                    };

            // Add the request to the RequestQueue.
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    1000 * 20, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.cancelAll(queue);
            queue.add(stringRequest);
            queue.getCache().clear();

        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"019-037-" + strServiceCode);
        }
    }




    public static void showVolley_error(Context context, String strErrorTitle, VolleyError error, final String strServiceCode) {
        NetworkResponse response = error.networkResponse;
        try {
            // check error code and related message
            String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
/*
            Log.d(TAG, "showVolley_error:NetworkResponse "+error.);
*/

            if(res.contains("503 Service Temporarily Unavailable"))
                MyAlert.showAlert(context,R.mipmap.icon_error, strErrorTitle, context.getString(R.string.str_service_unavailable) ,"019-038-" + strServiceCode);
            else {
                JSONObject obj = new JSONObject(res);
                String strErrorMessage = "";
                if (obj.has("message")) {
                    strErrorMessage = strErrorMessage + obj.getString("message");

                    if (obj.has("response"))
                        strErrorMessage = strErrorMessage + "\n" + obj.getString("response");
                    MyAlert.showAlert(context, R.mipmap.icon_error, strErrorTitle, strErrorMessage, "019-038-" + strServiceCode);

                }

                if (obj.has("error")) {
                    strErrorMessage = strErrorMessage + "\n" + obj.getString("error");

                    if (obj.has("error_description"))
                        strErrorMessage = strErrorMessage + "\n" + obj.getString("error_description");

                    if (obj.getString("error").equalsIgnoreCase("invalid_token")) {
                        final Dialog dialogAlert = new Dialog(context);
                        MyAlert.dialogForOk
                                (context, R.mipmap.icon_error, context.getString(R.string.app_error),
                                        context.getString(R.string.invald_token_msg),
                                        dialogAlert,
                                        context.getString(R.string.ok),
                                        new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialogAlert.dismiss();
                                            }
                                        }, "019-039-" + strServiceCode);
                        Intent intent;
                        ((Activity) context).startActivity(new Intent(((Activity) context), LoginActivity.class));
                        ((Activity) context).finishAffinity();
                    } else if (obj.getString("error").equalsIgnoreCase("invalid_grant")) {
                        MyAlert.showAlert(context, R.mipmap.icon_error, strErrorTitle, context.getString(R.string.incorrect_user_id_or_password), "019-040-" + strServiceCode);
                    } else
                        MyAlert.showAlert(context, R.mipmap.icon_error, strErrorTitle, strErrorMessage, "019-041-" + strServiceCode);
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "showVolley_error: "+e.toString());
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error),  e.getMessage(),"019-042-" + strServiceCode);
        }
    }

    public static String getHostUrl(Context context)
    {
        //return Constants.DOMAIN_TRAINING;

        String strHostUrl = "";
        if( (MySharedPref.getAppMode(context) == null) ||  (MySharedPref.getAppMode(context).equalsIgnoreCase("null")) || MySharedPref.getAppMode(context).equalsIgnoreCase("A") )
            strHostUrl = Constants.DOMAIN_MAIN;
        else if( MySharedPref.getAppMode(context).equalsIgnoreCase("T") )
            strHostUrl = Constants.DOMAIN_TRAINING;
        else
            strHostUrl = Constants.DOMAIN_MAIN;

        return strHostUrl;
    }

    public static RequestQueue getRequestQueue(Context context)
    {
        RequestQueue queue;
        if(Constants.DOMAIN_MAIN.contains("https"))
            //queue = Volley.newRequestQueue(context, new HurlStack(null, getSocketFactory(context)));
            queue = Volley.newRequestQueue(context, null);
        else
            queue = Volley.newRequestQueue(context, null);

        return queue;

    }

    private static SSLSocketFactory getSocketFactory(Context context)
    {
        return SSLCertification.getSocketFactory(context);
    }
}
