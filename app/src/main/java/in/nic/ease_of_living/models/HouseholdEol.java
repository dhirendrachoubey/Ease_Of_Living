package in.nic.ease_of_living.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Neha Jain on 20/11/2019.
 */
public class HouseholdEol implements Serializable {
    private Integer state_code;
    private String state_name;
    private String state_name_sl;
    private Integer district_code;
    private String district_name;
    private String district_name_sl;
    private Integer sub_district_code;
    private String sub_district_name;
    private String sub_district_name_sl;
    private Integer block_code;
    private String block_name;
    private String block_name_sl;
    private Integer gp_code;
    private String gp_name;
    private String gp_name_sl;
    private Integer village_code;
    private String village_name;
    private String village_name_sl;
    private Integer enum_block_code;
    private String enum_block_name;
    private String enum_block_name_sl;
    private Integer hhd_uid;

    private Boolean is_uncovered;
    private Integer uncovered_reason_code;
    private String uncovered_reason;

    private String statecode;
    private String districtcode;
    private String tehsilcode;
    private String towncode;
    private String wardid;
    private String ahlblockno;
    private String ahlsubblockno;
    private String ahl_family_tin;

    private Boolean is_lpg_connection_available;
    private String lpg_consumer_id;
    private Integer no_of_times_refilled_in_last_one_yr;
    private Boolean is_electricity_connection_available;
    private Boolean is_led_available_under_ujala;
    private Boolean is_any_member_have_jandhan_ac;
    private Boolean is_hhd_enrolled_in_pmjjby;
    private Boolean is_any_member_enrolled_in_pmsby;
    private Boolean is_any_member_immunised_under_mission_indradhanush;
    private Boolean is_any_child_0_6_yrs_or_pregnant_woman_available;
    private Boolean is_any_member_registered_in_icds;
    private Boolean is_hhd_availed_nutrition_benefits_under_icds;
    private ArrayList<Integer> services_availed_under_icds;
    private Boolean is_any_member_of_hhd_is_member_of_shg;
    private Integer type_of_house_used_for_living;
    private Boolean is_hhd_a_beneficiary_of_pmayg;
    private Integer pmayg_status;
    private String pmayg_id;
    private Boolean is_hhd_issued_health_card_under_abpmjay;
    private String family_health_card_number;
    private Boolean is_any_member_getting_pension_under_nsap;
    private ArrayList<Integer> type_of_pensions;
    private Boolean is_any_member_ever_worked_under_mgnrega;
    private String mgnrega_job_card_number;
    private Integer no_of_days_worked_under_mgnrega_in_last_one_yr;
    private Boolean is_any_member_undergone_training_under_any_skill_dev_prog;
    private ArrayList<Integer> undergone_skill_development_schemes;
    private Boolean is_hhd_available_a_functional_toilet;
    private ArrayList<String> mobile_numbers_hhd_members;
    private Boolean is_hhd_availing_ration_under_nfsa_scheme;
    private Integer nfsa_ration_card_type;
    private String nfsa_ration_card_number;

    private String hhd_latitude;
    private String hhd_longitude;

    private String user_id;
    private String device_id;
    private String app_id;
    private String app_version;
    private String ts_updated;
    private String dt_created;
    private Integer is_completed;
    private Integer is_synchronized;
    private Integer is_uploaded;
    private Integer is_verified;
    private String dt_sync;
    private String ts_verified;
    private Boolean is_new_hhd;
    private String enumerated_by;

    public HouseholdEol() {
        this.state_code = null;
        this.state_name = null;
        this.state_name_sl = null;
        this.district_code = null;
        this.district_name = null;
        this.district_name_sl = null;
        this.sub_district_code = null;
        this.sub_district_name = null;
        this.sub_district_name_sl = null;
        this.block_code = null;
        this.block_name = null;
        this.block_name_sl = null;
        this.gp_code = null;
        this.gp_name = null;
        this.gp_name_sl = null;
        this.village_code = null;
        this.village_name = null;
        this.village_name_sl = null;
        this.enum_block_code = null;
        this.enum_block_name = null;
        this.enum_block_name_sl = null;
        this.hhd_uid = null;
        this.is_uncovered = false;
        this.uncovered_reason_code = 0;
        this.uncovered_reason = "";
        this.statecode = null;
        this.districtcode = null;
        this.tehsilcode = null;
        this.towncode = null;
        this.wardid = null;
        this.ahlblockno = null;
        this.ahlsubblockno = null;
        this.ahl_family_tin = null;
        this.is_lpg_connection_available = null;
        this.lpg_consumer_id = null;
        this.no_of_times_refilled_in_last_one_yr = null;
        this.is_electricity_connection_available = null;
        this.is_led_available_under_ujala = null;
        this.is_any_member_have_jandhan_ac = null;
        this.is_hhd_enrolled_in_pmjjby = null;
        this.is_any_member_enrolled_in_pmsby = null;
        this.is_any_member_immunised_under_mission_indradhanush = null;
        this.is_any_child_0_6_yrs_or_pregnant_woman_available = null;
        this.is_any_member_registered_in_icds = null;
        this.is_hhd_availed_nutrition_benefits_under_icds = null;
        this.services_availed_under_icds = null;
        this.is_any_member_of_hhd_is_member_of_shg = null;
        this.type_of_house_used_for_living = 0;
        this.is_hhd_a_beneficiary_of_pmayg = null;
        this.pmayg_status = null;
        this.pmayg_id = null;
        this.is_hhd_issued_health_card_under_abpmjay = null;
        this.family_health_card_number = null;
        this.is_any_member_getting_pension_under_nsap = null;
        this.type_of_pensions = null;
        this.is_any_member_ever_worked_under_mgnrega = null;
        this.mgnrega_job_card_number = null;
        this.no_of_days_worked_under_mgnrega_in_last_one_yr = null;
        this.is_any_member_undergone_training_under_any_skill_dev_prog = null;
        this.undergone_skill_development_schemes = null;
        this.is_hhd_available_a_functional_toilet = null;
        this.mobile_numbers_hhd_members = null;
        this.is_hhd_availing_ration_under_nfsa_scheme = null;
        this.nfsa_ration_card_type = null;
        this.nfsa_ration_card_number = null;
        this.hhd_latitude = null;
        this.hhd_longitude = null;
        this.user_id = null;
        this.device_id = null;
        this.app_id = null;
        this.app_version = null;
        this.ts_updated = null;
        this.dt_created = null;
        this.is_completed = null;
        this.is_synchronized = null;
        this.is_uploaded = null;
        this.is_verified = null;
        this.dt_sync = null;
        this.ts_verified = null;
        this.is_new_hhd = null;
        this.enumerated_by = null;
    }

    public Integer getState_code() {
        return state_code;
    }

    public void setState_code(Integer state_code) {
        this.state_code = state_code;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getState_name_sl() {
        return state_name_sl;
    }

    public void setState_name_sl(String state_name_sl) {
        this.state_name_sl = state_name_sl;
    }

    public Integer getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(Integer district_code) {
        this.district_code = district_code;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getDistrict_name_sl() {
        return district_name_sl;
    }

    public void setDistrict_name_sl(String district_name_sl) {
        this.district_name_sl = district_name_sl;
    }

    public Integer getSub_district_code() {
        return sub_district_code;
    }

    public void setSub_district_code(Integer sub_district_code) {
        this.sub_district_code = sub_district_code;
    }

    public String getSub_district_name() {
        return sub_district_name;
    }

    public void setSub_district_name(String sub_district_name) {
        this.sub_district_name = sub_district_name;
    }

    public String getSub_district_name_sl() {
        return sub_district_name_sl;
    }

    public void setSub_district_name_sl(String sub_district_name_sl) {
        this.sub_district_name_sl = sub_district_name_sl;
    }

    public Integer getBlock_code() {
        return block_code;
    }

    public void setBlock_code(Integer block_code) {
        this.block_code = block_code;
    }

    public String getBlock_name() {
        return block_name;
    }

    public void setBlock_name(String block_name) {
        this.block_name = block_name;
    }

    public String getBlock_name_sl() {
        return block_name_sl;
    }

    public void setBlock_name_sl(String block_name_sl) {
        this.block_name_sl = block_name_sl;
    }

    public Integer getGp_code() {
        return gp_code;
    }

    public void setGp_code(Integer gp_code) {
        this.gp_code = gp_code;
    }

    public String getGp_name() {
        return gp_name;
    }

    public void setGp_name(String gp_name) {
        this.gp_name = gp_name;
    }

    public String getGp_name_sl() {
        return gp_name_sl;
    }

    public void setGp_name_sl(String gp_name_sl) {
        this.gp_name_sl = gp_name_sl;
    }

    public Integer getVillage_code() {
        return village_code;
    }

    public void setVillage_code(Integer village_code) {
        this.village_code = village_code;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }

    public String getVillage_name_sl() {
        return village_name_sl;
    }

    public void setVillage_name_sl(String village_name_sl) {
        this.village_name_sl = village_name_sl;
    }

    public Integer getEnum_block_code() {
        return enum_block_code;
    }

    public void setEnum_block_code(Integer enum_block_code) {
        this.enum_block_code = enum_block_code;
    }

    public String getEnum_block_name() {
        return enum_block_name;
    }

    public void setEnum_block_name(String enum_block_name) {
        this.enum_block_name = enum_block_name;
    }

    public String getEnum_block_name_sl() {
        return enum_block_name_sl;
    }

    public void setEnum_block_name_sl(String enum_block_name_sl) {
        this.enum_block_name_sl = enum_block_name_sl;
    }

    public Integer getHhd_uid() {
        return hhd_uid;
    }

    public void setHhd_uid(Integer hhd_uid) {
        this.hhd_uid = hhd_uid;
    }

    public Boolean getIs_uncovered() {
        return is_uncovered;
    }

    public void setIs_uncovered(Boolean is_uncovered) {
        this.is_uncovered = is_uncovered;
    }

    public Integer getUncovered_reason_code() {
        return uncovered_reason_code;
    }

    public void setUncovered_reason_code(Integer uncovered_reason_code) {
        this.uncovered_reason_code = uncovered_reason_code;
    }

    public String getUncovered_reason() {
        return uncovered_reason;
    }

    public void setUncovered_reason(String uncovered_reason) {
        this.uncovered_reason = uncovered_reason;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public String getDistrictcode() {
        return districtcode;
    }

    public void setDistrictcode(String districtcode) {
        this.districtcode = districtcode;
    }

    public String getTehsilcode() {
        return tehsilcode;
    }

    public void setTehsilcode(String tehsilcode) {
        this.tehsilcode = tehsilcode;
    }

    public String getTowncode() {
        return towncode;
    }

    public void setTowncode(String towncode) {
        this.towncode = towncode;
    }

    public String getWardid() {
        return wardid;
    }

    public void setWardid(String wardid) {
        this.wardid = wardid;
    }

    public String getAhlblockno() {
        return ahlblockno;
    }

    public void setAhlblockno(String ahlblockno) {
        this.ahlblockno = ahlblockno;
    }

    public String getAhlsubblockno() {
        return ahlsubblockno;
    }

    public void setAhlsubblockno(String ahlsubblockno) {
        this.ahlsubblockno = ahlsubblockno;
    }

    public String getAhl_family_tin() {
        return ahl_family_tin;
    }

    public void setAhl_family_tin(String ahl_family_tin) {
        this.ahl_family_tin = ahl_family_tin;
    }

    public Boolean getIs_lpg_connection_available() {
        return is_lpg_connection_available;
    }

    public void setIs_lpg_connection_available(Boolean is_lpg_connection_available) {
        this.is_lpg_connection_available = is_lpg_connection_available;
    }

    public String getLpg_consumer_id() {
        return lpg_consumer_id;
    }

    public void setLpg_consumer_id(String lpg_consumer_id) {
        this.lpg_consumer_id = lpg_consumer_id;
    }

    public Integer getNo_of_times_refilled_in_last_one_yr() {
        return no_of_times_refilled_in_last_one_yr;
    }

    public void setNo_of_times_refilled_in_last_one_yr(Integer no_of_times_refilled_in_last_one_yr) {
        this.no_of_times_refilled_in_last_one_yr = no_of_times_refilled_in_last_one_yr;
    }

    public Boolean getIs_electricity_connection_available() {
        return is_electricity_connection_available;
    }

    public void setIs_electricity_connection_available(Boolean is_electricity_connection_available) {
        this.is_electricity_connection_available = is_electricity_connection_available;
    }

    public Boolean getIs_led_available_under_ujala() {
        return is_led_available_under_ujala;
    }

    public void setIs_led_available_under_ujala(Boolean is_led_available_under_ujala) {
        this.is_led_available_under_ujala = is_led_available_under_ujala;
    }

    public Boolean getIs_any_member_have_jandhan_ac() {
        return is_any_member_have_jandhan_ac;
    }

    public void setIs_any_member_have_jandhan_ac(Boolean is_any_member_have_jandhan_ac) {
        this.is_any_member_have_jandhan_ac = is_any_member_have_jandhan_ac;
    }

    public Boolean getIs_hhd_enrolled_in_pmjjby() {
        return is_hhd_enrolled_in_pmjjby;
    }

    public void setIs_hhd_enrolled_in_pmjjby(Boolean is_hhd_enrolled_in_pmjjby) {
        this.is_hhd_enrolled_in_pmjjby = is_hhd_enrolled_in_pmjjby;
    }

    public Boolean getIs_any_member_enrolled_in_pmsby() {
        return is_any_member_enrolled_in_pmsby;
    }

    public void setIs_any_member_enrolled_in_pmsby(Boolean is_any_member_enrolled_in_pmsby) {
        this.is_any_member_enrolled_in_pmsby = is_any_member_enrolled_in_pmsby;
    }

    public Boolean getIs_any_member_immunised_under_mission_indradhanush() {
        return is_any_member_immunised_under_mission_indradhanush;
    }

    public void setIs_any_member_immunised_under_mission_indradhanush(Boolean is_any_member_immunised_under_mission_indradhanush) {
        this.is_any_member_immunised_under_mission_indradhanush = is_any_member_immunised_under_mission_indradhanush;
    }

    public Boolean getIs_any_child_0_6_yrs_or_pregnant_woman_available() {
        return is_any_child_0_6_yrs_or_pregnant_woman_available;
    }

    public void setIs_any_child_0_6_yrs_or_pregnant_woman_available(Boolean is_any_child_0_6_yrs_or_pregnant_woman_available) {
        this.is_any_child_0_6_yrs_or_pregnant_woman_available = is_any_child_0_6_yrs_or_pregnant_woman_available;
    }

    public Boolean getIs_any_member_registered_in_icds() {
        return is_any_member_registered_in_icds;
    }

    public void setIs_any_member_registered_in_icds(Boolean is_any_member_registered_in_icds) {
        this.is_any_member_registered_in_icds = is_any_member_registered_in_icds;
    }

    public Boolean getIs_hhd_availed_nutrition_benefits_under_icds() {
        return is_hhd_availed_nutrition_benefits_under_icds;
    }

    public void setIs_hhd_availed_nutrition_benefits_under_icds(Boolean is_hhd_availed_nutrition_benefits_under_icds) {
        this.is_hhd_availed_nutrition_benefits_under_icds = is_hhd_availed_nutrition_benefits_under_icds;
    }

    public ArrayList<Integer> getServices_availed_under_icds() {
        return services_availed_under_icds;
    }

    public void setServices_availed_under_icds(ArrayList<Integer> services_availed_under_icds) {
        this.services_availed_under_icds = services_availed_under_icds;
    }

    public Boolean getIs_any_member_of_hhd_is_member_of_shg() {
        return is_any_member_of_hhd_is_member_of_shg;
    }

    public void setIs_any_member_of_hhd_is_member_of_shg(Boolean is_any_member_of_hhd_is_member_of_shg) {
        this.is_any_member_of_hhd_is_member_of_shg = is_any_member_of_hhd_is_member_of_shg;
    }

    public Integer getType_of_house_used_for_living() {
        return type_of_house_used_for_living;
    }

    public void setType_of_house_used_for_living(Integer type_of_house_used_for_living) {
        this.type_of_house_used_for_living = type_of_house_used_for_living;
    }

    public Boolean getIs_hhd_a_beneficiary_of_pmayg() {
        return is_hhd_a_beneficiary_of_pmayg;
    }

    public void setIs_hhd_a_beneficiary_of_pmayg(Boolean is_hhd_a_beneficiary_of_pmayg) {
        this.is_hhd_a_beneficiary_of_pmayg = is_hhd_a_beneficiary_of_pmayg;
    }

    public Integer getPmayg_status() {
        return pmayg_status;
    }

    public void setPmayg_status(Integer pmayg_status) {
        this.pmayg_status = pmayg_status;
    }

    public String getPmayg_id() {
        return pmayg_id;
    }

    public void setPmayg_id(String pmayg_id) {
        this.pmayg_id = pmayg_id;
    }

    public Boolean getIs_hhd_issued_health_card_under_abpmjay() {
        return is_hhd_issued_health_card_under_abpmjay;
    }

    public void setIs_hhd_issued_health_card_under_abpmjay(Boolean is_hhd_issued_health_card_under_abpmjay) {
        this.is_hhd_issued_health_card_under_abpmjay = is_hhd_issued_health_card_under_abpmjay;
    }

    public String getFamily_health_card_number() {
        return family_health_card_number;
    }

    public void setFamily_health_card_number(String family_health_card_number) {
        this.family_health_card_number = family_health_card_number;
    }

    public Boolean getIs_any_member_getting_pension_under_nsap() {
        return is_any_member_getting_pension_under_nsap;
    }

    public void setIs_any_member_getting_pension_under_nsap(Boolean is_any_member_getting_pension_under_nsap) {
        this.is_any_member_getting_pension_under_nsap = is_any_member_getting_pension_under_nsap;
    }

    public ArrayList<Integer> getType_of_pensions() {
        return type_of_pensions;
    }

    public void setType_of_pensions(ArrayList<Integer> type_of_pensions) {
        this.type_of_pensions = type_of_pensions;
    }

    public Boolean getIs_any_member_ever_worked_under_mgnrega() {
        return is_any_member_ever_worked_under_mgnrega;
    }

    public void setIs_any_member_ever_worked_under_mgnrega(Boolean is_any_member_ever_worked_under_mgnrega) {
        this.is_any_member_ever_worked_under_mgnrega = is_any_member_ever_worked_under_mgnrega;
    }

    public String getMgnrega_job_card_number() {
        return mgnrega_job_card_number;
    }

    public void setMgnrega_job_card_number(String mgnrega_job_card_number) {
        this.mgnrega_job_card_number = mgnrega_job_card_number;
    }

    public Integer getNo_of_days_worked_under_mgnrega_in_last_one_yr() {
        return no_of_days_worked_under_mgnrega_in_last_one_yr;
    }

    public void setNo_of_days_worked_under_mgnrega_in_last_one_yr(Integer no_of_days_worked_under_mgnrega_in_last_one_yr) {
        this.no_of_days_worked_under_mgnrega_in_last_one_yr = no_of_days_worked_under_mgnrega_in_last_one_yr;
    }

    public Boolean getIs_any_member_undergone_training_under_any_skill_dev_prog() {
        return is_any_member_undergone_training_under_any_skill_dev_prog;
    }

    public void setIs_any_member_undergone_training_under_any_skill_dev_prog(Boolean is_any_member_undergone_training_under_any_skill_dev_prog) {
        this.is_any_member_undergone_training_under_any_skill_dev_prog = is_any_member_undergone_training_under_any_skill_dev_prog;
    }

    public ArrayList<Integer> getUndergone_skill_development_schemes() {
        return undergone_skill_development_schemes;
    }

    public void setUndergone_skill_development_schemes(ArrayList<Integer> undergone_skill_development_schemes) {
        this.undergone_skill_development_schemes = undergone_skill_development_schemes;
    }

    public Boolean getIs_hhd_available_a_functional_toilet() {
        return is_hhd_available_a_functional_toilet;
    }

    public void setIs_hhd_available_a_functional_toilet(Boolean is_hhd_available_a_functional_toilet) {
        this.is_hhd_available_a_functional_toilet = is_hhd_available_a_functional_toilet;
    }

    public ArrayList<String> getMobile_numbers_hhd_members() {
        return mobile_numbers_hhd_members;
    }

    public void setMobile_numbers_hhd_members(ArrayList<String> mobile_numbers_hhd_members) {
        this.mobile_numbers_hhd_members = mobile_numbers_hhd_members;
    }

    public Boolean getIs_hhd_availing_ration_under_nfsa_scheme() {
        return is_hhd_availing_ration_under_nfsa_scheme;
    }

    public void setIs_hhd_availing_ration_under_nfsa_scheme(Boolean is_hhd_availing_ration_under_nfsa_scheme) {
        this.is_hhd_availing_ration_under_nfsa_scheme = is_hhd_availing_ration_under_nfsa_scheme;
    }

    public Integer getNfsa_ration_card_type() {
        return nfsa_ration_card_type;
    }

    public void setNfsa_ration_card_type(Integer nfsa_ration_card_type) {
        this.nfsa_ration_card_type = nfsa_ration_card_type;
    }

    public String getNfsa_ration_card_number() {
        return nfsa_ration_card_number;
    }

    public void setNfsa_ration_card_number(String nfsa_ration_card_number) {
        this.nfsa_ration_card_number = nfsa_ration_card_number;
    }

    public String getHhd_latitude() {
        return hhd_latitude;
    }

    public void setHhd_latitude(String hhd_latitude) {
        this.hhd_latitude = hhd_latitude;
    }

    public String getHhd_longitude() {
        return hhd_longitude;
    }

    public void setHhd_longitude(String hhd_longitude) {
        this.hhd_longitude = hhd_longitude;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getTs_updated() {
        return ts_updated;
    }

    public void setTs_updated(String ts_updated) {
        this.ts_updated = ts_updated;
    }

    public String getDt_created() {
        return dt_created;
    }

    public void setDt_created(String dt_created) {
        this.dt_created = dt_created;
    }

    public Integer getIs_completed() {
        return is_completed;
    }

    public void setIs_completed(Integer is_completed) {
        this.is_completed = is_completed;
    }

    public Integer getIs_synchronized() {
        return is_synchronized;
    }

    public void setIs_synchronized(Integer is_synchronized) {
        this.is_synchronized = is_synchronized;
    }

    public Integer getIs_uploaded() {
        return is_uploaded;
    }

    public void setIs_uploaded(Integer is_uploaded) {
        this.is_uploaded = is_uploaded;
    }

    public Integer getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(Integer is_verified) {
        this.is_verified = is_verified;
    }

    public String getDt_sync() {
        return dt_sync;
    }

    public void setDt_sync(String dt_sync) {
        this.dt_sync = dt_sync;
    }

    public String getTs_verified() {
        return ts_verified;
    }

    public void setTs_verified(String ts_verified) {
        this.ts_verified = ts_verified;
    }

    public Boolean getIs_new_hhd() {
        return is_new_hhd;
    }

    public void setIs_new_hhd(Boolean is_new_hhd) {
        this.is_new_hhd = is_new_hhd;
    }

    public String getEnumerated_by() {
        return enumerated_by;
    }

    public void setEnumerated_by(String enumerated_by) {
        this.enumerated_by = enumerated_by;
    }
}
