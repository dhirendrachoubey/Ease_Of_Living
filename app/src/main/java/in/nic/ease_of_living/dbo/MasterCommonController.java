package in.nic.ease_of_living.dbo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.HashMap;

import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.models.MasterCommon;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MySharedPref;


/**
 * Created by Neha Jain on 17/Aug/2017.
 */
//023-010
public class MasterCommonController {

    private static final String CODE = "type_code";
    private static final String NAME = "type_name";
    private static final String TYPE_LANGUAGE = "type_language";
    private static final String TYPE_CATEGORY = "type_category";
    public static final HashMap<Integer, String> map_tableName;
    private static String strLanguageToLoad="en";
    public  static  String TAG = "MasterCommonController";
    static
    {
        map_tableName = new HashMap<Integer, String>()
        {{
            put(1, "lpg_connection_scheme_m");
            put(2, "lpg_application_status_m");
            put(3, "led_scheme_m");
            put(4, "bank_ac_type_m");
            put(5, "lic_type_m");
            put(6, "accidental_cover_type_m");
            put(7, "immunisation_source_m");
            put(8, "nutrition_supp_services_source_m");
            put(9, "health_services_source_m");
            put(10, "preschool_edu_services_source_m");
            put(11, "shg_type_m");
            put(12, "house_type_m");
            put(13, "housing_scheme_m");
            put(14, "housing_scheme_application_status_m");
            put(15, "health_scheme_m");
            put(16, "old_age_pension_source_m");
            put(17, "widow_pension_source_m");
            put(18, "disabled_pension_source_m");
            put(19, "mobile_contact_type_m");
            put(20, "food_security_scheme_m");
            put(21, "mgnrega_job_card_status_m");
            put(22, "pension_scheme_m");
            put(23, "skill_development_schemes_m");
            put(24, "uncovered_hhd_reason_m");



        }};
    }

    public static HashMap<Integer, String> mapLpgConnectionSchemeCategory= new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapLpgApplicationStatusCategory= new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapLedSchemeCategory = new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapBankAcTypeCategory = new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapLicTypeCategory= new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapAccidentalCoverTypeCategory = new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapImmunisationSourceCategory = new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapNutritionSuppServicesSourceCategory= new HashMap<Integer, String>();

    public static HashMap<Integer, String> mapHealthServicesSourceCategory = new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapPreschoolEduServicesSourceCategory = new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapShgTypeCategory= new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapHouseTypeCategory= new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapHousingSchemeCategory= new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapHousingSchemeApplicationStatusCategory= new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapHealthSchemeCategory= new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapOldAgePensionSourceCategory= new HashMap<Integer, String>();

    public static HashMap<Integer, String> mapWidowPensionSourceCategory= new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapDisabledPensionSourceCategory = new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapMobileContactTypeCategory= new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapFoodSecuritySchemeCategory= new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapMgnregaJobCardStatusCategory = new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapPensionSchemeCategory= new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapSkillDevelopmentSchemesCategory= new HashMap<Integer, String>();
    public static HashMap<Integer, String> mapUncoveredHhdReasonCategory= new HashMap<Integer, String>();


    public static void createTable(Context context, SQLiteDatabase db, String strTableName)
    {
        try {
            String str_createTable = "CREATE TABLE IF NOT EXISTS " + strTableName + "("
                    + CODE + " INTEGER NOT NULL ,"
                    + NAME + " TEXT NOT NULL ,"
                    + ")";
            db.execSQL(str_createTable);
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"023-001");
        }
    }


    /* Function to add data in database */
    public static boolean insertData(Context context, SQLiteDatabase db, MasterCommon data, int intTableCode) {
        long row = -1;
        try {
            ContentValues values = new ContentValues();
            values.put(CODE, data.getType_code());
            values.put(NAME, data.getType_name());

            row = db.insert(map_tableName.get(intTableCode), null, values);
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"023-002");
        }
        return row >= 0;
    }

    public static MasterCommon insertAll(Context context, SQLiteDatabase db, int intTableCode, ArrayList<MasterCommon> proItems) {

        MasterCommon lastItem = null;

        try {
            String sql = "Insert into "+ map_tableName.get(intTableCode) +" (" +CODE+"," + NAME  + " ) values(?,?)";
            SQLiteStatement insert = db.compileStatement(sql);
            db.beginTransaction();
            for(int i=0;i<proItems.size();i++){
                MasterCommon item = proItems.get(i);
                if(i == (proItems.size()-1)){
                    lastItem = item;
                }
                insert.clearBindings();
                insert.bindLong(1, item.getType_code());
                insert.bindString(2, item.getType_name());

                insert.execute();
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"023-003");
        }
        finally {
            db.endTransaction();
        }

        return lastItem;
    }

    // Retrieve all
    public static ArrayList<MasterCommon> getAllData(Context context, SQLiteDatabase db, int intTableCode) {

        ArrayList<MasterCommon> arrList = new ArrayList<MasterCommon>();

        try {
            // Select All Query
            String selectQuery = "SELECT * FROM " + map_tableName.get(intTableCode) + " where " + TYPE_LANGUAGE + " = ?";

            if(MySharedPref.getLocaleLanguage(context) != null)
                strLanguageToLoad = MySharedPref.getLocaleLanguage(context);
            Cursor cursor = db.rawQuery(selectQuery, new String[]{strLanguageToLoad});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    MasterCommon s = new MasterCommon();
                    s.setType_code(cursor.getInt(cursor.getColumnIndex(CODE)));
                    s.setType_name(cursor.getString(cursor.getColumnIndex(NAME)));

                    // Adding to list
                    arrList.add(s);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"023-004");
        }
        // return list
        return arrList;
    }

    // Retrieve name of the code
    public static String getName(Context context, SQLiteDatabase db, int intTableCode, int nCode) {

        MasterCommon s = new MasterCommon();

        try {
            // Select All Query
            String selectQuery = "SELECT * FROM " + map_tableName.get(intTableCode) + " where " + CODE + " = ? and " + TYPE_LANGUAGE + " = ?";
            if(MySharedPref.getLocaleLanguage(context) != null)
                strLanguageToLoad = MySharedPref.getLocaleLanguage(context);
            Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(nCode), strLanguageToLoad});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    s.setType_code(cursor.getInt(cursor.getColumnIndex(CODE)));
                    s.setType_name(cursor.getString(cursor.getColumnIndex(NAME)));

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            if(nCode == 0)
                return context.getString(R.string.not_available);
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"023-005");
        }
        // return list
        return s.getType_name();
    }

    // Retrieve all
    public static ArrayList<MasterCommon> getAllDataDistance(Context context, SQLiteDatabase db, int iTableCode, String strCategory) {

        ArrayList<MasterCommon> arrList = new ArrayList<MasterCommon>();

        try {
            // Select All Query
            String selectQuery = "SELECT * FROM " + map_tableName.get(iTableCode) + " where " + TYPE_LANGUAGE + " = ? and " + TYPE_CATEGORY + " = ?"
                    ;
            if(MySharedPref.getLocaleLanguage(context) != null)
                strLanguageToLoad = MySharedPref.getLocaleLanguage(context);
            Cursor cursor = db.rawQuery(selectQuery, new String[]{strLanguageToLoad, strCategory});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    MasterCommon s = new MasterCommon();
                    s.setType_code(cursor.getInt(cursor.getColumnIndex(CODE)));
                    s.setType_name(cursor.getString(cursor.getColumnIndex(NAME)));

                    // Adding to list
                    arrList.add(s);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();

        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"023-009");
        }
        // return list
        return arrList;
    }

    // Retrieve name of the code
    public static String getNameDistance(Context context, SQLiteDatabase db, int intTableCode, int nCode, String strCategory) {

        MasterCommon s = new MasterCommon();

        try {
            // Select All Query
            String selectQuery = "SELECT * FROM " + map_tableName.get(intTableCode) + " where " + CODE + " = ? and " + TYPE_LANGUAGE + " = ? and " + TYPE_CATEGORY + " = ?";
            if(MySharedPref.getLocaleLanguage(context) != null)
                strLanguageToLoad = MySharedPref.getLocaleLanguage(context);
            Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(nCode), strLanguageToLoad,strCategory});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    s.setType_code(cursor.getInt(cursor.getColumnIndex(CODE)));
                    s.setType_name(cursor.getString(cursor.getColumnIndex(NAME)));

                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            if(nCode == 0)
                return context.getString(R.string.not_available);
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"023-010");
        }
        // return list
        return s.getType_name();
    }

    // Retrieve name of the code of map yesno
    public static String getNameYesNo(Context context, Boolean bCode) {

        String strResult = "";

        try {

            if(bCode == null)
                strResult = context.getString(R.string.not_available);
            else if(bCode)
                strResult = context.getString(R.string.yes);
            else strResult = context.getString(R.string.no);
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"023-006");
        }
        // return list
        return strResult;
    }


    /* Function to delete all in table */
    public static boolean deleteAll(Context context, SQLiteDatabase db, int intTableCode) {
        int row = -1;

        try {

            row = db.delete(map_tableName.get(intTableCode), null, null);
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"023-007");
        }
        return row > 0;
    }
}
