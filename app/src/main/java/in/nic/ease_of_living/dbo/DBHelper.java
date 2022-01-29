package in.nic.ease_of_living.dbo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by Neha Jain on 09-09-2015.
 */
public class DBHelper {

    Context context;
    private DBHelper(Context context) {
        this.context = context;
    }

    public static SQLiteDatabase getInstance(Context context, boolean isWritable) {
        return isWritable ? SQLiteHelper.getInstance(context)
                .getWritableDatabase() : SQLiteHelper.getInstance(context)
                .getReadableDatabase();

    }


}
