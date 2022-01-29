package in.nic.ease_of_living.dbo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import in.nic.ease_of_living.models.District;


/**
 * Created by Chinki Sai on 11/22/2017.
 */

public class MasterDistrictController {
    private static final String TABLE_NAME = "district";
    private static final String STATE_CODE ="state_code";
    private static final String DISTRICT_CODE ="district_code";
    private static final String DISTRICT_NAME ="district_name";
    private static final String DISTRICT_NAME_SL ="district_name_sl";
    private static final String STATE_NAME_SL ="state_name_sl";
    private static final String LANG_CODE ="lang_code";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + STATE_CODE + " INTEGER ,"
            + DISTRICT_CODE + " INTEGER ,"
            + DISTRICT_NAME + " TEXT ,"
            + DISTRICT_NAME_SL + " TEXT ,"
            + STATE_NAME_SL + " TEXT ,"
            + LANG_CODE + " TEXT "
            + ")";

    public static boolean insert(SQLiteDatabase db, District item) {
        ContentValues values = new ContentValues();
        values.put(	 STATE_CODE , item.getState_code());
        values.put(	 DISTRICT_CODE , item.getDistrict_code());
        values.put(	 DISTRICT_NAME , item.getDistrict_name());
        values.put(	 DISTRICT_NAME_SL , item.getDistrict_name_sl()!= null ?item.getDistrict_name_sl() : "");
        values.put(	 STATE_NAME_SL , item.getState_name_sl()!= null ?item.getState_name_sl() : "");
        values.put(	 LANG_CODE , item.getLang_code()!= null ?item.getLang_code() : "");
        long row = db.insert(TABLE_NAME, null, values);
        return row >= 0;
    }

    public static ArrayList<District> getData(SQLiteDatabase db, int statecode) {

        ArrayList<District> list = new ArrayList<District>();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " where "+ STATE_CODE + " = ?";
            Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(statecode)});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    District item = new District();
                    item.setState_code(cursor.getString(cursor.getColumnIndex(STATE_CODE)));
                    item.setDistrict_code(cursor.getString(cursor.getColumnIndex(DISTRICT_CODE)));
                    item.setDistrict_name(cursor.getString(cursor.getColumnIndex(DISTRICT_NAME)));
                    item.setDistrict_name_sl(cursor.getString(cursor.getColumnIndex(DISTRICT_NAME_SL)));
                    item.setState_name_sl(cursor.getString(cursor.getColumnIndex(STATE_NAME_SL)));
                    item.setLang_code(cursor.getString(cursor.getColumnIndex(LANG_CODE)));
                    list.add(item);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch(Exception e)
        {
            System.out.println("Exception : " + e.toString());
        }
        return list;
    }

    public static District getByid(SQLiteDatabase db, String code) {
        District item = new District();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME +" where "+DISTRICT_CODE+" = ?";
            Cursor cursor = db.rawQuery(selectQuery, new String[]{code});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    item.setState_code(cursor.getString(cursor.getColumnIndex(STATE_CODE)));
                    item.setDistrict_code(cursor.getString(cursor.getColumnIndex(DISTRICT_CODE)));
                    item.setDistrict_name(cursor.getString(cursor.getColumnIndex(DISTRICT_NAME)));
                    item.setDistrict_name_sl(cursor.getString(cursor.getColumnIndex(DISTRICT_NAME_SL)));
                    item.setState_name_sl(cursor.getString(cursor.getColumnIndex(STATE_NAME_SL)));
                    item.setLang_code(cursor.getString(cursor.getColumnIndex(LANG_CODE)));
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch(Exception e)
        {
            System.out.println("Exception : " + e.toString());
        }
        return item;
    }
    public static void delete(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }
}
