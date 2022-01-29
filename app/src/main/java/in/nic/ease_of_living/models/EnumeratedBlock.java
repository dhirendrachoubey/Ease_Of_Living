package in.nic.ease_of_living.models;

/**
 * Created by Neha Jain on 7/21/2017.
 */
public class EnumeratedBlock {
    private String state_name, state_name_sl, district_name, district_name_sl, sub_district_name, sub_district_name_sl,
            block_name, block_name_sl, gp_name, gp_name_sl, village_name, village_name_sl,enum_block_name, enum_block_name_sl,
            b_file_name, dt_b_file_created;

    private Integer state_code, district_code, sub_district_code, block_code, gp_code, village_code, enum_block_code, total_member,
            total_hhd, total_hhd_covered, total_hhd_uncovered;

    private Boolean is_db_downloaded, is_eb_completed, is_hhd_uploaded;

    public EnumeratedBlock(String enum_block_name) {
        this.enum_block_name = enum_block_name;
    }

    public EnumeratedBlock() {
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

    public String getB_file_name() {
        return b_file_name;
    }

    public void setB_file_name(String b_file_name) {
        this.b_file_name = b_file_name;
    }

    public String getDt_b_file_created() {
        return dt_b_file_created;
    }

    public void setDt_b_file_created(String dt_b_file_created) {
        this.dt_b_file_created = dt_b_file_created;
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

    public Integer getEnum_block_code() {
        return enum_block_code;
    }

    public void setEnum_block_code(Integer enum_block_code) {
        this.enum_block_code = enum_block_code;
    }

    public Integer getTotal_member() {
        return total_member;
    }

    public void setTotal_member(Integer total_member) {
        this.total_member = total_member;
    }

    public Integer getTotal_hhd() {
        return total_hhd;
    }

    public void setTotal_hhd(Integer total_hhd) {
        this.total_hhd = total_hhd;
    }

    public Integer getTotal_hhd_covered() {
        return total_hhd_covered;
    }

    public void setTotal_hhd_covered(Integer total_hhd_covered) {
        this.total_hhd_covered = total_hhd_covered;
    }

    public Integer getTotal_hhd_uncovered() {
        return total_hhd_uncovered;
    }

    public void setTotal_hhd_uncovered(Integer total_hhd_uncovered) {
        this.total_hhd_uncovered = total_hhd_uncovered;
    }

    public Boolean getIs_db_downloaded() {
        return is_db_downloaded;
    }

    public void setIs_db_downloaded(Boolean is_db_downloaded) {
        this.is_db_downloaded = is_db_downloaded;
    }

    public Boolean getIs_eb_completed() {
        return is_eb_completed;
    }

    public void setIs_eb_completed(Boolean is_eb_completed) {
        this.is_eb_completed = is_eb_completed;
    }

    public Boolean getIs_hhd_uploaded() {
        return is_hhd_uploaded;
    }

    public void setIs_hhd_uploaded(Boolean is_hhd_uploaded) {
        this.is_hhd_uploaded = is_hhd_uploaded;
    }
}