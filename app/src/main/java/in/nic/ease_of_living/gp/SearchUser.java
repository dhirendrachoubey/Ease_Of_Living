package in.nic.ease_of_living.gp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.nic.ease_of_living.adapter.UserApprovalAdapter;
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
import in.nic.ease_of_living.utils.NetworkRegistered;
import in.nic.ease_of_living.utils.Support;


/**
 * Created by PC_126 on 8/30/2017.
 */
/*013-019*/
public class SearchUser extends AppCompatActivity implements AdapterView.OnClickListener ,Communicator,AdapterView.OnItemClickListener {
    private NetworkChangeReceiver mNetworkReceiver;
    private String searchWords;
    private Context context;
    private Button btnSearch;
    private MenuItem menuItem;
    private UserApprovalAdapter userApprovalAdapter;
    private ArrayList<User> alUserForApproval=new ArrayList<>();
    private ProgressDialog pd;
    private String strUserPassw = null;
    private ImageView ivArrow;
    private LinearLayout llSearchBar;
    private ListView lvSearchUsers;
    private RadioButton rbByName, rbByEmail, rbByMobile;
    private TextView tvNameSearch, tvEmailIdSearch, tvMobileNoSearch, tvTotalSearch;
    private EditText etNameSearch, etEmailIdSearch, etMobileNoSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            context = this;
            Intent intent = getIntent();
            strUserPassw = intent.getStringExtra("user_password");
            if (Password.matchUserCredentials(context, intent.getStringExtra("user_id"), strUserPassw)) {
                setContentView(R.layout.activity_search_users);
                Common.setAppHeader(context);
                //getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getString(R.string.app_name) + "</font>"));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                findView();
                setListiner();

                mNetworkReceiver = new NetworkChangeReceiver();
                NetworkRegistered.registerNetworkBroadcastForNougat(context, mNetworkReceiver);

                Support.setChangeListener(etNameSearch, tvNameSearch);
                Support.setChangeListener(etEmailIdSearch, tvEmailIdSearch);
                Support.setChangeListener(etMobileNoSearch, tvMobileNoSearch);
            }
        }catch (Exception e) {
            pd.dismiss();
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.search_user_error), e.getMessage(),"013-001");
        }
    }


    private void findView() {
        try {

            ivArrow = (ImageView) findViewById(R.id.ivArrow);
            //Linear Layout
            llSearchBar = (LinearLayout) findViewById(R.id.llSearchBar);


            btnSearch = (Button) findViewById(R.id.btnSearch);
            lvSearchUsers = (ListView) findViewById(R.id.lvSearchUsers);

            //TextView
            tvNameSearch = (TextView) findViewById(R.id.tvNameSearch);
            tvEmailIdSearch = (TextView) findViewById(R.id.tvEmailIdSearch);
            tvMobileNoSearch = (TextView) findViewById(R.id.tvMobileNoSearch);
            tvTotalSearch = (TextView) findViewById(R.id.tvTotalSearch);

            //EditText
            etNameSearch = (EditText) findViewById(R.id.etNameSearch);
            etEmailIdSearch = (EditText) findViewById(R.id.etEmailIdSearch);
            etMobileNoSearch = (EditText) findViewById(R.id.etMobileNoSearch);

            //Radio button
            rbByName = (RadioButton) findViewById(R.id.rbByName);
            rbByEmail = (RadioButton) findViewById(R.id.rbByEmail);
            rbByMobile = (RadioButton) findViewById(R.id.rbByMobile);
        }catch (Exception e) {
            pd.dismiss();
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.search_user_error), e.getMessage(),"013-002");
        }
    }

    private void setListiner() {
        try {
            ivArrow.setOnClickListener(this);
            btnSearch.setOnClickListener(this);
            rbByName.setOnClickListener(this);
            rbByEmail.setOnClickListener(this);
            rbByMobile.setOnClickListener(this);
            lvSearchUsers.setOnItemClickListener(this);
        }catch (Exception e) {
            pd.dismiss();
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.search_user_error), e.getMessage(),"013-003");
        }
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.ivArrow:
                    Animation animFadeud = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                    view.startAnimation(animFadeud);
                    if (llSearchBar.getVisibility() == View.VISIBLE) {
                        llSearchBar.setVisibility(View.GONE);
                        ivArrow.setImageResource(R.mipmap.icon_arrow_down);
                    } else {
                        llSearchBar.setVisibility(View.VISIBLE);
                        ivArrow.setImageResource(R.mipmap.icon_arrow_up);
                    }
                    break;
                case R.id.btnSearch:
                    if (!rbByEmail.isChecked() && !rbByMobile.isChecked() && !rbByName.isChecked()) {
                        MyAlert.showAlert(context, R.mipmap.icon_warning, context.getString(R.string.search_user_warning), context.getString(R.string.msg_select_option),"013-004");
                    } else {
                        if (rbByName.isChecked()) {
                            if (etNameSearch.getText().toString().trim().isEmpty()) {
                                MyAlert.showAlert(context, R.mipmap.icon_warning, context.getString(R.string.search_user_warning), context.getString(R.string.enter_name),"013-005");
                            } else if (etNameSearch.getText().toString().trim().length() < 2) {
                                MyAlert.showAlert(context, R.mipmap.icon_warning, context.getString(R.string.search_user_warning),  context.getString(R.string.name_length_lt_3),"013-006");
                            } else {
                                searchWords = etNameSearch.getText().toString().trim();
                                doSearch(searchWords);
                            }
                        }
                        if (rbByEmail.isChecked()) {
                            if (etEmailIdSearch.getText().toString().trim().isEmpty()) {
                                MyAlert.showAlert(context, R.mipmap.icon_warning, context.getString(R.string.search_user_warning),  context.getString(R.string.enter_email_id),"013-007");
                            } else if (!Support.isValidEmail(etEmailIdSearch.getText().toString().trim())) {
                                MyAlert.showAlert(context, R.mipmap.icon_warning, context.getString(R.string.search_user_warning),  context.getString(R.string.invalid_email_id),"013-008");
                            } else {
                                searchWords = etEmailIdSearch.getText().toString().trim();
                                doSearch(searchWords);
                            }
                        }

                        if (rbByMobile.isChecked()) {
                            if (etMobileNoSearch.getText().toString().trim().isEmpty()) {
                                MyAlert.showAlert(context, R.mipmap.icon_warning, context.getString(R.string.search_user_warning),  context.getString(R.string.enter_mobile_no),"013-009");
                            } else if (!Support.isValidMobileNumber(etMobileNoSearch.getText().toString().trim())) {
                                MyAlert.showAlert(context, R.mipmap.icon_warning, context.getString(R.string.search_user_warning),  context.getString(R.string.invalid_mob_no),"013-010");
                            } else {
                                searchWords = etMobileNoSearch.getText().toString().trim();
                                doSearch(searchWords);
                            }
                        }
                    }


                    break;
                case R.id.rbByName:
                    tvNameSearch.setVisibility(View.VISIBLE);
                    etNameSearch.setVisibility(View.VISIBLE);
                    tvEmailIdSearch.setVisibility(View.GONE);
                    etEmailIdSearch.setVisibility(View.GONE);
                    tvMobileNoSearch.setVisibility(View.GONE);
                    etMobileNoSearch.setVisibility(View.GONE);
                    break;
                case R.id.rbByEmail:
                    tvNameSearch.setVisibility(View.GONE);
                    etNameSearch.setVisibility(View.GONE);
                    tvEmailIdSearch.setVisibility(View.VISIBLE);
                    etEmailIdSearch.setVisibility(View.VISIBLE);
                    tvMobileNoSearch.setVisibility(View.GONE);
                    etMobileNoSearch.setVisibility(View.GONE);
                    break;
                case R.id.rbByMobile:
                    tvNameSearch.setVisibility(View.GONE);
                    etNameSearch.setVisibility(View.GONE);
                    tvEmailIdSearch.setVisibility(View.GONE);
                    etEmailIdSearch.setVisibility(View.GONE);
                    tvMobileNoSearch.setVisibility(View.VISIBLE);
                    etMobileNoSearch.setVisibility(View.VISIBLE);
                    break;
            }
        }catch (Exception e) {
            pd.dismiss();
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.search_user_error),  e.getMessage(),"013-011");
        }
    }

    /* Function  */
    private void doSearch(final String searchkeywords)
    {
        if((pd==null) || ( (pd!=null) && (!pd.isShowing()) ) )
            pd = new ProgressDialog(context);
        try {
            JSONObject jsRequest = new JSONObject();
            jsRequest.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
            jsRequest.put("searchedValue", searchkeywords);

            if (rbByName.isChecked())
            {
                jsRequest.put("searchBy", "N");
            }

            if (rbByEmail.isChecked())
            {
                jsRequest.put("searchBy", "E");
            }

            if (rbByMobile.isChecked())
            {
                jsRequest.put("searchBy", "M");
            }


            MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context,"008", true, true, "user/management/v1/getSearchUserForApproval", jsRequest, context.getString(R.string.search_user_error),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                lvSearchUsers.setAdapter(null);
                                pd.dismiss();
                                alUserForApproval.clear();
                                if (jsResponse.getBoolean("status")) {
                                    alUserForApproval = new Gson().fromJson(jsResponse.getJSONArray("response").toString(), new TypeToken<List<User>>() {
                                    }.getType());

                                    userApprovalAdapter = new UserApprovalAdapter(context, alUserForApproval);

                                    if (alUserForApproval.size() > 0) {
                                        tvTotalSearch.setTextColor(Color.GREEN);
                                        tvTotalSearch.setText(context.getString(R.string.total_records_found) + String.valueOf(alUserForApproval.size()));
                                        lvSearchUsers.setAdapter(userApprovalAdapter);
                                        llSearchBar.setVisibility(View.GONE);
                                        ivArrow.setImageResource(R.mipmap.icon_arrow_down);
                                        lvSearchUsers.setVisibility(View.VISIBLE);
                                    } else {
                                        tvTotalSearch.setTextColor(Color.RED);
                                        tvTotalSearch.setText(context.getString(R.string.no_records_found));
                                        lvSearchUsers.setVisibility(View.GONE);
                                    }
                                } else {
                                    tvTotalSearch.setTextColor(Color.RED);
                                    tvTotalSearch.setText(context.getString(R.string.no_records_found));
                                    lvSearchUsers.setVisibility(View.GONE);
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.search_user_error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"013-012");
                                }
                            } catch (Exception e) {
                                tvTotalSearch.setTextColor(Color.RED);
                                tvTotalSearch.setText(context.getString(R.string.no_records_found));
                                lvSearchUsers.setVisibility(View.GONE);
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.search_user_error), e.getMessage(),"013-013");
                            }
                        }
                    });
        }catch (Exception e) {
            pd.dismiss();
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.search_user_error),  e.getMessage(),"013-014");
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

            MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context,"009", true, true, "user/management/v1/userActivation", jsRequest, context.getString(R.string.search_user_error),
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
                                                            doSearch(searchWords);
                                                        }
                                                    },"013-018");
                                } else {
                                    pd.dismiss();
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.search_user_error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"013-015");
                                }

                            } catch (Exception e) {
                                pd.dismiss();
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.search_user_error),  e.getMessage(),"013-016");
                            }
                        }
                    });
        }catch (Exception e) {
            pd.dismiss();
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.search_user_error),  e.getMessage(),"013-017");
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
    public void onDestroy() {
        super.onDestroy();
        NetworkRegistered.unregisterNetworkChanges(context,mNetworkReceiver);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        popupUserDetail(alUserForApproval.get(i));
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
            tvEmail.setText(userForApproval.getEmail_id());
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
                tvMobile.setText(userForApproval.getMobile());
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

            if (userForApproval.getGroup_ownership().equalsIgnoreCase("false")) {
                btnReject.setVisibility(View.GONE);
                btnApprove.setVisibility(View.GONE);
                btnSkip.setText(getString(R.string.ok));
            } else {
                btnReject.setVisibility(View.VISIBLE);
                btnApprove.setVisibility(View.VISIBLE);
                btnSkip.setText(getString(R.string.skip));

                if ((userForApproval.getIsActive() == null) || userForApproval.getIsActive().equalsIgnoreCase("null")) {
                    btnApprove.setText(context.getString(R.string.approve));
                    btnReject.setText(context.getString(R.string.reject));
                } else {
                    if (userForApproval.getIsActive().equalsIgnoreCase("true")) {
                        btnApprove.setText(context.getString(R.string.deactivate));
                    } else {
                        btnApprove.setText(context.getString(R.string.activate));
                    }

                    btnReject.setVisibility(View.GONE);
                }
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
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.search_user_error), e.getMessage(),"013-019");
        }
    }


}

