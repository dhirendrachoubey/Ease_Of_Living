package in.nic.ease_of_living.gp;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import in.nic.ease_of_living.dbo.SQLiteHelper;
import in.nic.ease_of_living.dbo.SQLiteMaster;
import in.nic.ease_of_living.supports.IsConnected;
import in.nic.ease_of_living.supports.MySharedPref;
import in.nic.ease_of_living.supports.MyVolley;
import in.nic.ease_of_living.utils.AESHelper;


public class Splash extends Activity {

    private ProgressBar progressBar;
    Context context;
    private ProgressDialog pd;
    private String strAndroidAction, strAndroidVersion;
    private int flag=0;
    private String strLanguageToLoad = "en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;

        try{
            if(!MySharedPref.getIsSharedPrefCleared(context))
            {
                MySharedPref.clearSharedPref(context);
                MySharedPref.saveIsSharedPrefCleared(context,true);
                new SQLiteHelper(context).deleteDb();
                new SQLiteMaster(context).deleteDb();

            }

        }catch(Exception e)
        {

        }
        /* Set Language */
        if (MySharedPref.getLocaleLanguage(context) != null)
        {
            strLanguageToLoad = MySharedPref.getLocaleLanguage(context);
            MySharedPref.setStrLanguage(strLanguageToLoad);
        }
        Locale locale = new Locale(strLanguageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());


        /* It delays the spash screen for 2000ms. Other wise splash screen will be jumped fast.*/
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /*if(MySharedPref.getDeviceId(context)==null)
                        getApp_log(context, Installation.id(context));*/
                    if(IsConnected.isInternet_connected(context, false))
                        checkNotification();
                        /*if(MySharedPref.getCurrentUser(context)!=null && MySharedPref.getCurrentUser(context).getPc_code()>0) {
                            if(flag<1) {
                                checkNotification();
                                //checkUpdate();
                            }
                            flag++;
                        }
                    */
                        else {
                        startActivity(new Intent(Splash.this, LoginActivity.class));
                        finish();
                    }
                }
            }, 5000);

    }

    /* Function to check the app version on device and ask the user to update the latest version if not installed yet.*/
    private void checkUpdate()
    {
        if((pd==null) || ( (pd!=null) && (!pd.isShowing()) ) )
            pd = new ProgressDialog(context);
        try
        {
            JSONObject jsRequest = new JSONObject();
            MyVolley.callWebServiceUsingVolley(Request.Method.GET, pd, context,"006", false, false, "common/v1/getAppVersion/", jsRequest, context.getString(R.string.app_error),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject jsResponse) {
                        try {
                            pd.dismiss();
                            if (jsResponse.getBoolean("status")) {
                                strAndroidVersion = jsResponse.getJSONArray("response").getJSONObject(0).getString("version_id");
                                strAndroidAction = jsResponse.getJSONArray("response").getJSONObject(0).getString("andriod_action");

                                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                                int versionName = Integer.parseInt((packageInfo.versionName.replace(".", "").trim()));
                                int playstoreVersion = Integer.parseInt(strAndroidVersion.replace(".", "").trim());
                                if (playstoreVersion > versionName) {

                                    final Dialog dialog = new Dialog(context);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setContentView(R.layout.my_custom_dialog);
                                    Window window = dialog.getWindow();
                                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                                    dialog.setCancelable(false);
                                    TextView textTittle = (TextView) dialog.findViewById(R.id.tvTitle);
                                    TextView text = (TextView) dialog.findViewById(R.id.tvMessage);
                                    TextView tvDateTime = (TextView) dialog.findViewById(R.id.tvDateTime);
                                    TextView tvAlertId = (TextView) dialog.findViewById(R.id.tvAlertId);

                                    Calendar c = Calendar.getInstance();

                                    SimpleDateFormat df = new SimpleDateFormat("dd MMM, yy (HH:mm)");
                                    String formattedDate = df.format(c.getTime());
                                    tvDateTime.setText(formattedDate);


                                    Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                                    Button dialogButtonSkip = (Button) dialog.findViewById(R.id.dialogButtonSkip);
                                    //ImageView ivClose = (ImageView) dialog.findViewById(R.id.ivClose);
                                    text.setGravity(Gravity.CENTER);
                                    //ivClose.setVisibility(View.GONE);
                                    dialogButtonSkip.setVisibility(View.VISIBLE);
                                    // Force Update
                                    if(strAndroidAction.equalsIgnoreCase("2")) {
                                        textTittle.setText(context.getString(R.string.ma_version_update));
                                        text.setText(context.getString(R.string.force_update_version));
                                        dialogButtonSkip.setText(context.getString(R.string.later));
                                    }
                                    // Optional update
                                    else if(strAndroidAction.equalsIgnoreCase("1"))
                                    {
                                        textTittle.setText(context.getString(R.string.ma_version_update));
                                        text.setText(context.getString(R.string.update_version_optional));

                                        dialogButtonSkip.setText(context.getString(R.string.no_thanks));
                                    }
                                    else
                                    {
                                        startActivity(new Intent(Splash.this, LoginActivity.class));
                                        finish();
                                    }
                                    TextView textType = (TextView) dialog.findViewById(R.id.tvMessageType);
                                    textType.setVisibility(View.GONE);


                                    dialogButton.setText(context.getString(R.string.update_now));
                                    dialogButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            try {
                                                dialog.dismiss();
                                                if(strAndroidAction.equalsIgnoreCase("1")) {
                                                    startActivity(new Intent(Splash.this, LoginActivity.class));
                                                    finish();
                                                }
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=in.nic.mission_antyodaya.gp")));

                                            } catch (ActivityNotFoundException anfe) {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=in.nic.mission_antyodaya.gp")));
                                            }
                                        }
                                    });
                                    dialogButtonSkip.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            if(strAndroidAction.equalsIgnoreCase("2")) {
                                                if (Build.VERSION.SDK_INT >= 21) {
                                                    finishAndRemoveTask();
                                                } else {
                                                    finish();
                                                }
                                            }
                                            else if(strAndroidAction.equalsIgnoreCase("1")) {
                                                startActivity(new Intent(Splash.this, LoginActivity.class));
                                                finish();
                                            }
                                        }
                                    });

                                    dialog.show();
                                } else {
                                    startActivity(new Intent(Splash.this, LoginActivity.class));
                                    finish();
                                }
                            }

                        } catch (Exception e)
                        {
                            pd.dismiss();
                            startActivity(new Intent(Splash.this, LoginActivity.class));
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pd.dismiss();
                            //MyVolley.showVolley_error(context,"",error);
                            startActivity(new Intent(Splash.this, LoginActivity.class));
                            finish();
                        }

                    }
            );
        } catch (Exception e) {
            pd.dismiss();
            startActivity(new Intent(Splash.this, LoginActivity.class));
            finish();
        }
    }

    /* Function to call webservice to check notification */
    private void checkNotification()
    {
        if((pd==null) || ( (pd!=null) && (!pd.isShowing()) ) )
            pd = new ProgressDialog(context);

        try {
            JSONObject jsRequest = new JSONObject();

            if(MySharedPref.getCurrentUser(context) != null)
                jsRequest.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getEmail_id()));

                MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context,"007", false, false, "notification/v3", jsRequest, context.getString(R.string.app_error),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {

                                if (jsResponse.getBoolean("status")) {
                                    //pd.dismiss();
                                    final Dialog dialog = new Dialog(context);
                                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialog.setContentView(R.layout.layout_notification);
                                    Window window = dialog.getWindow();
                                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                    window.setBackgroundDrawableResource(R.color.transparent);
                                    TextView text = (TextView) dialog.findViewById(R.id.tvMessage);
                                    TextView textTittle = (TextView) dialog.findViewById(R.id.tvMessageTitle);
                                    textTittle.setText(context.getString(R.string.app_notification));
                                    JSONArray arr = jsResponse.getJSONArray("response");
                                    text.setText("");
                                    for (int i = 0; i < arr.length(); i++) {
                                        JSONObject jsObj = (JSONObject) arr.get(i);
                                        text.append(jsObj.get("message_text").toString());
                                        text.append("\n");
                                    }

                                    TextView textType = (TextView) dialog.findViewById(R.id.tvMessageType);
                                    textType.setVisibility(View.GONE);

                                    Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                                    // if button is clicked, close the custom dialog
                                    dialogButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                            checkUpdate();
                                        }
                                    });

                                    dialog.show();
                                } else {
                                    checkUpdate();
                                }
                            } catch (Exception e) {
                                checkUpdate();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //MyVolley.showVolley_error(context,"",error);
                            checkUpdate();
                        }

                    });
        }catch(Exception e)
        {
            checkUpdate();

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
    protected void onDestroy() {
        super.onDestroy();
    }
}