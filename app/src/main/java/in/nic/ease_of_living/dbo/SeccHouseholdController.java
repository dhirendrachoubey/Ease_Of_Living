package in.nic.ease_of_living.dbo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.models.HouseholdEol;
import in.nic.ease_of_living.models.SeccHousehold;
import in.nic.ease_of_living.models.SeccPopulation;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MySharedPref;
import in.nic.ease_of_living.utils.Common;


/**
 * Created by Chinki Sai on 7/4/2017.
 */
//031-001
public class SeccHouseholdController {
    private static final String TABLE_NAME = "secc_household";

    private static final String STATE_CODE= "state_code";
    private static final String STATE_NAME= "state_name";
    private static final String STATE_NAME_SL= "state_name_sl";
    private static final String DISTRICT_CODE= "district_code";
    private static final String DISTRICT_NAME= "district_name";
    private static final String DISTRICT_NAME_SL= "district_name_sl";
    private static final String SUB_DISTRICT_CODE= "sub_district_code";
    private static final String SUB_DISTRICT_NAME= "sub_district_name";
    private static final String SUB_DISTRICT_NAME_SL= "sub_district_name_sl";
    private static final String BLOCK_CODE= "block_code";
    private static final String BLOCK_NAME= "block_name";
    private static final String BLOCK_NAME_SL= "block_name_sl";
    private static final String GP_CODE= "gp_code";
    private static final String GP_NAME= "gp_name";
    private static final String GP_NAME_SL= "gp_name_sl";
    private static final String VILLAGE_CODE= "village_code";
    private static final String VILLAGE_NAME= "village_name";
    private static final String VILLAGE_NAME_SL= "village_name_sl";
    private static final String ENUM_BLOCK_CODE= "enum_block_code";
    private static final String ENUM_BLOCK_NAME= "enum_block_name";
    private static final String ENUM_BLOCK_NAME_SL= "enum_block_name_sl";
    private static final String HHD_UID= "hhd_uid";

    private static final String STATECODE= "statecode";
    private static final String STATENAME= "statename";
    private static final String DISTRICTCODE= "districtcode";
    private static final String DISTRICTNAME= "districtname";
    private static final String TEHSILCODE= "tehsilcode";
    private static final String TEHSILNAME= "tehsilname";
    private static final String TOWNCODE= "towncode";
    private static final String TOWNNAME= "townname";
    private static final String WARDID= "wardid";
    private static final String AHLBLOCKNO= "ahlblockno";
    private static final String AHLSUBBLOCKNO= "ahlsubblockno";
    private static final String GRAMPANCHAYATCODE= "grampanchayatcode";
    private static final String GRAMPANCHAYATNAME= "grampanchayatname";

    private static final String AHL_FAMILY_TIN= "ahl_family_tin";
    private static final String HEAD_AHL_TIN= "head_ahl_tin";
    private static final String AHLSLNOHHD= "ahlslnohhd";
    private static final String TYPEOFHHD= "typeofhhd";

    private static final String HEAD_NAME= "head_name";
    private static final String HEAD_FATHERNAME= "head_fathername";
    private static final String HEAD_MOTHERNAME= "head_mothername";
    private static final String HEAD_OCCUPATION= "head_occupation";
    private static final String ADDRESSLINE1= "addressline1";
    private static final String ADDRESSLINE2= "addressline2";
    private static final String ADDRESSLINE3= "addressline3";
    private static final String ADDRESSLINE4= "addressline4";
    private static final String ADDRESSLINE5= "addressline5";
    private static final String PINCODE= "pincode";

    private static final String HEAD_NAME_SL= "head_name_sl";
    private static final String HEAD_FATHERNAME_SL= "head_fathername_sl";
    private static final String HEAD_MOTHERNAME_SL= "head_mothername_sl";
    private static final String HEAD_OCCUPATION_SL= "head_occupation_sl";
    private static final String ADDRESSLINE1_SL= "addressline1_sl";
    private static final String ADDRESSLINE2_SL= "addressline2_sl";
    private static final String ADDRESSLINE3_SL= "addressline3_sl";
    private static final String ADDRESSLINE4_SL= "addressline4_sl";
    private static final String ADDRESSLINE5_SL= "addressline5_sl";
    private static final String NAME_IN_NPR_IMAGE= "name_in_npr_image";
    private static final String DT_CREATED= "dt_created";

    private static final String IS_UNCOVERED= "is_uncovered";



    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + 	STATE_CODE	 + " INTEGER  NOT NULL,"
            + 	STATE_NAME	 + " TEXT ,"
            + 	STATE_NAME_SL	 + " TEXT ,"
            + 	DISTRICT_CODE	 + " INTEGER  NOT NULL,"
            + 	DISTRICT_NAME	 + " TEXT ,"
            + 	DISTRICT_NAME_SL	 + " TEXT ,"
            + 	SUB_DISTRICT_CODE	 + " INTEGER  NOT NULL,"
            + 	SUB_DISTRICT_NAME	 + " TEXT ,"
            + 	SUB_DISTRICT_NAME_SL	 + " TEXT ,"
            + 	BLOCK_CODE	 + " INTEGER  NOT NULL,"
            + 	BLOCK_NAME	 + " TEXT ,"
            + 	BLOCK_NAME_SL	 + " TEXT ,"
            + 	GP_CODE	 + " INTEGER  NOT NULL,"
            + 	GP_NAME	 + " TEXT ,"
            + 	GP_NAME_SL	 + " TEXT ,"
            + 	VILLAGE_CODE	 + " INTEGER  NOT NULL,"
            + 	VILLAGE_NAME	 + " TEXT ,"
            + 	VILLAGE_NAME_SL	 + " TEXT ,"
            + 	ENUM_BLOCK_CODE	 + " INTEGER  NOT NULL,"
            + 	ENUM_BLOCK_NAME + " TEXT ,"
            + 	ENUM_BLOCK_NAME_SL + " TEXT ,"
            + 	HHD_UID	 + " INTEGER  NOT NULL,"

            + 	STATECODE	 + " TEXT  NOT NULL,"
            + 	STATENAME	 + " TEXT  NOT NULL,"
            + 	DISTRICTCODE	 + " TEXT  NOT NULL,"
            + 	DISTRICTNAME	 + " TEXT  NOT NULL,"
            + 	TEHSILCODE	 + " TEXT  NOT NULL,"
            + 	TEHSILNAME	 + " TEXT  NOT NULL,"
            + 	TOWNCODE	 + " TEXT  NOT NULL,"
            + 	TOWNNAME	 + " TEXT  NOT NULL,"
            + 	WARDID	 + " TEXT  NOT NULL,"
            + 	AHLBLOCKNO	 + " TEXT  NOT NULL,"
            + 	AHLSUBBLOCKNO	 + " TEXT  NOT NULL,"
            + 	GRAMPANCHAYATCODE	 + " TEXT ,"
            + 	GRAMPANCHAYATNAME	 + " TEXT ,"

            + 	AHL_FAMILY_TIN	 + " TEXT  NOT NULL,"
            + 	HEAD_AHL_TIN	 + " TEXT  NOT NULL,"
            + 	AHLSLNOHHD	 + " TEXT  NOT NULL,"
            + 	TYPEOFHHD	 + " TEXT ,"

            + 	HEAD_NAME	 + " TEXT ,"
            + 	HEAD_FATHERNAME	 + " TEXT ,"
            + 	HEAD_MOTHERNAME	 + " TEXT ,"
            + 	HEAD_OCCUPATION	 + " TEXT ,"
            + 	ADDRESSLINE1	 + " TEXT ,"
            + 	ADDRESSLINE2	 + " TEXT ,"
            + 	ADDRESSLINE3	 + " TEXT ,"
            + 	ADDRESSLINE4	 + " TEXT ,"
            + 	ADDRESSLINE5	 + " TEXT ,"
            + 	PINCODE	 + " TEXT ,"

            + 	HEAD_NAME_SL	 + " TEXT ,"
            + 	HEAD_FATHERNAME_SL	 + " TEXT ,"
            + 	HEAD_MOTHERNAME_SL	 + " TEXT ,"
            + 	HEAD_OCCUPATION_SL	 + " TEXT ,"
            + 	ADDRESSLINE1_SL	 + " TEXT ,"
            + 	ADDRESSLINE2_SL	 + " TEXT ,"
            + 	ADDRESSLINE3_SL	 + " TEXT ,"
            + 	ADDRESSLINE4_SL	 + " TEXT ,"
            + 	ADDRESSLINE5_SL	 + " TEXT ,"
            + 	NAME_IN_NPR_IMAGE	 + " TEXT ,"
            + 	DT_CREATED	 + " TEXT ,"

            + " CONSTRAINT secc_household_pkey PRIMARY KEY (state_code, district_code, sub_district_code, block_code, gp_code, village_code, enum_block_code, hhd_uid) )";



    public static String getMember_sn(SQLiteDatabase db, String hhd_uid) {
        String member_sl_no=null;
        String query="SELECT substr('00'||trim(ifnull(max(member_sl_no), 0) + 1),-3) as member_sl_no " +
                "FROM (SELECT ifnull(trim(hhd_uid),0), member_sl_no FROM `"+TABLE_NAME+"` WHERE trim(hhd_uid) = ? " +
                "group by hhd_uid, member_sl_no " +
                "UNION " +
                "SELECT ifnull(trim(hhd_uid),0), member_sl_no FROM `population_updated` " +
                "WHERE trim(hhd_uid) = ? group by hhd_uid, member_sl_no)A";

        Cursor cursor = db.rawQuery(query, new String[]{hhd_uid.trim(),hhd_uid.trim()});
        if (cursor.moveToFirst()) {
            member_sl_no = cursor.getString(cursor.getColumnIndex("member_sl_no")).trim();
        }
        return member_sl_no;
    }

    private static SeccHousehold getHouseholdFromCursor(Cursor cursor)
    {
        SeccHousehold h = new SeccHousehold();
        h.setState_code(cursor.getInt(cursor.getColumnIndex(	STATE_CODE	)));
        h.setState_name(cursor.getString(cursor.getColumnIndex(	STATE_NAME	)).trim());
        h.setState_name_sl(cursor.getString(cursor.getColumnIndex(	STATE_NAME_SL	)).trim());
        h.setDistrict_code(cursor.getInt(cursor.getColumnIndex(	DISTRICT_CODE	)));
        h.setDistrict_name(cursor.getString(cursor.getColumnIndex(	DISTRICT_NAME	)).trim());
        h.setDistrict_name_sl(cursor.getString(cursor.getColumnIndex(	DISTRICT_NAME_SL	)).trim());
        h.setSub_district_code(cursor.getInt(cursor.getColumnIndex(	SUB_DISTRICT_CODE	)));
        h.setSub_district_name(cursor.getString(cursor.getColumnIndex(	SUB_DISTRICT_NAME	)).trim());
        h.setSub_district_name_sl(cursor.getString(cursor.getColumnIndex(	SUB_DISTRICT_NAME_SL	)).trim());
        h.setBlock_code(cursor.getInt(cursor.getColumnIndex(	BLOCK_CODE	)));
        h.setBlock_name(cursor.getString(cursor.getColumnIndex(	BLOCK_NAME	)).trim());
        h.setBlock_name_sl(cursor.getString(cursor.getColumnIndex(	BLOCK_NAME_SL	)).trim());
        h.setGp_code(cursor.getInt(cursor.getColumnIndex(	GP_CODE	)));
        h.setGp_name(cursor.getString(cursor.getColumnIndex(	GP_NAME	)).trim());
        h.setGp_name_sl(cursor.getString(cursor.getColumnIndex(	GP_NAME_SL	)).trim());
        h.setVillage_code(cursor.getInt(cursor.getColumnIndex(	VILLAGE_CODE	)));
        h.setVillage_name(cursor.getString(cursor.getColumnIndex(	VILLAGE_NAME	)).trim());
        h.setVillage_name_sl(cursor.getString(cursor.getColumnIndex(	VILLAGE_NAME_SL	)).trim());
        h.setEnum_block_code(cursor.getInt(cursor.getColumnIndex(	ENUM_BLOCK_CODE	)));
        h.setEnum_block_name(cursor.getString(cursor.getColumnIndex(	ENUM_BLOCK_NAME	)).trim());
        h.setEnum_block_name_sl(cursor.getString(cursor.getColumnIndex(	ENUM_BLOCK_NAME_SL	)).trim());
        h.setHhd_uid(cursor.getInt(cursor.getColumnIndex(	HHD_UID	)));

        h.setStatecode(cursor.getString(cursor.getColumnIndex(	STATECODE	)).trim());
        h.setStatename(cursor.getString(cursor.getColumnIndex(	STATENAME	)).trim());
        h.setDistrictcode(cursor.getString(cursor.getColumnIndex(	DISTRICTCODE	)).trim());
        h.setDistrictname(cursor.getString(cursor.getColumnIndex(	DISTRICTNAME	)).trim());
        h.setTehsilcode(cursor.getString(cursor.getColumnIndex(	TEHSILCODE	)).trim());
        h.setTehsilname(cursor.getString(cursor.getColumnIndex(	TEHSILNAME	)).trim());
        h.setTowncode(cursor.getString(cursor.getColumnIndex(	TOWNCODE	)).trim());
        h.setTownname(cursor.getString(cursor.getColumnIndex(	TOWNNAME	)).trim());
        h.setWardid(cursor.getString(cursor.getColumnIndex(	WARDID	)).trim());
        h.setAhlblockno(cursor.getString(cursor.getColumnIndex(	AHLBLOCKNO	)).trim());
        h.setAhlsubblockno(cursor.getString(cursor.getColumnIndex(	AHLSUBBLOCKNO	)).trim());
        h.setGrampanchayatcode(cursor.getString(cursor.getColumnIndex(	GRAMPANCHAYATCODE	)).trim());
        h.setGrampanchayatname(cursor.getString(cursor.getColumnIndex(	GRAMPANCHAYATNAME	)).trim());

        h.setAhl_family_tin(cursor.getString(cursor.getColumnIndex(	AHL_FAMILY_TIN	)).trim());
        h.setHead_ahl_tin(cursor.getString(cursor.getColumnIndex(	HEAD_AHL_TIN	)).trim());
        h.setAhlslnohhd(cursor.getString(cursor.getColumnIndex(	AHLSLNOHHD	)).trim());
        h.setTypeofhhd(cursor.getString(cursor.getColumnIndex(	TYPEOFHHD	)).trim());

        h.setHead_name(cursor.getString(cursor.getColumnIndex(	HEAD_NAME	)).trim());
        h.setHead_fathername(cursor.getString(cursor.getColumnIndex(	HEAD_FATHERNAME	)).trim());
        h.setHead_mothername(cursor.getString(cursor.getColumnIndex(	HEAD_MOTHERNAME	)).trim());
        h.setHead_occupation(cursor.getString(cursor.getColumnIndex(	HEAD_OCCUPATION	)).trim());
        h.setAddressline1(cursor.getString(cursor.getColumnIndex(	ADDRESSLINE1	)).trim());
        h.setAddressline2(cursor.getString(cursor.getColumnIndex(	ADDRESSLINE2	)).trim());
        h.setAddressline3(cursor.getString(cursor.getColumnIndex(	ADDRESSLINE3	)).trim());
        h.setAddressline4(cursor.getString(cursor.getColumnIndex(	ADDRESSLINE4	)).trim());
        h.setAddressline5(cursor.getString(cursor.getColumnIndex(	ADDRESSLINE5	)).trim());
        h.setPincode(cursor.getString(cursor.getColumnIndex(	PINCODE	)).trim());

        h.setHead_name_sl(cursor.getString(cursor.getColumnIndex(	HEAD_NAME_SL	)).trim());
        h.setHead_fathername_sl(cursor.getString(cursor.getColumnIndex(	HEAD_FATHERNAME_SL	)).trim());
        h.setHead_mothername_sl(cursor.getString(cursor.getColumnIndex(	HEAD_MOTHERNAME_SL	)).trim());
        h.setHead_occupation_sl(cursor.getString(cursor.getColumnIndex(	HEAD_OCCUPATION_SL	)).trim());
        h.setAddressline1_sl(cursor.getString(cursor.getColumnIndex(	ADDRESSLINE1_SL	)).trim());
        h.setAddressline2_sl(cursor.getString(cursor.getColumnIndex(	ADDRESSLINE2_SL	)).trim());
        h.setAddressline3_sl(cursor.getString(cursor.getColumnIndex(	ADDRESSLINE3_SL	)).trim());
        h.setAddressline4_sl(cursor.getString(cursor.getColumnIndex(	ADDRESSLINE4_SL	)).trim());
        h.setAddressline5_sl(cursor.getString(cursor.getColumnIndex(	ADDRESSLINE5_SL	)).trim());
        h.setName_in_npr_image(cursor.getString(cursor.getColumnIndex(	NAME_IN_NPR_IMAGE	)).trim());
        h.setDt_created(cursor.getString(cursor.getColumnIndex(	DT_CREATED	)));

        return h;
    }

    private static ContentValues setHouseholdToContentValues(SeccHousehold h)
    {
        ContentValues values = new ContentValues();
        values.put(	STATE_CODE	, h.getState_code());
        values.put(	STATE_NAME	, h.getState_name());
        values.put(	STATE_NAME_SL	, h.getState_name_sl());
        values.put(	DISTRICT_CODE	, h.getDistrict_code());
        values.put(	DISTRICT_NAME	, h.getDistrict_name());
        values.put(	DISTRICT_NAME_SL	, h.getDistrict_name_sl());
        values.put(	SUB_DISTRICT_CODE	, h.getSub_district_code());
        values.put(	SUB_DISTRICT_NAME	, h.getSub_district_name());
        values.put(	SUB_DISTRICT_NAME_SL	, h.getSub_district_name_sl());
        values.put(	BLOCK_CODE	, h.getBlock_code());
        values.put(	BLOCK_NAME	, h.getBlock_name());
        values.put(	BLOCK_NAME_SL	, h.getBlock_name_sl());
        values.put(	GP_CODE	, h.getGp_code());
        values.put(	GP_NAME	, h.getGp_name());
        values.put(	GP_NAME_SL	, h.getGp_name_sl());
        values.put(	VILLAGE_CODE	, h.getVillage_code());
        values.put(	VILLAGE_NAME	, h.getVillage_name());
        values.put(	VILLAGE_NAME_SL	, h.getVillage_name_sl());
        values.put(	ENUM_BLOCK_CODE	, h.getEnum_block_code());
        values.put(	ENUM_BLOCK_NAME	, h.getEnum_block_name());
        values.put(	ENUM_BLOCK_NAME_SL	, h.getEnum_block_name_sl());
        values.put(	HHD_UID	, h.getHhd_uid());

        values.put(	STATECODE	, h.getStatecode());
        values.put(	STATENAME	, h.getStatename());
        values.put(	DISTRICTCODE	, h.getDistrictcode());
        values.put(	DISTRICTNAME	, h.getDistrictname());
        values.put(	TEHSILCODE	, h.getTehsilcode());
        values.put(	TEHSILNAME	, h.getTehsilname());
        values.put(	TOWNCODE	, h.getTowncode());
        values.put(	TOWNNAME	, h.getTownname());
        values.put(	WARDID	, h.getWardid());
        values.put(	AHLBLOCKNO	, h.getAhlblockno());
        values.put(	AHLSUBBLOCKNO	, h.getAhlsubblockno());
        values.put(	GRAMPANCHAYATCODE	, h.getGrampanchayatcode());
        values.put(	GRAMPANCHAYATNAME	, h.getGrampanchayatname());

        values.put(	AHL_FAMILY_TIN	, h.getAhl_family_tin());
        values.put(	HEAD_AHL_TIN	, h.getHead_ahl_tin());
        values.put(	AHLSLNOHHD	, h.getAhlslnohhd());
        values.put(	TYPEOFHHD	, h.getTypeofhhd());

        values.put(	HEAD_NAME	, h.getHead_name());
        values.put(	HEAD_FATHERNAME	, h.getHead_fathername());
        values.put(	HEAD_MOTHERNAME	, h.getHead_mothername());
        values.put(	HEAD_OCCUPATION	, h.getHead_occupation());
        values.put(	ADDRESSLINE1	, h.getAddressline1());
        values.put(	ADDRESSLINE2	, h.getAddressline2());
        values.put(	ADDRESSLINE3	, h.getAddressline3());
        values.put(	ADDRESSLINE4	, h.getAddressline4());
        values.put(	ADDRESSLINE5	, h.getAddressline5());
        values.put(	PINCODE	, h.getPincode());

        values.put(	HEAD_NAME_SL	, h.getHead_name_sl());
        values.put(	HEAD_FATHERNAME_SL	, h.getHead_fathername_sl());
        values.put(	HEAD_MOTHERNAME_SL	, h.getHead_mothername_sl());
        values.put(	HEAD_OCCUPATION_SL	, h.getHead_occupation_sl());
        values.put(	ADDRESSLINE1_SL	, h.getAddressline1_sl());
        values.put(	ADDRESSLINE2_SL	, h.getAddressline2_sl());
        values.put(	ADDRESSLINE3_SL	, h.getAddressline3_sl());
        values.put(	ADDRESSLINE4_SL	, h.getAddressline4_sl());
        values.put(	ADDRESSLINE5_SL	, h.getAddressline5_sl());
        values.put(	NAME_IN_NPR_IMAGE	, h.getName_in_npr_image());
        values.put(	DT_CREATED	, h.getDt_created());

        return values;
    }

    public static SeccHousehold insertAll(Context context, SQLiteDatabase db, ArrayList<SeccHousehold> alHhd, String strDate) {

        SeccHousehold lastItem = null;
        long l_row = -1;

        try {

            db.beginTransaction();
            for (int i = 0; i < alHhd.size(); i++) {
                SeccHousehold h = alHhd.get(i);
                if (i == (alHhd.size() - 1)) {
                    lastItem = h;
                    l_row = -1;
                }
                ContentValues values = setHouseholdToContentValues(h);


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

    public static ArrayList<SeccHousehold> getAllData(Context context, SQLiteDatabase db) {
        ArrayList<SeccHousehold> list = new ArrayList<>();
        try {
            String strSelectQuery = "select * from " + TABLE_NAME ;
            Cursor cursor = db.rawQuery(strSelectQuery, null);
            SQLiteHelper.checkTableColumns(context, db, TABLE_NAME);

            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    SeccHousehold h = getHouseholdFromCursor(cursor);

                    list.add(h);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.add_household_info), context.getString(R.string.house_completed),"");
        }
        return list;
    }

    public static ArrayList<SeccHousehold> getHouseholdList(Context ctx, SQLiteDatabase db) {
        ArrayList<SeccHousehold> alSeccHousehold = new ArrayList<>();

        String query=" select * from secc_household where hhd_uid not in (select hhd_uid from household_eol_status where is_completed = 1)";
        try{
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    SeccHousehold h = getHouseholdFromCursor(cursor);
                    alSeccHousehold.add(h);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }catch(Exception e)
            {
                return null;
            }
        return alSeccHousehold;
    }

    public static ArrayList<SeccHousehold> getHhdByHhdId(SQLiteDatabase db, final Context ctx, final int hhd_uid) {

        ArrayList<SeccHousehold> list = new ArrayList<>();
        String sqlQuery = null;
        Cursor cursor = null;
        sqlQuery=" select * from secc_household where hhd_uid = ?";

        cursor = db.rawQuery(sqlQuery, new String[]{ String.valueOf(hhd_uid)});

        try
        {
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    SeccHousehold h = getHouseholdFromCursor(cursor);
                    list.add(h);
                    cursor.moveToNext();
                }
                cursor.close();
                MySharedPref.saveTotal(ctx, String.valueOf(list.size()));
            }
        }catch (Exception e)
        {
            //MyAlert.showAlert(ctx, R.mipmap.icon_info, ctx.getString(R.string.hou), e.getMessage(), "");
        }
        return list;

    }

    public static ArrayList<SeccHousehold> getUncoveredHouseholdList(Context ctx, SQLiteDatabase db) {
        ArrayList<SeccHousehold> alSeccHousehold = new ArrayList<>();
        String member_sl_no=null;
        String query="select * from secc_household where hhd_uid in (select hhd_uid from household_eol_status where is_uncovered = 1)";

        try{
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    SeccHousehold h = getHouseholdFromCursor(cursor);
                    alSeccHousehold.add(h);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        }catch(Exception e)
            {
            }
        return alSeccHousehold;
    }

    public static boolean isTableExist(SQLiteDatabase db) {
        boolean isResult=false;
        int isexist=0;
        String query="select isexist from (\n" +
                "select count(1)>0 as isexist from sqlite_master where name = '"+TABLE_NAME+"' )a";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            isexist = cursor.getInt(cursor.getColumnIndex("isexist"));
            if(isexist==1)
            {
            String query2="select count(1)>0 as isexist from "+TABLE_NAME;

            Cursor cursor2 = db.rawQuery(query2, null);
            if (cursor2.moveToFirst()) {
                isexist = cursor2.getInt(cursor2.getColumnIndex("isexist"));
                if (isexist == 1)
                    isResult = true;
                else
                    isResult = false;
            }
             else
            {
                isResult = false;
            }
            }
            else
            {
                isResult = false;
            }
        }
        else
        {
            isResult=false;
        }
        return isResult;
    }

    public static boolean isEmptyTableExist(SQLiteDatabase db) {
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
