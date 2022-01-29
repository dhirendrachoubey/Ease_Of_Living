package in.nic.ease_of_living.gp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.nic.ease_of_living.adapter.DevelopmentBlockAdapter;
import in.nic.ease_of_living.adapter.DistrictAdapter;
import in.nic.ease_of_living.adapter.GPAdapter;
import in.nic.ease_of_living.adapter.IdsAdapter;
import in.nic.ease_of_living.adapter.GroupAdapter;
import in.nic.ease_of_living.adapter.StateAdapter;
import in.nic.ease_of_living.broadcast.NetworkChangeReceiver;
import in.nic.ease_of_living.interfaces.Communicator;
import in.nic.ease_of_living.models.DevelopmentBlock;
import in.nic.ease_of_living.models.District;
import in.nic.ease_of_living.models.GP;
import in.nic.ease_of_living.models.Group;
import in.nic.ease_of_living.models.Ids;
import in.nic.ease_of_living.models.State;
import in.nic.ease_of_living.models.User;
import in.nic.ease_of_living.supports.IsConnected;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MyVolley;
import in.nic.ease_of_living.supports.Password;
import in.nic.ease_of_living.supports.RedAsterisk;
import in.nic.ease_of_living.utils.Common;
import in.nic.ease_of_living.utils.ImageFilePath;
import in.nic.ease_of_living.utils.InputFilters;
import in.nic.ease_of_living.utils.NetworkRegistered;
import in.nic.ease_of_living.utils.OtpHandler;
import in.nic.ease_of_living.utils.Support;
import in.nic.ease_of_living.utils.Utility;


/**
 * Created by Neha Jain on 6/15/2017.
 */
/*015-092*/
public class SignupActivity extends AppCompatActivity implements View.OnClickListener , Communicator {
    private Context context;
    private NetworkChangeReceiver mNetworkReceiver;
    private MenuItem menuItem;
    private int iYear, iMonth, iDay;
    String strSpinnerStateSelected=null,strSpinnerDistrictSelected=null, strImageSelected,
            strSpinnerStateRoleSelected="",strSpinnerDistrictRoleSelected="",
            strSpinnerDbRoleSelected="",strSpinnerGpRoleSelected="",
            strSpinnerIdProofSelected=null,strSpinnerTitleNameSelected="NA", strSpinnerRoleTypeSelected=null,
            strRbGenderSelected="1";
    private CheckBox cbShowPwd, cbShowConfirmPassword;
    private ArrayList<State> alState;
    private ArrayList<District> alDistrict;
    private ArrayList<GP> alGp;
    private ArrayList<DevelopmentBlock> alDb;
    private ArrayList<Ids> alId;
    private ArrayList<Group> alGroup;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1, SELECT_DOCUMENT = 2;
    private String strUserChoosenTask;
    private ProgressDialog pd;
    private EditText etMobileOtp;
    private TextView tvVerifyOtpApproved;


    private StateAdapter stateAdapter, stateAdapterRole;
    private DistrictAdapter districtAdapter, districtAdapterRole;
    private DevelopmentBlockAdapter dbAdapter;
    private GPAdapter gpAdapter;
    private IdsAdapter idAdapter;
    private GroupAdapter groupAdapter;
    private static File file;
    private int iPasswordMsgCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);
            context = this;
            Common.setAppHeader(context);
            //getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getString(R.string.app_name) + "</font>"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            mNetworkReceiver = new NetworkChangeReceiver();
            NetworkRegistered.registerNetworkBroadcastForNougat(context, mNetworkReceiver);

            findViews();
            //setEditTextFilters();
            setListener();
            alState = new ArrayList<>();
            alDistrict = new ArrayList<>();
            alGp = new ArrayList<>();
            alDb = new ArrayList<>();
            alId = new ArrayList<>();
            alGroup = new ArrayList<>();
            setLength();

            getState();

            changeListener();
            //setLength();
            showMandatory();

            final ArrayList<String> titleList = new ArrayList<>();
            titleList.add(context.getString(R.string.spinner_heading_title));
            titleList.add("Mr.");
            titleList.add("Mrs.");
            titleList.add("Miss");

            getId();
            getGroup();

            final ArrayAdapter<String> titleAdapter = new ArrayAdapter<String>(
                    context, android.R.layout.simple_list_item_1, titleList) {
                @Override
                public boolean isEnabled(int position) {
                    if (position == 0) {
                        return false;
                    } else {
                        return true;
                    }
                }

                @Override
                public View getDropDownView(int position, View convertView,
                                            ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if (position == 0) {
                        tv.setTextColor(Color.GRAY);
                    } else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };

            spinnerTitleName.setAdapter(titleAdapter);

            spinnerTitleName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0)
                        strSpinnerTitleNameSelected = "0";
                    else
                        strSpinnerTitleNameSelected = titleList.get(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                /*cityValue = "City";*/
                }
            });

            spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    strSpinnerDistrictSelected = "";
                    spinnerDistrict.setAdapter(null);
                    if (i == 0)
                        strSpinnerStateSelected = null;
                    else {
                        State state = stateAdapter.getItem(i);
                        strSpinnerStateSelected = state.getState_code();
                        if (strSpinnerStateSelected != null && !Support.isWhite_space(strSpinnerStateSelected)) {
                            getDistrict(strSpinnerStateSelected, "org");
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
                        strSpinnerDistrictSelected = null;
                    else {
                        District district = districtAdapter.getItem(i);
                        strSpinnerDistrictSelected = district.getDistrict_code();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spinnerIdProof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0) {
                        strSpinnerIdProofSelected = null;
                    } else {
                        Ids idValue = idAdapter.getItem(i);
                        strSpinnerIdProofSelected = idValue.getIdentity_id();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerGroupType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    strSpinnerStateRoleSelected = "";
                    strSpinnerDistrictRoleSelected = "";
                    strSpinnerDbRoleSelected = "";
                    strSpinnerGpRoleSelected = "";
                    spinnerStateRole.setAdapter(null);
                    spinnerDistrictRole.setAdapter(null);
                    spinnerDbRole.setAdapter(null);
                    spinnerGpRole.setAdapter(null);
                    if (i == 0) {
                        strSpinnerRoleTypeSelected = null;
                        rolesLayout();
                    } else {
                        Group group = groupAdapter.getItem(i);
                        strSpinnerRoleTypeSelected = group.getGroup_id();
                        checkLoc(strSpinnerRoleTypeSelected);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            /////////
            spinnerStateRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerDistrictRole.setAdapter(null);
                    spinnerDbRole.setAdapter(null);
                    spinnerGpRole.setAdapter(null);
                    if (i == 0)
                        strSpinnerStateRoleSelected = "";
                    else {
                        State state = stateAdapterRole.getItem(i);
                        strSpinnerStateRoleSelected = state.getState_code();
                        if (strSpinnerStateRoleSelected != null && !Support.isWhite_space(strSpinnerStateRoleSelected)) {
                            getDistrict(strSpinnerStateRoleSelected, "role");
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spinnerDistrictRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerDbRole.setAdapter(null);
                    spinnerGpRole.setAdapter(null);

                    if (i == 0)
                        strSpinnerDistrictRoleSelected = "";
                    else {
                        District district = districtAdapterRole.getItem(i);
                        strSpinnerDistrictRoleSelected = district.getDistrict_code();
                        if (strSpinnerDistrictRoleSelected != null && !Support.isWhite_space(strSpinnerDistrictRoleSelected)) {
                            getDevelopementblock(strSpinnerStateRoleSelected, strSpinnerDistrictRoleSelected);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            spinnerDbRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerGpRole.setAdapter(null);
                    if (i == 0)
                        strSpinnerDbRoleSelected = "";
                    else {
                        DevelopmentBlock dpVlaue = dbAdapter.getItem(i);
                        strSpinnerDbRoleSelected = dpVlaue.getBlock_code();
                        getGP(strSpinnerStateRoleSelected, strSpinnerDistrictRoleSelected, strSpinnerDbRoleSelected);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerGpRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //GP gp = gpAdapter.getItem(i);
                    //strSpinnerGpRoleSelected = gp.getCode();
                    if (i == 0)
                        strSpinnerGpRoleSelected = "";
                    else {
                        GP gp = gpAdapter.getItem(i);
                        strSpinnerGpRoleSelected = gp.getGp_code();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-001");
        }
    }

    private void setLength() {
        try {
            etEmailId.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
            etOfficialEmailId.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
            etFirstName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), InputFilters.alphaWithSpSymbolFilter1});
            etMiddleName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), InputFilters.alphaWithSpSymbolFilter1});
            etLastName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), InputFilters.alphaWithSpSymbolFilter1});
            etPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15), InputFilters.passwordFilter});
            etConfirmPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15), InputFilters.passwordFilter});
            etIdNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100), InputFilters.generalFilter});
            etOrganisationName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), InputFilters.generalFilter});
            etLandmark.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), InputFilters.generalFilter});
            etCity.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), InputFilters.generalFilter});
            etAddress.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), InputFilters.generalFilter});
            etArea.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), InputFilters.generalFilter});
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-002");
        }
    }

    /* Function to handle textbox visibility based on editText content*/
    private void changeListener() {
        try {
            Support.setChangeListener(etFirstName, tvFirstName);
            Support.setChangeListener(etMiddleName, tvMiddleName);
            Support.setChangeListener(etLastName, tvLastName);
            Support.setChangeListener(etPassword, llpasswordHint);
            Support.setChangeListener(etConfirmPassword, tvConfirmPassword);
            Support.setChangeListener(etMobileNumber, tvMobileNumber);
            Support.setChangeListener(etEmailId, tvEmailId);
            Support.setChangeListener(etMiddleName, tvMiddleName);
            Support.setChangeListener(etHouseNumber, tvHouseNumber);
            Support.setChangeListener(etLandmark, tvLandmark);
            Support.setChangeListener(etArea, tvArea);
            Support.setChangeListener(etPincode, tvPincode);
            Support.setChangeListener(etIdNumber, tvIdNumber);
            Support.setChangeListener(etOrganisationName, tvOrganisationName);
            Support.setChangeListener(etOfficialEmailId, tvOfficialEmailId);
            Support.setChangeListener(etOfficialContact, tvOfficialContact);
            Support.setChangeListener(etAddress, tvAddress);
            Support.setChangeListener(etCity, tvCity);
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-003");
        }
    }

    /* Function to handle red mandatory star on TextItems*/
    private void showMandatory() {
        try {
            // tvTitleName.setText(RedAsterisk.Spantext("Title Name *",11,12));
            RedAsterisk.setAstrikOnEditText(etFirstName, context.getString(R.string.firstname));
            RedAsterisk.setAstrikOnEditText(etDob, context.getString(R.string.dob));
            RedAsterisk.setAstrikOnEditText(etEmailId, context.getString(R.string.email_id));
            RedAsterisk.setAstrikOnEditText(etMobileNumber, context.getString(R.string.mobile_number));
            RedAsterisk.setAstrikOnEditText(etPassword, context.getString(R.string.password));
            RedAsterisk.setAstrikOnEditText(etConfirmPassword, context.getString(R.string.confirmpassword));
            RedAsterisk.setAstrikOnEditText(etIdNumber, context.getString(R.string.id_number));
            RedAsterisk.setAstrikOnTextView(tvStateRole, context.getString(R.string.state));
            RedAsterisk.setAstrikOnTextView(tvDistrictRole, context.getString(R.string.district));
            RedAsterisk.setAstrikOnTextView(tvDbRole, context.getString(R.string.db_l));
            RedAsterisk.setAstrikOnTextView(tvGpRole, context.getString(R.string.gp));
            RedAsterisk.setAstrikOnTextView(tvIdProof, context.getString(R.string.id_proof));
            RedAsterisk.setAstrikOnTextView(tvGroupType, context.getString(R.string.role_type));
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-004");
        }
    }

    private LinearLayout llPersonalDetail, llPersonalDataDetail, llBusinessProfile, llBusinessProfileDetail,llGroup,
            llGroupDetail,llUploadPicture,llStateRole,llDistrictRole,llDbRole,llGpRole, llpasswordHint;
    private ImageView ivPersonalDetail, ivBusinessProfile,ivPhoto,ivGroup;
    private TextView
            tvTitleName,
            tvFirstName,
            tvMiddleName,
            tvLastName,
            tvPassword,
            tvConfirmPassword,
            tvMobileNumber,
            tvEmailId,
            tvHouseNumber,
            tvLandmark,
            tvPincode,
            tvPhotoUpload,
            tvArea,
            tvIdNumber,
            tvOrganisationName,
            tvOfficialEmailId,
            tvOfficialContact,
            tvAddress,
            tvCity,
            tvStateRole,
            tvDistrictRole,
            tvIdProof,
            tvState,
            tvDistrict,
            tvDbRole,
            tvGpRole,
            tvGroupType,
            tvPasswordHint;
    private EditText etFirstName,
             etMiddleName,
             etLastName,
             etDob,
             etPassword,
             etConfirmPassword,
             etMobileNumber,
             etEmailId,
             etHouseNumber,
             etLandmark,
             etPincode,
             etArea,
             etIdNumber,
             etOrganisationName,
             etOfficialEmailId,
             etOfficialContact,
             etAddress,
             etCity;
    private Spinner spinnerGroupType, spinnerState, spinnerDistrict, spinnerIdProof, spinnerTitleName,
            spinnerStateRole, spinnerDistrictRole, spinnerGpRole, spinnerDbRole;

    private Button btnSubmitRegistration;
    private RadioButton rbMale,rbFemale,rbTransgender;

    /* function that handles widget assignment*/
    private void findViews() {
        try {
            // Button
            btnSubmitRegistration = (Button) findViewById(R.id.btnSubmitRegistration);

            // Radio Button
            rbMale = (RadioButton) findViewById(R.id.rbMale);
            rbFemale = (RadioButton) findViewById(R.id.rbFemale);
            rbTransgender = (RadioButton) findViewById(R.id.rbTransgender);

            // TextView
            tvGroupType = (TextView) findViewById(R.id.tvGroupType);
            tvTitleName = (TextView) findViewById(R.id.tvTitleName);
            tvFirstName = (TextView) findViewById(R.id.tvFirstName);
            tvMiddleName = (TextView) findViewById(R.id.tvMiddleName);
            tvLastName = (TextView) findViewById(R.id.tvLastName);
            tvPassword = (TextView) findViewById(R.id.tvPassword);
            tvConfirmPassword = (TextView) findViewById(R.id.tvConfirmPassword);
            tvMobileNumber = (TextView) findViewById(R.id.tvMobileNumber);
            tvEmailId = (TextView) findViewById(R.id.tvEmailId);
            tvIdNumber = (TextView) findViewById(R.id.tvIdNumber);
            tvAddress = (TextView) findViewById(R.id.tvAddress);
            tvCity = (TextView) findViewById(R.id.tvCity);
            tvHouseNumber = (TextView) findViewById(R.id.tvHouseNumber);
            tvLandmark = (TextView) findViewById(R.id.tvLandmark);
            tvArea = (TextView) findViewById(R.id.tvArea);
            tvPincode = (TextView) findViewById(R.id.tvPincode);
            tvPhotoUpload = (TextView) findViewById(R.id.tvPhotoUpload);
            tvOrganisationName = (TextView) findViewById(R.id.tvOrganisationName);
            tvOfficialContact = (TextView) findViewById(R.id.tvOfficialContact);
            tvOfficialEmailId = (TextView) findViewById(R.id.tvOfficialEmailId);
            tvStateRole = (TextView) findViewById(R.id.tvStateRole);
            tvDistrictRole = (TextView) findViewById(R.id.tvDistrictRole);
            tvState = (TextView) findViewById(R.id.tvState);
            tvDistrict = (TextView) findViewById(R.id.tvDistrict);
            tvDbRole = (TextView) findViewById(R.id.tvDbRole);
            tvGpRole = (TextView) findViewById(R.id.tvGpRole);
            tvIdProof = (TextView) findViewById(R.id.tvIdProof);
            tvPasswordHint = (TextView) findViewById(R.id.tvPasswordHint);

            // Spinner
            spinnerGroupType = (Spinner) findViewById(R.id.spinnerGroupType);
            spinnerIdProof = (Spinner) findViewById(R.id.spinnerIdProof);
            spinnerTitleName = (Spinner) findViewById(R.id.spinnerTitleName);
            spinnerState = (Spinner) findViewById(R.id.spinnerState);
            spinnerDistrict = (Spinner) findViewById(R.id.spinnerDistrict);
            spinnerStateRole = (Spinner) findViewById(R.id.spinnerStateRole);
            spinnerDistrictRole = (Spinner) findViewById(R.id.spinnerDistrictRole);
            spinnerGpRole = (Spinner) findViewById(R.id.spinnerGpRole);
            spinnerDbRole = (Spinner) findViewById(R.id.spinnerDbRole);

            // EditText
            etFirstName = (EditText) findViewById(R.id.etFirstName);
            etMiddleName = (EditText) findViewById(R.id.etMiddleName);
            etLastName = (EditText) findViewById(R.id.etLastName);
            etDob = (EditText) findViewById(R.id.etDob);
            etPassword = (EditText) findViewById(R.id.etPassword);
            etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
            etMobileNumber = (EditText) findViewById(R.id.etMobileNumber);
            etEmailId = (EditText) findViewById(R.id.etEmailId);
            etIdNumber = (EditText) findViewById(R.id.etIdNumber);
            etAddress = (EditText) findViewById(R.id.etAddress);
            etCity = (EditText) findViewById(R.id.etCity);
            etHouseNumber = (EditText) findViewById(R.id.etHouseNumber);
            etLandmark = (EditText) findViewById(R.id.etLandmark);
            etArea = (EditText) findViewById(R.id.etArea);
            etPincode = (EditText) findViewById(R.id.etPincode);
            etOrganisationName = (EditText) findViewById(R.id.etOrganisationName);
            etOfficialContact = (EditText) findViewById(R.id.etOfficialContact);
            etOfficialEmailId = (EditText) findViewById(R.id.etOfficialEmailId);

            // LinearLayout
            llStateRole = (LinearLayout) findViewById(R.id.llStateRole);
            llDistrictRole = (LinearLayout) findViewById(R.id.llDistrictRole);
            llDbRole = (LinearLayout) findViewById(R.id.llDbRole);
            llGpRole = (LinearLayout) findViewById(R.id.llGpRole);
            llUploadPicture = (LinearLayout) findViewById(R.id.llUploadPicture);
            llPersonalDetail = (LinearLayout) findViewById(R.id.llPersonalDetail);
            llGroup = (LinearLayout) findViewById(R.id.llGroup);
            llBusinessProfile = (LinearLayout) findViewById(R.id.llBusinessProfile);
            llPersonalDataDetail = (LinearLayout) findViewById(R.id.llPersonalDataDetail);
            llBusinessProfileDetail = (LinearLayout) findViewById(R.id.llBusinessProfileDetail);
            llGroupDetail = (LinearLayout) findViewById(R.id.llGroupDetail);
            llpasswordHint = (LinearLayout) findViewById(R.id.llpasswordHint);

            // ImageView
            ivPersonalDetail = (ImageView) findViewById(R.id.ivPersonalDetail);
            ivBusinessProfile = (ImageView) findViewById(R.id.ivBusinessProfile);
            ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
            ivGroup = (ImageView) findViewById(R.id.ivGroup);

            // Checkbox
            cbShowPwd = (CheckBox) findViewById(R.id.cbShowPwd);
            cbShowConfirmPassword = (CheckBox) findViewById(R.id.cbShowConfirmPassword);
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), e.getMessage(),"015-005");
        }
    }

    /* Function that set onclickListeners*/
    private void setListener() {
        try {
            llUploadPicture.setOnClickListener(this);
            llPersonalDetail.setOnClickListener(this);
            llBusinessProfile.setOnClickListener(this);
            llGroup.setOnClickListener(this);
            etDob.setOnClickListener(this);
            btnSubmitRegistration.setOnClickListener(this);
            rbFemale.setOnClickListener(this);
            rbMale.setOnClickListener(this);
            rbTransgender.setOnClickListener(this);
            cbShowPwd.setOnClickListener(this);
            cbShowConfirmPassword.setOnClickListener(this);
            etPassword.setOnClickListener(this);
            tvPasswordHint.setOnClickListener(this);
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-006");
        }
    }

    /* Function that set editText filters*/
    private void setEditTextFilters()
    {
        try {
            etFirstName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), InputFilters.alphaFilter});
            etMiddleName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), InputFilters.alphaFilter});
            etLastName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), InputFilters.alphaFilter});
            etPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15), InputFilters.noSpaceFilter});
            etConfirmPassword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15), InputFilters.noSpaceFilter});
            etOrganisationName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), InputFilters.alphaNumericFilter});
            etAddress.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), InputFilters.alphaNumericFilter});
            etCity.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), InputFilters.alphaNumericFilter});
            etHouseNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), InputFilters.alphaNumericFilter});
            etArea.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), InputFilters.alphaNumericWithSpCharFilter2});
            etLandmark.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), InputFilters.alphaNumericWithSpCharFilter2});
            etIdNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
            etEmailId.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
            etMobileNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
            etPincode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
            etOfficialContact.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
            etOfficialEmailId.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50)});
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), e.getMessage(),"015-007");
        }
    }

    /* Function that set all roles visibility to gone state*/
    private void rolesLayout() {
        try {
            tvStateRole.setVisibility(View.GONE);
            tvDistrictRole.setVisibility(View.GONE);
            tvDbRole.setVisibility(View.GONE);
            tvGpRole.setVisibility(View.GONE);
            llStateRole.setVisibility(View.GONE);
            llDistrictRole.setVisibility(View.GONE);
            llDbRole.setVisibility(View.GONE);
            llGpRole.setVisibility(View.GONE);
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), e.getMessage(),"015-008");
        }
    }

    /* Function that handles roles visibility acc to user type*/
    private void checkLoc(String strSpinnerRoleTypeSelected) {
        try {
            switch (strSpinnerRoleTypeSelected) {
                case "ADM":
                    rolesLayout();
                    break;
                case "NTA":
                    rolesLayout();
                    break;
                case "STA":
                    rolesLayout();
                    tvStateRole.setVisibility(View.VISIBLE);
                    llStateRole.setVisibility(View.VISIBLE);
                    spinnerStateRole.setAdapter(stateAdapter);
                    //getState();
                    break;
                case "DTA":
                    rolesLayout();
                    tvStateRole.setVisibility(View.VISIBLE);
                    llStateRole.setVisibility(View.VISIBLE);
                    tvDistrictRole.setVisibility(View.VISIBLE);
                    llDistrictRole.setVisibility(View.VISIBLE);
                    spinnerStateRole.setAdapter(stateAdapter);
                    //getState();
                    break;

                case "DBA":
                    rolesLayout();
                    tvStateRole.setVisibility(View.VISIBLE);
                    llStateRole.setVisibility(View.VISIBLE);
                    tvDistrictRole.setVisibility(View.VISIBLE);
                    llDistrictRole.setVisibility(View.VISIBLE);
                    tvDbRole.setVisibility(View.VISIBLE);
                    llDbRole.setVisibility(View.VISIBLE);
                    spinnerStateRole.setAdapter(stateAdapter);
                    //getState();
                    break;
                case "GPU":
                    rolesLayout();
                    tvStateRole.setVisibility(View.VISIBLE);
                    llStateRole.setVisibility(View.VISIBLE);
                    tvDistrictRole.setVisibility(View.VISIBLE);
                    llDistrictRole.setVisibility(View.VISIBLE);
                    tvDbRole.setVisibility(View.VISIBLE);
                    tvGpRole.setVisibility(View.VISIBLE);
                    llDbRole.setVisibility(View.VISIBLE);
                    llGpRole.setVisibility(View.VISIBLE);
                    spinnerStateRole.setAdapter(stateAdapter);
                    //getState();
                    break;
            }
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), e.getMessage(),"015-009");
        }
    }

    /* Implement the OnClickListener callback */
    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.llPersonalDetail:
                    if (llPersonalDataDetail.getVisibility() == View.VISIBLE) {
                        llPersonalDataDetail.setVisibility(View.GONE);
                        ivPersonalDetail.setImageResource(R.mipmap.icon_plus);
                    } else {
                        llPersonalDataDetail.setVisibility(View.VISIBLE);
                        ivPersonalDetail.setImageResource(R.mipmap.icon_minus);
                        llBusinessProfileDetail.setVisibility(View.GONE);
                        ivBusinessProfile.setImageResource(R.mipmap.icon_plus);
                        llGroupDetail.setVisibility(View.GONE);
                        ivGroup.setImageResource(R.mipmap.icon_plus);
                    }
                    break;
                case R.id.llBusinessProfile:
                    if (llBusinessProfileDetail.getVisibility() == View.VISIBLE) {
                        llBusinessProfileDetail.setVisibility(View.GONE);
                        ivBusinessProfile.setImageResource(R.mipmap.icon_plus);
                    } else {
                        llBusinessProfileDetail.setVisibility(View.VISIBLE);
                        ivBusinessProfile.setImageResource(R.mipmap.icon_minus);
                        llPersonalDataDetail.setVisibility(View.GONE);
                        ivPersonalDetail.setImageResource(R.mipmap.icon_plus);
                        llGroupDetail.setVisibility(View.GONE);
                        ivGroup.setImageResource(R.mipmap.icon_plus);
                    }
                    break;
                case R.id.llGroup:
                    if (llGroupDetail.getVisibility() == View.VISIBLE) {
                        llGroupDetail.setVisibility(View.GONE);
                        ivGroup.setImageResource(R.mipmap.icon_plus);
                    } else {
                        llGroupDetail.setVisibility(View.VISIBLE);
                        ivGroup.setImageResource(R.mipmap.icon_minus);
                        llPersonalDataDetail.setVisibility(View.GONE);
                        ivPersonalDetail.setImageResource(R.mipmap.icon_plus);
                        llBusinessProfileDetail.setVisibility(View.GONE);
                        ivBusinessProfile.setImageResource(R.mipmap.icon_plus);
                    }
                    break;
                case R.id.etDob:
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    final Calendar c = Calendar.getInstance();
                    iYear = c.get(Calendar.YEAR);
                    iMonth = c.get(Calendar.MONTH);
                    iDay = c.get(Calendar.DAY_OF_MONTH);
                    if (!etDob.getText().toString().isEmpty()) {
                        String str = etDob.getText().toString();

                        iYear = Integer.parseInt(str.split("-")[0]);
                        iMonth = Integer.parseInt(str.split("-")[1]) - 1;
                        iDay = Integer.parseInt(str.split("-")[2]);
                    }

                    DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            String strAge = Support.getAge(year, monthOfYear, dayOfMonth);
                            try {
                                if (Float.parseFloat(strAge) < 18.0)
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  context.getString(R.string.msg_age_lt_18_error),"015-010");
                                else
                                    etDob.setText(year + "-" + String.format("%02d", (monthOfYear + 1)) + "-" + String.format("%02d", dayOfMonth));
                            } catch (NumberFormatException e) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), context.getString(R.string.msg_age_lt_18_error),"015-011");
                            }
                        }
                    }, iYear, iMonth, iDay);
                    dpd.getDatePicker().setMaxDate(System.currentTimeMillis());

                    dpd.show();

                    break;
                case R.id.llUploadPicture:
                    selectImage();
                    break;
                case R.id.rbFemale:
                    strRbGenderSelected = "2";
                    break;
                case R.id.rbMale:
                    strRbGenderSelected = "1";
                    break;
                case R.id.rbTransgender:
                    strRbGenderSelected = "3";
                    break;
                case R.id.etPassword:
                    if(iPasswordMsgCount < 1)
                    {
                        MyAlert.showAlert(context,R.mipmap.icon_info, context.getString(R.string.register_info),
                                context.getString(R.string.password_msg_validation_1) + "\n \n "
                                + context.getString(R.string.password_msg_validation_2) ,"015-012");
                        iPasswordMsgCount++;
                    }
                    break;
                case R.id.cbShowPwd:
                    PasswordTransformationMethod transform = (cbShowPwd.isChecked()) ?
                            null : new PasswordTransformationMethod();
                    etPassword.setTransformationMethod(transform);
                    etPassword.setTransformationMethod(transform);
                    if (cbShowPwd.isChecked())
                        cbShowPwd.setButtonDrawable(R.mipmap.icon_eye_open);
                    else
                        cbShowPwd.setButtonDrawable(R.mipmap.icon_eye_close);

                    break;

                case R.id.cbShowConfirmPassword:
                    transform = (cbShowConfirmPassword.isChecked()) ?
                            null : new PasswordTransformationMethod();
                    etConfirmPassword.setTransformationMethod(transform);
                    etConfirmPassword.setTransformationMethod(transform);
                    if (cbShowConfirmPassword.isChecked())
                        cbShowConfirmPassword.setButtonDrawable(R.mipmap.icon_eye_open);
                    else
                        cbShowConfirmPassword.setButtonDrawable(R.mipmap.icon_eye_close);

                    break;
                case R.id.tvPasswordHint:
                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.password_hint),
                            context.getString(R.string.password_msg_validation_1) + "\n \n "
                            + context.getString(R.string.password_msg_validation_2),"015-013"
                    );
                    break;
                case R.id.btnSubmitRegistration:
                    Animation animFadesm = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                    view.startAnimation(animFadesm);
                    try {
                        if (etEmailId.getText().toString().trim().isEmpty()) {
                            etEmailId.setError(context.getString(R.string.enter_email_id));
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  context.getString(R.string.enter_email_id),"015-014");
                            etEmailId.setFocusable(true);
                            llPersonalDataDetail.setVisibility(View.VISIBLE);
                            ivPersonalDetail.setImageResource(R.mipmap.icon_minus);
                        } else if (!Support.isValidEmail(etEmailId.getText().toString().trim())) {
                            etEmailId.setError(context.getString(R.string.invalid_email_id));
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), context.getString(R.string.invalid_email_id),"015-015");
                            llPersonalDataDetail.setVisibility(View.VISIBLE);
                            etEmailId.setFocusable(true);
                        } else if (etMobileNumber.getText().toString().trim().isEmpty()) {
                            etMobileNumber.setError(context.getString(R.string.enter_mobile_no));
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  context.getString(R.string.enter_mobile_no),"015-016");
                            etMobileNumber.setFocusable(true);
                            llPersonalDataDetail.setVisibility(View.VISIBLE);
                            ivPersonalDetail.setImageResource(R.mipmap.icon_minus);
                        } else if (!Support.isValidMobileNumber(etMobileNumber.getText().toString().trim())) {
                            etMobileNumber.setError(context.getString(R.string.invalid_mob_no));
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), context.getString(R.string.invalid_mob_no),"015-017");
                            etMobileNumber.setFocusable(true);
                            llPersonalDataDetail.setVisibility(View.VISIBLE);
                        } else if (etFirstName.getText().toString().trim().isEmpty()) {
                            etFirstName.setError(context.getString(R.string.empty_first_name));
                            etFirstName.setFocusable(true);
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), context.getString(R.string.empty_first_name),"015-018");
                            llPersonalDataDetail.setVisibility(View.VISIBLE);
                            ivPersonalDetail.setImageResource(R.mipmap.icon_minus);
                        } else if (etFirstName.getText().toString().trim().length() < 2) {
                            etFirstName.setError(context.getString(R.string.min_length_first_name));
                            etFirstName.setFocusable(true);
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  context.getString(R.string.min_length_first_name),"015-019");
                            llPersonalDataDetail.setVisibility(View.VISIBLE);
                            ivPersonalDetail.setImageResource(R.mipmap.icon_minus);
                        } else if (etPassword.getText().toString().trim().isEmpty()) {
                            etPassword.setError(context.getString(R.string.enter_user_password));
                            etPassword.setFocusable(true);
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), context.getString(R.string.enter_user_password),"015-020");
                            llPersonalDataDetail.setVisibility(View.VISIBLE);
                            ivPersonalDetail.setImageResource(R.mipmap.icon_minus);
                        } else if (!Support.isValidPassword(etPassword.getText().toString().trim())) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  context.getString(R.string.password_msg_validation_1),"015-021");
                            etPassword.setError(context.getString(R.string.password_msg_validation_1));
                            etPassword.setFocusable(true);
                            llPersonalDataDetail.setVisibility(View.VISIBLE);
                            ivPersonalDetail.setImageResource(R.mipmap.icon_minus);
                        } else if (Support.isPasswordContainUserIdParts(etEmailId.getText().toString().trim().toLowerCase(), etPassword.getText().toString().trim().toLowerCase())) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), context.getString(R.string.password_msg_validation_2),"015-022");
                            etPassword.setError(context.getString(R.string.password_msg_validation_2));
                            etPassword.setFocusable(true);
                            llPersonalDataDetail.setVisibility(View.VISIBLE);
                            ivPersonalDetail.setImageResource(R.mipmap.icon_minus);
                        } else if (etConfirmPassword.getText().toString().trim().isEmpty()) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), context.getString(R.string.enter_confirm_user_password),"015-023");
                            etConfirmPassword.setError(context.getString(R.string.enter_confirm_user_password));
                            etConfirmPassword.setFocusable(true);
                            llPersonalDataDetail.setVisibility(View.VISIBLE);
                            ivPersonalDetail.setImageResource(R.mipmap.icon_minus);
                        } else if (!etConfirmPassword.getText().toString().trim().equalsIgnoreCase(etPassword.getText().toString().trim())) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  context.getString(R.string.confirm_password_not_match),"015-024");
                            etConfirmPassword.setError(context.getString(R.string.confirm_password_not_match));
                            etConfirmPassword.setFocusable(true);
                            llPersonalDataDetail.setVisibility(View.VISIBLE);
                            ivPersonalDetail.setImageResource(R.mipmap.icon_minus);
                        } else if (etDob.getText().toString().trim().isEmpty())
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  context.getString(R.string.select_dob),"015-025");
                        else if (strSpinnerIdProofSelected == null) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  context.getString(R.string.select_id_proof),"015-026");
                            llPersonalDataDetail.setVisibility(View.VISIBLE);
                            ivPersonalDetail.setImageResource(R.mipmap.icon_minus);
                        } else if (etIdNumber.getText().toString().trim().isEmpty()) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), context.getString(R.string.enter_id_number),"015-027");
                            etIdNumber.setError(context.getString(R.string.enter_id_number));
                            etIdNumber.setFocusable(true);
                            llPersonalDataDetail.setVisibility(View.VISIBLE);
                            ivPersonalDetail.setImageResource(R.mipmap.icon_minus);
                        }
                    /*else if(strImageSelected==null) {
                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), "please select image");
                        llPersonalDataDetail.setVisibility(View.VISIBLE);
                        ivGroup.setImageResource(R.mipmap.icon_minus);
                        ivPhoto.requestFocus();
                    }

                   else if(etOrganisationName.getText().toString().trim().isEmpty()) {
                        MyAlert.showAlert(context,R.mipmap.icon_error, context.getString(R.string.register_error),"Please select Organisation Name");
                        etOrganisationName.setError("Please select Organisation Name.");
                        etOrganisationName.setFocusable(true);
                        llBusinessProfileDetail.setVisibility(View.VISIBLE);
                        ivBusinessProfile.setImageResource(R.mipmap.icon_minus);
                    }
                    else if(strSpinnerStateSelected==null) {
                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), "Please select state.");
                        llBusinessProfileDetail.setVisibility(View.VISIBLE);
                        ivBusinessProfile.setImageResource(R.mipmap.icon_minus);
                    }
                    else if(strSpinnerDistrictSelected==null) {
                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), "Please select district.");
                        llBusinessProfileDetail.setVisibility(View.VISIBLE);
                        ivBusinessProfile.setImageResource(R.mipmap.icon_minus);
                    }
                    */
                        else if (strSpinnerRoleTypeSelected == null) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), context.getString(R.string.select_roles),"015-028");
                            llGroupDetail.setVisibility(View.VISIBLE);
                            ivGroup.setImageResource(R.mipmap.icon_minus);
                        } else if (llStateRole.getVisibility() == View.VISIBLE && strSpinnerStateRoleSelected.isEmpty()) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), context.getString(R.string.select_state),"015-029");
                            llGroupDetail.setVisibility(View.VISIBLE);
                            ivGroup.setImageResource(R.mipmap.icon_minus);
                        } else if (llDistrictRole.getVisibility() == View.VISIBLE && strSpinnerDistrictRoleSelected.isEmpty()) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  context.getString(R.string.select_district),"015-030");
                            llGroupDetail.setVisibility(View.VISIBLE);
                            ivGroup.setImageResource(R.mipmap.icon_minus);
                        } else if (llDbRole.getVisibility() == View.VISIBLE && strSpinnerDbRoleSelected.isEmpty()) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  context.getString(R.string.select_db),"015-031");
                            llGroupDetail.setVisibility(View.VISIBLE);
                            ivGroup.setImageResource(R.mipmap.icon_minus);
                        } else if (llGpRole.getVisibility() == View.VISIBLE && strSpinnerGpRoleSelected.isEmpty()) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), context.getString(R.string.select_gp),"015-032");
                            llGroupDetail.setVisibility(View.VISIBLE);
                            ivGroup.setImageResource(R.mipmap.icon_minus);
                        } else if (!Support.isValidpincode(etPincode.getText().toString().trim())) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  context.getString(R.string.msg_pincode_length),"015-033");
                            etPincode.setError(context.getString(R.string.msg_pincode_length));
                            llPersonalDataDetail.setVisibility(View.VISIBLE);
                            etPincode.setFocusable(true);
                            ivGroup.setImageResource(R.mipmap.icon_minus);
                        } else if (!Support.isValidofficialEmail(etOfficialEmailId.getText().toString().trim())) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), context.getString(R.string.invalid_official_email),"015-034");
                            etOfficialEmailId.setError(context.getString(R.string.invalid_official_email));
                            llBusinessProfileDetail.setVisibility(View.VISIBLE);
                            etOfficialEmailId.setFocusable(true);
                            ivBusinessProfile.setImageResource(R.mipmap.icon_minus);
                        } else if (!Support.isValidContactNumber(etOfficialContact.getText().toString().trim())) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), context.getString(R.string.invalid_official_mob),"015-035");
                            etOfficialContact.setError(context.getString(R.string.invalid_official_mob));
                            llBusinessProfileDetail.setVisibility(View.VISIBLE);
                            etOfficialContact.setFocusable(true);
                            ivBusinessProfile.setImageResource(R.mipmap.icon_minus);
                        } else if (rbMale.isChecked() && !(strSpinnerTitleNameSelected.equalsIgnoreCase("Mr."))) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), context.getString(R.string.mismatch_title_gender),"015-036");
                            llPersonalDataDetail.setVisibility(View.VISIBLE);
                            ivPersonalDetail.setImageResource(R.mipmap.icon_minus);
                        } else if (rbFemale.isChecked() && (strSpinnerTitleNameSelected.equalsIgnoreCase("Mr."))) {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  context.getString(R.string.mismatch_title_gender),"015-037");
                            llPersonalDataDetail.setVisibility(View.VISIBLE);
                            ivPersonalDetail.setImageResource(R.mipmap.icon_minus);
                        } else {
                            if (IsConnected.isInternet_connected(context, false)) {
                                doRegisteration();
                            } else {
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), context.getString(R.string.no_internet),"015-038");
                            }
                        }
                    } catch (Exception e) {
                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-039");
                    }
                    break;
            }
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-040");
        }
    }

    /* Opens alertDialog to give options to select photo from.*/
    private void selectImage() {
        try {
            final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    boolean result = Utility.checkPermission(context);

                    if (items[item].equals("Take Photo")) {
                        strUserChoosenTask = "Take Photo";
                        if (result)
                            cameraIntent();

                    } else if (items[item].equals("Choose from Library")) {
                        strUserChoosenTask = "Choose from Library";
                        if (result)
                            galleryIntent();

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-041");
        }
    }

    /* Functionality to open Gallery*/
    private void galleryIntent() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-042");
        }
    }

    /* Method handles the results we have received*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == SELECT_FILE)
                    onSelectFromGalleryResult(data);
                else if (requestCode == REQUEST_CAMERA) {
                    onCaptureImageResult(data);
                }

            }
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), e.getMessage(),"015-043");
        }
    }

    private static Bitmap bm = null;
    /* Convert the image captured using camera to bitmap*/
    private void onCaptureImageResult(Intent intent) {
        try {
            bm = (Bitmap) intent.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

            File destination = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");

            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), e.getMessage(),"015-044");
            } catch (IOException e) {
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), e.getMessage(),"015-045");
            }
            ivPhoto.setImageBitmap(bm);
            if (bm != null)
                strImageSelected = getStringImage(Common.doCompressImage(context,bm));


        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-046");
        }
    }

    /* Convert the gallery selected image to bitmap*/
    private void onSelectFromGalleryResult(Intent intent) {

        try {
            Bitmap bm = null;
            if (intent != null) {
                try {
                    bm = MediaStore.Images.Media.getBitmap(getContentResolver(), intent.getData());
                    file = new File(ImageFilePath.getPath(context, intent.getData()));

                    ivPhoto.setImageURI(intent.getData());
                    tvPhotoUpload.setText("Upload Picture");
                    if (bm != null)
                        strImageSelected = getStringImage(Common.doCompressImage(context,bm));
                } catch (Exception e) {
                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), e.getMessage(),"015-047");
                }
            }


            if (bm.getWidth() > bm.getHeight()) {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 300 / bm.getWidth() * 100, bytes);
                bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                int divider = bm.getWidth() / 300;
                if (divider == 0)
                    divider = 1;
                ivPhoto.setImageBitmap(Bitmap.createScaledBitmap(bm, bm.getWidth() / divider, bm.getHeight() / divider, true));
            } else {
                int divider = bm.getHeight() / 300;
                if (divider == 0)
                    divider = 1;
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 300 / bm.getHeight() * 100, bytes);
                bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                ivPhoto.setImageBitmap(Bitmap.createScaledBitmap(bm, bm.getWidth() / divider, bm.getHeight() / divider, false));
            }
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-048");
        }
    }


    /* receives a callback of this dialog action for particular activity from which checkPermssion() has been called*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try {
            switch (requestCode) {
                case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if (strUserChoosenTask.equals("Take Photo"))
                            cameraIntent();
                        else if (strUserChoosenTask.equals("Choose from Library"))
                            galleryIntent();
                    } else {
                        //code for deny
                    }
                    break;
            }
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), e.getMessage(),"015-049");
        }
    }


    /* Open the device camera*/
    private void cameraIntent()
   {
       try {
           Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
           startActivityForResult(intent, REQUEST_CAMERA);
       }catch (Exception e) {
           MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-050");
       }
   }

    /* Function to call web service to register*/
    private void doRegisteration()
    {
        if((pd==null) || ( (pd!=null) && (!pd.isShowing()) ) )
            pd = new ProgressDialog(context);

        JSONObject jsRequest = new JSONObject();

        try {
            String strFinalPassword = Password.sha256(Password.sha256(etPassword.getText().toString().trim()));
            jsRequest.put("user_password", strFinalPassword);
            jsRequest.put("title_name", strSpinnerTitleNameSelected);
            jsRequest.put("first_name", etFirstName.getText().toString());
            jsRequest.put("middle_name", etMiddleName.getText().toString());
            jsRequest.put("last_name", etLastName.getText().toString());
            jsRequest.put("gender", strRbGenderSelected);
            jsRequest.put("dob", etDob.getText().toString());
            jsRequest.put("mobile", etMobileNumber.getText().toString());
            jsRequest.put("email_id", etEmailId.getText().toString().trim().toLowerCase());
            jsRequest.put("identity_id", strSpinnerIdProofSelected);
            jsRequest.put("identity_number", etIdNumber.getText().toString());
            jsRequest.put("organisation", etOrganisationName.getText().toString());
            //jsRequest.put("official_email_id", etOfficialEmailId.getText().toString());
            jsRequest.put("org_phone_no", etOfficialContact.getText().toString());
            jsRequest.put("org_address_hh_area", etHouseNumber.getText().toString());
            //jsRequest.put("org_address_landmark", etArea.getText().toString());
            jsRequest.put("org_address_landmark", etLandmark.getText().toString());
            jsRequest.put("org_address_city", etCity.getText().toString());

            if (strSpinnerStateSelected != null)
                jsRequest.put("org_state_code", strSpinnerStateSelected);

            if (strSpinnerDistrictSelected != null)
                jsRequest.put("org_district_code", strSpinnerDistrictSelected);

            //jsRequest.put("org_country", "India");
            jsRequest.put("org_pincode", etPincode.getText().toString());

            jsRequest.put("state_code", strSpinnerStateRoleSelected);
            jsRequest.put("district_code", strSpinnerDistrictRoleSelected);
            jsRequest.put("block_code", strSpinnerDbRoleSelected);
            jsRequest.put("gp_code", strSpinnerGpRoleSelected);
            //jsRequest.put("created_by",etEmailId.getText().toString());
            jsRequest.put("group_id", strSpinnerRoleTypeSelected);

            if (strImageSelected != null)
                jsRequest.put("profile_pic", strImageSelected);


            MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context,"031", true, false, "registration/v1/", jsRequest, context.getString(R.string.register_error),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse)
                        {
                            try {
                                pd.dismiss();
                                if(jsResponse.getBoolean("status")) {

                                    final Dialog dialogAlert = new Dialog(context);
                                    MyAlert.dialogForCancelOk
                                            (context, R.mipmap.icon_info, context.getString(R.string.register_info),
                                                    context.getString(R.string.register_success_ask_validation),
                                                    dialogAlert,
                                                    context.getString(R.string.continues),
                                                    new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            dialogAlert.dismiss();
                                                            User u= new User();
                                                            u.setEmail_id(etEmailId.getText().toString());
                                                            u.setMobile(etMobileNumber.getText().toString());
                                                            getOTP(context,etMobileNumber.getText().toString(),"M");
                                                        }
                                                    },
                                                    context.getString(R.string.skip),
                                                    new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            pd.dismiss();
                                                            dialogAlert.dismiss();
                                                            startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                                                            finishAffinity();
                                                        }
                                                    },
                                                    new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            pd.dismiss();
                                                            dialogAlert.dismiss();
                                                        }
                                                    },"015-051");
                                }
                                else
                                {
                                    MyAlert.showAlert(context,R.mipmap.icon_error, context.getString(R.string.register_error),  jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"015-052");
                                }
                            } catch (Exception e) {
                                pd.dismiss();
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-053");
                            }
                        }
                    });
        } catch (Exception e) {
            pd.dismiss();
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-054");
        }
    }

    private void reFresh() {
        try {
            etEmailId.setText("");
            etMobileNumber.setText("");
            etFirstName.setText("");
            etMiddleName.setText("");
            etLastName.setText("");
            etDob.setText("");
            etPassword.setText("");
            etConfirmPassword.setText("");
            etHouseNumber.setText("");
            etAddress.setText("");
            etLandmark.setText("");
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-055");
        }
    }

    /* Function to convert image bitmap to String*/
    public String getStringImage(Bitmap bmp){
        String strEncodedImage = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            strEncodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            //String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-056");
        }
        return strEncodedImage;
    }

    /* Function to get Identity Type from webservice  */
    private void getId() {
        if (!IsConnected.isInternet_connected(context , false))
        {
            final Dialog dialogAlert = new Dialog(context);
            MyAlert.dialogForOk
                    (context, R.mipmap.icon_error, context.getString(R.string.register_error),
                            context.getString(R.string.no_internet),
                            dialogAlert,
                            context.getString(R.string.ok_l),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogAlert.dismiss();
                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    finishAffinity();
                                }
                            },"015-057");
        }
        else {
            if ((pd == null) || ((pd != null) && (!pd.isShowing())))
                pd = new ProgressDialog(context);
            JSONObject jsRequest = new JSONObject();
            try {
                MyVolley.callWebServiceUsingVolley(Request.Method.GET, pd, context,"032", false, false,
                        "master/v1/getIdentityList/", jsRequest, context.getString(R.string.app_error),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsResponse) {
                                try {
                                    pd.dismiss();
                                    if (jsResponse.getBoolean("status")) {
                                        alId = new Gson().fromJson(jsResponse.getJSONObject("response").getJSONArray("identityList").toString(), new TypeToken<List<Ids>>() {
                                        }.getType());
                                        alId.add(0, new Ids(context.getString(R.string.spinner_heading_id)));
                                        idAdapter = new IdsAdapter(SignupActivity.this, android.R.layout.simple_spinner_item, alId);
                                        spinnerIdProof.setAdapter(idAdapter);

                                    } else {
                                        spinnerIdProof.setAdapter(null);
                                        strSpinnerIdProofSelected = "";
                                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"015-058");
                                    }


                                } catch (Exception e) {
                                    spinnerIdProof.setAdapter(null);
                                    strSpinnerIdProofSelected = "";
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-059");
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                final Dialog dialogAlert = new Dialog(context);
                                MyAlert.dialogForOk
                                        (context, R.mipmap.icon_error, context.getString(R.string.register_error),
                                                context.getString(R.string.error_no_server_available),
                                                dialogAlert,
                                                context.getString(R.string.ok_l),
                                                new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        pd.dismiss();
                                                        dialogAlert.dismiss();
                                                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                                        finishAffinity();
                                                    }
                                                },"015-060");

                            }

                        });
            } catch (Exception e) {
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-061");
            }
        }
    }

    /* Function to get user Group from webservice  */
    private void getGroup() {
        if (!IsConnected.isInternet_connected(context , false))
        {
            final Dialog dialogAlert = new Dialog(context);
            MyAlert.dialogForOk
                    (context, R.mipmap.icon_error, context.getString(R.string.register_error),
                            context.getString(R.string.no_internet),
                            dialogAlert,
                            context.getString(R.string.ok_l),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogAlert.dismiss();
                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    finishAffinity();
                                }
                            },"015-062");
        }
        else {
            if ((pd == null) || ((pd != null) && (!pd.isShowing())))
                pd = new ProgressDialog(context);
            JSONObject jsRequest = new JSONObject();
            try {
                MyVolley.callWebServiceUsingVolley(Request.Method.GET, pd, context, "033",false, false, "master/v1/getGroupList/", jsRequest, context.getString(R.string.app_error),
                        new Response.Listener<JSONObject>() {
                            @Override

                            public void onResponse(JSONObject jsResponse) {

                                try {
                                    pd.dismiss();
                                    if (jsResponse.getBoolean("status")) {
                                        alGroup = new Gson().fromJson(jsResponse.getJSONObject("response").getJSONArray("groupList").toString(), new TypeToken<List<Group>>() {
                                        }.getType());
                                        alGroup.add(0, new Group(context.getString(R.string.spinner_heading_role)));
                                        groupAdapter = new GroupAdapter(SignupActivity.this, android.R.layout.simple_spinner_item, alGroup);
                                        spinnerGroupType.setAdapter(groupAdapter);

                                    } else {
                                        spinnerGroupType.setAdapter(null);
                                        strSpinnerRoleTypeSelected = "";
                                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"015-063");
                                    }


                                } catch (Exception e) {
                                    spinnerGroupType.setAdapter(null);
                                    strSpinnerRoleTypeSelected = "";
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), e.getMessage(),"015-064");
                                }
                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                final Dialog dialogAlert = new Dialog(context);
                                MyAlert.dialogForOk
                                        (context, R.mipmap.icon_error, context.getString(R.string.register_error),
                                                context.getString(R.string.error_no_server_available),
                                                dialogAlert,
                                                context.getString(R.string.ok_l),
                                                new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        pd.dismiss();
                                                        dialogAlert.dismiss();
                                                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                                        finishAffinity();
                                                    }
                                                },"015-065");

                            }

                        });
            } catch (Exception e) {
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-066");
            }
        }
    }

    /* Function to get state from webservice  */
    private void getState()
    {
        if (!IsConnected.isInternet_connected(context , false))
        {
            final Dialog dialogAlert = new Dialog(context);
            MyAlert.dialogForOk
                    (context, R.mipmap.icon_error, context.getString(R.string.register_error),
                            context.getString(R.string.no_internet),
                            dialogAlert,
                            context.getString(R.string.ok_l),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogAlert.dismiss();
                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    finishAffinity();
                                }
                            },"015-067");
        }
        else {
            if ((pd == null) || ((pd != null) && (!pd.isShowing())))
                pd = new ProgressDialog(context);
            JSONObject jsRequest = new JSONObject();
            try {
                MyVolley.callWebServiceUsingVolley(Request.Method.GET, pd, context,"034", false, false, "master/v1/getStateList/",
                        jsRequest, context.getString(R.string.register_error),
                        new Response.Listener<JSONObject>() {
                            @Override

                            public void onResponse(JSONObject jsResponse) {

                                try {
                                    pd.dismiss();
                                    if (jsResponse.getBoolean("status")) {
                                        alState = new Gson().fromJson(jsResponse.getJSONObject("response").getJSONArray("stateList").toString(), new TypeToken<List<State>>() {
                                        }.getType());
                                        alState.add(0, new State(context.getString(R.string.spinner_heading_state)));

                                        stateAdapterRole = new StateAdapter(SignupActivity.this,
                                                android.R.layout.simple_spinner_item,
                                                alState);
                                        stateAdapter = new StateAdapter(SignupActivity.this,
                                                android.R.layout.simple_spinner_item,
                                                alState);

                                        spinnerStateRole.setAdapter(stateAdapterRole);
                                        strSpinnerStateRoleSelected = "";
                                        spinnerState.setAdapter(stateAdapter);
                                        strSpinnerStateSelected = "";

                                    } else {
                                        spinnerStateRole.setAdapter(null);
                                        strSpinnerStateRoleSelected = "";
                                        spinnerState.setAdapter(null);
                                        strSpinnerStateSelected = "";
                                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"015-068");
                                    }

                                } catch (Exception e) {
                                    spinnerStateRole.setAdapter(null);
                                    strSpinnerStateRoleSelected = "";
                                    spinnerState.setAdapter(null);
                                    strSpinnerStateSelected = "";
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), e.getMessage(),"015-069");
                                }


                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                final Dialog dialogAlert = new Dialog(context);
                                MyAlert.dialogForOk
                                        (context, R.mipmap.icon_error, context.getString(R.string.register_error),
                                                context.getString(R.string.error_no_server_available),
                                                dialogAlert,
                                                context.getString(R.string.ok_l),
                                                new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        pd.dismiss();
                                                        dialogAlert.dismiss();
                                                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                                        finishAffinity();
                                                    }
                                                },"015-070");

                            }

                        });

            } catch (Exception e) {
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-071");
            }
        }
    }

    /* Function to get district from webservice  */
    private void getDistrict(final String strSpinnerStateSelected, final String strDistrictSpinnerType)
    {
        if((pd==null) || ( (pd!=null) && (!pd.isShowing()) ) )
            pd = new ProgressDialog(context);
        try {
            JSONObject jsRequest = new JSONObject();
            jsRequest.put("state_code",strSpinnerStateSelected);

            MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context,"035", false, false, "master/v1/getDistrictList/", jsRequest, context.getString(R.string.register_error),
                    new Response.Listener<JSONObject>(){
                        @Override

                        public void onResponse(JSONObject jsResponse) {

                                try {
                                    pd.dismiss();
                                    if(jsResponse.getBoolean("status")) {
                                        alDistrict = new Gson().fromJson(jsResponse.getJSONObject("response").getJSONArray("districtList").toString(), new TypeToken<List<District>>() {
                                        }.getType());
                                        alDistrict.add(0, new District(context.getString(R.string.spinner_heading_district)));

                                        if(strDistrictSpinnerType.equals("role"))
                                        {
                                            districtAdapterRole = new DistrictAdapter(SignupActivity.this,
                                                    android.R.layout.simple_spinner_item,
                                                    alDistrict);
                                            spinnerDistrictRole.setAdapter(districtAdapterRole);
                                            strSpinnerDistrictRoleSelected="";
                                        }
                                        else{
                                            districtAdapter = new DistrictAdapter(SignupActivity.this,
                                                    android.R.layout.simple_spinner_item,
                                                    alDistrict);
                                            spinnerDistrict.setAdapter(districtAdapter);
                                            strSpinnerDistrictSelected="";
                                        }
                                    }
                                    else {
                                        if(strDistrictSpinnerType.equals("role")) {
                                            spinnerDistrictRole.setAdapter(null);
                                            strSpinnerDistrictRoleSelected = "";
                                        }
                                        else{
                                            spinnerDistrict.setAdapter(null);
                                            strSpinnerDistrictSelected="";
                                        }
                                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"015-072");
                                    }

                                } catch (Exception e) {
                                    if(strDistrictSpinnerType.equals("role")) {
                                        spinnerDistrictRole.setAdapter(null);
                                        strSpinnerDistrictRoleSelected = "";
                                    }
                                    else{
                                        spinnerDistrict.setAdapter(null);
                                        strSpinnerDistrictSelected="";
                                    }
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), e.getMessage(),"015-073");
                                }
                        }

                    });

        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-074");
        }
    }

    /* Function to get db from webservice  */
    private void getDevelopementblock(final String strSpinnerStateSelected, final String strSpinnerDistrictSelected)
    {
        if((pd==null) || ( (pd!=null) && (!pd.isShowing()) ) )
            pd = new ProgressDialog(context);
        try {
            JSONObject jsRequest = new JSONObject();
            jsRequest.put("state_code",strSpinnerStateSelected);
            jsRequest.put("district_code",strSpinnerDistrictSelected);

            strSpinnerDbRoleSelected = "";
            spinnerDbRole.setAdapter(null);

            MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context,"036", false, false, "master/v1/getDevelopementBlockList/", jsRequest, context.getString(R.string.register_error),
                        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsResponse) {
                        try {
                            pd.dismiss();
                            if(jsResponse.getBoolean("status")) {
                                alDb = new Gson().fromJson(jsResponse.getJSONObject("response").getJSONArray("developmentBlockList").toString(), new TypeToken<List<DevelopmentBlock>>() {
                                }.getType());
                                alDb.add(0, new DevelopmentBlock(context.getString(R.string.spinner_heading_db)));

                                dbAdapter = new DevelopmentBlockAdapter(SignupActivity.this,
                                        android.R.layout.simple_spinner_item,
                                        alDb);

                                spinnerDbRole.setAdapter(dbAdapter);
                                strSpinnerDbRoleSelected="";

                            }
                            else
                            {
                                spinnerDbRole.setAdapter(null);
                                strSpinnerDbRoleSelected="";
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"015-075");
                            }


                        } catch (Exception e) {
                            spinnerDbRole.setAdapter(null);
                            strSpinnerDbRoleSelected="";
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-076");
                        }
                    }
                }
            );
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),  e.getMessage(),"015-077");
        }
    }

    /* Function to get gp from webservice  */
    private void getGP(final String strSpinnerStateSelected, final String strSpinnerDistrictSelected, final String strSpinnerDbSelected)
    {
        if((pd==null) || ( (pd!=null) && (!pd.isShowing()) ) )
            pd = new ProgressDialog(context);
        try {
            JSONObject jsRequest = new JSONObject();

            jsRequest.put("state_code",strSpinnerStateSelected);
            jsRequest.put("district_code",strSpinnerDistrictSelected);
            jsRequest.put("block_code",strSpinnerDbSelected);

            spinnerGpRole.setAdapter(null);
            strSpinnerGpRoleSelected="";

            MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context,"037", false, false,"master/v1/getGrampanchayatList/", jsRequest, context.getString(R.string.register_error),
                    new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsResponse) {
                        try {
                            pd.dismiss();
                            if(jsResponse.getBoolean("status")) {
                                alGp = new Gson().fromJson(jsResponse.getJSONObject("response").getJSONArray("grampanchayatList").toString(), new TypeToken<List<GP>>() {
                                }.getType());
                                alGp.add(0, new GP(context.getString(R.string.spinner_heading_gp)));

                                gpAdapter = new GPAdapter(SignupActivity.this,
                                        android.R.layout.simple_spinner_item,
                                        alGp);

                                spinnerGpRole.setAdapter(gpAdapter);
                            }
                            else
                            {
                                spinnerGpRole.setAdapter(null);
                                strSpinnerGpRoleSelected="";
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error),jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"015-078");
                            }


                        } catch (Exception e) {
                            strSpinnerGpRoleSelected="";
                            spinnerGpRole.setAdapter(null);
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), e.getMessage(),"015-079");
                        }
                    }
            });
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), e.getMessage(),"015-080");
        }
    }


    private void getOTP(final Context context, final String strUserId, final String strUserLoginType) {
        if((pd==null) || ( (pd!=null) && (!pd.isShowing()) ) )
            pd = new ProgressDialog(context);
        try {

            OtpHandler.doGetOTP(context, context.getString(R.string.register_error), pd,
                    strUserId, strUserLoginType,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                pd.dismiss();
                                if(jsResponse.getBoolean("status")) {

                                    final Dialog dialogAlert = new Dialog(context);
                                    MyAlert.dialogForOk
                                            (context, R.mipmap.icon_info, context.getString(R.string.otp_info),
                                                    context.getString(R.string.otp_sent_to_mobile),
                                                    dialogAlert,
                                                    context.getString(R.string.ok_l),
                                                    new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            dialogAlert.dismiss();
                                                            verificationPopup(context, strUserId, strUserLoginType);

                                                        }
                                                    },"015-081");
                                }
                                else
                                {
                                    final Dialog dialog_alert = new Dialog(context);
                                    MyAlert.dialogForOk
                                            (context, R.mipmap.icon_error, context.getString(R.string.otp_error_header),
                                                     jsResponse.getString(context.getString(R.string.web_service_message_identifier)),
                                                    dialog_alert,
                                                    context.getString(R.string.ok_l),
                                                    new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            pd.dismiss();
                                                            dialog_alert.dismiss();
                                                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                                            finishAffinity();
                                                        }
                                                    },"015-082");
                                    //MyAlert.showAlert(context, context.getString(R.string.otp_error_header),"MA-015-076 : " + jsResponse.getString(context.getString(R.string.web_service_message_identifier)) );
                                }

                            } catch (Exception e) {
                                final Dialog dialog_alert = new Dialog(context);
                                MyAlert.dialogForOk
                                        (context, R.mipmap.icon_error, context.getString(R.string.otp_error_header),
                                                 context.getString(R.string.otp_error),
                                                dialog_alert,
                                                context.getString(R.string.ok_l),
                                                new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        pd.dismiss();
                                                        dialog_alert.dismiss();
                                                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                                        finishAffinity();
                                                    }
                                                },"015-083");
                                //MyAlert.showAlert(context, context.getString(R.string.otp_error_header),"MA-015-077 : " + context.getString(R.string.otp_error));
                            }

                        }

                    });

        } catch (Exception e) {
            pd.dismiss();
            final Dialog dialog_alert = new Dialog(context);
            MyAlert.dialogForOk
                    (context, R.mipmap.icon_error, context.getString(R.string.otp_error_header),
                           e.getMessage(),
                            dialog_alert,
                            context.getString(R.string.ok_l),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    pd.dismiss();
                                    dialog_alert.dismiss();
                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    finishAffinity();
                                }
                            },"015-084");
        }

    }

    private void validateOTP(final Context context, final Dialog dialogOtp, final String strUserId, final String strUserLoginType, final String strOtp) {
        try {

            OtpHandler.doValidateOTP(context, context.getString(R.string.register_error), pd,
                    strOtp,strUserId,strUserLoginType,

                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {

                            try {
                                    if((pd!=null) && (pd.isShowing()) )
                                        pd.dismiss();
                                    if(jsResponse.getBoolean("status")) {
                                        final Dialog dialog_alert = new Dialog(context);
                                        MyAlert.dialogForOk
                                                (context, R.mipmap.icon_info, context.getString(R.string.register_info),
                                                        context.getString(R.string.register_otp_validate_success),
                                                        dialog_alert,
                                                        context.getString(R.string.ok_l),
                                                        new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                dialog_alert.dismiss();
                                                                dialogOtp.dismiss();
                                                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                                                finishAffinity();
                                                            }
                                                        },"015-085");
                                    }
                                    else
                                    {
                                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.otp_error_header), jsResponse.getString(context.getString(R.string.web_service_message_identifier)),"015-086" );
                                    }
                                } catch (Exception e) {
                                    if((pd!=null) && (pd.isShowing()) )
                                        pd.dismiss();
                                    final Dialog dialog_alert = new Dialog(context);
                                    MyAlert.dialogForOk
                                            (context, R.mipmap.icon_error, context.getString(R.string.otp_error_header),
                                                     context.getString(R.string.otp_error),
                                                    dialog_alert,
                                                    context.getString(R.string.ok_l),
                                                    new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            pd.dismiss();
                                                            dialog_alert.dismiss();
                                                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                                            finishAffinity();
                                                        }
                                                    },"015-087");

                                    //MyAlert.showAlert(context, context.getString(R.string.otp_error_header), "MA-015-079 : " + context.getString(R.string.otp_error));
                                }
                        }
                    });
        } catch (Exception e) {
            if((pd!=null) && (pd.isShowing()) )
                pd.dismiss();
            final Dialog dialog_alert = new Dialog(context);
            MyAlert.dialogForOk
                    (context, R.mipmap.icon_error, context.getString(R.string.otp_error_header),
                             e.getMessage(),
                            dialog_alert,
                            context.getString(R.string.ok_l),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    pd.dismiss();
                                    dialog_alert.dismiss();
                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    finishAffinity();
                                }
                            },"015-088");
        }
    }


    private void verificationPopup(final Context context, final String strUserId, final String strUserLoginType) {

        try {
            if((pd!=null) && (pd.isShowing()) )
                pd.dismiss();

            final Dialog dialogOtp = new Dialog(context);
            dialogOtp.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogOtp.setContentView(R.layout.layout_verify_otp_mob);
            dialogOtp.setCancelable(false);
            Window window = dialogOtp.getWindow();
            window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(R.color.transparent);
            TextView tvHeader = (TextView) dialogOtp.findViewById(R.id.tvHeader);
            TextView tvMessage = (TextView) dialogOtp.findViewById(R.id.tvMessage);
            TextView tvMobileOtp = (TextView) dialogOtp.findViewById(R.id.tvMobileOtp);
            tvVerifyOtpApproved = (TextView) dialogOtp.findViewById(R.id.tvVerifyOtpApproved);
            etMobileOtp = (EditText) dialogOtp.findViewById(R.id.etMobileOtp);
            Button btnResendOtp = (Button) dialogOtp.findViewById(R.id.btnResendOtp);
            Button btnSubmitOtp = (Button) dialogOtp.findViewById(R.id.btnSubmitOtp);
            ImageView ivClose = (ImageView) dialogOtp.findViewById(R.id.ivClose);
            //tvVerifyOtpApproved.setText(u.getMobile());
            etMobileOtp.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
            Support.setChangeListener(etMobileOtp, tvMobileOtp);

            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogOtp.dismiss();
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    finishAffinity();
                }
            });

            btnResendOtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogOtp.dismiss();
                    getOTP(context, strUserId, strUserLoginType);
                }
            });
            btnSubmitOtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (etMobileOtp.getText().toString().isEmpty()) {
                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.input_error), context.getString(R.string.please_input),"015-092");
                    } else {
                        validateOTP(context, dialogOtp, strUserId, strUserLoginType, etMobileOtp.getText().toString());
                    }
                }
            });

            dialogOtp.show();
        }catch (Exception e) {
            final Dialog dialog_alert = new Dialog(context);
            MyAlert.dialogForOk
                    (context, R.mipmap.icon_error, context.getString(R.string.register_error),
                              e.getMessage(),
                            dialog_alert,
                            context.getString(R.string.ok_l),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    pd.dismiss();
                                    dialog_alert.dismiss();
                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    finishAffinity();
                                }
                            },"015-089");

        }
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
    public void onBackPressed() {
        try {
            final Dialog dialogAlert = new Dialog(context);
            MyAlert.dialogForCancelOk
                    (context, R.mipmap.icon_warning, context.getString(R.string.register_warning),
                            context.getString(R.string.register_discard),
                            dialogAlert,
                            context.getString(R.string.yes),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogAlert.dismiss();
                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    finish();

                                }
                            },
                            context.getString(R.string.no),
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
                            },"015-090");
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.register_error), e.getMessage(),"015-091");
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

    /* Called when the system is about to start resuming a previous activity*/
    @Override
    protected void onPause() {
        super.onPause();
    }

    /* Called when the activity is no longer visible to the user*/
    @Override
    protected void onStop() {
        super.onStop();
    }

    /* The final call you receive before your activity is destroyed*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        NetworkRegistered.unregisterNetworkChanges(context,mNetworkReceiver);
    }
}