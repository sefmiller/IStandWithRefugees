    package com.gb.istandwithrefugeesapp.Model;

    import android.app.ProgressDialog;
    import android.content.SharedPreferences;
    import android.graphics.drawable.Drawable;
    import android.os.AsyncTask;
    import android.support.annotation.Nullable;
    import android.util.SparseArray;
    import android.widget.Toast;

    import com.bumptech.glide.Glide;
    import com.bumptech.glide.load.DataSource;
    import com.bumptech.glide.load.engine.DiskCacheStrategy;
    import com.bumptech.glide.load.engine.GlideException;
    import com.bumptech.glide.request.RequestListener;
    import com.bumptech.glide.request.RequestOptions;
    import com.bumptech.glide.request.target.Target;
    import com.bumptech.glide.signature.ObjectKey;
    import com.gb.istandwithrefugeesapp.MainActivity;
    import com.gb.istandwithrefugeesapp.R;
    import com.gb.istandwithrefugeesapp.SplashScreenActivity;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.io.BufferedReader;
    import java.io.BufferedWriter;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.io.OutputStream;
    import java.io.OutputStreamWriter;
    import java.net.HttpURLConnection;
    import java.net.MalformedURLException;
    import java.net.URL;
    import java.net.URLEncoder;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.LinkedHashMap;
    import java.util.TreeSet;

    import static android.content.Context.MODE_PRIVATE;

    /**
     * Created by hp on 03/09/2017.
     */

    public class DBHelper {
        private SparseArray<LatLong> longLatMap = new SparseArray<>();
        private SparseArray<LatLong> longLatMapEU = new SparseArray<>();
        private SparseArray <HashMap<String, UkMarker>> markersMap = new SparseArray <>();
                private SparseArray <EUMarker> overseasMarkersMap = new SparseArray <>();
        private ArrayList<String> logoUrlsOverseas = new ArrayList<>();
        int in = 0;
        private LinkedHashMap<String, ArrayList<String>> countryAndRegionsArray = new LinkedHashMap<String, ArrayList<String>>();
        public LinkedHashMap<String, ArrayList<String>> getCountryAndRegionsArray() {
            return countryAndRegionsArray;
        }

        public void setCountryAndRegionsArray(LinkedHashMap<String, ArrayList<String>> countryAndRegionsArray) {
            this.countryAndRegionsArray = countryAndRegionsArray;
        }

        public ArrayList<String> getLogoUrlsOverseas() {
            return logoUrlsOverseas;
        }

        public void setLogoUrlsOverseas(ArrayList<String> logoUrlsOverseas) {
            this.logoUrlsOverseas = logoUrlsOverseas;
        }

        private SparseArray<OnlineAid> onlineAids = new SparseArray<>();
        private ArrayList<String> logoUrlsOnline = new ArrayList<>();

        private TreeSet<String> area_name_array = new TreeSet<>();
        private ArrayList<Bookmark> bookmarksArray = new ArrayList<>();
        private SplashScreenActivity splashScreenActivity;
        private String loginId;
        private SparseArray<String> ulMap = new SparseArray<>();
        private LatLong currentlatLong;
        private String markerIdToBeAdded;
        private String orgType;
        private ArrayList<String> logoUrls = new ArrayList<>();
        private ArrayList<String> lastModifiedList = new ArrayList<>();
        private SharedPreferences sharedPreferences;
        private ArrayList<String> lastModifiedListOnline = new ArrayList<>();
        private ArrayList<String> logoUrlsEU = new ArrayList<>();
        private ArrayList<String> lastModifiedListEU = new ArrayList<>();

        public SparseArray<OnlineAid> getOnlineAids() {
            return onlineAids;
        }

        public SparseArray<LatLong> getLongLatMapEU() {
            return longLatMapEU;
        }

        public void setLongLatMapEU(SparseArray<LatLong> longLatMapEU) {
            this.longLatMapEU = longLatMapEU;
        }

        public SparseArray<EUMarker> getOverseasMarkersMap() {
            return overseasMarkersMap;
        }

        public void setOverseasMarkersMap(SparseArray<EUMarker> overseasMarkersMap) {
            this.overseasMarkersMap = overseasMarkersMap;
        }

        public void setOnlineAids(SparseArray<OnlineAid> onlineAids) {
            this.onlineAids = onlineAids;
        }

        public ArrayList<String> getLogoUrlsOnline() {
            return logoUrlsOnline;
        }

        public void setLogoUrlsOnline(ArrayList<String> logoUrlsOnline) {
            this.logoUrlsOnline = logoUrlsOnline;
        }

        public ArrayList<String> getLastModifiedList() {
            return lastModifiedList;
        }

        public void setLastModifiedList(ArrayList<String> lastModifiedList) {
            this.lastModifiedList = lastModifiedList;
        }

        public ArrayList<String> getLogoUrls() {
            return logoUrls;
        }

        public void setLogoUrls(ArrayList<String> logoUrls) {
            this.logoUrls = logoUrls;
        }

        public void setLongLatMap(SparseArray<LatLong> longLatMap) {
            this.longLatMap = longLatMap;
        }

        public void setMarkersMap(SparseArray<HashMap<String, UkMarker>> markersMap) {
            this.markersMap = markersMap;
        }

        public DBHelper(MainActivity anActivity) {
            this.mainActivity = anActivity;
        }

        public SparseArray<LatLong> getLongLatMap() {
            return longLatMap;
        }

        public SparseArray<HashMap<String, UkMarker>> getMarkersMap() {
            return markersMap;
        }



        public SparseArray<String> getUlMap() {
            return ulMap;
        }

        public LatLong getCurrentlatLong() {
            return currentlatLong;
        }

        public String getMarkerIdToBeAdded() {
            return markerIdToBeAdded;
        }

        public String getOrgType() {
            return orgType;
        }

        public String getMyJSONString() {
            return myJSONString;
        }

        public MainActivity getMainActivity() {
            return mainActivity;
        }

        public static String getID() {
            return ID;
        }

        public static String getLat() {
            return Lat;
        }

        public static String getLong() {
            return Long;
        }


        public static String getMarkerId() {
            return markerId;
        }

        public String getLoginId() {
            return loginId;
        }

        public ArrayList<Bookmark> getBookmarksArray() {
            return bookmarksArray;

        }


        public TreeSet<String> getArea_name_array() {
            return area_name_array;
        }


        private String myJSONString;
        private MainActivity mainActivity;
        private static final String ID = "LatLongId";
        private static final String Lat= "Lat";
        private static final String Long = "Long";


        private static final String markerId = "MarkerId";

        public DBHelper(SplashScreenActivity splashScreenActivity) {
            this.splashScreenActivity = splashScreenActivity;
        }



        /**
         * Called after user first logs in to retirieve all user data from backend. Asynchronous task is ran upon method being called. In background thread, appends username/phone number to URL
         * php script is accessed by opening url connection.
         * script gets login entry containing the username and returns in JSON format
         * buffered reader appends json to String. A json array, userJsonArray, is created with the string containing the json array fed in as the argument
         * a further json array, userJsonArr, is created. This json array is nested inside userJsonArray. each array entry in userJsonArr
         * holds a table entry (e.g UserType, Login, Missing Family Member)
         * first try catch block, catches exception in url connection and bufferedReader reading output to string.
         * second and third try catch block catches json exception from creating json arrays
         * <p>
         * For loop iterates through each array nested inside userJsonArray. Switch statement checks on each value of i from the for loop
         * and handles the array data for each case:
         * case 0: Retrieves user login information. getString() methods called on json object to store username and loginID into strings
         * username added to array. array added to HashMap ulMap, with the key set to the unique loginID.
         * case 1: Retrieves user type information. userTypeID, UserType and loginID stored into strings.
         * currentUserType and loginID added to array. array added to HashMap userTypeMap, with the key set to the unique userTypeId.
         * case 2: if the currentUserType set in case 1 == 'Refugee', then a refugee object (currentRefugee) is created from the json array
         * and added to the hashMap, refMap
         * else if the currentUserType set in case 1 == 'AidWorker' then an aidWorker object (aidWorker) is created from the json
         * array.
         * case 3: if the currentUserType set in case 1 == 'Refugee', then a for loop iterates through the json array to find every Missing
         * Family member linked to currentRefugee (the user)
         * each family member is added to the hashMap, missingFamLinkedToCurrentRef, with the key set to the unique MissingPersonId.
         * else if the currentUserType set in case 1 == 'AidWorker' then a for loop iterates through the json array
         * to return each refugee object (currentRefugee) linked to the AidWorker. each refugee is added to the hashMap, refMap.
         * case 4: a for loop iterates through the json array to find every Missing
         * Family member. This has the constraint that the MissingPerson object must be linked to a Refugee object which is linked to the
         * AidWorker object representing the user. This is handled in the php script so only MissingPerson objects which satisfy this constraint
         * are added
         * each family member is added to the hashMap, missingPersonMap, with the key set to the unique MissingPersonId.
         * third try-catch block catches json exception from looping through the json arrays.
         *
         */
        public void getTables() {

            class GetTabs extends AsyncTask<String, Void, String> {
                private JSONArray userJsonArray;
                private JSONArray userJsonArr;


                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(String... params) {
                    URL url;
                    String uri = params[0];
                    String userUri = uri + getMainActivity().getPhoneNumberString();
                    BufferedReader bufferedReader;
                    try {
                        url = new URL(userUri);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        StringBuilder sb = new StringBuilder();
                        bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        String json;
                        while ((json = bufferedReader.readLine()) != null) {
                            sb.append(json + "\n");
                        }
                        myJSONString = sb.toString().trim();
                        try {
                            userJsonArray = new JSONArray(myJSONString);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            userJsonArr = userJsonArray.getJSONArray(0);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        return null;
                    }
                    try {
                        for (int i = 0; i < userJsonArr.length(); i++) {
                            JSONArray jsonArr = userJsonArr.getJSONArray(i);
                            int logId = 0;
                            BookmarkType bookmarkType = null;
                            if (jsonArr.length() != 0) {
                                JSONObject jsonObject = jsonArr.getJSONObject(0);
                                switch (i) {
                                    case 0:
                                        loginId = jsonObject.getString("LoginId");
                                        String phoneNumber = jsonObject.getString("PhoneNumber");
                                        logId = Integer.parseInt(loginId);
                                        ulMap.put(logId, phoneNumber);
                                        break;
                                    case 1:
                                        for (int b = 0; b < jsonArr.length(); b++) {
                                            JSONObject jsonObjective = jsonArr.getJSONObject(b);
                                            String markerID = jsonObjective.getString("MarkerId");
                                            String bookmarkIdString = jsonObjective.getString("BookmarkedId");
                                            Integer bookmarkedId = Integer.parseInt(bookmarkIdString);
                                            Integer markId = Integer.parseInt(markerID);
                                            String bookmarkTypeString = jsonObjective.getString("Type");
                                            switch (bookmarkTypeString){
                                                case "UK":
                                                    bookmarkType = BookmarkType.UK;
                                                    break;
                                                case "OVERSEAS":
                                                    bookmarkType = BookmarkType.OVERSEAS;
                                                    break;
                                                case "ONLINE":
                                                    bookmarkType = BookmarkType.ONLINE;
                                                    break;
                                            }
                                            int id = Integer.parseInt(markerID);
                                            Bookmark bookmark = new Bookmark(bookmarkedId, markId, logId, bookmarkType);
                                           bookmarksArray.add(bookmark);
                                        }
                                        break;
                                }
                            }
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return myJSONString;
                }
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    getMainActivity().init();
                    }
                }
            GetTabs gts = new GetTabs();
            gts.execute("http://ec2-34-207-107-248.compute-1.amazonaws.com/istandwithrefugees/get_all_tables.php?phone=");
        }

        public void setMainActivity(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        public void getMapMarkersAndLatLongs() {
            if (splashScreenActivity.getProgressDialog() != null) {
                splashScreenActivity.getProgressDialog().dismiss();
            }
            splashScreenActivity.setProgressDialog(new ProgressDialog(splashScreenActivity));
            splashScreenActivity.getProgressDialog().setMessage("Loading Organisations and Fundraisers. Please wait a moment");
            splashScreenActivity.getProgressDialog().show();
            splashScreenActivity.getProgressDialog().setCancelable(false);
            class GetMarkersAndLatLongs extends AsyncTask<String, Void, String> {

                private JSONArray userJsonArray;
                private JSONArray userJsonArr;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(String... params) {
                    URL url;
                    String uri = params[0];
                    BufferedReader bufferedReader;
                    logoUrls = new ArrayList<>();
                    lastModifiedList = new ArrayList<>();
                    try {
                        url = new URL(uri);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        StringBuilder sb = new StringBuilder();

                        bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        String json;
                        while ((json = bufferedReader.readLine()) != null) {
                            sb.append(json + "\n");
                        }
                        myJSONString = sb.toString().trim();

                        try {
                            userJsonArray = new JSONArray(myJSONString);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            userJsonArr = userJsonArray.getJSONArray(0);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        return null;
                    }
                    try {
                        for (int i = 0; i < userJsonArr.length(); i++) {
                            JSONArray jsonArr = userJsonArr.getJSONArray(i);
                            if (jsonArr.length() != 0) {
                                JSONObject jsonObject = jsonArr.getJSONObject(0);
                                switch (i) {

                                    case 0:
                                        for (int x = 0; x < jsonArr.length(); x++) {
                                            JSONObject jsonObj = jsonArr.getJSONObject(x);
                                            String markerId = jsonObj.getString("MarkerId");
                                            int id = Integer.parseInt(markerId);
                                            String title = jsonObj.getString("Title");
                                            String description = jsonObj.getString("Description");
                                            String website = jsonObj.getString("Website");
                                            String streetNo = jsonObj.getString("HouseNoOrBuildingName");
                                            String street = jsonObj.getString("Street");
                                            String otherAddress = jsonObj.getString("OtherAddress");
                                            String city = jsonObj.getString("CityOrTownOrVillage");
                                            String postcode = jsonObj.getString("Postcode");
                                            String region = jsonObj.getString("Region");
                                            String type = jsonObj.getString("Type");
                                            String typeOfAid = jsonObj.getString("TypeOfAid");
                                            String lastModified = jsonObj.getString("LastModified");
                                            String loginIdString = jsonObj.getString("LoginId");
                                            int loginId = Integer.parseInt(loginIdString);
                                            if (type.equals("Organisation")) {
                                                String logoURL = jsonObj.getString("logoURL");
                                                Charity aCharity = new Charity(title, description, website, streetNo, street,
                                                        otherAddress, city, postcode, region, typeOfAid, lastModified, logoURL, id, loginId);
                                                logoUrls.add(logoURL);
                                                lastModifiedList.add(lastModified);
                                                HashMap<String, UkMarker> hashMap = new HashMap<>();
                                                hashMap.put(type, aCharity);
                                                markersMap.put(id, hashMap);
                                            } else {
                                                String fundraiserDate = jsonObj.getString("FundraiserDate");
                                                String fundraiserTime = jsonObj.getString("FundraiserTime");
                                                String linkedtoCharityId = jsonObj.getString("linkedtoCharityId");
                                                int linkId = Integer.parseInt(linkedtoCharityId);
                                                HashMap h = markersMap.get(linkId);
                                                Charity charity = (Charity) h.get("Organisation");
                                                Fundraiser fundraiser = new Fundraiser(title, description, website, "23", street,
                                                        otherAddress, city, postcode, region, typeOfAid, lastModified, fundraiserDate,
                                                        fundraiserTime, charity, id, loginId);
                                                HashMap<String, UkMarker> hashMap = new HashMap<>();
                                                hashMap.put(type, fundraiser);
                                                markersMap.put(id, hashMap);
                                            }
                                        }
                                        break;

                                    case 1:
                                        for (int n = 0; n < jsonArr.length(); n++) {
                                            JSONObject jsonObjec = jsonArr.getJSONObject(n);
                                            String lat = jsonObjec.getString(Lat);
                                            String lon = jsonObjec.getString(Long);
                                            String markerID = jsonObjec.getString(markerId);
                                            String idString = jsonObjec.getString(ID);
                                            int id = Integer.parseInt(idString);
                                            float latitude = Float.parseFloat(lat);
                                            float longitude = Float.parseFloat(lon);
                                            ArrayList<String> tempArray = new ArrayList<>();
                                            String title;
                                            String desc;
                                            HashMap<String, UkMarker> hashMap = markersMap.get(id);
                                            if (hashMap.containsKey("Organisation")) {
                                                Charity aCharity = (Charity) hashMap.get("Organisation");
                                                title = aCharity.getTitle();
                                                desc = aCharity.getDescription();
                                            } else {
                                                Fundraiser fundraiser = (Fundraiser) hashMap.get("Fundraiser");
                                                title = fundraiser.getTitle() + "\n" + "For: " + fundraiser.getaCharity().getTitle();
                                                desc = fundraiser.getDescription();

                                            }
                                            LatLong latLong = new LatLong(latitude, longitude, title, desc, markerID);
                                            longLatMap.put(id, latLong);
                                        }
                                        break;
                                    case 2:
                                        for (int n = 0; n < jsonArr.length(); n++) {
                                            JSONObject jsonOb = jsonArr.getJSONObject(n);
                                            String markerId = jsonOb.getString("MarkerId");
                                            int id = Integer.parseInt(markerId);
                                            String title = jsonOb.getString("Title");
                                            String description = jsonOb.getString("Description");
                                            String website = jsonOb.getString("Website");
                                            String lastModified = jsonOb.getString("LastModified");
                                            String loginIdString = jsonOb.getString("LoginId");
                                            int loginId = Integer.parseInt(loginIdString);
                                            String logoURL = jsonOb.getString("logoUrl");
                                            logoUrlsOnline.add(logoURL);
                                            OnlineAid onlineAid = new OnlineAid(title, description, website, lastModified, logoURL, id, loginId);
                                            lastModifiedListOnline.add(lastModified);
                                            onlineAids.put(id, onlineAid);
                                        }

                                        break;
                                    case 3:
                                        ArrayList<String> franceRegions = new ArrayList<>();
                                        ArrayList<String> serbiaRegions = new ArrayList<>();
                                        ArrayList<String> bosniaRegions = new ArrayList<>();
                                        ArrayList<String> greeceRegions = new ArrayList<>();
                                        ArrayList<String> belgiumRegions = new ArrayList<>();
                                        ArrayList<String> italyRegions = new ArrayList<>();
                                        ArrayList<String> macedoniaRegions = new ArrayList<>();

                                        for (int x = 0; x < jsonArr.length(); x++) {
                                            JSONObject jsonObj = jsonArr.getJSONObject(x);
                                            String markerId = jsonObj.getString("MarkerId");
                                            int id = Integer.parseInt(markerId);
                                            String title = jsonObj.getString("Title");
                                            String description = jsonObj.getString("Description");
                                            String website = jsonObj.getString("Website");
                                            String city = jsonObj.getString("CityOrTownOrVillage");
                                            String country = jsonObj.getString("Country");
                                            String region = jsonObj.getString("Region");
                                            region.replace("\\n", "");
                                            String typeOfAid = jsonObj.getString("TypeOfAid");
                                            String lastModified = jsonObj.getString("LastModified");
                                            String loginIdString = jsonObj.getString("LoginId");
                                            int loginId = Integer.parseInt(loginIdString);
                                                String logoURL = jsonObj.getString("logoURL");
                                                EUMarker euMarker = new EUMarker(title, description, website, city, region, typeOfAid, lastModified, id, loginId, logoURL, country);
                                                if(region.length() <=1){
                                                    System.out.println("here");
                                                    region = "Not Specified";
                                                }
                                                switch (country){
                                                    case "France":
                                                        if (!franceRegions.contains(region)) {
                                                            franceRegions.add(region);
                                                        }
                                                        break;
                                                    case "Italy":
                                                        if (!italyRegions.contains(region)) {
                                                            italyRegions.add(region);
                                                        }
                                                        break;
                                                    case "Macedonia":
                                                        if (!macedoniaRegions.contains(region)) {
                                                            macedoniaRegions.add(region);
                                                        }
                                                        break;
                                                    case "Belgium":
                                                        if (!belgiumRegions.contains(region)) {
                                                            belgiumRegions.add(region);
                                                        }
                                                        break;
                                                    case "Greece":
                                                        if (!greeceRegions.contains(region)) {
                                                            greeceRegions.add(region);
                                                        }
                                                        break;
                                                    case "Serbia":
                                                        if (!serbiaRegions.contains(region)) {
                                                            serbiaRegions.add(region);
                                                        }
                                                        break;
                                                    case "Bosnia":
                                                        if (!bosniaRegions.contains(region)) {
                                                            bosniaRegions.add(region);
                                                        }
                                                        break;
                                                }
                                                logoUrlsEU.add(logoURL);
                                                lastModifiedListEU.add(lastModified);
                                                overseasMarkersMap.put(id, euMarker);
                                        }
                                        countryAndRegionsArray.put("Bosnia", bosniaRegions);
                                        countryAndRegionsArray.put("Serbia", serbiaRegions);
                                        countryAndRegionsArray.put("Greece", greeceRegions);
                                        countryAndRegionsArray.put("Belgium", belgiumRegions);
                                        countryAndRegionsArray.put("Macedonia", macedoniaRegions);
                                        countryAndRegionsArray.put("Italy", italyRegions);
                                        countryAndRegionsArray.put("France", franceRegions);
                                        System.out.println(countryAndRegionsArray + "OYYYYYYYYYYY");
                                        break;
                                    case 4:
                                        for (int n = 0; n < jsonArr.length(); n++) {
                                            JSONObject jsonObjec = jsonArr.getJSONObject(n);
                                            String lat = jsonObjec.getString(Lat);
                                            String lon = jsonObjec.getString(Long);
                                            String markerID = jsonObjec.getString(markerId);
                                            String idString = jsonObjec.getString(ID);
                                            int id = Integer.parseInt(idString);
                                            float latitude = Float.parseFloat(lat);
                                            float longitude = Float.parseFloat(lon);
                                            ArrayList<String> tempArray = new ArrayList<>();
                                            String title;
                                            String desc;
                                            EUMarker euMarker = overseasMarkersMap.get(id);
                                                title = euMarker.getTitle();
                                                desc = euMarker.getDescription();

                                            LatLong latLong = new LatLong(latitude, longitude, title, desc, markerID);
                                            longLatMapEU.put(id, latLong);
                                        }
                                        break;
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return myJSONString;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    sharedPreferences = splashScreenActivity.getSharedPreferences("showcaseTutorial", MODE_PRIVATE);

                    if (sharedPreferences.getBoolean("areAppImagesPreloaded", true)) {
                        preloadLogos();
                    } else if (sharedPreferences.getBoolean("areImagesPreloaded", true)) {
                        preloadUkLogos();
                    } else if (sharedPreferences.getBoolean("areOnlineImagesPreloaded", true)) {
                        preloadOnlineLogos();
                    } else if (sharedPreferences.getBoolean("areOverseasImagesPreloaded", true)) {
                        preloadOverseasLogos();
                        }
                    else {
                        splashScreenActivity.getProgressDialog().dismiss();
                        if (splashScreenActivity != null) {
                            splashScreenActivity.loadMainActivity();
                            splashScreenActivity = null;
                        }
                    }
                }
            }

            GetMarkersAndLatLongs getMarkersAndLatLongs = new GetMarkersAndLatLongs();
            getMarkersAndLatLongs.execute("http://ec2-34-207-107-248.compute-1.amazonaws.com/istandwithrefugees/get_markers_and_lat_longs.php");
        }

        public void preloadLogos() {
            splashScreenActivity.getProgressDialog().dismiss();
            final ProgressDialog progressDialog = new ProgressDialog(splashScreenActivity);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Loading App Data for the first time. Please wait a moment");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            int dimen = (int) splashScreenActivity.getResources().getDimensionPixelSize(R.dimen._30sdp);
            Glide.with(splashScreenActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/map_google_icon.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            dimen = (int) splashScreenActivity.getResources().getDimensionPixelSize(R.dimen._40sdp);
            Glide.with(splashScreenActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/ic_menu_my_calendar.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            Glide.with(splashScreenActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/time.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            Glide.with(splashScreenActivity)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/dropdown_arrow.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            dimen = (int) splashScreenActivity.getResources().getDimensionPixelSize(R.dimen._60sdp);
            Glide.with(splashScreenActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/hyperlink_icon.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            Glide.with(splashScreenActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/map_button.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            Glide.with(splashScreenActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/bookmarks_button.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            Glide.with(splashScreenActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/bookmarks_button_alt.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            Glide.with(splashScreenActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/copy.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            dimen = (int) splashScreenActivity.getResources().getDimensionPixelSize(R.dimen._80sdp);
            Glide.with(splashScreenActivity)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/logo.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            dimen = (int) splashScreenActivity.getResources().getDimensionPixelSize(R.dimen._100sdp);
            Glide.with(splashScreenActivity)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/ukhomebutton.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            Glide.with(splashScreenActivity)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/overseashomebutton.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            Glide.with(splashScreenActivity)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/onlinehomebutton.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            Glide.with(splashScreenActivity)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/homepersonalbutton.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            Glide.with(splashScreenActivity)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/searchbutton.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            Glide.with(splashScreenActivity)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/list_button.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            Glide.with(splashScreenActivity)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/add_org_button.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            Glide.with(splashScreenActivity)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/fundraiserbutton.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            Glide.with(splashScreenActivity)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/resources_button.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            Glide.with(splashScreenActivity)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/list_button_alt.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            dimen = (int) splashScreenActivity.getResources().getDimensionPixelSize(R.dimen._150sdp);
            Glide.with(splashScreenActivity)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/ins_log.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            dimen = (int) splashScreenActivity.getResources().getDimensionPixelSize(R.dimen._120sdp);
            Glide.with(splashScreenActivity)
                    .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/contributions_button.png")
                    .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                    .preload(dimen, dimen);
            SharedPreferences.Editor tutorialShowcasesEdit = sharedPreferences.edit();
            tutorialShowcasesEdit.putBoolean("areAppImagesPreloaded", false);
            tutorialShowcasesEdit.apply();
            progressDialog.dismiss();
                preloadUkLogos();
            }


        public void preloadUkLogos() {
            splashScreenActivity.getProgressDialog().dismiss();
            final ProgressDialog progressDialog = new ProgressDialog(splashScreenActivity);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Loading Uk Organisations for the first time. Please wait a moment");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMax(logoUrls.size());
            progressDialog.show();
            System.out.println("Yo");
            for (int i = 0; i < logoUrls.size(); i++) {
                int dimen;
                dimen = (int) splashScreenActivity.getResources().getDimensionPixelSize(R.dimen._50sdp);
                Glide.with(splashScreenActivity).load(logoUrls.get(i))
                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).signature(new ObjectKey(lastModifiedList.get(i))))
                        .listener(new RequestListener<Drawable>() {
                            int size = logoUrls.size() - 1;

                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                System.out.println(in + "oh-oh");
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                in = in + 1;
                                progressDialog.setProgress(in);
                                System.out.println(in);
                                if (in > logoUrls.size() - 20) {
                                    SharedPreferences.Editor tutorialShowcasesEdit = sharedPreferences.edit();
                                    tutorialShowcasesEdit.putBoolean("areImagesPreloaded", false);
                                    tutorialShowcasesEdit.apply();
                                    progressDialog.dismiss();
                                        preloadOnlineLogos();
                                }
                                return false;
                            }
                        })
                        .preload(dimen, dimen);
            }
        }

        public void preloadOnlineLogos(){
            splashScreenActivity.getProgressDialog().dismiss();
            final ProgressDialog progressDialog = new ProgressDialog(splashScreenActivity);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Loading Online Organisations for the first time. Please wait a moment");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMax(logoUrlsOnline.size());
            progressDialog.show();
            System.out.println("Yo");
            for (int i = 0; i < logoUrlsOnline.size(); i++) {
                int dimen;
                dimen = (int) splashScreenActivity.getResources().getDimensionPixelSize(R.dimen._50sdp);
                Glide.with(splashScreenActivity).load(logoUrlsOnline.get(i))
                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).signature(new ObjectKey(lastModifiedListOnline.get(i))))
                        .listener(new RequestListener<Drawable>() {

                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                System.out.println(in + "oh-oh");
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                in = in + 1;
                                progressDialog.setProgress(in);
                                if (in > logoUrlsOnline.size() - 2) {
                                    SharedPreferences.Editor tutorialShowcasesEdit = sharedPreferences.edit();
                                    tutorialShowcasesEdit.putBoolean("areOnlineImagesPreloaded", false);
                                    tutorialShowcasesEdit.apply();
                                    progressDialog.dismiss();
                                        preloadOverseasLogos();
                                    }
                                return false;
                            }
                        })
                        .preload(dimen, dimen);
                }
            }

        public void preloadOverseasLogos(){
            splashScreenActivity.getProgressDialog().dismiss();
            final ProgressDialog progressDialog = new ProgressDialog(splashScreenActivity);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Loading Overseas Organisations for the first time. Please wait a moment");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMax(logoUrlsEU.size());
            progressDialog.show();
            System.out.println("Yo");
            for (int i = 0; i < logoUrlsEU.size(); i++) {
                int dimen;
                dimen = (int) splashScreenActivity.getResources().getDimensionPixelSize(R.dimen._50sdp);
                Glide.with(splashScreenActivity).load(logoUrlsEU.get(i))
                        .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).signature(new ObjectKey(lastModifiedListEU.get(i))))
                        .listener(new RequestListener<Drawable>() {

                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                System.out.println(in + "oh-oh");
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                in = in + 1;
                                progressDialog.setProgress(in);
                                if (in > logoUrlsEU.size() - 20) {
                                    System.out.println("here");
                                    SharedPreferences.Editor tutorialShowcasesEdit = sharedPreferences.edit();
                                    tutorialShowcasesEdit.putBoolean("areOverseasImagesPreloaded", false);
                                    tutorialShowcasesEdit.apply();
                                    progressDialog.dismiss();
                                    if (splashScreenActivity != null) {
                                        System.out.println("NOOOOOOO");
                                        splashScreenActivity.loadMainActivity();
                                        splashScreenActivity = null;
                                    }
                                }
                                return false;
                            }
                        })
                        .preload(dimen, dimen);
            }
        }

        public void addBookmark(final int markerId, final BookmarkType bookmarkType, final android.support.v4.app.Fragment fragment) {
            class SetVoicemail extends AsyncTask<String, Void, String> {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(String... params) {

                    String uri = "http://ec2-34-207-107-248.compute-1.amazonaws.com/istandwithrefugees/add_bookmark.php";
                    String text = null;
                    try {
                        URL url = new URL(uri);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");
                        con.setDoOutput(true);
                        OutputStream os = con.getOutputStream();


                        String data = URLEncoder.encode("MarkerId", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(markerId), "UTF-8") + "&" +
                                URLEncoder.encode("Type", "UTF-8") + "=" + URLEncoder.encode(bookmarkType.toString(), "UTF-8") + "&" +
                                URLEncoder.encode("LoginId", "UTF-8") + "=" + URLEncoder.encode(loginId, "UTF-8");
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        bufferedWriter.write(data);
                        bufferedWriter.flush();
                        int statusCode = con.getResponseCode();
                        if (statusCode == 200) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            StringBuilder sb = new StringBuilder();
                            String line;

                            while ((line = reader.readLine()) != null)
                                sb.append(line).append("\n");
                            text = sb.toString();
                            bufferedWriter.close();
                            JSONObject responseJSON;
                            try {
                                responseJSON = new JSONObject(text);
                                String bookmarkedIdString = (responseJSON.getString("bookmarked_id"));
                                int bookmarkedId = Integer.parseInt(bookmarkedIdString);
                                Bookmark bookmark = new Bookmark(bookmarkedId, markerId, Integer.parseInt(loginId), bookmarkType);
                                System.out.println(bookmarksArray.size() + "here");
                                bookmarksArray.add(bookmark);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }



                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return text;
                }

                @Override
                protected void onPostExecute(String s) {
                    if(bookmarkType == BookmarkType.ONLINE) {
                        MainActivity.ListOnlineFragment fragment1 = (MainActivity.ListOnlineFragment) fragment;
                        fragment1.setResults();
                    }
                    else if(bookmarkType == BookmarkType.UK) {
                        MainActivity.ListMarkersFragment fragment1 = (MainActivity.ListMarkersFragment) fragment;
                        fragment1.setResults();
                    }
                    else{
                        //overseas
                    }
                    super.onPostExecute(s);

                }
            }
            SetVoicemail sV = new SetVoicemail();
            sV.execute();
        }

        private void addLatLong() {
            class SetVoicemail extends AsyncTask<String, Void, String> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(String... params) {
                    mainActivity.getProgressDialog().setMessage("Adding Map Marker");
                    String uri = "http://ec2-34-207-107-248.compute-1.amazonaws.com/istandwithrefugees/add_lat_long.php";
                    String text = null;
                    try {
                        URL url = new URL(uri);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");
                        con.setDoOutput(true);
                        OutputStream os = con.getOutputStream();

                        String data = URLEncoder.encode("LatLongId", "UTF-8") + "=" + URLEncoder.encode(markerIdToBeAdded, "UTF-8") + "&" +
                                URLEncoder.encode("Lat", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(currentlatLong.getLat()), "UTF-8") + "&" +
                                URLEncoder.encode("Long", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(currentlatLong.getLon()), "UTF-8") + "&" +
                                URLEncoder.encode("MarkerId", "UTF-8") + "=" + URLEncoder.encode(markerIdToBeAdded, "UTF-8");

                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        bufferedWriter.write(data);
                        bufferedWriter.flush();
                        int statusCode = con.getResponseCode();
                        if (statusCode == 200) {
                            System.out.println("YUP");
                            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            StringBuilder sb = new StringBuilder();
                            String line;

                            while ((line = reader.readLine()) != null)
                                sb.append(line).append("\n");

                            text = sb.toString();
                            bufferedWriter.close();
                        }

                        longLatMap.put(Integer.parseInt(markerIdToBeAdded), currentlatLong);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return text;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);

                    mainActivity.getProgressDialog().dismiss();
                    if (orgType == "Organisation") {
                        BookmarkType bookmarkType = BookmarkType.UK;
                        mainActivity.addLogo(bookmarkType);
                    }
                    else {
                        Toast.makeText(mainActivity, "Fundraiser Added", Toast.LENGTH_LONG).show();
                        MainActivity.HomeFragment frag = new MainActivity.HomeFragment();
                        mainActivity.loadFragment(frag);
                    }
                }
            }
            SetVoicemail sV = new SetVoicemail();
            sV.execute();
        }

        private void addCharity() {
            class SetVoicemail extends AsyncTask<String, Void, String> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(String... params) {
                    mainActivity.getProgressDialog().setMessage("Adding Organisation");
                    String uri = "http://ec2-34-207-107-248.compute-1.amazonaws.com/istandwithrefugees/add_charity.php";
                    String text = null;
                    try {
                        URL url = new URL(uri);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");
                        con.setDoOutput(true);
                        OutputStream os = con.getOutputStream();

                        String data = URLEncoder.encode("Title", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getCharityToBeAdded().getTitle(), "UTF-8") + "&" +
                                URLEncoder.encode("Description", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getCharityToBeAdded().getDescription(), "UTF-8") + "&" +
                                URLEncoder.encode("HouseNoOrBuildingName", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getCharityToBeAdded().getHouseNoOrBuldingName(), "UTF-8") + "&" +
                                URLEncoder.encode("Street", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getCharityToBeAdded().getStreet(), "UTF-8") + "&" +
                                URLEncoder.encode("OtherAddress", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getCharityToBeAdded().getOtherAddress(), "UTF-8") + "&" +
                                URLEncoder.encode("CityOrTownOrVillage", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getCharityToBeAdded().getCityOrTown(), "UTF-8") + "&" +
                                URLEncoder.encode("Postcode", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getCharityToBeAdded().getPostcode(), "UTF-8") + "&" +
                                URLEncoder.encode("Region", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getCharityToBeAdded().getRegion(), "UTF-8") + "&" +
                                URLEncoder.encode("MarkerId", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(mainActivity.getCharityToBeAdded().getMarkerId()), "UTF-8") + "&" +
                                URLEncoder.encode("Website", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getCharityToBeAdded().getWebsite(), "UTF-8") + "&" +
                                URLEncoder.encode("TypeOfAid", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getCharityToBeAdded().getTypeOfAid(), "UTF-8") + "&" +
                                URLEncoder.encode("LastModified", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getCharityToBeAdded().getLastModified(), "UTF-8") + "&" +
                                URLEncoder.encode("LoginId", "UTF-8") + "=" + URLEncoder.encode(loginId, "UTF-8");
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        bufferedWriter.write(data);
                        bufferedWriter.flush();
                        int statusCode = con.getResponseCode();
                        if (statusCode == 200) {

                            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            String result = "";
                            String line;

                            while ((line = reader.readLine()) != null)
                                result += line;
                            bufferedWriter.close();
                            JSONObject responseJSON;
                            try {
                                System.out.println(result);
                                responseJSON = new JSONObject(result);
                                markerIdToBeAdded = (responseJSON.getString("marker_id"));
                                mainActivity.getCharityToBeAdded().setMarkerId(Integer.parseInt(markerIdToBeAdded));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            orgType = "Organisation";
                            HashMap<String, UkMarker> hashMap = new HashMap<>();
                            hashMap.put("Organisation", mainActivity.getCharityToBeAdded());
                            markersMap.put(Integer.parseInt(markerIdToBeAdded), hashMap);
                        }

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return text;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    addLatLong();

                }
            }
            SetVoicemail sV = new SetVoicemail();
            sV.execute();
        }

        public void addOnlineAid() {
            class AddOnlineAid extends AsyncTask<String, Void, String> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(String... params) {
                    mainActivity.getProgressDialog().setMessage("Adding Online Aid");
                    String uri = "http://ec2-34-207-107-248.compute-1.amazonaws.com/istandwithrefugees/add_online.php";
                    String text = null;
                    try {
                        URL url = new URL(uri);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");
                        con.setDoOutput(true);
                        OutputStream os = con.getOutputStream();

                        String data = URLEncoder.encode("Title", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getOnlineAidToBeAdded().getTitle(), "UTF-8") + "&" +
                                URLEncoder.encode("Description", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getOnlineAidToBeAdded().getDescription(), "UTF-8") + "&" +
                                URLEncoder.encode("MarkerId", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(mainActivity.getOnlineAidToBeAdded().getMarkerId()), "UTF-8") + "&" +
                                URLEncoder.encode("Website", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getOnlineAidToBeAdded().getWebsite(), "UTF-8") + "&" +
                                URLEncoder.encode("LastModified", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getOnlineAidToBeAdded().getLastModified(), "UTF-8") + "&" +
                                URLEncoder.encode("LoginId", "UTF-8") + "=" + URLEncoder.encode(loginId, "UTF-8");
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        bufferedWriter.write(data);
                        bufferedWriter.flush();
                        int statusCode = con.getResponseCode();
                        if (statusCode == 200) {

                            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            String result = "";
                            String line;

                            while ((line = reader.readLine()) != null)
                                result += line;
                            bufferedWriter.close();
                            JSONObject responseJSON;
                            try {
                                System.out.println(result);
                                responseJSON = new JSONObject(result);
                                markerIdToBeAdded = (responseJSON.getString("marker_id"));
                                mainActivity.getOnlineAidToBeAdded().setMarkerId(Integer.parseInt(markerIdToBeAdded));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            onlineAids.put(Integer.parseInt(markerIdToBeAdded), mainActivity.getOnlineAidToBeAdded());
                        }

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return text;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    mainActivity.getProgressDialog().dismiss();
                    BookmarkType bookmarkType = BookmarkType.ONLINE;
                        mainActivity.addLogo(bookmarkType);

                }
            }
            AddOnlineAid addOnlineAid = new AddOnlineAid();
            addOnlineAid.execute();
        }

        private void addFundraiser() {
            class SetVoicemail extends AsyncTask<String, Void, String> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(String... params) {
                    mainActivity.getProgressDialog().setMessage("Adding Fundraiser");
                    String uri = "http://ec2-34-207-107-248.compute-1.amazonaws.com/istandwithrefugees/add_fundraiser.php";
                    String text = null;
                    try {
                        URL url = new URL(uri);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");
                        con.setDoOutput(true);
                        OutputStream os = con.getOutputStream();
                        String data = URLEncoder.encode("Title", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getFundraiserToBeAdded().getTitle(), "UTF-8") + "&" +
                                URLEncoder.encode("Description", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getFundraiserToBeAdded().getDescription(), "UTF-8") + "&" +
                                URLEncoder.encode("HouseNoOrBuildingName", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getFundraiserToBeAdded().getHouseNoOrBuldingName(), "UTF-8") + "&" +
                                URLEncoder.encode("Street", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getFundraiserToBeAdded().getStreet(), "UTF-8") + "&" +
                                URLEncoder.encode("OtherAddress", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getFundraiserToBeAdded().getOtherAddress(), "UTF-8") + "&" +
                                URLEncoder.encode("CityOrTownOrVillage", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getFundraiserToBeAdded().getCityOrTown(), "UTF-8") + "&" +
                                URLEncoder.encode("Postcode", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getFundraiserToBeAdded().getPostcode(), "UTF-8") + "&" +
                                URLEncoder.encode("Region", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getFundraiserToBeAdded().getRegion(), "UTF-8") + "&" +
                                URLEncoder.encode("FundraiserDate", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getFundraiserToBeAdded().getDate(), "UTF-8") + "&" +
                                URLEncoder.encode("LinkedToCharityId", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(mainActivity.getFundraiserToBeAdded().getaCharity().getMarkerId()), "UTF-8") + "&" +
                                URLEncoder.encode("FundraiserTime", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getFundraiserToBeAdded().getTime(), "UTF-8") + "&" +
                                URLEncoder.encode("MarkerId", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(mainActivity.getFundraiserToBeAdded().getMarkerId()), "UTF-8") + "&" +
                                URLEncoder.encode("LoginId", "UTF-8") + "=" + URLEncoder.encode(loginId, "UTF-8");
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        bufferedWriter.write(data);
                        bufferedWriter.flush();
                        int statusCode = con.getResponseCode();
                        if (statusCode == 200) {

                            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            String result = "";
                            String line;

                            while ((line = reader.readLine()) != null)
                                result += line;
                            bufferedWriter.close();
                            JSONObject responseJSON;
                            try {
                                responseJSON = new JSONObject(result);
                                markerIdToBeAdded = (responseJSON.getString("marker_id"));
                                mainActivity.getFundraiserToBeAdded().setMarkerId(Integer.parseInt(markerIdToBeAdded));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            System.out.println(result + "yo");
                            orgType = "Fundraiser";
                            HashMap<String, UkMarker> hashMap = new HashMap<>();
                            hashMap.put("Fundraiser", mainActivity.getFundraiserToBeAdded());
                            markersMap.put(Integer.parseInt(markerIdToBeAdded), hashMap);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return text;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    addLatLong();

                }
            }
            SetVoicemail sV = new SetVoicemail();
            sV.execute();
        }

        public void addAddressFundraiser() {
            class SetVoicemail extends AsyncTask<String, Void, Boolean> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Boolean doInBackground(String... params) {
                    Boolean result = true;
                    String text = null;
                    String streetNo = mainActivity.getFundraiserToBeAdded().getHouseNoOrBuldingName();
                    String streetName = mainActivity.getFundraiserToBeAdded().getStreet();
                    String postcode = mainActivity.getFundraiserToBeAdded().getPostcode();
                    String key = "AIzaSyDTLAdUgKzmoLYpgxgEwW5nIDqEfbkZdV0";
                    String uri = "https://maps.googleapis.com/maps/api/geocode/json?address=" + streetNo + "+" + streetName
                            + "+" + postcode + "&components=country:GB|postal_code:" + postcode + "&key=" + key;
                    uri = uri.replace(" ", "%20");
                    try {
                        URL url = new URL(uri);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        int statusCode = con.getResponseCode();
                        if (statusCode == 200) {

                            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            StringBuilder sb = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null)
                                sb.append(line).append("\n");
                            text = sb.toString().trim();
                        }
                        JSONArray userJsonArray = null;
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(text);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            userJsonArray = jsonObject.getJSONArray("results");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(userJsonArray.length() == 0){
                            //street and postcode
                            uri = "https://maps.googleapis.com/maps/api/geocode/json?address=" + streetName
                                    + "+" + postcode + "&components=country:GB|postal_code:" + postcode + "&key=" + key;
                            uri = uri.replace(" ", "%20");
                            url = new URL(uri);
                            con = (HttpURLConnection) url.openConnection();
                            statusCode = con.getResponseCode();
                            if (statusCode == 200) {
                                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                StringBuilder sb = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null)
                                    sb.append(line).append("\n");
                                text = sb.toString().trim();
                            }
                            userJsonArray = null;
                            jsonObject = null;
                            try {
                                jsonObject = new JSONObject(text);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                userJsonArray = jsonObject.getJSONArray("results");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(userJsonArray.length() == 0){
                                //just postcode
                                uri = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                                        postcode + "&components=country:GB|postal_code:" + postcode + "&key=" + key;
                                uri = uri.replace(" ", "%20");
                                url = new URL(uri);
                                con = (HttpURLConnection) url.openConnection();
                                statusCode = con.getResponseCode();
                                if (statusCode == 200) {

                                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                    StringBuilder sb = new StringBuilder();
                                    String line;
                                    while ((line = reader.readLine()) != null)
                                        sb.append(line).append("\n");
                                    text = sb.toString().trim();
                                }
                                userJsonArray = null;
                                jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(text);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    userJsonArray = jsonObject.getJSONArray("results");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if(userJsonArray.length() == 0) {
                                    result = false;
                                }
                                else{
                                    JSONObject userJsonObject = null;
                                    try {
                                        userJsonObject = userJsonArray.getJSONObject(0);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    JSONObject jsonObjective = null;
                                    try {
                                        jsonObjective = userJsonObject.getJSONObject("geometry");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    JSONObject jsonObjectives = null;
                                    try {
                                        jsonObjectives = jsonObjective.getJSONObject("location");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    String currentLat = null;
                                    try {
                                        currentLat = jsonObjectives.getString("lat");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    String currentLong = null;
                                    try {
                                        currentLong = jsonObjectives.getString("lng");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    float latitude = Float.parseFloat(currentLat);
                                    float longitude = Float.parseFloat(currentLong);
                                    currentlatLong = new LatLong(latitude, longitude, mainActivity.getFundraiserToBeAdded().getTitle(),
                                            mainActivity.getFundraiserToBeAdded().getDescription(), "0");
                                }
                            }
                            else {
                                uri = "https://maps.googleapis.com/maps/api/geocode/json?address=" + streetName
                                        + "+" + postcode + "&components=country:GB|postal_code:" + postcode + "&key=" + key;
                                uri = uri.replace(" ", "%20");
                                url = new URL(uri);
                                con = (HttpURLConnection) url.openConnection();
                                statusCode = con.getResponseCode();
                                if (statusCode == 200) {
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                    StringBuilder sb = new StringBuilder();
                                    String line;
                                    while ((line = reader.readLine()) != null)
                                        sb.append(line).append("\n");
                                    text = sb.toString().trim();
                                }
                                userJsonArray = null;
                                jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(text);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    userJsonArray = jsonObject.getJSONArray("results");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        else {
                            JSONObject userJsonObject = null;
                            try {
                                userJsonObject = userJsonArray.getJSONObject(0);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JSONObject jsonObjective = null;
                            try {
                                jsonObjective = userJsonObject.getJSONObject("geometry");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JSONObject jsonObjectives = null;
                            try {
                                jsonObjectives = jsonObjective.getJSONObject("location");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String currentLat = null;
                            try {
                                currentLat = jsonObjectives.getString("lat");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String currentLong = null;
                            try {
                                currentLong = jsonObjectives.getString("lng");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            float latitude = Float.parseFloat(currentLat);
                            float longitude = Float.parseFloat(currentLong);
                            currentlatLong = new LatLong(latitude, longitude, mainActivity.getFundraiserToBeAdded().getTitle(),
                                    mainActivity.getFundraiserToBeAdded().getDescription(), "0");
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(Boolean b) {
                    super.onPostExecute(b);
                    mainActivity.getProgressDialog().dismiss();
                    if (!b){
                        Toast.makeText(mainActivity, "Please enter a valid Postcode", Toast.LENGTH_LONG).show();
                    }
                    else {
                        addFundraiser();
                    }
                }
            }
            SetVoicemail sV = new SetVoicemail();
            sV.execute();
        }

        public void addAddressOrg() {
            class SetVoicemail extends AsyncTask<String, Void, Boolean> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Boolean doInBackground(String... params) {
                    Boolean result = true;
                    String text = null;
                    String streetNo = mainActivity.getCharityToBeAdded().getHouseNoOrBuldingName();
                    String streetName = mainActivity.getCharityToBeAdded().getStreet();
                    String postcode = mainActivity.getCharityToBeAdded().getPostcode();
                    String key = "AIzaSyDTLAdUgKzmoLYpgxgEwW5nIDqEfbkZdV0";
                    String uri = "https://maps.googleapis.com/maps/api/geocode/json?address=" + streetNo + "+" + streetName
                            + "+" + postcode + "&components=country:GB|postal_code:" + postcode + "&key=" + key;
                    uri = uri.replace(" ", "%20");
                    System.out.print(uri);

                    try {
                        URL url = new URL(uri);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        int statusCode = con.getResponseCode();
                        if (statusCode == 200) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            StringBuilder sb = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null)
                                sb.append(line).append("\n");
                            text = sb.toString().trim();
                        }
                        JSONArray userJsonArray = null;
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(text);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            userJsonArray = jsonObject.getJSONArray("results");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(userJsonArray.length() == 0){
                            //street and postcode
                            uri = "https://maps.googleapis.com/maps/api/geocode/json?address=" + streetName
                                    + "+" + postcode + "&components=country:GB|postal_code:" + postcode + "&key=" + key;
                            uri = uri.replace(" ", "%20");
                            url = new URL(uri);
                            con = (HttpURLConnection) url.openConnection();
                            statusCode = con.getResponseCode();
                            if (statusCode == 200) {
                                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                StringBuilder sb = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null)
                                    sb.append(line).append("\n");
                                text = sb.toString().trim();
                            }
                            userJsonArray = null;
                            jsonObject = null;
                            try {
                                jsonObject = new JSONObject(text);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                userJsonArray = jsonObject.getJSONArray("results");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if(userJsonArray.length() == 0){
                                //just postcode
                                uri = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                                        postcode + "&components=country:GB|postal_code:" + postcode + "&key=" + key;
                                uri = uri.replace(" ", "%20");
                                url = new URL(uri);
                                con = (HttpURLConnection) url.openConnection();
                                statusCode = con.getResponseCode();
                                if (statusCode == 200) {

                                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                    StringBuilder sb = new StringBuilder();
                                    String line;
                                    while ((line = reader.readLine()) != null)
                                        sb.append(line).append("\n");
                                    text = sb.toString().trim();
                                }
                                userJsonArray = null;
                                jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(text);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    userJsonArray = jsonObject.getJSONArray("results");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if(userJsonArray.length() == 0) {
                                    result = false;
                                }
                                else{
                                    JSONObject userJsonObject = null;
                                    try {
                                        userJsonObject = userJsonArray.getJSONObject(0);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    JSONObject jsonObjective = null;
                                    try {
                                        jsonObjective = userJsonObject.getJSONObject("geometry");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    JSONObject jsonObjectives = null;
                                    try {
                                        jsonObjectives = jsonObjective.getJSONObject("location");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    String currentLat = null;
                                    try {
                                        currentLat = jsonObjectives.getString("lat");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    String currentLong = null;
                                    try {
                                        currentLong = jsonObjectives.getString("lng");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    float latitude = Float.parseFloat(currentLat);
                                    float longitude = Float.parseFloat(currentLong);
                                    currentlatLong = new LatLong(latitude, longitude, mainActivity.getCharityToBeAdded().getTitle(),
                                            mainActivity.getCharityToBeAdded().getDescription(), "0");
                                }
                            }
                            else {
                                uri = "https://maps.googleapis.com/maps/api/geocode/json?address=" + streetName
                                        + "+" + postcode + "&components=country:GB|postal_code:" + postcode + "&key=" + key;
                                uri = uri.replace(" ", "%20");
                                url = new URL(uri);
                                con = (HttpURLConnection) url.openConnection();
                                statusCode = con.getResponseCode();
                                if (statusCode == 200) {
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                    StringBuilder sb = new StringBuilder();
                                    String line;
                                    while ((line = reader.readLine()) != null)
                                        sb.append(line).append("\n");
                                    text = sb.toString().trim();
                                }
                                userJsonArray = null;
                                jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(text);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    userJsonArray = jsonObject.getJSONArray("results");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        else {
                            JSONObject userJsonObject = null;
                            try {
                                userJsonObject = userJsonArray.getJSONObject(0);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JSONObject jsonObjective = null;
                            try {
                                jsonObjective = userJsonObject.getJSONObject("geometry");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JSONObject jsonObjectives = null;
                            try {
                                jsonObjectives = jsonObjective.getJSONObject("location");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String currentLat = null;
                            try {
                                currentLat = jsonObjectives.getString("lat");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String currentLong = null;
                            try {
                                currentLong = jsonObjectives.getString("lng");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            float latitude = Float.parseFloat(currentLat);
                            float longitude = Float.parseFloat(currentLong);
                            currentlatLong = new LatLong(latitude, longitude, mainActivity.getCharityToBeAdded().getTitle(),
                                    mainActivity.getCharityToBeAdded().getDescription(), "0");
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(Boolean b) {
                    super.onPostExecute(b);
                    mainActivity.getProgressDialog().dismiss();
                    if (!b){
                        Toast.makeText(mainActivity, "Please enter a valid Postcode", Toast.LENGTH_LONG).show();
                    }
                    else {
                        addCharity();
                    }
                }
            }
            SetVoicemail sV = new SetVoicemail();
            sV.execute();
        }

        public void addAddressOrgEU() {
            class SetVoicemail extends AsyncTask<String, Void, Boolean> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected Boolean doInBackground(String... params) {
                    Boolean result = true;
                    String text = null;
                    String cityOrTown = mainActivity.getEUMarkerToBeAdded().getCityOrTown();
                    String country = mainActivity.getEUMarkerToBeAdded().getCityOrTown();
                    String countrycode = "";
                    String region = "";

                    switch(country){
                        case "France":
                            countrycode = "FR";
                            break;
                        case "Italy":
                            countrycode = "IT";
                            break;
                        case "Macedonia":
                            countrycode = "MK";
                            break;
                        case "Belgium":
                            countrycode = "BE";
                            break;
                        case "Greece":
                            countrycode = "GR";
                            break;
                        case "Serbia":
                            countrycode = "RS";
                            break;
                        case "Bosnia":
                            countrycode = "BA";
                            break;
                    }

                    String key = "AIzaSyDTLAdUgKzmoLYpgxgEwW5nIDqEfbkZdV0";
                    String uri = "https://maps.googleapis.com/maps/api/geocode/json?address=" + cityOrTown +
                            "&components=country:" + countrycode + "&key=" + key;
                    uri = uri.replace(" ", "%20");

                    try {
                        URL url = new URL(uri);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        int statusCode = con.getResponseCode();
                        if (statusCode == 200) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            StringBuilder sb = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null)
                                sb.append(line).append("\n");
                            text = sb.toString().trim();
                        }
                        JSONArray userJsonArray = null;
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(text);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            result = false;
                        }
                        try {
                            userJsonArray = jsonObject.getJSONArray("results");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            result = false;
                        }
                        JSONObject userJsonObject = null;
                                    try {
                                        userJsonObject = userJsonArray.getJSONObject(0);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        result = false;
                                    }

                        JSONArray addressComponentsArray = null;
                        try {
                            addressComponentsArray = userJsonObject.getJSONArray("address_components");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            result = false;
                        }

                        for (int i = 0; i < addressComponentsArray.length(); i++) {
                            try {
                                JSONObject anObject = addressComponentsArray.getJSONObject(i);
                                JSONArray regionArray = anObject.getJSONArray("types");
                                System.out.println(regionArray.getString(0)  + "blah");
                                if (regionArray.getString(0).contains("administrative_area")){
                                    region = anObject.getString("long_name");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                result = false;
                            }
                            System.out.println(region  + "blah");

                        }

                                JSONObject jsonObjective = null;
                                    try {
                                        jsonObjective = userJsonObject.getJSONObject("geometry");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        result = false;
                                    }
                                    JSONObject jsonObjectives = null;
                                    try {
                                        jsonObjectives = jsonObjective.getJSONObject("location");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        result = false;
                                    }
                                    String currentLat = null;
                                    try {
                                        currentLat = jsonObjectives.getString("lat");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        result = false;
                                    }
                                    String currentLong = null;
                                    try {
                                        currentLong = jsonObjectives.getString("lng");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        result = false;
                                    }
                                    float latitude = Float.parseFloat(currentLat);
                                    float longitude = Float.parseFloat(currentLong);
                                    currentlatLong = new LatLong(latitude, longitude, mainActivity.getEUMarkerToBeAdded().getTitle(),
                                            mainActivity.getEUMarkerToBeAdded().getDescription(), "0");
                                    if(region == "Lesbos Prefecture" || region == "Lesbos"){
                                        region = "Lesvos";
                                    }
                                    mainActivity.getEUMarkerToBeAdded().setRegion(region);
                                }
                                catch (MalformedURLException e) {
                        e.printStackTrace();
                                    result = false;
                                } catch (IOException e) {
                        e.printStackTrace();
                        result = false;
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(Boolean b) {
                    super.onPostExecute(b);
                    mainActivity.getProgressDialog().dismiss();
                    if (!b){
                        Toast.makeText(mainActivity, "Please enter a valid Address", Toast.LENGTH_LONG).show();
                    }
                    else {
                        addEUMarker();
                    }
                }
            }
            SetVoicemail sV = new SetVoicemail();
            sV.execute();
        }


        public void removeBookmark(final int bookmarkId) {
            class SetVoicemail extends AsyncTask<String, Void, String> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(String... params) {

                    String uri = "http://ec2-34-207-107-248.compute-1.amazonaws.com/istandwithrefugees/rem_bookmark.php";
                    String text = null;
                    try {
                        URL url = new URL(uri);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");
                        con.setDoOutput(true);
                        OutputStream os = con.getOutputStream();


                        String data = URLEncoder.encode("BookmarkedId", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(bookmarkId), "UTF-8");
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        bufferedWriter.write(data);
                        bufferedWriter.flush();
                        int statusCode = con.getResponseCode();
                        if (statusCode == 200) {

                            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            StringBuilder sb = new StringBuilder();
                            String line;

                            while ((line = reader.readLine()) != null)
                                sb.append(line).append("\n");

                            text = sb.toString();
                            bufferedWriter.close();
                        }


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return text;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);

                }
            }
            SetVoicemail sV = new SetVoicemail();
            sV.execute();
        }

        private void addEUMarker() {
            class SetVoicemail extends AsyncTask<String, Void, String> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(String... params) {
                    mainActivity.getProgressDialog().setMessage("Adding Organisation");
                    String uri = "http://ec2-34-207-107-248.compute-1.amazonaws.com/istandwithrefugees/add_overseas.php";
                    String text = null;
                    try {
                        URL url = new URL(uri);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");
                        con.setDoOutput(true);
                        OutputStream os = con.getOutputStream();

                        String data = URLEncoder.encode("Title", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getEUMarkerToBeAdded().getTitle(), "UTF-8") + "&" +
                                URLEncoder.encode("Description", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getEUMarkerToBeAdded().getDescription(), "UTF-8") + "&" +
                                URLEncoder.encode("Country", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getEUMarkerToBeAdded().getCountry(), "UTF-8") + "&" +
                                URLEncoder.encode("CityOrTownOrVillage", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getEUMarkerToBeAdded().getCityOrTown(), "UTF-8") + "&" +
                                URLEncoder.encode("Region", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getEUMarkerToBeAdded().getRegion(), "UTF-8") + "&" +
                                URLEncoder.encode("MarkerId", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(mainActivity.getEUMarkerToBeAdded().getMarkerId()), "UTF-8") + "&" +
                                URLEncoder.encode("Website", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getEUMarkerToBeAdded().getWebsite(), "UTF-8") + "&" +
                                URLEncoder.encode("TypeOfAid", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getEUMarkerToBeAdded().getTypeOfAid(), "UTF-8") + "&" +
                                URLEncoder.encode("LastModified", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getEUMarkerToBeAdded().getLastModified(), "UTF-8") + "&" +
                                URLEncoder.encode("LoginId", "UTF-8") + "=" + URLEncoder.encode(loginId, "UTF-8");
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        bufferedWriter.write(data);
                        bufferedWriter.flush();
                        int statusCode = con.getResponseCode();
                        if (statusCode == 200) {

                            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            String result = "";
                            String line;

                            while ((line = reader.readLine()) != null)
                                result += line;
                            bufferedWriter.close();
                            JSONObject responseJSON;
                            try {
                                System.out.println(result);
                                responseJSON = new JSONObject(result);
                                markerIdToBeAdded = (responseJSON.getString("marker_id"));
                                mainActivity.getEUMarkerToBeAdded().setMarkerId(Integer.parseInt(markerIdToBeAdded));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            overseasMarkersMap.put(Integer.parseInt(markerIdToBeAdded), mainActivity.getEUMarkerToBeAdded());
                        }

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return text;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    addLatLongEU();

                }
            }
            SetVoicemail sV = new SetVoicemail();
            sV.execute();
        }

        private void addLatLongEU() {
            class SetVoicemail extends AsyncTask<String, Void, String> {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected String doInBackground(String... params) {
                    mainActivity.getProgressDialog().setMessage("Adding Map Marker");
                    String uri = "http://ec2-34-207-107-248.compute-1.amazonaws.com/istandwithrefugees/add_lat_long_eu.php";
                    String text = null;
                    try {
                        URL url = new URL(uri);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");
                        con.setDoOutput(true);
                        OutputStream os = con.getOutputStream();

                        String data = URLEncoder.encode("LatLongId", "UTF-8") + "=" + URLEncoder.encode(markerIdToBeAdded, "UTF-8") + "&" +
                                URLEncoder.encode("Lat", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(currentlatLong.getLat()), "UTF-8") + "&" +
                                URLEncoder.encode("Long", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(currentlatLong.getLon()), "UTF-8") + "&" +
                                URLEncoder.encode("MarkerId", "UTF-8") + "=" + URLEncoder.encode(markerIdToBeAdded, "UTF-8");

                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        bufferedWriter.write(data);
                        bufferedWriter.flush();
                        int statusCode = con.getResponseCode();
                        if (statusCode == 200) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            StringBuilder sb = new StringBuilder();
                            String line;

                            while ((line = reader.readLine()) != null)
                                sb.append(line).append("\n");

                            text = sb.toString();
                            bufferedWriter.close();
                        }

                        longLatMapEU.put(Integer.parseInt(markerIdToBeAdded), currentlatLong);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return text;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    mainActivity.getProgressDialog().dismiss();
                        BookmarkType bookmarkType = BookmarkType.OVERSEAS;
                        mainActivity.addLogo(bookmarkType);

                }
            }
            SetVoicemail sV = new SetVoicemail();
            sV.execute();
        }
    }
