package in.nic.ease_of_living.gp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import org.json.JSONObject;

import in.nic.ease_of_living.broadcast.NetworkChangeReceiver;
import in.nic.ease_of_living.interfaces.Communicator;
import in.nic.ease_of_living.models.User;
import in.nic.ease_of_living.supports.IsConnected;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MySharedPref;
import in.nic.ease_of_living.supports.MyVolley;
import in.nic.ease_of_living.supports.Password;
import in.nic.ease_of_living.utils.AESHelper;
import in.nic.ease_of_living.utils.Common;
import in.nic.ease_of_living.utils.InputFilters;
import in.nic.ease_of_living.utils.NetworkRegistered;
import in.nic.ease_of_living.utils.OtpHandler;
import in.nic.ease_of_living.utils.Support;

/**
 * Created by Neha Jain on 6/28/2017.
 */
/*010-025*/
public class ForgotPassword extends AppCompatActivity implements View.OnClickListener, Communicator{


    private Context context;
    private TextView tvUserIdEmailOrMob, tvMobOtp, tvNewPwd, tvConfirmPwd, tvPasswordHint;;
    private EditText etUserIdEmailOrMob, etMobileOtp, etNewPwd, etConfirmPwd;
    private Button btnSubmitEmailOrMob, btnResendOtp, btnSubmitOtp, btnSubmitForgotPassword;
    private CheckBox cbShowNewPwd, cbShowConfirmPwd;
    private LinearLayout llOtp, llNewPwd, llConfirmPwd, llpasswordHint;
    private ProgressDialog pd;
    private String strNewPasswordFinal;
    private MenuItem menuItem;
    private NetworkChangeReceiver mNetworkReceiver;
    private String strUserLoginType = "E", strUserId = "";
private  String TAG = "ForgotPassword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_forgot_password);
            context = this;
            Common.setAppHeader(context);
            //getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getString(R.string.app_name) + "</font>"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mNetworkReceiver = new NetworkChangeReceiver();
            NetworkRegistered.registerNetworkBroadcastForNougat(context, mNetworkReceiver);
            findViews();
            setListener();
            Support.setChangeListener(etUserIdEmailOrMob, tvUserIdEmailOrMob);
            Support.setChangeListener(etMobileOtp, tvMobOtp);
            Support.setChangeListener(etNewPwd, llpasswordHint);
            Support.setChangeListener(etConfirmPwd, tvConfirmPwd);

            etNewPwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15), InputFilters.passwordFilter});
            etConfirmPwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15), InputFilters.passwordFilter});
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.forgot_password_error),  e.getMessage(),"010-001");
        }
    }

    private void findViews() {
        try {
            tvUserIdEmailOrMob = (TextView) findViewById(R.id.tvUserIdEmailOrMob);
            tvMobOtp = (TextView) findViewById(R.id.tvMobOtp);
            tvNewPwd = (TextView) findViewById(R.id.tvNewPwd);
            tvConfirmPwd = (TextView) findViewById(R.id.tvConfirmPwd);
            tvPasswordHint = (TextView) findViewById(R.id.tvPasswordHint);

            etUserIdEmailOrMob = (EditText) findViewById(R.id.etUserIdEmailOrMob);
            etMobileOtp = (EditText) findViewById(R.id.etMobileOtp);
            etNewPwd = (EditText) findViewById(R.id.etNewPwd);
            etConfirmPwd = (EditText) findViewById(R.id.etConfirmPwd);

            btnSubmitEmailOrMob = (Button) findViewById(R.id.btnSubmitEmailOrMob);
            btnResendOtp = (Button) findViewById(R.id.btnResendOtp);
            btnSubmitOtp = (Button) findViewById(R.id.btnSubmitOtp);
            btnSubmitForgotPassword = (Button) findViewById(R.id.btnSubmitForgotPassword);

            cbShowNewPwd = (CheckBox) findViewById(R.id.cbShowNewPwd);
            cbShowNewPwd.setButtonDrawable(R.mipmap.icon_eye_close);

            cbShowConfirmPwd = (CheckBox) findViewById(R.id.cbShowConfirmPwd);
            cbShowConfirmPwd.setButtonDrawable(R.mipmap.icon_eye_close);

            llOtp = (LinearLayout) findViewById(R.id.llOtp);
            llNewPwd = (LinearLayout) findViewById(R.id.llNewPwd);
            llConfirmPwd = (LinearLayout) findViewById(R.id.llConfirmPwd);
            llpasswordHint = (LinearLayout) findViewById(R.id.llpasswordHint);
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.forgot_password_error),  e.getMessage(),"010-002");
        }
    }

    private void setListener() {
        try {
            btnSubmitEmailOrMob.setOnClickListener(this);
            btnResendOtp.setOnClickListener(this);
            btnSubmitOtp.setOnClickListener(this);
            btnSubmitForgotPassword.setOnClickListener(this);
            cbShowNewPwd.setOnClickListener(this);
            cbShowConfirmPwd.setOnClickListener(this);
            tvPasswordHint.setOnClickListener(this);
        }catch(Exception e)
        {
            MyAlert.showAlert(context,R.mipmap.icon_error,  context.getString(R.string.forgot_password_error),  e.getMessage(),"010-003");
        }
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnSubmitEmailOrMob:
                    strUserId = etUserIdEmailOrMob.getText().toString().trim();

                    if (strUserId.isEmpty()) {
                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.forgot_password_error), context.getString(R.string.empty_email_mob),"010-008");
                    } else if (Support.isValidEmail(strUserId)) {
                        strUserLoginType = "E";
                        doGetOTPForgotPassword();
                    } else if (Support.isValidMobileNumber(strUserId)) {
                        strUserLoginType = "M";
                        doGetOTPForgotPassword();
                    } else
                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.forgot_password_error),  context.getString(R.string.invalid_email_mob),"010-009");
                    break;

                case R.id.btnSubmitOtp:
                    if (etMobileOtp.getText().toString().trim().isEmpty())
                        etMobileOtp.setError(context.getString(R.string.enter_otp));
                    else if (etMobileOtp.getText().toString().trim().length() != 6)
                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.forgot_password_error),  context.getString(R.string.invalid_otp),"010-010");
                    else
                        doValidateOtpForgotPassword();
                    break;

                case R.id.btnResendOtp:
                    strUserId = etUserIdEmailOrMob.getText().toString().trim();

                    if (Support.isValidEmail(strUserId)) {
                        strUserLoginType = "E";
                        doGetOTPForgotPassword();
                    } else if (Support.isValidMobileNumber(strUserId)) {
                        strUserLoginType = "M";
                        doGetOTPForgotPassword();
                    } else
                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.forgot_password_error), context.getString(R.string.invalid_email_mob),"010-011");
                    break;


                case R.id.btnSubmitForgotPassword:
                    try {
                        // Verify mobile Otp
                        String strFinalPasswordNew = Password.sha256(Password.sha256(etNewPwd.getText().toString().trim()));

                        if (etNewPwd.getText().toString().trim().isEmpty())
                            etNewPwd.setError(context.getString(R.string.enter_new_user_password));
                        else if (etConfirmPwd.getText().toString().trim().isEmpty())
                            etConfirmPwd.setError(context.getString(R.string.enter_confirm_user_password));
                        else if (!Support.isValidPassword(etNewPwd.getText().toString().trim())) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.forgot_password_error), context.getString(R.string.password_msg_validation_1),"010-012");
                            etNewPwd.setError(context.getString(R.string.password_msg_validation_1));
                            etNewPwd.setFocusable(true);
                        } else if (Support.isPasswordContainUserIdParts(etUserIdEmailOrMob.getText().toString().toLowerCase(), etNewPwd.getText().toString().trim().toLowerCase())) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.forgot_password_error),  context.getString(R.string.password_msg_validation_2),"010-013");
                            etNewPwd.setError(context.getString(R.string.password_msg_validation_2));
                            etNewPwd.setFocusable(true);
                        } else if (etNewPwd.getText().toString().equalsIgnoreCase(etConfirmPwd.getText().toString())) {
                            // If old strPassword verified, then change the new strPassword
                            doForgotPwd(strUserId, etMobileOtp.getText().toString().trim(), etNewPwd.getText().toString().trim());
                        } else
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.forgot_password_error),  context.getString(R.string.confirm_password_not_match),"010-014");

                    } catch (Exception e) {
                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.forgot_password_error), e.getMessage(),"010-015");
                    }

                    break;

                case R.id.cbShowNewPwd:
                    PasswordTransformationMethod transform = (cbShowNewPwd.isChecked()) ?
                            null : new PasswordTransformationMethod();
                    etNewPwd.setTransformationMethod(transform);
                    etNewPwd.setTransformationMethod(transform);
                    if (cbShowNewPwd.isChecked())
                        cbShowNewPwd.setButtonDrawable(R.mipmap.icon_eye_open);
                    else
                        cbShowNewPwd.setButtonDrawable(R.mipmap.icon_eye_close);

                    break;

                case R.id.cbShowConfirmPwd:
                    transform = (cbShowConfirmPwd.isChecked()) ?
                            null : new PasswordTransformationMethod();
                    etConfirmPwd.setTransformationMethod(transform);
                    etConfirmPwd.setTransformationMethod(transform);
                    if (cbShowConfirmPwd.isChecked())
                        cbShowConfirmPwd.setButtonDrawable(R.mipmap.icon_eye_open);
                    else
                        cbShowConfirmPwd.setButtonDrawable(R.mipmap.icon_eye_close);

                    break;

                case R.id.tvPasswordHint:
                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.password_hint),
                            context.getString(R.string.password_msg_validation_1) + "\n \n "
                                    + context.getString(R.string.password_msg_validation_2),"010-025"
                    );
                    break;
            }
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.forgot_password_error),  e.getMessage(),"010-004");
        }
    }

    /* Function to check if user id (email or mob) exists and send otp to send otp to mobile */
    private void doGetOTPForgotPassword()
    {
        if((pd==null) || ( (pd!=null) && (!pd.isShowing()) ) )
            pd = new ProgressDialog(context);
        
        try {
            OtpHandler.doGetOTP(context, context.getString(R.string.forgot_password_error), pd,
                    strUserId,strUserLoginType,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                pd.dismiss();
                                if (jsResponse.getBoolean("status")) {
                                    etUserIdEmailOrMob.setEnabled(false);
                                    btnSubmitEmailOrMob.setVisibility(View.GONE);
                                    etMobileOtp.setVisibility(View.VISIBLE);
                                    btnSubmitOtp.setVisibility(View.VISIBLE);
                                    llNewPwd.setVisibility(View.GONE);
                                    llConfirmPwd.setVisibility(View.GONE);
                                    llOtp.setVisibility(View.VISIBLE);
                                    MyAlert.showAlert(context,R.mipmap.icon_info,  context.getString(R.string.forgot_password_info),  context.getString(R.string.otp_sent_to_mobile),"010-016");

                                } else {
                                    MyAlert.showAlert(context,R.mipmap.icon_error,  context.getString(R.string.forgot_password_error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"010-017");
                                }

                            } catch (Exception e) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.forgot_password_error),  e.getMessage(),"010-018");
                            }
                        }
                    });
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.forgot_password_error),  e.getMessage(),"010-005");
        }
    }


    /* Function to check if user id (email or mob) exists and send otp to send otp to mobile */
    private void doValidateOtpForgotPassword()
    {
        if((pd==null) || ( (pd!=null) && (!pd.isShowing()) ) )
            pd = new ProgressDialog(context);
        try {
            OtpHandler.doValidateOTP(context, context.getString(R.string.forgot_password_error), pd,
                    etMobileOtp.getText().toString().trim(),strUserId,strUserLoginType,

                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                pd.dismiss();
                                if (jsResponse.getBoolean("status")) {
                                    etMobileOtp.setEnabled(false);
                                    llOtp.setVisibility(View.GONE);
                                    llNewPwd.setVisibility(View.VISIBLE);
                                    llConfirmPwd.setVisibility(View.VISIBLE);
                                    btnSubmitForgotPassword.setVisibility(View.VISIBLE);
                                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.forgot_password_info),  context.getString(R.string.enter_password),"010-019");

                                } else {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.forgot_password_error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"010-020");
                                }

                            } catch (Exception e) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.forgot_password_error),  e.getMessage(),"010-021");
                            }
                        }
                    });
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.forgot_password_error), e.getMessage(),"010-006");
        }
    }

    /* Function to call web service to forgot strPassword*/
    private void doForgotPwd(final String strUserId, final String strOtpValue, final String strPassword)
    {
        if((pd==null) || ( (pd!=null) && (!pd.isShowing()) ) )
            pd = new ProgressDialog(context);
        try {
            JSONObject jsRequest = new JSONObject();
            jsRequest.put("otp", strOtpValue);
            jsRequest.put("user_id", strUserId);
            jsRequest.put("user_login_type", strUserLoginType);

            strNewPasswordFinal = Password.sha256(Password.sha256(strPassword));
            jsRequest.put("new_password", strNewPasswordFinal);

            MyVolley.callWebServiceUsingVolley(Request.Method.PUT, pd, context,"016", true, false,
                    "common/v1/forgotPassword/", jsRequest, context.getString(R.string.forgot_password_error),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                pd.dismiss();
                                Log.d(TAG, "onResponse:forgotPassword "+jsResponse.toString());
                                if (jsResponse.getBoolean("status")) {
                                    final String strMob = jsResponse.getString("response");
                                    User u = MySharedPref.getCurrentUser(context);
                                    if (u != null) {
                                        if(AESHelper.getDecryptedValue(context,u.getMobile()).equalsIgnoreCase(strMob)) {
                                            u.setUser_password(Password.sha256(Password.sha256(strPassword)));
                                            MySharedPref.saveCurrentUser(context, u);
                                        }
                                    }
                                    final Dialog d_alert = new Dialog(context);
                                    MyAlert.dialogForOk
                                            (context, R.mipmap.icon_info, context.getString(R.string.forgot_password_info),
                                                    context.getString(R.string.password_msg_2),
                                                    d_alert,
                                                    context.getString(R.string.ok_l),
                                                    new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            d_alert.dismiss();
                                                            startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                                                            finishAffinity();
                                                        }
                                                    },"010-024");
                                } else {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.forgot_password_error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"010-022");
                                }

                            } catch (Exception e) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.forgot_password_error),  e.getMessage(),"010-023");
                            }
                        }
                    });

        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.forgot_password_error),  e.getMessage(),"010-007");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_internet_connectivity, menu);
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
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
