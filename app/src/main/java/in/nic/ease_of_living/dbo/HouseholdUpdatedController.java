package in.nic.ease_of_living.dbo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.models.HouseholdUpdated;
import in.nic.ease_of_living.supports.MyAlert;


/**
 * Created by Chinki Sai on 7/4/2017.
 */

public class HouseholdUpdatedController {
    private static final String TABLE_NAME = "household_updated";
    private static final String STATE_CODE = "state_code";
    private static final String DISTRICT_CODE = "district_code";
    private static final String SUB_DISTRICT_CODE = "sub_district_code";
    private static final String BLOCK_CODE = "block_code";
    private static final String GP_CODE = "gp_code";
    private static final String VILLAGE_CODE = "village_code";
    private static final String SHORT_USER_ID = "short_user_id";
    private static final String HHD_SL_NO = "hhd_sl_no";
    private static final String DISTRICT_CENSUS_CODE = "district_census_code";
    private static final String TOWN_CENSUS_CODE = "town_census_code";
    private static final String HHD_UID = "hhd_uid";
    private static final String HHD_LATITUDE = "hhd_latitude";
    private static final String HHD_LONGITUDE = "hhd_longitude";
    private static final String HH_HEAD_SL_NO = "hh_head_sl_no";
    private static final String HH_HEAD_NAME = "hh_head_name";
    private static final String HOUSEHOLD_TYPE_CODE = "household_type_code";
    private static final String ADDRESS_LINE_1 = "addressline1";
    private static final String ADDRESS_LINE_2 = "addressline2";
    private static final String ADDRESS_LINE_3 = "addressline3";
    private static final String ADDRESS_LINE_4 = "addressline4";
    private static final String ADDRESS_LINE_5 = "addressline5";
    private static final String PINCODE = "pincode";
    private static final String PRED_MAT_OF_WALL_DWELLING_ROOM_CODE = "pred_mat_of_wall_dwelling_room_code";
    private static final String PRED_MAT_OF_ROOF_DWELLING_ROOM_CODE = "pred_mat_of_roof_dwelling_room_code";
    private static final String OWNERSHIP_STATUS_CODE = "ownership_status_code";
    private static final String HH_NO_OF_DWELLING_ROOMS_EXC_IN_POSS = "hh_no_of_dwelling_rooms_exc_in_poss";
    private static final String PIPED_DRINKING_WATER_CODE = "piped_drinking_water_code";
    private static final String HOUSE_ROAD_CONNECTIVITY_CODE = "house_road_connectivity_code";
    private static final String OUTSIDE_ROAD_WITH_STREET_LIGHT_CODE = "outside_road_with_street_light_code";
    private static final String DRAINAGE_FACILITY_CODE = "drainage_facility_code";
    private static final String LATRINE_FACILITY_CODE = "latrine_facility_code";
    private static final String LIGHTING_SOURCE_CODE = "lighting_source_code";
    private static final String COOKING_FUEL_CODE = "cooking_fuel_code";
    private static final String HOUSE_INCOME_SOURCE_CODE = "household_income_source_code";
    private static final String PO_ACCOUNT_CODE = "po_account_code";
    private static final String RATION_CARD_STATUS_CODE = "ration_card_status_code";
    private static final String RATION_CARD_NO = "ration_card_no";
    private static final String PHC_SUB_CENTER_CODE = "phc_sub_center_code";
    private static final String CHILD_FOR_ICDS_CODE = "child_for_icds_code";
    private static final String AGRICULTURE_LOAN_STATUS_CODE = "agriculture_loan_status_code";
    private static final String HH_LIABILITY_RANGE_CODE = "hh_liability_range_code";
    private static final String AGRICULTURE_LOAN_WAIVED_CODE = "agriculture_loan_waived_code";
    private static final String TELEPHONE_TYPE_CODE = "telephone_type_code";
    private static final String HH_CONTACT_NO = "hh_contact_no";
    private static final String REFRIGERATOR_STATUS_CODE = "refrigerator_status_code";
    private static final String AC_STATUS_CODE = "ac_status_code";
    private static final String WASHING_MACHINE_STATUS_CODE = "washing_machine_status_code";
    private static final String SMARTPHONE_STATUS_CODE = "smartphone_status_code";
    private static final String REGISTERED_MOTOR_CODE = "registered_motor_code";
    private static final String LAND_EXCLUDING_HOME_STATUS_CODE = "land_excluding_home_status_code";
    private static final String TOTAL_UNIRRIGATEDLAND_IN_ACRES = "total_unirrigatedland_in_acres";
    private static final String WITH_ASSURED_IRRIGATION_FOR_TWO_CROPS_IN_ACRES = "with_assured_irrigation_for_two_crops_in_acres";
    private static final String OTHER_IRRIGATED_LAND_IN_ACRES = "other_irrigated_land_in_acres";
    private static final String MECHANIZED_MACHINE_STATUS_CODE = "mechanized_machine_status_code";
    private static final String IRRIGATION_EQUIPMENT_STATUS_CODE = "irrigation_equipment_status_code";
    private static final String KISAN_CREDIT_CARD_GRT_50000_STATUS_CODE = "kisan_credit_card_grt_50000_status_code";
    private static final String NFSA_FREE_MEAL_NUT_SUPPORT_CODE = "nfsa_free_meal_nut_support_code";
    private static final String NFSA_RS_6000_NUT_SUPPORT_CODE = "nfsa_rs_6000_nut_support_code";
    private static final String NFSA_AAGANWADI_NUT_SUPPORT_CODE = "nfsa_aaganwadi_nut_support_code";
    private static final String NFSA_MID_DAY_MEAL_CODE = "nfsa_mid_day_meal_code";
    private static final String NFSA_FOOD_GAIN_UNDER_PDSA_CODE = "nfsa_food_gain_under_pdsa_code";
    private static final String SHELTERLESS_CODE = "shelterless_code";
    private static final String PRIMITIVE_TRIBAL_GROUP_CODE = "primitive_tribal_group_code";
    private static final String LEGALLY_BONDED_LABOUR_CODE = "legally_bonded_labour_code";
    private static final String MANUAL_SCAVENGER_CODE = "manual_scavenger_code";
    private static final String SEPARATE_ROOM_AS_KITCHEN_CODE = "separate_room_as_kitchen_code";
    private static final String COMPUTER_LAPTOP_AVAIL_CODE = "COMPUTER_LAPTOP_AVAIL_CODE";
    private static final String DT_CREATED = "dt_created";
    private static final String USER_ID = "user_id";
    private static final String DEVICE_ID = "device_id";
    private static final String APP_ID = "app_id";
    private static final String APP_VERSION = "app_version";
    private static final String IS_COMPLETED = "is_completed";
    private static final String IS_SYNCHRONIZED = "is_synchronized";
    private static final String IS_UPLOADED = "is_uploaded";
    private static final String DT_SYNC = "dt_sync";
    private static final String DT_UPDATED = "dt_updated";
    private static final String IS_NEW_HHD = "is_new_hhd";
    private static final String ENUMERATED_BY = "enumerated_by";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            +  STATE_CODE + " INTEGER ,"
            +  DISTRICT_CODE + " INTEGER ,"
            +  SUB_DISTRICT_CODE + " INTEGER ,"
            +  BLOCK_CODE + " INTEGER ,"
            +  GP_CODE + " INTEGER ,"
            +  VILLAGE_CODE + " INTEGER ,"
            +  SHORT_USER_ID + " TEXT ,"
            +  HHD_SL_NO + " INTEGER ,"
            +  DISTRICT_CENSUS_CODE + " TEXT ,"
            +  TOWN_CENSUS_CODE + " TEXT ,"
            +  HHD_UID + " TEXT ,"
            +  HHD_LATITUDE + " TEXT ,"
            +  HHD_LONGITUDE + " TEXT ,"
            +  HH_HEAD_SL_NO + " TEXT ,"
            +  HH_HEAD_NAME + " TEXT ,"
            +  HOUSEHOLD_TYPE_CODE + " INTEGER ,"
            +  ADDRESS_LINE_1 + " TEXT ,"
            +  ADDRESS_LINE_2 + " TEXT ,"
            +  ADDRESS_LINE_3 + " TEXT ,"
            +  ADDRESS_LINE_4 + " TEXT ,"
            +  ADDRESS_LINE_5 + " TEXT ,"
            +  PINCODE + " TEXT ,"
            +  PRED_MAT_OF_WALL_DWELLING_ROOM_CODE + " INTEGER ,"
            +  PRED_MAT_OF_ROOF_DWELLING_ROOM_CODE + " INTEGER ,"
            +  OWNERSHIP_STATUS_CODE + " INTEGER ,"
            +  HH_NO_OF_DWELLING_ROOMS_EXC_IN_POSS + " INTEGER ,"
            +  PIPED_DRINKING_WATER_CODE + " INTEGER ,"
            +  HOUSE_ROAD_CONNECTIVITY_CODE + " INTEGER ,"
            +  OUTSIDE_ROAD_WITH_STREET_LIGHT_CODE + " INTEGER ,"
            +  DRAINAGE_FACILITY_CODE + " INTEGER ,"
            +  LATRINE_FACILITY_CODE + " INTEGER ,"
            +  LIGHTING_SOURCE_CODE + " INTEGER ,"
            +  COOKING_FUEL_CODE + " INTEGER ,"
            +  HOUSE_INCOME_SOURCE_CODE + " INTEGER ,"
            +  PO_ACCOUNT_CODE + " INTEGER ,"
            +  RATION_CARD_STATUS_CODE + " INTEGER ,"
            +  RATION_CARD_NO + " TEXT ,"
            +  PHC_SUB_CENTER_CODE + " INTEGER ,"
            +  CHILD_FOR_ICDS_CODE + " INTEGER ,"
            +  AGRICULTURE_LOAN_STATUS_CODE + " INTEGER ,"
            +  HH_LIABILITY_RANGE_CODE + " INTEGER ,"
            +  AGRICULTURE_LOAN_WAIVED_CODE + " INTEGER ,"
            +  TELEPHONE_TYPE_CODE + " INTEGER ,"
            +  HH_CONTACT_NO + " TEXT ,"
            +  REFRIGERATOR_STATUS_CODE + " INTEGER ,"
            +  AC_STATUS_CODE + " INTEGER ,"
            +  WASHING_MACHINE_STATUS_CODE + " INTEGER ,"
            +  SMARTPHONE_STATUS_CODE + " INTEGER ,"
            +  REGISTERED_MOTOR_CODE + " INTEGER ,"
            +  LAND_EXCLUDING_HOME_STATUS_CODE + " INTEGER ,"
            +  TOTAL_UNIRRIGATEDLAND_IN_ACRES + " NUMERIC ,"
            +  WITH_ASSURED_IRRIGATION_FOR_TWO_CROPS_IN_ACRES + " NUMERIC ,"
            +  OTHER_IRRIGATED_LAND_IN_ACRES + " NUMERIC ,"
            +  MECHANIZED_MACHINE_STATUS_CODE + " INTEGER ,"
            +  IRRIGATION_EQUIPMENT_STATUS_CODE + " INTEGER ,"
            +  KISAN_CREDIT_CARD_GRT_50000_STATUS_CODE + " INTEGER ,"
            +  NFSA_FREE_MEAL_NUT_SUPPORT_CODE + " INTEGER ,"
            +  NFSA_RS_6000_NUT_SUPPORT_CODE + " INTEGER ,"
            +  NFSA_AAGANWADI_NUT_SUPPORT_CODE + " INTEGER ,"
            +  NFSA_MID_DAY_MEAL_CODE + " INTEGER ,"
            +  NFSA_FOOD_GAIN_UNDER_PDSA_CODE + " INTEGER ,"
            +  SHELTERLESS_CODE + " INTEGER ,"
            +  PRIMITIVE_TRIBAL_GROUP_CODE + " INTEGER ,"
            +  LEGALLY_BONDED_LABOUR_CODE + " INTEGER ,"
            +  MANUAL_SCAVENGER_CODE + " INTEGER ,"
            +  SEPARATE_ROOM_AS_KITCHEN_CODE + " INTEGER ,"
            +  COMPUTER_LAPTOP_AVAIL_CODE + " INTEGER ,"
            +  DT_CREATED + " TEXT ,"
            +  USER_ID + " TEXT ,"
            +  DEVICE_ID + " TEXT ,"
            +  APP_ID + " TEXT ,"
            +  APP_VERSION + " TEXT ,"
            +  IS_COMPLETED + " INTEGER DEFAULT 0, "
            +  IS_SYNCHRONIZED + " INTEGER DEFAULT 0 ,"
            +  IS_UPLOADED + " INTEGER DEFAULT 0 ,"
            +  DT_SYNC + " TEXT ,"
            +  DT_UPDATED + " TEXT, "
            +  IS_NEW_HHD + " INTEGER DEFAULT 0,"
            +  ENUMERATED_BY + " TEXT ,"
            + " CONSTRAINT household_pkey PRIMARY KEY (hhd_sl_no) )";

    public static boolean insertHhd(Context ctx, SQLiteDatabase db, HouseholdUpdated h) {
        ContentValues values = new ContentValues();
        ArrayList<String> al_columnNames = SQLiteHelper.checkTableColumns(ctx, db,TABLE_NAME);
        Log.d("", "insertHhd:  h.getHh_head_name() "+ h.getHh_head_name() );

        long row = -1;
        try {
            db.beginTransaction();
            values.put(STATE_CODE, h.getState_code());
            values.put(DISTRICT_CODE, h.getDistrict_code());
            values.put(SUB_DISTRICT_CODE, h.getSub_district_code());
            values.put(BLOCK_CODE, h.getBlock_code());
            values.put(GP_CODE, h.getGp_code());
            values.put(VILLAGE_CODE, h.getVillage_code());
            values.put(SHORT_USER_ID, h.getShort_user_id());
            values.put(HHD_SL_NO, h.getHhd_sl_no());
            values.put(DISTRICT_CENSUS_CODE, h.getDistrict_census_code());
            values.put(TOWN_CENSUS_CODE, h.getTown_census_code());
            values.put(HHD_UID,  h.getHhd_uid()!=null? h.getHhd_uid().trim() : h.getHhd_uid());
            values.put(HHD_LATITUDE, h.getHhd_latitude());
            values.put(HHD_LONGITUDE, h.getHhd_longitude());
            values.put(HH_HEAD_SL_NO, h.getHh_head_sl_no()!=null? h.getHh_head_sl_no().trim():h.getHh_head_sl_no());
            String getHh_head_name = h.getHh_head_name();
            Log.d("", "insertHhd:  Hh_head_name "+ getHh_head_name);
            values.put(HH_HEAD_NAME,getHh_head_name);

          //  values.put(HH_HEAD_NAME, h.getHh_head_name()!=null? h.getHh_head_name().trim():h.getHh_head_name());
            values.put(HOUSEHOLD_TYPE_CODE, h.getHousehold_type_code());
            values.put(ADDRESS_LINE_1, h.getAddressline1());
            values.put(ADDRESS_LINE_2, h.getAddressline2());
            values.put(ADDRESS_LINE_3, h.getAddressline3());
            values.put(ADDRESS_LINE_4, h.getAddressline4());
            values.put(ADDRESS_LINE_5, h.getAddressline5());
            values.put(PINCODE, h.getPincode());
            values.put(PRED_MAT_OF_WALL_DWELLING_ROOM_CODE, h.getPred_mat_of_wall_dwelling_room_code());
            values.put(PRED_MAT_OF_ROOF_DWELLING_ROOM_CODE, h.getPred_mat_of_roof_dwelling_room_code());
            values.put(OWNERSHIP_STATUS_CODE, h.getOwnership_status_code());
            values.put(HH_NO_OF_DWELLING_ROOMS_EXC_IN_POSS, h.getHh_no_of_dwelling_rooms_exc_in_poss());
            values.put(PIPED_DRINKING_WATER_CODE, h.getPiped_drinking_water_code());
            values.put(HOUSE_ROAD_CONNECTIVITY_CODE, h.getHouse_road_connectivity_code());
            values.put(OUTSIDE_ROAD_WITH_STREET_LIGHT_CODE, h.getOutside_road_with_street_light_code());
            values.put(DRAINAGE_FACILITY_CODE, h.getDrainage_facility_code());
            values.put(LATRINE_FACILITY_CODE, h.getLatrine_facility_code());
            values.put(LIGHTING_SOURCE_CODE, h.getLighting_source_code());
            values.put(COOKING_FUEL_CODE, h.getCooking_fuel_code());
            values.put(HOUSE_INCOME_SOURCE_CODE, h.getHousehold_income_source_code());
            values.put(PO_ACCOUNT_CODE, h.getPo_account_code());
            values.put(RATION_CARD_STATUS_CODE, h.getRation_card_status_code());
            values.put(RATION_CARD_NO, String.valueOf(h.getRation_card_no()));
            values.put(PHC_SUB_CENTER_CODE, h.getPhc_sub_center_code());
            values.put(CHILD_FOR_ICDS_CODE, h.getChild_for_icds_code());
            values.put(AGRICULTURE_LOAN_STATUS_CODE, h.getAgriculture_loan_status_code());
            values.put(HH_LIABILITY_RANGE_CODE, h.getHh_liability_range_code());
            values.put(AGRICULTURE_LOAN_WAIVED_CODE, h.getAgriculture_loan_waived_code());
            values.put(TELEPHONE_TYPE_CODE, h.getTelephone_type_code());
            values.put(HH_CONTACT_NO, h.getHh_contact_no());
            values.put(REFRIGERATOR_STATUS_CODE, h.getRefrigerator_status_code());
            values.put(AC_STATUS_CODE, h.getAc_status_code());
            values.put(WASHING_MACHINE_STATUS_CODE, h.getWashing_machine_status_code());
            values.put(SMARTPHONE_STATUS_CODE, h.getSmartphone_status_code());
            values.put(REGISTERED_MOTOR_CODE, h.getRegistered_motor_code());
            values.put(LAND_EXCLUDING_HOME_STATUS_CODE, h.getLand_excluding_home_status_code());
            values.put(TOTAL_UNIRRIGATEDLAND_IN_ACRES, h.getTotal_unirrigatedland_in_acres());
            values.put(WITH_ASSURED_IRRIGATION_FOR_TWO_CROPS_IN_ACRES, h.getWith_assured_irrigation_for_two_crops_in_acres());
            values.put(OTHER_IRRIGATED_LAND_IN_ACRES, h.getOther_irrigated_land_in_acres());
            values.put(MECHANIZED_MACHINE_STATUS_CODE, h.getMechanized_machine_status_code());
            values.put(IRRIGATION_EQUIPMENT_STATUS_CODE, h.getIrrigation_equipment_status_code());
            values.put(KISAN_CREDIT_CARD_GRT_50000_STATUS_CODE, h.getKisan_credit_card_grt_50000_status_code());
            values.put(NFSA_FREE_MEAL_NUT_SUPPORT_CODE, h.getNfsa_free_meal_nut_support_code());
            values.put(NFSA_RS_6000_NUT_SUPPORT_CODE, h.getNfsa_rs_6000_nut_support_code());
            values.put(NFSA_AAGANWADI_NUT_SUPPORT_CODE, h.getNfsa_aaganwadi_nut_support_code());
            values.put(NFSA_MID_DAY_MEAL_CODE, h.getNfsa_mid_day_meal_code());
            values.put(NFSA_FOOD_GAIN_UNDER_PDSA_CODE, h.getNfsa_food_gain_under_pdsa_code());
            values.put(SHELTERLESS_CODE, h.getShelterless_code());
            values.put(PRIMITIVE_TRIBAL_GROUP_CODE, h.getPrimitive_tribal_group_code());
            values.put(LEGALLY_BONDED_LABOUR_CODE, h.getLegally_bonded_labour_code());
            values.put(MANUAL_SCAVENGER_CODE, h.getManual_scavenger_code());
            values.put(SEPARATE_ROOM_AS_KITCHEN_CODE, h.getSeparate_room_as_kitchen_code());
            values.put(COMPUTER_LAPTOP_AVAIL_CODE, h.getComputer_laptop_avail_code());
            values.put(DT_CREATED, h.getDt_created());
            values.put(USER_ID, h.getUser_id());
            values.put(DEVICE_ID, h.getDevice_id());
            values.put(APP_ID, h.getApp_id());
            values.put(APP_VERSION, h.getApp_version());
            values.put(IS_COMPLETED, h.getIs_completed());
            values.put(IS_SYNCHRONIZED, h.getIs_synchronized());
            values.put(IS_UPLOADED, h.getIs_uploaded());
            values.put(DT_SYNC, h.getDt_sync());
            values.put(DT_UPDATED, h.getDt_updated());
            values.put(IS_NEW_HHD, h.getIs_new_hhd());
            values.put(ENUMERATED_BY, h.getEnumerated_by());
            row = db.insert(TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        }catch (Exception e)
        {
            Log.d("", "insertHhd: Exception "+e);
            Toast.makeText(ctx,"Exception"+e, Toast.LENGTH_LONG).show();
            row = -1;
        }
        finally
        {
            db.endTransaction();
        }
        return row >= 0;

    }

    public static boolean updateHhd(Context ctx, SQLiteDatabase db, HouseholdUpdated h) {
        long row = 0;
        try {

            Log.d("", "updateHhd:  h.getHh_head_name() "+ h.getHh_head_name() );
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(STATE_CODE, h.getState_code());
            values.put(DISTRICT_CODE, h.getDistrict_code());
            values.put(SUB_DISTRICT_CODE, h.getSub_district_code());
            values.put(BLOCK_CODE, h.getBlock_code());
            values.put(GP_CODE, h.getGp_code());
            values.put(VILLAGE_CODE, h.getVillage_code());
            values.put(SHORT_USER_ID, h.getShort_user_id());
            values.put(HHD_SL_NO, h.getHhd_sl_no());
            values.put(DISTRICT_CENSUS_CODE, h.getDistrict_census_code());
            values.put(TOWN_CENSUS_CODE, h.getTown_census_code());
            values.put(HHD_UID,  h.getHhd_uid()!= null? h.getHhd_uid().trim():h.getHhd_uid());
            values.put(HHD_LATITUDE, h.getHhd_latitude());
            values.put(HHD_LONGITUDE, h.getHhd_longitude());
            values.put(HH_HEAD_SL_NO, h.getHh_head_sl_no()!= null? h.getHh_head_sl_no().trim() : h.getHh_head_sl_no());
            values.put(HH_HEAD_NAME, h.getHh_head_name() != null? h.getHh_head_name().trim():h.getHh_head_name());
            values.put(HOUSEHOLD_TYPE_CODE, h.getHousehold_type_code());
            values.put(ADDRESS_LINE_1, h.getAddressline1());
            values.put(ADDRESS_LINE_2, h.getAddressline2());
            values.put(ADDRESS_LINE_3, h.getAddressline3());
            values.put(ADDRESS_LINE_4, h.getAddressline4());
            values.put(ADDRESS_LINE_5, h.getAddressline5());
            values.put(PINCODE, h.getPincode());
            values.put(PRED_MAT_OF_WALL_DWELLING_ROOM_CODE, h.getPred_mat_of_wall_dwelling_room_code());
            values.put(PRED_MAT_OF_ROOF_DWELLING_ROOM_CODE, h.getPred_mat_of_roof_dwelling_room_code());
            values.put(OWNERSHIP_STATUS_CODE, h.getOwnership_status_code());
            values.put(HH_NO_OF_DWELLING_ROOMS_EXC_IN_POSS, h.getHh_no_of_dwelling_rooms_exc_in_poss());
            values.put(PIPED_DRINKING_WATER_CODE, h.getPiped_drinking_water_code());
            values.put(HOUSE_ROAD_CONNECTIVITY_CODE, h.getHouse_road_connectivity_code());
            values.put(OUTSIDE_ROAD_WITH_STREET_LIGHT_CODE, h.getOutside_road_with_street_light_code());
            values.put(DRAINAGE_FACILITY_CODE, h.getDrainage_facility_code());
            values.put(LATRINE_FACILITY_CODE, h.getLatrine_facility_code());
            values.put(LIGHTING_SOURCE_CODE, h.getLighting_source_code());
            values.put(COOKING_FUEL_CODE, h.getCooking_fuel_code());
            values.put(HOUSE_INCOME_SOURCE_CODE, h.getHousehold_income_source_code());
            values.put(PO_ACCOUNT_CODE, h.getPo_account_code());
            values.put(RATION_CARD_STATUS_CODE, h.getRation_card_status_code());
            values.put(RATION_CARD_NO, String.valueOf(h.getRation_card_no()));
            values.put(PHC_SUB_CENTER_CODE, h.getPhc_sub_center_code());
            values.put(CHILD_FOR_ICDS_CODE, h.getChild_for_icds_code());
            values.put(AGRICULTURE_LOAN_STATUS_CODE, h.getAgriculture_loan_status_code());
            values.put(HH_LIABILITY_RANGE_CODE, h.getHh_liability_range_code());
            values.put(AGRICULTURE_LOAN_WAIVED_CODE, h.getAgriculture_loan_waived_code());
            values.put(TELEPHONE_TYPE_CODE, h.getTelephone_type_code());
            values.put(HH_CONTACT_NO, h.getHh_contact_no());
            values.put(REFRIGERATOR_STATUS_CODE, h.getRefrigerator_status_code());
            values.put(AC_STATUS_CODE, h.getAc_status_code());
            values.put(WASHING_MACHINE_STATUS_CODE, h.getWashing_machine_status_code());
            values.put(SMARTPHONE_STATUS_CODE, h.getSmartphone_status_code());
            values.put(REGISTERED_MOTOR_CODE, h.getRegistered_motor_code());
            values.put(LAND_EXCLUDING_HOME_STATUS_CODE, h.getLand_excluding_home_status_code());
            values.put(TOTAL_UNIRRIGATEDLAND_IN_ACRES, h.getTotal_unirrigatedland_in_acres());
            values.put(WITH_ASSURED_IRRIGATION_FOR_TWO_CROPS_IN_ACRES, h.getWith_assured_irrigation_for_two_crops_in_acres());
            values.put(OTHER_IRRIGATED_LAND_IN_ACRES, h.getOther_irrigated_land_in_acres());
            values.put(MECHANIZED_MACHINE_STATUS_CODE, h.getMechanized_machine_status_code());
            values.put(IRRIGATION_EQUIPMENT_STATUS_CODE, h.getIrrigation_equipment_status_code());
            values.put(KISAN_CREDIT_CARD_GRT_50000_STATUS_CODE, h.getKisan_credit_card_grt_50000_status_code());
            values.put(NFSA_FREE_MEAL_NUT_SUPPORT_CODE, h.getNfsa_free_meal_nut_support_code());
            values.put(NFSA_RS_6000_NUT_SUPPORT_CODE, h.getNfsa_rs_6000_nut_support_code());
            values.put(NFSA_AAGANWADI_NUT_SUPPORT_CODE, h.getNfsa_aaganwadi_nut_support_code());
            values.put(NFSA_MID_DAY_MEAL_CODE, h.getNfsa_mid_day_meal_code());
            values.put(NFSA_FOOD_GAIN_UNDER_PDSA_CODE, h.getNfsa_food_gain_under_pdsa_code());
            values.put(SHELTERLESS_CODE, h.getShelterless_code());
            values.put(PRIMITIVE_TRIBAL_GROUP_CODE, h.getPrimitive_tribal_group_code());
            values.put(LEGALLY_BONDED_LABOUR_CODE, h.getLegally_bonded_labour_code());
            values.put(MANUAL_SCAVENGER_CODE, h.getManual_scavenger_code());
            values.put(SEPARATE_ROOM_AS_KITCHEN_CODE, h.getSeparate_room_as_kitchen_code());
            values.put(COMPUTER_LAPTOP_AVAIL_CODE, h.getComputer_laptop_avail_code());
            values.put(DT_CREATED, h.getDt_created());
            values.put(USER_ID, h.getUser_id());
            values.put(DEVICE_ID, h.getDevice_id());
            values.put(APP_ID, h.getApp_id());
            values.put(APP_VERSION, h.getApp_version());
            values.put(IS_COMPLETED, h.getIs_completed());
            values.put(IS_SYNCHRONIZED, h.getIs_synchronized());
            values.put(IS_UPLOADED, h.getIs_uploaded());
            values.put(DT_SYNC, h.getDt_sync());
            values.put(DT_UPDATED, h.getDt_updated());
            values.put(IS_NEW_HHD, h.getIs_new_hhd());
            values.put(ENUMERATED_BY, h.getEnumerated_by());


            row = db.update(TABLE_NAME, values,  HHD_SL_NO + " =?",
                    new String[]{String.valueOf(h.getHhd_sl_no())});
            db.setTransactionSuccessful();
        }catch (Exception e)
        {
            row = 0;
            Log.d("", "updateHhd: Exception "+e);

        }
        finally
        {
            db.endTransaction();
        }
        return row >= 0;
    }

    public static Integer getNewHhdUid(Context context, SQLiteDatabase db, String device_id) {
        Integer huid=null;
        try {
            //String query = "select  substr(a.hhd_uid, 1, 12) || substr('00000'||cast(max(substr(a.hhd_uid, 13, 6)) + 1 as varchar),-6) as hhd_uid from(SELECT hhd_uid FROM "+TABLE_NAME+" group by hhd_uid union SELECT hhd_uid FROM population_updated group by hhd_uid) a  order by  hhd_uid";
            String query = "select  (max(a.hhd_sl_no) + 1) as " + HHD_SL_NO +
                    " from " +
                    " (SELECT "+ HHD_SL_NO + " FROM "+TABLE_NAME+" group by HHD_SL_NO " +
                    " union SELECT "+ HHD_SL_NO +
                    " FROM population_updated group by "+HHD_SL_NO +
                    " union SELECT " +HHD_SL_NO +
                    " FROM household_enumeration_info group by "+HHD_SL_NO + ") a  " +
                    " order by " + HHD_SL_NO;
            Cursor cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()) {
                huid = cursor.getInt(cursor.getColumnIndex(HHD_SL_NO));
            }
            if((huid == null) || (huid == 0))
                huid=1;
        }catch (Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.error), e.getMessage(), "031-001");
        }
        return huid;
    }

    public static ArrayList<HouseholdUpdated> getDataByHhd(Context ctx, SQLiteDatabase db, int iHhdSlNo) {
        ArrayList<HouseholdUpdated> list = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + HHD_SL_NO + " = ?", new String[]{String.valueOf(iHhdSlNo)});
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    HouseholdUpdated h = new HouseholdUpdated();
                    h.setState_code(cursor.getInt(cursor.getColumnIndex(STATE_CODE)));
                    h.setDistrict_code(cursor.getInt(cursor.getColumnIndex(DISTRICT_CODE)));
                    h.setSub_district_code(cursor.getInt(cursor.getColumnIndex(SUB_DISTRICT_CODE)));
                    h.setBlock_code(cursor.getInt(cursor.getColumnIndex(BLOCK_CODE)));
                    h.setGp_code(cursor.getInt(cursor.getColumnIndex(GP_CODE)));
                    h.setVillage_code(cursor.getInt(cursor.getColumnIndex(VILLAGE_CODE)));
                    h.setShort_user_id(cursor.getString(cursor.getColumnIndex(SHORT_USER_ID)));
                    h.setHhd_sl_no(cursor.getInt(cursor.getColumnIndex(HHD_SL_NO)));
                    h.setDistrict_census_code(cursor.getString(cursor.getColumnIndex(DISTRICT_CENSUS_CODE)));
                    h.setTown_census_code(cursor.getString(cursor.getColumnIndex(TOWN_CENSUS_CODE)));
                    h.setHhd_uid(cursor.getString(cursor.getColumnIndex(HHD_UID)));
                    h.setHhd_latitude(cursor.getString(cursor.getColumnIndex(HHD_LATITUDE)));
                    h.setHhd_longitude(cursor.getString(cursor.getColumnIndex(HHD_LONGITUDE)));
                    h.setHh_head_sl_no(cursor.getString(cursor.getColumnIndex(HH_HEAD_SL_NO)));
                    h.setHh_head_name(cursor.getString(cursor.getColumnIndex(HH_HEAD_NAME)));
                    h.setHousehold_type_code(cursor.getInt(cursor.getColumnIndex(HOUSEHOLD_TYPE_CODE)));
                    h.setAddressline1( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_1)));
                    h.setAddressline2( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_2)));
                    h.setAddressline3( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_3)));
                    h.setAddressline4( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_4)));
                    h.setAddressline5( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_5)));
                    h.setPincode( cursor.getString(cursor.getColumnIndex(PINCODE)));
                    h.setPred_mat_of_wall_dwelling_room_code(cursor.getInt(cursor.getColumnIndex(PRED_MAT_OF_WALL_DWELLING_ROOM_CODE)));
                    h.setPred_mat_of_roof_dwelling_room_code(cursor.getInt(cursor.getColumnIndex(PRED_MAT_OF_ROOF_DWELLING_ROOM_CODE)));
                    h.setOwnership_status_code(cursor.getInt(cursor.getColumnIndex(OWNERSHIP_STATUS_CODE)));
                    h.setHh_no_of_dwelling_rooms_exc_in_poss(cursor.getInt(cursor.getColumnIndex(HH_NO_OF_DWELLING_ROOMS_EXC_IN_POSS)));
                    h.setPiped_drinking_water_code(cursor.getInt(cursor.getColumnIndex(PIPED_DRINKING_WATER_CODE)));
                    h.setHouse_road_connectivity_code(cursor.getInt(cursor.getColumnIndex(HOUSE_ROAD_CONNECTIVITY_CODE)));
                    h.setOutside_road_with_street_light_code(cursor.getInt(cursor.getColumnIndex(OUTSIDE_ROAD_WITH_STREET_LIGHT_CODE)));
                    h.setDrainage_facility_code(cursor.getInt(cursor.getColumnIndex(DRAINAGE_FACILITY_CODE)));
                    h.setLatrine_facility_code(cursor.getInt(cursor.getColumnIndex(LATRINE_FACILITY_CODE)));
                    h.setLighting_source_code(cursor.getInt(cursor.getColumnIndex(LIGHTING_SOURCE_CODE)));
                    h.setCooking_fuel_code(cursor.getInt(cursor.getColumnIndex(COOKING_FUEL_CODE)));
                    h.setHousehold_income_source_code(cursor.getInt(cursor.getColumnIndex(HOUSE_INCOME_SOURCE_CODE)));
                    h.setPo_account_code(cursor.getInt(cursor.getColumnIndex(PO_ACCOUNT_CODE)));
                    h.setRation_card_status_code(cursor.getInt(cursor.getColumnIndex(RATION_CARD_STATUS_CODE)));
                    h.setRation_card_no(cursor.getString(cursor.getColumnIndex(RATION_CARD_NO)));
                    h.setPhc_sub_center_code(cursor.getInt(cursor.getColumnIndex(PHC_SUB_CENTER_CODE)));
                    h.setChild_for_icds_code(cursor.getInt(cursor.getColumnIndex(CHILD_FOR_ICDS_CODE)));
                    h.setAgriculture_loan_status_code(cursor.getInt(cursor.getColumnIndex(AGRICULTURE_LOAN_STATUS_CODE)));
                    h.setHh_liability_range_code(cursor.getInt(cursor.getColumnIndex(HH_LIABILITY_RANGE_CODE)));
                    h.setAgriculture_loan_waived_code(cursor.getInt(cursor.getColumnIndex(AGRICULTURE_LOAN_WAIVED_CODE)));
                    h.setTelephone_type_code(cursor.getInt(cursor.getColumnIndex(TELEPHONE_TYPE_CODE)));
                    h.setHh_contact_no(cursor.getString(cursor.getColumnIndex(HH_CONTACT_NO)));
                    h.setRefrigerator_status_code(cursor.getInt(cursor.getColumnIndex(REFRIGERATOR_STATUS_CODE)));
                    h.setAc_status_code(cursor.getInt(cursor.getColumnIndex(AC_STATUS_CODE)));
                    h.setWashing_machine_status_code(cursor.getInt(cursor.getColumnIndex(WASHING_MACHINE_STATUS_CODE)));
                    h.setSmartphone_status_code(cursor.getInt(cursor.getColumnIndex(SMARTPHONE_STATUS_CODE)));
                    h.setRegistered_motor_code(cursor.getInt(cursor.getColumnIndex(REGISTERED_MOTOR_CODE)));
                    h.setLand_excluding_home_status_code(cursor.getInt(cursor.getColumnIndex(LAND_EXCLUDING_HOME_STATUS_CODE)));
                    h.setTotal_unirrigatedland_in_acres(cursor.getDouble(cursor.getColumnIndex(TOTAL_UNIRRIGATEDLAND_IN_ACRES)));
                    h.setWith_assured_irrigation_for_two_crops_in_acres(cursor.getDouble(cursor.getColumnIndex(WITH_ASSURED_IRRIGATION_FOR_TWO_CROPS_IN_ACRES)));
                    h.setOther_irrigated_land_in_acres(cursor.getDouble(cursor.getColumnIndex(OTHER_IRRIGATED_LAND_IN_ACRES)));
                    h.setMechanized_machine_status_code(cursor.getInt(cursor.getColumnIndex(MECHANIZED_MACHINE_STATUS_CODE)));
                    h.setIrrigation_equipment_status_code(cursor.getInt(cursor.getColumnIndex(IRRIGATION_EQUIPMENT_STATUS_CODE)));
                    h.setKisan_credit_card_grt_50000_status_code(cursor.getInt(cursor.getColumnIndex(KISAN_CREDIT_CARD_GRT_50000_STATUS_CODE)));
                    h.setNfsa_free_meal_nut_support_code(cursor.getInt(cursor.getColumnIndex(NFSA_FREE_MEAL_NUT_SUPPORT_CODE)));
                    h.setNfsa_rs_6000_nut_support_code(cursor.getInt(cursor.getColumnIndex(NFSA_RS_6000_NUT_SUPPORT_CODE)));
                    h.setNfsa_aaganwadi_nut_support_code(cursor.getInt(cursor.getColumnIndex(NFSA_AAGANWADI_NUT_SUPPORT_CODE)));
                    h.setNfsa_mid_day_meal_code(cursor.getInt(cursor.getColumnIndex(NFSA_MID_DAY_MEAL_CODE)));
                    h.setNfsa_food_gain_under_pdsa_code(cursor.getInt(cursor.getColumnIndex(NFSA_FOOD_GAIN_UNDER_PDSA_CODE)));
                    h.setShelterless_code(cursor.getInt(cursor.getColumnIndex(SHELTERLESS_CODE)));
                    h.setPrimitive_tribal_group_code(cursor.getInt(cursor.getColumnIndex(PRIMITIVE_TRIBAL_GROUP_CODE)));
                    h.setLegally_bonded_labour_code(cursor.getInt(cursor.getColumnIndex(LEGALLY_BONDED_LABOUR_CODE)));
                    h.setManual_scavenger_code(cursor.getInt(cursor.getColumnIndex(MANUAL_SCAVENGER_CODE)));
                    h.setSeparate_room_as_kitchen_code(cursor.getInt(cursor.getColumnIndex(SEPARATE_ROOM_AS_KITCHEN_CODE)));
                    h.setComputer_laptop_avail_code(cursor.getInt(cursor.getColumnIndex(COMPUTER_LAPTOP_AVAIL_CODE)));
                    h.setDt_created(cursor.getString(cursor.getColumnIndex(DT_CREATED)));
                    h.setUser_id(cursor.getString(cursor.getColumnIndex(USER_ID)));
                    h.setDevice_id(cursor.getString(cursor.getColumnIndex(DEVICE_ID)));
                    h.setApp_id(cursor.getString(cursor.getColumnIndex(APP_ID)));
                    h.setApp_version(cursor.getString(cursor.getColumnIndex(APP_VERSION)));
                    h.setIs_completed(cursor.getInt(cursor.getColumnIndex(IS_COMPLETED)));
                    h.setIs_synchronized(cursor.getInt(cursor.getColumnIndex(IS_SYNCHRONIZED)));
                    h.setIs_uploaded(cursor.getInt(cursor.getColumnIndex(IS_UPLOADED)));
                    h.setDt_sync(cursor.getString(cursor.getColumnIndex(DT_SYNC)));
                    h.setDt_updated(cursor.getString(cursor.getColumnIndex(DT_UPDATED)));
                    h.setIs_new_hhd(cursor.getInt(cursor.getColumnIndex(IS_NEW_HHD))==1?true:false);
                    h.setEnumerated_by(cursor.getString(cursor.getColumnIndex(ENUMERATED_BY)));

                    list.add(h);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }catch(Exception e)
        {
            //MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.add_household_info), context.getString(R.string.house_completed));
        }
        return list;
    }

    public static ArrayList<HouseholdUpdated> getData(Context ctx, SQLiteDatabase db) {
        ArrayList<HouseholdUpdated> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                HouseholdUpdated h = new HouseholdUpdated();
                h.setState_code(cursor.getInt(cursor.getColumnIndex(STATE_CODE)));
                h.setDistrict_code(cursor.getInt(cursor.getColumnIndex(DISTRICT_CODE)));
                h.setSub_district_code(cursor.getInt(cursor.getColumnIndex(SUB_DISTRICT_CODE)));
                h.setBlock_code(cursor.getInt(cursor.getColumnIndex(BLOCK_CODE)));
                h.setGp_code(cursor.getInt(cursor.getColumnIndex(GP_CODE)));
                h.setVillage_code(cursor.getInt(cursor.getColumnIndex(VILLAGE_CODE)));
                h.setShort_user_id(cursor.getString(cursor.getColumnIndex(SHORT_USER_ID)));
                h.setHhd_sl_no(cursor.getInt(cursor.getColumnIndex(HHD_SL_NO)));
                h.setDistrict_census_code(cursor.getString(cursor.getColumnIndex(DISTRICT_CENSUS_CODE)));
                h.setTown_census_code(cursor.getString(cursor.getColumnIndex(TOWN_CENSUS_CODE)));
                h.setHhd_uid(cursor.getString(cursor.getColumnIndex(HHD_UID)));
                h.setHhd_latitude(cursor.getString(cursor.getColumnIndex(HHD_LATITUDE)));
                h.setHhd_longitude(cursor.getString(cursor.getColumnIndex(HHD_LONGITUDE)));
                h.setHh_head_sl_no(cursor.getString(cursor.getColumnIndex(HH_HEAD_SL_NO)));
                h.setHh_head_name(cursor.getString(cursor.getColumnIndex(HH_HEAD_NAME)));
                h.setHousehold_type_code(cursor.getInt(cursor.getColumnIndex(HOUSEHOLD_TYPE_CODE)));
                h.setAddressline1( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_1)));
                h.setAddressline2( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_2)));
                h.setAddressline3( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_3)));
                h.setAddressline4( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_4)));
                h.setAddressline5( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_5)));
                h.setPincode( cursor.getString(cursor.getColumnIndex(PINCODE)));
                h.setPred_mat_of_wall_dwelling_room_code(cursor.getInt(cursor.getColumnIndex(PRED_MAT_OF_WALL_DWELLING_ROOM_CODE)));
                h.setPred_mat_of_roof_dwelling_room_code(cursor.getInt(cursor.getColumnIndex(PRED_MAT_OF_ROOF_DWELLING_ROOM_CODE)));
                h.setOwnership_status_code(cursor.getInt(cursor.getColumnIndex(OWNERSHIP_STATUS_CODE)));
                h.setHh_no_of_dwelling_rooms_exc_in_poss(cursor.getInt(cursor.getColumnIndex(HH_NO_OF_DWELLING_ROOMS_EXC_IN_POSS)));
                h.setPiped_drinking_water_code(cursor.getInt(cursor.getColumnIndex(PIPED_DRINKING_WATER_CODE)));
                h.setHouse_road_connectivity_code(cursor.getInt(cursor.getColumnIndex(HOUSE_ROAD_CONNECTIVITY_CODE)));
                h.setOutside_road_with_street_light_code(cursor.getInt(cursor.getColumnIndex(OUTSIDE_ROAD_WITH_STREET_LIGHT_CODE)));
                h.setDrainage_facility_code(cursor.getInt(cursor.getColumnIndex(DRAINAGE_FACILITY_CODE)));
                h.setLatrine_facility_code(cursor.getInt(cursor.getColumnIndex(LATRINE_FACILITY_CODE)));
                h.setLighting_source_code(cursor.getInt(cursor.getColumnIndex(LIGHTING_SOURCE_CODE)));
                h.setCooking_fuel_code(cursor.getInt(cursor.getColumnIndex(COOKING_FUEL_CODE)));
                h.setHousehold_income_source_code(cursor.getInt(cursor.getColumnIndex(HOUSE_INCOME_SOURCE_CODE)));
                h.setPo_account_code(cursor.getInt(cursor.getColumnIndex(PO_ACCOUNT_CODE)));
                h.setRation_card_status_code(cursor.getInt(cursor.getColumnIndex(RATION_CARD_STATUS_CODE)));
                h.setRation_card_no(cursor.getString(cursor.getColumnIndex(RATION_CARD_NO)));
                h.setPhc_sub_center_code(cursor.getInt(cursor.getColumnIndex(PHC_SUB_CENTER_CODE)));
                h.setChild_for_icds_code(cursor.getInt(cursor.getColumnIndex(CHILD_FOR_ICDS_CODE)));
                h.setAgriculture_loan_status_code(cursor.getInt(cursor.getColumnIndex(AGRICULTURE_LOAN_STATUS_CODE)));
                h.setHh_liability_range_code(cursor.getInt(cursor.getColumnIndex(HH_LIABILITY_RANGE_CODE)));
                h.setAgriculture_loan_waived_code(cursor.getInt(cursor.getColumnIndex(AGRICULTURE_LOAN_WAIVED_CODE)));
                h.setTelephone_type_code(cursor.getInt(cursor.getColumnIndex(TELEPHONE_TYPE_CODE)));
                h.setHh_contact_no(cursor.getString(cursor.getColumnIndex(HH_CONTACT_NO)));
                h.setRefrigerator_status_code(cursor.getInt(cursor.getColumnIndex(REFRIGERATOR_STATUS_CODE)));
                h.setAc_status_code(cursor.getInt(cursor.getColumnIndex(AC_STATUS_CODE)));
                h.setWashing_machine_status_code(cursor.getInt(cursor.getColumnIndex(WASHING_MACHINE_STATUS_CODE)));
                h.setSmartphone_status_code(cursor.getInt(cursor.getColumnIndex(SMARTPHONE_STATUS_CODE)));
                h.setRegistered_motor_code(cursor.getInt(cursor.getColumnIndex(REGISTERED_MOTOR_CODE)));
                h.setLand_excluding_home_status_code(cursor.getInt(cursor.getColumnIndex(LAND_EXCLUDING_HOME_STATUS_CODE)));
                h.setTotal_unirrigatedland_in_acres(cursor.getDouble(cursor.getColumnIndex(TOTAL_UNIRRIGATEDLAND_IN_ACRES)));
                h.setWith_assured_irrigation_for_two_crops_in_acres(cursor.getDouble(cursor.getColumnIndex(WITH_ASSURED_IRRIGATION_FOR_TWO_CROPS_IN_ACRES)));
                h.setOther_irrigated_land_in_acres(cursor.getDouble(cursor.getColumnIndex(OTHER_IRRIGATED_LAND_IN_ACRES)));
                h.setMechanized_machine_status_code(cursor.getInt(cursor.getColumnIndex(MECHANIZED_MACHINE_STATUS_CODE)));
                h.setIrrigation_equipment_status_code(cursor.getInt(cursor.getColumnIndex(IRRIGATION_EQUIPMENT_STATUS_CODE)));
                h.setKisan_credit_card_grt_50000_status_code(cursor.getInt(cursor.getColumnIndex(KISAN_CREDIT_CARD_GRT_50000_STATUS_CODE)));
                h.setNfsa_free_meal_nut_support_code(cursor.getInt(cursor.getColumnIndex(NFSA_FREE_MEAL_NUT_SUPPORT_CODE)));
                h.setNfsa_rs_6000_nut_support_code(cursor.getInt(cursor.getColumnIndex(NFSA_RS_6000_NUT_SUPPORT_CODE)));
                h.setNfsa_aaganwadi_nut_support_code(cursor.getInt(cursor.getColumnIndex(NFSA_AAGANWADI_NUT_SUPPORT_CODE)));
                h.setNfsa_mid_day_meal_code(cursor.getInt(cursor.getColumnIndex(NFSA_MID_DAY_MEAL_CODE)));
                h.setNfsa_food_gain_under_pdsa_code(cursor.getInt(cursor.getColumnIndex(NFSA_FOOD_GAIN_UNDER_PDSA_CODE)));
                h.setShelterless_code(cursor.getInt(cursor.getColumnIndex(SHELTERLESS_CODE)));
                h.setPrimitive_tribal_group_code(cursor.getInt(cursor.getColumnIndex(PRIMITIVE_TRIBAL_GROUP_CODE)));
                h.setLegally_bonded_labour_code(cursor.getInt(cursor.getColumnIndex(LEGALLY_BONDED_LABOUR_CODE)));
                h.setManual_scavenger_code(cursor.getInt(cursor.getColumnIndex(MANUAL_SCAVENGER_CODE)));
                h.setSeparate_room_as_kitchen_code(cursor.getInt(cursor.getColumnIndex(SEPARATE_ROOM_AS_KITCHEN_CODE)));
                h.setComputer_laptop_avail_code(cursor.getInt(cursor.getColumnIndex(COMPUTER_LAPTOP_AVAIL_CODE)));
                h.setDt_created(cursor.getString(cursor.getColumnIndex(DT_CREATED)));
                h.setUser_id(cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.setDevice_id(cursor.getString(cursor.getColumnIndex(DEVICE_ID)));
                h.setApp_id(cursor.getString(cursor.getColumnIndex(APP_ID)));
                h.setApp_version(cursor.getString(cursor.getColumnIndex(APP_VERSION)));
                h.setIs_completed(cursor.getInt(cursor.getColumnIndex(IS_COMPLETED)));
                h.setIs_synchronized(cursor.getInt(cursor.getColumnIndex(IS_SYNCHRONIZED)));
                h.setIs_uploaded(cursor.getInt(cursor.getColumnIndex(IS_UPLOADED)));
                h.setDt_sync(cursor.getString(cursor.getColumnIndex(DT_SYNC)));
                h.setDt_updated(cursor.getString(cursor.getColumnIndex(DT_UPDATED)));
                h.setIs_new_hhd(cursor.getInt(cursor.getColumnIndex(IS_NEW_HHD))==1?true:false);
                h.setEnumerated_by(cursor.getString(cursor.getColumnIndex(ENUMERATED_BY)));
                list.add(h);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public static ArrayList<HouseholdUpdated> getHouseholdStatusList(Context ctx, SQLiteDatabase db) {
        ArrayList<HouseholdUpdated> list = new ArrayList<>();
        String strQuery = "select a.state_code, a.district_code, a.sub_district_code, a.block_code, a.gp_code," +
                " a.village_code, a.hhd_uid," +
                " a.total_member, a.total_member_av, a.total_member_mo," +
                " a.total_member_de, a.total_member_nw, a.is_uncovered, a.json_file_name," +
                " a.dt_json_file_updated, a.dt_updated, a.enumerated_by," +
                " hhd_latitude, hhd_longitude, hh_head_sl_no," +
                " household_type_code, addressline1, addressline2, addressline3," +
                " addressline4, addressline5, pincode, pred_mat_of_wall_dwelling_room_code," +
                " pred_mat_of_roof_dwelling_room_code, ownership_status_code, hh_no_of_dwelling_rooms_exc_in_poss," +
                " piped_drinking_water_code, house_road_connectivity_code, outside_road_with_street_light_code," +
                " drainage_facility_code, latrine_facility_code, lighting_source_code," +
                " cooking_fuel_code, household_income_source_code, po_account_code," +
                " ration_card_status_code, ration_card_no, phc_sub_center_code," +
                " child_for_icds_code, unemployment_benefit_code, agriculture_loan_status_code," +
                "  hh_liability_range_code, agriculture_loan_waived_code, telephone_type_code," +
                " hh_contact_no, refrigerator_status_code, ac_status_code, washing_machine_status_code," +
                " smartphone_status_code, registered_motor_code, land_excluding_home_status_code," +
                " total_unirrigatedland_in_acres, with_assured_irrigation_for_two_crops_in_acres," +
                " other_irrigated_land_in_acres, mechanized_machine_status_code," +
                "  irrigation_equipment_status_code, kisan_credit_card_grt_50000_status_code," +
                " nfsa_free_meal_nut_support_code, nfsa_rs_6000_nut_support_code," +
                " nfsa_aaganwadi_nut_support_code, nfsa_mid_day_meal_code, nfsa_food_gain_under_pdsa_code," +
                " dt_created, user_id, a.device_id, a.app_id, a.app_version, is_completed, is_synchronized, dt_sync" +
                " from household_enumeration_info a" +
                " left join household_updated b" +
                " on a.hhd_sl_no = b.hhd_sl_no" +
                " union" +
                " select state_code, district_code, sub_district_code, block_code, gp_code," +
                " village_code, hhd_uid," +
                " 0 as total_member, 0 as total_member_av, 0 as total_member_mo," +
                " 0 as total_member_de, 0 as total_member_nw, 0 as is_uncovered, null as json_file_name," +
                " null as dt_json_file_updated, null as dt_updated, null as enumerated_by," +
                " hhd_latitude, hhd_longitude, hh_head_sl_no," +
                " household_type_code, addressline1, addressline2, addressline3," +
                " addressline4, addressline5, pincode, pred_mat_of_wall_dwelling_room_code," +
                " pred_mat_of_roof_dwelling_room_code, ownership_status_code, hh_no_of_dwelling_rooms_exc_in_poss," +
                " piped_drinking_water_code, house_road_connectivity_code, outside_road_with_street_light_code," +
                " drainage_facility_code, latrine_facility_code, lighting_source_code," +
                " cooking_fuel_code, household_income_source_code, po_account_code," +
                " ration_card_status_code, ration_card_no, phc_sub_center_code," +
                " child_for_icds_code, unemployment_benefit_code, agriculture_loan_status_code," +
                " hh_liability_range_code, agriculture_loan_waived_code, telephone_type_code," +
                " hh_contact_no, refrigerator_status_code, ac_status_code, washing_machine_status_code," +
                " smartphone_status_code, registered_motor_code, land_excluding_home_status_code," +
                " total_unirrigatedland_in_acres, with_assured_irrigation_for_two_crops_in_acres," +
                " other_irrigated_land_in_acres, mechanized_machine_status_code," +
                " irrigation_equipment_status_code, kisan_credit_card_grt_50000_status_code," +
                " nfsa_free_meal_nut_support_code, nfsa_rs_6000_nut_support_code," +
                " nfsa_aaganwadi_nut_support_code, nfsa_mid_day_meal_code, nfsa_food_gain_under_pdsa_code," +
                " dt_created, user_id, device_id, app_id, app_version, is_completed, is_synchronized, dt_sync" +
                " from household_updated where hhd_sl_no not in (select hhd_sl_no from household_enumeration_info)" +
                " order by hhd_sl_no asc" ;
        Cursor cursor = db.rawQuery(strQuery, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                HouseholdUpdated h = new HouseholdUpdated();
                h.setState_code(cursor.getInt(cursor.getColumnIndex(STATE_CODE)));
                h.setDistrict_code(cursor.getInt(cursor.getColumnIndex(DISTRICT_CODE)));
                h.setSub_district_code(cursor.getInt(cursor.getColumnIndex(SUB_DISTRICT_CODE)));
                h.setBlock_code(cursor.getInt(cursor.getColumnIndex(BLOCK_CODE)));
                h.setGp_code(cursor.getInt(cursor.getColumnIndex(GP_CODE)));
                h.setVillage_code(cursor.getInt(cursor.getColumnIndex(VILLAGE_CODE)));
                h.setShort_user_id(cursor.getString(cursor.getColumnIndex(SHORT_USER_ID)));
                h.setHhd_sl_no(cursor.getInt(cursor.getColumnIndex(HHD_SL_NO)));
                h.setDistrict_census_code(cursor.getString(cursor.getColumnIndex(DISTRICT_CENSUS_CODE)));
                h.setTown_census_code(cursor.getString(cursor.getColumnIndex(TOWN_CENSUS_CODE)));
                h.setHhd_uid(cursor.getString(cursor.getColumnIndex(HHD_UID)));
                h.setHhd_latitude(cursor.getString(cursor.getColumnIndex(HHD_LATITUDE)));
                h.setHhd_longitude(cursor.getString(cursor.getColumnIndex(HHD_LONGITUDE)));
                if(cursor.getString(cursor.getColumnIndex(HH_HEAD_SL_NO)) != null)
                    h.setHh_head_sl_no(cursor.getString(cursor.getColumnIndex(HH_HEAD_SL_NO)));
                h.setHh_head_name(cursor.getString(cursor.getColumnIndex(HH_HEAD_NAME)));
                h.setHousehold_type_code(cursor.getInt(cursor.getColumnIndex(HOUSEHOLD_TYPE_CODE)));
                h.setAddressline1( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_1)));
                h.setAddressline2( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_2)));
                h.setAddressline3( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_3)));
                h.setAddressline4( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_4)));
                h.setAddressline5( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_5)));
                h.setPincode( cursor.getString(cursor.getColumnIndex(PINCODE)));
                h.setPred_mat_of_wall_dwelling_room_code(cursor.getInt(cursor.getColumnIndex(PRED_MAT_OF_WALL_DWELLING_ROOM_CODE)));
                h.setPred_mat_of_roof_dwelling_room_code(cursor.getInt(cursor.getColumnIndex(PRED_MAT_OF_ROOF_DWELLING_ROOM_CODE)));
                h.setOwnership_status_code(cursor.getInt(cursor.getColumnIndex(OWNERSHIP_STATUS_CODE)));
                h.setHh_no_of_dwelling_rooms_exc_in_poss(cursor.getInt(cursor.getColumnIndex(HH_NO_OF_DWELLING_ROOMS_EXC_IN_POSS)));
                h.setPiped_drinking_water_code(cursor.getInt(cursor.getColumnIndex(PIPED_DRINKING_WATER_CODE)));
                h.setHouse_road_connectivity_code(cursor.getInt(cursor.getColumnIndex(HOUSE_ROAD_CONNECTIVITY_CODE)));
                h.setOutside_road_with_street_light_code(cursor.getInt(cursor.getColumnIndex(OUTSIDE_ROAD_WITH_STREET_LIGHT_CODE)));
                h.setDrainage_facility_code(cursor.getInt(cursor.getColumnIndex(DRAINAGE_FACILITY_CODE)));
                h.setLatrine_facility_code(cursor.getInt(cursor.getColumnIndex(LATRINE_FACILITY_CODE)));
                h.setLighting_source_code(cursor.getInt(cursor.getColumnIndex(LIGHTING_SOURCE_CODE)));
                h.setCooking_fuel_code(cursor.getInt(cursor.getColumnIndex(COOKING_FUEL_CODE)));
                h.setHousehold_income_source_code(cursor.getInt(cursor.getColumnIndex(HOUSE_INCOME_SOURCE_CODE)));
                h.setPo_account_code(cursor.getInt(cursor.getColumnIndex(PO_ACCOUNT_CODE)));
                h.setRation_card_status_code(cursor.getInt(cursor.getColumnIndex(RATION_CARD_STATUS_CODE)));
                h.setRation_card_no(cursor.getString(cursor.getColumnIndex(RATION_CARD_NO)));
                h.setPhc_sub_center_code(cursor.getInt(cursor.getColumnIndex(PHC_SUB_CENTER_CODE)));
                h.setChild_for_icds_code(cursor.getInt(cursor.getColumnIndex(CHILD_FOR_ICDS_CODE)));
                h.setAgriculture_loan_status_code(cursor.getInt(cursor.getColumnIndex(AGRICULTURE_LOAN_STATUS_CODE)));
                h.setHh_liability_range_code(cursor.getInt(cursor.getColumnIndex(HH_LIABILITY_RANGE_CODE)));
                h.setAgriculture_loan_waived_code(cursor.getInt(cursor.getColumnIndex(AGRICULTURE_LOAN_WAIVED_CODE)));
                h.setTelephone_type_code(cursor.getInt(cursor.getColumnIndex(TELEPHONE_TYPE_CODE)));
                h.setHh_contact_no(cursor.getString(cursor.getColumnIndex(HH_CONTACT_NO)));
                h.setRefrigerator_status_code(cursor.getInt(cursor.getColumnIndex(REFRIGERATOR_STATUS_CODE)));
                h.setAc_status_code(cursor.getInt(cursor.getColumnIndex(AC_STATUS_CODE)));
                h.setWashing_machine_status_code(cursor.getInt(cursor.getColumnIndex(WASHING_MACHINE_STATUS_CODE)));
                h.setSmartphone_status_code(cursor.getInt(cursor.getColumnIndex(SMARTPHONE_STATUS_CODE)));
                h.setRegistered_motor_code(cursor.getInt(cursor.getColumnIndex(REGISTERED_MOTOR_CODE)));
                h.setLand_excluding_home_status_code(cursor.getInt(cursor.getColumnIndex(LAND_EXCLUDING_HOME_STATUS_CODE)));
                h.setTotal_unirrigatedland_in_acres(cursor.getDouble(cursor.getColumnIndex(TOTAL_UNIRRIGATEDLAND_IN_ACRES)));
                h.setWith_assured_irrigation_for_two_crops_in_acres(cursor.getDouble(cursor.getColumnIndex(WITH_ASSURED_IRRIGATION_FOR_TWO_CROPS_IN_ACRES)));
                h.setOther_irrigated_land_in_acres(cursor.getDouble(cursor.getColumnIndex(OTHER_IRRIGATED_LAND_IN_ACRES)));
                h.setMechanized_machine_status_code(cursor.getInt(cursor.getColumnIndex(MECHANIZED_MACHINE_STATUS_CODE)));
                h.setIrrigation_equipment_status_code(cursor.getInt(cursor.getColumnIndex(IRRIGATION_EQUIPMENT_STATUS_CODE)));
                h.setKisan_credit_card_grt_50000_status_code(cursor.getInt(cursor.getColumnIndex(KISAN_CREDIT_CARD_GRT_50000_STATUS_CODE)));
                h.setNfsa_free_meal_nut_support_code(cursor.getInt(cursor.getColumnIndex(NFSA_FREE_MEAL_NUT_SUPPORT_CODE)));
                h.setNfsa_rs_6000_nut_support_code(cursor.getInt(cursor.getColumnIndex(NFSA_RS_6000_NUT_SUPPORT_CODE)));
                h.setNfsa_aaganwadi_nut_support_code(cursor.getInt(cursor.getColumnIndex(NFSA_AAGANWADI_NUT_SUPPORT_CODE)));
                h.setNfsa_mid_day_meal_code(cursor.getInt(cursor.getColumnIndex(NFSA_MID_DAY_MEAL_CODE)));
                h.setNfsa_food_gain_under_pdsa_code(cursor.getInt(cursor.getColumnIndex(NFSA_FOOD_GAIN_UNDER_PDSA_CODE)));
                h.setShelterless_code(cursor.getInt(cursor.getColumnIndex(SHELTERLESS_CODE)));
                h.setPrimitive_tribal_group_code(cursor.getInt(cursor.getColumnIndex(PRIMITIVE_TRIBAL_GROUP_CODE)));
                h.setLegally_bonded_labour_code(cursor.getInt(cursor.getColumnIndex(LEGALLY_BONDED_LABOUR_CODE)));
                h.setManual_scavenger_code(cursor.getInt(cursor.getColumnIndex(MANUAL_SCAVENGER_CODE)));
                h.setSeparate_room_as_kitchen_code(cursor.getInt(cursor.getColumnIndex(SEPARATE_ROOM_AS_KITCHEN_CODE)));
                h.setComputer_laptop_avail_code(cursor.getInt(cursor.getColumnIndex(COMPUTER_LAPTOP_AVAIL_CODE)));
                h.setDt_created(cursor.getString(cursor.getColumnIndex(DT_CREATED)));
                h.setUser_id(cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.setDevice_id(cursor.getString(cursor.getColumnIndex(DEVICE_ID)));
                h.setApp_id(cursor.getString(cursor.getColumnIndex(APP_ID)));
                h.setApp_version(cursor.getString(cursor.getColumnIndex(APP_VERSION)));
                h.setIs_completed(cursor.getInt(cursor.getColumnIndex(IS_COMPLETED)));
                h.setIs_synchronized(cursor.getInt(cursor.getColumnIndex(IS_SYNCHRONIZED)));
                h.setIs_uploaded(cursor.getInt(cursor.getColumnIndex(IS_UPLOADED)));
                h.setDt_sync(cursor.getString(cursor.getColumnIndex(DT_SYNC)));
                h.setDt_updated(cursor.getString(cursor.getColumnIndex(DT_UPDATED)));
                h.setIs_new_hhd(cursor.getInt(cursor.getColumnIndex(IS_NEW_HHD))==1?true:false);
                h.setEnumerated_by(cursor.getString(cursor.getColumnIndex(ENUMERATED_BY)));

                list.add(h);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public static boolean updateHhdList(SQLiteDatabase db, ArrayList<HouseholdUpdated> alHhd, Context ctx) {
        ContentValues values = new ContentValues();
        long row = 0;
        try {

            db.beginTransaction();
            for (HouseholdUpdated h : alHhd) {
                values.put(STATE_CODE, h.getState_code());
                values.put(DISTRICT_CODE, h.getDistrict_code());
                values.put(SUB_DISTRICT_CODE, h.getSub_district_code());
                values.put(BLOCK_CODE, h.getBlock_code());
                values.put(GP_CODE, h.getGp_code());
                values.put(VILLAGE_CODE, h.getVillage_code());
                values.put(SHORT_USER_ID, h.getShort_user_id());
                values.put(HHD_SL_NO, h.getHhd_sl_no());
                values.put(DISTRICT_CENSUS_CODE, h.getDistrict_census_code());
                values.put(TOWN_CENSUS_CODE, h.getTown_census_code());
                values.put(HHD_UID, h.getHhd_uid()!=null ? h.getHhd_uid().trim():h.getHhd_uid());
                values.put(HHD_LATITUDE, h.getHhd_latitude());
                values.put(HHD_LONGITUDE, h.getHhd_longitude());
                values.put(HH_HEAD_SL_NO, h.getHh_head_sl_no()!=null ? h.getHh_head_sl_no().trim():h.getHh_head_sl_no());
                values.put(HH_HEAD_NAME, h.getHh_head_name() != null ? h.getHh_head_name().trim():h.getHh_head_name());
                values.put(HOUSEHOLD_TYPE_CODE, h.getHousehold_type_code());
                values.put(ADDRESS_LINE_1, h.getAddressline1());
                values.put(ADDRESS_LINE_2, h.getAddressline2());
                values.put(ADDRESS_LINE_3, h.getAddressline3());
                values.put(ADDRESS_LINE_4, h.getAddressline4());
                values.put(ADDRESS_LINE_5, h.getAddressline5());
                values.put(PINCODE, h.getPincode());
                values.put(PRED_MAT_OF_WALL_DWELLING_ROOM_CODE, h.getPred_mat_of_wall_dwelling_room_code());
                values.put(PRED_MAT_OF_ROOF_DWELLING_ROOM_CODE, h.getPred_mat_of_roof_dwelling_room_code());
                values.put(OWNERSHIP_STATUS_CODE, h.getOwnership_status_code());
                values.put(HH_NO_OF_DWELLING_ROOMS_EXC_IN_POSS, h.getHh_no_of_dwelling_rooms_exc_in_poss());
                values.put(PIPED_DRINKING_WATER_CODE, h.getPiped_drinking_water_code());
                values.put(HOUSE_ROAD_CONNECTIVITY_CODE, h.getHouse_road_connectivity_code());
                values.put(OUTSIDE_ROAD_WITH_STREET_LIGHT_CODE, h.getOutside_road_with_street_light_code());
                values.put(DRAINAGE_FACILITY_CODE, h.getDrainage_facility_code());
                values.put(LATRINE_FACILITY_CODE, h.getLatrine_facility_code());
                values.put(LIGHTING_SOURCE_CODE, h.getLighting_source_code());
                values.put(COOKING_FUEL_CODE, h.getCooking_fuel_code());
                values.put(HOUSE_INCOME_SOURCE_CODE, h.getHousehold_income_source_code());
                values.put(PO_ACCOUNT_CODE, h.getPo_account_code());
                values.put(RATION_CARD_STATUS_CODE, h.getRation_card_status_code());
                values.put(RATION_CARD_NO, String.valueOf(h.getRation_card_no()));
                values.put(PHC_SUB_CENTER_CODE, h.getPhc_sub_center_code());
                values.put(CHILD_FOR_ICDS_CODE, h.getChild_for_icds_code());
                values.put(AGRICULTURE_LOAN_STATUS_CODE, h.getAgriculture_loan_status_code());
                values.put(HH_LIABILITY_RANGE_CODE, h.getHh_liability_range_code());
                values.put(AGRICULTURE_LOAN_WAIVED_CODE, h.getAgriculture_loan_waived_code());
                values.put(TELEPHONE_TYPE_CODE, h.getTelephone_type_code());
                values.put(HH_CONTACT_NO, h.getHh_contact_no());
                values.put(REFRIGERATOR_STATUS_CODE, h.getRefrigerator_status_code());
                values.put(AC_STATUS_CODE, h.getAc_status_code());
                values.put(WASHING_MACHINE_STATUS_CODE, h.getWashing_machine_status_code());
                values.put(SMARTPHONE_STATUS_CODE, h.getSmartphone_status_code());
                values.put(REGISTERED_MOTOR_CODE, h.getRegistered_motor_code());
                values.put(LAND_EXCLUDING_HOME_STATUS_CODE, h.getLand_excluding_home_status_code());
                values.put(TOTAL_UNIRRIGATEDLAND_IN_ACRES, h.getTotal_unirrigatedland_in_acres());
                values.put(WITH_ASSURED_IRRIGATION_FOR_TWO_CROPS_IN_ACRES, h.getWith_assured_irrigation_for_two_crops_in_acres());
                values.put(OTHER_IRRIGATED_LAND_IN_ACRES, h.getOther_irrigated_land_in_acres());
                values.put(MECHANIZED_MACHINE_STATUS_CODE, h.getMechanized_machine_status_code());
                values.put(IRRIGATION_EQUIPMENT_STATUS_CODE, h.getIrrigation_equipment_status_code());
                values.put(KISAN_CREDIT_CARD_GRT_50000_STATUS_CODE, h.getKisan_credit_card_grt_50000_status_code());
                values.put(NFSA_FREE_MEAL_NUT_SUPPORT_CODE, h.getNfsa_free_meal_nut_support_code());
                values.put(NFSA_RS_6000_NUT_SUPPORT_CODE, h.getNfsa_rs_6000_nut_support_code());
                values.put(NFSA_AAGANWADI_NUT_SUPPORT_CODE, h.getNfsa_aaganwadi_nut_support_code());
                values.put(NFSA_MID_DAY_MEAL_CODE, h.getNfsa_mid_day_meal_code());
                values.put(NFSA_FOOD_GAIN_UNDER_PDSA_CODE, h.getNfsa_food_gain_under_pdsa_code());
                values.put(SHELTERLESS_CODE, h.getShelterless_code());
                values.put(PRIMITIVE_TRIBAL_GROUP_CODE, h.getPrimitive_tribal_group_code());
                values.put(LEGALLY_BONDED_LABOUR_CODE, h.getLegally_bonded_labour_code());
                values.put(MANUAL_SCAVENGER_CODE, h.getManual_scavenger_code());
                values.put(SEPARATE_ROOM_AS_KITCHEN_CODE, h.getSeparate_room_as_kitchen_code());
                values.put(COMPUTER_LAPTOP_AVAIL_CODE, h.getComputer_laptop_avail_code());
                values.put(DT_CREATED, h.getDt_created());
                values.put(USER_ID, h.getUser_id());
                values.put(DEVICE_ID, h.getDevice_id());
                values.put(APP_ID, h.getApp_id());
                values.put(APP_VERSION, h.getApp_version());
                values.put(IS_COMPLETED, h.getIs_completed());
                values.put(IS_SYNCHRONIZED, h.getIs_synchronized());
                values.put(IS_UPLOADED, h.getIs_uploaded());
                values.put(DT_SYNC, h.getDt_sync());
                values.put(DT_UPDATED, h.getDt_updated());
                values.put(IS_NEW_HHD, h.getIs_new_hhd());
                values.put(ENUMERATED_BY, h.getEnumerated_by());

                row = db.update(TABLE_NAME, values, HHD_SL_NO + " = ?",
                        new String[]{String.valueOf(h.getHhd_sl_no())});
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {

        }
        finally
        {
            db.endTransaction();
        }
        return row >= 0;
    }


    public static ArrayList<HouseholdUpdated> getAllInCompletedHhd(Context ctx, SQLiteDatabase db) {
        ArrayList<HouseholdUpdated> list = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + IS_COMPLETED + " = ?", new String[]{"0"});
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    HouseholdUpdated h = new HouseholdUpdated();
                    h.setState_code(cursor.getInt(cursor.getColumnIndex(STATE_CODE)));
                    h.setDistrict_code(cursor.getInt(cursor.getColumnIndex(DISTRICT_CODE)));
                    h.setSub_district_code(cursor.getInt(cursor.getColumnIndex(SUB_DISTRICT_CODE)));
                    h.setBlock_code(cursor.getInt(cursor.getColumnIndex(BLOCK_CODE)));
                    h.setGp_code(cursor.getInt(cursor.getColumnIndex(GP_CODE)));
                    h.setVillage_code(cursor.getInt(cursor.getColumnIndex(VILLAGE_CODE)));
                    h.setShort_user_id(cursor.getString(cursor.getColumnIndex(SHORT_USER_ID)));
                    h.setHhd_sl_no(cursor.getInt(cursor.getColumnIndex(HHD_SL_NO)));
                    h.setDistrict_census_code(cursor.getString(cursor.getColumnIndex(DISTRICT_CENSUS_CODE)));
                    h.setTown_census_code(cursor.getString(cursor.getColumnIndex(TOWN_CENSUS_CODE)));
                    h.setHhd_uid(cursor.getString(cursor.getColumnIndex(HHD_UID)));
                    h.setHhd_latitude(cursor.getString(cursor.getColumnIndex(HHD_LATITUDE)));
                    h.setHhd_longitude(cursor.getString(cursor.getColumnIndex(HHD_LONGITUDE)));
                    if(cursor.getString(cursor.getColumnIndex(HH_HEAD_SL_NO)) != null)
                        h.setHh_head_sl_no(cursor.getString(cursor.getColumnIndex(HH_HEAD_SL_NO)));
                    h.setHh_head_name(cursor.getString(cursor.getColumnIndex(HH_HEAD_NAME)));
                    h.setHousehold_type_code(cursor.getInt(cursor.getColumnIndex(HOUSEHOLD_TYPE_CODE)));
                    h.setAddressline1( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_1)));
                    h.setAddressline2( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_2)));
                    h.setAddressline3( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_3)));
                    h.setAddressline4( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_4)));
                    h.setAddressline5( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_5)));
                    h.setPincode( cursor.getString(cursor.getColumnIndex(PINCODE)));
                    h.setPred_mat_of_wall_dwelling_room_code(cursor.getInt(cursor.getColumnIndex(PRED_MAT_OF_WALL_DWELLING_ROOM_CODE)));
                    h.setPred_mat_of_roof_dwelling_room_code(cursor.getInt(cursor.getColumnIndex(PRED_MAT_OF_ROOF_DWELLING_ROOM_CODE)));
                    h.setOwnership_status_code(cursor.getInt(cursor.getColumnIndex(OWNERSHIP_STATUS_CODE)));
                    h.setHh_no_of_dwelling_rooms_exc_in_poss(cursor.getInt(cursor.getColumnIndex(HH_NO_OF_DWELLING_ROOMS_EXC_IN_POSS)));
                    h.setPiped_drinking_water_code(cursor.getInt(cursor.getColumnIndex(PIPED_DRINKING_WATER_CODE)));
                    h.setHouse_road_connectivity_code(cursor.getInt(cursor.getColumnIndex(HOUSE_ROAD_CONNECTIVITY_CODE)));
                    h.setOutside_road_with_street_light_code(cursor.getInt(cursor.getColumnIndex(OUTSIDE_ROAD_WITH_STREET_LIGHT_CODE)));
                    h.setDrainage_facility_code(cursor.getInt(cursor.getColumnIndex(DRAINAGE_FACILITY_CODE)));
                    h.setLatrine_facility_code(cursor.getInt(cursor.getColumnIndex(LATRINE_FACILITY_CODE)));
                    h.setLighting_source_code(cursor.getInt(cursor.getColumnIndex(LIGHTING_SOURCE_CODE)));
                    h.setCooking_fuel_code(cursor.getInt(cursor.getColumnIndex(COOKING_FUEL_CODE)));
                    h.setHousehold_income_source_code(cursor.getInt(cursor.getColumnIndex(HOUSE_INCOME_SOURCE_CODE)));
                    h.setPo_account_code(cursor.getInt(cursor.getColumnIndex(PO_ACCOUNT_CODE)));
                    h.setRation_card_status_code(cursor.getInt(cursor.getColumnIndex(RATION_CARD_STATUS_CODE)));
                    h.setRation_card_no(cursor.getString(cursor.getColumnIndex(RATION_CARD_NO)));
                    h.setPhc_sub_center_code(cursor.getInt(cursor.getColumnIndex(PHC_SUB_CENTER_CODE)));
                    h.setChild_for_icds_code(cursor.getInt(cursor.getColumnIndex(CHILD_FOR_ICDS_CODE)));
                    h.setAgriculture_loan_status_code(cursor.getInt(cursor.getColumnIndex(AGRICULTURE_LOAN_STATUS_CODE)));
                    h.setHh_liability_range_code(cursor.getInt(cursor.getColumnIndex(HH_LIABILITY_RANGE_CODE)));
                    h.setAgriculture_loan_waived_code(cursor.getInt(cursor.getColumnIndex(AGRICULTURE_LOAN_WAIVED_CODE)));
                    h.setTelephone_type_code(cursor.getInt(cursor.getColumnIndex(TELEPHONE_TYPE_CODE)));
                    h.setHh_contact_no(cursor.getString(cursor.getColumnIndex(HH_CONTACT_NO)));
                    h.setRefrigerator_status_code(cursor.getInt(cursor.getColumnIndex(REFRIGERATOR_STATUS_CODE)));
                    h.setAc_status_code(cursor.getInt(cursor.getColumnIndex(AC_STATUS_CODE)));
                    h.setWashing_machine_status_code(cursor.getInt(cursor.getColumnIndex(WASHING_MACHINE_STATUS_CODE)));
                    h.setSmartphone_status_code(cursor.getInt(cursor.getColumnIndex(SMARTPHONE_STATUS_CODE)));
                    h.setRegistered_motor_code(cursor.getInt(cursor.getColumnIndex(REGISTERED_MOTOR_CODE)));
                    h.setLand_excluding_home_status_code(cursor.getInt(cursor.getColumnIndex(LAND_EXCLUDING_HOME_STATUS_CODE)));
                    h.setTotal_unirrigatedland_in_acres(cursor.getDouble(cursor.getColumnIndex(TOTAL_UNIRRIGATEDLAND_IN_ACRES)));
                    h.setWith_assured_irrigation_for_two_crops_in_acres(cursor.getDouble(cursor.getColumnIndex(WITH_ASSURED_IRRIGATION_FOR_TWO_CROPS_IN_ACRES)));
                    h.setOther_irrigated_land_in_acres(cursor.getDouble(cursor.getColumnIndex(OTHER_IRRIGATED_LAND_IN_ACRES)));
                    h.setMechanized_machine_status_code(cursor.getInt(cursor.getColumnIndex(MECHANIZED_MACHINE_STATUS_CODE)));
                    h.setIrrigation_equipment_status_code(cursor.getInt(cursor.getColumnIndex(IRRIGATION_EQUIPMENT_STATUS_CODE)));
                    h.setKisan_credit_card_grt_50000_status_code(cursor.getInt(cursor.getColumnIndex(KISAN_CREDIT_CARD_GRT_50000_STATUS_CODE)));
                    h.setNfsa_free_meal_nut_support_code(cursor.getInt(cursor.getColumnIndex(NFSA_FREE_MEAL_NUT_SUPPORT_CODE)));
                    h.setNfsa_rs_6000_nut_support_code(cursor.getInt(cursor.getColumnIndex(NFSA_RS_6000_NUT_SUPPORT_CODE)));
                    h.setNfsa_aaganwadi_nut_support_code(cursor.getInt(cursor.getColumnIndex(NFSA_AAGANWADI_NUT_SUPPORT_CODE)));
                    h.setNfsa_mid_day_meal_code(cursor.getInt(cursor.getColumnIndex(NFSA_MID_DAY_MEAL_CODE)));
                    h.setNfsa_food_gain_under_pdsa_code(cursor.getInt(cursor.getColumnIndex(NFSA_FOOD_GAIN_UNDER_PDSA_CODE)));
                    h.setShelterless_code(cursor.getInt(cursor.getColumnIndex(SHELTERLESS_CODE)));
                    h.setPrimitive_tribal_group_code(cursor.getInt(cursor.getColumnIndex(PRIMITIVE_TRIBAL_GROUP_CODE)));
                    h.setLegally_bonded_labour_code(cursor.getInt(cursor.getColumnIndex(LEGALLY_BONDED_LABOUR_CODE)));
                    h.setManual_scavenger_code(cursor.getInt(cursor.getColumnIndex(MANUAL_SCAVENGER_CODE)));
                    h.setSeparate_room_as_kitchen_code(cursor.getInt(cursor.getColumnIndex(SEPARATE_ROOM_AS_KITCHEN_CODE)));
                    h.setComputer_laptop_avail_code(cursor.getInt(cursor.getColumnIndex(COMPUTER_LAPTOP_AVAIL_CODE)));
                    h.setDt_created(cursor.getString(cursor.getColumnIndex(DT_CREATED)));
                    h.setUser_id(cursor.getString(cursor.getColumnIndex(USER_ID)));
                    h.setDevice_id(cursor.getString(cursor.getColumnIndex(DEVICE_ID)));
                    h.setApp_id(cursor.getString(cursor.getColumnIndex(APP_ID)));
                    h.setApp_version(cursor.getString(cursor.getColumnIndex(APP_VERSION)));
                    h.setIs_completed(cursor.getInt(cursor.getColumnIndex(IS_COMPLETED)));
                    h.setIs_synchronized(cursor.getInt(cursor.getColumnIndex(IS_SYNCHRONIZED)));
                    h.setIs_uploaded(cursor.getInt(cursor.getColumnIndex(IS_UPLOADED)));
                    h.setDt_sync(cursor.getString(cursor.getColumnIndex(DT_SYNC)));
                    h.setDt_updated(cursor.getString(cursor.getColumnIndex(DT_UPDATED)));
                    h.setIs_new_hhd(cursor.getInt(cursor.getColumnIndex(IS_NEW_HHD))==1?true:false);
                    h.setEnumerated_by(cursor.getString(cursor.getColumnIndex(ENUMERATED_BY)));
                    list.add(h);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }catch(Exception e)
        {
            //MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.add_household_info), context.getString(R.string.house_completed));
        }
        return list;
    }

    public static ArrayList<HouseholdUpdated> getAllUnSyncedHhd(Context ctx, SQLiteDatabase db) {
        ArrayList<HouseholdUpdated> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + IS_SYNCHRONIZED + " = ? AND " + IS_COMPLETED + " = ?", new String[]{"0","1"});
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                HouseholdUpdated h = new HouseholdUpdated();
                h.setState_code(cursor.getInt(cursor.getColumnIndex(STATE_CODE)));
                h.setDistrict_code(cursor.getInt(cursor.getColumnIndex(DISTRICT_CODE)));
                h.setSub_district_code(cursor.getInt(cursor.getColumnIndex(SUB_DISTRICT_CODE)));
                h.setBlock_code(cursor.getInt(cursor.getColumnIndex(BLOCK_CODE)));
                h.setGp_code(cursor.getInt(cursor.getColumnIndex(GP_CODE)));
                h.setVillage_code(cursor.getInt(cursor.getColumnIndex(VILLAGE_CODE)));
                h.setShort_user_id(cursor.getString(cursor.getColumnIndex(SHORT_USER_ID)));
                h.setHhd_sl_no(cursor.getInt(cursor.getColumnIndex(HHD_SL_NO)));
                h.setDistrict_census_code(cursor.getString(cursor.getColumnIndex(DISTRICT_CENSUS_CODE)));
                h.setTown_census_code(cursor.getString(cursor.getColumnIndex(TOWN_CENSUS_CODE)));
                h.setHhd_uid(cursor.getString(cursor.getColumnIndex(HHD_UID)));
                h.setHhd_latitude(cursor.getString(cursor.getColumnIndex(HHD_LATITUDE)));
                h.setHhd_longitude(cursor.getString(cursor.getColumnIndex(HHD_LONGITUDE)));
                if(cursor.getString(cursor.getColumnIndex(HH_HEAD_SL_NO)) != null)
                    h.setHh_head_sl_no(cursor.getString(cursor.getColumnIndex(HH_HEAD_SL_NO)));
                h.setHh_head_name(cursor.getString(cursor.getColumnIndex(HH_HEAD_NAME)));
                h.setHousehold_type_code(cursor.getInt(cursor.getColumnIndex(HOUSEHOLD_TYPE_CODE)));
                h.setAddressline1( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_1)));
                h.setAddressline2( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_2)));
                h.setAddressline3( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_3)));
                h.setAddressline4( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_4)));
                h.setAddressline5( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_5)));
                h.setPincode( cursor.getString(cursor.getColumnIndex(PINCODE)));
                h.setPred_mat_of_wall_dwelling_room_code(cursor.getInt(cursor.getColumnIndex(PRED_MAT_OF_WALL_DWELLING_ROOM_CODE)));
                h.setPred_mat_of_roof_dwelling_room_code(cursor.getInt(cursor.getColumnIndex(PRED_MAT_OF_ROOF_DWELLING_ROOM_CODE)));
                h.setOwnership_status_code(cursor.getInt(cursor.getColumnIndex(OWNERSHIP_STATUS_CODE)));
                h.setHh_no_of_dwelling_rooms_exc_in_poss(cursor.getInt(cursor.getColumnIndex(HH_NO_OF_DWELLING_ROOMS_EXC_IN_POSS)));
                h.setPiped_drinking_water_code(cursor.getInt(cursor.getColumnIndex(PIPED_DRINKING_WATER_CODE)));
                h.setHouse_road_connectivity_code(cursor.getInt(cursor.getColumnIndex(HOUSE_ROAD_CONNECTIVITY_CODE)));
                h.setOutside_road_with_street_light_code(cursor.getInt(cursor.getColumnIndex(OUTSIDE_ROAD_WITH_STREET_LIGHT_CODE)));
                h.setDrainage_facility_code(cursor.getInt(cursor.getColumnIndex(DRAINAGE_FACILITY_CODE)));
                h.setLatrine_facility_code(cursor.getInt(cursor.getColumnIndex(LATRINE_FACILITY_CODE)));
                h.setLighting_source_code(cursor.getInt(cursor.getColumnIndex(LIGHTING_SOURCE_CODE)));
                h.setCooking_fuel_code(cursor.getInt(cursor.getColumnIndex(COOKING_FUEL_CODE)));
                h.setHousehold_income_source_code(cursor.getInt(cursor.getColumnIndex(HOUSE_INCOME_SOURCE_CODE)));
                h.setPo_account_code(cursor.getInt(cursor.getColumnIndex(PO_ACCOUNT_CODE)));
                h.setRation_card_status_code(cursor.getInt(cursor.getColumnIndex(RATION_CARD_STATUS_CODE)));
                h.setRation_card_no(cursor.getString(cursor.getColumnIndex(RATION_CARD_NO)));
                h.setPhc_sub_center_code(cursor.getInt(cursor.getColumnIndex(PHC_SUB_CENTER_CODE)));
                h.setChild_for_icds_code(cursor.getInt(cursor.getColumnIndex(CHILD_FOR_ICDS_CODE)));
                h.setAgriculture_loan_status_code(cursor.getInt(cursor.getColumnIndex(AGRICULTURE_LOAN_STATUS_CODE)));
                h.setHh_liability_range_code(cursor.getInt(cursor.getColumnIndex(HH_LIABILITY_RANGE_CODE)));
                h.setAgriculture_loan_waived_code(cursor.getInt(cursor.getColumnIndex(AGRICULTURE_LOAN_WAIVED_CODE)));
                h.setTelephone_type_code(cursor.getInt(cursor.getColumnIndex(TELEPHONE_TYPE_CODE)));
                h.setHh_contact_no(cursor.getString(cursor.getColumnIndex(HH_CONTACT_NO)));
                h.setRefrigerator_status_code(cursor.getInt(cursor.getColumnIndex(REFRIGERATOR_STATUS_CODE)));
                h.setAc_status_code(cursor.getInt(cursor.getColumnIndex(AC_STATUS_CODE)));
                h.setWashing_machine_status_code(cursor.getInt(cursor.getColumnIndex(WASHING_MACHINE_STATUS_CODE)));
                h.setSmartphone_status_code(cursor.getInt(cursor.getColumnIndex(SMARTPHONE_STATUS_CODE)));
                h.setRegistered_motor_code(cursor.getInt(cursor.getColumnIndex(REGISTERED_MOTOR_CODE)));
                h.setLand_excluding_home_status_code(cursor.getInt(cursor.getColumnIndex(LAND_EXCLUDING_HOME_STATUS_CODE)));
                h.setTotal_unirrigatedland_in_acres(cursor.getDouble(cursor.getColumnIndex(TOTAL_UNIRRIGATEDLAND_IN_ACRES)));
                h.setWith_assured_irrigation_for_two_crops_in_acres(cursor.getDouble(cursor.getColumnIndex(WITH_ASSURED_IRRIGATION_FOR_TWO_CROPS_IN_ACRES)));
                h.setOther_irrigated_land_in_acres(cursor.getDouble(cursor.getColumnIndex(OTHER_IRRIGATED_LAND_IN_ACRES)));
                h.setMechanized_machine_status_code(cursor.getInt(cursor.getColumnIndex(MECHANIZED_MACHINE_STATUS_CODE)));
                h.setIrrigation_equipment_status_code(cursor.getInt(cursor.getColumnIndex(IRRIGATION_EQUIPMENT_STATUS_CODE)));
                h.setKisan_credit_card_grt_50000_status_code(cursor.getInt(cursor.getColumnIndex(KISAN_CREDIT_CARD_GRT_50000_STATUS_CODE)));
                h.setNfsa_free_meal_nut_support_code(cursor.getInt(cursor.getColumnIndex(NFSA_FREE_MEAL_NUT_SUPPORT_CODE)));
                h.setNfsa_rs_6000_nut_support_code(cursor.getInt(cursor.getColumnIndex(NFSA_RS_6000_NUT_SUPPORT_CODE)));
                h.setNfsa_aaganwadi_nut_support_code(cursor.getInt(cursor.getColumnIndex(NFSA_AAGANWADI_NUT_SUPPORT_CODE)));
                h.setNfsa_mid_day_meal_code(cursor.getInt(cursor.getColumnIndex(NFSA_MID_DAY_MEAL_CODE)));
                h.setNfsa_food_gain_under_pdsa_code(cursor.getInt(cursor.getColumnIndex(NFSA_FOOD_GAIN_UNDER_PDSA_CODE)));
                h.setShelterless_code(cursor.getInt(cursor.getColumnIndex(SHELTERLESS_CODE)));
                h.setPrimitive_tribal_group_code(cursor.getInt(cursor.getColumnIndex(PRIMITIVE_TRIBAL_GROUP_CODE)));
                h.setLegally_bonded_labour_code(cursor.getInt(cursor.getColumnIndex(LEGALLY_BONDED_LABOUR_CODE)));
                h.setManual_scavenger_code(cursor.getInt(cursor.getColumnIndex(MANUAL_SCAVENGER_CODE)));
                h.setSeparate_room_as_kitchen_code(cursor.getInt(cursor.getColumnIndex(SEPARATE_ROOM_AS_KITCHEN_CODE)));
                h.setComputer_laptop_avail_code(cursor.getInt(cursor.getColumnIndex(COMPUTER_LAPTOP_AVAIL_CODE)));
                h.setDt_created(cursor.getString(cursor.getColumnIndex(DT_CREATED)));
                h.setUser_id(cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.setDevice_id(cursor.getString(cursor.getColumnIndex(DEVICE_ID)));
                h.setApp_id(cursor.getString(cursor.getColumnIndex(APP_ID)));
                h.setApp_version(cursor.getString(cursor.getColumnIndex(APP_VERSION)));
                h.setIs_completed(cursor.getInt(cursor.getColumnIndex(IS_COMPLETED)));
                h.setIs_synchronized(cursor.getInt(cursor.getColumnIndex(IS_SYNCHRONIZED)));
                h.setIs_uploaded(cursor.getInt(cursor.getColumnIndex(IS_UPLOADED)));
                h.setDt_sync(cursor.getString(cursor.getColumnIndex(DT_SYNC)));
                h.setDt_updated(cursor.getString(cursor.getColumnIndex(DT_UPDATED)));
                h.setIs_new_hhd(cursor.getInt(cursor.getColumnIndex(IS_NEW_HHD))==1?true:false);
                h.setEnumerated_by(cursor.getString(cursor.getColumnIndex(ENUMERATED_BY)));
                list.add(h);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public static ArrayList<HouseholdUpdated> getAllSyncedHhd(Context ctx, SQLiteDatabase db) {
        ArrayList<HouseholdUpdated> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + IS_SYNCHRONIZED + " = ? " , new String[]{"1"});
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                HouseholdUpdated h = new HouseholdUpdated();
                h.setState_code(cursor.getInt(cursor.getColumnIndex(STATE_CODE)));
                h.setDistrict_code(cursor.getInt(cursor.getColumnIndex(DISTRICT_CODE)));
                h.setSub_district_code(cursor.getInt(cursor.getColumnIndex(SUB_DISTRICT_CODE)));
                h.setBlock_code(cursor.getInt(cursor.getColumnIndex(BLOCK_CODE)));
                h.setGp_code(cursor.getInt(cursor.getColumnIndex(GP_CODE)));
                h.setVillage_code(cursor.getInt(cursor.getColumnIndex(VILLAGE_CODE)));
                h.setShort_user_id(cursor.getString(cursor.getColumnIndex(SHORT_USER_ID)));
                h.setHhd_sl_no(cursor.getInt(cursor.getColumnIndex(HHD_SL_NO)));
                h.setDistrict_census_code(cursor.getString(cursor.getColumnIndex(DISTRICT_CENSUS_CODE)));
                h.setTown_census_code(cursor.getString(cursor.getColumnIndex(TOWN_CENSUS_CODE)));
                h.setHhd_uid(cursor.getString(cursor.getColumnIndex(HHD_UID)));
                h.setHhd_latitude(cursor.getString(cursor.getColumnIndex(HHD_LATITUDE)));
                h.setHhd_longitude(cursor.getString(cursor.getColumnIndex(HHD_LONGITUDE)));
                if(cursor.getString(cursor.getColumnIndex(HH_HEAD_SL_NO)) != null)
                    h.setHh_head_sl_no(cursor.getString(cursor.getColumnIndex(HH_HEAD_SL_NO)));
                h.setHh_head_name(cursor.getString(cursor.getColumnIndex(HH_HEAD_NAME)));
                h.setHousehold_type_code(cursor.getInt(cursor.getColumnIndex(HOUSEHOLD_TYPE_CODE)));
                h.setAddressline1( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_1)));
                h.setAddressline2( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_2)));
                h.setAddressline3( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_3)));
                h.setAddressline4( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_4)));
                h.setAddressline5( cursor.getString(cursor.getColumnIndex(ADDRESS_LINE_5)));
                h.setPincode( cursor.getString(cursor.getColumnIndex(PINCODE)));
                h.setPred_mat_of_wall_dwelling_room_code(cursor.getInt(cursor.getColumnIndex(PRED_MAT_OF_WALL_DWELLING_ROOM_CODE)));
                h.setPred_mat_of_roof_dwelling_room_code(cursor.getInt(cursor.getColumnIndex(PRED_MAT_OF_ROOF_DWELLING_ROOM_CODE)));
                h.setOwnership_status_code(cursor.getInt(cursor.getColumnIndex(OWNERSHIP_STATUS_CODE)));
                h.setHh_no_of_dwelling_rooms_exc_in_poss(cursor.getInt(cursor.getColumnIndex(HH_NO_OF_DWELLING_ROOMS_EXC_IN_POSS)));
                h.setPiped_drinking_water_code(cursor.getInt(cursor.getColumnIndex(PIPED_DRINKING_WATER_CODE)));
                h.setHouse_road_connectivity_code(cursor.getInt(cursor.getColumnIndex(HOUSE_ROAD_CONNECTIVITY_CODE)));
                h.setOutside_road_with_street_light_code(cursor.getInt(cursor.getColumnIndex(OUTSIDE_ROAD_WITH_STREET_LIGHT_CODE)));
                h.setDrainage_facility_code(cursor.getInt(cursor.getColumnIndex(DRAINAGE_FACILITY_CODE)));
                h.setLatrine_facility_code(cursor.getInt(cursor.getColumnIndex(LATRINE_FACILITY_CODE)));
                h.setLighting_source_code(cursor.getInt(cursor.getColumnIndex(LIGHTING_SOURCE_CODE)));
                h.setCooking_fuel_code(cursor.getInt(cursor.getColumnIndex(COOKING_FUEL_CODE)));
                h.setHousehold_income_source_code(cursor.getInt(cursor.getColumnIndex(HOUSE_INCOME_SOURCE_CODE)));
                h.setPo_account_code(cursor.getInt(cursor.getColumnIndex(PO_ACCOUNT_CODE)));
                h.setRation_card_status_code(cursor.getInt(cursor.getColumnIndex(RATION_CARD_STATUS_CODE)));
                h.setRation_card_no(cursor.getString(cursor.getColumnIndex(RATION_CARD_NO)));
                h.setPhc_sub_center_code(cursor.getInt(cursor.getColumnIndex(PHC_SUB_CENTER_CODE)));
                h.setChild_for_icds_code(cursor.getInt(cursor.getColumnIndex(CHILD_FOR_ICDS_CODE)));
                h.setAgriculture_loan_status_code(cursor.getInt(cursor.getColumnIndex(AGRICULTURE_LOAN_STATUS_CODE)));
                h.setHh_liability_range_code(cursor.getInt(cursor.getColumnIndex(HH_LIABILITY_RANGE_CODE)));
                h.setAgriculture_loan_waived_code(cursor.getInt(cursor.getColumnIndex(AGRICULTURE_LOAN_WAIVED_CODE)));
                h.setTelephone_type_code(cursor.getInt(cursor.getColumnIndex(TELEPHONE_TYPE_CODE)));
                h.setHh_contact_no(cursor.getString(cursor.getColumnIndex(HH_CONTACT_NO)));
                h.setRefrigerator_status_code(cursor.getInt(cursor.getColumnIndex(REFRIGERATOR_STATUS_CODE)));
                h.setAc_status_code(cursor.getInt(cursor.getColumnIndex(AC_STATUS_CODE)));
                h.setWashing_machine_status_code(cursor.getInt(cursor.getColumnIndex(WASHING_MACHINE_STATUS_CODE)));
                h.setSmartphone_status_code(cursor.getInt(cursor.getColumnIndex(SMARTPHONE_STATUS_CODE)));
                h.setRegistered_motor_code(cursor.getInt(cursor.getColumnIndex(REGISTERED_MOTOR_CODE)));
                h.setLand_excluding_home_status_code(cursor.getInt(cursor.getColumnIndex(LAND_EXCLUDING_HOME_STATUS_CODE)));
                h.setTotal_unirrigatedland_in_acres(cursor.getDouble(cursor.getColumnIndex(TOTAL_UNIRRIGATEDLAND_IN_ACRES)));
                h.setWith_assured_irrigation_for_two_crops_in_acres(cursor.getDouble(cursor.getColumnIndex(WITH_ASSURED_IRRIGATION_FOR_TWO_CROPS_IN_ACRES)));
                h.setOther_irrigated_land_in_acres(cursor.getDouble(cursor.getColumnIndex(OTHER_IRRIGATED_LAND_IN_ACRES)));
                h.setMechanized_machine_status_code(cursor.getInt(cursor.getColumnIndex(MECHANIZED_MACHINE_STATUS_CODE)));
                h.setIrrigation_equipment_status_code(cursor.getInt(cursor.getColumnIndex(IRRIGATION_EQUIPMENT_STATUS_CODE)));
                h.setKisan_credit_card_grt_50000_status_code(cursor.getInt(cursor.getColumnIndex(KISAN_CREDIT_CARD_GRT_50000_STATUS_CODE)));
                h.setNfsa_free_meal_nut_support_code(cursor.getInt(cursor.getColumnIndex(NFSA_FREE_MEAL_NUT_SUPPORT_CODE)));
                h.setNfsa_rs_6000_nut_support_code(cursor.getInt(cursor.getColumnIndex(NFSA_RS_6000_NUT_SUPPORT_CODE)));
                h.setNfsa_aaganwadi_nut_support_code(cursor.getInt(cursor.getColumnIndex(NFSA_AAGANWADI_NUT_SUPPORT_CODE)));
                h.setNfsa_mid_day_meal_code(cursor.getInt(cursor.getColumnIndex(NFSA_MID_DAY_MEAL_CODE)));
                h.setNfsa_food_gain_under_pdsa_code(cursor.getInt(cursor.getColumnIndex(NFSA_FOOD_GAIN_UNDER_PDSA_CODE)));
                h.setShelterless_code(cursor.getInt(cursor.getColumnIndex(SHELTERLESS_CODE)));
                h.setPrimitive_tribal_group_code(cursor.getInt(cursor.getColumnIndex(PRIMITIVE_TRIBAL_GROUP_CODE)));
                h.setLegally_bonded_labour_code(cursor.getInt(cursor.getColumnIndex(LEGALLY_BONDED_LABOUR_CODE)));
                h.setManual_scavenger_code(cursor.getInt(cursor.getColumnIndex(MANUAL_SCAVENGER_CODE)));
                h.setSeparate_room_as_kitchen_code(cursor.getInt(cursor.getColumnIndex(SEPARATE_ROOM_AS_KITCHEN_CODE)));
                h.setComputer_laptop_avail_code(cursor.getInt(cursor.getColumnIndex(COMPUTER_LAPTOP_AVAIL_CODE)));
                h.setDt_created(cursor.getString(cursor.getColumnIndex(DT_CREATED)));
                h.setUser_id(cursor.getString(cursor.getColumnIndex(USER_ID)));
                h.setDevice_id(cursor.getString(cursor.getColumnIndex(DEVICE_ID)));
                h.setApp_id(cursor.getString(cursor.getColumnIndex(APP_ID)));
                h.setApp_version(cursor.getString(cursor.getColumnIndex(APP_VERSION)));
                h.setIs_completed(cursor.getInt(cursor.getColumnIndex(IS_COMPLETED)));
                h.setIs_synchronized(cursor.getInt(cursor.getColumnIndex(IS_SYNCHRONIZED)));
                h.setIs_uploaded(cursor.getInt(cursor.getColumnIndex(IS_UPLOADED)));
                h.setDt_sync(cursor.getString(cursor.getColumnIndex(DT_SYNC)));
                h.setDt_updated(cursor.getString(cursor.getColumnIndex(DT_UPDATED)));
                h.setIs_new_hhd(cursor.getInt(cursor.getColumnIndex(IS_NEW_HHD))==1?true:false);
                h.setEnumerated_by(cursor.getString(cursor.getColumnIndex(ENUMERATED_BY)));
                list.add(h);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public static boolean isTableExist(SQLiteDatabase db) {
        boolean isResult=false;
        int isexist=0;
        String query="select isexist from (\n" +
                "select count(1)>0 as isexist from sqlite_master where name = '"+TABLE_NAME+"' )a";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            isexist = cursor.getInt(cursor.getColumnIndex("isexist"));
            if(isexist==1)
            {
                String query2="select count(1)>0 as isexist from "+TABLE_NAME;

                Cursor cursor2 = db.rawQuery(query2, null);
                if (cursor2.moveToFirst()) {
                    isexist = cursor2.getInt(cursor2.getColumnIndex("isexist"));
                    if (isexist == 1)
                        isResult = true;
                    else
                        isResult = false;
                }
                else
                {
                    isResult = false;
                }
            }
            else
            {
                isResult = false;
            }
        }
        else
        {
            isResult=false;
        }
        return isResult;
    }

    public static void delete(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

}
