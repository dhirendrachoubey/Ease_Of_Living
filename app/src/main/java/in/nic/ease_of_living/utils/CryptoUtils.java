package in.nic.ease_of_living.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * A utility class that encrypts or decrypts a file.
 * @author www.codejava.net
 *
 */
public class CryptoUtils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private static String algorithm = "AES";

    public static void encrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }
 
    public static void decrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }
 
    private static void doCrypto(int cipherMode, String encodekey, File inputFile,
            File outputFile) throws CryptoException {
        try {

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);

            byte[] key = encodekey.getBytes("UTF-8");

            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // use only first 128 bit

            SecretKeySpec secretKeySpec = new SecretKeySpec(key, algorithm);

            cipher.init(cipherMode, secretKeySpec);


           ////////// copy file to destination
            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);
            FileOutputStream outputStream = null;
             try{
                 byte[] outputBytes = cipher.doFinal(inputBytes);
                 outputStream = new FileOutputStream(outputFile);
                 outputStream.write(outputBytes);
             }catch(Exception e){
               System.out.print(e.getMessage());
             }finally {
                 inputStream.close();
                 if(outputStream!=null)
                 outputStream.close();
             }


             
        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException |/* BadPaddingException
                | IllegalBlockSizeException |*/ IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }


}

