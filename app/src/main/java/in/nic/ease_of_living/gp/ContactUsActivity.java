package in.nic.ease_of_living.gp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import in.nic.ease_of_living.adapter.PhoneOptionAdapter;
import in.nic.ease_of_living.broadcast.NetworkChangeReceiver;
import in.nic.ease_of_living.interfaces.Communicator;
import in.nic.ease_of_living.supports.IsConnected;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MySharedPref;
import in.nic.ease_of_living.supports.Password;
import in.nic.ease_of_living.utils.AESHelper;
import in.nic.ease_of_living.utils.Common;
import in.nic.ease_of_living.utils.NetworkRegistered;

/**
 * Created by Neha Jain on 9/25/2017.
 */
/*007-005*/
public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener, Communicator {

    private Context context;
    private MenuItem menuItem;
    private NetworkChangeReceiver mNetworkReceiver;
    private String strUserPassw = null;
    private TextView tvEmail;
    private Button btnOkContactUs;
    private String phone="011-49075511";
    private GridView gvCall;
    private PhoneOptionAdapter mPhoneAdapter;
    private ArrayList<String> alCall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            context = this;
            Intent intent = getIntent();
            strUserPassw = intent.getStringExtra("user_password");
            if (Password.matchUserCredentials(context, intent.getStringExtra("user_id"), strUserPassw)) {
                setContentView(R.layout.activity_contact_us);
                Common.setAppHeader(context);
                //getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getString(R.string.app_name) + "</font>"));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                mNetworkReceiver = new NetworkChangeReceiver();
                NetworkRegistered.registerNetworkBroadcastForNougat(context, mNetworkReceiver);
                findViews();
                setListeners();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            200);
                }
                doCall();
                gvCall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try {
                            // String strPhone = (String) mPhoneAdapter.getItem(i);
                            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                Intent callIntent2 = new Intent(Intent.ACTION_CALL);
                                callIntent2.setData(Uri.parse("tel:" + alCall.get(i)));
                                startActivity(callIntent2);
                            }

                        } catch (SecurityException e) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error),  e.getMessage(),"007-005");
                        }
                    }
                });

            }
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.contact_us), e.getMessage(),"007-001");
        }

    }


    private void findViews() {
        try {
            tvEmail = (TextView) findViewById(R.id.tvEmail);
            gvCall = (GridView) findViewById(R.id.gvCall);
            btnOkContactUs = (Button) findViewById(R.id.btnOkContactUs);
            tvEmail.setPaintFlags(tvEmail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.contact_us), e.getMessage(),"007-002");
        }
    }

    private void setListeners(){
        try {
            tvEmail.setOnClickListener(this);
            btnOkContactUs.setOnClickListener(this);
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.contact_us), e.getMessage(),"007-003");
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.tvEmail:
                    String recepientEmail2 = "nictech-ma@gov.in"; // either set to destination email or leave empty
                    Intent intent2 = new Intent(Intent.ACTION_SENDTO);
                    intent2.setData(Uri.parse("mailto:" + recepientEmail2));
                    startActivity(intent2);

                    break;
              /*  case R.id.tvCall:
                    try {
                        Intent callIntent2 = new Intent(Intent.ACTION_CALL);
                        callIntent2.setData(Uri.parse("tel:" + phone));
                        startActivity(callIntent2);
                    } catch (SecurityException e) {
                        MyAlert.showAlert(context, getString(R.string.app_error), "MA-007-005 : " + getString(R.string.err_call_permission_denied));
                    }
                    break;*/
                case R.id.btnOkContactUs:

                    Intent intent = new Intent(ContactUsActivity.this, Home.class);
                    intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                    intent.putExtra("user_password", strUserPassw);
                    startActivity(intent);
                    finishAffinity();

                    break;

            }
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.contact_us), e.getMessage(),"007-004");
        }
    }

    private void doCall()
    {
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

