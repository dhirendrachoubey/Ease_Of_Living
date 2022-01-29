package in.nic.ease_of_living.adapter;

/**
 * Created by Neha Jain on 7/21/2017.
 */
public class DevelopmentBlock {
    private String block_name_sl, block_name, block_code, sub_district_code, district_code, state_code,enum_block_code,enu_block_name;

    public DevelopmentBlock(String block_name) {
        this.block_name = block_name;
    }

    public DevelopmentBlock() {
    }

    public String getBlock_name() {
        /*if ( (MySharedPref.getStrLanguage()!=null) && !(MySharedPref.getStrLanguage().equalsIgnoreCase("en"))
                && (block_name_sl != null) && (block_name_sl.trim().length() != 0) )
        {
            return block_name_sl;
        }
        else {
            return block_name;
        }*/
        return block_name;
    }

    public void setBlock_name(String block_name) {
        this.block_name = block_name;
    }

    public String getBlock_code() {
        return block_code;
    }

    public void setBlock_code(String block_code) {
        this.block_code = block_code;
    }

    public String getSub_district_code() {
        return sub_district_code;
    }

    public void setSub_district_code(String sub_district_code) {
        this.sub_district_code = sub_district_code;
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

    public String getBlock_name_sl() {
        return block_name_sl;
    }

    public void setBlock_name_sl(String block_name_sl) {
        this.block_name_sl = block_name_sl;
    }

    public String getEnum_block_code() {
        return enum_block_code;
    }

    public void setEnum_block_code(String enum_block_code) {
        this.enum_block_code = enum_block_code;
    }

    public String getEnu_block_name() {
        return enu_block_name;
    }

    public void setEnu_block_name(String enu_block_name) {
        this.enu_block_name = enu_block_name;
    }
}