package in.nic.ease_of_living.models;

/**
 * Created by Neha Jain on 25/7/2017.
 */
public class GpVillageChecksum {

    private int state_code;
    private int district_code;
    private int sub_district_code;
    private int block_code;
    private int gp_code;
    private int village_code;
    private String  checksum,  b_file_name, app_id, app_version, client_imei_no, client_ip_address, client_mac_address, device_id, user_id;

    public GpVillageChecksum()
    {
        state_code = 0;
        district_code = 0;
        block_code = 0;
        gp_code = 0;
        village_code = 0;
        checksum = "";
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

    public int getSub_district_code() {
        return sub_district_code;
    }

    public void setSub_district_code(int sub_district_code) {
        this.sub_district_code = sub_district_code;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getB_file_name() {
        return b_file_name;
    }

    public void setB_file_name(String b_file_name) {
        this.b_file_name = b_file_name;
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

    public String getClient_imei_no() {
        return client_imei_no;
    }

    public void setClient_imei_no(String client_imei_no) {
        this.client_imei_no = client_imei_no;
    }

    public String getClient_ip_address() {
        return client_ip_address;
    }

    public void setClient_ip_address(String client_ip_address) {
        this.client_ip_address = client_ip_address;
    }

    public String getClient_mac_address() {
        return client_mac_address;
    }

    public void setClient_mac_address(String client_mac_address) {
        this.client_mac_address = client_mac_address;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
