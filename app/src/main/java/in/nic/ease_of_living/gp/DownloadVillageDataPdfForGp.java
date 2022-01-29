package in.nic.ease_of_living.gp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.nic.ease_of_living.adapter.EnuDbHeaderAdapter;
import in.nic.ease_of_living.adapter.EnumBlockAdapter;
import in.nic.ease_of_living.adapter.VillageAdapter;
import in.nic.ease_of_living.broadcast.NetworkChangeReceiver;
import in.nic.ease_of_living.interfaces.Communicator;
import in.nic.ease_of_living.interfaces.VolleyCallback;
import in.nic.ease_of_living.models.EnumeratedBlock;
import in.nic.ease_of_living.models.Village;
import in.nic.ease_of_living.supports.IsConnected;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MySharedPref;
import in.nic.ease_of_living.supports.MyVolley;
import in.nic.ease_of_living.supports.Password;
import in.nic.ease_of_living.utils.AESHelper;
import in.nic.ease_of_living.utils.NetworkRegistered;


/**
 * Created by Neha Jain on 6/22/2017.
 */
///, "034-001"
public class DownloadVillageDataPdfForGp extends AppCompatActivity implements AdapterView.OnItemClickListener, Communicator {
    NetworkChangeReceiver mNetworkReceiver;
    private Context context;
    private String strUserPwd = null;

    private ListView lvEnuDb;
    private LinearLayout llVillage;
    private ArrayList<Village> alVillage = new ArrayList<>();
    private String strActivityName = null;
      private  String TAG = "DownloadVillageDataPdfForGp";
    int iSpinnerEnuDbSelected = 0, iSpinnerEnuVillageSelected = 0;
    private ArrayList<Village> alEnuVillage = new ArrayList<>();
    private ArrayList<EnumeratedBlock> alEnuDb = new ArrayList<>();
    private EnumBlockAdapter enuDbAdapter;
    VillageAdapter villageAdapter;
    Spinner spinnerEnuVillage;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        Intent intent = getIntent();
        strUserPwd =  intent.getStringExtra("user_password");
        strActivityName = intent.getStringExtra("activity_name");
        if( Password.matchUserCredentials(context, intent.getStringExtra("user_id"), strUserPwd) ) {
            setContentView(R.layout.activity_download_village_base_data);
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getString(R.string.app_name) + "</font>"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mNetworkReceiver = new NetworkChangeReceiver();

            NetworkRegistered.registerNetworkBroadcastForNougat(context, mNetworkReceiver);

            alVillage.clear();
            findViews();
            getVillageListThroughWebService();
            spinnerEnuVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    iSpinnerEnuVillageSelected = 0;
                    lvEnuDb.setAdapter(null);
                    if (i == 0) {
                        iSpinnerEnuVillageSelected = 0;
                        lvEnuDb.setAdapter(null);
                    } else {
                        Village villVlaue = villageAdapter.getItem(i);
                        iSpinnerEnuVillageSelected = villVlaue.getVillage_code();

                        getEnumBlockListThroughWebService(villVlaue.getSub_district_code(), iSpinnerEnuVillageSelected);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }


    private void findViews() {
        llVillage=(LinearLayout)findViewById(R.id.llVillage);
        lvEnuDb=(ListView)findViewById(R.id.lvEnuDb);
        spinnerEnuVillage = (Spinner) findViewById(R.id.spinnerEnuVillage);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
    private void getVillageListThroughWebService() {
        try {

            JSONObject jsRequest = new JSONObject();
            jsRequest.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
            jsRequest.put("state_code", Integer.toString(MySharedPref.getCurrentUser(context).getState_code()));
            jsRequest.put("district_code", Integer.toString(MySharedPref.getCurrentUser(context).getDistrict_code()));
            //jsRequest.put("sub_district_code", Integer.toString(MySharedPref.getCurrentUser(context).getSub_district_code()));
            jsRequest.put("block_code", Integer.toString(MySharedPref.getCurrentUser(context).getBlock_code()));
            jsRequest.put("gp_code", Integer.toString(MySharedPref.getCurrentUser(context).getGp_code()));
            /*jsRequest.put("user_id", "b7b6e4fd-6a04-479b-bd6f-6226edf1f747");
            jsRequest.put("state_code", 2);
            jsRequest.put("district_code", 24);
            jsRequest.put("block_code", 198);
            jsRequest.put("gp_code", 10084);*/


            MyVolley.getJsonResponse(context.getString(R.string.home_option_get_data), context, Request.Method.POST, "019",
                    true, true, true,
                    false, false, false, "eb/v1/getVillageData", jsRequest,
                    new VolleyCallback() {
                        @Override
                        public void onVolleySuccessResponse(JSONObject jsResponse) {

                            try {
                                Log.d(TAG, "onResponse:jsResponse "+jsResponse.toString());
                                if (jsResponse.getBoolean("status")) {
                                    alEnuVillage = new Gson().fromJson(jsResponse.getJSONArray("response").toString(), new TypeToken<List<Village>>() {
                                    }.getType());
                                    alEnuVillage.add(0, new Village(context.getString(R.string.spinner_heading_village)));

                                    villageAdapter = new VillageAdapter(DownloadVillageDataPdfForGp.this,
                                            android.R.layout.simple_spinner_item,
                                            alEnuVillage);
                                    spinnerEnuVillage.setAdapter(villageAdapter);

                                } else {
                                    iSpinnerEnuVillageSelected = 0;
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_option_get_data), jsResponse.getString(context.getString(R.string.web_service_message_identifier)), "004-048");
                                }
                            } catch (Exception e) {
                                iSpinnerEnuVillageSelected = 0;
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_option_get_data), e.getMessage(), "004-049");
                            }

                        }

                        @Override
                        public Response<JSONObject> onVolleyNetworkResponse(NetworkResponse response) {
                            return null;
                        }

                        @Override
                        public void onVolleyErrorResponse(VolleyError error) {

                        }
                    });

        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_option_get_data), e.getMessage(), "004-050");
        }
    }


    private void getEnumBlockListThroughWebService(int iSubDistrictCode, int iVillageCode) {
        try {

            JSONObject jsRequest = new JSONObject();
            jsRequest.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
            jsRequest.put("state_code", Integer.toString(MySharedPref.getCurrentUser(context).getState_code()));
            jsRequest.put("district_code", Integer.toString(MySharedPref.getCurrentUser(context).getDistrict_code()));
            jsRequest.put("sub_district_code", iSubDistrictCode);
            jsRequest.put("block_code", Integer.toString(MySharedPref.getCurrentUser(context).getBlock_code()));
            jsRequest.put("gp_code", Integer.toString(MySharedPref.getCurrentUser(context).getGp_code()));
            jsRequest.put("village_code", iVillageCode);


            /*jsRequest.put("user_id", "b7b6e4fd-6a04-479b-bd6f-6226edf1f747");
            jsRequest.put("state_code", 2);
            jsRequest.put("district_code", 24);
            jsRequest.put("sub_district_code", 168);
            jsRequest.put("block_code", 198);
            jsRequest.put("gp_code", 10084);
            jsRequest.put("village_code", 22677);*/


            MyVolley.getJsonResponse(context.getString(R.string.home_option_get_data), context, Request.Method.POST, "019",
                    true, true, true,
                    false, false, false, "eb/v1/getEBList", jsRequest,
                    new VolleyCallback() {
                        @Override
                        public void onVolleySuccessResponse(JSONObject jsResponse) {

                            try {
                                ;
                                if (jsResponse.getBoolean("status")) {
                                    alEnuDb = new Gson().fromJson(jsResponse.getJSONArray("response").toString(), new TypeToken<List<EnumeratedBlock>>() {
                                    }.getType());

                                    EnuDbHeaderAdapter enuDbAdapter = new EnuDbHeaderAdapter(context, alEnuDb, strActivityName);
                                    lvEnuDb.setAdapter(enuDbAdapter);


                                } else {
                                    lvEnuDb.setAdapter(null);
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_option_get_data), jsResponse.getString(context.getString(R.string.web_service_message_identifier)), "004-048");
                                }
                            } catch (Exception e) {
                                lvEnuDb.setAdapter(null);

                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_option_get_data), e.getMessage(), "004-049");
                            }

                        }

                        @Override
                        public Response<JSONObject> onVolleyNetworkResponse(NetworkResponse response) {
                            return null;
                        }

                        @Override
                        public void onVolleyErrorResponse(VolleyError error) {

                        }
                    });

        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_option_get_data), e.getMessage(), "004-050");
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        NetworkRegistered.unregisterNetworkChanges(context,mNetworkReceiver);
    }
    MenuItem menuItem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_internet_connectivity, menu);
        menuItem=menu.findItem(R.id.action_internet_status);
        if(IsConnected.isInternet_connected(context,false))
            menuItem.setIcon(R.mipmap.icon_online_status);
        else
            menuItem.setIcon(R.mipmap.icon_offline_status);

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

}

