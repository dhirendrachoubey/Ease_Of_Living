package in.nic.ease_of_living.gp;

import android.app.Dialog;
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.nic.ease_of_living.adapter.DistrictAdapter;
import in.nic.ease_of_living.adapter.StateAdapter;
import in.nic.ease_of_living.adapter.UserApprovalAdapter;
import in.nic.ease_of_living.broadcast.NetworkChangeReceiver;
import in.nic.ease_of_living.interfaces.Communicator;
import in.nic.ease_of_living.models.District;
import in.nic.ease_of_living.models.State;
import in.nic.ease_of_living.models.User;
import in.nic.ease_of_living.supports.IsConnected;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MySharedPref;
import in.nic.ease_of_living.supports.MyVolley;
import in.nic.ease_of_living.supports.Password;
import in.nic.ease_of_living.utils.AESHelper;
import in.nic.ease_of_living.utils.Common;
import in.nic.ease_of_living.utils.NetworkRegistered;
import in.nic.ease_of_living.utils.Support;


/**
 * Created by Neha Jain on 6/22/2017.
 */
/*017-019*/
public class UserApproval extends AppCompatActivity implements AdapterView.OnItemClickListener,Communicator {
    private NetworkChangeReceiver mNetworkReceiver;
    private Context context;
    private UserApprovalAdapter userApprovalAdapter;
    private ArrayList<User> alUserForApproval=new ArrayList<>();
    private Spinner spinnerState, spinnerDistrict;
    private String strStateCodeSelected=null,strDistrictCodeSelected=null;
    private StateAdapter stateAdapter;
    private DistrictAdapter districtAdapter;
    private ArrayList<State> alState;
    private ArrayList<District> alDistrict;
    private Button btnSubmitGetApprovalList;
    private TextView tvState, tvDistrict;
    private LinearLayout llState, llDistrict;
    private ProgressDialog pd;
    private String strUserPassw = null;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            context = this;
            Intent intent = getIntent();
            strUserPassw = intent.getStringExtra("user_password");
            if (Password.matchUserCredentials(context, intent.getStringExtra("user_id"), strUserPassw)) {
                setContentView(R.layout.activity_user_approval);
                Common.setAppHeader(context);
                //getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getString(R.string.app_name) + "</font>"));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                mNetworkReceiver = new NetworkChangeReceiver();

                NetworkRegistered.registerNetworkBroadcastForNougat(context, mNetworkReceiver);

                findViews();
                setListener();
                setSpinners();
                alState = new ArrayList<>();
                alDistrict = new ArrayList<>();


                String strGroupId = MySharedPref.getCurrentUser(context).getGroup_id();
                if ((strGroupId.equalsIgnoreCase("ADM")) || (strGroupId.equalsIgnoreCase("NTA"))) {
                    getState();
                    tvState.setVisibility(View.VISIBLE);
                    llState.setVisibility(View.VISIBLE);
                    tvDistrict.setVisibility(View.VISIBLE);
                    llDistrict.setVisibility(View.VISIBLE);
                    btnSubmitGetApprovalList.setVisibility(View.VISIBLE);
                } else if (strGroupId.equalsIgnoreCase("STA")) {
                    tvState.setVisibility(View.GONE);
                    llState.setVisibility(View.GONE);
                    tvDistrict.setVisibility(View.VISIBLE);
                    llDistrict.setVisibility(View.VISIBLE);
                    btnSubmitGetApprovalList.setVisibility(View.VISIBLE);

                    alDistrict.clear();
                    getDistrict(String.valueOf(MySharedPref.getCurrentUser(context).getState_code()));
                } else {
                    tvState.setVisibility(View.GONE);
                    llState.setVisibility(View.GONE);
                    tvDistrict.setVisibility(View.GONE);
                    llDistrict.setVisibility(View.GONE);
                    btnSubmitGetApprovalList.setVisibility(View.GONE);
                    getUserApprovalData();
                }

                btnSubmitGetApprovalList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    /*if(llState.getVisibility()== View.VISIBLE && strStateCodeSelected==null) {
                        MyAlert.showAlert(context, context.getString(R.string.approval_warning), context.getString(R.string.select_state));
                    }
                    else if(llDistrict.getVisibility()== View.VISIBLE && strDistrictCodeSelected==null) {
                        MyAlert.showAlert(context, context.getString(R.string.approval_warning), context.getString(R.string.select_district));
                    }
                    else
                    */
                        getUserApprovalData();
                    }
                });
            }
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.approval_error), e.getMessage(),"017-001");
        }
    }

    private void setListener() {
        try {
            lvUserApproval.setOnItemClickListener(this);
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.approval_error),  e.getMessage(),"017-002");
        }
    }

    private ListView lvUserApproval;
    private EditText etSearch;
    private Button btnAll;
    private void findViews() {
        try {
            btnAll = (Button) findViewById(R.id.btnAll);
            etSearch = (EditText) findViewById(R.id.etSearch);
            lvUserApproval = (ListView) findViewById(R.id.lvUserApproval);
            spinnerState = (Spinner) findViewById(R.id.spinnerState);
            spinnerDistrict = (Spinner) findViewById(R.id.spinnerDistrict);
            btnSubmitGetApprovalList = (Button) findViewById(R.id.btnSubmitGetApprovalList);
            tvState = (TextView) findViewById(R.id.tvState);
            tvDistrict = (TextView) findViewById(R.id.tvDistrict);
            llState = (LinearLayout) findViewById(R.id.llState);
            llDistrict = (LinearLayout) findViewById(R.id.llDistrict);
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.approval_error),  e.getMessage(),"017-003");
        }
    }

    /* set spinners listeners*/
    private void setSpinners()
    {
        try {
            spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerDistrict.setAdapter(null);
                    if (i == 0) {
                        strStateCodeSelected = null;
                    } else {
                        State state = stateAdapter.getItem(i);
                        strStateCodeSelected = state.getState_code();
                        if (strStateCodeSelected != null) {
                            getDistrict(strStateCodeSelected);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0)
                        strDistrictCodeSelected = null;
                    else {
                        District district = districtAdapter.getItem(i);
                        strDistrictCodeSelected = district.getDistrict_code();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.approval_error),  e.getMessage(),"017-004");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            popupUserDetail(alUserForApproval.get(i));
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.approval_error), e.getMessage(),"017-005");
        }
    }

    /* Functionality to get list of users to be approved */
    private void getUserApprovalData()
    {
        if((pd==null) || ( (pd!=null) && (!pd.isShowing()) ) )
            pd = new ProgressDialog(context);
        try {
            JSONObject jsRequest = new JSONObject();
            jsRequest.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));

            if(strStateCodeSelected != null)
                jsRequest.put("state_code", strStateCodeSelected);

            if(strDistrictCodeSelected != null)
            jsRequest.put("district_code", strDistrictCodeSelected);

            lvUserApproval.setAdapter(null);

            MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context,"011", true, true,  "user/management/v1/getUsersForApproval", jsRequest, context.getString(R.string.approval_error),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                pd.dismiss();
                                alUserForApproval.clear();
                                if (jsResponse.getBoolean("status")) {

                                    alUserForApproval=new Gson().fromJson(jsResponse.getJSONArray("response").toString(), new TypeToken<List<User>>() {
                                    }.getType());

                                    userApprovalAdapter = new UserApprovalAdapter(context, alUserForApproval);
                                    lvUserApproval.setAdapter(userApprovalAdapter);
                                } else {
                                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.approval_info),  jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"017-006");
                                }
                            } catch (Exception e) {
                                pd.dismiss();
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.approval_error), e.getMessage(),"017-007");
                            }
                        }
                    });
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.approval_error), e.getMessage(),"017-008");
        }

    }

    private void popupUserDetail(final User userForApproval) {

        try {
            final Dialog dialogUserDetail = new Dialog(context);
            dialogUserDetail.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogUserDetail.setContentView(R.layout.layout_users_detail);
            dialogUserDetail.setCancelable(false);
            Window window = dialogUserDetail.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(R.color.transparent);

            ImageView ivUserImage = (ImageView) dialogUserDetail.findViewById(R.id.ivUserImage);
            TextView tvUserName = (TextView) dialogUserDetail.findViewById(R.id.tvUserName);
            TextView tvEmail = (TextView) dialogUserDetail.findViewById(R.id.tvEmail);
            TextView tvState = (TextView) dialogUserDetail.findViewById(R.id.tvState);
            TextView tvDistrict = (TextView) dialogUserDetail.findViewById(R.id.tvDistrict);
            TextView tvTehsil = (TextView) dialogUserDetail.findViewById(R.id.tvTehsil);
            TextView tvTown = (TextView) dialogUserDetail.findViewById(R.id.tvTown);
            TextView tvName = (TextView) dialogUserDetail.findViewById(R.id.tvName);
            TextView tvMobile = (TextView) dialogUserDetail.findViewById(R.id.tvMobile);
            TextView tvRejectedMsg = (TextView) dialogUserDetail.findViewById(R.id.tvRejectedMsg);
            TextView tvOrganisationName = (TextView) dialogUserDetail.findViewById(R.id.tvOrganisationName);
            TextView tvMobileVerified = (TextView) dialogUserDetail.findViewById(R.id.tvMobileVerified);
            TextView tvEmailVerified = (TextView) dialogUserDetail.findViewById(R.id.tvEmailVerified);
            ImageView ivEmailVerified = (ImageView) dialogUserDetail.findViewById(R.id.ivEmailVerified);
            ImageView ivMobileVerified = (ImageView) dialogUserDetail.findViewById(R.id.ivMobileVerified);

            tvUserName.setText(userForApproval.getEmail_id());
            tvEmail.setText(userForApproval.getEmail_id() + " ");
            if (userForApproval.getState_name() != null)
                tvState.setText(userForApproval.getState_name());
            else
                tvState.setText(context.getString(R.string.not_available));

            if (userForApproval.isIs_email_validated()) {
                tvEmailVerified.setText(" " + getString(R.string.verified));
                ivEmailVerified.setImageResource(R.mipmap.icon_verified);
            } else {
                tvEmailVerified.setText(" " + getString(R.string.not_verified));
                ivEmailVerified.setImageResource(R.mipmap.icon_non_verified);
            }

            if (userForApproval.isIs_mobile_validated()) {
                tvMobileVerified.setText(" " + getString(R.string.verified));
                ivMobileVerified.setImageResource(R.mipmap.icon_verified);
            } else {
                tvMobileVerified.setText(" " + getString(R.string.not_verified));
                ivMobileVerified.setImageResource(R.mipmap.icon_non_verified);
            }

            if (userForApproval.getDistrict_name() != null)
                tvDistrict.setText(userForApproval.getDistrict_name());
            else
                tvDistrict.setText(context.getString(R.string.not_available));

            if (userForApproval.getBlock_name() != null)
                tvTehsil.setText(userForApproval.getBlock_name());
            else
                tvTehsil.setText(context.getString(R.string.not_available));

            if (userForApproval.getGp_name() != null)
                tvTown.setText(userForApproval.getGp_name());
            else
                tvTown.setText(context.getString(R.string.not_available));

            String n2 = "", n3 = "", n4 = "";

            if (userForApproval.getFirst_name() != null)
                n2 = userForApproval.getFirst_name();

            if (userForApproval.getMiddle_name() != null)
                n3 = userForApproval.getMiddle_name();

            if (userForApproval.getLast_name() != null)
                n4 = userForApproval.getLast_name();

            tvName.setText(n2 + " " + n3 + " " + n4);

            if (userForApproval.getOrganisation() != null)
                tvOrganisationName.setText(userForApproval.getOrganisation());
            else
                tvOrganisationName.setText(context.getString(R.string.not_available));

            if (userForApproval.getMobile() != null)
                tvMobile.setText(userForApproval.getMobile() + " ");
            else
                tvMobile.setText(context.getString(R.string.not_available));

            if (userForApproval.getProfile_pic() != null && !Support.isWhite_space(userForApproval.getProfile_pic())) {
                byte[] decodedString = Base64.decode(userForApproval.getProfile_pic(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                ivUserImage.setImageBitmap(decodedByte);
            }

            final Button btnApprove = (Button) dialogUserDetail.findViewById(R.id.btnApprove);
            Button btnSkip = (Button) dialogUserDetail.findViewById(R.id.btnSkip);
            Button btnReject = (Button) dialogUserDetail.findViewById(R.id.btnReject);

            btnReject.setVisibility(View.VISIBLE);
            btnApprove.setVisibility(View.VISIBLE);
            tvRejectedMsg.setVisibility(View.GONE);

            if ( (userForApproval.getIsActive() == null) || userForApproval.getIsActive().equalsIgnoreCase("null")) {
                btnApprove.setText(context.getString(R.string.approve));
                btnReject.setText(context.getString(R.string.reject));
            }
        /*else if(userForApproval.getReg_status().equalsIgnoreCase("R")) {
            btnReject.setVisibility(View.GONE);
            btnApprove.setVisibility(View.GONE);
            tvRejectedMsg.setVisibility(View.VISIBLE);
        }*/
            else {
                if (userForApproval.getIsActive().equalsIgnoreCase("true")) {
                    btnApprove.setText(context.getString(R.string.deactivate));
                } else {
                    btnApprove.setText(context.getString(R.string.activate));
                }

                btnReject.setVisibility(View.GONE);
            }

            btnApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogUserDetail.dismiss();

                    if ((userForApproval.getIsActive() == null) || userForApproval.getIsActive().equalsIgnoreCase("null")) {
                        doApproveProcess(userForApproval.getUser_id(), "C", "");
                    } else {
                        doApproveProcess(userForApproval.getUser_id(), "", userForApproval.getIsActive());
                    }
                }
            });

            btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogUserDetail.dismiss();
                    doApproveProcess(userForApproval.getUser_id(), "R", "");

                }
            });
            btnSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogUserDetail.dismiss();
                }
            });

            dialogUserDetail.show();
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.approval_error), e.getMessage(),"017-009");
        }
    }


    /* Functionality to approve the user */
    private void doApproveProcess(final String strTargetUserId,final String strApproveStatus, final String strIsActive)
    {
        if((pd==null) || ( (pd!=null) && (!pd.isShowing()) ) )
            pd = new ProgressDialog(context);

        try {
            JSONObject jsRequest = new JSONObject();
            jsRequest.put("user_id", AESHelper.getDecryptedValue(context,MySharedPref.getCurrentUser(context).getUser_id()));

            jsRequest.put("target_user_id", strTargetUserId);
            if(strApproveStatus.equalsIgnoreCase("R")){
                jsRequest.put("approve", "f");
                jsRequest.put("active", "");
            }
            else if(strApproveStatus.equalsIgnoreCase("C"))
            {
                jsRequest.put("approve", "t");
                jsRequest.put("active", "");
            }
            else if(strApproveStatus.equalsIgnoreCase(""))
            {
                jsRequest.put("approve", "");
                if(strIsActive.equalsIgnoreCase("true"))
                    jsRequest.put("active", "f");
                else
                    jsRequest.put("active", "t");
            }

            MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context, "012",true, true, "user/management/v1/userActivation", jsRequest, context.getString(R.string.approval_error),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                if (jsResponse.getBoolean("status")) {
                                    final Dialog dialog_alert = new Dialog(context);
                                    MyAlert.dialogForOk
                                            (context, R.mipmap.icon_info, context.getString(R.string.approval_info),
                                                    jsResponse.getString(context.getString(R.string.web_service_message_identifier)),
                                                    dialog_alert,
                                                    context.getString(R.string.ok),
                                                    new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            dialog_alert.dismiss();
                                                            getUserApprovalData();
                                                        }
                                                    },"017-010");
                                } else {
                                    pd.dismiss();
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.approval_error),  jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"017-011");
                                }

                            } catch (Exception e) {
                                pd.dismiss();
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.approval_error),  e.getMessage(),"017-012");
                            }
                        }
                    });
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.approval_error),  e.getMessage(),"017-013");
        }

    }

    /* Function to get state from webservice  */
    private void getState()
    {
        if((pd==null) || ( (pd!=null) && (!pd.isShowing()) ) )
            pd = new ProgressDialog(context);

        try {
            JSONObject jsRequest = new JSONObject();
            jsRequest.put("user_id", AESHelper.getDecryptedValue(context,MySharedPref.getCurrentUser(context).getUser_id()));

            strStateCodeSelected = "";
            spinnerState.setAdapter(null);

            MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context,"013", true, true, "user/management/v1/getPendingStateForApproval", jsRequest, context.getString(R.string.approval_error),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                pd.dismiss();
                                if (jsResponse.getBoolean("status")) {
                                    alState=new Gson().fromJson(jsResponse.getJSONArray("response").toString(), new TypeToken<List<State>>() {
                                    }.getType());

                                    alState.add(0, new State(context.getString(R.string.spinner_heading_state)));

                                    stateAdapter = new StateAdapter(UserApproval.this,
                                            android.R.layout.simple_spinner_item,
                                            alState);

                                    spinnerState.setAdapter(stateAdapter);
                                    strStateCodeSelected = "";

                                } else {
                                    spinnerState.setAdapter(null);
                                    strStateCodeSelected = "";
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.approval_error),  jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"017-014");
                                }


                            } catch (Exception e) {
                                spinnerState.setAdapter(null);
                                strStateCodeSelected = "";
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.approval_error),  e.getMessage(),"017-015");
                            }
                        }
                    });
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.approval_error), e.getMessage(),"017-016");
        }

    }

    /* Function to get district from webservice  */
    private void getDistrict(final String strStateSelected)
    {
        if((pd==null) || ( (pd!=null) && (!pd.isShowing()) ) )
            pd = new ProgressDialog(context);

        try {
            JSONObject jsRequest = new JSONObject();
            jsRequest.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
            jsRequest.put("state_code", strStateSelected);

            strDistrictCodeSelected = "";
            spinnerDistrict.setAdapter(null);

            MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context,"014", true, true, "user/management/v1/getPendingDistrictForApproval", jsRequest, context.getString(R.string.approval_error),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                pd.dismiss();
                                if (jsResponse.getBoolean("status")) {
                                    alDistrict=new Gson().fromJson(jsResponse.getJSONArray("response").toString(), new TypeToken<List<District>>() {
                                    }.getType());

                                    alDistrict.add(0, new District(context.getString(R.string.spinner_heading_district)));

                                    districtAdapter = new DistrictAdapter(UserApproval.this,
                                            android.R.layout.simple_spinner_item,
                                            alDistrict);

                                    spinnerDistrict.setAdapter(districtAdapter);
                                    strDistrictCodeSelected = "";

                                } else {
                                    spinnerDistrict.setAdapter(null);
                                    strDistrictCodeSelected = "";
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.approval_error),  jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"017-017");
                                }
                            } catch (Exception e) {
                                spinnerDistrict.setAdapter(null);
                                strDistrictCodeSelected = "";
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.approval_error), e.getMessage(),"017-018");
                            }
                        }
                    });
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.approval_error), e.getMessage(),"017-019");
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NetworkRegistered.unregisterNetworkChanges(context,mNetworkReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_internet_connectivity, menu);
        menuItem=menu.findItem(R.id.action_internet_status);
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

}

