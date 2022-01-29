package in.nic.ease_of_living.models;

/**
 * Created by Chinki on 6/23/2017.
 */
public class Roles {
    private String role_description, role_type, dt_created_on;
    private boolean is_enabled;
    private int role_priorty;
    public Roles()
    {

    }
    public Roles(String name)
    {
        this.role_description=name;
    }
    public void setRole_description(String str)
    {
        role_description=str;
    }
    public String getRole_description()
    {
        return role_description;
    }
    public void setRole_type(String str)
    {
        role_type=str;
    }
    public String getRole_type()
    {
        return role_type;
    }
}

