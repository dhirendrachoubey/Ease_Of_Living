package in.nic.ease_of_living.models;

/**
 * Created by Neha Jain on 3/21/2018.
 */

public class SubDistrict {
    private String district_code, state_code, sub_district_code, sub_district_name, sub_district_name_sl;

    public SubDistrict() {
    }

    public SubDistrict(String sub_district_code, String sub_district_name) {
        this.sub_district_code = sub_district_code;
        this.sub_district_name = sub_district_name;
    }

    public SubDistrict(String sub_district_name) {
        this.sub_district_name = sub_district_name;

    }

    public String getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(String district_code) {
        this.district_code = district_code;
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

    public String getSub_district_name() {
        /*if ( (MySharedPref.getStrLanguage()!=null) && !(MySharedPref.getStrLanguage().equalsIgnoreCase("en"))
                && (sub_district_name_sl != null) && (sub_district_name_sl.trim().length() != 0) )
        {
            return sub_district_name_sl;
        }
        else {
            return sub_district_name;
        }*/
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
}
