package in.nic.ease_of_living.gp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.nic.ease_of_living.broadcast.NetworkChangeReceiver;
import in.nic.ease_of_living.interfaces.Communicator;
import in.nic.ease_of_living.supports.IsConnected;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MyDateSupport;
import in.nic.ease_of_living.supports.MySharedPref;
import in.nic.ease_of_living.supports.Password;
import in.nic.ease_of_living.utils.AESHelper;
import in.nic.ease_of_living.utils.Common;
import in.nic.ease_of_living.utils.NetworkRegistered;


/**
 * Created by Neha Jain on 6/28/2017.
 */
/*011-004*/
public class MyProfile extends AppCompatActivity implements View.OnClickListener, Communicator{

    Context context;
    private MenuItem menuItem;
    private NetworkChangeReceiver mNetworkReceiver;
    private ProgressDialog pd;
    private String strUserPassw = null;
    private EditText etUserId,etEmailId,etMobileNumber,etAddress,etName, etDob,etIdProof,etOrganisation,etRole,etAssignedby,
            etGenderData, etStateRole, etDistrictRole, etDbRole, etGpRole;
    private TextView tvAllotedLocation, tvStateRole, tvDistrictRole, tvDbRole, tvGpRole, tvAddress, tvMobileVerified, tvEmailVerified;
    private LinearLayout llAllotedRoleLocation, llUploadPicture;
    private ImageView ivPhoto, ivEmailVerified, ivMobileVerified;
    private Button btnOkMyProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            context = this;

            Intent intent = getIntent();
            strUserPassw = intent.getStringExtra("user_password");
            if (Password.matchUserCredentials(context, intent.getStringExtra("user_id"), strUserPassw)) {
                setContentView(R.layout.activity_myprofile);
                Common.setAppHeader(context);
                //getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getString(R.string.app_name) + "</font>"));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                mNetworkReceiver = new NetworkChangeReceiver();
                NetworkRegistered.registerNetworkBroadcastForNougat(context, mNetworkReceiver);
                findViews();
                setListener();

                if (MySharedPref.getCurrentUser(context).getProfile_pic() != null) {
                    byte[] decodedString = Base64.decode(MySharedPref.getCurrentUser(context).getProfile_pic(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    if (decodedByte != null) {
                        ivPhoto.setImageBitmap(decodedByte);
                        ivPhoto.setVisibility(View.VISIBLE);
                        llUploadPicture.setVisibility(View.VISIBLE);
                    } else {
                        ivPhoto.setVisibility(View.GONE);
                        llUploadPicture.setVisibility(View.GONE);
                    }
                } else {
                    ivPhoto.setVisibility(View.GONE);
                    llUploadPicture.setVisibility(View.GONE);
                }


                if (MySharedPref.getCurrentUser(context).getUser_id() != null)
                    etUserId.setText(AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                else
                    etUserId.setText(context.getString(R.string.not_available));

                etUserId.setVisibility(View.GONE);

                if (MySharedPref.getCurrentUser(context).getEmail_id() != null)
                    etEmailId.setText(AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getEmail_id()) + " ");
                else
                    etEmailId.setText(context.getString(R.string.not_available));

                if (MySharedPref.getCurrentUser(context).getMobile() != null)
                    etMobileNumber.setText(AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getMobile()) + " ");
                else
                    etMobileNumber.setText(context.getString(R.string.not_available));
                String n1 = "", n2 = "", n3 = "", n4 = "";
                if (MySharedPref.getCurrentUser(context).getTitle_name() != null)
                    n1 = MySharedPref.getCurrentUser(context).getTitle_name();

                if (MySharedPref.getCurrentUser(context).isIs_email_validated()) {
                    tvEmailVerified.setText(" " + getString(R.string.verified));
                    ivEmailVerified.setImageResource(R.mipmap.icon_verified);
                } else {
                    tvEmailVerified.setText(" " + getString(R.string.not_verified));
                    ivEmailVerified.setImageResource(R.mipmap.icon_non_verified);
                }

                if (MySharedPref.getCurrentUser(context).isIs_mobile_validated()) {
                    tvMobileVerified.setText(" " + getString(R.string.verified));
                    ivMobileVerified.setImageResource(R.mipmap.icon_verified);
                } else {
                    tvMobileVerified.setText(" " + getString(R.string.not_verified));
                    ivMobileVerified.setImageResource(R.mipmap.icon_non_verified);
                }


                n4 = MySharedPref.getCurrentUser(context).getLast_name();
                if (MySharedPref.getCurrentUser(context).getFirst_name() != null)
                    n2 = MySharedPref.getCurrentUser(context).getFirst_name();

                if (MySharedPref.getCurrentUser(context).getMiddle_name() != null)
                    n3 = MySharedPref.getCurrentUser(context).getMiddle_name();

                if (MySharedPref.getCurrentUser(context).getLast_name() != null)

                    etName.setText(n1 + " " + " " + n2 + " " + n3 + " " + n4);

                String a1 = "", a2 = "", a3 = "", a4 = "";
                if (MySharedPref.getCurrentUser(context).getOrg_address_hh_area() != null)
                    a1 = MySharedPref.getCurrentUser(context).getOrg_address_hh_area();


                if (MySharedPref.getCurrentUser(context).getOrg_address_landmark() != null)
                    a2 = MySharedPref.getCurrentUser(context).getOrg_address_landmark();

                if (MySharedPref.getCurrentUser(context).getOrg_address_city() != null)
                    a3 = MySharedPref.getCurrentUser(context).getOrg_address_city();

                if (MySharedPref.getCurrentUser(context).getOrg_phone_no() != null)
                    a4 = MySharedPref.getCurrentUser(context).getOrg_phone_no();


                String strAddress = "";
                if (MySharedPref.getCurrentUser(context).getOrg_address_hh_area() != null)
                    strAddress += MySharedPref.getCurrentUser(context).getOrg_address_hh_area() + "\n";

                if (MySharedPref.getCurrentUser(context).getOrg_address_landmark() != null)
                    strAddress += MySharedPref.getCurrentUser(context).getOrg_address_landmark() + "\n";

                if (MySharedPref.getCurrentUser(context).getOrg_address_city() != null)
                    strAddress += MySharedPref.getCurrentUser(context).getOrg_address_city() + "\n";

                if (MySharedPref.getCurrentUser(context).getOrg_state_code() != null)
                    strAddress += MySharedPref.getCurrentUser(context).getOrg_state_name() + "\n";

                if (MySharedPref.getCurrentUser(context).getOrg_district_code() != null)
                    strAddress += MySharedPref.getCurrentUser(context).getOrg_district_name() + "\n";

                if (MySharedPref.getCurrentUser(context).getOrg_phone_no() != null)
                    strAddress += MySharedPref.getCurrentUser(context).getOrg_phone_no() + "\n";


                //String str_addressFinal = a1+" "+a2+"\n"+a3+" "+strAddress;
                if (strAddress.trim().isEmpty()) {
                    etAddress.setVisibility(View.GONE);
                    tvAddress.setVisibility(View.GONE);
                } else {
                    etAddress.setVisibility(View.VISIBLE);
                    tvAddress.setVisibility(View.VISIBLE);
                    etAddress.setText(strAddress);
                }

                if (MySharedPref.getCurrentUser(context).getDob() != null)
                    etDob.setText(MyDateSupport.formateDateFromstring("yyyy-MM-dd", "dd, MMM yyyy", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getDob())));
                else
                    etDob.setText(getString(R.string.not_available));

                if (MySharedPref.getCurrentUser(context).getIdentity_type() != null)
                    etIdProof.setText(MySharedPref.getCurrentUser(context).getIdentity_type() + " - " + AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getIdentity_number()));
                else
                    etIdProof.setText(getString(R.string.not_available));

                if (!((MySharedPref.getCurrentUser(context).getOrganisation() == null) || (MySharedPref.getCurrentUser(context).getOrganisation().length() == 0)))
                    etOrganisation.setText(MySharedPref.getCurrentUser(context).getOrganisation());
                else
                    etOrganisation.setText(getString(R.string.not_available));

                if (MySharedPref.getCurrentUser(context).getGroup_name() != null)
                    etRole.setText(MySharedPref.getCurrentUser(context).getGroup_name());
                else
                    etRole.setText(getString(R.string.not_available));

                switch (MySharedPref.getCurrentUser(context).getGroup_id()) {
                    case "ADM":
                        tvAllotedLocation.setVisibility(View.GONE);
                        llAllotedRoleLocation.setVisibility(View.GONE);
                        break;

                    case "NTA":
                        tvAllotedLocation.setVisibility(View.GONE);
                        llAllotedRoleLocation.setVisibility(View.GONE);
                        break;

                    case "STA":
                        tvAllotedLocation.setVisibility(View.VISIBLE);
                        llAllotedRoleLocation.setVisibility(View.VISIBLE);
                        tvStateRole.setVisibility(View.VISIBLE);
                        etStateRole.setVisibility(View.VISIBLE);
                        tvDistrictRole.setVisibility(View.GONE);
                        etDistrictRole.setVisibility(View.GONE);
                        tvDbRole.setVisibility(View.GONE);
                        etDbRole.setVisibility(View.GONE);
                        tvGpRole.setVisibility(View.GONE);
                        etGpRole.setVisibility(View.GONE);
                        etStateRole.setText(MySharedPref.getCurrentUser(context).getState_name() + " (" + MySharedPref.getCurrentUser(context).getState_code() + ")");
                        break;

                    case "DTA":
                        tvAllotedLocation.setVisibility(View.VISIBLE);
                        llAllotedRoleLocation.setVisibility(View.VISIBLE);
                        tvStateRole.setVisibility(View.VISIBLE);
                        etStateRole.setVisibility(View.VISIBLE);
                        tvDistrictRole.setVisibility(View.VISIBLE);
                        etDistrictRole.setVisibility(View.VISIBLE);
                        tvDbRole.setVisibility(View.GONE);
                        etDbRole.setVisibility(View.GONE);
                        tvGpRole.setVisibility(View.GONE);
                        etGpRole.setVisibility(View.GONE);
                        etStateRole.setText(MySharedPref.getCurrentUser(context).getState_name() + " (" + MySharedPref.getCurrentUser(context).getState_code() + ")");
                        etDistrictRole.setText(MySharedPref.getCurrentUser(context).getDistrict_name() + " (" + MySharedPref.getCurrentUser(context).getDistrict_code() + ")");
                        break;

                    case "DBA":
                        tvAllotedLocation.setVisibility(View.VISIBLE);
                        llAllotedRoleLocation.setVisibility(View.VISIBLE);
                        tvStateRole.setVisibility(View.VISIBLE);
                        etStateRole.setVisibility(View.VISIBLE);
                        tvDistrictRole.setVisibility(View.VISIBLE);
                        etDistrictRole.setVisibility(View.VISIBLE);
                        tvDbRole.setVisibility(View.VISIBLE);
                        etDbRole.setVisibility(View.VISIBLE);
                        tvGpRole.setVisibility(View.GONE);
                        etGpRole.setVisibility(View.GONE);
                        etStateRole.setText(MySharedPref.getCurrentUser(context).getState_name() + " (" + MySharedPref.getCurrentUser(context).getState_code() + ")");
                        etDistrictRole.setText(MySharedPref.getCurrentUser(context).getDistrict_name() + " (" + MySharedPref.getCurrentUser(context).getDistrict_code() + ")");
                        etDbRole.setText(MySharedPref.getCurrentUser(context).getBlock_name() + " (" + MySharedPref.getCurrentUser(context).getBlock_code() + ")");
                        break;

                    case "GPU":
                        tvAllotedLocation.setVisibility(View.VISIBLE);
                        llAllotedRoleLocation.setVisibility(View.VISIBLE);
                        tvStateRole.setVisibility(View.VISIBLE);
                        etStateRole.setVisibility(View.VISIBLE);
                        tvDistrictRole.setVisibility(View.VISIBLE);
                        etDistrictRole.setVisibility(View.VISIBLE);
                        tvDbRole.setVisibility(View.VISIBLE);
                        etDbRole.setVisibility(View.VISIBLE);
                        tvGpRole.setVisibility(View.VISIBLE);
                        etGpRole.setVisibility(View.VISIBLE);
                        etStateRole.setText(MySharedPref.getCurrentUser(context).getState_name() + " (" + MySharedPref.getCurrentUser(context).getState_code() + ")");
                        etDistrictRole.setText(MySharedPref.getCurrentUser(context).getDistrict_name() + " (" + MySharedPref.getCurrentUser(context).getDistrict_code() + ")");
                        etDbRole.setText(MySharedPref.getCurrentUser(context).getBlock_name() + " (" + MySharedPref.getCurrentUser(context).getBlock_code() + ")");
                        etGpRole.setText(MySharedPref.getCurrentUser(context).getGp_name() + " (" + MySharedPref.getCurrentUser(context).getGp_code() + ")");
                        break;
                }

                if (MySharedPref.getCurrentUser(context).getApproved_by() != null)
                    etAssignedby.setText(MySharedPref.getCurrentUser(context).getApproved_by());
                else
                    etAssignedby.setText(getString(R.string.not_available));


                switch (MySharedPref.getCurrentUser(context).getGender()) {
                    case "1":
                        etGenderData.setText(context.getString(R.string.male));
                        break;
                    case "2":
                        etGenderData.setText(context.getString(R.string.female));
                        break;
                    case "3":
                        etGenderData.setText(context.getString(R.string.trans_gender));
                        break;

                }
            }
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.my_profile_error),  e.getMessage(),"011-001");
        }
    }

    private void findViews() {
        try {
            llUploadPicture = (LinearLayout) findViewById(R.id.llUploadPicture);
            btnOkMyProfile = (Button) findViewById(R.id.btnOkMyProfile);
            ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
            etUserId = (EditText) findViewById(R.id.etUserId);
            etRole = (EditText) findViewById(R.id.etRole);
            etEmailId = (EditText) findViewById(R.id.etEmailId);
            etName = (EditText) findViewById(R.id.etName);
            etMobileNumber = (EditText) findViewById(R.id.etMobileNumber);
            etAddress = (EditText) findViewById(R.id.etAddress);
            etAssignedby = (EditText) findViewById(R.id.etAssignedby);
            etDob = (EditText) findViewById(R.id.etDob);
            etIdProof = (EditText) findViewById(R.id.etIdProof);
            etOrganisation = (EditText) findViewById(R.id.etOrganisation);
            etGenderData = (EditText) findViewById(R.id.etGenderData);
            etStateRole = (EditText) findViewById(R.id.etStateRole);
            etDistrictRole = (EditText) findViewById(R.id.etDistrictRole);
            etDbRole = (EditText) findViewById(R.id.etDbRole);
            etGpRole = (EditText) findViewById(R.id.etGpRole);

            tvAllotedLocation = (TextView) findViewById(R.id.tvAllotedLocation);
            tvStateRole = (TextView) findViewById(R.id.tvStateRole);
            tvDistrictRole = (TextView) findViewById(R.id.tvDistrictRole);
            tvDbRole = (TextView) findViewById(R.id.tvDbRole);
            tvGpRole = (TextView) findViewById(R.id.tvGpRole);
            tvAddress = (TextView) findViewById(R.id.tvAddress);
            tvMobileVerified = (TextView) findViewById(R.id.tvMobileVerified);
            tvEmailVerified = (TextView) findViewById(R.id.tvEmailVerified);
            ivEmailVerified = (ImageView) findViewById(R.id.ivEmailVerified);
            ivMobileVerified = (ImageView) findViewById(R.id.ivMobileVerified);


            llAllotedRoleLocation = (LinearLayout) findViewById(R.id.llAllotedRoleLocation);
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.my_profile_error),  e.getMessage(),"011-002");
        }
    }

    private void setListener() {

        try {
            btnOkMyProfile.setOnClickListener(this);
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.my_profile_error), e.getMessage(),"011-003");
        }
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.btnOkMyProfile:
                    Intent intent = new Intent(MyProfile.this, Home.class);
                    intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                    intent.putExtra("user_password", strUserPassw);
                    startActivity(intent);
                    finishAffinity();
                    break;
            }
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.my_profile_error), e.getMessage(),"011-004");
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
