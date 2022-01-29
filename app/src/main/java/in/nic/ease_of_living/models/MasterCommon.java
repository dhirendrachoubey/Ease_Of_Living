package in.nic.ease_of_living.models;

/**
 * Created by Neha Jain on 25/7/2017.
 */
public class MasterCommon {

    private int type_code;
    private String type_name;

    public MasterCommon()
    {
    }

    public MasterCommon(String strName)
    {
        this.type_name = strName;
    }

    public MasterCommon(int nCode, String strName)
    {
        this.type_code = nCode;
        this.type_name = strName;
    }

    public int getType_code() {
        return type_code;
    }

    public void setType_code(int type_code) {
        this.type_code = type_code;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }
}
