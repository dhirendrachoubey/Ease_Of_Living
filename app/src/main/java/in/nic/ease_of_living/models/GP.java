package in.nic.ease_of_living.models;

/**
 * Created by Neha Jain on 7/21/2017.
 */
public class GP {
    private String block_code, district_code, gp_code, gp_name, gp_name_sl, state_code, sub_district_code;



    public GP()
    {
    }

    public GP(String gp_name) {
        this.gp_name = gp_name;
    }

    public String getBlock_code() {
        return block_code;
    }

    public void setBlock_code(String block_code) {
        this.block_code = block_code;
    }

    public String getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(String district_code) {
        this.district_code = district_code;
    }

    public String getGp_code() {
        return gp_code;
    }

    public void setGp_code(String gp_code) {
        this.gp_code = gp_code;
    }

    public String getGp_name() {
        /*if ( (MySharedPref.getStrLanguage()!=null) && !(MySharedPref.getStrLanguage().equalsIgnoreCase("en"))
                && (gp_name_sl != null) && (gp_name_sl.trim().length() != 0) )
        {
            return gp_name_sl;
        }
        else {
            return gp_name;
        }*/
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

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public String getSub_district_code() {
        return sub_district_code;
    }

    public void setSub_district_code(String sub_district_code) {
        this.sub_district_code = sub_district_code;
    }
}
