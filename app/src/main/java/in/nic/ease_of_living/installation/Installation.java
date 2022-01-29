package in.nic.ease_of_living.installation;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

import in.nic.ease_of_living.utils.SafeClose;

public class Installation {
    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";

  public synchronized static String id(Context context) {
        if (sID == null) {

          File installation = new File(context.getFilesDir(), INSTALLATION);
          try {
            if (!installation.exists()) {
              writeInstallationFile(installation);
              //getApp_log(context,readInstallationFile(installation));
            }
            sID = readInstallationFile(installation);

          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        }
        return sID;
  }
  private static String readInstallationFile(File installation) throws IOException {
      byte[] bytes;
      RandomAccessFile f = null;
      try {
          f = new RandomAccessFile(installation, "r");
          bytes = new byte[(int) f.length()];
          f.readFully(bytes);
          f.close();
      }finally
      {
          if (f != null)
              SafeClose.safeCloseRandomAccessFile(f);
      }
    return new String(bytes);
  }

  private static void writeInstallationFile(File installation) throws IOException {
      FileOutputStream out = null;
      try{
          out = new FileOutputStream(installation);
          String id = UUID.randomUUID().toString();
          out.write(id.getBytes());
          out.close();
      }finally
      {
          if (out != null)
              SafeClose.safeCloseFileOutputStream(out);
      }

  }


}