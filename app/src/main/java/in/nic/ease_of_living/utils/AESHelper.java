package in.nic.ease_of_living.utils;

import android.content.Context;

import java.security.KeyStore;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import in.nic.ease_of_living.supports.MySharedPref;
import in.nic.ease_of_living.supports.Password;

public class AESHelper {
    KeyStore keyStore;


    private final static String HEX = "0123456789ABCDEF";
    private final static String ALGORITHM = "AES";

    /**
     * Encrypt data
     * @param secretKey - a secret key used for encryption
     * @param data - data to encrypt
     * @return Encrypted data
     * @throws Exception
     */
    public static String cipher(String secretKey, String data) throws Exception {
        if(data == null || Support.isWhite_space(data))
            return "";
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      //  SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmbcSHA1");
        KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), secretKey.getBytes(), 128, 128/*256*/);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey key = new SecretKeySpec(tmp.getEncoded(), ALGORITHM);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return toHex(cipher.doFinal(data.getBytes()));
    }

    /**
     * Decrypt data
     * @param secretKey - a secret key used for decryption
     * @param data - data to decrypt
     * @return Decrypted data
     * @throws Exception
     */
    public static String decipher(String secretKey, String data) throws Exception {

        if(data == null || Support.isWhite_space(data))
            return "";
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      //  SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmbcSHA1");
        KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), secretKey.getBytes(), 128, 128/*256*/);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey key = new SecretKeySpec(tmp.getEncoded(), ALGORITHM);

        Cipher cipher = Cipher.getInstance(ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, key);

        return new String(cipher.doFinal(toByte(data)));
    }

// Helper methods

    private static byte[] toByte(String hexString) {
        int len = hexString.length()/2;

        byte[] result = new byte[len];

        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
        return result;
    }

    public static String toHex(byte[] stringBytes) {
        StringBuffer result = new StringBuffer(2*stringBytes.length);

        for (int i = 0; i < stringBytes.length; i++) {
            result.append(HEX.charAt((stringBytes[i]>>4)&0x0f)).append(HEX.charAt(stringBytes[i]&0x0f));
        }

        return result.toString();
    }

    public static String getDecryptedValue(Context context, String str_encryptedValue)
    {
        try {
            return decipher(decipher(Password.sha256(MySharedPref.getDeviceId(context)), MySharedPref.getCurrentUser(context).getApplication_value()), str_encryptedValue);
        }catch(Exception e)
        {

        }
        return "";
    }


    public static String getEncryptedValue(Context context, String strDecryptedValue)
    {
        try {
            String strAppValue = null;
            if(MySharedPref.getCurrentUser(context) != null)
                strAppValue = MySharedPref.getCurrentUser(context).getApplication_value();
            else if(MySharedPref.getEnuUser(context) != null)
                strAppValue = MySharedPref.getEnuUser(context).getApplication_value();
            return cipher(decipher(Password.sha256(MySharedPref.getDevice_ID(context)), strAppValue), strDecryptedValue);
        }catch(Exception e)
        {

        }
        return "";
    }

}
