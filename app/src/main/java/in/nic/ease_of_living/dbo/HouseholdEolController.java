package in.nic.ease_of_living.dbo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.models.HouseholdEol;
import in.nic.ease_of_living.models.HouseholdEolEnumerated;
import in.nic.ease_of_living.models.SeccHousehold;
import in.nic.ease_of_living.models.SeccPopulation;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.utils.Common;


/**
 * Created by Chinki Sai on 7/4/2017.
 */

public class HouseholdEolController {
    private static final String TABLE_NAME = "household_eol_status";

    private static final String STATE_CODE= "state_code";
    private static final String STATE_NAME= "state_name";
    private static final String STATE_NAME_SL= "state_name_sl";
    private static final String DISTRICT_CODE= "district_code";
    private static final String DISTRICT_NAME= "district_name";
    private static final String DISTRICT_NAME_SL= "district_name_sl";
    private static final String SUB_DISTRICT_CODE= "sub_district_code";
    private static final String SUB_DISTRICT_NAME= "sub_district_name";
    private static final String SUB_DISTRICT_NAME_SL= "sub_district_name_sl";
    private static final String BLOCK_CODE= "block_code";
    private static final String BLOCK_NAME= "block_name";
    private static final String BLOCK_NAME_SL= "block_name_sl";
    private static final String GP_CODE= "gp_code";
    private static final String GP_NAME= "gp_name";
    private static final String GP_NAME_SL= "gp_name_sl";
    private static final String VILLAGE_CODE= "village_code";
    private static final String VILLAGE_NAME= "village_name";
    private static final String VILLAGE_NAME_SL= "village_name_sl";
    private static final String ENUM_BLOCK_CODE= "enum_block_code";
    private static final String ENUM_BLOCK_NAME= "enum_block_name";
    private static final String ENUM_BLOCK_NAME_SL= "enum_block_name_sl";
    private static final String HHD_UID = "hhd_uid";
    private static final String IS_UNCOVERED = "is_uncovered";
    private static final String UNCOVERED_REASON_CODE = "uncovered_reason_code";
    private static final String UNCOVERED_REASON = "uncovered_reason";

    private static final String STATECODE = "statecode";
    private static final String DISTRICTCODE = "districtcode";
    private static final String TEHSILCODE = "tehsilcode";
    private static final String TOWNCODE = "towncode";
    private static final String WARDID = "wardid";
    private static final String AHLBLOCKNO = "ahlblockno";
    private static final String AHLSUBBLOCKNO = "ahlsubblockno";
    private static final String AHL_FAMILY_TIN = "ahl_family_tin";

    private static final String IS_LPG_CONNECTION_AVAILABLE = "is_lpg_connection_available";
    private static final String LPG_CONSUMER_ID = "lpg_consumer_id";
    private static final String NO_OF_TIMES_REFILLED_IN_LAST_ONE_YR = "no_of_times_refilled_in_last_one_yr";
    private static final String IS_ELECTRICITY_CONNECTION_AVAILABLE = "is_electricity_connection_available";
    private static final String IS_LED_AVAILABLE_UNDER_UJALA = "is_led_available_under_ujala";
    private static final String IS_ANY_MEMBER_HAVE_JANDHAN_AC = "is_any_member_have_jandhan_ac";
    private static final String IS_HHD_ENROLLED_IN_PMJJBY = "is_hhd_enrolled_in_pmjjby";
    private static final String IS_ANY_MEMBER_ENROLLED_IN_PMSBY = "is_any_member_enrolled_in_pmsby";
    private static final String IS_ANY_MEMBER_IMMUNISED_UNDER_MISSION_INDRADHANUSH = "is_any_member_immunised_under_mission_indradhanush";
    private static final String IS_ANY_CHILD_0_6_YRS_OR_PREGNANT_WOMAN_AVAILABLE = "is_any_child_0_6_yrs_or_pregnant_woman_available";
    private static final String IS_ANY_MEMBER_REGISTERED_IN_ICDS = "is_any_member_registered_in_icds";
    private static final String IS_HHD_AVAILED_NUTRITION_BENEFITS_UNDER_ICDS = "is_hhd_availed_nutrition_benefits_under_icds";
    private static final String SERVICES_AVAILED_UNDER_ICDS = "services_availed_under_icds";
    private static final String IS_ANY_MEMBER_OF_HHD_IS_MEMBER_OF_SHG = "is_any_member_of_hhd_is_member_of_shg";
    private static final String TYPE_OF_HOUSE_USED_FOR_LIVING = "type_of_house_used_for_living";
    private static final String IS_HHD_A_BENEFICIARY_OF_PMAYG = "is_hhd_a_beneficiary_of_pmayg";
    private static final String PMAYG_STATUS = "pmayg_status";
    private static final String PMAYG_ID = "pmayg_id";
    private static final String IS_HHD_ISSUED_HEALTH_CARD_UNDER_ABPMJAY = "is_hhd_issued_health_card_under_abpmjay";
    private static final String FAMILY_HEALTH_CARD_NUMBER = "family_health_card_number";
    private static final String IS_ANY_MEMBER_GETTING_PENSION_UNDER_NSAP = "is_any_member_getting_pension_under_nsap";
    private static final String TYPE_OF_PENSIONS = "type_of_pensions";
    private static final String IS_ANY_MEMBER_EVER_WORKED_UNDER_MGNREGA = "is_any_member_ever_worked_under_mgnrega";
    private static final String MGNREGA_JOB_CARD_NUMBER = "mgnrega_job_card_number";
    private static final String NO_OF_DAYS_WORKED_UNDER_MGNREGA_IN_LAST_ONE_YR = "no_of_days_worked_under_mgnrega_in_last_one_yr";
    private static final String IS_ANY_MEMBER_UNDERGONE_TRAINING_UNDER_ANY_SKILL_DEV_PROG = "is_any_member_undergone_training_under_any_skill_dev_prog";
    private static final String UNDERGONE_SKILL_DEVELOPMENT_SCHEMES = "undergone_skill_development_schemes";
    private static final String IS_HHD_AVAILABLE_A_FUNCTIONAL_TOILET = "is_hhd_available_a_functional_toilet";
    private static final String MOBILE_NUMBERS_HHD_MEMBERS = "mobile_numbers_hhd_members";
    private static final String IS_HHD_AVAILING_RATION_UNDER_NFSA_SCHEME = "is_hhd_availing_ration_under_nfsa_scheme";
    private static final String NFSA_RATION_CARD_TYPE = "nfsa_ration_card_type";
    private static final String NFSA_RATION_CARD_NUMBER = "nfsa_ration_card_number";

    private static final String HHD_LATITUDE = "hhd_latitude";
    private static final String HHD_LONGITUDE = "hhd_longitude";

    private static final String USER_ID = "user_id";
    private static final String DEVICE_ID = "device_id";
    private static final String APP_ID = "app_id";
    private static final String APP_VERSION = "app_version";
    private static final String TS_UPDATED = "ts_updated";
    private static final String DT_CREATED = "dt_created";
    private static final String IS_COMPLETED = "is_completed";
    private static final String IS_SYNCHRONIZED = "is_synchronized";
    private static final String IS_UPLOADED = "is_uploaded";
    private static final String IS_VERIFIED = "is_verified";
    private static final String DT_SYNC = "dt_sync";
    private static final String TS_VERIFIED = "ts_verified";

    private static final String ENUMERATED_BY = "enumerated_by";


    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + 	STATE_CODE	 + " INTEGER  NOT NULL,"
            + 	STATE_NAME	 + " TEXT ,"
            + 	STATE_NAME_SL	 + " TEXT ,"
            + 	DISTRICT_CODE	 + " INTEGER  NOT NULL,"
            + 	DISTRICT_NAME	 + " TEXT ,"
            + 	DISTRICT_NAME_SL	 + " TEXT ,"
            + 	SUB_DISTRICT_CODE	 + " INTEGER  NOT NULL,"
            + 	SUB_DISTRICT_NAME	 + " TEXT ,"
            + 	SUB_DISTRICT_NAME_SL	 + " TEXT ,"
            + 	BLOCK_CODE	 + " INTEGER  NOT NULL,"
            + 	BLOCK_NAME	 + " TEXT ,"
            + 	BLOCK_NAME_SL	 + " TEXT ,"
            + 	GP_CODE	 + " INTEGER  NOT NULL,"
            + 	GP_NAME	 + " TEXT ,"
            + 	GP_NAME_SL	 + " TEXT ,"
            + 	VILLAGE_CODE	 + " INTEGER  NOT NULL,"
            + 	VILLAGE_NAME	 + " TEXT ,"
            + 	VILLAGE_NAME_SL	 + " TEXT ,"
            + 	ENUM_BLOCK_CODE	 + " INTEGER  NOT NULL,"
            + 	ENUM_BLOCK_NAME + " TEXT ,"
            + 	ENUM_BLOCK_NAME_SL + " TEXT ,"
            +  HHD_UID + " INTEGER  NOT NULL,"
            +  IS_UNCOVERED + " INTEGER  NOT NULL,"
            +  UNCOVERED_REASON_CODE + " INTEGER  NOT NULL,"
            +  UNCOVERED_REASON + " TEXT  NOT NULL,"

            +  STATECODE + " TEXT ,"
            +  DISTRICTCODE + " TEXT ,"
            +  TEHSILCODE + " TEXT ,"
            +  TOWNCODE + " TEXT ,"
            +  WARDID + " TEXT ,"
            +  AHLBLOCKNO + " TEXT ,"
            +  AHLSUBBLOCKNO + " TEXT ,"
            +  AHL_FAMILY_TIN + " TEXT  NOT NULL,"

            +  IS_LPG_CONNECTION_AVAILABLE + " INTEGER  NOT NULL,"
            +  LPG_CONSUMER_ID + " TEXT ,"
            +  NO_OF_TIMES_REFILLED_IN_LAST_ONE_YR + " INTEGER ,"
            +  IS_ELECTRICITY_CONNECTION_AVAILABLE + " INTEGER  NOT NULL,"
            +  IS_LED_AVAILABLE_UNDER_UJALA + " INTEGER ,"
            +  IS_ANY_MEMBER_HAVE_JANDHAN_AC + " INTEGER  NOT NULL,"
            +  IS_HHD_ENROLLED_IN_PMJJBY + " INTEGER  NOT NULL,"
            +  IS_ANY_MEMBER_ENROLLED_IN_PMSBY + " INTEGER  NOT NULL,"
            +  IS_ANY_MEMBER_IMMUNISED_UNDER_MISSION_INDRADHANUSH + " INTEGER  NOT NULL,"
            +  IS_ANY_CHILD_0_6_YRS_OR_PREGNANT_WOMAN_AVAILABLE + " INTEGER  NOT NULL,"
            +  IS_ANY_MEMBER_REGISTERED_IN_ICDS + " INTEGER ,"
            +  IS_HHD_AVAILED_NUTRITION_BENEFITS_UNDER_ICDS + " INTEGER ,"
            +  SERVICES_AVAILED_UNDER_ICDS + " TEXT ,"
            +  IS_ANY_MEMBER_OF_HHD_IS_MEMBER_OF_SHG + " INTEGER  NOT NULL,"
            +  TYPE_OF_HOUSE_USED_FOR_LIVING + " INTEGER  NOT NULL,"
            +  IS_HHD_A_BENEFICIARY_OF_PMAYG + " INTEGER  NOT NULL,"
            +  PMAYG_STATUS + " INTEGER ,"
            +  PMAYG_ID + " TEXT ,"
            +  IS_HHD_ISSUED_HEALTH_CARD_UNDER_ABPMJAY + " INTEGER  NOT NULL,"
            +  FAMILY_HEALTH_CARD_NUMBER + " TEXT ,"
            +  IS_ANY_MEMBER_GETTING_PENSION_UNDER_NSAP + " INTEGER  NOT NULL,"
            +  TYPE_OF_PENSIONS + " TEXT ,"
            +  IS_ANY_MEMBER_EVER_WORKED_UNDER_MGNREGA + " INTEGER  NOT NULL,"
            +  MGNREGA_JOB_CARD_NUMBER + " TEXT ,"
            +  NO_OF_DAYS_WORKED_UNDER_MGNREGA_IN_LAST_ONE_YR + " INTEGER ,"
            +  IS_ANY_MEMBER_UNDERGONE_TRAINING_UNDER_ANY_SKILL_DEV_PROG + " INTEGER  NOT NULL,"
            +  UNDERGONE_SKILL_DEVELOPMENT_SCHEMES + " TEXT ,"
            +  IS_HHD_AVAILABLE_A_FUNCTIONAL_TOILET + " INTEGER  NOT NULL,"
            +  MOBILE_NUMBERS_HHD_MEMBERS + " TEXT ,"
            +  IS_HHD_AVAILING_RATION_UNDER_NFSA_SCHEME + " INTEGER  NOT NULL,"
            +  NFSA_RATION_CARD_TYPE + " INTEGER ,"
            +  NFSA_RATION_CARD_NUMBER + " TEXT ,"

            +  HHD_LATITUDE + " TEXT  NOT NULL,"
            +  HHD_LONGITUDE + " TEXT  NOT NULL,"

            +  USER_ID + " TEXT  NOT NULL,"
            +  DEVICE_ID + " TEXT  NOT NULL,"
            +  APP_ID + " TEXT  NOT NULL,"
            +  APP_VERSION + " TEXT  NOT NULL,"
            +  TS_UPDATED + " TEXT ,"
            +  DT_CREATED + " TEXT  NOT NULL,"
            +  IS_COMPLETED + " INTEGER ,"
            +  IS_SYNCHRONIZED + " INTEGER ,"
            +  IS_UPLOADED + " INTEGER ,"
            +  IS_VERIFIED + " INTEGER ,"
            +  DT_SYNC + " TEXT ,"
            +  TS_VERIFIED + " TEXT ,"

            +  ENUMERATED_BY + " TEXT ,"

            + " CONSTRAINT household_eol_status_pkey PRIMARY KEY (state_code, district_code, sub_district_code, block_code, gp_code, village_code, enum_block_code, hhd_uid) )";

    private static HouseholdEol getHouseholdFromCursor(Cursor cursor)
    {
        HouseholdEol h = new HouseholdEol();
        h.setState_code(cursor.getInt(cursor.getColumnIndex(	STATE_CODE	)));
        h.setState_name(cursor.getString(cursor.getColumnIndex(	STATE_NAME	)));
        h.setState_name_sl(cursor.getString(cursor.getColumnIndex(	STATE_NAME_SL	)));
        h.setDistrict_code(cursor.getInt(cursor.getColumnIndex(	DISTRICT_CODE	)));
        h.setDistrict_name(cursor.getString(cursor.getColumnIndex(	DISTRICT_NAME	)));
        h.setDistrict_name_sl(cursor.getString(cursor.getColumnIndex(	DISTRICT_NAME_SL	)));
        h.setSub_district_code(cursor.getInt(cursor.getColumnIndex(	SUB_DISTRICT_CODE	)));
        h.setSub_district_name(cursor.getString(cursor.getColumnIndex(	SUB_DISTRICT_NAME	)));
        h.setSub_district_name_sl(cursor.getString(cursor.getColumnIndex(	SUB_DISTRICT_NAME_SL	)));
        h.setBlock_code(cursor.getInt(cursor.getColumnIndex(	BLOCK_CODE	)));
        h.setBlock_name(cursor.getString(cursor.getColumnIndex(	BLOCK_NAME	)));
        h.setBlock_name_sl(cursor.getString(cursor.getColumnIndex(	BLOCK_NAME_SL	)));
        h.setGp_code(cursor.getInt(cursor.getColumnIndex(	GP_CODE	)));
        h.setGp_name(cursor.getString(cursor.getColumnIndex(	GP_NAME	)));
        h.setGp_name_sl(cursor.getString(cursor.getColumnIndex(	GP_NAME_SL	)));
        h.setVillage_code(cursor.getInt(cursor.getColumnIndex(	VILLAGE_CODE	)));
        h.setVillage_name(cursor.getString(cursor.getColumnIndex(	VILLAGE_NAME	)));
        h.setVillage_name_sl(cursor.getString(cursor.getColumnIndex(	VILLAGE_NAME_SL	)));
        h.setEnum_block_code(cursor.getInt(cursor.getColumnIndex(	ENUM_BLOCK_CODE	)));
        h.setEnum_block_name(cursor.getString(cursor.getColumnIndex(	ENUM_BLOCK_NAME	)));
        h.setEnum_block_name_sl(cursor.getString(cursor.getColumnIndex(	ENUM_BLOCK_NAME_SL	)));
        h.setHhd_uid(cursor.getInt(cursor.getColumnIndex(HHD_UID)));
        h.setIs_uncovered(cursor.getInt(cursor.getColumnIndex(IS_UNCOVERED)) == 1? true:false);
        h.setUncovered_reason_code(cursor.getInt(cursor.getColumnIndex(UNCOVERED_REASON_CODE)));
        h.setUncovered_reason(cursor.getString(cursor.getColumnIndex(UNCOVERED_REASON)));

        h.setStatecode(cursor.getString(cursor.getColumnIndex(STATECODE)));
        h.setDistrictcode(cursor.getString(cursor.getColumnIndex(DISTRICTCODE)));
        h.setTehsilcode(cursor.getString(cursor.getColumnIndex(TEHSILCODE)));
        h.setTowncode(cursor.getString(cursor.getColumnIndex(TOWNCODE)));
        h.setWardid(cursor.getString(cursor.getColumnIndex(WARDID)));
        h.setAhlblockno(cursor.getString(cursor.getColumnIndex(AHLBLOCKNO)));
        h.setAhlsubblockno(cursor.getString(cursor.getColumnIndex(AHLSUBBLOCKNO)));
        h.setAhl_family_tin(cursor.getString(cursor.getColumnIndex(AHL_FAMILY_TIN)));

        if(cursor.getInt(cursor.getColumnIndex(IS_LPG_CONNECTION_AVAILABLE)) == 0)
            h.setIs_lpg_connection_available(null);
        else
            h.setIs_lpg_connection_available(cursor.getInt(cursor.getColumnIndex(IS_LPG_CONNECTION_AVAILABLE))==1? true : false);
        h.setLpg_consumer_id(cursor.getString(cursor.getColumnIndex(LPG_CONSUMER_ID)));
        h.setNo_of_times_refilled_in_last_one_yr(cursor.getInt(cursor.getColumnIndex(NO_OF_TIMES_REFILLED_IN_LAST_ONE_YR)));
        if(cursor.getInt(cursor.getColumnIndex(IS_ELECTRICITY_CONNECTION_AVAILABLE)) == 0)
            h.setIs_electricity_connection_available(null);
        else
            h.setIs_electricity_connection_available(cursor.getInt(cursor.getColumnIndex(IS_ELECTRICITY_CONNECTION_AVAILABLE))==1? true : false);
        if(cursor.getInt(cursor.getColumnIndex(IS_LED_AVAILABLE_UNDER_UJALA)) == 0)
            h.setIs_led_available_under_ujala(null);
        else
            h.setIs_led_available_under_ujala(cursor.getInt(cursor.getColumnIndex(IS_LED_AVAILABLE_UNDER_UJALA))==1? true : false);
        if(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_HAVE_JANDHAN_AC)) == 0)
            h.setIs_any_member_have_jandhan_ac(null);
        else
            h.setIs_any_member_have_jandhan_ac(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_HAVE_JANDHAN_AC))==1? true : false);
        if(cursor.getInt(cursor.getColumnIndex(IS_HHD_ENROLLED_IN_PMJJBY)) == 0)
            h.setIs_hhd_enrolled_in_pmjjby(null);
        else
            h.setIs_hhd_enrolled_in_pmjjby(cursor.getInt(cursor.getColumnIndex(IS_HHD_ENROLLED_IN_PMJJBY))==1? true : false);
        if(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_ENROLLED_IN_PMSBY)) == 0)
            h.setIs_any_member_enrolled_in_pmsby(null);
        else
            h.setIs_any_member_enrolled_in_pmsby(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_ENROLLED_IN_PMSBY))==1? true : false);
        if(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_IMMUNISED_UNDER_MISSION_INDRADHANUSH)) == 0)
            h.setIs_any_member_immunised_under_mission_indradhanush(null);
        else
            h.setIs_any_member_immunised_under_mission_indradhanush(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_IMMUNISED_UNDER_MISSION_INDRADHANUSH))==1? true : false);
        if(cursor.getInt(cursor.getColumnIndex(IS_ANY_CHILD_0_6_YRS_OR_PREGNANT_WOMAN_AVAILABLE)) == 0)
            h.setIs_any_child_0_6_yrs_or_pregnant_woman_available(null);
        else
            h.setIs_any_child_0_6_yrs_or_pregnant_woman_available(cursor.getInt(cursor.getColumnIndex(IS_ANY_CHILD_0_6_YRS_OR_PREGNANT_WOMAN_AVAILABLE))==1? true : false);
        if(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_REGISTERED_IN_ICDS)) == 0)
            h.setIs_any_member_registered_in_icds(null);
        else
            h.setIs_any_member_registered_in_icds(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_REGISTERED_IN_ICDS))==1? true : false);
        if(cursor.getInt(cursor.getColumnIndex(IS_HHD_AVAILED_NUTRITION_BENEFITS_UNDER_ICDS)) == 0)
            h.setIs_hhd_availed_nutrition_benefits_under_icds(null);
        else
            h.setIs_hhd_availed_nutrition_benefits_under_icds(cursor.getInt(cursor.getColumnIndex(IS_HHD_AVAILED_NUTRITION_BENEFITS_UNDER_ICDS))==1? true : false);
        h.setServices_availed_under_icds(Common.convertStringToArrayListInt(cursor.getString(cursor.getColumnIndex(SERVICES_AVAILED_UNDER_ICDS))));
        if(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_OF_HHD_IS_MEMBER_OF_SHG)) == 0)
            h.setIs_any_member_of_hhd_is_member_of_shg(null);
        else
            h.setIs_any_member_of_hhd_is_member_of_shg(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_OF_HHD_IS_MEMBER_OF_SHG))==1? true : false);
        h.setType_of_house_used_for_living(cursor.getInt(cursor.getColumnIndex(TYPE_OF_HOUSE_USED_FOR_LIVING)));
        if(cursor.getInt(cursor.getColumnIndex(IS_HHD_A_BENEFICIARY_OF_PMAYG)) == 0)
            h.setIs_hhd_a_beneficiary_of_pmayg(null);
        else
            h.setIs_hhd_a_beneficiary_of_pmayg(cursor.getInt(cursor.getColumnIndex(IS_HHD_A_BENEFICIARY_OF_PMAYG))==1? true : false);
        h.setPmayg_status(cursor.getInt(cursor.getColumnIndex(PMAYG_STATUS)));
        h.setPmayg_id(cursor.getString(cursor.getColumnIndex(PMAYG_ID)));
        if(cursor.getInt(cursor.getColumnIndex(IS_HHD_ISSUED_HEALTH_CARD_UNDER_ABPMJAY)) == 0)
            h.setIs_hhd_issued_health_card_under_abpmjay(null);
        else
            h.setIs_hhd_issued_health_card_under_abpmjay(cursor.getInt(cursor.getColumnIndex(IS_HHD_ISSUED_HEALTH_CARD_UNDER_ABPMJAY))==1? true : false);
        h.setFamily_health_card_number(cursor.getString(cursor.getColumnIndex(FAMILY_HEALTH_CARD_NUMBER)));
        if(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_GETTING_PENSION_UNDER_NSAP)) == 0)
            h.setIs_any_member_getting_pension_under_nsap(null);
        else
            h.setIs_any_member_getting_pension_under_nsap(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_GETTING_PENSION_UNDER_NSAP))==1? true : false);
        h.setType_of_pensions(Common.convertStringToArrayListInt(cursor.getString(cursor.getColumnIndex(TYPE_OF_PENSIONS))));
        if(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_EVER_WORKED_UNDER_MGNREGA)) == 0)
            h.setIs_any_member_ever_worked_under_mgnrega(null);
        else
            h.setIs_any_member_ever_worked_under_mgnrega(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_EVER_WORKED_UNDER_MGNREGA))==1? true : false);
        h.setMgnrega_job_card_number(cursor.getString(cursor.getColumnIndex(MGNREGA_JOB_CARD_NUMBER)));
        h.setNo_of_days_worked_under_mgnrega_in_last_one_yr(cursor.getInt(cursor.getColumnIndex(NO_OF_DAYS_WORKED_UNDER_MGNREGA_IN_LAST_ONE_YR)));
        if(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_UNDERGONE_TRAINING_UNDER_ANY_SKILL_DEV_PROG)) == 0)
            h.setIs_any_member_undergone_training_under_any_skill_dev_prog(null);
        else
            h.setIs_any_member_undergone_training_under_any_skill_dev_prog(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_UNDERGONE_TRAINING_UNDER_ANY_SKILL_DEV_PROG))==1? true : false);
        h.setUndergone_skill_development_schemes(Common.convertStringToArrayListInt(cursor.getString(cursor.getColumnIndex(UNDERGONE_SKILL_DEVELOPMENT_SCHEMES))));
        if(cursor.getInt(cursor.getColumnIndex(IS_HHD_AVAILABLE_A_FUNCTIONAL_TOILET)) == 0)
            h.setIs_hhd_available_a_functional_toilet(null);
        else
            h.setIs_hhd_available_a_functional_toilet(cursor.getInt(cursor.getColumnIndex(IS_HHD_AVAILABLE_A_FUNCTIONAL_TOILET))==1? true : false);
        h.setMobile_numbers_hhd_members(Common.convertStringToArrayListString(cursor.getString(cursor.getColumnIndex(MOBILE_NUMBERS_HHD_MEMBERS))));
        if(cursor.getInt(cursor.getColumnIndex(IS_HHD_AVAILING_RATION_UNDER_NFSA_SCHEME)) == 0)
            h.setIs_hhd_availing_ration_under_nfsa_scheme(null);
        else
            h.setIs_hhd_availing_ration_under_nfsa_scheme(cursor.getInt(cursor.getColumnIndex(IS_HHD_AVAILING_RATION_UNDER_NFSA_SCHEME))==1? true : false);
        h.setNfsa_ration_card_type(cursor.getInt(cursor.getColumnIndex(NFSA_RATION_CARD_TYPE)));
        h.setNfsa_ration_card_number(cursor.getString(cursor.getColumnIndex(NFSA_RATION_CARD_NUMBER)));

        h.setHhd_latitude(cursor.getString(cursor.getColumnIndex(HHD_LATITUDE)));
        h.setHhd_longitude(cursor.getString(cursor.getColumnIndex(HHD_LONGITUDE)));

        h.setUser_id(cursor.getString(cursor.getColumnIndex(USER_ID)));
        h.setDevice_id(cursor.getString(cursor.getColumnIndex(DEVICE_ID)));
        h.setApp_id(cursor.getString(cursor.getColumnIndex(APP_ID)));
        h.setApp_version(cursor.getString(cursor.getColumnIndex(APP_VERSION)));
        h.setTs_updated(cursor.getString(cursor.getColumnIndex(TS_UPDATED)));
        h.setDt_created(cursor.getString(cursor.getColumnIndex(DT_CREATED)));
        h.setIs_completed(cursor.getInt(cursor.getColumnIndex(IS_COMPLETED)));
        h.setIs_synchronized(cursor.getInt(cursor.getColumnIndex(IS_SYNCHRONIZED)));
        h.setIs_uploaded(cursor.getInt(cursor.getColumnIndex(IS_UPLOADED)));
        h.setIs_verified(cursor.getInt(cursor.getColumnIndex(IS_VERIFIED)));
        h.setDt_sync(cursor.getString(cursor.getColumnIndex(DT_SYNC)));
        h.setTs_verified(cursor.getString(cursor.getColumnIndex(TS_VERIFIED)));

        h.setEnumerated_by(cursor.getString(cursor.getColumnIndex(ENUMERATED_BY)));


        return h;
    }

    private static HouseholdEolEnumerated getHouseholdEnumeratedFromCursor(Cursor cursor)
    {
        HouseholdEolEnumerated h = new HouseholdEolEnumerated();
        h.setState_code(cursor.getInt(cursor.getColumnIndex(	STATE_CODE	)));
        h.setState_name(cursor.getString(cursor.getColumnIndex(	STATE_NAME	)));
        h.setState_name_sl(cursor.getString(cursor.getColumnIndex(	STATE_NAME_SL	)));
        h.setDistrict_code(cursor.getInt(cursor.getColumnIndex(	DISTRICT_CODE	)));
        h.setDistrict_name(cursor.getString(cursor.getColumnIndex(	DISTRICT_NAME	)));
        h.setDistrict_name_sl(cursor.getString(cursor.getColumnIndex(	DISTRICT_NAME_SL	)));
        h.setSub_district_code(cursor.getInt(cursor.getColumnIndex(	SUB_DISTRICT_CODE	)));
        h.setSub_district_name(cursor.getString(cursor.getColumnIndex(	SUB_DISTRICT_NAME	)));
        h.setSub_district_name_sl(cursor.getString(cursor.getColumnIndex(	SUB_DISTRICT_NAME_SL	)));
        h.setBlock_code(cursor.getInt(cursor.getColumnIndex(	BLOCK_CODE	)));
        h.setBlock_name(cursor.getString(cursor.getColumnIndex(	BLOCK_NAME	)));
        h.setBlock_name_sl(cursor.getString(cursor.getColumnIndex(	BLOCK_NAME_SL	)));
        h.setGp_code(cursor.getInt(cursor.getColumnIndex(	GP_CODE	)));
        h.setGp_name(cursor.getString(cursor.getColumnIndex(	GP_NAME	)));
        h.setGp_name_sl(cursor.getString(cursor.getColumnIndex(	GP_NAME_SL	)));
        h.setVillage_code(cursor.getInt(cursor.getColumnIndex(	VILLAGE_CODE	)));
        h.setVillage_name(cursor.getString(cursor.getColumnIndex(	VILLAGE_NAME	)));
        h.setVillage_name_sl(cursor.getString(cursor.getColumnIndex(	VILLAGE_NAME_SL	)));
        h.setEnum_block_code(cursor.getInt(cursor.getColumnIndex(	ENUM_BLOCK_CODE	)));
        h.setEnum_block_name(cursor.getString(cursor.getColumnIndex(	ENUM_BLOCK_NAME	)));
        h.setEnum_block_name_sl(cursor.getString(cursor.getColumnIndex(	ENUM_BLOCK_NAME_SL	)));
        h.setHhd_uid(cursor.getInt(cursor.getColumnIndex(HHD_UID)));
        h.setIs_uncovered(cursor.getInt(cursor.getColumnIndex(IS_UNCOVERED)) == 1? true:false);
        h.setUncovered_reason_code(cursor.getInt(cursor.getColumnIndex(UNCOVERED_REASON_CODE)));
        h.setUncovered_reason(cursor.getString(cursor.getColumnIndex(UNCOVERED_REASON)));

        h.setStatecode(cursor.getString(cursor.getColumnIndex(STATECODE)));
        h.setDistrictcode(cursor.getString(cursor.getColumnIndex(DISTRICTCODE)));
        h.setTehsilcode(cursor.getString(cursor.getColumnIndex(TEHSILCODE)));
        h.setTowncode(cursor.getString(cursor.getColumnIndex(TOWNCODE)));
        h.setWardid(cursor.getString(cursor.getColumnIndex(WARDID)));
        h.setAhlblockno(cursor.getString(cursor.getColumnIndex(AHLBLOCKNO)));
        h.setAhlsubblockno(cursor.getString(cursor.getColumnIndex(AHLSUBBLOCKNO)));
        h.setAhl_family_tin(cursor.getString(cursor.getColumnIndex(AHL_FAMILY_TIN)));

        if(cursor.getInt(cursor.getColumnIndex(IS_LPG_CONNECTION_AVAILABLE)) == 0)
            h.setIs_lpg_connection_available(null);
        else
            h.setIs_lpg_connection_available(cursor.getInt(cursor.getColumnIndex(IS_LPG_CONNECTION_AVAILABLE))==1? true : false);
        h.setLpg_consumer_id(cursor.getString(cursor.getColumnIndex(LPG_CONSUMER_ID)));
        h.setNo_of_times_refilled_in_last_one_yr(cursor.getInt(cursor.getColumnIndex(NO_OF_TIMES_REFILLED_IN_LAST_ONE_YR)));
        if(cursor.getInt(cursor.getColumnIndex(IS_ELECTRICITY_CONNECTION_AVAILABLE)) == 0)
            h.setIs_electricity_connection_available(null);
        else
            h.setIs_electricity_connection_available(cursor.getInt(cursor.getColumnIndex(IS_ELECTRICITY_CONNECTION_AVAILABLE))==1? true : false);
        if(cursor.getInt(cursor.getColumnIndex(IS_LED_AVAILABLE_UNDER_UJALA)) == 0)
            h.setIs_led_available_under_ujala(null);
        else
            h.setIs_led_available_under_ujala(cursor.getInt(cursor.getColumnIndex(IS_LED_AVAILABLE_UNDER_UJALA))==1? true : false);
        if(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_HAVE_JANDHAN_AC)) == 0)
            h.setIs_any_member_have_jandhan_ac(null);
        else
            h.setIs_any_member_have_jandhan_ac(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_HAVE_JANDHAN_AC))==1? true : false);
        if(cursor.getInt(cursor.getColumnIndex(IS_HHD_ENROLLED_IN_PMJJBY)) == 0)
            h.setIs_hhd_enrolled_in_pmjjby(null);
        else
            h.setIs_hhd_enrolled_in_pmjjby(cursor.getInt(cursor.getColumnIndex(IS_HHD_ENROLLED_IN_PMJJBY))==1? true : false);
        if(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_ENROLLED_IN_PMSBY)) == 0)
            h.setIs_any_member_enrolled_in_pmsby(null);
        else
            h.setIs_any_member_enrolled_in_pmsby(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_ENROLLED_IN_PMSBY))==1? true : false);
        if(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_IMMUNISED_UNDER_MISSION_INDRADHANUSH)) == 0)
            h.setIs_any_member_immunised_under_mission_indradhanush(null);
        else
            h.setIs_any_member_immunised_under_mission_indradhanush(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_IMMUNISED_UNDER_MISSION_INDRADHANUSH))==1? true : false);
        if(cursor.getInt(cursor.getColumnIndex(IS_ANY_CHILD_0_6_YRS_OR_PREGNANT_WOMAN_AVAILABLE)) == 0)
            h.setIs_any_child_0_6_yrs_or_pregnant_woman_available(null);
        else
            h.setIs_any_child_0_6_yrs_or_pregnant_woman_available(cursor.getInt(cursor.getColumnIndex(IS_ANY_CHILD_0_6_YRS_OR_PREGNANT_WOMAN_AVAILABLE))==1? true : false);
        if(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_REGISTERED_IN_ICDS)) == 0)
            h.setIs_any_member_registered_in_icds(null);
        else
            h.setIs_any_member_registered_in_icds(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_REGISTERED_IN_ICDS))==1? true : false);
        if(cursor.getInt(cursor.getColumnIndex(IS_HHD_AVAILED_NUTRITION_BENEFITS_UNDER_ICDS)) == 0)
            h.setIs_hhd_availed_nutrition_benefits_under_icds(null);
        else
            h.setIs_hhd_availed_nutrition_benefits_under_icds(cursor.getInt(cursor.getColumnIndex(IS_HHD_AVAILED_NUTRITION_BENEFITS_UNDER_ICDS))==1? true : false);
        h.setServices_availed_under_icds(Common.convertStringToArrayListInt(cursor.getString(cursor.getColumnIndex(SERVICES_AVAILED_UNDER_ICDS))));
        if(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_OF_HHD_IS_MEMBER_OF_SHG)) == 0)
            h.setIs_any_member_of_hhd_is_member_of_shg(null);
        else
            h.setIs_any_member_of_hhd_is_member_of_shg(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_OF_HHD_IS_MEMBER_OF_SHG))==1? true : false);
        h.setType_of_house_used_for_living(cursor.getInt(cursor.getColumnIndex(TYPE_OF_HOUSE_USED_FOR_LIVING)));
        if(cursor.getInt(cursor.getColumnIndex(IS_HHD_A_BENEFICIARY_OF_PMAYG)) == 0)
            h.setIs_hhd_a_beneficiary_of_pmayg(null);
        else
            h.setIs_hhd_a_beneficiary_of_pmayg(cursor.getInt(cursor.getColumnIndex(IS_HHD_A_BENEFICIARY_OF_PMAYG))==1? true : false);
        h.setPmayg_status(cursor.getInt(cursor.getColumnIndex(PMAYG_STATUS)));
        h.setPmayg_id(cursor.getString(cursor.getColumnIndex(PMAYG_ID)));
        if(cursor.getInt(cursor.getColumnIndex(IS_HHD_ISSUED_HEALTH_CARD_UNDER_ABPMJAY)) == 0)
            h.setIs_hhd_issued_health_card_under_abpmjay(null);
        else
            h.setIs_hhd_issued_health_card_under_abpmjay(cursor.getInt(cursor.getColumnIndex(IS_HHD_ISSUED_HEALTH_CARD_UNDER_ABPMJAY))==1? true : false);
        h.setFamily_health_card_number(cursor.getString(cursor.getColumnIndex(FAMILY_HEALTH_CARD_NUMBER)));
        if(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_GETTING_PENSION_UNDER_NSAP)) == 0)
            h.setIs_any_member_getting_pension_under_nsap(null);
        else
            h.setIs_any_member_getting_pension_under_nsap(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_GETTING_PENSION_UNDER_NSAP))==1? true : false);
        h.setType_of_pensions(Common.convertStringToArrayListInt(cursor.getString(cursor.getColumnIndex(TYPE_OF_PENSIONS))));
        if(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_EVER_WORKED_UNDER_MGNREGA)) == 0)
            h.setIs_any_member_ever_worked_under_mgnrega(null);
        else
            h.setIs_any_member_ever_worked_under_mgnrega(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_EVER_WORKED_UNDER_MGNREGA))==1? true : false);
        h.setMgnrega_job_card_number(cursor.getString(cursor.getColumnIndex(MGNREGA_JOB_CARD_NUMBER)));
        h.setNo_of_days_worked_under_mgnrega_in_last_one_yr(cursor.getInt(cursor.getColumnIndex(NO_OF_DAYS_WORKED_UNDER_MGNREGA_IN_LAST_ONE_YR)));
        if(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_UNDERGONE_TRAINING_UNDER_ANY_SKILL_DEV_PROG)) == 0)
            h.setIs_any_member_undergone_training_under_any_skill_dev_prog(null);
        else
            h.setIs_any_member_undergone_training_under_any_skill_dev_prog(cursor.getInt(cursor.getColumnIndex(IS_ANY_MEMBER_UNDERGONE_TRAINING_UNDER_ANY_SKILL_DEV_PROG))==1? true : false);
        h.setUndergone_skill_development_schemes(Common.convertStringToArrayListInt(cursor.getString(cursor.getColumnIndex(UNDERGONE_SKILL_DEVELOPMENT_SCHEMES))));
        if(cursor.getInt(cursor.getColumnIndex(IS_HHD_AVAILABLE_A_FUNCTIONAL_TOILET)) == 0)
            h.setIs_hhd_available_a_functional_toilet(null);
        else
            h.setIs_hhd_available_a_functional_toilet(cursor.getInt(cursor.getColumnIndex(IS_HHD_AVAILABLE_A_FUNCTIONAL_TOILET))==1? true : false);
        h.setMobile_numbers_hhd_members(Common.convertStringToArrayListString(cursor.getString(cursor.getColumnIndex(MOBILE_NUMBERS_HHD_MEMBERS))));
        if(cursor.getInt(cursor.getColumnIndex(IS_HHD_AVAILING_RATION_UNDER_NFSA_SCHEME)) == 0)
            h.setIs_hhd_availing_ration_under_nfsa_scheme(null);
        else
            h.setIs_hhd_availing_ration_under_nfsa_scheme(cursor.getInt(cursor.getColumnIndex(IS_HHD_AVAILING_RATION_UNDER_NFSA_SCHEME))==1? true : false);
        h.setNfsa_ration_card_type(cursor.getInt(cursor.getColumnIndex(NFSA_RATION_CARD_TYPE)));
        h.setNfsa_ration_card_number(cursor.getString(cursor.getColumnIndex(NFSA_RATION_CARD_NUMBER)));

        h.setHhd_latitude(cursor.getString(cursor.getColumnIndex(HHD_LATITUDE)));
        h.setHhd_longitude(cursor.getString(cursor.getColumnIndex(HHD_LONGITUDE)));

        h.setUser_id(cursor.getString(cursor.getColumnIndex(USER_ID)));
        h.setDevice_id(cursor.getString(cursor.getColumnIndex(DEVICE_ID)));
        h.setApp_id(cursor.getString(cursor.getColumnIndex(APP_ID)));
        h.setApp_version(cursor.getString(cursor.getColumnIndex(APP_VERSION)));
        h.setTs_updated(cursor.getString(cursor.getColumnIndex(TS_UPDATED)));
        return h;
    }

    private static ContentValues setHouseholdToContentValues(HouseholdEol h)
    {
        ContentValues values = new ContentValues();
        values.put(STATE_CODE, h.getState_code());
        values.put(STATE_NAME, h.getState_name());
        values.put(STATE_NAME_SL, h.getState_name_sl());
        values.put(DISTRICT_CODE, h.getDistrict_code());
        values.put(DISTRICT_NAME, h.getDistrict_name());
        values.put(DISTRICT_NAME_SL, h.getDistrict_name_sl());
        values.put(SUB_DISTRICT_CODE, h.getSub_district_code());
        values.put(SUB_DISTRICT_NAME, h.getSub_district_name());
        values.put(SUB_DISTRICT_NAME_SL, h.getSub_district_name_sl());
        values.put(BLOCK_CODE, h.getBlock_code());
        values.put(BLOCK_NAME, h.getBlock_name());
        values.put(BLOCK_NAME_SL, h.getBlock_name_sl());
        values.put(GP_CODE, h.getGp_code());
        values.put(GP_NAME, h.getGp_name());
        values.put(GP_NAME_SL, h.getGp_name_sl());
        values.put(VILLAGE_CODE, h.getVillage_code());
        values.put(VILLAGE_NAME, h.getVillage_name());
        values.put(VILLAGE_NAME_SL, h.getVillage_name_sl());
        values.put(ENUM_BLOCK_CODE, h.getEnum_block_code());
        values.put(ENUM_BLOCK_NAME, h.getEnum_block_name());
        values.put(ENUM_BLOCK_NAME_SL, h.getEnum_block_name_sl());
        values.put(HHD_UID, h.getHhd_uid());
        values.put(IS_UNCOVERED, h.getIs_uncovered()? 1:0);
        values.put(UNCOVERED_REASON_CODE, h.getUncovered_reason_code());
        values.put(UNCOVERED_REASON, h.getUncovered_reason());

        values.put(STATECODE, h.getStatecode());
        values.put(DISTRICTCODE, h.getDistrictcode());
        values.put(TEHSILCODE, h.getTehsilcode());
        values.put(TOWNCODE, h.getTowncode());
        values.put(WARDID, h.getWardid());
        values.put(AHLBLOCKNO, h.getAhlblockno());
        values.put(AHLSUBBLOCKNO, h.getAhlsubblockno());
        values.put(AHL_FAMILY_TIN, h.getAhl_family_tin());

        if(h.getIs_lpg_connection_available() == null)
            values.put(IS_LPG_CONNECTION_AVAILABLE, 0);
        else values.put(IS_LPG_CONNECTION_AVAILABLE, h.getIs_lpg_connection_available()? 1:2);
        values.put(LPG_CONSUMER_ID, h.getLpg_consumer_id());
        values.put(NO_OF_TIMES_REFILLED_IN_LAST_ONE_YR, h.getNo_of_times_refilled_in_last_one_yr());
        if(h.getIs_electricity_connection_available() == null)
            values.put(IS_ELECTRICITY_CONNECTION_AVAILABLE, 0);
        else values.put(IS_ELECTRICITY_CONNECTION_AVAILABLE, h.getIs_electricity_connection_available()? 1:2);
        if(h.getIs_led_available_under_ujala() == null)
            values.put(IS_LED_AVAILABLE_UNDER_UJALA, 0);
        else values.put(IS_LED_AVAILABLE_UNDER_UJALA, h.getIs_led_available_under_ujala()? 1:2);
        if(h.getIs_any_member_have_jandhan_ac() == null)
            values.put(IS_ANY_MEMBER_HAVE_JANDHAN_AC, 0);
        else values.put(IS_ANY_MEMBER_HAVE_JANDHAN_AC, h.getIs_any_member_have_jandhan_ac()? 1:2);
        if(h.getIs_hhd_enrolled_in_pmjjby() == null)
            values.put(IS_HHD_ENROLLED_IN_PMJJBY, 0);
        else values.put(IS_HHD_ENROLLED_IN_PMJJBY, h.getIs_hhd_enrolled_in_pmjjby()? 1:2);
        if(h.getIs_any_member_enrolled_in_pmsby() == null)
            values.put(IS_ANY_MEMBER_ENROLLED_IN_PMSBY, 0);
        else values.put(IS_ANY_MEMBER_ENROLLED_IN_PMSBY, h.getIs_any_member_enrolled_in_pmsby()? 1:2);
        if(h.getIs_any_member_immunised_under_mission_indradhanush() == null)
            values.put(IS_ANY_MEMBER_IMMUNISED_UNDER_MISSION_INDRADHANUSH, 0);
        else values.put(IS_ANY_MEMBER_IMMUNISED_UNDER_MISSION_INDRADHANUSH, h.getIs_any_member_immunised_under_mission_indradhanush()? 1:2);
        if(h.getIs_any_child_0_6_yrs_or_pregnant_woman_available() == null)
            values.put(IS_ANY_CHILD_0_6_YRS_OR_PREGNANT_WOMAN_AVAILABLE, 0);
        else values.put(IS_ANY_CHILD_0_6_YRS_OR_PREGNANT_WOMAN_AVAILABLE, h.getIs_any_child_0_6_yrs_or_pregnant_woman_available()? 1:2);
        if(h.getIs_any_member_registered_in_icds() == null)
            values.put(IS_ANY_MEMBER_REGISTERED_IN_ICDS, 0);
        else values.put(IS_ANY_MEMBER_REGISTERED_IN_ICDS, h.getIs_any_member_registered_in_icds()? 1:2);
        if(h.getIs_hhd_availed_nutrition_benefits_under_icds() == null)
            values.put(IS_HHD_AVAILED_NUTRITION_BENEFITS_UNDER_ICDS, 0);
        else values.put(IS_HHD_AVAILED_NUTRITION_BENEFITS_UNDER_ICDS, h.getIs_hhd_availed_nutrition_benefits_under_icds()? 1:2);
        values.put(SERVICES_AVAILED_UNDER_ICDS, Common.joinIntegerToString(h.getServices_availed_under_icds()));
        if(h.getIs_any_member_of_hhd_is_member_of_shg() == null)
            values.put(IS_ANY_MEMBER_OF_HHD_IS_MEMBER_OF_SHG, 0);
        else values.put(IS_ANY_MEMBER_OF_HHD_IS_MEMBER_OF_SHG, h.getIs_any_member_of_hhd_is_member_of_shg()? 1:2);
        values.put(TYPE_OF_HOUSE_USED_FOR_LIVING, h.getType_of_house_used_for_living());
        if(h.getIs_hhd_a_beneficiary_of_pmayg() == null)
            values.put(IS_HHD_A_BENEFICIARY_OF_PMAYG, 0);
        else values.put(IS_HHD_A_BENEFICIARY_OF_PMAYG, h.getIs_hhd_a_beneficiary_of_pmayg()? 1:2);
        values.put(PMAYG_STATUS, h.getPmayg_status());
        values.put(PMAYG_ID, h.getPmayg_id());
        if(h.getIs_hhd_issued_health_card_under_abpmjay() == null)
            values.put(IS_HHD_ISSUED_HEALTH_CARD_UNDER_ABPMJAY, 0);
        else values.put(IS_HHD_ISSUED_HEALTH_CARD_UNDER_ABPMJAY, h.getIs_hhd_issued_health_card_under_abpmjay()? 1:2);
        values.put(FAMILY_HEALTH_CARD_NUMBER, h.getFamily_health_card_number());
        if(h.getIs_any_member_getting_pension_under_nsap() == null)
            values.put(IS_ANY_MEMBER_GETTING_PENSION_UNDER_NSAP, 0);
        else values.put(IS_ANY_MEMBER_GETTING_PENSION_UNDER_NSAP, h.getIs_any_member_getting_pension_under_nsap()? 1:2);
        values.put(TYPE_OF_PENSIONS, Common.joinIntegerToString(h.getType_of_pensions()));
        if(h.getIs_any_member_ever_worked_under_mgnrega() == null)
            values.put(IS_ANY_MEMBER_EVER_WORKED_UNDER_MGNREGA, 0);
        else values.put(IS_ANY_MEMBER_EVER_WORKED_UNDER_MGNREGA, h.getIs_any_member_ever_worked_under_mgnrega()? 1:2);
        values.put(MGNREGA_JOB_CARD_NUMBER, h.getMgnrega_job_card_number());
        values.put(NO_OF_DAYS_WORKED_UNDER_MGNREGA_IN_LAST_ONE_YR, h.getNo_of_days_worked_under_mgnrega_in_last_one_yr());
        if(h.getIs_any_member_undergone_training_under_any_skill_dev_prog() == null)
            values.put(IS_ANY_MEMBER_UNDERGONE_TRAINING_UNDER_ANY_SKILL_DEV_PROG, 0);
        else values.put(IS_ANY_MEMBER_UNDERGONE_TRAINING_UNDER_ANY_SKILL_DEV_PROG, h.getIs_any_member_undergone_training_under_any_skill_dev_prog()? 1:2);
        values.put(UNDERGONE_SKILL_DEVELOPMENT_SCHEMES, Common.joinIntegerToString(h.getUndergone_skill_development_schemes()));
        if(h.getIs_hhd_available_a_functional_toilet() == null)
            values.put(IS_HHD_AVAILABLE_A_FUNCTIONAL_TOILET, 0);
        else values.put(IS_HHD_AVAILABLE_A_FUNCTIONAL_TOILET, h.getIs_hhd_available_a_functional_toilet()? 1:2);
        values.put(MOBILE_NUMBERS_HHD_MEMBERS, Common.joinString(h.getMobile_numbers_hhd_members()));
        if(h.getIs_hhd_availing_ration_under_nfsa_scheme() == null)
            values.put(IS_HHD_AVAILING_RATION_UNDER_NFSA_SCHEME, 0);
        else values.put(IS_HHD_AVAILING_RATION_UNDER_NFSA_SCHEME, h.getIs_hhd_availing_ration_under_nfsa_scheme()? 1:2);
        values.put(NFSA_RATION_CARD_TYPE, h.getNfsa_ration_card_type());
        values.put(NFSA_RATION_CARD_NUMBER, h.getNfsa_ration_card_number());

        values.put(HHD_LATITUDE, h.getHhd_latitude());
        values.put(HHD_LONGITUDE, h.getHhd_longitude());

        values.put(USER_ID, h.getUser_id());
        values.put(DEVICE_ID, h.getDevice_id());
        values.put(APP_ID, h.getApp_id());
        values.put(APP_VERSION, h.getApp_version());
        values.put(TS_UPDATED, h.getTs_updated());
        values.put(DT_CREATED, h.getDt_created());
        values.put(IS_COMPLETED, h.getIs_completed());
        values.put(IS_SYNCHRONIZED, h.getIs_synchronized());
        values.put(IS_UPLOADED, h.getIs_uploaded());
        values.put(IS_VERIFIED, h.getIs_verified());
        values.put(DT_SYNC, h.getDt_sync());
        values.put(TS_VERIFIED, h.getTs_verified());

        values.put(ENUMERATED_BY, h.getEnumerated_by());
        return values;
    }

    public static boolean insertHhd(Context ctx, SQLiteDatabase db, HouseholdEol h) {
        ContentValues values = new ContentValues();
        ArrayList<String> al_columnNames = SQLiteHelper.checkTableColumns(ctx, db,TABLE_NAME);

        long row = -1;
        try {
            db.beginTransaction();
            values = setHouseholdToContentValues(h);
            row = db.insert(TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        }catch (Exception e)
        {
            row = -1;
        }
        finally
        {
            db.endTransaction();
        }
        return row >= 0;

    }

    public static boolean updateHhd(Context ctx, SQLiteDatabase db, HouseholdEol h) {
        long row = 0;
        try {

            db.beginTransaction();
            ContentValues values = new ContentValues();
            values = setHouseholdToContentValues(h);
            row = db.update(TABLE_NAME, values,  HHD_UID + " =?",
                    new String[]{String.valueOf(h.getHhd_uid())});
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
            /*String query = "select  (max(a.hhd_sl_no) + 1) as " + HHD_SL_NO +
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
                huid=1;*/
        }catch (Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.error), e.getMessage(), "031-001");
        }
        return huid;
    }

    public static ArrayList<HouseholdEol> getDataByHhd(Context ctx, SQLiteDatabase db, int iHhdUid) {
        ArrayList<HouseholdEol> list = new ArrayList<>();
        try {
            String strSelectQuery = "select * from " + TABLE_NAME + " where " + HHD_UID + " =? ";
            Cursor cursor = db.rawQuery(strSelectQuery, new String[]{String.valueOf(iHhdUid)});
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    HouseholdEol h = getHouseholdFromCursor(cursor);

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

    public static ArrayList<HouseholdEol> getData(Context ctx, SQLiteDatabase db) {
        ArrayList<HouseholdEol> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                HouseholdEol h = getHouseholdFromCursor(cursor);

                list.add(h);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public static ArrayList<HouseholdEol> getDataWithSeccHhd(Context ctx, SQLiteDatabase db) {
        ArrayList<HouseholdEol> list = new ArrayList<>();
        String strSelectQuery = "Select state_code, state_name, state_name_sl, district_code, district_name, " +
                "       district_name_sl, sub_district_code, sub_district_name, sub_district_name_sl, " +
                "       block_code, block_name, block_name_sl, gp_code, gp_name, gp_name_sl, " +
                "       village_code, village_name, village_name_sl, enum_block_code, " +
                "       enum_block_name, enum_block_name_sl, hhd_uid, is_uncovered, uncovered_reason_code, " +
                "       uncovered_reason, statecode, districtcode, tehsilcode, towncode, " +
                "       wardid, ahlblockno, ahlsubblockno, ahl_family_tin, is_lpg_connection_available, " +
                "       lpg_consumer_id, no_of_times_refilled_in_last_one_yr, is_electricity_connection_available, " +
                "       is_led_available_under_ujala, is_any_member_have_jandhan_ac, " +
                "       is_hhd_enrolled_in_pmjjby, is_any_member_enrolled_in_pmsby, is_any_member_immunised_under_mission_indradhanush, " +
                "       is_any_child_0_6_yrs_or_pregnant_woman_available, is_any_member_registered_in_icds, " +
                "       is_hhd_availed_nutrition_benefits_under_icds, services_availed_under_icds, " +
                "       is_any_member_of_hhd_is_member_of_shg, type_of_house_used_for_living, " +
                "       is_hhd_a_beneficiary_of_pmayg, pmayg_status, pmayg_id, is_hhd_issued_health_card_under_abpmjay, " +
                "       family_health_card_number, is_any_member_getting_pension_under_nsap, " +
                "       type_of_pensions, is_any_member_ever_worked_under_mgnrega, mgnrega_job_card_number, " +
                "       no_of_days_worked_under_mgnrega_in_last_one_yr, is_any_member_undergone_training_under_any_skill_dev_prog, " +
                "       undergone_skill_development_schemes, is_hhd_available_a_functional_toilet, " +
                "       mobile_numbers_hhd_members, is_hhd_availing_ration_under_nfsa_scheme, " +
                "       nfsa_ration_card_type, nfsa_ration_card_number, hhd_latitude, " +
                "       hhd_longitude, user_id, device_id, app_id, app_version, ts_updated," +
                "dt_created, is_completed, is_synchronized, is_uploaded, is_verified, dt_sync, ts_verified, enumerated_by from household_eol_status" +
                " union" +
                " select state_code, state_name, state_name_sl, district_code, district_name, " +
                "       district_name_sl, sub_district_code, sub_district_name, sub_district_name_sl, " +
                "       block_code, block_name, block_name_sl, gp_code, gp_name, gp_name_sl, " +
                "       village_code, village_name, village_name_sl, enum_block_code, " +
                "       enum_block_name, enum_block_name_sl, hhd_uid, '', null, " +
                "       'not_started', statecode, districtcode, tehsilcode, towncode, " +
                "       wardid, ahlblockno, ahlsubblockno, ahl_family_tin, null, " +
                "       null, null, null, null, null, null, null, null, null, null, null, null, null, null, " +
                "       null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, " +
                "       null, null, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '' " +
                "   from secc_household a where a.hhd_uid not in (select hhd_uid from household_eol_status)";
        Cursor cursor = db.rawQuery(strSelectQuery, null);
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                HouseholdEol h = getHouseholdFromCursor(cursor);

                list.add(h);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public static ArrayList<HouseholdEol> getHouseholdStatusList(Context ctx, SQLiteDatabase db) {
        ArrayList<HouseholdEol> list = new ArrayList<>();
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
                HouseholdEol h = getHouseholdFromCursor(cursor);
                list.add(h);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public static boolean updateHhdList(Context ctx, SQLiteDatabase db, ArrayList<HouseholdEol> alHhd) {
        ContentValues values = new ContentValues();
        long row = 0;
        try {

            db.beginTransaction();
            for (HouseholdEol h : alHhd) {
                values = setHouseholdToContentValues(h);

                row = db.update(TABLE_NAME, values, HHD_UID + " = ?",
                        new String[]{String.valueOf(h.getHhd_uid())});
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


    public static int isHhdAvailable(SQLiteDatabase db, int iHhdUid) {
        int result=0;
        String Query = "Select count(1) as total_hhd from " + TABLE_NAME + " where trim(" + HHD_UID + ") = ?";
        Cursor cursor = db.rawQuery(Query, new String[]{String.valueOf(iHhdUid)});
        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                result=cursor.getInt(cursor.getColumnIndex("total_hhd"));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return result;
    }

    public static ArrayList<HouseholdEol> getAllInCompletedHhd(Context ctx, SQLiteDatabase db) {
        ArrayList<HouseholdEol> list = new ArrayList<>();
        try {
            String strSelectQuery = "select * from " + TABLE_NAME + " where " + IS_COMPLETED + " = ?";
            Cursor cursor = db.rawQuery(strSelectQuery, new String[]{"0"});

            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    HouseholdEol h = getHouseholdFromCursor(cursor);
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

    public static ArrayList<HouseholdEol> getAllCompletedHhd(Context ctx, SQLiteDatabase db) {
        ArrayList<HouseholdEol> list = new ArrayList<>();
        try {
            String strSelectQuery = "select * from " + TABLE_NAME + " where " + IS_COMPLETED + " = ?";
            Cursor cursor = db.rawQuery(strSelectQuery, new String[]{"1"});

            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    HouseholdEol h = getHouseholdFromCursor(cursor);
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

    public static ArrayList<HouseholdEol> getAllUnSyncedHhd(Context ctx, SQLiteDatabase db) {
        ArrayList<HouseholdEol> list = new ArrayList<>();
        String strSelectQuery = "select * from " + TABLE_NAME + " where " + IS_SYNCHRONIZED + " = ? AND " + IS_COMPLETED + " = ?";
        Cursor cursor = db.rawQuery(strSelectQuery, new String[]{"0","1"});

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                HouseholdEol h = getHouseholdFromCursor(cursor);
                list.add(h);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public static ArrayList<HouseholdEolEnumerated> getAllUnSyncedHhdToUpload(Context context, SQLiteDatabase db, Boolean isMarkComplete) {
        ArrayList<HouseholdEolEnumerated> list = new ArrayList<>();
        try {

            String strSelectQuery = "";
            Cursor cursor = null;
            if(isMarkComplete) {
                strSelectQuery = "select * from " + TABLE_NAME + " where " + IS_SYNCHRONIZED + " = ? and " + IS_COMPLETED + " = ?";
                cursor = db.rawQuery(strSelectQuery, new String[]{String.valueOf(0), String.valueOf(1)});
            }
            else
            {
                strSelectQuery = "select * from " + TABLE_NAME + " where " + IS_SYNCHRONIZED + " = ? and " + IS_COMPLETED + " = ? and " + IS_UNCOVERED + " <> ?";
                cursor = db.rawQuery(strSelectQuery, new String[]{String.valueOf(0), String.valueOf(1), String.valueOf(1)});
            }

            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    HouseholdEolEnumerated h = getHouseholdEnumeratedFromCursor(cursor);
                    list.add(h);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error,  context.getString(R.string.app_error), e.getMessage(),"022-010");
        }
        return list;
    }

    public static ArrayList<HouseholdEol> getAllUnSyncedHhdToUpdateUploadStatus(Context context, SQLiteDatabase db, Boolean isMarkComplete) {
        ArrayList<HouseholdEol> list = new ArrayList<>();
        try {

            String strSelectQuery = "";
            Cursor cursor = null;
            if(isMarkComplete) {
                strSelectQuery = "select * from " + TABLE_NAME + " where " + IS_SYNCHRONIZED + " = ? and " + IS_COMPLETED + " = ?";
                cursor = db.rawQuery(strSelectQuery, new String[]{String.valueOf(0), String.valueOf(1)});
            }
            else
            {
                strSelectQuery = "select * from " + TABLE_NAME + " where " + IS_SYNCHRONIZED + " = ? and " + IS_COMPLETED + " = ? and " + IS_UNCOVERED + " <> ?";
                cursor = db.rawQuery(strSelectQuery, new String[]{String.valueOf(0), String.valueOf(1), String.valueOf(1)});
            }

            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    HouseholdEol h = getHouseholdFromCursor(cursor);
                    list.add(h);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error,  context.getString(R.string.app_error), e.getMessage(),"022-010");
        }
        return list;
    }


    public static ArrayList<HouseholdEol> getAllSyncedHhd(Context ctx, SQLiteDatabase db) {
        ArrayList<HouseholdEol> list = new ArrayList<>();
        String strSelectQuery = "select * from " + TABLE_NAME + " where " + IS_SYNCHRONIZED + " = ? ";
        Cursor cursor = db.rawQuery(strSelectQuery,  new String[]{"1"});

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                HouseholdEol h = getHouseholdFromCursor(cursor);
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

    public static boolean deleteAll(Context context, SQLiteDatabase db) {
        int row = -1;
        try {

            row = db.delete(TABLE_NAME, null, null);
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"022-013");
        }
        return row > 0;
    }

}
