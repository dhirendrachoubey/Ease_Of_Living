package in.nic.ease_of_living.dbo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import in.nic.ease_of_living.models.Village;


/**
 * Created by Chinki Sai on 8/1/2017.
 */
public class MasterVillageController {
    private static final String TABLE_NAME = "village";

    private static final String STATE_CODE = "state_code";
    private static final String DISTRICT_CODE = "district_code";
    private static final String SUB_DISTRICT_CODE = "sub_district_code";
    private static final String BLOCK_CODE = "block_code";
    private static final String GP_CODE = "gp_code";
    private static final String VILLAGE_CODE = "village_code";
    private static final String VILLAGE_NAME = "village_name";
    private static final String VILLAGE_NAME_SL = "village_name_sl";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + STATE_CODE + " INTEGER ,"
            + DISTRICT_CODE + " INTEGER ,"
            + SUB_DISTRICT_CODE + " INTEGER ,"
            + BLOCK_CODE + " INTEGER ,"
            + GP_CODE + " INTEGER ,"
            + VILLAGE_CODE + " INTEGER ,"
            + VILLAGE_NAME + " TEXT ,"
            + VILLAGE_NAME_SL + " TEXT "
            + ")";


  //  public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS lgd_village_m ( state_code_lgd integer, state_name_lgd character varying(150), district_code_lgd integer, district_name_lgd character varying(150), block_code_lgd integer, block_name_lgd character varying(150), lgd_code integer, lgd_name character varying(150), village_code_lgd integer, village_name_lgd character varying(150) )";

    public static boolean insert(SQLiteDatabase db, Village pop) {
        ContentValues values = new ContentValues();
        values.put(STATE_CODE , pop.getState_code());
        values.put(DISTRICT_CODE , pop.getDistrict_code());
        values.put(SUB_DISTRICT_CODE , pop.getSub_district_code());
        values.put(BLOCK_CODE , pop.getBlock_code());
        values.put(GP_CODE , pop.getGp_code());
        values.put(VILLAGE_CODE , pop.getVillage_code());
        values.put(VILLAGE_NAME, pop.getVillage_name());
        values.put(VILLAGE_NAME_SL, pop.getVillage_name_sl());

        long row = db.insert(TABLE_NAME, null, values);
        return row >= 0;
    }



    public static ArrayList<Village> getVillageList(SQLiteDatabase db) {
        ArrayList<Village> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                Village data = new Village();
                data.setState_code(cursor.getInt(cursor.getColumnIndex(STATE_CODE )));
                data.setDistrict_code( cursor.getInt(cursor.getColumnIndex(DISTRICT_CODE )) );
                data.setSub_district_code( cursor.getInt(cursor.getColumnIndex(SUB_DISTRICT_CODE )) );
                data.setBlock_code( cursor.getInt(cursor.getColumnIndex(BLOCK_CODE)) );
                data.setGp_code( cursor.getInt(cursor.getColumnIndex(GP_CODE )) );
                data.setVillage_code( cursor.getInt(cursor.getColumnIndex(VILLAGE_CODE)) );
                data.setVillage_name( cursor.getString(cursor.getColumnIndex(VILLAGE_NAME )) );
                data.setVillage_name_sl( cursor.getString(cursor.getColumnIndex(VILLAGE_NAME_SL )) );


                list.add(data);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }
    public static String getVillage_name(SQLiteDatabase db, String village_code) {
        String villageName="";
        Cursor cursor = db.rawQuery("select " + VILLAGE_NAME + " from " + TABLE_NAME+" where "+VILLAGE_CODE+" = ?", new String[]{village_code});

        if (cursor.moveToFirst()) {
                villageName=cursor.getString(cursor.getColumnIndex(VILLAGE_NAME));
        }
        cursor.close();
        return villageName;
    }

    public static Village getVillage(SQLiteDatabase db, String village_code) {
        Village data = new Village();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME+" where "+VILLAGE_CODE+" = ?", new String[]{village_code});

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {

                data.setState_code(cursor.getInt(cursor.getColumnIndex(STATE_CODE )));
                data.setDistrict_code( cursor.getInt(cursor.getColumnIndex(DISTRICT_CODE )) );
                data.setSub_district_code( cursor.getInt(cursor.getColumnIndex(SUB_DISTRICT_CODE )) );
                data.setBlock_code( cursor.getInt(cursor.getColumnIndex(BLOCK_CODE)) );
                data.setGp_code( cursor.getInt(cursor.getColumnIndex(GP_CODE )) );
                data.setVillage_code( cursor.getInt(cursor.getColumnIndex(VILLAGE_CODE)) );
                data.setVillage_name( cursor.getString(cursor.getColumnIndex(VILLAGE_NAME )) );
                data.setVillage_name_sl( cursor.getString(cursor.getColumnIndex(VILLAGE_NAME_SL )) );
                cursor.moveToNext();
            }
            cursor.close();
        }
        return data;
    }


    public static boolean isTableExist(SQLiteDatabase db) {
        {
            boolean isResult = false;
            int isexist = 0;
            String query = "select isexist from ( select count(1)>0 as isexist from sqlite_master where name = 'village' )a";

            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                isexist = cursor.getInt(cursor.getColumnIndex("isexist"));
                if (isexist == 1) {
                    String query2 = "select count(1)>0 as isexist from village";

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
    }
    public static void delete(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }



}
