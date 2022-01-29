package in.nic.ease_of_living.utils;

import android.content.Context;

import net.lingala.zip4j.core.ZipFile;

import java.io.File;

import in.nic.ease_of_living.dbo.SQLiteHelper;
import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MySharedPref;


public class FileUtils {

    static String  sourceFile;
    static File directory;
    public  static  String TAG="FileUtils";
    public  static void unzip(Context ctx,String source,String destination,boolean bIsImport,boolean bIsEncrypted, boolean isImportduringGetBaseData) {
        try {
            ZipFile zipFile = new ZipFile(source);
             directory = new File(destination);
            File[] files = directory.listFiles();
            if(files!=null) {
                for (int i = 0; i < files.length; i++) {
                    //Delete old file
                    File fdelete = new File(directory.getPath() + "/" + files[i].getName());
                    /*if (fdelete.exists()
                            && files[i].getName().contains(String.format("%08d", 10084))
                            && files[i].getName().toLowerCase().contains("_b_")
                            ) */
                    if (fdelete.exists())
                    {
                        if (fdelete.delete()) {

                        } else {

                        }
                    }
                }
            }
             zipFile.extractAll(destination);
            File[] files1 = directory.listFiles();

            for (File f : files1) {
                if(bIsImport)
                    if (f.isFile() && f.getName().contains(String.format("%08d", 10084)) && f.getName().endsWith(".db")) {
                        sourceFile = f.getAbsolutePath();
                    }
                else
                        sourceFile = f.getAbsolutePath();

            }


            if(bIsImport) {
                // Encrypt and Dycrypt
                if(bIsEncrypted) {
                 String key = "$2a$10$u1RWttx2W5wS9opJv79bTuPNoYGIaN.PFOnLMSqpjlxf3KR/mmz/e/";
                    File inputFile = new File(sourceFile);
                    //String decFileName = directory.getAbsolutePath() + File.separator + "Test.db";
                    String decFileName = sourceFile.split(".db")[0] + "_eol" + ".db";
                    File decryptedFile = new File(decFileName);

                    try {
                        CryptoUtils.decrypt(key, inputFile, decryptedFile);
                    } catch (Exception ex) {

                    }

                    new SQLiteHelper(ctx).importData(decFileName, isImportduringGetBaseData);
                }
                else
                    new SQLiteHelper(ctx).importData(sourceFile, isImportduringGetBaseData);

                // new SQLiteHelper(ctx).importDatabase(sourceFile, false);

           }

        } catch (Exception e) {

            MyAlert.showAlert(ctx, R.mipmap.icon_error, ctx.getString(R.string.home_option_import) + ctx.getString(R.string.error), "File Invalid  !!", "020-005");

        }
    }

}