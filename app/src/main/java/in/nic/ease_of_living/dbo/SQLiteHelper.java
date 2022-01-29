package in.nic.ease_of_living.dbo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.installation.Installation;
import in.nic.ease_of_living.models.SeccHousehold;
import in.nic.ease_of_living.models.SeccPopulation;
import in.nic.ease_of_living.supports.GetAddress;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MyDateSupport;
import in.nic.ease_of_living.supports.MySharedPref;
import in.nic.ease_of_living.utils.AESHelper;
import in.nic.ease_of_living.utils.AddFileZip;
import in.nic.ease_of_living.utils.SafeClose;

/**
 * Created by Neha Jain on 09-09-2017.
 */
/*025-013*/
public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "EOL";
    private static final int DB_VER = 1;
    private static SQLiteHelper instance;
    private Context context;
    private final String DB_FILEPATH;
    private final String TAG="SQLiteHelper";
    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
        this.context = context;
        final String packageName = context.getPackageName();
    // DB_FILEPATH = Environment.getExternalStorageDirectory().toString()+"/" +DB_NAME;
  DB_FILEPATH = "/data/data/" + packageName + "/databases/"+DB_NAME;
    }

    public static synchronized SQLiteHelper getInstance(Context context) {
        try {
            if (instance == null)
                instance = new SQLiteHelper(context);
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"025-001");
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(SeccPopulationController.CREATE_TABLE);
            db.execSQL(SeccHouseholdController.CREATE_TABLE);
            db.execSQL(HouseholdEolController.CREATE_TABLE);

        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"025-002");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            if(oldVersion < 3)
            {
                context.deleteDatabase(DB_NAME);
                onCreate(db);
            }
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"025-003");
        }
    }

    public void deleteAll(Context context, SQLiteDatabase db)
    {
        try {
            SeccPopulationController.deleteAll(context, db);
            SeccHouseholdController.deleteAll(context, db);
            HouseholdEolController.deleteAll(context, db);
            MySharedPref.saveIsBaseDataAcknowledgePending(context,false);
        }
        catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"025-004");
        }
    }

    public void deleteDb()
    {
        try {
            context.deleteDatabase(DB_NAME);
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error),  e.getMessage(),"025-005");
        }
    }

    /**
     * Copies the database file at the specified location over the current
     * internal application database.
     * */
    public boolean importData(String dbPath, Boolean bImportDuringGetData) throws IOException {
        close();
        SQLiteDatabase dbToImport = SQLiteDatabase.openDatabase(dbPath, null, 0);
        try {
            int total_records = 0;

            // SQLiteDatabase db = DBHelper.getInstance(context, true);

            String queryTable_existence = "SELECT count(1)>0 isexist FROM `sqlite_master` where tbl_name = 'secc_population'";
            Cursor cursorTable_existence = dbToImport.rawQuery(queryTable_existence, null);
            if (cursorTable_existence.moveToFirst()) {
                int isexist = cursorTable_existence.getInt(cursorTable_existence.getColumnIndex("isexist"));
                if(isexist>0)
                {
                    //new SQLiteHelper(context).deleteAll(context, DBHelper.getInstance(context, true));

                    String query = "Select count(1) total_records from secc_population ";
                    Cursor cursor = dbToImport.rawQuery(query, null);
                    if (cursor.moveToFirst()) {
                        total_records = cursor.getInt(cursor.getColumnIndex("total_records"));

                    }
                    ArrayList<SeccPopulation> alPop = new ArrayList<>();
                    ArrayList<SeccHousehold> alhhd = new ArrayList<>();
                    if(total_records > 0)
                    {

                        alPop = SeccPopulationController.getAllData(context, dbToImport);
                        final String strDate = MyDateSupport.getCurrentDateTimefordatabaseStorage();
                        SeccPopulationController.insertAll(context, DBHelper.getInstance(context, true), alPop, strDate);
                        int n_row = SeccPopulationController.getRowsCount(context, DBHelper.getInstance(context, true));

                        Boolean bResult=false;
                        if(n_row > 0)
                        {
                            alhhd = SeccHouseholdController.getAllData(context, dbToImport);

                            if(alhhd.size()>0)
                                SeccHouseholdController.insertAll(context, DBHelper.getInstance(context, true), alhhd, strDate);
                            n_row = SeccHouseholdController.getRowsCount(context, DBHelper.getInstance(context, true));

                            if(n_row>0)
                            {
                                bResult = true;

                            }
                        }

                        if(bResult) {
                            if (!bImportDuringGetData)
                                MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.home_option_import), "Data Imported successfully.", "020-004");
                        }
                        else {
                            deleteAll(context, DBHelper.getInstance(context, true));
                            if(!bImportDuringGetData)
                                MyAlert.showAlert(context,R.mipmap.icon_error,context.getString(R.string.home_option_import) + context.getString(R.string.error),"Unable to import\nPlease try again !!","020-005");
                            return false;
                        }
                    }
                    else {
                        if(!bImportDuringGetData)
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_option_import) + context.getString(R.string.error), "Mismatch Data !!", "020-004");
                    }

                }
                else
                {
                    if(!bImportDuringGetData)
                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_option_import) + context.getString(R.string.error), "File Invalid  !!", "020-005");
                }
            }

        }
        catch (Exception e)
        {
            if(!bImportDuringGetData)
                MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.home_option_import) + context.getString(R.string.error),"Invalid File !!", "020-006");
        }
        finally {
            dbToImport.close();
            dbToImport.releaseReference();
        }

        return false;
    }

    /**
     * Copies the database file at the specified location over the current
     * internal application database.
     * */
    public boolean importDatabase(String dbPath, Boolean bImportDuringGetData) throws IOException {
        close();
        try {
            int total_records = 0;
            SQLiteDatabase db = SQLiteDatabase.openDatabase(dbPath, null, 0);

            // SQLiteDatabase db = DBHelper.getInstance(context, true);

            String queryTable_existence = "SELECT count(1)>0 isexist FROM `sqlite_master` where tbl_name = 'secc_population'";
            Cursor cursorTable_existence = db.rawQuery(queryTable_existence, null);
            if (cursorTable_existence.moveToFirst()) {
                int isexist = cursorTable_existence.getInt(cursorTable_existence.getColumnIndex("isexist"));
                if(isexist>0)
                {
                    /*String query = "Select count(1) total_records from secc_population where town_village_code = ?";
                    Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(MySharedPref.getCurrentUser(context).getVillage_code())});
                    */
                    String query = "Select count(1) total_records from secc_population ";
                    Cursor cursor = db.rawQuery(query, null);
                    if (cursor.moveToFirst()) {
                        total_records = cursor.getInt(cursor.getColumnIndex("total_records"));
                        db.close();
                        db.releaseReference();
                    }
                    if (total_records > 0) {

                        File newDb = new File(dbPath);
                        // File oldDb = new File(DB_FILEPATH);
                        if (newDb.exists()) {
                            this.getWritableDatabase();
                            copyFile(new FileInputStream(newDb), new FileOutputStream(DB_FILEPATH));
                            this.close();
                            if(!bImportDuringGetData)
                                MyAlert.showAlert(context, R.mipmap.icon_info,context.getString(R.string.home_option_import), "Data Imported successfully.","020-004");

                            return true;
                        }
                        else {
                            if(!bImportDuringGetData)
                                MyAlert.showAlert(context,R.mipmap.icon_error,context.getString(R.string.home_option_import) + context.getString(R.string.error),"Unable to import\nPlease try again !!","020-005");
                            return false;
                        }
                    }
                    else {
                        if(!bImportDuringGetData)
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_option_import) + context.getString(R.string.error), "Missmatch SHUV !!", "020-004");
                    }
                    db.close();
                    db.releaseReference();
                }
                else
                {
                    if(!bImportDuringGetData)
                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.home_option_import) + context.getString(R.string.error), "Either File Invalid  !!", "020-005");
                }
            }

        }
        catch (Exception e)
        {
            if(!bImportDuringGetData)
                MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.home_option_import) + context.getString(R.string.error),"Invalid File !!", "020-006");
        }

        return false;
    }


    private void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"025-007");
        }
        finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }

    /* Function to export survey data to a file*/
/*
    public String exportSurveyDataToFile(Context c) throws IOException {

        String str_res = "false";
        OutputStream output = null;
        OutputStream outputDycrpt = null;
        FileInputStream fis = null;
        final ArrayList<GpVillageSurvey> alGpVillageSurveyCompleted = GpVillageUpdatedController.getCompletedVillageList(context, DBHelper.getInstance(context, true));

        try {
            if (isSDCardWriteable()) {

                ArrayList<GpVillageSurvey> alGpVillageBase = GpVillageBaseController.getAllGp(context, DBHelper.getInstance(context, false));

                String strFileName = String.format("%08d", alGpVillageBase.get(0).getGp_code()) +
                        MySharedPref.getDeviceId(context) + MyDateSupport.getCurrentDateTimeforNames() + "_G";

                File folder = new File(Environment.getExternalStorageDirectory() + "/" + "mission_antyodaya");
                if (!folder.exists())
                    folder.mkdirs();

                */
/* Search old files*//*

                File directory = new File(folder.getAbsolutePath());
                File[] files = directory.listFiles();

                for (int i = 0; i < files.length; i++)
                {
                    */
/* Delete old file *//*

                    File fdelete = new File(folder.getAbsolutePath() + "/" + files[i].getName());
                    if (fdelete.exists()
                            && files[i].getName().contains(String.format("%08d", alGpVillageBase.get(0).getGp_code()))
                            && files[i].getName().contains("_G")
                    )
                    {
                        if (fdelete.delete()) {

                        } else {

                        }
                    }
                }

                String outFileName = folder.getAbsolutePath() + "/" + strFileName;
                String outFileNameD = folder.getAbsolutePath() + "/" + "Dycrpt";

                // Open the empty db as the output stream
                output = new FileOutputStream(outFileName);

                String strMac = GetAddress.getWifiMacAddress();

                if(MySharedPref.getMacAddress(context) != null)
                {
                    strMac = MySharedPref.getMacAddress(context);
                }
                else if (strMac != null)
                {
                    MySharedPref.saveMacAddress(context, GetAddress.getWifiMacAddress());
                }

                JSONObject jsInput = new JSONObject();
                jsInput.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));

                jsInput.put("app_id", Installation.id(context));
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                jsInput.put("app_version", packageInfo.versionName);

                if (MySharedPref.getImei(context) != null) {
                    jsInput.put("client_imei_no", MySharedPref.getImei(context));
                } else if (GetAddress.getIMEI(context) != null) {
                    MySharedPref.saveImei(context, GetAddress.getIMEI(context));
                    jsInput.put("client_imei_no", GetAddress.getIMEI(context));
                } else
                    jsInput.put("client_imei_no", "XXXXXXXXXXXXXXX");

                if (GetAddress.getIPAddress(true) != null)
                    jsInput.put("client_ip_address", GetAddress.getIPAddress(true));
                else
                    jsInput.put("client_ip_address", "XXX.XXX.XXX.XXX");

                jsInput.put("client_mac_address", strMac);
                jsInput.put("device_id", MySharedPref.getDeviceId(context));

                Gson gson = new Gson();

                String strGpVillageEnumerated = gson.toJson(
                        alGpVillageSurveyCompleted,
                        new TypeToken<ArrayList<GpVillageChecksum>>() {
                        }.getType());

                JSONArray ja = new JSONArray(strGpVillageEnumerated);

                jsInput.put("villageSurveyList", ja);
                String strEncrypted = (AESHelper.cipher(AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()), jsInput.toString()));
                output.write( strEncrypted.getBytes() );
                // Close the streams
                output.flush();
                output.close();

                */
/*fis = new FileInputStream(outFileName);
                outputDycrpt = new FileOutputStream(outFileNameD);
                StringBuffer fileContent = new StringBuffer("");

                byte[] buffer = new byte[1024];

                int n = 0;
                while ((n = fis.read(buffer)) != -1)
                {
                    fileContent.append(new String(buffer, 0, n));
                }

                String strDecrypted = AESHelper.decipher(AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()), fileContent.toString());
                outputDycrpt.write(strDecrypted.getBytes());
                outputDycrpt.flush();
                outputDycrpt.close();*//*


                str_res = "true";

                File file_created = new File(outFileName);

                str_res = "true";
            }
        }catch(Exception e)
        {
            str_res = e.toString();
            MyAlert.showAlert(context,R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"025-009");
        }
        finally
        {
            if (output != null)
                SafeClose.safeCloseOutputStream(output);
            if(fis != null)
                SafeClose.safeCloseInputStream(fis);
        }
        return str_res;
    }
*/


    private boolean isSDCardWriteable() {
        boolean rc = false;
        try {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                rc = true;
            }
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"025-010");
        }
        return rc;
    }

    public static ArrayList<String> checkTableColumns(Context context, SQLiteDatabase db, String strTableName)
    {
        Cursor c = db.rawQuery("SELECT * FROM " + strTableName + " WHERE 0", null);

        ArrayList<String> al_columnNames = new ArrayList<>();
        try {
            al_columnNames = new ArrayList<String>(Arrays.asList(c.getColumnNames()));
        } catch(Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"025-011");
        }
        return al_columnNames;
    }

    public boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        Boolean bResult = false;
        try {
            //checkDB = SQLiteDatabase.openDatabase(DB_FILEPATH, null, SQLiteDatabase.OPEN_READONLY);
            //checkDB.close();
            File dbFile = context.getDatabasePath(DB_NAME);
            bResult = dbFile.exists();
        } catch (SQLiteException e) {
            checkDB = null;
            // database doesn't exist yet.
        }
        //return checkDB != null;
        return bResult;
    }

    public String backupDatabase(Context c,Boolean isBackup) throws IOException {

        String str_res = "false";
        //ImportExport obj_ie = new ImportExport();
        OutputStream output = null;
        FileInputStream fis = null;

        try {
            if (isSDCardWriteable()) {

                DateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                String strDate = sdf.format(new Date());

                sdf = new SimpleDateFormat("HHmmss");
                String strTime = sdf.format(new Date());

             //   ArrayList<GpVillageSurvey> alGpVillageBase = GpVillageBaseController.getAllGp(context, DBHelper.getInstance(context, false));
                 ArrayList<SeccHousehold> alGpVillageBase = SeccHouseholdController.getAllData(context, DBHelper.getInstance(context, false));

                String strFileName = String.format("%05d", alGpVillageBase.get(0).getDistrict_code()) + "_"
                        + String.format("%05d", alGpVillageBase.get(0).getBlock_code()) + "_" +
                        String.format("%08d", alGpVillageBase.get(0).getGp_code()) + "_B_" +
                        MySharedPref.getDeviceId(context) + MyDateSupport.getCurrentDateTimeforNames() + ".db";
                String strZipName = String.format("%05d", alGpVillageBase.get(0).getDistrict_code()) + "_"
                        + String.format("%05d", alGpVillageBase.get(0).getBlock_code()) + "_" +
                        String.format("%08d", alGpVillageBase.get(0).getGp_code()) + "_B_" +
                        MySharedPref.getDeviceId(context) + MyDateSupport.getCurrentDateTimeforNames() + ".zip";

                // Open your local db as the input stream

/*
                String inFileName = c.getDatabasePath(DB_FILEPATH).getPath();
*/
                String inFileName = c.getDatabasePath(DB_NAME).getPath();
                File dbFile = new File(inFileName);
                fis = new FileInputStream(dbFile);
                File folder = new File(Environment.getExternalStorageDirectory() + "/" + "EOL_backup");
                if (!folder.exists())
                    folder.mkdirs();


                /* Search old files*/
                File directory = new File(folder.getAbsolutePath());
                File[] files = directory.listFiles();
                for (int i = 0; i < files.length; i++)
                {
                    /* Delete old file */
                    File fdelete = new File(folder.getAbsolutePath() + "/" + files[i].getName());
                    if (fdelete.exists() && files[i].getName().contains(String.format("%05d", alGpVillageBase.get(0).getBlock_code()) + "_" +
                            String.format("%08d", alGpVillageBase.get(0).getGp_code()) + "_B_")) {
                        if (fdelete.delete()) {

                        } else {

                        }
                    }
                }


                String outFileName = folder.getAbsolutePath() + "/" + strFileName;

                // Open the empty db as the output stream
                output = new FileOutputStream(outFileName);
                // transfer bytes from the inputfile to the outputfile
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
                // Close the streams
                output.flush();
                output.close();
                fis.close();
                str_res = "true";
                File file_created = new File(outFileName);
                AddFileZip.Zip(outFileName,folder.getAbsolutePath() + "/" + strZipName);

                str_res = "true";
            }
        }catch(Exception e)
        {
            str_res = e.toString();

        }
        finally
        {
            if (output != null)
                SafeClose.safeCloseOutputStream(output);
            if(fis != null)
                SafeClose.safeCloseInputStream(fis);
        }
        return str_res;
    }


}


