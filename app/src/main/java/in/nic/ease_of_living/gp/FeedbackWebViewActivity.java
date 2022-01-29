package in.nic.ease_of_living.gp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

/*009-012*/
public class FeedbackWebViewActivity extends AppCompatActivity implements View.OnClickListener, Communicator {

    Context context;
    private MenuItem menuItem;
    private NetworkChangeReceiver mNetworkReceiver;
    private String strUserPassw = null;
    private WebView wvReport;
    private ProgressDialog pd;
    private static final int INPUT_FILE_REQUEST_CODE = 1;
    private static final int FILECHOOSER_RESULTCODE = 1;
    private WebSettings webSettings;
    private ValueCallback<Uri> mUploadMessage;
    private Uri mCapturedImageURI = null;
    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath,strLanguageToLoad;
    String TAG="FeedbackWebViewActivity";

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
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                mNetworkReceiver = new NetworkChangeReceiver();
                NetworkRegistered.registerNetworkBroadcastForNougat(context, mNetworkReceiver);

                pd = new ProgressDialog(context);
                pd.setMessage(context.getString(R.string.please_wait));
                pd.setCancelable(false);
                pd.setCanceledOnTouchOutside(false);
                pd.show();

                wvReport = (WebView) findViewById(R.id.wvReport);
                webSettings = wvReport.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setLoadWithOverviewMode(true);
                webSettings.setAllowFileAccess(true);

                if(IsConnected.isInternet_connected(context, true))
                    getSalt();
                else
                    pd.dismiss();
            }
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.feedback_error), e.getMessage(),"009-001");
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

            MyVolley.callWebServiceUsingVolleyWithNetworkResponse(Request.Method.POST, pd, context,"004" ,headers, false,
                    true, "common/v1/getSalt", jsRequest, context.getString(R.string.feedback_error),
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
                                        String strUrl = Common.getWebUrlValue(context, "url_feedback");


                                        wvReport.setWebViewClient(new Client());
                                        wvReport.setWebChromeClient(new ChromeClient());
                                        if (Build.VERSION.SDK_INT >= 19) {
                                            wvReport.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                                        }
                                        else if(Build.VERSION.SDK_INT >=11 && Build.VERSION.SDK_INT < 19) {
                                            wvReport.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                                        }

                                        wvReport.loadUrl(strUrl, headers);
                                        pd.dismiss();

                                    } else {
                                        pd.dismiss();
                                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.feedback_error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"009-003");
                                    }
                                }
                                else {
                                    pd.dismiss();
                                    MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.feedback_error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"009-004");
                                }

                            } catch (Exception e) {
                                pd.dismiss();
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.feedback_error),  e.getMessage(),"009-005");
                            }
                        }
                    });

        } catch(Exception e)
        {
            pd.dismiss();
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.feedback_error),  e.getMessage(),"009-002");
        }
    }

    public class ChromeClient extends WebChromeClient {
        // For Android 5.0
        public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePath, WebChromeClient.FileChooserParams fileChooserParams) {
            try {
                // Double check that we don't have any existing callbacks
                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePath;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                    } catch (IOException e) {
                        // Error occurred while creating the File
                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.feedback_error),  e.getMessage(),"009-006");
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                    } else {
                        takePictureIntent = null;
                    }
                }
                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");
                Intent[] intentArray;
                if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                } else {
                    intentArray = new Intent[0];
                }
                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);
                return true;
            }catch(Exception e)
            {
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.feedback_error),  e.getMessage(),"009-007");
            }
            return false;
        }

        // openFileChooser for Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            try {
                mUploadMessage = uploadMsg;
                // Create AndroidExampleFolder at sdcard
                // Create AndroidExampleFolder at sdcard
                File imageStorageDir = new File(
                        Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES)
                        , "AndroidExampleFolder");
                if (!imageStorageDir.exists()) {
                    // Create AndroidExampleFolder at sdcard
                    imageStorageDir.mkdirs();
                }
                // Create camera captured image file path and name
                File file = new File(
                        imageStorageDir + File.separator + "IMG_"
                                + String.valueOf(System.currentTimeMillis())
                                + ".jpg");
                mCapturedImageURI = Uri.fromFile(file);
                // Camera capture image intent
                final Intent captureIntent = new Intent(
                        android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                // Create file chooser intent
                Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
                // Set camera intent to file chooser
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS
                        , new Parcelable[]{captureIntent});
                // On select image call onActivityResult method of activity
                startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
            }catch(Exception e)
            {
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.feedback_error), e.getMessage(),"009-008");
            }
        }

        // openFileChooser for Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, "");
        }

        //openFileChooser for other Android versions
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType,
                                    String capture) {
            openFileChooser(uploadMsg, acceptType);
        }
    }

    public class ClientNew extends WebViewClient {
        ProgressDialog progressDialog;
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // If url contains mailto link then open Mail Intent
            if (url.contains("mailto:")) {
                // Could be cleverer and use a regex
                //Open links in new browser
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                // Here we can open new activity
                return true;
            }else {
                // Stay within this webview and load url
                view.loadUrl(url);
                return true;
            }
        }
        //Show loader on url load
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // Then show progress  Dialog
            // in standard case YourActivity.this
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(FeedbackWebViewActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
            }
        }
        // Called when all page resources loaded
        public void onPageFinished(WebView view, String url) {
            try {
                // Close progressDialog
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public class Client extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }

        @SuppressWarnings("deprecation")
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            try {
                // Handle the error
                final Dialog dialog_alert = new Dialog(context);
                MyAlert.dialogForOk
                        (context, R.mipmap.icon_info, context.getString(R.string.feedback_info),
                                context.getString(R.string.open_feedback_failure),
                                dialog_alert,
                                context.getString(R.string.ok),
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog_alert.dismiss();
                                        pd.dismiss();
                                        Intent intent = new Intent(FeedbackWebViewActivity.this, Home.class);
                                        intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                                        intent.putExtra("user_password", strUserPassw);
                                        startActivity(intent);
                                        finishAffinity();

                                    }
                                },"009-011");
            }catch(Exception e)
            {
                pd.dismiss();
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.feedback_error), e.getMessage(),"009-009");
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
                        (context, R.mipmap.icon_warning, context.getString(R.string.feedback_warning),
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
                                },"009-012");
            }catch(Exception e)
            {
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.feedback_error),  e.getMessage(),"009-010");
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }
            Uri[] results = null;
            // Check that the response is a good one
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }
            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            if (requestCode != FILECHOOSER_RESULTCODE || mUploadMessage == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }
            if (requestCode == FILECHOOSER_RESULTCODE) {
                if (null == this.mUploadMessage) {
                    return;
                }
                Uri result = null;
                try {
                    if (resultCode != RESULT_OK) {
                        result = null;
                    } else {
                        // retrieve from the private variable if the intent is null
                        result = data == null ? mCapturedImageURI : data.getData();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "activity :" + e,
                            Toast.LENGTH_LONG).show();
                }
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }
        return;
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

    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.getItem(0).setVisible(true);
        menu.getItem(1).setVisible(true);
        menu.getItem(1).setIcon(R.mipmap.icon_support);
        return true;
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


}
