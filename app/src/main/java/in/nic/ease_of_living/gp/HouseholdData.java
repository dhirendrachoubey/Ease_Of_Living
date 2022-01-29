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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.math.BigDecimal;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.nic.ease_of_living.adapter.DistrictAdapter;
import in.nic.ease_of_living.dbo.HouseholdEolController;
import in.nic.ease_of_living.dbo.MasterCommonController;
import in.nic.ease_of_living.models.SeccHousehold;
import in.nic.ease_of_living.adapter.StateAdapter;
import in.nic.ease_of_living.adapter.VillageAdapter;
import in.nic.ease_of_living.broadcast.NetworkChangeReceiver;
import in.nic.ease_of_living.dbo.DBHelper;
import in.nic.ease_of_living.dbo.SeccHouseholdController;
import in.nic.ease_of_living.installation.Installation;
import in.nic.ease_of_living.interfaces.Communicator;
import in.nic.ease_of_living.models.District;
import in.nic.ease_of_living.models.HouseholdEol;
import in.nic.ease_of_living.models.HouseholdUpdated;
import in.nic.ease_of_living.models.MasterCommon;
import in.nic.ease_of_living.models.State;
import in.nic.ease_of_living.models.Village;
import in.nic.ease_of_living.supports.GPSTracker;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MyDateSupport;
import in.nic.ease_of_living.supports.MySharedPref;
import in.nic.ease_of_living.supports.Password;
import in.nic.ease_of_living.supports.RedAsterisk;
import in.nic.ease_of_living.utils.AESHelper;
import in.nic.ease_of_living.utils.NetworkRegistered;
import in.nic.ease_of_living.utils.PopulateMasterLists;
import in.nic.ease_of_living.utils.Support;

import static in.nic.ease_of_living.utils.PopulateMasterLists.alPensionSchemeCategory;
import static in.nic.ease_of_living.utils.PopulateMasterLists.alSkillDevelopmentSchemesCategory;


/**
 * Created by Chinki sai on 7/26/2017.
 */

//006-068
public class HouseholdData extends AppCompatActivity implements View.OnClickListener, Communicator, GoogleApiClient.ConnectionCallbacks {
    NetworkChangeReceiver mNetworkReceiver;

    ArrayList<Village> alVillage = new ArrayList<>();
    ArrayList<State> alState = new ArrayList<>();
    ArrayList<District> alDistrict = new ArrayList<>();
    ArrayList<String> alAge = new ArrayList<>();

    ArrayList<LinearLayout> alLinearLayout = new ArrayList<LinearLayout>();
    ArrayList<ImageView> alImageView = new ArrayList<ImageView>();
    ArrayList<RadioGroup> alRadioGroup = new ArrayList<RadioGroup>();
    ArrayList<RadioButton> alRadioButton = new ArrayList<RadioButton>();
    ArrayList<TextView> alTextView = new ArrayList<TextView>();
    ArrayList<EditText> alEditText = new ArrayList<EditText>();
    ArrayList<Spinner> alSpinner = new ArrayList<Spinner>();
    ArrayList<Button> alButton = new ArrayList<Button>();





    private final static int REQUEST_LOCATION = 199;
    private GoogleApiClient googleApiClient = null;

    Context context;

    ArrayList<Integer> alChkPension = new ArrayList<>();
    ArrayList<Integer> alChkUSDS = new ArrayList<>();

    String strUserPassw = null;
    LinearLayout parentLayout;

    Map<String, LinearLayout> hmLinearLayout = new HashMap<>();
    ArrayList<String> alTagNameLinearLayout=new ArrayList<>();

    HashMap<String, ImageView> hmImageView = new HashMap<>();
    ArrayList<String> alTagNameImageView=new ArrayList<>();

    HashMap<String, RadioGroup> hmRadioGroup = new HashMap<>();
    ArrayList<String> alTagNameRadioGroup=new ArrayList<>();


    HashMap<String, RadioButton> hmRadioButton = new HashMap<>();
    ArrayList<String> alTagNameRadioButton=new ArrayList<>();


    HashMap<String, TextView> hmTextView = new HashMap<>();
    ArrayList<String> alTagNameTextView=new ArrayList<>();

    HashMap<String, EditText> hmEditText = new HashMap<>();
    ArrayList<String> alTagNameEditText=new ArrayList<>();


    HashMap<String, Spinner> hmSpinner = new HashMap<>();
    ArrayList<String> alTagNameSpinner=new ArrayList<>();



    HashMap<String, CheckBox> hmChkPension = new HashMap<>();
    ArrayList<String> alTagNameChkPension=new ArrayList<>();


    HashMap<String, CheckBox> hmChkUSDS = new HashMap<>();
    ArrayList<String> alTagNameChkUSDS=new ArrayList<>();


    HashMap<String, Button> hmButton = new HashMap<>();
    ArrayList<String> alTagNameButton=new ArrayList<>();




    private Integer iRbAvailabilityOfElectricityConnection = 0,iRbAnyChild0To6OrPregnantWomanAvailable = 0,  iRbWhether_any_member_worked_with_mgnrega = 0,
            iRbWhether_any_member_undergone_training_under_skill_dev_scheme = 0,iRbWhethere_household_has_functional_toilets = 0;





    Integer  ispinnerLpgConnectionSchemeCategory= 0, ispinnerLpgApplicationStatusCategory= 0, ispinnerLedSchemeCategory= 0, ispinnerBankAcTypeCategory= 0,
             ispinnerLicTypeCategory= 0, ispinnerAccidentalCoverTypeCategory= 0, ispinnerImmunisationSourceCategory= 0, ispinnerNutritionSuppServicesSourceCategory= 0,
            ispinnerHealthServicesSourceCategory= 0, ispinnerPreschoolEduServicesSourceCategory= 0, ispinnerShgTypeCategory= 0, ispinnerHouseTypeCategory= 0,
            ispinnerHousingSchemeCategory= 0, ispinnerHousingSchemeApplicationStatusCategory= 0, ispinnerHealthSchemeCategory= 0, ispinnerOldAgePensionSourceCategory= 0,
            ispinnerWidowPensionSourceCategory= 0, ispinnerDisabledPensionSourceCategory= 0, ispinnerMobileContactTypeCategory= 0, ispinnerFoodSecuritySchemeCategory= 0,
            ispinnerMgnregaJobCardStatusCategory= 0;




    private ArrayList<HouseholdEol> alHouseholdEoleSurvey = new ArrayList<>();
    private Double dLatitude = 0.0, dLongitude = 0.0;
    private GPSTracker gps;
    private boolean bIsGpsThroughSubmit = false;


    String TAG="HouseholdData";
    int hhd_uid =0;
    View view =null;



    private LinearLayout llLocationParameters, llLocationParametersDetails, llFormDetails, llHouseholdParticulars, llHouseholdParticularsDetails,
            llAvailabilityOfLpgConnectionYes, llAvailabilityOfElectricityConnectionYes, llAnyChild0To6OrPregnantWomanAvailableYes,llWhetherBeneficiaryOfPmayGYes,
            llWhetherAnyMemberWorkedWithMgnregaYes, llWhetherAnyMemberUndergoneTrainingInSkillDevSchemeYes,
            llAvailabilityOfLpgConnectionNo,llWhetherBeneficiaryOfPmayGNo,llWhetherIssuedEHealthCardUnderAbPmjayYes,llWhetherAnyMemberWorkedWithMgnregaNo,
           l1WhetherHouseHoldHasNFSASchemeYes,llWhetherOldAgeSelectSchemeYes,llWhetherWidowAgeSelectSchemeYes,llWhetherDisabilitySelectSchemeYes;
    private ImageView ivLocationParameters, ivHouseholdParticulars;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            context = this;
            Intent intent = getIntent();
            strUserPassw = intent.getStringExtra("user_password");
            hhd_uid = intent.getIntExtra("HHUID",0);
            if (Password.matchUserCredentials(context, intent.getStringExtra("user_id"), strUserPassw)) {
                setContentView(R.layout.activity_update_household);
                getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getString(R.string.app_name) + "</font>"));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                NetworkRegistered.registerNetworkBroadcastForNougat(context, mNetworkReceiver);
                findViews();

                findViewsWithTag();
                PopulateMasterLists.populateMastersList(context);
                populateArrayList();

                setListener();
                setCheckboxListener();
                setMandatory();

                setEditTextFilters();

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



                // Spinner Adapters
                setSpinnersAdapters();

                // Spinner Listeners
                setSpinnersListeners();

                ArrayList<SeccHousehold> alSeccHousehold= SeccHouseholdController.getHhdByHhdId( DBHelper.getInstance(context, true),context, hhd_uid);
                SeccHousehold seccHousehold = alSeccHousehold.get(0);
                hmTextView.get(context.getString(R.string.tag_tv_stateData)).setText(seccHousehold.getState_name() + " (" + seccHousehold.getState_code() + ")");
                hmTextView.get(context.getString(R.string.tag_tv_districtData)).setText(seccHousehold.getDistrict_name() + " (" + seccHousehold.getDistrict_code() + ")");
                hmTextView.get(context.getString(R.string.tag_tv_blockData)).setText(seccHousehold.getBlock_name() + " (" + seccHousehold.getBlock_code() + ")");
                hmTextView.get(context.getString(R.string.tag_tv_gpData)).setText(seccHousehold.getGp_name() + " (" + seccHousehold.getGp_code() + ")");
                hmTextView.get(context.getString(R.string.tag_tv_villageData)).setText(seccHousehold.getVillage_name() + " (" + seccHousehold.getVillage_code() + ")");
                hmTextView.get(context.getString(R.string.tag_tv_ebData)).setText(seccHousehold.getEnum_block_name() + " (" + seccHousehold.getEnum_block_code() + ")");

                ArrayList<HouseholdEol> alHouseholdEol= HouseholdEolController.getDataByHhd(context,  DBHelper.getInstance(context, true),hhd_uid);
                if(alHouseholdEol.size()>0) {
                    populateHouseholdEolData(alHouseholdEol.get(0));
                    llHouseholdParticularsDetails.setVisibility(View.VISIBLE);
                    ivHouseholdParticulars.setImageResource(R.mipmap.icon_minus);
                }

            }
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.household_data_error),  e.getMessage(), "006-023");
        }
    }




    private void findViewsWithTag() {


        ///ImageView
        alTagNameImageView.add(context.getString(R.string.tag_iv_locationParameters));
        alTagNameImageView.add(context.getString(R.string.tag_iv_householdParticulars));


        for(int i=0;i<alTagNameImageView.size();i++){
            hmImageView.put(alTagNameImageView.get(i), (ImageView) parentLayout.findViewWithTag( alTagNameImageView.get(i)));
        }

        ////Button
        alTagNameButton.add(context.getString(R.string.tag_btn_saveProceedSectionA_1));
        alTagNameButton.add(context.getString(R.string.tag_btn_saveDraftGp));
        alTagNameButton.add(context.getString(R.string.tag_btn_submitGpData));


        for(int i=0;i<alTagNameButton.size();i++){
            hmButton.put(alTagNameButton.get(i), (Button) parentLayout.findViewWithTag( alTagNameButton.get(i)));
        }

        ////Edit Text

        alTagNameEditText.add(context.getString(R.string.tag_et_lpgConsumerId));
        alTagNameEditText.add(context.getString(R.string.tag_et_cylinderRefillFrequency));
        alTagNameEditText.add(context.getString(R.string.tag_et_pmayg_id));
        alTagNameEditText.add(context.getString(R.string.tag_et_family_health_card_no));
        alTagNameEditText.add(context.getString(R.string.tag_et_mgnrega_job_card_no));
        alTagNameEditText.add(context.getString(R.string.tag_et_no_days_worked_with_mgnrega_last_year));
        alTagNameEditText.add(context.getString(R.string.tag_et_house_hold_mobile_no));
        alTagNameEditText.add(context.getString(R.string.tag_et_nfsa_ration_card_no));

        for(int i=0;i<alTagNameEditText.size();i++){
            hmEditText.put(alTagNameEditText.get(i), (EditText) parentLayout.findViewWithTag( alTagNameEditText.get(i)));
        }

        //   Spinner
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_lpgConnectionAvailability));
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_lpgGasConnectionApplied));
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_availabilityOfLedBulbsUjala));
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_availabilityOfPmJanDhanAccount));
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_WhetherHhdHasInsuranceCover));
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_whetherHhdHasAccidentalInsuranceCover));
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_whetherAnyMemberOfHhdImmunised));
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_whetherNutritionSupplementServicesAvailed));
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_whetherHealthServicesAvailed));
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_whetherPreSchoolEducationServicesAvailed));
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_whetherAnyMemberOfHhdMemberOfShg));
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_householdType));
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_whetherHhdBeneficiaryOfGovthousing));
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_whetherHhdBeneficiaryOfGovthousingNo));
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_WhetherHhdRegisteredHealthScheme));
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_whetherOldAgeSelectScheme));
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_whetherWidowAgeSelectScheme));
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_whetherDisabilityAgeSelectScheme));
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_MobileNoBelogsTo));
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_WhetherHouseHoldAvailingRationNFSAScheme));
        alTagNameSpinner.add(context.getString(R.string.tag_spinner_WhetherAnyMemberWorkedWithMgnregaNo));



        for(int i=0;i<alTagNameSpinner.size();i++){
            hmSpinner.put(alTagNameSpinner.get(i), (Spinner) parentLayout.findViewWithTag( alTagNameSpinner.get(i)));
        }


        ////Checkbox


        alTagNameChkPension.add(context.getString(R.string.tag_chkOldAge));
        alTagNameChkPension.add(context.getString(R.string.tag_chkWidow));
        alTagNameChkPension.add(context.getString(R.string.tag_chkDisability));
        alTagNameChkPension.add(context.getString(R.string.tag_chkNone));


        for(int i=0;i<alTagNameChkPension.size();i++){
            hmChkPension.put(alTagNameChkPension.get(i), (CheckBox) parentLayout.findViewWithTag( alTagNameChkPension.get(i)));
        }

        alTagNameChkUSDS.add(context.getString(R.string.tag_chkDDUGKY));
        alTagNameChkUSDS.add(context.getString(R.string.tag_chkPMKVY));
        alTagNameChkUSDS.add(context.getString(R.string.tag_chkPMEGP));
        alTagNameChkUSDS.add(context.getString(R.string.tag_chkAnyOther));
        alTagNameChkUSDS.add(context.getString(R.string.tag_chkNo));

        for(int i=0;i<alTagNameChkUSDS.size();i++){
            hmChkUSDS.put(alTagNameChkUSDS.get(i), (CheckBox) parentLayout.findViewWithTag( alTagNameChkUSDS.get(i)));
        }

        //Linear Layout
        alTagNameLinearLayout.add(context.getString(R.string.tag_ll_locationParameters));
        alTagNameLinearLayout.add(context.getString(R.string.tag_ll_locationParametersDetails));
        alTagNameLinearLayout.add(context.getString(R.string.tag_ll_formDetails));
        alTagNameLinearLayout.add(context.getString(R.string.tag_ll_householdParticulars));
        alTagNameLinearLayout.add(context.getString(R.string.tag_ll_householdParticularsDetails));
        alTagNameLinearLayout.add(context.getString(R.string.tag_ll_availabilityOfLpgConnectionYes));
        alTagNameLinearLayout.add(context.getString(R.string.tag_ll_availabilityOfLpgConnectionNo));
        alTagNameLinearLayout.add(context.getString(R.string.tag_ll_availabilityOfElectricityConnectionYes));
        alTagNameLinearLayout.add(context.getString(R.string.tag_ll_AnyChild0To6OrPregnantWomanAvailableYes));
        alTagNameLinearLayout.add(context.getString(R.string.tag_ll_whetherBeneficiaryOfPmayGYes));
        alTagNameLinearLayout.add(context.getString(R.string.tag_ll_whetherBeneficiaryOfPmayGNo));
        alTagNameLinearLayout.add(context.getString(R.string.tag_ll_whether_issued_ehealth_card_under_abpmjay_yes));
        alTagNameLinearLayout.add(context.getString(R.string.tag_ll_whether_any_member_worked_with_mgnrega_yes));
        alTagNameLinearLayout.add(context.getString(R.string.tag_ll_whether_any_member_worked_with_mgnrega_no));
        alTagNameLinearLayout.add(context.getString(R.string.tag_ll_whether_any_member_undergone_training_under_skill_dev_scheme_yes));
        alTagNameLinearLayout.add(context.getString(R.string.tag_l1_Whether_house_hold_nfsa_scheme_Yes));
        alTagNameLinearLayout.add(context.getString(R.string.tag_ll_WhetherOldAgeSelectSchemeYes));
        alTagNameLinearLayout.add(context.getString(R.string.tag_ll_WhetherWidowAgeSelectSchemeYes));
        alTagNameLinearLayout.add(context.getString(R.string.tag_ll_WhetherDisabilitySelectSchemeYes));
        alTagNameLinearLayout.add(context.getString(R.string.tag_ll_submitBtn));



        for(int i=0;i<alTagNameLinearLayout.size();i++){
            hmLinearLayout.put(alTagNameLinearLayout.get(i), (LinearLayout) parentLayout.findViewWithTag( alTagNameLinearLayout.get(i)));
        }

        //   RadioGroup
        alTagNameRadioGroup.add(context.getString(R.string.tag_rg_availabilityOfElectricityConnection));
        alTagNameRadioGroup.add(context.getString(R.string.tag_rg_anyChild0To6OrPregnantWomanAvailable));
        alTagNameRadioGroup.add(context.getString(R.string.tag_rg_whether_any_member_worked_with_mgnrega));
        alTagNameRadioGroup.add(context.getString(R.string.tag_rg_whether_any_member_undergone_training_under_skill_dev_scheme));
        alTagNameRadioGroup.add(context.getString(R.string.tag_rg_Whethere_household_has_functional_toilets));



        for(int i=0;i<alTagNameRadioGroup.size();i++){
            hmRadioGroup.put(alTagNameRadioGroup.get(i), (RadioGroup) parentLayout.findViewWithTag( alTagNameRadioGroup.get(i)));
        }

        //    RadioButton

        alTagNameRadioButton.add(context.getString(R.string.tag_rb_availabilityOfElectricityConnectionYes));
        alTagNameRadioButton.add(context.getString(R.string.tag_rb_availabilityOfElectricityConnectionNo));
        alTagNameRadioButton.add(context.getString(R.string.tag_rb_anyChild0To6OrPregnantWomanAvailableYes));
        alTagNameRadioButton.add(context.getString(R.string.tag_rb_anyChild0To6OrPregnantWomanAvailableNo));
        alTagNameRadioButton.add(context.getString(R.string.tag_rb_whether_any_member_worked_with_mgnrega_yes));
        alTagNameRadioButton.add(context.getString(R.string.tag_rb_whether_any_member_worked_with_mgnrega_no));
        alTagNameRadioButton.add(context.getString(R.string.tag_rb_whether_any_member_undergone_training_under_skill_dev_scheme_yes));
        alTagNameRadioButton.add(context.getString(R.string.tag_rb_whether_any_member_undergone_training_under_skill_dev_scheme_no));
        alTagNameRadioButton.add(context.getString(R.string.tag_rb_Whethere_household_has_functional_toilets_yes));
        alTagNameRadioButton.add(context.getString(R.string.tag_rb_Whether_house_hold_has_functional_toilets_No));




        for(int i=0;i<alTagNameRadioButton.size();i++){
            hmRadioButton.put(alTagNameRadioButton.get(i), (RadioButton) parentLayout.findViewWithTag( alTagNameRadioButton.get(i)));
        }

        // TextView
        alTagNameTextView.add(context.getString(R.string.tag_tv_state));
        alTagNameTextView.add(context.getString(R.string.tag_tv_stateData));
        alTagNameTextView.add(context.getString(R.string.tag_tv_district));
        alTagNameTextView.add(context.getString(R.string.tag_tv_districtData));
        alTagNameTextView.add(context.getString(R.string.tag_tv_block));
        alTagNameTextView.add(context.getString(R.string.tag_tv_blockData));
        alTagNameTextView.add(context.getString(R.string.tag_tv_gp));
        alTagNameTextView.add(context.getString(R.string.tag_tv_gpData));
        alTagNameTextView.add(context.getString(R.string.tag_tv_village));
        alTagNameTextView.add(context.getString(R.string.tag_tv_villageData));
        alTagNameTextView.add(context.getString(R.string.tag_tv_eb));
        alTagNameTextView.add(context.getString(R.string.tag_tv_ebData));
        alTagNameTextView.add(context.getString(R.string.tag_tv_availabilityOfLpgConnection));
        alTagNameTextView.add(context.getString(R.string.tag_tv_lpgConsumerId));
        alTagNameTextView.add(context.getString(R.string.tag_tv_lpgGasConnectionApplied));
        alTagNameTextView.add(context.getString(R.string.tag_tv_cylinderRefillFrequency));
        alTagNameTextView.add(context.getString(R.string.tag_tv_availabilityOfElectricityConnection));
        alTagNameTextView.add(context.getString(R.string.tag_tv_availabilityOfLedBulbsUjala));
        alTagNameTextView.add(context.getString(R.string.tag_tv_availabilityOfPmJanDhanAccount));
        alTagNameTextView.add(context.getString(R.string.tag_tv_anyChild0To6OrPregnantWomanAvailable));
        alTagNameTextView.add(context.getString(R.string.tag_tv_householdType));
        alTagNameTextView.add(context.getString(R.string.tag_tv_pmayg_id));
        alTagNameTextView.add(context.getString(R.string.tag_tv_family_health_card_no));
        alTagNameTextView.add(context.getString(R.string.tag_tv_whether_any_member_getting_pension_nsap));
        alTagNameTextView.add(context.getString(R.string.tag_tv_whether_any_member_wotked_with_mgnrega));
        alTagNameTextView.add(context.getString(R.string.tag_tv_mgnrega_job_card_no));
        alTagNameTextView.add(context.getString(R.string.tag_tv_no_days_worked_with_mgnrega_last_year));
        alTagNameTextView.add(context.getString(R.string.tag_tv_whether_any_member_undergone_training_under_skill_dev_scheme));
        alTagNameTextView.add(context.getString(R.string.tag_tv_skill_dev_scheme_type));
        alTagNameTextView.add(context.getString(R.string.tag_tv_Whethere_household_has_functional_toilets));
        alTagNameTextView.add(context.getString(R.string.tag_tv_house_hold_mobile_no));
        alTagNameTextView.add(context.getString(R.string.tag_tv_Whether_house_hold_availing_ration_nfsa_scheme));
        alTagNameTextView.add(context.getString(R.string.tag_tv_nfsa_ration_card_no));
        alTagNameTextView.add(context.getString(R.string.tag_tv_WhetherHhdHasInsuranceCover));
        alTagNameTextView.add(context.getString(R.string.tag_tv_WhetherHhdHasAccidentalInsuranceCover));
        alTagNameTextView.add(context.getString(R.string.tag_tv_whetherAnyMemberOfHhdImmunised));
        alTagNameTextView.add(context.getString(R.string.tag_tv_whetherNutritionSupplementServicesAvailed));
        alTagNameTextView.add(context.getString(R.string.tag_tv_whetherHealthServicesAvailed));
        alTagNameTextView.add(context.getString(R.string.tag_tv_whetherPreSchoolEducationServicesAvailed));
        alTagNameTextView.add(context.getString(R.string.tag_tv_whetherAnyMemberOfHhdMemberOfShg));
        alTagNameTextView.add(context.getString(R.string.tag_tv_whetherHhdBeneficiaryOfGovthousing));
        alTagNameTextView.add(context.getString(R.string.tag_tv_whetherHhdBeneficiaryOfGovthousingNo));
        alTagNameTextView.add(context.getString(R.string.tag_tv_WhetherHhdRegisteredHealthScheme));
        alTagNameTextView.add(context.getString(R.string.tag_tv_MobileNoBelogsTo));
        alTagNameTextView.add(context.getString(R.string.tag_tv_whetherOldAgeSelectScheme));
        alTagNameTextView.add(context.getString(R.string.tag_tv_whetherWidowAgeSelectScheme));
        alTagNameTextView.add(context.getString(R.string.tag_tv_whetherDisabilityAgeSelectScheme));
        alTagNameTextView.add(context.getString(R.string.tag_tv_WhetherAnyMemberWorkedWithMgnregaNo));

        for(int i=0;i<alTagNameTextView.size();i++){
            hmTextView.put(alTagNameTextView.get(i), (TextView) parentLayout.findViewWithTag( alTagNameTextView.get(i)));
        }
    }


    private void findViews() {
        try {
            parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
            llLocationParameters = (LinearLayout) findViewById(R.id.llLocationParameters);
            llLocationParametersDetails = (LinearLayout) findViewById(R.id.llLocationParametersDetails);
            llFormDetails = (LinearLayout) findViewById(R.id.llFormDetails);
            llHouseholdParticulars = (LinearLayout) findViewById(R.id.llHouseholdParticulars);
            llHouseholdParticularsDetails = (LinearLayout) findViewById(R.id.llHouseholdParticularsDetails);
            llAvailabilityOfLpgConnectionYes = (LinearLayout) findViewById(R.id.llAvailabilityOfLpgConnectionYes);
            llAvailabilityOfLpgConnectionNo = (LinearLayout) findViewById(R.id.llAvailabilityOfLpgConnectionNo);
            llAvailabilityOfElectricityConnectionYes  = (LinearLayout) findViewById(R.id.llAvailabilityOfElectricityConnectionYes);
            llAnyChild0To6OrPregnantWomanAvailableYes = (LinearLayout) findViewById(R.id.llAnyChild0To6OrPregnantWomanAvailableYes);
            llWhetherBeneficiaryOfPmayGYes = (LinearLayout) findViewById(R.id.llWhetherBeneficiaryOfPmayGYes);
            llWhetherBeneficiaryOfPmayGNo = (LinearLayout) findViewById(R.id.llWhetherBeneficiaryOfPmayGNo);
            llWhetherIssuedEHealthCardUnderAbPmjayYes = (LinearLayout) findViewById(R.id.llWhetherIssuedEHealthCardUnderAbPmjayYes);
            llWhetherAnyMemberWorkedWithMgnregaYes = (LinearLayout) findViewById(R.id.llWhetherAnyMemberWorkedWithMgnregaYes);
            llWhetherAnyMemberWorkedWithMgnregaNo = (LinearLayout) findViewById(R.id.llWhetherAnyMemberWorkedWithMgnregaNo);
            llWhetherAnyMemberUndergoneTrainingInSkillDevSchemeYes = (LinearLayout) findViewById(R.id.llWhetherAnyMemberUndergoneTrainingInSkillDevSchemeYes);
            l1WhetherHouseHoldHasNFSASchemeYes   = (LinearLayout) findViewById(R.id.l1WhetherHouseHoldHasNFSASchemeYes);
            llWhetherOldAgeSelectSchemeYes = (LinearLayout) findViewById(R.id.llWhetherOldAgeSelectSchemeYes);
            llWhetherWidowAgeSelectSchemeYes = (LinearLayout) findViewById(R.id.llWhetherWidowAgeSelectSchemeYes);
            llWhetherDisabilitySelectSchemeYes = (LinearLayout) findViewById(R.id.llWhetherDisabilitySelectSchemeYes);
            ivLocationParameters = (ImageView) findViewById(R.id.ivLocationParameters);
            ivHouseholdParticulars = (ImageView) findViewById(R.id.ivHouseholdParticulars);


        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.household_data_error),  e.getMessage(), "006-026");
        }

    }




    private void setMandatory() {
        try {
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_availabilityOfLpgConnection)), context.getString(R.string.is_lpg_connection_available));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_lpgConsumerId)), context.getString(R.string.lpg_consumer_id));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_lpgGasConnectionApplied)), context.getString(R.string.lpg_gas_connection_applied));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_cylinderRefillFrequency)), context.getString(R.string.no_of_times_refilled_in_last_one_yr));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_availabilityOfElectricityConnection)), context.getString(R.string.is_electricity_connection_available));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_availabilityOfLedBulbsUjala)), context.getString(R.string.is_led_available_under_ujala));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_availabilityOfPmJanDhanAccount)), context.getString(R.string.is_any_member_have_jandhan_ac));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_anyChild0To6OrPregnantWomanAvailable)), context.getString(R.string.is_any_child_0_6_yrs_or_pregnant_woman_available));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_householdType)), context.getString(R.string.type_of_house_used_for_living));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_pmayg_id)), context.getString(R.string.pmayg_id));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_family_health_card_no)), context.getString(R.string.family_health_card_number));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_whether_any_member_getting_pension_nsap)), context.getString(R.string.is_any_member_getting_pension_under_nsap));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_whether_any_member_wotked_with_mgnrega)), context.getString(R.string.is_any_member_ever_worked_under_mgnrega));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_mgnrega_job_card_no)), context.getString(R.string.mgnrega_job_card_number));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_no_days_worked_with_mgnrega_last_year)), context.getString(R.string.no_of_days_worked_under_mgnrega_in_last_one_yr));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_whether_any_member_undergone_training_under_skill_dev_scheme)), context.getString(R.string.is_any_member_undergone_training_under_any_skill_dev_prog));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_skill_dev_scheme_type)), context.getString(R.string.type_of_scheme));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_Whethere_household_has_functional_toilets)), context.getString(R.string.is_hhd_available_a_functional_toilet));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_house_hold_mobile_no)), context.getString(R.string.mobile_numbers_hhd_members));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_Whether_house_hold_availing_ration_nfsa_scheme)), context.getString(R.string.is_hhd_availing_ration_under_nfsa_scheme));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_nfsa_ration_card_no)), context.getString(R.string.provide_ration_card_number));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_WhetherHhdHasInsuranceCover)), context.getString(R.string.whether_hhd_has_insurance_cover));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_WhetherHhdHasAccidentalInsuranceCover)), context.getString(R.string.whether_hhd_has_accidental_insurance_cover));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_whetherAnyMemberOfHhdImmunised)), context.getString(R.string.whether_any_member_of_hhd_immunised));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_whetherNutritionSupplementServicesAvailed)), context.getString(R.string.whether_nutrition_supplement_services_availed));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_whetherHealthServicesAvailed)), context.getString(R.string.whether_health_services_availed));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_whetherPreSchoolEducationServicesAvailed)), context.getString(R.string.whether_pre_school_education_services_availed));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_whetherAnyMemberOfHhdMemberOfShg)), context.getString(R.string.whether_any_member_of_hhd_is_member_of_shg));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_whetherHhdBeneficiaryOfGovthousing)), context.getString(R.string.whether_hhd_beneficiary_of_govthousing));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_whetherHhdBeneficiaryOfGovthousingNo)), context.getString(R.string.whether_hhd_beneficiary_of_govthousing_no));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_WhetherHhdRegisteredHealthScheme)), context.getString(R.string.whether_hhd_registered_health_scheme));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_MobileNoBelogsTo)), context.getString(R.string.mobile_no_belogs_to));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_whetherOldAgeSelectScheme)), context.getString(R.string.whether_old_age_select_scheme));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_whetherWidowAgeSelectScheme)), context.getString(R.string.whether_widow_age_select_scheme));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_whetherDisabilityAgeSelectScheme)), context.getString(R.string.whether_disability_age_select_scheme));
            RedAsterisk.setAstrikOnTextView(hmTextView.get(context.getString(R.string.tag_tv_WhetherAnyMemberWorkedWithMgnregaNo)), context.getString(R.string.whether_any_member_worked_with_mgnrega_no));


        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.household_data_error),  e.getMessage(), "006-028");
        }
    }




    private void setListener() {
        try {

            ( hmButton.get(context.getString(R.string.tag_btn_saveProceedSectionA_1))) .setOnClickListener(this);
            (hmButton.get(context.getString(R.string.tag_btn_saveDraftGp))).setOnClickListener(this);
            (hmButton.get(context.getString(R.string.tag_btn_submitGpData))) .setOnClickListener(this);

            hmLinearLayout.get(context.getString(R.string.tag_ll_locationParameters)).setOnClickListener(this);
            hmLinearLayout.get(context.getString(R.string.tag_ll_householdParticulars)).setOnClickListener(this);
            llLocationParameters.setOnClickListener(this);
            llHouseholdParticulars.setOnClickListener(this);


            for(int i=0; i<hmRadioButton.size(); i++)
            {
                hmRadioButton.get(alTagNameRadioButton.get(i)).setOnClickListener(this);
            }



            for(int i=0; i<hmChkPension.size(); i++)
            {
                hmChkPension.get(alTagNameChkPension.get(i)).setOnClickListener(this);
            }

            for(int i=0; i<hmChkUSDS.size(); i++)
            {
                hmChkUSDS.get(alTagNameChkUSDS.get(i)).setOnClickListener(this);
            }


        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.household_data_error),  e.getMessage(), "006-029");
        }
    }


    private void setSpinnersAdapters()
    {
        try {
            hmSpinner.get(context.getString(R.string.tag_spinner_lpgConnectionAvailability)) .setAdapter(PopulateMasterLists.adapterLpgConnectionSchemeCategory);hmSpinner.get(context.getString(R.string.tag_spinner_lpgGasConnectionApplied)) .setAdapter(PopulateMasterLists.adapterLpgApplicationStatusCategory);
            hmSpinner.get(context.getString(R.string.tag_spinner_availabilityOfLedBulbsUjala)) .setAdapter(PopulateMasterLists.adapterLedSchemeCategory);
            hmSpinner.get(context.getString(R.string.tag_spinner_availabilityOfPmJanDhanAccount)) .setAdapter(PopulateMasterLists.adapterBankAcTypeCategory);
            hmSpinner.get(context.getString(R.string.tag_spinner_WhetherHhdHasInsuranceCover)) .setAdapter(PopulateMasterLists.adapterLicTypeCategory);
            hmSpinner.get(context.getString(R.string.tag_spinner_whetherHhdHasAccidentalInsuranceCover)) .setAdapter(PopulateMasterLists.adapterAccidentalCoverTypeCategory);
            hmSpinner.get(context.getString(R.string.tag_spinner_whetherAnyMemberOfHhdImmunised)) .setAdapter(PopulateMasterLists.adapterImmunisationSourceCategory);
            hmSpinner.get(context.getString(R.string.tag_spinner_whetherNutritionSupplementServicesAvailed)) .setAdapter(PopulateMasterLists.adapterNutritionSuppServicesSourceCategory);
            hmSpinner.get(context.getString(R.string.tag_spinner_whetherHealthServicesAvailed)) .setAdapter(PopulateMasterLists.adapterHealthServicesSourceCategory);
            hmSpinner.get(context.getString(R.string.tag_spinner_whetherPreSchoolEducationServicesAvailed)) .setAdapter(PopulateMasterLists.adapterPreschoolEduServicesSourceCategory);
            hmSpinner.get(context.getString(R.string.tag_spinner_whetherAnyMemberOfHhdMemberOfShg)) .setAdapter(PopulateMasterLists.adapterShgTypeCategory);
            hmSpinner.get(context.getString(R.string.tag_spinner_householdType)) .setAdapter(PopulateMasterLists.adapterHouseTypeCategory);
            hmSpinner.get(context.getString(R.string.tag_spinner_whetherHhdBeneficiaryOfGovthousing)) .setAdapter(PopulateMasterLists.adapterHousingSchemeCategory);
            hmSpinner.get(context.getString(R.string.tag_spinner_whetherHhdBeneficiaryOfGovthousingNo)) .setAdapter(PopulateMasterLists.adapterHousingSchemeApplicationStatusCategory);
            hmSpinner.get(context.getString(R.string.tag_spinner_WhetherHhdRegisteredHealthScheme)) .setAdapter(PopulateMasterLists.adapterHealthSchemeCategory);
            hmSpinner.get(context.getString(R.string.tag_spinner_whetherOldAgeSelectScheme)) .setAdapter(PopulateMasterLists.adapterOldAgePensionSourceCategory);
            hmSpinner.get(context.getString(R.string.tag_spinner_whetherWidowAgeSelectScheme)) .setAdapter(PopulateMasterLists.adapterWidowPensionSourceCategory);
            hmSpinner.get(context.getString(R.string.tag_spinner_whetherDisabilityAgeSelectScheme)) .setAdapter(PopulateMasterLists.adapterDisabledPensionSourceCategory);
            hmSpinner.get(context.getString(R.string.tag_spinner_MobileNoBelogsTo)) .setAdapter(PopulateMasterLists.adapterMobileContactTypeCategory);
            hmSpinner.get(context.getString(R.string.tag_spinner_WhetherHouseHoldAvailingRationNFSAScheme)) .setAdapter(PopulateMasterLists.adapterFoodSecuritySchemeCategory);
            hmSpinner.get(context.getString(R.string.tag_spinner_WhetherAnyMemberWorkedWithMgnregaNo)) .setAdapter(PopulateMasterLists.adapterMgnregaJobCardStatusCategory);

        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.add_gp_error), e.getMessage(),"005-007");
        }
    }

    private void setSpinnersListeners()
    {

        try {
             hmSpinner.get(context.getString(R.string.tag_spinner_lpgConnectionAvailability)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterLpgConnectionSchemeCategory.getItem(i);
                    if (i == 0) {
                        ispinnerLpgConnectionSchemeCategory = 0;
                        llAvailabilityOfLpgConnectionYes.setVisibility(View.GONE);
                        llAvailabilityOfLpgConnectionNo.setVisibility(View.GONE);

                    }
                    else {
                        ispinnerLpgConnectionSchemeCategory = obj_masterCommon.getType_code();
                        if(ispinnerLpgConnectionSchemeCategory==1 || ispinnerLpgConnectionSchemeCategory==2)
                        {
                            llAvailabilityOfLpgConnectionYes.setVisibility(View.VISIBLE);
                            llAvailabilityOfLpgConnectionNo.setVisibility(View.GONE);

                        }
                         else
                        {
                            llAvailabilityOfLpgConnectionYes.setVisibility(View.GONE);
                            llAvailabilityOfLpgConnectionNo.setVisibility(View.VISIBLE);


                        }

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            hmSpinner.get(context.getString(R.string.tag_spinner_lpgGasConnectionApplied)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterLpgApplicationStatusCategory.getItem(i);
                    if (i == 0)
                        ispinnerLpgApplicationStatusCategory = 0;
                    else
                        ispinnerLpgApplicationStatusCategory = obj_masterCommon.getType_code();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            hmSpinner.get(context.getString(R.string.tag_spinner_availabilityOfLedBulbsUjala)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterLedSchemeCategory.getItem(i);
                    if (i == 0)
                        ispinnerLedSchemeCategory = 0;
                    else
                        ispinnerLedSchemeCategory  = obj_masterCommon.getType_code();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            hmSpinner.get(context.getString(R.string.tag_spinner_availabilityOfPmJanDhanAccount)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterBankAcTypeCategory.getItem(i);
                    if (i == 0)
                        ispinnerBankAcTypeCategory = 0;
                    else
                        ispinnerBankAcTypeCategory  = obj_masterCommon.getType_code();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            hmSpinner.get(context.getString(R.string.tag_spinner_WhetherHhdHasInsuranceCover)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterLicTypeCategory.getItem(i);
                    if (i == 0)
                        ispinnerLicTypeCategory = 0;
                    else
                        ispinnerLicTypeCategory  = obj_masterCommon.getType_code();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            hmSpinner.get(context.getString(R.string.tag_spinner_whetherHhdHasAccidentalInsuranceCover)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterAccidentalCoverTypeCategory.getItem(i);
                    if (i == 0)
                        ispinnerAccidentalCoverTypeCategory = 0;
                    else
                        ispinnerAccidentalCoverTypeCategory  = obj_masterCommon.getType_code();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            hmSpinner.get(context.getString(R.string.tag_spinner_whetherAnyMemberOfHhdImmunised)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterImmunisationSourceCategory.getItem(i);
                    if (i == 0)
                        ispinnerImmunisationSourceCategory = 0;
                    else
                        ispinnerImmunisationSourceCategory  = obj_masterCommon.getType_code();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            hmSpinner.get(context.getString(R.string.tag_spinner_whetherNutritionSupplementServicesAvailed)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterNutritionSuppServicesSourceCategory.getItem(i);
                    if (i == 0)
                        ispinnerNutritionSuppServicesSourceCategory = 0;
                    else
                        ispinnerNutritionSuppServicesSourceCategory  = obj_masterCommon.getType_code();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            hmSpinner.get(context.getString(R.string.tag_spinner_whetherHealthServicesAvailed)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterHealthServicesSourceCategory.getItem(i);
                    if (i == 0)
                        ispinnerHealthServicesSourceCategory = 0;
                    else
                        ispinnerHealthServicesSourceCategory  = obj_masterCommon.getType_code();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            hmSpinner.get(context.getString(R.string.tag_spinner_whetherPreSchoolEducationServicesAvailed)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterPreschoolEduServicesSourceCategory.getItem(i);
                    if (i == 0)
                        ispinnerPreschoolEduServicesSourceCategory= 0;
                    else
                        ispinnerPreschoolEduServicesSourceCategory  = obj_masterCommon.getType_code();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            hmSpinner.get(context.getString(R.string.tag_spinner_whetherAnyMemberOfHhdMemberOfShg)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterShgTypeCategory.getItem(i);
                    if (i == 0)
                        ispinnerShgTypeCategory= 0;
                    else
                        ispinnerShgTypeCategory  = obj_masterCommon.getType_code();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            hmSpinner.get(context.getString(R.string.tag_spinner_householdType)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterHouseTypeCategory.getItem(i);
                    if (i == 0)
                        ispinnerHouseTypeCategory= 0;
                    else
                        ispinnerHouseTypeCategory  = obj_masterCommon.getType_code();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            hmSpinner.get(context.getString(R.string.tag_spinner_whetherHhdBeneficiaryOfGovthousing)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterHousingSchemeCategory.getItem(i);
                    if (i == 0) {
                        ispinnerHousingSchemeCategory = 0;
                        llWhetherBeneficiaryOfPmayGYes.setVisibility(View.GONE);
                        llWhetherBeneficiaryOfPmayGNo.setVisibility(View.GONE);
                    }
                    else  {
                        ispinnerHousingSchemeCategory = obj_masterCommon.getType_code();

                        if( ispinnerHousingSchemeCategory==1 ){
                            llWhetherBeneficiaryOfPmayGYes.setVisibility(View.VISIBLE);
                            llWhetherBeneficiaryOfPmayGNo.setVisibility(View.GONE);
                        }
                        else  if( ispinnerHousingSchemeCategory==4 ){
                            llWhetherBeneficiaryOfPmayGYes.setVisibility(View.GONE);
                            llWhetherBeneficiaryOfPmayGNo.setVisibility(View.VISIBLE);
                        }
                        else {
                            llWhetherBeneficiaryOfPmayGYes.setVisibility(View.GONE);
                            llWhetherBeneficiaryOfPmayGNo.setVisibility(View.GONE);
                        }


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            hmSpinner.get(context.getString(R.string.tag_spinner_whetherHhdBeneficiaryOfGovthousingNo)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterHousingSchemeApplicationStatusCategory.getItem(i);
                    if (i == 0)
                        ispinnerHousingSchemeApplicationStatusCategory= 0;
                    else
                        ispinnerHousingSchemeApplicationStatusCategory  = obj_masterCommon.getType_code();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            hmSpinner.get(context.getString(R.string.tag_spinner_WhetherHhdRegisteredHealthScheme)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterHealthSchemeCategory.getItem(i);
                    if (i == 0) {
                        ispinnerHealthSchemeCategory = 0;
                        llWhetherIssuedEHealthCardUnderAbPmjayYes.setVisibility(View.GONE);
                    }
                    else {
                        ispinnerHealthSchemeCategory = obj_masterCommon.getType_code();
                        if(ispinnerHealthSchemeCategory==1)
                            llWhetherIssuedEHealthCardUnderAbPmjayYes.setVisibility(View.VISIBLE);
                        else
                            llWhetherIssuedEHealthCardUnderAbPmjayYes.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            hmSpinner.get(context.getString(R.string.tag_spinner_whetherOldAgeSelectScheme)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterOldAgePensionSourceCategory.getItem(i);
                    if (i == 0)
                        ispinnerOldAgePensionSourceCategory= 0;
                    else
                        ispinnerOldAgePensionSourceCategory  = obj_masterCommon.getType_code();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            hmSpinner.get(context.getString(R.string.tag_spinner_whetherWidowAgeSelectScheme)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterWidowPensionSourceCategory.getItem(i);
                    if (i == 0)
                        ispinnerWidowPensionSourceCategory= 0;
                    else
                        ispinnerWidowPensionSourceCategory  = obj_masterCommon.getType_code();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            hmSpinner.get(context.getString(R.string.tag_spinner_whetherDisabilityAgeSelectScheme)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterDisabledPensionSourceCategory.getItem(i);
                    if (i == 0)
                        ispinnerDisabledPensionSourceCategory= 0;
                    else
                        ispinnerDisabledPensionSourceCategory  = obj_masterCommon.getType_code();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            hmSpinner.get(context.getString(R.string.tag_spinner_MobileNoBelogsTo)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterMobileContactTypeCategory.getItem(i);
                    if (i == 0)
                        ispinnerMobileContactTypeCategory= 0;
                    else
                        ispinnerMobileContactTypeCategory  = obj_masterCommon.getType_code();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });



            hmSpinner.get(context.getString(R.string.tag_spinner_WhetherHouseHoldAvailingRationNFSAScheme)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterFoodSecuritySchemeCategory.getItem(i);
                    if (i == 0) {
                        ispinnerFoodSecuritySchemeCategory = 0;
                        l1WhetherHouseHoldHasNFSASchemeYes.setVisibility(View.GONE);
                    }
                    else {
                        ispinnerFoodSecuritySchemeCategory = obj_masterCommon.getType_code();

                         if(ispinnerFoodSecuritySchemeCategory==1 ||  ispinnerFoodSecuritySchemeCategory==2)
                             l1WhetherHouseHoldHasNFSASchemeYes.setVisibility(View.VISIBLE);
                         else
                             l1WhetherHouseHoldHasNFSASchemeYes.setVisibility(View.GONE);



                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            hmSpinner.get(context.getString(R.string.tag_spinner_WhetherAnyMemberWorkedWithMgnregaNo)) .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MasterCommon obj_masterCommon = PopulateMasterLists.adapterMgnregaJobCardStatusCategory.getItem(i);
                    if (i == 0)
                        ispinnerMgnregaJobCardStatusCategory = 0;

                    else
                        ispinnerMgnregaJobCardStatusCategory  = obj_masterCommon.getType_code();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.household_data_error),  e.getMessage(), "006-030");
        }
    }

    private void setCheckboxListener() {



        hmChkPension.get(context.getString(R.string.tag_chkOldAge)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                    llWhetherOldAgeSelectSchemeYes.setVisibility(View.VISIBLE);
                 else
                    llWhetherOldAgeSelectSchemeYes.setVisibility(View.GONE);

            }
        });
        hmChkPension.get(context.getString(R.string.tag_chkWidow)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                    llWhetherWidowAgeSelectSchemeYes.setVisibility(View.VISIBLE);
                else
                    llWhetherWidowAgeSelectSchemeYes.setVisibility(View.GONE);

            }
        });

        hmChkPension.get(context.getString(R.string.tag_chkDisability)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked)
                    llWhetherDisabilitySelectSchemeYes.setVisibility(View.VISIBLE);
                else
                    llWhetherDisabilitySelectSchemeYes.setVisibility(View.GONE);

            }
        });

        hmChkPension.get(context.getString(R.string.tag_chkNone)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    hmChkPension.get(context.getString(R.string.tag_chkOldAge)).setChecked(false);
                    hmChkPension.get(context.getString(R.string.tag_chkWidow)).setChecked(false);
                    hmChkPension.get(context.getString(R.string.tag_chkDisability)).setChecked(false);

                    llWhetherOldAgeSelectSchemeYes.setVisibility(View.GONE);
                    llWhetherWidowAgeSelectSchemeYes.setVisibility(View.GONE);
                    llWhetherDisabilitySelectSchemeYes.setVisibility(View.GONE);
                }

            }
        });

    }
    private void showHideLayout(String strValue, final TextView tv, final LinearLayout ll)
    {
        try {
            if (strValue.trim().equalsIgnoreCase(context.getString(R.string.none))) {
                tv.setVisibility(View.VISIBLE);
                ll.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.GONE);
                ll.setVisibility(View.GONE);
            }
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.add_gp_error), e.getMessage(),"005-012");
        }
    }

    /* Functionality to apply filters on editText*/
    private void setEditTextFilters() {
      /*  etAddressLine1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(60), InputFilters.alphaNumericWithSpCharFilter2});
        etAddressLine2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(60),InputFilters.alphaNumericWithSpCharFilter2});
        etAddressLine3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(60),InputFilters.alphaNumericWithSpCharFilter2});
        etPincode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6),InputFilters.digitsFilter});
        etNo_of_dweeling_room.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5),InputFilters.digitsFilter});
        etRation_card_no.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
        etHousehold_contact.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10),InputFilters.digitsFilter});
        etOther_irrigated.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8), InputFilters.decimalDigitFilter});
        etTotal_unirrigatedland.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8),InputFilters.decimalDigitFilter});
        etWith_assured_irrigation.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8),InputFilters.decimalDigitFilter});
*/    }

    private void populateHouseholdEolData(HouseholdEol hheol) {

/*
        try {

            if (hheol.getIs_lpg_connection_available() == null){
                hmRadioGroup.get(context.getString(R.string.tag_rg_availabilityOfLpgConnection)).clearCheck();
                llAvailabilityOfLpgConnectionYes.setVisibility(View.GONE);
                iRbAvailabilityOfLpgConnection = 0;
            } else if (hheol.getIs_lpg_connection_available()) {
                hmRadioButton.get(context.getString(R.string.tag_rb_availabilityOfLpgConnectionYes)).setChecked(true);
                llAvailabilityOfLpgConnectionYes.setVisibility(View.VISIBLE);
                iRbAvailabilityOfLpgConnection = 1;
            } else if (!hheol.getIs_lpg_connection_available()){
                hmRadioButton.get(context.getString(R.string.tag_rb_availabilityOfLpgConnectionNo)).setChecked(true);
                llAvailabilityOfLpgConnectionYes.setVisibility(View.GONE);
                iRbAvailabilityOfLpgConnection = 2;
            }


            if ((hheol.getLpg_consumer_id() != null) && (!hheol.getLpg_consumer_id().trim().isEmpty()))
                hmEditText.get(context.getString(R.string.tag_et_lpgConsumerId)).setText(hheol.getLpg_consumer_id());


            if ((hheol.getNo_of_times_refilled_in_last_one_yr() != null) && (hheol.getNo_of_times_refilled_in_last_one_yr()!=0))
                hmEditText.get(context.getString(R.string.tag_et_cylinderRefillFrequency)).setText(String.valueOf(hheol.getNo_of_times_refilled_in_last_one_yr()));



            if (hheol.getIs_electricity_connection_available() == null){
                hmRadioGroup.get(context.getString(R.string.tag_rg_availabilityOfElectricityConnection)).clearCheck();
                llAvailabilityOfElectricityConnectionYes.setVisibility(View.GONE);
                iRbAvailabilityOfElectricityConnection = 0;
            } else if (hheol.getIs_electricity_connection_available()) {
                hmRadioButton.get(context.getString(R.string.tag_rb_availabilityOfElectricityConnectionYes)).setChecked(true);
                llAvailabilityOfElectricityConnectionYes.setVisibility(View.VISIBLE);
                iRbAvailabilityOfElectricityConnection = 1;
            } else if (!hheol.getIs_electricity_connection_available()){
                hmRadioButton.get(context.getString(R.string.tag_rb_availabilityOfElectricityConnectionNo)).setChecked(true);
                llAvailabilityOfElectricityConnectionYes.setVisibility(View.GONE);
                iRbAvailabilityOfElectricityConnection = 2;
            }


            if (hheol.getIs_led_available_under_ujala() == null){
                hmRadioGroup.get(context.getString(R.string.tag_rg_availabilityOfLedBulbsUjala)).clearCheck();
                iRbAvailabilityOfLedBulbsUjala = 0;
            } else if (hheol.getIs_led_available_under_ujala()) {
                hmRadioButton.get(context.getString(R.string.tag_rb_availabilityOfLedBulbsUjalaYes)).setChecked(true);
                iRbAvailabilityOfLedBulbsUjala = 1;
            } else if (!hheol.getIs_led_available_under_ujala()){
                hmRadioButton.get(context.getString(R.string.tag_rb_availabilityOfLedBulbsUjalaNo)).setChecked(true);
                iRbAvailabilityOfLedBulbsUjala = 2;
            }



            if (hheol.getIs_any_member_have_jandhan_ac() == null){
                hmRadioGroup.get(context.getString(R.string.tag_rg_AvailabilityOfPmJanDhanAccount)).clearCheck();
                iRbAvailabilityOfPmJanDhanAccount = 0;
            } else if (hheol.getIs_any_member_have_jandhan_ac()) {
                hmRadioButton.get(context.getString(R.string.tag_rb_AvailabilityOfPmJanDhanAccountYes)).setChecked(true);
                iRbAvailabilityOfPmJanDhanAccount = 1;
            } else if (!hheol.getIs_any_member_have_jandhan_ac()){
                hmRadioButton.get(context.getString(R.string.tag_rb_AvailabilityOfPmJanDhanAccountNo)).setChecked(true);
                iRbAvailabilityOfPmJanDhanAccount = 2;
            }


            if (hheol.getIs_hhd_enrolled_in_pmjjby() == null){
                hmRadioGroup.get(context.getString(R.string.tag_rg_whetherEnrolledForPmjjy)).clearCheck();
                iRbwhetherEnrolledForPmjjy = 0;
            } else if (hheol.getIs_hhd_enrolled_in_pmjjby()) {
                hmRadioButton.get(context.getString(R.string.tag_rb_whetherEnrolledForPmjjyYes)).setChecked(true);
                iRbwhetherEnrolledForPmjjy = 1;
            } else if (!hheol.getIs_hhd_enrolled_in_pmjjby()){
                hmRadioButton.get(context.getString(R.string.tag_rb_whetherEnrolledForPmjjyNo)).setChecked(true);
                iRbwhetherEnrolledForPmjjy = 2;
            }

            if (hheol.getIs_any_member_enrolled_in_pmsby() == null){
                hmRadioGroup.get(context.getString(R.string.tag_rg_whetherEnrolledForPmsby)).clearCheck();
                iRbWhetherEnrolledForPmsby = 0;
            } else if (hheol.getIs_any_member_enrolled_in_pmsby()) {
                hmRadioButton.get(context.getString(R.string.tag_rb_whetherEnrolledForPmsbyYes)).setChecked(true);
                iRbWhetherEnrolledForPmsby = 1;
            } else if (!hheol.getIs_any_member_enrolled_in_pmsby()){
                hmRadioButton.get(context.getString(R.string.tag_rb_whetherEnrolledForPmsbyNo)).setChecked(true);
                iRbWhetherEnrolledForPmsby = 2;
            }


            if (hheol.getIs_any_member_immunised_under_mission_indradhanush() == null){
                hmRadioGroup.get(context.getString(R.string.tag_rg_whetherImmunizedUnderMissionIndradhanush)).clearCheck();
                iRbWhetherImmunizedUnderMissionIndradhanush = 0;
            } else if (hheol.getIs_any_member_immunised_under_mission_indradhanush()) {
                hmRadioButton.get(context.getString(R.string.tag_rb_whetherImmunizedUnderMissionIndradhanushYes)).setChecked(true);
                iRbWhetherImmunizedUnderMissionIndradhanush = 1;
            } else if (!hheol.getIs_any_member_immunised_under_mission_indradhanush()){
                hmRadioButton.get(context.getString(R.string.tag_rb_whetherImmunizedUnderMissionIndradhanushNo)).setChecked(true);
                iRbWhetherImmunizedUnderMissionIndradhanush = 2;
            }





            if (hheol.getIs_any_child_0_6_yrs_or_pregnant_woman_available() == null){
                hmRadioGroup.get(context.getString(R.string.tag_rg_anyChild0To6OrPregnantWomanAvailable)).clearCheck();
                llAnyChild0To6OrPregnantWomanAvailableYes.setVisibility(View.GONE);
                llAnyChild0To6OrPregnantWomanAvailableNo.setVisibility(View.GONE);
                iRbAnyChild0To6OrPregnantWomanAvailable = 0;
            } else if (hheol.getIs_any_child_0_6_yrs_or_pregnant_woman_available()) {
                hmRadioButton.get(context.getString(R.string.tag_rb_anyChild0To6OrPregnantWomanAvailableYes)).setChecked(true);
                llAnyChild0To6OrPregnantWomanAvailableYes.setVisibility(View.VISIBLE);
                llAnyChild0To6OrPregnantWomanAvailableNo.setVisibility(View.GONE);

                iRbAnyChild0To6OrPregnantWomanAvailable = 1;
            } else if (!hheol.getIs_any_child_0_6_yrs_or_pregnant_woman_available()){
                hmRadioButton.get(context.getString(R.string.tag_rb_anyChild0To6OrPregnantWomanAvailableNo)).setChecked(true);
                llAnyChild0To6OrPregnantWomanAvailableYes.setVisibility(View.GONE);
                llAnyChild0To6OrPregnantWomanAvailableNo.setVisibility(View.VISIBLE);
                iRbAnyChild0To6OrPregnantWomanAvailable = 2;
            }


            if (hheol.getIs_any_member_registered_in_icds() == null){
                hmRadioGroup.get(context.getString(R.string.tag_rg_whetherAnyMemberRegisteredUnderIcds)).clearCheck();
                iRbWhetherAnyMemberRegisteredUnderIcds = 0;
            } else if (hheol.getIs_any_member_registered_in_icds()) {
                hmRadioButton.get(context.getString(R.string.tag_rb_whetherAnyMemberRegisteredUnderIcdsYes)).setChecked(true);
                iRbWhetherAnyMemberRegisteredUnderIcds = 1;
            } else if (!hheol.getIs_any_member_registered_in_icds()){
                hmRadioButton.get(context.getString(R.string.tag_rb_whetherAnyMemberRegisteredUnderIcdsNo)).setChecked(true);
                iRbWhetherAnyMemberRegisteredUnderIcds = 2;
            }

            for(int i=0; i<hheol.getServices_availed_under_icds().size();i++){

                hmChkSAUI.get(alTagNameChkSAUI.get(hheol.getServices_availed_under_icds().get(i))).setChecked(true);
                alChkSAUI.add(hheol.getServices_availed_under_icds().get(i));

            }




            if (hheol.getIs_hhd_availed_nutrition_benefits_under_icds() == null){
                hmRadioGroup.get(context.getString(R.string.tag_rg_whetherAvailedNutritionBenefirIcds)).clearCheck();
                iRbWhetherAvailedNutritionBenefirIcds = 0;
            } else if (hheol.getIs_hhd_availed_nutrition_benefits_under_icds()) {
                hmRadioButton.get(context.getString(R.string.tag_rb_whetherAvailedNutritionBenefirIcdsYes)).setChecked(true);
                iRbWhetherAvailedNutritionBenefirIcds = 1;
            } else if (!hheol.getIs_hhd_availed_nutrition_benefits_under_icds()){
                hmRadioButton.get(context.getString(R.string.tag_rb_whetherAvailedNutritionBenefirIcdsNo)).setChecked(true);
                iRbWhetherAvailedNutritionBenefirIcds = 2;
            }


            if (hheol.getIs_any_member_of_hhd_is_member_of_shg() == null){
                hmRadioGroup.get(context.getString(R.string.tag_rg_anyMemberOfShgUnderDayNrlm)).clearCheck();
                iRbAnyMemberOfShgUnderDayNrlm = 0;
            } else if (hheol.getIs_any_member_of_hhd_is_member_of_shg()) {
                hmRadioButton.get(context.getString(R.string.tag_rb_anyMemberOfShgUnderDayNrlmYes)).setChecked(true);
                iRbAnyMemberOfShgUnderDayNrlm = 1;
            } else if (!hheol.getIs_any_member_of_hhd_is_member_of_shg()){
                hmRadioButton.get(context.getString(R.string.tag_rb_anyMemberOfShgUnderDayNrlmNo)).setChecked(true);
                iRbAnyMemberOfShgUnderDayNrlm = 2;
            }



            hmSpinner.get(context.getString(R.string.tag_spinner_householdType)).setSelection(PopulateMasterLists.adapterTypeOfHouseUsedForLivingCategory.getPosition(
                    new MasterCommon(hheol.getType_of_house_used_for_living(), MasterCommonController.mapTypeOfHouseUsedForLivingCategory.get(hheol.getType_of_house_used_for_living()))));
            ispinnerHouseholdType = hheol.getType_of_house_used_for_living();



            if (hheol.getIs_hhd_a_beneficiary_of_pmayg() == null){
                hmRadioGroup.get(context.getString(R.string.tag_rg_WhetherBeneficiaryOfPmayG)).clearCheck();
                llWhetherBeneficiaryOfPmayGYes.setVisibility(View.GONE);
                iRbWhetherBeneficiaryOfPmayG = 0;
            } else if (hheol.getIs_hhd_a_beneficiary_of_pmayg()) {
                hmRadioButton.get(context.getString(R.string.tag_rb_WhetherBeneficiaryOfPmayGYes)).setChecked(true);
                llWhetherBeneficiaryOfPmayGYes.setVisibility(View.VISIBLE);
                iRbWhetherBeneficiaryOfPmayG = 1;
            } else if (!hheol.getIs_hhd_a_beneficiary_of_pmayg()){
                hmRadioButton.get(context.getString(R.string.tag_rb_WhetherBeneficiaryOfPmayGNo)).setChecked(true);
                llWhetherBeneficiaryOfPmayGYes.setVisibility(View.GONE);
                iRbWhetherBeneficiaryOfPmayG = 2;
            }


            hmSpinner.get(context.getString(R.string.tag_spinner_provideStatusOfPmayG)).setSelection(PopulateMasterLists.adapterPmaygStatusCategory.getPosition(
                    new MasterCommon(hheol.getPmayg_status(), MasterCommonController.mapPmaygStatusCategory.get(hheol.getPmayg_status()))));
            ispinnerProvideStatusOfPmayG = hheol.getPmayg_status();

            if ((hheol.getPmayg_id() != null) && (!hheol.getPmayg_id().trim().isEmpty()))
                hmEditText.get(context.getString(R.string.tag_et_pmayg_id)).setText(hheol.getPmayg_id());




            if (hheol.getIs_hhd_issued_health_card_under_abpmjay() == null){
                hmRadioGroup.get(context.getString(R.string.tag_rg_whether_issued_ehealth_card_under_abpmjay)).clearCheck();
                llWhetherIssuedEHealthCardUnderAbPmjayYes.setVisibility(View.GONE);
                iRbWhether_issued_ehealth_card_under_abpmjay = 0;
            } else if (hheol.getIs_hhd_issued_health_card_under_abpmjay()) {
                hmRadioButton.get(context.getString(R.string.tag_rb_whether_issued_ehealth_card_under_abpmjay_yes)).setChecked(true);
                llWhetherIssuedEHealthCardUnderAbPmjayYes.setVisibility(View.VISIBLE);
                iRbWhether_issued_ehealth_card_under_abpmjay = 1;
            } else if (!hheol.getIs_hhd_issued_health_card_under_abpmjay()){
                hmRadioButton.get(context.getString(R.string.tag_rb_whether_issued_ehealth_card_under_abpmjay_no)).setChecked(true);
                llWhetherIssuedEHealthCardUnderAbPmjayYes.setVisibility(View.GONE);
                iRbWhether_issued_ehealth_card_under_abpmjay = 2;
            }



            if ((hheol.getFamily_health_card_number() != null) && (!hheol.getFamily_health_card_number().trim().isEmpty()))
                hmEditText.get(context.getString(R.string.tag_et_family_health_card_no)).setText(hheol.getFamily_health_card_number());



            if (hheol.getIs_any_member_getting_pension_under_nsap() == null){
                hmRadioGroup.get(context.getString(R.string.tag_rg_whether_issued_ehealth_card_under_abpmjay)).clearCheck();
                llWhetherAnyMemberGettingPensionNsapYes.setVisibility(View.GONE);
                iRbWhether_any_member_getting_pension_nsap = 0;
            } else if (hheol.getIs_any_member_getting_pension_under_nsap()) {
                hmRadioButton.get(context.getString(R.string.tag_rb_whether_any_member_getting_pension_nsap_yes)).setChecked(true);
                llWhetherAnyMemberGettingPensionNsapYes.setVisibility(View.VISIBLE);
                iRbWhether_any_member_getting_pension_nsap = 1;
            } else if (!hheol.getIs_any_member_getting_pension_under_nsap()){
                hmRadioButton.get(context.getString(R.string.tag_rb_whether_any_member_getting_pension_nsap_no)).setChecked(true);
                llWhetherAnyMemberGettingPensionNsapYes.setVisibility(View.GONE);
                iRbWhether_any_member_getting_pension_nsap = 2;
            }




            if ((hheol.getFamily_health_card_number() != null) && (!hheol.getFamily_health_card_number().trim().isEmpty()))
                hmEditText.get(context.getString(R.string.tag_et_family_health_card_no)).setText(hheol.getFamily_health_card_number());

/////////////////TYPE_OF_PENSIONS


            if (hheol.getIs_any_member_ever_worked_under_mgnrega() == null){
                hmRadioGroup.get(context.getString(R.string.tag_rg_whether_any_member_worked_with_mgnrega)).clearCheck();
                llWhetherAnyMemberWorkedWithMgnregaYes.setVisibility(View.GONE);
                iRbWhether_any_member_worked_with_mgnrega = 0;
            } else if (hheol.getIs_any_member_ever_worked_under_mgnrega()) {
                hmRadioButton.get(context.getString(R.string.tag_rb_whether_any_member_worked_with_mgnrega_yes)).setChecked(true);
                llWhetherAnyMemberWorkedWithMgnregaYes.setVisibility(View.VISIBLE);
                iRbWhether_any_member_worked_with_mgnrega = 1;
            } else if (!hheol.getIs_any_member_ever_worked_under_mgnrega()){
                hmRadioButton.get(context.getString(R.string.tag_rb_whether_any_member_worked_with_mgnrega_no)).setChecked(true);
                llWhetherAnyMemberWorkedWithMgnregaYes.setVisibility(View.GONE);
                iRbWhether_any_member_worked_with_mgnrega = 2;
            }




            if ((hheol.getMgnrega_job_card_number() != null) && (!hheol.getMgnrega_job_card_number().trim().isEmpty()))
                hmEditText.get(context.getString(R.string.tag_et_mgnrega_job_card_no)).setText(hheol.getMgnrega_job_card_number());



            if ((hheol.getNo_of_days_worked_under_mgnrega_in_last_one_yr() != null) && (hheol.getNo_of_days_worked_under_mgnrega_in_last_one_yr()!=0))
                hmEditText.get(context.getString(R.string.tag_et_no_days_worked_with_mgnrega_last_year)).setText(String.valueOf(hheol.getNo_of_days_worked_under_mgnrega_in_last_one_yr()));



            if (hheol.getIs_any_member_undergone_training_under_any_skill_dev_prog() == null){
                hmRadioGroup.get(context.getString(R.string.tag_rg_whether_any_member_undergone_training_under_skill_dev_scheme)).clearCheck();
                llWhetherAnyMemberUndergoneTrainingInSkillDevSchemeYes.setVisibility(View.GONE);
                iRbWhether_any_member_undergone_training_under_skill_dev_scheme = 0;
            } else if (hheol.getIs_any_member_undergone_training_under_any_skill_dev_prog()) {
                hmRadioButton.get(context.getString(R.string.tag_rb_whether_any_member_undergone_training_under_skill_dev_scheme_yes)).setChecked(true);
                llWhetherAnyMemberUndergoneTrainingInSkillDevSchemeYes.setVisibility(View.VISIBLE);
                iRbWhether_any_member_undergone_training_under_skill_dev_scheme = 1;
            } else if (!hheol.getIs_any_member_undergone_training_under_any_skill_dev_prog()){
                hmRadioButton.get(context.getString(R.string.tag_rb_whether_any_member_undergone_training_under_skill_dev_scheme_no)).setChecked(true);
                llWhetherAnyMemberUndergoneTrainingInSkillDevSchemeYes.setVisibility(View.GONE);
                iRbWhether_any_member_undergone_training_under_skill_dev_scheme = 2;
            }


            ////UNDERGONE_SKILL_DEVELOPMENT_SCHEMES


            if (hheol.getIs_hhd_available_a_functional_toilet() == null){
                hmRadioGroup.get(context.getString(R.string.tag_rg_Whethere_household_has_functional_toilets)).clearCheck();
                iRbWhethere_household_has_functional_toilets = 0;
            } else if (hheol.getIs_hhd_available_a_functional_toilet()) {
                hmRadioButton.get(context.getString(R.string.tag_rb_Whethere_household_has_functional_toilets_yes)).setChecked(true);
                iRbWhethere_household_has_functional_toilets = 1;
            } else if (!hheol.getIs_hhd_available_a_functional_toilet()){
                hmRadioButton.get(context.getString(R.string.tag_rb_Whether_house_hold_has_functional_toilets_No)).setChecked(true);
                iRbWhethere_household_has_functional_toilets = 2;
            }

/////////////////MOBILE_NUMBERS_HHD_MEMBERS

            if (hheol.getIs_hhd_availing_ration_under_nfsa_scheme() == null){
                hmRadioGroup.get(context.getString(R.string.tag_rg_Whethere_house_hold_has_ration_card)).clearCheck();
                l1WhetherHouseHoldHasNFSASchemeYes.setVisibility(View.GONE);
                iRbWhether_house_hold_has_ration_card = 0;
            } else if (hheol.getIs_hhd_availing_ration_under_nfsa_scheme()) {
                hmRadioButton.get(context.getString(R.string.tag_rb_Whether_house_hold_has_ration_card_yes)).setChecked(true);
                l1WhetherHouseHoldHasNFSASchemeYes.setVisibility(View.VISIBLE);
                iRbWhether_house_hold_has_ration_card = 1;
            } else if (!hheol.getIs_hhd_availing_ration_under_nfsa_scheme()){
                hmRadioButton.get(context.getString(R.string.tag_rb_Whether_house_hold_has_ration_card_no)).setChecked(true);
                l1WhetherHouseHoldHasNFSASchemeYes.setVisibility(View.GONE);
                iRbWhether_house_hold_has_ration_card = 2;
            }




            hmSpinner.get(context.getString(R.string.tag_spinner_nfsa_ration_card_type)).setSelection(PopulateMasterLists.adapterNfsaRationCardTypeCategory.getPosition(
                    new MasterCommon(hheol.getNfsa_ration_card_type(), MasterCommonController.mapNfsaRationCardTypeCategory.get(hheol.getNfsa_ration_card_type()))));
            ispinnerNfsaRationCardType = hheol.getNfsa_ration_card_type();


            if ((hheol.getNfsa_ration_card_number() != null) && (!hheol.getNfsa_ration_card_number().trim().isEmpty()))
                hmEditText.get(context.getString(R.string.tag_et_nfsa_ration_card_no)).setText(hheol.getNfsa_ration_card_number());



        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.add_gp_error), e.getMessage(),"005-018");
        }
*/
    }

    @Override
    public void onClick(View view) {
        try {
            switch (view.getId()) {
                case R.id.llLocationParameters:
                    if (llLocationParametersDetails.getVisibility() == View.VISIBLE) {
                        llLocationParametersDetails.setVisibility(View.GONE);
                        ivLocationParameters.setImageResource(R.mipmap.icon_plus);
                    } else {
                        Support.setLinearLayoutHideShow(alLinearLayout, alImageView, llLocationParametersDetails, ivLocationParameters);
                    }
                    break;

                case R.id.llHouseholdParticulars:
                    if (llHouseholdParticularsDetails.getVisibility() == View.VISIBLE) {
                        llHouseholdParticularsDetails.setVisibility(View.GONE);
                        ivHouseholdParticulars.setImageResource(R.mipmap.icon_plus);
                    } else {
                        Support.setLinearLayoutHideShow(alLinearLayout, alImageView, llHouseholdParticularsDetails, ivHouseholdParticulars);
                    }
                    break;

                 case R.id.rbAvailabilityOfElectricityConnectionYes:
                    llAvailabilityOfElectricityConnectionYes.setVisibility(View.VISIBLE);
                    break;
                case R.id.rbAvailabilityOfElectricityConnectionNo:
                    llAvailabilityOfElectricityConnectionYes.setVisibility(View.GONE);
                    break;
                case R.id.rbAnyChild0To6OrPregnantWomanAvailableYes:
                    llAnyChild0To6OrPregnantWomanAvailableYes.setVisibility(View.VISIBLE);

                    break;
                case R.id.rbAnyChild0To6OrPregnantWomanAvailableNo:
                    llAnyChild0To6OrPregnantWomanAvailableYes.setVisibility(View.GONE);

                    break;

                    case R.id.rbWhetherAnyMemberWorkedWithMgnregaYes:
                    llWhetherAnyMemberWorkedWithMgnregaYes.setVisibility(View.VISIBLE);
                    llWhetherAnyMemberWorkedWithMgnregaNo.setVisibility(View.GONE);

                        break;
                case R.id.rbWhetherAnyMemberWorkedWithMgnregaNo:
                    llWhetherAnyMemberWorkedWithMgnregaYes.setVisibility(View.GONE);
                    llWhetherAnyMemberWorkedWithMgnregaNo.setVisibility(View.VISIBLE);
                    break;
                case R.id.rbWhetherAnyMemberUndergoneTrainingInSkillDevSchemeYes:
                    llWhetherAnyMemberUndergoneTrainingInSkillDevSchemeYes.setVisibility(View.VISIBLE);
                    break;
                case R.id.rbWhetherAnyMemberUndergoneTrainingInSkillDevSchemeNo:
                    llWhetherAnyMemberUndergoneTrainingInSkillDevSchemeYes.setVisibility(View.GONE);
                    break;

                case R.id.btnSubmitGpData:
                    getRadioButtonValues();
                 //   if (validateMandatoryValues())
                    {
                        bIsGpsThroughSubmit = true;
                        HouseholdEol hhoel = retreiveHouseholdValues(true);
                        if (hhoel != null)
                            submitDataPopUpDialog(saveLocalGpData(hhoel));
                        else
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.gps_location_not_retrievable),"005-015");

                    }
                    break;

                case R.id.btnSaveDraftGp:
                    getRadioButtonValues();

                    bIsGpsThroughSubmit = false;
                    saveDraft();

                    break;



            }

        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.add_gp_error), e.getMessage(),"005-016");
        }
    }

/*
    private boolean validateMandatoryValues() {
        boolean bResult = true;
        if ((iRbAvailabilityOfLpgConnection == 0)) {
            hmTextView.get(context.getString(R.string.tag_tv_availabilityOfLpgConnection)).setFocusable(true);
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.msg_select) + context.getString(R.string.is_lpg_connection_available), "005-171");
            bResult = false;
        }
        else if((iRbAvailabilityOfLpgConnection == 1) && (hmEditText.get(context.getString(R.string.tag_et_lpgConsumerId)).getText().toString().trim().isEmpty())) {
            hmEditText.get(context.getString(R.string.tag_et_lpgConsumerId)).setFocusable(true);
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.msg_empty_value) + context.getString(R.string.lpg_consumer_id), "005-171");
            bResult = false;
        }
        else if((iRbAvailabilityOfLpgConnection == 1) && (hmEditText.get(context.getString(R.string.tag_et_cylinderRefillFrequency)).getText().toString().trim().isEmpty())) {
            hmEditText.get(context.getString(R.string.tag_et_cylinderRefillFrequency)).setFocusable(true);
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.msg_empty_value) + context.getString(R.string.no_of_times_refilled_in_last_one_yr), "005-171");
            bResult = false;
        }

        else if ((iRbAvailabilityOfElectricityConnection == 0)) {
            hmTextView.get(context.getString(R.string.tag_tv_availabilityOfElectricityConnection)).setFocusable(true);
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.msg_select) + context.getString(R.string.is_electricity_connection_available), "005-171");
            bResult = false;
        }
        else if ((iRbAvailabilityOfElectricityConnection == 1)&&(iRbAvailabilityOfLedBulbsUjala == 0)) {
            hmTextView.get(context.getString(R.string.tag_tv_availabilityOfLedBulbsUjala)).setFocusable(true);
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.msg_select) + context.getString(R.string.is_led_available_under_ujala), "005-171");
            bResult = false;
        }

        else if ((iRbAvailabilityOfPmJanDhanAccount == 0)) {
            hmTextView.get(context.getString(R.string.tag_tv_availabilityOfPmJanDhanAccount)).setFocusable(true);
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.msg_select) + context.getString(R.string.is_any_member_have_jandhan_ac), "005-171");
            bResult = false;
        }
        else if ((iRbwhetherEnrolledForPmjjy == 0)) {
            hmTextView.get(context.getString(R.string.tag_tv_whetherEnrolledForPmjjy)).setFocusable(true);
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.msg_select) + context.getString(R.string.is_hhd_enrolled_in_pmjjby), "005-171");
            bResult = false;
        }
        else if ((iRbWhetherEnrolledForPmsby == 0)) {
            hmTextView.get(context.getString(R.string.tag_tv_whetherEnrolledForPmsby)).setFocusable(true);
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.msg_select) + context.getString(R.string.is_any_member_enrolled_in_pmsby), "005-171");
            bResult = false;
        }
        else if ((iRbWhetherImmunizedUnderMissionIndradhanush == 0)) {
            hmTextView.get(context.getString(R.string.tag_tv_whetherImmunizedUnderMissionIndradhanush)).setFocusable(true);
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.msg_select) + context.getString(R.string.is_any_member_immunised_under_mission_indradhanush), "005-171");
            bResult = false;
        }
        else if ((iRbAnyChild0To6OrPregnantWomanAvailable == 0)) {
            hmTextView.get(context.getString(R.string.tag_tv_anyChild0To6OrPregnantWomanAvailable)).setFocusable(true);
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.msg_select) + context.getString(R.string.is_any_child_0_6_yrs_or_pregnant_woman_available), "005-171");
            bResult = false;
        }
        else if ((iRbAnyChild0To6OrPregnantWomanAvailable == 1)&&(iRbWhetherAnyMemberRegisteredUnderIcds == 0)) {
            hmTextView.get(context.getString(R.string.tag_tv_whetherAnyMemberRegisteredUnderIcds)).setFocusable(true);
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.msg_select) + context.getString(R.string.is_any_member_registered_in_icds), "005-171");
            bResult = false;
        }

        else if ((iRbAnyChild0To6OrPregnantWomanAvailable == 1)&&(iRbWhetherAnyMemberRegisteredUnderIcds == 0)) {
            hmTextView.get(context.getString(R.string.tag_tv_checkServicesUnderIcds)).setFocusable(true);
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.msg_select) + context.getString(R.string.services_availed_under_icds), "005-171");
            bResult = false;
        }

        else if ((iRbAnyChild0To6OrPregnantWomanAvailable == 2)&&(iRbWhetherAvailedNutritionBenefirIcds == 0)) {
            hmTextView.get(context.getString(R.string.tag_tv_whetherAvailedNutritionBenefirIcds)).setFocusable(true);
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.msg_select) + context.getString(R.string.is_hhd_availed_nutrition_benefits_under_icds), "005-171");
            bResult = false;
        }


        else if ((ispinnerHouseholdType == 0)) {
            hmTextView.get(context.getString(R.string.tag_tv_householdType)).setFocusable(true);
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.msg_select) + context.getString(R.string.type_of_house_used_for_living), "005-171");
            bResult = false;
        }


        else if ((iRbWhether_any_member_getting_pension_nsap == 0)) {
            hmTextView.get(context.getString(R.string.tag_tv_whether_any_member_getting_pension_nsap)).setFocusable(true);
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.msg_select) + context.getString(R.string.is_any_member_getting_pension_under_nsap), "005-171");
            bResult = false;
        }
        else if ((iRbWhether_any_member_worked_with_mgnrega == 0)) {
            hmTextView.get(context.getString(R.string.tag_tv_whether_any_member_wotked_with_mgnrega)).setFocusable(true);
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.msg_select) + context.getString(R.string.is_any_member_ever_worked_under_mgnrega), "005-171");
            bResult = false;
        }
        else if ((iRbWhether_any_member_undergone_training_under_skill_dev_scheme == 0)) {
            hmTextView.get(context.getString(R.string.tag_tv_whether_any_member_undergone_training_under_skill_dev_scheme)).setFocusable(true);
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.msg_select) + context.getString(R.string.is_any_member_undergone_training_under_any_skill_dev_prog), "005-171");
            bResult = false;
        }
        else if ((iRbWhethere_household_has_functional_toilets == 0)) {
            hmTextView.get(context.getString(R.string.tag_tv_Whethere_household_has_functional_toilets)).setFocusable(true);
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.msg_select) + context.getString(R.string.is_hhd_available_a_functional_toilet), "005-171");
            bResult = false;
        }
        else if ((iRbWhethere_household_has_functional_toilets == 0)) {
            hmTextView.get(context.getString(R.string.tag_tv_Whethere_household_has_functional_toilets)).setFocusable(true);
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.msg_select) + context.getString(R.string.is_hhd_available_a_functional_toilet), "005-171");
            bResult = false;
        }
        else if ((iRbWhether_house_hold_has_ration_card == 0)) {
            hmTextView.get(context.getString(R.string.tag_tv_Whether_house_hold_availing_ration_nfsa_scheme)).setFocusable(true);
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), context.getString(R.string.msg_select) + context.getString(R.string.is_hhd_availing_ration_under_nfsa_scheme), "005-171");
            bResult = false;
        }
        return  bResult;
    }
*/



    private void saveDraft()
    {
        try {
            HouseholdEol hheol = retreiveHouseholdValues(false);

            if (hheol != null)
                saveDataPopUpDialog(saveLocalGpData(hheol));
        }catch(Exception e)
        {
            MyAlert.showAlert(context,R.mipmap.icon_error,  getString(R.string.add_gp_error),  e.getMessage(),"005-335");
        }
    }

    private void saveDataPopUpDialog(Boolean bResult)
    {
        try {
            if (bResult == true) {
                final Dialog dialogAlert = new Dialog(context);
                MyAlert.dialogForOk
                        (context, R.mipmap.icon_info, context.getString(R.string.add_gp_info),
                                context.getString(R.string.add_gp_msg4),
                                dialogAlert,
                                context.getString(R.string.ok_l),
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialogAlert.dismiss();
                                        //finish();

                                    }
                                },"005-159");
            } else
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error),  context.getString(R.string.add_gp_msg3),"005-143");
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.add_gp_error), e.getMessage(),"005-144");
        }
    }

    private boolean saveLocalGpData(HouseholdEol hheol) {
        boolean bResult = false;
        try {
            alHouseholdEoleSurvey = HouseholdEolController.getDataByHhd(context, DBHelper.getInstance(context, true), hhd_uid);
            if(alHouseholdEoleSurvey.size() > 0)
                bResult = HouseholdEolController.updateHhd(context, DBHelper.getInstance(context, true), hheol);
            else
                bResult = HouseholdEolController.insertHhd(context, DBHelper.getInstance(context, true), hheol);


        } catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.add_gp_error), e.getMessage(),"005-022");
        }
        return bResult;

    }
    private HouseholdEol retreiveHouseholdValues(Boolean bIsFinalSubmit) {

/*
        try {
            HouseholdEol hheol = new HouseholdEol();

            // check if GPS enabled
            gps = new GPSTracker(context);
            Boolean isGPSEnabled = gps.getLocationManager().isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (bIsFinalSubmit && !isGPSEnabled) {
                //new GPS().turnGpsOn(context, AddGramPanchayatActivity.this);
                googleApiClient = null;
                enableLoc();
                return null;
            } else if (!bIsFinalSubmit || (bIsFinalSubmit && ((gps.canGetLocation()) || ((dLatitude != 0.0) && (dLongitude != 0.0))))) {
                if (gps.canGetLocation()) {
                    dLatitude = gps.getLatitude();
                    dLongitude = gps.getLongitude();
                }


                ArrayList<SeccHousehold> alSeccHousehold= SeccHouseholdController.getHhdByHhdId( DBHelper.getInstance(context, true),context, hhd_uid);

                hheol.setState_code(alSeccHousehold.get(0).getState_code());
                hheol.setState_name(alSeccHousehold.get(0).getState_name());
                hheol.setState_name_sl(alSeccHousehold.get(0).getState_name_sl());
                hheol.setStatecode(alSeccHousehold.get(0).getStatecode());
                hheol.setDistrict_code(alSeccHousehold.get(0).getDistrict_code());
                hheol.setDistrict_name(alSeccHousehold.get(0).getDistrict_name());
                hheol.setDistrict_name_sl(alSeccHousehold.get(0).getDistrict_name_sl());
                hheol.setDistrictcode(alSeccHousehold.get(0).getDistrictcode());
                hheol.setTehsilcode(alSeccHousehold.get(0).getTehsilcode());
                hheol.setTowncode(alSeccHousehold.get(0).getTowncode());

                hheol.setSub_district_code(alSeccHousehold.get(0).getSub_district_code());
                hheol.setSub_district_name(alSeccHousehold.get(0).getSub_district_name());
                hheol.setSub_district_name_sl(alSeccHousehold.get(0).getSub_district_name_sl());
                hheol.setBlock_code(alSeccHousehold.get(0).getBlock_code());
                hheol.setBlock_name(alSeccHousehold.get(0).getBlock_name());
                hheol.setBlock_name_sl(alSeccHousehold.get(0).getBlock_name_sl());
                hheol.setAhlblockno(alSeccHousehold.get(0).getAhlblockno());
                hheol.setAhlsubblockno(alSeccHousehold.get(0).getAhlsubblockno());
                hheol.setAhl_family_tin(alSeccHousehold.get(0).getAhl_family_tin());

                hheol.setGp_code(alSeccHousehold.get(0).getGp_code());
                hheol.setGp_name(alSeccHousehold.get(0).getGp_name());
                hheol.setGp_name_sl(alSeccHousehold.get(0).getGp_name_sl());
                hheol.setVillage_code(alSeccHousehold.get(0).getVillage_code());
                hheol.setVillage_name(alSeccHousehold.get(0).getVillage_name());
                hheol.setVillage_name_sl(alSeccHousehold.get(0).getVillage_name_sl());
                hheol.setEnum_block_code(alSeccHousehold.get(0).getEnum_block_code());
                hheol.setEnum_block_name(alSeccHousehold.get(0).getEnum_block_name());
                hheol.setEnum_block_name_sl(alSeccHousehold.get(0).getEnum_block_name_sl());

                hheol.setHhd_uid(hhd_uid);




                if(iRbAvailabilityOfLpgConnection==1)
                    hheol.setIs_lpg_connection_available(true);
                else if(iRbAvailabilityOfLpgConnection==2)
                    hheol.setIs_lpg_connection_available(false);
                else
                    hheol.setIs_lpg_connection_available(null);


                if (!(hmEditText.get(context.getString(R.string.tag_et_lpgConsumerId)).getText().toString().trim().isEmpty()))
                    hheol.setLpg_consumer_id(hmEditText.get(context.getString(R.string.tag_et_lpgConsumerId)).getText().toString().trim());

                if (!(hmEditText.get(context.getString(R.string.tag_et_cylinderRefillFrequency)).getText().toString().trim().isEmpty()))
                    hheol.setNo_of_times_refilled_in_last_one_yr(Integer.valueOf(hmEditText.get(context.getString(R.string.tag_et_cylinderRefillFrequency)).getText().toString().trim()));


                if(iRbAvailabilityOfElectricityConnection==1)
                    hheol.setIs_electricity_connection_available(true);
                else if(iRbAvailabilityOfElectricityConnection==2)
                    hheol.setIs_electricity_connection_available(false);
                else
                    hheol.setIs_electricity_connection_available(null);





                if(iRbAvailabilityOfLedBulbsUjala==1)
                    hheol.setIs_led_available_under_ujala(true);
                else if(iRbAvailabilityOfLedBulbsUjala==2)
                    hheol.setIs_led_available_under_ujala(false);
                else
                    hheol.setIs_led_available_under_ujala(null);



             if(iRbAvailabilityOfPmJanDhanAccount==1)
                    hheol.setIs_any_member_have_jandhan_ac(true);
                else if(iRbAvailabilityOfPmJanDhanAccount==2)
                    hheol.setIs_any_member_have_jandhan_ac(false);
                else
                    hheol.setIs_any_member_have_jandhan_ac(null);





                if(iRbwhetherEnrolledForPmjjy==1)
                    hheol.setIs_hhd_enrolled_in_pmjjby(true);
                else if(iRbwhetherEnrolledForPmjjy==2)
                    hheol.setIs_hhd_enrolled_in_pmjjby(false);
                else
                    hheol.setIs_hhd_enrolled_in_pmjjby(null);



                if(iRbWhetherEnrolledForPmsby==1)
                    hheol.setIs_any_member_enrolled_in_pmsby(true);
                else if(iRbWhetherEnrolledForPmsby==2)
                    hheol.setIs_any_member_enrolled_in_pmsby(false);
                else
                    hheol.setIs_any_member_enrolled_in_pmsby(null);



                if(iRbWhetherImmunizedUnderMissionIndradhanush==1)
                    hheol.setIs_any_member_immunised_under_mission_indradhanush(true);
                else if(iRbWhetherImmunizedUnderMissionIndradhanush==2)
                    hheol.setIs_any_member_immunised_under_mission_indradhanush(false);
                else
                    hheol.setIs_any_member_immunised_under_mission_indradhanush(null);




                if(iRbAnyChild0To6OrPregnantWomanAvailable==1)
                {
                    hheol.setIs_any_child_0_6_yrs_or_pregnant_woman_available(true);


                    hheol.setServices_availed_under_icds(alChkSAUI);
                }
                else if(iRbAnyChild0To6OrPregnantWomanAvailable==2)
                    hheol.setIs_any_child_0_6_yrs_or_pregnant_woman_available(false);
                else
                    hheol.setIs_any_child_0_6_yrs_or_pregnant_woman_available(null);




                if(iRbWhetherAnyMemberRegisteredUnderIcds==1)
                    hheol.setIs_any_member_registered_in_icds(true);
                else if(iRbWhetherAnyMemberRegisteredUnderIcds==2)
                    hheol.setIs_any_member_registered_in_icds(false);
                else
                    hheol.setIs_any_member_registered_in_icds(null);


                if(iRbWhetherAvailedNutritionBenefirIcds==1)
                    hheol.setIs_hhd_availed_nutrition_benefits_under_icds(true);
                else if(iRbWhetherAvailedNutritionBenefirIcds==2)
                    hheol.setIs_hhd_availed_nutrition_benefits_under_icds(false);
                else
                    hheol.setIs_hhd_availed_nutrition_benefits_under_icds(null);






                if(iRbAnyMemberOfShgUnderDayNrlm==1)
                    hheol.setIs_any_member_of_hhd_is_member_of_shg(true);
                else if(iRbAnyMemberOfShgUnderDayNrlm==2)
                    hheol.setIs_any_member_of_hhd_is_member_of_shg(false);
                else
                    hheol.setIs_any_member_of_hhd_is_member_of_shg(null);




                hheol.setType_of_house_used_for_living(ispinnerHouseholdType);



                if(iRbWhetherBeneficiaryOfPmayG==1)
                    hheol.setIs_hhd_a_beneficiary_of_pmayg(true);
                else if(iRbWhetherBeneficiaryOfPmayG==2)
                    hheol.setIs_hhd_a_beneficiary_of_pmayg(false);
                else
                    hheol.setIs_hhd_a_beneficiary_of_pmayg(null);


                hheol.setPmayg_status(ispinnerProvideStatusOfPmayG);



                if (!(hmEditText.get(context.getString(R.string.tag_et_pmayg_id)).getText().toString().trim().isEmpty()))
                    hheol.setPmayg_id(hmEditText.get(context.getString(R.string.tag_et_pmayg_id)).getText().toString().trim());




                if(iRbWhether_issued_ehealth_card_under_abpmjay==1)
                    hheol.setIs_hhd_issued_health_card_under_abpmjay(true);
                else if(iRbWhether_issued_ehealth_card_under_abpmjay==2)
                    hheol.setIs_hhd_issued_health_card_under_abpmjay(false);
                else
                    hheol.setIs_hhd_issued_health_card_under_abpmjay(null);



                if (!(hmEditText.get(context.getString(R.string.tag_et_family_health_card_no)).getText().toString().trim().isEmpty()))
                    hheol.setFamily_health_card_number(hmEditText.get(context.getString(R.string.tag_et_family_health_card_no)).getText().toString().trim());



                if(iRbWhether_any_member_getting_pension_nsap==1)
                {
                    hheol.setIs_any_member_getting_pension_under_nsap(true);

                    hheol.setType_of_pensions(alChkPension);
                }
                else if(iRbWhether_any_member_getting_pension_nsap==2)
                    hheol.setIs_any_member_getting_pension_under_nsap(false);
                else
                    hheol.setIs_any_member_getting_pension_under_nsap(null);









                if(iRbWhether_any_member_worked_with_mgnrega==1)
                    hheol.setIs_any_member_ever_worked_under_mgnrega(true);
                else if(iRbWhether_any_member_worked_with_mgnrega==2)
                    hheol.setIs_any_member_ever_worked_under_mgnrega(false);
                else
                    hheol.setIs_any_member_ever_worked_under_mgnrega(null);



                if (!(hmEditText.get(context.getString(R.string.tag_et_mgnrega_job_card_no)).getText().toString().trim().isEmpty()))
                    hheol.setMgnrega_job_card_number(hmEditText.get(context.getString(R.string.tag_et_mgnrega_job_card_no)).getText().toString().trim());

                if (!(hmEditText.get(context.getString(R.string.tag_et_no_days_worked_with_mgnrega_last_year)).getText().toString().trim().isEmpty()))
                    hheol.setNo_of_days_worked_under_mgnrega_in_last_one_yr(Integer.valueOf(hmEditText.get(context.getString(R.string.tag_et_no_days_worked_with_mgnrega_last_year)).getText().toString().trim()));




                if(iRbWhether_any_member_undergone_training_under_skill_dev_scheme==1)
                {
                    hheol.setIs_any_member_undergone_training_under_any_skill_dev_prog(true);

                    hheol.setUndergone_skill_development_schemes(alChkUSDS);
                }
                else if(iRbWhether_any_member_undergone_training_under_skill_dev_scheme==2)
                    hheol.setIs_any_member_undergone_training_under_any_skill_dev_prog(false);
                else
                    hheol.setIs_any_member_undergone_training_under_any_skill_dev_prog(null);


                if(iRbWhethere_household_has_functional_toilets==1)
                    hheol.setIs_hhd_available_a_functional_toilet(true);
                else if(iRbWhethere_household_has_functional_toilets==2)
                    hheol.setIs_hhd_available_a_functional_toilet(false);
                else
                    hheol.setIs_hhd_available_a_functional_toilet(null);


                ArrayList<String> alTempString = new ArrayList<>();
                if (!(hmEditText.get(context.getString(R.string.tag_et_house_hold_mobile_no)).getText().toString().trim().isEmpty()))
               {
                   alTempString.clear();
                   String arrMobileNumbers[] = hmEditText.get(context.getString(R.string.tag_et_house_hold_mobile_no)).getText().toString().trim().split(",");
                  for(int i=0;i<arrMobileNumbers.length;i++)
                  {
                      if(!arrMobileNumbers[i].trim().isEmpty() && arrMobileNumbers[i]!=null) {
                          alTempString.add(arrMobileNumbers[i]);
                      }
                  }
                   for(int i=0;i<alTempString.size();i++)
                   {
                       Log.d(TAG, "showVolley_error:alTempString"+alTempString.get(i));

                   }
                   hheol.setMobile_numbers_hhd_members(alTempString);
               }


                if(iRbWhether_house_hold_has_ration_card==1)
                    hheol.setIs_hhd_availing_ration_under_nfsa_scheme(true);
                else if(iRbWhether_house_hold_has_ration_card==2)
                    hheol.setIs_hhd_availing_ration_under_nfsa_scheme(false);
                else
                    hheol.setIs_hhd_availing_ration_under_nfsa_scheme(null);


                hheol.setNfsa_ration_card_type(ispinnerNfsaRationCardType);

                if (!(hmEditText.get(context.getString(R.string.tag_et_nfsa_ration_card_no)).getText().toString().trim().isEmpty()))
                    hheol.setNfsa_ration_card_number(hmEditText.get(context.getString(R.string.tag_et_nfsa_ration_card_no)).getText().toString().trim());

                hheol.setHhd_latitude(String.valueOf(dLatitude));
                hheol.setHhd_longitude(String.valueOf(dLongitude));
                hheol.setUser_id(AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                hheol.setDevice_id(MySharedPref.getDeviceId(context));
                hheol.setApp_id(Installation.id(context));

                try {
                    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                    hheol.setApp_version(packageInfo.versionName);
                } catch (Exception e) {
                    MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.add_gp_error), e.getMessage(),"005-020");
                }

                hheol.setDt_created(MyDateSupport.getCurrentDateTimefordatabaseStorage());
                hheol.setTs_updated(MyDateSupport.getCurrentDateTimefordatabaseStorage());
                hheol.setDt_sync("");
                hheol.setIs_synchronized(0);
                hheol.setIs_uncovered(false);


                hheol.setUncovered_reason_code(0);
                hheol.setUncovered_reason("");

                hheol.setIs_completed(bIsFinalSubmit?1:0);
                return hheol;
            }
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error), e.getMessage(),"005-021");
        }
*/

        return null;
    }





    private void submitDataPopUpDialog(Boolean bResult)
    {
        try {
            if (bResult == true) {
                final Dialog dialogAlert = new Dialog(context);
                MyAlert.dialogForOk
                        (context, R.mipmap.icon_info, context.getString(R.string.add_gp_info),
                                context.getString(R.string.add_gp_msg4),
                                dialogAlert,
                                context.getString(R.string.ok_l),
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialogAlert.dismiss();
                                        Intent intent = new Intent(HouseholdData.this, Home.class);
                                        intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                                        intent.putExtra("user_password", strUserPassw);
                                        startActivity(intent);
                                        finishAffinity();

                                    }
                                },"005-158");
            } else
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.add_gp_error),  context.getString(R.string.add_gp_msg3),"005-145");
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.add_gp_error),  e.getMessage(),"005-146");
        }
    }

    private void populateArrayList()
    {
        try {

            alLinearLayout.clear();
            alImageView.clear();
            alEditText.clear();

            alLinearLayout.add(llLocationParametersDetails);
            alLinearLayout.add(llHouseholdParticularsDetails);

            alImageView.add(ivLocationParameters);
            alImageView.add(ivHouseholdParticulars);

        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.add_gp_error), e.getMessage(),"005-004");
        }
    }

    private void getRadioButtonValues()
    {

      if (hmRadioButton.get(context.getString(R.string.tag_rb_availabilityOfElectricityConnectionYes)).isChecked())
            iRbAvailabilityOfElectricityConnection = 1;
        if (hmRadioButton.get(context.getString(R.string.tag_rb_availabilityOfElectricityConnectionNo)).isChecked())
            iRbAvailabilityOfElectricityConnection = 2;

        if (hmRadioButton.get(context.getString(R.string.tag_rb_anyChild0To6OrPregnantWomanAvailableYes)).isChecked())
            iRbAnyChild0To6OrPregnantWomanAvailable = 1;
        if (hmRadioButton.get(context.getString(R.string.tag_rb_anyChild0To6OrPregnantWomanAvailableNo)).isChecked())
            iRbAnyChild0To6OrPregnantWomanAvailable = 2;

        if (hmRadioButton.get(context.getString(R.string.tag_rb_whether_any_member_worked_with_mgnrega_yes)).isChecked())
            iRbWhether_any_member_worked_with_mgnrega = 1;
        if (hmRadioButton.get(context.getString(R.string.tag_rb_whether_any_member_worked_with_mgnrega_no)).isChecked())
            iRbWhether_any_member_worked_with_mgnrega = 2;


        if (hmRadioButton.get(context.getString(R.string.tag_rb_whether_any_member_undergone_training_under_skill_dev_scheme_yes)).isChecked())
            iRbWhether_any_member_undergone_training_under_skill_dev_scheme = 1;
        if (hmRadioButton.get(context.getString(R.string.tag_rb_whether_any_member_undergone_training_under_skill_dev_scheme_no)).isChecked())
            iRbWhether_any_member_undergone_training_under_skill_dev_scheme = 2;

        if (hmRadioButton.get(context.getString(R.string.tag_rb_Whethere_household_has_functional_toilets_yes)).isChecked())
            iRbWhethere_household_has_functional_toilets = 1;
        if (hmRadioButton.get(context.getString(R.string.tag_rb_Whether_house_hold_has_functional_toilets_No)).isChecked())
            iRbWhethere_household_has_functional_toilets = 2;





        alChkPension.clear();
        alChkUSDS.clear();




        for(int i=0; i<hmChkPension.size(); i++)
        {
            if(hmChkPension.get(alTagNameChkPension.get(i)).isChecked())
            {
                Log.d(TAG, "showVolley_error:alChkUSDS"+alPensionSchemeCategory.get(i+1).getType_code());



                alChkPension.add(alPensionSchemeCategory.get(i+1).getType_code());
            }
        }

        for(int i=0; i<hmChkUSDS.size(); i++)
        {
            if(hmChkUSDS.get(alTagNameChkUSDS.get(i)).isChecked())
            {

                Log.d(TAG, "showVolley_error:alChkUSDS"+alSkillDevelopmentSchemesCategory.get(i+1).getType_code());

                alChkUSDS.add(alSkillDevelopmentSchemesCategory.get(i+1).getType_code());
            }
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



    Boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        try {
            final Dialog dialogAlert = new Dialog(context);
            MyAlert.dialogForCancelOk
                    (context,R.mipmap.icon_info,  context.getString(R.string.add_household_warning),
                            context.getString(R.string.add_household_back_btn_alert),
                            dialogAlert,
                            context.getString(R.string.yes),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogAlert.dismiss();
                                    Intent intent = new Intent(HouseholdData.this, Home.class);
                                    intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                                    intent.putExtra("user_password", strUserPassw);
                                    startActivity(intent);
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
                            }, "006-022");
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.household_data_error),  e.getMessage(), "006-033");
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
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.add_household_error), "MA-005-026 : " + e.getMessage(), "006-019");
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
                                                        Intent intent = new Intent(HouseholdData.this, Home.class);
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
                                                }, "006-020");
                            } else {
                                Intent intent = new Intent(HouseholdData.this, Home.class);
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
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.add_household_error),  e.getMessage(), "006-021");
        }

    }


    private String  removeLastComma(String strng){
        int n=strng.lastIndexOf(",");
        String a=strng.substring(0,n);
        return a;
    }
    MenuItem menuItem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_internet_connectivity, menu);
        menuItem = menu.findItem(R.id.action_internet_status);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void event(Boolean data) {
        try {
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }



}