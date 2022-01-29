package in.nic.ease_of_living.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import in.nic.ease_of_living.gp.BuildConfig;
import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.models.Weburl;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MySharedPref;

/**
 * Created by Neha on 7/23/2018.
 */
/*027-003*/
public class Common {
    private static boolean bIsShowGpDashboard = true;

    public static String getWebUrlValue(Context context, String strUrlKey)
    {
        String strResult = null;
        try {
            ArrayList<Weburl> alWebUrl = MySharedPref.getWebUrl(context);
            if(alWebUrl != null)
                for (int i = 0; i < alWebUrl.size(); i++) {
                    if (alWebUrl.get(i).getConf_key().contains(strUrlKey)) {
                        return alWebUrl.get(i).getConf_value();
                    }
                }
        }catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error),  e.getMessage(),"027-001");
        }
        return  strResult;
    }

    public static void setAppHeader(Context context)
    {
        if( (MySharedPref.getAppMode(context) == null) ||  (MySharedPref.getAppMode(context).equalsIgnoreCase("null")) || MySharedPref.getAppMode(context).equalsIgnoreCase("A") )
            ((AppCompatActivity)context).getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getString(R.string.app_name) + "</font>"));
        else
        {
            ((AppCompatActivity)context).getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>" + context.getString(R.string.app_name) + "</font>"));
            ((AppCompatActivity)context).getSupportActionBar().setSubtitle(Html.fromHtml(" <font color=" + ContextCompat.getColor(context, R.color.yellow) + "> (" + context.getString(R.string.training_mode) + ")</font>"));
        }

    }

    public static boolean isbIsShowGpDashboard() {
        return bIsShowGpDashboard;
    }

    public static void setbIsShowGpDashboard(boolean bIsShowGpDashboard) {
        Common.bIsShowGpDashboard = bIsShowGpDashboard;
    }

    public static Bitmap doCompressImage(Context context, Bitmap bitmap)
    {
        try {
            int origWidth = bitmap.getWidth();
            int origHeight = bitmap.getHeight();

            final int destWidth = 400;//or the width you need

            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outStream);
            byte[] bmpPicByteArray = outStream.toByteArray();
            long lLengthBmp = bmpPicByteArray.length;
            int iCompressQuality = 105;
            int MAX_IMAZE_SIZE = 50000;

            if( (origWidth > destWidth) )
            {
                // picture is wider than we want it, we calculate its target height
                int destHeight = origHeight/( origWidth / destWidth );
                // we create an scaled bitmap so it reduces the image, not just trim it
                bitmap = Bitmap.createScaledBitmap(bitmap, destWidth, destHeight, false);
                outStream = new ByteArrayOutputStream();
                // compress to the format you want, JPEG, PNG...
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
                bmpPicByteArray = outStream.toByteArray();
                lLengthBmp = bmpPicByteArray.length;
            }
            while( ( lLengthBmp > MAX_IMAZE_SIZE) && (iCompressQuality > 5))
            {
                try {
                    outStream.flush();//to avoid out of memory error
                    outStream.reset();
                } catch (IOException e) {
                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error),  e.getMessage(),"027-002");
                }
                iCompressQuality -= 5;
                bitmap.compress(Bitmap.CompressFormat.JPEG, iCompressQuality, outStream);
                bmpPicByteArray = outStream.toByteArray();
                lLengthBmp = bmpPicByteArray.length;
            }

            if(BuildConfig.DEBUG) {

            }

            /*// we save the file, at least until we have made use of it
            File f = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "test.jpg");
            f.createNewFile();
            //write the bytes in file
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(outStream.toByteArray());
            // remember close de FileOutput
            fo.close();*/

        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.login_error),  e.getMessage(),"027-003");
        }
        return bitmap;
    }


    public static boolean isOdd( int val )
    { return (val & 0x01) != 0; }

    /**
     * Converts a HashMap.toString() back to a HashMap
     * @param text
     * @return HashMap<String, String>
     */
    public static HashMap<String,String> convertToStringToHashMap(String text){
        if(text==null)
            return null;
        HashMap<String,String> data = new HashMap<String,String>();
        Pattern p = Pattern.compile("[\\{\\}\\=\\, ]++");
        String[] split = p.split(text);
        for ( int i=1; i+2 <= split.length; i+=2 ){
            data.put( split[i], split[i+1] );
        }
        return data;
    }

    /* Concate String to form Array*/
    public static String joinString(ArrayList<String> alInput)
    {
        if((alInput == null ) || (alInput.size() == 0))
            return null;
        String strRes = null;
        strRes = alInput.get(0);
        for(int i=1; i<alInput.size(); i++)
        {
            strRes+=","+ alInput.get(i);
        }
        return strRes;
    }

    /* Concate String to form Array*/
    public static String joinIntegerToString(ArrayList<Integer> alInput)
    {
        if((alInput == null ) || (alInput.size() == 0))
            return null;
        String strRes = null;
        strRes = String.valueOf(alInput.get(0));
        for(int i=1; i<alInput.size(); i++)
        {
            strRes+=","+ String.valueOf(alInput.get(i));
        }
        return strRes;
    }

    /* Convert String to Array*/
    public static ArrayList<Integer> convertStringToArrayListInt(String str)
    {
        ArrayList<Integer> alRes = new ArrayList<>();
        if((str !=null) && (str.length()>0))
        {
            for(int i=0; i < str.split(",").length; i++)
            {
                alRes.add(Integer.parseInt(str.split(",")[i]));
            }
        }
        return alRes;
    }

    /* Convert String to Array*/
    public static ArrayList<String> convertStringToArrayListString(String str)
    {
        ArrayList<String> alRes = new ArrayList<>();
        if((str !=null) && (str.length()>0))
        {
            for(int i=0; i < str.split(",").length; i++)
            {
                alRes.add(str.split(",")[i]);
            }
        }
        return alRes;
    }


}
