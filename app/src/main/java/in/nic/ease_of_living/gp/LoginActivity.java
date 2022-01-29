package in.nic.ease_of_living.gp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;

import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.InputFilter;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import in.nic.ease_of_living.adapter.PhoneOptionAdapter;
import in.nic.ease_of_living.broadcast.NetworkChangeReceiver;
import in.nic.ease_of_living.dbo.DBHelper;
import in.nic.ease_of_living.dbo.SQLiteHelper;
import in.nic.ease_of_living.dbo.SQLiteMaster;
import in.nic.ease_of_living.firebase_notification.NotificationUtils;
import in.nic.ease_of_living.installation.Installation;
import in.nic.ease_of_living.interfaces.BooleanVariableListener;
import in.nic.ease_of_living.interfaces.Communicator;
import in.nic.ease_of_living.interfaces.Constants;
import in.nic.ease_of_living.models.AuthorizationToken;
import in.nic.ease_of_living.models.User;
import in.nic.ease_of_living.models.Weburl;
import in.nic.ease_of_living.supports.GetAddress;
import in.nic.ease_of_living.supports.IsConnected;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MySharedPref;
import in.nic.ease_of_living.supports.MyVolley;
import in.nic.ease_of_living.supports.Password;
import in.nic.ease_of_living.supports.RedAsterisk;
import in.nic.ease_of_living.utils.AESHelper;
import in.nic.ease_of_living.utils.Authorization;
import in.nic.ease_of_living.utils.Common;
import in.nic.ease_of_living.utils.InputFilters;
import in.nic.ease_of_living.utils.NetworkRegistered;
import in.nic.ease_of_living.utils.OtpHandler;
import in.nic.ease_of_living.utils.Support;


/**
 * Created by Neha Jain on 6/14/2017.
 */
/*  003-077  Max - ,"031-004"*/
    public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Communicator {
    Context context;
    private MenuItem menuItem;
    private ProgressDialog pd;
    private NetworkChangeReceiver mNetworkReceiver;
    private String strLanguageToLoad = "en";
    private static final int PERMISSIONS_MULTIPLE_REQUEST = 123;
    private String strAppVersion = null;
    private String strImei = null;
    private String strMac = null;
    private String strSalt = null;
    private ArrayList<User> al_user;
    private String strApplicationValue = null;
    private EditText etMobileOtp, etMobile;
    private TextView tvVerifyOtpApproved, tvMessage;
    private LinearLayout llOtp;
    private Button btnSubmit;
    private TextView tvLogin,tvUserId,tvUserPassword,tvSignUp, tvForgotPassword, tvDeRegister,tvContactUs,tvEmail,tvInstructions, tvLoginAnotherUser, tvChangeAppMode;
    private EditText etUserId,etUserPassword;
    private CheckBox cbHide;
    private LinearLayout llLogin,llInstruction;
    private Button btnLogin,btnOk;
    private GridView gvCall;
    private PhoneOptionAdapter mPhoneAdapter;
    private ArrayList<String> alCall;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    String TAG = "LoginActivity";

    String instruction_old = "<font color = #237DC1>Instruction for Installation</font><br>" +
            "1.   Install this latest version of the App from Google Play store.<br> " +
            "2.   After Installation the password is required to be reset for successful login. <br>" +
            "3.   All the training data has been flushed . <br>" +
            "4.   Now actual One Time Password (OTP) received in SMS will have to be filled when asked for.  <br><br>" +
            "<font color = #237DC1>What’s New?</font><br>" +
            "- The number of mandatory fields in registration/new sign-up is reduced. <br>" +
            "- Mobile  phone number can also be used as user id for login. <br>" +
            "- Forget Password feature is available for resetting strPassword. <br>" +
            "- Remove Registration  facility is provided for deleting registration. <br>" +
            "- PDF can be generated for printing of finalized GP Data. <br>" +
            "- Questions are available in English , Hindi and Bangla languages. <br>" +
            "- After completing and Uploading of one GP, the team can change the GP. For Change of GP internet connectivity should be available.";



    String strInstructionOld = "<u><font color = #237DC1>Instruction for Installation</font></u><br>" +
            " - Install the new version of Mission Antyodaya mobile App from Google playstore." +
            "On installing new version, all the previous Mission Antyodaya data (if any) on device will be permanently removed. " +
            "<br> - However, any exported file or PDF will not be deleted.  " +
            "<br><br><u><font color = #237DC1>What’s New?</font></u><br>" +
            "<br> - Enhancements in Questionnaire." +
            "<br> - Capturing the Parliament and Assembly Constituency Details." +
            "<br> - Coverage of all 29 subjects of the 11th schedule in Part-A." +
            "<br> - Introduction of new part-B of questionnaire." +
            "<br> - Saving draft facility at each section of Part A & B." +
            "<br> - The completed section is shown in green colour to identify incomplete sections." +
            "<br> - Minor bug fixing." +
            "<br> - Training mode has been provided for testing purpose and to keep the training data separated." +
            "<br> - Login as another user option has been included." +
            "<br> - Feedback functionality has been added for ease." +
            "<br><br><font color = #237DC1>For GP Users</font>" +
            "<br> - Generation of Village-wise PDF of Survey data." +
            "<br> - Generation of PDF for Draft & Finalised Survey data." +
            "<br> - Uploading scanned file of Gram Panchayat verified and signed survey data." +
            "<br> - In case the base data is downloaded by mistake for any GP, the user can reset the current selection and begin from initial stage. " +
            "<br><br><font color = #237DC1>For Nodal Officers</font>" +
            "<br> - Nodal officer can approve the users upto one level in hierarchy. " +
            "<br> - State nodal officer can approve only district user." +
            "<br> - District nodal officer can approve Block & Gram Panchayat user." +
            "<br> - Block nodal officer can approve Gram Panchayat user." +
            "<br> - For state nodal officers, the district dropdowns will display only those districts where any user is pending for approval. " +
            "<br><br><u><font color = #237DC1>Key Information :-</font></u><br>" +
            "- Mobile  phone number can also be used as user ID for login. <br>" +
            "- Actual One Time Password (OTP) received in SMS will have to be filled as and when asked <br>" +
            "- Forget Password feature is available to reset the Password. <br>" +
            "- Remove Registration facility is provided to remove the registration. <br>" +
            "- PDF can be generated for printing of finalized GP Data. <br>" +
            "- Questions are available in English , Hindi and Bangla languages. <br>" +
            "- After completing and Uploading of one GP, the user can change the GP and internet connectivity is required for this purpose.";

String strInstruction = "<u><font color = #237DC1>Instruction for Installation</font></u><br>" +
            " - Install the new version of Ease of Living (EOL) mobile App from Google playstore." +
            "On installing new version, all the previous EOL data (if any) on device will be permanently removed." +
            "<br> - However, any saved PDF will not be deleted.  " +
            "<br><br><u><font color = #237DC1>Highlights</font></u><br>" +
            "<br> - Collection of survey data on major flagship schemes of Govt. of India such as MGNREGA, PMAY-G and more" +
            "<br> - User friendly interface" +
            "<br> - Training mode has been provided for testing purpose and to keep the training data separated" +
            "<br> - Login as another user option has been included" +
            "<br> - Change GP option for users where one enumerator is working in more than one Gram Panchayat" +
            "<br> - Reset data functionality if user has not downloaded the base data as per PDF" +
            "<br> - Saving draft facility to review before final uploading" +
            "<br> - Marking completion status for every part of households under village" +
            "<br><br><font color = #237DC1>For GP Users</font>" +
            "<br> - Downloading of base data PDF for each Village Part (Enumeration block)" +
            "<br> - Base data for each village part will be available for download  only for the user (enumerator) who has been assigned for it." +
            "<br> - Offline fill up of the survey form once base data downloaded" +
            "<br> - Search on household number or advanced search on name, father  name, mother name &amp; YOB. " +
            "<br> - View the survey status under show GP data section " +
            "<br> - Change Gram Panchayat in case working on more than one GP. " +
            "<br> - Marking of completion status. " +
            "<br><br><font color = #237DC1>For Nodal Officers</font>" +
            "<br> - Nodal officer can approve the users upto one level in hierarchy. " +
            "<br> - State nodal officer can approve only district user." +
            "<br> - District nodal officer can approve Block & Gram Panchayat user." +
            "<br> - Block nodal officer can approve Gram Panchayat user." +
            "<br> - For state nodal officers, the district dropdowns will display only those districts where any user is pending for approval. " +
            "<br><br><u><font color = #237DC1>Key Information :-</font></u><br>" +
            "- Mobile  phone number can also be used as user ID for login. <br>" +
            "- Actual One Time Password (OTP) received in SMS will have to be filled as and when asked <br>" +
            "- Forget Password feature is available to reset the Password. <br>" +
            "- Remove Registration facility is provided to remove the registration. <br>" +
            "- PDF password will be notified to the user during PDF download. <br>" +
            "- After completing and Uploading of one GP, the user can change the GP and internet connectivity is required for this purpose.";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        Common.setAppHeader(context);
        mNetworkReceiver = new NetworkChangeReceiver();
        NetworkRegistered.registerNetworkBroadcastForNougat(context, mNetworkReceiver);
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Constants.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_GLOBAL);


                } else if (intent.getAction().equals(Constants.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");


                }

            }
        };

        findViews();
        setListener();

        tvLogin.setText(Constants.APP_EDITION + " " + Constants.DOMAIN_MAIN);
        tvLogin.setVisibility(View.GONE);

        //tvSignUp.setVisibility(View.GONE);
        //tvForgotPassword.setVisibility(View.GONE);
      //  tvDeRegister.setVisibility(View.GONE);

        if( (MySharedPref.getAppMode(context) == null) ||  (MySharedPref.getAppMode(context).equalsIgnoreCase("null")) || MySharedPref.getAppMode(context).equalsIgnoreCase("A") )
        {
            MySharedPref.saveAppMode(context,"A");
            tvChangeAppMode.setText(context.getString(R.string.switch_to_training_mode));
        }
        else
            tvChangeAppMode.setText(context.getString(R.string.switch_to_actual_mode));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) + ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) + ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) + ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                checkPermission();
            }
        }
        try{

            //if (MySharedPref.getIsBaseDataAcknowledgePending(context))
             //   acknowledgeBaseDataReceived();

            if (!MySharedPref.getIsInstallationMsgSeen(context)) {
                tvInstructions.setText(Html.fromHtml(strInstruction));
                llInstruction.setVisibility(View.VISIBLE);
                llLogin.setVisibility(View.GONE);
                MySharedPref.saveIsInstallationMsgSeen(context, true);
            } else {
                llInstruction.setVisibility(View.GONE);
                llLogin.setVisibility(View.VISIBLE);
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

            Support.setChangeListener(etUserId, tvUserId);
            Support.setChangeListener(etUserPassword, tvUserPassword);

            etUserId.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
            etUserPassword.setFilters(new InputFilter[] { new InputFilter.LengthFilter(15) , InputFilters.passwordFilter});

            RedAsterisk.setAstrikOnEditText(etUserId, context.getString(R.string.email_or_mob));
            RedAsterisk.setAstrikOnEditText(etUserPassword, context.getString(R.string.password));


            if (MySharedPref.getCurrentUser(context) == null)
                etUserId.setText("");
            else {
                try {
                    etUserId.setText(AESHelper.decipher(AESHelper.decipher(Password.sha256(MySharedPref.getDeviceId(context)), MySharedPref.getCurrentUser(context).getApplication_value()), MySharedPref.getCurrentUser(context).getEmail_id()));
                } catch (Exception e) {
                    MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error), e.getMessage(),"003-001");
                }
            }


            if(MySharedPref.getCurrentUser(context) == null)
                tvLoginAnotherUser.setVisibility(View.GONE);
            else
                tvLoginAnotherUser.setVisibility(View.VISIBLE);

            Common.setbIsShowGpDashboard(true);

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        llInstruction.setVisibility(View.GONE);
                        llLogin.setVisibility(View.VISIBLE);
                    }catch(Exception e) {
                        MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error),  e.getMessage(),"003-002");
                    }
                }
            });

            if(IsConnected.isInternet_connected(context, false)) {
                if ((pd == null) || ((pd != null) && (!pd.isShowing())))
                    pd = new ProgressDialog(context);

                getSupportedUrl(pd, new BooleanVariableListener.ChangeListener() {
                    @Override
                    public void onChange() {
                        // Todo
                    }
                });
            }
            gvCall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                       // String strPhone = (String) mPhoneAdapter.getItem(i);
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            Intent callIntent2 = new Intent(Intent.ACTION_CALL);
                            callIntent2.setData(Uri.parse("tel:" + alCall.get(i)));
                            startActivity(callIntent2);
                        } else {
                            checkPermission();
                        }

                    } catch (SecurityException e) {
                        MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error),  e.getMessage(),"003-003");
                    }
                }
            });
            if(Common.getWebUrlValue(context, "contact_email") == null)
                tvEmail.setVisibility(View.GONE);
            else
                tvEmail.setVisibility(View.VISIBLE);

            if(Common.getWebUrlValue(context, "contact_phone") == null)
                gvCall.setVisibility(View.GONE);
            else
            {
                String strPhone = Common.getWebUrlValue(context, "contact_phone");
                alCall = new ArrayList();
                for(int i = 0; i < strPhone.split(",").length; i++)
                {
                    alCall.add(strPhone.split(",")[i]);
                }
                if(alCall.size() < 2)
                    gvCall.setNumColumns(1);

                mPhoneAdapter=new PhoneOptionAdapter(context, alCall);
                gvCall.setAdapter(mPhoneAdapter);
                gvCall.setVisibility(View.VISIBLE);
            }

            cbHide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try{
                        PasswordTransformationMethod transform = (cbHide.isChecked()) ?
                                null : new PasswordTransformationMethod();
                        etUserPassword.setTransformationMethod(transform);
                        etUserPassword.setTransformationMethod(transform);
                        if (cbHide.isChecked())
                            cbHide.setButtonDrawable(R.mipmap.icon_eye_open);
                        else
                            cbHide.setButtonDrawable(R.mipmap.icon_eye_close);


                    }catch(Exception e) {
                        MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error),  e.getMessage(),"003-004");
                    }
                }
            });
        }
        catch(Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error), e.getMessage(),"003-005");
        }


}


    private void findViews() {
        try {
            llInstruction = (LinearLayout) findViewById(R.id.llInstruction);
            llLogin = (LinearLayout) findViewById(R.id.llLogin);
            cbHide = (CheckBox) findViewById(R.id.cbShowLogin);
            cbHide.setButtonDrawable(R.mipmap.icon_eye_close);
            tvEmail = (TextView) findViewById(R.id.tvEmail);
            tvInstructions = (TextView) findViewById(R.id.tvInstructions);
            gvCall = (GridView) findViewById(R.id.gvCall);
            tvLogin = (TextView) findViewById(R.id.tvLogin);
            tvSignUp = (TextView) findViewById(R.id.tvSignUp);
            tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
            tvDeRegister = (TextView) findViewById(R.id.tvDeRegister);
            tvLoginAnotherUser = (TextView) findViewById(R.id.tvLoginAnotherUser);
            tvChangeAppMode = (TextView) findViewById(R.id.tvChangeAppMode);
            tvUserId = (TextView) findViewById(R.id.tvUserId);
            tvUserPassword = (TextView) findViewById(R.id.tvUserPassword);
            etUserId = (EditText) findViewById(R.id.etUserId);
            etUserPassword = (EditText) findViewById(R.id.etUserPassword);
            btnLogin = (Button) findViewById(R.id.btnLogin);
            btnOk = (Button) findViewById(R.id.btnOk);
            tvDeRegister.setPaintFlags(tvDeRegister.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            tvForgotPassword.setPaintFlags(tvForgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            tvLoginAnotherUser.setPaintFlags(tvLoginAnotherUser.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            tvChangeAppMode.setPaintFlags(tvChangeAppMode.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            tvSignUp.setPaintFlags(tvSignUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            tvEmail.setPaintFlags(tvEmail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }catch(Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error) ,  e.getMessage(),"003-006");
        }
    }

    private void setListener() {
        try{
            btnLogin.setOnClickListener(this);
            //btnOk.setOnClickListener(this);
            cbHide.setOnClickListener(this);
            tvSignUp.setOnClickListener(this);
            tvForgotPassword.setOnClickListener(this);
            tvDeRegister.setOnClickListener(this);
            tvEmail.setOnClickListener(this);
            tvLoginAnotherUser.setOnClickListener(this);
            tvChangeAppMode.setOnClickListener(this);

        }catch(Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error),  e.getMessage(),"003-007");
        }
    }

    @Override
    public void onClick(View view) {

        final View myView = view;
        if(MySharedPref.getDeviceId(context)==null)
        {
            deviceRegistration(new BooleanVariableListener.ChangeListener() {
                @Override
                public void onChange() {
                    doOnClick(myView);
                }
            });
        }
        else
        {
            if((MySharedPref.getWebUrl(context) == null) || (MySharedPref.getWebUrl(context).size() <=0) )
            {
                if(IsConnected.isInternet_connected(context, true)) {
                    if ((pd == null) || ((pd != null) && (!pd.isShowing())))
                        pd = new ProgressDialog(context);

                    getSupportedUrl(pd, new BooleanVariableListener.ChangeListener() {
                        @Override
                        public void onChange() {
                            doOnClick(myView);
                        }
                    });
                }
            }
            else
                doOnClick(view);
        }
    }

    @SuppressLint("StringFormatInvalid")
    private void doOnClick(View view)
    {
        final Dialog dialogOtp = new Dialog(context);
        pd = new ProgressDialog(context);
        switch (view.getId())
        {
            case R.id.btnLogin :
                try{
                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                    view.startAnimation(animation);

                    String strUserId = etUserId.getText().toString().trim();
                    String strUserLoginType = "E";
                    if(etUserId.getText().toString().trim().isEmpty())
                    {
                        MyAlert.showAlert(context,R.mipmap.icon_error, context.getString(R.string.login_error), context.getString(R.string.empty_email_mob),"003-008");
                        etUserId.setError(context.getString(R.string.empty_email_mob));
                    }
                    else {
                        if (Support.isValidEmail(strUserId)) {
                            strUserLoginType = "E";
                        } else if (Support.isValidMobileNumber(strUserId)) {
                            strUserLoginType = "M";
                        } else if ((!Support.isValidEmail(strUserId)) && (!Support.isValidMobileNumber(strUserId))) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error), context.getString(R.string.invalid_email_mob),"003-009");
                        }
                    }

                    if( (Support.isValidEmail(strUserId)) || (Support.isValidMobileNumber(strUserId))) {
                        if (etUserPassword.getText().toString().trim().isEmpty())
                        {
                            etUserPassword.setError(context.getString(R.string.enter_user_password));
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error), context.getString(R.string.enter_user_password),"003-010");
                        }
                        else if (etUserPassword.getText().toString().trim().length() < 8)
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error), context.getString(R.string.incorrect_user_id_or_password),"003-011");
                        else {

                            /* check if user already exists on local device*/
                            if (MySharedPref.getCurrentUser(context) != null)
                            {
                                /* If user already exists on local device */
                                try {
                                    String strFinalPwd = Password.sha256(Password.sha256(etUserPassword.getText().toString().trim()));
                                    if (!( (etUserId.getText().toString().trim().equalsIgnoreCase(AESHelper.getDecryptedValue(context,MySharedPref.getCurrentUser(context).getEmail_id())) )
                                            || (etUserId.getText().toString().trim().equalsIgnoreCase(AESHelper.getDecryptedValue(context,MySharedPref.getCurrentUser(context).getMobile()))) ))
                                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error), String.format(getResources().getString(R.string.login_msg_lock),
                                                (AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getEmail_id()) )) ,"003-012");
                                    else {
                                        // If internet found, connect through server
                                        if(IsConnected.isInternet_connected(context, false))
                                            getSalt(pd, etUserId.getText().toString().trim().toLowerCase(), Password.sha256(Password.sha256(etUserPassword.getText().toString().trim())), strUserLoginType);
                                        else
                                        {   // connect locally through device
                                            if (((etUserId.getText().toString().trim().equalsIgnoreCase(AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getEmail_id())))
                                                    || (etUserId.getText().toString().trim().equalsIgnoreCase(AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getMobile()))))
                                                    && strFinalPwd.equalsIgnoreCase(MySharedPref.getCurrentUser(context).getUser_password())) {
                                                Intent intent = new Intent(LoginActivity.this, Home.class);
                                                intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                                                intent.putExtra("user_password", etUserPassword.getText().toString().trim());
                                                startActivity(intent);
                                                finishAffinity();
                                            } else
                                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error),  context.getString(R.string.incorrect_user_id_or_password),"003-013");
                                        }
                                    }

                                } catch (Exception e) {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error), e.getMessage(),"003-014");
                                }
                            }
                            else if(IsConnected.isInternet_connected(context, false))
                                getSalt(pd, etUserId.getText().toString().trim().toLowerCase(), Password.sha256(Password.sha256(etUserPassword.getText().toString().trim())), strUserLoginType);

                            else MyAlert.showAlert(context,R.mipmap.icon_error, context.getString(R.string.login_error), context.getString(R.string.no_internet),"003-015");

                        }
                    }

                }catch(Exception e) {
                    MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error),  e.getMessage(),"003-016");
                }
                break;
            case R.id.tvSignUp :
                try{
                    if (IsConnected.isInternet_connected(context , true)) {
                        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                    }
                }catch(Exception e) {
                    MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error), e.getMessage(),"003-017");
                }
                break;
            case R.id.tvForgotPassword:
                try{
                    if (IsConnected.isInternet_connected(context , true)) {
                        startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
                    }
                }catch(Exception e) {
                    MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error),  e.getMessage(),"003-018");
                }
                break;
            case R.id.tvLoginAnotherUser:
                try{
                    final Dialog dialogAlert = new Dialog(context);
                    MyAlert.dialogForCancelOk
                            (context, R.mipmap.icon_warning, context.getString(R.string.login_warning),
                                    String.format(getResources().getString(R.string.login_another_user_msg_confirmation),
                                            (AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getEmail_id()) )),
                                    dialogAlert,
                                    context.getString(R.string.yes),
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (MySharedPref.getCurrentUser(context) == null) {
                                                dialogAlert.dismiss();
                                                resetAppForOtherUser();
                                                final Dialog dialogAlert = new Dialog(context);
                                                MyAlert.dialogForOk
                                                        (context, R.mipmap.icon_info,  context.getString(R.string.reset_data_info),
                                                                context.getString(R.string.reset_data_msg_success),
                                                                dialogAlert,
                                                                context.getString(R.string.ok),
                                                                new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View view) {
                                                                        dialogAlert.dismiss();
                                                                    }
                                                                },"003-019");
                                            } else {
                                                final Dialog dialogPassword = new Dialog(context);
                                                popupEnterPassword(dialogPassword, new BooleanVariableListener.ChangeListener() {
                                                    @Override
                                                    public void onChange() {
                                                        dialogAlert.dismiss();
                                                        resetAppForOtherUser();
                                                        final Dialog dialogAlert = new Dialog(context);
                                                        MyAlert.dialogForOk
                                                                (context, R.mipmap.icon_info, context.getString(R.string.reset_data_info),
                                                                        context.getString(R.string.reset_data_msg_success),
                                                                        dialogAlert,
                                                                        context.getString(R.string.ok),
                                                                        new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View view) {
                                                                                dialogAlert.dismiss();
                                                                                dialogPassword.dismiss();
                                                                            }
                                                                        },"003-020");
                                                    }

                                                } );

                                                dialogAlert.dismiss();

                                            }
                                        }
                                    },
                                    context.getString(R.string.no),
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialogAlert.dismiss();
                                        }
                                    },
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialogAlert.dismiss();
                                        }
                                    },"003-021");

                }catch(Exception e) {
                    MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error), e.getMessage(),"003-022");
                }
                break;
            case R.id.tvChangeAppMode:
                try{
                    popupSwitchAppMode();

                }catch(Exception e) {
                    MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error),  e.getMessage(),"003-023");
                }
                break;
            case R.id.tvEmail :
                try{
                    String strRecepientEmail = "nictech-ma@gov.in"; // either set to destination email or leave empty
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:" + strRecepientEmail));
                    startActivity(intent);
                }catch(Exception e) {
                    MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error), e.getMessage(),"003-024");
                }
                break;

            case R.id.tvDeRegister:
                try{
                    if (IsConnected.isInternet_connected(context , true)) {

                        DeRegister.dialogForOtpDeRegister
                                (context,
                                        dialogOtp,
                                        context.getString(R.string.de_register),
                                        pd,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject jsResponse) {
                                                try {
                                                    pd.dismiss();
                                                    if (jsResponse.getBoolean("status")) {

                                                        final String strMob = jsResponse.getString("response");

                                                        final Dialog dialog_alert = new Dialog(context);
                                                        MyAlert.dialogForOk
                                                                (context, R.mipmap.icon_info, context.getString(R.string.deregister_info),
                                                                        context.getString(R.string.deregister_msg_success),
                                                                        dialog_alert,
                                                                        context.getString(R.string.ok),
                                                                        new View.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(View view) {
                                                                                dialog_alert.dismiss();
                                                                                dialogOtp.dismiss();
                                                                                User u = MySharedPref.getCurrentUser(context);
                                                                                if (u != null) {
                                                                                    if (AESHelper.getDecryptedValue(context, u.getMobile()).equalsIgnoreCase(strMob)) {
                                                                                        resetAppForOtherUser();
                                                                                    }
                                                                                }
                                                                            }
                                                                        },"003-025");


                                                    } else {
                                                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error),  jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"003-026");
                                                    }

                                                } catch (Exception e) {
                                                    MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error),  e.getMessage(),"003-027");
                                                }
                                            }
                                        }
                                );
                    }
                }catch(Exception e) {
                    MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error),  e.getMessage(),"003-028");
                }
                break;

        }
    }

    /* Call webservice to get salt*/
    private void getSalt(final ProgressDialog pdLogin, final String strUserId, final String strPassword, final String strUserLoginType) {
        try {
            if((pdLogin==null) || ( (pdLogin!=null) && (!pdLogin.isShowing()) ) )
            {
                pdLogin.setMessage(context.getString(R.string.please_wait));
                pdLogin.setCancelable(false);
                pdLogin.setCanceledOnTouchOutside(false);
                pdLogin.show();
            }
            Map<String,String> headers = new HashMap<String, String>();

            JSONObject jsRequest = new JSONObject();
            jsRequest.put("user_id", strUserId);
            jsRequest.put("user_login_type", strUserLoginType);


            MyVolley.callWebServiceUsingVolleyWithNetworkResponse(Request.Method.POST, pdLogin, context,"004", headers, false,
                    true, "common/v1/getSalt", jsRequest, context.getString(R.string.login_error),
                    new Response.Listener<JSONObject>() {
                        @Override
                            public void onResponse(JSONObject jsResponse) {
                            try {
                                if (jsResponse.getBoolean("status")) {
                                    if (jsResponse.getJSONObject("headers").has("salt")) {
                                        strSalt = jsResponse.getJSONObject("headers").getString("salt");
                                    }
                                    if (strSalt != null) {
                                        /* Call webservice to getToken */
                                        Authorization.getToken(pdLogin, strUserId, Password.getFinalPassword(strSalt, strPassword),
                                                context,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String strResponse) {
                                                        try {
                                                            JSONObject jsResponse = new JSONObject(strResponse);
                                                            AuthorizationToken token = new Gson().fromJson(jsResponse.toString(), AuthorizationToken.class);
                                                            MySharedPref.saveAuthorizationToken(context, token);

                                                            /* Call webservice to getUserDetail*/
                                                            getUserDetail(pdLogin, strUserId, token.getAccess_token());
                                                        } catch (Exception e) {
                                                            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error),  e.getMessage(),"003-029");
                                                            pdLogin.dismiss();
                                                        }
                                                    }
                                                });

                                    } else {
                                        pdLogin.dismiss();
                                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error),  context.getString(R.string.incorrect_user_id_or_password),"003-030");
                                    }
                                }
                                else {
                                    pdLogin.dismiss();
                                    MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error),  jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"003-031");
                                }

                            } catch (Exception e) {
                                pdLogin.dismiss();
                                MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error), e.getMessage(),"003-032");
                            }
                        }
                });


        } catch (Exception e) {
            pdLogin.dismiss();
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error),  e.getMessage(),"003-033");
        }
    }


    /* Function to getUserDetails using webservice */
    private void getUserDetail(final ProgressDialog pdLogin, final String strUserId, final String strToken)
    {
        try {
            Map<String,String> headers = new HashMap<String, String>();
            String strFinalToken = MySharedPref.getAuthorizationToken(context).getToken_type() + " " + MySharedPref.getAuthorizationToken(context).getAccess_token();
            headers.put("Authorization", strFinalToken);

            JSONObject jsRequest = new JSONObject();
            jsRequest.put("user_id", strUserId);

            MyVolley.callWebServiceUsingVolleyWithNetworkResponse(Request.Method.POST, pdLogin, context, "005",headers, true,
                    true, "login/v1/getUserDetails", jsRequest, context.getString(R.string.login_error),

                    new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsResponse) {
                        try {
                            al_user = new ArrayList<>();
                            if (jsResponse.getBoolean("status")) {

                                // Get application value
                                if (jsResponse.getJSONObject("headers").has("application_value")) {
                                    strApplicationValue = jsResponse.getJSONObject("headers").getString("application_value");
                                }
                                al_user.add(new Gson().fromJson(jsResponse.getJSONObject("response").toString(), User.class));

                                User u = al_user.get(0);

                                if(u.isIs_mobile_validated()||u.isIs_email_validated()) {
                                    if ((u.getGroup_id().equalsIgnoreCase("adm"))
                                            || (u.getGroup_id().equalsIgnoreCase("nta"))
                                            || (u.getGroup_id().equalsIgnoreCase("sta"))
                                            || (u.getGroup_id().equalsIgnoreCase("dta"))
                                            || (u.getGroup_id().equalsIgnoreCase("dba"))
                                            || (u.getGroup_id().equalsIgnoreCase("gpu"))) {
                                        u.setApplication_value(AESHelper.cipher(Password.sha256(MySharedPref.getDeviceId(context)), strApplicationValue));
                                        u.setUser_id(AESHelper.cipher(strApplicationValue, u.getUser_id()));
                                        u.setEmail_id(AESHelper.cipher(strApplicationValue, u.getEmail_id()));
                                        u.setMobile(AESHelper.cipher(strApplicationValue, u.getMobile()));
                                        u.setDob(AESHelper.cipher(strApplicationValue, u.getDob()));
                                        u.setIdentity_id(AESHelper.cipher(strApplicationValue, u.getIdentity_id()));
                                        u.setIdentity_number(AESHelper.cipher(strApplicationValue, u.getIdentity_number()));


                                        if(MySharedPref.getCurrentUser(context)!=null && MySharedPref.getCurrentUser(context).getUser_id().equalsIgnoreCase(u.getUser_id()))
                                        {
                                            u.setSub_district_code(MySharedPref.getCurrentUser(context).getSub_district_code());
                                            u.setVillage_code(MySharedPref.getCurrentUser(context).getVillage_code());
                                            u.setEnum_block_code(MySharedPref.getCurrentUser(context).getEnum_block_code());
                                            u.setB_file_name(MySharedPref.getCurrentUser(context).getB_file_name());
                                        }
                                        MySharedPref.saveCurrentUser(context, u);
                                        Intent intent = new Intent(LoginActivity.this, Home.class);
                                        intent.putExtra("user_id", AESHelper.getDecryptedValue(context,MySharedPref.getCurrentUser(context).getUser_id()));
                                        intent.putExtra("user_password", etUserPassword.getText().toString().trim());
                                        startActivity(intent);
                                        pdLogin.dismiss();
                                        finishAffinity();
                                        getSupportedUrl(pdLogin, new BooleanVariableListener.ChangeListener() {
                                            @Override
                                            public void onChange() {
                                                // Todo
                                            }
                                        });
                                    }
                                    else {
                                        pdLogin.dismiss();
                                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.ma_error),  context.getString(R.string.not_authorized),"003-034");
                                    }
                                }
                                else
                                {
                                    pdLogin.dismiss();
                                    verificationPopupLogin(u);
                                }
                            } else {
                                pdLogin.dismiss();
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"003-035");
                            }
                        } catch (Exception e) {
                            pdLogin.dismiss();
                            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error),  e.getMessage(),"003-036");
                        }
                    }
                });

        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error), e.getMessage(),"003-037");
        }

    }

    /* Get Supported url*/
    private void getSupportedUrl(final ProgressDialog pd, final BooleanVariableListener.ChangeListener booleanVariableChangeListener)
    {
        try{
            //Map<String,String> headers = new HashMap<String, String>();
            //String strFinalToken = MySharedPref.getAuthorizationToken(context).getToken_type() + " " + MySharedPref.getAuthorizationToken(context).getAccess_token();
            //headers.put("Authorization", strFinalToken);

            JSONObject jsRequest = new JSONObject();

            MyVolley.callWebServiceUsingVolley(Request.Method.GET, pd, context,"027", false,
                    false, "misc/v1/getConfigurationM", jsRequest, context.getString(R.string.login_error),

                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                ArrayList<Weburl> alWebUrl = new ArrayList<>();
                                if (jsResponse.getBoolean("status")) {
                                    alWebUrl = new Gson().fromJson(jsResponse.getJSONArray("response").toString(), new TypeToken<List<Weburl>>() {
                                    }.getType());

                                    if(alWebUrl.size() > 0)
                                    {
                                        MySharedPref.saveWebUrl(context,alWebUrl);
                                    }

                                    String strEmail = Common.getWebUrlValue(context, "contact_email");
                                    String strPhone = Common.getWebUrlValue(context, "contact_phone");

                                    if( (strEmail == null) || (strEmail.length() <= 4) )
                                    {
                                        tvEmail.setVisibility(View.GONE);
                                    }
                                    else
                                    {
                                        tvEmail.setVisibility(View.VISIBLE);
                                        tvEmail.setText(strEmail);
                                    }

                                    if((strPhone == null) || (strPhone.length() <= 2) )
                                    {
                                        gvCall.setVisibility(View.GONE);
                                    }
                                    else
                                    {
                                        gvCall.setVisibility(View.VISIBLE);
                                        alCall = new ArrayList();
                                        for(int i = 0; i < strPhone.split(",").length; i++)
                                        {
                                            alCall.add(strPhone.split(",")[i]);
                                        }
                                        if(alCall.size() < 2)
                                            gvCall.setNumColumns(1);

                                        mPhoneAdapter=new PhoneOptionAdapter(context, alCall);
                                        gvCall.setAdapter(mPhoneAdapter);

                                    }



                                    pd.dismiss();

                                    BooleanVariableListener mBooleanVariableListener = new BooleanVariableListener();
                                    mBooleanVariableListener.setListener(booleanVariableChangeListener);
                                    mBooleanVariableListener.setBoo(true);

                                } else {
                                    pd.dismiss();
                                }
                            } catch (Exception e) {
                                pd.dismiss();
                            }
                        }
                    });
        }catch(Exception e) {
            pd.dismiss();
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error), e.getMessage(),"003-038");
        }
    }

    /* Opens OTP dialog*/
    private void verificationPopupLogin(final User u) {

        try {
            if((pd!=null) && (pd.isShowing()) )
                pd.dismiss();

            final Dialog dialogOtp = new Dialog(context);
            dialogOtp.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogOtp.setContentView(R.layout.layout_verify_otp_mob);
            dialogOtp.setCancelable(false);
            Window window = dialogOtp.getWindow();
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(R.color.transparent);
            TextView tvHeader = (TextView) dialogOtp.findViewById(R.id.tvHeader);
            tvMessage = (TextView) dialogOtp.findViewById(R.id.tvMessage);
            TextView tvMobileOtp = (TextView) dialogOtp.findViewById(R.id.tvMobileOtp);
            TextView tvMobile = (TextView) dialogOtp.findViewById(R.id.tvMobile);
            etMobile = (EditText) dialogOtp.findViewById(R.id.etMobile);
            tvVerifyOtpApproved = (TextView) dialogOtp.findViewById(R.id.tvVerifyOtpApproved);
            etMobileOtp = (EditText) dialogOtp.findViewById(R.id.etMobileOtp);
            llOtp = (LinearLayout) dialogOtp.findViewById(R.id.llOtp);
            Button btnResendOtp = (Button) dialogOtp.findViewById(R.id.btnResendOtp);
            Button btnSubmitOtp = (Button) dialogOtp.findViewById(R.id.btnSubmitOtp);
            btnSubmit = (Button) dialogOtp.findViewById(R.id.btnSubmit);
            ImageView ivClose = (ImageView) dialogOtp.findViewById(R.id.ivClose);

            Support.setChangeListener(etMobileOtp, tvMobileOtp);
            Support.setChangeListener(etMobile, tvMobile);

            tvHeader.setText(getString(R.string.otp_info));
            tvMessage.setText(Html.fromHtml("Mobile No. : <font size='2' color='#17A589'>" + u.getMobile() + "</font> is not verified.\n" +
                    "Please verify your contact details to complete verification process. if this number is not correct then Modify your no.\n"));
            etMobile.setText(u.getMobile());
            llOtp.setVisibility(View.GONE);
            tvMobileOtp.setVisibility(View.GONE);
            etMobileOtp.setVisibility(View.GONE);

            etMobile.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
            btnSubmit.setText("Continue");
            btnSubmit.setVisibility(View.VISIBLE);
            etMobile.setVisibility(View.VISIBLE);
            tvMobile.setVisibility(View.VISIBLE);

            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogOtp.dismiss();
                }
            });

            btnResendOtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doGetOTPLogin(dialogOtp, u);
                }
            });

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //doValidateOtp(dialog, strUserId, strPassword, etMobileOtp.getText().toString().trim(), strUserLoginType);
                    if (etMobile.getText().toString().equalsIgnoreCase(u.getMobile())) {
                        //dialog.dismiss();
                        doGetOTPLogin(dialogOtp, u);
                    } else {
                        //dialog.dismiss();
                        updateMobile(dialogOtp, u, etMobile.getText().toString());
                    }
                }
            });

            btnSubmitOtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (etMobileOtp.getText().toString().trim().isEmpty())
                        etMobileOtp.setError(context.getString(R.string.enter_otp));
                    else if (etMobileOtp.getText().toString().trim().length() != 6)
                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error) , context.getString(R.string.invalid_otp),"003-039");
                    else
                        doValidateOtpLogin(dialogOtp, u);
                }
            });

            dialogOtp.show();
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error),  e.getMessage(),"003-040");
        }
    }

    /* Function to check if user id (email or mob) exists and send otp to send otp to mobile */
    private void doGetOTPLogin(final Dialog dialogOtp, final User u)
    {
        if((pd==null) || ( (pd!=null) && (!pd.isShowing()) ) )
            pd = new ProgressDialog(context);
        try {
            OtpHandler.doGetOTP(context, context.getString(R.string.login_error), pd,
                    u.getMobile(),"M",
                    new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(final JSONObject jsResponse) {
                            try {
                                pd.dismiss();
                                if (jsResponse.getBoolean("status")) {
                                    tvMessage.setVisibility(View.GONE);
                                    llOtp.setVisibility(View.VISIBLE);
                                    etMobileOtp.setVisibility(View.VISIBLE);
                                    etMobile.setEnabled(false);
                                    btnSubmit.setVisibility(View.GONE);
                                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.login_info) , context.getString(R.string.otp_sent_to_mobile),"003-041");

                                } else {
                                    pd.dismiss();
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error) ,  jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"003-042");
                                }
                            } catch (Exception e) {
                                pd.dismiss();
                                MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error), e.getMessage(),"003-043");
                            }
                        }
                    });

        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error), e.getMessage(),"003-044");
        }
    }

    /* Function to check if user id (email or mob) exists and send otp to send otp to mobile */
        private void doValidateOtpLogin(final Dialog dialogOtp, final User u)
    {
        if((pd==null) || ( (pd!=null) && (!pd.isShowing()) ) )
            pd = new ProgressDialog(context);
        try {
            OtpHandler.doValidateOTP(context, context.getString(R.string.login_error), pd,
                    etMobileOtp.getText().toString().trim(),u.getMobile(),"M",

                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                if (jsResponse.getBoolean("status")) {
                                    final Dialog dialog_alert = new Dialog(context);
                                    MyAlert.dialogForOk
                                            (context, R.mipmap.icon_info, context.getString(R.string.login_info),
                                                    context.getString(R.string.mobile_verification_success_wait_approval),
                                                    dialog_alert,
                                                    context.getString(R.string.ok),
                                                    new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {

                                                            try {
                                                                dialog_alert.dismiss();
                                                                dialogOtp.dismiss();
                                                                if (!(u.getIsActive() == null)) {
                                                                    if (u.getIsActive().equals("true")) {
                                                                        if ((u.getGroup_id().equalsIgnoreCase("adm"))
                                                                                || (u.getGroup_id().equalsIgnoreCase("nta"))
                                                                                || (u.getGroup_id().equalsIgnoreCase("sta"))
                                                                                || (u.getGroup_id().equalsIgnoreCase("dta"))
                                                                                || (u.getGroup_id().equalsIgnoreCase("dba"))
                                                                                || (u.getGroup_id().equalsIgnoreCase("gpu"))) {
                                                                            u.setApplication_value(AESHelper.cipher(Password.sha256(MySharedPref.getDeviceId(context)), strApplicationValue));
                                                                            u.setUser_id(AESHelper.cipher(strApplicationValue, u.getUser_id()));
                                                                            u.setEmail_id(AESHelper.cipher(strApplicationValue, u.getEmail_id()));
                                                                            u.setMobile(AESHelper.cipher(strApplicationValue, u.getMobile()));
                                                                            u.setDob(AESHelper.cipher(strApplicationValue, u.getDob()));
                                                                            u.setIdentity_id(AESHelper.cipher(strApplicationValue, u.getIdentity_id()));
                                                                            u.setIdentity_number(AESHelper.cipher(strApplicationValue, u.getIdentity_number()));
                                                                            if(MySharedPref.getCurrentUser(context)!=null && MySharedPref.getCurrentUser(context).getUser_id().equalsIgnoreCase(u.getUser_id()))
                                                                            {
                                                                                u.setSub_district_code(MySharedPref.getCurrentUser(context).getSub_district_code());
                                                                                u.setVillage_code(MySharedPref.getCurrentUser(context).getVillage_code());
                                                                                u.setEnum_block_code(MySharedPref.getCurrentUser(context).getEnum_block_code());
                                                                                u.setB_file_name(MySharedPref.getCurrentUser(context).getB_file_name());
                                                                            }

                                                                            MySharedPref.saveCurrentUser(context, u);
                                                                            Intent intent = new Intent(LoginActivity.this, Home.class);
                                                                            intent.putExtra("user_id", AESHelper.getDecryptedValue(context,MySharedPref.getCurrentUser(context).getUser_id()));
                                                                            intent.putExtra("user_password", etUserPassword.getText().toString().trim());
                                                                            startActivity(intent);
                                                                            finishAffinity();
                                                                            //getSupportedUrl(pd);
                                                                        } else {
                                                                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error), context.getString(R.string.not_authorized),"003-045");
                                                                            pd.dismiss();
                                                                        }
                                                                    }
                                                                } else {
                                                                    pd.dismiss();
                                                                    startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                                                                    finishAffinity();
                                                                }
                                                            }catch(Exception e)
                                                            {
                                                                pd.dismiss();
                                                                MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error), e.getMessage(),"003-046");
                                                            }
                                                        }
                                                    },"003-047");
                                    //MyAlert.showAlert(context, context.getString(R.string.login_info), context.getString(R.string.otp_validate_success));


                                } else {
                                    pd.dismiss();
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"003-048");
                                }

                            } catch (Exception e) {
                                pd.dismiss();
                                MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error), e.getMessage(),"003-049");
                            }
                        }
                    });
        }catch (Exception e) {
            pd.dismiss();
            MyAlert.showAlert(context,R.mipmap.icon_error,  getString(R.string.login_error),  e.getMessage(),"003-050");
        }
    }

    private void checkPermission() {
        try{
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) + ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)+ ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)+ ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)|| ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)|| ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {

                    Snackbar.make(this.findViewById(android.R.id.content),
                            "Please Grant Permissions to read phone status.",
                            Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(
                                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_MULTIPLE_REQUEST);
                                    }
                                }
                            }).show();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_MULTIPLE_REQUEST);
                    }
                }
            } else {
                // write your logic code if permission already granted

            }
        }catch(Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error), e.getMessage(),"003-044");
        }
    }

    /* Reset data*/
    private void resetAppForOtherUser()
    {
        try{
            MySharedPref.clearSharedPref(context);
            MySharedPref.saveIsSharedPrefCleared(context,true);
            new SQLiteHelper(context).deleteAll(context, DBHelper.getInstance(context, true));
           // new SQLiteReports(context).deleteAll(context, DBReport.getInstance(context, true));
            new SQLiteHelper(context).deleteDb();
            new SQLiteMaster(context).deleteDb();
            //new SQLiteReports(context).deleteDb();
            etUserId.setText("");
            etUserPassword.setText("");
            tvLoginAnotherUser.setVisibility(View.GONE);
        }catch(Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error),  e.getMessage(),"003-051");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_internet_connectivity, menu);
        menuItem = menu.findItem(R.id.action_internet_status);
        if(!IsConnected.isInternet_connected(context, false))
            menuItem.setIcon(R.mipmap.icon_offline_status);
        else
            menuItem.setIcon(R.mipmap.icon_online_status);
        return super.onCreateOptionsMenu(menu);
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
           // MyAlert.showAlert(context, getString(R.string.login_error), "MA-003-015");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Constants.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Constants.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        try{
            switch (requestCode) {
                case PERMISSIONS_MULTIPLE_REQUEST:
                    if (grantResults.length > 0) {
                        boolean callPhone = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                        boolean writeExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                        boolean readPhoneStatus = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                        if(!callPhone && !writeExternalFile && !readPhoneStatus)
                        {
                            Snackbar.make(this.findViewById(android.R.id.content),
                                    "Please Grant Permissions to read phone status",
                                    Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(
                                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_MULTIPLE_REQUEST);
                                            }
                                        }
                                    }).show();
                        }
                        else{
                            if (MySharedPref.getDeviceId(context) == null)
                                deviceRegistration(new BooleanVariableListener.ChangeListener() {
                                    @Override
                                    public void onChange() {
                                        // ToDo
                                    }
                                });
                        }

                    }
                    break;
            }
        }catch(Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error), e.getMessage(),"003-052");
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

    Pattern p = Pattern.compile("(|^)\\d{6}");

    private void deviceRegistration(final BooleanVariableListener.ChangeListener booleanVariableChangeListener) {
        if((pd==null) || ( (pd!=null) && (!pd.isShowing()) ) )
            pd = new ProgressDialog(context);
        try {
            JSONObject jsRequest = new JSONObject();
            strImei = GetAddress.getIMEI(context);
            strMac = GetAddress.getWifiMacAddress();
            if((MySharedPref.getImei(context) != null) && !MySharedPref.getImei(context).trim().equals("") && !MySharedPref.getImei(context).trim().equals("null"))
            {
                strImei = MySharedPref.getImei(context);
            }
            else if ((strImei != null) && !strImei.trim().equals("") && !strImei.trim().equals("null"))
            {
                strImei =  GetAddress.getIMEI(context);
                MySharedPref.saveImei(context, strImei);
            }

            if((MySharedPref.getMacAddress(context) != null )&& !(MySharedPref.getMacAddress(context).trim().equals("")) && !MySharedPref.getMacAddress(context).trim().equals("null"))
            {
                strMac = MySharedPref.getMacAddress(context);
            }


            else if (strMac != null && !(strMac.trim().equals("")) && !strMac.trim().equals("null"))
            {
                strMac =GetAddress.getWifiMacAddress();
                MySharedPref.saveMacAddress(context, strMac);
            }

            jsRequest.put("device_imei_no", strImei);
            jsRequest.put("app_id", Installation.id(context));
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            strAppVersion = packageInfo.versionName;
            jsRequest.put("app_version", strAppVersion);
            jsRequest.put("device_andriod_version", Build.VERSION.SDK_INT);
            jsRequest.put("device_model_no", android.os.Build.MODEL);
            //jsRequest.put("firebase_token", Installation.id(context));
            jsRequest.put("firebase_token_id", strImei);

            if(strMac == null)
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error),  context.getString(R.string.allow_wifi_imei),"003-053");
            else {
                jsRequest.put("device_mac_address", strMac);


                MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context, "002", false, false,
                        "common/v1/getDeviceApplicationDetails/", jsRequest, context.getString(R.string.login_error),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                Log.d(TAG, "onResponse: "+jsResponse.toString());
                                if (jsResponse.getBoolean("status")) {
                                    MySharedPref.saveDevice_ID(context, jsResponse.getJSONObject("response").getString("device_id"));
                                    /*BooleanVariableListener mBooleanVariableListener = new BooleanVariableListener();
                                    mBooleanVariableListener.setListener(booleanVariableChangeListener);
                                    mBooleanVariableListener.setBoo(true);
                                    */
                                    getSupportedUrl(pd, booleanVariableChangeListener);

                                } else {
                                    pd.dismiss();
                                    /*MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error),  context.getString(R.string.error_no_server_available) +
                                            "\napp_id : " + Installation.id(context) +
                                            "\napp_version : " + strAppVersion +
                                            "\nimei_no : " + strImei +
                                            "\nmac_address : " + strMac, "003-054");*/
                                    showDeviceRegError(Installation.id(context), strAppVersion, strImei, strMac,MySharedPref.getFirebaseTokenId(context), false, null);

                                }
                            } catch (Exception e) {
                                pd.dismiss();
                                MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error),  e.toString(),"003-055");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pd.dismiss();
                            /*MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error),  context.getString(R.string.error_no_server_available) +
                                    "\napp_id : " + Installation.id(context) +
                                    "\napp_version : " + strAppVersion +
                                    "\nimei_no : " + strImei +
                                    "\nmac_address : " + strMac,"003-056");*/
                            showDeviceRegError(Installation.id(context), strAppVersion, strImei, strMac,MySharedPref.getFirebaseTokenId(context), true, error);


                        }
                    }
                );
            }
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error),  e.getMessage(),"003-057");
        }
    }

    private void showDeviceRegError(String strApp_id, String strAppVersion, String strImei, String strMac, String strFirebaseToken, Boolean bServerError, VolleyError error)
    {

        if((strMac == null) ||(strMac.length()<=0))
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error),  context.getString(R.string.error_no_server_available) +
                    context.getString(R.string.mac_not_available),"003-054");
        else if((strImei == null) || (strImei.length()<=0))
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error),  context.getString(R.string.error_no_server_available) +
                    context.getString(R.string.imei_not_available),"003-056");
        else if((strApp_id == null) || (strApp_id.length()<=0))
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error),  context.getString(R.string.error_no_server_available) +
                    context.getString(R.string.app_id_not_available),"003-071");
        else if((strAppVersion == null) || (strAppVersion.length()<=0))
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error),  context.getString(R.string.error_no_server_available) +
                    context.getString(R.string.app_version_not_available),"003-072");
        else if((strFirebaseToken == null) || (strFirebaseToken.length()<=0))
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error),  context.getString(R.string.error_no_server_available) +
                    context.getString(R.string.token_not_available),"003-077");
        else if(error == null)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error),  context.getString(R.string.error_no_server_available) +
                    context.getString(R.string.reopen_app),"003-078");
        }
        else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error), context.getString(R.string.error_no_connection),"003-73");
        } else if (error instanceof AuthFailureError) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error),context.getString(R.string.error_server_default),"003-074");
        } else if (error instanceof ServerError) {
            MyVolley.showVolley_error(context,  context.getString(R.string.login_error), error, "002");
        } else if (error instanceof NetworkError) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error),context.getString(R.string.error_server_default),"003-075");
        } else if (error instanceof ParseError) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error),context.getString(R.string.error_server_default),"003-076");
        } else if (error.toString().contains("NullPointerException")) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error),context.getString(R.string.error_null_pointer),"003-077");
        }

    }

    private void updateMobile(final Dialog dialogOtp,final User u, final String strMobile) {

        try {
            JSONObject jsRequest = new JSONObject();
            jsRequest.put("mobile", strMobile);
            jsRequest.put("user_id", u.getUser_id());
            jsRequest.put("user_login_type", "U");

            MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context,"028", true, true, "login/v1/updateUserMobile/", jsRequest, context.getString(R.string.login_error),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                pd.dismiss();
                                if (jsResponse.getBoolean("status")) {
                                    final Dialog dialog_alert = new Dialog(context);
                                    MyAlert.dialogForOk
                                            (context, R.mipmap.icon_info, context.getString(R.string.login_info),
                                                    context.getString(R.string.user_mobile_updated),
                                                    dialog_alert,
                                                    context.getString(R.string.ok),
                                                    new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            dialog_alert.dismiss();
                                                            u.setMobile(strMobile);
                                                            doGetOTPLogin(dialogOtp,u);
                                                        }
                                                    },"003-058");

                                } else {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error),  jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"003-059");
                                }
                            } catch (Exception e) {
                                pd.dismiss();
                                MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error),  e.getMessage(),"003-060");
                            }
                        }
                    });
        }
        catch (Exception e) {
            MyAlert.showAlert(context,R.mipmap.icon_error,  getString(R.string.login_error),  e.getMessage(),"003-061");
        }
    }


    /* Function to open pop up to switch app mode */
    private void popupSwitchAppMode() {
        try {
            if((pd!=null) && (pd.isShowing()) )
                pd.dismiss();
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_select_app_mode);
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(R.color.transparent);

            final RadioButton rbTraining = (RadioButton) dialog.findViewById(R.id.rbTraining);
            final RadioButton rbActual = (RadioButton) dialog.findViewById(R.id.rbActual);


            Button btnContinue = (Button) dialog.findViewById(R.id.btnContinue);
            ImageView ivClose = (ImageView) dialog.findViewById(R.id.ivClose);

            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            // if button is clicked, close the custom dialog
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if( ( ( (MySharedPref.getAppMode(context) == null) ||
                            (MySharedPref.getAppMode(context).equalsIgnoreCase("null"))
                            || MySharedPref.getAppMode(context).equalsIgnoreCase("A") )
                            && (rbTraining.isChecked()) )
                        || ( (MySharedPref.getAppMode(context).equalsIgnoreCase("T") )
                            && (rbActual.isChecked()) ) )
                    {
                        final Dialog dialogAlert = new Dialog(context);
                        String strMsg;
                        if(rbTraining.isChecked())
                            strMsg = getString(R.string.change_mode_training_msg);
                        else
                            strMsg = getString(R.string.change_mode_actual_msg);
                        MyAlert.dialogForCancelOk
                                (context, R.mipmap.icon_warning, context.getString(R.string.app_warning),
                                        strMsg,
                                        dialogAlert,
                                        context.getString(R.string.yes),
                                        new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if(MySharedPref.getCurrentUser(context) == null) {
                                                    dialogAlert.dismiss();
                                                    resetAppForOtherUser();
                                                    if (rbTraining.isChecked())
                                                        MySharedPref.saveAppMode(context, "T");
                                                    else
                                                        MySharedPref.saveAppMode(context, "A");

                                                    startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                                                    finishAffinity();
                                                }
                                                else {
                                                    final Dialog dialogPassword = new Dialog(context);
                                                    popupEnterPassword(dialogPassword, new BooleanVariableListener.ChangeListener() {
                                                        @Override
                                                        public void onChange() {
                                                            dialogAlert.dismiss();
                                                            dialogPassword.dismiss();
                                                            resetAppForOtherUser();
                                                            if (rbTraining.isChecked())
                                                                MySharedPref.saveAppMode(context, "T");
                                                            else
                                                                MySharedPref.saveAppMode(context, "A");

                                                            startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                                                            finishAffinity();
                                                        }
                                                    });
                                                    dialogAlert.dismiss();
                                                }
                                            }
                                        },
                                        context.getString(R.string.cancel),
                                        new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialogAlert.dismiss();
                                            }
                                        },
                                        new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialogAlert.dismiss();
                                            }
                                        },"003-064");
                    }

                    dialog.dismiss();


                }
            });

            rbTraining.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            rbActual.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            dialog.show();
        }catch( Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.login_error), e.getMessage(),"003-065");
        }
    }

    private void popupEnterPassword(final Dialog dialogPassword, final BooleanVariableListener.ChangeListener booleanVariableChangeListener) {
        try {
            if((pd!=null) && (pd.isShowing()) )
                pd.dismiss();
            dialogPassword.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogPassword.setContentView(R.layout.layout_enter_password);
            Window window = dialogPassword.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(R.color.transparent);

            final EditText etPwd = (EditText) dialogPassword.findViewById(R.id.etPwd);
            final CheckBox cbShowPwd = (CheckBox) dialogPassword.findViewById(R.id.cbShowPwd);
            Button btnContinue = (Button) dialogPassword.findViewById(R.id.btnContinue);
            ImageView ivClose = (ImageView) dialogPassword.findViewById(R.id.ivClose);
            cbShowPwd.setButtonDrawable(R.mipmap.icon_eye_close);

            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogPassword.dismiss();
                }
            });
            // if button is clicked, close the custom dialogPassword
            cbShowPwd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        PasswordTransformationMethod transform = (cbShowPwd.isChecked()) ?
                                null : new PasswordTransformationMethod();
                        etPwd.setTransformationMethod(transform);
                        if (cbShowPwd.isChecked())
                            cbShowPwd.setButtonDrawable(R.mipmap.icon_eye_open);
                        else
                            cbShowPwd.setButtonDrawable(R.mipmap.icon_eye_close);

                    }catch(Exception e) {
                        MyAlert.showAlert(context,R.mipmap.icon_error,  getString(R.string.login_error),  e.getMessage(),"003-066");
                    }
                }
            });


            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (etPwd.getText().toString().trim().isEmpty())
                            etPwd.setError(context.getString(R.string.enter_user_password));
                        else if (etPwd.getText().toString().trim().length() < 8)
                            MyAlert.showAlert(context,R.mipmap.icon_error,  context.getString(R.string.login_error),  context.getString(R.string.incorrect_password),"003-067");
                        else {

                        /* If user already exists on local device */
                            String strFinalPwd = Password.sha256(Password.sha256(etPwd.getText().toString().trim()));
                            if (!strFinalPwd.equalsIgnoreCase(MySharedPref.getCurrentUser(context).getUser_password())) {
                                MyAlert.showAlert(context,R.mipmap.icon_error,  getString(R.string.login_error), context.getString(R.string.incorrect_password),"003-068");
                            }
                            else
                            {
                                BooleanVariableListener mBooleanVariableListener = new BooleanVariableListener();
                                mBooleanVariableListener.setListener(booleanVariableChangeListener);
                                mBooleanVariableListener.setBoo(true);
                            }
                        }
                    } catch (Exception e) {
                        MyAlert.showAlert(context,R.mipmap.icon_error,  getString(R.string.login_error),  e.getMessage(),"003-069");
                    }
                }
            });

            dialogPassword.show();
        }catch( Exception e)
        {
            MyAlert.showAlert(context,R.mipmap.icon_error,  getString(R.string.login_error),  e.getMessage(),"003-070");
        }
    }


}
