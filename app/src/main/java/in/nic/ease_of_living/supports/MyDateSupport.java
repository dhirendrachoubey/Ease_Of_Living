package in.nic.ease_of_living.supports;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Neha Jain on 6/29/2017.
 */
public class MyDateSupport {

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate){

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {

        }

        return outputDate;

    }
    public static long Daybetween(String date1, String date2, String pattern)
    {

        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        Date Date1 = null,Date2 = null;
        long l = 0;
        try{
            Date1 = sdf.parse(date1);
            Date2 = sdf.parse(date2);
            if( (Date2!=null) && (Date1 != null))
                l=(Date2.getTime() - Date1.getTime())/(24*60*60*1000);

        }catch(Exception e)
        {

        }

        return l;
    }

        public static Date addDays(Date date, int days)
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, days); //minus number would decrement the days
            return cal.getTime();
        }

    public static String getCurrentDateTimeforNames()
    {

        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        String strDate = sdf.format(new Date());

        return strDate;
    }

    public static String getCurrentDateTimefordatabaseStorage()
    {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        String strDate = sdf.format(new Date());

        return strDate;
    }

}
