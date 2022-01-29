package in.nic.ease_of_living.models;

import java.io.Serializable;

/**
 * Created by Chinki Sai on 7/4/2017.
 */
public class HouseholdUpdated implements Serializable {
    private Integer state_code ;
    private Integer district_code ;
    private Integer sub_district_code ;
    private Integer block_code ;
    private Integer gp_code ;
    private Integer village_code ;
    private String short_user_id;
    private Integer hhd_sl_no;
    private String district_census_code;
    private String town_census_code;
    private String hhd_uid;
    private String hhd_latitude;
    private String hhd_longitude;
    private String hh_head_sl_no;
    private String hh_head_name;
    private Integer household_type_code;
    private String addressline1;
    private String addressline2;
    private String addressline3;
    private String addressline4;
    private String addressline5;
    private String pincode;
    private Integer pred_mat_of_wall_dwelling_room_code;
    private Integer pred_mat_of_roof_dwelling_room_code;
    private Integer ownership_status_code;
    private Integer hh_no_of_dwelling_rooms_exc_in_poss;
    private Integer piped_drinking_water_code;
    private Integer house_road_connectivity_code;
    private Integer outside_road_with_street_light_code;
    private Integer drainage_facility_code;
    private Integer latrine_facility_code;
    private Integer lighting_source_code;
    private Integer cooking_fuel_code;
    private Integer household_income_source_code;
    private Integer po_account_code;
    private Integer ration_card_status_code;
    private String ration_card_no;
    private Integer phc_sub_center_code;
    private Integer child_for_icds_code;
    private Integer unemployment_benefit_code;
    private Integer agriculture_loan_status_code;
    private Integer hh_liability_range_code;
    private Integer agriculture_loan_waived_code;
    private Integer telephone_type_code;
    private String hh_contact_no;
    private Integer refrigerator_status_code;
    private Integer ac_status_code;
    private Integer washing_machine_status_code;
    private Integer smartphone_status_code;
    private Integer registered_motor_code;
    private Integer land_excluding_home_status_code;
    private double total_unirrigatedland_in_acres;
    private double with_assured_irrigation_for_two_crops_in_acres;
    private double other_irrigated_land_in_acres;
    private Integer mechanized_machine_status_code;
    private Integer irrigation_equipment_status_code;
    private Integer kisan_credit_card_grt_50000_status_code;
    private Integer nfsa_free_meal_nut_support_code;
    private Integer nfsa_rs_6000_nut_support_code;
    private Integer nfsa_aaganwadi_nut_support_code ;
    private Integer nfsa_mid_day_meal_code;
    private Integer nfsa_food_gain_under_pdsa_code;
    private Integer shelterless_code;
    private Integer primitive_tribal_group_code;
    private Integer legally_bonded_labour_code;
    private Integer manual_scavenger_code;
    private Integer separate_room_as_kitchen_code;
    private Integer computer_laptop_avail_code;
    private String dt_created;
    private String user_id;
    private String device_id;
    private String app_id;
    private String app_version;
    private Integer is_completed;
    private Integer is_synchronized;
    private Integer is_uploaded;
    private String dt_sync;
    private String dt_updated;
    private Boolean is_new_hhd;
    private String enumerated_by;

    public HouseholdUpdated() {
        this.state_code = null;
        this.district_code = null;
        this.sub_district_code = null;
        this.block_code = null;
        this.gp_code = null;
        this.village_code = null;
        this.short_user_id = null;
        this.hhd_sl_no = null;
        this.district_census_code = null;
        this.town_census_code = null;
        this.hhd_uid = null;
        this.hhd_latitude = null;
        this.hhd_longitude = null;
        this.hh_head_sl_no = null;
        this.hh_head_name = null;
        this.household_type_code = null;
        this.addressline1 = null;
        this.addressline2 = null;
        this.addressline3 = null;
        this.addressline4 = null;
        this.addressline5 = null;
        this.pincode = null;
        this.pred_mat_of_wall_dwelling_room_code = null;
        this.pred_mat_of_roof_dwelling_room_code = null;
        this.ownership_status_code = null;
        this.hh_no_of_dwelling_rooms_exc_in_poss = null;
        this.piped_drinking_water_code = null;
        this.house_road_connectivity_code = null;
        this.outside_road_with_street_light_code = null;
        this.drainage_facility_code = null;
        this.latrine_facility_code = null;
        this.lighting_source_code = null;
        this.cooking_fuel_code = null;
        this.household_income_source_code = null;
        this.po_account_code = null;
        this.ration_card_status_code = null;
        this.ration_card_no = null;
        this.phc_sub_center_code = null;
        this.child_for_icds_code = null;
        this.unemployment_benefit_code = null;
        this.agriculture_loan_status_code = null;
        this.hh_liability_range_code = null;
        this.agriculture_loan_waived_code = null;
        this.telephone_type_code = null;
        this.hh_contact_no = null;
        this.refrigerator_status_code = null;
        this.ac_status_code = null;
        this.washing_machine_status_code = null;
        this.smartphone_status_code = null;
        this.registered_motor_code = null;
        this.land_excluding_home_status_code = null;
        this.total_unirrigatedland_in_acres = 0.0;
        this.with_assured_irrigation_for_two_crops_in_acres = 0.0;
        this.other_irrigated_land_in_acres = 0.0;
        this.mechanized_machine_status_code = null;
        this.irrigation_equipment_status_code = null;
        this.kisan_credit_card_grt_50000_status_code = null;
        this.nfsa_free_meal_nut_support_code = null;
        this.nfsa_rs_6000_nut_support_code = null;
        this.nfsa_aaganwadi_nut_support_code = null;
        this.nfsa_mid_day_meal_code = null;
        this.nfsa_food_gain_under_pdsa_code = null;
        this.shelterless_code = null;
        this.primitive_tribal_group_code = null;
        this.legally_bonded_labour_code = null;
        this.manual_scavenger_code = null;
        this.separate_room_as_kitchen_code = null;
        this.computer_laptop_avail_code = null;
        this.dt_created = null;
        this.user_id = null;
        this.device_id = null;
        this.app_id = null;
        this.app_version = null;
        this.is_completed = null;
        this.is_synchronized = null;
        this.is_uploaded = null;
        this.dt_sync = null;
        this.dt_updated = null;
        this.is_new_hhd = null;
        this.enumerated_by = null;
    }

    public Integer getState_code() {
        return state_code;
    }

    public void setState_code(Integer state_code) {
        this.state_code = state_code;
    }

    public Integer getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(Integer district_code) {
        this.district_code = district_code;
    }

    public Integer getSub_district_code() {
        return sub_district_code;
    }

    public void setSub_district_code(Integer sub_district_code) {
        this.sub_district_code = sub_district_code;
    }

    public Integer getBlock_code() {
        return block_code;
    }

    public void setBlock_code(Integer block_code) {
        this.block_code = block_code;
    }

    public Integer getGp_code() {
        return gp_code;
    }

    public void setGp_code(Integer gp_code) {
        this.gp_code = gp_code;
    }

    public Integer getVillage_code() {
        return village_code;
    }

    public void setVillage_code(Integer village_code) {
        this.village_code = village_code;
    }

    public String getShort_user_id() {
        return short_user_id;
    }

    public void setShort_user_id(String short_user_id) {
        this.short_user_id = short_user_id;
    }

    public Integer getHhd_sl_no() {
        return hhd_sl_no;
    }

    public void setHhd_sl_no(Integer hhd_sl_no) {
        this.hhd_sl_no = hhd_sl_no;
    }

    public String getDistrict_census_code() {
        return district_census_code;
    }

    public void setDistrict_census_code(String district_census_code) {
        this.district_census_code = district_census_code;
    }

    public String getTown_census_code() {
        return town_census_code;
    }

    public void setTown_census_code(String town_census_code) {
        this.town_census_code = town_census_code;
    }

    public String getHhd_uid() {
        return hhd_uid;
    }

    public void setHhd_uid(String hhd_uid) {
        this.hhd_uid = hhd_uid;
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

    public String getHh_head_sl_no() {
        return hh_head_sl_no;
    }

    public void setHh_head_sl_no(String hh_head_sl_no) {
        this.hh_head_sl_no = hh_head_sl_no;
    }

    public String getHh_head_name() {
        return hh_head_name;
    }

    public void setHh_head_name(String hh_head_name) {
        this.hh_head_name = hh_head_name;
    }

    public Integer getHousehold_type_code() {
        return household_type_code;
    }

    public void setHousehold_type_code(Integer household_type_code) {
        this.household_type_code = household_type_code;
    }

    public String getAddressline1() {
        return addressline1;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    public String getAddressline2() {
        return addressline2;
    }

    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }

    public String getAddressline3() {
        return addressline3;
    }

    public void setAddressline3(String addressline3) {
        this.addressline3 = addressline3;
    }

    public String getAddressline4() {
        return addressline4;
    }

    public void setAddressline4(String addressline4) {
        this.addressline4 = addressline4;
    }

    public String getAddressline5() {
        return addressline5;
    }

    public void setAddressline5(String addressline5) {
        this.addressline5 = addressline5;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Integer getPred_mat_of_wall_dwelling_room_code() {
        return pred_mat_of_wall_dwelling_room_code;
    }

    public void setPred_mat_of_wall_dwelling_room_code(Integer pred_mat_of_wall_dwelling_room_code) {
        this.pred_mat_of_wall_dwelling_room_code = pred_mat_of_wall_dwelling_room_code;
    }

    public Integer getPred_mat_of_roof_dwelling_room_code() {
        return pred_mat_of_roof_dwelling_room_code;
    }

    public void setPred_mat_of_roof_dwelling_room_code(Integer pred_mat_of_roof_dwelling_room_code) {
        this.pred_mat_of_roof_dwelling_room_code = pred_mat_of_roof_dwelling_room_code;
    }

    public Integer getOwnership_status_code() {
        return ownership_status_code;
    }

    public void setOwnership_status_code(Integer ownership_status_code) {
        this.ownership_status_code = ownership_status_code;
    }

    public Integer getHh_no_of_dwelling_rooms_exc_in_poss() {
        return hh_no_of_dwelling_rooms_exc_in_poss;
    }

    public void setHh_no_of_dwelling_rooms_exc_in_poss(Integer hh_no_of_dwelling_rooms_exc_in_poss) {
        this.hh_no_of_dwelling_rooms_exc_in_poss = hh_no_of_dwelling_rooms_exc_in_poss;
    }

    public Integer getPiped_drinking_water_code() {
        return piped_drinking_water_code;
    }

    public void setPiped_drinking_water_code(Integer piped_drinking_water_code) {
        this.piped_drinking_water_code = piped_drinking_water_code;
    }

    public Integer getHouse_road_connectivity_code() {
        return house_road_connectivity_code;
    }

    public void setHouse_road_connectivity_code(Integer house_road_connectivity_code) {
        this.house_road_connectivity_code = house_road_connectivity_code;
    }

    public Integer getOutside_road_with_street_light_code() {
        return outside_road_with_street_light_code;
    }

    public void setOutside_road_with_street_light_code(Integer outside_road_with_street_light_code) {
        this.outside_road_with_street_light_code = outside_road_with_street_light_code;
    }

    public Integer getDrainage_facility_code() {
        return drainage_facility_code;
    }

    public void setDrainage_facility_code(Integer drainage_facility_code) {
        this.drainage_facility_code = drainage_facility_code;
    }

    public Integer getLatrine_facility_code() {
        return latrine_facility_code;
    }

    public void setLatrine_facility_code(Integer latrine_facility_code) {
        this.latrine_facility_code = latrine_facility_code;
    }

    public Integer getLighting_source_code() {
        return lighting_source_code;
    }

    public void setLighting_source_code(Integer lighting_source_code) {
        this.lighting_source_code = lighting_source_code;
    }

    public Integer getCooking_fuel_code() {
        return cooking_fuel_code;
    }

    public void setCooking_fuel_code(Integer cooking_fuel_code) {
        this.cooking_fuel_code = cooking_fuel_code;
    }

    public Integer getHousehold_income_source_code() {
        return household_income_source_code;
    }

    public void setHousehold_income_source_code(Integer household_income_source_code) {
        this.household_income_source_code = household_income_source_code;
    }

    public Integer getPo_account_code() {
        return po_account_code;
    }

    public void setPo_account_code(Integer po_account_code) {
        this.po_account_code = po_account_code;
    }

    public Integer getRation_card_status_code() {
        return ration_card_status_code;
    }

    public void setRation_card_status_code(Integer ration_card_status_code) {
        this.ration_card_status_code = ration_card_status_code;
    }

    public String getRation_card_no() {
        return ration_card_no;
    }

    public void setRation_card_no(String ration_card_no) {
        this.ration_card_no = ration_card_no;
    }

    public Integer getPhc_sub_center_code() {
        return phc_sub_center_code;
    }

    public void setPhc_sub_center_code(Integer phc_sub_center_code) {
        this.phc_sub_center_code = phc_sub_center_code;
    }

    public Integer getChild_for_icds_code() {
        return child_for_icds_code;
    }

    public void setChild_for_icds_code(Integer child_for_icds_code) {
        this.child_for_icds_code = child_for_icds_code;
    }


    public Integer getAgriculture_loan_status_code() {
        return agriculture_loan_status_code;
    }

    public void setAgriculture_loan_status_code(Integer agriculture_loan_status_code) {
        this.agriculture_loan_status_code = agriculture_loan_status_code;
    }

    public Integer getHh_liability_range_code() {
        return hh_liability_range_code;
    }

    public void setHh_liability_range_code(Integer hh_liability_range_code) {
        this.hh_liability_range_code = hh_liability_range_code;
    }

    public Integer getAgriculture_loan_waived_code() {
        return agriculture_loan_waived_code;
    }

    public void setAgriculture_loan_waived_code(Integer agriculture_loan_waived_code) {
        this.agriculture_loan_waived_code = agriculture_loan_waived_code;
    }

    public Integer getTelephone_type_code() {
        return telephone_type_code;
    }

    public void setTelephone_type_code(Integer telephone_type_code) {
        this.telephone_type_code = telephone_type_code;
    }

    public String getHh_contact_no() {
        return hh_contact_no;
    }

    public void setHh_contact_no(String hh_contact_no) {
        this.hh_contact_no = hh_contact_no;
    }

    public Integer getRefrigerator_status_code() {
        return refrigerator_status_code;
    }

    public void setRefrigerator_status_code(Integer refrigerator_status_code) {
        this.refrigerator_status_code = refrigerator_status_code;
    }

    public Integer getAc_status_code() {
        return ac_status_code;
    }

    public void setAc_status_code(Integer ac_status_code) {
        this.ac_status_code = ac_status_code;
    }

    public Integer getWashing_machine_status_code() {
        return washing_machine_status_code;
    }

    public void setWashing_machine_status_code(Integer washing_machine_status_code) {
        this.washing_machine_status_code = washing_machine_status_code;
    }

    public Integer getSmartphone_status_code() {
        return smartphone_status_code;
    }

    public void setSmartphone_status_code(Integer smartphone_status_code) {
        this.smartphone_status_code = smartphone_status_code;
    }

    public Integer getRegistered_motor_code() {
        return registered_motor_code;
    }

    public void setRegistered_motor_code(Integer registered_motor_code) {
        this.registered_motor_code = registered_motor_code;
    }

    public Integer getLand_excluding_home_status_code() {
        return land_excluding_home_status_code;
    }

    public void setLand_excluding_home_status_code(Integer land_excluding_home_status_code) {
        this.land_excluding_home_status_code = land_excluding_home_status_code;
    }

    public double getTotal_unirrigatedland_in_acres() {
        return total_unirrigatedland_in_acres;
    }

    public void setTotal_unirrigatedland_in_acres(double total_unirrigatedland_in_acres) {
        this.total_unirrigatedland_in_acres = total_unirrigatedland_in_acres;
    }

    public double getWith_assured_irrigation_for_two_crops_in_acres() {
        return with_assured_irrigation_for_two_crops_in_acres;
    }

    public void setWith_assured_irrigation_for_two_crops_in_acres(double with_assured_irrigation_for_two_crops_in_acres) {
        this.with_assured_irrigation_for_two_crops_in_acres = with_assured_irrigation_for_two_crops_in_acres;
    }

    public double getOther_irrigated_land_in_acres() {
        return other_irrigated_land_in_acres;
    }

    public void setOther_irrigated_land_in_acres(double other_irrigated_land_in_acres) {
        this.other_irrigated_land_in_acres = other_irrigated_land_in_acres;
    }

    public Integer getMechanized_machine_status_code() {
        return mechanized_machine_status_code;
    }

    public void setMechanized_machine_status_code(Integer mechanized_machine_status_code) {
        this.mechanized_machine_status_code = mechanized_machine_status_code;
    }

    public Integer getIrrigation_equipment_status_code() {
        return irrigation_equipment_status_code;
    }

    public void setIrrigation_equipment_status_code(Integer irrigation_equipment_status_code) {
        this.irrigation_equipment_status_code = irrigation_equipment_status_code;
    }

    public Integer getKisan_credit_card_grt_50000_status_code() {
        return kisan_credit_card_grt_50000_status_code;
    }

    public void setKisan_credit_card_grt_50000_status_code(Integer kisan_credit_card_grt_50000_status_code) {
        this.kisan_credit_card_grt_50000_status_code = kisan_credit_card_grt_50000_status_code;
    }

    public Integer getNfsa_free_meal_nut_support_code() {
        return nfsa_free_meal_nut_support_code;
    }

    public void setNfsa_free_meal_nut_support_code(Integer nfsa_free_meal_nut_support_code) {
        this.nfsa_free_meal_nut_support_code = nfsa_free_meal_nut_support_code;
    }

    public Integer getNfsa_rs_6000_nut_support_code() {
        return nfsa_rs_6000_nut_support_code;
    }

    public void setNfsa_rs_6000_nut_support_code(Integer nfsa_rs_6000_nut_support_code) {
        this.nfsa_rs_6000_nut_support_code = nfsa_rs_6000_nut_support_code;
    }

    public Integer getNfsa_aaganwadi_nut_support_code() {
        return nfsa_aaganwadi_nut_support_code;
    }

    public void setNfsa_aaganwadi_nut_support_code(Integer nfsa_aaganwadi_nut_support_code) {
        this.nfsa_aaganwadi_nut_support_code = nfsa_aaganwadi_nut_support_code;
    }

    public Integer getNfsa_mid_day_meal_code() {
        return nfsa_mid_day_meal_code;
    }

    public void setNfsa_mid_day_meal_code(Integer nfsa_mid_day_meal_code) {
        this.nfsa_mid_day_meal_code = nfsa_mid_day_meal_code;
    }

    public Integer getNfsa_food_gain_under_pdsa_code() {
        return nfsa_food_gain_under_pdsa_code;
    }

    public void setNfsa_food_gain_under_pdsa_code(Integer nfsa_food_gain_under_pdsa_code) {
        this.nfsa_food_gain_under_pdsa_code = nfsa_food_gain_under_pdsa_code;
    }

    public Integer getShelterless_code() {
        return shelterless_code;
    }

    public void setShelterless_code(Integer shelterless_code) {
        this.shelterless_code = shelterless_code;
    }

    public Integer getPrimitive_tribal_group_code() {
        return primitive_tribal_group_code;
    }

    public void setPrimitive_tribal_group_code(Integer primitive_tribal_group_code) {
        this.primitive_tribal_group_code = primitive_tribal_group_code;
    }

    public Integer getLegally_bonded_labour_code() {
        return legally_bonded_labour_code;
    }

    public void setLegally_bonded_labour_code(Integer legally_bonded_labour_code) {
        this.legally_bonded_labour_code = legally_bonded_labour_code;
    }

    public Integer getManual_scavenger_code() {
        return manual_scavenger_code;
    }

    public void setManual_scavenger_code(Integer manual_scavenger_code) {
        this.manual_scavenger_code = manual_scavenger_code;
    }

    public Integer getSeparate_room_as_kitchen_code() {
        return separate_room_as_kitchen_code;
    }

    public void setSeparate_room_as_kitchen_code(Integer separate_room_as_kitchen_code) {
        this.separate_room_as_kitchen_code = separate_room_as_kitchen_code;
    }

    public Integer getComputer_laptop_avail_code() {
        return computer_laptop_avail_code;
    }

    public void setComputer_laptop_avail_code(Integer computer_laptop_avail_code) {
        this.computer_laptop_avail_code = computer_laptop_avail_code;
    }

    public String getDt_created() {
        return dt_created;
    }

    public void setDt_created(String dt_created) {
        this.dt_created = dt_created;
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

    public String getDt_sync() {
        return dt_sync;
    }

    public void setDt_sync(String dt_sync) {
        this.dt_sync = dt_sync;
    }

    public String getDt_updated() {
        return dt_updated;
    }

    public void setDt_updated(String dt_updated) {
        this.dt_updated = dt_updated;
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
