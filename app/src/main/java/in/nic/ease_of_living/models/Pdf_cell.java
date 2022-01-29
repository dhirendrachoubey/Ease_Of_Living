package in.nic.ease_of_living.models;

/**
 * Created by PC_126 on 9/11/2017.
 */
public class Pdf_cell {
    private String sno,cellLeft,cellMid,cellRight,distanceValue,gramSamiti_approval,gramSamiti_change_value, distanceValueBase;

    public Pdf_cell(String sn,String left, String mid, String distanceValueBase, String right,String distance,String gram_samiti_approval,String gram_samiti_changevalue) {
        sno=sn;
        cellLeft=left;
        cellMid=mid;
        this.distanceValueBase = distanceValueBase;
        cellRight=right;
        distanceValue=distance;
        gramSamiti_approval=gram_samiti_approval;
        gramSamiti_change_value=gram_samiti_changevalue;
    }

    public String getCellRight() {
        return cellRight;
    }

    public void setCellRight(String cellRight) {
        this.cellRight = cellRight;
    }

    public String getCellLeft() {
        return cellLeft;
    }

    public void setCellLeft(String cellLeft) {
        this.cellLeft = cellLeft;
    }

    public String getCellMid() {
        return cellMid;
    }

    public void setCellMid(String cellMid) {
        this.cellMid = cellMid;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getDistanceValue() {
        return distanceValue;
    }

    public void setDistanceValue(String distanceValue) {
        this.distanceValue = distanceValue;
    }

    public String getGramSamiti_approval() {
        return gramSamiti_approval;
    }

    public void setGramSamiti_approval(String gramSamiti_approval) {
        this.gramSamiti_approval = gramSamiti_approval;
    }

    public String getGramSamiti_change_value() {
        return gramSamiti_change_value;
    }

    public void setGramSamiti_change_value(String gramSamiti_change_value) {
        this.gramSamiti_change_value = gramSamiti_change_value;
    }

    public String getDistanceValueBase() {
        return distanceValueBase;
    }

    public void setDistanceValueBase(String distanceValueBase) {
        this.distanceValueBase = distanceValueBase;
    }
}
