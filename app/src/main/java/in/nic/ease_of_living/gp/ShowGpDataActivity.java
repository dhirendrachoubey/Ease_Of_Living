package in.nic.ease_of_living.gp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.nic.ease_of_living.adapter.HouseholdEolPaginationAdapter;
import in.nic.ease_of_living.broadcast.NetworkChangeReceiver;
import in.nic.ease_of_living.dbo.DBHelper;
import in.nic.ease_of_living.dbo.DBMaster;
import in.nic.ease_of_living.dbo.HouseholdEolController;
import in.nic.ease_of_living.dbo.MasterCommonController;
import in.nic.ease_of_living.dbo.SeccHouseholdController;
import in.nic.ease_of_living.interfaces.Communicator;
import in.nic.ease_of_living.listener.PaginationScrollListener;
import in.nic.ease_of_living.listener.RecyclerTouchListener;
import in.nic.ease_of_living.models.HouseholdEol;
import in.nic.ease_of_living.models.SeccHousehold;
import in.nic.ease_of_living.supports.IsConnected;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MySharedPref;
import in.nic.ease_of_living.supports.Password;
import in.nic.ease_of_living.utils.Common;
import in.nic.ease_of_living.utils.NetworkRegistered;
import in.nic.ease_of_living.utils.PopulateMasterLists;
import in.nic.ease_of_living.utils.Support;

/*014-015"*/
public class ShowGpDataActivity extends AppCompatActivity implements Communicator {


    private Context context;
    private RecyclerView rvHousehold;
    private TextView tvPdf,tvGpName, tvTotalHhdsData, tvCompletedHhdsData, tvUploadPendingHhdsData,
            tvVerifiedHhdsData, tvEnumBlockLastSynced, tvUploadedHhdsData, tvSaveAsDraftHhdsData, tvNotStartedHhdsData;
    private ArrayList<LinearLayout> alLinearLayout = new ArrayList<LinearLayout>();
    private ArrayList<LinearLayout> alLinearLayoutMain = new ArrayList<LinearLayout>();
    private ArrayList<ImageView> alImageView = new ArrayList<ImageView>();
    private ArrayList<HouseholdEol> alEol =new ArrayList<>();
    private ArrayList<SeccHousehold> alHhdNotStarted =new ArrayList<>();
    private ArrayList<SeccHousehold> alSeccHhd =new ArrayList<>();
    private ArrayList<HouseholdEol> alHhdCompleted =new ArrayList<>();
    private MenuItem menuItem;
    private NetworkChangeReceiver mNetworkReceiver;
    private int iSaveAsDraftHhds = 0;
    private int iCompletedHhds = 0;
    private int iSynchronizedHhds = 0;
    private int iUploadedHhds = 0;
    private int iNotStartedHhds = 0;
    private int iVerifiedHhds = 0;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yy");
    private ProgressDialog pd;
    private String strUserPassw = null;
    private LinearLayout llEnumBlockStatus, llEnumBlockStatusData, llEnumBlockDetail, llEnumBlockDetailData;
    private ImageView ivEnumBlockStatus, ivEnumBlockDetail;
    private ArrayList<ImageView> alImageViewMain = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;
    private HouseholdEolPaginationAdapter hhdEolPagiAdapter;
    private static final int PAGE_START = 0;
    private static final int MAX_LIST_ITEM = 50;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 0;
    private int currentPage = PAGE_START;
private  String TAG = "ShowGpDataActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            context = this;
            Intent intent = getIntent();
            strUserPassw = intent.getStringExtra("user_password");
            if (Password.matchUserCredentials(context, intent.getStringExtra("user_id"), strUserPassw)) {
                setContentView(R.layout.activity_show_gp_data);
                Common.setAppHeader(context);
                //getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getString(R.string.app_name) + "</font>"));
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                mNetworkReceiver = new NetworkChangeReceiver();
                NetworkRegistered.registerNetworkBroadcastForNougat(context, mNetworkReceiver);

                tvPdf = (TextView) findViewById(R.id.tvPdf);
                tvGpName = (TextView) findViewById(R.id.tvGpName);
                tvTotalHhdsData = (TextView) findViewById(R.id.tvTotalHhdsData);
                tvCompletedHhdsData = (TextView) findViewById(R.id.tvCompletedHhdsData);
                tvUploadPendingHhdsData = (TextView) findViewById(R.id.tvUploadPendingHhdsData);
                tvUploadedHhdsData = (TextView) findViewById(R.id.tvUploadedHhdsData);
                tvVerifiedHhdsData = (TextView) findViewById(R.id.tvVerifiedHhdsData);
                tvEnumBlockLastSynced = (TextView) findViewById(R.id.tvEnumBlockLastSynced);
                tvSaveAsDraftHhdsData = (TextView) findViewById(R.id.tvSaveAsDraftHhdsData);
                //tvUninhabitatVillagesData = (TextView) findViewById(R.id.tvUninhabitatVillagesData);
                tvNotStartedHhdsData = (TextView) findViewById(R.id.tvNotStartedHhdsData);
                rvHousehold = (RecyclerView) findViewById(R.id.rvHousehold);
                llEnumBlockStatus = (LinearLayout) findViewById(R.id.llEnumBlockStatus);
                llEnumBlockStatusData = (LinearLayout) findViewById(R.id.llEnumBlockStatusData);
                llEnumBlockDetail = (LinearLayout) findViewById(R.id.llEnumBlockDetail);
                llEnumBlockDetailData = (LinearLayout) findViewById(R.id.llEnumBlockDetailData);
                ivEnumBlockStatus = (ImageView) findViewById(R.id.ivEnumBlockStatus);
                ivEnumBlockDetail = (ImageView) findViewById(R.id.ivEnumBlockDetail);


                try {

                    alImageViewMain.clear();
                    alImageViewMain.add(ivEnumBlockStatus);
                    alImageViewMain.add(ivEnumBlockDetail);

                    alLinearLayoutMain.clear();
                    alLinearLayoutMain.add(llEnumBlockStatusData);
                    alLinearLayoutMain.add(llEnumBlockDetailData);
                    llEnumBlockStatus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (llEnumBlockStatusData.getVisibility() == View.VISIBLE) {
                                llEnumBlockStatusData.setVisibility(View.GONE);
                                ivEnumBlockStatus.setImageResource(R.mipmap.icon_plus);
                            } else {
                                Support.setLinearLayoutHideShow(alLinearLayoutMain, alImageViewMain, llEnumBlockStatusData, ivEnumBlockStatus);
                            }
                        }
                    });
                    llEnumBlockDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (llEnumBlockDetailData.getVisibility() == View.VISIBLE) {
                                llEnumBlockDetailData.setVisibility(View.GONE);
                                ivEnumBlockDetail.setImageResource(R.mipmap.icon_plus);
                            } else {
                                Support.setLinearLayoutHideShow(alLinearLayoutMain, alImageViewMain, llEnumBlockDetailData, ivEnumBlockDetail);
                            }
                        }
                    });


                    alEol = HouseholdEolController.getDataWithSeccHhd(context, DBHelper.getInstance(context, true));
                    alHhdNotStarted = SeccHouseholdController.getHouseholdList(context, DBHelper.getInstance(context, true));
                    alSeccHhd = SeccHouseholdController.getAllData(context, DBHelper.getInstance(context, true));
                    alHhdCompleted = HouseholdEolController.getAllCompletedHhd(context, DBHelper.getInstance(context, true));
                    iCompletedHhds = 0;
                    iSynchronizedHhds = 0;
                    iUploadedHhds = 0;
                    iNotStartedHhds = 0;
                    iSaveAsDraftHhds = 0;
                    iVerifiedHhds = 0;
                    tvGpName.setText("(" + MySharedPref.getCurrentUser(context).getGp_name() + "-" + String.valueOf(MySharedPref.getCurrentUser(context).getGp_code())  + " / " +
                            MySharedPref.getCurrentUser(context).getVillage_name() + "-" + String.valueOf(MySharedPref.getCurrentUser(context).getVillage_code())  + " / " +
                            MySharedPref.getCurrentUser(context).getEnum_block_code() + "-" + String.valueOf(MySharedPref.getCurrentUser(context).getEnum_block_code())  + ")");

                    if (alSeccHhd.size() == 0)
                        MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.show_gp_data_info), context.getString(R.string.show_gp_data_msg_no_data),"014-001");
                    else
                        {
                        PopulateMasterLists.populateMastersList(context);
                        hhdEolPagiAdapter = new HouseholdEolPaginationAdapter(context);
                            mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

                            rvHousehold.setLayoutManager(mLayoutManager);
                            rvHousehold.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
                            rvHousehold.setItemAnimator(new DefaultItemAnimator());
                            TOTAL_PAGES = alSeccHhd.size() / MAX_LIST_ITEM;
                            if (TOTAL_PAGES * MAX_LIST_ITEM < alSeccHhd.size())
                                TOTAL_PAGES = TOTAL_PAGES + 1;
                            if((TOTAL_PAGES == 0) || (TOTAL_PAGES == 1))
                                currentPage = TOTAL_PAGES;

                            rvHousehold.setAdapter(hhdEolPagiAdapter);
                           loadFirstPageEolHhd();
                            rvHousehold.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
                                @Override
                                protected void loadMoreItems() {
                                    isLoading = true;
                                    currentPage += 1;

                                    // mocking network delay for API call
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            loadNextPageEolHhd();
                                        }
                                    }, 1000);
                                }

                                @Override
                                public int getTotalPageCount() {
                                    return TOTAL_PAGES;
                                }

                                @Override
                                public boolean isLastPage() {
                                    return isLastPage;
                                }

                                @Override
                                public boolean isLoading() {
                                    return isLoading;
                                }
                            });

                        /* Find out completed and synchronized villages */
                        for (int n_village = 0; n_village < alEol.size(); n_village++) {
                            if(alEol.get(n_village).getUncovered_reason().equalsIgnoreCase("not_started"))
                                iNotStartedHhds++;
                            else if (alEol.get(n_village).getIs_verified() == 1)
                                iVerifiedHhds++;
                            else if (alEol.get(n_village).getIs_uploaded()== 1)
                                iUploadedHhds++;
                            else if (alEol.get(n_village).getIs_synchronized()== 1)
                                iSynchronizedHhds++;
                            else if (alEol.get(n_village).getIs_completed()== 1)
                                iCompletedHhds++;
                            else if ((alEol.get(n_village).getIs_completed()== 0))
                                iSaveAsDraftHhds++;

                        }
                    }



                    //ArrayList<GpVillageSurvey> alGpVillageBase = GpVillageBaseController.getAllGp(context, DBHelper.getInstance(context, true));
                    /*if ( alGpVillageBase.size() > 0)
                        tvEnumBlockLastSynced.setText( "(Last updated : " + alGpVillageBase.get(0).getTs_base_data_last_sync() + " )");
                    else
                    {
                        tvEnumBlockLastSynced.setText("");
                    }*/
                    //tvTotalHhdsData.setText(String.valueOf(PopulateMasterLists.adapterVillage.getCount() - 1));
                    tvTotalHhdsData.setText(String.valueOf(alSeccHhd.size()));
                    tvCompletedHhdsData.setText(String.valueOf(iCompletedHhds));
                    tvUploadPendingHhdsData.setText(String.valueOf(iSynchronizedHhds));
                    tvUploadedHhdsData.setText(String.valueOf(iUploadedHhds));
                    tvVerifiedHhdsData.setText(String.valueOf(iVerifiedHhds));
                    tvSaveAsDraftHhdsData.setText(String.valueOf(iSaveAsDraftHhds));
                    tvNotStartedHhdsData.setText(String.valueOf(iNotStartedHhds));

                    rvHousehold.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvHousehold, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int pos) {
                            if(!(alEol.get(pos).getUncovered_reason().equalsIgnoreCase("not_started")))
                                popUpHhdDetail(alEol.get(pos));
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));

                    /*tvPdf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            *//**//**//**//*if ((alHhdCompleted.size() + alUninhabitatedVillage.size()) != alVillageBase.size()) {
                                MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.show_gp_data_info), context.getString(R.string.generate_pdf_msg_submit_village),"014-002");
                            } else *//**//**//**//*
                            if (alHhdCompleted.size() == 0)
                                MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.show_gp_data_info),  context.getString(R.string.generate_pdf_msg_no_data),"014-003");
                            else {
                                PackageManager pm = context.getPackageManager();
                                int n_hasWritePerm = pm.checkPermission(
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        context.getPackageName());
                                //if (MySharedPref.getLocaleLanguage(context) == null || MySharedPref.getLocaleLanguage(context).equalsIgnoreCase("en")) {
                                if (n_hasWritePerm != PackageManager.PERMISSION_GRANTED) {
                                    MyAlert.showAlert(context, R.mipmap.icon_warning, context.getString(R.string.generate_pdf_warning),  context.getString(R.string.generate_pdf_msg_storage_permission),"014-004");
                                } else {
                                    if ((pd == null) || ((pd != null) && (!pd.isShowing())))
                                        pd = new ProgressDialog(context);
                                    Handler handler = new Handler();
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                (new GeneratePdf()).dialogForPDF(pd, context, true);
                                            } catch (Exception e) {
                                                pd.dismiss();
                                            }

                                        }
                                    });
                                    //(new GeneratePdf()).dialogForPDF(pd, context, alHhdCompleted, true);
                                }
                            *//**//**//**//*} else
                                MyAlert.showAlert(context, context.getString(R.string.pdf_generation), context.getString(R.string.pdf_generation_msg));
*//**//**//**//*
                            }
                        }
                    });*/
                } catch (Exception e) {
                    MyAlert.showAlert(context,R.mipmap.icon_error, context.getString(R.string.show_gp_data_error),  e.getMessage(),"014-005");
                }
            }
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.show_gp_data_error),  e.getMessage(),"014-006");
        }
    }

    /* Household Eol*/
    List<HouseholdEol> alHhdEol;

    private void loadFirstPageEolHhd() {
        alHhdEol = new ArrayList<>();
        isLastPage = false;
        Log.d(TAG, "hhdEolPagiAdapter:count "+hhdEolPagiAdapter.getItemCount());
        List<HouseholdEol> hhds = getDataEolHhd(hhdEolPagiAdapter.getItemCount());
        //MyVolley.dialog.dismiss();
        hhdEolPagiAdapter.addAll(hhds);

        if (currentPage < TOTAL_PAGES) hhdEolPagiAdapter.addLoadingFooter();
        else isLastPage = true;

    }

    private List<HouseholdEol> getDataEolHhd(int itemCount) {
        alHhdEol.clear();
        int i = 0;
        int size = itemCount == 0 ? (itemCount + MAX_LIST_ITEM) : (itemCount + MAX_LIST_ITEM - 1);
        for (i = itemCount == 0 ? (itemCount) : (itemCount - 1); i < size && i < alEol.size(); i++) {
            HouseholdEol h = alEol.get(i);
            alHhdEol.add(h);
        }
        return alHhdEol;
    }

    private void loadNextPageEolHhd() {
        List<HouseholdEol> hhds = getDataEolHhd(hhdEolPagiAdapter.getItemCount());
        isLoading = false;

        hhdEolPagiAdapter.removeLoadingFooter();

        hhdEolPagiAdapter.addAll(hhds);

        if (currentPage != TOTAL_PAGES) hhdEolPagiAdapter.addLoadingFooter();
        else isLastPage = true;
    }


    private LinearLayout llLocationParameters, llLocationParametersDetails, llFormDetails, llHouseholdParticulars,
            llHouseholdParticularsDetails,llOthers, llOthersDetails,
            llAvailabilityOfLpgConnectionYes, llAvailabilityOfElectricityConnectionYes, llAnyChild0To6OrPregnantWomanAvailableYes,
            llAnyChild0To6OrPregnantWomanAvailableNo, llWhetherBeneficiaryOfPmayGYes, llWhetherIssuedEHealthCardUnderAbPmjayYes,
            llWhetherAnyMemberGettingPensionNsapYes, llWhetherAnyMemberWorkedWithMgnregaYes, llWhetherAnyMemberUndergoneTrainingInSkillDevSchemeYes,
            l1WhetherHouseHoldHasNFSASchemeYes;
    private ImageView ivLocationParameters, ivHouseholdParticulars, ivOthers;
    HashMap<String, TextView> hmTextView = new HashMap<>();
    ArrayList<String> alTagNameTextView=new ArrayList<>();

    private TextView tvStateData, tvDistrictData, tvBlockData, tvGpData, tvVillageData, tvEbData,
            tvAvailabilityOfLpgConnectionData, tvLpgConsumerIdData, tvCylinderRefillFrequencyData,
            tvAvailabilityOfElectricityConnectionData, tvAvailabilityOfLedBulbsUjalaData, tvAvailabilityOfPmJanDhanAccountData,
            tvWhetherEnrolledForPmjjyData, tvWhetherEnrolledForPmsbyData, tvWhetherImmunizedUnderMissionIndradhanushData,
            tvAnyChild0To6OrPregnantWomanAvailableData, tvWhetherAnyMemberRegisteredUnderIcdsData, tvCheckServicesUnderIcdsData,
            tvWhetherAvailedNutritionBenefirIcdsData, tvAnyMemberOfShgUnderDayNrlmData, tvHouseholdTypeData,
            tvWhetherBeneficiaryOfPmayGData, tvProvideStatusOfPmayGData, tvPmayGIdData,
            tvWhetherIssuedEHealthCardUnderAbPmjayData, tvFamilyHealthCardNoData, tvWhetherAnyMemberGettingPensionNsapData,
            tvSelectPensionData, tvWhetherAnyMemberWorkedWithMgnregaData, tvMgnregaJobCardNoData,
            tvNoDaysWorkedWithMgnregaInLastYearData, tvWhetherAnyMemberUndergoneTrainingInSkillDevSchemeData,
            tvSkillDevSchemeTypeData, tvWhetherHouseHoldHasFunctionalToiletsData, tvHouseHoldMobileNoData,
            tvWhetherHouseHoldAvailingRationNFSASchemeData, tvNFSASchemeTypeData, tvNfsaRationCardNoData, tvDtRecordData,
            tvIsCompletedData, tvIsSendToServerData, tvTsSendToServerData, tvIsUploadedData, tvTsUploadedData,
            tvUploadedFromAppOrPortalData, tvUploadedByData, tvIsVerifiedData, tvTsVerifiedData, tvVerifiedFromAppOrPortalData,
            tvVerifiedByData, tvDtRecord, tvTsSendToServer, tvTsUploaded, tvUploadedFromAppOrPortal, tvUploadedBy;


    private Button btn_okGp;    // Button Pdf;

    private void popUpHhdDetail(final HouseholdEol h) {

        try {

            /*if( (h.getIs_uninhabited()) || (h.getBase_data_enum_phase().equalsIgnoreCase("not_started")) )
            {
                // Do nothing
            }
            else*/ {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_household_details);
                dialog.setCancelable(false);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                findView(dialog);
                populateArrayList();
                setListener(dialog);
                {
                    llFormDetails.setVisibility(View.VISIBLE);
                    populateGPData(h);
                }


                dialog.show();
            }
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.show_gp_data_error), e.getMessage(),"014-007");
        }
    }

    private void findView(Dialog d) {

        try {
            // Button
            btn_okGp = (Button) d.findViewById(R.id.btn_okGp);
            // btn_pdf = (Button) d.findViewById(R.id.btn_pdf);

            //TextView



            // LinearLayout
            llLocationParameters = (LinearLayout) d.findViewById(R.id.llLocationParameters);
            llLocationParametersDetails = (LinearLayout) d.findViewById(R.id.llLocationParametersDetails);
            llFormDetails = (LinearLayout) d.findViewById(R.id.llFormDetails);
            llHouseholdParticulars = (LinearLayout) d.findViewById(R.id.llHouseholdParticulars);
            llHouseholdParticularsDetails = (LinearLayout) d.findViewById(R.id.llHouseholdParticularsDetails);
            llOthers = (LinearLayout) d.findViewById(R.id.llOthers);
            llOthersDetails = (LinearLayout) d.findViewById(R.id.llOthersDetail);
            llAvailabilityOfLpgConnectionYes = (LinearLayout) d.findViewById(R.id.llAvailabilityOfLpgConnectionYes);
            llAvailabilityOfElectricityConnectionYes = (LinearLayout) d.findViewById(R.id.llAvailabilityOfElectricityConnectionYes);
            llAnyChild0To6OrPregnantWomanAvailableYes = (LinearLayout) d.findViewById(R.id.llAnyChild0To6OrPregnantWomanAvailableYes);
            llAnyChild0To6OrPregnantWomanAvailableNo = (LinearLayout) d.findViewById(R.id.llAnyChild0To6OrPregnantWomanAvailableNo);
            llWhetherBeneficiaryOfPmayGYes = (LinearLayout) d.findViewById(R.id.llWhetherBeneficiaryOfPmayGYes);
            llWhetherIssuedEHealthCardUnderAbPmjayYes = (LinearLayout) d.findViewById(R.id.llWhetherIssuedEHealthCardUnderAbPmjayYes);
            llWhetherAnyMemberGettingPensionNsapYes = (LinearLayout) d.findViewById(R.id.llWhetherAnyMemberGettingPensionNsapYes);
            llWhetherAnyMemberWorkedWithMgnregaYes = (LinearLayout) d.findViewById(R.id.llWhetherAnyMemberWorkedWithMgnregaYes);
            llWhetherAnyMemberUndergoneTrainingInSkillDevSchemeYes = (LinearLayout) d.findViewById(R.id.llWhetherAnyMemberUndergoneTrainingInSkillDevSchemeYes);
            l1WhetherHouseHoldHasNFSASchemeYes = (LinearLayout) d.findViewById(R.id.l1WhetherHouseHoldHasNFSASchemeYes);

            ivLocationParameters = (ImageView) d.findViewById(R.id.ivLocationParameters);
            ivHouseholdParticulars = (ImageView) d.findViewById(R.id.ivHouseholdParticulars);
            ivOthers = (ImageView) d.findViewById(R.id.ivOthers);


            llFormDetails = (LinearLayout) d.findViewById(R.id.llFormDetails);

            // TextView
            tvStateData = (TextView) d.findViewById(R.id.tvStateData);
            tvDistrictData = (TextView) d.findViewById(R.id.tvDistrictData);
            tvBlockData = (TextView) d.findViewById(R.id.tvBlockData);
            tvGpData = (TextView) d.findViewById(R.id.tvGpData);
            tvVillageData = (TextView) d.findViewById(R.id.tvVillageData);
            tvEbData = (TextView) d.findViewById(R.id.tvEbData);
            tvAvailabilityOfLpgConnectionData = (TextView) d.findViewById(R.id.tvAvailabilityOfLpgConnectionData);
            tvLpgConsumerIdData = (TextView) d.findViewById(R.id.tvLpgConsumerIdData);
            tvCylinderRefillFrequencyData = (TextView) d.findViewById(R.id.tvCylinderRefillFrequencyData);
            tvAvailabilityOfElectricityConnectionData = (TextView) d.findViewById(R.id.tvAvailabilityOfElectricityConnectionData);
            tvAvailabilityOfLedBulbsUjalaData = (TextView) d.findViewById(R.id.tvAvailabilityOfLedBulbsUjalaData);
            tvAvailabilityOfPmJanDhanAccountData = (TextView) d.findViewById(R.id.tvAvailabilityOfPmJanDhanAccountData);
            tvWhetherEnrolledForPmjjyData = (TextView) d.findViewById(R.id.tvWhetherEnrolledForPmjjyData);
            tvWhetherEnrolledForPmsbyData = (TextView) d.findViewById(R.id.tvWhetherEnrolledForPmsbyData);
            tvWhetherImmunizedUnderMissionIndradhanushData = (TextView) d.findViewById(R.id.tvWhetherImmunizedUnderMissionIndradhanushData);
            tvAnyChild0To6OrPregnantWomanAvailableData = (TextView) d.findViewById(R.id.tvAnyChild0To6OrPregnantWomanAvailableData);
            tvWhetherAnyMemberRegisteredUnderIcdsData = (TextView) d.findViewById(R.id.tvWhetherAnyMemberRegisteredUnderIcdsData);
            tvCheckServicesUnderIcdsData = (TextView) d.findViewById(R.id.tvCheckServicesUnderIcdsData);
            tvWhetherAvailedNutritionBenefirIcdsData = (TextView) d.findViewById(R.id.tvWhetherAvailedNutritionBenefirIcdsData);
            tvAnyMemberOfShgUnderDayNrlmData = (TextView) d.findViewById(R.id.tvAnyMemberOfShgUnderDayNrlmData);
            tvHouseholdTypeData = (TextView) d.findViewById(R.id.tvHouseholdTypeData);
            tvWhetherBeneficiaryOfPmayGData = (TextView) d.findViewById(R.id.tvWhetherBeneficiaryOfPmayGData);
            tvProvideStatusOfPmayGData = (TextView) d.findViewById(R.id.tvProvideStatusOfPmayGData);
            tvPmayGIdData = (TextView) d.findViewById(R.id.tvPmayGIdData);
            tvWhetherIssuedEHealthCardUnderAbPmjayData = (TextView) d.findViewById(R.id.tvWhetherIssuedEHealthCardUnderAbPmjayData);
            tvFamilyHealthCardNoData = (TextView) d.findViewById(R.id.tvFamilyHealthCardNoData);
            tvWhetherAnyMemberGettingPensionNsapData = (TextView) d.findViewById(R.id.tvWhetherAnyMemberGettingPensionNsapData);
            tvSelectPensionData = (TextView) d.findViewById(R.id.tvSelectPensionData);
            tvWhetherAnyMemberWorkedWithMgnregaData = (TextView) d.findViewById(R.id.tvWhetherAnyMemberWorkedWithMgnregaData);
            tvMgnregaJobCardNoData = (TextView) d.findViewById(R.id.tvMgnregaJobCardNoData);
            tvNoDaysWorkedWithMgnregaInLastYearData = (TextView) d.findViewById(R.id.tvNoDaysWorkedWithMgnregaInLastYearData);
            tvWhetherAnyMemberUndergoneTrainingInSkillDevSchemeData = (TextView) d.findViewById(R.id.tvWhetherAnyMemberUndergoneTrainingInSkillDevSchemeData);
            tvSkillDevSchemeTypeData = (TextView) d.findViewById(R.id.tvSkillDevSchemeTypeData);
            tvWhetherHouseHoldHasFunctionalToiletsData = (TextView) d.findViewById(R.id.tvWhetherHouseHoldHasFunctionalToiletsData);
            tvHouseHoldMobileNoData = (TextView) d.findViewById(R.id.tvHouseHoldMobileNoData);
            tvWhetherHouseHoldAvailingRationNFSASchemeData = (TextView) d.findViewById(R.id.tvWhetherHouseHoldAvailingRationNFSASchemeData);
            tvNFSASchemeTypeData = (TextView) d.findViewById(R.id.tvNFSASchemeTypeData);
            tvNfsaRationCardNoData = (TextView) d.findViewById(R.id.tvNfsaRationCardNoData);
            tvDtRecordData = (TextView) d.findViewById(R.id.tvDtRecordData);
            tvIsCompletedData = (TextView) d.findViewById(R.id.tvIsCompletedData);
            tvIsSendToServerData = (TextView) d.findViewById(R.id.tvIsSendToServerData);
            tvTsSendToServerData = (TextView) d.findViewById(R.id.tvTsSendToServerData);
            tvIsUploadedData = (TextView) d.findViewById(R.id.tvIsUploadedData);
            tvTsUploadedData = (TextView) d.findViewById(R.id.tvTsUploadedData);
            tvUploadedFromAppOrPortalData = (TextView) d.findViewById(R.id.tvUploadedFromAppOrPortalData);
            tvUploadedByData = (TextView) d.findViewById(R.id.tvUploadedByData);
            tvIsVerifiedData = (TextView) d.findViewById(R.id.tvIsVerifiedData);
            tvTsVerifiedData = (TextView) d.findViewById(R.id.tvTsVerifiedData);
            tvVerifiedFromAppOrPortalData = (TextView) d.findViewById(R.id.tvVerifiedFromAppOrPortalData);
            tvVerifiedByData = (TextView) d.findViewById(R.id.tvVerifiedByData);
            tvDtRecord = (TextView) d.findViewById(R.id.tvDtRecord);
            tvTsSendToServer = (TextView) d.findViewById(R.id.tvTsSendToServer);
            tvTsUploaded = (TextView) d.findViewById(R.id.tvTsUploaded);
            tvUploadedFromAppOrPortal = (TextView) d.findViewById(R.id.tvUploadedFromAppOrPortal);
            tvUploadedBy = (TextView) d.findViewById(R.id.tvUploadedBy);

        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.show_gp_data_error), e.getMessage(),"014-013");
        }
    }

    private void populateArrayList()
    {
        try {
            alLinearLayout.clear();
            alImageView.clear();
            // Populate arrayList from database
            alLinearLayout.add(llLocationParametersDetails);
            alLinearLayout.add(llHouseholdParticularsDetails);
            alLinearLayout.add(llOthersDetails);

            alImageView.add(ivLocationParameters);
            alImageView.add(ivHouseholdParticulars);
            alImageView.add(ivOthers);

        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.show_gp_data_error),  e.getMessage(),"014-012");
        }
    }

    private void setListener(final Dialog d) {
        try {
            llLocationParameters.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (llLocationParametersDetails.getVisibility() == View.VISIBLE) {
                        llLocationParametersDetails.setVisibility(View.GONE);
                        ivLocationParameters.setImageResource(R.mipmap.icon_plus);
                    } else {
                        Support.setLinearLayoutHideShow(alLinearLayout, alImageView, llLocationParametersDetails, ivLocationParameters);
                    }
                }
            });

            llHouseholdParticulars.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (llHouseholdParticularsDetails.getVisibility() == View.VISIBLE) {
                        llHouseholdParticularsDetails.setVisibility(View.GONE);
                        ivHouseholdParticulars.setImageResource(R.mipmap.icon_plus);
                    } else {
                        Support.setLinearLayoutHideShow(alLinearLayout, alImageView, llHouseholdParticularsDetails, ivHouseholdParticulars);
                    }
                }
            });




            llOthers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (llOthersDetails.getVisibility() == View.VISIBLE) {
                        llOthersDetails.setVisibility(View.GONE);
                        ivOthers.setImageResource(R.mipmap.icon_plus);
                    } else {
                        Support.setLinearLayoutHideShow(alLinearLayout, alImageView, llOthersDetails, ivOthers);

                    }
                }
            });

            btn_okGp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    d.dismiss();
                }
            });
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.show_gp_data_error), e.getMessage(),"014-014");
        }

    }

    /* Function to populate household data to form*/
    private void populateGPData(HouseholdEol h) {
        try {
            String strValue;


            tvStateData.setText(h.getState_name() + "(" + h.getState_code() + ")");
            tvDistrictData.setText(h.getDistrict_name() + "(" + h.getDistrict_code() + ")");
            tvBlockData.setText(h.getBlock_name() + "(" + h.getBlock_code() + ")");
            tvGpData.setText(h.getGp_name() + "(" + h.getGp_code() + ")");
            tvVillageData.setText(h.getVillage_name() + "(" + h.getVillage_code() + ")");
            tvEbData.setText(h.getEnum_block_name() + "(" + h.getEnum_block_code() + ")");

            tvAvailabilityOfLpgConnectionData.setText(MasterCommonController.getNameYesNo(context, h.getIs_lpg_connection_available()));
            if (h.getIs_lpg_connection_available() == null);
            else if (h.getIs_lpg_connection_available()) {
                llAvailabilityOfLpgConnectionYes.setVisibility(View.VISIBLE);
                if ((h.getLpg_consumer_id() != null) && (!h.getLpg_consumer_id().trim().isEmpty()))
                    tvLpgConsumerIdData.setText(h.getLpg_consumer_id());


                if ((h.getNo_of_times_refilled_in_last_one_yr() != null) && (h.getNo_of_times_refilled_in_last_one_yr()!=0))
                    tvCylinderRefillFrequencyData.setText(String.valueOf(h.getNo_of_times_refilled_in_last_one_yr()));

            } else {
                llAvailabilityOfLpgConnectionYes.setVisibility(View.GONE);
            }


            tvAvailabilityOfElectricityConnectionData.setText(MasterCommonController.getNameYesNo(context, h.getIs_electricity_connection_available()));
            if (h.getIs_electricity_connection_available() == null);
            else if (h.getIs_electricity_connection_available() != null) {
                llAvailabilityOfElectricityConnectionYes.setVisibility(View.VISIBLE);
                tvAvailabilityOfLedBulbsUjalaData.setText(MasterCommonController.getNameYesNo(context, h.getIs_led_available_under_ujala()));
            } else {
                llAvailabilityOfElectricityConnectionYes.setVisibility(View.GONE);
            }

            tvAvailabilityOfPmJanDhanAccountData.setText(MasterCommonController.getNameYesNo(context, h.getIs_any_member_have_jandhan_ac()));
            tvWhetherEnrolledForPmjjyData.setText(MasterCommonController.getNameYesNo(context, h.getIs_hhd_enrolled_in_pmjjby()));
            tvWhetherEnrolledForPmsbyData.setText(MasterCommonController.getNameYesNo(context, h.getIs_any_member_enrolled_in_pmsby()));
            tvWhetherImmunizedUnderMissionIndradhanushData.setText(MasterCommonController.getNameYesNo(context, h.getIs_any_member_immunised_under_mission_indradhanush()));


            tvAnyChild0To6OrPregnantWomanAvailableData.setText(MasterCommonController.getNameYesNo(context, h.getIs_any_child_0_6_yrs_or_pregnant_woman_available()));
            if (h.getIs_any_child_0_6_yrs_or_pregnant_woman_available() == null);
            else if (h.getIs_any_child_0_6_yrs_or_pregnant_woman_available()) {
                llAnyChild0To6OrPregnantWomanAvailableYes.setVisibility(View.VISIBLE);
                llAnyChild0To6OrPregnantWomanAvailableNo.setVisibility(View.GONE);
                tvWhetherAnyMemberRegisteredUnderIcdsData.setText(MasterCommonController.getNameYesNo(context, h.getIs_any_member_registered_in_icds()));
                strValue = MasterCommonController.getName(context, DBMaster.getInstance(context, true), 3,
                        h.getServices_availed_under_icds().get(0));
                tvCheckServicesUnderIcdsData.setText(strValue);


            } else {
                llAnyChild0To6OrPregnantWomanAvailableYes.setVisibility(View.GONE);
                llAnyChild0To6OrPregnantWomanAvailableNo.setVisibility(View.VISIBLE);
                tvWhetherAvailedNutritionBenefirIcdsData.setText(MasterCommonController.getNameYesNo(context, h.getIs_hhd_availed_nutrition_benefits_under_icds()));

            }

            tvAnyMemberOfShgUnderDayNrlmData.setText(MasterCommonController.getNameYesNo(context, h.getIs_any_member_of_hhd_is_member_of_shg()));

            strValue = MasterCommonController.getName(context, DBMaster.getInstance(context, true), 4,
                    h.getType_of_house_used_for_living());
            tvHouseholdTypeData.setText(strValue);


            tvWhetherBeneficiaryOfPmayGData.setText(MasterCommonController.getNameYesNo(context, h.getIs_hhd_a_beneficiary_of_pmayg()));
            if (h.getIs_hhd_a_beneficiary_of_pmayg() == null);
            else if (h.getIs_hhd_a_beneficiary_of_pmayg()) {
                llWhetherBeneficiaryOfPmayGYes.setVisibility(View.VISIBLE);
                strValue = MasterCommonController.getName(context, DBMaster.getInstance(context, true), 3,
                        h.getPmayg_status());
                tvCheckServicesUnderIcdsData.setText(strValue);
                if ((h.getPmayg_id() != null) && (!h.getPmayg_id().trim().isEmpty()))
                    tvPmayGIdData.setText(h.getPmayg_id());

            } else {
                llWhetherBeneficiaryOfPmayGYes.setVisibility(View.GONE);
            }

            tvWhetherIssuedEHealthCardUnderAbPmjayData.setText(MasterCommonController.getNameYesNo(context, h.getIs_hhd_issued_health_card_under_abpmjay()));
            if (h.getIs_hhd_issued_health_card_under_abpmjay() == null);
            else if (h.getIs_hhd_issued_health_card_under_abpmjay()) {
                llWhetherIssuedEHealthCardUnderAbPmjayYes.setVisibility(View.VISIBLE);
                if ((h.getFamily_health_card_number() != null) && (!h.getFamily_health_card_number().trim().isEmpty()))
                    tvFamilyHealthCardNoData.setText(h.getFamily_health_card_number());

            } else {
                llWhetherIssuedEHealthCardUnderAbPmjayYes.setVisibility(View.GONE);
            }


            tvWhetherAnyMemberGettingPensionNsapData.setText(MasterCommonController.getNameYesNo(context, h.getIs_any_member_getting_pension_under_nsap()));
            if (h.getIs_any_member_getting_pension_under_nsap() == null);
            else if (h.getIs_any_member_getting_pension_under_nsap()) {
                llWhetherAnyMemberGettingPensionNsapYes.setVisibility(View.VISIBLE);
                strValue = MasterCommonController.getName(context, DBMaster.getInstance(context, true), 5,
                        h.getType_of_pensions().get(0));
                tvSelectPensionData.setText(strValue);

            } else {
                llWhetherAnyMemberGettingPensionNsapYes.setVisibility(View.GONE);
            }


            tvWhetherAnyMemberWorkedWithMgnregaData.setText(MasterCommonController.getNameYesNo(context, h.getIs_any_member_ever_worked_under_mgnrega()));
            if (h.getIs_any_member_ever_worked_under_mgnrega() == null);
            else if (h.getIs_any_member_ever_worked_under_mgnrega()) {
                llWhetherAnyMemberWorkedWithMgnregaYes.setVisibility(View.VISIBLE);
                if ((h.getMgnrega_job_card_number() != null) && (!h.getMgnrega_job_card_number().trim().isEmpty()))
                    tvMgnregaJobCardNoData.setText(h.getMgnrega_job_card_number());

                if ((h.getNo_of_days_worked_under_mgnrega_in_last_one_yr() != null) && (h.getNo_of_days_worked_under_mgnrega_in_last_one_yr()!=0))
                    tvNoDaysWorkedWithMgnregaInLastYearData.setText(String.valueOf(h.getNo_of_days_worked_under_mgnrega_in_last_one_yr()));

            } else {
                llWhetherAnyMemberWorkedWithMgnregaYes.setVisibility(View.GONE);
            }


            tvWhetherAnyMemberUndergoneTrainingInSkillDevSchemeData.setText(MasterCommonController.getNameYesNo(context, h.getIs_any_member_undergone_training_under_any_skill_dev_prog()));
            if (h.getIs_any_member_undergone_training_under_any_skill_dev_prog() == null);
            else if (h.getIs_any_member_undergone_training_under_any_skill_dev_prog()) {
                llWhetherAnyMemberUndergoneTrainingInSkillDevSchemeYes.setVisibility(View.VISIBLE);
                strValue = MasterCommonController.getName(context, DBMaster.getInstance(context, true), 7,
                        h.getUndergone_skill_development_schemes().get(0));
                tvSkillDevSchemeTypeData.setText(strValue);

            } else {
                llWhetherAnyMemberUndergoneTrainingInSkillDevSchemeYes.setVisibility(View.GONE);

            }

            tvWhetherHouseHoldHasFunctionalToiletsData.setText(MasterCommonController.getNameYesNo(context, h.getIs_hhd_available_a_functional_toilet()));

            if ((h.getMobile_numbers_hhd_members() != null) && (h.getMobile_numbers_hhd_members().size() > 0))
                tvHouseHoldMobileNoData.setText(h.getMobile_numbers_hhd_members().get(0));

            tvWhetherHouseHoldAvailingRationNFSASchemeData.setText(MasterCommonController.getNameYesNo(context, h.getIs_hhd_availing_ration_under_nfsa_scheme()));
            if (h.getIs_hhd_availing_ration_under_nfsa_scheme() == null);
            else if (h.getIs_hhd_availing_ration_under_nfsa_scheme()) {
                l1WhetherHouseHoldHasNFSASchemeYes.setVisibility(View.VISIBLE);
                if ((h.getNfsa_ration_card_number() != null) && (!h.getNfsa_ration_card_number().trim().isEmpty()))
                    tvNfsaRationCardNoData.setText(h.getNfsa_ration_card_number());
                strValue = MasterCommonController.getName(context, DBMaster.getInstance(context, true), 7,
                        h.getNfsa_ration_card_type());
                tvNFSASchemeTypeData.setText(strValue);
            } else {
                l1WhetherHouseHoldHasNFSASchemeYes.setVisibility(View.GONE);
            }

            if( (h.getDt_created() != null ) && (h.getDt_created().length() > 0) )
            {
                tvDtRecordData.setText(h.getDt_created());
                tvDtRecordData.setVisibility(View.VISIBLE);
                tvDtRecord.setVisibility(View.VISIBLE);
            }
            else
            {
                tvDtRecordData.setVisibility(View.GONE);
                tvDtRecord.setVisibility(View.GONE);
            }
            tvIsCompletedData.setText(String.valueOf(h.getIs_completed() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no)));

            if(h.getIs_uploaded() == 1)
                tvIsSendToServerData.setText(context.getString(R.string.no));
            else
                tvIsSendToServerData.setText(String.valueOf(h.getIs_synchronized() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no)));

            if(!(h.getIs_uploaded()==1) && (h.getIs_synchronized() == 1))
            {
                if ( (h.getDt_sync() == null) || h.getDt_sync().isEmpty())
                    tvTsSendToServerData.setText(context.getString(R.string.not_available));
                else
                    tvTsSendToServerData.setText(h.getDt_sync());

                tvTsSendToServer.setVisibility(View.VISIBLE);
                tvTsSendToServerData.setVisibility(View.VISIBLE);
            }
            else {
                tvTsSendToServer.setVisibility(View.GONE);
                tvTsSendToServerData.setVisibility(View.GONE);
            }


            tvIsUploadedData.setText(String.valueOf(h.getIs_uploaded() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no)));

            if(h.getIs_uploaded() == 1)
            {
                if ((h.getTs_updated() == null) || h.getTs_updated().isEmpty())
                    tvTsUploadedData.setText(context.getString(R.string.not_available));
                else
                    tvTsUploadedData.setText(h.getTs_updated());

                /*if ((h.get() == null) || h.getUploaded_by().isEmpty())
                    tvUploadedByData.setText(context.getString(R.string.not_available));
                else
                    tvUploadedByData.setText(h.getUploaded_by());


                if ((h.getUploaded_from_app_or_portal() == null) || h.getUploaded_from_app_or_portal().isEmpty())
                    tvUploadedFromAppOrPortalData.setText(context.getString(R.string.not_available));
                else
                    tvUploadedFromAppOrPortalData.setText(h.getUploaded_from_app_or_portal());*/

                tvTsUploaded.setVisibility(View.VISIBLE);
                tvTsUploadedData.setVisibility(View.VISIBLE);
                tvUploadedFromAppOrPortal.setVisibility(View.VISIBLE);
                tvUploadedFromAppOrPortalData.setVisibility(View.VISIBLE);
                tvUploadedBy.setVisibility(View.VISIBLE);
                tvUploadedByData.setVisibility(View.VISIBLE);
            }
            else
            {
                tvTsUploaded.setVisibility(View.GONE);
                tvTsUploadedData.setVisibility(View.GONE);
                tvUploadedFromAppOrPortal.setVisibility(View.GONE);
                tvUploadedFromAppOrPortalData.setVisibility(View.GONE);
                tvUploadedBy.setVisibility(View.GONE);
                tvUploadedByData.setVisibility(View.GONE);
            }

            tvIsVerifiedData.setText(String.valueOf(h.getIs_verified() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no)));

            if(h.getIs_verified() == 1)
            {
                tvTsVerifiedData.setText(h.getTs_verified());
                /*tvVerifiedByData.setText(h.getVerified_by());
                tvVerifiedFromAppOrPortalData.setText(h.getVerified_from_app_or_portal());*/

                /*tvTsVerified.setVisibility(View.VISIBLE);
                tvTsVerifiedData.setVisibility(View.VISIBLE);
                tvVerifiedFromAppOrPortal.setVisibility(View.GONE);
                tvVerifiedFromAppOrPortalData.setVisibility(View.GONE);
                tvVerifiedBy.setVisibility(View.VISIBLE);
                tvVerifiedByData.setVisibility(View.VISIBLE);*/
            }
            else
            {
                /*tvTsVerified.setVisibility(View.GONE);
                tvTsVerifiedData.setVisibility(View.GONE);
                tvVerifiedFromAppOrPortal.setVisibility(View.GONE);
                tvVerifiedFromAppOrPortalData.setVisibility(View.GONE);
                tvVerifiedBy.setVisibility(View.GONE);
                tvVerifiedByData.setVisibility(View.GONE);*/
            }

        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.show_gp_data_error), e.getMessage(),"014-009");
        }
    }

    private void setYesNoChangeListener(int nCode, String str, TextView tv , TextView tvData)
    {
        try {
            if ((nCode == 1) ||(nCode == 0)) {
                tv.setVisibility(View.GONE);
                tvData.setVisibility(View.GONE);
            } else if (nCode == 2)  {
                tv.setVisibility(View.VISIBLE);
                tvData.setVisibility(View.VISIBLE);
                tvData.setText(str);
            }
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.show_gp_data_error),  e.getMessage(),"014-010");
        }
    }

    private void setSpinnerValueChangeListener(String strValue, String str, TextView tv , TextView tvData)
    {
        try {
            if (strValue == null) {
                tv.setVisibility(View.GONE);
                tvData.setVisibility(View.GONE);
            } else if (strValue.equalsIgnoreCase(context.getString(R.string.none))) {
                tv.setVisibility(View.VISIBLE);
                tvData.setVisibility(View.VISIBLE);
                tvData.setText(str);
            } else {
                tv.setVisibility(View.GONE);
                tvData.setVisibility(View.GONE);
            }

        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.show_gp_data_error),  e.getMessage(),"014-011");
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

}
