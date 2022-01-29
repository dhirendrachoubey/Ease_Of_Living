package in.nic.ease_of_living.dbo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Neha Jain on 7/12/2017.
 */
public class DBMaster {
    Context context;
    private DBMaster(Context context) {
        this.context = context;
    }

    public static SQLiteDatabase getInstance(Context context, boolean isWritable) {
        return isWritable ? SQLiteMaster.getInstance(context)
                .getWritableDatabase() : SQLiteMaster.getInstance(context)
                .getReadableDatabase();

    }
}
