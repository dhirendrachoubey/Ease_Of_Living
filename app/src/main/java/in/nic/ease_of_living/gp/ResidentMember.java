package in.nic.ease_of_living.gp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;


import in.nic.ease_of_living.adapter.SeccPopulationAdapter;
import in.nic.ease_of_living.broadcast.NetworkChangeReceiver;
import in.nic.ease_of_living.dbo.DBHelper;
import in.nic.ease_of_living.dbo.HouseholdEolController;

import in.nic.ease_of_living.dbo.SeccHouseholdController;
import in.nic.ease_of_living.dbo.SeccPopulationController;
import in.nic.ease_of_living.installation.Installation;
import in.nic.ease_of_living.interfaces.Communicator;
import in.nic.ease_of_living.models.HouseholdEol;

import in.nic.ease_of_living.models.HouseholdUpdated;
import in.nic.ease_of_living.models.MasterCommon;

import in.nic.ease_of_living.models.SeccHousehold;
import in.nic.ease_of_living.models.SeccPopulation;
import in.nic.ease_of_living.supports.GPSTracker;
import in.nic.ease_of_living.supports.IsConnected;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MyDateSupport;
import in.nic.ease_of_living.supports.MySharedPref;
import in.nic.ease_of_living.supports.Password;
import in.nic.ease_of_living.utils.AESHelper;
import in.nic.ease_of_living.utils.NetworkRegistered;
import in.nic.ease_of_living.utils.PopulateMasterLists;
import in.nic.ease_of_living.utils.Support;


/**
 * Created by Chinki Sai on 8/3/2017.
 */
//010-023
public class ResidentMember extends AppCompatActivity implements View.OnClickListener,Communicator, GoogleApiClient.ConnectionCallbacks {
        NetworkChangeReceiver mNetworkReceiver;
        static boolean isBackable = true;
        Context context;
        SeccPopulationAdapter adapter;
        Boolean isLast = false;
        Integer iHhdUid;
        boolean isCompleted = false;
        ArrayList<SeccPopulation> alSeccPop = new ArrayList<>();
        private String strUserPassw = null;

        private final static int REQUEST_LOCATION = 199;
        private GoogleApiClient googleApiClient = null;
        private Double dLatitude = 0.0, dLongitude = 0.0;
        private GPSTracker gps;
        private boolean bIsGpsThroughSubmit = false;
        private Button btnContinue;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                Intent intent = getIntent();
                context = this;
                strUserPassw = intent.getStringExtra("user_password");
                if (Password.matchUserCredentials(context, intent.getStringExtra("user_id"), strUserPassw)) {
                        setContentView(R.layout.activity_resident_member);
                        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getString(R.string.app_name) + "</font>"));
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                        mNetworkReceiver = new NetworkChangeReceiver();
                        NetworkRegistered.registerNetworkBroadcastForNougat(context, mNetworkReceiver);
                        findViews();
                        setListener();
                        try {

                                if (getIntent() != null)
                                {
                                        iHhdUid = getIntent().getIntExtra("HH_UID",0);

                                }

                                /*if (PopulationUpdatedController.isNew_house(DBHelper.getInstance(context, false), iHhdSlNo) == 1) {
                                        btnHouseMigratedOut.setVisibility(View.GONE);
                                        btnSkipHouseHold.setVisibility(View.GONE);
                                } else {*/
                                        if (HouseholdEolController.isHhdAvailable(DBHelper.getInstance(context, true), iHhdUid) > 0) {
                                                btnHouseMigratedOut.setVisibility(View.GONE);
                                                btnSkipHouseHold.setVisibility(View.GONE);
                                        } else {
                                                // btnHouseMigratedOut.setVisibility(View.VISIBLE);
                                                btnSkipHouseHold.setVisibility(View.VISIBLE);
                                        }
                                //}

                                alSeccPop = SeccPopulationController.getPopByHhdId(DBHelper.getInstance(context, false), context, iHhdUid);


                                if (alSeccPop.size() > 0) {
                                        MySharedPref.saveHeadofhouse(context, alSeccPop.get(0));
                                        //  MySharedPref.saveCurrentVillage(context, MasterVillageController.getVillage(DBHelper.getInstance(context, true), alSeccPop.get(0).getVillage_code()));
                                }

                                String strHhdNo = iHhdUid !=null ? String.valueOf(iHhdUid) : null;
                                tvHouseNumber.setText("House No. (" + strHhdNo + ")");

                                bIsGpsThroughSubmit = false;
                                gps = new GPSTracker(context);
                                Boolean isGPSEnabled = gps.getLocationManager().isProviderEnabled(LocationManager.GPS_PROVIDER);

                                // check if GPS enabled
                                if (gps.canGetLocation()) {
                                        dLatitude = gps.getLatitude();
                                        dLongitude = gps.getLongitude();
                                } else if (!isGPSEnabled) {
                                        //new GPS().turnGpsOn(context, AddGramPanchayatActivity.this);
                                        enableLoc();
                                }

                                adapter = new SeccPopulationAdapter(context, alSeccPop);
                                lvResidentMember.setAdapter(adapter);
                                lvResidentMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                                                confirmationPopup();
                                        }
                                });
                        } catch (Exception e) {
                                MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.resident_member_info),e.getMessage(), "010-010");
                        }
                }

        }

        ListView lvResidentMember;
        TextView tvAddNewMember,tvHouseNumber;
        Button btnHouseMigratedOut,btnSkipHouseHold;
        private void findViews() {
                lvResidentMember=(ListView)findViewById(R.id.lvResidentMember);
                tvAddNewMember=(TextView)findViewById(R.id.tvAddNewMember);
                tvHouseNumber=(TextView)findViewById(R.id.tvHouseNumber);
                btnSkipHouseHold=(Button)findViewById(R.id.btnSkipHouseHold);
                btnHouseMigratedOut=(Button)findViewById(R.id.btnHouseMigratedOut);
                btnContinue=(Button)findViewById(R.id.btnContinue);
        }

        private void setListener() {
                tvAddNewMember.setOnClickListener(this);
                btnHouseMigratedOut.setOnClickListener(this);
                btnContinue.setOnClickListener(this);
                btnSkipHouseHold.setOnClickListener(this);
        }

      //Uncovered_house houseUncoveredReason = null;
        MasterCommon houseUncoveredReason = null;
        @Override
        public void onClick(View view) {
                switch (view.getId())
                {
                        case R.id.tvAddNewMember:
                              //  startActivity(new Intent(context,IndividualData.class));
                                break;
                        case R.id.btnSkipHouseHold:
                                houseUncoveredReason =null;
                                final Dialog dialog = new Dialog(context);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setContentView(R.layout.layout_reason_of_skip_house);
                                dialog.setCancelable(false);
                                Window window = dialog.getWindow();
                                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                                window.setBackgroundDrawableResource(R.color.transparent);

                                Button btnContinue = (Button) dialog.findViewById(R.id.btnContinue);
                                ImageView ivClose = (ImageView) dialog.findViewById(R.id.ivClose);
                                final Spinner spinnerHouseholdSkipReason=(Spinner)dialog.findViewById(R.id.spinnerSelect_reason);
                                spinnerHouseholdSkipReason.setAdapter(PopulateMasterLists.adapterUncoveredHhdReasonCategory);
                                spinnerHouseholdSkipReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                MasterCommon obj_masterCommon = PopulateMasterLists.adapterUncoveredHhdReasonCategory.getItem(i);
                                                houseUncoveredReason = obj_masterCommon;
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                });

                                // if button is clicked, close the custom pd
                                btnContinue.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                                dialog.dismiss();

                                                        if((houseUncoveredReason ==null)||(houseUncoveredReason.getType_code() == 0))
                                                                MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.resident_member_info),context.getString(R.string.select_reason_for_house_skip), "010-010");
                                                        else
                                                        {
                                                                try {
                                                                        saveUncoveredData(context, houseUncoveredReason, context.getString(R.string.resident_member_info),context.getString(R.string.house_skipped_success));
                                                                }
                                                                catch (Exception e)
                                                                {

                                                                }
                                                        }


                                        }
                                });
                                ivClose.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                                dialog.dismiss();
                                        }
                                });

                                dialog.show();

                                break;
                        case R.id.btnHouseMigratedOut:
                                MasterCommon unH = new MasterCommon();
                                unH.setType_code(4);
                                unH.setType_name(context.getString(R.string.family_migrated_out));
                                saveUncoveredData(context,unH,context.getString(R.string.resident_member_info),context.getString(R.string.family_migrated_out_successfully));
                                break;
                        case R.id.btnContinue:
                                confirmationPopup();
                                break;
                }
        }

        private void saveUncoveredData(final Context context, MasterCommon selectedReason, String title, String msg) {
                try {
                        gps = new GPSTracker(context);
                        Boolean isGPSEnabled = gps.getLocationManager().isProviderEnabled(LocationManager.GPS_PROVIDER);
                        if (!isGPSEnabled) {
                                //new GPS().turnGpsOn(context, AddGramPanchayatActivity.this);
                                googleApiClient = null;
                                enableLoc();
                        } else if (  ((gps.canGetLocation()) || ((dLatitude != 0.0) && (dLongitude != 0.0)))) {
                                if (gps.canGetLocation()) {
                                        dLatitude = gps.getLatitude();
                                        dLongitude = gps.getLongitude();
                                }

                                Long tsLong = System.currentTimeMillis() / 1000;
                                String ts = tsLong.toString();
                                HouseholdEol h = new HouseholdEol();

                                SeccHousehold seccHousehold = SeccHouseholdController.getHhdByHhdId( DBHelper.getInstance(context, true),context, alSeccPop.get(0).getHhd_uid()).get(0);

                                h.setState_code(seccHousehold.getState_code());
                                h.setState_name(seccHousehold.getState_name());
                                h.setState_name_sl(seccHousehold.getState_name_sl());
                                h.setStatecode(seccHousehold.getStatecode());
                                h.setDistrict_code(seccHousehold.getDistrict_code());
                                h.setDistrict_name(seccHousehold.getDistrict_name());
                                h.setDistrict_name_sl(seccHousehold.getDistrict_name_sl());
                                h.setDistrictcode(seccHousehold.getDistrictcode());
                                h.setTehsilcode(seccHousehold.getTehsilcode());
                                h.setTowncode(seccHousehold.getTowncode());

                                h.setSub_district_code(seccHousehold.getSub_district_code());
                                h.setSub_district_name(seccHousehold.getSub_district_name());
                                h.setSub_district_name_sl(seccHousehold.getSub_district_name_sl());
                                h.setBlock_code(seccHousehold.getBlock_code());
                                h.setBlock_name(seccHousehold.getBlock_name());
                                h.setBlock_name_sl(seccHousehold.getBlock_name_sl());
                                h.setWardid(seccHousehold.getWardid());
                                h.setAhlblockno(seccHousehold.getAhlblockno());
                                h.setAhlsubblockno(seccHousehold.getAhlsubblockno());
                                h.setAhl_family_tin(seccHousehold.getAhl_family_tin());

                                h.setGp_code(seccHousehold.getGp_code());
                                h.setGp_name(seccHousehold.getGp_name());
                                h.setGp_name_sl(seccHousehold.getGp_name_sl());
                                h.setVillage_code(seccHousehold.getVillage_code());
                                h.setVillage_name(seccHousehold.getVillage_name());
                                h.setVillage_name_sl(seccHousehold.getVillage_name_sl());
                                h.setEnum_block_code(seccHousehold.getEnum_block_code());
                                h.setEnum_block_name(seccHousehold.getEnum_block_name());
                                h.setEnum_block_name_sl(seccHousehold.getEnum_block_name_sl());

                                h.setHhd_uid(alSeccPop.get(0).getHhd_uid());
                                h.setHhd_latitude(String.valueOf(dLatitude));
                                h.setHhd_longitude(String.valueOf(dLongitude));
                                h.setIs_uncovered(true);
                                h.setUncovered_reason_code(selectedReason.getType_code());
                                h.setUncovered_reason(selectedReason.getType_name());
                                h.setDt_created(MyDateSupport.getCurrentDateTimefordatabaseStorage());
                                h.setTs_updated(MyDateSupport.getCurrentDateTimefordatabaseStorage());
                                //h.setDt_sync(ts);
                                h.setUser_id(AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                                h.setDevice_id(MySharedPref.getDevice_ID(context));
                                h.setApp_id(Installation.id(context));
                                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                                h.setApp_version(packageInfo.versionName);
                                h.setIs_synchronized(1);

                                boolean bResult = HouseholdEolController.insertHhd(context, DBHelper.getInstance(context, true), h);

                                if (bResult)
                                {
                                        final Dialog dialog_alert = new Dialog(context);
                                        MyAlert.dialogForOk
                                                (context,R.mipmap.icon_info, title,
                                                        msg,
                                                        dialog_alert,
                                                        context.getString(R.string.ok),
                                                        new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View view) {
                                                                        dialog_alert.dismiss();
                                                                        Intent intent = new Intent(ResidentMember.this,SearchResident.class);
                                                                        intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                                                                        intent.putExtra("user_password", strUserPassw);
                                                                        startActivity(intent);
                                                                        finish();
                                                                }
                                                        }, "010-011");
                                }
                                else
                                        MyAlert.showAlert(context, R.mipmap.icon_error, title, context.getString(R.string.something_wrong), "010-012");
                        }
                }catch (Exception e)
                {

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
        public void onBackPressed() {
                if(isBackable)
                {
                        super.onBackPressed();
                        /*startActivity(new Intent(ResidentMember.this,MainActivity.class));
                        finishAffinity();*/
                }
                else {
                        final Dialog dialog_alert = new Dialog(context);
                        MyAlert.dialogForOk
                                (context,R.mipmap.icon_info,  context.getString(R.string.resident_member_warning),
                                        context.getString(R.string.cant_go_back_before_enumeration_complete),
                                        dialog_alert,
                                        context.getString(R.string.ok),
                                        new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                        dialog_alert.dismiss();
                                                }
                                        }, "010-013");
                }
        }



        private void confirmationPopup() {
                try {
                        final Dialog dialogAlert = new Dialog(context);
                        MyAlert.dialogForCancelOk
                                (context,R.mipmap.icon_info,  context.getString(R.string.resident_member_warning),
                                        context.getString(R.string.household_finalized_msg),
                                        dialogAlert,
                                        context.getString(R.string.yes),
                                        new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                        dialogAlert.dismiss();
                                                        Intent i = new Intent(ResidentMember.this, HouseholdData.class);
                                                        i.putExtra("HHUID", iHhdUid);
                                                        i.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                                                        i.putExtra("user_password", strUserPassw);
                                                        startActivity(i);
                                                        finish();

                                                }
                                        },
                                        context.getString(R.string.no),
                                        new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                        dialogAlert.dismiss();
                                                        finish();

                                                }
                                        },
                                        new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                        dialogAlert.dismiss();

                                                }
                                        }, "010-017");
                }catch(Exception e)
                {
                        MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.individual_data_error), e.getMessage(), "010-018");
                }

        }

        /* This function opens pop - up  to enable GPS*/
        private void enableLoc() {

                try {
                        if (googleApiClient == null) {
                                googleApiClient = new GoogleApiClient.Builder(this)
                                        .addApi(LocationServices.API)
                                        .addConnectionCallbacks(this)
                                        .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                                                @Override
                                                public void onConnectionFailed(ConnectionResult connectionResult) {

                                                        //  Timber.v("Location error " + connectionResult.getErrorCode());
                                                }
                                        }).build();
                                googleApiClient.connect();

                                LocationRequest locationRequest = LocationRequest.create();
                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                                locationRequest.setInterval(30 * 1000);
                                locationRequest.setFastestInterval(5 * 1000);
                                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                                        .addLocationRequest(locationRequest);

                                builder.setAlwaysShow(true);

                                PendingResult<LocationSettingsResult> result =
                                        LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
                                result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                                        @Override
                                        public void onResult(LocationSettingsResult result) {
                                                final Status status = result.getStatus();
                                                switch (status.getStatusCode()) {
                                                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                                                try {
                                                                        // Show the pd by calling startResolutionForResult(),
                                                                        // and check the result in onActivityResult().
                                                                        status.startResolutionForResult(
                                                                                (Activity) context, REQUEST_LOCATION);
                                                                } catch (IntentSender.SendIntentException e) {
                                                                        // Ignore the error.
                                                                }
                                                                break;
                                                }
                                        }
                                });
                        }
                }catch(Exception e)
                {
                        MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.add_household_error),  e.getMessage(), "010-019");
                }
        }

        /* This function checks the GPS pd result*/
        @Override
        protected void onActivityResult(int requestCode,int resultCode, Intent data) {

                try {
                        switch (requestCode) {
                                case REQUEST_LOCATION:
                                        switch (resultCode) {
                                                case Activity.RESULT_CANCELED: {
                                                        // replace the AddGramPanchayatActivity with the page you want to redirect to on click of cancel button

                                                        if (bIsGpsThroughSubmit) {
                                                                final Dialog dialogAlert = new Dialog(context);
                                                                MyAlert.dialogForCancelOk
                                                                        (context,R.mipmap.icon_info,  context.getString(R.string.add_household_warning),
                                                                                context.getString(R.string.gps_cancel_submit_data_msg),
                                                                                dialogAlert,
                                                                                context.getString(R.string.allow_gps),
                                                                                new View.OnClickListener() {
                                                                                        @Override
                                                                                        public void onClick(View view) {
                                                                                                dialogAlert.dismiss();
                                                                                                Intent intent = new Intent(ResidentMember.this, Home.class);
                                                                                                intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                                                                                                intent.putExtra("user_password", strUserPassw);
                                                                                                startActivity(intent);
                                                                                                finishAffinity();

                                                                                        }
                                                                                },
                                                                                context.getString(R.string.delete_data_and_proceed),
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
                                                                                }, "010-020");
                                                        } else {
                                                                Intent intent = new Intent(ResidentMember.this, Home.class);
                                                                intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                                                                intent.putExtra("user_password", strUserPassw);
                                                                startActivity(intent);
                                                                finishAffinity();
                                                        }
                                                        break;
                                                }

                                                case Activity.RESULT_OK:
                                                        if (gps.canGetLocation()) {
                                                                dLatitude = gps.getLatitude();
                                                                dLongitude = gps.getLongitude();
                                                        }
                                                        break;
                                                default: {
                                                        break;
                                                }
                                        }
                                        break;
                        }
                }catch(Exception e)
                {
                        MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.add_household_error), e.getMessage(), "010-021");
                }

        }

        @Override
        public void onDestroy() {
                super.onDestroy();
                NetworkRegistered.unregisterNetworkChanges(this,mNetworkReceiver);
        }

        MenuItem menuItem;
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.menu_internet_connectivity, menu);
                menuItem=menu.findItem(R.id.action_internet_status);
                if(IsConnected.isInternet_connected(this,false))
                        menuItem.setIcon(R.mipmap.icon_online_status);
                else
                        menuItem.setIcon(R.mipmap.icon_offline_status);

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
        public void onConnected(@Nullable Bundle bundle) {

        }

        @Override
        public void onConnectionSuspended(int i) {

        }
}

