package in.nic.ease_of_living.gp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONObject;

import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MyVolley;
import in.nic.ease_of_living.utils.OtpHandler;
import in.nic.ease_of_living.utils.Support;

/**
 * Created by Neha on 9/14/2017.
 */

public class DeRegister {


    private static TextView tvUserIdEmailOrMob, tvMobileOtp, tvHeader;
    private static EditText etUserIdEmailOrMob, etMobileOtp;
    private static LinearLayout llOtp;
    private static Button btnSubmitEmailOrMob, btnSubmitDeRegister;
    private static String strUserId = "", strUserLoginType = "E";

    public static void  dialogForOtpDeRegister(final Context context,
                                      final Dialog dialog,
                                      final String strAlertTitle,
                                      final ProgressDialog pd,
                                      final Response.Listener<JSONObject> volleyResponseListener
                                      )
    {

        try {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_deregister);
            dialog.setCancelable(false);
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(R.color.transparent);
            tvHeader = (TextView) dialog.findViewById(R.id.tvHeader);
            tvUserIdEmailOrMob = (TextView) dialog.findViewById(R.id.tvUserIdEmailOrMob);
            etUserIdEmailOrMob = (EditText) dialog.findViewById(R.id.etUserIdEmailOrMob);
            tvMobileOtp = (TextView) dialog.findViewById(R.id.tvMobileOtp);
            etMobileOtp = (EditText) dialog.findViewById(R.id.etMobileOtp);
            btnSubmitEmailOrMob = (Button) dialog.findViewById(R.id.btnSubmitEmailOrMob);
            Button btnResendOtp = (Button) dialog.findViewById(R.id.btnResendOtp);
            Button btnSubmitOtp = (Button) dialog.findViewById(R.id.btnSubmitOtp);
            btnSubmitDeRegister = (Button) dialog.findViewById(R.id.btnSubmitDeRegister);
            llOtp = (LinearLayout) dialog.findViewById(R.id.llOtp);
            ImageView ivClose = (ImageView) dialog.findViewById(R.id.ivClose);

            tvHeader.setText(context.getString(R.string.de_register));
            btnSubmitDeRegister.setText(context.getString(R.string.de_register));

            Support.setChangeListener(etUserIdEmailOrMob, tvUserIdEmailOrMob);
            Support.setChangeListener(etMobileOtp, tvMobileOtp);

            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            btnSubmitEmailOrMob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    strUserId = etUserIdEmailOrMob.getText().toString().trim();
                    strUserLoginType = "E";
                    if (strUserId.length() == 0) {
                        MyAlert.showAlert(context, R.mipmap.icon_error, strAlertTitle + context.getString(R.string.error),  context.getString(R.string.empty_email_mob),"008-007");
                    } else if (Support.isValidEmail(strUserId)) {
                        strUserLoginType = "E";
                        doGetOTPDeRegister(context, strAlertTitle, pd);
                    } else if (Support.isValidMobileNumber(strUserId)) {
                        strUserLoginType = "M";
                        doGetOTPDeRegister(context, strAlertTitle, pd);
                    } else
                        MyAlert.showAlert(context, R.mipmap.icon_error, strAlertTitle + context.getString(R.string.error),  context.getString(R.string.invalid_email_mob),"008-008");

                }
            });

            btnResendOtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    strUserId = etUserIdEmailOrMob.getText().toString().trim();
                    strUserLoginType = "E";
                    if (strUserId.length() == 0) {
                        MyAlert.showAlert(context, R.mipmap.icon_error, strAlertTitle + context.getString(R.string.error),  context.getString(R.string.empty_email_mob),"008-009");
                    } else if (Support.isValidEmail(strUserId)) {
                        strUserLoginType = "E";
                        doGetOTPDeRegister(context, strAlertTitle, pd);
                    } else if (Support.isValidMobileNumber(strUserId)) {
                        strUserLoginType = "M";
                        doGetOTPDeRegister(context, strAlertTitle, pd);
                    } else
                        MyAlert.showAlert(context, R.mipmap.icon_error, strAlertTitle + context.getString(R.string.error),  context.getString(R.string.invalid_email_mob),"008-010");

                }
            });

            btnSubmitOtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (etMobileOtp.getText().toString().trim().isEmpty())
                        etMobileOtp.setError(context.getString(R.string.enter_otp));
                    else if (etMobileOtp.getText().toString().trim().length() != 6)
                        MyAlert.showAlert(context, R.mipmap.icon_error, strAlertTitle + context.getString(R.string.error), context.getString(R.string.invalid_otp),"008-011");
                    else
                        doValidateOtpDeRegister(context, strAlertTitle, pd);
                }
            });

            btnSubmitDeRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deRegistration(context, strAlertTitle, pd, volleyResponseListener);
                }
            });

            dialog.show();
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.deregister_error), e.getMessage(),"008-001");
        }
    }

    /* Function to check if user id (email or mob) exists and send otp to send otp to mobile */
    private static void doGetOTPDeRegister(final Context context, final String strAlertTitle, final ProgressDialog pd
                                           )
    {
        try {
            OtpHandler.doGetOTP(context, strAlertTitle, pd,
                    strUserId,strUserLoginType,
                    new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(final JSONObject jsResponse) {
                            try {
                                pd.dismiss();
                                if (jsResponse.getBoolean("status")) {
                                    etUserIdEmailOrMob.setEnabled(false);
                                    btnSubmitEmailOrMob.setVisibility(View.GONE);
                                    etMobileOtp.setVisibility(View.VISIBLE);
                                    llOtp.setVisibility(View.VISIBLE);
                                    btnSubmitDeRegister.setVisibility(View.GONE);
                                    MyAlert.showAlert(context, R.mipmap.icon_info, strAlertTitle + context.getString(R.string.info), context.getString(R.string.otp_sent_to_mobile),"008-012");

                                } else {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, strAlertTitle + context.getString(R.string.error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"008-013");
                                }
                            } catch(Exception e)
                            {
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.deregister_error),  e.getMessage(),"008-002");
                            }
                        }
                    });

        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.deregister_error),  e.getMessage(),"008-003");
        }
    }

    /* Function to check if user id (email or mob) exists and send otp to send otp to mobile */
    private static void doValidateOtpDeRegister(final Context context, final String strAlertTitle, final ProgressDialog pd)
    {
        try {
            OtpHandler.doValidateOTP(context, strAlertTitle, pd,
                    etMobileOtp.getText().toString().trim(), strUserId,strUserLoginType,

                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                pd.dismiss();
                                if (jsResponse.getBoolean("status")) {
                                    etMobileOtp.setEnabled(false);
                                    llOtp.setVisibility(View.GONE);
                                    btnSubmitDeRegister.setVisibility(View.VISIBLE);
                                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.deregister_info), context.getString(R.string.otp_validate_success),"008-014");

                                } else {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.deregister_error),  jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"008-015");
                                }

                            } catch(Exception e)
                            {
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.deregister_error),  e.getMessage(),"008-004");
                            }
                        }
                    });
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.deregister_error), e.getMessage(),"008-005");
        }
    }

    /* Function to deregister user */
    private static void deRegistration(final Context context, final String strAlertTitle,final ProgressDialog pd,
                                       final Response.Listener<JSONObject> volleyResponseListener
    )
    {
        try {

            JSONObject jsRequest = new JSONObject();

            jsRequest.put("otp", etMobileOtp.getText().toString().trim());
            jsRequest.put("user_id", strUserId);
            jsRequest.put("user_login_type", strUserLoginType);

            MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context,"015", true, false, "registration/v1/userDeregistration/", jsRequest, strAlertTitle,
                    volleyResponseListener);

        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.deregister_error),  e.getMessage(),"008-006");
        }
    }

}
