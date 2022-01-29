package in.nic.ease_of_living.gp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.nic.ease_of_living.broadcast.NetworkChangeReceiver;
import in.nic.ease_of_living.interfaces.Communicator;
import in.nic.ease_of_living.supports.IsConnected;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MySharedPref;
import in.nic.ease_of_living.supports.MyVolley;
import in.nic.ease_of_living.supports.Password;
import in.nic.ease_of_living.utils.AESHelper;
import in.nic.ease_of_living.utils.Common;
import in.nic.ease_of_living.utils.NetworkRegistered;
import android.annotation.TargetApi;
import android.webkit.WebResourceError;
import android.webkit.WebViewClient;

/*012-009*/
public class ReportWebViewActivity extends AppCompatActivity implements View.OnClickListener, Communicator {

    Context context;
    private MenuItem menuItem;
    private NetworkChangeReceiver mNetworkReceiver;
    private String strUserPassw = null;
    private WebView wvReport;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            context = this;

            Intent intent = getIntent();
            strUserPassw = intent.getStringExtra("user_password");
            if (Password.matchUserCredentials(context, intent.getStringExtra("user_id"), strUserPassw)) {
                setContentView(R.layout.activity_report_web_view);
                Common.setAppHeader(context);
                //getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getString(R.string.app_name) + "</font>"));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                mNetworkReceiver = new NetworkChangeReceiver();
                NetworkRegistered.registerNetworkBroadcastForNougat(context, mNetworkReceiver);

                pd = new ProgressDialog(context);
                pd.setMessage(context.getString(R.string.please_wait));
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);
                pd.show();

                wvReport = (WebView) findViewById(R.id.wvReport);
                WebSettings webSettings = wvReport.getSettings();
                webSettings.setJavaScriptEnabled(true);

                if(IsConnected.isInternet_connected(context, true))
                    getSalt();
                else
                    pd.dismiss();
            }
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.adm_reports_error),  e.getMessage(),"012-001");
        }

    }

    /* Call webservice to get salt*/
    private void getSalt() {
        try {
            if((pd==null) || ( (pd!=null) && (!pd.isShowing()) ) )
            {
                pd.setMessage(context.getString(R.string.please_wait));
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);
                pd.show();
            }
            Map<String,String> headers = new HashMap<String, String>();

            JSONObject jsRequest = new JSONObject();
            jsRequest.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
            jsRequest.put("user_login_type", "U");

            MyVolley.callWebServiceUsingVolleyWithNetworkResponse(Request.Method.POST, pd, context,"004", headers, false,
                    true, "common/v1/getSalt", jsRequest, context.getString(R.string.adm_reports_error),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                if (jsResponse.getBoolean("status")) {
                                    String strSalt = null;
                                    if (jsResponse.getJSONObject("headers").has("salt")) {
                                        strSalt = jsResponse.getJSONObject("headers").getString("salt");
                                    }
                                    if (strSalt != null) {
                                        pd.dismiss();
                                        String strFinalPassword = Password.getFinalPassword(strSalt,MySharedPref.getCurrentUser(context).getUser_password());
                                        Map<String,String> headers = new HashMap<String, String>();
                                        headers.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getEmail_id()));
                                        headers.put("password", strFinalPassword);
                                        String strFinalToken = MySharedPref.getAuthorizationToken(context).getToken_type() + " " + MySharedPref.getAuthorizationToken(context).getAccess_token();
                                        headers.put("Authorization", strFinalToken);
                                        headers.put("token", MySharedPref.getAuthorizationToken(context).getAccess_token());
                                        String strUrl = Common.getWebUrlValue(context, "url_report");
                                        wvReport.loadUrl(strUrl, headers);
                                        wvReport.setWebViewClient(new Client());
                                        pd.dismiss();

                                    } else {
                                        pd.dismiss();
                                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.adm_reports_error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"012-003");
                                    }
                                }
                                else {
                                    pd.dismiss();
                                    MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.adm_reports_error),  jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"012-004");
                                }

                            } catch (Exception e) {
                                pd.dismiss();
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.adm_reports_error), e.getMessage(),"012-005");
                            }
                        }
                    });

        } catch (Exception e) {
            pd.dismiss();
            MyAlert.showAlert(context,R.mipmap.icon_error,  context.getString(R.string.adm_reports_error),  e.getMessage(),"012-002");
        }
    }

    public class Client extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
        {
            return false;
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            try {
                // Handle the error
                final Dialog dialog_alert = new Dialog(context);
                MyAlert.dialogForOk
                        (context, R.mipmap.icon_info, context.getString(R.string.adm_reports_info),
                                context.getString(R.string.open_reports_failure),
                                dialog_alert,
                                context.getString(R.string.ok),
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog_alert.dismiss();
                                        pd.dismiss();
                                        Intent intent = new Intent(ReportWebViewActivity.this, Home.class);
                                        intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                                        intent.putExtra("user_password", strUserPassw);
                                        startActivity(intent);
                                        finishAffinity();

                                    }
                                },"012-008");
            }catch (Exception e) {
                pd.dismiss();
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.adm_reports_error),  e.getMessage(),"012-006");
            }
        }

        @TargetApi(android.os.Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
            // Redirect to deprecated method, so you can use it in all SDK versions
            onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
        }

        @Override
        public void onReceivedSslError (WebView view, final SslErrorHandler handler, SslError error) {
            try {
                String strMessage = "SSL Certificate error.";
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        strMessage = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        strMessage = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        strMessage = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_NOTYETVALID:
                        strMessage = "The certificate is not yet valid.";
                        break;
                }
                strMessage += " Do you want to continue anyway?";

                final Dialog dialogAlert = new Dialog(context);
                MyAlert.dialogForCancelOk
                        (context,R.mipmap.icon_warning, context.getString(R.string.adm_reports_warning),
                                strMessage,
                                dialogAlert,
                                context.getString(R.string.ok),
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialogAlert.dismiss();
                                        handler.proceed();
                                    }
                                },
                                context.getString(R.string.cancel),
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        handler.cancel();
                                        dialogAlert.dismiss();
                                    }
                                },
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        handler.cancel();
                                        dialogAlert.dismiss();
                                    }
                                },"012-009");
            }catch (Exception e) {
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.adm_reports_error),  e.getMessage(),"012-007");
            }
        }
    }



    //Check whether there’s any WebView history that the user can navigate back to//
    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK) && wvReport.canGoBack()) {
            wvReport.goBack();
            //If there is history, then the canGoBack method will return ‘true’//
            return true;
        }

        //If the button that’s been pressed wasn’t the ‘Back’ button, or there’s currently no
        //WebView history, then the system should resort to its default behavior and return
        //the user to the previous Activity//
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_webview, menu);
        //return true;
        menuItem = menu.findItem(R.id.action_internet_status);
        if(!IsConnected.isInternet_connected(context, false))
            menuItem.setIcon(R.mipmap.icon_offline_status);
        else
            menuItem.setIcon(R.mipmap.icon_online_status);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                break;

            case R.id.action_webview_home:
                if(IsConnected.isInternet_connected(context, true))
                    this.getSalt();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void event(Boolean data) {
        try
        {
            if (data) {
                menuItem.setIcon(R.mipmap.icon_online_status);
            } else {
                menuItem.setIcon(R.mipmap.icon_offline_status);
            }
        } catch (NullPointerException e) {
            // MyAlert.showAlert(context, getString(R.string.app_error), "EX-001-015");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NetworkRegistered.unregisterNetworkChanges(context, mNetworkReceiver);
    }
    /*@Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
     handler.proceed();
    }*/
}
