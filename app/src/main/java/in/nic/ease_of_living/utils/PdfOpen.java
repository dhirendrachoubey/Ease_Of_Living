package in.nic.ease_of_living.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import in.nic.ease_of_living.gp.BuildConfig;
import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.supports.MyAlert;

/**
 * Created by Neha Jain on 9/20/2017.
 */
/*,"028-006"*/
public class PdfOpen {
    static String saveFilePath;
    public static void loadPdf(Context context, String strFileName) {
        Document document = new Document();

        try {
            AssetManager assetManager = context.getAssets();
            InputStream in = assetManager.open(strFileName);
            File path = Environment.getExternalStorageDirectory();
            File folder = new File(path + "/" + "Ma_GP_Pdf");
            if (!folder.exists())
                folder.mkdirs();

            saveFilePath = folder.getAbsolutePath() + File.separator + strFileName;

            PdfReader pdfReader = new PdfReader(in);

            PdfStamper pdfStamper = new PdfStamper(pdfReader,
                    new FileOutputStream(folder.getAbsolutePath() + File.separator + strFileName));

            BaseColor myColorpan = WebColors.getRGBColor("#142E74");

            for(int i=1; i<= pdfReader.getNumberOfPages(); i++){

                PdfContentByte content = pdfStamper.getUnderContent(i);

                content = pdfStamper.getOverContent(i);
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.WINANSI, BaseFont.EMBEDDED);
                content.beginText();
                content.setColorStroke(myColorpan);
                content.setFontAndSize(bf, 40);
                content.setTextMatrix(60, 740);
                content.showText("");
                content.endText();

            }

            pdfStamper.close();
            document.close();
            viewPdf(context,saveFilePath);
        } catch (FileNotFoundException e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error), e.getMessage(),"028-001");
        } catch (DocumentException e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error),  e.getMessage(),"028-002");
        } catch (Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error), e.getMessage(),"028-003");
        }

    }


    public static void viewPdf(Context context,String filePath) {

        try {
            //File pdfFile = new File(FILE);
            File spath= new File(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString());
            if(!spath.exists()){spath.mkdir();}
            //  File pdfFile = new File(spath, "gram_panchayat"+census2017_status.getGp_code()+".pdf");
            File pdfFile = new File(filePath);

            // Setting the intent for pdf reader
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);

            Uri path;
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if(currentAPIVersion < Build.VERSION_CODES.N) {
                path = Uri.fromFile(pdfFile);
            }
            else
            {
                path = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", pdfFile);
            }

            pdfIntent.setDataAndType(path, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            context.startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error), context.getString(R.string.generate_pdf_view_failure) ,"028-004");
        } catch( Exception e)
        {
            if(e.getMessage().contains("Intent.getData"))
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error),  context.getString(R.string.generate_pdf_view_failure) ,"028-005");
            else
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error),  e.getMessage(),"028-006");
        }
    }

}
