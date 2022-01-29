package in.nic.ease_of_living.models;

/**
 * Created by Neha Jain on 4/11/2018.
 */

public class Group {

    private String display_order,group_desc,group_id,group_name;

    public Group() {
    }

    public Group(String string) {
        this.group_name = string;
        group_id="0";
    }

    public String getDisplay_order() {
        return display_order;
    }

    public void setDisplay_order(String display_order) {
        this.display_order = display_order;
    }

    public String getGroup_desc() {
        return group_desc;
    }

    public void setGroup_desc(String group_desc) {
        this.group_desc = group_desc;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
}
