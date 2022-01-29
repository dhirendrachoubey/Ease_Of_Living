package in.nic.ease_of_living.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseFont;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import in.nic.ease_of_living.gp.BuildConfig;
import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.models.Village;
import in.nic.ease_of_living.supports.MyAlert;

/**
 * Created by Neha Jain on 5/27/2018.
 */
/*,"018-031"*/
public class GeneratePdf {


    private BaseFont baseFont;

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 8);
    private static Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD);
    private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD|Font.UNDERLINE);
    private static Font villageFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD|Font.UNDERLINE);
    private static Font disclaimerFont = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.BOLDITALIC);
    BaseColor myColorpan = WebColors.getRGBColor("#237DC1");
  //  private ArrayList<GpVillageSurvey> al_village_completed=new ArrayList<>();
    private Context context;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yy");
    private int n_completedVillages = 0;
    private int n_synchronizedVillages = 0;
    private int iUploadedVillages = 0;
   // private ArrayList<GpVillageSurvey> alVillage=new ArrayList<>();
    private Village village = null;
    private int iSpinnerVillageSelected = 0;
    private String strFileName = null;
    private int nPdfType = 0; // 1-gp, 2-village draft, 3- village final

    private String strUserId = null;
    private String strPwd = null;

    // Method for opening a pdf file
    public void viewPdf(Context context, String filePath) {

        try {
            //File pdfFile = new File(FILE);
            File spath= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString());
            if(!spath.exists()){spath.mkdir();}
            //  File pdfFile = new File(spath, "gram_panchayat"+census2017_status.getLgd_code()+".pdf");
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
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error), context.getString(R.string.generate_pdf_view_failure) ,"018-001");
        } catch( Exception e)
        {
            if(e.getMessage().contains("Intent.getData"))
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error),  context.getString(R.string.generate_pdf_view_failure)  ,"018-002");
            else
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error),  e.getMessage() ,"018-003");
        }
    }


   /* public void dialogForPDF(final ProgressDialog pd, final Context context, final Boolean bViewPdf)
    {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_pdf_options);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.transparent);
        this.strUserId = strUserId;
        this.strPwd = strPwd;

        final TextView text = (TextView) dialog.findViewById(R.id.tvMessage);
        final RadioGroup rgPdfSelect = (RadioGroup) dialog.findViewById(R.id.rgPdfSelect);
        final RadioButton rbPdfGp = (RadioButton) dialog.findViewById(R.id.rbPdfGp);
        final RadioButton rbPdfVillageDraft = (RadioButton) dialog.findViewById(R.id.rbPdfVillageDraft);
        final RadioButton rbPdfVillageFinal = (RadioButton) dialog.findViewById(R.id.rbPdfVillageFinal);
        final TextView tvSelectVillage = (TextView) dialog.findViewById(R.id.tvSelectVillage);
        final Spinner spinnerVillage = (Spinner) dialog.findViewById(R.id.spinnerVillage);
        final LinearLayout llVillage = (LinearLayout) dialog.findViewById(R.id.llVillage);
        final Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);
        final ImageView ivClose = (ImageView) dialog.findViewById(R.id.ivClose);
        nPdfType = 0;
        PopulateMasterLists.populateMastersList(context);

        rbPdfGp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvSelectVillage.setVisibility(View.GONE);
                llVillage.setVisibility(View.GONE);
            }
        });

        rbPdfVillageDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerVillage.setAdapter(PopulateMasterLists.adapterVillage);
                tvSelectVillage.setVisibility(View.VISIBLE);
                llVillage.setVisibility(View.VISIBLE);
            }
        });

        rbPdfVillageFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerVillage.setAdapter(PopulateMasterLists.adapterVillage);
                tvSelectVillage.setVisibility(View.VISIBLE);
                llVillage.setVisibility(View.VISIBLE);
            }
        });

        spinnerVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                village = PopulateMasterLists.adapterVillage.getItem(i);
                if(i==0)
                    iSpinnerVillageSelected = 0;
                else
                    iSpinnerVillageSelected = village.getVillage_code();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.dismiss();
                dialog.dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbPdfGp.isChecked())
                {
                    nPdfType = 1;
                    final ArrayList<GpVillageSurvey> alGpVillageSurveyCompletedGenPdf = GpVillageUpdatedController.getCompletedVillageList(context, DBHelper.getInstance(context, true));
                    final ArrayList<Village> alVillageGenPdf = GpVillageBaseController.getAllVillage(context, DBHelper.getInstance(context, true));
                    final ArrayList<Village> alUninhabitatedVillageGenPdf = GpVillageBaseController.getUninhabitedVillageList(context, DBHelper.getInstance(context, true));
                    if ((alGpVillageSurveyCompletedGenPdf.size() + alUninhabitatedVillageGenPdf.size()) != alVillageGenPdf.size()) {
                        MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.generate_pdf_info),  context.getString(R.string.generate_pdf_msg_submit_village),"018-031");
                    } else {
                        strFileName = MySharedPref.getCurrentUser(context).getGp_name()+"_"+alGpVillageSurveyCompletedGenPdf.get(0).getGp_code()+".pdf";
                        checkFontFileExistence(pd, context, alGpVillageSurveyCompletedGenPdf, bViewPdf, strFileName, "");
                        dialog.dismiss();
                    }
                }
                else if(rbPdfVillageDraft.isChecked())
                {
                    nPdfType = 2;
                    if(iSpinnerVillageSelected == 0)
                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error),  context.getString(R.string.msg_select_village) ,"018-027");
                    else
                    {
                        ArrayList <GpVillageSurvey> alGpVillageSurvey1 = GpVillageUpdatedController.getSelectedVillage1(context, DBHelper.getInstance(context, true), iSpinnerVillageSelected);
                        if(alGpVillageSurvey1.size() > 0)
                        {
                            strFileName = alGpVillageSurvey1.get(0).getVillage_name()+"_"+alGpVillageSurvey1.get(0).getVillage_code()+".pdf";
                            checkFontFileExistence(pd, context, alGpVillageSurvey1, bViewPdf,strFileName, context.getString(R.string.watermark_village));
                        }
                        else
                        {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error),  context.getString(R.string.msg_village_not_found) ,"018-028");
                        }
                    }
                }
                else
                {
                    nPdfType = 3;
                    if(iSpinnerVillageSelected == 0)
                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error),  context.getString(R.string.msg_select_village) ,"018-029");
                    else
                    {
                        ArrayList <GpVillageSurvey> alGpVillageSurvey1 = GpVillageUpdatedController.getSelectedVillage1(context, DBHelper.getInstance(context, true), iSpinnerVillageSelected);
                        if(alGpVillageSurvey1.size() > 0)
                        {
                            if(alGpVillageSurvey1.get(0).getIs_completed())
                            {
                                strFileName = alGpVillageSurvey1.get(0).getVillage_name()+"_"+alGpVillageSurvey1.get(0).getVillage_code()+".pdf";
                                checkFontFileExistence(pd, context, alGpVillageSurvey1, bViewPdf,strFileName, "");
                            }
                            else
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error),  context.getString(R.string.msg_village_not_completed ),"018-030");
                        }
                        else
                        {
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error),  context.getString(R.string.msg_village_not_found) ,"018-031");
                        }
                    }
                }
            }
        });

        dialog.show();

    }

    public void checkFontFileExistence(final ProgressDialog pd, final Context context, final ArrayList<GpVillageSurvey> alGpVillageSurvey, final Boolean bViewPdf, final String strFileName, final String strWatermark)
    {
        if ((pd == null) || ((pd != null) && (!pd.isShowing()))) {
            pd.show();
        }
        this.strPwd = strPwd;
        this.context = context;

        if(MySharedPref.getLocaleLanguage(context)==null|| MySharedPref.getLocaleLanguage(context).equalsIgnoreCase("en")) {
            createPdf(pd, context, alGpVillageSurvey, bViewPdf, null, strWatermark);
        }
        else
        {
            try{

                (new GeneratePdfFileHtml()).exportDataToHtml(pd, context, strFileName, alGpVillageSurvey,bViewPdf, nPdfType, strWatermark, MySharedPref.getLocaleLanguage(context));
            }catch(Exception e)
            {

            }
        }

    }


    public void createPdf(final ProgressDialog pd, Context context, ArrayList<GpVillageSurvey> alGpVillageSurvey, Boolean bViewPdf,
                          String strLanguageLocale, String strWatermark) {
        try {
            Document document = new Document(PageSize.A4);
            this.context = context;
            n_completedVillages = 0;
            n_synchronizedVillages = 0;
            iUploadedVillages = 0;
            al_village_completed = GpVillageUpdatedController.getCompletedVillageList(context, DBHelper.getInstance(context, true));
            alVillage = GpVillageUpdatedController.getAllGp(context, DBHelper.getInstance(context, true));

            if (alVillage.size() == 0)
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error),  context.getString(R.string.generate_pdf_msg_no_data) ,"018-014");
            else {
                PopulateMasterLists.populateMastersList(context);

 Find out completed and synchronized villages

                for(int n_village = 0; n_village < alVillage.size(); n_village++)
                {
                    if(alVillage.get(n_village).getIs_completed())
                        n_completedVillages++;
                    if(alVillage.get(n_village).getIs_synchronized())
                        n_synchronizedVillages++;
                    if(alVillage.get(n_village).getIs_uploaded())
                        iUploadedVillages++;
                }
            }

            document.setMargins(5, 5, 25, 25);
            File path = Environment.getExternalStorageDirectory();
            File folder = new File(path + "/" + "GP_Pdf");
            if (!folder.exists())
                folder.mkdirs();

            String saveFilePath = folder.getAbsolutePath() + File.separator + strFileName;
            File file = new File(saveFilePath);

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            writer.setPageEvent(new MyFooter());
            writer.setPageEvent(new WatermarkPageEvent(strWatermark));
            document.open();


            addContent(context, document,alGpVillageSurvey);
            document.close();

            pd.dismiss();
            if(bViewPdf)
                viewPdf(context, saveFilePath);
            if(strLanguageLocale!=null)
            {
                Locale locale = new Locale(strLanguageLocale);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                // MySharedPref.saveLocaleLanguage(context, locale);
                context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
            }

            MySharedPref.getCurrentUser(context).setB_isPdfGenerated(true);
        } catch (FileNotFoundException e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error), e.getMessage() ,"018-015");
        } catch (DocumentException e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error),  e.getMessage() ,"018-016");
        } catch (Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error), e.getMessage() ,"018-017");
        }
    }

    private void addContent(Context context, Document document,ArrayList<GpVillageSurvey>  alGpVillageSurvey) throws DocumentException {

        try {

            document.addTitle(context.getString(R.string.pdf_title));
            try {

                InputStream ims = context.getAssets().open("ic_launcher_logo.png");
                Bitmap bmp = BitmapFactory.decodeStream(ims);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);


                Image image = Image.getInstance(stream.toByteArray());
                //document.setNumberDepth(0);
                //document.add(new Chunk(image,(int)((PageSize.A4.getWidth())*3/4)-10,0-image.getHeight()/4));

            } catch (IOException e) {
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error),  e.getMessage() ,"018-018");
            }


            for (int i = 0; i < alGpVillageSurvey.size(); i++) {
                GpVillageSurvey gp_survey_base = GpVillageBaseController.getGp(context, DBHelper.getInstance(context, true), alGpVillageSurvey.get(i).getVillage_code());

                Chapter catPart = new Chapter(Element.TITLE);
                if (i == 0) {
                    try {
                        InputStream ims = context.getAssets().open("ic_launcher_logo.png");
                        Bitmap bmp = BitmapFactory.decodeStream(ims);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);


                        Image image = Image.getInstance(stream.toByteArray());
                        catPart.setNumberDepth(0);
                        catPart.add(new Chunk(image, (int) ((PageSize.A4.getWidth()) * 3 / 4) - 10, 0 - image.getHeight() / 4));
                        Paragraph p = new Paragraph();
                        p.add(new Chunk(context.getString(R.string.pdf_title), titleFont));
                        p.setAlignment(Element.ALIGN_CENTER);
                        catPart.setTitle(p);

                    } catch (IOException e) {
                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error),  e.getMessage() ,"018-019");
                    }
                }

                Paragraph p = new Paragraph();
                p.add(new Chunk(context.getString(R.string.village) + " :" + MasterCommonController.mapVillage.get(alGpVillageSurvey.get(i).getVillage_code()), villageFont));
                p.setAlignment(Element.ALIGN_LEFT);

                catPart.add(p);
                addVillage_table(catPart, document, gp_survey_base, alGpVillageSurvey.get(i));
            }
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error), e.getMessage() ,"018-020");
        }
        // document.add(catPart);
    }

    private void addVillage_table(Chapter catPart, Document document, GpVillageSurvey gpVillageBase, GpVillageSurvey gpVillageSurvey)throws DocumentException  {
        // Location Parameters
        try {
            ArrayList<Pdf_cell> list = new ArrayList<>();
            list.add(new Pdf_cell("", context.getString(R.string.state), "", "", gpVillageSurvey.getState_name() + "(" + gpVillageSurvey.getState_code() + ")", "", "", ""));
            list.add(new Pdf_cell("", context.getString(R.string.district), "", "", gpVillageSurvey.getDistrict_name() + "(" + gpVillageSurvey.getDistrict_code() + ")", "", "", ""));
            list.add(new Pdf_cell("", context.getString(R.string.dev_block), "", "", gpVillageSurvey.getBlock_name() + "(" + gpVillageSurvey.getBlock_code() + ")", "", "", ""));
            list.add(new Pdf_cell("", context.getString(R.string.gp), "", "", gpVillageSurvey.getGp_name() + "(" + gpVillageSurvey.getGp_code() + ")", "", "", ""));
            list.add(new Pdf_cell("", context.getString(R.string.total_villages), "", "", String.valueOf(PopulateMasterLists.adapterVillage.getCount() - 1), "", "", ""));
            list.add(new Pdf_cell("", context.getString(R.string.completed_villages), "", "", String.valueOf(n_completedVillages), "", "", ""));
            list.add(new Pdf_cell("", context.getString(R.string.village), "", "", MasterCommonController.mapVillage.get(gpVillageSurvey.getVillage_code()) + "(" + gpVillageSurvey.getVillage_code() + ")", "", "", ""));
            list.add(new Pdf_cell("", context.getString(R.string.pincode), "", "", gpVillageSurvey.getVillage_pin_code(), "", "", ""));
            list.add(new Pdf_cell("", context.getString(R.string.parliament_constituency), "", "",
                    gpVillageSurvey.getPc_code() == 0 ? context.getString(R.string.not_available): ParliamentConstituencyController.getName(context, DBHelper.getInstance(context, false),gpVillageSurvey.getPc_code(),false),
                    "", "", ""));
            list.add(new Pdf_cell("", context.getString(R.string.assembly_constituency), "", "",
                    gpVillageSurvey.getAc_code() == 0 ? context.getString(R.string.not_available): AssemblyConstituencyController.getName(context, DBHelper.getInstance(context, false), gpVillageSurvey.getAc_code(),gpVillageSurvey.getPc_code(),false),
                    "", "", ""));
            list.add(new Pdf_cell("", context.getString(R.string.name_of_constituency), "", "",
                    gpVillageSurvey.getOther_assembly_constituencies(), "", "", ""));
            list.add(new Pdf_cell("", "", "", "",
                    "", "", "", ""));


            if(gpVillageSurvey.getIs_verified()) {
                list.add(new Pdf_cell("", context.getString(R.string.is_verified), "", "", gpVillageSurvey.getIs_verified() == true ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));
                if ((gpVillageSurvey.getTs_verified() == null) || gpVillageSurvey.getTs_verified().isEmpty())
                    list.add(new Pdf_cell("", context.getString(R.string.ts_verified), "", "", context.getString(R.string.not_available), "", "", ""));
                else
                    list.add(new Pdf_cell("", context.getString(R.string.ts_verified), "", "", gpVillageSurvey.getTs_verified(), "", "", ""));
            }
            else if(gpVillageSurvey.getIs_uploaded()) {
                list.add(new Pdf_cell("", context.getString(R.string.is_uploaded), "", "", gpVillageSurvey.getIs_uploaded() == true ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));
                if ((gpVillageSurvey.getTs_uploaded() == null) || gpVillageSurvey.getTs_uploaded().isEmpty())
                    list.add(new Pdf_cell("", context.getString(R.string.ts_uploaded), "", "", context.getString(R.string.not_available), "", "", ""));
                else
                    list.add(new Pdf_cell("", context.getString(R.string.ts_uploaded), "", "", gpVillageSurvey.getTs_uploaded(), "", "", ""));
            }
            else {
                if (!gpVillageSurvey.getIs_uploaded())
                    list.add(new Pdf_cell("", context.getString(R.string.is_send_to_server), "", "", String.valueOf(gpVillageSurvey.getIs_synchronized() == true ? context.getString(R.string.yes) : context.getString(R.string.no)), "", "", ""));
                else
                    list.add(new Pdf_cell("", context.getString(R.string.is_send_to_server), "", "", context.getString(R.string.no), "", "", ""));
                if ((gpVillageSurvey.getDt_sync() == null) || gpVillageSurvey.getDt_sync().isEmpty())
                    list.add(new Pdf_cell("", context.getString(R.string.ts_send_to_server), "", "", context.getString(R.string.not_available), "", "", ""));
                else
                    list.add(new Pdf_cell("", context.getString(R.string.ts_send_to_server), "", "", gpVillageSurvey.getDt_sync(), "", "", ""));
            }

            catPart.setNumberDepth(0);

            Paragraph p1 = new Paragraph(context.getString(R.string.location_parameters), titleFont);
            p1.setAlignment(Element.ALIGN_LEFT);
            addEmptyLine(p1, 1);
            Section subCatPart = catPart.addSection(p1,0);
            createTable(subCatPart, 1, list);


************************ Part A***********************

            Paragraph p2 = new Paragraph(context.getString(R.string.part_a),villageFont);
            p2.setAlignment(Element.ALIGN_CENTER);
            Section subCatPartA = catPart.addSection(p2,0);

            // Basic Parameters
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            // sdf//

            list.add(new Pdf_cell("1", context.getString(R.string.total_population),
                    gpVillageBase.getIs_base_data_available() ? String.valueOf(gpVillageBase.getTotal_population()) : context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_population()), "", "", ""));

            list.add(new Pdf_cell("2", context.getString(R.string.male),
                    gpVillageBase.getIs_base_data_available() ? String.valueOf(gpVillageBase.getMale_population()) : context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getMale_population()), "", "", ""));

            list.add(new Pdf_cell("3", context.getString(R.string.female),
                    gpVillageBase.getIs_base_data_available() ? String.valueOf(gpVillageBase.getFemale_population()) : context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getFemale_population()), "", "", ""));

            list.add(new Pdf_cell("4", context.getString(R.string.total_household),
                    gpVillageBase.getIs_base_data_available() ? String.valueOf(gpVillageBase.getTotal_hhd()) : context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_hhd()), "", "", ""));


            Paragraph subParaA1 = new Paragraph(context.getString(R.string.basic_parameters), titleFont);
            addEmptyLine(subParaA1, 1);
            Section subCatPartA1 = subCatPartA.addSection(subParaA1);
            createTable(subCatPartA1, 2, list);

            // Agriculture
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("5", context.getString(R.string.hhd_farm_activity),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? String.valueOf(gpVillageBase.getTotal_hhd_engaged_in_farm_activities()) : context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_hhd_engaged_in_farm_activities()), "", "", ""));

            list.add(new Pdf_cell("6", context.getString(R.string.hhd_non_farm_activity),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? String.valueOf(gpVillageBase.getTotal_hhd_engaged_in_non_farm_activities()) : context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_hhd_engaged_in_non_farm_activities()), "", "", ""));

            list.add(new Pdf_cell("7", context.getString(R.string.govt_seed_center),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? (gpVillageBase.getIs_govt_seed_centre_available() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no)) : context.getString(R.string.not_available),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? (gpVillageBase.getIs_govt_seed_centre_available() == 2 ? getDistanceValueA(gpVillageBase.getDistance_of_govt_seed_centre()) : "") : "",
                    gpVillageSurvey.getIs_govt_seed_centre_available() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), gpVillageSurvey.getIs_govt_seed_centre_available() == 2 ? getDistanceValueA(gpVillageSurvey.getDistance_of_govt_seed_centre()) : "", "", ""));

            list.add(new Pdf_cell("8", context.getString(R.string.water_shed_dev),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_watershed_dev_project() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            list.add(new Pdf_cell("9", context.getString(R.string.rain_water_harvesting),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_rain_harvest_system() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            list.add(new Pdf_cell("10", context.getString(R.string.farmers_collective),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_fpos_pacs() == 0 ? context.getString(R.string.not_available) : MasterCommonController.mapFarmersCollectiveCategory.get(gpVillageSurvey.getAvailability_of_fpos_pacs()), "", "", ""));

            list.add(new Pdf_cell("11", context.getString(R.string.food_storage_warehouse),
                    context.getString(R.string.not_available),
                    "",
                    gpVillageSurvey.getAvailability_of_food_storage_warehouse() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no),
                    gpVillageSurvey.getAvailability_of_food_storage_warehouse() == 2 ? getDistanceValueA(gpVillageSurvey.getDistance_of_food_storage_warehouse()) : "", "", ""));

            list.add(new Pdf_cell("12", context.getString(R.string.primary_processing_facilities),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_farm_gate_processing() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));


            list.add(new Pdf_cell("13", context.getString(R.string.access_to_custom_hiring_centre),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_custom_hiring_centre_agri_equipment() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));



            Paragraph subParaA2 = new Paragraph(context.getString(R.string.section_agriculture), titleFont);
            addEmptyLine(subParaA2, 1);
            Section subCatPartA2 = subCatPartA.addSection(subParaA2);
            createTable(subCatPartA2, 2, list);

            // Land Improvement
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("14", context.getString(R.string.total_cultivable_area),
                    gpVillageBase.getIs_base_data_available() ? String.valueOf(gpVillageBase.getTotal_cultivable_area_in_hac()) : context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_cultivable_area_in_hac()), "", "", ""));

            list.add(new Pdf_cell("15", context.getString(R.string.net_area_sown),
                    gpVillageBase.getIs_base_data_available() ? String.valueOf(gpVillageBase.getNet_sown_area_in_hac()) : context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getNet_sown_area_in_hac()), "", "", ""));


            list.add(new Pdf_cell("15a", context.getString(R.string.kharif),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getNet_sown_area_kharif_in_hac()), "", "", ""));

            list.add(new Pdf_cell("15b", context.getString(R.string.rabi),
                     context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getNet_sown_area_rabi_in_hac()), "", "", ""));

            list.add(new Pdf_cell("15c", context.getString(R.string.others_area),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getNet_sown_area_other_in_hac()), "", "", ""));


            list.add(new Pdf_cell("16", context.getString(R.string.soil_testing_center),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? (gpVillageBase.getIs_soil_testing_centre_available() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no)) : context.getString(R.string.not_available),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? (gpVillageBase.getIs_soil_testing_centre_available() == 2 ? getDistanceValueA(gpVillageBase.getDistance_of_soil_testing_centre()) : "") : "",
                    gpVillageSurvey.getIs_soil_testing_centre_available() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), gpVillageSurvey.getIs_soil_testing_centre_available() == 2 ? getDistanceValueA(gpVillageSurvey.getDistance_of_soil_testing_centre()) : "", "", ""));

            list.add(new Pdf_cell("17", context.getString(R.string.fertilizer_shop),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? (gpVillageBase.getIs_fertilizer_shop_available() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no)) : context.getString(R.string.not_available),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? (gpVillageBase.getIs_fertilizer_shop_available() == 2 ? getDistanceValueA(gpVillageBase.getDistance_of_fertilizer_shop()) : "") : "",
                    gpVillageSurvey.getIs_fertilizer_shop_available() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), gpVillageSurvey.getIs_fertilizer_shop_available() == 2 ? getDistanceValueA(gpVillageSurvey.getDistance_of_fertilizer_shop()) : "", "", ""));


            list.add(new Pdf_cell("18", context.getString(R.string.irrigation_main_source),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_major_source_of_irrigation() == 0 ? context.getString(R.string.not_available) : MasterCommonController.mapIrrSourceCategory.get(gpVillageSurvey.getAvailability_of_major_source_of_irrigation()),  "", "", ""));

            list.add(new Pdf_cell("19", context.getString(R.string.drip_sprinkler),
                    context.getString(R.string.not_available), "",
                    String.valueOf(gpVillageSurvey.getNo_of_farmers_using_drip_sprinkler()), "", "", ""));


            list.add(new Pdf_cell("20", context.getString(R.string.irrigated_land),
                    gpVillageBase.getIs_base_data_available() ? String.valueOf(gpVillageBase.getArea_irrigated_in_hac()) : context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getArea_irrigated_in_hac()), "", "", ""));


            list.add(new Pdf_cell("21", context.getString(R.string.unirrigated_land),
                    gpVillageBase.getIs_base_data_available() ? String.valueOf(gpVillageBase.getTotal_unirrigated_land_area_in_hac()) : context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_unirrigated_land_area_in_hac()), "", "", ""));


            Paragraph subParaA3 = new Paragraph(context.getString(R.string.section_land_improvement_minor_irr), titleFont);
            addEmptyLine(subParaA3, 1);
            Section subCatPartA3 = subCatPartA.addSection(subParaA3);
            createTable(subCatPartA3, 2, list);

            // Animal Husbandry
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("22", context.getString(R.string.livestock_extension_services),
                    context.getString(R.string.not_available),
                    "",
                    gpVillageSurvey.getAvailability_of_livestock_extension_services() == 0 ? context.getString(R.string.not_available) : MasterCommonController.mapLivestockExtServicesCategory.get(gpVillageSurvey.getAvailability_of_livestock_extension_services()), "", "", ""));

            list.add(new Pdf_cell("23", context.getString(R.string.milk_routes),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_milk_routes() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            list.add(new Pdf_cell("24", context.getString(R.string.poultry),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_poultry_dev_project() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            list.add(new Pdf_cell("25", context.getString(R.string.goatry),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_goatary_dev_project() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            list.add(new Pdf_cell("26", context.getString(R.string.pigery),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_pigery_development() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            list.add(new Pdf_cell("27", context.getString(R.string.veterinary_centre),
                    gpVillageBase.getIs_base_data_available() ? (gpVillageBase.getIs_veterinary_hospital_available() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no)) : context.getString(R.string.not_available),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? (gpVillageBase.getIs_veterinary_hospital_available() == 2 ? getDistanceValueA(gpVillageBase.getDistance_of_veterinary_hospital()) : "") : "",
                    gpVillageSurvey.getIs_veterinary_hospital_available() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), gpVillageSurvey.getIs_veterinary_hospital_available() == 2 ? getDistanceValueA(gpVillageSurvey.getDistance_of_veterinary_hospital()) : "", "", ""));


            Paragraph subParaA4 = new Paragraph(context.getString(R.string.section_animal_husbandry), titleFont);
            addEmptyLine(subParaA4, 1);
            Section subCatPartA4 = subCatPartA.addSection(subParaA4);
            createTable(subCatPartA4, 2, list);

            // Fisheries
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));


            list.add(new Pdf_cell("28", context.getString(R.string.pisciculture),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_fish_farming() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));


            list.add(new Pdf_cell("29", context.getString(R.string.fishing_ponds),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_fish_community_ponds() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));


            list.add(new Pdf_cell("30", context.getString(R.string.aquaculture_ext_facilities),
                    context.getString(R.string.not_available),
                    "",
                    gpVillageSurvey.getAvailability_of_aquaculture_ext_facility() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no),
                    gpVillageSurvey.getAvailability_of_aquaculture_ext_facility() == 2 ? getDistanceValueA(gpVillageSurvey.getDistance_of_aquaculture_ext_facility()) : "", "", ""));


            Paragraph subParaA5 = new Paragraph(context.getString(R.string.section_fisheries), titleFont);
            addEmptyLine(subParaA5, 1);
            Section subCatPartA5 = subCatPartA.addSection(subParaA5);
            createTable(subCatPartA5, 2, list);

            // Rural housing
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));


            list.add(new Pdf_cell("31", context.getString(R.string.hhd_in_kuccha_house),
                    gpVillageBase.getIs_base_data_available() ? String.valueOf(gpVillageBase.getTotal_hhd_with_kuccha_wall_kuccha_roof()) : context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_hhd_with_kuccha_wall_kuccha_roof()), "", "", ""));


            list.add(new Pdf_cell("32", context.getString(R.string.hhd_got_pmay_house),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_hhd_have_got_pmay_house()), "", "", ""));

            list.add(new Pdf_cell("33", context.getString(R.string.hhd_in_permamnent_wait_list),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_hhd_in_pmay_permanent_wait_list()), "", "", ""));


            list.add(new Pdf_cell("34", context.getString(R.string.hhd_benefitted_from_state_specific_housing_scheme),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_hhd_got_benefit_under_state_housing_scheme()), "", "", ""));


            list.add(new Pdf_cell("35", context.getString(R.string.hhd_in_permamnent_wait_list_state_specific_housing_scheme),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_hhd_in_perm_waitlist_under_state_housing_scheme()), "", "", ""));


            Paragraph subParaA6 = new Paragraph(context.getString(R.string.section_rural_housing), titleFont);
            addEmptyLine(subParaA6, 1);
            Section subCatPartA6 = subCatPartA.addSection(subParaA6);
            createTable(subCatPartA6, 2, list);

            // Drinking Water
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("36", context.getString(R.string.tap_water),
                    gpVillageBase.getIs_base_data_available() ? (gpVillageSurvey.getAvailability_of_piped_tap_water() == 0 ? context.getString(R.string.not_available) : MasterCommonController.mapTapWaterCategory.get(gpVillageBase.getAvailability_of_piped_tap_water())) : context.getString(R.string.not_available),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? (gpVillageBase.getAvailability_of_piped_tap_water() == 5 ? getDistanceValueA(gpVillageBase.getDistance_of_piped_tap_water()) : "") : "",
                    gpVillageSurvey.getAvailability_of_piped_tap_water() == 0 ? context.getString(R.string.not_available) : MasterCommonController.mapTapWaterCategory.get(gpVillageSurvey.getAvailability_of_piped_tap_water()), gpVillageSurvey.getAvailability_of_piped_tap_water() == 5 ? getDistanceValueA(gpVillageSurvey.getDistance_of_piped_tap_water()) : "", "", ""));

            Paragraph subParaA7 = new Paragraph(context.getString(R.string.section_drinking_water), titleFont);
            addEmptyLine(subParaA7, 1);
            Section subCatPartA7 = subCatPartA.addSection(subParaA7);
            createTable(subCatPartA7, 2, list);

            // Roads
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));


            list.add(new Pdf_cell("37", context.getString(R.string.all_weather_road),
                    gpVillageBase.getIs_base_data_available() ? (gpVillageBase.getIs_village_connected_to_all_weather_road() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no)) : context.getString(R.string.not_available),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? (gpVillageBase.getIs_village_connected_to_all_weather_road() == 2 ? getDistanceValueA(gpVillageBase.getDistance_of_all_weather_road()) : "") : "",
                    gpVillageSurvey.getIs_village_connected_to_all_weather_road() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no),
                    gpVillageSurvey.getIs_village_connected_to_all_weather_road() == 2 ? getDistanceValueA(gpVillageSurvey.getDistance_of_all_weather_road()) : "", "", ""));


            list.add(new Pdf_cell("38", context.getString(R.string.cc_or_brick_road),
                    context.getString(R.string.not_available),
                    "", gpVillageSurvey.getAvailability_of_internal_pucca_road() == 0 ? context.getString(R.string.not_available) : MasterCommonController.mapInternalPuccaRoadCategory.get(gpVillageSurvey.getAvailability_of_internal_pucca_road()), "", "", ""));

            list.add(new Pdf_cell("39", context.getString(R.string.public_transport),
                    gpVillageBase.getIs_base_data_available() ? (gpVillageBase.getAvailability_of_public_transport() == 0 ? context.getString(R.string.not_available) : MasterCommonController.mapPublicTransportCategory.get(gpVillageBase.getAvailability_of_public_transport()) ): context.getString(R.string.not_available),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? (gpVillageBase.getAvailability_of_public_transport() == 4 ? getDistanceValueA(gpVillageBase.getDistance_of_public_transport()) : "") : "",
                    gpVillageSurvey.getAvailability_of_public_transport() == 0 ? context.getString(R.string.not_available) : MasterCommonController.mapPublicTransportCategory.get(gpVillageSurvey.getAvailability_of_public_transport()),
                    gpVillageSurvey.getAvailability_of_public_transport() == 4 ? getDistanceValueA(gpVillageSurvey.getDistance_of_public_transport()) : "", "", ""));

            list.add(new Pdf_cell("40", context.getString(R.string.railway_station),
                    context.getString(R.string.not_available),
                    "",
                    gpVillageSurvey.getAvailability_of_railway_station() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no),
                    gpVillageSurvey.getAvailability_of_railway_station() == 2 ? getDistanceValueB(gpVillageSurvey.getDistance_of_railway_station()) : "", "", ""));


            Paragraph subParaA8 = new Paragraph(context.getString(R.string.section_roads), titleFont);
            addEmptyLine(subParaA8, 1);
            Section subCatPartA8 = subCatPartA.addSection(subParaA8);
            createTable(subCatPartA8, 2, list);

            // Rural Electrification
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("41", context.getString(R.string.electricity),
                    gpVillageBase.getIs_base_data_available() ? (gpVillageBase.getAvailablility_hours_of_domestic_electricity() == 0 ? context.getString(R.string.not_available) : MasterCommonController.mapElectricityHoursCategory.get(gpVillageBase.getAvailablility_hours_of_domestic_electricity())) : context.getString(R.string.not_available),
                    "",
                    gpVillageSurvey.getAvailablility_hours_of_domestic_electricity() == 0 ? context.getString(R.string.not_available) : MasterCommonController.mapElectricityHoursCategory.get(gpVillageSurvey.getAvailablility_hours_of_domestic_electricity()), "", "", ""));

            list.add(new Pdf_cell("42", context.getString(R.string.hhd_availing_benefits_saubhagya),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_hhd_having_pmsbhgy_benefit()), "", "", ""));

            list.add(new Pdf_cell("43", context.getString(R.string.electricity_msme),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_elect_supply_to_msme() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            Paragraph subParaA9 = new Paragraph(context.getString(R.string.section_rural_electrification), titleFont);
            addEmptyLine(subParaA9, 1);
            Section subCatPartA9 = subCatPartA.addSection(subParaA9);
            createTable(subCatPartA9, 2, list);

            // Non-conventional energy
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("44", context.getString(R.string.solar_energy_use),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_solor_wind_energy() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            if (gpVillageSurvey.getAvailability_of_solor_wind_energy() == 1) {
                list.add(new Pdf_cell("44a", context.getString(R.string.hhd_electrified_by_solar_energy),
                        context.getString(R.string.not_available),
                        "",
                        String.valueOf(gpVillageSurvey.getTotal_hhd_having_solor_wind_energy()), "", "", ""));
            } else {
                list.add(new Pdf_cell("44b", context.getString(R.string.hhd_electrified_by_solar_energy),
                        context.getString(R.string.not_available),
                        "",
                        "", "", "", ""));
            }

            Paragraph subParaA10 = new Paragraph(context.getString(R.string.section_non_conventional), titleFont);
            addEmptyLine(subParaA10, 1);
            Section subCatPartA10 = subCatPartA.addSection(subParaA10);
            createTable(subCatPartA10, 2, list);

            // Maintenance of community assets
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("45", context.getString(R.string.panchayat_bhawan),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_panchayat_bhawan() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            list.add(new Pdf_cell("46", context.getString(R.string.common_service_centre),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_csc_in_village() == 0 ? context.getString(R.string.not_available) : MasterCommonController.mapCommonServiceCentreCategory.get(gpVillageSurvey.getAvailability_of_csc_in_village()), "", "", ""));

            list.add(new Pdf_cell("47", context.getString(R.string.public_info_board),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_public_information_board() == 0 ? context.getString(R.string.not_available) : MasterCommonController.mapPublicInfoBoardCategory.get(gpVillageSurvey.getAvailability_of_public_information_board()), "", "", ""));

            Paragraph subParaA19 = new Paragraph(context.getString(R.string.section_maint_community_assets), titleFont);
            addEmptyLine(subParaA19, 1);
            Section subCatPartA19 = subCatPartA.addSection(subParaA19);
            createTable(subCatPartA19, 2, list);
            // Fuel and fodder
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("48", context.getString(R.string.common_pastures),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getIs_common_pastures_available() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            list.add(new Pdf_cell("49", context.getString(R.string.hhd_availing_benefit_pmuy),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_hhd_availing_pmuy_benefits()), "", "", ""));

            Paragraph subParaA27 = new Paragraph(context.getString(R.string.section_fuel_fodder), titleFont);
            addEmptyLine(subParaA27, 1);
            Section subCatPartA27 = subCatPartA.addSection(subParaA27);
            createTable(subCatPartA27, 2, list);

            // Libraries
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("50", context.getString(R.string.public_library),
                    context.getString(R.string.not_available),
                    "",
                    gpVillageSurvey.getAvailability_of_public_library() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no),
                    gpVillageSurvey.getAvailability_of_public_library() == 2 ? getDistanceValueA(gpVillageSurvey.getDistance_of_public_library()) : "", "", ""));


            Paragraph subParaA29 = new Paragraph(context.getString(R.string.section_libraries), titleFont);
            addEmptyLine(subParaA29, 1);
            Section subCatPartA29 = subCatPartA.addSection(subParaA29);
            createTable(subCatPartA29, 2, list);

            // Cultural activities
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("51", context.getString(R.string.recreational_centre),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_recreational_centre() == 0 ? context.getString(R.string.not_available) : MasterCommonController.mapRecreationalCentreCategory.get(gpVillageSurvey.getAvailability_of_recreational_centre()), "", "", ""));

            Paragraph subParaA30 = new Paragraph(context.getString(R.string.section_cultural_activities), titleFont);
            addEmptyLine(subParaA30, 1);
            Section subCatPartA30 = subCatPartA.addSection(subParaA30);
            createTable(subCatPartA30, 2, list);

            // infrastructure
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("52", context.getString(R.string.banks),
                    gpVillageBase.getIs_base_data_available() ? MasterCommonController.getNameYesNo(context, gpVillageBase.getIs_bank_available()) : context.getString(R.string.not_available),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? (gpVillageBase.getIs_bank_available() == 2 ? getDistanceValueA(gpVillageBase.getDistance_of_banks()) : "") : "",
                    MasterCommonController.getNameYesNo(context, gpVillageSurvey.getIs_bank_available()), gpVillageSurvey.getIs_bank_available() == 2 ? getDistanceValueA(gpVillageSurvey.getDistance_of_banks()) : "", "", ""));

            list.add(new Pdf_cell("53", context.getString(R.string.business_has_internet_connectivity),
                        (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? MasterCommonController.getNameYesNo(context, gpVillageBase.getIs_bank_buss_correspondent_with_internet()) : context.getString(R.string.not_available),
                        "", MasterCommonController.getNameYesNo(context, gpVillageSurvey.getIs_bank_buss_correspondent_with_internet()), "", "", ""));

            list.add(new Pdf_cell("54", context.getString(R.string.atm),
                    gpVillageBase.getIs_base_data_available() ? MasterCommonController.getNameYesNo(context, gpVillageBase.getIs_atm_available()) : context.getString(R.string.not_available),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? (gpVillageBase.getIs_atm_available() == 2 ? getDistanceValueA(gpVillageBase.getDistance_of_atm()) : "") : "",
                    MasterCommonController.getNameYesNo(context, gpVillageSurvey.getIs_atm_available()), gpVillageSurvey.getIs_atm_available() == 2 ? getDistanceValueA(gpVillageSurvey.getDistance_of_atm()) : "", "", ""));

            list.add(new Pdf_cell("55", context.getString(R.string.hhd_having_jan_dhan_acc),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_hhd_availing_pmjdy_bank_ac()), "", "", ""));

            list.add(new Pdf_cell("56", context.getString(R.string.post_office),
                    gpVillageBase.getIs_base_data_available() ? (gpVillageBase.getIs_post_office_available() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no)) : context.getString(R.string.not_available),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? (gpVillageBase.getIs_post_office_available() == 2 ? getDistanceValueA(gpVillageBase.getDistance_of_post_office()) : "") : "",
                    gpVillageSurvey.getIs_post_office_available() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), gpVillageSurvey.getIs_post_office_available() == 2 ? getDistanceValueA(gpVillageSurvey.getDistance_of_post_office()) : "", "", ""));

            list.add(new Pdf_cell("57", context.getString(R.string.telephone),
                    gpVillageBase.getIs_base_data_available() ? (gpVillageBase.getAvailability_of_telephone_services() == 0 ? context.getString(R.string.not_available) : MasterCommonController.mapTelephoneServicesCategory.get(gpVillageBase.getAvailability_of_telephone_services())): context.getString(R.string.not_available),
                    "",
                    gpVillageSurvey.getAvailability_of_telephone_services() == 0 ? context.getString(R.string.not_available) : MasterCommonController.mapTelephoneServicesCategory.get(gpVillageSurvey.getAvailability_of_telephone_services()), "", "", ""));

            list.add(new Pdf_cell("58", context.getString(R.string.internet_broadband_facility),
                    gpVillageBase.getIs_base_data_available() ? (gpVillageBase.getIs_broadband_available() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no)) : context.getString(R.string.not_available),
                    "",
                    gpVillageSurvey.getIs_broadband_available() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            Paragraph subParaA31 = new Paragraph(context.getString(R.string.section_financial_communcation_infrastructure), titleFont);
            addEmptyLine(subParaA31, 1);
            Section subCatPartA31 = subCatPartA.addSection(subParaA31);
            createTable(subCatPartA31, 2, list);
            // Public distribution system
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("59", context.getString(R.string.pds),
                    gpVillageBase.getIs_base_data_available() ? (gpVillageBase.getIs_pds_available() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no)) : context.getString(R.string.not_available),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? (gpVillageBase.getIs_pds_available() == 2 ? getDistanceValueA(gpVillageBase.getDistance_of_pds_office()) : "") : "",
                    gpVillageSurvey.getIs_pds_available() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), gpVillageSurvey.getIs_pds_available() == 2 ? getDistanceValueA(gpVillageSurvey.getDistance_of_pds_office()) : "", "", ""));

            list.add(new Pdf_cell("60", context.getString(R.string.hhd_having_bpl_ration_card),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_hhd_having_bpl_cards()), "", "", ""));

            Paragraph subParaA18 = new Paragraph(context.getString(R.string.section_pds), titleFont);
            addEmptyLine(subParaA18, 1);
            Section subCatPartA18 = subCatPartA.addSection(subParaA18);
            createTable(subCatPartA18, 2, list);



            // Education
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("61", context.getString(R.string.primary_school),
                    context.getString(R.string.not_available),
                    "",
                    gpVillageSurvey.getAvailability_of_primary_school() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no),
                    gpVillageSurvey.getAvailability_of_primary_school() == 2 ? getDistanceValueA(gpVillageSurvey.getDistance_of_primary_school()) : "", "", ""));

            if (gpVillageSurvey.getAvailability_of_primary_school() == 1) {
                list.add(new Pdf_cell("61a", context.getString(R.string.with_electricity),
                        context.getString(R.string.not_available),
                        "",
                        gpVillageSurvey.getIs_primary_school_with_electricity() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

                list.add(new Pdf_cell("61b", context.getString(R.string.with_toilet),
                        context.getString(R.string.not_available),
                        "",
                        gpVillageSurvey.getPrimary_school_toilet() == 0 ? context.getString(R.string.not_available) : MasterCommonController.mapToiletCategory.get(gpVillageSurvey.getPrimary_school_toilet()), "", "", ""));

                list.add(new Pdf_cell("61c", context.getString(R.string.with_computer_lab),
                        context.getString(R.string.not_available),
                        "",
                        gpVillageSurvey.getIs_primary_school_with_computer_lab() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

                list.add(new Pdf_cell("61d", context.getString(R.string.with_playground),
                        context.getString(R.string.not_available),
                        "",
                        gpVillageSurvey.getIs_primary_school_with_playground() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

                list.add(new Pdf_cell("61e", context.getString(R.string.with_drinking_water),
                        context.getString(R.string.not_available),
                        "",
                        gpVillageSurvey.getIs_primary_school_have_drinking_water() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

                list.add(new Pdf_cell("61f", context.getString(R.string.with_mid_day_meal),
                        context.getString(R.string.not_available),
                        "",
                        gpVillageSurvey.getAvailability_of_mid_day_meal_scheme() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

                list.add(new Pdf_cell("61g", context.getString(R.string.total_students_primary_school),
                        context.getString(R.string.not_available),
                        "",
                        String.valueOf(gpVillageSurvey.getTotal_primary_school_students()), "", "", ""));

                list.add(new Pdf_cell("61h", context.getString(R.string.total_teachers_primary_school),
                        context.getString(R.string.not_available),
                        "",
                        String.valueOf(gpVillageSurvey.getTotal_primary_school_teachers()), "", "", ""));


            } else {
                // Todo
            }

            list.add(new Pdf_cell("62", context.getString(R.string.middle_school),
                    context.getString(R.string.not_available),
                    "",
                    gpVillageSurvey.getAvailability_of_middle_school() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no),
                    gpVillageSurvey.getAvailability_of_middle_school() == 2 ? getDistanceValueA(gpVillageSurvey.getDistance_of_middle_school()) : "", "", ""));



            list.add(new Pdf_cell("63", context.getString(R.string.high_school),
                    context.getString(R.string.not_available),
                    "",
                    gpVillageSurvey.getAvailability_of_high_school() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no),
                    gpVillageSurvey.getAvailability_of_high_school() == 2 ? getDistanceValueA(gpVillageSurvey.getDistance_of_high_school()) : "", "", ""));



            list.add(new Pdf_cell("64", context.getString(R.string.senior_sec_school),
                    context.getString(R.string.not_available),
                    "",
                    gpVillageSurvey.getAvailability_of_ssc_school() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no),
                    gpVillageSurvey.getAvailability_of_ssc_school() == 2 ? getDistanceValueA(gpVillageSurvey.getDistance_of_ssc_school()) : "", "", ""));


            list.add(new Pdf_cell("65", context.getString(R.string.children_not_attending_school),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getNo_of_children_not_attending_school()), "", "", ""));


            list.add(new Pdf_cell("66", context.getString(R.string.degree_college),
                    context.getString(R.string.not_available),
                    "",
                    gpVillageSurvey.getAvailability_of_govt_degree_college() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no),
                    gpVillageSurvey.getAvailability_of_govt_degree_college() == 2 ? getDistanceValueB(gpVillageSurvey.getDistance_of_degree_college()) : "", "", ""));

            list.add(new Pdf_cell("67", context.getString(R.string.total_grad_postgrad),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_grd_and_pg_in_village()), "", "", ""));


            Paragraph subParaA12 = new Paragraph(context.getString(R.string.section_education), titleFont);
            addEmptyLine(subParaA12, 1);
            Section subCatPartA12 = subCatPartA.addSection(subParaA12);
            createTable(subCatPartA12, 2, list);

            // Vocational education
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("68", context.getString(R.string.voc_edu_centres),
                    gpVillageBase.getIs_base_data_available() ? (gpVillageBase.getIs_vocational_edu_centre_available() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no)) : context.getString(R.string.not_available),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? (gpVillageBase.getIs_vocational_edu_centre_available() == 2 ? getDistanceValueA(gpVillageBase.getDistance_of_vocational_edu_centre()) : "") : "",
                    gpVillageSurvey.getIs_vocational_edu_centre_available() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), gpVillageSurvey.getIs_vocational_edu_centre_available() == 2 ? getDistanceValueA(gpVillageSurvey.getDistance_of_vocational_edu_centre()) : "", "", ""));

            list.add(new Pdf_cell("69", context.getString(R.string.trainees_skill_dev_prog),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_trained_trainee_under_skill_devp_program()), "", "", ""));

            Paragraph subParaA13 = new Paragraph(context.getString(R.string.section_vocational_education), titleFont);
            addEmptyLine(subParaA13, 1);
            Section subCatPartA13 = subCatPartA.addSection(subParaA13);
            createTable(subCatPartA13, 2, list);

            // Markets and fairs
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("70", context.getString(R.string.market),
                    gpVillageBase.getIs_base_data_available() ? (gpVillageBase.getAvailability_of_market() == 0 ? context.getString(R.string.not_available) : MasterCommonController.mapMarketCategory.get(gpVillageBase.getAvailability_of_market())) : context.getString(R.string.not_available),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? (gpVillageBase.getAvailability_of_market() == 4 ? getDistanceValueA(gpVillageBase.getDistance_of_market()) : "") : "",
                    gpVillageSurvey.getAvailability_of_market() == 0 ? context.getString(R.string.not_available) : MasterCommonController.mapMarketCategory.get(gpVillageSurvey.getAvailability_of_market()), gpVillageSurvey.getAvailability_of_market() == 4 ? getDistanceValueA(gpVillageSurvey.getDistance_of_market()) : "", "", ""));



            Paragraph subParaA14 = new Paragraph(context.getString(R.string.section_markets_fairs), titleFont);
            addEmptyLine(subParaA14, 1);
            Section subCatPartA14 = subCatPartA.addSection(subParaA14);
            createTable(subCatPartA14, 2, list);

            // Health and sanitation
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("71", context.getString(R.string.phc_centre),
                    gpVillageBase.getIs_base_data_available() ? (gpVillageBase.getAvailability_of_phc_chc() == 0 ? context.getString(R.string.not_available) : (MasterCommonController.mapPhcChcCategory.get(gpVillageBase.getAvailability_of_phc_chc()))) : context.getString(R.string.not_available),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? (gpVillageBase.getAvailability_of_phc_chc() == 4 ? getDistanceValueA(gpVillageBase.getDistance_of_phc_chc()) : "") : "",
                    gpVillageSurvey.getAvailability_of_phc_chc() == 0 ? context.getString(R.string.not_available) : MasterCommonController.mapPhcChcCategory.get(gpVillageSurvey.getAvailability_of_phc_chc()), gpVillageSurvey.getAvailability_of_phc_chc() == 4 ? getDistanceValueA(gpVillageSurvey.getDistance_of_phc_chc()) : "", "", ""));

            list.add(new Pdf_cell("72", context.getString(R.string.jan_aushadhi_kendra),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_jan_aushadhi_kendra() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            list.add(new Pdf_cell("73", context.getString(R.string.hhd_health_insurance_services_pmjay),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_hhd_registered_under_pmjay()),
                    gpVillageSurvey.getTotal_hhd_registered_under_pmjay() > 0 ? getDistanceValueA(gpVillageSurvey.getDistance_of_nearest_empaneled_hospital()) : "", "", ""));

            list.add(new Pdf_cell("74", context.getString(R.string.drainage),
                    gpVillageBase.getIs_base_data_available() ? (gpVillageBase.getAvailability_of_drainage_system() == 0 ? context.getString(R.string.not_available) : (MasterCommonController.mapDrainageCategory.get(gpVillageBase.getAvailability_of_drainage_system()))) : context.getString(R.string.not_available),
                    "",
                    gpVillageSurvey.getAvailability_of_drainage_system() == 0 ? context.getString(R.string.not_available) : MasterCommonController.mapDrainageCategory.get(gpVillageSurvey.getAvailability_of_drainage_system()), "", "", ""));

            list.add(new Pdf_cell("75", context.getString(R.string.community_waste),
                    gpVillageBase.getIs_base_data_available() ? (gpVillageBase.getIs_community_waste_disposal_system() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no)) : context.getString(R.string.not_available),
                    "", gpVillageSurvey.getIs_community_waste_disposal_system() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            list.add(new Pdf_cell("76", context.getString(R.string.total_hhd_using_clean_energy),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? String.valueOf(gpVillageBase.getTotal_hhd_with_clean_energy()) : context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_hhd_with_clean_energy()), "", "", ""));


            list.add(new Pdf_cell("77", context.getString(R.string.waste_recycle),
                    gpVillageBase.getIs_base_data_available() ? (gpVillageBase.getIs_community_biogas_waste_recycle_for_production() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no)) : context.getString(R.string.not_available),
                    "", gpVillageSurvey.getIs_community_biogas_waste_recycle_for_production() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            list.add(new Pdf_cell("78", context.getString(R.string.open_defecation),
                    gpVillageBase.getIs_base_data_available() ? (gpVillageBase.getIs_village_odf() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no)) : context.getString(R.string.not_available),
                    "", gpVillageSurvey.getIs_village_odf() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));


            Paragraph subParaA15 = new Paragraph(context.getString(R.string.section_health_sanitation), titleFont);
            addEmptyLine(subParaA15, 1);
            Section subCatPartA15 = subCatPartA.addSection(subParaA15);
            createTable(subCatPartA15, 2, list);

            // Women and child development
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("79", context.getString(R.string.aanganwadi_centre),
                    gpVillageBase.getIs_base_data_available() ? (gpVillageBase.getIs_aanganwadi_centre_available() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no)) : context.getString(R.string.not_available),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? (gpVillageBase.getIs_aanganwadi_centre_available() == 2 ? getDistanceValueA(gpVillageBase.getDistance_of_aanganwadi_centre()) : "") : "",
                    gpVillageSurvey.getIs_aanganwadi_centre_available() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), gpVillageSurvey.getIs_aanganwadi_centre_available() == 2 ? getDistanceValueA(gpVillageSurvey.getDistance_of_aanganwadi_centre()) : "", "", ""));

            list.add(new Pdf_cell("80", context.getString(R.string.early_childhood_edu_aanganwadi),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getIs_early_childhood_edu_provided_in_anganwadi() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            list.add(new Pdf_cell("81", context.getString(R.string.total_0_to_3_aged_children),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? String.valueOf(gpVillageBase.getTotal_childs_aged_0_to_3_years()) : context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_childs_aged_0_to_3_years()), "", "", ""));

            list.add(new Pdf_cell("82", context.getString(R.string.zero_to_3_aged_children_aanganwadi),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? String.valueOf(gpVillageBase.getTotal_childs_aged_0_to_3_years_reg_under_aanganwadi()) : context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_childs_aged_0_to_3_years_reg_under_aanganwadi()), "", "", ""));

            list.add(new Pdf_cell("83", context.getString(R.string.three_to_6_aged_children_aanganwadi),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_childs_aged_3_to_6_years_reg_under_aanganwadi()), "", "", ""));

            list.add(new Pdf_cell("84", context.getString(R.string.zero_to_3_aged_children_immunized),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? String.valueOf(gpVillageBase.getTotal_childs_aged_0_to_3_years_immunized()) : context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_childs_aged_0_to_3_years_immunized()), "", "", ""));

            list.add(new Pdf_cell("85", context.getString(R.string.non_stunted_children_icds),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? String.valueOf(gpVillageBase.getTotal_childs_categorized_non_stunted_as_per_icds()) : context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_childs_categorized_non_stunted_as_per_icds()), "", "", ""));

            list.add(new Pdf_cell("86", context.getString(R.string.pregnant_women_anemic),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_anemic_pregnant_women()), "", "", ""));

            list.add(new Pdf_cell("87", context.getString(R.string.anemic_adoloscent_girls),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_anemic_adolescent_girls()), "", "", ""));

            list.add(new Pdf_cell("88", context.getString(R.string.children_under_6_underweight),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_underweight_child_age_under_6_years()), "", "", ""));

            list.add(new Pdf_cell("89", context.getString(R.string.total_child_male_0_6),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_male_child_age_bw_0_6()), "", "", ""));

            list.add(new Pdf_cell("90", context.getString(R.string.total_child_female_0_6),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_female_child_age_bw_0_6()), "", "", ""));


            Paragraph subParaA16 = new Paragraph(context.getString(R.string.section_women_child_dev), titleFont);
            addEmptyLine(subParaA16, 1);
            Section subCatPartA16 = subCatPartA.addSection(subParaA16);
            createTable(subCatPartA16, 2, list);

            // Social welfare
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("91", context.getString(R.string.minority_children_scholarship),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_minority_children_getting_scholarship()), "", "", ""));


            list.add(new Pdf_cell("92", context.getString(R.string.minority_hhd_bank_loan),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_minority_hh_provided_bank_loan()), "", "", ""));

            list.add(new Pdf_cell("93", context.getString(R.string.implants_appliances_to_handicapped),
                    context.getString(R.string.not_available), "",
                    String.valueOf(gpVillageSurvey.getNo_of_physically_challenged_recvd_implants()), "", "", ""));


            Paragraph subParaA17 = new Paragraph(context.getString(R.string.section_social_welfare), titleFont);
            addEmptyLine(subParaA17, 1);
            Section subCatPartA17 = subCatPartA.addSection(subParaA17);
            createTable(subCatPartA17, 2, list);


            // Family welfare
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("94", context.getString(R.string.hhd_with_more_than_2_childs),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_hhd_with_more_than_2_childs()), "", "", ""));

            list.add(new Pdf_cell("95", context.getString(R.string.mother_child_facilities),
                    context.getString(R.string.not_available),
                    "",
                    gpVillageSurvey.getAvailability_of_mother_child_health_facilities() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no),
                    gpVillageSurvey.getAvailability_of_mother_child_health_facilities() == 2 ? getDistanceValueA(gpVillageSurvey.getDistance_of_mother_child_health_facilities()) : "", "", ""));

            Paragraph subParaA20 = new Paragraph(context.getString(R.string.section_family_welfare), titleFont);
            addEmptyLine(subParaA20, 1);
            Section subCatPartA20 = subCatPartA.addSection(subParaA20);
            createTable(subCatPartA20, 2, list);

            // Welfare of the weaker sections
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("96", context.getString(R.string.hhd_getting_pension_nsap),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_hhd_availing_pension_under_nsap()), "", "", ""));

            Paragraph subParaA21 = new Paragraph(context.getString(R.string.section_weaker_section), titleFont);
            addEmptyLine(subParaA21, 1);
            Section subCatPartA21 = subCatPartA.addSection(subParaA21);
            createTable(subCatPartA21, 2, list);

            // Poverty alleviation programme
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("97", context.getString(R.string.total_shg),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_shg()), "", "", ""));

            list.add(new Pdf_cell("98", context.getString(R.string.hhd_mobilized_to_shg),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? String.valueOf(gpVillageBase.getTotal_hhd_mobilized_into_shg()) : context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_hhd_mobilized_into_shg()), "", "", ""));

            list.add(new Pdf_cell("99", context.getString(R.string.self_help_group_federated),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? String.valueOf(gpVillageBase.getTotal_no_of_shg_promoted()) : context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_shg_promoted()), "", "", ""));



            list.add(new Pdf_cell("100", context.getString(R.string.hhd_mobilized_to_producer_groups),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? String.valueOf(gpVillageBase.getTotal_hhd_mobilized_into_pg()) : context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_hhd_mobilized_into_pg()), "", "", ""));


            list.add(new Pdf_cell("101", context.getString(R.string.shg_bank_loans),
                    (gpVillageBase.getIs_base_data_available() && (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))) ? String.valueOf(gpVillageBase.getTotal_shg_accessed_bank_loans()) : context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_shg_accessed_bank_loans()), "", "", ""));




            Paragraph subParaA11 = new Paragraph(context.getString(R.string.section_poverty_alleviation_programme), titleFont);
            addEmptyLine(subParaA11, 1);
            Section subCatPartA11 = subCatPartA.addSection(subParaA11);
            createTable(subCatPartA11, 2, list);


            // Khadi, village and cottage industries
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("102", context.getString(R.string.bee_keeping),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getIs_bee_farming() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            list.add(new Pdf_cell("103", context.getString(R.string.sericulture),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getIs_sericulture() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            list.add(new Pdf_cell("104", context.getString(R.string.handloom),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getIs_handloom() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            list.add(new Pdf_cell("105", context.getString(R.string.handicraft),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getIs_handicrafts() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            Paragraph subParaA26 = new Paragraph(context.getString(R.string.section_khadi_village_cottage_ind), titleFont);
            addEmptyLine(subParaA26, 1);
            Section subCatPartA26 = subCatPartA.addSection(subParaA26);
            createTable(subCatPartA26, 2, list);

            // Social Forestry
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("106", context.getString(R.string.community_forest),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_community_forest() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            Paragraph subParaA23 = new Paragraph(context.getString(R.string.section_social_forestry), titleFont);
            addEmptyLine(subParaA23, 1);
            Section subCatPartA23 = subCatPartA.addSection(subParaA23);
            createTable(subCatPartA23, 2, list);
            // Minor Forest Produce
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("107", context.getString(R.string.minor_forest_production),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_minor_forest_production() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            list.add(new Pdf_cell("108", context.getString(R.string.hhd_source_livelihood_minor_forest_prod),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_hhd_source_of_minor_forest_production()), "", "", ""));

            Paragraph subParaA24 = new Paragraph(context.getString(R.string.section_minor_forest_produce), titleFont);
            addEmptyLine(subParaA24, 1);
            Section subCatPartA24 = subCatPartA.addSection(subParaA24);
            createTable(subCatPartA24, 2, list);

            // Small scale industries
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("109", context.getString(R.string.cottage_small_scale_units),
                    context.getString(R.string.not_available), "",
                    gpVillageSurvey.getAvailability_of_cottage_small_scale_units() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));

            list.add(new Pdf_cell("109a", context.getString(R.string.no_of_hhd_engaged_cottage_small_scale),
                    context.getString(R.string.not_available),
                    "",
                    String.valueOf(gpVillageSurvey.getTotal_hhd_engaged_cottage_small_scale_units()), "", "", ""));

            Paragraph subParaA25 = new Paragraph(context.getString(R.string.section_small_scale_industries), titleFont);
            addEmptyLine(subParaA25, 1);
            Section subCatPartA25 = subCatPartA.addSection(subParaA25);
            createTable(subCatPartA25, 2, list);

            // Adult and non-formal education
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("110", context.getString(R.string.adult_edu_centre),
                    context.getString(R.string.not_available),
                    "",
                    gpVillageSurvey.getAvailability_of_adult_edu_centre() == 1 ? context.getString(R.string.yes) : context.getString(R.string.no),
                    gpVillageSurvey.getAvailability_of_adult_edu_centre() == 2 ? getDistanceValueA(gpVillageSurvey.getDistance_of_adult_edu_centre()) : "", "", ""));


            Paragraph subParaA28 = new Paragraph(context.getString(R.string.section_adult_non_formal_edu), titleFont);
            addEmptyLine(subParaA28, 1);
            Section subCatPartA28 = subCatPartA.addSection(subParaA28);
            createTable(subCatPartA28, 2, list);


****************************   Extra line ******************************

            Paragraph pLine = new Paragraph();
            addEmptyLine(pLine,1);
            catPart.addSection(pLine,0);


            /
************************************* Part B ************************************************

            Paragraph p3 = new Paragraph(context.getString(R.string.part_b),villageFont);
            p3.setAlignment(Element.ALIGN_CENTER);
            Section subCatPartB = catPart.addSection(p3,0);


            // Health and Nutrition
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("111", context.getString(R.string.total_registered_children_aanganwadi),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_registered_children_in_anganwadi()), "", "", ""));

            list.add(new Pdf_cell("112", context.getString(R.string.total_children_0_6_immunized_icds),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_children_0_to_6_years_immunized_under_icds()), "", "", ""));

            list.add(new Pdf_cell("113", context.getString(R.string.total_pregnant_women),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_pregnant_women()), "", "", ""));

            list.add(new Pdf_cell("114", context.getString(R.string.pregnant_women_receiving_services_icds),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_pregnant_women_receiving_services_under_icds()), "", "", ""));

            list.add(new Pdf_cell("115", context.getString(R.string.total_lactating_mothers),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_lactating_mothers()), "", "", ""));

            list.add(new Pdf_cell("116", context.getString(R.string.lactating_mothers_receiving_services_icds),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_lactating_mothers_receiving_services_under_icds()), "", "", ""));

            list.add(new Pdf_cell("117", context.getString(R.string.babies_registered_asha),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_women_delivered_babies_at_hospitals_registered_asha()), "", "", ""));

            list.add(new Pdf_cell("118", context.getString(R.string.total_children_icds_cas),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_children_in_icds_cas()), "", "", ""));

            list.add(new Pdf_cell("119", context.getString(R.string.yuong_anemic_children_icds_cas),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_young_anemic_children_6_59_months_in_icds_cas()), "", "", ""));

            list.add(new Pdf_cell("120", context.getString(R.string.total_new_born_children),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_newly_born_children()), "", "", ""));

            list.add(new Pdf_cell("121", context.getString(R.string.newly_born_under_weight),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_newly_born_underweight_children()), "", "", ""));

            list.add(new Pdf_cell("122", context.getString(R.string.hhd_not_having_sanitary_latrines),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_hhd_not_having_sanitary_latrines()), "", "", ""));


            Paragraph subPara8 = new Paragraph(context.getString(R.string.health_nutrition), titleFont);
            addEmptyLine(subPara8, 1);
            Section subCatPart8 = subCatPartB.addSection(subPara8,1);
            createTable(subCatPart8, 8, list);

            // Social Security
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("123", context.getString(R.string.beneficiaries_pmmvy),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_eligible_beneficiaries_under_pmmvy()), "", "", ""));

            list.add(new Pdf_cell("124", context.getString(R.string.beneficiaries_receiving_benefit_pmmvy),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_beneficiaries_receiving_benefits_under_pmmvy()), "", "", ""));

            list.add(new Pdf_cell("125", context.getString(R.string.beneficiaries_pmjay),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getGp_total_no_of_eligible_beneficiaries_under_pmjay()), "", "", ""));

            list.add(new Pdf_cell("126", context.getString(R.string.beneficiaries_reveiving_benefit_pmjay),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getGp_total_no_of_beneficiaries_receiving_benefits_under_pmjay()), "", "", ""));

            list.add(new Pdf_cell("127", context.getString(R.string.eligible_hhd_nfsa),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getGp_total_hhd_eligible_under_nfsa()), "", "", ""));

            list.add(new Pdf_cell("128", context.getString(R.string.hhd_food_grain_fps),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getGp_total_hhd_receiving_food_grains_from_fps()), "", "", ""));

            list.add(new Pdf_cell("129", context.getString(R.string.farmers_pmkpy),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_farmers_registered_under_pmkpy()), "", "", ""));

            list.add(new Pdf_cell("130", context.getString(R.string.farmers_18_40_pmkpy),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_farmers_subscribed_aged_18_40_under_pmkpy()), "", "", ""));

            Paragraph subPara9 = new Paragraph(context.getString(R.string.social_security), titleFont);
            addEmptyLine(subPara9, 1);
            Section subCatPart9 = subCatPartB.addSection(subPara9);
            createTable(subCatPart9, 9, list);


            // Agriculture and livelihoods
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("131", context.getString(R.string.total_no_of_farmers),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_farmers()), "", "", ""));

            list.add(new Pdf_cell("132", context.getString(R.string.farmers_received_benefit_pmfby),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_farmers_received_benefit_under_pmfby()), "", "", ""));

            list.add(new Pdf_cell("133", context.getString(R.string.farmers_adopted_organic_farming),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_farmers_adopted_organic_farming()), "", "", ""));

            list.add(new Pdf_cell("134", context.getString(R.string.farmers_received_soil_testing_reports),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_farmers_add_fert_in_soil_as_per_report()), "", "", ""));



            Paragraph subPara10 = new Paragraph(context.getString(R.string.agriculture_livelihoods), titleFont);
            addEmptyLine(subPara10, 1);
            Section subCatPart10 = subCatPartB.addSection(subPara10);
            createTable(subCatPart10, 10, list);

            // Good Governance
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("135", context.getString(R.string.elected_representatives),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_elected_representatives()), "", "", ""));

            list.add(new Pdf_cell("136", context.getString(R.string.elected_representatives_oriented_rgsa),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_elect_rep_oriented_under_rgsa()), "", "", ""));

            list.add(new Pdf_cell("137", context.getString(R.string.elected_representatives_trained_rgsa),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_no_of_elect_rep_undergone_training_under_rgsa()), "", "", ""));

            Paragraph subPara12 = new Paragraph(context.getString(R.string.good_governance), titleFont);
            addEmptyLine(subPara12, 1);
            Section subCatPart12 = subCatPartB.addSection(subPara12);
            createTable(subCatPart12, 12, list);

            // Jal Shakti Abhiyan
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

            list.add(new Pdf_cell("138", context.getString(R.string.approved_labour_budget),
                    context.getString(R.string.not_available),
                    "", String.format("%.2f",new BigDecimal(gpVillageSurvey.getTotal_approved_labour_budget_for_year())), "", "", ""));

            list.add(new Pdf_cell("139", context.getString(R.string.approved_labour_budget_expenditure_nrm),
                    context.getString(R.string.not_available),
                    "",String.format("%.2f",new BigDecimal(gpVillageSurvey.getTotal_expenditure_approved_under_nrm_labour_budget_during_yr())), "", "", ""));

            list.add(new Pdf_cell("140", context.getString(R.string.irrigation_area_drip_sprinkler),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_area_covered_under_irrigation_using_drip_sprinkler()), "", "", ""));

            list.add(new Pdf_cell("141", context.getString(R.string.hhd_having_piped_water_connection),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_hhd_having_piped_water_connection()), "", "", ""));

            Paragraph subPara13 = new Paragraph(context.getString(R.string.water_management_efficiency), titleFont);
            addEmptyLine(subPara13, 1);
            Section subCatPart13 = subCatPartB.addSection(subPara13);
            createTable(subCatPart13, 13, list);


            PdfPTable table = new PdfPTable(2);
            PdfPCell cell = new PdfPCell(new Phrase(context.getString(R.string.gram_sevak), catFont));
            cell.setFixedHeight(50f);
            table.addCell(cell);

            PdfPCell cell23 = new PdfPCell(new Phrase(context.getString(R.string.gramsmiti_signature), catFont));
            cell23.setColspan(2);
            cell23.setRowspan(2);
            table.addCell(cell23);
            cell = new PdfPCell(new Phrase(new Phrase(context.getString(R.string.bdo), catFont)));
            cell.setFixedHeight(50f);

            table.addCell(cell);
            table.setSpacingBefore(15f);

            catPart.add(table);

            if((nPdfType == 1) || (nPdfType == 3)) {
                Paragraph pDisclaimer = new Paragraph(context.getString(R.string.msg_disclaimer), disclaimerFont);
                pDisclaimer.setAlignment(Element.ALIGN_CENTER);
                catPart.addSection(pDisclaimer, 0);
            }


            document.add(catPart);
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error),  e.getMessage(),"018-021");
        }

    }


    private String getDistanceValueA(int nCodeDistance)
    {
        try{
            return MasterCommonController.getNameDistance(context, DBMaster.getInstance(context, true), 3, nCodeDistance, "A");
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error), e.getMessage(),"018-022");
        }
        return null;
    }

    private String getDistanceValueB(int nCodeDistance)
    {
        try{
            return MasterCommonController.getNameDistance(context, DBMaster.getInstance(context, true), 3, nCodeDistance, "B");
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error), e.getMessage(),"018-026");
        }
        return null;
    }


    private  void createTable(Section subCatPart,int part,ArrayList<Pdf_cell> list) throws BadElementException {
        try {
            headerFont=new Font(baseFont,8);
            PdfPTable table;
            if (part == 1) {
                table = new PdfPTable(4);
                table.setWidths(new int[]{100,100,100,100});
                for (int i = 0; i < list.size(); i=i+2) {

                    PdfPCell c1 = new PdfPCell(new Phrase(list.get(i).getCellLeft(), catFont));
                    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    c1.setVerticalAlignment(Element.ALIGN_CENTER);
                    c1.setFixedHeight(20f);

                    PdfPCell c2 = new PdfPCell(new Phrase(list.get(i).getCellRight(), catFont));
                    c2.setHorizontalAlignment(Element.ALIGN_LEFT);

                    PdfPCell c3 = new PdfPCell();PdfPCell c4 = new PdfPCell();
                    c3.setHorizontalAlignment(Element.ALIGN_LEFT);

                    c4.setHorizontalAlignment(Element.ALIGN_LEFT);
                    if(i<list.size()-1) {
                        c3 = new PdfPCell(new Phrase(list.get(i + 1).getCellLeft(), catFont));
                        c4 = new PdfPCell(new Phrase(list.get(i + 1).getCellRight(), catFont));
                    }
                    else
                    {
                        c3 = new PdfPCell(new Phrase(""));
                        c4 = new PdfPCell(new Phrase(""));
                    }
                    table.addCell(c1);
                    table.addCell(c2);
                    table.addCell(c3);
                    table.addCell(c4);


                }
            } else {
                table = new PdfPTable(6);
                table.setWidths(new int[]{15,65,25,30,25,40});
                for (int i = 0; i < list.size(); i++) {
                    Font font=new Font();
                    if(list.get(i).getSno().equalsIgnoreCase(context.getString(R.string.sno)))
                        font=headerFont;
                    else
                        font=catFont;

                    PdfPCell csn = new PdfPCell(new Phrase(list.get(i).getSno(), font));
                    csn.setHorizontalAlignment(Element.ALIGN_CENTER);

                    PdfPCell c1 = new PdfPCell(new Phrase(list.get(i).getCellLeft(), font));
                    c1.setHorizontalAlignment(Element.ALIGN_LEFT);
                    c1.setVerticalAlignment(Element.ALIGN_CENTER);

                    //  c1.setFixedHeight(20f);
                    String distance_value="";
                    if(list.get(i).getDistanceValueBase()!=null)
                        distance_value=list.get(i).getDistanceValueBase().isEmpty()?"":" -("+list.get(i).getDistanceValueBase()+")";
                    else
                        distance_value="";
                    PdfPCell c2 = new PdfPCell(new Phrase(list.get(i).getCellMid()+distance_value, font));
                    c2.setHorizontalAlignment(Element.ALIGN_CENTER);

                    if(list.get(i).getDistanceValue()!=null)
                        distance_value=list.get(i).getDistanceValue().isEmpty()?"":" -("+list.get(i).getDistanceValue()+")";
                    else
                        distance_value="";

                    PdfPCell c3 = new PdfPCell(new Phrase(list.get(i).getCellRight()+distance_value, font));
                    c3.setHorizontalAlignment(Element.ALIGN_CENTER);


                    PdfPCell c4 = new PdfPCell();
                    if(list.get(i).getGramSamiti_approval()== null || Support.isWhite_space(list.get(i).getGramSamiti_approval()))
                    {
                        Font zapfdingbats = new Font(Font.FontFamily.ZAPFDINGBATS, 14);
                        Chunk chunk = new Chunk("o", zapfdingbats);
                        c4 = new PdfPCell(new Phrase(chunk));
                    }
                    else
                    {
                        Font zapfdingbats = new Font(Font.FontFamily.ZAPFDINGBATS, 14);
                        Chunk chunk = new Chunk("4", zapfdingbats);
                        Chunk chunk2 = new Chunk("6", zapfdingbats);
                        Paragraph Para = new Paragraph();
                        Para.add(" (");Para.add(chunk);Para.add("or");Para.add(chunk2);Para.add(")");
                        c4.addElement(new Phrase(list.get(i).getGramSamiti_approval(),font));
                        c4.addElement(Para);
                    }
                    c4.setHorizontalAlignment(Element.ALIGN_CENTER);

                    PdfPCell c5 = new PdfPCell();

                    if(list.get(i).getGramSamiti_change_value()== null || Support.isWhite_space(list.get(i).getGramSamiti_change_value())) {
                        c5= new PdfPCell(new Phrase(list.get(i).getGramSamiti_change_value(), font));
                    }
                    else
                    {
                        Font zapfdingbats = new Font(Font.FontFamily.ZAPFDINGBATS, 14);
                        Chunk chunk = new Chunk("6", zapfdingbats);
                        c5.addElement(new Phrase(list.get(i).getGramSamiti_change_value(), font));
                        Paragraph Para = new Paragraph();
                        Para.add(" ( If ");
                        Para.add(chunk);
                        Para.add(")");
                        c5.addElement(Para);
                    }
                    c5.setHorizontalAlignment(Element.ALIGN_CENTER);

                    table.addCell(csn);
                    table.addCell(c1);
                    table.addCell(c2);
                    table.addCell(c3);
                    table.addCell(c4);
                    table.addCell(c5);

                }
            }
            table.setKeepTogether(false);

            subCatPart.add(table);
        }catch (Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error),  e.getMessage(),"018-023");
        }

    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        try {
            for (int i = 0; i < number; i++) {
                paragraph.add(new Paragraph(" "));
            }
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error),  e.getMessage(),"018-024");
        }
    }

    class MyFooter extends PdfPageEventHelper {
        Font ffont = new Font(Font.FontFamily.UNDEFINED, 8, Font.ITALIC);

        public void onEndPage(PdfWriter writer, Document document) {
            try {
                PdfContentByte cb = writer.getDirectContent();
                // Phrase header = new Phrase("this is a header", ffont);
                Phrase footer = new Phrase(String.valueOf(dateFormat.format(new Date())), ffont);
                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                        footer,
                        (document.right() - document.left()) / 2 + document.leftMargin(),
                        document.bottom() - 10, 0);
            }catch (Exception e)
            {
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error),  e.getMessage(),"018-025");
            }
        }
    }

    private Phrase footer() {
        Font ffont = new Font(Font.FontFamily.UNDEFINED, 5, Font.ITALIC);
        Phrase p = new Phrase("this is a footer", ffont);
        return p;
    }*/
}
