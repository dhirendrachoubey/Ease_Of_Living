package in.nic.ease_of_living.gp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.nic.ease_of_living.adapter.SeccHouseholdPaginationAdapter;
import in.nic.ease_of_living.adapter.SeccPopPaginationAdapter;
import in.nic.ease_of_living.broadcast.NetworkChangeReceiver;
import in.nic.ease_of_living.dbo.DBHelper;
import in.nic.ease_of_living.dbo.SeccHouseholdController;
import in.nic.ease_of_living.dbo.SeccPopulationController;
import in.nic.ease_of_living.interfaces.Communicator;
import in.nic.ease_of_living.listener.PaginationScrollListener;
import in.nic.ease_of_living.listener.RecyclerTouchListener;
import in.nic.ease_of_living.models.SeccHousehold;
import in.nic.ease_of_living.models.SeccPopulation;
import in.nic.ease_of_living.supports.IsConnected;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MySharedPref;
import in.nic.ease_of_living.supports.MyVolley;
import in.nic.ease_of_living.supports.Password;
import in.nic.ease_of_living.supports.RedAsterisk;
import in.nic.ease_of_living.utils.AESHelper;
import in.nic.ease_of_living.utils.InputFilters;
import in.nic.ease_of_living.utils.NetworkRegistered;


/**
 * Created by Neha Jain on 14/Nov/2019.
 */
// 011-004"
public class SearchResident extends AppCompatActivity implements AdapterView.OnClickListener, Communicator {
    NetworkChangeReceiver mNetworkReceiver;

    Context context;
    Button btnSearch;
    //VillageAdapter villageAdapter;
    ArrayAdapter<String> yobAdapter;
    SeccPopPaginationAdapter seccPopPaginationAdapter;
    SeccHouseholdPaginationAdapter seccHhdPaginationAdapter;
    //SCHEMEIDAdapter schemeidBaseAdapter;
    String yobSelectedvalue = "";
    //ArrayList<Village> alVillage = new ArrayList<>();
    ArrayList<String> alYob = new ArrayList<String>();

    ArrayList<SeccPopulation> alSeccPop = new ArrayList<>();
    ArrayList<SeccHousehold> alSeccHousehold = new ArrayList<>();
    private static final int PAGE_START = 0;
    private static final int MAX_LIST_ITEM = 50;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 0;
    private int currentPage = PAGE_START;
    private String strUserPassw = null;
    private int iListType; // 1--Household, 2--skip hoseholds, 3--Population
    String TAG = "SearchResident",strSelectedSchemeID;
    ImageView ivArrow;
    Spinner spinnerYob; //spinnerID;
    LinearLayout llSearchBar;
    TextView tvTotalSearch, tvNameSearch, tvHhdIdSearch;
    RecyclerView rvSearchResident;
    RadioButton rbPendingHousehold, rbSkipHousehold, rbSearchPop, rbByName, rbByHhdId;
    LinearLayout llTvByName, llTvByHhdId; //llTvByID,llIDNameByID;
    EditText etNameSearch, etFatherNameSearch, etMotherNameSearch, etHhdIdSearch;
    LinearLayoutManager mLayoutManager;
    RadioGroup rgOption;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        Intent intent = getIntent();
        strUserPassw = intent.getStringExtra("user_password");
        if (Password.matchUserCredentials(context, intent.getStringExtra("user_id"), strUserPassw)) {
            setContentView(R.layout.activity_search_resident);
            getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getString(R.string.app_name) + "</font>"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            findView();
            setListiner();
            setEditTextFilters();
            setMandatoryAstrik();
            alSeccHousehold.clear();
            iListType = 1;

            mNetworkReceiver = new NetworkChangeReceiver();
            NetworkRegistered.registerNetworkBroadcastForNougat(context, mNetworkReceiver);


            //Support.setChangeListener(etPmaySearch, tvPmaySearch);
            //Support.setChangeListener(etMgSearch, tvMgSearch);

            try {
                alSeccHousehold = SeccHouseholdController.getHouseholdList(context, DBHelper.getInstance(context, false));
                TOTAL_PAGES = alSeccHousehold.size() / MAX_LIST_ITEM;
                if (TOTAL_PAGES * MAX_LIST_ITEM < alSeccHousehold.size())
                    TOTAL_PAGES = TOTAL_PAGES + 1;

            }catch (Exception e)
            {

            }
            alYob.clear();
            int thisYear = Calendar.getInstance().get(Calendar.YEAR);
            yobSelectedvalue = String.valueOf(thisYear);
            for (int i = thisYear; i > thisYear - 150; i--) {
                alYob.add(Integer.toString(i));
            }
            alYob.add(0, context.getString(R.string.select_yob));
            yobAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, alYob);
            spinnerYob.setAdapter(yobAdapter);

            // RecyclerView
            seccPopPaginationAdapter = new SeccPopPaginationAdapter(context);
            seccHhdPaginationAdapter = new SeccHouseholdPaginationAdapter(context);
            seccHhdPaginationAdapter.clear();
            rvSearchResident.setHasFixedSize(true);

            mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

            rvSearchResident.setLayoutManager(mLayoutManager);
            rvSearchResident.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            rvSearchResident.setItemAnimator(new DefaultItemAnimator());
            rvSearchResident.setAdapter(seccHhdPaginationAdapter);
            loadFirstPageSeccHhd();


            llSearchBar.setVisibility(View.GONE);
            ivArrow.setImageResource(R.mipmap.icon_arrow_down);
            rvSearchResident.setVisibility(View.VISIBLE);

            rvSearchResident.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvSearchResident, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int pos) {
                    Intent intent = new Intent(SearchResident.this, ResidentMember.class);
                    Integer iHhdUid = null;
                    if((iListType == 1) || (iListType == 2))
                        iHhdUid = alSeccHousehold.get(pos).getHhd_uid();
                    else if(iListType == 3)
                        iHhdUid = alSeccPop.get(pos).getHhd_uid();
                    intent.putExtra("HH_UID", iHhdUid);
                    intent.putExtra("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                    intent.putExtra("user_password", strUserPassw);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));


            spinnerYob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0)
                        yobSelectedvalue = "";
                    else
                        yobSelectedvalue = yobAdapter.getItem(i);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            /*spinnerID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i == 0)
                       strSelectedSchemeID ="0";
                    else {
                        SchemeIdBase schemeIdBase  = schemeidBaseAdapter.getItem(i);

                        strSelectedSchemeID = schemeIdBase.getId_type();
                        Log.d(TAG, " aspinner strSelectedSchemeID"+strSelectedSchemeID);

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });*/
            rvSearchResident.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
                @Override
                protected void loadMoreItems() {
                    isLoading = true;
                    currentPage += 1;

                    // mocking network delay for API call
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if((iListType == 1) || (iListType == 2))
                                loadNextPageSeccHhd();
                            else if(iListType == 3)
                                loadNextPageSeccPop();
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
        }
    }

    private void setEditTextFilters() {
        etNameSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), InputFilters.alphaWithSpSymbolFilter1});
        etFatherNameSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), InputFilters.alphaWithSpSymbolFilter1});
        etMotherNameSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50), InputFilters.alphaWithSpSymbolFilter1});

    }

    private void findView() {
        ivArrow = (ImageView) findViewById(R.id.ivArrow);

        //Linear Layout
        llSearchBar = (LinearLayout) findViewById(R.id.llSearchBar);
        llTvByName = (LinearLayout) findViewById(R.id.llTvByName);
        llTvByHhdId = (LinearLayout) findViewById(R.id.llTvByHhdId);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        rvSearchResident = (RecyclerView) findViewById(R.id.rvSearchResident);

        //TextView
        tvTotalSearch = (TextView) findViewById(R.id.tvTotalSearch);
        tvNameSearch = (TextView) findViewById(R.id.tvNameSearch);
        tvHhdIdSearch = (TextView) findViewById(R.id.tvHhdIdSearch);

        rbPendingHousehold = (RadioButton) findViewById(R.id.rbPendingHousehold);
        rbSkipHousehold = (RadioButton) findViewById(R.id.rbSkipHousehold);
        rbSearchPop = (RadioButton) findViewById(R.id.rbSearchPop);
        rbByName = (RadioButton) findViewById(R.id.rbByName);
        rbByHhdId = (RadioButton) findViewById(R.id.rbByHhdId);

        // Radio group
        rgOption = (RadioGroup) findViewById(R.id.rgOption);

        // EditText
        etNameSearch  = (EditText) findViewById(R.id.etNameSearch);
        etFatherNameSearch  = (EditText) findViewById(R.id.etFatherNameSearch);
        etMotherNameSearch  = (EditText) findViewById(R.id.etMotherNameSearch);
        etHhdIdSearch  = (EditText) findViewById(R.id.etHhdIdSearch);

        //spinner
        spinnerYob  = (Spinner) findViewById(R.id.spinnerYob);
    }

    private void setListiner() {
        ivArrow.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        rbPendingHousehold.setOnClickListener(this);
        rbSkipHousehold.setOnClickListener(this);
        rbSearchPop.setOnClickListener(this);
        rbByHhdId.setOnClickListener(this);
        rbByName.setOnClickListener(this);

    }

    private void setMandatoryAstrik() {
        try {
            RedAsterisk.setAstrikOnTextView(tvNameSearch, context.getString(R.string.name));
            RedAsterisk.setAstrikOnTextView(tvHhdIdSearch, context.getString(R.string.hhd_id));
        }catch(Exception e)
        {

        }
    }

    @Override
    public void onClick(View view) {
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
                // hide keyboard
                if ((MyVolley.dialog == null) || ((MyVolley.dialog != null) && (!MyVolley.dialog.isShowing())))
                    MyVolley.dialog = new ProgressDialog(context);
                if (!MyVolley.dialog.isShowing()) {
                    MyVolley.dialog.setMessage(context.getString(R.string.please_wait));
                    MyVolley.dialog.setCancelable(false);
                    MyVolley.dialog.setCanceledOnTouchOutside(false);
                    MyVolley.dialog.show();
                }
                Thread mThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            /*InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
*/
                            seccPopPaginationAdapter.clear();

                            if ( !rbByHhdId.isChecked() && !rbByName.isChecked()) {
                                MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.search_resident_warning), context.getString(R.string.msg_select_option), "011-001");
                                MyVolley.dialog.dismiss();
                            } else {
                                if (rbByName.isChecked()) {

                                    if (etNameSearch.getText().toString().trim().isEmpty()) {

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.search_resident_warning), context.getString(R.string.enter_name), "011-002");

                                            }
                                        });
                                    } else {

                                        alSeccPop.clear();
                                        alSeccPop = SeccPopulationController.getPopByName(DBHelper.getInstance(context, false), context, etNameSearch.getText().toString().trim().toUpperCase(),
                                                etFatherNameSearch.getText().toString().trim().toUpperCase(), etMotherNameSearch.getText().toString().trim().toUpperCase(), yobSelectedvalue);
                                    }
                                }
                                else if (rbByHhdId.isChecked()) {

                                    if (etHhdIdSearch.getText().toString().trim().isEmpty()) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.search_resident_warning), context.getString(R.string.enter_hhd_id), "011-002");

                                            }
                                        });
                                    } else {

                                        alSeccPop.clear();
                                        alSeccPop = SeccPopulationController.getPopByHhdId(DBHelper.getInstance(context, false), context, Integer.parseInt(etHhdIdSearch.getText().toString().trim()));
                                    }
                                }


                                if (alSeccPop.size() > 0) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tvTotalSearch.setVisibility(View.VISIBLE);
                                            tvTotalSearch.setTextColor(Color.GREEN);
                                            tvTotalSearch.setText(context.getString(R.string.total_records_found) + String.valueOf(alSeccPop.size()));
                                            TOTAL_PAGES = alSeccPop.size() / MAX_LIST_ITEM;
                                            if (TOTAL_PAGES * MAX_LIST_ITEM < alSeccPop.size())
                                                TOTAL_PAGES = TOTAL_PAGES + 1;
                                            if ((TOTAL_PAGES == 0) || (TOTAL_PAGES == 1))
                                                currentPage = TOTAL_PAGES;
                                            iListType = 3;
                                            seccPopPaginationAdapter.clear();
                                            rvSearchResident.setAdapter(seccPopPaginationAdapter);
                                            loadFirstPageSeccPop();
                                            llSearchBar.setVisibility(View.GONE);
                                            ivArrow.setImageResource(R.mipmap.icon_arrow_down);
                                            rvSearchResident.setVisibility(View.VISIBLE);
                                        }
                                    });

                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tvTotalSearch.setVisibility(View.VISIBLE);
                                            tvTotalSearch.setTextColor(Color.RED);
                                            tvTotalSearch.setText(context.getString(R.string.no_record_found));
                                            rvSearchResident.setVisibility(View.GONE);
                                        }
                                    });
                                }
                                MyVolley.dialog.dismiss();
                            }

                        }catch(Exception e)
                        {
                            MyVolley.dialog.dismiss();
                        }

                    }
                };
                mThread.start();
                if(alSeccPop.size() > 0)
                {

                }
                break;

            case R.id.rbPendingHousehold:
                iListType = 1;
                llSearchBar.setVisibility(View.GONE);
                ivArrow.setImageResource(R.mipmap.icon_arrow_down);
                rvSearchResident.setVisibility(View.VISIBLE);
                tvTotalSearch.setVisibility(View.GONE);
                alSeccHousehold.clear();
                seccHhdPaginationAdapter.clear();
                alSeccHousehold = SeccHouseholdController.getHouseholdList(context, DBHelper.getInstance(context, false));
                currentPage = PAGE_START + 1;
                TOTAL_PAGES = alSeccHousehold.size() / MAX_LIST_ITEM;
                if (TOTAL_PAGES * MAX_LIST_ITEM < alSeccHousehold.size())
                    TOTAL_PAGES = TOTAL_PAGES + 1;
                if((TOTAL_PAGES == 0) || (TOTAL_PAGES == 1))
                    currentPage = TOTAL_PAGES;
                rvSearchResident.setAdapter(seccHhdPaginationAdapter);
                loadFirstPageSeccHhd();
                break;
            case R.id.rbSkipHousehold:
                iListType = 2;
                llSearchBar.setVisibility(View.GONE);
                ivArrow.setImageResource(R.mipmap.icon_arrow_down);
                rvSearchResident.setVisibility(View.VISIBLE);
                tvTotalSearch.setVisibility(View.GONE);
                alSeccHousehold.clear();
                seccHhdPaginationAdapter.clear();
                alSeccHousehold = SeccHouseholdController.getUncoveredHouseholdList(context, DBHelper.getInstance(context, false));
                currentPage = PAGE_START + 1;
                TOTAL_PAGES = alSeccHousehold.size() / MAX_LIST_ITEM;
                if (TOTAL_PAGES * MAX_LIST_ITEM < alSeccHousehold.size())
                    TOTAL_PAGES = TOTAL_PAGES + 1;
                if((TOTAL_PAGES == 0) || (TOTAL_PAGES == 1))
                    currentPage = TOTAL_PAGES;
                rvSearchResident.setAdapter(seccHhdPaginationAdapter);
                loadFirstPageSeccHhd();
                break;
            case R.id.rbSearchPop:
                iListType = 3;
                llSearchBar.setVisibility(View.VISIBLE);
                ivArrow.setImageResource(R.mipmap.icon_arrow_up);
                seccPopPaginationAdapter.clear();
                currentPage = PAGE_START + 1;
                TOTAL_PAGES = alSeccPop.size() / MAX_LIST_ITEM;
                if (TOTAL_PAGES * MAX_LIST_ITEM < alSeccPop.size())
                    TOTAL_PAGES = TOTAL_PAGES + 1;
                if((TOTAL_PAGES == 0) || (TOTAL_PAGES == 1))
                    currentPage = TOTAL_PAGES;
                rvSearchResident.setAdapter(seccPopPaginationAdapter);
                loadFirstPageSeccPop();
                break;
            case R.id.rbByName:
                llTvByHhdId.setVisibility(View.GONE);
                llTvByName.setVisibility(View.VISIBLE);
                break;
            case R.id.rbByHhdId:
                llTvByHhdId.setVisibility(View.VISIBLE);
                llTvByName.setVisibility(View.GONE);
                break;

        }
    }

    MenuItem menuItem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        menuItem = menu.findItem(R.id.action_internet_status);
        if (IsConnected.isInternet_connected(context,false))
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
    public void onDestroy() {
        super.onDestroy();
        NetworkRegistered.unregisterNetworkChanges(context, mNetworkReceiver);
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

    List<SeccPopulation> pops;

    private void loadFirstPageSeccPop() {
        pops = new ArrayList<>();
        isLastPage = false;
        List<SeccPopulation> populations = getDataSeccPop(seccPopPaginationAdapter.getItemCount());
        if((MyVolley.dialog != null ) && (MyVolley.dialog.isShowing()))
            MyVolley.dialog.dismiss();
        seccPopPaginationAdapter.addAll(populations);

        if (currentPage < TOTAL_PAGES) seccPopPaginationAdapter.addLoadingFooter();
        else isLastPage = true;

    }

    private List<SeccPopulation> getDataSeccPop(int itemCount) {
        pops.clear();
        int i = 0;
        int size = itemCount == 0 ? (itemCount + MAX_LIST_ITEM) : (itemCount + MAX_LIST_ITEM - 1);
        for (i = itemCount == 0 ? (itemCount) : (itemCount - 1); i < size && i < alSeccPop.size(); i++) {
            SeccPopulation p = new SeccPopulation(alSeccPop.get(i).getName(), alSeccPop.get(i).getFather_name(), alSeccPop.get(i).getMother_name(), alSeccPop.get(i).getDob(), alSeccPop.get(i).getHhd_uid(), alSeccPop.get(i).getGenderid());
            pops.add(p);
        }
        return pops;
    }

    private void loadNextPageSeccPop() {
        List<SeccPopulation> populations = getDataSeccPop(seccPopPaginationAdapter.getItemCount());
        isLoading = false;

        seccPopPaginationAdapter.removeLoadingFooter();

        seccPopPaginationAdapter.addAll(populations);

        if (currentPage != TOTAL_PAGES) seccPopPaginationAdapter.addLoadingFooter();
        else isLastPage = true;
    }

    /*******************/
    /* Secc Household*/
    List<SeccHousehold> hhdList;

    private void loadFirstPageSeccHhd() {
        hhdList = new ArrayList<>();
        isLastPage = false;
        List<SeccHousehold> hhds = getDataSeccHhd(seccHhdPaginationAdapter.getItemCount());
        //MyVolley.dialog.dismiss();
        seccHhdPaginationAdapter.addAll(hhds);

        if (currentPage < TOTAL_PAGES) seccHhdPaginationAdapter.addLoadingFooter();
        else isLastPage = true;

    }

    private List<SeccHousehold> getDataSeccHhd(int itemCount) {
        hhdList.clear();
        int i = 0;
        int size = itemCount == 0 ? (itemCount + MAX_LIST_ITEM) : (itemCount + MAX_LIST_ITEM - 1);
        for (i = itemCount == 0 ? (itemCount) : (itemCount - 1); i < size && i < alSeccHousehold.size(); i++) {
            SeccHousehold h = new SeccHousehold(alSeccHousehold.get(i).getHead_name(), alSeccHousehold.get(i).getHead_fathername(), alSeccHousehold.get(i).getHead_mothername(), alSeccHousehold.get(i).getHhd_uid(), alSeccHousehold.get(i).getAddressline1(), alSeccHousehold.get(i).getAddressline2(), alSeccHousehold.get(i).getAddressline3());
            hhdList.add(h);
        }
        return hhdList;
    }

    private void loadNextPageSeccHhd() {
        List<SeccHousehold> hhds = getDataSeccHhd(seccHhdPaginationAdapter.getItemCount());
        isLoading = false;

        seccHhdPaginationAdapter.removeLoadingFooter();

        seccHhdPaginationAdapter.addAll(hhds);

        if (currentPage != TOTAL_PAGES) seccHhdPaginationAdapter.addLoadingFooter();
        else isLastPage = true;
    }

}
