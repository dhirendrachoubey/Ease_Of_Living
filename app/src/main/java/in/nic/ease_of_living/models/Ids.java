package in.nic.ease_of_living.models;

/**
 * Created by Neha Jain on 6/26/2017.
 */
public class Ids {
    private String identity_id,identity_type;
    public Ids()
    {
        identity_type="";
        identity_id="0";
    }

    public Ids(String name)
    {
        this.identity_type=name;
        identity_id="0";
    }

    public String getIdentity_id() {
        return identity_id;
    }

    public void setIdentity_id(String identity_id) {
        this.identity_id = identity_id;
    }

    public String getIdentity_type() {
        return identity_type;
    }

    public void setIdentity_type(String identity_type) {
        this.identity_type = identity_type;
    }
}
