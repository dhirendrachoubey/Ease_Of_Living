package in.nic.ease_of_living.supports;

import android.content.Context;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

import in.nic.ease_of_living.utils.AESHelper;

/**
 * Created by Neha Jain on 7/18/2017.
 */

public class Password {

    /* Function to get random salt*/
    public static String getSalt()
    {
        int length = 100;
        Random random = new Random();
        String result = "";

        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch(Exception e) {

        }

        for(int index_len = 0; index_len < length; index_len++)
        {
            long l_random = random.nextLong();
            int n_remainingLength = length - index_len;
            String str_random = String.valueOf(l_random);
            int n_randomLength = str_random.length();
            if( (index_len < length) ) {
                if(n_randomLength > n_remainingLength)
                    str_random = str_random.substring(0, (n_remainingLength + 1));
                result += str_random;
                index_len = result.length();
            }
        }

        return result;
    }

    /* Function to convert String to SHA-256 Hash*/
    public static String sha256(String str) {
        if(str!=null)
        {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(str.getBytes("UTF-8"));
                StringBuffer hexString = new StringBuffer();

                for (byte b : hash) hexString.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));

                return hexString.toString();
            }catch(Exception e)
            {

            }
        }
        return "";
    }

    /* Function to get Final Password
    * Steps
    * 1. Change passw to sha256
    * 2. Concat sha256 passw to salt
    * 3. sha256 (concated passw)
    * 4. sha256 (step 3 String .to uppercase ) */
    public static String getFinalPassword(String strSalt, String str256Pass)
    {
        String strFinalPassw = "";

        String strConcatPassw =  strSalt + str256Pass;
        strFinalPassw = sha256(sha256(strConcatPassw));
        return strFinalPassw;
    }

    public static Boolean matchUserCredentials(Context context, String strUserId, String strPassword)
    {
        String strFinalPwd= Password.sha256(Password.sha256(strPassword));

        if(strUserId.equalsIgnoreCase(AESHelper.getDecryptedValue(context,MySharedPref.getCurrentUser(context).getUser_id()))
                && strFinalPwd.equalsIgnoreCase(MySharedPref.getCurrentUser(context).getUser_password()) )
            return true;
        else
            return false;
    }

}
