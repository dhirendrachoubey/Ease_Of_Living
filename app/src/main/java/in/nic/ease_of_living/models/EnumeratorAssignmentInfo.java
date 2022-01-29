package in.nic.ease_of_living.models;

import java.io.Serializable;


public class EnumeratorAssignmentInfo implements Serializable {

	private Integer state_code;
	private Integer district_code;
	private Integer sub_district_code;
	private Integer block_code;
	private Integer gp_code;
	private Integer village_code;
	private String short_user_id;
	private String user_id;
	private String db_file_name;
	private Integer hhd_count;
	private Integer hhd_range_start;
	private Integer hhd_range_end;
	private String range_assigned_by;
	private String first_updated;
	private String last_updated;
	private Boolean is_final_db_uploaded;
	private String final_db_uploaded_by;
	private String dt_final_db_uploaded;
	private Boolean is_enumeration_completed;
	private String dt_enumeration_completed;
	private String state_name, district_name, sub_district_name, block_name, gp_name, village_name;
	private Integer min_hhd, max_hhd;
	private String email_id, gender, mobile;
	private Boolean is_modified;

	public EnumeratorAssignmentInfo() {
		this.state_code = null;
		this.district_code = null;
		this.sub_district_code = null;
		this.block_code = null;
		this.gp_code = null;
		this.village_code = null;
		this.short_user_id = null;
		this.user_id = null;
		this.db_file_name = null;
		this.hhd_count = null;
		this.hhd_range_start = null;
		this.hhd_range_end = null;
		this.range_assigned_by = null;
		this.first_updated = null;
		this.last_updated = null;
		this.is_final_db_uploaded = null;
		this.final_db_uploaded_by = null;
		this.dt_final_db_uploaded = null;
		this.is_enumeration_completed = null;
		this.dt_enumeration_completed = null;
		this.state_name = null;
		this.district_name = null;
		this.sub_district_name = null;
		this.block_name = null;
		this.gp_name = null;
		this.village_name = null;
		this.min_hhd = null;
		this.max_hhd = null;
		this.email_id = null;
		this.mobile = null;
		this.gender = null;
		this.is_modified = false;
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

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getDb_file_name() {
		return db_file_name;
	}

	public void setDb_file_name(String db_file_name) {
		this.db_file_name = db_file_name;
	}

	public Integer getHhd_count() {
		return hhd_count;
	}

	public void setHhd_count(Integer hhd_count) {
		this.hhd_count = hhd_count;
	}

	public Integer getHhd_range_start() {
		return hhd_range_start;
	}

	public void setHhd_range_start(Integer hhd_range_start) {
		this.hhd_range_start = hhd_range_start;
	}

	public Integer getHhd_range_end() {
		return hhd_range_end;
	}

	public void setHhd_range_end(Integer hhd_range_end) {
		this.hhd_range_end = hhd_range_end;
	}

	public String getRange_assigned_by() {
		return range_assigned_by;
	}

	public void setRange_assigned_by(String range_assigned_by) {
		this.range_assigned_by = range_assigned_by;
	}

	public String getFirst_updated() {
		return first_updated;
	}

	public void setFirst_updated(String first_updated) {
		this.first_updated = first_updated;
	}

	public String getLast_updated() {
		return last_updated;
	}

	public void setLast_updated(String last_updated) {
		this.last_updated = last_updated;
	}

	public Boolean getIs_final_db_uploaded() {
		return is_final_db_uploaded;
	}

	public void setIs_final_db_uploaded(Boolean is_final_db_uploaded) {
		this.is_final_db_uploaded = is_final_db_uploaded;
	}

	public String getFinal_db_uploaded_by() {
		return final_db_uploaded_by;
	}

	public void setFinal_db_uploaded_by(String final_db_uploaded_by) {
		this.final_db_uploaded_by = final_db_uploaded_by;
	}

	public String getDt_final_db_uploaded() {
		return dt_final_db_uploaded;
	}

	public void setDt_final_db_uploaded(String dt_final_db_uploaded) {
		this.dt_final_db_uploaded = dt_final_db_uploaded;
	}

	public Boolean getIs_enumeration_completed() {
		return is_enumeration_completed;
	}

	public void setIs_enumeration_completed(Boolean is_enumeration_completed) {
		this.is_enumeration_completed = is_enumeration_completed;
	}

	public String getDt_enumeration_completed() {
		return dt_enumeration_completed;
	}

	public void setDt_enumeration_completed(String dt_enumeration_completed) {
		this.dt_enumeration_completed = dt_enumeration_completed;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public String getDistrict_name() {
		return district_name;
	}

	public void setDistrict_name(String district_name) {
		this.district_name = district_name;
	}

	public String getSub_district_name() {
		return sub_district_name;
	}

	public void setSub_district_name(String sub_district_name) {
		this.sub_district_name = sub_district_name;
	}

	public String getBlock_name() {
		return block_name;
	}

	public void setBlock_name(String block_name) {
		this.block_name = block_name;
	}

	public String getGp_name() {
		return gp_name;
	}

	public void setGp_name(String gp_name) {
		this.gp_name = gp_name;
	}

	public String getVillage_name() {
		return village_name;
	}

	public void setVillage_name(String village_name) {
		this.village_name = village_name;
	}

	public Integer getMin_hhd() {
		if(min_hhd == null)
			return 0;

		else
			return min_hhd;

	}

	public void setMin_hhd(Integer min_hhd) {
		this.min_hhd = min_hhd;
	}

	public Integer getMax_hhd() {
		if(max_hhd==null)
			return 0;
		else
			return max_hhd;

	}

	public void setMax_hhd(Integer max_hhd) {
		this.max_hhd = max_hhd;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Boolean getIs_modified() {
		return is_modified;
	}

	public void setIs_modified(Boolean is_modified) {
		this.is_modified = is_modified;
	}
}
