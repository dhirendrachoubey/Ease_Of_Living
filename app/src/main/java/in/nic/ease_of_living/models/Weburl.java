package in.nic.ease_of_living.models;

/**
 * Created by Neha Jain on 4/11/2018.
 */

public class Weburl {

    private String conf_key, conf_value;

    public Weburl() {
    }

    public Weburl(String string) {
        this.conf_key = string;
        conf_value="";
    }

    public String getConf_key() {
        return conf_key;
    }

    public void setConf_key(String conf_key) {
        this.conf_key = conf_key;
    }

    public String getConf_value() {
        return conf_value;
    }

    public void setConf_value(String conf_value) {
        this.conf_value = conf_value;
    }
}
