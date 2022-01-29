package in.nic.ease_of_living.dbo;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.installation.Installation;
import in.nic.ease_of_living.models.GpVillageChecksum;
import in.nic.ease_of_living.models.SeccPopulation;
import in.nic.ease_of_living.supports.GetAddress;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MySharedPref;
import in.nic.ease_of_living.utils.AESHelper;


/**
 * Created by Chinki Sai on 7/4/2017.
 */
//031-001
public class SeccPopulationController {
    private static final String TABLE_NAME = "secc_population";

    private static final String STATE_CODE = "state_code";
    private static final String DISTRICT_CODE = "district_code";
    private static final String SUB_DISTRICT_CODE = "sub_district_code";
    private static final String BLOCK_CODE = "block_code";
    private static final String GP_CODE = "gp_code";
    private static final String VILLAGE_CODE = "village_code";
    private static final String ENUM_BLOCK_CODE = "enum_block_code";
    private static final String HHD_UID = "hhd_uid";

    private static final String MEMBER_SL_NO = "member_sl_no";
    private static final String AHL_TIN = "ahl_tin";
    private static final String NAME = "name";
    private static final String RELATION = "relation";
    private static final String GENDERID = "genderid";
    private static final String DOB = "dob";
    private static final String AGE = "age";
    private static final String MARITAL_STATUS = "marital_status";
    private static final String FATHER_NAME = "father_name";
    private static final String MOTHER_NAME = "mother_name";
    private static final String OCCUPATION = "occupation";
    private static final String NAME_SL = "name_sl";
    private static final String RELATION_SL = "relation_sl";
    private static final String FATHER_NAME_SL = "father_name_sl";
    private static final String MOTHER_NAME_SL = "mother_name_sl";
    private static final String OCCUPATION_SL = "occupation_sl";
    private static final String DT_CREATED = "dt_created";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + STATE_CODE + " INTEGER  NOT NULL,"
            + DISTRICT_CODE + " INTEGER  NOT NULL,"
            + SUB_DISTRICT_CODE + " INTEGER  NOT NULL,"
            + BLOCK_CODE + " INTEGER  NOT NULL,"
            + GP_CODE + " INTEGER  NOT NULL,"
            + VILLAGE_CODE + " INTEGER  NOT NULL,"
            + ENUM_BLOCK_CODE + " INTEGER  NOT NULL,"
            + HHD_UID + " INTEGER  NOT NULL,"

            + MEMBER_SL_NO + " TEXT  NOT NULL,"
            + AHL_TIN + " TEXT  NOT NULL,"
            + NAME + " TEXT  NOT NULL,"
            + RELATION + " TEXT ,"
            + GENDERID + " TEXT ,"
            + DOB + " TEXT ,"
            + AGE + " TEXT ,"
            + MARITAL_STATUS + " TEXT ,"
            + FATHER_NAME + " TEXT ,"
            + MOTHER_NAME + " TEXT ,"
            + OCCUPATION + " TEXT ,"
            + NAME_SL + " TEXT ,"
            + RELATION_SL + " TEXT ,"
            + FATHER_NAME_SL + " TEXT ,"
            + MOTHER_NAME_SL + " TEXT ,"
            + OCCUPATION_SL + " TEXT ,"
            + DT_CREATED + " TEXT ,"
            + " CONSTRAINT secc_population_pkey PRIMARY KEY (state_code, district_code, sub_district_code, block_code, gp_code, village_code, enum_block_code, hhd_uid,member_sl_no) )";


    static String selectQuery = "select * from secc_population where hhd_uid not in (select hhd_uid from household_eol_status where is_completed = 1 and is_uncovered <> 1) and " ;

    private static SeccPopulation getPopulationFromCursor(Cursor cursor)
    {
        SeccPopulation p = new SeccPopulation();
        p.setState_code(cursor.getInt(cursor.getColumnIndex(	 STATE_CODE 	)));
        p.setDistrict_code(cursor.getInt(cursor.getColumnIndex(	 DISTRICT_CODE 	)));
        p.setSub_district_code(cursor.getInt(cursor.getColumnIndex(	 SUB_DISTRICT_CODE 	)));
        p.setBlock_code(cursor.getInt(cursor.getColumnIndex(	 BLOCK_CODE 	)));
        p.setGp_code(cursor.getInt(cursor.getColumnIndex(	 GP_CODE 	)));
        p.setVillage_code(cursor.getInt(cursor.getColumnIndex(	 VILLAGE_CODE 	)));
        p.setEnum_block_code(cursor.getInt(cursor.getColumnIndex(	 ENUM_BLOCK_CODE 	)));
        p.setHhd_uid(cursor.getInt(cursor.getColumnIndex(	 HHD_UID 	)));

        p.setMember_sl_no(cursor.getString(cursor.getColumnIndex(	 MEMBER_SL_NO 	)));
        p.setAhl_tin(cursor.getString(cursor.getColumnIndex(	 AHL_TIN 	)));
        p.setName(cursor.getString(cursor.getColumnIndex(	 NAME 	)));
        p.setRelation(cursor.getString(cursor.getColumnIndex(	 RELATION 	)));
        p.setGenderid(cursor.getString(cursor.getColumnIndex(	 GENDERID 	)));
        p.setDob(cursor.getString(cursor.getColumnIndex(	 DOB 	)));
        p.setAge(cursor.getString(cursor.getColumnIndex(	 AGE 	)));
        p.setMarital_status(cursor.getString(cursor.getColumnIndex(	 MARITAL_STATUS 	)));
        p.setFather_name(cursor.getString(cursor.getColumnIndex(	 FATHER_NAME 	)));
        p.setMother_name(cursor.getString(cursor.getColumnIndex(	 MOTHER_NAME 	)));
        p.setOccupation(cursor.getString(cursor.getColumnIndex(	 OCCUPATION 	)));
        p.setName_sl(cursor.getString(cursor.getColumnIndex(	 NAME_SL 	)));
        p.setRelation_sl(cursor.getString(cursor.getColumnIndex(	 RELATION_SL 	)));
        p.setFather_name_sl(cursor.getString(cursor.getColumnIndex(	 FATHER_NAME_SL 	)));
        p.setMother_name_sl(cursor.getString(cursor.getColumnIndex(	 MOTHER_NAME_SL 	)));
        p.setOccupation_sl(cursor.getString(cursor.getColumnIndex(	 OCCUPATION_SL 	)));
        p.setDt_created(cursor.getString(cursor.getColumnIndex(	 DT_CREATED 	)));
        return p;
    }

    private static ContentValues setPopulationToContentValues(SeccPopulation p)
    {
        ContentValues values = new ContentValues();
        values.put(	 STATE_CODE 	, p.getState_code());
        values.put(	 DISTRICT_CODE 	, p.getDistrict_code());
        values.put(	 SUB_DISTRICT_CODE 	, p.getSub_district_code());
        values.put(	 BLOCK_CODE 	, p.getBlock_code());
        values.put(	 GP_CODE 	, p.getGp_code());
        values.put(	 VILLAGE_CODE 	, p.getVillage_code());
        values.put(	 ENUM_BLOCK_CODE 	, p.getEnum_block_code());
        values.put(	 HHD_UID 	, p.getHhd_uid());

        values.put(	 MEMBER_SL_NO 	, p.getMember_sl_no());
        values.put(	 AHL_TIN 	, p.getAhl_tin());
        values.put(	 NAME 	, p.getName());
        values.put(	 RELATION 	, p.getRelation());
        values.put(	 GENDERID 	, p.getGenderid());
        values.put(	 DOB 	, p.getDob());
        values.put(	 AGE 	, p.getAge());
        values.put(	 MARITAL_STATUS 	, p.getMarital_status());
        values.put(	 FATHER_NAME 	, p.getFather_name());
        values.put(	 MOTHER_NAME 	, p.getMother_name());
        values.put(	 OCCUPATION 	, p.getOccupation());
        values.put(	 NAME_SL 	, p.getName_sl());
        values.put(	 RELATION_SL 	, p.getRelation_sl());
        values.put(	 FATHER_NAME_SL 	, p.getFather_name_sl());
        values.put(	 MOTHER_NAME_SL 	, p.getMother_name_sl());
        values.put(	 OCCUPATION_SL 	, p.getOccupation_sl());
        values.put(	 DT_CREATED 	, p.getDt_created());

        return values;
    }

    public static ArrayList<SeccPopulation> getAllData(Context context, SQLiteDatabase db) {
        ArrayList<SeccPopulation> list = new ArrayList<>();
        try {
            String strSelectQuery = "select * from " + TABLE_NAME ;
            Cursor cursor = db.rawQuery(strSelectQuery, null);
            //Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
            SQLiteHelper.checkTableColumns(context, db, TABLE_NAME);

            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    SeccPopulation p = getPopulationFromCursor(cursor);

                    list.add(p);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }catch(Exception e)
        {
            return null;
            //MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.add_household_info), context.getString(R.string.house_completed));
        }
        return list;
    }

    public static SeccPopulation insertAll(Context context, SQLiteDatabase db, ArrayList<SeccPopulation> alPop, String strDate) {

        SeccPopulation lastItem = null;
        long l_row = -1;

        try {

            db.beginTransaction();
            for (int i = 0; i < alPop.size(); i++) {
                SeccPopulation p = alPop.get(i);
                if (i == (alPop.size() - 1)) {
                    lastItem = p;
                    l_row = -1;
                }
                ContentValues values = setPopulationToContentValues(p);


                l_row = db.insert(TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(), "021-002");
        } finally {
            db.endTransaction();
        }
        return lastItem;
    }



    public static ArrayList<SeccPopulation> getPopByName(SQLiteDatabase db, final Context ctx,
                                                         final String strPersonName,
                                                         final String strFatherName,
                                                         final String strMotherName,
                                                         final String strYob) {



        ArrayList<SeccPopulation> list = new ArrayList<>();
        try{
            String paramValue[]=null;
            String sqlQuery= "";
            sqlQuery = selectQuery + " trim(upper(name)) like ? and trim(upper(father_name)) like ? and trim(upper(mother_name)) like ? and trim(dob) like ?";
            paramValue=new String[]{strPersonName+"%",strFatherName+"%",strMotherName+"%", strYob+"%"};
            Cursor cursor = db.rawQuery(sqlQuery,paramValue);

            if (cursor.moveToFirst()) {
                MySharedPref.saveTotal(ctx, String.valueOf(cursor.getCount()));
                while (cursor.isAfterLast() == false) {
                    SeccPopulation pop = getPopulationFromCursor(cursor);
                    list.add(pop);

                    cursor.moveToNext();
                }
                cursor.close();
                MySharedPref.saveTotal(ctx, String.valueOf(list.size()));
            }
            else
            {
                Toast.makeText(ctx,"No data found !!", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e)
        {

        }
        return list;

    }


    public static ArrayList<SeccPopulation> getPopByHhdId(SQLiteDatabase db, final Context ctx, final int hhd_uid) {

        ArrayList<SeccPopulation> list = new ArrayList<>();
        String sqlQuery = null;
        Cursor cursor = null;
        sqlQuery = selectQuery + " hhd_uid = ? order by member_sl_no asc";

        cursor = db.rawQuery(sqlQuery, new String[]{ String.valueOf(hhd_uid)});

        try
        {
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    SeccPopulation pop = getPopulationFromCursor(cursor);
                    list.add(pop);
                    cursor.moveToNext();
                }
                cursor.close();
                MySharedPref.saveTotal(ctx, String.valueOf(list.size()));
            }
        }catch (Exception e)
        {
            MyAlert.showAlert(ctx, R.mipmap.icon_info, ctx.getString(R.string.population), e.getMessage(), "");
        }
        return list;

    }



    public static String getMember_sn(SQLiteDatabase db, String hhd_uid, int hhd_sl_no) {
        String member_sl_no=null;
        String query = null;
        Cursor cursor = null;
        if(hhd_uid == null)
        {
            query="SELECT substr('00'||trim(ifnull(max(member_sl_no), 0) + 1),-3) as member_sl_no " +
                    "FROM ( " +
                    "SELECT hhd_sl_no, member_sl_no FROM `population_updated` " +
                    "WHERE hhd_sl_no = ? group by hhd_sl_no, member_sl_no)A";

            cursor = db.rawQuery(query, new String[]{String.valueOf(hhd_sl_no)});
        }
        else
        {
            query="SELECT substr('00'||trim(ifnull(max(member_sl_no), 0) + 1),-3) as member_sl_no " +
                    "FROM (SELECT ifnull(trim(hhd_uid),0), member_sl_no FROM `"+TABLE_NAME+"` WHERE trim(hhd_uid) = ? " +
                    "group by hhd_uid, member_sl_no " +
                    "UNION " +
                    "SELECT hhd_sl_no, member_sl_no FROM `population_updated` " +
                    "WHERE hhd_sl_no = ? group by hhd_sl_no, member_sl_no)A";

            cursor = db.rawQuery(query, new String[]{hhd_uid, String.valueOf(hhd_sl_no)});
        }

        if (cursor.moveToFirst()) {
            member_sl_no = cursor.getString(cursor.getColumnIndex("member_sl_no")).trim();
        }
        return member_sl_no;
    }

    public static Boolean isHouseholdHead(SQLiteDatabase db, String strHhdUid, String strAhlTin) {
        int iHeadCount = 0;
        String strQuery = null;
        Cursor cursor = null;
        if(strHhdUid != null)
        {
            strQuery="select count(*) hhd_head from secc_population a\n" +
                    "inner join secc_household b where a.hhd_uid = ? and \n" +
                    "a.hhd_uid = b.hhd_uid and \n" +
                    "a.ahl_tin = ? and\n" +
                    "a.ahl_tin = b.head_ahl_tin";

            cursor = db.rawQuery(strQuery, new String[]{strHhdUid, strAhlTin});
        }

        if (cursor.moveToFirst()) {
            iHeadCount = cursor.getInt(cursor.getColumnIndex("hhd_head"));
        }
        return (iHeadCount > 0);
    }

    public static GpVillageChecksum getAllGpforAcknowledge(Context context, SQLiteDatabase db) {
        GpVillageChecksum gp = new GpVillageChecksum();
        try {

            String strMac = MySharedPref.getMacAddress(context);
            String strImei = "XXXXXXXXXXXXXXX";
            String strIp = "XXX.XXX.XXX.XXX";
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (MySharedPref.getImei(context) != null) {
                strImei = MySharedPref.getImei(context);
            } else if (GetAddress.getIMEI(context) != null) {
                MySharedPref.saveImei(context, GetAddress.getIMEI(context));
                strImei = GetAddress.getIMEI(context);
            }

            if (GetAddress.getIPAddress(true) != null)
                strIp = GetAddress.getIPAddress(true);

            String strDeviceId = MySharedPref.getDeviceId(context);
            String strAppId = Installation.id(context);
            String strAppVersion = packageInfo.versionName;

            String limit = " LIMIT 0,1";
            Cursor cursor = db.rawQuery("select " + STATE_CODE + "," + DISTRICT_CODE
                    + "," + SUB_DISTRICT_CODE + "," + BLOCK_CODE + "," + GP_CODE
                    + "," + VILLAGE_CODE
                    + " from " + TABLE_NAME + limit, null);

            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {

                    gp.setState_code(cursor.getInt(cursor.getColumnIndex(STATE_CODE)));
                    gp.setDistrict_code(cursor.getInt(cursor.getColumnIndex(DISTRICT_CODE)));
                    gp.setSub_district_code(cursor.getInt(cursor.getColumnIndex(SUB_DISTRICT_CODE)));
                    gp.setBlock_code(cursor.getInt(cursor.getColumnIndex(BLOCK_CODE)));
                    gp.setGp_code(cursor.getInt(cursor.getColumnIndex(GP_CODE)));
                    gp.setVillage_code(cursor.getInt(cursor.getColumnIndex(VILLAGE_CODE)));
                    gp.setClient_mac_address(strMac);
                    gp.setClient_imei_no(strImei);
                    gp.setClient_ip_address(strIp);
                    gp.setDevice_id(strDeviceId);
                    gp.setApp_id(strAppId);
                    gp.setApp_version(strAppVersion);
                    gp.setUser_id(AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));


                    cursor.moveToNext();
                }
                cursor.close();
            }
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(), "021-006");
        }
        return gp;
    }


    public static boolean isTableExist(SQLiteDatabase db) {
        boolean isResult = false;
        int isexist = 0;
        String query = "select isexist from (\n" +
                "select count(1)>0 as isexist from sqlite_master where name = '" + TABLE_NAME + "' )a";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            isexist = cursor.getInt(cursor.getColumnIndex("isexist"));
            if (isexist == 1) {
                String query2 = "select count(1)>0 as isexist from " + TABLE_NAME;

                Cursor cursor2 = db.rawQuery(query2, null);
                if (cursor2.moveToFirst()) {
                    isexist = cursor2.getInt(cursor2.getColumnIndex("isexist"));
                    if (isexist == 1)
                        isResult = true;
                    else
                        isResult = false;
                } else {
                    isResult = false;
                }
            } else {
                isResult = false;
            }
        } else {
            isResult = false;
        }
        return isResult;
    }

    public static boolean   isEmptyTableExist(SQLiteDatabase db) {
        boolean isResult=false;
        try {
            int isexist = 0;
        /*String query="select isexist from (\n" +
                "select count(1)>0 as isexist from sqlite_master where name = '"+TABLE_NAME+"' )a";
*/
            String query = "SELECT count(*)>0 isexist FROM sqlite_master WHERE type='table' AND name= '" + TABLE_NAME + "'";


            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                isexist = cursor.getInt(cursor.getColumnIndex("isexist"));
                if (isexist == 1) {
                    isResult = true;
                } else {
                    isResult = false;
                }
            } else {
                isResult = false;
            }
        }catch(Exception e)
        {

        }
        return isResult;
    }

    public static String getHh_uid_old(Context context, SQLiteDatabase db, String device_id) {
        String huid=null;
        try {
            //String query = "select  substr(a.hhd_uid, 1, 12) || substr('00000'||cast(max(substr(a.hhd_uid, 13, 6)) + 1 as varchar),-6) as hhd_uid from(SELECT hhd_uid FROM "+TABLE_NAME+" group by hhd_uid union SELECT hhd_uid FROM population_updated group by hhd_uid) a  order by  hhd_uid";
            String query = "select  substr('00000'||cast(max(cast(a.hhd_uid as INTEGER)) + 1 as varchar),-6) as hhd_uid  " +
                    "from " +
                    "(SELECT hhd_uid FROM "+TABLE_NAME+" group by hhd_uid " +
                    " union SELECT hhd_uid " +
                    " FROM population_updated group by hhd_uid" +
                    " union SELECT hhd_uid  " +
                    " FROM household_enumeration_info group by hhd_uid) a  " +
                    " order by  hhd_uid";
            Cursor cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()) {
                huid = cursor.getString(cursor.getColumnIndex("hhd_uid"));
            }
            if(huid == null)
                huid="000001";
        }catch (Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.error), e.getMessage(), "031-001");
        }
        return huid;
    }


    public static int getRowsCount(Context context, SQLiteDatabase db) {
        int cnt = 0;
        try {
            String countQuery = "SELECT  * FROM " + TABLE_NAME;

            Cursor cursor = db.rawQuery(countQuery, null);
            cnt = cursor.getCount();
            cursor.close();
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(), "021-010");
        }

        return cnt;
    }


    public static void delete(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public static boolean deleteAll(Context context, SQLiteDatabase db) {
        int row = -1;
        try {

            row = db.delete(TABLE_NAME, null, null);
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"022-013");
        }
        return row > 0;
    }


}
