package in.nic.ease_of_living.gp;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.nic.ease_of_living.adapter.DevelopmentBlockAdapter;
import in.nic.ease_of_living.adapter.GPAdapter;
import in.nic.ease_of_living.adapter.VillageAdapter;
import in.nic.ease_of_living.broadcast.NetworkChangeReceiver;
import in.nic.ease_of_living.interfaces.Communicator;
import in.nic.ease_of_living.models.DevelopmentBlock;
import in.nic.ease_of_living.models.GP;
import in.nic.ease_of_living.models.Village;
import in.nic.ease_of_living.supports.IsConnected;
import in.nic.ease_of_living.supports.MyAlert;
import in.nic.ease_of_living.supports.MySharedPref;
import in.nic.ease_of_living.supports.MyVolley;
import in.nic.ease_of_living.supports.Password;
import in.nic.ease_of_living.supports.VolleyMultipartRequest;
import in.nic.ease_of_living.supports.VolleySingleton;
import in.nic.ease_of_living.utils.AESHelper;
import in.nic.ease_of_living.utils.Common;
import in.nic.ease_of_living.utils.ImageFilePath;
import in.nic.ease_of_living.utils.NetworkRegistered;
import in.nic.ease_of_living.utils.PopulateMasterLists;
import in.nic.ease_of_living.utils.Utility;

//031-036
public class UploadPdfFile extends AppCompatActivity implements Communicator {

    Button btnUploadPdf;
    ImageView ivPhoto, ivPhoto2nd;
    TextView tvPhotoUpload, tvPhotoUpload2nd;
    Context context;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1, SELECT_DOCUMENT = 2;
    private String strUserChoosenTask, strImageSelected;
    private static File file;
    private int iPasswordMsgCount = 0;
    String TAG = "uploadPdfFile";
    private static Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD | Font.UNDERLINE);
    private Document document;
    private String directoryPath = "";
    private String filepath = "";
    private String imagePath1st = "";
    private String imagePath2nd = "";
    private String pdfFileDirectoryPath = "";


    private LinearLayout.LayoutParams params1st, params2nd;
    private String strFileName = "";
    private NetworkChangeReceiver mNetworkReceiver;
    private String strUserPassw = null;
    private MenuItem menuItem;

    private String strSpinnerDbRoleSelected = "", strSpinnerGpRoleSelected = "", strSpinnerVillageRoleSelected = "";
    private Spinner spinnerVillage, spinnerGpRole, spinnerDbRole;
    private int iSpinnerVillageSelected = 0;
    private int iSpinnerDbRoleSelected = 0;
    private int iSpinnerGpRoleSelected = 0;
    private DevelopmentBlockAdapter dbAdapter;
    private GPAdapter gpAdapter;
    private VillageAdapter villageAdapter;
    private ProgressDialog pd;
    private ArrayList<GP> alGp;
    private ArrayList<DevelopmentBlock> alDb;
    private ArrayList<Village> alVillage;
    JSONObject dbjsResponse;
    public Uri outputFileUri = null;
    Village village = null;
    public String strActivityName = "UploadPdfFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = UploadPdfFile.this;
        alGp = new ArrayList<>();
        alDb = new ArrayList<>();
        alVillage = new ArrayList<>();
        Intent intent = getIntent();
        strUserPassw = intent.getStringExtra("user_password");
        if (Password.matchUserCredentials(context, intent.getStringExtra("user_id"), strUserPassw)) {
            setContentView(R.layout.activity_upload_pdf_file);
            PopulateMasterLists.populateMastersList(context);
            Common.setAppHeader(context);
            dbjsResponse = new JSONObject();
            directoryPath = Environment.getExternalStorageDirectory().toString();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mNetworkReceiver = new NetworkChangeReceiver();
            NetworkRegistered.registerNetworkBroadcastForNougat(context, mNetworkReceiver);

            params1st = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params1st.setMargins(0, 15, 0, 0);
            params2nd = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params2nd.setMargins(50, 15, 0, 0);

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            findViews();
            getDevelopementblock();
            File folder = new File(Environment.getExternalStorageDirectory() + "/" + "ma_camera");
            if (folder.exists())
                deleteDirectory(folder);

            spinnerDbRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinnerGpRole.setAdapter(null);
                    spinnerVillage.setAdapter(null);
                    if (i == 0)
                        strSpinnerDbRoleSelected = "";
                    else {
                        DevelopmentBlock dpVlaue = dbAdapter.getItem(i);
                        strSpinnerDbRoleSelected = dpVlaue.getBlock_code();
                        getGP(strSpinnerDbRoleSelected);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerGpRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //GP gp = gpAdapter.getItem(i);
                    //strSpinnerGpRoleSelected = gp.getCode();
                    spinnerVillage.setAdapter(null);
                    if (i == 0)
                        strSpinnerGpRoleSelected = "";
                    else {
                        GP gp = gpAdapter.getItem(i);
                        strSpinnerGpRoleSelected = gp.getGp_code();
                        getVillage(strSpinnerDbRoleSelected, strSpinnerGpRoleSelected);

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            spinnerVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {


                    if (i == 0) {
                        iSpinnerVillageSelected = 0;
                    } else {
                        village = villageAdapter.getItem(i);
                        iSpinnerVillageSelected = village.getVillage_code();

                        if (village.getE_user_id().trim().equalsIgnoreCase(AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()))) {
                            btnUploadPdf.setEnabled(true);
                            btnUploadPdf.setBackgroundResource(R.color.colorPrimary);


                            strFileName = village.getE_file_name().trim().split("\\.")[0] + ".pdf";
                            document = new Document(PageSize.A4, 36, 36, 90, 36);

                            File folder = new File(Environment.getExternalStorageDirectory() + "/" + "mission_antyodaya");
                            if (!folder.exists())
                                folder.mkdirs();
                            pdfFileDirectoryPath = folder.getAbsolutePath() + "/" + strFileName;

                            try {
                                PdfWriter.getInstance(document, new FileOutputStream(pdfFileDirectoryPath)); //  Change pdf's name.
                            } catch (DocumentException e) {

                            } catch (FileNotFoundException e) {

                            }
                            document.open();
                        } else {
                            btnUploadPdf.setEnabled(false);
                            btnUploadPdf.setBackgroundResource(R.color.grey);
                            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), context.getString(R.string.msg_upload_pdf_user_not_allowed), "031-016");
                            spinnerVillage.setSelection(0);
                            iSpinnerVillageSelected = 0;
                        }

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            tvPhotoUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MySharedPref.saveImageFlag(context, "1");
                    selectImage();
                }
            });
            tvPhotoUpload2nd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MySharedPref.saveImageFlag(context, "2");

                    selectImage();
                }
            });
            btnUploadPdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!imagePath1st.isEmpty() && imagePath1st != null) {
                                Image image1 = null;  // Change image's name and extension.
                                try {
                                    image1 = Image.getInstance(imagePath1st);

                                } catch (BadElementException e) {

                                } catch (IOException e) {


                                }
                                float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                                        - document.rightMargin() - 0) / image1.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
                                image1.scalePercent(scaler);
                                image1.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
                                try {
                                    document.add(image1);
                                } catch (DocumentException e) {

                                }
                            }

                            if (!imagePath2nd.isEmpty() && imagePath2nd != null) {
                                Image image2 = null;  // Change image's name and extension.
                                try {
                                    image2 = Image.getInstance(imagePath2nd);
                                } catch (BadElementException e) {

                                } catch (IOException e) {

                                }
                                float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                                        - document.rightMargin() - 0) / image2.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
                                image2.scalePercent(scaler);
                                image2.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
                                try {
                                    document.add(image2);
                                } catch (DocumentException e) {

                                }
                                document.newPage();

                            }

                            if (strSpinnerDbRoleSelected.equalsIgnoreCase("") || strSpinnerDbRoleSelected.equals(null))
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), context.getString(R.string.select_db), "031-017");
                            else if (strSpinnerGpRoleSelected.equalsIgnoreCase("") || strSpinnerGpRoleSelected.equals(null))
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), context.getString(R.string.select_gp), "031-018");

                            else if (iSpinnerVillageSelected == 0)
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), context.getString(R.string.msg_select_village), "031-019");

                            else if ((imagePath1st.isEmpty() || imagePath1st == null) || (imagePath2nd.isEmpty() || imagePath2nd == null)) {
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), context.getString(R.string.select_image), "031-003");

                            } else {
                                document.close();
                                saveProfileAccount();
                            }

                        }
                    });


                }
            });
        }

    }

    private void findViews() {
        btnUploadPdf = (Button) findViewById(R.id.btnUploadPdf);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        tvPhotoUpload = (TextView) findViewById(R.id.tvPhotoUpload);
        ivPhoto2nd = (ImageView) findViewById(R.id.ivPhoto2nd);
        tvPhotoUpload2nd = (TextView) findViewById(R.id.tvPhotoUpload2nd);
        spinnerVillage = (Spinner) findViewById(R.id.spinnerVillage);
        spinnerGpRole = (Spinner) findViewById(R.id.spinnerGpRole);
        spinnerDbRole = (Spinner) findViewById(R.id.spinnerDbRole);
    }

    private void selectImage() {
        try {
            final CharSequence[] items = {context.getString(R.string.take_photo), context.getString(R.string.choose_from_gallery), context.getString(R.string.cancel)};

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            builder.setTitle(context.getString(R.string.takepicture));
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    boolean result = Utility.checkPermission(context);

                    if (items[item].equals(context.getString(R.string.take_photo))) {
                        strUserChoosenTask = context.getString(R.string.take_photo);
                        if (result)
                            cameraIntent();

                    } else if (items[item].equals(context.getString(R.string.choose_from_gallery))) {
                        strUserChoosenTask = context.getString(R.string.choose_from_gallery);
                        if (result)
                            galleryIntent();

                    } else if (items[item].equals(context.getString(R.string.cancel))) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), e.getMessage(), "031-004");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == SELECT_FILE) {
                    onSelectFromGalleryResult(data, Integer.parseInt(MySharedPref.getImageFlag(context)), document);
                } else if (requestCode == REQUEST_CAMERA) {
                    onCaptureImageResult(data, Integer.parseInt(MySharedPref.getImageFlag(context)), document);

                }

            }
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), e.getMessage(), "031-005");
        }
    }

    private static Bitmap bm = null;

    /* Convert the image captured using camera to bitmap*/
    private void onCaptureImageResult(Intent intent, int imageFlag, Document document) {
        try {
/*
           bm = (Bitmap) intent.getExtras().get("data");
*/
           /*
            bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(),outputFileUri);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 40, bytes);*/

            File folder = new File(Environment.getExternalStorageDirectory() + "/" + "ma_camera");
            if (!folder.exists())
                folder.mkdirs();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            // bm = Media.getBitmap(mContext.getContentResolver(), imageLoc);
            bm = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(outputFileUri), null, options);
            FileOutputStream out = new FileOutputStream(folder.getAbsolutePath() + "/" + filepath + ".jpg");
            bm.compress(Bitmap.CompressFormat.JPEG, 20, out);
           /* if (bm != null && !bm.isRecycled()) {
                bm.recycle();

            }
*/
          /*  filepath = "";
            filepath = String.valueOf(System.currentTimeMillis());
            File destination = new File(Environment.getExternalStorageDirectory(),
                    Long.valueOf(filepath) + ".jpg");

            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (FileNotFoundException e) {
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), e.getMessage(), "031-006");
            } catch (IOException e) {
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), e.getMessage(), "031-007");
            }
*/
            if (imageFlag == 1) {
                imagePath1st = "";
                imagePath1st = directoryPath + "/" + "ma_camera" + "/" +
                        filepath + ".jpg";

                ivPhoto.setVisibility(View.VISIBLE);
                ivPhoto.setImageBitmap(bm);
                ViewGroup.LayoutParams params = tvPhotoUpload.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                tvPhotoUpload.setLayoutParams(params);
                // tvPhotoUpload.setLayoutParams(params1st);
                tvPhotoUpload.setText(context.getString(R.string.change_first_page));

            } else if (imageFlag == 2) {
                imagePath2nd = "";
                imagePath2nd = directoryPath + "/" + "ma_camera" + "/" +
                        filepath + ".jpg";

                ivPhoto2nd.setVisibility(View.VISIBLE);
                ivPhoto2nd.setImageBitmap(bm);

                ViewGroup.LayoutParams params = tvPhotoUpload2nd.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                tvPhotoUpload2nd.setLayoutParams(params);
                tvPhotoUpload2nd.setText(context.getString(R.string.change_last_page));
                // tvPhotoUpload2nd.setLayoutParams(params2nd);
            }
            if (bm != null)
                strImageSelected = getStringImage(Common.doCompressImage(context, bm));


        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), e.getMessage(), "031-008");
        }
    }

    /* Convert the gallery selected image to bitmap*/
    private void onSelectFromGalleryResult(Intent intent, int imageFlag, Document document) {

        try {
            Bitmap bm = null;
            if (intent != null) {
                try {

                  /*  bm = MediaStore.Images.Media.getBitmap(getContentResolver(), intent.getData());
                    file = new File(ImageFilePath.getPath(context, intent.getData()));
                 */
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    // bm = Media.getBitmap(mContext.getContentResolver(), imageLoc);
                    bm = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(intent.getData()), null, options);
                    FileOutputStream out = new FileOutputStream(ImageFilePath.getPath(context, intent.getData()));
                    bm.compress(Bitmap.CompressFormat.JPEG, 20, out);


                    if (imageFlag == 1) {
                        imagePath1st = "";
                        imagePath1st = ImageFilePath.getPath(context, intent.getData());
                        ivPhoto.setVisibility(View.VISIBLE);
                        ivPhoto.setImageURI(intent.getData());
                        // tvPhotoUpload.setLayoutParams(params1st);
                        ViewGroup.LayoutParams params = tvPhotoUpload.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        tvPhotoUpload.setLayoutParams(params);

                        tvPhotoUpload.setText(context.getString(R.string.change_first_page));
                    } else if (imageFlag == 2) {
                        imagePath2nd = "";
                        imagePath2nd = ImageFilePath.getPath(context, intent.getData());
                        ivPhoto2nd.setVisibility(View.VISIBLE);
                        ivPhoto2nd.setImageURI(intent.getData());
                        //  tvPhotoUpload2nd.setLayoutParams(params2nd);

                        ViewGroup.LayoutParams params = tvPhotoUpload2nd.getLayoutParams();
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        tvPhotoUpload2nd.setLayoutParams(params);
                        tvPhotoUpload2nd.setText(context.getString(R.string.change_last_page));
                    }
                    if (bm != null)
                        strImageSelected = getStringImage(Common.doCompressImage(context, bm));
                } catch (Exception e) {
                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), e.getMessage(), "031-009");
                }
            }


            if (bm.getWidth() > bm.getHeight()) {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 300 / bm.getWidth() * 100, bytes);
                bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                int divider = bm.getWidth() / 300;
                if (divider == 0)
                    divider = 1;
                if (imageFlag == 1)
                    ivPhoto.setImageBitmap(Bitmap.createScaledBitmap(bm, bm.getWidth() / divider, bm.getHeight() / divider, true));
                else if (imageFlag == 2)
                    ivPhoto2nd.setImageBitmap(Bitmap.createScaledBitmap(bm, bm.getWidth() / divider, bm.getHeight() / divider, true));

            } else {
                int divider = bm.getHeight() / 300;
                if (divider == 0)
                    divider = 1;
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 300 / bm.getHeight() * 100, bytes);
                bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                if (imageFlag == 1)
                    ivPhoto.setImageBitmap(Bitmap.createScaledBitmap(bm, bm.getWidth() / divider, bm.getHeight() / divider, false));
                else if (imageFlag == 2)
                    ivPhoto2nd.setImageBitmap(Bitmap.createScaledBitmap(bm, bm.getWidth() / divider, bm.getHeight() / divider, false));

            }
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), e.getMessage(), "031-010");
        }
    }


    /* Open the device camera*/
    private void cameraIntent() {
        try {

            filepath = "";
            filepath = String.valueOf(System.currentTimeMillis());


            File destination = new File(Environment.getExternalStorageDirectory(),
                    Long.valueOf(filepath) + ".jpg");
            try {
                destination.createNewFile();
            } catch (IOException e) {
            }

            outputFileUri = Uri.fromFile(destination);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);


            startActivityForResult(intent, REQUEST_CAMERA);
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), e.getMessage(), "031-011");
        }
    }

    private void galleryIntent() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");

            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), e.getMessage(), "031-012");
        }
    }

    public String getStringImage(Bitmap bmp) {
        String strEncodedImage = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            strEncodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            //String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), e.getMessage(), "031-013");
        }
        return strEncodedImage;
    }

    private void saveProfileAccount() {
        // loading or check internet connection or something...
        // ... then
        //   String url = "http://10.199.89.99:8080/training/api/madatav1/uploadSurveyPDF";
        String url = "api/madata/v1/uploadSurveyPDF";
        String directoryPath = Environment.getExternalStorageDirectory().toString();
        //final String  pdfPath=directoryPath + strFileName;
        String strFinalUrl = MyVolley.getHostUrl(context) + url;

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(getApplicationContext(), Request.Method.POST, strFinalUrl, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    String status = result.getString("status");
                    String message = result.getString("message");

                    if (status.equals("true")) {
                        final Dialog dialogAlert = new Dialog(context);
                        MyAlert.dialogForOk
                                (context, R.mipmap.icon_info, context.getString(R.string.upload_success),
                                        context.getString(R.string.pdf_uploaded),
                                        dialogAlert,
                                        context.getString(R.string.ok),
                                        new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialogAlert.dismiss();
                                                finish();
                                            }
                                        }, "031-014");
                    } else
                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), message, "031-015");
                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               /* NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message + " Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message + " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message + " Something is getting wrong";
                        }
                    } catch (JSONException e) {

                    }
                }

                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), errorMessage, "031-020");*/

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    MyAlert.showAlert(context, R.mipmap.icon_error,  context.getString(R.string.upload_error), context.getString(R.string.error_no_connection),"031-031");
                } else if (error instanceof AuthFailureError) {
                    MyAlert.showAlert(context, R.mipmap.icon_error,  context.getString(R.string.upload_error),context.getString(R.string.error_server_default),"031-032" );
                } else if (error instanceof ServerError) {
                   MyVolley.showVolley_error(context,  context.getString(R.string.upload_error), error, "002");
                } else if (error instanceof NetworkError) {
                    MyAlert.showAlert(context, R.mipmap.icon_error,  context.getString(R.string.upload_error),context.getString(R.string.error_server_default),"031-033");
                } else if (error instanceof ParseError) {
                    MyAlert.showAlert(context, R.mipmap.icon_error,  context.getString(R.string.upload_error),context.getString(R.string.error_server_default),"031-034");
                } else if (error.toString().contains("NullPointerException")) {
                    MyAlert.showAlert(context, R.mipmap.icon_error,  context.getString(R.string.upload_error),context.getString(R.string.error_null_pointer),"031-035");
                }



            }
        }


        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
                params.put("state_code", String.valueOf(village.getState_code()));
                params.put("district_code", String.valueOf(village.getDistrict_code()));
                params.put("block_code", String.valueOf(village.getBlock_code()));
                params.put("gp_code", String.valueOf(village.getBlock_code()));
                params.put("village_code", String.valueOf(iSpinnerVillageSelected));

                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("surveyPdf", new DataPart(strFileName, convertPDFToByteArray(pdfFileDirectoryPath), "application/pdf"));

                return params;
            }
        };

        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(multipartRequest);
    }

    private static byte[] convertPDFToByteArray(String pdfPath) {

        InputStream inputStream = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {

            inputStream = new FileInputStream(pdfPath);

            byte[] buffer = new byte[16384];
            baos = new ByteArrayOutputStream();

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {

                }
            }
        }
        return baos.toByteArray();
    }

    @Override
    public void onBackPressed() {

        try {
            final Dialog dialogAlert = new Dialog(context);
            MyAlert.dialogForCancelOk
                    (context, R.mipmap.icon_warning, context.getString(R.string.upload_error),
                            context.getString(R.string.add_gp_msg2),
                            dialogAlert,
                            context.getString(R.string.yes),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogAlert.dismiss();

                                    finish();

                                }
                            },
                            context.getString(R.string.no),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogAlert.dismiss();
                                }
                            },
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogAlert.dismiss();
                                }
                            }, "031-022");
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, getString(R.string.upload_error), e.getMessage(), "031-021");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_internet_connectivity, menu);
        //return true;
        menuItem = menu.findItem(R.id.action_internet_status);
        if (!IsConnected.isInternet_connected(context, false))
            menuItem.setIcon(R.mipmap.icon_offline_status);
        else
            menuItem.setIcon(R.mipmap.icon_online_status);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void event(Boolean data) {
        try {
            if (data) {
                menuItem.setIcon(R.mipmap.icon_online_status);
            } else {
                menuItem.setIcon(R.mipmap.icon_offline_status);
            }
        } catch (NullPointerException e) {

        }
    }

    private void getDevelopementblock() {
        if ((pd == null) || ((pd != null) && (!pd.isShowing())))
            pd = new ProgressDialog(context);
        try {
            JSONObject jsRequest = new JSONObject();
            jsRequest.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
            //  jsRequest.put("user_id","9578e810-27cf-44f6-bd2a-c3cc4281f288");
            strSpinnerDbRoleSelected = "";
            spinnerDbRole.setAdapter(null);


            MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context,"023", true, true, "location/management/v1/completedDBMaster", jsRequest, context.getString(R.string.upload_error),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                pd.dismiss();


                                if (jsResponse.getBoolean("status")) {
                                    alDb = new Gson().fromJson(jsResponse.getJSONObject("response").getJSONArray("developmentBlockList").toString(), new TypeToken<List<DevelopmentBlock>>() {
                                    }.getType());

                                    alDb.add(0, new DevelopmentBlock(context.getString(R.string.spinner_heading_db)));

                                    dbAdapter = new DevelopmentBlockAdapter(UploadPdfFile.this,
                                            android.R.layout.simple_spinner_item,
                                            alDb);

                                    spinnerDbRole.setAdapter(dbAdapter);
                                    strSpinnerDbRoleSelected = "";


                                } else {
                                    spinnerDbRole.setAdapter(null);
                                    strSpinnerDbRoleSelected = "";
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)), "031-023");
                                }


                            } catch (Exception e) {
                                spinnerDbRole.setAdapter(null);
                                strSpinnerDbRoleSelected = "";
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), e.getMessage(), "031-024");
                            }
                        }
                    }
            );
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), e.getMessage(), "031-025");
        }
    }

    /* Function to get gp from webservice  */
    private void getGP(final String strSpinnerDbSelected) {


        if ((pd == null) || ((pd != null) && (!pd.isShowing())))
            pd = new ProgressDialog(context);
        try {
            JSONObject jsRequest = new JSONObject();

            jsRequest.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
            jsRequest.put("block_code", Integer.parseInt(strSpinnerDbSelected));
            spinnerGpRole.setAdapter(null);
            strSpinnerGpRoleSelected = "";

            MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context, "024",true, true, "location/management/v1/completedGPMaster", jsRequest, context.getString(R.string.upload_error),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                pd.dismiss();

                                if (jsResponse.getBoolean("status")) {
                                    alGp = new Gson().fromJson(jsResponse.getJSONObject("response").getJSONArray("grampanchayatList").toString(), new TypeToken<List<GP>>() {
                                    }.getType());
                                    alGp.add(0, new GP(context.getString(R.string.spinner_heading_gp)));

                                    gpAdapter = new GPAdapter(UploadPdfFile.this,
                                            android.R.layout.simple_spinner_item,
                                            alGp);

                                    spinnerGpRole.setAdapter(gpAdapter);

                                } else {
                                    spinnerGpRole.setAdapter(null);
                                    strSpinnerGpRoleSelected = "";
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)), "031-036");
                                }


                            } catch (Exception e) {
                                strSpinnerGpRoleSelected = "";
                                spinnerGpRole.setAdapter(null);
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), e.getMessage(), "031-026");
                            }
                        }
                    });
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), e.getMessage(), "031-027");
        }
    }

    private void getVillage(final String strSpinnerDbSelected, final String strSpinnerGpSelected) {
        if ((pd == null) || ((pd != null) && (!pd.isShowing())))
            pd = new ProgressDialog(context);
        try {
            JSONObject jsRequest = new JSONObject();
            jsRequest.put("user_id", AESHelper.getDecryptedValue(context, MySharedPref.getCurrentUser(context).getUser_id()));
            jsRequest.put("block_code", Integer.parseInt(strSpinnerDbSelected));
            jsRequest.put("gp_code", Integer.parseInt(strSpinnerGpSelected));

            spinnerVillage.setAdapter(null);
            strSpinnerVillageRoleSelected = "";

            MyVolley.callWebServiceUsingVolley(Request.Method.POST, pd, context, "025",true, true, "location/management/v1/completedVillageMaster", jsRequest, context.getString(R.string.upload_error),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsResponse) {
                            try {
                                pd.dismiss();

                                if (jsResponse.getBoolean("status")) {
                                    alVillage = new Gson().fromJson(jsResponse.getJSONObject("response").getJSONArray("villageList").toString(), new TypeToken<List<Village>>() {
                                    }.getType());
                                    alVillage.add(0, new Village(context.getString(R.string.spinner_heading_village)));

                                    villageAdapter = new VillageAdapter(UploadPdfFile.this,
                                            android.R.layout.simple_spinner_item,
                                            alVillage);

                                    spinnerVillage.setAdapter(villageAdapter);
                                } else {
                                    spinnerVillage.setAdapter(null);
                                    strSpinnerVillageRoleSelected = "";
                                    MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), jsResponse.getString(context.getString(R.string.web_service_message_identifier)), "031-028");
                                }


                            } catch (Exception e) {
                                spinnerVillage.setAdapter(null);
                                strSpinnerVillageRoleSelected = "";
                                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), e.getMessage(), "031-029");
                            }
                        }
                    });
        } catch (Exception e) {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.upload_error), e.getMessage(), "031-030");
        }
    }
    public static boolean deleteDirectory(File path) {
        if( path.exists() ) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return( path.delete() );
    }
}
