package in.nic.ease_of_living.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Pattern;

import in.nic.ease_of_living.gp.R;

/**
 * Created by Neha Jain on 8/7/2015.
 */
public class Support {
    final static Pattern lastIntPattern = Pattern.compile("[^0-9]+([0-9]+)$");


    public static boolean isValidEmail(String email) {
        //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailPattern = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

        email = email.trim();
        if (email.matches(emailPattern)) {
            return true;
        }
        return false;
    }
    public static boolean isValidofficialEmail(String email) {
        if(email.isEmpty())
        {
            return true;
        }
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        return false;
    }

    public static boolean isValidName(String name) {
        String pattern = "([a-zA-Z]{3,30}\\s*)+";
        if (name.matches(pattern)) {
           // if(name.length()>=3 && name.length()<25)
                return true;
        }
        return false;
    }
 public static boolean isWhite_space(String name) {
     boolean isWhitespace = name.matches("^\\s*$");
     return isWhitespace;
    }

    public static boolean isValidConfirmPassword(String pwd, String cpwd) {
        if (pwd.matches(cpwd)) {
            return true;
        }
        return false;
    }

    public static int ENTER_FIRSTNAME=-1;
    public static int ENTER_LASTNAME=-2;
    public static int VALID_NAME=1;
    public static int isValidFullName(final String fullName) {
        String pattern = "([a-zA-Z]{3,30}\\s*)+";
        try {
            if(fullName.length()>=3) {
                String[] str = fullName.split(" ");
                if (str.length >= 2 && str[0].matches(pattern) && str[1].matches(pattern)) {
                    return VALID_NAME;
                } else if (str.length == 1) {
                    return ENTER_LASTNAME;
                }
            }else{
                return ENTER_FIRSTNAME;
            }

        } catch (Exception e) {

        }
        return ENTER_FIRSTNAME;
    }

    public static boolean isValidPassword(String pwd) {
        String pwdPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*?[#?!@$%^&*-])(\\w|[#?!@$%^&*-]){8,15}";
        if (pwd.matches(pwdPattern)) {
            if(pwd.length()>=8 && pwd.length()<=15)
                return true;
        }
        return false;
    }

    public static boolean isPasswordContainUserIdParts(final String user_id, final String pwd) {
        for(int i=0;(i+3) < user_id.length();i++)
            if(pwd.indexOf(user_id.substring(i,i+3))!=-1)
                return true;
        return false;
    }

    public static boolean isValidMobileNumber(String strMobileNo) {
        /*String phonePattern = "^[7-9][0-9]{9}$";
        if (mobileNo.matches(phonePattern)) {
            return true;
        }*/
        if(strMobileNo.length()==10)
            return true;
        return false;
    }
    public static boolean isValidContactNumber(String strMobileNo) {
        /*String phonePattern = "^[7-9][0-9]{9}$";
        if(mobileNo.isEmpty())
        {
            return true;
        }
        else if (mobileNo.matches(phonePattern)) {
            return true;
        }*/
        if(strMobileNo.isEmpty() || (strMobileNo.length()==0))
        {
            return true;
        }
        else if( (strMobileNo.length()>=8) && (strMobileNo.length() <= 15) )
            return true;
        return false;
    }
    public static boolean isValidpincode(String pincode) {
        String phonePattern ="^d{6}$";
        if(pincode.isEmpty())
        {
            return true;
        }
        else if (pincode.length()==6) {
            return true;
        }
        return false;
    }
    public static boolean isValidgender(String gender, String title) {
        boolean flag=false;
      switch(gender)
      {
          case "M":
              if(title.equalsIgnoreCase("Mr."))
                    flag=true;
              else
                    flag=false;
              break;
          case "F":
              if(title.equalsIgnoreCase("Mrs.")||title.equalsIgnoreCase("Miss"))
                  flag=true;
              else
                  flag=false;
              break;
          case "T":
              flag=true;
              break;
      }
        return flag;
    }


    public static void setChangeListener(final EditText et, final TextView tv) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    tv.setVisibility(View.GONE);
                } else {

                    tv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public static void setChangeListener(final EditText et, final LinearLayout ll) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    ll.setVisibility(View.GONE);
                } else {

                    ll.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public static void setRadioButtonClickListener(final RadioButton rb_yes, final RadioButton rb_no, final TextView tv, final LinearLayout ll)
    {
        rb_yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                tv.setVisibility(View.GONE);
                ll.setVisibility(View.GONE);
            }

        });

        rb_no.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                tv.setVisibility(View.VISIBLE);
                ll.setVisibility(View.VISIBLE);
            }

        });
    }

    public static void setShowHideTextViewAndLinearLayout(final TextView tv, final LinearLayout ll, final int n_visibility)
    {
        tv.setVisibility(n_visibility);
        ll.setVisibility(n_visibility);
    }

    public static void setRadioButtonfields(RadioButton rb, final TextView tv, final LinearLayout ll, final int n_visibility)
    {
        rb.setChecked(true);
        tv.setVisibility(n_visibility);
        ll.setVisibility(n_visibility);
    }

    public static void setSpinnerChangeListener(final Spinner sp, final TextView tv, final LinearLayout ll)
    {
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (sp.getSelectedItem().toString().equalsIgnoreCase("none"))
                {
                    tv.setVisibility(View.VISIBLE);
                    ll.setVisibility(View.VISIBLE);
                }
                else
                {
                    tv.setVisibility(View.GONE);
                    ll.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public static void setLinearLayoutHideShow(final ArrayList<LinearLayout> alLinearLayout,
                                               ArrayList<ImageView> alImageView,
                                               final LinearLayout ll_select,
                                               final ImageView iv_select
                                                )
    {
        for(int count = 0;count < alLinearLayout.size(); count ++)
        {
            alLinearLayout.get(count).setVisibility(View.GONE);
            alImageView.get(count).setImageResource(R.mipmap.icon_plus);
        }
        ll_select.setVisibility(View.VISIBLE);
        iv_select.setImageResource(R.mipmap.icon_minus);
        TextView tv;
        final int childCount = ll_select.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = ll_select.getChildAt(i);
            if(v instanceof TextView){
                tv = (TextView) v;
                tv.setFocusable(true);
                i = childCount;
            }
        }   // end for
    }

    public static void setLinearLayoutHideShowIncludingPartAB(final ArrayList<LinearLayout> alLinearLayout,
                                                              ArrayList<ImageView> alImageView,
                                                              final LinearLayout ll_select,
                                                              final ImageView iv_select,
                                                              final ArrayList<LinearLayout> alLinearLayout2,
                                                              ArrayList<ImageView> alImageView2,
                                                              final LinearLayout ll_select2,
                                                              final ImageView iv_select2)
    {
        setLinearLayoutHideShow(alLinearLayout2, alImageView2, ll_select2, iv_select2);
        setLinearLayoutHideShow(alLinearLayout, alImageView, ll_select, iv_select);
    }

    public static String getAge(int year, int month, int day){
        AgeCalculation age=new AgeCalculation();
        age.getCurrentDate();
        age.setDateOfBirth(year,month,day);
        age.calcualteYear();
        age.calcualteMonth();
        age.calcualteDay();
        return age.getResult();
    }


}
