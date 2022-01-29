package in.nic.ease_of_living.dbo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import in.nic.ease_of_living.models.State;


/**
 * Created by Chinki Sai on 4/16/2018.
 */

public class MasterStateController {
    private static final String TABLE_NAME = "state";
    private static final String STATE_CODE="state_code";
    private static final String STATE_NAME ="state_name";
    private static final String STATE_NAME_NL ="state_name_nl";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
            + STATE_CODE + " INTEGER ,"
            + STATE_NAME + " TEXT ,"
            + STATE_NAME_NL + " TEXT "
            + ")";

    public static boolean insert(SQLiteDatabase db, State item) {
        ContentValues values = new ContentValues();
        values.put(	 STATE_CODE , item.getState_code());
        values.put(	 STATE_NAME , item.getState_name());
        values.put(	 STATE_NAME_NL , item.getState_name_nl());
        long row = db.insert(TABLE_NAME, null, values);
        return row >= 0;
    }

    public static ArrayList<State> getData(SQLiteDatabase db) {

        ArrayList<State> list = new ArrayList<State>();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME;
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    State item = new State();
                    item.setState_code(cursor.getString(cursor.getColumnIndex(STATE_CODE)));
                    item.setState_name(cursor.getString(cursor.getColumnIndex(STATE_NAME)));
                    item.setState_name_nl(cursor.getString(cursor.getColumnIndex(STATE_NAME_NL)));
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
    public static void delete(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

}



