package in.nic.ease_of_living.models;

import in.nic.ease_of_living.supports.MySharedPref;

/**
 * Created by Neha Jain on 6/21/2017.
 */
public class User{

    private String
            user_id,
            user_password,
            title_name,
            first_name,
            middle_name,
            last_name,
            email_id,
            mobile,
            dob,
            gender,
            identity_type,
            identity_id,
            identity_number,
            identity_pic,
            created_by,
            group_id,
            group_name,
            organisation,
            org_phone_no,
            org_address_hh_area,
            org_address_landmark,
            org_address_city,
            org_country,
            org_state_code,
            org_state_name,
            org_district_code,
            org_district_name,
            org_pincode,
            profile_pic,
            state_name,
            state_name_sl,
            district_name,
            district_name_sl,
            sub_district_name,
            sub_district_name_sl,
            block_name,
            block_name_sl,
            gp_name,
            gp_name_sl,
            village_name,
            village_name_sl,
            isActive,
            approved_by,
            ts_approved,
            application_value,
            group_ownership,   short_user_id,
            b_file_name;


    private int
            state_code,
            district_code,
            sub_district_code,
            block_code,
            gp_code,
            village_code,
            enum_block_code;

    private boolean flag_reset_pass, is_email_validated, is_mobile_validated, is_enabled, b_isPdfGenerated,is_enumeration_completed;

    public String getUser_id() {
        return user_id;
    }

    public String getShort_user_id() {
        return short_user_id;
    }

    public void setShort_user_id(String short_user_id) {
        this.short_user_id = short_user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getTitle_name() {
        return title_name;
    }

    public void setTitle_name(String title_name) {
        this.title_name = title_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdentity_type() {
        return identity_type;
    }

    public void setIdentity_type(String identity_type) {
        this.identity_type = identity_type;
    }

    public String getIdentity_id() {
        return identity_id;
    }

    public void setIdentity_id(String identity_id) {
        this.identity_id = identity_id;
    }

    public String getIdentity_number() {
        return identity_number;
    }

    public void setIdentity_number(String identity_number) {
        this.identity_number = identity_number;
    }

    public String getIdentity_pic() {
        return identity_pic;
    }

    public void setIdentity_pic(String identity_pic) {
        this.identity_pic = identity_pic;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getOrg_phone_no() {
        return org_phone_no;
    }

    public void setOrg_phone_no(String org_phone_no) {
        this.org_phone_no = org_phone_no;
    }

    public String getOrg_address_hh_area() {
        return org_address_hh_area;
    }

    public void setOrg_address_hh_area(String org_address_hh_area) {
        this.org_address_hh_area = org_address_hh_area;
    }

    public String getOrg_address_landmark() {
        return org_address_landmark;
    }

    public void setOrg_address_landmark(String org_address_landmark) {
        this.org_address_landmark = org_address_landmark;
    }

    public String getOrg_address_city() {
        return org_address_city;
    }

    public void setOrg_address_city(String org_address_city) {
        this.org_address_city = org_address_city;
    }

    public String getOrg_country() {
        return org_country;
    }

    public void setOrg_country(String org_country) {
        this.org_country = org_country;
    }

    public String getOrg_state_code() {
        return org_state_code;
    }

    public void setOrg_state_code(String org_state_code) {
        this.org_state_code = org_state_code;
    }

    public String getOrg_district_code() {
        return org_district_code;
    }

    public void setOrg_district_code(String org_district_code) {
        this.org_district_code = org_district_code;
    }

    public String getOrg_pincode() {
        return org_pincode;
    }

    public void setOrg_pincode(String org_pincode) {
        this.org_pincode = org_pincode;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getState_name() {
        if ( (MySharedPref.getStrLanguage()!=null) && !(MySharedPref.getStrLanguage().equalsIgnoreCase("en"))
                && (state_name_sl != null) && (state_name_sl.trim().length() != 0) )
        {
            return state_name_sl;
        }
        else {
            return state_name;
        }
    }

    public String getState_name_en() {
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

    public String getDistrict_name_en() {
        return district_name;
    }

    public String getDistrict_name() {
        if ( (MySharedPref.getStrLanguage()!=null) && !(MySharedPref.getStrLanguage().equalsIgnoreCase("en"))
                && (district_name_sl != null) && (district_name_sl.trim().length() != 0) )
        {
            return district_name_sl;
        }
        else {
            return district_name;
        }
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

    public String getSub_district_name() {
        if ( (MySharedPref.getStrLanguage()!=null) && !(MySharedPref.getStrLanguage().equalsIgnoreCase("en"))
                && (sub_district_name_sl != null) && (sub_district_name_sl.trim().length() != 0) )
        {
            return sub_district_name_sl;
        }
        else {
            return sub_district_name;
        }
    }

    public String getSub_district_name_en() {
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

    public String getBlock_name() {
        if ( (MySharedPref.getStrLanguage()!=null) && !(MySharedPref.getStrLanguage().equalsIgnoreCase("en"))
                && (block_name_sl != null) && (block_name_sl.trim().length() != 0) )
        {
            return block_name_sl;
        }
        else {
            return block_name;
        }
    }

    public String getBlock_name_en() {
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

    public String getGp_name() {
        if ( (MySharedPref.getStrLanguage()!=null) && !(MySharedPref.getStrLanguage().equalsIgnoreCase("en"))
                && (gp_name_sl != null) && (gp_name_sl.trim().length() != 0) )
        {
            return gp_name_sl;
        }
        else {
            return gp_name;
        }
    }

    public String getGp_name_en() {
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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getApproved_by() {
        return approved_by;
    }

    public void setApproved_by(String approved_by) {
        this.approved_by = approved_by;
    }

    public String getTs_approved() {
        return ts_approved;
    }

    public void setTs_approved(String ts_approved) {
        this.ts_approved = ts_approved;
    }

    public String getApplication_value() {
        return application_value;
    }

    public void setApplication_value(String application_value) {
        this.application_value = application_value;
    }

    public int getState_code() {
        return state_code;
    }

    public void setState_code(int state_code) {
        this.state_code = state_code;
    }

    public int getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(int district_code) {
        this.district_code = district_code;
    }

    public int getSub_district_code() {
        return sub_district_code;
    }

    public void setSub_district_code(int sub_district_code) {
        this.sub_district_code = sub_district_code;
    }

    public int getBlock_code() {
        return block_code;
    }

    public void setBlock_code(int block_code) {
        this.block_code = block_code;
    }

    public int getGp_code() {
        return gp_code;
    }

    public void setGp_code(int gp_code) {
        this.gp_code = gp_code;
    }

    public int getVillage_code() {
        return village_code;
    }

    public void setVillage_code(int village_code) {
        this.village_code = village_code;
    }

    public boolean isFlag_reset_pass() {
        return flag_reset_pass;
    }

    public void setFlag_reset_pass(boolean flag_reset_pass) {
        this.flag_reset_pass = flag_reset_pass;
    }

    public boolean isIs_email_validated() {
        return is_email_validated;
    }

    public void setIs_email_validated(boolean is_email_validated) {
        this.is_email_validated = is_email_validated;
    }

    public boolean isIs_mobile_validated() {
        return is_mobile_validated;
    }

    public void setIs_mobile_validated(boolean is_mobile_validated) {
        this.is_mobile_validated = is_mobile_validated;
    }

    public boolean isIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(boolean is_enabled) {
        this.is_enabled = is_enabled;
    }

    public boolean isB_isPdfGenerated() {
        return b_isPdfGenerated;
    }

    public void setB_isPdfGenerated(boolean b_isPdfGenerated) {
        this.b_isPdfGenerated = b_isPdfGenerated;
    }

    public String getGroup_ownership() {
        return group_ownership;
    }

    public void setGroup_ownership(String group_ownership) {
        this.group_ownership = group_ownership;
    }

    public String getOrg_state_name() {
        return org_state_name;
    }

    public void setOrg_state_name(String org_state_name) {
        this.org_state_name = org_state_name;
    }

    public String getOrg_district_name() {
        return org_district_name;
    }

    public void setOrg_district_name(String org_district_name) {
        this.org_district_name = org_district_name;
    }
    public boolean isIs_enumeration_completed() {
        return is_enumeration_completed;
    }
    public boolean is_enumeration_completed() {
        return is_enumeration_completed;
    }

    public String getB_file_name() {
        return b_file_name;
    }

    public void setB_file_name(String b_file_name) {
        this.b_file_name = b_file_name;
    }

    public int getEnum_block_code() {
        return enum_block_code;
    }

    public void setEnum_block_code(int enum_block_code) {
        this.enum_block_code = enum_block_code;
    }

    public void setIs_enumeration_completed(boolean is_enumeration_completed) {
        this.is_enumeration_completed = is_enumeration_completed;
    }
}
