package in.nic.ease_of_living.utils;

public class GeneratePdfFileHtml {
/*

    private OutputStream output = null;
    private String strOutFileNameHtml = null;
    private String strOutFileNamePdf = null;
    private String strHtml = null;
    private Context mContext;
    private String strOutFilePathPdf =  null;
    private String strOutFilePathHtml = null;
    private int nPdfType = 0;
    private Boolean bViewPdf;
    private String strWatermark = "";
    private String strLanguageLocale = null;


    private boolean isSDCardWriteable(Context context) {
        boolean rc = false;
        try {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                rc = true;
            }
        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"025-010");
        }
        return rc;
    }

    */
/* Function to export survey data to a file*//*

    public String exportDataToHtml(final ProgressDialog pd, Context context, String strFileName,
                                   ArrayList<GpVillageSurvey>  alGpVillageSurvey, Boolean bViewPdf, int nPdfType,
                                   String strWatermark, final String strLanguageLocale) throws IOException {

        String str_res = "false";
        OutputStream outputDycrpt = null;
        FileInputStream fis = null;
        this.mContext = context;
        this.strOutFileNamePdf = strFileName;
        this.nPdfType = nPdfType;
        this.bViewPdf = bViewPdf;
        this.strWatermark = strWatermark;
        this.strLanguageLocale = strLanguageLocale;

        try {
            if (isSDCardWriteable(context)) {

                String strFileNameHtml = strFileName.split(".pdf")[0] + ".html";

                strOutFilePathPdf = Environment.getExternalStorageDirectory() + "/" + "GP_Pdf";
                File folder = new File(strOutFilePathPdf);
                if (!folder.exists())
                    folder.mkdirs();

                */
/* Search old files*//*

                File directory = new File(folder.getAbsolutePath());
                File[] files = directory.listFiles();

                for (int i = 0; i < files.length; i++)
                {
                    */
/* Delete old file *//*

                    File fdelete = new File(folder.getAbsolutePath() + "/" + files[i].getName());
                    if (fdelete.exists()
                            && files[i].getName().contains(strFileName.split(".pdf")[0])
                    )
                    {
                        if (fdelete.delete()) {

                        } else {

                        }
                    }
                }

                strOutFileNameHtml = folder.getAbsolutePath() + "/" + strFileNameHtml;

                // Open the empty db as the output stream
                output = new FileOutputStream(strOutFileNameHtml);

                addContent(pd, context, alGpVillageSurvey);

                str_res = "true";
            }
        }catch(Exception e)
        {
            str_res = e.toString();
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.app_error), e.getMessage(),"025-009");
        }
        finally
        {
            if (output != null)
                SafeClose.safeCloseOutputStream(output);
            if(fis != null)
                SafeClose.safeCloseInputStream(fis);
        }
        return str_res;
    }

    private void addContent(final ProgressDialog pd, Context context, ArrayList<GpVillageSurvey>  alGpVillageSurvey)
    {
        try {

            strHtml = "<html><style>"+
                    "body {" +
                    "  min-height: 300vh;" +
                    "  position: relative;" +
                    "  margin: 0;" +
                    "}" +
                    "" +
                    "body:before {" +
                    "  content: \"\";" +
                    "  position: absolute;" +
                    "  z-index: -1;" +
                    "  top: 0;" +
                    "  bottom: 0;" +
                    "  left: 0;" +
                    "  right: 0;" +
                    "  background: url('data:image/svg+xml;utf8,<svg style=\"transform:rotate(-45deg) \" xmlns=\"http://www.w3.org/2000/svg\"  viewBox=\"0 0 80 60\"><text x=\"10\" y=\"45\" fill=\"rgb(204,204,204)\" font-size=\"20\">" + strWatermark + "</text></svg>') 0 0/100% 100vh;" +
                    "}</style>"+
                    "<body style=\"BORDER: 0px; MARGIN: 0px; PADDING: 0px\">";

            //strHtml = "<html><body style=\"BORDER: 0px; MARGIN: 0px; PADDING: 0px\">" fill:#d0d0d0;;

            for (int i = 0; i < alGpVillageSurvey.size(); i++) {
                GpVillageSurvey gp_survey_base = GpVillageBaseController.getGp(context, DBHelper.getInstance(context, true), alGpVillageSurvey.get(i).getVillage_code());

                if (i == 0) {
                    try {
                        InputStream ims = context.getAssets().open("ic_launcher_logo.png");
                        Bitmap bmp = BitmapFactory.decodeStream(ims);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

                        String imagepath = "file:///android_res/mipmap/ic_launcher_logo.png";

                        strHtml += "<div><img src=" + imagepath + " width=\"100\" height=\"100\" align=\"right\"><font size=\"6\"><b><center>" + context.getString(R.string.pdf_title) + "</center></b></font> </div>";

                    } catch (IOException e) {
                        MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error), e.getMessage(), "018-019");
                    }
                }

                strHtml += "<div><font size=\"5\"><b><u>"+ context.getString(R.string.village) + " :" + MasterCommonController.mapVillage.get(alGpVillageSurvey.get(i).getVillage_code()) + "</u></b></font></div>";

                addVillage_table(pd, context,  gp_survey_base, alGpVillageSurvey.get(i));
            }
        }catch(Exception e)
            {
                MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error), e.getMessage() ,"018-020");
            }
    }

    private void addVillage_table(final ProgressDialog pd, Context context, GpVillageSurvey gpVillageBase, GpVillageSurvey gpVillageSurvey) {
        // Location Parameters
        try {
            ArrayList<Pdf_cell> list = new ArrayList<>();
            list.add(new Pdf_cell("", context.getString(R.string.state), "", "", gpVillageSurvey.getState_name() + "(" + gpVillageSurvey.getState_code() + ")", "", "", ""));
            list.add(new Pdf_cell("", context.getString(R.string.district), "", "", gpVillageSurvey.getDistrict_name() + "(" + gpVillageSurvey.getDistrict_code() + ")", "", "", ""));
            list.add(new Pdf_cell("", context.getString(R.string.dev_block), "", "", gpVillageSurvey.getBlock_name() + "(" + gpVillageSurvey.getBlock_code() + ")", "", "", ""));
            list.add(new Pdf_cell("", context.getString(R.string.gp), "", "", gpVillageSurvey.getGp_name() + "(" + gpVillageSurvey.getGp_code() + ")", "", "", ""));
            list.add(new Pdf_cell("", context.getString(R.string.total_villages), "", "", String.valueOf(PopulateMasterLists.adapterVillage.getCount() - 1), "", "", ""));
            list.add(new Pdf_cell("", context.getString(R.string.completed_villages), "", "", String.valueOf(0), "", "", ""));
            list.add(new Pdf_cell("", context.getString(R.string.village), "", "", MasterCommonController.mapVillage.get(gpVillageSurvey.getVillage_code()) + "(" + gpVillageSurvey.getVillage_code() + ")", "", "", ""));
            list.add(new Pdf_cell("", context.getString(R.string.pincode), "", "", gpVillageSurvey.getVillage_pin_code(), "", "", ""));
            list.add(new Pdf_cell("", context.getString(R.string.parliament_constituency), "", "",
                    gpVillageSurvey.getPc_code() == 0 ? context.getString(R.string.not_available) : ParliamentConstituencyController.getName(context, DBHelper.getInstance(context, false), gpVillageSurvey.getPc_code(), false),
                    "", "", ""));
            list.add(new Pdf_cell("", context.getString(R.string.assembly_constituency), "", "",
                    gpVillageSurvey.getAc_code() == 0 ? context.getString(R.string.not_available) : AssemblyConstituencyController.getName(context, DBHelper.getInstance(context, false), gpVillageSurvey.getAc_code(), gpVillageSurvey.getPc_code(), false),
                    "", "", ""));
            list.add(new Pdf_cell("", context.getString(R.string.name_of_constituency), "", "",
                    gpVillageSurvey.getOther_assembly_constituencies(), "", "", ""));
            list.add(new Pdf_cell("", "", "", "",
                    "", "", "", ""));


            if (gpVillageSurvey.getIs_verified()) {
                list.add(new Pdf_cell("", context.getString(R.string.is_verified), "", "", gpVillageSurvey.getIs_verified() == true ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));
                if ((gpVillageSurvey.getTs_verified() == null) || gpVillageSurvey.getTs_verified().isEmpty())
                    list.add(new Pdf_cell("", context.getString(R.string.ts_verified), "", "", context.getString(R.string.not_available), "", "", ""));
                else
                    list.add(new Pdf_cell("", context.getString(R.string.ts_verified), "", "", gpVillageSurvey.getTs_verified(), "", "", ""));
            } else if (gpVillageSurvey.getIs_uploaded()) {
                list.add(new Pdf_cell("", context.getString(R.string.is_uploaded), "", "", gpVillageSurvey.getIs_uploaded() == true ? context.getString(R.string.yes) : context.getString(R.string.no), "", "", ""));
                if ((gpVillageSurvey.getTs_uploaded() == null) || gpVillageSurvey.getTs_uploaded().isEmpty())
                    list.add(new Pdf_cell("", context.getString(R.string.ts_uploaded), "", "", context.getString(R.string.not_available), "", "", ""));
                else
                    list.add(new Pdf_cell("", context.getString(R.string.ts_uploaded), "", "", gpVillageSurvey.getTs_uploaded(), "", "", ""));
            } else {
                if (!gpVillageSurvey.getIs_uploaded())
                    list.add(new Pdf_cell("", context.getString(R.string.is_send_to_server), "", "", String.valueOf(gpVillageSurvey.getIs_synchronized() == true ? context.getString(R.string.yes) : context.getString(R.string.no)), "", "", ""));
                else
                    list.add(new Pdf_cell("", context.getString(R.string.is_send_to_server), "", "", context.getString(R.string.no), "", "", ""));
                if ((gpVillageSurvey.getDt_sync() == null) || gpVillageSurvey.getDt_sync().isEmpty())
                    list.add(new Pdf_cell("", context.getString(R.string.ts_send_to_server), "", "", context.getString(R.string.not_available), "", "", ""));
                else
                    list.add(new Pdf_cell("", context.getString(R.string.ts_send_to_server), "", "", gpVillageSurvey.getDt_sync(), "", "", ""));
            }

            createTable(context, 1, 0, context.getString(R.string.location_parameters), list);

            */
/************************* Part A************************//*

            strHtml += "<div><font size=\"5\"><center><b><u>" + context.getString(R.string.part_a) + "</u></b></center></font></div>";


            // Basic Parameters
            list.clear();
            if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2011"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.census_2011_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2017"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2017_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));
            else if (gpVillageBase.getBase_data_enum_phase().equalsIgnoreCase("2018"))
                list.add(new Pdf_cell(context.getString(R.string.sno), context.getString(R.string.question), context.getString(R.string.ma_2018_status), "", context.getString(R.string.ma_2019_status), "", context.getString(R.string.gram_smiti_approval), context.getString(R.string.gram_smiti_changes)));

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


            createTable(context, 2, 1, context.getString(R.string.basic_parameters), list);

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



            createTable(context, 2, 2, context.getString(R.string.section_agriculture), list);

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


            createTable(context, 2, 3, context.getString(R.string.section_land_improvement_minor_irr), list);


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


            createTable(context, 2, 4, context.getString(R.string.section_animal_husbandry), list);


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


            createTable(context, 2, 5, context.getString(R.string.section_fisheries), list);


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


            createTable(context, 2, 6, context.getString(R.string.section_rural_housing), list);


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

            createTable(context, 2, 7, context.getString(R.string.section_drinking_water), list);

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


            createTable(context, 2, 8, context.getString(R.string.section_roads), list);

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

            createTable(context, 2, 9, context.getString(R.string.section_rural_electrification), list);

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

            createTable(context, 2, 10, context.getString(R.string.section_non_conventional), list);

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

            createTable(context, 2, 11, context.getString(R.string.section_maint_community_assets), list);

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

            createTable(context, 2, 12, context.getString(R.string.section_fuel_fodder), list);

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


            createTable(context, 2, 13, context.getString(R.string.section_libraries), list);

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

            createTable(context, 2, 14, context.getString(R.string.section_cultural_activities), list);

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

            createTable(context, 2, 15, context.getString(R.string.section_financial_communcation_infrastructure), list);

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

            createTable(context, 2, 16, context.getString(R.string.section_pds), list);



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


            createTable(context, 2, 17, context.getString(R.string.section_education), list);

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

            createTable(context, 2, 18, context.getString(R.string.section_vocational_education), list);

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



            createTable(context, 2, 19, context.getString(R.string.section_markets_fairs), list);

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


            createTable(context, 2, 20, context.getString(R.string.section_health_sanitation), list);

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


            createTable(context, 2, 21, context.getString(R.string.section_women_child_dev), list);

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


            createTable(context, 2, 22, context.getString(R.string.section_social_welfare), list);


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

            createTable(context, 2, 23, context.getString(R.string.section_family_welfare), list);

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

            createTable(context, 2, 24, context.getString(R.string.section_weaker_section), list);

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


            createTable(context, 2, 25, context.getString(R.string.section_poverty_alleviation_programme), list);


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

            createTable(context, 2, 26, context.getString(R.string.section_khadi_village_cottage_ind), list);

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

            createTable(context, 2, 27, context.getString(R.string.section_social_forestry), list);

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

            createTable(context, 2, 28, context.getString(R.string.section_minor_forest_produce), list);

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

            createTable(context, 2, 29, context.getString(R.string.section_small_scale_industries), list);

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


            createTable(context, 2, 30, context.getString(R.string.section_adult_non_formal_edu), list);


            */
/*****************************   Extra line *******************************//*

            strHtml += "<br>";


            */
/************************************** Part B ************************************************//*


            strHtml += "<div><font size=\"5\"><center><b><u>" + context.getString(R.string.part_b) + "</u></b></center></font></div>";

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


            createTable(context, 2, 1, context.getString(R.string.health_nutrition), list);


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

            createTable(context, 2, 2, context.getString(R.string.social_security), list);


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



            createTable(context, 2, 3, context.getString(R.string.agriculture_livelihoods), list);

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

            createTable(context, 2, 4, context.getString(R.string.good_governance), list);

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
                    "", String.format("%.2f",new BigDecimal(gpVillageSurvey.getTotal_expenditure_approved_under_nrm_labour_budget_during_yr())), "", "", ""));

            list.add(new Pdf_cell("140", context.getString(R.string.irrigation_area_drip_sprinkler),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_area_covered_under_irrigation_using_drip_sprinkler()), "", "", ""));

            list.add(new Pdf_cell("141", context.getString(R.string.hhd_having_piped_water_connection),
                    context.getString(R.string.not_available),
                    "", String.valueOf(gpVillageSurvey.getTotal_hhd_having_piped_water_connection()), "", "", ""));

            createTable(context, 3, 5, context.getString(R.string.water_management_efficiency), list);

            strHtml += "<br><table cols=\"4\" border=\"2\"><tbody><tr><td style=\"width: 200mm;\"><div>" + context.getString(R.string.gram_sevak).replaceAll("\n","<br>")+ "</div></td>" +
                    "<td rowspan=\"2\" valign=\"top\" style=\"width: 200mm;\">" + context.getString(R.string.gramsmiti_signature) + "</td></tr>" +
                    "<tr>" +
                    "<td><div>" + context.getString(R.string.bdo).replaceAll("\n","<br>") + "</div></td>" +
                    "</tr></tbody></table>";

            if((nPdfType == 1) || (nPdfType == 3))
                strHtml += "<p>" + context.getString(R.string.msg_disclaimer) + "</p>";

            strHtml +="</body></html>";
            output.write( strHtml.getBytes() );
            // Close the streams
            output.flush();
            output.close();

        }catch(Exception e)
        {
            MyAlert.showAlert(context, R.mipmap.icon_error, context.getString(R.string.generate_pdf_error),  e.getMessage(),"018-021");
        }
        finally
        {
            pd.dismiss();
            newPdfGen(context);
        }
    }

    private  void createTable(Context context, int part, int nTableSNo, String strTableHeading, ArrayList<Pdf_cell> list) {
        try {
            String strTable = "";
            if(nTableSNo == 0)
                strHtml += "<div><font size=\"4\"><b><u>" + strTableHeading + "</u></b></font></div>";
            else if(nTableSNo == 1)
                strHtml += "<div><font size=\"4\"><b><u>" + String.valueOf(nTableSNo) + ". " + strTableHeading + "</u></b></font></div>";
            else
                strHtml += "<br><div><font size=\"4\"><b><u>" + String.valueOf(nTableSNo) + ". " + strTableHeading + "</u></b></font></div>";
            if (part == 1) {
                strTable = "<Table  cols=\"4\" border=\"2\"><tbody>";
                for (int i = 0; i < list.size(); i=i+2) {

                    strTable += "<tr height=\"0\">";
                    String str1 = list.get(i).getCellLeft();
                    strTable += "<td  style=\"width: 100mm; height: 6.35mm;\" ><div >" + str1 + "</div></td>";


                    String str2 = list.get(i).getCellRight();
                    strTable += "<td  style=\"width: 100mm;\"><div >" + str2 + "</div></td>";


                    String str3="", str4="";
                    if(i<list.size()-1) {
                        str3 = list.get(i + 1).getCellLeft();
                        str4 = list.get(i + 1).getCellRight();
                    }
                    else
                    {
                        str3 = "";
                        str4="";

                    }
                    strTable += "<td  style=\"width: 100mm;\"><div >" + str3 + "</div></td>";
                    strTable += "<td  style=\"width: 100mm;\"><div >" + str4 + "</div></td>";

                    strTable += "</tr>";

                }
                strTable += "</tbody></Table>";
            } else {
                strTable = "<Table  cols=\"6\" border=\"2\"><tbody>";

                for (int i = 0; i < list.size(); i++) {
                    strTable += "<tr height=\"0\">";

                    String strCsn = list.get(i).getSno();
                    strTable += "<td  style=\"width: 15mm; height: 6.35mm;\" ><div>" + strCsn + "</div></td>";


                    String strQues = list.get(i).getCellLeft();
                    strTable += "<td  style=\"width: 65mm; height: 6.35mm;\" ><div >" + strQues + "</div></td>";


                    String distance_value="";
                    if(list.get(i).getDistanceValueBase()!=null)
                        distance_value=list.get(i).getDistanceValueBase().isEmpty()?"":" -("+list.get(i).getDistanceValueBase()+")";
                    else
                        distance_value="";
                    String strBaseValue = list.get(i).getCellMid() + distance_value;
                    strTable += "<td  style=\"width: 25mm; height: 6.35mm;\" ><div >" + strBaseValue + "</div></td>";


                    if(list.get(i).getDistanceValue()!=null)
                        distance_value=list.get(i).getDistanceValue().isEmpty()?"":" -("+list.get(i).getDistanceValue()+")";
                    else
                        distance_value="";

                    String strCurrentValue = list.get(i).getCellRight() + distance_value;
                    strTable += "<td  style=\"width: 30mm; height: 6.35mm;\" ><div >" + strCurrentValue + "</div></td>";

                    String strGsIsValueChange;
                    if(list.get(i).getGramSamiti_approval()== null || Support.isWhite_space(list.get(i).getGramSamiti_approval()))
                    {
                        strGsIsValueChange = "□";
                        strTable += "<td  style=\"width: 30mm; height: 6.35mm;\" ><div ><center><font size=\"5\">" + strGsIsValueChange + "</font></center></div></td>";
                    }
                    else
                    {
                        strGsIsValueChange = list.get(i).getGramSamiti_approval() + "(✓ or ✘)";
                        strTable += "<td  style=\"width: 30mm; height: 6.35mm;\" ><div >" + strGsIsValueChange + "</div></td>";
                    }

                    String strGsChangeValue;
                    if(list.get(i).getGramSamiti_change_value()== null || Support.isWhite_space(list.get(i).getGramSamiti_change_value())) {
                        strGsChangeValue = list.get(i).getGramSamiti_change_value();
                    }
                    else
                    {
                        strGsChangeValue = list.get(i).getGramSamiti_change_value() + "(If " + "✘" + ")";
                    }
                    strTable += "<td  style=\"width: 35mm; height: 6.35mm;\" ><div class=\"a100\">" + strGsChangeValue + "</div></td>";


                    strTable += "</tr>";

                }
                strTable += "</tbody></Table>";
            }
            strHtml += strTable;


        }catch (Exception e)
        {
           MyAlert.showAlert(mContext, R.mipmap.icon_error, mContext.getString(R.string.generate_pdf_error),  e.getMessage(),"018-023");
        }

    }

    public void newPdfGen(final Context context) {
        try {
            StringBuilder sb = new StringBuilder();
            InputStream is = new FileInputStream(strOutFileNameHtml);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();

            new CreatePdf(mContext)
                    .setPdfName(strOutFileNamePdf.split(".pdf")[0])
                    .openPrintDialog(false)
                    .setContentBaseUrl(null)
                    .setPageSize(PrintAttributes.MediaSize.ISO_A4)

                    // pass your string here...
                    .setContent(sb.toString())

                    //It Creates directory/filepath for the pdf file. - You can change the path as per your provision
                    .setFilePath(strOutFilePathPdf)
                    .setCallbackListener(new CreatePdf.PdfCallbackListener() {
                        @Override
                        public void onFailure(String s) {
                            // handle error
                            Toast.makeText(context, "Pdf error ..", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onSuccess(String s) {
                            // do your stuff here

                            File directory = new File(strOutFilePathPdf);
                            File[] files = directory.listFiles();

                            for (int i = 0; i < files.length; i++)
                            {
                               //  Delete old file
                                File fdelete = new File(strOutFilePathPdf + "/" + files[i].getName());
                                if (fdelete.exists()
                                        && files[i].getName().contains(strOutFileNamePdf.split(".pdf")[0]+".html")
                                )
                                {
                                    if (fdelete.delete()) {

                                    } else {

                                    }
                                }
                            }

                        }
                    })
                    .create();

            if(strLanguageLocale!=null)
            {
                Locale locale = new Locale(strLanguageLocale);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                // MySharedPref.saveLocaleLanguage(context, locale);
                context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
            }

            if(bViewPdf)
            {
                File folder = new File(strOutFilePathPdf);

                final String strSaveFilePath = folder.getAbsolutePath() + File.separator + strOutFileNamePdf;

                Handler handler = new Handler();
                final ProgressDialog pd = new ProgressDialog(context);
                pd.setMessage(context.getString(R.string.please_wait));
                pd.setCancelable(false);
                pd.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        (new GeneratePdf()).viewPdf(context, strSaveFilePath);


                    }
                },4000);

            }
        }catch (Exception e)
        {
            MyAlert.showAlert(mContext, R.mipmap.icon_error, mContext.getString(R.string.generate_pdf_error), e.getMessage(),"018-022");
        }
    }

    private String getDistanceValueA(int nCodeDistance)
    {
        try{
            return MasterCommonController.getNameDistance(mContext, DBMaster.getInstance(mContext, true), 3, nCodeDistance, "A");
        }catch(Exception e)
        {
            MyAlert.showAlert(mContext, R.mipmap.icon_error, mContext.getString(R.string.generate_pdf_error), e.getMessage(),"018-022");
        }
        return null;
    }

    private String getDistanceValueB(int nCodeDistance)
    {
        try{
            return MasterCommonController.getNameDistance(mContext, DBMaster.getInstance(mContext, true), 3, nCodeDistance, "B");
        }catch(Exception e)
        {
            MyAlert.showAlert(mContext, R.mipmap.icon_error, mContext.getString(R.string.generate_pdf_error), e.getMessage(),"018-026");
        }
        return null;
    }
*/
}
