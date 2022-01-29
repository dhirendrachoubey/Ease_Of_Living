package in.nic.ease_of_living.adapter;

/**
 * Created by Neha Jain on 25/7/2017.
 */
public class Village {

    private int state_code, district_code, sub_district_code, block_code, gp_code, village_code;


    private String state_name, district_name, sub_district_name, block_name, gp_name, village_name, village_name_sl;

    private Boolean is_completed, is_uninhabited;
    private String e_file_name;
    private String dt_e_file_uploaded;
    private String e_user_id;
    private boolean is_verified;

    public Village()
    {
        state_code = 0;
        state_name = "";
        district_code = 0;
        district_name = "";
        sub_district_code = 0;
        sub_district_name = "";
        block_code = 0;
        block_name = "";
        gp_code = 0;
        gp_name = "";
        village_code = 0;
        village_name = "";
        village_name_sl = "";
        is_uninhabited = false;
        is_completed = false;
        e_file_name="";
        dt_e_file_uploaded="";
        e_user_id = "";
        is_verified=false;
    }

    public Village(int village_code)
    {
        state_code = 0;
        state_name = "";
        district_code = 0;
        district_name = "";
        sub_district_code = 0;
        sub_district_name = "";
        block_code = 0;
        block_name = "";
        gp_code = 0;
        gp_name = "";
        this.village_code = village_code;
        village_name = "";
        village_name_sl = "";
        is_uninhabited = false;
        is_completed = false;
        e_file_name="";
        dt_e_file_uploaded="";
        e_user_id = "";
        is_verified=false;
    }

    public Village(String village_name)
    {
        state_code = 0;
        state_name = "";
        district_code = 0;
        district_name = "";
        sub_district_code = 0;
        sub_district_name = "";
        block_code = 0;
        block_name = "";
        gp_code = 0;
        gp_name = "";
        village_code = 0;
        this.village_name = village_name;
        village_name_sl = "";
        is_uninhabited = false;
        is_completed = false;
        e_file_name="";
        dt_e_file_uploaded="";
        e_user_id = "";
        is_verified=false;
    }
    public Village(int village_code,String village_name){

        this.village_code =village_code;
        this.village_name = village_name;
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

    public String getE_file_name() {
        return e_file_name;
    }

    public void setE_file_name(String e_file_name) {
        this.e_file_name = e_file_name;
    }

    public String getDt_e_file_uploaded() {
        return dt_e_file_uploaded;
    }

    public void setDt_e_file_uploaded(String dt_e_file_uploaded) {
        this.dt_e_file_uploaded = dt_e_file_uploaded;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public String getE_user_id() {

        return e_user_id;
    }

    public void setE_user_id(String e_user_id) {
        this.e_user_id = e_user_id;
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
        /*if ( (MySharedPref.getStrLanguage()!=null) && !(MySharedPref.getStrLanguage().equalsIgnoreCase("en"))
                && (village_name_sl != null) && (village_name_sl.trim().length() != 0) )
        {
            return village_name_sl;
        }
        else {
            return village_name;
        }*/
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }

    public Boolean getIs_completed() {
        return is_completed;
    }

    public void setIs_completed(Boolean is_completed) {
        this.is_completed = is_completed;
    }

    public Boolean getIs_uninhabited() {
        return is_uninhabited;
    }

    public void setIs_uninhabited(Boolean is_uninhabited) {
        this.is_uninhabited = is_uninhabited;
    }

    public String getVillage_name_sl() {
        return village_name_sl;
    }

    public void setVillage_name_sl(String village_name_sl) {
        this.village_name_sl = village_name_sl;
    }
}
