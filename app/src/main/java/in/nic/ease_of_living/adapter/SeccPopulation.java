package in.nic.ease_of_living.adapter;

import java.io.Serializable;

/**
 * Created by Neha Jain on 20/July/2019.
 */
public class SeccPopulation implements Serializable {

    private Integer state_code;
    private Integer district_code;
    private Integer sub_district_code;
    private Integer block_code;
    private Integer gp_code;
    private Integer village_code;
    private Integer enum_block_code;
    private Integer hhd_uid;

    private String member_sl_no;
    private String ahl_tin;
    private String name;
    private String relation;
    private String genderid;
    private String dob;
    private String age;
    private String marital_status;
    private String father_name;
    private String mother_name;
    private String occupation;
    private String name_sl;
    private String relation_sl;
    private String father_name_sl;
    private String mother_name_sl;
    private String occupation_sl;
    private String dt_created;


    public SeccPopulation() {
    }

    public SeccPopulation(String person_name, String father_name, String mother_name, String dob, Integer hh_uid, String genderid) {
        this.name=person_name;
        this.father_name=father_name;
        this.mother_name=mother_name;
        this.dob=dob;
        this.hhd_uid=hh_uid;
        this.genderid=genderid;

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

    public String getMember_sl_no() {
        return member_sl_no;
    }

    public void setMember_sl_no(String member_sl_no) {
        this.member_sl_no = member_sl_no;
    }

    public String getAhl_tin() {
        return ahl_tin;
    }

    public void setAhl_tin(String ahl_tin) {
        this.ahl_tin = ahl_tin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getGenderid() {
        return genderid;
    }

    public void setGenderid(String genderid) {
        this.genderid = genderid;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getMother_name() {
        return mother_name;
    }

    public void setMother_name(String mother_name) {
        this.mother_name = mother_name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getName_sl() {
        return name_sl;
    }

    public void setName_sl(String name_sl) {
        this.name_sl = name_sl;
    }

    public String getRelation_sl() {
        return relation_sl;
    }

    public void setRelation_sl(String relation_sl) {
        this.relation_sl = relation_sl;
    }

    public String getFather_name_sl() {
        return father_name_sl;
    }

    public void setFather_name_sl(String father_name_sl) {
        this.father_name_sl = father_name_sl;
    }

    public String getMother_name_sl() {
        return mother_name_sl;
    }

    public void setMother_name_sl(String mother_name_sl) {
        this.mother_name_sl = mother_name_sl;
    }

    public String getOccupation_sl() {
        return occupation_sl;
    }

    public void setOccupation_sl(String occupation_sl) {
        this.occupation_sl = occupation_sl;
    }

    public String getDt_created() {
        return dt_created;
    }

    public void setDt_created(String dt_created) {
        this.dt_created = dt_created;
    }
    
}
