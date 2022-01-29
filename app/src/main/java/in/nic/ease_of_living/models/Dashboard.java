package in.nic.ease_of_living.models;

/**
 * Created by New on 8/May/2019.
 */

public class Dashboard {

    private Integer state_code ;
    private Integer district_code ;
    private Integer sub_district_code ;
    private Integer block_code ;
    private Integer gp_code ;
    private Integer village_code ;
    private Integer total_hhd_secc ;                // secc_total_household
    private Integer total_hhd_uncovered ;         // secc_household_uncovered
    private Integer total_hhd_covered ;              // secc_household_uploaded
    private Integer total_hhd_nw ;                  // new_household_uploaded
    private Integer total_hhd_nw_count ;                  // new_household_total
    private Integer total_hhd ;                     // total_household_uploaded
    private Integer total_hhd_pending ;             //household_pending_upload
    private Integer total_member_secc ;             // secc_total_pop
    private Integer total_member_uncovered ;         //secc_pop_uncovered
    private Integer total_member_covered ;              //secc_pop_uploaded
    private Integer total_member_av ;
    private Integer total_member_de ;
    private Integer total_member_mo ;
    private Integer total_member_nw ;                   // new_member_uploaded
    private Integer total_member_nw_count ;             // new_member_total
    private Integer total_member ;                      // total_pop
    private Integer total_member_pending;

    public Dashboard() {
        this.state_code = null;
        this.district_code = null;
        this.sub_district_code = null;
        this.block_code = null;
        this.gp_code = null;
        this.village_code = null;
        this.total_hhd_secc = null;
        this.total_hhd_uncovered = null;
        this.total_hhd_covered = null;
        this.total_hhd_nw = null;
        this.total_hhd_nw_count = null;
        this.total_hhd = null;
        this.total_hhd_pending = null;
        this.total_member_secc = null;
        this.total_member_uncovered = null;
        this.total_member_covered = null;
        this.total_member_av = null;
        this.total_member_de = null;
        this.total_member_mo = null;
        this.total_member_nw = null;
        this.total_member_nw_count = null;
        this.total_member = null;
        this.total_member_pending = null;
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

    public Integer getTotal_hhd_secc() {
        return total_hhd_secc;
    }

    public void setTotal_hhd_secc(Integer total_hhd_secc) {
        this.total_hhd_secc = total_hhd_secc;
    }

    public Integer getTotal_hhd_uncovered() {
        return total_hhd_uncovered;
    }

    public void setTotal_hhd_uncovered(Integer total_hhd_uncovered) {
        this.total_hhd_uncovered = total_hhd_uncovered;
    }

    public Integer getTotal_hhd_covered() {
        return total_hhd_covered;
    }

    public void setTotal_hhd_covered(Integer total_hhd_covered) {
        this.total_hhd_covered = total_hhd_covered;
    }

    public Integer getTotal_hhd_nw() {
        return total_hhd_nw;
    }

    public void setTotal_hhd_nw(Integer total_hhd_nw) {
        this.total_hhd_nw = total_hhd_nw;
    }

    public Integer getTotal_hhd_nw_count() {
        return total_hhd_nw_count;
    }

    public void setTotal_hhd_nw_count(Integer total_hhd_nw_count) {
        this.total_hhd_nw_count = total_hhd_nw_count;
    }

    public Integer getTotal_hhd() {
        return total_hhd;
    }

    public void setTotal_hhd(Integer total_hhd) {
        this.total_hhd = total_hhd;
    }

    public Integer getTotal_hhd_pending() {
        return total_hhd_pending;
    }

    public void setTotal_hhd_pending(Integer total_hhd_pending) {
        this.total_hhd_pending = total_hhd_pending;
    }

    public Integer getTotal_member_secc() {
        return total_member_secc;
    }

    public void setTotal_member_secc(Integer total_member_secc) {
        this.total_member_secc = total_member_secc;
    }

    public Integer getTotal_member_uncovered() {
        return total_member_uncovered;
    }

    public void setTotal_member_uncovered(Integer total_member_uncovered) {
        this.total_member_uncovered = total_member_uncovered;
    }

    public Integer getTotal_member_covered() {
        return total_member_covered;
    }

    public void setTotal_member_covered(Integer total_member_covered) {
        this.total_member_covered = total_member_covered;
    }

    public Integer getTotal_member_av() {
        return total_member_av;
    }

    public void setTotal_member_av(Integer total_member_av) {
        this.total_member_av = total_member_av;
    }

    public Integer getTotal_member_de() {
        return total_member_de;
    }

    public void setTotal_member_de(Integer total_member_de) {
        this.total_member_de = total_member_de;
    }

    public Integer getTotal_member_mo() {
        return total_member_mo;
    }

    public void setTotal_member_mo(Integer total_member_mo) {
        this.total_member_mo = total_member_mo;
    }

    public Integer getTotal_member_nw() {
        return total_member_nw;
    }

    public void setTotal_member_nw(Integer total_member_nw) {
        this.total_member_nw = total_member_nw;
    }

    public Integer getTotal_member_nw_count() {
        return total_member_nw_count;
    }

    public void setTotal_member_nw_count(Integer total_member_nw_count) {
        this.total_member_nw_count = total_member_nw_count;
    }

    public Integer getTotal_member() {
        return total_member;
    }

    public void setTotal_member(Integer total_member) {
        this.total_member = total_member;
    }

    public Integer getTotal_member_pending() {
        return total_member_pending;
    }

    public void setTotal_member_pending(Integer total_member_pending) {
        this.total_member_pending = total_member_pending;
    }
}