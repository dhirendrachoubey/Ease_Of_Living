package in.nic.ease_of_living.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * Created by Neha on 8/28/2017.
 */

public class SafeClose {

    public static void safeCloseFileInputStream(FileInputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {

            }
        }
    }

    public static void safeCloseFileOutputStream(FileOutputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {

            }
        }
    }

    public static void safeCloseInputStream(InputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {

            }
        }
    }

    public static void safeCloseOutputStream(OutputStream fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {

            }
        }
    }

    public static void safeCloseRandomAccessFile(RandomAccessFile fis) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {

            }
        }
    }
}
