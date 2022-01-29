package in.nic.ease_of_living.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import in.nic.ease_of_living.adapter.MasterCommonAdapter;
import in.nic.ease_of_living.dbo.DBMaster;
import in.nic.ease_of_living.dbo.MasterCommonController;
import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.models.MasterCommon;

/**
 * Created by Neha on 8/9/2017.
 */

public class PopulateMasterLists {

    public static ArrayList<MasterCommon> alLpgConnectionSchemeCategory= new ArrayList<>();
    public static ArrayList<MasterCommon> alLpgApplicationStatusCategory= new ArrayList<>();
    public static ArrayList<MasterCommon> alLedSchemeCategory = new ArrayList<>();
    public static ArrayList<MasterCommon> alBankAcTypeCategory = new ArrayList<>();
    public static ArrayList<MasterCommon> alLicTypeCategory = new ArrayList<>();
    public static ArrayList<MasterCommon> alAccidentalCoverTypeCategory= new ArrayList<>();
    public static ArrayList<MasterCommon> alImmunisationSourceCategory = new ArrayList<>();
    public static ArrayList<MasterCommon> alNutritionSuppServicesSourceCategory = new ArrayList<>();

    public static ArrayList<MasterCommon> alHealthServicesSourceCategory= new ArrayList<>();
    public static ArrayList<MasterCommon> alPreschoolEduServicesSourceCategory = new ArrayList<>();
    public static ArrayList<MasterCommon> alShgTypeCategory = new ArrayList<>();
    public static ArrayList<MasterCommon> alHouseTypeCategory= new ArrayList<>();
    public static ArrayList<MasterCommon> alHousingSchemeCategory = new ArrayList<>();
    public static ArrayList<MasterCommon> alHousingSchemeApplicationStatusCategory = new ArrayList<>();
    public static ArrayList<MasterCommon> alHealthSchemeCategory = new ArrayList<>();
    public static ArrayList<MasterCommon> alOldAgePensionSourceCategory= new ArrayList<>();


    public static ArrayList<MasterCommon> alWidowPensionSourceCategory = new ArrayList<>();
    public static ArrayList<MasterCommon> alDisabledPensionSourceCategory = new ArrayList<>();
    public static ArrayList<MasterCommon> alMobileContactTypeCategory = new ArrayList<>();
    public static ArrayList<MasterCommon> alFoodSecuritySchemeCategory = new ArrayList<>();
    public static ArrayList<MasterCommon> alMgnregaJobCardStatusCategory = new ArrayList<>();
    public static ArrayList<MasterCommon> alPensionSchemeCategory= new ArrayList<>();
    public static ArrayList<MasterCommon> alSkillDevelopmentSchemesCategory = new ArrayList<>();
    public static ArrayList<MasterCommon> alUncoveredHhdReasonCategory = new ArrayList<>();


    public static ArrayList<LinearLayout> alLinearLayout = new ArrayList<>();
    public static ArrayList<ImageView> alImageView = new ArrayList<>();


    public static MasterCommonAdapter adapterLpgConnectionSchemeCategory;
    public static MasterCommonAdapter adapterLpgApplicationStatusCategory;
    public static MasterCommonAdapter adapterLedSchemeCategory;
    public static MasterCommonAdapter adapterBankAcTypeCategory;
    public static MasterCommonAdapter adapterLicTypeCategory;
    public static MasterCommonAdapter adapterAccidentalCoverTypeCategory;
    public static MasterCommonAdapter adapterImmunisationSourceCategory;
    public static MasterCommonAdapter adapterNutritionSuppServicesSourceCategory;

    public static MasterCommonAdapter adapterHealthServicesSourceCategory;
    public static MasterCommonAdapter adapterPreschoolEduServicesSourceCategory;
    public static MasterCommonAdapter adapterShgTypeCategory;
    public static MasterCommonAdapter adapterHouseTypeCategory;
    public static MasterCommonAdapter adapterHousingSchemeCategory;
    public static MasterCommonAdapter adapterHousingSchemeApplicationStatusCategory;
    public static MasterCommonAdapter adapterHealthSchemeCategory;
    public static MasterCommonAdapter adapterOldAgePensionSourceCategory;

    public static MasterCommonAdapter adapterWidowPensionSourceCategory;
    public static MasterCommonAdapter adapterDisabledPensionSourceCategory;
    public static MasterCommonAdapter adapterMobileContactTypeCategory;
    public static MasterCommonAdapter adapterFoodSecuritySchemeCategory;
    public static MasterCommonAdapter adapterMgnregaJobCardStatusCategory;
    public static MasterCommonAdapter adapterPensionSchemeCategory;
    public static MasterCommonAdapter adapterSkillDevelopmentSchemesCategory;
    public static MasterCommonAdapter adapterUncoveredHhdReasonCategory;

    public static void populateMastersList(Context context)
    {
        populateArrayList(context);
        populateAdapters(context);
        setHashMaps(context);
    }

    private static void populateArrayList(Context context)
    {
        alLpgConnectionSchemeCategory.clear();
        alLpgApplicationStatusCategory.clear();
        alLedSchemeCategory.clear();
        alBankAcTypeCategory.clear();
        alLicTypeCategory.clear();
        alAccidentalCoverTypeCategory.clear();
        alImmunisationSourceCategory.clear();
        alNutritionSuppServicesSourceCategory.clear();


        alHealthServicesSourceCategory.clear();
        alPreschoolEduServicesSourceCategory.clear();
        alShgTypeCategory.clear();
        alHouseTypeCategory.clear();
        alHousingSchemeCategory.clear();
        alHousingSchemeApplicationStatusCategory.clear();
        alHealthSchemeCategory.clear();
        alOldAgePensionSourceCategory.clear();


        alWidowPensionSourceCategory.clear();
        alDisabledPensionSourceCategory.clear();
        alMobileContactTypeCategory.clear();
        alFoodSecuritySchemeCategory.clear();
        alMgnregaJobCardStatusCategory.clear();
        alPensionSchemeCategory.clear();
        alSkillDevelopmentSchemesCategory.clear();
        alUncoveredHhdReasonCategory.clear();

        // Populate arrayList from database
        alLpgConnectionSchemeCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 1);
        alLpgConnectionSchemeCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alLpgApplicationStatusCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 2);
        alLpgApplicationStatusCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alLedSchemeCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 3);
        alLedSchemeCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alBankAcTypeCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 4);
        alBankAcTypeCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alLicTypeCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 5);
        alLicTypeCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alAccidentalCoverTypeCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 6);
        alAccidentalCoverTypeCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alImmunisationSourceCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 7);
        alImmunisationSourceCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alNutritionSuppServicesSourceCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 8);
        alNutritionSuppServicesSourceCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alHealthServicesSourceCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 9);
        alHealthServicesSourceCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alPreschoolEduServicesSourceCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 10);
        alPreschoolEduServicesSourceCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alShgTypeCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 11);
        alShgTypeCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alHouseTypeCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 12);
        alHouseTypeCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alHousingSchemeCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 13);
        alHousingSchemeCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alHousingSchemeApplicationStatusCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 14);
        alHousingSchemeApplicationStatusCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alHealthSchemeCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 15);
        alHealthSchemeCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alOldAgePensionSourceCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 16);
        alOldAgePensionSourceCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alWidowPensionSourceCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 17);
        alWidowPensionSourceCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alDisabledPensionSourceCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 18);
        alDisabledPensionSourceCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alMobileContactTypeCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 19);
        alMobileContactTypeCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alFoodSecuritySchemeCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 20);
        alFoodSecuritySchemeCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));


        alMgnregaJobCardStatusCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 21);
        alMgnregaJobCardStatusCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alPensionSchemeCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 22);
        alPensionSchemeCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alSkillDevelopmentSchemesCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 23);
        alSkillDevelopmentSchemesCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

        alUncoveredHhdReasonCategory = MasterCommonController.getAllData(context, DBMaster.getInstance(context, true), 24);
        alUncoveredHhdReasonCategory.add(0, new MasterCommon(context.getString(R.string.spinner_heading_select_option)));

    }

    private static void populateAdapters(Context context)
    {
        // Make Adapters
        adapterLpgConnectionSchemeCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alLpgConnectionSchemeCategory);
        adapterLpgApplicationStatusCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alLpgApplicationStatusCategory);
        adapterLedSchemeCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alLedSchemeCategory);
        adapterBankAcTypeCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alBankAcTypeCategory);
        adapterLicTypeCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alLicTypeCategory);
        adapterAccidentalCoverTypeCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alAccidentalCoverTypeCategory);
        adapterImmunisationSourceCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alImmunisationSourceCategory);
        adapterNutritionSuppServicesSourceCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alNutritionSuppServicesSourceCategory);

        adapterHealthServicesSourceCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alHealthServicesSourceCategory);
        adapterPreschoolEduServicesSourceCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alPreschoolEduServicesSourceCategory);
        adapterShgTypeCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alShgTypeCategory);
        adapterHouseTypeCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alHouseTypeCategory);
        adapterHousingSchemeCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alHousingSchemeCategory);
        adapterHousingSchemeApplicationStatusCategory= new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alHousingSchemeApplicationStatusCategory);
        adapterHealthSchemeCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alHealthSchemeCategory);
        adapterOldAgePensionSourceCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alOldAgePensionSourceCategory);

        adapterWidowPensionSourceCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alWidowPensionSourceCategory);
        adapterDisabledPensionSourceCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alDisabledPensionSourceCategory);
        adapterMobileContactTypeCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alMobileContactTypeCategory);
        adapterFoodSecuritySchemeCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alFoodSecuritySchemeCategory);
        adapterMgnregaJobCardStatusCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alMgnregaJobCardStatusCategory);
        adapterPensionSchemeCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alPensionSchemeCategory);
        adapterSkillDevelopmentSchemesCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alSkillDevelopmentSchemesCategory);
        adapterUncoveredHhdReasonCategory = new MasterCommonAdapter(context, android.R.layout.simple_spinner_item, alUncoveredHhdReasonCategory);

    }

    private static void setHashMaps(Context context)
    {

        for(int i = 0; i < alLpgConnectionSchemeCategory.size(); i++)
        {
            MasterCommonController.mapLpgConnectionSchemeCategory.put(alLpgConnectionSchemeCategory.get(i).getType_code(), alLpgConnectionSchemeCategory.get(i).getType_name());
        }
        for(int i = 0; i < alLpgApplicationStatusCategory.size(); i++)
        {
            MasterCommonController.mapLpgApplicationStatusCategory.put(alLpgApplicationStatusCategory.get(i).getType_code(), alLpgApplicationStatusCategory.get(i).getType_name());
        }
        for(int i = 0; i < alLedSchemeCategory.size(); i++)
        {
            MasterCommonController.mapLedSchemeCategory.put(alLedSchemeCategory.get(i).getType_code(), alLedSchemeCategory.get(i).getType_name());
        }
        for(int i = 0; i < alBankAcTypeCategory.size(); i++)
        {
            MasterCommonController.mapBankAcTypeCategory.put(alBankAcTypeCategory.get(i).getType_code(), alBankAcTypeCategory.get(i).getType_name());
        }
        for(int i = 0; i < alLicTypeCategory.size(); i++)
        {
            MasterCommonController.mapLicTypeCategory.put(alLicTypeCategory.get(i).getType_code(), alLicTypeCategory.get(i).getType_name());
        }

        for(int i = 0; i < alAccidentalCoverTypeCategory.size(); i++)
        {
            MasterCommonController.mapAccidentalCoverTypeCategory.put(alAccidentalCoverTypeCategory.get(i).getType_code(), alAccidentalCoverTypeCategory.get(i).getType_name());
        }

        for(int i = 0; i < alImmunisationSourceCategory.size(); i++)
        {
            MasterCommonController.mapImmunisationSourceCategory.put(alImmunisationSourceCategory.get(i).getType_code(), alImmunisationSourceCategory.get(i).getType_name());
        }

        for(int i = 0; i < alNutritionSuppServicesSourceCategory.size(); i++)
        {
            MasterCommonController.mapNutritionSuppServicesSourceCategory.put(alNutritionSuppServicesSourceCategory.get(i).getType_code(), alNutritionSuppServicesSourceCategory.get(i).getType_name());
        }

        for(int i = 0; i < alHealthServicesSourceCategory.size(); i++)
        {
            MasterCommonController.mapHealthServicesSourceCategory.put(alHealthServicesSourceCategory.get(i).getType_code(), alHealthServicesSourceCategory.get(i).getType_name());
        }
        for(int i = 0; i < alPreschoolEduServicesSourceCategory.size(); i++)
        {
            MasterCommonController.mapPreschoolEduServicesSourceCategory.put(alPreschoolEduServicesSourceCategory.get(i).getType_code(), alPreschoolEduServicesSourceCategory.get(i).getType_name());
        }
        for(int i = 0; i < alShgTypeCategory.size(); i++)
        {
            MasterCommonController.mapShgTypeCategory.put(alShgTypeCategory.get(i).getType_code(), alShgTypeCategory.get(i).getType_name());
        }
        for(int i = 0; i < alHouseTypeCategory.size(); i++)
        {
            MasterCommonController.mapHouseTypeCategory.put(alHouseTypeCategory.get(i).getType_code(), alHouseTypeCategory.get(i).getType_name());
        }

        for(int i = 0; i < alHousingSchemeCategory.size(); i++)
        {
            MasterCommonController.mapHousingSchemeCategory.put(alHousingSchemeCategory.get(i).getType_code(), alHousingSchemeCategory.get(i).getType_name());
        }

        for(int i = 0; i < alHousingSchemeApplicationStatusCategory.size(); i++)
        {
            MasterCommonController.mapHousingSchemeApplicationStatusCategory.put(alHousingSchemeApplicationStatusCategory.get(i).getType_code(), alHousingSchemeApplicationStatusCategory.get(i).getType_name());
        }

        for(int i = 0; i < alHealthSchemeCategory.size(); i++)
        {
            MasterCommonController.mapHealthSchemeCategory.put(alHealthSchemeCategory.get(i).getType_code(), alHealthSchemeCategory.get(i).getType_name());
        }

        for(int i = 0; i < alOldAgePensionSourceCategory.size(); i++)
        {
            MasterCommonController.mapNutritionSuppServicesSourceCategory.put(alOldAgePensionSourceCategory.get(i).getType_code(), alOldAgePensionSourceCategory.get(i).getType_name());
        }



        for(int i = 0; i < alWidowPensionSourceCategory.size(); i++)
        {
            MasterCommonController.mapWidowPensionSourceCategory.put(alWidowPensionSourceCategory.get(i).getType_code(), alWidowPensionSourceCategory.get(i).getType_name());
        }
        for(int i = 0; i < alDisabledPensionSourceCategory.size(); i++)
        {
            MasterCommonController.mapDisabledPensionSourceCategory.put(alDisabledPensionSourceCategory.get(i).getType_code(), alDisabledPensionSourceCategory.get(i).getType_name());
        }
        for(int i = 0; i < alMobileContactTypeCategory.size(); i++)
        {
            MasterCommonController.mapMobileContactTypeCategory.put(alMobileContactTypeCategory.get(i).getType_code(), alMobileContactTypeCategory.get(i).getType_name());
        }
        for(int i = 0; i < alFoodSecuritySchemeCategory.size(); i++)
        {
            MasterCommonController.mapFoodSecuritySchemeCategory.put(alFoodSecuritySchemeCategory.get(i).getType_code(), alFoodSecuritySchemeCategory.get(i).getType_name());
        }
        for(int i = 0; i < alMgnregaJobCardStatusCategory.size(); i++)
        {
            MasterCommonController.mapMgnregaJobCardStatusCategory.put(alMgnregaJobCardStatusCategory.get(i).getType_code(), alMgnregaJobCardStatusCategory.get(i).getType_name());
        }

        for(int i = 0; i < alPensionSchemeCategory.size(); i++)
        {
            MasterCommonController.mapPensionSchemeCategory.put(alPensionSchemeCategory.get(i).getType_code(), alPensionSchemeCategory.get(i).getType_name());
        }

        for(int i = 0; i < alSkillDevelopmentSchemesCategory.size(); i++)
        {
            MasterCommonController.mapSkillDevelopmentSchemesCategory.put(alSkillDevelopmentSchemesCategory.get(i).getType_code(), alSkillDevelopmentSchemesCategory.get(i).getType_name());
        }

        for(int i = 0; i < alUncoveredHhdReasonCategory.size(); i++)
        {
            MasterCommonController.mapUncoveredHhdReasonCategory.put(alUncoveredHhdReasonCategory.get(i).getType_code(), alUncoveredHhdReasonCategory.get(i).getType_name());
        }


    }
}
