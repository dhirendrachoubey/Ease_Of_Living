package in.nic.ease_of_living.supports;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import in.nic.ease_of_living.gp.R;


/**
 * Created by Neha Jain on 6/22/2017.
 */
public class MyAlert {

    private static Toast toast;
    public static int LENGTH_LONG=1;

    public static void showAlert(Context context, int icon, String type, String strMessage, String strAlertId) {
        if (toast == null) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.my_custom_dialog);
            dialog.setCancelable(false);
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(R.color.transparent);

            ImageView ivMessage = (ImageView) dialog.findViewById(R.id.ivMessage);
            ivMessage.setImageResource(icon);
            TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
            TextView textTittle = (TextView) dialog.findViewById(R.id.tvTitle);
            TextView textType = (TextView) dialog.findViewById(R.id.tvMessageType);
            TextView tvDateTime = (TextView) dialog.findViewById(R.id.tvDateTime);
            TextView tvAlertId = (TextView) dialog.findViewById(R.id.tvAlertId);
            textTittle.setText(type);
            tvMessage.setText(strMessage);
            tvMessage.setGravity(Gravity.CENTER);

            textType.setVisibility(View.GONE);
            tvAlertId.setText("( EOL-" + strAlertId + " )");

            Calendar c = Calendar.getInstance();

            SimpleDateFormat df = new SimpleDateFormat("dd MMM, yy (HH:mm)");
            String formattedDate = df.format(c.getTime());
            tvDateTime.setText(formattedDate);

            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        } else {
            toast.cancel();
            toast = Toast.makeText(context, strMessage, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public static void showAlertLeftAlign(Context context, int icon, String type, String strMessage, String strAlertId) {
        if (toast == null) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.my_custom_dialog);
            dialog.setCancelable(false);
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(R.color.transparent);

            ImageView ivMessage = (ImageView) dialog.findViewById(R.id.ivMessage);
            ivMessage.setImageResource(icon);
            TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
            TextView textTittle = (TextView) dialog.findViewById(R.id.tvTitle);
            TextView textType = (TextView) dialog.findViewById(R.id.tvMessageType);
            TextView tvDateTime = (TextView) dialog.findViewById(R.id.tvDateTime);
            TextView tvAlertId = (TextView) dialog.findViewById(R.id.tvAlertId);
            textTittle.setText(type);
            tvMessage.setGravity(Gravity.LEFT);
            tvMessage.setText(strMessage);

            textType.setVisibility(View.GONE);
            tvAlertId.setText("( EOL-" + strAlertId + " )");

            Calendar c = Calendar.getInstance();

            SimpleDateFormat df = new SimpleDateFormat("dd MMM, yy (HH:mm)");
            String formattedDate = df.format(c.getTime());
            tvDateTime.setText(formattedDate);

            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        } else {
            toast.cancel();
            toast = Toast.makeText(context, strMessage, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public static void showAlert1(Context context, String type, String str) {
        if (toast == null)
        {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.my_custom_dialog);
            dialog.setCancelable(false);
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(R.color.transparent);

            TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
            tvMessage.setText(str);
            TextView textTittle = (TextView) dialog.findViewById(R.id.tvMessageTitle);
            textTittle.setText(type);
            TextView textType = (TextView) dialog.findViewById(R.id.tvMessageType);
            textType.setVisibility(View.GONE);
            ImageView ivClose = (ImageView) dialog.findViewById(R.id.ivClose);
            ivClose.setVisibility(View.GONE);
            tvMessage.setGravity(Gravity.CENTER);

            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
        else
        {
            toast.cancel();
            toast = Toast.makeText(context, str, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public static void networkAlert(final Context context, final Activity activity) {

        if (toast == null)
        {
         //   AppMsg appMsg= AppMsgController.getAppMsg(DBHelper.getInstance(context, false), "Net_Connect");
            if (context!= null && !activity.isFinishing()) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.my_custom_dialog);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);


                TextView textTittle = (TextView) dialog.findViewById(R.id.tvMessageTitle);
                textTittle.setText("Warning : Network");
                TextView textType = (TextView) dialog.findViewById(R.id.tvMessageType);
                textType.setText("Warning : Network");
                TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
                tvMessage.setGravity(Gravity.CENTER);


                tvMessage.setText("No internet access. Please check your network settings.");

                Button dialogButtonSetting = (Button) dialog.findViewById(R.id.dialogButtonOK);
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonSkip);
                dialogButton.setText("Exit");

                dialogButton.setVisibility(View.VISIBLE);
                dialogButtonSetting.setText("Settings");

                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        activity.finishAffinity();
                    }
                });

                dialogButtonSetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        //startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });



                dialog.show();
            }
        }
        else
        {
            toast.cancel();
            toast = Toast.makeText(context, "", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public static boolean dialogForCancelOk1(final Context context, final String sTitle, final String sMessage,
                                            final Dialog dialog,
                                            final String sOk, View.OnClickListener okClickListener,
                                            final String sCancel, View.OnClickListener cancelClickListener
                                            , View.OnClickListener closeClickListener)
    {
        final boolean bResult[] = {false};
        //final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.my_custom_dialog);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.transparent);

        TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
        TextView textTittle = (TextView) dialog.findViewById(R.id.tvMessageTitle);
        textTittle.setText(sTitle);
        tvMessage.setText(sMessage);
        TextView textType = (TextView) dialog.findViewById(R.id.tvMessageType);
        textType.setVisibility(View.GONE);
        tvMessage.setGravity(Gravity.CENTER);

        Button dialogButtonOk = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonSkip);
        ImageView ivClose = (ImageView) dialog.findViewById(R.id.ivClose);
        dialogButtonOk.setOnClickListener(okClickListener);
        dialogButtonCancel.setOnClickListener(cancelClickListener);
        ivClose.setOnClickListener(closeClickListener);
        dialogButtonOk.setText(sOk);
        dialogButtonCancel.setText(sCancel);
        dialogButtonCancel.setVisibility(View.VISIBLE);

        dialog.show();

        return bResult[0];
    }

    public static boolean dialogForOk1(final Context context, final String sTitle, final String sMessage,
                                                final Dialog dialog,
                                                final String sOk, View.OnClickListener okClickListener)
    {
        final boolean bResult[] = {false};
        //final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.my_custom_dialog);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.transparent);

        TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
        TextView textTittle = (TextView) dialog.findViewById(R.id.tvMessageTitle);
        textTittle.setText(sTitle);
        tvMessage.setText(sMessage);
        TextView textType = (TextView) dialog.findViewById(R.id.tvMessageType);
        textType.setVisibility(View.GONE);
        tvMessage.setGravity(Gravity.CENTER);

        Button dialogButtonOk = (Button) dialog.findViewById(R.id.dialogButtonOK);
        ImageView ivClose = (ImageView) dialog.findViewById(R.id.ivClose);
        ivClose.setVisibility(View.GONE);
        dialogButtonOk.setOnClickListener(okClickListener);
        dialogButtonOk.setText(sOk);
        dialog.show();

        return bResult[0];
    }

    public static boolean dialogForCancelOk(final Context context, int icon, final String strTitle, final String sMessage,
                                            final Dialog dialog,
                                            final String strOk, View.OnClickListener okClickListener,
                                            final String strCancel, View.OnClickListener cancelClickListener
            , View.OnClickListener closeClickListener, String strAlertId
    )
    {
        final boolean bResult[] = {false};
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.my_custom_dialog);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.transparent);

        TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
        TextView textTittle = (TextView) dialog.findViewById(R.id.tvTitle);
        TextView textType = (TextView) dialog.findViewById(R.id.tvMessageType);
        TextView tvDateTime = (TextView) dialog.findViewById(R.id.tvDateTime);
        TextView tvAlertId = (TextView) dialog.findViewById(R.id.tvAlertId);
        textTittle.setText(strTitle);
        tvMessage.setText(sMessage);
        tvMessage.setGravity(Gravity.CENTER);

        textType.setVisibility(View.GONE);
        tvAlertId.setText("( EOL-" + strAlertId + " )");

        Button dialogButtonOk = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonSkip);
        ImageView ivIconMessage = (ImageView) dialog.findViewById(R.id.ivMessage);
        dialogButtonOk.setOnClickListener(okClickListener);
        dialogButtonCancel.setOnClickListener(cancelClickListener);
        dialogButtonOk.setText(strOk);
        dialogButtonCancel.setText(strCancel);
        dialogButtonCancel.setVisibility(View.VISIBLE);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM, yy (HH:mm)");
        String formattedDate = df.format(c.getTime());
        tvDateTime.setText(formattedDate);

        dialog.show();

        return bResult[0];
    }

    public static boolean dialogForOk(final Context context, int icon, final String str_title, final String str_message,
                                      final Dialog dialog,
                                      final String str_ok, View.OnClickListener okClickListener,
                                      String strAlertId)
    {
        final boolean b_result[] = {false};
        //final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.my_custom_dialog);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.transparent);

        TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
        TextView textTittle = (TextView) dialog.findViewById(R.id.tvTitle);
        textTittle.setText(str_title);
        tvMessage.setText(str_message);
        TextView textType = (TextView) dialog.findViewById(R.id.tvMessageType);
        TextView tvDateTime = (TextView) dialog.findViewById(R.id.tvDateTime);
        TextView tvAlertId = (TextView) dialog.findViewById(R.id.tvAlertId);
        tvMessage.setGravity(Gravity.CENTER);

        textType.setVisibility(View.GONE);
        tvAlertId.setText("( EOL-" + strAlertId + " )");

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM, yy (HH:mm)");
        String formattedDate = df.format(c.getTime());
        tvDateTime.setText(formattedDate);

        Button dialogButtonOk = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialogButtonOk.setOnClickListener(okClickListener);
        dialogButtonOk.setText(str_ok);
        dialog.show();

        return b_result[0];
    }


}
