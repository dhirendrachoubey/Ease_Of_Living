package in.nic.ease_of_living.models;

import java.io.Serializable;

/**
 * Created by Neha Jain on 20/July/2019.
 */
public class SeccHousehold implements Serializable {

    private Integer state_code;
    private String state_name;
    private String state_name_sl;
    private Integer district_code;
    private String district_name;
    private String district_name_sl;
    private Integer sub_district_code;
    private String sub_district_name;
    private String sub_district_name_sl;
    private Integer block_code;
    private String block_name;
    private String block_name_sl;
    private Integer gp_code;
    private String gp_name;
    private String gp_name_sl;
    private Integer village_code;
    private String village_name;
    private String village_name_sl;
    private Integer enum_block_code;
    private String enum_block_name;
    private String enum_block_name_sl;
    private Integer hhd_uid;

    private String statecode;
    private String statename;
    private String districtcode;
    private String districtname;
    private String tehsilcode;
    private String tehsilname;
    private String towncode;
    private String townname;
    private String wardid;
    private String ahlblockno;
    private String ahlsubblockno;
    private String grampanchayatcode;
    private String grampanchayatname;

    private String ahl_family_tin;
    private String head_ahl_tin;
    private String ahlslnohhd;
    private String typeofhhd;

    private String head_name;
    private String head_fathername;
    private String head_mothername;
    private String head_occupation;
    private String addressline1;
    private String addressline2;
    private String addressline3;
    private String addressline4;
    private String addressline5;
    private String pincode;

    private String head_name_sl;
    private String head_fathername_sl;
    private String head_mothername_sl;
    private String head_occupation_sl;
    private String addressline1_sl;
    private String addressline2_sl;
    private String addressline3_sl;
    private String addressline4_sl;
    private String addressline5_sl;
    private String name_in_npr_image;
    private String dt_created;

    public SeccHousehold() {
    }

    public SeccHousehold(String head_name, String father_name, String mother_name, Integer hh_uid, String addressline1, String addressline2, String addressline3) {
        this.head_name=head_name;
        this.head_fathername=father_name;
        this.head_mothername=mother_name;
        this.hhd_uid=hh_uid;
        this.addressline1 = addressline1;
        this.addressline2 = addressline2;
        this.addressline3 = addressline3;
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

    public Integer getHhd_uid() {
        return hhd_uid;
    }

    public void setHhd_uid(Integer hhd_uid) {
        this.hhd_uid = hhd_uid;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public String getDistrictcode() {
        return districtcode;
    }

    public void setDistrictcode(String districtcode) {
        this.districtcode = districtcode;
    }

    public String getDistrictname() {
        return districtname;
    }

    public void setDistrictname(String districtname) {
        this.districtname = districtname;
    }

    public String getTehsilcode() {
        return tehsilcode;
    }

    public void setTehsilcode(String tehsilcode) {
        this.tehsilcode = tehsilcode;
    }

    public String getTehsilname() {
        return tehsilname;
    }

    public void setTehsilname(String tehsilname) {
        this.tehsilname = tehsilname;
    }

    public String getTowncode() {
        return towncode;
    }

    public void setTowncode(String towncode) {
        this.towncode = towncode;
    }

    public String getTownname() {
        return townname;
    }

    public void setTownname(String townname) {
        this.townname = townname;
    }

    public String getWardid() {
        return wardid;
    }

    public void setWardid(String wardid) {
        this.wardid = wardid;
    }

    public String getAhlblockno() {
        return ahlblockno;
    }

    public void setAhlblockno(String ahlblockno) {
        this.ahlblockno = ahlblockno;
    }

    public String getAhlsubblockno() {
        return ahlsubblockno;
    }

    public void setAhlsubblockno(String ahlsubblockno) {
        this.ahlsubblockno = ahlsubblockno;
    }

    public String getGrampanchayatcode() {
        return grampanchayatcode;
    }

    public void setGrampanchayatcode(String grampanchayatcode) {
        this.grampanchayatcode = grampanchayatcode;
    }

    public String getGrampanchayatname() {
        return grampanchayatname;
    }

    public void setGrampanchayatname(String grampanchayatname) {
        this.grampanchayatname = grampanchayatname;
    }

    public String getAhl_family_tin() {
        return ahl_family_tin;
    }

    public void setAhl_family_tin(String ahl_family_tin) {
        this.ahl_family_tin = ahl_family_tin;
    }

    public String getHead_ahl_tin() {
        return head_ahl_tin;
    }

    public void setHead_ahl_tin(String head_ahl_tin) {
        this.head_ahl_tin = head_ahl_tin;
    }

    public String getAhlslnohhd() {
        return ahlslnohhd;
    }

    public void setAhlslnohhd(String ahlslnohhd) {
        this.ahlslnohhd = ahlslnohhd;
    }

    public String getTypeofhhd() {
        return typeofhhd;
    }

    public void setTypeofhhd(String typeofhhd) {
        this.typeofhhd = typeofhhd;
    }

    public String getHead_name() {
        return head_name;
    }

    public void setHead_name(String head_name) {
        this.head_name = head_name;
    }

    public String getHead_fathername() {
        return head_fathername;
    }

    public void setHead_fathername(String head_fathername) {
        this.head_fathername = head_fathername;
    }

    public String getHead_mothername() {
        return head_mothername;
    }

    public void setHead_mothername(String head_mothername) {
        this.head_mothername = head_mothername;
    }

    public String getHead_occupation() {
        return head_occupation;
    }

    public void setHead_occupation(String head_occupation) {
        this.head_occupation = head_occupation;
    }

    public String getName_in_npr_image() {
        return name_in_npr_image;
    }

    public void setName_in_npr_image(String name_in_npr_image) {
        this.name_in_npr_image = name_in_npr_image;
    }

    public String getAddressline1() {
        return addressline1;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    public String getAddressline2() {
        return addressline2;
    }

    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }

    public String getAddressline3() {
        return addressline3;
    }

    public void setAddressline3(String addressline3) {
        this.addressline3 = addressline3;
    }

    public String getAddressline4() {
        return addressline4;
    }

    public void setAddressline4(String addressline4) {
        this.addressline4 = addressline4;
    }

    public String getAddressline5() {
        return addressline5;
    }

    public void setAddressline5(String addressline5) {
        this.addressline5 = addressline5;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getHead_name_sl() {
        return head_name_sl;
    }

    public void setHead_name_sl(String head_name_sl) {
        this.head_name_sl = head_name_sl;
    }

    public String getHead_fathername_sl() {
        return head_fathername_sl;
    }

    public void setHead_fathername_sl(String head_fathername_sl) {
        this.head_fathername_sl = head_fathername_sl;
    }

    public String getHead_mothername_sl() {
        return head_mothername_sl;
    }

    public void setHead_mothername_sl(String head_mothername_sl) {
        this.head_mothername_sl = head_mothername_sl;
    }

    public String getHead_occupation_sl() {
        return head_occupation_sl;
    }

    public void setHead_occupation_sl(String head_occupation_sl) {
        this.head_occupation_sl = head_occupation_sl;
    }

    public String getAddressline1_sl() {
        return addressline1_sl;
    }

    public void setAddressline1_sl(String addressline1_sl) {
        this.addressline1_sl = addressline1_sl;
    }

    public String getAddressline2_sl() {
        return addressline2_sl;
    }

    public void setAddressline2_sl(String addressline2_sl) {
        this.addressline2_sl = addressline2_sl;
    }

    public String getAddressline3_sl() {
        return addressline3_sl;
    }

    public void setAddressline3_sl(String addressline3_sl) {
        this.addressline3_sl = addressline3_sl;
    }

    public String getAddressline4_sl() {
        return addressline4_sl;
    }

    public void setAddressline4_sl(String addressline4_sl) {
        this.addressline4_sl = addressline4_sl;
    }

    public String getAddressline5_sl() {
        return addressline5_sl;
    }

    public void setAddressline5_sl(String addressline5_sl) {
        this.addressline5_sl = addressline5_sl;
    }

    public String getDt_created() {
        return dt_created;
    }

    public void setDt_created(String dt_created) {
        this.dt_created = dt_created;
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

    public String getEnum_block_name() {
        return enum_block_name;
    }

    public void setEnum_block_name(String enum_block_name) {
        this.enum_block_name = enum_block_name;
    }

    public String getState_name_sl() {
        return state_name_sl;
    }

    public void setState_name_sl(String state_name_sl) {
        this.state_name_sl = state_name_sl;
    }

    public String getDistrict_name_sl() {
        return district_name_sl;
    }

    public void setDistrict_name_sl(String district_name_sl) {
        this.district_name_sl = district_name_sl;
    }

    public String getSub_district_name_sl() {
        return sub_district_name_sl;
    }

    public void setSub_district_name_sl(String sub_district_name_sl) {
        this.sub_district_name_sl = sub_district_name_sl;
    }

    public String getBlock_name_sl() {
        return block_name_sl;
    }

    public void setBlock_name_sl(String block_name_sl) {
        this.block_name_sl = block_name_sl;
    }

    public String getGp_name_sl() {
        return gp_name_sl;
    }

    public void setGp_name_sl(String gp_name_sl) {
        this.gp_name_sl = gp_name_sl;
    }

    public String getVillage_name_sl() {
        return village_name_sl;
    }

    public void setVillage_name_sl(String village_name_sl) {
        this.village_name_sl = village_name_sl;
    }

    public String getEnum_block_name_sl() {
        return enum_block_name_sl;
    }

    public void setEnum_block_name_sl(String enum_block_name_sl) {
        this.enum_block_name_sl = enum_block_name_sl;
    }
}
