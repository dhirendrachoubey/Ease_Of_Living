package in.nic.ease_of_living.supports;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Locale;

import in.nic.ease_of_living.models.AuthorizationToken;
import in.nic.ease_of_living.models.SeccPopulation;
import in.nic.ease_of_living.models.User;
import in.nic.ease_of_living.models.Weburl;

/**
 * Created by Neha Jain on 6/21/2017.
 */
public class MySharedPref {
    private static SharedPreferences spref;
    private static SharedPreferences.Editor editor;
    private static String strLanguage;

    public static void clearSharedPref(Context context) {
        saveCurrentUser(context,null);
        saveAuthorizationToken(context,null);
        saveDevice_ID(context,null);
        saveDeviceKey(context,null);
        saveIsInstallationMsgSeen(context,false);
        saveIsBaseDataAcknowledgePending(context,false);
        //saveCommonGpSurvey(context, null);
    }

    public static void saveCurrentUser(Context context, User user){
        try{
            spref=context.getSharedPreferences("User", Context.MODE_PRIVATE);
            editor=spref.edit();
            String json=new Gson().toJson(user);
            editor.putString("object", json);
            editor.apply();
        }catch (Exception e){

        }
    }

    public static User getCurrentUser(Context context){
        try{
            spref=context.getSharedPreferences("User", Context.MODE_PRIVATE);
            String json = spref.getString("object", null);
            return  new Gson().fromJson(json, User.class);
        }catch (Exception e){

            return null;
        }
    }

    public static void saveAuthorizationToken(Context context, AuthorizationToken token){
        try{
            spref=context.getSharedPreferences("AuthorizationToken", Context.MODE_PRIVATE);
            editor=spref.edit();
            String json=new Gson().toJson(token);
            editor.putString("object", json);
            editor.apply();
        }catch (Exception e){

        }
    }

    public static AuthorizationToken getAuthorizationToken(Context context){
        try{
            spref=context.getSharedPreferences("AuthorizationToken", Context.MODE_PRIVATE);
            String json = spref.getString("object", null);
            return  new Gson().fromJson(json, AuthorizationToken.class);
        }catch (Exception e){

            return null;
        }
    }

    public static void saveDevice_ID(Context context, String id){
        try{
            spref=context.getSharedPreferences("Device_ID", Context.MODE_PRIVATE);
            editor=spref.edit();
            editor.putString("ID", id);
            editor.apply();
        }catch (Exception e){

        }
    }



    public static String getDeviceId(Context context){
        try{
            spref=context.getSharedPreferences("Device_ID", Context.MODE_PRIVATE);
            String status = spref.getString("ID", null);
            return  status;
        }catch (Exception e){

            return null;
        }
    }

    public static void saveDeviceKey(Context context, String key){
        try{
            spref=context.getSharedPreferences("DEVICE_KEY", Context.MODE_PRIVATE);
            editor=spref.edit();
            editor.putString("KEY", key);
            editor.apply();
        }catch (Exception e){

        }
    }

    public static String getDeviceKey(Context context){
        try{
            spref=context.getSharedPreferences("DEVICE_KEY", Context.MODE_PRIVATE);
            String status = spref.getString("KEY", null);
            return  status;
        }catch (Exception e){

            return null;
        }
    }

    public static void saveMacAddress(Context context, String key){
        try{
            spref=context.getSharedPreferences("MAC_ADDRESS", Context.MODE_PRIVATE);
            editor=spref.edit();
            editor.putString("KEY", key);
            editor.apply();
        }catch (Exception e){

        }
    }

    public static String getMacAddress(Context context){
        try{
            spref=context.getSharedPreferences("MAC_ADDRESS", Context.MODE_PRIVATE);
            String status = spref.getString("KEY", null);
            return  status;
        }catch (Exception e){
            return null;
        }
    }

    public static void saveImei(Context context, String key){
        try{
            spref=context.getSharedPreferences("IMEI", Context.MODE_PRIVATE);
            editor=spref.edit();
            editor.putString("KEY", key);
            editor.apply();
        }catch (Exception e){

        }
    }

    public static String getImei(Context context){
        try{
            spref=context.getSharedPreferences("IMEI", Context.MODE_PRIVATE);
            String status = spref.getString("KEY", null);
            return  status;
        }catch (Exception e){

            return null;
        }
    }

    public static void saveLocaleLanguage(Context context, Locale locale){
        try{
            spref=context.getSharedPreferences("Locale", Context.MODE_PRIVATE);
            editor=spref.edit();
            editor.putString("LOCALE", locale.getLanguage());
            strLanguage = locale.getLanguage();
            editor.apply();
        }catch (Exception e){

        }
    }

    public static String getLocaleLanguage(Context context){
        try{
            spref=context.getSharedPreferences("Locale", Context.MODE_PRIVATE);
            String status = spref.getString("LOCALE", null);
            return  status;
        }catch (Exception e){
            return null;
        }
    }

    public static void saveIsGpDataValidated(Context context, Boolean b_val){
        try{
            spref=context.getSharedPreferences("IS_GP_DATA_VALIDATED", Context.MODE_PRIVATE);
            editor=spref.edit();
            editor.putBoolean("KEY", b_val);
            editor.apply();
        }catch (Exception e){

        }
    }

    public static Boolean getIsGpDataValidated(Context context){
        try{
            spref=context.getSharedPreferences("IS_GP_DATA_VALIDATED", Context.MODE_PRIVATE);
            Boolean b_val = spref.getBoolean("KEY", false);
            return  b_val;
        }catch (Exception e){

            return null;
        }
    }

    public static void saveIsInstallationMsgSeen(Context context, Boolean b_val){
        try{
            spref=context.getSharedPreferences("IS_INSTALLATION_MSG_SEEN", Context.MODE_PRIVATE);
            editor=spref.edit();
            editor.putBoolean("KEY", b_val);
            editor.apply();
        }catch (Exception e){

        }
    }

    public static Boolean getIsInstallationMsgSeen(Context context){
        try{
            spref=context.getSharedPreferences("IS_INSTALLATION_MSG_SEEN", Context.MODE_PRIVATE);
            Boolean b_val = spref.getBoolean("KEY", false);
            return  b_val;
        }catch (Exception e){

            return null;
        }
    }

    public static Boolean getIsBaseDataAcknowledgePending(Context context){
        try{
            spref=context.getSharedPreferences("IsBaseDataAcknowledgePending", Context.MODE_PRIVATE);
            Boolean status = spref.getBoolean("KEY", false);
            return  status;
        }catch (Exception e){

            return false;
        }
    }
    public static void saveIsBaseDataAcknowledgePending(Context context, Boolean key){
        try{
            spref=context.getSharedPreferences("IsBaseDataAcknowledgePending", Context.MODE_PRIVATE);
            editor=spref.edit();
            editor.putBoolean("KEY", key);
            editor.apply();
        }catch (Exception e){

        }
    }

    public static String getStrLanguage() {
        return strLanguage;
    }

    public static void setStrLanguage(String strLanguage) {
        MySharedPref.strLanguage = strLanguage;
    }

    public static Boolean getIsSharedPrefCleared(Context context){
        try{
            spref=context.getSharedPreferences("IsSharedPrefCleared", Context.MODE_PRIVATE);
            Boolean status = spref.getBoolean("KEY", false);
            return  status;
        }catch (Exception e){

            return false;
        }
    }

    public static void saveIsSharedPrefCleared(Context context, Boolean key){
        try{
            spref=context.getSharedPreferences("IsSharedPrefCleared", Context.MODE_PRIVATE);
            editor=spref.edit();
            editor.putBoolean("KEY", key);
            editor.apply();
        }catch (Exception e){

        }
    }

    public static void saveAppMode(Context context, String strAppMode){
        try{
            spref=context.getSharedPreferences("APP_MODE", Context.MODE_PRIVATE);
            editor=spref.edit();
            editor.putString("KEY", strAppMode);
            editor.apply();
        }catch (Exception e){

        }
    }

    public static String getAppMode(Context context){
        try{
            spref=context.getSharedPreferences("APP_MODE", Context.MODE_PRIVATE);
            String status = spref.getString("KEY", null);
            return  status;
        }catch (Exception e){

            return null;
        }
    }

    public static void saveWebUrl(Context context, ArrayList<Weburl> alWebUrl){
        try{
            spref=context.getSharedPreferences("WEB_URL", Context.MODE_PRIVATE);
            editor=spref.edit();
            editor.putString("KEY", new Gson().toJson(alWebUrl));
            editor.apply();
        }catch (Exception e){

        }
    }

    public static ArrayList<Weburl> getWebUrl(Context context){
        try{
            spref=context.getSharedPreferences("WEB_URL", Context.MODE_PRIVATE);
            String status = spref.getString("KEY", null);
            return  new Gson().fromJson(status, new TypeToken<ArrayList<Weburl>>() {}.getType());
        }catch (Exception e){

            return null;
        }
    }

    /*public static void saveCommonGpSurvey(Context context, GpVillageSurveyCommon gp){
        try{
            spref=context.getSharedPreferences("GpVillageSurveyCommon", Context.MODE_PRIVATE);
            editor=spref.edit();
            String json=new Gson().toJson(gp);
            editor.putString("object", json);
            editor.apply();
        }catch (Exception e){

        }
    }

    public static GpVillageSurveyCommon getCommonGpSurvey(Context context){
        try{
            spref=context.getSharedPreferences("GpVillageSurveyCommon", Context.MODE_PRIVATE);
            String json = spref.getString("object", null);
            return  new Gson().fromJson(json, GpVillageSurveyCommon.class);
        }catch (Exception e){

            return null;
        }
    }*/

    public static void saveFirebaseTokenId(Context context, String key){
        try{
            spref=context.getSharedPreferences("FIREBASE_ID", Context.MODE_PRIVATE);
            editor=spref.edit();
            editor.putString("KEY", key);
            editor.apply();
        }catch (Exception e){

        }
    }

    public static String getFirebaseTokenId(Context context){
        try{
            spref=context.getSharedPreferences("FIREBASE_ID", Context.MODE_PRIVATE);
            String status = spref.getString("KEY", null);
            return  status;
        }catch (Exception e){

            return null;
        }
    }

    public static void saveImageFlag(Context context, String id){
        try{
            spref=context.getSharedPreferences("IMAGE_FLAG", Context.MODE_PRIVATE);
            editor=spref.edit();
            editor.putString("IMAGE_FLAG", id);
            editor.apply();
        }catch (Exception e){

        }
    }



    public static String getImageFlag(Context context){
        try{
            spref=context.getSharedPreferences("IMAGE_FLAG", Context.MODE_PRIVATE);
            String status = spref.getString("IMAGE_FLAG", null);
            return  status;
        }catch (Exception e){

            return null;
        }
    }
    public static User getAssignedHH(Context context){
        try{
            spref=context.getSharedPreferences("User", Context.MODE_PRIVATE);
            String json = spref.getString("object", null);
            return  new Gson().fromJson(json, User.class);
        }catch (Exception e){

            return null;
        }
    }

    public static void saveHnHeadName(Context context, String key){
        try{
            spref=context.getSharedPreferences("HH_HEAD_NAME", Context.MODE_PRIVATE);
            editor=spref.edit();
            editor.putString("KEY", key);
            editor.apply();
        }catch (Exception e){

        }
    }
    public static void saveTotal(Context context, String value){
        try{
            spref=context.getSharedPreferences("RECORDS", Context.MODE_PRIVATE);
            editor=spref.edit();
            editor.putString("TOTAL", value);
            editor.apply();
        }catch (Exception e){

        }
    }

    public static String getTotal(Context context){
        try{
            spref=context.getSharedPreferences("RECORDS", Context.MODE_PRIVATE);
            String status = spref.getString("TOTAL", null);
            return  status;
        }catch (Exception e){

            return null;
        }
    }

    public static void saveHeadofhouse(Context context, SeccPopulation user){
        try{
            spref=context.getSharedPreferences("Head", Context.MODE_PRIVATE);
            editor=spref.edit();
            String json=new Gson().toJson(user);
            editor.putString("HEAD", json);
            editor.apply();
        }catch (Exception e){

        }
    }

    public static SeccPopulation getHeadofhouse(Context context){
        try{
            spref=context.getSharedPreferences("Head", Context.MODE_PRIVATE);
            String json = spref.getString("HEAD", null);
            return  new Gson().fromJson(json, SeccPopulation.class);
        }catch (Exception e){

            return null;
        }
    }
    public static void saveEnuUser(Context context, User user){
        try{
            spref=context.getSharedPreferences("ENU_USER", Context.MODE_PRIVATE);
            editor=spref.edit();
            String json=new Gson().toJson(user);
            editor.putString("object", json);
            editor.apply();
        }catch (Exception e){

        }
    }

    public static User getEnuUser(Context context){
        try{
            spref=context.getSharedPreferences("ENU_USER", Context.MODE_PRIVATE);
            String json = spref.getString("object", null);
            return  new Gson().fromJson(json, User.class);
        }catch (Exception e){

            return null;
        }
    }
    public static String getDevice_ID(Context context){
        try{
            spref=context.getSharedPreferences("Device_ID", Context.MODE_PRIVATE);
            String status = spref.getString("ID", null);
            return  status;
        }catch (Exception e){

            return null;
        }
    }
    public static int getPhoneFlag(Context context){
        try{
            spref=context.getSharedPreferences("Phone_Flag", Context.MODE_PRIVATE);
            int status = spref.getInt("PhoneFlag", 0);
            return  status;
        }catch (Exception e){

            return 0;
        }
    }

    public static void savePhoneFlag(Context context, int phoneFlag){
        try{
            spref=context.getSharedPreferences("Phone_Flag", Context.MODE_PRIVATE);
            editor=spref.edit();
            editor.putInt("PhoneFlag", phoneFlag);
            editor.apply();
        }catch (Exception e){

        }
    }



    public static String getHnHeadName(Context context){
        try{
            spref=context.getSharedPreferences("HH_HEAD_NAME", Context.MODE_PRIVATE);
            String status = spref.getString("KEY", "");
            return  status;
        }catch (Exception e){

            return null;
        }
    }

    public static void saveIsGetDataDone(Context context, Boolean strAppMode){
        try{
            spref=context.getSharedPreferences("APP_MODE", Context.MODE_PRIVATE);
            editor=spref.edit();
            editor.putBoolean("KEY", strAppMode);
            editor.apply();
        }catch (Exception e){

        }
    }

    public static Boolean getIsGetDataDone(Context context){
        try{
            spref=context.getSharedPreferences("APP_MODE", Context.MODE_PRIVATE);
            Boolean status = spref.getBoolean("KEY", false);
            return  status;
        }catch (Exception e){

            return null;
        }
    }

    public static void saveIsEnumBlockMarkCompleted(Context context, Boolean isMarkComplete){
        try{
            spref=context.getSharedPreferences("ENUM_BLOCK_MARK_COMPLETE", Context.MODE_PRIVATE);
            editor=spref.edit();
            editor.putBoolean("KEY", isMarkComplete);
            editor.apply();
        }catch (Exception e){

        }
    }

    public static Boolean getIsEnumBlockMarkCompleted(Context context){
        try{
            spref=context.getSharedPreferences("ENUM_BLOCK_MARK_COMPLETE", Context.MODE_PRIVATE);
            Boolean status = spref.getBoolean("KEY", false);
            return  status;
        }catch (Exception e){

            return null;
        }
    }


}
