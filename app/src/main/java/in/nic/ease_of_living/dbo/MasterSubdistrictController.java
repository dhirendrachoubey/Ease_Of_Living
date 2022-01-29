package in.nic.ease_of_living.dbo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import in.nic.ease_of_living.models.SubDistrict;


/**
 * Created by Chinki Sai on 4/11/2018.
 */

public class MasterSubdistrictController {

    private static final String TABLE_NAME = "subdistrict";
    private static final String STATE_CODE="state_code";
    private static final String DISTRICT_CODE ="district_code";
    private static final String SUB_DISTRICT_CODE ="sub_district_code";
    private static final String SUB_DISTRICT_NAME ="sub_district_name";
    private static final String SUB_DISTRICT_NAME_SL ="sub_district_name_sl";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + STATE_CODE + " INTEGER ,"
            + DISTRICT_CODE + " INTEGER ,"
            + SUB_DISTRICT_CODE + " INTEGER ,"
            + SUB_DISTRICT_NAME + " TEXT ,"
            + SUB_DISTRICT_NAME_SL + " TEXT "
            + ")";
    


    public static boolean insert(SQLiteDatabase db, SubDistrict item) {
        ContentValues values = new ContentValues();
        values.put(	 STATE_CODE , item.getState_code());
        values.put(	 DISTRICT_CODE , item.getDistrict_code());
        values.put(	 SUB_DISTRICT_CODE , item.getSub_district_code());
        values.put(	 SUB_DISTRICT_NAME , item.getSub_district_name());
        values.put(	 SUB_DISTRICT_NAME_SL , item.getSub_district_name_sl()!= null ?item.getSub_district_name_sl() : "");
        long row = db.insert(TABLE_NAME, null, values);
        return row >= 0;
    }

    public static ArrayList<SubDistrict> getData(SQLiteDatabase db, String statecode) {

        ArrayList<SubDistrict> list = new ArrayList<SubDistrict>();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " where "+ STATE_CODE + " = ?";
            Cursor cursor = db.rawQuery(selectQuery, new String[]{statecode});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    SubDistrict item = new SubDistrict();
                    item.setState_code(cursor.getString(cursor.getColumnIndex(STATE_CODE)));
                    item.setDistrict_code(cursor.getString(cursor.getColumnIndex(DISTRICT_CODE)));
                    item.setSub_district_code(cursor.getString(cursor.getColumnIndex(SUB_DISTRICT_CODE)));
                    item.setSub_district_name(cursor.getString(cursor.getColumnIndex(SUB_DISTRICT_NAME)));
                    item.setSub_district_name_sl(cursor.getString(cursor.getColumnIndex(SUB_DISTRICT_NAME_SL)));
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

    public static SubDistrict getByid(SQLiteDatabase db, String code) {
        SubDistrict item = new SubDistrict();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME +" where "+DISTRICT_CODE+" = ?";
            Cursor cursor = db.rawQuery(selectQuery, new String[]{code});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    item.setState_code(cursor.getString(cursor.getColumnIndex(STATE_CODE)));
                    item.setDistrict_code(cursor.getString(cursor.getColumnIndex(DISTRICT_CODE)));
                    item.setSub_district_code(cursor.getString(cursor.getColumnIndex(SUB_DISTRICT_CODE)));
                    item.setSub_district_name(cursor.getString(cursor.getColumnIndex(SUB_DISTRICT_NAME)));
                    item.setSub_district_name_sl(cursor.getString(cursor.getColumnIndex(SUB_DISTRICT_NAME_SL)));
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
