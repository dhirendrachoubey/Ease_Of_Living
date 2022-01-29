package in.nic.ease_of_living.models;

/**
 * Created by Neha Jain on 7/5/2017.
 */
public class District {

    private String district_code, district_name, district_name_sl, state_code, lang_code, state_name_sl;

    public District()
    {
    }
    public District(String name)
    {
        this.district_name=name;
        district_code="0";
    }
    public District(String district_code,String name)
    {
        this.district_name=name;
        this.district_code=district_code;
    }

    public String getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(String district_code) {
        this.district_code = district_code;
    }

    public String getDistrict_name() {
        /*if ( (MySharedPref.getStrLanguage()!=null) && !(MySharedPref.getStrLanguage().equalsIgnoreCase("en"))
                && (district_name_sl != null) && (district_name_sl.trim().length() != 0) )
        {
            return district_name_sl;
        }
        else {
            return district_name;
        }*/
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

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public String getLang_code() {
        return lang_code;
    }

    public void setLang_code(String lang_code) {
        this.lang_code = lang_code;
    }

    public String getState_name_sl() {
        return state_name_sl;
    }

    public void setState_name_sl(String state_name_sl) {
        this.state_name_sl = state_name_sl;
    }
}
