package in.nic.ease_of_living.models;

/**
 * Created by Neha Jain on 4/17/2018.
 */

public class State {
    private String state_code, state_name, state_name_sl,state_name_nl;;

    public State()
    {
    }
    public State(String name)
    {
        this.state_name=name;
        state_code="0";
    }
    public State(String state_code,String name)
    {
        this.state_name=name;
        this.state_code=state_code;
    }

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public String getState_name() {
        /*if ( (MySharedPref.getStrLanguage()!=null) && !(MySharedPref.getStrLanguage().equalsIgnoreCase("en"))
                && (state_name_sl != null) && (state_name_sl.trim().length() != 0) )
        {
            return state_name_sl;
        }
        else {
            return state_name;
        }*/
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getState_name_sl() {
        return state_name_sl;
    }

    public void setState_name_sl(String state_name_sl) {
        this.state_name_sl = state_name_sl;
    }

    public String getState_name_nl() {
        return state_name_nl;
    }

    public void setState_name_nl(String state_name_nl) {
        this.state_name_nl = state_name_nl;
    }
}
