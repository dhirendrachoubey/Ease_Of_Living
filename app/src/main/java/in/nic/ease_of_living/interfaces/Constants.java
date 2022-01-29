package in.nic.ease_of_living.interfaces;

import android.os.Environment;

/**
 * Created by Neha Jain on 6/20/2017.
 */
public class Constants {
    public static String DOMAIN= "https://10.199.89.74:8443/marestwebservice/";          /* Angad*/
    /*public static String DOMAIN_TRAINING= "https://10.199.89.74:8443/marestwebservice/";          // Training
    public static String DOMAIN_MAIN= "https://10.199.89.74:8443/marestwebservice/";           //Main
*/
    /*public static String DOMAIN_TRAINING= "http://10.199.89.99:5151/marestwebservice/";           //Training
    public static String DOMAIN_MAIN= "http://10.199.89.99:5151/marestwebservice/";           //Main
*/
    /*public static String DOMAIN_TRAINING= "https://services-ma.nic.in/training/";           //Training
    public static String DOMAIN_MAIN= "https://services-ma.nic.in/training/";           //Training
    *///public static String DOMAIN_MAIN= "https://services-ma.nic.in/marestwebservice/";           //Main

    //https://services-ma.nic.in/training2/swagger-ui.html
    //Live
   /* public static String DOMAIN_TRAINING= "https://services-ma.nic.in/training2/";
    public static String DOMAIN_MAIN= "https://services-ma.nic.in/mainapi2019/";
*/
    public static String DOMAIN_TRAINING= "http://10.199.89.73/eolrestapi/";
    public static String DOMAIN_MAIN= "http://10.199.89.73/eolrestapi/";

/*
    public static String DOMAIN_TRAINING= "https://secc2011.nic.in/eolrestapi/";
  public static String DOMAIN_MAIN= "https://secc2011.nic.in/eolrestapi/";
*/

  //local Training1
   /* public static String DOMAIN_TRAINING= "http://10.199.89.99:5151/mainapi2019/";
    public static String DOMAIN_MAIN= "http://10.199.89.99:5151/mainapi2019/";
  */  //local Training2
 /*public static String DOMAIN_TRAINING= "http://10.199.89.99:5151/training2/";
 public static String DOMAIN_MAIN= "http://10.199.89.99:5151/mainapi2019/";
*//**/    //Main

  /*  public static String DOMAIN_TRAINING= "http://10.199.89.73/mainapi2019/";
    public static String DOMAIN_MAIN= "http://10.199.89.73/mainapi2019/";
*/
//Training
    //public static String DOMAIN_MAIN= "https://services-ma.nic.in/training2/";           //Main

    //public static String DOMAIN_TRAINING= "https://services-ma.nic.in/trainingapi2019/";           //Training

    /*public static String DOMAIN_TRAINING= "http://10.199.89.73/marestwebservicev3/";           //Training
    public static String DOMAIN_MAIN= "http://10.199.89.73/marestwebservicev3/";           //Main
    */
    /*public static String DOMAIN_TRAINING= "http://10.199.89.99:8080/training/";           //Training
    public static String DOMAIN_MAIN= "http://10.199.89.99:8080/training/";           //Main
*/
    public static String DOMAIN_EXTENDEDSERVICES= "https://services-ma.nic.in/serviceextended/";          /* service extended*/

  public static String APP_EDITION="Server";
  public static Boolean IS_INSTALLATION_MSG_SEEN=false;

    /* Firebase constants*/
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";


    public static String COMMON="common/v1/";
    public static String LOGIN="login/v1/";
    public static String MASTER="master/v1/";
    public static String NOTIFICATION="notification/v1/";
    public static String OTP="otp/v1/";
    public static String REGISTRATION="registration/v1/";
    public static String PROFILE="profile/v1/";
    public static String UM="user/management/v1/";
    public static String SR="shuvdata/v1/";
    public static String DASHBOARD="dashboard/v1/";
    public static String LMR="location/management/v1/";
    public static String MISC="misc/v1/";
    public static String ASSIGNMENT="assignment/v1/";
    public static String DOWNLOAD="download/v1/";
  public static String FOLDER_IMPORT = Environment.getExternalStorageDirectory() + "/" + "eol" + "/" + "eol_import";




}
