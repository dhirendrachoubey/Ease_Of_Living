package in.nic.ease_of_living.dbo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.supports.MyAlert;

/**
 * Created by Neha Jain on 7/12/2017.
 */
public class SQLiteMaster extends SQLiteAssetHelper {
    private Context context;
    private static final String DATABASE_NAME = "Master.db4";
    private static final int DATABASE_VERSION = 1;
    private static SQLiteMaster instance;

    public SQLiteMaster(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
        //setForcedUpgrade();

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            if(oldVersion < 1) {
              /*  context.deleteDatabase(DATABASE_NAME);
                new SQLiteMaster(context);
        */    }

        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"025-003");
        }
    }



    public static synchronized SQLiteMaster getInstance(Context context) {
        if (instance == null)
            instance = new SQLiteMaster(context);
        return instance;
    }

    public void deleteDb()
    {
        context.deleteDatabase(DATABASE_NAME);
    }
}