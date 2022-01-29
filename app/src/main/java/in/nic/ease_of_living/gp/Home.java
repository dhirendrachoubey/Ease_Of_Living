package in.nic.ease_of_living.gp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import in.nic.ease_of_living.adapter.DevelopmentBlockAdapter;
import in.nic.ease_of_living.adapter.EnumBlockAdapter;
import in.nic.ease_of_living.adapter.GPAdapter;
import in.nic.ease_of_living.adapter.OptionsAdapter;
import in.nic.ease_of_living.adapter.VillageAdapter;
import in.nic.ease_of_living.broadcast.NetworkChangeReceiver;
import in.nic.ease_of_living.dbo.DBHelper;
import in.nic.ease_of_living.dbo.HouseholdEolController;
import in.nic.ease_of_living.dbo.MasterVillageController;
import in.nic.ease_of_living.dbo.SQLiteHelper;
import in.nic.ease_of_living.dbo.SeccHouseholdController;
import in.nic.ease_of_living.dbo.SeccPopulationController;
import in.nic.ease_of_living.interfaces.BooleanVariableListener;
import in.nic.ease_of_living.interfaces.Communicator;
import in.nic.ease_of_living.interfaces.Constants;
import in.nic.ease_of_living.interfaces.VolleyCallback;
import in.nic.ease_of_living.models.AknowledgeVillageData;
import in.nic.ease_of_living.models.DevelopmentBlock;
import in.nic.ease_of_living.models.EnumeratedBlock;
import in.nic.ease_of_living.models.GP;
import in.nic.ease_of_living.models.GpVillageChecksum;
import in.nic.ease_of_living.models.HouseholdEolEnumerated;
import in.nic.ease_of_living.models.HouseholdEol;
import in.nic.ease_of_living.models.SeccHousehold;
import in.nic.ease_of_living.models.User;
import in.nic.ease_of_living.models.Village;
import in.nic.ease_of_living.models.Weburl;
import in.nic.ease_of_living.supports.IsConnected;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MyDateSupport;
import in.nic.ease_of_living.supports.MySharedPref;
import in.nic.ease_of_living.supports.MyVolley;
import in.nic.ease_of_living.supports.Password;
import in.nic.ease_of_living.utils.AESHelper;
import in.nic.ease_of_living.utils.Common;
import in.nic.ease_of_living.utils.FileUtils;
import in.nic.ease_of_living.utils.ImageFilePath;
import in.nic.ease_of_living.utils.NetworkRegistered;
import in.nic.ease_of_living.utils.PdfOpen;
import in.nic.ease_of_living.utils.PopulateMasterLists;
import in.nic.ease_of_living.utils.Utility;

/*004-089*/
public class Home extends AppCompatActivity implements AdapterView.OnItemClickListener, Communicator {
    Context context;
    private NetworkChangeReceiver mNetworkReceiver;
    private boolean isImport = true;
    private int SELECT_DOCUMENT = 0;
    private ArrayList<String> alName = new ArrayList();
    private ArrayList<Integer> alImage = new ArrayList();
    private ArrayList<Village> alEnuVillage = new ArrayList<>();
    private ArrayList<EnumeratedBlock> alEnuDb = new ArrayList<>();
    private ArrayList<SeccHousehold> alSeccHhd =new ArrayList<>();
    private MenuItem menuItem;
    private ProgressDialog pd;
    private String strUserPassw = null;
    private List<String> listCategories;
    private String strLanguageToLoad = "en";
    private Spinner spinnerDbRole, spinnerGpRole, spinnerEnuVillage, spinnerEnumBlock;
    private ArrayList<GP> alGp;
    private ArrayList<DevelopmentBlock> alDb;
    private DevelopmentBlockAdapter dbAdapter;
    private EnumBlockAdapter enuDbAdapter;
    VillageAdapter villageAdapter;
    private GPAdapter gpAdapter;
    private String strSpinnerDbRoleNameSelected = "", strSpinnerGpRoleNameSelected = "",
            strSpinnerDbRoleNameSlSelected = "", strSpinnerGpRoleNameSlSelected = "",
            strSpinnerDbRoleCodeSelected = "", strSpinnerGpRoleCodeSelected = "",
            strSpinnerEnuVillageNameSlSelected = "", strSpinnerEnuDbNameSlSelected = "",
            strSpinnerEnuVillageNameSelected = "", strSpinnerEnuDbNameSelected = "";
    int iSpinnerVillageSelected = 0, iSpinnerEnumBlockSelected = 0, iSpinnerSubDistrictSelected = 0;
    private Dialog dialogAlert;
    private String strOldLanguage;
    private GridView gvOptions;
    private TextView tvWelcome, tvGpStatus;

    private String fileName = "";
    String strInstruction = "<u><font color = #237DC1>Instruction for Installation</font></u><br>" +
            " - Install the new version of Mission Antyodaya mobile App from Google playstore." +
            "On installing new version, all the previous Mission Antyodaya data (if any) on device will be permanently removed. " +
            "<br> - However, any exported file or PDF will not be deleted.  " +
            "<br><br><u><font color = #237DC1>What’s New?</font></u><br>" +
            "<br> - Enhancements in Questionnaire." +
            "<br> - Capturing the Parliament and Assembly Constituency Details." +
            "<br> - Coverage of all 29 subjects of the 11th schedule in Part-A." +
            "<br> - Introduction of new part-B of questionnaire." +
            "<br> - Saving draft facility at each section of Part A & B." +
            "<br> - The completed section is shown in green colour to identify incomplete sections." +
            "<br> - Minor bug fixing." +
            "<br> - Training mode has been provided for testing purpose and to keep the training data separated." +
            "<br> - Login as another user option has been included." +
            "<br> - Feedback functionality has been added for ease." +
            "<br><br><font color = #237DC1>For GP Users</font>" +
            "<br> - Generation of Village-wise PDF of Survey data." +
            "<br> - Generation of PDF for Draft & Finalised Survey data." +
            "<br> - Uploading scanned file of Gram Panchayat verified and signed survey data." +
            "<br> - In case the base data is downloaded by mistake for any GP, the user can reset the current selection and begin from initial stage. " +
            "<br><br><font color = #237DC1>For Nodal Officers</font>" +
            "<br> - Nodal officer can approve the users upto one level in hierarchy. " +
            "<br> - State nodal officer can approve only district user." +
            "<br> - District nodal officer can approve Block & Gram Panchayat user." +
            "<br> - Block nodal officer can approve Gram Panchayat user." +
            "<br> - For state nodal officers, the district dropdowns will display only those districts where any user is pending for approval. " +
            "<br><br><u><font color = #237DC1>Key Information :-</font></u><br>" +
            "- Mobile  phone number can also be used as user ID for login. <br>" +
            "- Actual One Time Password (OTP) received in SMS will have to be filled as and when asked <br>" +
            "- Forget Password feature is available to reset the Password. <br>" +
            "- Remove Registration facility is provided to remove the registration. <br>" +
            "- PDF can be generated for printing of finalized GP Data. <br>" +
            "- Questions are available in English , Hindi and Bangla languages. <br>" +
            "- After completing and Uploading of one GP, the user can change the GP and internet connectivity is required for this purpose.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            context = this;

            Intent intent = getIntent();
            strUserPassw = intent.getStringExtra("user_password");
            if (Password.matchUserCredentials(context, intent.getStringExtra("user_id"), strUserPassw)) {
                setContentView(R.layout.activity_home);

                //getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getString(R.string.app_name) + "</font>"));
                Common.setAppHeader(context);

                mNetworkReceiver = new NetworkChangeReceiver();
                NetworkRegistered.registerNetworkBroadcastForNougat(context, mNetworkReceiver);
                findView();
                setListener();
                //enumeration();
                pd = new ProgressDialog(context);
                pd.setMessage(context.getString(R.string.please_wait));
                pd.setCancelable(false);

                PackageManager pm = context.getPackageManager();
                int intHasWritePerm = pm.checkPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        context.getPackageName());
                if (intHasWritePerm != PackageManager.PERMISSION_GRANTED) {
                    final int permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if ((permission != PackageManager.PERMISSION_GRANTED))
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                    }
                }

            /* Set Language */
                if (MySharedPref.getLocaleLanguage(context) != null)
                    strLanguageToLoad = MySharedPref.getLocaleLanguage(context);
                Locale locale = new Locale(strLanguageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

                tvWelcome.setText(context.getString(R.string.welcome) + " " + MySharedPref.getCurrentUser(context).getFirst_name() + " !!");
                tvGpStatus.setPaintFlags(tvGpStatus.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                switch (MySharedPref.getCurrentUser(context).getGroup_id()) {
                    case "ADM":
                        alName.clear();
                        alImage.clear();
                        userApproval();
                        tvGpStatus.setVisibility(View.GONE);
                        break;

                    case "NTA":
                        alName.clear();
                        alImage.clear();
                        userApproval();
                        tvGpStatus.setVisibility(View.GONE);
                        break;

                    case "STA":
                        alName.clear();
                        alImage.clear();
                        userApproval();
                        tvGpStatus.setVisibility(View.GONE);
                        break;

                    case "STU":
                        alName.clear();
                        alImage.clear();
                        MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.ma_info), context.getString(R.string.not_authorized), "004-001");
                        tvGpStatus.setVisibility(View.GONE);
                        break;

                    case "DTA":
                        alName.clear();
                        alImage.clear();
                        userApproval();
                        tvGpStatus.setVisibility(View.GONE);
                        break;

                    case "DTU":
                        alName.clear();
                        alImage.clear();
                        MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.ma_info), context.getString(R.string.not_authorized), "004-002");
                        tvGpStatus.setVisibility(View.GONE);
                        break;

                    case "DBA":
                        alName.clear();
                        alImage.clear();
                        userApproval();
                        tvGpStatus.setVisibility(View.GONE);
                        break;

                    case "DBU":
                        alName.clear();
                        alImage.clear();
                        MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.ma_info), context.getString(R.string.not_authorized), "004-003");
                        tvGpStatus.setVisibility(View.GONE);
                        break;

                    case "GPU":
                        alName.clear();
                        alImage.clear();
                        enumeration();
                        //tvGpStatus.setVisibility(View.VISIBLE);
                        tvGpStatus.setVisibility(View.GONE);
                        break;

                    case "ENU":
                        alName.clear();
                        alImage.clear();
                        MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.ma_info), context.getString(R.string.not_authorized), "004-004");
                        tvGpStatus.setVisibility(View.GONE);
                        break;
                }
                gvOptions.setAdapter(new OptionsAdapter(context, alName, alImage));
            }

        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.home_error), e.getMessage(), "004-005");
        }

    }

    private void enumeration() {
        try {
            alName.add(context.getString(R.string.home_option_download_village_data));
            alName.add(context.getString(R.string.home_option_get_data));
            //  alName.add(context.getString(R.string.home_option_add_gp));
            alName.add(context.getString(R.string.home_option_search));

            alName.add(context.getString(R.string.home_option_show_gp));
            alName.add(context.getString(R.string.home_option_upload_data));
            //alName.add(context.getString(R.string.home_option_export));
            //alName.add(context.getString(R.string.home_option_generate_pdf));
            alName.add(context.getString(R.string.home_option_change_gp));
            alName.add(context.getString(R.string.home_option_reset_data));
            alName.add(context.getString(R.string.home_option_mark_village_part_complete));
            //alName.add(context.getString(R.string.home_option_upload_pdf));
          /*  alName.add(context.getString(R.string.home_option_backup));
            alName.add(context.getString(R.string.home_option_import));


            alName.add("");*/
            alImage.add(R.mipmap.icon_verify_user);
            alImage.add(R.mipmap.icon_get_data);
            //  alImage.add(R.mipmap.icon_gram_panchayat);
            alImage.add(R.mipmap.icon_search);

            alImage.add(R.mipmap.icon_show_data);
            alImage.add(R.mipmap.icon_upload_data);
          /*  alImage.add(R.mipmap.icon_export_data);
            alImage.add(R.mipmap.icon_pdf);
         */
            alImage.add(R.mipmap.icon_gram_panchayat);
            alImage.add(R.mipmap.icon_reset);
            alImage.add(R.mipmap.icon_reset);
            //  alImage.add(R.mipmap.icon_pdf_verify);
          /*  alImage.add(R.mipmap.icon_backup);
            alImage.add(R.mipmap.icon_import_data);


            alImage.add(R.mipmap.icon_blank);*/

            PopulateMasterLists.populateMastersList(context);
            /*if (Common.isbIsShowGpDashboard()) {
                ArrayList<GpVillageSurvey> alGpVillageBase = GpVillageBaseController.getAllGp(context, DBHelper.getInstance(context, true));
                if (alGpVillageBase.size() > 0)
                    popupGpDashboard();
                Common.setbIsShowGpDashboard(false);
            }*/
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.home_error), e.getMessage(), "004-006");
        }
    }

    private void userApproval() {
        try {
            alName.add(context.getString(R.string.home_option_user_approval));
            alName.add(context.getString(R.string.home_option_search_user));
            alName.add(context.getString(R.string.home_option_report));
            alName.add("");
            alImage.add(R.mipmap.icon_verify_user);
            alImage.add(R.mipmap.icon_search_users);
            alImage.add(R.mipmap.icon_show_data);
            alImage.add(R.mipmap.icon_blank);
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.home_error), e.getMessage(), "004-007");
        }
    }

    private void findView() {
        try {
            gvOptions = (GridView) findViewById(R.id.gvOptions);
            tvWelcome = (TextView) findViewById(R.id.tvWelcome);
            tvGpStatus = (TextView) findViewById(R.id.tvGpStatus);
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.home_error), e.getMessage(), "004-008");
        }
    }

    private void setListener() {
        try {
            gvOptions.setOnItemClickListener(this);
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.home_error), e.getMessage(), "004-009");
        }
    }

    private void popupSelect_language() {
        try {
            if ((pd != null) && (pd.isShowing()))
                pd.dismiss();
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_select_language);
            Window window = dialog.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(R.color.transparent);

            Spinner spinnerSelectLanguage = (Spinner) dialog.findViewById(R.id.spinnerSelectLanguage);
            // Spinner Drop down elements
            listCategories = new ArrayList<String>();
            listCategories.add("English");
          /*  listCategories.add("Hindi");
            //listCategories.add("Bengali");
            listCategories.add("Malyalam");
            listCategories.add("Odia");
            listCategories.add("Punjabi");
            listCategories.add("Telugu");
            listCategories.add("Tamil");
            listCategories.add("Manipuri");
            listCategories.add("Kannada");*/
            //  listCategories.add("Mizo");


            // Spinner click listener
            spinnerSelectLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    strOldLanguage = strLanguageToLoad;

                    switch (listCategories.get(i).toString().toLowerCase()) {

                        case "english":
                            strLanguageToLoad = "en";
                            break;
                      /*  case "hindi":
                            strLanguageToLoad = "hi";
                            break;
                        case "bengali":
                            strLanguageToLoad = "bn";
                            break;
                        case "malyalam":
                            strLanguageToLoad = "ml";
                            break;
                        case "odia":
                            strLanguageToLoad = "or";
                            break;
                        case "punjabi":
                            strLanguageToLoad = "pa";
                            break;
                        case "telugu":
                            strLanguageToLoad = "te";
                            break;
                        case "tamil":
                            strLanguageToLoad = "ta";

                            break;
                        case "manipuri":
                            strLanguageToLoad = "mni";
                            break;
                        case "kannada":
                            strLanguageToLoad = "kn";
                            break;
                       case "mizo":
                            strLanguageToLoad = "mi";
                            break;*/
                        default:
                            strLanguageToLoad = "en";
                            break;

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listCategories);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinnerSelectLanguage.setAdapter(dataAdapter);

            // set language as selected
            if (MySharedPref.getLocaleLanguage(context) != null)
                strLanguageToLoad = MySharedPref.getLocaleLanguage(context);

            switch (strLanguageToLoad) {
                case "en":
                    spinnerSelectLanguage.setSelection(dataAdapter.getPosition("English"));
                    break;
                /*    case "hi":
                    spinnerSelectLanguage.setSelection(dataAdapter.getPosition("Hindi"));
                    break;
                case "bn":
                    spinnerSelectLanguage.setSelection(dataAdapter.getPosition("Bengali"));
                    break;
                case "ml":
                    spinnerSelectLanguage.setSelection(dataAdapter.getPosition("Malyalam"));
                    break;
                case "or":
                    spinnerSelectLanguage.setSelection(dataAdapter.getPosition("Odia"));
                    break;
                case "pa":
                    spinnerSelectLanguage.setSelection(dataAdapter.getPosition("Punjabi"));

                    break;
                case "te":
                    spinnerSelectLanguage.setSelection(dataAdapter.getPosition("Telugu"));

                    break;
                case "ta":
                    spinnerSelectLanguage.setSelection(dataAdapter.getPosition("Tamil"));

                    break;
                case "mni":
                    spinnerSelectLanguage.setSelection(dataAdapter.getPosition("Manipuri"));

                    break;
                case "kn":
                    spinnerSelectLanguage.setSelection(dataAdapter.getPosition("Kannada"));

                    break;
            case "mi":
                    spinnerSelectLanguage.setSelection(dataAdapter.getPosition("Mizo"));

                    break;*/
            }

            Button btnContinue = (Button) dialog.findViewById(R.id.btnContinue);
            ImageView ivClose = (ImageView) dialog.findViewById(R.id.ivClose);

            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            // if button is clicked, close the custom dialog
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                    Locale locale = new Locale(strLanguageToLoad);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;

                    MySharedPref.saveLocaleLanguage(context, locale);
                    context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
                    if (!(strLanguageToLoad.equalsIgnoreCase(strOldLanguage))) {
                        Intent intent = new Intent(Home.this, Home.class);
                        intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                        intent.putExtra("user_password", strUserPassw);
                        startActivity(intent);
                        finishAffinity();
                    }
                }
            });

            dialog.show();
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.home_error), e.getMessage(), "004-010");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        //return true;
        menuItem = menu.findItem(R.id.action_internet_status);
        if (!IsConnected.isInternet_connected(context, false))
            menuItem.setIcon(R.mipmap.icon_offline_status);
        else
            menuItem.setIcon(R.mipmap.icon_online_status);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        PackageManager pm;
        int intHasWritePerm;


        switch (id) {
            case R.id.action_home:
                startActivity(new Intent(Home.this, Home.class));
                finish();
                break;
            case R.id.action_myprofile:
                intent = new Intent(Home.this, MyProfile.class);
                intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                intent.putExtra("user_password", strUserPassw);
                startActivity(intent);
                break;
            case R.id.action_change_password:
                intent = new Intent(Home.this, ChangePassword.class);
                intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                intent.putExtra("user_password", strUserPassw);
                startActivity(intent);
                break;
            case R.id.action_change_language:
                popupSelect_language();
                break;
            case R.id.action_faq:
                pm = context.getPackageManager();
                intHasWritePerm = pm.checkPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        context.getPackageName());
                if (intHasWritePerm != PackageManager.PERMISSION_GRANTED) {
                    MyAlert.showAlert(context, R.mipmap.icon_warning, context.getString(R.string.generate_pdf_warning), context.getString(R.string.generate_pdf_msg_storage_permission), "004-011");
                } else {
                    PdfOpen.loadPdf(context, "faqs.pdf");
                }

                break;
            case R.id.action_hindi_quessionaire:
                pm = context.getPackageManager();
                intHasWritePerm = pm.checkPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        context.getPackageName());
                if (intHasWritePerm != PackageManager.PERMISSION_GRANTED) {
                    MyAlert.showAlert(context, R.mipmap.icon_warning, context.getString(R.string.generate_pdf_warning), context.getString(R.string.generate_pdf_msg_storage_permission), "004-011");
                } else {
                    PdfOpen.loadPdf(context, "questionnaire_hindi.pdf");
                }

                break;
            case R.id.action_contact_us:
                intent = new Intent(Home.this, ContactUsActivity.class);
                intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                intent.putExtra("user_password", strUserPassw);
                startActivity(intent);
                /*try{
                    (new GeneratePdfFileHtml()).exportDataToHtml(context);
                }catch(Exception e)
                {

                }*/
                break;
            case R.id.action_about_app:
                try {
                    PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                    MyAlert.showAlertLeftAlign(context, R.mipmap.icon_info, context.getString(R.string.app_detail_info),
                            context.getString(R.string.app_detail_name) + packageInfo.applicationInfo.loadLabel(getPackageManager()).toString() +
                                    context.getString(R.string.app_detail_version) + packageInfo.versionName + "\n\n" +
                                    Html.fromHtml(strInstruction), "004-012");
                } catch (Exception e) {
                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_detail_error), e.getMessage(), "004-013");
                }
                break;


            case R.id.action_device_profile:
                MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.device_detail_info),
                        context.getString(R.string.device_detail_version) + Build.VERSION.RELEASE +
                                context.getString(R.string.device_detail_api_level) + Build.VERSION.SDK_INT +
                                context.getString(R.string.device_detail_device) + Build.DEVICE +
                                context.getString(R.string.device_detail_model) + Build.MODEL +
                                context.getString(R.string.device_detail_product) + Build.PRODUCT, "004-014");
                break;
            case R.id.action_support_feedback:


                if ((MySharedPref.getWebUrl(context) != null) && (MySharedPref.getWebUrl(context).size() > 0)) {
                    intent = new Intent(Home.this, FeedbackWebViewActivity.class);
                    intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                    intent.putExtra("user_password", strUserPassw);
                    startActivity(intent);
                } else
                    getSupportedUrl(pd, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                ArrayList<Weburl> alWebUrl = new ArrayList<>();
                                if (jsResponse.getBoolean("status")) {

                                    alWebUrl = new Gson().fromJson(jsResponse.getJSONArray("response").toString(), new TypeToken<List<Weburl>>() {
                                    }.getType());

                                    if (alWebUrl.size() > 0) {
                                        MySharedPref.saveWebUrl(context, alWebUrl);
                                        Intent intent = new Intent(Home.this, FeedbackWebViewActivity.class);
                                        intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                                        intent.putExtra("user_password", strUserPassw);
                                        startActivity(intent);
                                        finish();
                                    } else
                                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_error), getString(R.string.error_server_default), "004-015");

                                    pd.dismiss();

                                } else {
                                    pd.dismiss();
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)), "004-016");
                                }
                            } catch (Exception e) {
                                pd.dismiss();
                            }
                        }
                    });

                break;
            case R.id.action_logout:
                //MySharedPref.saveCurrentUser(context,null);
                startActivity(new Intent(Home.this, LoginActivity.class));
                finishAffinity();
                //doLogout();
                break;
        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // Retreive unsynchronized gp list
        //final ArrayList<GpVillageSurvey> alGpVillageBase = GpVillageUpdatedController.getAllGp(DBHelper.getInstance(context, true));
        try {
            Intent intent;
            Animation animation;
            dialogAlert = new Dialog(context);


             if (alName.get(i).toString().equalsIgnoreCase(getString(R.string.home_option_get_data))) {

                Animation animFadegd = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                view.startAnimation(animFadegd);
                ArrayList<SeccHousehold> alSeccHhdList =  SeccHouseholdController.getHouseholdList(context, DBHelper.getInstance(context, true));
                if(alSeccHhdList.size() > 0)
                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_option_get_data), getString(R.string.base_data_already_downloaded), "004-028");
                else
                    getVillageListThroughWebService();


            } else if (alName.get(i).toString().equalsIgnoreCase(getString(R.string.home_option_show_gp))) {
                 animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                view.startAnimation(animation);

                 alSeccHhd = SeccHouseholdController.getAllData(context, DBHelper.getInstance(context, true));

                 if (alSeccHhd.size() == 0)
                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.show_gp_data_info), context.getString(R.string.show_gp_data_msg_no_data), "004-020");
                else {
                    intent = new Intent(Home.this, ShowGpDataActivity.class);
                    intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                    intent.putExtra("user_password", strUserPassw);
                    startActivity(intent);
                }
            } else if (alName.get(i).toString().equalsIgnoreCase(getString(R.string.home_option_upload_data))) {
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                view.startAnimation(animation);
                uploadDataToServer("UO",false);
            }
            else if (alName.get(i).toString().equalsIgnoreCase(getString(R.string.home_option_export))) {
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                view.startAnimation(animation);

         /*       final ArrayList<GpVillageSurvey> alGpVillageSurveyCompleted = GpVillageUpdatedController.getCompletedVillageList(context, DBHelper.getInstance(context, true));
                final ArrayList<Village> alVillage = GpVillageBaseController.getAllVillage(context, DBHelper.getInstance(context, true));
                final ArrayList<Village> alUninhabitatedVillage = GpVillageBaseController.getUninhabitedVillageList(context, DBHelper.getInstance(context, true));
                alGpVillageBase = GpVillageUpdatedController.getAllGp(context, DBHelper.getInstance(context, true));
                if (alGpVillageBase.size() == 0)
                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.export_info), context.getString(R.string.export_msg_no_data), "004-021");
                else if ((alGpVillageSurveyCompleted.size() + alUninhabitatedVillage.size()) != alVillage.size()) {
                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.export_info), context.getString(R.string.export_info_submit_village), "004-022");
                } else {
                    dialogAlert = new Dialog(context);
                    MyAlert.dialogForCancelOk
                            (context, R.mipmap.icon_warning, context.getString(R.string.export_warning),
                                    context.getString(R.string.export_info_warning_msg),
                                    dialogAlert,
                                    context.getString(R.string.ok),
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialogAlert.dismiss();
                                            try {
                                                PackageManager pm = context.getPackageManager();
                                                int intHasWritePerm = pm.checkPermission(
                                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                        context.getPackageName());
                                                String strBackupResponse = new SQLiteHelper(context).exportSurveyDataToFile(context);
                                                if (strBackupResponse.equalsIgnoreCase("true")) {
                                                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.export_info), context.getString(R.string.export_msg_success), "004-023");
                                                } else if (intHasWritePerm != PackageManager.PERMISSION_GRANTED) {
                                                    final int permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                                    if (permission == PackageManager.PERMISSION_GRANTED) {
                                                        MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.export_info), context.getString(R.string.export_msg_error_export), "004-024");
                                                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                                                    }
                                                } else {
                                                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.export_info), context.getString(R.string.export_msg_error_export), "004-025");
                                                }
                                            } catch (Exception e) {
                                                MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.export_info), e.getMessage(), "004-026");
                                            }
                                        }
                                    },
                                    context.getString(R.string.cancel),
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
                                    }, "004-027");
                }*/
            } else if (alName.get(i).toString().equalsIgnoreCase(getString(R.string.home_option_user_approval))) {
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                view.startAnimation(animation);
                intent = new Intent(Home.this, UserApproval.class);
                intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                intent.putExtra("user_password", strUserPassw);
                startActivity(intent);
            } else if (alName.get(i).toString().equalsIgnoreCase(getString(R.string.home_option_search_user))) {
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                view.startAnimation(animation);
                //intent = new Intent(Home.this, SearchUser.class);
                intent = new Intent(Home.this, HouseholdData.class);
                intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                intent.putExtra("user_password", strUserPassw);
                startActivity(intent);
            } else if (alName.get(i).toString().equalsIgnoreCase(getString(R.string.home_option_report))) {
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                view.startAnimation(animation);
                //intent = new Intent(Home.this,StateReport.class);
                if ((MySharedPref.getWebUrl(context) != null) && (MySharedPref.getWebUrl(context).size() > 0)) {
                    intent = new Intent(Home.this, ReportWebViewActivity.class);
                    intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                    intent.putExtra("user_password", strUserPassw);
                    startActivity(intent);
                } else
                    getSupportedUrl(pd, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                ArrayList<Weburl> alWebUrl = new ArrayList<>();
                                if (jsResponse.getBoolean("status")) {

                                    alWebUrl = new Gson().fromJson(jsResponse.getJSONArray("response").toString(), new TypeToken<List<Weburl>>() {
                                    }.getType());

                                    if (alWebUrl.size() > 0) {
                                        MySharedPref.saveWebUrl(context, alWebUrl);
                                        Intent intent = new Intent(Home.this, ReportWebViewActivity.class);
                                        intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                                        intent.putExtra("user_password", strUserPassw);
                                        startActivity(intent);
                                    } else
                                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_error), getString(R.string.error_server_default), "004-028");

                                    pd.dismiss();

                                } else {
                                    pd.dismiss();
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)), "004-029");
                                }
                            } catch (Exception e) {
                                pd.dismiss();
                            }
                        }
                    });


            /*switch (MySharedPref.getCurrentUser(context).getGroup_id()) {
                case "ADM":
                    intent.putExtra("LEVEL", "S");
                    break;

                case "NTA":
                    intent.putExtra("LEVEL", "S");
                    break;

                case "STA":
                    intent.putExtra("LEVEL", "D");
                    intent.putExtra("SC", MySharedPref.getCurrentUser(context).getPc_code());
                    intent.putExtra("SN", MySharedPref.getCurrentUser(context).getState_name());
                    break;

                case "DTA":
                    intent.putExtra("LEVEL", "B");
                    intent.putExtra("SC", MySharedPref.getCurrentUser(context).getPc_code());
                    intent.putExtra("SN", MySharedPref.getCurrentUser(context).getState_name());
                    intent.putExtra("DC", MySharedPref.getCurrentUser(context).getDistrict_code());
                    intent.putExtra("DN", MySharedPref.getCurrentUser(context).getDistrict_name());
                    break;


            }*/

            } else if (alName.get(i).toString().equalsIgnoreCase(getString(R.string.home_option_change_gp))) {
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                view.startAnimation(animation);

                /*final ArrayList<GpVillageSurvey> alGpVillageSurveyCompletedChangeGp = GpVillageUpdatedController.getCompletedVillageList(context, DBHelper.getInstance(context, true));
                final ArrayList<GpVillageSurvey> alGpVillageSurveyUploadedChangeGp = GpVillageUpdatedController.getUploadedVillageList(context, DBHelper.getInstance(context, true));
                final ArrayList<Village> alVillageChangeGp = GpVillageBaseController.getAllVillage(context, DBHelper.getInstance(context, true));
               // final ArrayList<HouseholdEolEnumerated> alGpUnSyncedListChangeGp = GpVillageUpdatedController.getAllUnSyncedGpToUpload(context, DBHelper.getInstance(context, true));
                final ArrayList<Village> alUninhabitatedVillageChangeGp = GpVillageBaseController.getUninhabitedVillageList(context, DBHelper.getInstance(context, true));
                if ((alGpVillageSurveyUploadedChangeGp.size() + alUninhabitatedVillageChangeGp.size()) != alVillageChangeGp.size()) {
                    //MyAlert.showAlert(context, context.getString(R.string.change_gp_info), "MA-004-021 : " + context.getString(R.string.change_gp_msg_submit_village));
                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.change_gp_info), context.getString(R.string.change_gp_msg_upload_villages), "004-030");
                } *//*else if (alGpUnSyncedListChangeGp.size() > 0) {
                    uploadDataToServer("CGP");
                } *//* else {
                    checkPdfCreatedForChangeGP(alGpVillageSurveyCompletedChangeGp);*/
               // }
            } else if (alName.get(i).toString().equalsIgnoreCase(getString(R.string.home_option_generate_pdf))) {
                /*final ArrayList<GpVillageSurvey> alGpVillageSurveyCompletedGenPdf = GpVillageUpdatedController.getCompletedVillageList(context, DBHelper.getInstance(context, true));
                final ArrayList<GpVillageSurvey> alGpVillageSurveyGenPdf = GpVillageUpdatedController.getAllGp(context, DBHelper.getInstance(context, true));
                final ArrayList<Village> alVillageGenPdf = GpVillageBaseController.getAllVillage(context, DBHelper.getInstance(context, true));
                final ArrayList<Village> alUninhabitatedVillageGenPdf = GpVillageBaseController.getUninhabitedVillageList(context, DBHelper.getInstance(context, true));
                *//*if ((alGpVillageSurveyCompletedGenPdf.size() + alUninhabitatedVillageGenPdf.size()) != alVillageGenPdf.size()) {
                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.generate_pdf_info),  context.getString(R.string.generate_pdf_msg_submit_village),"004-031");
                } else *//*
                if (alGpVillageSurveyGenPdf.size() == 0)
                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.generate_pdf_info), context.getString(R.string.generate_pdf_msg_no_data), "004-032");
                else {
                    PackageManager pm = context.getPackageManager();
                    int intHasWritePerm = pm.checkPermission(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            context.getPackageName());
                    //if(MySharedPref.getLocaleLanguage(context)==null||MySharedPref.getLocaleLanguage(context).equalsIgnoreCase("en")) {
                    if (intHasWritePerm != PackageManager.PERMISSION_GRANTED) {
                        MyAlert.showAlert(context, R.mipmap.icon_warning, context.getString(R.string.generate_pdf_warning), context.getString(R.string.generate_pdf_msg_storage_permission), "004-033");
                    } else {
                        if ((pd == null) || ((pd != null) && (!pd.isShowing()))) {
                            pd.show();
                        }

                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                  //  (new GeneratePdf()).dialogForPDF(pd, context, true);
                                } catch (Exception e) {
                                    pd.dismiss();
                                }

                            }
                        });

                    }
                }*/

            } else if (alName.get(i).toString().equalsIgnoreCase(getString(R.string.home_option_reset_data))) {
                dialogAlert = new Dialog(context);
                MyAlert.dialogForCancelOk
                        (context, R.mipmap.icon_warning, context.getString(R.string.reset_data_warning),
                                context.getString(R.string.reset_data_msg_confirmation),
                                dialogAlert,
                                context.getString(R.string.yes),
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        final Dialog dialogPassword = new Dialog(context);
                                        popupEnterPassword(dialogPassword, new BooleanVariableListener.ChangeListener() {
                                            @Override
                                            public void onChange() {
                                                dialogAlert.dismiss();
                                                dialogPassword.dismiss();
                                                resetData();
                                            }
                                        });
                                        dialogAlert.dismiss();

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
                                }, "004-040");

            } else if (alName.get(i).toString().equalsIgnoreCase(getString(R.string.home_option_mark_village_part_complete))) {
                 // Retreive unsynchronized pop list
                 final ArrayList<HouseholdEol> alHouseholdEolIncomplete = HouseholdEolController.getAllInCompletedHhd(context, DBHelper.getInstance(context, true));
                 final ArrayList<HouseholdEol> alHouseholdEolSurvey = HouseholdEolController.getData(context, DBHelper.getInstance(context, true));
                 final ArrayList<SeccHousehold> alSeccHouseholdPending = SeccHouseholdController.getHouseholdList(context, DBHelper.getInstance(context, true));

                 if(alSeccHouseholdPending.size() > 0)
                 {
                     MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_option_mark_village_part_complete) + context.getString(R.string.error), context.getString(R.string.pending_household_for_survey), "004-029");
                 }
                 else if(alHouseholdEolIncomplete.size() > 0)
                 {
                     MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_option_mark_village_part_complete) + context.getString(R.string.error), context.getString(R.string.incomplete_household), "004-029");
                 }
                 else {
                     dialogAlert = new Dialog(context);
                     MyAlert.dialogForCancelOk
                             (context, R.mipmap.icon_warning, context.getString(R.string.home_option_mark_village_part_complete),
                                     context.getString(R.string.mark_village_part_complete_confirmation),
                                     dialogAlert,
                                     context.getString(R.string.yes),
                                     new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             /*final Dialog dialogPassword = new Dialog(context);
                                             popupEnterPassword(dialogPassword, new BooleanVariableListener.ChangeListener() {
                                                 @Override
                                                 public void onChange() {
                                                     dialogAlert.dismiss();
                                                     dialogPassword.dismiss();
                                                     resetData();
                                                 }
                                             });*/
                                             uploadDataToServer("MC",true);
                                             dialogAlert.dismiss();

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
                                     }, "004-040");
                 }
            } else if (alName.get(i).toString().equalsIgnoreCase(getString(R.string.home_option_backup))) {
                Animation animFadeib = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                view.startAnimation(animFadeib);

                if (new SQLiteHelper(context).checkDataBase()) {
                    if (SeccPopulationController.isEmptyTableExist(DBHelper.getInstance(context, true))) {
                        if (!MySharedPref.getCurrentUser(context).is_enumeration_completed()) {
                            try {
                                String b = new SQLiteHelper(context).backupDatabase(context, true);

                                if (b.equalsIgnoreCase("true")) {
                                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.backup_info), context.getString(R.string.backup_msg_success), "021-011");
                                } else if (b.contains("FileNotFoundException")) {
                                    final int permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                    if (permission == PackageManager.PERMISSION_GRANTED) {
                                        MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.backup_error), context.getString(R.string.backup_msg_failure), "021-012");
                                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                                    }
                                } else {
                                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.backup_error), context.getString(R.string.backup_msg_failure), "021-013");
                                }
                            } catch (Exception e) {

                            }
                        } else
                            MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.add_household_info), context.getString(R.string.house_completed), "021-014");

                    } else
                        MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.backup_info), context.getString(R.string.backup_msg_no_data), "021-015");
                } else
                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.backup_info), context.getString(R.string.backup_msg_no_data), "021-016");

            } else if (alName.get(i).toString().equalsIgnoreCase(getString(R.string.home_option_import))) {
                Animation animFader = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                view.startAnimation(animFader);
                if (new SQLiteHelper(context).checkDataBase()) {
                    if (SeccPopulationController.isTableExist(DBHelper.getInstance(context, true)))
                        MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.home_option_import), context.getString(R.string.msg_data_already_exists), "021-017");
                    else {
                        showFileChooser(context.getString(R.string.home_option_import));
                    }

                } else {
                    showFileChooser(context.getString(R.string.home_option_import));
                }
            } else if (alName.get(i).toString().equalsIgnoreCase(getString(R.string.home_option_upload_pdf))) {
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                view.startAnimation(animation);
                intent = new Intent(Home.this, UploadPdfFile.class);
                intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                intent.putExtra("user_password", strUserPassw);
                startActivity(intent);

            } else if (alName.get(i).toString().equalsIgnoreCase(getString(R.string.home_option_download_village_data))) {
                Animation animFadeisu = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                view.startAnimation(animFadeisu);
                intent = new Intent(Home.this, DownloadVillageDataPdfForGp.class);
                intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                intent.putExtra("user_password", strUserPassw);
                intent.putExtra("activity_name", getString(R.string.home_option_download_village_data));
                startActivity(intent);

            } else    if( alName.get(i).toString().equalsIgnoreCase(getString(R.string.home_option_search)))
            {
                Animation animFadeam = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                view.startAnimation(animFadeam);

                if (new SQLiteHelper(context).checkDataBase()) {
                    if (SeccPopulationController.isEmptyTableExist(DBHelper.getInstance(context, true))) {
                        if (SeccPopulationController.isTableExist(DBHelper.getInstance(context, true)))
                        {

                            final ArrayList<HouseholdEol> alHhdEol = HouseholdEolController.getAllInCompletedHhd(context, DBHelper.getInstance(context, false));

                            if (alHhdEol.size() > 0) {
                                final Dialog dialog_alert = new Dialog(context);
                                MyAlert.dialogForOk
                                        (context,R.mipmap.icon_info, context.getString(R.string.add_household_warning),
                                                context.getString(R.string.household_enumeration_pending),
                                                dialog_alert,
                                                context.getString(R.string.ok),
                                                new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        dialog_alert.dismiss();
                                                        Intent intent = new Intent(Home.this, ResidentMember.class);
                                                        intent.putExtra("HH_UID", alHhdEol.get(0).getHhd_uid());
                                                        intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                                                        intent.putExtra("user_password", strUserPassw);
                                                        startActivity(intent);
                                                    }
                                                }, "021-001");

                            } else if (!MySharedPref.getCurrentUser(context).is_enumeration_completed()) {

                                intent = new Intent(Home.this, SearchResident.class);
                                intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                                intent.putExtra("user_password", strUserPassw);
                                startActivity(intent);

                            } else
                                MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.add_household_info), context.getString(R.string.house_completed), "021-002");
                        }else
                            MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.search_resident_info), context.getString(R.string.search_msg_base_data_empty), "021-063");
                    } else
                        MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.search_resident_info), context.getString(R.string.search_msg_no_data), "021-002");

                } else
                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.search_resident_info), context.getString(R.string.search_msg_no_data), "021-003");
            }
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.home_error), e.getMessage(), "004-034");
        }
    }

    /* Get Supported url*/
    private void getSupportedUrl(ProgressDialog pd, Response.Listener<JSONObject> volleyResponseListener) {
        try {
            if ((pd == null) || ((pd != null) && (!pd.isShowing())))
                pd = new ProgressDialog(context);
            Map<String, String> headers = new HashMap<String, String>();
            String strFinalToken = MySharedPref.getAuthorizationToken(context).getToken_type() + " " + MySharedPref.getAuthorizationToken(context).getAccess_token();
            headers.put("Authorization", strFinalToken);

            JSONObject jsRequest = new JSONObject();

            MyVolley.callWebServiceUsingVolley(Request.Method.GET, pd, context, "017", false,
                    true, "misc/v1/getConfigurationM", jsRequest, context.getString(R.string.login_error),
                    volleyResponseListener);
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.home_error), e.getMessage(), "004-035");
        }
    }

/*
    private void checkPdfCreatedForChangeGP(final ArrayList<GpVillageSurvey> alGpVillageSurveyCompletedChangeGp) {
        try {
            if (alGpVillageSurveyCompletedChangeGp.size() > 0) {
                PackageManager pm = context.getPackageManager();
                int intHasWritePerm = pm.checkPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        context.getPackageName());
                if (intHasWritePerm != PackageManager.PERMISSION_GRANTED) {
                    MyAlert.showAlert(context, R.mipmap.icon_warning, context.getString(R.string.generate_pdf_warning), context.getString(R.string.generate_pdf_msg_storage_permission), "004-036");
                } else {
                    if ((pd == null) || ((pd != null) && (!pd.isShowing())))
                        pd = new ProgressDialog(context);
                    final ArrayList<GpVillageSurvey> alGpVillageSurveyCompletedGenPdf = GpVillageUpdatedController.getCompletedVillageList(context, DBHelper.getInstance(context, true));
                    String strFileName = MySharedPref.getCurrentUser(context).getGp_name() + "_" + alGpVillageSurveyCompletedGenPdf.get(0).getGp_code() + ".pdf";
                 //   (new GeneratePdf()).checkFontFileExistence(pd, context, alGpVillageSurveyCompletedChangeGp, false, strFileName, "");
                }

                File path = Environment.getExternalStorageDirectory();
                File folder = new File(path + "/" + "GP_Pdf");

                try {
                    final Dialog dialogAlert = new Dialog(context);
                    MyAlert.dialogForCancelOk
                            (context, R.mipmap.icon_warning, context.getString(R.string.change_gp_warning),
                                    context.getString(R.string.change_gp_message_confirmation),
                                    dialogAlert,
                                    context.getString(R.string.yes),
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialogAlert.dismiss();
                                            //changeGPDialog();
                                            getDevelopementblock();
                                            dialogAlert.dismiss();
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
                                    }, "004-037");
                } catch (Exception e) {
                    MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.home_error), e.getMessage(), "004-038");
                    dialogAlert.dismiss();
                }
            } else
                getDevelopementblock();
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.home_error), e.getMessage(), "004-039");
        }
    }
*/

    private void changeGPDialog() {
        try {
            final Dialog dialogChangeGp = new Dialog(context);
            dialogChangeGp.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogChangeGp.setContentView(R.layout.layout_change_gp);
            Window window = dialogChangeGp.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(R.color.transparent);

            spinnerDbRole = (Spinner) dialogChangeGp.findViewById(R.id.spinnerDbRole);
            spinnerGpRole = (Spinner) dialogChangeGp.findViewById(R.id.spinnerGpRole);
            Button btnContinue = (Button) dialogChangeGp.findViewById(R.id.btnContinue);
            ImageView ivClose = (ImageView) dialogChangeGp.findViewById(R.id.ivClose);

            spinnerDbRole.setAdapter(dbAdapter);
            strSpinnerDbRoleCodeSelected = "";

            // if button is clicked, close the custom dialog
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (strSpinnerDbRoleCodeSelected.isEmpty()) {
                        MyAlert.showAlert(context, R.mipmap.icon_warning, context.getString(R.string.change_gp_warning), context.getString(R.string.select_db), "004-041");
                    } else if (strSpinnerGpRoleCodeSelected.isEmpty()) {
                        MyAlert.showAlert(context, R.mipmap.icon_warning, context.getString(R.string.change_gp_warning), context.getString(R.string.select_gp), "004-042");
                    } else
                        doChangeGP(dialogChangeGp, strSpinnerDbRoleCodeSelected, strSpinnerGpRoleCodeSelected);
                }
            });

            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogChangeGp.dismiss();
                }
            });

            spinnerDbRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerGpRole.setAdapter(null);
                    strSpinnerDbRoleCodeSelected = "";
                    strSpinnerDbRoleNameSelected = "";
                    strSpinnerDbRoleNameSlSelected = "";
                    if (i == 0) {
                        strSpinnerDbRoleCodeSelected = "";
                        strSpinnerDbRoleNameSelected = "";
                        strSpinnerDbRoleNameSlSelected = "";
                    } else {
                        DevelopmentBlock dpVlaue = dbAdapter.getItem(i);
                        strSpinnerDbRoleCodeSelected = dpVlaue.getBlock_code();
                        strSpinnerDbRoleNameSelected = dpVlaue.getBlock_name();
                        strSpinnerDbRoleNameSlSelected = dpVlaue.getBlock_name_sl();
                        getGP();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerGpRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    if (i == 0) {
                        strSpinnerGpRoleCodeSelected = "";
                        strSpinnerGpRoleNameSelected = "";
                        strSpinnerGpRoleNameSlSelected = "";
                    } else {
                        GP gp = gpAdapter.getItem(i);
                        strSpinnerGpRoleCodeSelected = gp.getGp_code();
                        strSpinnerGpRoleNameSelected = gp.getGp_name();
                        strSpinnerGpRoleNameSlSelected = gp.getGp_name_sl();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            dialogChangeGp.show();
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.home_error), e.getMessage(), "004-043");
        }
    }

    private void getBaseDataDialog() {
        try {
            final Dialog dialogGetData = new Dialog(context);
            dialogGetData.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogGetData.setContentView(R.layout.layout_get_enu_block);
            Window window = dialogGetData.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(R.color.transparent);

            spinnerEnuVillage = (Spinner) dialogGetData.findViewById(R.id.spinnerEnuVillage);
            spinnerEnumBlock = (Spinner) dialogGetData.findViewById(R.id.spinnerEnuDb);
            Button btnContinue = (Button) dialogGetData.findViewById(R.id.btnContinue);
            ImageView ivClose = (ImageView) dialogGetData.findViewById(R.id.ivClose);
            TextView tvHeader = (TextView) dialogGetData.findViewById(R.id.tvHeader);
            spinnerEnuVillage.setAdapter(villageAdapter);

            iSpinnerVillageSelected = 0;


            // if button is clicked, close the custom dialog
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iSpinnerVillageSelected == 0) {
                        MyAlert.showAlert(context, R.mipmap.icon_warning, context.getString(R.string.home_option_get_data), context.getString(R.string.select_village), "004-041");
                    } else if (iSpinnerEnumBlockSelected == 0) {
                        MyAlert.showAlert(context, R.mipmap.icon_warning, context.getString(R.string.home_option_get_data), context.getString(R.string.select_enum_block), "004-042");
                    } else {
                        getBaseDataUsingWebService(dialogGetData, iSpinnerSubDistrictSelected, iSpinnerVillageSelected, iSpinnerEnumBlockSelected);

                        /*if (new SQLiteHelper(context).checkDataBase()) {

                            if (*//*MySharedPref.getIsGetDataDone(context) &&*//* SeccPopulationController.isEmptyTableExist(DBHelper.getInstance(context, true))) {
                                getHouseholdStatus(true, dialogGetData, iSpinnerEnumBlockSelected);

                            } else {
                                getBaseDataUsingWebService(dialogGetData, iSpinnerEnumBlockSelected);
                            }

                        } else {
                            getBaseDataUsingWebService(dialogGetData, iSpinnerEnumBlockSelected);

                        }*/


                    }

                }
            });

            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogGetData.dismiss();
                    pd.dismiss();
                }
            });

            spinnerEnuVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerEnumBlock.setAdapter(null);
                    iSpinnerVillageSelected = 0;
                    strSpinnerEnuVillageNameSelected = "";
                    strSpinnerEnuVillageNameSlSelected = "";
                    if (i == 0) {
                        iSpinnerVillageSelected = 0;
                        strSpinnerEnuVillageNameSelected = "";
                        strSpinnerEnuVillageNameSlSelected = "";
                    } else {
                        Village village = villageAdapter.getItem(i);
                        iSpinnerVillageSelected = village.getVillage_code();
                        strSpinnerEnuVillageNameSelected = village.getVillage_name();
                        strSpinnerEnuVillageNameSlSelected = village.getVillage_name_sl();
                        getEnumBlockListThroughWebService(village.getSub_district_code(), iSpinnerVillageSelected);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerEnumBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    if (i == 0) {
                        iSpinnerEnumBlockSelected = 0;
                        strSpinnerEnuDbNameSelected = "";
                        strSpinnerEnuDbNameSlSelected = "";
                    } else {
                        EnumeratedBlock enuDb = enuDbAdapter.getItem(i);
                        iSpinnerEnumBlockSelected = enuDb.getEnum_block_code();
                        strSpinnerEnuDbNameSelected = enuDb.getEnum_block_name();
                        iSpinnerSubDistrictSelected = enuDb.getSub_district_code();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            dialogGetData.show();
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.home_error), e.getMessage(), "004-043");
        }
    }

    /* Function to call webservice to change Gram Panchayat */
    private void doChangeGP(final Dialog dialogChangeGp, final String strSpinnerDbRoleCodeSelected, final String strSpinnerGpRoleCodeSelected) {
        try {
            if ((pd == null) || ((pd != null) && (!pd.isShowing())))
                pd = new ProgressDialog(context);

            JSONObject jsRequest = new JSONObject();
            jsRequest.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
            jsRequest.put("state_code", MySharedPref.getCurrentUser(context).getState_code());
            jsRequest.put("district_code", MySharedPref.getCurrentUser(context).getDistrict_code());
            jsRequest.put("block_code", strSpinnerDbRoleCodeSelected);
            jsRequest.put("gp_code", strSpinnerGpRoleCodeSelected);

            MyVolley.callWebServiceUsingVolley(Request.Method.PUT, pd, context, "018", false, true,
                    "location/management/v1/changeGP", jsRequest, getString(R.string.change_gp),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                pd.dismiss();
                                if (jsResponse.getBoolean("status")) {

                                    new SQLiteHelper(context).deleteAll(context, DBHelper.getInstance(context, true));

                                    User u = MySharedPref.getCurrentUser(context);
                                    u.setBlock_code(Integer.parseInt(strSpinnerDbRoleCodeSelected));
                                    u.setBlock_name(strSpinnerDbRoleNameSelected);
                                    u.setBlock_name_sl(strSpinnerDbRoleNameSlSelected);
                                    u.setGp_code(Integer.parseInt(strSpinnerGpRoleCodeSelected));
                                    u.setGp_name(strSpinnerGpRoleNameSelected);
                                    u.setGp_name_sl(strSpinnerDbRoleNameSlSelected);
                                    u.setB_isPdfGenerated(false);

                                    MySharedPref.saveCurrentUser(context, u);

                                    final Dialog dialogAlert = new Dialog(context);
                                    MyAlert.dialogForOk
                                            (context, R.mipmap.icon_info, context.getString(R.string.change_gp_info),
                                                    context.getString(R.string.change_gp_msg_success),
                                                    dialogAlert,
                                                    context.getString(R.string.ok),
                                                    new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            dialogAlert.dismiss();
                                                            dialogChangeGp.dismiss();
                                                        }
                                                    }, "004-044");

                                } else {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_gp_error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)), "004-045");
                                }

                            } catch (Exception e) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_gp_error), e.getMessage(), "004-046");
                            }
                        }
                    });
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_gp_error), e.getMessage(), "004-047");
        }
    }




/*
    private void doGetEnuData(final Dialog dialogChangeGp, final String strSpinnerDbRoleCodeSelected, final String strSpinnerGpRoleCodeSelected) {
        try {
            if ((pd == null) || ((pd != null) && (!pd.isShowing())))
                pd = new ProgressDialog(context);

             if (new SQLiteHelper(context).checkDataBase()) {

            if (MySharedPref.getIsGetDataDone(context) && SeccPopulationController.isEmptyTableExist(DBHelper.getInstance(context, true)))
            {
                getVillageStatus(true);

            }
            else {

                getVillageData();
            }

        } else {

            getVillageData();
        }

      */
/*      MyVolley.callWebServiceUsingVolley(Request.Method.PUT, pd, context,"018", false, true,
                    "location/managementv1/changeGP", jsRequest, getString(R.string.change_gp),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                pd.dismiss();
                                if (jsResponse.getBoolean("status")) {

                                    new SQLiteHelper(context).deleteAll(context, DBHelper.getInstance(context, true));

                                    User u = MySharedPref.getCurrentUser(context);
                                    u.setBlock_code(Integer.parseInt(strSpinnerDbRoleCodeSelected));
                                    u.setBlock_name(strSpinnerDbRoleNameSelected);
                                    u.setBlock_name_sl(strSpinnerDbRoleNameSlSelected);
                                    u.setGp_code(Integer.parseInt(strSpinnerGpRoleCodeSelected));
                                    u.setGp_name(strSpinnerGpRoleNameSelected);
                                    u.setGp_name_sl(strSpinnerDbRoleNameSlSelected);
                                    u.setB_isPdfGenerated(false);

                                    MySharedPref.saveCurrentUser(context, u);

                                    final Dialog dialogAlert = new Dialog(context);
                                    MyAlert.dialogForOk
                                            (context, R.mipmap.icon_info, context.getString(R.string.change_gp_info),
                                                    context.getString(R.string.change_gp_msg_success),
                                                    dialogAlert,
                                                    context.getString(R.string.ok),
                                                    new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            dialogAlert.dismiss();
                                                            dialogChangeGp.dismiss();
                                                        }
                                                    }, "004-044");

                                } else {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_gp_error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)), "004-045");
                                }

                            } catch (Exception e) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_gp_error), e.getMessage(), "004-046");
                            }
                        }
                    });*//*

        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_gp_error), e.getMessage(), "004-047");
        }
    }
*/


    /* Function to call webservice get Dbs */
    private void getDevelopementblock() {
        try {
            if ((pd == null) || ((pd != null) && (!pd.isShowing())))
                pd = new ProgressDialog(context);

            JSONObject jsRequest = new JSONObject();
            jsRequest.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
            strSpinnerDbRoleCodeSelected = "";
            MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context, "019", false, true,
                    "location/management/v1/getPendingDbList", jsRequest, getString(R.string.change_gp),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {

                                pd.dismiss();

                                if (jsResponse.getBoolean("status")) {
                                    alDb = new Gson().fromJson(jsResponse.getJSONArray("response").toString(), new TypeToken<List<DevelopmentBlock>>() {
                                    }.getType());
                                    alDb.add(0, new DevelopmentBlock(context.getString(R.string.spinner_heading_db)));

                                    dbAdapter = new DevelopmentBlockAdapter(Home.this,
                                            android.R.layout.simple_spinner_item,
                                            alDb);

                                    changeGPDialog();
                                } else {
                                    strSpinnerDbRoleCodeSelected = "";
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_gp_error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)), "004-048");
                                }


                            } catch (Exception e) {
                                //spinnerDbRole.setAdapter(null);
                                strSpinnerDbRoleCodeSelected = "";
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_gp_error), e.getMessage(), "004-049");
                            }
                        }
                    });
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_gp_error), e.getMessage(), "004-050");
        }
    }

    /* Function to call webservice to get GPs */
    private void getGP() {
        try {
            if ((pd == null) || ((pd != null) && (!pd.isShowing())))
                pd = new ProgressDialog(context);

            JSONObject jsRequest = new JSONObject();
            jsRequest.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
            jsRequest.put("block_code", strSpinnerDbRoleCodeSelected);

            spinnerGpRole.setAdapter(null);
            strSpinnerGpRoleCodeSelected = "";
            MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context, "020", false, true,
                    "location/management/v1/getPendingGPList", jsRequest, getString(R.string.change_gp),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                pd.dismiss();
                                if (jsResponse.getBoolean("status")) {
                                    alGp = new Gson().fromJson(jsResponse.getJSONArray("response").toString(), new TypeToken<List<GP>>() {
                                    }.getType());
                                    alGp.add(0, new GP(context.getString(R.string.spinner_heading_gp)));

                                    gpAdapter = new GPAdapter(Home.this,
                                            android.R.layout.simple_spinner_item,
                                            alGp);

                                    spinnerGpRole.setAdapter(gpAdapter);


                                } else {
                                    spinnerGpRole.setAdapter(null);
                                    strSpinnerGpRoleCodeSelected = "";
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_gp_error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)), "004-051");
                                }


                            } catch (Exception e) {
                                strSpinnerGpRoleCodeSelected = "";
                                spinnerGpRole.setAdapter(null);
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_gp_error), e.getMessage(), "004-052");
                            }
                        }
                    });
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_gp_error), e.getMessage(), "004-053");
        }
    }


    private void getVillageListThroughWebService() {
        try {
            if ((pd == null) || ((pd != null) && (!pd.isShowing())))
                pd = new ProgressDialog(context);

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

                                if (jsResponse.getBoolean("status")) {
                                    alEnuVillage = new Gson().fromJson(jsResponse.getJSONArray("response").toString(), new TypeToken<List<Village>>() {
                                    }.getType());
                                    alEnuVillage.add(0, new Village(context.getString(R.string.spinner_heading_village)));

                                    villageAdapter = new VillageAdapter(Home.this,
                                            android.R.layout.simple_spinner_item,
                                            alEnuVillage);

                                    getBaseDataDialog();
                                } else {
                                    iSpinnerVillageSelected = 0;
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_option_get_data), jsResponse.getString(context.getString(R.string.web_service_message_identifier)), "004-048");
                                }
                            } catch (Exception e) {
                                iSpinnerVillageSelected = 0;
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
            if ((pd == null) || ((pd != null) && (!pd.isShowing())))
                pd = new ProgressDialog(context);

            JSONObject jsRequest = new JSONObject();
            jsRequest.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
            jsRequest.put("state_code", Integer.toString(MySharedPref.getCurrentUser(context).getState_code()));
            jsRequest.put("district_code", Integer.toString(MySharedPref.getCurrentUser(context).getDistrict_code()));
            jsRequest.put("sub_district_code", iSubDistrictCode);
            jsRequest.put("block_code", Integer.toString(MySharedPref.getCurrentUser(context).getBlock_code()));
            jsRequest.put("gp_code", Integer.toString(MySharedPref.getCurrentUser(context).getGp_code()));
            jsRequest.put("village_code", iVillageCode);


           /* jsRequest.put("user_id", "b7b6e4fd-6a04-479b-bd6f-6226edf1f747");
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
                                pd.dismiss();
                                Log.d(TAG, "onResponse:DB jsResponse " + jsResponse.toString());
                                if (jsResponse.getBoolean("status")) {
                                    alEnuDb = new Gson().fromJson(jsResponse.getJSONArray("response").toString(), new TypeToken<List<EnumeratedBlock>>() {
                                    }.getType());
                                    alEnuDb.add(0, new EnumeratedBlock(context.getString(R.string.spinner_heading_db)));

                                    enuDbAdapter = new EnumBlockAdapter(Home.this,
                                            android.R.layout.simple_spinner_item,
                                            alEnuDb);

                                    spinnerEnumBlock.setAdapter(enuDbAdapter);
                                } else {
                                    iSpinnerEnumBlockSelected = 0;
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_option_get_data), jsResponse.getString(context.getString(R.string.web_service_message_identifier)), "004-048");
                                }
                            } catch (Exception e) {
                                iSpinnerEnumBlockSelected = 0;
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
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == SELECT_DOCUMENT)
                    onDocumentResult(data);
            }
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.home_error), e.getMessage(), "004-054");
        }
    }

    private String TAG = "FileUpload";
    private static File file;
    private static Uri fileuri;


    private void onDocumentResult(Intent data) {
        try {

            if (data == null) {
                MyAlert.showAlert(context, R.mipmap.icon_warning, context.getString(R.string.import_warning), context.getString(R.string.import_msg_no_data), "021-052");
                return;
            }

            fileuri = data.getData();
            String filePath = ImageFilePath.getPath(context, fileuri);
            file = new File(filePath);
            fileName = file.getName();
            if (Utility.checkPermission(context)) {
                File folder = new File(Constants.FOLDER_IMPORT);
                if (!folder.exists())
                    folder.mkdirs();
                String outFileName = folder.getAbsolutePath() + file.separator + "inputFile";

                FileUtils.unzip(context, filePath, outFileName, isImport, true, false);

            } else
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.import_error), context.getString(R.string.msg_storage_permissions), "021-053");


        } catch (Exception e) {

        }
    }

    /* Reset data*/
    private void resetData() {
        try {
            new SQLiteHelper(context).deleteAll(context, DBHelper.getInstance(context, true));
            final Dialog dialogAlert = new Dialog(context);
            MyAlert.dialogForOk
                    (context, R.mipmap.icon_info, context.getString(R.string.reset_data_info),
                            context.getString(R.string.reset_data_msg_success),
                            dialogAlert,
                            context.getString(R.string.ok),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogAlert.dismiss();
                                }
                            }, "004-064");
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_error), e.getMessage(), "004-057");
        }
    }

    /* Functionality to sync local device data to server */
    private void uploadDataToServer(final String strCalledFrom, Boolean isMarkComplete) {
        try {
            if ((pd == null) || ((pd != null) && (!pd.isShowing())))
                pd = new ProgressDialog(context);

            // Retreive unsynchronized pop list
            final ArrayList<HouseholdEolEnumerated> alHouseholdEolEnumerated = HouseholdEolController.getAllUnSyncedHhdToUpload(context, DBHelper.getInstance(context, true), isMarkComplete);
            final ArrayList<HouseholdEol> alHouseholdEolSurvey = HouseholdEolController.getAllUnSyncedHhdToUpdateUploadStatus(context, DBHelper.getInstance(context, true), isMarkComplete);


            if (alHouseholdEolEnumerated.size() == 0)
                MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.sync_info), context.getString(R.string.sync_msg_no_data), "004-058");
            else {

                JSONObject jsRequest = new JSONObject();
                jsRequest.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                jsRequest.put("b_file_name", MySharedPref.getCurrentUser(context).getB_file_name());
                jsRequest.put("state_code", MySharedPref.getCurrentUser(context).getState_code());
                jsRequest.put("district_code", MySharedPref.getCurrentUser(context).getDistrict_code());
                jsRequest.put("sub_district_code", MySharedPref.getCurrentUser(context).getSub_district_code());
                jsRequest.put("block_code", MySharedPref.getCurrentUser(context).getBlock_code());
                jsRequest.put("gp_code", MySharedPref.getCurrentUser(context).getGp_code());
                jsRequest.put("village_code", MySharedPref.getCurrentUser(context).getVillage_code());
                jsRequest.put("enum_block_code", MySharedPref.getCurrentUser(context).getEnum_block_code());
                jsRequest.put("is_village_part_completed", isMarkComplete);

                /*jsRequest.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                jsRequest.put("state_code", 2);
                jsRequest.put("district_code", 24);
                jsRequest.put("b_file_name","00010084_0022677_0001_b_201911151759133899");
                jsRequest.put("sub_district_code", 168);
                jsRequest.put("block_code", 198);
                jsRequest.put("gp_code", 10084);
                jsRequest.put("village_code", 22677);
                jsRequest.put("enum_block_code", 1);*/
                Gson gson = new Gson();

                String strGpHhdEnumerated = gson.toJson(
                        alHouseholdEolEnumerated,
                        new TypeToken<ArrayList<HouseholdEolEnumerated>>() {
                        }.getType());

                JSONArray ja = new JSONArray(strGpHhdEnumerated);

                jsRequest.put("households", ja);

                MyVolley.callWebServiceUsingVolleyWithHeaders(Request.Method.POST, pd, context, "021", true, true,
                        "eb/v1/uploadHouseholdData", jsRequest, getString(R.string.sync_error),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsResponse) {
                                try {
                                    pd.dismiss();
                                    if (jsResponse.getBoolean("status")) {

                                        String strResponse = jsResponse.getString("response");
                                        /*String[] keyValuePairs = (strResponse.split("\\{")[1].split("\\}")[0]).split(",");
                                        Map<String, String> map = new HashMap<>();

                                        for (String pair : keyValuePairs)                        //iterate over the pairs
                                        {
                                            String[] entry = pair.split("=");                   //split the pairs to get key and value
                                            map.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap and trim whitespaces
                                        }*/

                                        /*for (int nGpIndex = 0; nGpIndex < alHouseholdEolSurvey.size(); nGpIndex++) {
                                            if ((map.get(String.valueOf(alHouseholdEolSurvey.get(nGpIndex).getVillage_code())) != null) && (map.get(String.valueOf(alHouseholdEolSurvey.get(nGpIndex).getVillage_code())).contains("successfully received"))) {
                                                alHouseholdEolSurvey.get(nGpIndex).setIs_synchronized(1);
                                                alHouseholdEolSurvey.get(nGpIndex).setDt_sync(MyDateSupport.getCurrentDateTimefordatabaseStorage());
                                            } else if ((map.get(String.valueOf(alHouseholdEolSurvey.get(nGpIndex).getVillage_code())) != null) && (map.get(String.valueOf(alHouseholdEolSurvey.get(nGpIndex).getVillage_code())).contains("already uploaded"))) {
                                                if (alHouseholdEolSurvey.get(nGpIndex).getIs_uploaded() != 1) {
                                                    alHouseholdEolSurvey.get(nGpIndex).setIs_uploaded(1);
                                                    alHouseholdEolSurvey.get(nGpIndex).setTs_updated("");
                                                    *//*alHouseholdEolSurvey.get(nGpIndex).setUploaded_from_app_or_portal("");
                                                    alHouseholdEolSurvey.get(nGpIndex).setUploaded_by("");*//*

                                                }
                                            }

                                        }*/
                                        for (int iHhdIndex = 0; iHhdIndex < alHouseholdEolSurvey.size(); iHhdIndex++) {
                                            alHouseholdEolSurvey.get(iHhdIndex).setIs_synchronized(1);
                                            alHouseholdEolSurvey.get(iHhdIndex).setDt_sync(MyDateSupport.getCurrentDateTimefordatabaseStorage());
                                        }


                                         /*Set synchronized value to true in Gram Panchayat Tables */
                                        HouseholdEolController.updateHhdList(context, DBHelper.getInstance(context, true), alHouseholdEolSurvey);

                                        /* Add record in Update download table*/
                                        if (strCalledFrom.equalsIgnoreCase("UO"))   /* Upload Online*/ {
                                            MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.records_added_successfully), jsResponse.getString(context.getString(R.string.web_service_message_identifier)), "004-059");
                                        } else if (strCalledFrom.equalsIgnoreCase("CGP")) /* Change GP*/ {
                               /*             ArrayList<GpVillageSurvey> alGpVillageSurveyCompletedChangeGp = GpVillageUpdatedController.getCompletedVillageList(context, DBHelper.getInstance(context, true));
                                            checkPdfCreatedForChangeGP(alGpVillageSurveyCompletedChangeGp);
                              */          }else if (strCalledFrom.equalsIgnoreCase("MC")) /* Change GP*/ {
                                            MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.home_option_mark_village_part_complete), jsResponse.getString(context.getString(R.string.web_service_message_identifier)), "004-059");
                                            MySharedPref.saveIsEnumBlockMarkCompleted(context, true);
                                        }
                                    } else {
                                        if (strCalledFrom.equalsIgnoreCase("UO"))
                                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.sync_error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)), "004-060");
                                        else if (strCalledFrom.equalsIgnoreCase("CGP"))
                                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.change_gp_error),
                                                    context.getString(R.string.change_gp_msg_failure), "004-061");
                                        else if (strCalledFrom.equalsIgnoreCase("MC"))
                                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_option_mark_village_part_complete),
                                                    context.getString(R.string.mark_complete_msg_failure), "004-061");
                                    }

                                } catch (Exception e) {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.sync_error), e.getMessage(), "004-062");
                                }
                            }
                        });
            }
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.sync_error), e.getMessage(), "004-063");
        }
    }


    /* Function to update base data*/
   /* private void updateBaseData(final ArrayList<GpVillageSurvey> alGpVillageStatus,
                                final ArrayList<GpVillageSurvey> alGpVillageFinalData,
                                final ArrayList<GpVillageSurvey> alGpVillageBase) {
        try {
            int n_gpSize = alGpVillageBase.size();
            int n_row = -1;

            ArrayList<GpVillageSurvey> alGpVillageBaseOld = GpVillageBaseController.getAllGp(context, DBHelper.getInstance(context, true));
            Boolean bOldListModified = false;
            final String strDate = MyDateSupport.getCurrentDateTimefordatabaseStorage();

            if (alGpVillageBase.size() > 0) {
                // No previous data exists
                if (alGpVillageBaseOld.size() <= 0) {

                    GpVillageBaseController.insertAll(context, DBHelper.getInstance(context, true), alGpVillageBase, strDate);
                    bOldListModified = true;
                } else    // Village base data already exists
                {
                    // Check villages for deletion from old list
                    for (int iGpIndexOld = 0; iGpIndexOld < alGpVillageBaseOld.size(); iGpIndexOld++) {
                        Boolean bVillageFound = false;
                        for (int iGpIndex = 0; iGpIndex < alGpVillageBase.size(); iGpIndex++) {
                            // If data from old list mismatch with new list -- remove the data
                            if (!bVillageFound && (alGpVillageBaseOld.get(iGpIndexOld).getVillage_code().equals(alGpVillageBase.get(iGpIndex).getVillage_code()))
                                    && (alGpVillageBaseOld.get(iGpIndexOld).getChecksum().equalsIgnoreCase(alGpVillageBase.get(iGpIndex).getChecksum()))) {
                                bVillageFound = true;
                            }
                        }
                        if (!bVillageFound) {
                            // If old village is not found in new list, delete the village from base data and enumerated data.
                            GpVillageBaseController.deleteGPRow(context, DBHelper.getInstance(context, true), alGpVillageBaseOld.get(iGpIndexOld).getVillage_code());
                            GpVillageUpdatedController.deleteGPRow(context, DBHelper.getInstance(context, true), alGpVillageBaseOld.get(iGpIndexOld).getVillage_code());
                            bOldListModified = true;
                        }
                    }

                    // Check villages for addition from new list
                    for (int iGpIndex = 0; iGpIndex < alGpVillageBase.size(); iGpIndex++) {
                        Boolean bVillageFound = false;
                        for (int iGpIndexOld = 0; iGpIndexOld < alGpVillageBaseOld.size(); iGpIndexOld++) {
                            // If data from old list mismatch with new list -- remove the data
                            if (!bVillageFound && (alGpVillageBaseOld.get(iGpIndexOld).getVillage_code().equals(alGpVillageBase.get(iGpIndex).getVillage_code()))) {
                                bVillageFound = true;
                            }
                        }
                        if (!bVillageFound) {
                            // If new village is found in new list, insert it to old list.
                            GpVillageBaseController.insertGPData(context, DBHelper.getInstance(context, true), alGpVillageBase.get(iGpIndex), strDate);
                            bOldListModified = true;
                        }
                    }
                }
            }
            n_row = GpVillageBaseController.getRowsCount(context, DBHelper.getInstance(context, true));

            //update final data
            if (alGpVillageFinalData.size() > 0) {
                GpVillageUpdatedController.resetGpList(context, DBHelper.getInstance(context, true), alGpVillageFinalData);
                GpVillageUpdatedController.updateGpList(context, DBHelper.getInstance(context, true), alGpVillageFinalData);
                GpVillageUpdatedController.updateGpListStatus(context, DBHelper.getInstance(context, true), alGpVillageStatus);

                *//*GpVillageSurveyCommon gpVillageSurveyCommon = new GpVillageSurveyCommon();
                gpVillageSurveyCommon.setGp_total_no_of_eligible_beneficiaries_under_pmjay(alGpVillageFinalData.get(0).getGp_total_no_of_eligible_beneficiaries_under_pmjay());
                gpVillageSurveyCommon.setGp_total_no_of_beneficiaries_receiving_benefits_under_pmjay(alGpVillageFinalData.get(0).getGp_total_no_of_beneficiaries_receiving_benefits_under_pmjay());
                gpVillageSurveyCommon.setGp_total_hhd_eligible_under_nfsa(alGpVillageFinalData.get(0).getGp_total_hhd_eligible_under_nfsa());
                gpVillageSurveyCommon.setGp_total_hhd_receiving_food_grains_from_fps(alGpVillageFinalData.get(0).getGp_total_hhd_receiving_food_grains_from_fps());
                gpVillageSurveyCommon.setGp_is_final(true);
                MySharedPref.saveCommonGpSurvey(context, gpVillageSurveyCommon);*//*
            }

            if (bOldListModified) {
                if (n_gpSize == n_row) {
                    //getConstituency();
                } else {
                    pd.dismiss();
                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.get_data_info), context.getString(R.string.get_data_msg_failure), "004-070");
                    GpVillageBaseController.deleteAllGpData(context, DBHelper.getInstance(context, true));
                }
            } else {
                pd.dismiss();
                if (alGpVillageFinalData.size() > 0)
                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.get_data_info), context.getString(R.string.get_data_survey_data_updated), "004-071");
                else
                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.get_data_info), context.getString(R.string.get_data_no_data_mismatch), "004-072");

            }
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.get_data_error), e.getMessage(), "004-073");
        }
    }
*/

    /* Function to acknowledge base data retreived*/
    private void acknowledgeBaseDataReceived(String fileName, int enuDbCode) {
        if ((pd == null) || ((pd != null) && (!pd.isShowing())))
            pd = new ProgressDialog(context);
        try {
            JSONObject jsRequest = new JSONObject();
            jsRequest.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
           // jsRequest.put("user_id", "b7b6e4fd-6a04-479b-bd6f-6226edf1f747");

            GpVillageChecksum gpVillageChecksum = SeccPopulationController.getAllGpforAcknowledge(context, DBHelper.getInstance(context, true));

            JSONObject ja = new JSONObject();
            ja.put("app_id",gpVillageChecksum.getApp_id());
            ja.put("app_version",gpVillageChecksum.getApp_id());
            ja.put("b_file_name",fileName);
            ja.put("block_code",gpVillageChecksum.getBlock_code());
            ja.put("client_imei_no",gpVillageChecksum.getClient_imei_no());
            ja.put("client_ip_address",gpVillageChecksum.getClient_ip_address());
            ja.put("client_mac_address",gpVillageChecksum.getClient_mac_address());
            ja.put("device_id",gpVillageChecksum.getDevice_id());
            ja.put("district_code",gpVillageChecksum.getDistrict_code());
            ja.put("enum_block_code",enuDbCode);
            ja.put("gp_code",gpVillageChecksum.getGp_code());
            ja.put("state_code",gpVillageChecksum.getState_code());
            ja.put("sub_district_code",gpVillageChecksum.getSub_district_code());
            ja.put("user_id",gpVillageChecksum.getUser_id());
            ja.put("village_code",gpVillageChecksum.getVillage_code());

          /*  ja.put("app_id", "5d5c1ef2-4772-412b-bd96-2d6524c1ff83");
            ja.put("app_version", "2.0.5");
            ja.put("b_file_name", fileName);
            ja.put("block_code", 198);
            ja.put("client_imei_no", "865993048448955");
            ja.put("client_ip_address", "10.199.89.227");
            ja.put("client_mac_address", "58:85:E9:48:5E:EF");
            ja.put("device_id", "FPVJZF");
            ja.put("district_code", 24);
            ja.put("enum_block_code", enuDbCode);
            ja.put("gp_code", 10084);
            ja.put("state_code", 2);
            ja.put("sub_district_code", 168);
            ja.put("user_id", "b7b6e4fd-6a04-479b-bd6f-6226edf1f747");
            ja.put("village_code", 22677);
*/

            jsRequest.put("ebDownloadInfo", ja);


            Log.d(TAG, "acknowledgeBaseDataReceived: jsRequest" + jsRequest.toString());
            MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context, "001", false, true,
                    "eb/v1/acknowledgeBaseData", jsRequest, context.getString(R.string.get_data_error),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                Log.d(TAG, "onResponse:acknowledgeBaseDataReceived " + jsResponse.toString());
                                if (jsResponse.getBoolean("status")) {
                                    pd.dismiss();
                                 MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.get_data_info), context.getString(R.string.get_data_msg_success),"004-075");
                                    MySharedPref.saveIsBaseDataAcknowledgePending(context, false);
                                } else
                                    pd.dismiss();
                            } catch (Exception e) {
                                pd.dismiss();
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.get_data_error), e.getMessage(), "004-074");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            MyVolley.showVolley_error(context, "", error, "001");
                            pd.dismiss();
                        }

                    }
            );
        } catch (Exception e) {
            pd.dismiss();
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.get_data_error), e.getMessage(), "004-075");
        }
    }

    /* Function to display GP dashboard */
 /*   private void popupGpDashboard() {

        try {
            if ((pd != null) && (pd.isShowing()))
                pd.dismiss();
            final Dialog dialogGpDashboard = new Dialog(context);
            dialogGpDashboard.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogGpDashboard.setContentView(R.layout.layout_gp_dashboard);
            dialogGpDashboard.setCancelable(false);
            Window window = dialogGpDashboard.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

            TextView tvGpName = (TextView) dialogGpDashboard.findViewById(R.id.tvGpName);
            TextView tvTotalVillagesData = (TextView) dialogGpDashboard.findViewById(R.id.tvTotalVillagesData);
            TextView tvCompletedVillagesData = (TextView) dialogGpDashboard.findViewById(R.id.tvCompletedVillagesData);
            TextView tvSynchronizedVillagesData = (TextView) dialogGpDashboard.findViewById(R.id.tvSynchronizedVillagesData);
            TextView tvUploadedVillagesData = (TextView) dialogGpDashboard.findViewById(R.id.tvUploadedVillagesData);
            TextView tvVerifiedVillagesData = (TextView) dialogGpDashboard.findViewById(R.id.tvVerifiedVillagesData);
            TextView tvGpLastSynced = (TextView) dialogGpDashboard.findViewById(R.id.tvGpLastSynced);
            TextView tvSaveAsDraftVillagesData = (TextView) dialogGpDashboard.findViewById(R.id.tvSaveAsDraftVillagesData);
            TextView tvUninhabitatVillagesData = (TextView) dialogGpDashboard.findViewById(R.id.tvUninhabitatVillagesData);
            TextView tvNotStartedVillagesData = (TextView) dialogGpDashboard.findViewById(R.id.tvNotStartedVillagesData);
            Button btn_okGp = (Button) dialogGpDashboard.findViewById(R.id.btn_okGp);

            try {
                tvGpName.setText("(" + MySharedPref.getCurrentUser(context).getGp_name() + ")");
                ArrayList<GpVillageSurvey> alGp = GpVillageUpdatedController.getAllGp(context, DBHelper.getInstance(context, true));
                ArrayList<GpVillageSurvey> alGpVillageBase = GpVillageBaseController.getAllGp(context, DBHelper.getInstance(context, true));
                ArrayList<GpVillageSurvey> alGpVillageCompleted = GpVillageUpdatedController.getCompletedVillageList(context, DBHelper.getInstance(context, true));
                final ArrayList<GpVillageSurvey> alUninhabitatedVillage = GpVillageBaseController.getUninhabitedVillageList1(context, DBHelper.getInstance(context, true));
                int n_completedVillages = 0;
                int n_synchronizedVillages = 0;
                int n_uploadedVillages = 0;
                int n_verifiedVillages = 0;
                int iSaveAsDraftVillages = 0;

                ArrayList<GpVillageSurvey> alVillageNotStarted = new ArrayList<>();
                alVillageNotStarted.clear();

                for (int iVillageNotStartedIndex = 0; iVillageNotStartedIndex < alGpVillageBase.size(); iVillageNotStartedIndex++) {
                    ArrayList<GpVillageSurvey> alVillageSelected = GpVillageUpdatedController.getSelectedVillage1(context, DBHelper.getInstance(context, true), alGpVillageBase.get(iVillageNotStartedIndex).getVillage_code());

                    if (alVillageSelected.size() <= 0) {
                        alGpVillageBase.get(iVillageNotStartedIndex).setBase_data_enum_phase("not_started");
                        alVillageNotStarted.add(alGpVillageBase.get(iVillageNotStartedIndex));
                    }
                }

                if (alGpVillageBase.size() <= 0)
                    dialogGpDashboard.dismiss();
                else {
                    *//* Find out completed and synchronized villages *//*
                    for (int n_village = 0; n_village < alGp.size(); n_village++) {
                        if (alGp.get(n_village).getIs_uninhabited()) {

                        } else if (alGp.get(n_village).getBase_data_enum_phase().equalsIgnoreCase("not_started")) {

                        } else if (alGp.get(n_village).getIs_verified())
                            n_verifiedVillages++;
                        else if (alGp.get(n_village).getIs_uploaded())
                            n_uploadedVillages++;
                        else if (alGp.get(n_village).getIs_synchronized())
                            n_synchronizedVillages++;
                        else if (alGp.get(n_village).getIs_completed())
                            n_completedVillages++;
                        else if (!alGp.get(n_village).getIs_completed())
                            iSaveAsDraftVillages++;
                    }

                    if (alGpVillageBase.size() > 0)
                        tvGpLastSynced.setText("(Last updated : " + alGpVillageBase.get(0).getTs_base_data_last_sync() + " )");
                    else {
                        tvGpLastSynced.setText("");
                    }
                    tvTotalVillagesData.setText(String.valueOf(alGpVillageBase.size()));
                    tvCompletedVillagesData.setText(String.valueOf(n_completedVillages));
                    tvSynchronizedVillagesData.setText(String.valueOf(n_synchronizedVillages));
                    tvUploadedVillagesData.setText(String.valueOf(n_uploadedVillages));
                    tvVerifiedVillagesData.setText(String.valueOf(n_verifiedVillages));
                    tvUninhabitatVillagesData.setText(String.valueOf(alUninhabitatedVillage.size()));
                    tvSaveAsDraftVillagesData.setText(String.valueOf(iSaveAsDraftVillages));
                    tvNotStartedVillagesData.setText(String.valueOf(alVillageNotStarted.size()));

                }


                btn_okGp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogGpDashboard.dismiss();
                    }
                });

            } catch (Exception e) {
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_error), e.getMessage(), "004-076");
            }

            dialogGpDashboard.show();
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_error), e.getMessage(), "004-077");
        }
    }*/


    private void popupEnterPassword(final Dialog dialogPassword, final BooleanVariableListener.ChangeListener booleanVariableChangeListener) {
        try {
            if ((pd != null) && (pd.isShowing()))
                pd.dismiss();
            dialogPassword.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogPassword.setContentView(R.layout.layout_enter_password);
            Window window = dialogPassword.getWindow();
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawableResource(R.color.transparent);

            final EditText etPwd = (EditText) dialogPassword.findViewById(R.id.etPwd);
            final CheckBox cbShowPwd = (CheckBox) dialogPassword.findViewById(R.id.cbShowPwd);
            Button btnContinue = (Button) dialogPassword.findViewById(R.id.btnContinue);
            ImageView ivClose = (ImageView) dialogPassword.findViewById(R.id.ivClose);
            cbShowPwd.setButtonDrawable(R.mipmap.icon_eye_close);

            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogPassword.dismiss();
                }
            });
            // if button is clicked, close the custom dialog
            cbShowPwd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        PasswordTransformationMethod transform = (cbShowPwd.isChecked()) ?
                                null : new PasswordTransformationMethod();
                        etPwd.setTransformationMethod(transform);
                        if (cbShowPwd.isChecked())
                            cbShowPwd.setButtonDrawable(R.mipmap.icon_eye_open);
                        else
                            cbShowPwd.setButtonDrawable(R.mipmap.icon_eye_close);

                    } catch (Exception e) {
                        MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.reset_data_error), e.getMessage(), "004-078");

                    }
                }
            });


            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (etPwd.getText().toString().trim().isEmpty())
                            etPwd.setError(context.getString(R.string.enter_user_password));
                        else if (etPwd.getText().toString().trim().length() < 8)
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.reset_data_error), context.getString(R.string.incorrect_password), "004-079");
                        else {

                        /* If user already exists on local device */
                            String strFinalPwd = Password.sha256(Password.sha256(etPwd.getText().toString().trim()));
                            if (!strFinalPwd.equalsIgnoreCase(MySharedPref.getCurrentUser(context).getUser_password())) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.reset_data_error), context.getString(R.string.incorrect_password), "004-080");
                            } else {
                                BooleanVariableListener mBooleanVariableListener = new BooleanVariableListener();
                                mBooleanVariableListener.setListener(booleanVariableChangeListener);
                                mBooleanVariableListener.setBoo(true);
                            }
                        }
                    } catch (Exception e) {
                        MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.reset_data_error), e.getMessage(), "004-081");
                    }
                }
            });

            dialogPassword.show();
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.home_error), e.getMessage(), "004-082");
        }
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
    public void onBackPressed() {
        //super.onBackPressed();
        if (MySharedPref.getLocaleLanguage(context) != null)
            strLanguageToLoad = MySharedPref.getLocaleLanguage(context);
        Locale locale = new Locale(strLanguageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        MySharedPref.saveLocaleLanguage(context, locale);
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        try {
            final Dialog dialogAlert = new Dialog(context);
            MyAlert.dialogForCancelOk
                    (context, R.mipmap.icon_warning, context.getString(R.string.ma_warning),
                            context.getString(R.string.exit_confirmation),
                            dialogAlert,
                            context.getString(R.string.ok),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogAlert.dismiss();
                                    try {
                                        // Close Activity as well as all activities immediately below it in the current task that have the same affinity
                                        ActivityCompat.finishAffinity(Home.this);
                                        // Close Application
                                        System.exit(0);
                                    } catch (Exception e) {
                                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_error), e.getMessage(), "004-083");
                                    }
                                }
                            },
                            context.getString(R.string.cancel),
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
                            }, "004-085");
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_error), e.getMessage(), "004-084");
        }
    }

    String saveFilePath;
    boolean isCompletedFile = false;
    private static final int BUFFER_SIZE = 1024;

    private void showFileChooser(String str_alertTitle) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat) {
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");

        } else {
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
        }

        try {
            startActivityForResult(Intent.createChooser(intent, context.getString(R.string.select_file_to_upload)), SELECT_DOCUMENT);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            MyAlert.showAlert(context, R.mipmap.icon_info, str_alertTitle, context.getString(R.string.install_file_manager), "021-054");
        }

    }

    /* Get household status*/
    private void getHouseholdStatus(final Boolean bDataAlreadyRetreived, Dialog dialog, int enumBlockCode) {
        try {
            dialog.dismiss();
            JSONObject js = new JSONObject();
          /*  js.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
            js.put("state_code", MySharedPref.getCurrentUser(context).getState_code());
            js.put("district_code", MySharedPref.getCurrentUser(context).getDistrict_code());
            js.put("sub_district_code", MySharedPref.getCurrentUser(context).getSub_district_code());
            js.put("block_code", MySharedPref.getCurrentUser(context).getBlock_code());
            js.put("gp_code", MySharedPref.getCurrentUser(context).getGp_code());
            js.put("village_code", MySharedPref.getCurrentUser(context).getVillage_code());
*/
            js.put("user_id", "b7b6e4fd-6a04-479b-bd6f-6226edf1f747");
            js.put("state_code", 2);
            js.put("district_code", 24);
            js.put("sub_district_code", 168);
            js.put("block_code", 198);
            js.put("gp_code", 10084);
            js.put("village_code", 22677);
            js.put("enum_block_code", enumBlockCode);

            String strUrl = "eb/v1/getHouseholdStatus";

            MyVolley.getJsonResponse(context.getString(R.string.home_option_get_data), context, Request.Method.POST, "",
                    true, true, true,
                    false, false, false, strUrl, js,
                    new VolleyCallback() {
                        @Override
                        public void onVolleySuccessResponse(JSONObject jsResponse) {

                            try {
                                Log.d(TAG, "onVolleySuccessResponse: jsResponse" + jsResponse.toString());
                                if (jsResponse.getBoolean("status")) {

/*
                                    ArrayList<HouseholdEnumeration> alHouseholdEnu = new Gson().fromJson(jsResponse.getJSONArray("response").toString(), new TypeToken<List<HouseholdEnumeration>>() {
                                    }.getType());

                                    SQLiteDatabase db = DBHelper.getInstance(context, true);
                                    db.execSQL(HouseholdEnumerationController.CREATE_TABLE);

                                    String strMessage = "";
                                    if(!bDataAlreadyRetreived)
                                        strMessage = context.getString(R.string.status_update_success);

                                    if(strMessage.length()>1)
                                        strMessage += "\n";
                                    Boolean bResult = false;


                                    if(alHouseholdEnu.size() > 0) {
                                        int iTotalInsert = HouseholdEnumerationController.insertHhdList(context, db, alHouseholdEnu);


                                        if(iTotalInsert > 0 )
                                        {
                                            strMessage += context.getString(R.string.status_update_success_1);
                                        }
                                        else
                                        {
                                            if(bDataAlreadyRetreived)
                                                strMessage += context.getString(R.string.get_data_no_data);
                                        }
                                    }
                                    else
                                        strMessage += context.getString(R.string.get_data_no_village_status);

                                    MyAlert.showAlert(context, R.mipmap.icon_info , context.getString(R.string.home_option_get_data) + context.getString(R.string.info),strMessage, "021-056");
                                    if ((MyVolley.dialog != null) && MyVolley.dialog.isShowing()) {
                                        MyVolley.dialog.dismiss();
                                    }*/
                                } else {
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_option_village_status) + context.getString(R.string.error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)), "021-057");
                                    if ((MyVolley.dialog != null) && MyVolley.dialog.isShowing()) {
                                        MyVolley.dialog.dismiss();
                                    }
                                }

                            } catch (Exception e) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_option_village_status) + context.getString(R.string.error), e.getMessage(), "021-058");
                                if ((MyVolley.dialog != null) && MyVolley.dialog.isShowing()) {
                                    MyVolley.dialog.dismiss();
                                }
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
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_option_village_status) + context.getString(R.string.error), e.getMessage(), "021-059");
            if ((MyVolley.dialog != null) && MyVolley.dialog.isShowing()) {
                MyVolley.dialog.dismiss();
            }
        }
    }

    ArrayList<AknowledgeVillageData> aknowledgeVillageData;

    private void getVillageData() {

        try {
            JSONObject js = new JSONObject();
            js.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
            js.put("state_code", MySharedPref.getCurrentUser(context).getState_code());
            js.put("district_code", MySharedPref.getCurrentUser(context).getDistrict_code());
            js.put("sub_district_code", MySharedPref.getCurrentUser(context).getSub_district_code());
            js.put("block_code", MySharedPref.getCurrentUser(context).getBlock_code());
            js.put("gp_code", MySharedPref.getCurrentUser(context).getGp_code());
            js.put("village_code", MySharedPref.getCurrentUser(context).getVillage_code());

            String strUrl = Constants.SR + "getVillageData";

            MyVolley.getJsonResponse(context.getString(R.string.home_option_get_data), context, Request.Method.POST, "",
                    true, true, true,
                    false, false, false, strUrl, js,
                    new VolleyCallback() {
                        @Override
                        public void onVolleySuccessResponse(JSONObject jsResponse) {

                            try {
                                if (jsResponse.getBoolean("status")) {
                                    aknowledgeVillageData = new Gson().fromJson(jsResponse.getJSONArray("response").toString(), new TypeToken<List<AknowledgeVillageData>>() {
                                    }.getType());

                                    //getBaseData();
                                } else
                                    MyAlert.showAlert(context, R.mipmap.icon_error, R.string.login + " : " + "VillageData", "somthing went wrong !!!", "021-032");

                            } catch (Exception e) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, R.string.login + " : " + "VillageData", "somthing went wrong !!!", "021-033");
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
            e.printStackTrace();
        }
    }

    private void getBaseDataUsingWebService(Dialog dialogGetData, final int iSubDistrictCode, final int iVillageCode, final int iEnumeratedBlockCode) {

        try {
            dialogGetData.dismiss();
            isCompletedFile = false;
            JSONObject js = new JSONObject();
            js.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
            js.put("state_code", MySharedPref.getCurrentUser(context).getState_code());
            js.put("district_code", MySharedPref.getCurrentUser(context).getDistrict_code());
            js.put("sub_district_code", iSubDistrictCode);
            js.put("block_code", MySharedPref.getCurrentUser(context).getBlock_code());
            js.put("gp_code", MySharedPref.getCurrentUser(context).getGp_code());
            js.put("village_code", iVillageCode);
            js.put("enum_block_code", iEnumeratedBlockCode);


            /*js.put("user_id", "b7b6e4fd-6a04-479b-bd6f-6226edf1f747");
            js.put("state_code", 2);
            js.put("district_code", 24);
            js.put("sub_district_code", 168);
            js.put("block_code", 198);
            js.put("gp_code", 10084);
            js.put("village_code", 22677);
            js.put("enum_block_code", enumeratedBlockCode);*/

            String strUrl = Constants.DOWNLOAD + "getEBBaseData";
            MyVolley.getJsonResponse(context.getString(R.string.home_option_get_data), context, Request.Method.POST, "",
                    true, true, true,
                    true, false, false, strUrl, js,
                    new VolleyCallback() {
                        @Override
                        public void onVolleySuccessResponse(JSONObject jsResponse) {
                            try {

                                if (isCompletedFile ) {

                                    // MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.get_data), context.getString(R.string.get_data_msg_success));
                                    acknowledgeBaseDataReceived(fileName, iEnumeratedBlockCode);
                                    User u = MySharedPref.getCurrentUser(context);
                                    u.setSub_district_code(iSubDistrictCode);
                                    u.setVillage_code(iVillageCode);
                                    u.setEnum_block_code(iEnumeratedBlockCode);
                                    u.setB_file_name(fileName);

                                    MySharedPref.saveCurrentUser(context, u);
                                    MySharedPref.saveIsEnumBlockMarkCompleted(context, false);
                                } else if ((jsResponse.has("isBaseDataAvailable")) && !(jsResponse.getBoolean("isBaseDataAvailable"))) {

                                    SQLiteDatabase db = DBHelper.getInstance(context, true);
                                    db.execSQL(MasterVillageController.CREATE_TABLE);
                                    db.execSQL(HouseholdEolController.CREATE_TABLE);
                                    //db.execSQL(PopulationDelogController.CREATE_TABLE);
                                    /*db.execSQL(MasterStateController.CREATE_TABLE);
                                    db.execSQL(MasterDistrictController.CREATE_TABLE);
                                    db.execSQL(MasterSubdistrictController.CREATE_TABLE);
                                    db.execSQL(MasterDevelopmemtBlockController.CREATE_TABLE);
                                    db.execSQL(MasterGPController.CREATE_TABLE);
                                    */
                                    db.execSQL(SeccPopulationController.CREATE_TABLE);
                                    db.execSQL(SeccHouseholdController.CREATE_TABLE);
                                    //db.execSQL(HouseholdEnumerationController.CREATE_TABLE);
                                    //db.execSQL(SchemeIdBaseController.CREATE_TABLE);


                                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.get_data), jsResponse.getString("developer_message"), "021-062");
                                    if ((MyVolley.dialog != null) && MyVolley.dialog.isShowing()) {
                                        MyVolley.dialog.dismiss();
                                    }
                                } else {
                                    MyAlert.showAlert(context, R.mipmap.icon_warning, context.getString(R.string.get_data), jsResponse.getString("developer_message"), "021-030");
                                    if ((MyVolley.dialog != null) && MyVolley.dialog.isShowing()) {
                                        MyVolley.dialog.dismiss();
                                    }
                                }

                            } catch (Exception e) {
                                if ((MyVolley.dialog != null) && MyVolley.dialog.isShowing()) {
                                    MyVolley.dialog.dismiss();
                                }
                            }
                        }

                        @Override
                        public Response<JSONObject> onVolleyNetworkResponse(NetworkResponse response) {


                            try {
                                JSONObject js = new JSONObject(response.headers);

                                if (js.has("Content-Disposition")) {
                                    fileName = js.getString("Content-Disposition").split(";")[1].split("=")[1];

                                    Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
                                    File folder = new File(Environment.getExternalStorageDirectory() + "/" + "EOL");

                                    if (!folder.exists())
                                        folder.mkdirs();
                                    saveFilePath = folder.getAbsolutePath() + File.separator + fileName;

                                    InputStream inputStream = new ByteArrayInputStream(response.data);
                                    // opens an output stream to save into file
                                    FileOutputStream outputStream = new FileOutputStream(saveFilePath);

                                    int bytesRead = -1;
                                    byte[] buffer = new byte[BUFFER_SIZE];
                                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                                        outputStream.write(buffer, 0, bytesRead);
                                    }

                                    outputStream.close();
                                    inputStream.close();


                                    // check length of file
                                    File f = new File(saveFilePath);
                                    //dialog.dismiss();
                                    ////////////Unzip

                                    //////////////////////Unzip

                                    if (String.valueOf(f.length()).equals(js.getString("Content-Length"))) {
                                        System.out.println("download");

                                        //  if (Utility.checkPermission(context))
                                        {
                                            File path = Environment.getExternalStorageDirectory();
                                            File folderImport = new File(path + "/" + "EOL_import");
                                            if (!folderImport.exists())
                                                folderImport.mkdirs();
                                            String outFileName = folder.getAbsolutePath() + file.separator + "inputFile";
                                            FileUtils.unzip(context, saveFilePath, outFileName, true, true, true);

                                        } /*else
                                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.get_data), context.getString(R.string.msg_storage_permissions), "021-053");
*/

                                     /*   boolean status = new SQLiteHelper(context).importDatabase(saveFilePath, true);
                                        //   dialog.dismiss();
                                        if (status)
                                            isCompletedFile = true;*/
                                    } else {
                                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.get_data), "Incomplete file !!", "021-031");
                                    }
                                       isCompletedFile = true;

                                    return Response.success(new JSONObject(),
                                            HttpHeaderParser.parseCacheHeaders(response));
                                } else {
                                    String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                                    JSONObject jsonResponse = new JSONObject(json);
                                    return Response.success(jsonResponse, HttpHeaderParser.parseCacheHeaders(response));
                                }

                            } catch (UnsupportedEncodingException e) {
                                return Response.error(new ParseError(e));
                            } catch (Exception e) {
                                Log.d(TAG, "onVolleyNetworkResponse: Exception" + e);
                                return Response.error(new ParseError(e));
                            }
                        }


                        @Override
                        public void onVolleyErrorResponse(VolleyError error) {
                            Log.d(TAG, "getBaseData:Error Response");

                        }
                    });

        } catch (Exception e) {
            if ((MyVolley.dialog != null) && MyVolley.dialog.isShowing()) {
                MyVolley.dialog.dismiss();
            }
        }
    }


}
