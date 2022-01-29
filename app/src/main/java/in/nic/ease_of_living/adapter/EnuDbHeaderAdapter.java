package in.nic.ease_of_living.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import in.nic.ease_of_living.gp.R;
import in.nic.ease_of_living.interfaces.Constants;
import in.nic.ease_of_living.interfaces.VolleyCallback;
import in.nic.ease_of_living.models.AESHelper;
import in.nic.ease_of_living.models.EnumeratedBlock;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MySharedPref;
import in.nic.ease_of_living.supports.MyVolley;
import in.nic.ease_of_living.utils.GeneratePdf;


/**
 * Created by Neha Jain on 7/7/2017.
 */
///, "035-003"
public class EnuDbHeaderAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    ArrayList<EnumeratedBlock> alEnumBlock;
    private String saveFilePath;
    private String fileName = "";
    private boolean isCompletedFile = false;
    private static final int BUFFER_SIZE = 1024;
    private String strActivityName;
    private String TAG = "EnuDbHeaderAdapter";

    public EnuDbHeaderAdapter(Context c, final ArrayList<EnumeratedBlock> alEnuDb, final String strActivityName) {
        inflater = LayoutInflater.from(c);
        context = c;
        this.alEnumBlock = alEnuDb;
        this.strActivityName = strActivityName;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return alEnumBlock.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.layout_enu_db_list_item, parent, false);
        TextView tvEnuDbNameData = convertView.findViewById(R.id.tvEnuDbNameData);
        TextView tvEnuDbStatusData = convertView.findViewById(R.id.tvEnuDbStatusData);
        LinearLayout llEnudb = convertView.findViewById(R.id.llEnudb);
        tvEnuDbNameData.setText(alEnumBlock.get(position).getEnum_block_name());
        tvEnuDbNameData.setTextColor(ContextCompat.getColor(context, R.color.black));
        tvEnuDbStatusData.setTextColor(ContextCompat.getColor(context, R.color.red));
        tvEnuDbStatusData.setText(context.getString(R.string.download));

        llEnudb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (strActivityName.equalsIgnoreCase(context.getString(R.string.home_option_download_village_data)))
                    getBasePdf(alEnumBlock.get(position), "B");
                else if (strActivityName.equalsIgnoreCase(context.getString(R.string.home_option_get_updated_village_pdf)))
                    getBasePdf(alEnumBlock.get(position), "C");
            }
        });

        return convertView;
    }

    private void getBasePdf(EnumeratedBlock enumBlock, String strPdfType) {

        try {

            JSONObject js = new JSONObject();
            js.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
            js.put("state_code", MySharedPref.getCurrentUser(context).getState_code());
            js.put("district_code", MySharedPref.getCurrentUser(context).getDistrict_code());
            js.put("sub_district_code", enumBlock.getSub_district_code());
            js.put("block_code", enumBlock.getBlock_code());
            js.put("gp_code", enumBlock.getGp_code());
            js.put("village_code", enumBlock.getVillage_code());
            js.put("enum_block_code", enumBlock.getEnum_block_code());
            js.put("pdf_type", strPdfType);
            /*js.put("user_id", "b7b6e4fd-6a04-479b-bd6f-6226edf1f747");
            js.put("state_code", 2);
            js.put("district_code", 24);
            js.put("sub_district_code", 168);
            js.put("block_code", 198);
            js.put("gp_code", 10084);
            js.put("village_code", 22677);
            js.put("enum_block_code", enumBlockCode);*/
            Log.d(TAG, "getBasePdf: js" + js.toString());
            String strUrl = Constants.DOWNLOAD + "downloadEBPDF";

            MyVolley.getJsonResponse(context.getString(R.string.home_option_download_village_data), context, Request.Method.POST, "",
                    true, true, true,
                    true, false, false, strUrl, js, new VolleyCallback() {
                        @Override
                        public void onVolleySuccessResponse(JSONObject jsResponse) {

                            try {
                                if ((jsResponse.has("isBaseDataAvailable")) && !(jsResponse.getBoolean("isBaseDataAvailable"))) {

                                    MyAlert.showAlert(context, R.mipmap.icon_info, context.getString(R.string.get_data), jsResponse.getString("developer_message"), "035-002");
                                    if ((MyVolley.dialog != null) && MyVolley.dialog.isShowing()) {
                                        MyVolley.dialog.dismiss();
                                    }
                                } else {
                                    MyAlert.showAlert(context, R.mipmap.icon_warning, context.getString(R.string.get_data), jsResponse.getString("developer_message"), "035-003");
                                    if ((MyVolley.dialog != null) && MyVolley.dialog.isShowing()) {
                                        MyVolley.dialog.dismiss();
                                    }
                                }
                            } catch (Exception e) {

                            }
                        }

                        @Override
                        public Response<JSONObject> onVolleyNetworkResponse(NetworkResponse response) {
                            try {
                                final JSONObject js = new JSONObject(response.headers);

                                if (js.has("Content-Disposition")) {
                                    fileName = js.getString("Content-Disposition").split(";")[1].split("=")[1];
                                    // fileName=   "00010084_0022677_0001_b_201909131224556065.pdf";
                                    Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
                                    File folder = new File(Environment.getExternalStorageDirectory() + "/" + "EOL");

                                    if (!folder.exists())
                                        folder.mkdirs();
                                    saveFilePath = folder.getAbsolutePath() + File.separator + fileName;
                                    /////////////////////////Password Security///////////////////////////////
                                    Log.d("", "getBasePdf: saveFilePath" + saveFilePath);

                                    //  securePdfFile(saveFilePath);

                                    /////////////////////////Password Security///////////////////////////////

                                    ////////////////////Write Content To File//////////////////////////
                                    //   String dummySaveFilePath = folder.getAbsolutePath() + File.separator + "dummyVillagePdf.pdf";
                                    InputStream inputStream = new ByteArrayInputStream(response.data);
                                    // opens an output stream to save into file
                                    FileOutputStream outputStream = new FileOutputStream(saveFilePath);
                                    int bytesRead = -1;
                                    byte[] buffer = new byte[BUFFER_SIZE];
                                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                                        outputStream.write(buffer, 0, bytesRead);
                                    }

                                    outputStream.close();
                                    inputStream.close();
                                    ////////////////////Write Content To Password Secured File//////////////////////////
                                    //     pdfEncryption(dummySaveFilePath,saveFilePath);

                                    ////////////////////Write Content To Password Secured File////////////////////////


                                    //   File file = new File(dummySaveFilePath);
                                    //  file.delete();

                                    if ((MyVolley.dialog != null) && MyVolley.dialog.isShowing()) {
                                        MyVolley.dialog.dismiss();
                                    }

                                    /////////////////View Pdf//////////////

                                    File f = new File(saveFilePath);

                                    if (String.valueOf(f.length()).equals(js.getString("Content-Length"))) {
                                        System.out.println("download");

                                        new GeneratePdf().viewPdf(context, saveFilePath);

                                        if ((MyVolley.dialog != null) && MyVolley.dialog.isShowing()) {
                                            MyVolley.dialog.dismiss();
                                        }

                                    } else {
                                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.get_data), "Incomplete file !!", "035-001");
                                    }
                                    /////////////////View Pdf//////////////
                                    // check length of file

                                    return Response.success(new JSONObject(),
                                            HttpHeaderParser.parseCacheHeaders(response));
                                } else {
                                    String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                                    JSONObject jsonResponse = new JSONObject(json);
                                    return Response.success(jsonResponse, HttpHeaderParser.parseCacheHeaders(response));
                                }

                            } catch (UnsupportedEncodingException e) {
                                Log.d(TAG, "getBasePdf:ParseError 1" + e);
                                return Response.error(new ParseError(e));

                            } catch (Exception e) {
                                Log.d(TAG, "getBasePdf:ParseError 2" + e);

                                return Response.error(new ParseError(e));
                            }
                        }


                        @Override
                        public void onVolleyErrorResponse(VolleyError error) {
                            Log.d(TAG, "getBasePdf: Error " + error);

                        }
                    });

        } catch (Exception e) {
            if ((MyVolley.dialog != null) && MyVolley.dialog.isShowing()) {
                MyVolley.dialog.dismiss();
            }
        }
    }

    private void securePdfFile(String filePath) throws DocumentException, MalformedURLException, IOException {

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        writer.setEncryption("pwd123".getBytes(), "cp123".getBytes(), PdfWriter.ALLOW_COPY, PdfWriter.STANDARD_ENCRYPTION_40);
        writer.createXmpMetadata();
        document.open();
        document.add(new Paragraph("This is create PDF with Password demo."));
        document.close();
        System.out.println("Done securePdfFile");

    }

    public void pdfEncryption(String srcPdf, String targetPdf) {

        PdfReader reader = null;
        try {
            reader = new PdfReader(srcPdf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PdfStamper stamper = null;
        try {
            stamper = new PdfStamper(reader, new FileOutputStream(targetPdf));
            stamper.setEncryption("pwd123".getBytes(), "cp123".getBytes(), PdfWriter.ALLOW_COPY, PdfWriter.ENCRYPTION_AES_256);
            stamper.close();
            reader.close();
            System.out.println("Done pdfEncryption");
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
