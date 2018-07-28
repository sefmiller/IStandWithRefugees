    package com.gb.istandwithrefugeesapp.Model;

    import android.app.ProgressDialog;
    import android.graphics.drawable.Drawable;
    import android.os.AsyncTask;
    import android.os.Parcel;
    import android.os.Parcelable;
    import android.support.annotation.Nullable;
    import android.util.Log;
    import android.util.SparseArray;
    import android.widget.Toast;

    import com.bumptech.glide.Glide;
    import com.bumptech.glide.load.DataSource;
    import com.bumptech.glide.load.engine.DiskCacheStrategy;
    import com.bumptech.glide.load.engine.GlideException;
    import com.bumptech.glide.request.RequestListener;
    import com.bumptech.glide.request.RequestOptions;
    import com.bumptech.glide.request.target.Target;
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
    import java.util.List;
    import java.util.TreeSet;

    /**
     * Created by hp on 03/09/2017.
     */

    public class DBHelper {
        private SparseArray<LatLong> longLatMap = new SparseArray<>();
        private SparseArray <HashMap<String, UkMarker>> markersMap = new SparseArray <>();
        private SparseArray<UkMarker> overseasMap = new SparseArray<>();
        private SparseArray<UkMarker> onlineMap = new SparseArray<>();
        int in = 0;
        private TreeSet<String> area_name_array = new TreeSet<>();
        private List<Integer> bookmarksArray = new ArrayList<>();
        private List<Integer> bookmarksOverseasArray = new ArrayList<>();
        private List<Integer> bookmarksOnlineArray = new ArrayList<>();
        private SplashScreenActivity splashScreenActivity;
        private String loginId;
        private SparseArray<String> ulMap = new SparseArray<>();
        private LatLong currentlatLong;
        private String markerIdToBeAdded;
        private String orgType;
        private ArrayList<String> logoUrls = new ArrayList<>();

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

        public SparseArray<UkMarker> getOverseasMap() {
            return overseasMap;
        }

        public SparseArray<UkMarker> getOnlineMap() {
            return onlineMap;
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

        public List<Integer> getBookmarksArray() {
            return bookmarksArray;
        }


        public TreeSet<String> getArea_name_array() {
            return area_name_array;
        }

        public List<Integer> getBookmarksOverseasArray() {
            return bookmarksOverseasArray;
        }

        public List<Integer> getBookmarksOnlineArray() {
            return bookmarksOnlineArray;
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
                            if (jsonArr.length() != 0) {
                                JSONObject jsonObject = jsonArr.getJSONObject(0);
                                switch (i) {
                                    case 0:
                                        loginId = jsonObject.getString("LoginId");
                                        String phoneNumber = jsonObject.getString("PhoneNumber");
                                        int logId = Integer.parseInt(loginId);
                                        ulMap.put(logId, phoneNumber);
                                        break;
                                    case 1:
                                        for (int b = 0; b < jsonArr.length(); b++) {
                                            JSONObject jsonObjective = jsonArr.getJSONObject(b);
                                            String markerID = jsonObjective.getString("MarkerId");
                                            int id = Integer.parseInt(markerID);
                                           bookmarksArray.add(id);
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
                    Glide.get(splashScreenActivity).clearDiskCache();
                    logoUrls = new ArrayList<>();
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
                                            String loginIdString = jsonObj.getString("LoginId");
                                            int loginId = Integer.parseInt(loginIdString);
                                            if (type.equals("Organisation")) {
                                                String logoURL = jsonObj.getString("logoURL");
                                                Charity aCharity = new Charity(title, description, website, streetNo, street,
                                                        otherAddress, city, postcode, region, logoURL, id, loginId);
                                                logoUrls.add(logoURL);
                                                HashMap<String, UkMarker> hashMap = new HashMap<>();
                                                hashMap.put(type, aCharity);
                                                markersMap.put(id, hashMap);
                                            } else {
                                                String fundraiserDate = jsonObj.getString("FundraiserDate");
                                                String fundraiserTime = jsonObj.getString("FundraiserTime");
                                                String linkedtoCharityId = jsonObj.getString("linkedtoCharityId");
                                                int linkId = Integer.parseInt(linkedtoCharityId);
                                                HashMap h = markersMap.get(linkId);
                                                System.out.println();
                                                Charity charity = (Charity) h.get("Organisation");
                                                Fundraiser fundraiser = new Fundraiser(title, description, website, "23", street,
                                                        otherAddress, city, postcode, region, fundraiserDate,
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
                    Glide.get(splashScreenActivity).clearMemory();

                    int dimen = (int) splashScreenActivity.getResources().getDimensionPixelSize(R.dimen._30sdp);
                    Glide.with(splashScreenActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/map_google_icon.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);
                    dimen = (int) splashScreenActivity.getResources().getDimensionPixelSize(R.dimen._40sdp);
                    Glide.with(splashScreenActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/ic_menu_my_calendar.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);
                    Glide.with(splashScreenActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/time.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);
                    Glide.with(splashScreenActivity)
                            .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/dropdown_arrow.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);
                    dimen = (int)splashScreenActivity.getResources().getDimensionPixelSize(R.dimen._60sdp);
                    Glide.with(splashScreenActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/hyperlink_icon.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);
                    Glide.with(splashScreenActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/map_button.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);
                    Glide.with(splashScreenActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/bookmarks_button.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);
                    Glide.with(splashScreenActivity).load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/bookmarks_button_alt.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);
                    dimen = (int) splashScreenActivity.getResources().getDimensionPixelSize(R.dimen._80sdp);
                    Glide.with(splashScreenActivity)
                            .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/logo.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);
                    dimen = (int)splashScreenActivity.getResources().getDimensionPixelSize(R.dimen._100sdp);
                    Glide.with(splashScreenActivity)
                            .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/ukhomebutton.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);
                    Glide.with(splashScreenActivity)
                            .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/overseashomebutton.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);
                    Glide.with(splashScreenActivity)
                            .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/onlinehomebutton.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);
                    Glide.with(splashScreenActivity)
                            .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/homepersonalbutton.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);
                    Glide.with(splashScreenActivity)
                            .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/searchbutton.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);;
                    Glide.with(splashScreenActivity)
                            .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/list_button.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);
                    Glide.with(splashScreenActivity)
                            .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/add_org_button.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);
                    Glide.with(splashScreenActivity)
                            .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/fundraiserbutton.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);
                    Glide.with(splashScreenActivity)
                            .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/resources_button.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);
                    Glide.with(splashScreenActivity)
                            .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/list_button_alt.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);
                    dimen = (int)splashScreenActivity.getResources().getDimensionPixelSize(R.dimen._150sdp);
                    Glide.with(splashScreenActivity)
                            .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/ins_log.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);
                    dimen = (int) splashScreenActivity.getResources().getDimensionPixelSize(R.dimen._120sdp);
                    Glide.with(splashScreenActivity)
                            .load("https://s3.amazonaws.com/istandwithrefugees-userfiles-mobilehub-1734667399/public/contributions_button.png")
                            .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                            .preload(dimen, dimen);

                    for (int i = 0; i < logoUrls.size(); i++) {
                        dimen = (int) splashScreenActivity.getResources().getDimensionPixelSize(R.dimen._50sdp);
                        Glide.with(splashScreenActivity).load(logoUrls.get(i))
                                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                                .listener(new RequestListener<Drawable>() {
                                    int size = logoUrls.size() - 1;

                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        in = in + 1;
                                        System.out.println(in);
                                        if (in == logoUrls.size() - 10) {
                                            splashScreenActivity.getProgressDialog().dismiss();
                                            splashScreenActivity.loadMainActivity();
                                            splashScreenActivity = null;
                                        }
                                        return false;
                                    }
                                })
                                .preload(dimen, dimen);
                    }
                }
            }
                    GetMarkersAndLatLongs getMarkersAndLatLongs = new GetMarkersAndLatLongs();
                    getMarkersAndLatLongs.execute("http://ec2-34-207-107-248.compute-1.amazonaws.com/istandwithrefugees/get_markers_and_lat_longs.php");
        }

        public void addBookmark(final int markerId) {
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
                        mainActivity.addLogo();
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
                        URLEncoder.encode("LinkedToCharityId", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(
                                        mainActivity.getFundraiserToBeAdded().getaCharity().getMarkerId()), "UTF-8") + "&" +
                                URLEncoder.encode("FundraiserTime", "UTF-8") + "=" + URLEncoder.encode(mainActivity.getFundraiserToBeAdded().getTime(), "UTF-8") + "&" +
                                URLEncoder.encode(
                                        "MarkerId", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(mainActivity.getFundraiserToBeAdded().getMarkerId()), "UTF-8") + "&" +
                                URLEncoder.encode("LoginId", "UTF-8") + "=" + URLEncoder.encode(loginId, "UTF-8");
                        System.out.println(mainActivity.getFundraiserToBeAdded());
                        System.out.println(loginId);
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


        public void removeBookmark(final int markerId) {
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


                        String data = URLEncoder.encode("MarkerId", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(markerId), "UTF-8") + "&" +
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





    }
