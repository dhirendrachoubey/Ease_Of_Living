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

import java.util.HashMap;
import java.util.Map;

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
import in.nic.ease_of_living.utils.Support;

/**
 * Created by Neha Jain on 6/28/2017.
 */
/*006-022*/
public class ChangePassword extends AppCompatActivity implements View.OnClickListener, Communicator{


    Context context;
    EditText etOldPass, etNewPass, etRePass;
    private CheckBox cbShowOldPwd, cbShowNewPwd, cbShowConfirmPwd;
    private TextView tvPasswordHint, tvOldPwd, tvConfirmPwd;
    private LinearLayout llOldPwd, llNewPwd, llConfirmPwd, llpasswordHint;
    Button btnSubmit;
    private ProgressDialog pd;
    private MenuItem menuItem;
    private NetworkChangeReceiver mNetworkReceiver;
    private String strSalt=null;
    private String strUserPassw = null;
private  String TAG = "ChangePassword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            context = this;
            Intent intent = getIntent();
            strUserPassw = intent.getStringExtra("user_password");
            if (Password.matchUserCredentials(context, intent.getStringExtra("user_id"), strUserPassw)) {
                setContentView(R.layout.activity_change_password);
                Common.setAppHeader(context);
                //getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getString(R.string.app_name) + "</font>"));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                mNetworkReceiver = new NetworkChangeReceiver();
                NetworkRegistered.registerNetworkBroadcastForNougat(context, mNetworkReceiver);
                findViews();
                setListener();
                etNewPass.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15), InputFilters.passwordFilter});
                etOldPass.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15), InputFilters.passwordFilter});
                etRePass.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15), InputFilters.passwordFilter});

                Support.setChangeListener(etNewPass, llpasswordHint);
                Support.setChangeListener(etOldPass, tvOldPwd);
                Support.setChangeListener(etRePass, tvConfirmPwd);

            }
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_password_error),  e.getMessage(),"006-001");
        }
    }

    private void findViews() {
        try {
            etOldPass = (EditText) findViewById(R.id.etOldPwd);
            etNewPass = (EditText) findViewById(R.id.etNewPwd);
            etRePass = (EditText) findViewById(R.id.etConfirmPwd);
            btnSubmit = (Button) findViewById(R.id.btnSubmitChangePassword);

            cbShowOldPwd = (CheckBox) findViewById(R.id.cbShowOldPwd);
            cbShowNewPwd = (CheckBox) findViewById(R.id.cbShowNewPwd);
            cbShowConfirmPwd = (CheckBox) findViewById(R.id.cbShowConfirmPwd);

            llOldPwd = (LinearLayout) findViewById(R.id.llOldPwd);
            llNewPwd = (LinearLayout) findViewById(R.id.llNewPwd);
            llConfirmPwd = (LinearLayout) findViewById(R.id.llConfirmPwd);
            llpasswordHint = (LinearLayout) findViewById(R.id.llpasswordHint);

            tvPasswordHint = (TextView) findViewById(R.id.tvPasswordHint);
            tvOldPwd = (TextView) findViewById(R.id.tvOldPwd);
            tvConfirmPwd = (TextView) findViewById(R.id.tvConfirmPwd);
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_password_error), e.getMessage(),"006-002");
        }
    }

    private void setListener() {
        try {
            btnSubmit.setOnClickListener(this);
            cbShowOldPwd.setOnClickListener(this);
            cbShowNewPwd.setOnClickListener(this);
            cbShowConfirmPwd.setOnClickListener(this);
            tvPasswordHint.setOnClickListener(this);
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_password_error), e.getMessage(),"006-003");
        }
    }

    /* Call webservice to get salt*/
        private void getSalt(final ProgressDialog pd, final String strUserId, final String strOldPassword, final String strNewPasswordFinal) {
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
            jsRequest.put("user_id", strUserId);
            jsRequest.put("user_login_type", "U");

            MyVolley.callWebServiceUsingVolleyWithNetworkResponse(Request.Method.POST, pd, context,"004", headers, false,
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
                                        doChangePwd(pd, strUserId,Password.getFinalPassword(strSalt,strOldPassword),strNewPasswordFinal);
                                    } else {
                                        pd.dismiss();
                                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_password_error),  jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"006-008");
                                    }
                                }
                                else {
                                    pd.dismiss();
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_password_error),  jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"006-009");
                                }

                            } catch (Exception e) {
                                pd.dismiss();
                                MyAlert.showAlert(context,R.mipmap.icon_error, context.getString(R.string.change_password_error), e.getMessage(),"006-010");
                            }
                }
            });

        } catch(Exception e)
        {
            pd.dismiss();
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_password_error),  e.getMessage(),"006-004");
        }
    }

    /* Function to call web service to change strPassword */
    private void doChangePwd(final ProgressDialog pd, final String strUserId, final String strOldPasswordFinal, final String strNewPasswordFinal)
    {
        try {
            JSONObject jsRequest = new JSONObject();

            jsRequest.put("user_id",strUserId);
            jsRequest.put("user_login_type","U");
            jsRequest.put("old_password",strOldPasswordFinal);
            jsRequest.put("new_password",strNewPasswordFinal);

            MyVolley.callWebServiceUsingVolley(Request.Method.PUT, pd, context,"026", false, true, "profile/v1/changePassword/", jsRequest, context.getString(R.string.change_password_error),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                pd.dismiss();
                                Log.d(TAG, "onResponse:changePassword "+jsResponse.toString());
                                if (jsResponse.getBoolean("status")) {
                                    User u = MySharedPref.getCurrentUser(context);
                                    u.setUser_password(strNewPasswordFinal);

                                    MySharedPref.saveCurrentUser(context, u);

                                    final Dialog dialog_alert = new Dialog(context);
                                    MyAlert.dialogForOk
                                            (context, R.mipmap.icon_info, context.getString(R.string.change_password_info),
                                                    context.getString(R.string.password_msg_2),
                                                    dialog_alert,
                                                    context.getString(R.string.ok),
                                                    new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            dialog_alert.dismiss();
                                                            Intent intent = new Intent(ChangePassword.this, Home.class);
                                                            intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                                                            intent.putExtra("user_password", etNewPass.getText().toString().trim());
                                                            startActivity(intent);
                                                            finishAffinity();

                                                        }
                                                    },"006-021");

                                } else {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_password_error),  jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"006-011");
                                }

                            } catch (Exception e) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_password_error),  e.getMessage(),"006-005");
                            }
                        }
                    });
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_password_error),  e.getMessage(),"006-006");
        }
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnSubmitChangePassword:
                    try {
                        String strFinalPassword = Password.sha256(Password.sha256(etOldPass.getText().toString().trim()));

                        // Verify the old strPassword
                        if (etOldPass.getText().toString().trim().isEmpty()) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_password_error), context.getString(R.string.enter_old_user_password),"006-012");
                            etOldPass.setError(context.getString(R.string.enter_old_user_password));
                        } else if (etNewPass.getText().toString().trim().isEmpty()) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_password_error),  context.getString(R.string.enter_new_user_password),"006-013");
                            etNewPass.setError(context.getString(R.string.enter_new_user_password));
                        } else if (etRePass.getText().toString().trim().isEmpty()) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_password_error),  context.getString(R.string.enter_confirm_user_password),"006-014");
                            etRePass.setError(context.getString(R.string.enter_confirm_user_password));
                        } else if (!(strFinalPassword.equalsIgnoreCase(MySharedPref.getCurrentUser(context).getUser_password()))) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_password_error),  context.getString(R.string.old_password_not_match),"006-015");
                            etOldPass.setError(context.getString(R.string.old_password_not_match));
                            etOldPass.setFocusable(true);
                        } else if (etOldPass.getText().toString().trim().equalsIgnoreCase(etNewPass.getText().toString().trim())) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_password_error), context.getString(R.string.password_msg_3),"006-016");
                            etNewPass.setError(context.getString(R.string.password_msg_3));
                            etNewPass.setFocusable(true);
                        } else if (!Support.isValidPassword(etNewPass.getText().toString().trim())) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_password_error), context.getString(R.string.password_msg_validation_1),"006-017");
                            etNewPass.setError(context.getString(R.string.password_msg_validation_1));
                            etNewPass.setFocusable(true);
                        } else if (Support.isPasswordContainUserIdParts(MySharedPref.getCurrentUser(context).getUser_id().toLowerCase(), etNewPass.getText().toString().trim().toLowerCase())) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_password_error), context.getString(R.string.password_msg_validation_2),"006-018");
                            etNewPass.setError(context.getString(R.string.password_msg_validation_2));
                            etNewPass.setFocusable(true);
                        } else if (etNewPass.getText().toString().equalsIgnoreCase(etRePass.getText().toString())) {
                            // If old strPassword verified, then change the new strPassword
                            //doChangePwd(MySharedPref.getCurrentUser(context).getUser_id(), etNewPass.getText().toString());
                            pd = new ProgressDialog(context);
                            getSalt(pd, AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()),
                                    Password.sha256(Password.sha256(etOldPass.getText().toString().trim())), Password.sha256(Password.sha256(etNewPass.getText().toString().trim())));
                        } else if (!(etNewPass.getText().toString().equalsIgnoreCase(etRePass.getText().toString()))) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_password_error), context.getString(R.string.confirm_password_not_match),"006-019");
                            etNewPass.setError(context.getString(R.string.confirm_password_not_match));
                            etRePass.setError(context.getString(R.string.confirm_password_not_match));
                            etNewPass.setFocusable(true);
                        }
                    } catch (Exception e) {
                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_password_error),  e.getMessage(),"006-020");
                    }

                    break;

                case R.id.cbShowOldPwd:
                    PasswordTransformationMethod transform = (cbShowOldPwd.isChecked()) ?
                            null : new PasswordTransformationMethod();
                    etOldPass.setTransformationMethod(transform);
                    etOldPass.setTransformationMethod(transform);
                    if (cbShowOldPwd.isChecked())
                        cbShowOldPwd.setButtonDrawable(R.mipmap.icon_eye_open);
                    else
                        cbShowOldPwd.setButtonDrawable(R.mipmap.icon_eye_close);

                    break;

                case R.id.cbShowNewPwd:
                    transform = (cbShowNewPwd.isChecked()) ?
                            null : new PasswordTransformationMethod();
                    etNewPass.setTransformationMethod(transform);
                    etNewPass.setTransformationMethod(transform);
                    if (cbShowNewPwd.isChecked())
                        cbShowNewPwd.setButtonDrawable(R.mipmap.icon_eye_open);
                    else
                        cbShowNewPwd.setButtonDrawable(R.mipmap.icon_eye_close);

                    break;

                case R.id.cbShowConfirmPwd:
                    transform = (cbShowConfirmPwd.isChecked()) ?
                            null : new PasswordTransformationMethod();
                    etRePass.setTransformationMethod(transform);
                    etRePass.setTransformationMethod(transform);
                    if (cbShowConfirmPwd.isChecked())
                        cbShowConfirmPwd.setButtonDrawable(R.mipmap.icon_eye_open);
                    else
                        cbShowConfirmPwd.setButtonDrawable(R.mipmap.icon_eye_close);

                    break;

                case R.id.tvPasswordHint:
                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.password_hint),
                            context.getString(R.string.password_msg_validation_1) + "\n \n "
                                    + context.getString(R.string.password_msg_validation_2),"006-022"
                    );
                    break;
            }
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_password_error), e.getMessage(),"006-007");
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
