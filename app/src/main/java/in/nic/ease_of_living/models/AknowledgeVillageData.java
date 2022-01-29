package in.nic.ease_of_living.models;

/**
 * Created by Chinki on 12/14/2018.
 */

public class AknowledgeVillageData

{
    public int block_code,district_code,gp_code,hhd_count,pop_count,state_code,sub_district_code,village_code;
    public String checksum,db_file_name,dt_db_file_created,user_id,village_name;
    public boolean is_db_file_created;

    public int getBlock_code() {
        return block_code;
    }

    public void setBlock_code(int block_code) {
        this.block_code = block_code;
    }

    public int getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(int district_code) {
        this.district_code = district_code;
    }

    public int getGp_code() {
        return gp_code;
    }

    public void setGp_code(int gp_code) {
        this.gp_code = gp_code;
    }

    public int getHhd_count() {
        return hhd_count;
    }

    public void setHhd_count(int hhd_count) {
        this.hhd_count = hhd_count;
    }

    public int getPop_count() {
        return pop_count;
    }

    public void setPop_count(int pop_count) {
        this.pop_count = pop_count;
    }

    public int getState_code() {
        return state_code;
    }

    public void setState_code(int state_code) {
        this.state_code = state_code;
    }

    public int getSub_district_code() {
        return sub_district_code;
    }

    public void setSub_district_code(int sub_district_code) {
        this.sub_district_code = sub_district_code;
    }

    public int getVillage_code() {
        return village_code;
    }

    public void setVillage_code(int village_code) {
        this.village_code = village_code;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getDb_file_name() {
        return db_file_name;
    }

    public void setDb_file_name(String db_file_name) {
        this.db_file_name = db_file_name;
    }

    public String getDt_db_file_created() {
        return dt_db_file_created;
    }

    public void setDt_db_file_created(String dt_db_file_created) {
        this.dt_db_file_created = dt_db_file_created;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }

    public boolean getIs_db_file_created() {
        return is_db_file_created;
    }

    public void setIs_db_file_created(boolean is_db_file_created) {
        this.is_db_file_created = is_db_file_created;
    }
}
