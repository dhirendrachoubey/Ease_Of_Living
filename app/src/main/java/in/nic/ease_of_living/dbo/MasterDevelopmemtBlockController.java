package in.nic.ease_of_living.dbo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import in.nic.ease_of_living.models.DevelopmentBlock;


/**
 * Created by Chinki Sai on 4/11/2018.
 */

public class MasterDevelopmemtBlockController {

    private static final String TABLE_NAME = "block";
    private static final String STATE_CODE="state_code";
    private static final String DISTRICT_CODE ="district_code";
    private static final String SUB_DISTRICT_CODE ="sub_district_code";
    private static final String BLOCK_CODE ="block_code";
    private static final String BLOCK_NAME ="block_name";
    private static final String BLOCK_NAME_SL ="block_name_sl";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + STATE_CODE + " INTEGER ,"
            + DISTRICT_CODE + " INTEGER ,"
            + SUB_DISTRICT_CODE + " INTEGER ,"
            + BLOCK_CODE + " INTEGER ,"
            + BLOCK_NAME + " TEXT ,"
            + BLOCK_NAME_SL + " TEXT "
            + ")";


    public static boolean insert(SQLiteDatabase db, DevelopmentBlock item) {
        ContentValues values = new ContentValues();
        values.put(	 STATE_CODE , item.getState_code());
        values.put(	 DISTRICT_CODE , item.getDistrict_code());
        values.put(	 SUB_DISTRICT_CODE , item.getSub_district_code());
        values.put(	 BLOCK_CODE , item.getBlock_code());
        values.put(	 BLOCK_NAME , item.getBlock_name());
        values.put(	 BLOCK_NAME_SL , item.getBlock_name_sl()!= null ?item.getBlock_name_sl() : "");
        long row = db.insert(TABLE_NAME, null, values);
        return row >= 0;
    }

    public static ArrayList<DevelopmentBlock> getData(SQLiteDatabase db, String statecode) {

        ArrayList<DevelopmentBlock> list = new ArrayList<DevelopmentBlock>();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " where "+ DISTRICT_CODE + " = ?";
            Cursor cursor = db.rawQuery(selectQuery, new String[]{statecode});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    DevelopmentBlock item = new DevelopmentBlock();
                    item.setState_code(cursor.getString(cursor.getColumnIndex(STATE_CODE)));
                    item.setDistrict_code(cursor.getString(cursor.getColumnIndex(DISTRICT_CODE)));
                    item.setSub_district_code(cursor.getString(cursor.getColumnIndex(SUB_DISTRICT_CODE)));
                    item.setBlock_code(cursor.getString(cursor.getColumnIndex(BLOCK_CODE)));
                    item.setBlock_name(cursor.getString(cursor.getColumnIndex(BLOCK_NAME)));
                    item.setBlock_name_sl(cursor.getString(cursor.getColumnIndex(BLOCK_NAME_SL)));
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

    public static DevelopmentBlock getByid(SQLiteDatabase db, String code) {
        DevelopmentBlock item = new DevelopmentBlock();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME +" where "+DISTRICT_CODE+" = ?";
            Cursor cursor = db.rawQuery(selectQuery, new String[]{code});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    item.setState_code(cursor.getString(cursor.getColumnIndex(STATE_CODE)));
                    item.setDistrict_code(cursor.getString(cursor.getColumnIndex(DISTRICT_CODE)));
                    item.setSub_district_code(cursor.getString(cursor.getColumnIndex(SUB_DISTRICT_CODE)));
                    item.setBlock_code(cursor.getString(cursor.getColumnIndex(BLOCK_CODE)));
                    item.setBlock_name(cursor.getString(cursor.getColumnIndex(BLOCK_NAME)));
                    item.setBlock_name_sl(cursor.getString(cursor.getColumnIndex(BLOCK_NAME_SL)));
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
